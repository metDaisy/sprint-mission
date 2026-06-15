import { create } from 'zustand';
import { EventSourcePolyfill } from 'event-source-polyfill';
import config from '@/config';
import useAuthStore from './authStore';

interface SseState {
  eventSource: EventSource | null;
  isConnected: boolean;
  isConnecting: boolean;
  subscriptions: Map<string, (data: any) => void>;
  connect: () => Promise<void>;
  disconnect: () => void;
  subscribe: (topic: string, callback: (data: any) => void) => void;
  unsubscribe: (topic: string) => void;
}

export const useSseStore = create<SseState>((set, get) => ({
  eventSource: null,
  isConnected: false,
  isConnecting: false,
  subscriptions: new Map(),

  connect: async () => {
    const { isConnected, isConnecting } = get();
    if (isConnected || isConnecting) return;
    set({ isConnecting: true });

    const accessToken = useAuthStore.getState().accessToken;
    if (!accessToken) {
      set({ isConnected: false, isConnecting: false });
      return;
    }

    try {
      const eventSource = new EventSourcePolyfill(
          `${config.sseBaseUrl}`,
          {
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
            withCredentials: true,
          }
      );

      eventSource.onopen = () => {
        set({ eventSource, isConnected: true, isConnecting: false });
        console.log('SSE 연결 성공');
      };

      eventSource.onerror = (error: any) => {
        console.error('SSE 에러:', {error, readyState: eventSource.readyState});
        set({ isConnected: false, isConnecting: eventSource.readyState === EventSourcePolyfill.CONNECTING, eventSource: null });
      };

    } catch (error) {
      console.error('SSE 연결 시도 중 에러:', error);
      set({ isConnected: false, isConnecting: false, eventSource: null });
    }
  },

  disconnect: () => {
    const { eventSource, isConnected, subscriptions } = get();
    if (eventSource && isConnected) {
      // 모든 구독 해제
      subscriptions.forEach((callback, topic) => {
        eventSource.removeEventListener(topic, callback);
      });
      eventSource.close();
      set({ eventSource: null, isConnected: false });
    }
  },

  subscribe: (topic, callback) => {
    const { eventSource, isConnected, subscriptions } = get();

    if (subscriptions.has(topic)) {
      console.log('already subscribed', topic)
      return;
    }

    // 구독 정보 저장
    const wrappedCallback = (event: MessageEvent) => {
      try {
        const data = JSON.parse(event.data);
        callback(data);
      } catch (error) {
        console.error('SSE 메시지 파싱 에러:', error);
        callback(event.data); // JSON 파싱 실패시 원본 데이터 전달
      }
    };

    if (eventSource && isConnected) {
      console.log('eventSource.subscribe', topic)
      eventSource.addEventListener(topic, wrappedCallback);

      subscriptions.set(topic, wrappedCallback);
      set({ subscriptions });
    }
  },

  unsubscribe: (topic) => {
    const { eventSource, isConnected, subscriptions } = get();

    if (eventSource && isConnected) {
      const callback = subscriptions.get(topic);
      if (callback) {
        eventSource.removeEventListener(topic, callback);
      }
    }

    subscriptions.delete(topic);
    set({ subscriptions });
  }
}));
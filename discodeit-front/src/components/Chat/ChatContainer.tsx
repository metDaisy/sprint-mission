import {WS_DESTINATIONS} from '@/constants/websocket';
import WebSocket from "@/components/WebSocket/WebSocket.tsx";
import {ChannelDto, MessageDto} from '@/types/api';
import {useCallback, useState} from "react";
import ChatInput from "@/components/Chat/ChatInput";

interface ChatContainerProps {
  channel: ChannelDto | null;
}

function ChatContainer({channel}: ChatContainerProps): JSX.Element {
  const [messages, setMessages] = useState<MessageDto[]>([]);

  // 1. 웹소켓으로 새 메시지가 날아왔을 때 실행될 콜백 함수
  const handleNewMessage = useCallback((newMessage: MessageDto) => {
    setMessages((prev) => [...prev, newMessage]);
  }, []);

  return (
      <div>
        {channel && (
            <WebSocket
                destination={WS_DESTINATIONS.TOPIC_CHANNEL(channel.id)}
                subscribeCallback={handleNewMessage}
            />
        )}
        {/* 3. 메시지 목록 렌더링 */}
        <div className="chat-window">
          {messages.map(msg => <div key={msg.id}>{msg.content}</div>)}
        </div>

        {/* 입력창 컴포넌트는 아래 3단계 참고 */}
        <ChatInput channelId={channel?.id}/>
      </div>
  );
}

export default ChatContainer; 

export const WS_DESTINATIONS = {
  // 백엔드의 SUB_ENDPOINT (/sub) 와 일치해야 합니다.
  TOPIC_CHANNEL: (channelId: string) => `/sub/channels/${channelId}`,
  TOPIC_CHANNEL_LIST: '/sub/channels',
  TOPIC_PRESENCE: '/sub/presence',
  TOPIC_NOTIFICATION: (userId: string) => `/sub/users/${userId}/notifications`,
  TOPIC_BINARY_CONTENT: '/sub/binary-contents',

  // 백엔드의 PUB_ENDPOINT (/pub) 와 일치해야 합니다.
  APP_CHANNEL_MESSAGE: (channelId: string) => `/pub/channels/${channelId}/messages`,
};

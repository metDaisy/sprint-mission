const WS_PREFIX = {
  SUB: '/sub',
  PUB: '/pub',
}

export const WS_DESTINATIONS = {
  SUB_MESSAGE: (channelId: string) => `${WS_PREFIX.SUB}/channels/${channelId}`,
  SUB_CHANNEL: `${WS_PREFIX.SUB}/channels`,
  SUB_USERS: `${WS_PREFIX.SUB}/users`,
  SUB_NOTIFICATION: (userId: string) => `${WS_PREFIX.SUB}/users/${userId}/notifications`,
  SUB_BINARY_CONTENT: `${WS_PREFIX.SUB}/binary-contents`,

  PUB_MESSAGE: (channelId: string) => `${WS_PREFIX.PUB}/channels/${channelId}/messages`,
};

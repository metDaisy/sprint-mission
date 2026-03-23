package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ChannelServiceDTO.*;
import com.sprint.mission.discodeit.dto.MessageServiceDTO.MessageResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicChannelService extends BasicDomainService<Channel> implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;
    private final IdGenerator idGenerator;

    @Override
    public ChannelResponse create(ChannelCreateRequest model) {
        if (model.type() == ChannelType.PUBLIC) {
            return createPublicChannel(
                    new PublicChannelCreateRequest(model.channelName(), model.description()));
        }
        return createPrivateChannel(
                new PrivateChannelCreateRequest(model.userIdsInChannel()));
    }

    @Override
    public ChannelResponse find(UUID channelId) {
        Channel channel = findById(channelId);
        MessageResponse lastMessageResponse = getLastMessageResponse(channelId);
        return channel.toResponse(lastMessageResponse.createdAt());
    }

    @Override
    public List<ChannelResponse> findAllByUserId(UUID userId) {
        return channelRepository.filter(channel -> isVisibleTo(channel, userId))
                .map(channel -> {
                    MessageResponse lastMessageResponse = getLastMessageResponse(channel.getId());
                    return channel.toResponse(lastMessageResponse.createdAt());
                })
                .toList();
    }

    @Override
    public ChannelResponse update(PublicChannelUpdateRequest model) {
        // todo refactoring
        Channel channel = findById(model.channelId());
        MessageResponse lastMsgResp = getLastMessageResponse(channel.getId());
        if (channel.matchChannelType(ChannelType.PRIVATE)) {
            // private channel can't be modified
            return channel.toResponse(lastMsgResp.createdAt());
        }
        channel.update(model.newName(), model.newDescription());
        channelRepository.save(channel);
        return channel.toResponse(lastMsgResp.createdAt());
    }

    @Override
    public void delete(UUID channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException(ID_NOT_FOUND.formatted("Channel", channelId));
        }
        List<UUID> msgToDelete = messageRepository.filter(message -> message.isInChannel(channelId))
                .map(Message::getId)
                .toList();
        msgToDelete.forEach(messageRepository::deleteById);

        List<UUID> readStatusToDelete = readStatusRepository.filter(readStatus -> readStatus.matchChannelId(channelId))
                .map(ReadStatus::getId)
                .toList();
        readStatusToDelete.forEach(readStatusRepository::deleteById);

        channelRepository.deleteById(channelId);
    }

    @Override
    protected Channel findById(UUID id) {
        return findEntityById(id, "Channel", channelRepository);
    }

    private ChannelResponse createPrivateChannel(PrivateChannelCreateRequest model) {
        Channel channel = new Channel(idGenerator.generateId(), model.userIdsInPrivateChannel());
        channelRepository.save(channel);
        ChannelResponse response = channel.toResponse();
        response.userIdsInPrivateChannel()
                .stream()
                .map(userId -> new ReadStatus(userId, response.channelId()))
                .forEach(readStatusRepository::save);
        return response;
    }

    private ChannelResponse createPublicChannel(PublicChannelCreateRequest model) {
        Channel channel = new Channel(idGenerator.generateId(), model.channelName(), model.description());
        channelRepository.save(channel);
        return channel.toResponse();
    }

    private MessageResponse getLastMessageResponse(UUID channelId) {
        return messageRepository.filter(message -> message.isInChannel(channelId))
                .max(Message::compareTo)
                .orElseThrow(() -> new NoSuchElementException("this channel have no message"))
                .toResponse();
    }

    private boolean isVisibleTo(Channel channel, UUID userId) {
        return channel.matchChannelType(ChannelType.PUBLIC)
                || channel.isPrivateMember(userId);
    }
}

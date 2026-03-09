package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.PrivateChannelCreateDto;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.PublicChannelCreateDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicChannelService extends BasicDomainService<Channel> implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;
    private final ChannelMapper channelMapper;

    @Override
    public ChannelResponse createPublic(PublicChannelCreateDto dto) {
        Channel channel = new Channel(dto);
        channelRepository.save(channel);
        return channelMapper.toResponse(channel);
    }

    @Override
    public ChannelResponse createPrivate(PrivateChannelCreateDto dto) {
        Channel channel = new Channel(dto);
        channelRepository.save(channel);
        dto.participants()
                .stream()
                .map(ChannelMapper.userMapper::toEntity)
                .map(user -> new ReadStatus(user, channel, Instant.MIN))
                .forEach(readStatusRepository::save);
        return channelMapper.toResponse(channel);
    }

    @Override
    public ChannelResponse find(UUID id) {
        return channelMapper.toResponse(findById(id));
    }

    @Override
    public List<ChannelResponse> findAllByUserId(UUID userId) {
        return channelRepository.findAll()
                .stream()
                .filter(channel -> channel.isVisibleTo(userId))
                .map(channelMapper::toResponse)
                .toList();
    }

    @Override
    public ChannelResponse update(ChannelDto dto) {
        Channel channel = findById(dto.id());
//        MessageResponse lastMsgResp = getLastMessageResponse(channel.getId());
        ensure(ChannelType.PRIVATE,
                channel::matchChannelType,
                type -> new APIException(ErrorCode.PRIVATE_CHANNEL_NOT_UPDATE, dto.id()));
        channel.update(dto);
        channelRepository.save(channel);
        return channelMapper.toResponse(channel);
    }

    @Override
    public void delete(UUID id) {
        ensure(id, channelRepository::existsById, val -> new APIException(ErrorCode.CHANNELID_NOT_FOUND, val));
        List<Message> msgToDelete = messageRepository.findAllByChannelId(id);
        messageRepository.deleteAll(msgToDelete);
        List<ReadStatus> readStatuses = readStatusRepository.findAllByChannelId(id);
        readStatusRepository.deleteAll(readStatuses);
        channelRepository.deleteById(id);
    }

    @Override
    protected Channel findById(UUID id) {
        return getOrThrow(id, channelRepository::findById,
                () -> new APIException(ErrorCode.CHANNELID_NOT_FOUND, id));
    }

    // deprecated ?
//    private MessageDto getLastMessageResponse(UUID channelId) {
//        return messageRepository.filter(message -> message.isInChannel(channelId))
//                .max(Message::compareTo)
//                .orElseThrow(() -> new NoSuchElementException("this channel have no message"))
//                .toResponse();
//    }
}

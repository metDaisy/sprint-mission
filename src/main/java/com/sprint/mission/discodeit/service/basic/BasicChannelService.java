package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.PrivateChannelCreateDto;
import com.sprint.mission.discodeit.dto.channel.ChannelServiceDTO.PublicChannelCreateDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class BasicChannelService extends BasicDomainService<Channel> implements ChannelService {
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final ChannelMapper channelMapper;

    @Override
    public ChannelResponse createPublic(PublicChannelCreateDto dto) {
        Channel channel = channelMapper.fromDto(dto);
        channelRepository.save(channel);
        return channelMapper.toResponse(channel);
    }

    // todo: [warn] user id not found
    @Override
    public ChannelResponse createPrivate(PrivateChannelCreateDto dto) {
        Channel channel = channelMapper.fromDto(dto);
        channelRepository.save(channel);

        // todo: possible to be tuned ?
        List<ReadStatus> readStatuses = userRepository.findAllById(dto.participantIds())
                .stream()
                .map(user -> new ReadStatus(user, channel, Instant.now()))
                .toList();

        readStatusRepository.saveAll(readStatuses);
        return channelMapper.toResponse(channel);
    }

    @Override
    @Transactional(readOnly = true)
    public ChannelResponse find(UUID id) {
        Channel channel = findById(id);
        return channelMapper.toResponse(channel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChannelResponse> findAllByUserId(UUID userId) {
        return channelRepository.findVisibleToWithLastMsgAt(userId)
                .stream()
                .map(channelMapper::toResponse)
                .toList();
    }

    @Override
    public ChannelResponse update(ChannelDto dto) {
        if (dto.type() == ChannelType.PRIVATE) {
            throw new APIException(ErrorCode.PRIVATE_CHANNEL_CANT_BE_UPDATED, dto.id());
        }
        Channel channel = findById(dto.id());
        channel.update(dto);
        return channelMapper.toResponse(channel);
    }

    @Override
    public void delete(UUID id) {
        deleteByIdOrThrow(id, channelRepository, new APIException(ErrorCode.CHANNELID_NOT_FOUND, id));
    }

    @Override
    protected Channel findById(UUID id) {
        return getOrThrow(id, channelRepository::findByIdWithLastMsgAt,
                () -> new APIException(ErrorCode.CHANNELID_NOT_FOUND, id));
    }

}

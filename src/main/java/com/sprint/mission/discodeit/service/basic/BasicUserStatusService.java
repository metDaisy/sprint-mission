package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusServiceDTO.UserStatusDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusServiceDTO.UserStatusResponse;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService extends BasicDomainService<UserStatus> implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserStatusMapper userStatusMapper;

    @Override
    public UserStatusResponse update(UserStatusDto dto) {
        UserStatus status = findById(dto.userId());
        status.update(dto);
        userStatusRepository.save(status);
        return userStatusMapper.toResponse(status);
    }

    @Override
    protected UserStatus findById(UUID userId) {
        return getOrThrow(userId, userStatusRepository::findByUserId,
                () -> new APIException(ErrorCode.USERSTATUSID_NOT_FOUND, userId));
    }
}

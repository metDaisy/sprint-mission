package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserCreateDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserResponse;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserUniquenessDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserUpdateDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class BasicUserService extends BasicDomainService<User> implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserResponse find(UUID id) {
        return userMapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse create(UserCreateDto dto) {
        validateUserUniqueness(dto);
        UserStatus status = new UserStatus();
        BinaryContent profile = registerProfile(dto.profile());
        User user = new User(dto, profile, status);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    /*
     * todo: Regarding profile image updates.
     *  If an image already exists and the received image is null,
     *  distinguish whether to delete the image or not update it.
     * */
    @Override
    public UserResponse update(UserUpdateDto dto) {
        validateUserUniqueness(dto);
        User user = findById(dto.id());
        user.update(dto);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    // todo: delete cascade ?
    @Override
    public void delete(UUID id) {
        deleteByIdOrThrow(id, userRepository, new APIException(ErrorCode.USERID_NOT_FOUND, id));
    }

    @Override
    protected User findById(UUID id) {
        return getOrThrow(id, userRepository::findById, () -> new APIException(ErrorCode.USERID_NOT_FOUND, id));
    }

    private void validateUserUniqueness(UserUniquenessDto dto) {
        ensure(dto.username(),
                userRepository::existsByUsername,
                value -> new APIException(ErrorCode.USERNAME_ALREADY_EXIST, value));
        ensure(dto.email(),
                userRepository::existsByEmail,
                value -> new APIException(ErrorCode.EMAIL_ALREADY_EXIST, value));
    }

    private BinaryContent registerProfile(BinaryContentDto profile) {
        return Optional.ofNullable(profile)
                .map(UserMapper.profileImageMapper::toEntity)
                .orElse(null);
    }
}

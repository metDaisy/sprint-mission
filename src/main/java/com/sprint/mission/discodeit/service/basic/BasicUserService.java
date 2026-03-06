package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserCreateDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserResponse;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserUniquenessDto;
import com.sprint.mission.discodeit.dto.user.UserServiceDTO.UserUpdateDto;
import com.sprint.mission.discodeit.dto.user.request.UserFindRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Transactional, not yet
@Service
@RequiredArgsConstructor
public class BasicUserService extends BasicDomainService<User> implements UserService {
    private final UserRepository userRepository;
    private final BinaryContentRepository profileRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserMapper userMapper;

    // deprecated ?
    @Override
    public UserResponse find(UserFindRequest request) {
        return null;
    }

    // deprecated ?
    @Override
    public UserResponse find(UUID userId) {
        return null;
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse create(UserCreateDto dto) {
        validateUserUniqueness(dto);
        BinaryContent profileImage = registerProfile(dto.profile());
        UserStatus status = new UserStatus();
        User user = userMapper.toEntity(dto, profileImage, status);
        userStatusRepository.save(status);
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
        BinaryContent newProfileImage = registerProfile(dto.profile());
        user.update(newProfileImage);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    // todo: delete cascade ?
    @Override
    public void delete(UUID id) {
        User user = findById(id);
        profileRepository.delete(user.getProfile());
        userStatusRepository.delete(user.getStatus());
        userRepository.delete(user);
    }

    @Override
    protected User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new APIException(ErrorCode.USERID_NOT_FOUND, id));
    }

    private void validateUserUniqueness(UserUniquenessDto dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new APIException(ErrorCode.USERNAME_ALREADY_EXIST, dto.username());
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new APIException(ErrorCode.EMAIL_ALREADY_EXIST, dto.email());
        }
    }

    private BinaryContent registerProfile(BinaryContentDto profile) {
        return Optional.ofNullable(profile)
                .map(UserMapper.profileImageMapper::toEntity)
                .map(profileRepository::save)
                .orElse(null);
    }
}

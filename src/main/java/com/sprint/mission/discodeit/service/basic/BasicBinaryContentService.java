package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentResponse;
import com.sprint.mission.discodeit.dto.binarycontent.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService extends BasicDomainService<BinaryContent> implements BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentMapper binaryContentMapper;

    @Override
    public List<BinaryContentResponse> findAllByIdIn(List<UUID> ids) {
        return ids.stream()
                .map(this::find)
                .toList();
    }

    @Override
    public BinaryContentResponse create(BinaryContentCreateRequest request) {
        BinaryContent content = binaryContentMapper.toEntityFromRequest(request);
        binaryContentRepository.save(content);
        return binaryContentMapper.toResponse(content);
    }

    @Override
    public BinaryContentResponse find(UUID id) {
        return binaryContentMapper.toResponse(findById(id));
    }

    @Override
    public void delete(UUID id) {
        deleteByIdOrThrow(id, binaryContentRepository, new APIException(ErrorCode.BINARYCONTENTID_NOT_FOUND, id));
    }

    @Override
    protected BinaryContent findById(UUID id) {
        return getOrThrow(id, binaryContentRepository::findById,
                () -> new APIException(ErrorCode.BINARYCONTENTID_NOT_FOUND, id));
    }
}

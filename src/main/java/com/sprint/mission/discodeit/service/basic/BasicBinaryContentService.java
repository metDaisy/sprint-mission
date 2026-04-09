package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.exception.binarycontent.BinaryContentErrorCode;
import com.sprint.mission.discodeit.exception.binarycontent.BinaryContentException;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BasicBinaryContentService extends BasicDomainService<BinaryContent>
    implements BinaryContentService {

  private final BinaryContentRepository binaryContentRepository;
  private final BinaryContentMapper binaryContentMapper;

  @Override
  @Transactional(readOnly = true)
  public List<BinaryContentDto> findAllByIdIn(List<UUID> ids) {
    return binaryContentMapper.toDto(binaryContentRepository.findAllById(ids));
  }

  @Override
  @Transactional(readOnly = true)
  public BinaryContentDto find(UUID id) {
    return binaryContentMapper.toDto(findById(id));
  }

  @Override
  protected BinaryContent findById(UUID id) {
    return getOrThrow(id, binaryContentRepository::findById,
        value -> new BinaryContentException(BinaryContentErrorCode.BINARYCONTENTID_NOT_FOUND,
            value));
  }
}

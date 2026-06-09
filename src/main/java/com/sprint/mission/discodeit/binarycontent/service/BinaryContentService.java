package com.sprint.mission.discodeit.binarycontent.service;

import com.sprint.mission.discodeit.binarycontent.controller.dto.response.BinaryContentDto;
import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.binarycontent.domain.exception.BinaryContentErrorCode;
import com.sprint.mission.discodeit.binarycontent.domain.exception.BinaryContentException;
import com.sprint.mission.discodeit.binarycontent.controller.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.binarycontent.infra.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.common.api.request.FileUploadRequest;
import com.sprint.mission.discodeit.common.storage.event.FileUploadEventPublisher;
import com.sprint.mission.discodeit.common.support.DomainServiceSupport;
import com.sprint.mission.discodeit.global.log.ServiceLogAround;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BinaryContentService {

  private final BinaryContentRepository repository;
  private final BinaryContentMapper mapper;
  private final FileUploadEventPublisher eventPublisher;
  private final String URL_TEMPLATE = "/binaryContents/%s";

  @ServiceLogAround
  public List<String> create(List<FileUploadRequest> request) {
    List<BinaryContent> entities = mapper.toEntityFrom(request);
    repository.saveAll(entities);
    eventPublisher.publishAllFileUploadEvent(entities, request);
    return entities.stream().map(BinaryContent::getId).map(this::getUrl).toList();
  }

  @ServiceLogAround
  @Transactional(readOnly = true)
  public List<BinaryContentDto> findAllByIdIn(List<UUID> ids) {
    return mapper.toDto(repository.findAllCompletedByIds(ids));
  }

  @ServiceLogAround
  @Transactional(readOnly = true)
  public BinaryContentDto find(UUID id) {
    return mapper.toDto(findById(id));
  }

  private BinaryContent findById(UUID id) {
    return DomainServiceSupport.getOrThrow(id, repository::findCompletedById,
        value -> new BinaryContentException(BinaryContentErrorCode.BINARYCONTENTID_NOT_FOUND,
            value));
  }

  private String getUrl(UUID id) {
    return URL_TEMPLATE.formatted(id);
  }
}

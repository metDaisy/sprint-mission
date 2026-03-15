package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.common.exception.code.ErrorCode;
import com.sprint.mission.discodeit.common.exception.custom.APIException;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentDto;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentServiceDTO.BinaryContentResponse;
import com.sprint.mission.discodeit.dto.binarycontent.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.config.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(config = GlobalMapperConfig.class)
public interface BinaryContentMapper extends BaseMapper<BinaryContentDto, BinaryContent, BinaryContentResponse> {
    // todo: add 'read file error'
    default BinaryContentDto toDtoFromFile(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            return BinaryContentDto.builder()
                    .fileName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .bytes(bytes)
                    .build();
        } catch (IOException e) {
            throw new APIException(ErrorCode.FILE_CANT_READ, file.getName());
        }
    }

    BinaryContent toEntityFromRequest(BinaryContentCreateRequest request);
}

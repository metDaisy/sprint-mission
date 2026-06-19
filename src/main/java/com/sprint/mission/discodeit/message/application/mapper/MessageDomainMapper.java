package com.sprint.mission.discodeit.message.application.mapper;

import com.sprint.mission.discodeit.common.mapper.GenericDomainMapper;
import com.sprint.mission.discodeit.common.mapper.config.GlobalMapperConfig;
import com.sprint.mission.discodeit.message.domain.entity.Message;
import com.sprint.mission.discodeit.message.presentation.dto.request.MessageUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface MessageDomainMapper extends GenericDomainMapper<MessageUpdateRequest, Message> {

}

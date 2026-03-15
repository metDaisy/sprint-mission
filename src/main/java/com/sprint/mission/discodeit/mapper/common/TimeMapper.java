package com.sprint.mission.discodeit.mapper.common;

import com.sprint.mission.discodeit.common.util.TimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.Instant;
import java.time.LocalDate;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TimeMapper {

    default Instant toInstant(LocalDate time) {
        return TimeConverter.toInstant(time);
    }

    default LocalDate toDateTime(Instant time) {
        return TimeConverter.toDateTime(time);
    }
}

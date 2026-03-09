package com.sprint.mission.discodeit.dto.binarycontent.request;

public record BinaryContentCreateRequest(String fileName, byte[] bytes, String contentType, Long size) {
}

package com.sprint.mission.discodeit.config.aws.property;

import lombok.experimental.Delegate;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
public record AwsProperties(@Delegate Credentials credentials,
                            @Delegate S3 s3,
                            @Delegate String region) {

  public record Credentials(String accessKey, String secretKey) {

  }

  public record S3(String bucket) {

  }
}

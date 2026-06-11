package com.sprint.mission.discodeit.global.infra.storage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("dev")
@Configuration
public class StorageResourceHandlerConfig implements WebMvcConfigurer {

  @Value("${discodeit.storage.local.root-path}")
  private String storagePath;
  @Value("${discodeit.storage.local.prefix-path}")
  private String storagePrefixPath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(storagePrefixPath + "/*")
        .addResourceLocations(new FileSystemResource(storagePath));
  }
}

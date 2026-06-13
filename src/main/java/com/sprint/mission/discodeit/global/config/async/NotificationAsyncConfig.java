package com.sprint.mission.discodeit.global.config.async;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
class NotificationAsyncConfig {

  @Bean(name = "notificationWorker")
  public Executor notificationWorker() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setQueueCapacity(20);
    executor.setMaxPoolSize(30);
    executor.setThreadNamePrefix("notificationWorker-Async-");
    executor.initialize();
    return executor;
  }
}

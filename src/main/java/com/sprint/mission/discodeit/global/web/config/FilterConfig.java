package com.sprint.mission.discodeit.global.web.config;

import com.sprint.mission.discodeit.global.web.filter.MdcLoggingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterConfig {

  @Value("${discodeit.api-prefix}")
  private String API_PREFIX;

  @Bean
  public FilterRegistrationBean<MdcLoggingFilter> loggingFilter() {
    FilterRegistrationBean<MdcLoggingFilter> loggingFilter = new FilterRegistrationBean<>();
    loggingFilter.setFilter(new MdcLoggingFilter());
    loggingFilter.setOrder(Ordered.HIGHEST_PRECEDENCE);
    loggingFilter.addUrlPatterns(resolveUrl("/**"));
    return loggingFilter;
  }

  private String resolveUrl(String url) {
    return API_PREFIX + url;
  }
}

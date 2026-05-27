package com.sprint.mission.discodeit.global.security.config;

import com.sprint.mission.discodeit.auth.repository.RememberMeTokenRepository;
import com.sprint.mission.discodeit.auth.security.DiscodeitUserDetailsService;
import com.sprint.mission.discodeit.global.security.handler.ForbiddenAccessHandler;
import com.sprint.mission.discodeit.global.security.handler.UnauthenticatedEntryPoint;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

  @Value("${discodeit.api-prefix}")
  private String API_PREFIX;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http,
      AuthenticationSuccessHandler loginSuccessHandler,
      AuthenticationFailureHandler loginFailureHandler,
      LogoutSuccessHandler logoutSuccessHandler,
      DiscodeitUserDetailsService userDetailsService,
      RememberMeTokenRepository rememberMeTokenRepository)
      throws Exception {
    return http.csrf(
            csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(
            session ->
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .sessionConcurrency(concurrency ->
                        concurrency.sessionRegistry(sessionRegistry())
                            .maximumSessions(1)
                            .maxSessionsPreventsLogin(false)))
        .formLogin(form ->
            form.loginProcessingUrl(resolveUrl("/auth/login"))
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler))
        .rememberMe(remember -> remember.key("discodeit-remember-key")
            .rememberMeParameter("remember-me")
            .tokenValiditySeconds(60 * 60 * 24 * 7)
            .userDetailsService(userDetailsService)
            .tokenRepository(rememberMeTokenRepository))
        .logout(logout -> logout.logoutUrl(resolveUrl("/auth/logout"))
            .logoutSuccessHandler(logoutSuccessHandler)
            .deleteCookies("JSESSIONID", "XSRF-TOKEN")
            .permitAll())
        .httpBasic(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(HttpMethod.GET,
                        resolveUrl("/auth/csrf-token"),
                        "/actuator/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/api.json").permitAll()
                    .requestMatchers(HttpMethod.POST,
                        resolveUrl("/auth/login"),
                        resolveUrl("/users")).permitAll()
                    .anyRequest().authenticated())
        .exceptionHandling(
            exception -> exception
                .authenticationEntryPoint(new UnauthenticatedEntryPoint())
                .accessDeniedHandler(new ForbiddenAccessHandler()))
        .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost"));
    config.setAllowedMethods(
        Stream.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.DELETE,
                HttpMethod.OPTIONS)
            .map(HttpMethod::toString).toList());
    config.setAllowedHeaders(List.of("authorization", "content-type", "x-xsrf-token"));
    config.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration(resolveUrl("/**"), config);
    return source;
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  private String resolveUrl(String url) {
    return API_PREFIX + url;
  }
}

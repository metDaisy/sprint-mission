package com.sprint.mission.discodeit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.auth.DiscodeitUserDetailsService;
import com.sprint.mission.discodeit.auth.converter.JsonUserPasswordAuthentificationConverter;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final ObjectMapper objectMapper;
  private final DiscodeitUserDetailsService userDetailsService;

  @Value("${discodeit.api-prefix}")
  private String API_PREFIX;

  @Bean
  public GrantedAuthorityDefaults grantedAuthorityDefaults() {
    return new GrantedAuthorityDefaults("");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    JsonUserPasswordAuthentificationConverter converter = new JsonUserPasswordAuthentificationConverter(
        objectMapper);
    AuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
    AuthenticationManager authManager = new ProviderManager(provider);
    AuthenticationFilter filter = new AuthenticationFilter(authManager, converter);
    filter.setRequestMatcher(
        PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, resolveUrl("/auth/login"))
    );
    return http.csrf(
            csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .addFilterAt(filter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(resolveUrl("/auth/login"), resolveUrl("/auth/csrf-token"))
                    .permitAll()
                    .anyRequest().authenticated())
        .exceptionHandling(
            exception -> exception.authenticationEntryPoint(
                (request, response, authException) -> response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")))
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

  private String resolveUrl(String url) {
    return API_PREFIX + url;
  }
}

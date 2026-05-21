package com.szh.sms.config;

import com.szh.sms.utils.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC configuration.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(@NonNull CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowCredentials(true)
        .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
        .exposedHeaders("Authorization", "freshToken")
        .maxAge(3600 * 24);
  }

  @Override
  public void addInterceptors(@NonNull InterceptorRegistry registry) {
    registry.addInterceptor(appInterceptor())
        .addPathPatterns("/api/sms/**")
        .excludePathPatterns("/login", "/api/sms/score/sendEmail");
  }

  @Bean
  public HandlerInterceptor appInterceptor() {
    return new AuthenticationInterceptor();
  }
}

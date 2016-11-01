package com.ezhuanbing.api.authorize;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfigurerAdapter extends WebMvcConfigurerAdapter {

  @Override
  public void addCorsMappings(CorsRegistry registry) {

    registry.addMapping("/api/**")
            .allowedOrigins("*")
            .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE", "PATCH")
            .allowedHeaders("content-type", "Authorization", "accept")
            .allowCredentials(false)
            .maxAge(3600);
  }
}
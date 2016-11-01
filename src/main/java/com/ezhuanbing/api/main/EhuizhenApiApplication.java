package com.ezhuanbing.api.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.ezhuanbing.api")
@MapperScan("com.ezhuanbing.api.dao.mybatis.mapper")
public class EhuizhenApiApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(EhuizhenApiApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(EhuizhenApiApplication.class, args);
  }
}
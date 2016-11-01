package com.ezhuanbing.api.conf;

import com.ezhuanbing.api.authorize.AuthorityInterceptor;
import com.ezhuanbing.api.conf.json.DefaultBeanSerializerModifier;
/*import com.ezhuanbing.api.authorize.UserRoleInterceptor;*/
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

  @Value("${devMode:false}")
  private String devMode;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedMethods("GET", "POST", "PATCH", "DELETE");
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
      @Override
      public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers)
              throws IOException, JsonProcessingException {
        
        gen.writeString("");
      }
    });

   objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
            .withSerializerModifier(new DefaultBeanSerializerModifier()));

    jsonConverter.setObjectMapper(objectMapper);
    converters.add(jsonConverter);
  }

  @Bean
  public Filter characterEncodingFilter() {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding("UTF-8");
    characterEncodingFilter.setForceEncoding(true);
    return characterEncodingFilter;
  }

 @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //if ("true".equals(devMode)) return;

    registry.addInterceptor(new AuthorityInterceptor());
    super.addInterceptors(registry);
  }
 
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/iosjs/**").addResourceLocations("classpath:/iosjs/");
  }
}

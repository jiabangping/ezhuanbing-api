package com.ezhuanbing.api.authorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiControl {

  int functionId() default 0;

  String functionTitle() default "";

  //是否公开API，默认不公开
  APIAccessType apiAccessType() default APIAccessType.PRIVATE_API;
  APIType apiType() default APIType.DOCTOR;
}

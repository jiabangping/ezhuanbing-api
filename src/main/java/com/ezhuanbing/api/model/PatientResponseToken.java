package com.ezhuanbing.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wangwl on 2016/7/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseToken {
  private String loginName;
  private Integer id;
  private String token;
}

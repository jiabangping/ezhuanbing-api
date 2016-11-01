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
public class DoctorResponseToken {
  private String loginName;
  private Integer hospitalId;
  private String admin;
  private String token;
  private String departmentName;
  private Integer userId;
  private Integer doctorID;
  private Integer departId;
  
}

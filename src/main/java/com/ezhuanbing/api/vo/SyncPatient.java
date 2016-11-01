package com.ezhuanbing.api.vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SyncPatient {
  private int id;
  private String loginName;
  private String phoneNum;
  private String name;
  private String sex;
  private Date birthday;
  private String idCard;
  private Integer sysId;
  private Integer districtId;
}

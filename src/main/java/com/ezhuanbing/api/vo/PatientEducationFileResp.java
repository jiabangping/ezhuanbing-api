package com.ezhuanbing.api.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientEducationFileResp {
  private int id;
  private String title;
  private String profile;
  private String fileUrl;
  private String doctorName;
  private String departmentName;
  private Date sendTime;
}

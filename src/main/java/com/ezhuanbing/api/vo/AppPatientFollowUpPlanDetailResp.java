package com.ezhuanbing.api.vo;

import java.util.Date;

import com.ezhuanbing.api.tools.ImageConfigInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPatientFollowUpPlanDetailResp {

  private Integer planId;
  private Integer planDetailId;
  private Integer paperId;
  private String planMark;
  private String departmentName;
  private String doctorName;
  private Date sendTime;
  private String pushMessage;
  private Character status;
  /**
   * 0:普通随访 1：首次随访
   */
  private Integer planType;
  private Integer doctorId;
  private String photo;
  
  public String getDoctorPhotoUrl(){
    return ImageConfigInfo.GetImageServer("DoctorHeader", photo)+"?t="+new Date().getTime();
  }
  
}

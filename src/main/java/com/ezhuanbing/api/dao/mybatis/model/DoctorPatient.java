package com.ezhuanbing.api.dao.mybatis.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ezhuanbing.api.tools.ImageConfigInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="mb_doctorpatient")
public class DoctorPatient {
  
  @Id
  private Integer id;
  private Integer doctorId;
  private Integer patientId;
  private Integer groupId;
  private Date joinDate;
  
  @Transient
  private String doctorName;
  @Transient
  private String doctorPosition;
  @Transient
  private String doctorHospital;
  @Transient
  private String doctorDepartment;
  @Transient
  private String doctorGoodField;
  @Transient
  private String doctorPhotoUrl;
  @Transient
  private String doctorPhoto;
  
  public String getDoctorPhotoUrl(){
    if(doctorPhoto == null || doctorPhoto.isEmpty())
      return ImageConfigInfo.GetImageServer("DoctorHeader", "default.jpg");
    else
      return ImageConfigInfo.GetImageServer("DoctorHeader", doctorPhoto);
  }
}
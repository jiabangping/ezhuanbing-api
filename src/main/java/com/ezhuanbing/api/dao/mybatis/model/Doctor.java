package com.ezhuanbing.api.dao.mybatis.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ezhuanbing.api.conf.ConstantConfig;
import com.ezhuanbing.api.tools.ImageConfigInfo;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Doctor {
  
  private Integer doctorId;
  private String doctorName;
  private String doctorPosition;
  private String doctorHospital;
  private String doctorDepartment;
  private Integer doctorDepartmentId;
  private String doctorGoodField;
  private String doctorPhoto;
  private String doctorDescription;
  
  
  public String getDoctorPhotoUrl(){
    if(doctorPhoto == null || doctorPhoto.isEmpty())
      return ImageConfigInfo.GetImageServer("DoctorHeader", "default.jpg")+"?t="+new Date().getTime();
    else
      return ImageConfigInfo.GetImageServer("DoctorHeader", doctorPhoto)+"?t="+new Date().getTime();
  }
  
  //获取医生二维码
  public String getDoctorQrCodePicUrl(){
	  return ImageConfigInfo.GetImageServer(ConstantConfig.FtpPathConst.DoctorQrCodePic.name(), this.doctorId+".jpg")+"?t="+new Date().getTime();
  }
}

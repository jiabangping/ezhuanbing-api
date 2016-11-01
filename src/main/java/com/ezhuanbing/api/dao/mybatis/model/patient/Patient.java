package com.ezhuanbing.api.dao.mybatis.model.patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name="mb_patient")
public class Patient {

  @Id
  private Integer id;
  private String loginName;
  private String password;
  private String name;
  private String sex;
  private String photo;
  private Date birthday;
  private String idCard;
  private Integer height;
  private Integer weight;
  private String address;
  private Integer districtId;
  private String phoneNumber;
  private int sysId;
  private Character phoneType;
  private String phoneIMEI;
  
  @Transient
  private List<PatientExtraInfo> patientExtraInfo = new ArrayList<PatientExtraInfo>();
  
  public String getPhotoUrl(){
    if(photo == null || photo.isEmpty()){
      photo = "default.jpg";
    }
    return ImageConfigInfo.GetImageServer("PatientHeader", photo)+"?t="+new Date().getTime();
  }
  
  public String getStrSex(){
    if("0".equals(sex))
      return "女";
    else if("1".equals(sex))
      return "男";
    else
      return "未知";
  }
}
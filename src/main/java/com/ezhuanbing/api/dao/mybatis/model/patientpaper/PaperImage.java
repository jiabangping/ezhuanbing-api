package com.ezhuanbing.api.dao.mybatis.model.patientpaper;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.ezhuanbing.api.tools.ImageConfigInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mb_paperimage")
public class PaperImage {

  @Id
  private Integer id;
  private Integer paperId;
  private String imageName;
  private Date uploadTime;
  
  public void setImageName(String imageName) {
    this.imageName = imageName == null ? null : imageName.trim();
  }
  
  public String getImageUrl(){
    return ImageConfigInfo.GetImageServer("FllowupPlan", imageName)+"?t="+new Date().getTime();
  }
}
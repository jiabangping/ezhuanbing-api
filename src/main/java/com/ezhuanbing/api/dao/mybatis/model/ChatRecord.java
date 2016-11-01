package com.ezhuanbing.api.dao.mybatis.model;

import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ezhuanbing.api.im.netease.JsonAnalyseTool;
import com.ezhuanbing.api.tools.ImageConfigInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mb_chatrecord")
public class ChatRecord {

  @Id
  private Integer id;
  private Integer planDetailId;
  private String chatPersonId;
  private String chatPersonName;
  private String chatPersonImage;
  private Date chatDateTime;
  private String chatContent;
  private Character contentType;
  private Character isDoctorMsg;
  private Integer chatTarget;
  
  public String getChatPersonImage(){
    
    String directory = "";
    if(isDoctorMsg == '0'){
      directory = "PatientHeader";
    }else if(isDoctorMsg == '1'){
      directory = "DoctorHeader";
    }
    
    return ImageConfigInfo.GetImageServer(directory , chatPersonImage)+"?t="+new Date().getTime();
  }
  
  public String getChatContent(){
    if(contentType == '1'){
      return JsonAnalyseTool.getInfoByJsonCode("url",chatContent);
    }else{
      return chatContent;
    }
  }
}

package com.ezhuanbing.api.vo;

import java.util.ArrayList;
import java.util.List;

import com.ezhuanbing.api.conf.ConstantConfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPatientContentClass {
  
  private int id;
  private int patientPaperId;
  private String title;
  private int allItem;
  private int finishItem;
  
  public String getContenClassUrl(){
    return ConstantConfig.SERVER_URL + this.id +"&patientpaperId=" + patientPaperId;
  }
  
  private List<AppPatientContentClass> childs = new ArrayList<AppPatientContentClass>();
}

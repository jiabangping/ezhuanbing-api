package com.ezhuanbing.api.dao.mybatis.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="mb_patienteducationfile")
public class PatientEducationFile {
  
  @Id
  private Integer id;
  private Integer patientid;
  private Integer doctoreducationid;
  private Date sendTime;
}
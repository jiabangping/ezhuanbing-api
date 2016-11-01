package com.ezhuanbing.api.dao.mybatis.model.patient;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mb_patient_wx")
public class PatientWeixin {

  @Id
  private Integer id;

  private String loginName;

  private Integer systemId;

  private String wxOpendId;


}

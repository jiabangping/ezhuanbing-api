package com.ezhuanbing.api.dao.mybatis.model.patientpaper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 患者问卷分类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mb_patientcontentclass")
public class PatientContentClass {

  @Id
  private Integer id;

  private Integer patientPaperId;

  private Integer contentClassTemplatesId;

  private String className;

  private Integer classSort;

  private Integer classStatus;

  private Integer leaf;// 1:leaf

  private Integer pid;

  @Transient
  private List<PatientQuestions> patientQuestions = new ArrayList<PatientQuestions>();

  @Transient
  private List<PatientContentClass> childs = new ArrayList<PatientContentClass>();

}

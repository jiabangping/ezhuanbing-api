package com.ezhuanbing.api.dao.mybatis.model.followplan;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ezhuanbing.api.dao.mybatis.model.patient.Patient;

/**
 * 随访计划
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mb_patientfollowupplan")
public class PatientFollowUpPlan {

  @Id
  private Integer id;

  private Integer doctorId;

  private Integer patientId;

  private String planName;

  private Integer planType;

  private Date startDate;

  private Date endDate;

  private String status;

  private Integer isFinished;
  
  @Transient
  private Patient patient;

}

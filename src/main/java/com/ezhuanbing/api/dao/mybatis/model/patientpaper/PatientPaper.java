package com.ezhuanbing.api.dao.mybatis.model.patientpaper;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 患者问卷
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mb_patientpaper")
public class PatientPaper {
  
    @Id
    private Integer id;

    private Integer patientId;

    private Date patientPaperDateTime;

    private String paperDoctor;

    private Integer paperDoctorId;

    private String paperDept;

    private Integer paperDeptId;

    private String paperTitle;

    private Integer paperStatus;

    private String paperSummary;
    
    private Integer planDetailId;
    
    @Transient
    List<PatientContentClass> patientContentClasses;
   
}
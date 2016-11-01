package com.ezhuanbing.api.dao.mybatis.model;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="mb_patientCaseInfo")
public class PatientCaseInfo {
    private Integer id;
    private Integer departmentId;
    private Integer doctorid;
    private Integer patientid;
    private String complained;
    private String presentillness;
    private String pasthistory;
    private String physical;
    private String auxiliaryexamination;
 /*   private String diagnosis;
    private String opinionresult;*/

}
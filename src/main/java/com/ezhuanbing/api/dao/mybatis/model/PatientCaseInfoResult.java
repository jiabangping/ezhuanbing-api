package com.ezhuanbing.api.dao.mybatis.model;

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
@Table(name="mb_patientcaseinforesult")
public class PatientCaseInfoResult {
    @Id
    private Integer id;
    private Integer doctorid;
    private Integer plandetailid;
    private String diagnosis;
    private String opinionresult;
}
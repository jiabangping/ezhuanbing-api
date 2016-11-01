package com.ezhuanbing.api.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientCaseInfoResp {
    private Integer id;
    private Integer departmentId;
    private Integer doctorid;
    private Integer patientid;
    private Integer planId;
    private String patientName;
    private String patientAge;
    private String patientIdCard;
    private String patientSex;
    private String patientPhoneNumber;
    private String patientAddress;
    private String complained;
    private String presentillness;
    private String pasthistory;
    private String physical;
    private String auxiliaryexamination;
    private String diagnosis;
    private String opinionresult;
}
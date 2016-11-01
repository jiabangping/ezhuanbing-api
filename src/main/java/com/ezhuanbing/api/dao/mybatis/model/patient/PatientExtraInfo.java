package com.ezhuanbing.api.dao.mybatis.model.patient;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="mb_patientextrainfo")
public class PatientExtraInfo {
    @Id
    private Integer id;
    private Integer patientId;
    private String dictionarytypename;
    private Integer dictionarycode;
    private String dictionaryname;
    private String content;
}
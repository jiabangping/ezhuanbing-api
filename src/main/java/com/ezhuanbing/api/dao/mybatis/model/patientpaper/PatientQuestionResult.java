package com.ezhuanbing.api.dao.mybatis.model.patientpaper;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *	患者问卷答案
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mb_patientquestionresult")
public class PatientQuestionResult {
    private Integer id;

    private Integer patientQuestionsId;

    private Integer optionTemplatesId;

    private String optionNumber;

    private Integer optionType;

    private String optionPicture;

    private String optientResult;

    private Integer optientStatus;

    private String optionContent;
    
    private String optionOther;
    
}
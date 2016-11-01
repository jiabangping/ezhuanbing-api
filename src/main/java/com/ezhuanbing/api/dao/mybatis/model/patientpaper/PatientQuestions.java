package com.ezhuanbing.api.dao.mybatis.model.patientpaper;

import java.util.Date;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *  患者问卷题目
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mb_patientquestions")
public class PatientQuestions {
    private Integer id;

    private Integer patientContentClassId;

    private Integer qstType;

    private String qstTitle;

    private Integer qstStatus;

    private Date qstAnswerDateTime;

    private Integer questionsTemplatesId;
    
    private String qstPicture;

    private String qstAnswer;
    
    @Transient
    private List<PatientQuestionResult> questionResults;
    
}
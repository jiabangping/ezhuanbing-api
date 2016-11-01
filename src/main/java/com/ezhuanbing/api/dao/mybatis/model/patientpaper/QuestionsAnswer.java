package com.ezhuanbing.api.dao.mybatis.model.patientpaper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *  患者问卷答案
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsAnswer {
    private String questionId;
    private List<Answer> answers;
    /*private Answers answers;*/
}
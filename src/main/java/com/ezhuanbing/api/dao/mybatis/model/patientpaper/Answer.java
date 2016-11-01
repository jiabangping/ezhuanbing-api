package com.ezhuanbing.api.dao.mybatis.model.patientpaper;


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
public class Answer {
    private String answerId;
    private String type;
    private String value;
}
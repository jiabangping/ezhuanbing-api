package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;

import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientQuestionResult;
import com.ezhuanbing.noscan.BaseMapper;

public interface PatientQuestionResultMapper extends BaseMapper<PatientQuestionResult> {

  List<PatientQuestionResult> selectQuestionResultsByQuestion(Integer id);
  
  PatientQuestionResult selectQuestionResult(PatientQuestionResult patientQuestionResult);
  
  List<PatientQuestionResult> selectQuestionResultsBy(PatientQuestionResult patientQuestionResult);
  void updateByPrimary (PatientQuestionResult patientQuestionResult);
}
package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;

import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientQuestions;
import com.ezhuanbing.noscan.BaseMapper;

public interface PatientQuestionsMapper extends BaseMapper<PatientQuestions> {

  List<PatientQuestions> selectPatientQuestionsByPatientContentClass(Integer id);
  PatientQuestions selectByIdPatientContentClass(Integer id);
  void updateByPrimary(PatientQuestions patientQuestions);
}
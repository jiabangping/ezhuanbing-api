package com.ezhuanbing.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.PatientQuestionResultMapper;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientQuestionResult;
@Service
public class PatientQuestionResultService {

	@Autowired
	PatientQuestionResultMapper patientQuestionResultMapper;
   
	
/*	public PatientQuestionsWithBLOBs selectByPrimaryKey(Integer id) {
		return patientQuestionsMapper.selectByPrimaryKey(id);
	}*/
	public List<PatientQuestionResult> selectPatientQuestionsByPatientContentClass(Integer id) {
	  return patientQuestionResultMapper.selectQuestionResultsByQuestion(id);
  }
	
	public List<PatientQuestionResult> selectPatientQuestionsBy(PatientQuestionResult patientQuestionResult) {
      return patientQuestionResultMapper.selectQuestionResultsBy(patientQuestionResult);
  }
	
	public PatientQuestionResult selectQuestionResult(PatientQuestionResult patientQuestionResult) {
      return patientQuestionResultMapper.selectQuestionResult(patientQuestionResult);
  }
	
	public void updateByPrimary(PatientQuestionResult patientQuestionResult) {
      patientQuestionResultMapper.updateByPrimary(patientQuestionResult);
  }
}

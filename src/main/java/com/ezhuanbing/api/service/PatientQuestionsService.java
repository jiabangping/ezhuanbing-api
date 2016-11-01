package com.ezhuanbing.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.PatientQuestionsMapper;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientQuestions;

@Service
public class PatientQuestionsService {

	@Autowired
	PatientQuestionsMapper patientQuestionsMapper;
   
	
/*	public PatientQuestionsWithBLOBs selectByPrimaryKey(Integer id) {
		return patientQuestionsMapper.selectByPrimaryKey(id);
	}*/
	public List<PatientQuestions> selectPatientQuestionsByPatientContentClass(Integer id) {
      return patientQuestionsMapper.selectPatientQuestionsByPatientContentClass(id);
  }
	
	public PatientQuestions selectByIdPatientContentClass(Integer id) {
      return patientQuestionsMapper.selectByIdPatientContentClass(id);
  }
	public void updateByPrimary(PatientQuestions patientQuestions){
	  patientQuestionsMapper.updateByPrimary(patientQuestions);
	}
}

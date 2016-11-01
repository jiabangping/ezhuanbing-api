package com.ezhuanbing.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.PatientContentClassMapper;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientContentClass;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientQuestionResult;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientQuestions;

@Service
public class PatientContentClassService {
	
	@Autowired
	PatientContentClassMapper patientContentClassMapper;
	
	@Autowired
	PatientQuestionsService patientQuestionsService;
	
	@Autowired
	PatientQuestionResultService patientQuestionResultService;
	
	
/*	public PatientPaper selectByPrimaryKey(Integer paperId) {
		return patientPaperMapper.selectByPrimaryKey(paperId);
	}*/
	 
	/*public PatientPaper getPatientPaperSummary(Integer paperId) {
		PatientPaper patientPaper = selectByPrimaryKey(paperId);
		return patientPaper;
	}*/
	

	public PatientContentClass getPatientContentClassDetail(Integer paperId) {
	  PatientContentClass pcc = patientContentClassMapper.selectPatientContentClassByidDetail(paperId);
	  List<PatientQuestions> patientQuestions = patientQuestionsService.selectPatientQuestionsByPatientContentClass(paperId);
	  
	  for(int i=0;i<patientQuestions.size();i++){
	    PatientQuestions patientQuestion = patientQuestions.get(i);
	    List<PatientQuestionResult> patientQuesResults  = patientQuestionResultService.selectPatientQuestionsByPatientContentClass(patientQuestion.getId());	    
	    patientQuestion.setQuestionResults(patientQuesResults);
	  }
	  if(patientQuestions != null && patientQuestions.size() > 0){
	    pcc.setPatientQuestions(patientQuestions);
	  }
	  
      return pcc;
    }

	public PatientContentClass selectPatientContentClassWithQuestions(Integer classId) {
		 return patientContentClassMapper.selectPatientContentClassWithQuestions(classId);
	}
}

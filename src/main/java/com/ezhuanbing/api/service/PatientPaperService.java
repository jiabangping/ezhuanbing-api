package com.ezhuanbing.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.PatientContentClassMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientPaperMapper;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientContentClass;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientPaper;
import com.ezhuanbing.api.vo.AppPatientContentClass;

@Service
public class PatientPaperService {

	@Autowired
	PatientPaperMapper patientPaperMapper;
	
	@Autowired
    PatientContentClassMapper patientContentClassMapper;
	
	 
	public PatientPaper selectPaperWithClassesAndQuestions(Integer paperId) {
		PatientPaper patientPaper = patientPaperMapper.selectPaperWithClassesAndQuestions(paperId);
		return patientPaper;
	}
	
	public PatientPaper getPaperClassesTree(Integer paperId) {
		PatientPaper patientPaper = patientPaperMapper.getPaperClassesTree(paperId);
		return patientPaper;
	}

	public List<PatientPaper> getPatientPapersByPlanDetailId(Integer planDetailId) {
		return patientPaperMapper.selectPatientPapersByPlanDetailId(planDetailId);
	}

	public List<Integer> getPatientPaperIdsByPlanDetailId(Integer planDetailId) {
		return patientPaperMapper.selectPatientPaperIdsByPlanDetailId(planDetailId);
	}

	public List<PatientPaper> getSimplePatientPapers(Integer planDetailId) {
		return patientPaperMapper.selectSimplePatientPapersByPlanDetailId(planDetailId);
	}

	public PatientPaper getPatientPaperSummary(Integer paperId) {
      PatientPaper patientPaper = selectPaperWithClassesAndQuestions(paperId);
      return patientPaper;
	}
	
	public PatientContentClass getPatientContentClassDetail(Integer paperId) {
      return patientContentClassMapper.selectByPrimaryKey(paperId);
    }
	
    public List<AppPatientContentClass> selectPatientPaperContentClassForApp(int paperId){
      return patientPaperMapper.selectPatientPaperContentClassForApp(paperId);
    }
}

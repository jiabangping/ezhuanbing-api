package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientPaper;
import com.ezhuanbing.api.vo.AppPatientContentClass;
import com.ezhuanbing.noscan.BaseMapper;

public interface PatientPaperMapper extends BaseMapper<PatientPaper> {
	
    PatientPaper selectPaperWithClassesAndQuestions(Integer id);
    
    PatientPaper getPaperClassesTree(Integer id);

	List<PatientPaper> selectPatientPapersByPlanDetailId(Integer id);

	List<Integer> selectPatientPaperIdsByPlanDetailId(Integer id);

	List<PatientPaper> selectSimplePatientPapersByPlanDetailId(Integer id);
	
	List<AppPatientContentClass> selectPatientPaperContentClassForApp(@Param("paperId") int paperId);
}
package com.ezhuanbing.api.dao.mybatis.mapper;

import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientContentClass;
import com.ezhuanbing.noscan.BaseMapper;

public interface PatientContentClassMapper extends BaseMapper<PatientContentClass> {


    PatientContentClass selectPatientContentClassByidDetail(Integer paperId);

	PatientContentClass selectPatientContentClassWithQuestions(Integer id);
	
}
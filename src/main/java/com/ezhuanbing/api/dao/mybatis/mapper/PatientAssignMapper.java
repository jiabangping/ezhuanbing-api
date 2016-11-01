package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.ezhuanbing.api.dao.mybatis.model.PatientAssign;
import com.ezhuanbing.noscan.BaseMapper;

public interface PatientAssignMapper extends BaseMapper<PatientAssign> {

	int assignNewDoctor(Map<String, Object> paramsMap);

	List<Map<String, Object>> getAssignedPatientList(Map<String, Object> paramsMap);
	
}

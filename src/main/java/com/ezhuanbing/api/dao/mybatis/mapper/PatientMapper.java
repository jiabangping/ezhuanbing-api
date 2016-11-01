package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.ezhuanbing.api.dao.mybatis.model.patient.Patient;
import com.ezhuanbing.api.vo.PatientResp;
import com.ezhuanbing.noscan.BaseMapper;

public interface PatientMapper extends BaseMapper<Patient> { 
	List<PatientResp> selectByParamsMap(Map<String,Object> paramsMap);

	/**
	 * 获得病人的病史信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> getPatientExtInfoByLoginName(Map<String, Object> paramsMap);

	Map<String, Object> getPatientInfo(Map<String, Object> paramsMap);

	int getPatientNumsByGroupId(Map<String, Object> paramsMap);
}
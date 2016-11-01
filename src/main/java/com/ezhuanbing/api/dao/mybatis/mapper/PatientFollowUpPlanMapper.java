package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlan;
import com.ezhuanbing.noscan.BaseMapper;

public interface PatientFollowUpPlanMapper extends BaseMapper<PatientFollowUpPlan> {

	int insertAndGetId(PatientFollowUpPlan r);

	List<Map<String, Object>> selectFollowUpDetailList(Map<String, Object> paramsMap);
	
	List<Map<String, Object>> selectFollowUpPlanOtherList(PatientFollowUpPlan paramsMap);

	List<Date> selectStartAndEndTime(List<Integer> followTimes);

	List<Map<String, Date>> selectStartAndEndTime(Map<String, Object> paramsMap);

	int updateById(Map<String, Object> paramsMap);

	List<Map<String, Object>> selectFollowUpPlanOtherList_PM(Map<String, Object> paramsMap);
	
}
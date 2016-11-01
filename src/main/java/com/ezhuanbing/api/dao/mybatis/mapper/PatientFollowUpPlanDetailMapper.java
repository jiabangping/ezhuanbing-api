package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlanDetail;
import com.ezhuanbing.api.vo.AppPatientFollowUpPlanDetailResp;
import com.ezhuanbing.api.vo.PatientFollowUpPlanDetailResp;
import com.ezhuanbing.noscan.BaseMapper;

public interface PatientFollowUpPlanDetailMapper extends BaseMapper<PatientFollowUpPlanDetail> {
    
    List<PatientFollowUpPlanDetailResp> selectByParamsMap(Map<String,Object> paramsMap);
    List<PatientFollowUpPlanDetailResp> selectByPlanDetail(PatientFollowUpPlanDetail planDetail);
    
    PatientFollowUpPlanDetail selectPatientFollowUpPlanDetail(PatientFollowUpPlanDetail params);
    
    List<AppPatientFollowUpPlanDetailResp> selectPatientFollowUpPlanDetailForApp(@Param("patientId") int patientId);
    
    List<AppPatientFollowUpPlanDetailResp> selectPatientFollowUpPlanDetailHistoryForApp
      (@Param("patientId") int patientId,@Param("startColumn") int startColumn,
          @Param("pageSize") int pageSize,@Param("queryText") String queryText);

	Integer updateById(Map<String, Object> paramsMap);

	List<Map<String, Object>> getPlanDetailToPatient(Map<String, Object> paramsMap);

	List<Map<String, Object>> getPlanDetailToPatient2(Map<String, Object> paramsMap);

	Date selectNextTimeByPlanIdAndDetailId(@Param("planId") Integer planId, @Param("detailId")  Integer detailId);

	void updateDetailsSendTimeById(List<PatientFollowUpPlanDetail> detailList);
	
	void updateDetailOvertimeStatus(int overtimeDays);
	
	Map<String, Object> getPlanNameAndPlanOrder(int detailId);
}
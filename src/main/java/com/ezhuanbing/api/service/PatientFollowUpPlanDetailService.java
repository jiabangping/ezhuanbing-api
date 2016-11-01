package com.ezhuanbing.api.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.conf.ConstantConfig;
import com.ezhuanbing.api.conf.StringConfig.PushMessage;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientFollowUpPlanDetailMapper;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlan;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlanDetail;
import com.ezhuanbing.api.dao.mybatis.model.patient.Patient;
import com.ezhuanbing.api.model.Doctor;
import com.ezhuanbing.api.vo.AppPatientFollowUpPlanDetailResp;
import com.ezhuanbing.api.vo.PatientFollowUpPlanDetailResp;
import com.github.pagehelper.PageHelper;

@Service
public class PatientFollowUpPlanDetailService {

	@Autowired
	PatientFollowUpPlanDetailMapper patientFollowUpPlanDetailMapper;
	
	public List<PatientFollowUpPlanDetail> select(PatientFollowUpPlanDetail record) {
		return patientFollowUpPlanDetailMapper.select(record);
	}
	
	public PatientFollowUpPlanDetail selectPatientFollowUpPlanDetail(PatientFollowUpPlanDetail param) {
      return patientFollowUpPlanDetailMapper.selectPatientFollowUpPlanDetail(param);
  }

	public List<PatientFollowUpPlanDetailResp> selectByParamsMap(Map<String,Object> paramsMap) {
		return patientFollowUpPlanDetailMapper.selectByParamsMap(paramsMap);
	}

	public List<PatientFollowUpPlanDetailResp> getUnFinishFollowUpDetails(
			Integer doctorId, Integer pageIndex, Integer pageSize,
			String planName, String patientName, Integer planDetailPayStatus,
			String planDetailStatus,Integer overTimeStatu) {
		
		if (pageSize == null) {
			pageSize = ConstantConfig.PAGE_SIZE;
		}
		PageHelper.startPage(pageIndex, pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		if(null != doctorId) {
			Doctor doctor = new Doctor();
			doctor.setId(doctorId);
			paramsMap.put("doctor", doctor);
		}
		if(null != planName && !"".equals(planName.trim())) {
			PatientFollowUpPlan plan = new PatientFollowUpPlan();
			plan.setPlanName("%"+planName.trim()+"%");
			paramsMap.put("plan", plan);
		}
		if(null != patientName && !"".equals(patientName.trim())) {
			Patient patient = new Patient();
			patient.setName("%"+patientName.trim()+"%");
			paramsMap.put("patient", patient);
		}
		/*String status = "-1";
		PatientFollowUpPlanDetail planDetail = new PatientFollowUpPlanDetail();
		if(planDetailPayStatus != null && planDetailPayStatus != -1) {
		  if(planDetailPayStatus == 1) {
		    status = "3";
		  }else if(planDetailPayStatus == 0) {
		    status = "2";
		  }
		}
		if(status.equals("-1")) {
    		if(planDetailStatus != null ) {
    		  status = planDetailStatus.trim();
    		}
    		if(status.equals("-1")) {
    		  planDetail.setUnEqStatu("5");
    		}else {
    		  planDetail.setStatus(status);
    		}
		}else {
		  planDetail.setStatus(status);
		}*/
		PatientFollowUpPlanDetail planDetail = new PatientFollowUpPlanDetail();
		if(planDetailStatus != null && "-1".equals(planDetailStatus)) {
		  planDetail.setUnEqStatu("5");
		}else if(planDetailStatus != null && !"-1".equals(planDetailStatus)){
		  if("9".equals(planDetailStatus)) {//超时未提醒
		    planDetail.setOverTimeStatus(1);
		  }else {
		    planDetail.setStatus(planDetailStatus);
		  }
		}else {
		  planDetail.setUnEqStatu("5");
		}
		paramsMap.put("planDetail", planDetail);
		
		return selectByParamsMap(paramsMap);
	}

	
	public List<PatientFollowUpPlanDetailResp> getFinishFollowUpDetails(
			Integer doctorId, Integer pageIndex, Integer pageSize,
			String planName, String patientName,Integer planId) {
		
		if (pageSize == null) {
			pageSize = ConstantConfig.PAGE_SIZE;
		}
		PageHelper.startPage(pageIndex, pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		if(null != doctorId) {
			Doctor doctor = new Doctor();
			doctor.setId(doctorId);
			paramsMap.put("doctor", doctor);
		}
		PatientFollowUpPlan plan = new PatientFollowUpPlan();
		paramsMap.put("plan", plan);
		if(null != planName && !"".equals(planName.trim())) {
			plan.setPlanName("%"+planName.trim()+"%");
		}
		if(null != planId) {
			plan.setId(planId);
		}
		if(null != patientName && !"".equals(patientName.trim())) {
			Patient patient = new Patient();
			patient.setName("%"+patientName.trim()+"%");
			paramsMap.put("patient", patient);
		}
		
		PatientFollowUpPlanDetail planDetail = new PatientFollowUpPlanDetail();
		planDetail.setStatus("5");
		paramsMap.put("planDetail", planDetail);
		
		return selectByParamsMap(paramsMap);
	}
	
	public List<PatientFollowUpPlanDetailResp> getAllFollowUpDetailsByDoctorAndPlan(
			Integer doctorId, Integer pageIndex, Integer pageSize,
			String planName, String patientName,Integer planId) {
		
		if (pageSize == null) {
			pageSize = ConstantConfig.PAGE_SIZE;
		}
		PageHelper.startPage(pageIndex, pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		if(null != doctorId) {
			Doctor doctor = new Doctor();
			doctor.setId(doctorId);
			paramsMap.put("doctor", doctor);
		}
		PatientFollowUpPlan plan = new PatientFollowUpPlan();
		paramsMap.put("plan", plan);
		if(null != planName && !"".equals(planName.trim())) {
			plan.setPlanName("%"+planName.trim()+"%");
		}
		if(null != planId) {
			plan.setId(planId);
		}
		if(null != patientName && !"".equals(patientName.trim())) {
			Patient patient = new Patient();
			patient.setName("%"+patientName.trim()+"%");
			paramsMap.put("patient", patient);
		}
		
		PatientFollowUpPlanDetail planDetail = new PatientFollowUpPlanDetail();
		planDetail.setIsActive(1);
		paramsMap.put("planDetail", planDetail);
		
		//该查询需与其他方法共用了相同的SQL语句，但是该查询会多查询一张表，因此这里传入此参数用来关联该张表，其他方法不传该参数，可以少关联一张表，借此优化性能
		paramsMap.put("paperTitle", "paperTitle");
		
		return selectByParamsMap(paramsMap);
	}
	
	public List<PatientFollowUpPlanDetailResp> getPatientFollowUpDetails(
        Integer planId, Integer pageIndex, Integer pageSize) {
    
    if (pageSize == null) {
        pageSize = ConstantConfig.PAGE_SIZE;
    }
    PageHelper.startPage(pageIndex, pageSize);
    PatientFollowUpPlanDetail planDetail = new PatientFollowUpPlanDetail();

    if(null != planId && !"".equals(planId)) {
      planDetail.setPlanId(planId);
    }      
    //planDetail.setStatus("5");
    
    return patientFollowUpPlanDetailMapper.selectByPlanDetail(planDetail);
}
	/**
	 * 
	* @Title: getPatientFollowUpPlanDetailForApp 
	* @Description: 获取患者随访详情（app端用）
	* @param @param patientId
	* @param @return    设定文件 
	* @return List<AppPatientFollowUpPlanDetailResp>    返回类型 
	* @throws
	 */
	public List<AppPatientFollowUpPlanDetailResp> getPatientFollowUpPlanDetailForApp(int patientId){
	  return patientFollowUpPlanDetailMapper.selectPatientFollowUpPlanDetailForApp(patientId);
	}
	
	   /**
     * 
    * @Title: getPatientFollowUpPlanDetailForApp 
    * @Description: 获取患者随访详情（app端用）
    * @param @param patientId
    * @param @return    设定文件 
    * @return List<AppPatientFollowUpPlanDetailResp>    返回类型 
    * @throws
     */
    public List<AppPatientFollowUpPlanDetailResp> getPatientFollowUpPlanDetailHistoryForApp(
        int patientId,int currentPage,String queryText){
      
      currentPage = currentPage -1;
      
      int startColum = currentPage * ConstantConfig.PAGE_SIZE;
      return patientFollowUpPlanDetailMapper.selectPatientFollowUpPlanDetailHistoryForApp(
          patientId,startColum,ConstantConfig.PAGE_SIZE,queryText);
    }
    
    

    /**
     * 
    * @Title: followupPlanPay 
    * @Description: 更改随访表状态为已支付
    * @param @param planId
    * @param @return    设定文件 
    * @return boolean    返回类型 
    * @throws
     */
    public boolean followupPlanDetailPay(int planId){
      PatientFollowUpPlanDetail patientFollowUpPlanDetail = 
          PatientFollowUpPlanDetail.builder().id(planId).status("3")
          .pushMessage(PushMessage.SF_FOLLOWUP_FEE_SUCCESS_TEXT)
          .isPay(1)
          .build();
      int result = patientFollowUpPlanDetailMapper.updateByPrimaryKeySelective(patientFollowUpPlanDetail);
      return result > 0;
    }
    
    /**
     * 
    * @Title: followupPlanDetailCommit 
    * @Description: 更改随访表状态为患者已提交
    * @param @param planId
    * @param @return    设定文件 
    * @return boolean    返回类型 
    * @throws
     */
    public boolean followupPlanDetailCommit(int planId){
      PatientFollowUpPlanDetail patientFollowUpPlanDetail = 
          PatientFollowUpPlanDetail.builder().id(planId).status("4")
          .feedbackTime(new Date())
          .pushMessage(PushMessage.SF_FOLLOWUP_COMMIT_TEXT)
          .build();
      int result = patientFollowUpPlanDetailMapper.updateByPrimaryKeySelective(patientFollowUpPlanDetail);
      return result > 0;
    }
    
    /**
     * 
    * @Title: addFollowupPlanDetail 
    * @Description: 添加随访详情
    * @param @return    设定文件 
    * @return boolean    返回类型 
    * @throws
     */
    public PatientFollowUpPlanDetail addFollowupPlanDetail(PatientFollowUpPlanDetail patientFollowUpPlanDetail){
      int result = patientFollowUpPlanDetailMapper.insertUseGeneratedKeys(patientFollowUpPlanDetail);
      if(result > 0)
        return patientFollowUpPlanDetail;
      else
        return null;
    }
    
    public int updateBySelective(PatientFollowUpPlanDetail selective) {
    	return patientFollowUpPlanDetailMapper.updateByPrimaryKeySelective(selective);
    }
    
    /**
     * 查询可以推送给病人的随访计划。推送条件是：1. 距离今天小于等于3天；2. isActive是0；3. templateId不为空。<br>
     *另外，planOrder是1则直接推送，如果planOrder不是1，则需要使用getPlanDetailToPatient2判断前一条数据的status是否是5，如果是5才继续推送
     * @return
     */
    public List<Map<String, Object>> getPlanDetailToPatient(int predays) {
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("predays", predays);
    	return patientFollowUpPlanDetailMapper.getPlanDetailToPatient(paramsMap);
    }
    
    /**
     * 根据planId和planOrder查询可以推送给病人的随访计划。
     * @return
     */
    public Map<String, Object> getPlanDetailToPatient2(int planId, int planOrder) {
    	Map<String,Object> paramsMap = new HashMap<String,Object>();
    	paramsMap.put("planId", planId);
    	paramsMap.put("planOrder", planOrder);
    	List<Map<String, Object>> t = patientFollowUpPlanDetailMapper.getPlanDetailToPatient2(paramsMap);
    	if (t.size() > 0) {
    		return t.get(0);
    	}
    	return null;
    }

	public void updateDetailsSendTimeById(List<PatientFollowUpPlanDetail> detailList) {
		for (PatientFollowUpPlanDetail d : detailList) {
			patientFollowUpPlanDetailMapper.updateByPrimaryKeySelective(d);
		}
	}
	
    public Date getNextTimeByPlanIdAndDetailId(Integer planId,Integer detailId) {
      return patientFollowUpPlanDetailMapper.selectNextTimeByPlanIdAndDetailId(planId,detailId);
    }
    
    public PatientFollowUpPlanDetail getPatientFollowUpPlanDetailById(Integer detailId){
      return patientFollowUpPlanDetailMapper.selectByPrimaryKey(detailId);
    }

	public void updateDetailOvertimeStatus(int overtimeDays) {
		patientFollowUpPlanDetailMapper.updateDetailOvertimeStatus(overtimeDays);
	}
}

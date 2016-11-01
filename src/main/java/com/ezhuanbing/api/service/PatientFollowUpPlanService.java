package com.ezhuanbing.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ezhuanbing.api.conf.ConstantConfig;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientFollowUpPlanDetailMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientFollowUpPlanMapper;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlan;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlanDetail;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlanExample;
import com.ezhuanbing.api.exception.HttpStatus403Exception;
import com.github.pagehelper.PageHelper;

@Service
public class PatientFollowUpPlanService {

	@Autowired
	PatientFollowUpPlanMapper patientFollowUpPlanMapper;
	
	@Autowired
	PatientFollowUpPlanDetailMapper patientFollowUpPlanDetailMapper;

	public List<PatientFollowUpPlan> getListBySelective(
			PatientFollowUpPlan selective, Integer pageIndex, Integer pageSize)
			throws HttpStatus403Exception {
		if (pageSize == null) {
			pageSize = ConstantConfig.PAGE_SIZE;
		}
		PageHelper.startPage(pageIndex, pageSize);
		PatientFollowUpPlanExample example = new PatientFollowUpPlanExample();
		PatientFollowUpPlanExample.Criteria criteria = example.createCriteria();
		if (null != selective) {
			if (selective.getDoctorId() != null) {
				criteria.andDoctorIdEqualTo(
						selective.getDoctorId());
			}
			if(selective.getPatientId()!=null ){
				criteria.andPatientIdEqualTo(selective.getPatientId());
			}
		}
		return patientFollowUpPlanMapper.selectByExample(example);
	}
	
	public List<PatientFollowUpPlan> select(PatientFollowUpPlan selective) {
		//getListBySelective在获取数据时，如果参数中有isFinished，则会查询错误，因此这里单独写了一条语句
		return patientFollowUpPlanMapper.select(selective);
	}
	
	/**
	 * 
	* @Title: addPatientFollowUpPlan 
	* @Description: 添加首诊计划
	* @param @param patientId
	* @param @param doctorId
	* @param @return    设定文件 
	* @return PatientFollowUpPlan    返回类型 
	* @throws
	 */
	public PatientFollowUpPlan addPatientFollowUpPlan(int patientId, int doctorId){
	  PatientFollowUpPlan patientFollowUpPlan 
	    = PatientFollowUpPlan.builder()
	        .patientId(patientId).doctorId(doctorId).planName("首诊计划")
	        .planType(1).status("1").startDate(new Date()).endDate(new Date()).build();
	  int result = patientFollowUpPlanMapper.insertUseGeneratedKeys(patientFollowUpPlan);
	  if(result > 0){
	    return patientFollowUpPlan;
	  }else{
	    return null;
	  }
	}
	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
	public int insertFollowUpPlans(int doctorId, Integer patientId, String planName, Long startDate, 
			Date[] startTimeArr, Integer[] templateArr) {
		//插入一条随访计划的数据
		PatientFollowUpPlan r = PatientFollowUpPlan.builder().doctorId(doctorId).patientId(patientId).planName(planName).startDate(new Date(startDate)).status("1").build();
		patientFollowUpPlanMapper.insertAndGetId(r);
		int id = r.getId();
		
		//插入相应的具体随访计划
		List<PatientFollowUpPlanDetail> detailList = new ArrayList<PatientFollowUpPlanDetail>();
		for (int i = 0; i < startTimeArr.length; i++) {
			int isLastRecord = 0;
			if (i == startTimeArr.length -1) {
				isLastRecord = 1;
			}
			PatientFollowUpPlanDetail d = PatientFollowUpPlanDetail.builder().planId(id).templateId(templateArr[i]).isActive(0).status("0").sendTime(startTimeArr[i]).generateTime(startTimeArr[i]).planOrder(i+1).isLastRecord(isLastRecord).build();
			detailList.add(d);
		}
		patientFollowUpPlanDetailMapper.insertList(detailList);
		return id;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
	public int dropPlanAndDetail(Integer planId) {
		PatientFollowUpPlanDetail d = PatientFollowUpPlanDetail.builder().planId(planId).build();
		patientFollowUpPlanDetailMapper.delete(d);
		return patientFollowUpPlanMapper.delete(PatientFollowUpPlan.builder().id(planId).build());
	}

	public List<Map<String, Object>> selectFollowUpDetailList(Integer planId, Integer pageIndex,
			Integer pageSize) {
		if (pageSize == null) {
			pageSize = ConstantConfig.PAGE_SIZE;
		}
		PageHelper.startPage(pageIndex, pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		if(null != planId ) { 
			paramsMap.put("planId", planId);
		}
		return patientFollowUpPlanMapper.selectFollowUpDetailList(paramsMap);
	}
	
	
	public List<Map<String, Object>> selectFollowUpPlanOtherList(PatientFollowUpPlan paramsMap, Integer pageIndex,
        Integer pageSize) {
    if (pageSize == null) {
        pageSize = ConstantConfig.PAGE_SIZE;
    }
    PageHelper.startPage(pageIndex, pageSize);
    /*Map<String,Object> paramsMap = new HashMap<String,Object>();
    if(null != planId ) { 
        paramsMap.put("planId", planId);
    }*/
    return patientFollowUpPlanMapper.selectFollowUpPlanOtherList(paramsMap);
}

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
	public String updateFollowUpPlans(Integer planId, Integer detailId, Long startTime, Integer templateId, Integer times) {
		int c = patientFollowUpPlanDetailMapper.selectCount(PatientFollowUpPlanDetail.builder().planId(planId).build());
		List<Integer> followTimes = new ArrayList<Integer>();
		List<Map<String,Date>> dateList = new ArrayList<Map<String,Date>>();
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("planId", planId);
		
		followTimes.add(times+1);
		followTimes.add(times-1);
		paramsMap.put("followTimes", followTimes);
		dateList = patientFollowUpPlanMapper.selectStartAndEndTime(paramsMap);
		if (startTime >= 2145888000000L) {
			return "随访计划时间不能超过2037-12-31!";
		}
		if (times == 1) {
			//第一次随访的时间不能早于当前时间，也不能晚于下一次的时间
			Date d = new Date();
			@SuppressWarnings("deprecation")
			long l = new Date(d.getYear(), d.getMonth(), d.getDate()).getTime()-1;
			if (startTime < l) {
				return "计划日期不能早于当前日期！";
			}
			if (dateList.size() > 0 && (startTime >= dateList.get(0).get("generateTime").getTime())){
				return "本次计划时间不能晚于下一次计划时间！";
			}
			patientFollowUpPlanMapper.updateByPrimaryKeySelective(PatientFollowUpPlan.builder().id(planId).startDate(new Date(startTime)).build());
		} else if (times == c) {
			//最后一次随访计划时间不能早于前一次的时间
			if (startTime <= dateList.get(0).get("generateTime").getTime()){
				return "本次计划时间不能早于上一次计划时间！";
			}
		} else {
			if (startTime <= dateList.get(0).get("generateTime").getTime() ||
					startTime >= dateList.get(1).get("generateTime").getTime()){
				return "本次计划时间不能早于上一次计划时间，也不能晚于下一次计划时间！";
			}
		}
		patientFollowUpPlanDetailMapper.updateByPrimaryKeySelective(PatientFollowUpPlanDetail.builder().id(detailId).templateId(templateId).generateTime(new Date(startTime)).build());
		return "OK";
	}
	
	public int update(PatientFollowUpPlan plan) {
	  return patientFollowUpPlanMapper.updateByPrimaryKeySelective(plan);
	}

	public PatientFollowUpPlan getPatientFollowUpPlanById(int planId){
	  return patientFollowUpPlanMapper.selectByPrimaryKey(planId);
	}

	public List<Map<String, Object>> selectFollowUpPlanOtherList_PM(Map<String, Object> paramsMap, Integer pageIndex,
			Integer pageSize) {
	    if (pageSize == null) {
	        pageSize = ConstantConfig.PAGE_SIZE;
	    }
	    PageHelper.startPage(pageIndex, pageSize);
	    return patientFollowUpPlanMapper.selectFollowUpPlanOtherList_PM(paramsMap);
	}
	
}

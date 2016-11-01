package com.ezhuanbing.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlan;
import com.ezhuanbing.api.exception.HttpStatus403Exception;
import com.ezhuanbing.api.model.DoctorResponseToken;
import com.ezhuanbing.api.service.PatientFollowUpPlanService;
import com.ezhuanbing.api.tools.ValidationTools;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/api/v1/patientFollowUpPlan")
public class PatientFollowUpPlanController {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PatientFollowUpPlanService patientFollowUpPlanService;
	
	/**
	 * 获取未完成随访计划列表
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value = "/getUnFinishFollowUps", method = RequestMethod.GET)
	public PageInfo<PatientFollowUpPlan> getUnFinishFollowUps(
			@RequestParam(value = "doctorId", required = false) Integer doctorId,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) throws Exception  {
		
		PatientFollowUpPlan selective = new PatientFollowUpPlan();
		selective.setDoctorId(doctorId);
//		selective.setDoctorId(14739);
		try {
			return new PageInfo<PatientFollowUpPlan>(patientFollowUpPlanService.getListBySelective(selective, pageIndex, pageSize));
		} catch (HttpStatus403Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	
	/**
	 * 获取患者的随访记录
	 * @param doctorId doctorId为-1时，查看当前医生制定的随访信息，doctorId>0时，查询该id医生指定的随访信息（主治医生查看分配医生指定的随访计划时可用）
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
    @RequestMapping(value = "/getPatientFollowUpPlans/{patientId}", method = RequestMethod.GET)
    public PageInfo<Map<String, Object>> getPatientFollowUpPlans(
          @PathVariable("patientId") Integer patientId,
          @RequestParam(value = "doctorid", required = false) Integer doctorid,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletRequest request) throws Exception  {
	    DoctorResponseToken doctorResponseToken = (DoctorResponseToken) request.getAttribute("doctorResponseToken");
        PatientFollowUpPlan selective = new PatientFollowUpPlan();
        selective.setPatientId(patientId);
        if (doctorid != null) {
        	if (doctorid < 0) {
        	    doctorid = doctorResponseToken.getDoctorID();
        		selective.setDoctorId(doctorid);
//        		selective.setDoctorId(14739);
        	} else {
        		selective.setDoctorId(doctorid);
        	}
        	selective.setStatus("0");
        }else{
          selective.setStatus("1");
          doctorid = doctorResponseToken.getDoctorID();
          selective.setDoctorId(doctorid);
        }
        
        try {
          return new PageInfo<Map<String, Object>>(patientFollowUpPlanService.selectFollowUpPlanOtherList(selective, pageIndex, pageSize));
      } catch (Exception e) {
          log.error(e.getMessage(),e);
          throw e;
      }
        
    }
    
    /**
     * 患者管理中获取患者的随访记录
     * @param doctorId doctorId为-1时，查看当前医生制定的随访信息，doctorId>0时，查询该id医生指定的随访信息（主治医生查看分配医生指定的随访计划时可用）
     */
    @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
    @RequestMapping(value = "/getPatientFollowUpPlans_PM/{patientId}", method = RequestMethod.GET)
    public PageInfo<Map<String, Object>> getPatientFollowUpPlans_PM(
    		@PathVariable("patientId") Integer patientId,
    		@RequestParam(value = "doctorid", required = false) Integer doctorid,
    		@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
    		@RequestParam(value = "pageSize", required = false) Integer pageSize,
    		HttpServletRequest request) throws Exception  {
    	DoctorResponseToken doctorResponseToken = (DoctorResponseToken) request.getAttribute("doctorResponseToken");
    	
    	Map<String,Object> paramsMap = new HashMap<String,Object>();
    	paramsMap.put("patientId", patientId);
    	if (doctorid != null) {
    		
    		if (doctorid < 0) {
        	    doctorid = doctorResponseToken.getDoctorID();
    			paramsMap.put("doctorid", doctorid);
//    			paramsMap.put("doctorid", 14739);
    		} else {
    			paramsMap.put("doctorid", doctorid);
    		}
    		paramsMap.put("status", 0);
    	}else{
    		paramsMap.put("status", 1);
    		doctorid = doctorResponseToken.getDoctorID();
    		paramsMap.put("doctorid", doctorid);
    	}
    	try {
    		return new PageInfo<Map<String, Object>>(patientFollowUpPlanService.selectFollowUpPlanOtherList_PM(paramsMap, pageIndex, pageSize));
    	} catch (Exception e) {
    		log.error(e.getMessage(),e);
    		throw e;
    	}
    }
    
    /**
     * 制定随访计划及各个详细信息。
     * @return
     */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
    @RequestMapping(value = "/insertFollowUpPlans", method = RequestMethod.POST)
    public int insertFollowUpPlans(
    		@RequestParam("patientId") Integer patientId,
    		@RequestParam("planName") String planName,
    		@RequestParam("tdStartTimeStr") String tdStartTimeStr,
    		@RequestParam("tdPaperTemplateStr") String tdPaperTemplateStr,
    		HttpServletRequest request) {
		DoctorResponseToken doctorResponseToken = (DoctorResponseToken) request.getAttribute("doctorResponseToken");
		Integer doctorid = doctorResponseToken.getDoctorID();
//		Integer doctorid = 14739;
    	long startDate = 0L;
    	
    	String[] startTimeStrArr = tdStartTimeStr.split(",");
    	Date[] startTimeArr = new Date[startTimeStrArr.length];
    	int j = 0;
    	for (int i = startTimeStrArr.length-1; i > -1; i--) {
    		if (i == startTimeStrArr.length - 1) {
    			startDate = Long.valueOf(startTimeStrArr[i]);
    		}
    		startTimeArr[j] = new Date(Long.valueOf(startTimeStrArr[i]));
    		j++;
    	}
    	
    	String[] templateStrArr = tdPaperTemplateStr.split(",");
    	Integer[] templateArr = new Integer[templateStrArr.length];
    	j = 0;
    	for (int i = startTimeStrArr.length-1; i > -1; i--) {
    		templateArr[j] = Integer.valueOf(templateStrArr[i]);
    		j++;
    	}
		try {
			if (planName != null) {
				planName = URLDecoder.decode(planName, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        try {
        	return patientFollowUpPlanService.insertFollowUpPlans(doctorid, patientId, planName, startDate, startTimeArr, templateArr);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw e;
        }
    }
    
    @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
    @RequestMapping(value = "/updateFollowUpPlans", method = RequestMethod.GET)
    public String updateFollowUpPlans(
    		@RequestParam("planId") Integer planId,
    		@RequestParam("detailId") Integer detailId,
    		@RequestParam("startTime") Long startTime,
    		@RequestParam("templateId") Integer templateId,
    		@RequestParam("times") String timesStr,
    		HttpServletRequest request) {
    	String t1 = timesStr;
		try {
			if (timesStr != null) {
				timesStr = URLDecoder.decode(timesStr, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String t2 = timesStr;
    	timesStr = timesStr.substring(1, timesStr.length()-1);
    	String t3 = timesStr;
    	Integer times = 0;
    	if (ValidationTools.isInt(timesStr)) {
    		times = Integer.valueOf(timesStr);
    	} else {
    		log.error(t1+"______"+t2+"______"+t3);
    		return t1+"______"+t2+"______"+t3;
    	}
        try {
        	return patientFollowUpPlanService.updateFollowUpPlans(planId, detailId, startTime, templateId, times);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw e;
        }
    }  
    
    /**
     * 废弃随访计划及detail
     * @param planId
     * @return
     */
    @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
    @RequestMapping(value = "/dropPlanAndDetail", method = RequestMethod.GET)
    public int dropPlanAndDetail(@RequestParam("planId") String planId,
    		HttpServletRequest request) {
    	if (ValidationTools.isInt(planId)) {
            try {
            	return patientFollowUpPlanService.dropPlanAndDetail(Integer.valueOf(planId));
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                throw e;
            }
    	} else 
    		return 0;
    }
	
	/**
     * 获取患者的随访记录
     */
    @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
    @RequestMapping(value = "/selectFollowUpDetailList", method = RequestMethod.GET)
    public PageInfo<Map<String, Object>> selectFollowUpDetailList(
          @RequestParam(value = "planId", required = false) Integer planId,
          @RequestParam(value = "patientId", required = false) Integer patientId,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletRequest request) throws Exception  {
    	if (planId == null) {
    		//根据doctorid和patientid判断该医生和病人之间是否有未完成的随访计划，如果有，返回该随访计划，否则返回nodata供医生新建随访计划
    		DoctorResponseToken doctorResponseToken = (DoctorResponseToken) request.getAttribute("doctorResponseToken");
    		Integer doctorid = doctorResponseToken.getDoctorID();
//    		Integer doctorid = 14739;
    		
    		PatientFollowUpPlan p = PatientFollowUpPlan.builder().doctorId(doctorid).patientId(patientId).isFinished(0).build();
    		List<PatientFollowUpPlan> planList = patientFollowUpPlanService.select(p);
    		if (planList.isEmpty()) {
    			List<Map<String, Object>> t = new ArrayList<Map<String, Object>>();
    			Map<String, Object> m = new HashMap<String, Object>(); 
    			m.put("nodata", true);
    			t.add(m);
    			return new PageInfo<Map<String, Object>>(t);
    		} else {
    			return new PageInfo<Map<String, Object>>(patientFollowUpPlanService.selectFollowUpDetailList(planList.get(0).getId(), pageIndex, pageSize));
    		}
    	}
        try {
            return new PageInfo<Map<String, Object>>(patientFollowUpPlanService.selectFollowUpDetailList(planId, pageIndex, pageSize));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw e;
        }
    }
    /**
     * 获取doctorId&patientId 	PatientFollowUpPlans
     */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
    @RequestMapping(value = "/patientFollowUpPlans", method = RequestMethod.GET)
    public PageInfo<PatientFollowUpPlan> getPatientFollowUpPlans(
    		@RequestParam(value = "doctorId", required = false) Integer doctorId,
    		@RequestParam(value = "patientId", required = false) Integer patientId,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) throws Exception  {
        
        PatientFollowUpPlan selective = new PatientFollowUpPlan();
        selective.setPatientId(patientId);
        selective.setDoctorId(doctorId);
//        selective.setDoctorId(14739);
        try {
            return new PageInfo<PatientFollowUpPlan>(patientFollowUpPlanService.getListBySelective(selective, pageIndex, pageSize));
        } catch (HttpStatus403Exception e) {
            log.error(e.getMessage(),e);
            throw e;
        }
    }
}

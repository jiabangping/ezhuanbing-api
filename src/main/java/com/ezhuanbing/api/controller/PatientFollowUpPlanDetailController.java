package com.ezhuanbing.api.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.model.PatientCaseInfoResult;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlan;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlanDetail;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientPaper;
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.exception.HttpStatus500Exception;
import com.ezhuanbing.api.service.PatientCaseInfoResultService;
import com.ezhuanbing.api.service.PatientFollowUpPlanDetailService;
import com.ezhuanbing.api.service.PatientFollowUpPlanService;
import com.ezhuanbing.api.service.PatientPaperService;
import com.ezhuanbing.api.vo.AppPatientFollowUpPlanDetailResp;
import com.ezhuanbing.api.vo.PatientFollowUpPlanDetailResp;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/api/v1/followUpPlanDetail")
public class PatientFollowUpPlanDetailController {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PatientFollowUpPlanDetailService patientFollowUpPlanDetailService;
	
	@Autowired
	PatientFollowUpPlanService patientFollowUpPlanService;
	
	@Autowired
	PatientPaperService patientPaperService;
	/**
	 * 获取未完成随访计划详情列表
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API , apiType = APIType.DOCTOR)
	@RequestMapping(value = "/getUnFinishFollowUpDetails", method = RequestMethod.GET)
	public PageInfo<PatientFollowUpPlanDetailResp> getUnFinishFollowUpDetails(
			@RequestParam(value = "doctorId", required = false) Integer doctorId,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "planName", required = false) String planName,
			@RequestParam(value = "patientName", required = false) String patientName,
			@RequestParam(value = "planDetailPayStatus", required = false) Integer planDetailPayStatus,
			@RequestParam(value = "planDetailStatus", required = false) String planDetailStatus,
			@RequestParam(value = "overTimeStatus", required = false) Integer overTimeStatus
			) throws Exception  {
//		doctorId = 11767;
		try {
			
			return new PageInfo<PatientFollowUpPlanDetailResp>(patientFollowUpPlanDetailService.getUnFinishFollowUpDetails(doctorId,
					pageIndex,pageSize,planName,patientName,planDetailPayStatus,planDetailStatus,overTimeStatus));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new HttpStatus500Exception("fail", "500","查询失败", "");
		}
	}
	
	/**
     * 根据模板ID获取随访计划详情
     */
    @RequestMapping(value = "/getFollowUpPlanDetail/{patientPaperId}", method = RequestMethod.GET)
    public PatientFollowUpPlanDetail getFollowUpPlanDetail(@PathVariable("patientPaperId") Integer patientPaperId
            ) throws Exception  {
        
        try {
            PatientPaper patientPaper = patientPaperService.selectPaperWithClassesAndQuestions(patientPaperId);
            PatientFollowUpPlanDetail patientFollowUpPlanDetail = new PatientFollowUpPlanDetail();
            
            patientFollowUpPlanDetail.setId(patientPaper.getPlanDetailId());
            return patientFollowUpPlanDetailService.selectPatientFollowUpPlanDetail(patientFollowUpPlanDetail);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new HttpStatus500Exception("fail", "500","查询失败", "");
        }
    }
	
	
	
	/**
	 * 获取已完成随访计划详情列表
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API , apiType = APIType.DOCTOR)
	@RequestMapping(value = "/getAlreadyFinishFollowUpDetails", method = RequestMethod.GET)
	public PageInfo<PatientFollowUpPlanDetailResp> getAlreadyFinishFollowUpDetails(
			@RequestParam(value = "doctorId", required = false) Integer doctorId,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "planName", required = false) String planName,
			@RequestParam(value = "patientName", required = false) String patientName,
			@RequestParam(value = "nextDate", required = false) Long nextDate,
			@RequestParam(value = "planId", required = false) Integer planId
			) throws Exception  {
//		doctorId = 11767;
		try {
			return new PageInfo<PatientFollowUpPlanDetailResp>(patientFollowUpPlanDetailService.getFinishFollowUpDetails(doctorId,pageIndex,pageSize,planName,patientName,planId));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new HttpStatus500Exception("fail", "500","查询失败", "");
		}
	}
	
	/**
	 * 获取当前医生、病人同一随访计划内的详情列表（所有已激活的，不管是否完成的详情）
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API , apiType = APIType.DOCTOR)
	@RequestMapping(value = "/getAllFollowUpDetailsByDoctorAndPlan", method = RequestMethod.GET)
	public PageInfo<PatientFollowUpPlanDetailResp> getAllFollowUpDetailsByDoctorAndPlan(
			@RequestParam(value = "doctorId", required = false) Integer doctorId,
			@RequestParam(value = "did", required = false) Integer did,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "planName", required = false) String planName,
			@RequestParam(value = "patientName", required = false) String patientName,
			@RequestParam(value = "planId", required = false) Integer planId
			) throws Exception  {
//		doctorId = 11767;
		if (did > 0) {
			doctorId = did;
		}
		try {
			return new PageInfo<PatientFollowUpPlanDetailResp>(patientFollowUpPlanDetailService.getAllFollowUpDetailsByDoctorAndPlan(doctorId,pageIndex,pageSize,planName,patientName,planId));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new HttpStatus500Exception("fail", "500","查询失败", "");
		}
	}
	
	/**
     * 获取患者所有随访计划详情列表
     */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API , apiType = APIType.DOCTOR)
    @RequestMapping(value = "/getPatientFollowUpPlanDetails/{planId}", method = RequestMethod.GET)
    public PageInfo<PatientFollowUpPlanDetailResp> getPatientFollowUpPlanDetails(
            @PathVariable("planId") Integer planId,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
            ) throws Exception  {
        
        try {
            return new PageInfo<PatientFollowUpPlanDetailResp>(patientFollowUpPlanDetailService.getPatientFollowUpDetails(planId,pageIndex,pageSize));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new HttpStatus500Exception("fail", "500","查询失败", "");
        }
    }
	
	/**
	 * 根据planId获得不分页的planDetails
	 * @param planId
	 * @param request
	 * @return
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API , apiType = APIType.DOCTOR)
    @RequestMapping(value = "/getAllPatientFollowUpPlanDetailsByPlanId", method = RequestMethod.GET)
	public List<PatientFollowUpPlanDetail> getAllPatientFollowUpPlanDetailsByPlanId(
			@RequestParam("planId") Integer planId,
			HttpServletRequest request) {
        PatientFollowUpPlanDetail planDetail = PatientFollowUpPlanDetail.builder().planId(planId).build();
        List<PatientFollowUpPlanDetail> t = patientFollowUpPlanDetailService.select(planDetail);
		return t;
	}
    
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API , apiType = APIType.DOCTOR)
    @RequestMapping(value = "/updatePatientFollowUpPlanDetails", method = RequestMethod.POST)
    public String updatePatientFollowUpPlanDetails(
    		@RequestParam("planDetailIdStr") String planDetailIdStr,
    		@RequestParam("tdStartTimeStr") String tdStartTimeStr) {
		String[] startTimeStrArr = tdStartTimeStr.split(",");
		
		String[] planDetailIdStrArr = planDetailIdStr.split(",");
		
		List<PatientFollowUpPlanDetail> detailList = new ArrayList<PatientFollowUpPlanDetail>();
		
		for (int i = 0; i < startTimeStrArr.length; i++) {
			if (Long.valueOf(startTimeStrArr[i]) >= 2145888000000L) {
				return "随访计划时间不能超过2037-12-31!";
			}
			PatientFollowUpPlanDetail d = PatientFollowUpPlanDetail.builder().id(Integer.valueOf(planDetailIdStrArr[i])).generateTime(new Date(Long.valueOf(startTimeStrArr[i]))).build();
			detailList.add(d);
		}
    	try {
			patientFollowUpPlanDetailService.updateDetailsSendTimeById(detailList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return "批量更新随访计划时发生错误！";
		}
    	return "OK";
    }
    
    /**
     * @throws Exception 
     * 
    * @Title: getPatientFollowUpPlanDetailForApp 
    * @Description: 获取患者随访详情（app端用）
    * @param @param patientId
    * @param @return    设定文件 
    * @return List<AppPatientFollowUpPlanDetailResp>    返回类型 
    * @throws
     */
    @RequestMapping(value = "/app/all", method = RequestMethod.GET)
    @ApiControl(apiAccessType = APIAccessType.PRIVATE_API , apiType = APIType.PATIENT)
    public List<AppPatientFollowUpPlanDetailResp> getPatientFollowUpPlanDetailForApp(@RequestParam int patientId) throws Exception{
      
      if(patientId < 0)
        throw new HttpStatus400Exception("获取患者随访详情","","参数错误【patientId】应该大于0","");
      
//      List<AppPatientFollowUpPlanDetailResp> result = new ArrayList<AppPatientFollowUpPlanDetailResp>(); 
          
      List<AppPatientFollowUpPlanDetailResp> details = patientFollowUpPlanDetailService.getPatientFollowUpPlanDetailForApp(patientId);
//      if(details.size() <= 0){
//        details = patientFollowUpPlanDetailService.getPatientFollowUpPlanDetailHistoryForApp(patientId,0,"");
//        if(details.size() > 3){
//          result.add(details.get(0));
//          result.add(details.get(1));
//          result.add(details.get(2));
//        }else{
//          result.addAll(details);
//        }
//      }else{
//        result.addAll(details);
//      }
      
      return details;
    }
    
    /**
     * @throws Exception 
     * 
    * @Title: getPatientFollowUpPlanDetailForApp 
    * @Description: 获取患者历史随访详情（app端用）
    * @param @param patientId
    * @param @return    设定文件 
    * @return List<AppPatientFollowUpPlanDetailResp>    返回类型 
    * @throws
     */
    @RequestMapping(value = "/app/history", method = RequestMethod.GET)
    @ApiControl(apiAccessType = APIAccessType.PRIVATE_API , apiType = APIType.PATIENT)
    public List<AppPatientFollowUpPlanDetailResp> getPatientFollowUpPlanDetailHistoryForApp(
        @RequestParam int patientId,
        @RequestParam(defaultValue="0",required=false) int currentPage) throws Exception{

      if(patientId < 0)
        throw new HttpStatus400Exception("获取患者随访历史","","参数错误【patientId】应该大于0","");
      
      return patientFollowUpPlanDetailService.
          getPatientFollowUpPlanDetailHistoryForApp(patientId,currentPage,null);
    }
    
    /**
     * @throws Exception 
     * 
    * @Title: getPatientFollowUpPlanDetailForApp 
    * @Description: 查询患者历史随访详情（app端用）
    * @param @param patientId
    * @param @return    设定文件 
    * @return List<AppPatientFollowUpPlanDetailResp>    返回类型 
    * @throws
     */
    @RequestMapping(value = "/app/history/query", method = RequestMethod.GET)
    @ApiControl(apiAccessType = APIAccessType.PRIVATE_API , apiType = APIType.PATIENT)
    public List<AppPatientFollowUpPlanDetailResp> getPatientFollowUpPlanDetailHistoryForApp(
        @RequestParam int patientId,@RequestParam String queryText) throws Exception{

      if(patientId < 0)
        throw new HttpStatus400Exception("获取患者随访历史","","参数错误【patientId】应该大于0","");
      
      return patientFollowUpPlanDetailService
          .getPatientFollowUpPlanDetailHistoryForApp(patientId,0,queryText);
    }
    
   @Autowired
   PatientCaseInfoResultService patientCaseInfoResultService;
    
   /**
    * 归档 
    */
    @ApiControl(apiAccessType = APIAccessType.PRIVATE_API , apiType = APIType.DOCTOR)
    @ResponseBody
    @RequestMapping(value = "/archive/{planDetailId}", method = RequestMethod.PATCH)
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public JSONObject archiveFollowUpPlanDetail (
              @RequestParam(value = "planType", required = false, defaultValue = "1") Integer planType,
    		  @PathVariable("planDetailId") Integer planDetailId,
    		  @RequestParam(value = "isLastRecord", required = false, defaultValue = "-1") Integer isLastRecord,
    		  @RequestParam(value = "planId", required = true) Integer planId
    		 ) throws Exception{
          if(planDetailId < 0) throw new HttpStatus400Exception("归档","","参数错误【planDetailId】应该大于0","");
          if(planId < 0) throw new HttpStatus400Exception("归档","","参数错误【planId】应该大于0","");
          if(planType == 0) {//0:普通随访，1:首次随访   首次随访没有病例
            List<PatientCaseInfoResult> caseInfos = patientCaseInfoResultService.getPatientCaseInfoResultByPlanDetailId(planDetailId);
            if(caseInfos == null || caseInfos.size() == 0) {
              JSONObject result = new JSONObject();
              result.put("result", "noCaseInfoData");
              return result;
            }
            if(caseInfos.get(0).getOpinionresult() == null || "".equals(caseInfos.get(0).getOpinionresult().trim())) {
              JSONObject result = new JSONObject();
              result.put("result", "noCaseInfoData");
              return result;
            }
          }
	      PatientFollowUpPlanDetail detail = new PatientFollowUpPlanDetail();
	      detail.setId(planDetailId);
	      detail.setStatus("5");
	      detail.setAchiveTime(new Timestamp(System.currentTimeMillis()));
	      if(isLastRecord != 1) {//not last detail
	        Date generateTime = patientFollowUpPlanDetailService.getNextTimeByPlanIdAndDetailId(planId, planDetailId);
	        if(generateTime != null) {
	          detail.setNextTime(generateTime);
	        }
	      }
	    
	      patientFollowUpPlanDetailService.updateBySelective(detail);
	      if(isLastRecord == 1) {//last detail，finish plan
	        PatientFollowUpPlan plan = PatientFollowUpPlan.builder().id(planId).isFinished(1).endDate(new Timestamp(System.currentTimeMillis())).build();
	        patientFollowUpPlanService.update(plan);
	      }
	      return successResponse();
    }
    
    private JSONObject successResponse() {
      JSONObject result = new JSONObject();
      result.put("result", "success");
      return result;
    }
    
}

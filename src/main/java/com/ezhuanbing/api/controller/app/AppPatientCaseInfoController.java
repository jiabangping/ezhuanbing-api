package com.ezhuanbing.api.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.service.PatientCaseInfoService;
import com.ezhuanbing.api.vo.AppPatientCaseInfoResult;

@RestController
@RequestMapping("/api/v1/followupDetail")
public class AppPatientCaseInfoController {
  
  @Autowired
  PatientCaseInfoService patientCaseInfoService;

  /**
   * 
  * @Title: getPatientCaseInfoResult 
  * @Description: 获取随访建议
  * @param @param planFollowupDetailId
  * @param @param doctorId
  * @param @param patientId
  * @param @return    设定文件 
  * @return AppPatientCaseInfoResult    返回类型 
  * @throws
   */
  @RequestMapping(value="/{planFollowupDetailId}", method=RequestMethod.GET)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  private AppPatientCaseInfoResult getPatientCaseInfoResult(
      @PathVariable int planFollowupDetailId,
      @RequestParam int doctorId,
      @RequestParam int patientId){
    return patientCaseInfoService.getPatientCaseInfo(planFollowupDetailId, doctorId, patientId);
  }
}

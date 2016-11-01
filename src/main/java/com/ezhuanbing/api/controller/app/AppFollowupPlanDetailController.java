package com.ezhuanbing.api.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.service.PatientFollowUpPlanDetailService;

@RestController
@RequestMapping("/api/v1/followupPlanDetail")
public class AppFollowupPlanDetailController {

  @Autowired
  PatientFollowUpPlanDetailService patientFollowUpPlanDetailService;
  
  /**
   * 
  * @Title: paperCommit 
  * @Description: 患者随访表提交
  * @param @param followupPlanDetailId
  * @param @throws Exception    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping(value = "/{followupPlanDetailId}/commit", method = RequestMethod.PATCH)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public void paperCommit(@PathVariable int followupPlanDetailId) throws Exception{
    
    if(followupPlanDetailId < 0)
      throw new HttpStatus400Exception("问卷提交 ","","参数错误【followupPlanDetailId】应该大于0","");
    
    boolean result = patientFollowUpPlanDetailService.followupPlanDetailCommit(followupPlanDetailId);
    
    if(!result)
      throw new HttpStatus400Exception("问卷提交 ","","问卷提交失败，请稍后重试！","");
  }
}

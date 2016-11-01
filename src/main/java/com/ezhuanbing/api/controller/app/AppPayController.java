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
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.service.PatientFollowUpPlanDetailService;
import com.ezhuanbing.api.service.PushMsgService;

@RestController
@RequestMapping("/api/v1/pay")
public class AppPayController {
  
  @Autowired
  PatientFollowUpPlanDetailService patientFollowUpPlanDetailService;
  
  @Autowired
  PushMsgService pushMsgService;
  
  /**
   * @throws Exception 
   * 
  * @Title: planPay 
  * @Description: 更改随访表状态为已支付
  * @param @param planId    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping(value = "/{planDetailId}",method = RequestMethod.POST)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public void planPay(@PathVariable int planDetailId,@RequestParam int patientId) throws Exception{
    
    if(planDetailId < 0)
      throw new HttpStatus400Exception("患者支付","","参数错误【planDetailId】应该大于0","");
    
    if(patientId < 0)
      throw new HttpStatus400Exception("患者支付","","参数错误【patientId】应该大于0","");

    if(!patientFollowUpPlanDetailService.followupPlanDetailPay(planDetailId)){
      throw new HttpStatus400Exception("患者支付","","支付失败，请稍后重试！","");
    }
    
    // 发送推送
    pushMsgService.sendFellowupFeeSuccess(patientId, planDetailId);
  }
}

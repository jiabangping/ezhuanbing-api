package com.ezhuanbing.api.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.dao.mybatis.model.PatientEducationFile;
import com.ezhuanbing.api.dao.mybatis.model.patient.Patient;
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.service.PatientEducationFileService;
import com.ezhuanbing.api.service.PatientPaperGenerateService;
import com.ezhuanbing.api.service.PatientService;
import com.ezhuanbing.api.service.PushMsgService;

@RestController
@RequestMapping("/api/v1/push")
public class PushController {
  
  @Autowired
  PushMsgService pushMsgService;
  
  @Autowired
  PatientPaperGenerateService patientPaperGenerateService;
  
  @Autowired
  PatientService patientService;
  
  @Autowired
  PatientEducationFileService patientEducationFileService;
  
  /**
   * 
  * @Title: pushEducationFile 
  * @Description: 宣教资料提醒
  * @param @param patientId
  * @param @param educationFileId
  * @param @throws Exception    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping("/educationInfo")
  public void pushEducationFile(@RequestParam(name = "patientId", required = true) String patientId,
      @RequestParam(name = "educationFileId", required = true) int educationFileId) throws Exception{
    
    
    if(patientId == null || patientId.isEmpty())
      throw new HttpStatus400Exception("推送宣教资料","","patientId不正确。","");
    
    String[] patients = patientId.split(",");
    if(patients.length > 0){
      for(String patient : patients){
        PatientEducationFile pfile = PatientEducationFile.builder()
            .patientid(Integer.valueOf(patient))
            .doctoreducationid(educationFileId)
            .sendTime(new Date()).build();
        int result = patientEducationFileService.addPatientEducationFile(pfile);
        if(result > 0){
          pushMsgService.sendEducationFileRemind(Integer.valueOf(patient),educationFileId);
        }
      }
    }
  }
  
  /**
   * 
  * @Title: pushSendFirstFellowUpRemind 
  * @Description: 首诊详情（提醒） title为SF_FIRST_FOLLOWUP_REMIND(尊敬的患者，你好！由于你上传的图片不是很清晰，请重新上传一下，谢谢！)
  * @param @param patientId
  * @param @param message
  * @param @throws Exception    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping("/firstFellowUp/{planDetailId}/remind")
  @ResponseStatus(value=HttpStatus.NO_CONTENT)
  public void pushSendFirstFellowUpRemind(
      @RequestParam(name = "patientId", required = true) int patientId,
      @RequestParam(name = "planDetailId", required = true) int planDetailId,
      @RequestParam String message) throws Exception{
    
    if(patientId < 0)
      throw new HttpStatus400Exception("推送信息","","参数错误【patientId】应该大于0","");
    
      pushMsgService.sendFirstFellowUpRemind(patientId,planDetailId,message);
  }
  
  /**
   * 提醒患者完成首诊 
   * 推送title为SF_FIRST_FOLLOWUP SF_FIRST_FOLLOWUP_TEXT = "尊敬的患者，你好！请将你的首诊信息提交给我，便于我更好的了解您的病情。";
   */
  @RequestMapping("/firstFellowUp/remindToComplete")
  @ResponseStatus(value=HttpStatus.NO_CONTENT)
  public void pushSendFirstFellowUpremindToComplete(
      @RequestParam(name = "patientId", required = true) int patientId,
      @RequestParam(name = "planDetailId", required = true) int planDetailId,
      @RequestParam String message) throws Exception{
    
    if(patientId < 0)
      throw new HttpStatus400Exception("推送信息","","参数错误【patientId】应该大于0","");
    
      pushMsgService.sendFirstFellowUpRemindToComplete(patientId,planDetailId,message);
  }
  
  
//  /**
//   * 
//  * @Title: pushSendFirstFellowUp 
//  * @Description: 首诊
//  * @param @param patientId
//  * @param @param planDetailId
//  * @param @throws Exception    设定文件 
//  * @return void    返回类型 
//  * @throws
//   */
//  @RequestMapping("/firstFellowUp")
//  public void pushSendFirstFellowUp(int patientId,int planDetailId) throws Exception{
//    
//    if(patientId < 0)
//      throw new HttpStatus400Exception("推送信息","","参数错误【patientId】应该大于0","");
//    
//    pushMsgService.sendFirstFellowUp(patientId, planDetailId);
//  }
  
  /**
   * 
  * @Title: pushSendFirstFellowUpWarnAgain 
  * @Description: 首诊（再次提醒）
  * @param @param patientId
  * @param @param planDetailId
  * @param @throws Exception    设定文件 
  * @return void    返回类型 
  * @throws
   */
  //再次提醒
  @RequestMapping("/warnAgain")
  @ResponseStatus(value=HttpStatus.NO_CONTENT)
  public void pushSendFirstFellowUpWarnAgain(@RequestParam(name = "patientId", required = true) int patientId,
		  @RequestParam(name = "planDetailId", required = true) int planDetailId) throws Exception {
    if(patientId < 0) {
      throw new HttpStatus400Exception("再次提醒信息","","参数错误【patientId】应该大于0","");
    }
    if(planDetailId < 0) {
      throw new HttpStatus400Exception("再次提醒信息","","参数错误【planDetailId】应该大于0","");
    }
    
     pushMsgService.sendFellowupRemind(patientId,planDetailId);
  }
  
  /**
   * 
  * @Title: sendFellowupAccessory 
  * @Description: 发送附加随访表
  * @param @param patientId
  * @param @param planDetailId
  * @param @throws Exception    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping("/accessoryinfo")
  public void sendFellowupAccessory(@RequestParam String patientLoginName,@RequestParam int doctorId,
       @RequestParam int planDetailId,@RequestParam int templateId) throws Exception{
    
    if(patientLoginName == null || patientLoginName.isEmpty()) {
      throw new HttpStatus400Exception("发送附加随访表","","参数错误【patientLoginName】应该大于0","");
    }
    if(planDetailId < 0) {
      throw new HttpStatus400Exception("发送附加随访表","","参数错误【planDetailId】应该大于0","");
    }
    if(templateId < 0) {
      throw new HttpStatus400Exception("发送附加随访表","","参数错误【templateId】应该大于0","");
    }
   
    Patient p = patientService.getPatientByLoginName(patientLoginName);
    boolean isOk = patientPaperGenerateService.GeneratePaper(p.getId(), doctorId, planDetailId, templateId);
    if(!isOk)
      throw new HttpStatus400Exception("随访表生效","","随访表生成失败，请稍后重试！","");
    
    pushMsgService.sendFellowupAccessory(patientLoginName, planDetailId,templateId);
  }
  
  /**
   * @throws Exception 
   * 
  * @Title: sendPlanDetailIsActive 
  * @Description: 随访表生效
  * @param @param patientId
  * @param @param planDetailId    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping("/{planDetailId}/active")
  public void sendPlanDetailIsActive(@RequestParam int patientId,@RequestParam int doctorId,
      @PathVariable int planDetailId,@RequestParam int templateId) throws Exception{
    
    if(patientId < 0) {
      throw new HttpStatus400Exception("随访表生效","","参数错误【patientId】应该大于0","");
    }
    if(planDetailId < 0) {
      throw new HttpStatus400Exception("随访表生效","","参数错误【planDetailId】应该大于0","");
    }
    
    boolean isOk = patientPaperGenerateService.GeneratePaper(patientId, doctorId, planDetailId, templateId);
    if(!isOk)
      throw new HttpStatus400Exception("随访表生效","","随访表生成失败，请稍后重试！","");
    
    pushMsgService.sendFellowupFee(patientId, planDetailId);
    
  }
  
  /**
   * @throws Exception 
   * 
  * @Title: planFinish 
  * @Description: 随访结束通知
  * @param     设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping("/{planDetailId}/finish")
  public void planFinish(@RequestParam int patientId,@PathVariable int planDetailId) throws Exception{
    
    if(patientId < 0) {
      throw new HttpStatus400Exception("随访表结束","","参数错误【patientId】应该大于0","");
    }
    if(planDetailId < 0) {
      throw new HttpStatus400Exception("随访表结束","","参数错误【planDetailId】应该大于0","");
    }
    
    pushMsgService.sendFellowupFinish(patientId, planDetailId);
  }
}

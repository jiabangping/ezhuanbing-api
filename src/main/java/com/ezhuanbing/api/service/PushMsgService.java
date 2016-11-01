package com.ezhuanbing.api.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.conf.EnumClass.PushMessageType;
import com.ezhuanbing.api.conf.StringConfig.PushMessage;
import com.ezhuanbing.api.dao.mybatis.mapper.DoctorEducationFileMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientCaseInfoResultMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientFollowUpPlanDetailMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientMapper;
import com.ezhuanbing.api.dao.mybatis.model.DoctorEducationFile;
import com.ezhuanbing.api.dao.mybatis.model.PatientCaseInfoResult;
import com.ezhuanbing.api.dao.mybatis.model.PatientCaseInfoResultCriteria;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlanDetail;
import com.ezhuanbing.api.dao.mybatis.model.patient.Patient;
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.model.PushModel;
import com.ezhuanbing.api.tools.PushMsgUtil;

@Service
public class PushMsgService {

  @Autowired
  PushMsgUtil pushMsgUtil;
  
  @Autowired
  PatientMapper patientMapper;
  
  @Autowired
  DoctorEducationFileMapper doctorEducationFileMapper;
  
  @Autowired
  PatientFollowUpPlanDetailMapper patientFollowUpPlanDetailMapper;
  
  @Autowired
  PatientService patientService;
  
  @Autowired
  PatientCaseInfoResultMapper patientCaseInfoResultMapper;
  
  /**
   * 
  * @Title: sendFirstFellowUp 
  * @Description: 首次随访
  * @param @return    设定文件 
  * @return JSONObject    返回类型 
  * @throws
   */
  @Async
  public JSONObject sendFirstFellowUp(int patientId,String msg){
    //return SendPushMsg(patientId,PushMessageType.SF_FIRST_FOLLOWUP,planDetialId);
    return SendPushMsg(patientId,PushMessageType.SF_FIRST_FOLLOWUP,msg);
  }
  
  /**
   * @throws Exception 
   * 
  * @Title: sendFirstFellowUpRemind 
  * @Description: 首次随访（医生发送信息）
  * @param @return    设定文件 
  * @return JSONObject    返回类型 
  * @throws
   */
  @Async
  public JSONObject sendFirstFellowUpRemind(int patientId,int planDetailId,String message) throws Exception{
    
//    String msgType =  PushMessageType.SF_FIRST_FOLLOWUP_REMIND;
//    Map<String,Object> custom = new HashMap<String,Object>();
//    custom.put("MT", msgType);
//    Patient p = patientMapper.selectByPrimaryKey(patientId);
//    PushModel pushModel = new PushModel();
//    pushModel.setMobileImei(p.getPhoneIMEI());
//    pushModel.setPhoneType(p.getPhoneType());
//    pushModel.setTitle(getPushTitle(msgType));
//    pushModel.setContent(message);
//    pushModel.setCustom(custom);
    
    // 信息保存
    updatePlanDetailInfo(message,planDetailId);
    
    // return pushMsgUtil.PushSingleDeviceNotification(pushModel);
    // 发送信息
    return SendPushMsg(patientId, PushMessageType.SF_FIRST_FOLLOWUP_REMIND, message);
    
  }
  
  /**
   * @throws Exception 
   * 
  * @Title: sendFirstFellowUp
  * @Description: 提醒患者完成首诊
  * @param @return    设定文件 
  * @return JSONObject    返回类型 
  * @throws
   */
  @Async
  public JSONObject sendFirstFellowUpRemindToComplete(int patientId,int planDetailId,String message) throws Exception{
    
//    String msgType =  PushMessageType.SF_FIRST_FOLLOWUP;
//    Map<String,Object> custom = new HashMap<String,Object>();
//    custom.put("MT", msgType);
//    Patient p = patientMapper.selectByPrimaryKey(patientId);
//    PushModel pushModel = new PushModel();
//    pushModel.setMobileImei(p.getPhoneIMEI());
//    pushModel.setPhoneType(p.getPhoneType());
//    pushModel.setTitle(getPushTitle(msgType));
//    pushModel.setContent(message);
//    pushModel.setCustom(custom);
    
    // 信息保存
    updatePlanDetailInfo(message,planDetailId);
    
    // 发送信息
//    return pushMsgUtil.PushSingleDeviceNotification(pushModel);
    return SendPushMsg(patientId, PushMessageType.SF_FIRST_FOLLOWUP, message);
    
  }
  
  
  /**
   * @throws Exception 
   * 
  * @Title: sendFellowupFee 
  * @Description: 发送随访表(自动任务发送、付费提醒)
  * @param @return    设定文件 
  * @return JSONObject    返回类型 
  * @throws
   */
  @Async
  public JSONObject sendFellowupFee(int patientId,int planDetailId) throws Exception{
    // 数据状态更新为1(待付款)
    String message = getPushMessage(PushMessageType.SF_FOLLOWUP_FEE,planDetailId);
    //updatePlanDetailInfo("1",getPushMessage(PushMessageType.SF_FOLLOWUP_FEE,planDetailId),planDetailId);
    updatePlanDetailInfo("1",message,planDetailId);
    //return SendPushMsg(patientId,PushMessageType.SF_FOLLOWUP_FEE,planDetailId);
    return SendPushMsg(patientId, PushMessageType.SF_FOLLOWUP_FEE, message);
  }
  
  /**
   * 
  * @Title: sendFellowupRemind 
  * @Description: T发送随访表(发送随访表提醒)
  * @param @return    设定文件 
  * @return JSONObject    返回类型 
  * @throws
   */
  @Async
  public JSONObject sendFellowupRemind(int patientId,int planDetailId){
    //return SendPushMsg(patientId,PushMessageType.SF_FOLLOWUP_REMIND,planDetailId);
    String message = getPushMessage(PushMessageType.SF_FOLLOWUP_REMIND,planDetailId);
    return SendPushMsg(patientId, PushMessageType.SF_FOLLOWUP_REMIND, message);
  }
  
  /**
   * @throws Exception 
   * 
  * @Title: sendFellowupFeeSuccess 
  * @Description: 付费成功提醒
  * @param @return    设定文件 
  * @return JSONObject    返回类型 
  * @throws
   */
  @Async
  public JSONObject sendFellowupFeeSuccess(int patientId,int planDetailId) throws Exception{
    String message = getPushMessage(PushMessageType.SF_FOLLOWUP_FEE_SUCCESS,planDetailId);
    // 数据状态更新为3(已付款付款)
    //updatePlanDetailInfo("3",getPushMessage(PushMessageType.SF_FOLLOWUP_FEE_SUCCESS,planDetailId),planDetailId);
    updatePlanDetailInfo("3",message,planDetailId);
    //return SendPushMsg(patientId,PushMessageType.SF_FOLLOWUP_FEE_SUCCESS,planDetailId);
    return SendPushMsg(patientId,PushMessageType.SF_FOLLOWUP_FEE_SUCCESS,message);
  }
  
  /**
   * @throws Exception 
   * 
  * @Title: sendFellowupAccessory 
  * @Description:  发送附加随访表
  * @param @return    设定文件 
  * @return JSONObject    返回类型 
  * @throws
   */
  @Async
  public JSONObject sendFellowupAccessory(String patientloginName,int planDetailId,int templateId) throws Exception{
    
    Patient p = patientService.getPatientByLoginName(patientloginName);
    String message = getPushMessage(PushMessageType.SF_FOLLOWUP_ACCESSORY,planDetailId);
//    // 发送推送
//    //return SendPushMsg(patientId,PushMessageType.SF_FOLLOWUP_ACCESSORY,planId);
//    String msgType =  PushMessageType.SF_FOLLOWUP_ACCESSORY;
//    Map<String,Object> custom = new HashMap<String,Object>();
//    custom.put("MT", msgType);
//    PushModel pushModel = new PushModel();
//    pushModel.setMobileImei(p.getPhoneIMEI());
//    pushModel.setPhoneType(p.getPhoneType());
//    pushModel.setTitle(getPushTitle(msgType));
//    pushModel.setContent(getPushMessage(msgType,planDetailId));
//    pushModel.setCustom(custom);
    
    // 信息保存
    //updatePlanDetailInfo(getPushMessage(msgType,planDetailId),planDetailId);
    updatePlanDetailInfo(message,planDetailId);
    // 发送信息
    //return pushMsgUtil.PushSingleDeviceNotification(pushModel);
    return SendPushMsg(p.getId(),PushMessageType.SF_FOLLOWUP_ACCESSORY,message);
  }
  
  /**
   * @throws Exception 
   * 
  * @Title: sendFellowupFinish 
  * @Description: 随访结束提醒
  * @param @return    设定文件 
  * @return JSONObject    返回类型 
  * @throws
   */
  @Async
  public JSONObject sendFellowupFinish(int patientId,int planDetailId) throws Exception{
    // 数据状态更新为5(已归档)
    PatientCaseInfoResult patientCaseInfoResult = null;
    PatientCaseInfoResultCriteria criteria = new PatientCaseInfoResultCriteria();
    criteria.createCriteria().andPlandetailidEqualTo(planDetailId);
    List<PatientCaseInfoResult> result = patientCaseInfoResultMapper.selectByExample(criteria);
    if(result.size() > 0){
      patientCaseInfoResult = result.get(0);
    }
    
    String message="";
    if(patientCaseInfoResult != null){
      message = patientCaseInfoResult.getOpinionresult();
    }
    
    updatePlanDetailInfo("5",message,planDetailId);
    //return SendPushMsg(patientId,message,planDetailId);
    return SendPushMsg(patientId,PushMessageType.SF_FOLLOWUP_FINISH,message);
    
  }
  
  /**
   * 
  * @Title: sendEducationFileRemind 
  * @Description: 宣教资料发送提醒
  * @param @return    设定文件 
  * @return JSONObject    返回类型 
  * @throws
   */
  @Async
  public JSONObject sendEducationFileRemind(int patientId,int educationFileId){
    //return SendPushMsg(patientId,PushMessageType.SF_EDUCATION_FILE_REMIND,educationFileId);
    DoctorEducationFile ddf = doctorEducationFileMapper.selectByPrimaryKey(educationFileId);
    String title = "";
    if(ddf != null){
      title = ddf.getTitle();
    }
    String message;
    try {
      message = String.format(PushMessage.SF_EDUCATION_FILE_REMIND_TEXT, URLDecoder.decode(title, "utf-8"));
      return SendPushMsg(patientId, PushMessageType.SF_EDUCATION_FILE_REMIND, message);
    } catch (UnsupportedEncodingException e) {
      //e.printStackTrace();
      return null;
    }
  }
  
//  private JSONObject SendPushMsg(int patientId, String msgType, int id){
//    // 更改数据库状态
//    Map<String,Object> custom = new HashMap<String,Object>();
//    custom.put("MT", msgType);
//    Patient p = patientMapper.selectByPrimaryKey(patientId);
//    PushModel pushModel = new PushModel();
//    pushModel.setMobileImei(p.getPhoneIMEI());
//    pushModel.setPhoneType(p.getPhoneType());
//    pushModel.setTitle(getPushTitle(msgType));
//    pushModel.setContent(getPushMessage(msgType,id));
//    pushModel.setCustom(custom);
//    // 发送信息
//    return pushMsgUtil.PushSingleDeviceNotification(pushModel);
//  }
  
  /**
   * 
  * @Title: SendPushMsg 
  * @Description: 直接推送信息
  * @param @param patientId
  * @param @param msgType
  * @param @param msg
  * @param @return    设定文件 
  * @return JSONObject    返回类型 
  * @throws
   */
  private JSONObject SendPushMsg(int patientId, String msgType, String msg){
    // 更改数据库状态
    Map<String,Object> custom = new HashMap<String,Object>();
    custom.put("MT", msgType);
    Patient p = patientMapper.selectByPrimaryKey(patientId);
    if(p != null && p.getPhoneType() != null && p.getPhoneIMEI() != null){
      PushModel pushModel = new PushModel();
      pushModel.setMobileImei(p.getPhoneIMEI());
      pushModel.setPhoneType(p.getPhoneType());
      pushModel.setTitle(getPushTitle(msgType));
      pushModel.setContent(msg);
      pushModel.setCustom(custom);
      // 发送信息
      return pushMsgUtil.PushSingleDeviceNotification(pushModel); 
    }else{
      return null;
    }
  }
  
  /**
   * 
  * @Title: getPushMessage 
  * @Description: 获取推送消息
  * @param @param pushType
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  private String getPushMessage(String pushType,int id){
    String message = "";
    if(PushMessageType.SF_FIRST_FOLLOWUP.equals(pushType)){
      message = PushMessage.SF_FIRST_FOLLOWUP_TEXT;
    }else if(PushMessageType.SF_FIRST_FOLLOWUP_REMIND.equals(pushType)){
      message = PushMessage.SF_FIRST_FOLLOWUP_REMIND_TEXT;
    }else if(PushMessageType.SF_FOLLOWUP_FEE.equals(pushType)){
      message = PushMessage.SF_FOLLOWUP_FEE_TEXT;
    }else if(PushMessageType.SF_FOLLOWUP_REMIND.equals(pushType)){//超时未反馈提醒 id为planDetailId
      message = PushMessage.SF_FOLLOWUP_REMIND_TEXT;
      PatientFollowUpPlanDetail detail = patientFollowUpPlanDetailMapper.selectByPrimaryKey(id);
      message = String.format(PushMessage.SF_FOLLOWUP_REMIND_TEXT,detail.getPlanMark());
    }else if(PushMessageType.SF_FOLLOWUP_FEE_SUCCESS.equals(pushType)){
      message = PushMessage.SF_FOLLOWUP_FEE_SUCCESS_TEXT;
    }else if(PushMessageType.SF_FOLLOWUP_ACCESSORY.equals(pushType)){
      message = PushMessage.SF_FOLLOWUP_ACCESSORY_TEXT;
    }else if(PushMessageType.SF_FOLLOWUP_FINISH.equals(pushType)){
      message = PushMessage.SF_FOLLOWUP_FINISH_TEXT;
    }else if(PushMessageType.SF_EDUCATION_FILE_REMIND.equals(pushType)){
      DoctorEducationFile ddf = doctorEducationFileMapper.selectByPrimaryKey(id);
      String title = "";
      if(ddf != null){
        title = ddf.getTitle();
      }
      message = String.format(PushMessage.SF_EDUCATION_FILE_REMIND_TEXT,title);
    }
    return message;
  }
  
  private String getPushTitle(String pushType){
    String title = "";
    if(PushMessageType.SF_FIRST_FOLLOWUP.equals(pushType)){
      title = PushMessage.SF_FIRST_FOLLOWUP_TEXT;
    }else if(PushMessageType.SF_FIRST_FOLLOWUP_REMIND.equals(pushType)){
      title = PushMessage.SF_FIRST_FOLLOWUP_REMIND_TEXT;
    }else if(PushMessageType.SF_FOLLOWUP_FEE.equals(pushType)){
      title = PushMessage.SF_FOLLOWUP_FEE_TEXT;
    }else if(PushMessageType.SF_FOLLOWUP_REMIND.equals(pushType)){
      title = PushMessage.SF_FOLLOWUP_REMIND_TEXT;
    }else if(PushMessageType.SF_FOLLOWUP_FEE_SUCCESS.equals(pushType)){
      title = PushMessage.SF_FOLLOWUP_FEE_SUCCESS_TEXT;
    }else if(PushMessageType.SF_FOLLOWUP_ACCESSORY.equals(pushType)){
      title = PushMessage.SF_FOLLOWUP_ACCESSORY_TEXT;
    }else if(PushMessageType.SF_FOLLOWUP_FINISH.equals(pushType)){
      title = PushMessage.SF_FOLLOWUP_FINISH_TEXT;
    }else if(PushMessageType.SF_EDUCATION_FILE_REMIND.equals(pushType)){
      title = PushMessage.SF_EDUCATION_FILE_REMIND_TEXT;
    }
    return title;
  }
  
  /**
   * 
  * @Title: updatePlanDetailInfo 
  * @Description: 更新状态和消息
  * @param @param status
  * @param @param msg
  * @param @param detailId
  * @param @throws Exception    设定文件 
  * @return void    返回类型 
  * @throws
   */
  private void updatePlanDetailInfo(String status,String msg,int detailId) throws Exception{
    PatientFollowUpPlanDetail detail = PatientFollowUpPlanDetail.builder()
        .id(detailId).status(status).pushMessage(msg).build();
    int result = patientFollowUpPlanDetailMapper.updateByPrimaryKeySelective(detail);
    if(result <= 0 )
      throw new HttpStatus400Exception("推送数据更新", "", "推送数据更新，请稍后重试！", "");
  }
  
  /**
   * 
  * @Title: updatePlanDetailInfo 
  * @Description: 更新消息
  * @param @param msg
  * @param @param detailId
  * @param @throws Exception    设定文件 
  * @return void    返回类型 
  * @throws
   */
  private void updatePlanDetailInfo(String msg,int detailId) throws Exception{
    PatientFollowUpPlanDetail detail = PatientFollowUpPlanDetail.builder()
        .id(detailId).pushMessage(msg).build();
    int result = patientFollowUpPlanDetailMapper.updateByPrimaryKeySelective(detail);
    if(result <= 0 )
      throw new HttpStatus400Exception("推送数据更新", "", "推送数据更新，请稍后重试！", "");
  }
  
  //================================================================================================
  /**
   * 
  * @Title: pushImInfoToApp 
  * @Description: 给App端推送信息
  * @param @param model
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  public String pushImInfoToApp(PushModel model){
    JSONObject ret = pushMsgUtil.PushSingleDeviceNotification(model);
    return ret.toString();
  }
}

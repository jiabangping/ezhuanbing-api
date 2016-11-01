package com.ezhuanbing.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezhuanbing.api.conf.EnumClass.NeteaseImMessageType;
import com.ezhuanbing.api.conf.EnumClass.PushMessageType;
import com.ezhuanbing.api.dao.mybatis.mapper.ChatRecordMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.OnlineDoctorMapper;
import com.ezhuanbing.api.dao.mybatis.model.ChatRecord;
import com.ezhuanbing.api.dao.mybatis.model.ChatRecordCriteria;
import com.ezhuanbing.api.dao.mybatis.model.OnlineDoctor;
import com.ezhuanbing.api.dao.mybatis.model.OnlineDoctorCriteria;
import com.ezhuanbing.api.dao.mybatis.model.mq.ImMessageQueueModel;
import com.ezhuanbing.api.dao.mybatis.model.patient.Patient;
import com.ezhuanbing.api.im.netease.JsonAnalyseTool;
import com.ezhuanbing.api.im.netease.model.CallBackModel;
import com.ezhuanbing.api.model.PushModel;
import com.ezhuanbing.api.service.MessageQueueService;
import com.ezhuanbing.api.service.PatientService;
import com.ezhuanbing.api.service.PushMsgService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

@Controller
public class NetEaseIMController {
  
  @Autowired
  MessageQueueService messageQueueService;
  @Autowired
  ChatRecordMapper chatRecordMapper;
  @Autowired
  PatientService patientService;
  @Autowired
  PushMsgService pushMsgService;
  @Autowired
  OnlineDoctorMapper onlineDoctorMapper;

  /**
   * 
  * @Title: transferMsgToOtherGroup 
  * @Description: 消息转发
  * @param @param request    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping(value = "/api/v1/im/netease/group/message/transfer", method = RequestMethod.POST)
  public @ResponseBody void transferMsgToOtherGroup(HttpServletRequest request) {
    try {
      // 消息转发
      transferMessage(request);
    } catch (Exception e) {
    }
  }
  
  /**
   * 
  * @Title: getChatRecords 
  * @Description: 获取历史信息
  * @param @param planDetailId
  * @param @param pageIndex
  * @param @param pageSize
  * @param @return    设定文件 
  * @return PageInfo<ChatRecord>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/api/v1/im/netease/chatrecord/{planDetailId}", method = RequestMethod.GET)
  public @ResponseBody PageInfo<ChatRecord> getChatRecords(
      @PathVariable int planDetailId,
      @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
    
    ChatRecordCriteria criteria = new ChatRecordCriteria();
    criteria.setOrderByClause("id desc");
    criteria.createCriteria().andPlandetailidEqualTo(planDetailId);
    PageHelper.startPage(pageIndex, pageSize);
    List<ChatRecord> records = chatRecordMapper.selectByExample(criteria);
    return new PageInfo<ChatRecord>(records);    
  }
  
  /**
   * 
  * @Title: getPcNoReadChatRecords 
  * @Description: 获取医生未读信息
  * @param @param doctorId
  * @param @return    设定文件 
  * @return List<ChatRecord>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/api/v1/im/netease/chatrecord/{doctorId}/unread", method = RequestMethod.GET)
  public @ResponseBody List<ChatRecord> getPcNoReadChatRecords(@PathVariable int doctorId){
    ChatRecordCriteria criteria = new ChatRecordCriteria();
    criteria.setOrderByClause("id desc");
    criteria.createCriteria().andChattargetEqualTo(doctorId).andPconlineEqualTo("0");
    return chatRecordMapper.selectByExample(criteria);
  }
  
  @RequestMapping(value = "/api/v1/im/netease/chatrecord/{doctorId}/read", method = RequestMethod.POST)
  public @ResponseBody void setChatDataRead(@PathVariable int doctorId){
    chatRecordMapper.setChatDataRead(doctorId);
  }

  /**
   * 
   * @Title: transferMessage 
   * @Description: 转发消息-异步处理
   * @param @param request 
   * @param @throws Exception
   * 设定文件 @return void 返回类型 @throws
   */
  @Async
  private void transferMessage(HttpServletRequest request) throws Exception {
    // 获取信息
    int size = request.getContentLength();
    InputStream is = null;
    try {
      is = request.getInputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    byte[] reqBodyBytes = readBytes(is, size);
    String res = new String(reqBodyBytes, "utf-8");
    System.out.println("ezhuanbing res===========>"+res);
    CallBackModel callBackModel =
        (CallBackModel) JSONObject.toBean(JSONObject.fromObject(res), CallBackModel.class);
    
    String msg = "";
    String type = "";
    if (NeteaseImMessageType.STR_PICTURE.equals(callBackModel.getMsgType())) {
      msg = callBackModel.getAttach();
      type = NeteaseImMessageType.PICTURE;
    } else if (NeteaseImMessageType.STR_TEXT.equals(callBackModel.getMsgType())) {
      msg = callBackModel.getBody();
      type = NeteaseImMessageType.TEXT;
    }
    System.out.println("ezhuanbing msg----->" + msg);
    // 将内容保存至消息队列
    saveChatContent(callBackModel, msg, type);
  }

  @Async
  private void saveChatContent(CallBackModel callBackModel, String msg, String type) {
    
    ImMessageQueueModel chatContent = new ImMessageQueueModel();
    chatContent.setFromAccountId(callBackModel.getFromAccount());
    chatContent.setToGroupId(callBackModel.getTo());
    chatContent.setMessage(msg);
    chatContent.setType(type);
    chatContent.setDate(callBackModel.getMsgTimestamp());
    
    // res 解析
    String ext = callBackModel.getExt();
    if(ext != null && !ext.isEmpty()){
      String planDetailId = JsonAnalyseTool.getInfoByJsonCode("planDetailId", ext);
      if(planDetailId != null && !planDetailId.isEmpty()){
        chatContent.setDetailId(Integer.valueOf(planDetailId));
      }
      
      String sender = JsonAnalyseTool.getInfoByJsonCode("sender", ext);
      if(sender != null && !sender.isEmpty()){
        int senderType = Integer.valueOf(sender);
        if(senderType == 1){
          pushImInfo(callBackModel, msg, type, planDetailId);
        }else{
          // 消息来自app，判断pc端是否在线
          System.out.println("doctor id is---->"+Integer.valueOf(callBackModel.getTo()));
          OnlineDoctorCriteria criteria = new OnlineDoctorCriteria();
          criteria.createCriteria().andDoctoridEqualTo(Integer.valueOf(callBackModel.getTo()));
          List<OnlineDoctor> result = onlineDoctorMapper.selectByExample(criteria);
          if(result.size() > 0){
            // 在线，不做处理
            chatContent.setPcOnline(1);
          }else{
            // 不在线
            chatContent.setPcOnline(0);
          }
        }
        chatContent.setSender(senderType);
      }
    }
    
    chatContent.setOrigin(1);
    messageQueueService.sendMsg(chatContent);
  }

  @Async
  private void pushImInfo(CallBackModel callBackModel, String msg, String type, String planDetailId){
    // 信息来自pc需要给app通知
    Patient p = patientService.getPatientByLoginName(callBackModel.getTo());
    Map<String,Object> custom = new HashMap<String,Object>();
    // 类型
    custom.put("MT", PushMessageType.SF_IM_INFO);
    // DetailId
    custom.put("DT", planDetailId);
    custom.put("FROM", callBackModel.getFromAccount());
    custom.put("TO", callBackModel.getTo());
    custom.put("doctorName", callBackModel.getFromNick());
    PushModel pushModel = new PushModel();
    pushModel.setMobileImei(p.getPhoneIMEI());
    pushModel.setPhoneType(p.getPhoneType());
    pushModel.setTitle("聊天信息提醒");
    if("0".equals(type)){
      pushModel.setContent("医生发送了文字信息："+ msg);
    }else{
      pushModel.setContent("医生发送了图片信息，请查看。");
    }
    pushModel.setCustom(custom);
    pushMsgService.pushImInfoToApp(pushModel);
  }
  
  @RequestMapping("/im/push/test")
  public void testPush(@RequestParam String patientLoginName){
    Patient p = patientService.getPatientByLoginName(patientLoginName);
    Map<String,Object> custom = new HashMap<String,Object>();
    // 类型
    custom.put("MT", PushMessageType.SF_IM_INFO);
    // DetailId
    custom.put("DT", 170);
    custom.put("FROM", "14652");
    custom.put("TO", patientLoginName);
    PushModel pushModel = new PushModel();
    pushModel.setMobileImei(p.getPhoneIMEI());
    pushModel.setPhoneType(p.getPhoneType());
    pushModel.setTitle("聊天信息提醒");
    pushModel.setContent("医生发送了文字信息："+ "这是测试");
    pushModel.setCustom(custom);
    pushMsgService.pushImInfoToApp(pushModel);
  }
  
  private final byte[] readBytes(InputStream is, int contentLen) throws Exception {
    if (contentLen > 0) {
      int readLen = 0;
      int readLengthThisTime = 0;
      byte[] message = new byte[contentLen];
      while (readLen != contentLen) {
        readLengthThisTime = is.read(message, readLen, contentLen - readLen);
        if (readLengthThisTime == -1) {
          break;
        }
        readLen += readLengthThisTime;
      }
      return message;
    }
    return new byte[] {};
  }
}

package com.ezhuanbing.api.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.conf.EnumClass;
import com.ezhuanbing.api.conf.SmsConfig;
import com.ezhuanbing.api.conf.WXPushConfig;
import com.ezhuanbing.api.conf.wxpush.SendTemplate;
import com.ezhuanbing.api.dao.mybatis.mapper.SmsSendMapper;
import com.ezhuanbing.api.exception.ServiceException;
import com.ezhuanbing.api.model.SmsModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class SmsService {
  private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
  @Autowired
  SmsSendMapper smsSend;
  @Autowired
  SmsConfig smsConfig;
  

/*  @Autowired
  PatientService patientService;*/
  @Autowired
  SendTemplate sendTemplate;
  
  /**
   * mobsms短信发送
   * 
   * @param phoneNum
   * @param content
   * @return
   * @throws UnsupportedEncodingException
   */
  public boolean smsSend(String phoneNum, String content, int type, int inUser) {

    try {
      SmsModel sms = new SmsModel();
      // 短信表增加数据
      sms.setMobile(phoneNum);
      sms.setNoteContent(content);
      sms.setType(type);
      sms.setSendTime(new Date());
      sms.setRetryCount(0);
      sms.setStatus(0);
      sms.setInDate(new Date());
      sms.setInUser(inUser);
      sms.setEditDate(new Date());
      sms.setEditUser(inUser);
      smsSend.insert(sms);
      String inputString = URLEncoder.encode(content, "GB2312");
      String url = smsConfig.sendUrl(phoneNum, inputString);
      CloseableHttpClient httpClient = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost(url);
      HttpResponse result = httpClient.execute(httpPost);
      // 短信发送成功
      if (result.getStatusLine().getStatusCode() == 200) {
        sms.setStatus(1);
        sms.setRetryCount(1);
        smsSend.updateByPrimary(sms);
        return true;
      }

      return false;
    } catch (Exception e) {
      return false;
    }
  }

  
  /**
   * 普通短信发送- 无限制
   * 
   * @param patientId 患者ID
   * @param request  
   * @param pushMg 短信模板
   * @return  
   * @throws ServiceException
   *//*
     * @Async public void sendModSMSASync(String phoneNum, String content, int type, int inUser) {
     * if (ConstantConfig.pushSms == 0) { logger.info("普通短信发送，发送关闭，不发送"); return; } if
     * (!PushCheck.isSmsTime()) { logger.info("普通短信发送，非短信发送时间，不发送"); return; } try { SmsModel sms =
     * new SmsModel(); sms.setType(type); sms.setMobile(phoneNum); sms.setNoteContent(content);
     * sms.setInUser(inUser); // sms.setStatus(0); // 入库 int smsId = smsDao.insertNormalSMS(sms); //
     * 发送短信 boolean sendOK = false; int retryCount = 0; while (retryCount <
     * ConstantConfig.SMS_RETRY_COUNT && !sendOK) { retryCount++; try { String inputString =
     * URLEncoder.encode(content, "GB2312"); String url = smsConfig.sendUrl(phoneNum, inputString);
     * HttpClient httpclient = new DefaultHttpClient(); HttpPost httpPost = new HttpPost(url);
     * HttpResponse result = httpclient.execute(httpPost); // 短信发送成功 if
     * (result.getStatusLine().getStatusCode() == 200) { sendOK = true; logger.info("普通短信发送，发送成功");
     * } } catch (Exception e) { logger.info("普通短信发送，异常：[" + e.getMessage() + "]"); } }
     * logger.info("普通短信发送次数：" + retryCount);
     * 
     * // 发送成功 if (sendOK && smsId != -1) { // 短信信息入库 if (smsDao.smsSended(smsId, retryCount)) {
     * logger.info("普通短信发送后更新成功"); } else { logger.info("普通短信发送后更新失败"); } } } catch (Exception e) {
     * logger.error("普通短信发送 ，异常：[" + e.getMessage() + "]"); } }
     */
  
  public void pushMessageOnlySMS(String patientPhoneNum,String sendMsg,int sendType){

    if (patientPhoneNum != null && !"".equals(patientPhoneNum)) {
      smsSend(patientPhoneNum, sendMsg, sendType, 0);
    } else {
      logger.error("手机号为空");
    }
  }
  
 /* public void pushMessage(int patientId, HttpServletRequest request, String pushMg,
      String splitString) throws ServiceException {
    // 获得 患者 信息
    Patient patient = patientService.selectByPrimaryKey(patientId);
    if (patient == null) {

      if (patient == null) {
        logger.error("未查询到患者 信息");
      }
    }
    System.out.println(patient.getPhoneNum());
    // 短信(关注用户发微信通知)
    String openId = patient.getOpenId();
    if (openId == null || "".equals(openId)) {
      // 消息 拼接
      if (splitString == null || "".equals(splitString)) {
        splitString = patient.getPhoneNum().trim().substring(5);
      }
      if (patient.getPhoneNum() != null && !"".equals(patient.getPhoneNum())) {
        smsSend(patient.getPhoneNum(), String.format(pushMg, patient.getPatientName()),
            EnumClass.SMSType.TRANSFERCONFIRM.getType(), 0);
      } else {
        logger.error("手机号为空");
      }

      
       * if(doctor.getOverSea() == 1){//国外的用户发送邮箱 if (doctor.getEmail() == null ||
       * doctor.getEmail().equals("")) { throw new RequestParamException("邮箱为空"); } String
       * regexEmail=
       * "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
       * if(doctor.getEmail() != null && !"".equals(doctor.getEmail())){
       * if(doctor.getEmail().matches(regexEmail)){ //消息 拼接
       * if(splitString==null||"".equals(splitString)){ splitString = "123456"; } try {
       * EmailUtil.sendResetPasswdEmail(String.format(StringConfig. EMAIL_APPROVED_TITLE),
       * doctor.getEmail(),String.format(pushMg,doctor.getDoctorName(), splitString)); SmsModel sms
       * = new SmsModel(); sms.setType(EnumClass.SMSType.APPROVED.getType());
       * sms.setMobile(doctor.getEmail());//保存的是邮箱 sms.setNoteContent(pushMg);
       * smsDao.insertNormalSMS(sms); } catch (Exception e) { e.printStackTrace(); } } } }
       
    } else {// 有openId的用户发送微信信息

      // 微信通知
      List<String> dates = new ArrayList<String>();
      String passWord = "";

      SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
      String str = format.format(new Date());

      // 消息 拼接
      if (splitString != null && !"".equals(splitString)) {
        dates.add(0, "您好，很抱歉的通知您，注册信息未通过审核。\n");
        dates.add(1, patient.getPatientName());
        dates.add(2, "未通过");
        dates.add(3, str + "\n");
        dates.add(4, "您的注册信息未通过系统审核，原因如下：【" + splitString
            + "】。详情请咨询客服【400-888-3918】,我们会有专人帮助您完成注册，以便您可尽快使用我们的App");
      } else {
        dates.add(0, "您好,注册信息已通过审核。\n");
        dates.add(1, patient.getPatientName());
        dates.add(2, "已通过");
        dates.add(3, str + "\n");
        dates.add(4, "您的注册信息已经通过了审核，初始密码是【" + passWord + "】，如需修改请登录e会诊App，感谢您的关注");
      }

      // 模板：受理重置密码通知
      String templateId = WXPushConfig.templateId_Approved;
      sendTemplate.sendWx(openId, templateId, dates, request);
    }

  }
*/
}

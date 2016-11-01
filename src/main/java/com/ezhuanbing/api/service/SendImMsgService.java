package com.ezhuanbing.api.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.im.netease.NeteaseMsgHandle;
import com.ezhuanbing.api.model.SendMsgModel;
import com.ezhuanbing.api.model.UserInfo;

import java.util.List;

@Service
public class SendImMsgService {

  @Autowired
  NeteaseMsgHandle neteaseMsgHandle;

  @Autowired
  UserService userService;

/*  @Autowired
  TransferInfoDetailService transferInfoDetailService;

  *//**
   * @param transferId 转诊Id
   * @param type       消息类型 0:提出申请 1:申请确认 2:申请拒绝 3:撤销申请
   * @return void 返回类型
   * @Title: sendMsg
   * @Description: 发送Im消息
   *//*
  public void sendMsg(Integer transferId, int type) {

    // 获取转诊详情
    TransferInfoDetail transferInfoDetail = transferInfoDetailService.queryTransferInfoOrderById(transferId);
    if (transferInfoDetail == null)
      return;

    SendMsgModel msg = null;
    switch (type) {
      case 0:
        msg = new SendMsgModel("[转诊申请通知]",
            String.format("您有一条新的转诊申请%s，请及时处理，更多信息可查看记录详情。", transferInfoDetail.getTransferNo()),0);
        break;
      case 1:
        msg = new SendMsgModel("[转诊同意通知]", String.format("您的转诊申请%s已被%s同意接收，更多信息请查看记录详情。",
            transferInfoDetail.getTransferNo(), transferInfoDetail.getTrHospitalName()), 0);
        break;
      case 2:
        msg = new SendMsgModel("[转诊拒绝通知]", String.format("您的转诊申请%s已被%s拒绝，更多信息请查看记录详情。",
            transferInfoDetail.getTransferNo(), transferInfoDetail.getTrHospitalName()), 0);
        break;
      case 3:
        msg = new SendMsgModel("[转诊撤销通知]", String.format("转诊订单%s已被%s撤销，更多信息请查看记录详情。",
            transferInfoDetail.getTransferNo(), transferInfoDetail.getFromHospitalName()), 0);
        break;
      default:
        break;
    }

    if (msg != null) {
      String from = "000000";
      String sendMsg = "{\"kind\":\"" + msg.getType() + "\",\"title\":\"" + msg.getTitle()
              + "\",\"message\":\"" + msg.getContent() + "\",\"delaytime\":\"" + msg.getDelaytime() + "\"}";

      // 获取对方医院所有在线用户
      List<UserInfo> users = null;
      if (type == 0 || type == 3) {
        // 给发起转诊的医院发信息
        users = userService.selectUserAndAdmin(Integer.valueOf(transferInfoDetail.getTrHospital()));
      } else {
        // 给接受转诊的医院发信息
        users = userService.selectUserAndAdmin(Integer.valueOf(transferInfoDetail.getFromHospital()));
      }

      for (UserInfo user : users) {
        if ("1".equals(user.getOnline())) {
          neteaseMsgHandle.sendAttachMsgToPerson(from, getSendId(user), sendMsg);
        }
      }
    }
  }*/

  /**
   * 
  * @Title: sendJwtTimeout 
  * @Description: token过期
  * @param 
  * @return void
  * @throws
   */
  public void sendJwtTimeout(String loginName) {
    if (StringUtils.isNotEmpty(loginName)) {
      String from = "000000";
      String sendMsg = "{\"kind\":\"102\"}";

      UserInfo userInfo = userService.selectUserOrAdminByLoginName(loginName);

      if (userInfo != null && "1".equals(userInfo.getOnline())) {
        neteaseMsgHandle.sendAttachMsgToPerson(from, getSendId(userInfo), sendMsg);
      }
    }
  }
  
  private String getSendId(UserInfo userInfo){
    String sendId = "";
    if(userInfo.getDoctorId() != null && userInfo.getDoctorId()> 0){
      sendId = userInfo.getDoctorId().toString();
    }else{
      sendId = userInfo.getLogin();
    }
    return sendId;
  }
  
  public void sendTestInfo(String loginName) {
    if (StringUtils.isNotEmpty(loginName)) {
      String from = "000000";
      String sendMsg = "{\"kind\":\"999\"}";

      UserInfo userInfo = userService.selectUserOrAdminByLoginName(loginName);

      if (userInfo != null && "1".equals(userInfo.getOnline())) {
        String value = neteaseMsgHandle.sendAttachMsgToPerson(from, getSendId(userInfo), sendMsg);
        System.out.println(value);
      }
    }
  }
}

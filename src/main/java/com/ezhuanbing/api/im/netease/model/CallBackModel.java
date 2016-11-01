package com.ezhuanbing.api.im.netease.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallBackModel {
  private String eventType;
  private String convType;
  private String to;
  private String fromAccount;
  private String fromClientType;
  private String fromDeviceId;
  private String fromNick;
  private String msgTimestamp;
  private String msgType;
  private String body;
  private String attach;
  private String msgidClient;
  private String msgidServer;
  private String resendFlag;
  private String ext;
  // private String customSafeFlag;
  // private String customApnsTex;
  // private String tMembers;

}

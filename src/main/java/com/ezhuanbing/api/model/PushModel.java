package com.ezhuanbing.api.model;

import java.util.HashMap;
import java.util.Map;

import com.ezhuanbing.api.tools.PropertyFileOperation;
import com.tencent.xinge.ClickAction;

public class PushModel {
  
  private String mobileImei;
  private char phoneType;
  private String title;
  private String content;
  private Map<String, Object> custom = new HashMap<String, Object>();
  private ClickAction action = new ClickAction();
  private int iosBadge = 1;

  public PushModel() {
  }

  public PushModel(String mobileImei, char phoneType, String title, String content) {
      this.mobileImei = mobileImei;
      this.phoneType = phoneType;
      this.title = title;
      this.content = content;
  }

  public String getMobileImei() {
      return this.mobileImei;
  }

  public void setMobileImei(String mobileImei) {
      this.mobileImei = mobileImei;
  }

  public char getPhoneType() {
      return this.phoneType;
  }

  public void setPhoneType(char phoneType) {
      this.phoneType = phoneType;
  }

  public String getTitle() {
      return this.title;
  }

  public void setTitle(String title) {
      this.title = title;
  }

  public String getContent() {
      return this.content;
  }

  public void setContent(String content) {
      this.content = content;
  }

  public Map<String, Object> getCustom() {
      return custom;
  }

  public void setCustom(Map<String, Object> custom) {
    this.custom = custom;
    String messageType = custom.get("MT").toString();
    ClickAction action = new ClickAction();
    action.setActionType(ClickAction.TYPE_ACTIVITY);
    action.setActivity(PropertyFileOperation.getPropertyByName("pushtype","push.type." + messageType));
    this.action = action;
  }

  public ClickAction getAction() {
      return action;
  }

  public void setAction(ClickAction action) {
      this.action = action;
  }

  public int getIosBadge() {
      return iosBadge;
  }

  public void setIosBadge(int iosBadge) {
      this.iosBadge = iosBadge;
  }
}

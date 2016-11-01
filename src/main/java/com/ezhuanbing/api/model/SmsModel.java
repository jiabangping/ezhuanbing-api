package com.ezhuanbing.api.model;

import java.util.Date;
public class SmsModel {

  private Integer id;
  private int type;
  private String mobile;
  private String noteContent;
  private String verifyCode;
  private Date expTime;
  private Date sendTime;
  private Integer status;
  private Integer retryCount;
  private int inUser;
  private Date inDate;
  private int editUser;
  private Date editDate;

  public SmsModel() {}

  public SmsModel(int type, int inUser, Date inDate, int editUser, Date editDate) {
    this.type = type;
    this.inUser = inUser;
    this.inDate = inDate;
    this.editUser = editUser;
    this.editDate = editDate;
  }

  public SmsModel(int type, String mobile, String noteContent, String verifyCode, Date expTime,
      Date sendTime, Integer status, Integer retryCount, int inUser, Date inDate, int editUser,
      Date editDate) {
    this.type = type;
    this.mobile = mobile;
    this.noteContent = noteContent;
    this.verifyCode = verifyCode;
    this.expTime = expTime;
    this.sendTime = sendTime;
    this.status = status;
    this.retryCount = retryCount;
    this.inUser = inUser;
    this.inDate = inDate;
    this.editUser = editUser;
    this.editDate = editDate;
  }


  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }


  public String getMobile() {
    return this.mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }


  public String getNoteContent() {
    return this.noteContent;
  }

  public void setNoteContent(String noteContent) {
    this.noteContent = noteContent;
  }


  public String getVerifyCode() {
    return this.verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }


  public Date getExpTime() {
    return this.expTime;
  }

  public void setExpTime(Date expTime) {
    this.expTime = expTime;
  }


  public Date getSendTime() {
    return this.sendTime;
  }

  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }


  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }


  public Integer getRetryCount() {
    return this.retryCount;
  }

  public void setRetryCount(Integer retryCount) {
    this.retryCount = retryCount;
  }


  public int getInUser() {
    return this.inUser;
  }

  public void setInUser(int inUser) {
    this.inUser = inUser;
  }


  public Date getInDate() {
    return this.inDate;
  }

  public void setInDate(Date inDate) {
    this.inDate = inDate;
  }


  public int getEditUser() {
    return this.editUser;
  }

  public void setEditUser(int editUser) {
    this.editUser = editUser;
  }


  public Date getEditDate() {
    return this.editDate;
  }

  public void setEditDate(Date editDate) {
    this.editDate = editDate;
  }
}
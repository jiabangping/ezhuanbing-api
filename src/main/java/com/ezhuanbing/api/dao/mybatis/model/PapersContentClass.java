package com.ezhuanbing.api.dao.mybatis.model;

import java.util.Date;
import java.util.List;

public class PapersContentClass {

  private Integer id;
  private String userName;
  private String paperTile;
  private String paperSummary;
  private Date paperStartDate;
  private String paperBg;
  private Integer paperType;
  private Integer paperDept;
  private Integer paperLevel;
  private Integer paperStatus;
  private List<ContentClassQuestions> paperContentClass;
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getPaperTile() {
    return paperTile;
  }
  public void setPaperTile(String paperTile) {
    this.paperTile = paperTile;
  }
  public String getPaperSummary() {
    return paperSummary;
  }
  public void setPaperSummary(String paperSummary) {
    this.paperSummary = paperSummary;
  }
  public Date getPaperStartDate() {
    return paperStartDate;
  }
  public void setPaperStartDate(Date paperStartDate) {
    this.paperStartDate = paperStartDate;
  }
  public String getPaperBg() {
    return paperBg;
  }
  public void setPaperBg(String paperBg) {
    this.paperBg = paperBg;
  }
  public Integer getPaperType() {
    return paperType;
  }
  public void setPaperType(Integer paperType) {
    this.paperType = paperType;
  }
  public Integer getPaperDept() {
    return paperDept;
  }
  public void setPaperDept(Integer paperDept) {
    this.paperDept = paperDept;
  }
  public Integer getPaperLevel() {
    return paperLevel;
  }
  public void setPaperLevel(Integer paperLevel) {
    this.paperLevel = paperLevel;
  }
  public Integer getPaperStatus() {
    return paperStatus;
  }
  public void setPaperStatus(Integer paperStatus) {
    this.paperStatus = paperStatus;
  }
  public List<ContentClassQuestions> getPaperContentClass() {
    return paperContentClass;
  }
  public void setPaperContentClass(List<ContentClassQuestions> paperContentClass) {
    this.paperContentClass = paperContentClass;
  }
}
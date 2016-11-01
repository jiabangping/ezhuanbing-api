package com.ezhuanbing.api.dao.mybatis.model.templateManager;

import java.util.List;

public class ContentClassQuestions {

  private Integer id;
  private Integer papersId;
  private String className;
  private Integer classSort;
  private Integer classStatus;
  private Integer classCount;
  private Integer classPid;
  private List<QuestionsOption> QueOptionTemp;
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public Integer getPapersId() {
    return papersId;
  }
  public void setPapersId(Integer papersId) {
    this.papersId = papersId;
  }
  public String getClassName() {
    return className;
  }
  public void setClassName(String className) {
    this.className = className;
  }
  public Integer getClassSort() {
    return classSort;
  }
  public void setClassSort(Integer classSort) {
    this.classSort = classSort;
  }
  public Integer getClassStatus() {
    return classStatus;
  }
  public void setClassStatus(Integer classStatus) {
    this.classStatus = classStatus;
  }
  public Integer getClassCount() {
    return classCount;
  }
  public void setClassCount(Integer classCount) {
    this.classCount = classCount;
  }
  public List<QuestionsOption> getQueOptionTemp() {
    return QueOptionTemp;
  }
  public void setQueOptionTemp(List<QuestionsOption> queOptionTemp) {
    QueOptionTemp = queOptionTemp;
  }
  public Integer getClassPid() {
    return classPid;
  }
  public void setClassPid(Integer classPid) {
    this.classPid = classPid;
  }


  
}
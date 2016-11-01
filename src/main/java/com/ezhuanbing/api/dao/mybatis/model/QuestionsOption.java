package com.ezhuanbing.api.dao.mybatis.model;

import java.util.List;

public class QuestionsOption {

  
    
    private Integer id;
    private Integer contentclassicid;
    private Integer qsttype;
    private String qsttitle;
    private String qstpicture;
    private Integer qstrestultcount;
    private List<OptionTemplates> optionTemp;
    public Integer getId() {
      return id;
    }
    public void setId(Integer id) {
      this.id = id;
    }
    public Integer getContentclassicid() {
      return contentclassicid;
    }
    public void setContentclassicid(Integer contentclassicid) {
      this.contentclassicid = contentclassicid;
    }
    public Integer getQsttype() {
      return qsttype;
    }
    public void setQsttype(Integer qsttype) {
      this.qsttype = qsttype;
    }
    public String getQsttitle() {
      return qsttitle;
    }
    public void setQsttitle(String qsttitle) {
      this.qsttitle = qsttitle;
    }
    public String getQstpicture() {
      return qstpicture;
    }
    public void setQstpicture(String qstpicture) {
      this.qstpicture = qstpicture;
    }
    public Integer getQstrestultcount() {
      return qstrestultcount;
    }
    public void setQstrestultcount(Integer qstrestultcount) {
      this.qstrestultcount = qstrestultcount;
    }
    public List<OptionTemplates> getOptionTemp() {
      return optionTemp;
    }
    public void setOptionTemp(List<OptionTemplates> optionTemp) {
      this.optionTemp = optionTemp;
    }
       
  
}
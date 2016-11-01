package com.ezhuanbing.api.model;

public class SendMsgModel {
  
  /**
   * 消息类型：转诊中固定为101
   */
  private int type = 101;
  /**
   * 消息标题
   */
  private String title;
  /**
   * 消息内容
   */
  private String content;
  /**
   * 消息多少秒后消失
   */
  private int delaytime = 0;
  
  public SendMsgModel(String title,String content,int delaytime){
    this.title = title;
    this.content = content;
    this.delaytime = delaytime;
  }

  public int getType() {
    return type;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public int getDelaytime() {
    return delaytime;
  }
}

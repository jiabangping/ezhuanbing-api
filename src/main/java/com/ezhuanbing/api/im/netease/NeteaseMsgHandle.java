package com.ezhuanbing.api.im.netease;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NeteaseMsgHandle {

  @Autowired
  HttpRequestServer httpRequestServer;
  
  public String sendAttachMsgToPerson(String from, String to, String msg) {
    String url = "/msg/sendMsg.action";
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    nvps.add(new BasicNameValuePair("from", from));
    nvps.add(new BasicNameValuePair("ope", "0"));
    nvps.add(new BasicNameValuePair("to", to));
    nvps.add(new BasicNameValuePair("type", "100"));
    nvps.add(new BasicNameValuePair("body", msg));
    return httpRequestServer.post(url, nvps);
  }
}

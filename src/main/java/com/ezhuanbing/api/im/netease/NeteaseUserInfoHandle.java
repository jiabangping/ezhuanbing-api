package com.ezhuanbing.api.im.netease;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NeteaseUserInfoHandle {

  @Autowired
  HttpRequestServer httpRequestServer;

  public String modifyUserPhotoUrl(String userId, String userPhotoUrl, String userName) {
    String url = "/user/updateUinfo.action";
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    nvps.add(new BasicNameValuePair("accid", userId));
    nvps.add(new BasicNameValuePair("icon", userPhotoUrl));
    nvps.add(new BasicNameValuePair("name", userName));
    return httpRequestServer.post(url, nvps);
  }
}

package com.ezhuanbing.api.im.netease;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: NetEaseImIDHandle
 * @Description: 处理登陆网易云信ID的相关操作
 * @author 王凯
 * @date 2016年4月27日 上午11:49:58
 *
 */
@Component
public class NetEaseImIDHandle {

  @Autowired
  HttpRequestServer httpRequestServer;

  /**
   * 
   * @Title: userCreate
   * @Description: 新建用户
   * @param phoneNum 用户电话
   * @param userName 用户名称
   * @return String 服务器响应结果
   */
  public String userCreate(String phoneNum, String userName, String photoUrl) {

    String url = "/user/create.action";
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    nvps.add(new BasicNameValuePair("accid", phoneNum));
    nvps.add(new BasicNameValuePair("name", userName));
    nvps.add(new BasicNameValuePair("icon", photoUrl));
    return httpRequestServer.post(url, nvps);
  }

  /**
   * 
   * @Title: getUserToken
   * @Description: 更新用户token
   * @param phoneNum
   * @return String 服务器响应结果
   * 
   */
  public String refreshUserToken(String phoneNum) {
    String url = "/user/refreshToken.action";
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    nvps.add(new BasicNameValuePair("accid", phoneNum));
    return httpRequestServer.post(url, nvps);
  }

  /**
   * 
   * @Title: getUserInfo 
   * @Description: 获取用户信息 
   * @param @param ids 
   * @param @return 设定文件 
   * @return String 返回类型 
   * @throws
   */
  public String getUserInfo(String ids) {
    String url = "/user/getUinfos.action";
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    nvps.add(new BasicNameValuePair("accids", ids));
    return httpRequestServer.post(url, nvps);
  }
}

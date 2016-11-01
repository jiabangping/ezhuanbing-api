package com.ezhuanbing.api.im.netease;

import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezhuanbing.api.conf.NeteaseImcomfig;

/**
 * 
 * @ClassName: HttpRequestServer
 * @Description: IM接口请求类
 * @author A18ccms a18ccms_gmail_com
 * @date 2016年4月27日 上午11:37:49
 *
 */
@Component
public class HttpRequestServer {

    @Autowired
    NeteaseImcomfig neteaseImcomfig;
  
	/**
	 * 
	 * @Title: post 
	 * @Description: post请求
	 * @param functionUrl 请求的功能地址 
	 * @param nvps 请求参数
	 * @return String
	 * 
	 */
	public String post(String functionUrl, List<NameValuePair> nvps) {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = neteaseImcomfig.getRequesturl() + functionUrl;
		HttpPost httpPost = new HttpPost(url);
		
		String appKey = neteaseImcomfig.getAppkey();
		String appSecret = neteaseImcomfig.getAppsecret();
		String nonce = neteaseImcomfig.getRandom();
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);

		// 设置请求的header
		httpPost.addHeader("AppKey", appKey);
		httpPost.addHeader("Nonce", nonce);
		httpPost.addHeader("CurTime", curTime);
		httpPost.addHeader("CheckSum", checkSum);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		try {
			
			// 设置请求的参数
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			System.out.println(httpPost.getURI());
			HttpResponse response = httpClient.execute(httpPost);
			// 打印执行结果
			return EntityUtils.toString(response.getEntity(), "utf-8");

		} catch (Exception e) {

			return "";
		}
	}
}

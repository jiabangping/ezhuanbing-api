package com.ezhuanbing.api.main;

import java.io.Serializable;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StartRunner implements CommandLineRunner{
  
  private static final Logger log = LoggerFactory.getLogger(StartRunner.class);

  @Value("${ssoUrl}")
  private String ssoUrl;
  public String getSsoUrl() {
    return ssoUrl;
  }
  public void setSsoUrl(String ssoUrl) {
    this.ssoUrl = ssoUrl;
  }
  
  @Value("${ssoPatientCallBackServer}")
  private String ssoPatientCallBackServer;
  public String getSsoPatientCallBackServer() {
    return ssoPatientCallBackServer;
  }
  public void setSsoPatientCallBackServer(String ssoPatientCallBackServer) {
    this.ssoPatientCallBackServer = ssoPatientCallBackServer;
  }
  
  @Value("${ssoDoctorLoginServer}")
  private String ssoDoctorLoginServer;
  public String getSsoDoctorLoginServer() {
    return ssoDoctorLoginServer;
  }
  public void setSsoDoctorLoginServer(String ssoDoctorLoginServer) {
    this.ssoDoctorLoginServer = ssoDoctorLoginServer;
  }
  
  @Value("${ssoDoctorLogoutServer}")
  private String ssoDoctorLogoutServer;
  public String getSsoDoctorLogoutServer() {
    return ssoDoctorLogoutServer;
  }
  public void setSsoDoctorLogoutServer(String ssoDoctorLogoutServer) {
    this.ssoDoctorLogoutServer = ssoDoctorLogoutServer;
  }
  
  @Override
  public void run(String... args) throws Exception {
    registerService();
  }
  
  @Async
  private void registerService(){
    patientSync();
    DoctorLoginSync();
    DoctorLogoutSync();
  }
  
  /**
   * 
  * @Title: patientSync 
  * @Description: 患者信息同步
  * @param     设定文件 
  * @return void    返回类型 
  * @throws
   */
  private void patientSync(){
    SyncModel syncModel = new SyncModel();
    syncModel.setSystemId(3);
    syncModel.setOperation("ADDED");
    syncModel.setMethodUrl(ssoPatientCallBackServer);
    try{
      getRestTemplate().postForObject(ssoUrl + "/api/patient/callback", syncModel, String.class);
    }catch(Exception e){
      log.error("服务注册失败！");
    }
  }
  
  /**
   * 
  * @Title: DoctorLoginSync 
  * @Description: 医生登入同步
  * @param     设定文件 
  * @return void    返回类型 
  * @throws
   */
  private void DoctorLoginSync(){
    SyncModel syncModel = new SyncModel();
    syncModel.setSystemId(3);
    syncModel.setOperation("LOGIN");
    syncModel.setMethodUrl(ssoDoctorLoginServer);
    try{
      getRestTemplate().postForObject(ssoUrl + "/api/sso/callback", syncModel, String.class);
    }catch(Exception e){
      log.error("服务注册失败！");
    }
  }
  
  /**
   * 
  * @Title: DoctorLoginLogoutSync 
  * @Description: 医生登出同步
  * @param     设定文件 
  * @return void    返回类型 
  * @throws
   */
  private void DoctorLogoutSync(){
    SyncModel syncModel = new SyncModel();
    syncModel.setSystemId(3);
    syncModel.setOperation("LOGOUT");
    syncModel.setMethodUrl(ssoDoctorLogoutServer);
    try{
      getRestTemplate().postForObject(ssoUrl + "/api/sso/callback", syncModel, String.class);
    }catch(Exception e){
      log.error("服务注册失败！");
    }
  }
  
  private RestTemplate getRestTemplate(){
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    SimpleClientHttpRequestFactory sf = new SimpleClientHttpRequestFactory();
    sf.setConnectTimeout(2000);
    sf.setReadTimeout(2000);
    restTemplate.setRequestFactory(sf);
    return restTemplate;
  }
  
  class SyncModel implements Serializable{
    
    /** 
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    */ 
    private static final long serialVersionUID = 1L;
    private int systemId;
    private String operation;
    private String methodUrl;
    
    public int getSystemId() {
      return systemId;
    }
    public void setSystemId(int systemId) {
      this.systemId = systemId;
    }
    public String getOperation() {
      return operation;
    }
    public void setOperation(String operation) {
      this.operation = operation;
    }
    public String getMethodUrl() {
      return methodUrl;
    }
    public void setMethodUrl(String methodUrl) {
      this.methodUrl = methodUrl;
    }
  }
}

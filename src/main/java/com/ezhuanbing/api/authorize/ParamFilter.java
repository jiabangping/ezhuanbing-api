package com.ezhuanbing.api.authorize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ezhuanbing.api.model.DoctorResponseToken;
import com.ezhuanbing.api.model.PatientResponseToken;
import com.ezhuanbing.api.service.UserService;
import com.ezhuanbing.api.tools.JacksonJsonUtil;

@Component("paramFilter")
public class ParamFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(ParamFilter.class);

  @Autowired
  UserService userService;

/*  @Autowired
  SendMsgService sendMsgService;*/

  @Value("${ssoUrl:http://192.168.4.129:8080}")
  private String ssoUrl;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
	Map<String, String[]> params  = auth(request);
    request = new ParameterRequestWrapper(request, params);
    filterChain.doFilter(request, response);
  }

  private  Map<String, String[]> auth(ServletRequest request) {
	Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
	   
    DoctorResponseToken doctorResponseToken = null;
    PatientResponseToken patientResponseToken = null;
    String token = "";
    String apiType = "";
    
    token = ((HttpServletRequest) request).getHeader("Authorization");
    apiType = ((HttpServletRequest) request).getHeader("APIType");
    
    if(token == null || token.isEmpty()){
      token = ((HttpServletRequest) request).getParameter("Authorization");
    }
    
    if(apiType == null || apiType.isEmpty()){
      apiType = ((HttpServletRequest) request).getParameter("APIType");
    }
    
    log.debug("Authorization : " + (token == null ? "null" : token));
    log.debug("apiType : " + (apiType == null ? "null" : apiType));
    
    if (token != null && apiType != null) {
      final String uri = ssoUrl + "/auth/validateToken?jwt=" + token+"&loginType="+apiType;

      RestTemplate restTemplate = new RestTemplate();
      try {
        String result = restTemplate.getForObject(uri, String.class);
        if (result != null) {
          if(String.valueOf(APIType.DOCTOR.ordinal()).equals(apiType)){
            doctorResponseToken = (DoctorResponseToken) JacksonJsonUtil.jsonToBean(result, DoctorResponseToken.class);
          }else if(String.valueOf(APIType.PATIENT.ordinal()).equals(apiType)){
            patientResponseToken = (PatientResponseToken) JacksonJsonUtil.jsonToBean(result, PatientResponseToken.class);
          }
        }
//      } catch (HttpClientErrorException e) {
//        log.error(e.getLocalizedMessage(), e);
//        HttpStatus httpStatus = e.getStatusCode();
//        if (httpStatus == HttpStatus.FORBIDDEN) {
//          try {
//            if(String.valueOf(APIType.DOCTOR.ordinal()).equals(apiType)){
//              doctorResponseToken = (DoctorResponseToken) JacksonJsonUtil.jsonToBean(e.getResponseBodyAsString(), DoctorResponseToken.class);
//            }else if(String.valueOf(APIType.PATIENT.ordinal()).equals(apiType)){
//              patientResponseToken = (PatientResponseToken) JacksonJsonUtil.jsonToBean(e.getResponseBodyAsString(), PatientResponseToken.class);
//            }
//          } catch (Exception ignored) {
//            log.error("validateToken error", e);
//          }
          //sendMsgService.sendJwtTimeout(responseToken.getLoginName());
//        } else {
//          log.error("validateToken error", e);
//        }
      } catch (Exception e) {
        log.error(e.getLocalizedMessage(), e);
      }
    }
    if(String.valueOf(APIType.DOCTOR.ordinal()).equals(apiType)){
      request.setAttribute("doctorResponseToken", doctorResponseToken);
      request.setAttribute("apiType", String.valueOf(APIType.DOCTOR.ordinal()));
      
      params.put("doctorId", new String[] { String.valueOf(doctorResponseToken.getDoctorID()) } );
      params.put("departId", new String[] { String.valueOf(doctorResponseToken.getDepartId()) } );
      
    }else if(String.valueOf(APIType.PATIENT.ordinal()).equals(apiType)){
      request.setAttribute("patientResponseToken", patientResponseToken);
      request.setAttribute("apiType", String.valueOf(APIType.PATIENT.ordinal()));
    }
    return params;
  }
}

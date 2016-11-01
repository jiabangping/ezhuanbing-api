package com.ezhuanbing.api.authorize;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.ezhuanbing.api.exception.HttpStatus403Exception;
import com.ezhuanbing.api.model.DoctorResponseToken;
import com.ezhuanbing.api.model.PatientResponseToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthorityInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
          throws Exception {
    
    DoctorResponseToken doctorResponseToken = null;
    PatientResponseToken patientResponseToken = null;
    
    String apiType = (String)request.getAttribute("apiType");
    if(String.valueOf(APIType.DOCTOR.ordinal()).equals(apiType)){
      doctorResponseToken = (DoctorResponseToken) request.getAttribute("doctorResponseToken");
    }else if(String.valueOf(APIType.PATIENT.ordinal()).equals(apiType)){
      patientResponseToken = (PatientResponseToken) request.getAttribute("patientResponseToken");
    }

    if (!(handler instanceof HandlerMethod)) {
      return true;
    }

    HandlerMethod method = (HandlerMethod) handler;

    ApiControl methodAnnotation = method.getMethodAnnotation(ApiControl.class);
    if (methodAnnotation != null) {
      if (methodAnnotation.apiAccessType() == APIAccessType.PRIVATE_API) {
        // api类型是否一致
        if(!String.valueOf(methodAnnotation.apiType().ordinal()).equals(apiType)){
          throw new HttpStatus403Exception("没有访问权限", "token_validate_error", "您没有访问权限！请确认后重新访问。", "");
        }else{
          
          // token是否存在
          if(String.valueOf(APIType.DOCTOR.ordinal()).equals(apiType)){
            if (doctorResponseToken == null || StringUtils.isEmpty(doctorResponseToken.getToken())) {
              throw new HttpStatus403Exception("没有访问权限", "token_validate_error", "您没有访问权限！请确认后重新访问。", "");
            }
          }else if(String.valueOf(APIType.PATIENT.ordinal()).equals(apiType)){
            if (patientResponseToken == null || StringUtils.isEmpty(patientResponseToken.getToken())) {
              throw new HttpStatus403Exception("没有访问权限", "token_validate_error", "您没有访问权限！请确认后重新访问。", "");
            }
          }
        }
      }

      /*
      int functionId = methodAnnotation.functionId();
      if (functionId > 0 && responseToken != null) {
        if (isNotAdmin(responseToken)) {

          if (userService == null) {//解决service为null无法注入问题
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            userService = (UserService) factory.getBean("userService");
          }

          if (roleService == null) {//解决service为null无法注入问题
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            roleService = (RoleService) factory.getBean("roleService");
          }

          String loginName = responseToken.getLoginName();
          UserInfo userInfo = userService.selectBySelective(UserInfo.builder().login(loginName).build());
          if (userInfo == null) {
            throw new HttpStatus404Exception("找不到该用户", "get_user_error",
                    "找不到登陆名为 " + loginName + " 的用户，可能是用户不存在", "");
          }

          if (validateRole(functionId, userInfo.getId())) return true;

          throw new HttpStatus403Exception("没有访问权限", "role_validate_error", "您没有访问权限！请确认后重新访问。", "");
        }
      }*/
    }

    return true;
  }

//  private boolean validateRole(int functionId, Integer id) throws HttpStatus403Exception {
//    List<RoleFunction> roleFunctionList = roleService.getListByUserId(id);
//    List<Function> functionList = roleService.getFunctionListByUserId(id);
//
//    for (RoleFunction roleFunction : roleFunctionList) {
//      List<Function> functionList1 = roleFunction.getFunctions();
//      if (validateFunction(functionId, functionList1)) return true;
//    }
//
//    if (validateFunction(functionId, functionList)) return true;
//
//    return false;
//  }
//
//  private boolean validateFunction(int functionId, List<Function> functionList1) {
//    for (Function function : functionList1) {
//      if (functionId == function.getId()) {
//        return true;
//      }
//    }
//    return false;
//  }
//
//  private boolean isNotAdmin(ResponseToken responseToken) {
//    return "0".equals(responseToken.getAdmin());
//  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    super.afterCompletion(request, response, handler, ex);
  }
}
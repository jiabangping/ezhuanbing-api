package com.ezhuanbing.api.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.model.patient.PatientExtraInfo;
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.service.PatientExtraInfoService;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientExtraInfoController {

  @Autowired
  PatientExtraInfoService patientExtraInfoService;

  /**
   * 
  * @Title: updatePatientExtraInfo 
  * @Description: 修改患者附加信息
  * @param @param info
  * @param @param patientId
  * @param @throws Exception    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping(value = "/{patientId}/extrainfo", method = RequestMethod.PATCH)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public void updatePatientExtraInfo(@RequestBody PatientExtraInfo info,
      @PathVariable("patientId") int patientId) throws Exception {
    if (!patientExtraInfoService.updatePatientExtraInfo(info.getId(), info.getContent())) {
      throw new HttpStatus400Exception("患者信息更新", "", "患者信息更新失败,请稍后重试！", "");
    }
  }

  /**
   * 
  * @Title: insertPatientExtraInfo 
  * @Description: 添加患者附加信息
  * @param @param info
  * @param @param patientId
  * @param @throws Exception    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping(value = "/{patientId}/extrainfo", method = RequestMethod.POST)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public void insertPatientExtraInfo(@RequestBody PatientExtraInfo info,
      @PathVariable("patientId") int patientId) throws Exception {
    
    String dictionaryName ="";
    switch(info.getDictionarycode()){
      case 0:
        dictionaryName = "既往史";
        break;
      case 1:
        dictionaryName = "个人史";
        break;
      case 2:
        dictionaryName = "家族史";
        break;    
      case 3:
        dictionaryName = "过敏史";
        break;
      default:
        break;
    }
    
    info.setPatientId(patientId);
    info.setDictionarytypename("patientExtraInfo");
    info.setDictionaryname(dictionaryName);
    if (!patientExtraInfoService.addPatientExtraInfo(info)) {
      throw new HttpStatus400Exception("患者信息添加", "", "患者信息添加失败,请稍后重试！", "");
    }
  }
}

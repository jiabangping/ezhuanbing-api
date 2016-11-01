package com.ezhuanbing.api.controller.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.service.PatientEducationFileService;
import com.ezhuanbing.api.vo.PatientEducationFileResp;

@RestController
@RequestMapping("/api/v1/patient/educationfile")
public class PatientEducationFileController {

  @Autowired
  PatientEducationFileService patientEducationFileService;
  
  /**
 * @throws Exception 
   * 
  * @Title: selectPatientEducationFile 
  * @Description: 我的宣教资料
  * @param @param patientId
  * @param @return    设定文件 
  * @return List<PatientEducationFileResp>    返回类型 
  * @throws
   */
  @RequestMapping(value="",method=RequestMethod.GET)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public List<PatientEducationFileResp> selectPatientEducationFile(@RequestParam int patientId) throws Exception{
    try {
		return patientEducationFileService.selectPatientEducationFile(patientId);
	} catch (Exception e) {
		throw new HttpStatus400Exception("获取宣教资料", "", "获取宣教资料失败，请稍后重试！", "");
	}
  }
}

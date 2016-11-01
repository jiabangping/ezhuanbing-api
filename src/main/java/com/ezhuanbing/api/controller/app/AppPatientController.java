package com.ezhuanbing.api.controller.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.model.patient.Patient;
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.service.PatientService;
import com.ezhuanbing.api.service.PlanDrugService;
import com.ezhuanbing.api.vo.DrugListDetail;

@RestController
@RequestMapping("/api/v1/patient")
public class AppPatientController {

  @Autowired
  PlanDrugService planDrugService;
  @Autowired
  PatientService patientService;
  
  /**
   * 
  * @Title: getDrugsByPatientId 
  * @Description: 取得患者用药信息
  * @param @param patientId
  * @param @param planFollowupDetailId
  * @param @return    设定文件 
  * @return List<DrugListDetail>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/{patientId}/medicines", method = RequestMethod.GET)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  List<DrugListDetail> getDrugsByPatientId(@PathVariable int patientId,
      @RequestParam int planFollowupDetailId){
    return planDrugService.getDrugsByPatientId(patientId,planFollowupDetailId);
  }
  
  /**
   * 
  * @Title: updatePatientPhoneIMEIInfo 
  * @Description: 更新患者IMEI
  * @param @param patientId
  * @param @param patient
  * @param @throws Exception    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping(value="/{patientId}/phoneImei",method = RequestMethod.PATCH)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API,apiType=APIType.PATIENT)
  public void updatePatientPhoneIMEIInfo(@PathVariable int patientId,
      @RequestBody Patient patient) throws Exception{
    
    if(patientId < 0)
      throw new HttpStatus400Exception("更新患者手机串码","","参数错误【patientId】应该大于0","");
    
    if(!patientService.updatePatientPhoneIEMI(patientId, patient))
      throw new HttpStatus400Exception("更新患者手机串码","","更新患者手机串码失败，请稍后重试！","");
  }
}

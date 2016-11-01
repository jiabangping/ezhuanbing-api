package com.ezhuanbing.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.PatientExtraInfoMapper;
import com.ezhuanbing.api.dao.mybatis.model.patient.PatientExtraInfo;
import com.ezhuanbing.api.dao.mybatis.model.patient.PatientExtraInfoCriteria;

@Service
public class PatientExtraInfoService {

  @Autowired
  PatientExtraInfoMapper patientExtraInfoMapper;
  
  /**
   * 
  * @Title: getPatientExtraInfo 
  * @Description: 查詢患者的附加信息
  * @param patientId 患者Id
  * @return List<PatientExtraInfo> 返回类型 
  * @throws
   */
  public List<PatientExtraInfo> getPatientExtraInfo(int patientId){
    PatientExtraInfoCriteria criteria = new PatientExtraInfoCriteria();
    criteria.createCriteria().andPatientidEqualTo(patientId);
    return patientExtraInfoMapper.selectByExample(criteria);
  }
  
  /**
   * 
  * @Title: updatePatientExtraInfo 
  * @Description: 更新患者附加信息
  * @param patientExtraInfo 患者附加信息
  * @return boolean 返回类型 
  * @throws
   */
  public boolean updatePatientExtraInfo(int id, String content){
    PatientExtraInfo updateInfo = 
        PatientExtraInfo.builder().id(id)
        .content(content)
        .build();
    int result = patientExtraInfoMapper.updateByPrimaryKeySelective(updateInfo);
    return result > 0;
  }
  
  public boolean addPatientExtraInfo(PatientExtraInfo patientExtraInfo){
    int result = patientExtraInfoMapper.insert(patientExtraInfo);
    return result > 0;
  }
}

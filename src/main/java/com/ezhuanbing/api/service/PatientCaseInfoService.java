package com.ezhuanbing.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.DoctorPatientMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientCaseInfoMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientCaseInfoResultMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientMapper;
import com.ezhuanbing.api.dao.mybatis.model.Doctor;
import com.ezhuanbing.api.dao.mybatis.model.PatientCaseInfo;
import com.ezhuanbing.api.dao.mybatis.model.PatientCaseInfoCriteria;
import com.ezhuanbing.api.dao.mybatis.model.PatientCaseInfoResult;
import com.ezhuanbing.api.dao.mybatis.model.PatientCaseInfoResultCriteria;
import com.ezhuanbing.api.exception.HttpStatus403Exception;
import com.ezhuanbing.api.vo.AppPatientCaseInfoResult;
import com.ezhuanbing.api.vo.PatientCaseInfoResp;

@Service
public class PatientCaseInfoService {

	@Autowired
	PatientMapper patientMapper;
	@Autowired
	DoctorPatientMapper doctorPatientMapper;
	@Autowired
	PatientCaseInfoMapper patientCaseInfoMapper;
	@Autowired
	PatientCaseInfoResultMapper patientCaseInfoResultMapper;
	@Autowired
	DoctorService doctorService;


	public List<PatientCaseInfoResp> getPatientCaseInfoMap(PatientCaseInfo example )
			throws HttpStatus403Exception {
		return patientCaseInfoMapper.selectByPatientCaseMap(example);
	}
	
	public PatientCaseInfoResp getPatientCaseInfo(PatientCaseInfo example )
        throws HttpStatus403Exception {
    return patientCaseInfoMapper.selectByPatientCaseInfo(example);
}
	
	public void updatePatientCaseInfo(PatientCaseInfo patientCaseInfo )
        throws HttpStatus403Exception {
      patientCaseInfoMapper.updateByPatientCaseInfo(patientCaseInfo);
	}

	public void insertPatientCaseInfo(PatientCaseInfo patientCaseInfo )
        throws HttpStatus403Exception {
      patientCaseInfoMapper.insert(patientCaseInfo);
    }
	
	/**
	 * 
	* @Title: getPatientCaseInfoResult 
	* @Description: 获取随访意见
	* @param @param planFollowupDetailId
	* @param @param doctorId
	* @param @return    设定文件 
	* @return AppPatientCaseInfoResult    返回类型 
	* @throws
	 */
	public AppPatientCaseInfoResult getPatientCaseInfo(int planFollowupDetailId,int doctorId, int patientId){
	  
	  AppPatientCaseInfoResult result = new AppPatientCaseInfoResult();
	  result.setDoctorId(doctorId);
	  result.setPlanFollowupDetailId(planFollowupDetailId);
	  
	  PatientCaseInfoResultCriteria patientCaseInfoResultCriteria = new PatientCaseInfoResultCriteria();
	  patientCaseInfoResultCriteria.createCriteria().andPlandetailidEqualTo(planFollowupDetailId);
	  List<PatientCaseInfoResult> patientCaseInfoResults 
	      = patientCaseInfoResultMapper.selectByExample(patientCaseInfoResultCriteria);
	  if(patientCaseInfoResults.size() > 0){
	    PatientCaseInfoResult _patientCaseInfoResult = patientCaseInfoResults.get(0);
	    if(_patientCaseInfoResult.getDiagnosis() == null 
	        || _patientCaseInfoResult.getDiagnosis().isEmpty()){
	      result.setDiagnosis("暂缺");
	    }else{
	      result.setDiagnosis(_patientCaseInfoResult.getDiagnosis());
	    }

	    if(_patientCaseInfoResult.getOpinionresult() == null 
            || _patientCaseInfoResult.getOpinionresult().isEmpty()){
          result.setOpinionResult("暂缺");
        }else{
          result.setOpinionResult(_patientCaseInfoResult.getOpinionresult());
        }
	  }
	  
	  Doctor d = doctorService.getDoctor(doctorId);
	  if(d != null && d.getDoctorDepartmentId() != null){
	     PatientCaseInfoCriteria patientCaseInfoCriteria = new PatientCaseInfoCriteria();
	     patientCaseInfoCriteria.createCriteria().andDepartmentidEqualTo(d.getDoctorDepartmentId())
	     .andPatientidEqualTo(patientId).andPatientidEqualTo(patientId);
	     List<PatientCaseInfo> PatientCaseInfos = patientCaseInfoMapper.selectByExample(patientCaseInfoCriteria);
	     if(PatientCaseInfos.size() > 0){
	       PatientCaseInfo _patientCaseInfo = PatientCaseInfos.get(0);
	       if(_patientCaseInfo.getComplained() == null || _patientCaseInfo.getComplained().isEmpty()){
	         result.setComplained("暂缺");
	       }else{
	         result.setComplained(_patientCaseInfo.getComplained());
	       }
	       
           if(_patientCaseInfo.getPresentillness() == null || _patientCaseInfo.getPresentillness().isEmpty()){
             result.setPresentIllness("暂缺");
           }else{
             result.setPresentIllness(_patientCaseInfo.getPresentillness());
           }
	       
           if(_patientCaseInfo.getPasthistory() == null || _patientCaseInfo.getPasthistory().isEmpty()){
             result.setPastHistory("暂缺");
           }else{
             result.setPastHistory(_patientCaseInfo.getPasthistory());
           }
	       
           if(_patientCaseInfo.getPhysical() == null || _patientCaseInfo.getPhysical().isEmpty()){
             result.setPhysical("暂缺");
           }else{
             result.setPhysical(_patientCaseInfo.getPhysical());
           }
           
           if(_patientCaseInfo.getAuxiliaryexamination() == null 
               || _patientCaseInfo.getAuxiliaryexamination().isEmpty()){
             result.setAuxiliaryExamination("暂缺");
           }else{
             result.setAuxiliaryExamination(_patientCaseInfo.getAuxiliaryexamination());
           }
	     }
	  }
	  
	  return result;
	}
}

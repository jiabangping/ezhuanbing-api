package com.ezhuanbing.api.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.PatientCaseInfoResultMapper;
import com.ezhuanbing.api.dao.mybatis.model.PatientCaseInfoResult;
import com.ezhuanbing.api.dao.mybatis.model.PatientCaseInfoResultCriteria;
import com.ezhuanbing.api.exception.HttpStatus403Exception;

@Service
public class PatientCaseInfoResultService {
	@Autowired
	PatientCaseInfoResultMapper patientCaseInfoResultMapper;
	
	public List<PatientCaseInfoResult> getPatientCaseInfoResultByPlanId(int planId)
        throws HttpStatus403Exception {
	  PatientCaseInfoResultCriteria example =  new PatientCaseInfoResultCriteria();
	  example.createCriteria().andPlandetailidEqualTo(planId);
      return patientCaseInfoResultMapper.selectByExample(example);
    }
	
	public List<PatientCaseInfoResult> getPatientCaseInfoResultByPlanDetailId(int planDetailId)
        throws HttpStatus403Exception {
      PatientCaseInfoResultCriteria example =  new PatientCaseInfoResultCriteria();
      example.createCriteria().andPlandetailidEqualTo(planDetailId);
      return patientCaseInfoResultMapper.selectByExample(example);
    }

	
	public void updatePatientCaseInfoResult(PatientCaseInfoResult patientCaseInfoResult )
        throws HttpStatus403Exception {
	  patientCaseInfoResultMapper.updateByPrimaryKey(patientCaseInfoResult);
	}
	

	public void insertPatientCaseInfoResult(PatientCaseInfoResult patientCaseInfoResult )
        throws HttpStatus403Exception {
	  patientCaseInfoResultMapper.insert(patientCaseInfoResult);
    }
}

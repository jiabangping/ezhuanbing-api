package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;


import com.ezhuanbing.api.dao.mybatis.model.PatientCaseInfo;
import com.ezhuanbing.api.vo.PatientCaseInfoResp;
import com.ezhuanbing.noscan.BaseMapper;

public interface PatientCaseInfoMapper  extends BaseMapper<PatientCaseInfo>{
  List<PatientCaseInfoResp> selectByPatientCaseMap(PatientCaseInfo patient);
  PatientCaseInfoResp selectByPatientCaseInfo(PatientCaseInfo patient);
  void updateByPatientCaseInfo(PatientCaseInfo patient);
}
package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezhuanbing.api.dao.mybatis.model.PatientEducationFile;
import com.ezhuanbing.api.vo.PatientEducationFileResp;
import com.ezhuanbing.noscan.BaseMapper;

public interface PatientEducationFileMapper extends BaseMapper<PatientEducationFile>{
  List<PatientEducationFileResp> selectPatientEducationFile(@Param("patientId")int patientId);
}
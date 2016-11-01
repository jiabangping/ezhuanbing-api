package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezhuanbing.api.dao.mybatis.model.Doctor;
import com.ezhuanbing.api.dao.mybatis.model.DoctorPatient;
import com.ezhuanbing.noscan.BaseMapper;

public interface DoctorPatientMapper extends BaseMapper<DoctorPatient>{
  public List<DoctorPatient> getPatientDoctor(@Param("patientId") int patientId);
  public Doctor getDoctor(@Param("doctorId")int doctorId);
  
  /**
   * 修改Doctor-Patient的人群分类id（已有人群分类被删除时，原有人群被重置为默认人群，即可调用此方法）
   * @param paramsMap
   * @return
   */
  public int updateDPGroupId(Map<String, Object> paramsMap);
  
	/**
	 * 批量修改人群分类
	 * @param paramsMap
	 * @return
	 */
	int updateGroupBatch(Map<String, Object> paramsMap);
	
	/**
	 * 获得全部医生
	 * @param paramsMap
	 * @return
	 */
	public List<Doctor> getDoctorList(Map<String, Object> paramsMap);
}
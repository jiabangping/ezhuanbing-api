package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezhuanbing.api.dao.mybatis.model.medicine.PlanDrug;
import com.ezhuanbing.noscan.BaseMapper;

public interface PlanDrugMapper extends BaseMapper<PlanDrug>{
	List<PlanDrug> getDrugsByPatientIdAndPlanDetailId(@Param("patientId")Integer patientId,@Param("planDetailId")Integer planDetailId);
    int getCountByDrugIdAndPlanId(@Param("planDetailId") Integer planDetailId,@Param("drugId") Integer drugId);//@Param("drugListId") Integer drugListId,
}

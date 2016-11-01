package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ezhuanbing.api.dao.mybatis.model.medicine.Medicine;
import com.ezhuanbing.noscan.BaseMapper;

public interface MedicineMapper extends BaseMapper<Medicine>{
	List<Medicine> getMedicinesByName(@Param("name") String name);
	List<Map<String,Object>> getDrugListInfo(@Param("planId") Integer planId);
	List<Medicine> getMedicineNameByAbbr(@Param("abbr") String abbr,@Param("pageIndex")int pageIndex,@Param("pageSize") int pageSize);
}

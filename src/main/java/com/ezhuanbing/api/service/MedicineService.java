package com.ezhuanbing.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.MedicineMapper;
import com.ezhuanbing.api.dao.mybatis.model.medicine.Dictionary;
import com.ezhuanbing.api.dao.mybatis.model.medicine.Medicine;
import com.ezhuanbing.api.dao.mybatis.model.medicine.MedicineCriteria;

@Service
public class MedicineService {
	
	@Autowired
	private MedicineMapper medicineMapper;
	
	public List<Medicine> getDrugNameAndSpecs(String name){
		List<Medicine> list = medicineMapper.getMedicinesByName(name);
		return list;
	}
	public Integer getMedicineId(String name,String specification,String unit,String usage){
		Medicine medicine = new Medicine();
		medicine.setName(name);
		medicine.setSpecifications(specification);
		medicine.setUnit(unit);
		medicine.setUsages(usage);
		List<Medicine> list = medicineMapper.select(medicine);
		Integer id = 0;
		if(CollectionUtils.isNotEmpty(list)){
			id = list.get(0).getId();
		}
		return id;
	}
	
	public Medicine getById(Integer id){
		return medicineMapper.selectByPrimaryKey(id);
	}
	
	public List<Map<String,Object>> getDrugListInfo(Integer planId){
		return medicineMapper.getDrugListInfo(planId);
	}
	public List<Medicine> getMedicineNameByAbbr(String abbr,int pageIndex,int pageSize){
		MedicineCriteria example = new MedicineCriteria();
		example.setLimitStart(pageIndex);
		example.setLimitEnd(pageSize);
		MedicineCriteria.Criteria c = example.createCriteria();
		c.andAbbreviationLike("%" + abbr + "%");
		return medicineMapper.selectByExample(example);//.getMedicineNameByAbbr(abbr,pageIndex,pageSize);
	}
	
	public Medicine getMedicineInfo(String name,String spec,String usages){
		MedicineCriteria example = new MedicineCriteria();
		MedicineCriteria.Criteria c = example.createCriteria();
		c.andNameEqualTo(name).andSpecificationsEqualTo(spec).andUsagesEqualTo(usages);
		List<Medicine> list = medicineMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
}

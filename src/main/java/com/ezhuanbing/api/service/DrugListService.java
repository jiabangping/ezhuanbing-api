package com.ezhuanbing.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.DrugListMapper;
import com.ezhuanbing.api.dao.mybatis.model.medicine.DrugList;

@Service
public class DrugListService {
	@Autowired
	private DrugListMapper drugListMapper;
	
	public int add(DrugList record){
		int num = drugListMapper.insertUseGeneratedKeys(record);
		return num;
	}
	
	public int count(Integer planDetailId){
		DrugList record = new DrugList();
		record.setPlanDetailId(planDetailId);
		return drugListMapper.selectCount(record);
	}
	/**
	 * 查看当前用药计划有多少个药单
	 * @param planId
	 * @return
	 */
	public List<DrugList> getDrugList(Integer planDetailId){
		DrugList record = new DrugList();
		record.setPlanDetailId(planDetailId);
		return drugListMapper.select(record);
	}
}

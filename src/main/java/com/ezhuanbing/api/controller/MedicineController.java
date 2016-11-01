package com.ezhuanbing.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ezhuanbing.api.dao.mybatis.model.medicine.Medicine;
import com.ezhuanbing.api.service.MedicineService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;

@RestController
@RequestMapping("/medicine")
public class MedicineController {
	
	@Autowired
	private MedicineService medicineService;
	
	@RequestMapping(value = "/getDrugNameAndSpecs",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getDrugNameAndSpecs(String name){
		return JSON.toJSONString(medicineService.getDrugNameAndSpecs(name));
	}
	
	@RequestMapping(value = "/getMedicineNameByAbbr")//,produces=MediaType.APPLICATION_JSON_UTF8_VALUE
	public PageInfo<Medicine> getMedicineNameByAbbr(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
	          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,@RequestParam String name){
		if(StringUtil.isNotEmpty(name)){
			return new PageInfo<Medicine>(medicineService.getMedicineNameByAbbr(name,pageIndex,pageSize));
		}
		return new PageInfo<Medicine>();
	}
	@RequestMapping(value = "/getMedicineInfo",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)//
	public String getMedicineInfo(@RequestParam String name,@RequestParam String spec,@RequestParam String usages){
		return JSON.toJSONString(medicineService.getMedicineInfo(name, spec, usages));
	}
}

package com.ezhuanbing.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ezhuanbing.api.service.DictionaryService;
import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.model.medicine.Dictionary;
@RestController
@RequestMapping("/dict")
public class DictionaryController {
	@Autowired
	private DictionaryService dictionaryService;
	
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getDictsByName",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getDictsByName(String name){
		List<Dictionary> list = new ArrayList<Dictionary>();
		list = dictionaryService.getDictsByName(name);
		return JSON.toJSONString(list);
	}
}

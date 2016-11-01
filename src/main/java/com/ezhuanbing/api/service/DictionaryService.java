package com.ezhuanbing.api.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.DictionaryMapper;
import com.ezhuanbing.api.dao.mybatis.model.medicine.Dictionary;
import com.ezhuanbing.api.dao.mybatis.model.medicine.DictionaryCriteria;

@Service
public class DictionaryService {
	@Autowired
	private DictionaryMapper dictionaryMaper;
	public List<Dictionary> getDictsByName(String name){
		return dictionaryMaper.getDictsByName(name);
	}
	
	public String getDictCode(Dictionary dict){
		List<Dictionary> list = dictionaryMaper.select(dict);
		String value = null;
		if(CollectionUtils.isNotEmpty(list)){
			value = list.get(0).getValue();
		}
		return value;
	}
	
	public String getDictCode(String typeName,Integer code){
		DictionaryCriteria example = new DictionaryCriteria();
		DictionaryCriteria.Criteria c = example.createCriteria();
		if(code!=null){
			c.andTypenameEqualTo(typeName).andCodeEqualTo(code);
			List<Dictionary> list = dictionaryMaper.selectByExample(example);
			String value = null;
			if(CollectionUtils.isNotEmpty(list)){
				value = list.get(0).getValue();
			}
			return value;
		}else{
			return null;
		}
	}
	public Integer getDictKey(String typeName,String value){
		DictionaryCriteria example = new DictionaryCriteria();
		DictionaryCriteria.Criteria c = example.createCriteria();
		c.andTypenameEqualTo(typeName).andValueEqualTo(value);
		List<Dictionary> list = dictionaryMaper.selectByExample(example);
		Integer key = null;
		if (CollectionUtils.isNotEmpty(list)) {
			key = list.get(0).getCode();
		}
		return key;
	}
}

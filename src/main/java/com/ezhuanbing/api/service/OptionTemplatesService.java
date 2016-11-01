package com.ezhuanbing.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.OptionTemplatesMapper;
import com.ezhuanbing.api.dao.mybatis.model.OptionTemplates;
import com.ezhuanbing.api.dao.mybatis.model.OptionTemplatesCriteria;


@Service
public class OptionTemplatesService {
	@Autowired
	private OptionTemplatesMapper optionTemplatesMapper;
	
	public OptionTemplates getById(Integer id){
		return optionTemplatesMapper.selectByPrimaryKey(id);
	}
	
	public OptionTemplates add(OptionTemplates record){
		int num = optionTemplatesMapper.insert(record);
		if(num>0){
			return record;
		}else{
			return null;
		}
	}
	public List<OptionTemplates> getAllOptionClass(Integer quesId){
	    OptionTemplatesCriteria example = new OptionTemplatesCriteria();
	    OptionTemplatesCriteria.Criteria c = example.createCriteria();
		c.andQuestionstemplatesidEqualTo(quesId);
		return optionTemplatesMapper.selectByExample(example);
	}
	
	public int deleteByQuesId(Integer quesId){
	    OptionTemplatesCriteria example = new OptionTemplatesCriteria();
	    OptionTemplatesCriteria.Criteria c = example.createCriteria();
		c.andQuestionstemplatesidEqualTo(quesId);
		return optionTemplatesMapper.deleteByExample(example);
	}
	
}

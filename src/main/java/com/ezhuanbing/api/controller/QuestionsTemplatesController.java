package com.ezhuanbing.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.model.QuestionsTemplates;
import com.ezhuanbing.api.service.QuestionsTemplatesService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/qst")
public class QuestionsTemplatesController {
	@Autowired
	private QuestionsTemplatesService questionsTemplatesService;
	
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping("/getAllQst")
	public PageInfo<QuestionsTemplates> getAllQst(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
	          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,@RequestParam("classId") Integer classId){
		List<QuestionsTemplates> list = questionsTemplatesService.getAllQstsByPage(pageIndex,pageSize,classId);
		return new PageInfo<QuestionsTemplates>(list);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/delete/{id}")
	public String delete(@PathVariable("id") Integer id){
		int num = questionsTemplatesService.deleteById(id);
		return String.valueOf(num);
	}
}

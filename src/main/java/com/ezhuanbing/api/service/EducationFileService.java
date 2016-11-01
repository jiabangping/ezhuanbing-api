package com.ezhuanbing.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.EducationFileMapper;
import com.ezhuanbing.api.dao.mybatis.model.EducationFile;
import com.github.pagehelper.PageHelper;

@Service
public class EducationFileService {
	@Autowired
	private EducationFileMapper mbEducationFileMapper;
	
	public EducationFile add(EducationFile file){
		int num = mbEducationFileMapper.insert(file);
		if(num > 0){
			return file;
		}else{
			return null;
		}
	}
	
	public List<EducationFile> getAllFiles(int pageIndex,int pageSize){
		PageHelper.startPage(pageIndex,pageSize);
		return mbEducationFileMapper.selectByExample(null);
				
	}
	
	public EducationFile getEduFileById(Integer id){
		return mbEducationFileMapper.selectByPrimaryKey(id);
	}
	
	public int update(EducationFile record){
		return mbEducationFileMapper.updateByPrimaryKeySelective(record);
	}
	
}

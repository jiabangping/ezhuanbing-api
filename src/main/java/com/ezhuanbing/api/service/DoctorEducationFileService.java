package com.ezhuanbing.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.DoctorEducationFileMapper;
import com.ezhuanbing.api.dao.mybatis.model.DoctorEducationFile;
import com.ezhuanbing.api.dao.mybatis.model.DoctorEducationFileCriteria;
import com.github.pagehelper.PageHelper;

@Service
public class DoctorEducationFileService {
	
	@Autowired
	private DoctorEducationFileMapper mbDoctorEducationFileMapper;
	
	public DoctorEducationFile add(DoctorEducationFile record){
		int num = mbDoctorEducationFileMapper.insert(record);
		if(num > 0){
			return record;
		}else{
			return null;
		}
	}
	public List<DoctorEducationFile> getAllDocFiles(int pageIndex,int pageSize,Integer doctorId){
		PageHelper.startPage(pageIndex,pageSize);
		DoctorEducationFileCriteria example = new DoctorEducationFileCriteria();
		DoctorEducationFileCriteria.Criteria c = example.createCriteria();
		c.andDoctoridEqualTo(doctorId);
		return mbDoctorEducationFileMapper.selectByExample(example);
	}
	
	public DoctorEducationFile getFileById(Integer id){
		return mbDoctorEducationFileMapper.selectByPrimaryKey(id);
	}
	
	public int deleteFileById(Integer id){
		return mbDoctorEducationFileMapper.deleteByPrimaryKey(id);
	}
	
	public DoctorEducationFile update(DoctorEducationFile record){
		int num =  mbDoctorEducationFileMapper.updateByPrimaryKeySelective(record);
		if(num >0){
			return getFileById(record.getId());
		}else{
			return null;
		}
	}
	
}

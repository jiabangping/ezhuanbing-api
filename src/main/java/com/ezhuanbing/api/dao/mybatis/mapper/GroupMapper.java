package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.ezhuanbing.api.dao.mybatis.model.Group;
import com.ezhuanbing.noscan.BaseMapper;

public interface GroupMapper extends BaseMapper<Group> { 
	
	/**
	 * 根据医生id获得该医生的人群分类
	 */
	List<Group> selectGroupsByDoctorId(Map<String, Object> paramsMap);
	
	/**
	 * 新建一个人群分类
	 */
	int insertGroup(Group record);
	
	/**
	 * 删除一个人群分类
	 */
	int delGroupById(Map<String, Object> paramsMap);

}
package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezhuanbing.api.dao.mybatis.model.medicine.Dictionary;
import com.ezhuanbing.noscan.BaseMapper;

public interface DictionaryMapper extends BaseMapper<Dictionary>{
	List<Dictionary> getDictsByName(@Param("name") String name);
}

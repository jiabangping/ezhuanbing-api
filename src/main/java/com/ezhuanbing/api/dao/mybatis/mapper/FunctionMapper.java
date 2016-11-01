package com.ezhuanbing.api.dao.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.ezhuanbing.api.model.Function;
import com.ezhuanbing.api.model.FunctionExample;
import com.ezhuanbing.noscan.BaseMapper;

import java.util.List;

public interface FunctionMapper extends BaseMapper<Function> {
  int countByExample(FunctionExample example);

  int deleteByExample(FunctionExample example);

  List<Function> selectByExample(FunctionExample example);

  int updateByExampleSelective(@Param("record") Function record, @Param("example") FunctionExample example);

  int updateByExample(@Param("record") Function record, @Param("example") FunctionExample example);

  List<Function> selectByRoleId(int roleId);

  List<Function> selectByUserId(Integer userId);

  List<Function> selectAllByUserLogin(String loginName);

  List<Function> selectAllByRoleIds(List<Integer> roleIds);
}
package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;

import com.ezhuanbing.api.model.RoleInfo;
import com.ezhuanbing.noscan.BaseMapper;

public interface RoleInfoMapper extends BaseMapper<RoleInfo> {

  List<RoleInfo> selectByUserId(Integer userId);
}
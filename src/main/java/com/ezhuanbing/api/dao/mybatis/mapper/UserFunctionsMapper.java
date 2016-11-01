package com.ezhuanbing.api.dao.mybatis.mapper;

import com.ezhuanbing.api.model.UserFunctions;
import com.ezhuanbing.noscan.BaseMapper;

public interface UserFunctionsMapper extends BaseMapper<UserFunctions> {
    int deleteByPrimaryKey(UserFunctions key);

    int insert(UserFunctions record);

    int insertSelective(UserFunctions record);
}
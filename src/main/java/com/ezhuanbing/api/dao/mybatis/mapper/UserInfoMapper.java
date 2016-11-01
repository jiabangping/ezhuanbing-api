package com.ezhuanbing.api.dao.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.ezhuanbing.api.model.UserInfo;
import com.ezhuanbing.noscan.BaseMapper;

import java.util.List;

public interface UserInfoMapper extends BaseMapper<UserInfo> {

  UserInfo selectBySelective(UserInfo record);

  int updateUserStatus(int id);

  int updateUserNeteaseToken(@Param("userName") String userName, @Param("neteaseToken") String neteaseToken);

  List<UserInfo> selectUserAndAdmin(Integer hospitalId);
}
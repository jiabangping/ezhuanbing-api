package com.ezhuanbing.api.dao.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.ezhuanbing.api.dao.mybatis.model.ChatRecord;
import com.ezhuanbing.noscan.BaseMapper;

public interface ChatRecordMapper extends BaseMapper<ChatRecord>{
  public int setChatDataRead(@Param("doctorId") int doctorId);
}
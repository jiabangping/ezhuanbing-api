package com.ezhuanbing.api.dao.mybatis.mapper;

import com.ezhuanbing.api.model.SmsModel;

public interface SmsSendMapper {

  int insert(SmsModel sms);

  int updateByPrimary(SmsModel sms);

}

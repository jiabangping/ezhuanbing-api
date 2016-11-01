package com.ezhuanbing.api.dao.mybatis.model.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImMessageQueueModel {

	private String fromAccountId;
	private String toGroupId;
	private String message;
	private String type;
	private String date;
	/**
	 * 会诊系统聊天计划Id
	 */
	private int planId;
	/**
	 * 专病系统新追加，固定1
	 */
	private int origin; 
	/**
	 * 专病系统新追加 随访详情Id
	 */
	private int detailId;
	/**
	 * 0:专病患者发送的信息 1：专病医生发送的信息
	 */
	private int sender;
	/**
	 * pc端是否在线
	 */
	private int pcOnline;
}

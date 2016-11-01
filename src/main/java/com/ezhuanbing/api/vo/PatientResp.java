package com.ezhuanbing.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResp {
	  
	private Integer id; 
	private String name;//患者名称 
	private String sex; 
	private String age; 
	private String idCard;// 
	private String loginName;// 
	private Integer groupId;// 
	private String groupName;// 
	private Integer followupCount;//随访次数 
	private String area;//  
	private String inDate;//加入日期
	 
}

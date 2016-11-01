package com.ezhuanbing.api.dao.mybatis.model.medicine;

import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="mb_plandrug")
public class PlanDrug {
	@Id
	private Integer id;
	private Integer drugListId;
	private Integer drugId;
//	private Integer dose;
	private String dose;
	private Integer unit;
	private Integer amount;
	private Integer medicationMethods;
	private Integer frequency;
	private String  doctoradvice;
	private Integer patientId;
	private Integer days;
	
	
}

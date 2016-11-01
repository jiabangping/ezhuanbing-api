package com.ezhuanbing.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlanDrugVo {
	private Integer drugListId;
	private Integer drugId;
	private String dose;
	private String unit;
	private Integer amount;
	private String medicationMethods;
	private Integer frequency;
	private String doctoradvice;
	private Integer patientId;
	private Integer days;

}

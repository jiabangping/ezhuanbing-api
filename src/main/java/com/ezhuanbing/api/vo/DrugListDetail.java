package com.ezhuanbing.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrugListDetail {
	
	private Integer id;
	private String name;
	private String specification;
	private String dose;
	private String unit;
	private String amount;
	private String medicationMethods;
	private String frequency;
	private Integer doseCode;
	private Integer unitCode;
	private Integer medicationMethodsCode;
	private Integer frequencyCode;
	private String doctorAdvice;
	private Integer drugListId;
	private Integer days;
	private String doseUnit;
}

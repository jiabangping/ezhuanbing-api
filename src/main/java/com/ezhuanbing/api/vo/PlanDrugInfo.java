package com.ezhuanbing.api.vo;

import com.ezhuanbing.api.dao.mybatis.model.medicine.PlanDrug;

public class PlanDrugInfo extends PlanDrug{
	private String name;
	private String specification;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	
}

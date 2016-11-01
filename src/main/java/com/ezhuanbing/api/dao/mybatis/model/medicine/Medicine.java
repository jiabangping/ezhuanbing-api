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
@Table(name="mb_medicine")
public class Medicine {
	@Id
	private Integer id;
	private String name;
	private String factory;
	private String specifications;
	private String abbreviation;
	private String unit;
	private String doseUnit;
	private String usages;
	
}

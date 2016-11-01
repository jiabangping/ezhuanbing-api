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
@Table(name="mb_druglist")
public class DrugList {
	@Id
	private Integer id;
	private Integer planDetailId;
	private String name;

}

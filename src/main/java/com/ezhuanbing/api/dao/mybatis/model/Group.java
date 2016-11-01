package com.ezhuanbing.api.dao.mybatis.model;

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
@Table(name="mb_group")
public class Group {
	
	@Id
	private Integer id;
	private Integer doctorId;
	private String name;
	private Integer isdefault;
}

package com.ezhuanbing.api.dao.mybatis.model.medicine;

import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="mb_dictionary")
public class Dictionary {
    private String typeName;
	private Integer code;
	private String value;
	private Integer status;
}

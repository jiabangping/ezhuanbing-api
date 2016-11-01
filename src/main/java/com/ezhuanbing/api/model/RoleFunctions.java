package com.ezhuanbing.api.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Table;

@Data
@Builder
@Table(name = "tr_roleFunctions")
public class RoleFunctions {
    private Integer roleId;

    private Integer functionId;
}
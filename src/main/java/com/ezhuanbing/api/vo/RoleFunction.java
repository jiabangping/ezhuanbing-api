package com.ezhuanbing.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.ezhuanbing.api.model.Function;

/**
 * Created by wangwl on 2016/7/18.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleFunction {

    private Integer roleId;

    private String roleName;

    private List<Function> functions;
}

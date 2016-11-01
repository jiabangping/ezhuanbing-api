package com.ezhuanbing.api.model;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wangwl on 2016/7/25.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tr_userRoles")
public class UserRoles {

  private Integer userId;

  private Integer roleId;
}

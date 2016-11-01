package com.ezhuanbing.api.model;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tr_userFunctions")
public class UserFunctions {

  private Integer userId;

  private Integer functionId;
}
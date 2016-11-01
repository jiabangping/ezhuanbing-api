package com.ezhuanbing.api.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tr_userInfo")
public class UserInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String userName;

  private String password;

  private Integer hospitalId;

  private String login;

  private Integer sysId;

  private Integer doctorId;

  private String status;
  
  private String online;
  
  private String neteaseToken;
}
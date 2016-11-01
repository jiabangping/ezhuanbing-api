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
@Table(name="mb_onlinedoctor")
public class OnlineDoctor {

  @Id
  private Integer id;
  private Integer doctorid;
}
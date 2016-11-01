package com.ezhuanbing.api.dao.mybatis.model;

import java.util.Date;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "area")
public class Area {

    private Integer id;
    private Integer provinceid;
    private Integer cityid;
    private String provincename;
    private String cityname;
    private String districtname;
    private Integer sort;
    private Integer status;
    private Date indate;
    private Integer inuser;
    private Date editdate;
    private Integer edituser;
}
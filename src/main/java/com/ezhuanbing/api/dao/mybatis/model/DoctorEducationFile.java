package com.ezhuanbing.api.dao.mybatis.model;

import javax.persistence.Id;
import javax.persistence.Table;

import com.ezhuanbing.api.tools.ImageConfigInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="mb_doctoreducationfile")
public class DoctorEducationFile {
	@Id
    private Integer id;

    private String title;

    private String thumbnailurl;

    private String profile;

    private String fileurl;

    private String status;

    private Integer doctorid;

    public String getImgUrl(){
        return ImageConfigInfo.GetImageServer("EducationFile", thumbnailurl);
    }
}
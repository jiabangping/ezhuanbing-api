package com.ezhuanbing.api.dao.mybatis.mapper;

import java.util.List;

import tk.mybatis.mapper.common.BaseMapper;

import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PaperImage;

public interface PaperImageMapper extends BaseMapper<PaperImage> {


	List<PaperImage> selectPaperImagesByPaperId(Integer id);
}
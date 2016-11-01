package com.ezhuanbing.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.PaperImageMapper;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PaperImage;

@Service
public class PaperImageService {

	@Autowired
	PaperImageMapper paperImageMapper;
	
	public List<PaperImage> getPaperImagesByPaperId(Integer paperId) {
		return paperImageMapper.selectPaperImagesByPaperId(paperId);
	}
	
	/**
	 * 
	* @Title: addPaperImage 
	* @Description: 新增问卷图片
	* @param @param paperId
	* @param @param imgUrl
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean addPaperImage(int paperId,String imgUrl){
	  
	  PaperImage paperImg = PaperImage.builder().paperId(paperId)
	                        .imageName(imgUrl).uploadTime(new Date()).build();
	  int result = paperImageMapper.insert(paperImg);
	  return result > 0;
	  
	}
	
	/**
	 * 
	* @Title: deletePaperImage 
	* @Description: 删除图片
	* @param @param imgId
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean deletePaperImage(int imgId){
	  int result = paperImageMapper.deleteByPrimaryKey(imgId);
	  return result > 0;
	}
}

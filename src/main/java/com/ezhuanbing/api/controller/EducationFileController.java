package com.ezhuanbing.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.model.DoctorEducationFile;
import com.ezhuanbing.api.dao.mybatis.model.EducationFile;
import com.ezhuanbing.api.model.DoctorResponseToken;
import com.ezhuanbing.api.service.DoctorEducationFileService;
import com.ezhuanbing.api.service.EducationFileService;
import com.ezhuanbing.api.service.ImageService;
import com.ezhuanbing.api.tools.CommonTools;
import com.ezhuanbing.api.tools.ConstantClass;
//import com.ezhuanbing.api.tools.ConstantClass;
import com.ezhuanbing.api.vo.MbEducationFileVo;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/mbEduFile")
public class EducationFileController {
	@Autowired
	private EducationFileService mbEducationFileService;
	
	@Autowired
	private DoctorEducationFileService mbDoctorEducationFileService;
	
	@Autowired
	private ImageService imageService;
	
	
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/cpFileToDoctor",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String cpFileToDoctor(HttpServletRequest request) throws Exception{
		Integer id = Integer.parseInt(request.getParameter("id")==null?"0":request.getParameter("id"));
		EducationFile file = mbEducationFileService.getEduFileById(id);
		BeanCopier beanCopier = BeanCopier.create(EducationFile.class, DoctorEducationFile.class, false);
		DoctorEducationFile docFile = new DoctorEducationFile();
		String oldFilePath = file.getFileurl();
		String name = System.currentTimeMillis() + ".html";//oldFilePath.substring(oldFilePath.lastIndexOf("."),oldFilePath.length());
		String fname = "";
		if(StringUtils.isNotBlank(oldFilePath)){
			fname = new File(oldFilePath).getName();
		}
		String path = request.getSession().getServletContext().getRealPath("/");
		String fileUrl = "";
		if(StringUtils.isNotBlank(oldFilePath)){
			String cpyName = fname.substring(0,fname.lastIndexOf("."))+"cpy.html";
			String basePath = path + "/PostTemp/" +fname;
			fileUrl = imageService.uploadFile(cpyName, 2, new File(basePath));
		}
//		FileInputStream fis = null;
//		FileOutputStream fos = null;
//		try {
//			fis = new FileInputStream(f);
//			fos = new FileOutputStream(new File(basePath));
//			int len = 0;
//	        byte[] buf = new byte[1024];
//	        while ((len = fis.read(buf)) != -1) {
//	            fos.write(buf, 0, len);
//	        }
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally{
//			try{
//				 fis.close();
//			     fos.close();
//			}catch(IOException e){
//				e.printStackTrace();
//			}
//		}
		beanCopier.copy(file, docFile, null);
		DoctorResponseToken token = (DoctorResponseToken)request.getAttribute("doctorResponseToken");
		Integer doctorId = token.getDoctorID();
		docFile.setDoctorid(doctorId);
		docFile.setFileurl(fileUrl);
		docFile = mbDoctorEducationFileService.add(docFile);
		return JSON.toJSONString(docFile);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getAll")
	public PageInfo<EducationFile> getAll(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
	          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize){
		List<EducationFile> list = mbEducationFileService.getAllFiles(pageIndex, pageSize);
		return new PageInfo<EducationFile>(list);
	}
	
	
}

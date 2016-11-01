package com.ezhuanbing.api.service;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.PatientEducationFileMapper;
import com.ezhuanbing.api.dao.mybatis.model.PatientEducationFile;
import com.ezhuanbing.api.vo.PatientEducationFileResp;

@Service
public class PatientEducationFileService {
  
  @Autowired
  PatientEducationFileMapper patientEducationFileMapper;
  
  /**
 * @throws Exception 
   * 
  * @Title: selectPatientEducationFile 
  * @Description: 获取患者宣教资料
  * @param @param patientId
  * @param @return    设定文件 
  * @return List<PatientEducationFileResp>    返回类型 
  * @throws
   */
  public List<PatientEducationFileResp> selectPatientEducationFile(int patientId) throws Exception{
	  
	  List<PatientEducationFileResp> returnfiles = new ArrayList<PatientEducationFileResp>();
	  List<PatientEducationFileResp> files = 
			  patientEducationFileMapper.selectPatientEducationFile(patientId);
	  for (PatientEducationFileResp patientEducationFileResp : files) {
		  PatientEducationFileResp file = 
				  PatientEducationFileResp.builder()
				  .title(URLDecoder.decode(patientEducationFileResp.getTitle(), "utf-8"))
				  .profile(URLDecoder.decode(patientEducationFileResp.getProfile(), "utf-8"))
				  .fileUrl(patientEducationFileResp.getFileUrl())
				  .sendTime(patientEducationFileResp.getSendTime())
				  .build();
		  returnfiles.add(file);
	  }
	  
	  return returnfiles;
  }
  
  /**
   * 
  * @Title: addPatientEducationFile 
  * @Description: 添加患者宣教资料
  * @param @param file
  * @param @return    设定文件 
  * @return int    返回类型 
  * @throws
   */
  public int addPatientEducationFile(PatientEducationFile file){
    return patientEducationFileMapper.insert(file);
  }
}

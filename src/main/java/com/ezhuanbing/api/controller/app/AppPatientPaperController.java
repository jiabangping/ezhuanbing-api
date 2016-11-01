package com.ezhuanbing.api.controller.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.service.PatientPaperService;
import com.ezhuanbing.api.vo.AppPatientContentClass;

@RestController
@RequestMapping("/api/v1/paper")
public class AppPatientPaperController {

  @Autowired
  PatientPaperService patientPaperService;
  // 1 获取计划下的所有问卷
  
  // 2 获取问卷下的所有分类
 /**
  * 
 * @Title: getPaperClass 
 * @Description:根据patientPaperId获取分类菜单Paper
 * @param @param paperId
 * @param @return
 * @param @throws Exception    设定文件 
 * @return List<AppPatientContentClass>    返回类型 
 * @throws
  */
  @RequestMapping(value = "/{paperId}/contendclass", method = RequestMethod.GET)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public List<AppPatientContentClass> getPaperClass(
          @PathVariable("paperId") int paperId) throws Exception  {
    
    if(paperId < 0)
      throw new HttpStatus400Exception("获取问卷下的所有分类","","参数错误【paperId】应该大于0","");
    
      return patientPaperService.selectPatientPaperContentClassForApp(paperId);
  }
}

package com.ezhuanbing.api.controller.app;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientFollowUpPlanDetailMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientFollowUpPlanMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientPaperMapper;
import com.ezhuanbing.api.dao.mybatis.model.PapersTemplates;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlan;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlanDetail;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PaperImage;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientPaper;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientPaperExample;
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.service.ImageService;
import com.ezhuanbing.api.service.PaperImageService;
import com.ezhuanbing.api.service.PapersTemplatesService;

@RestController
@RequestMapping("/api/v1/paper")
public class AppPaperController {

  @Autowired
  ImageService imageService;
  @Autowired
  PaperImageService paperImageService;
  @Autowired
  PapersTemplatesService papersTemplatesService;
  @Autowired
  PatientFollowUpPlanDetailMapper patientFollowUpPlanDetailMapper;
  @Autowired
  PatientFollowUpPlanMapper patientFollowUpPlanMapper;
  @Autowired
  PatientPaperMapper patientPaperMapper;
  
  
  /**
   * 
   * @Title: uploadImage 
   * @Description: 问卷图片上传
   * @param @param paperId 
   * @param @param file 
   * @param @throws Exception 设定文件
   * @return void 返回类型 @throws
   */
  @RequestMapping(value = "/{paperId}/image", method = RequestMethod.POST)
  //@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public void uploadImage(@PathVariable int paperId,
                          @RequestParam("file") MultipartFile file)
      throws Exception {

    if(paperId < 0)
      throw new HttpStatus400Exception("问卷图片上传 ","","参数错误【paperId】应该大于0","");
    
    
    boolean result = false;
    String imgUrl = imageService.uploadPaperImage(paperId, file);
    if (imgUrl != null && !imgUrl.isEmpty()) {
      result = paperImageService.addPaperImage(paperId, imgUrl);
    }

    if (!result)
      throw new HttpStatus400Exception("问卷图片上传", "", "图片上传失败，请稍后重试！", "");
    
    PatientPaper patientPaper = patientPaperMapper.selectByPrimaryKey(paperId);
    int planDetailId = patientPaper.getPlanDetailId();
    // 修改首诊状态
    PatientFollowUpPlanDetail detail 
      = patientFollowUpPlanDetailMapper.selectByPrimaryKey(planDetailId);
    if(detail != null){
      PatientFollowUpPlan plan = patientFollowUpPlanMapper.selectByPrimaryKey(detail.getPlanId());
      if(plan != null && plan.getPlanType() !=null 
          && plan.getPlanType() == 1 && !"4".equals(detail.getStatus())){
        // 首诊有图片上传成功,且未修改过数据库状态，就修改状态为4
        PatientFollowUpPlanDetail modifyPlanDetail = PatientFollowUpPlanDetail.builder()
            .id(planDetailId).status("4").feedbackTime(new Date()).build();
          patientFollowUpPlanDetailMapper.updateByPrimaryKeySelective(modifyPlanDetail);
      }
    }
  }
  
  @RequestMapping(value = "/image/{imgId}", method = RequestMethod.DELETE)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public void deleteImage(@PathVariable int imgId) throws Exception{
    
    boolean result = paperImageService.deletePaperImage(imgId);
    
    if (!result)
      throw new HttpStatus400Exception("问卷图片上传", "", "图片删除失败，请稍后重试！", "");
  }

  /**
   * 
  * @Title: uploadImage 
  * @Description: 获取问卷图片列表
  * @param @param paperId
  * @param @return
  * @param @throws Exception    设定文件 
  * @return List<PaperImage>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/{paperId}/images", method = RequestMethod.GET)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public List<PaperImage> uploadImage(@PathVariable int paperId) throws Exception {

    if(paperId < 0)
      throw new HttpStatus400Exception("问卷图片获取 ","","参数错误【paperId】应该大于0","");
    
    return paperImageService.getPaperImagesByPaperId(paperId);
  }
  
  /**
   * 
  * @Title: getDoctorTemplates 
  * @Description: 获取医生的所有模板
  * @param @param doctorId
  * @param @return    设定文件 
  * @return List<PapersTemplates>    返回类型 
  * @throws
   */
  @RequestMapping(value="/all")
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public List<PapersTemplates> getDoctorTemplates(@RequestParam int doctorId){
    return papersTemplatesService.getDoctorTemplates(doctorId);
  }
  
  /**
   * 
  * @Title: getDoctorTemplates 
  * @Description: 获取医生的所有模板(pc)
  * @param @param doctorId
  * @param @return    设定文件 
  * @return List<PapersTemplates>    返回类型 
  * @throws
   */
  @RequestMapping(value="/pc/all")
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  public List<PapersTemplates> getPCDoctorTemplates(@RequestParam int doctorId){
    return papersTemplatesService.getDoctorTemplates(doctorId);
  }
}

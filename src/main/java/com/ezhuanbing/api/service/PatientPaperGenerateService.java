package com.ezhuanbing.api.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezhuanbing.api.conf.StringConfig.PushMessage;
import com.ezhuanbing.api.dao.mybatis.mapper.ContentClassTemplatesMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.OptionTemplatesMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PapersTemplatesMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientContentClassMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientFollowUpPlanDetailMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientFollowUpPlanMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientPaperMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientQuestionResultMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientQuestionsMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.QuestionsTemplatesMapper;
import com.ezhuanbing.api.dao.mybatis.model.ContentClassTemplates;
import com.ezhuanbing.api.dao.mybatis.model.ContentClassTemplatesCriteria;
import com.ezhuanbing.api.dao.mybatis.model.Doctor;
import com.ezhuanbing.api.dao.mybatis.model.OptionTemplates;
import com.ezhuanbing.api.dao.mybatis.model.OptionTemplatesCriteria;
import com.ezhuanbing.api.dao.mybatis.model.PapersTemplates;
import com.ezhuanbing.api.dao.mybatis.model.PapersTemplatesCriteria;
import com.ezhuanbing.api.dao.mybatis.model.QuestionsTemplates;
import com.ezhuanbing.api.dao.mybatis.model.QuestionsTemplatesCriteria;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlan;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlanDetail;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientContentClass;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientPaper;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientQuestionResult;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientQuestions;
import com.ezhuanbing.api.exception.HttpStatus400Exception;

@Service
public class PatientPaperGenerateService {
	
  private static final Logger log = LoggerFactory.getLogger(PatientPaperGenerateService.class);
  
  @Autowired
  PatientFollowUpPlanMapper patientFollowUpPlanMapper;
  @Autowired
  PatientFollowUpPlanDetailMapper patientFollowUpPlanDetailMapper;
  @Autowired
  PapersTemplatesMapper papersTemplatesMapper;
  @Autowired
  PatientPaperMapper patientPaperMapper;
  @Autowired
  PatientContentClassMapper patientContentClassMapper;
  @Autowired
  ContentClassTemplatesMapper contentClassTemplatesMapper;
  @Autowired
  QuestionsTemplatesMapper questionsTemplatesMapper;
  @Autowired
  PatientQuestionsMapper patientQuestionsMapper;
  @Autowired
  OptionTemplatesMapper optionTemplatesMapper;
  @Autowired
  PatientQuestionResultMapper patientQuestionResultMapper;
  @Autowired
  DoctorService doctorService;
  @Autowired
  PatientFollowUpPlanDetailService patientFollowUpPlanDetailService;
  @Autowired
  PushMsgService pushMsgService;

  /**
   * 
  * @Title: GenerateFirstPlan 
  * @Description: 生成首诊问卷
  * @param @param patientId
  * @param @param doctorId
  * @param @return
  * @param @throws Exception    设定文件 
  * @return boolean    返回类型 
  * @throws
   */
  @Transactional
  public boolean GenerateFirstPaper(int patientId, int doctorId) throws Exception{

    // 查找首诊模板
    PapersTemplates firstPaperTemplate = null;
    Doctor d = doctorService.getDoctor(doctorId);
    PapersTemplatesCriteria papersTemplatesCriteria = new PapersTemplatesCriteria();
    papersTemplatesCriteria.createCriteria()
      .andIsfirstfollowupEqualTo(1).andPaperdeptidEqualTo(d.getDoctorDepartmentId());
    List<PapersTemplates> isFirstTemplatesFromDoctor =
        papersTemplatesMapper.selectByExample(papersTemplatesCriteria);
    if(isFirstTemplatesFromDoctor.size() > 0){
      firstPaperTemplate = isFirstTemplatesFromDoctor.get(0);
    }else{
      PapersTemplatesCriteria _papersTemplatesCriteria = new PapersTemplatesCriteria();
      _papersTemplatesCriteria.createCriteria().andIsfirstfollowupEqualTo(1).andUsernameidEqualTo(0);
      List<PapersTemplates> isFirstTemplatesFromSystem 
        = papersTemplatesMapper.selectByExample(_papersTemplatesCriteria);
      if(isFirstTemplatesFromSystem.size() > 0){
        firstPaperTemplate = isFirstTemplatesFromSystem.get(0);
      }
    }
    
    if(firstPaperTemplate == null){
      throw new HttpStatus400Exception("添加医生", "", "首诊模板不存在，请确认后重新添加！", "");
    }
    
    // 添加计划
    PatientFollowUpPlan patientFollowUpPlan = PatientFollowUpPlan.builder()
        .patientId(patientId).doctorId(doctorId).planName("首诊计划")
        .planType(1).status("1").startDate(new Date()).build();
    patientFollowUpPlan = addPlan(patientFollowUpPlan);
    if(patientFollowUpPlan != null){
      // 添加计划详情
      PatientFollowUpPlanDetail patientFollowUpPlanDetail =
          PatientFollowUpPlanDetail.builder().planId(patientFollowUpPlan.getId())
              .templateId(null).questionnaireId(null)
              .planMark(firstPaperTemplate.getPapertile()).isActive(1).status("3")
              .planOrder(1).pushMessage(PushMessage.SF_FIRST_FOLLOWUP_TEXT)
              .isLastRecord(1)
              .sendTime(new Date()).build();
      patientFollowUpPlanDetail = addPlanDetail(patientFollowUpPlanDetail);
      if(patientFollowUpPlanDetail != null){
        // 添加问卷
        PatientPaper paper = addPlanDetailPaper(firstPaperTemplate,doctorId,patientId,patientFollowUpPlanDetail.getId());
        if (paper != null){
          // 更新计划详情
          PatientFollowUpPlanDetail updatePatientFollowUpPlanDetail =
              PatientFollowUpPlanDetail.builder().id(patientFollowUpPlanDetail.getId())
              .questionnaireId(paper.getId()).templateId(firstPaperTemplate.getId()).build();
          int result = patientFollowUpPlanDetailMapper.updateByPrimaryKeySelective(updatePatientFollowUpPlanDetail);
          if(result > 0){
            // 推送信息
            pushMsgService.sendFirstFellowUp(patientId, PushMessage.SF_FIRST_FOLLOWUP_TEXT);
            return true;
          }
        }
      }
    }
    
    return false;
  }
  
  /**
   * 
  * @Title: GenerateAccessoryPlan 
  * @Description: 生成附加问卷
  * @param @param patientId
  * @param @param doctorId
  * @param @param detailId
  * @param @param templateId
  * @param @return    设定文件 
  * @return boolean    返回类型 
  * @throws
   */
  public boolean GeneratePaper(int patientId, int doctorId, int detailId, int templateId){
    log.debug("进入到了GeneratePaper中，准备推送！");
    PapersTemplates papersTemplates = papersTemplatesMapper.selectByPrimaryKey(templateId);
    PatientPaper paper = addPlanDetailPaper(papersTemplates,doctorId,patientId,detailId);
    if (paper != null){
      // 更新planMask
//    	Integer planOrder = patientFollowUpPlanDetailMapper.selectByPrimaryKey(detailId).getPlanOrder();
    	Map<String, Object> o = patientFollowUpPlanDetailMapper.getPlanNameAndPlanOrder(detailId);
      PatientFollowUpPlanDetail patientFollowUpPlanDetail =
          PatientFollowUpPlanDetail.builder().id(detailId)
          .planMark(o.get("planName") + "（第" + o.get("planOrder") + "次）")
          .questionnaireId(paper.getId())
          .build();
      int result = patientFollowUpPlanDetailMapper
          .updateByPrimaryKeySelective(patientFollowUpPlanDetail);
      if(result <= 0){
        // 重试，失败不再保存
        patientFollowUpPlanDetailMapper.updateByPrimaryKeySelective(patientFollowUpPlanDetail);
      }
      
//    	try {
//    		log.debug("准备修改Active字段！");
//			patientFollowUpPlanDetailService.updateBySelective(PatientFollowUpPlanDetail.builder().id(detailId).isActive(1).build());
//			log.debug("完成Active字段修改！");
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			e.printStackTrace();
//		}
      return true;
    }
    return false;
  }
  
//  public boolean GeneratePaper(int patientId, int doctorId, int templateId){
//    // 查找首诊模板
//    PapersTemplatesCriteria papersTemplatesCriteria = new PapersTemplatesCriteria();
//    papersTemplatesCriteria.createCriteria().andIdEqualTo(templateId).andUsernameidEqualTo(doctorId);
//    List<PapersTemplates> templates =
//        papersTemplatesMapper.selectByExample(papersTemplatesCriteria);
//    
//    if(templates.size() > 0){
//      // 添加计划
//      PatientFollowUpPlan patientFollowUpPlan = PatientFollowUpPlan.builder()
//          .patientId(patientId).doctorId(doctorId).planName(templates.get(0).getPapertile())
//          .planType(0).status("1").startDate(new Date()).endDate(new Date()).build();
//      patientFollowUpPlan = addPlan(patientFollowUpPlan);
//      if(patientFollowUpPlan != null){
//        // 添加计划详情
//        PatientFollowUpPlanDetail patientFollowUpPlanDetail =
//            PatientFollowUpPlanDetail.builder().planId(patientFollowUpPlan.getId())
//                .templateId(null).questionnaireId(null)
//                .planMark(templates.get(0).getPapertile()).isActive(0).status("0")
//                .planOrder(1).pushMessage(PushMessage.SF_FIRST_FOLLOWUP_TEXT).build();
//        patientFollowUpPlanDetail = addPlanDetail(patientFollowUpPlanDetail);
//        if(patientFollowUpPlanDetail != null){
//          // 添加问卷
//          PatientPaper paper = addPlanDetailPaper(templates.get(0),doctorId,patientId,patientFollowUpPlanDetail.getId());
//          if (paper != null){
//            return true;
//          }
//        }
//      }
//    }
//    
//    return false;
//  }
  
  /**
   * 
  * @Title: addPlan 
  * @Description: 添加随访计划
  * @param @param patientId
  * @param @param doctorId
  * @param @param isFirstPlan
  * @param @return    设定文件 
  * @return PatientFollowUpPlan    返回类型 
  * @throws
   */
  private PatientFollowUpPlan addPlan(PatientFollowUpPlan patientFollowUpPlan){
    int result = patientFollowUpPlanMapper.insertUseGeneratedKeys(patientFollowUpPlan);
    if(result > 0){
      return patientFollowUpPlan;
    }else{
      return null;
    }
  }
  
  /**
   * 
  * @Title: addPlanDetail 
  * @Description: 添加随访详情
  * @param @param patientFollowUpPlanDetail
  * @param @return    设定文件 
  * @return PatientFollowUpPlanDetail    返回类型 
  * @throws
   */
  private PatientFollowUpPlanDetail addPlanDetail(PatientFollowUpPlanDetail patientFollowUpPlanDetail){
    int result = patientFollowUpPlanDetailMapper.insertUseGeneratedKeys(patientFollowUpPlanDetail);
    if(result > 0){
      return patientFollowUpPlanDetail;
    }else{
      return null; 
    }
  }
  
  /**
   * 
  * @Title: addPlanDetailPaper 
  * @Description: 添加问卷
  * @param @param papersTemplate
  * @param @param doctor
  * @param @param patientId
  * @param @param planDetaiId
  * @param @return    设定文件 
  * @return PatientPaper    返回类型 
  * @throws
   */
  private PatientPaper addPlanDetailPaper(PapersTemplates papersTemplate, int doctorId, int patientId, int planDetaiId){
    log.debug("进入执行添加问卷操作的方法！！！");
    Doctor doctor = doctorService.getDoctor(doctorId);
    if(doctor != null){
      // 添加paper
      PatientPaper patientPaper = PatientPaper.builder().patientId(patientId)
          .patientPaperDateTime(new Date())
          .paperDoctor(doctor.getDoctorName()).paperDoctorId(doctor.getDoctorId())
          .paperDept(doctor.getDoctorDepartment()).paperDeptId(doctor.getDoctorDepartmentId())
          .paperTitle(papersTemplate.getPapertile()).paperSummary(papersTemplate.getPapersummary())
          .paperStatus(1).planDetailId(planDetaiId).build();
      log.debug("执行添加问卷操作方法之前！！！");
      int result = patientPaperMapper.insertUseGeneratedKeys(patientPaper);
      log.debug("执行添加问卷操作方法之后！！！result="+result);
      if(result > 0){
          ContentClassTemplatesCriteria templateContentCls = new ContentClassTemplatesCriteria();
          templateContentCls.createCriteria().andPapersidEqualTo(papersTemplate.getId());
          List<ContentClassTemplates> contentClassTemplates =
              contentClassTemplatesMapper.selectByExample(templateContentCls);
          
          if(addPaperContentClass(contentClassTemplates,patientPaper.getId())){
            return patientPaper;
          }
      }
    }
    return null;
  }
  
  /**
   * 
  * @Title: addPaperContentClass 
  * @Description: 添加问卷问题分类
  * @param @param contentClassTemplates
  * @param @param paperId
  * @param @return    设定文件 
  * @return boolean    返回类型 
  * @throws
   */
  private boolean addPaperContentClass(List<ContentClassTemplates> contentClassTemplates, int paperId){
    
    Map<Integer,Integer> tmpIdAndClassId = new HashMap<Integer,Integer>();
    for (ContentClassTemplates contentClsTmp : contentClassTemplates) {
      
      int parentPatientContentClassId = 0;
      if(contentClsTmp.getClasspid() != null && contentClsTmp.getClasspid() > 0){
        Integer classId = tmpIdAndClassId.get(contentClsTmp.getClasspid());
        if(classId != null && classId >0){
          // 存在父节点
          parentPatientContentClassId = classId;
        }else{
          // 获取父节点
          ContentClassTemplates parentContentClassTemplates = 
              contentClassTemplatesMapper.selectByPrimaryKey(contentClsTmp.getClasspid());
          
          PatientContentClass parentPatientContentClass =
              PatientContentClass.builder().patientPaperId(paperId)
                  .contentClassTemplatesId(parentContentClassTemplates.getId())
                  .className(parentContentClassTemplates.getClassname())
                  .classSort(1).classStatus(1)
                  .leaf(parentContentClassTemplates.getLeaf())
                  .pid(0).build();
          int result = patientContentClassMapper.insertUseGeneratedKeys(parentPatientContentClass);
          if(result > 0){
            parentPatientContentClassId = parentPatientContentClass.getId();
            tmpIdAndClassId.put(contentClsTmp.getClasspid(), parentPatientContentClass.getId());
          } 
        }
      }
      
      PatientContentClass patientContentClass = null;
      if(parentPatientContentClassId > 0){  
        patientContentClass =
            PatientContentClass.builder().patientPaperId(paperId)
                .contentClassTemplatesId(contentClsTmp.getId())
                .className(contentClsTmp.getClassname()).classSort(1).classStatus(1)
                .leaf(contentClsTmp.getLeaf()).pid(parentPatientContentClassId).build();
      }else{
        patientContentClass =
            PatientContentClass.builder().patientPaperId(paperId)
                .contentClassTemplatesId(contentClsTmp.getId())
                .className(contentClsTmp.getClassname()).classSort(1).classStatus(1)
                .leaf(contentClsTmp.getLeaf()).pid(0).build();
      }
      int result = patientContentClassMapper.insertUseGeneratedKeys(patientContentClass);
      if(result > 0){
        tmpIdAndClassId.put(contentClsTmp.getId(), patientContentClass.getId());
        // 添加分类问题
        if(addContentClassQuest(contentClsTmp.getId(),patientContentClass.getId())){
          continue;
        }else{
          // 添加分类问题失败
          return false;
        }
      }else{
        // 添加分类失败
        return false; 
      }
    }
    
    return true;
  }
  
  /**
   * 
  * @Title: addContentClassQuest 
  * @Description: 添加分类问题
  * @param @param contentTemplateClassId
  * @param @param patientContentClassId
  * @param @return    设定文件 
  * @return boolean    返回类型 
  * @throws
   */
  private boolean addContentClassQuest(int contentTemplateClassId,int patientContentClassId){
    QuestionsTemplatesCriteria questionsTemplatesCriteria =
        new QuestionsTemplatesCriteria();
    questionsTemplatesCriteria.createCriteria()
        .andContentclassicidEqualTo(contentTemplateClassId);
    List<QuestionsTemplates> questionsTemplates =
        questionsTemplatesMapper.selectByExample(questionsTemplatesCriteria);
    for (QuestionsTemplates qt : questionsTemplates) {
      PatientQuestions pq = PatientQuestions.builder()
          .patientContentClassId(patientContentClassId).qstType(qt.getQsttype())
          .qstTitle(qt.getQsttitle()).qstPicture(qt.getQstpicture()).qstStatus(1)
          .qstAnswer(null).qstAnswerDateTime(null).questionsTemplatesId(null).build();
      int result = patientQuestionsMapper.insertUseGeneratedKeys(pq);
      if(result > 0){
        // add 分类问题答案选项
        addContentClassQuestOption(qt.getId(), pq.getId());
      }else{
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * 
  * @Title: addContentClassQuestOption 
  * @Description: 添加问题选项
  * @param @param questionsTemplatesId
  * @param @param patientQuestionsId
  * @param @return    设定文件 
  * @return boolean    返回类型 
  * @throws
   */
  private boolean addContentClassQuestOption(int questionsTemplatesId, int patientQuestionsId){
    OptionTemplatesCriteria optionTemplatesCriteria = new OptionTemplatesCriteria();
    optionTemplatesCriteria.createCriteria()
        .andQuestionstemplatesidEqualTo(questionsTemplatesId);
    List<OptionTemplates> optionTemplates =
        optionTemplatesMapper.selectByExample(optionTemplatesCriteria);
    for (OptionTemplates ot : optionTemplates) {
      PatientQuestionResult pqr = PatientQuestionResult.builder()
          .patientQuestionsId(patientQuestionsId).optionTemplatesId(ot.getId())
          .optionNumber(ot.getOptionnumber()).optionContent(ot.getOptioncontent())
          .optionType(ot.getOptiontype()).optionPicture(ot.getOptionpicture())
          .optientResult(null).optientStatus(0).optionOther(null).build();
      int result = patientQuestionResultMapper.insert(pqr);
      if(result <= 0){
        return false;
      }
    }
    return true;
  }
}

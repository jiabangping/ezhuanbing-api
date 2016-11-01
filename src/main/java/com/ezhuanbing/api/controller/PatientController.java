package com.ezhuanbing.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.mapper.GroupMapper;
import com.ezhuanbing.api.dao.mybatis.model.Doctor;
import com.ezhuanbing.api.dao.mybatis.model.DoctorPatient;
import com.ezhuanbing.api.dao.mybatis.model.Group;
import com.ezhuanbing.api.dao.mybatis.model.PatientCaseInfo;
import com.ezhuanbing.api.dao.mybatis.model.PatientCaseInfoResult;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlan;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlanDetail;
import com.ezhuanbing.api.dao.mybatis.model.patient.Patient;
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.exception.HttpStatus403Exception;
import com.ezhuanbing.api.exception.HttpStatus404Exception;
import com.ezhuanbing.api.model.DoctorResponseToken;
import com.ezhuanbing.api.service.DoctorService;
import com.ezhuanbing.api.service.ImageService;
import com.ezhuanbing.api.service.PatientCaseInfoResultService;
import com.ezhuanbing.api.service.PatientCaseInfoService;
import com.ezhuanbing.api.service.PatientFollowUpPlanDetailService;
import com.ezhuanbing.api.service.PatientFollowUpPlanService;
import com.ezhuanbing.api.service.PatientService;
import com.ezhuanbing.api.tools.ConstantClass;
import com.ezhuanbing.api.tools.ValidationTools;
import com.ezhuanbing.api.vo.PatientCaseInfoResp;
import com.ezhuanbing.api.vo.PatientResp;
import com.ezhuanbing.api.vo.SyncPatient;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

  public PatientController() {
    System.out.println("inint-PatientController(),i=" + i);

  }

  int i = 0;

  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  PatientService patientService;

  @Autowired
  ImageService imageService;

  @Autowired
  PatientCaseInfoService patientCaseInfoService;

  @Autowired
  DoctorService doctorService;

  @Autowired
  PatientCaseInfoResultService patientCaseInfoResultService;

  @Autowired
  GroupMapper groupMapper;
  
  @Autowired
  PatientFollowUpPlanService patientFollowUpPlanService;
  
  @Autowired
  PatientFollowUpPlanDetailService patientFollowUpPlanDetailService;

  /**
   * 获取未完成随访患者列表
   */
  @RequestMapping(value = "/getPatients", method = RequestMethod.GET)
  public PageInfo<Patient> getUnFinishFollowUpPatients(@RequestParam(value = "pageIndex",
      required = false, defaultValue = "1") Integer pageIndex, @RequestParam(value = "pageSize",
      required = false) Integer pageSize) throws Exception {

    try {
      return new PageInfo<Patient>(patientService.getList(pageIndex, pageSize));
    } catch (HttpStatus403Exception e) {
      log.error(e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 获取指定医生的患者列表
   * 
   * @throws HttpStatus403Exception
   */
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @RequestMapping(value = "/getDoctorPatientList", method = RequestMethod.GET)
  public PageInfo<PatientResp> getDoctorPatientList(
      @RequestParam(value = "name", required = false) String name, @RequestParam(value = "groupId",
          required = false) Integer groupId, @RequestParam(value = "followupCount",
          required = false) String followupCount, @RequestParam(value = "pageIndex",
          required = false, defaultValue = "1") Integer pageIndex, @RequestParam(
          value = "pageSize", required = false) Integer pageSize, HttpServletRequest request)
      throws HttpStatus403Exception {

    DoctorResponseToken doctorResponseToken =
        (DoctorResponseToken) request.getAttribute("doctorResponseToken");
    Integer doctorid = doctorResponseToken.getDoctorID();
    // Integer doctorid = 11761;
    // Integer doctorid = 14650;
    try {
      if (name != null) {
        name = URLDecoder.decode(name, "utf-8");
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    try {
      return new PageInfo<PatientResp>(patientService.getDoctorPatientList(doctorid, name, groupId,
          followupCount, pageIndex, pageSize));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 根据医生ID获得医生的人群分类。如果该医生没有任何人群分类，则新建一条数据。
   * 
   * @return
   */
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
  @RequestMapping(value = "/getGroupList", method = RequestMethod.GET)
  public List<Group> selectGroupsByDoctorId(HttpServletRequest request) {
    DoctorResponseToken doctorResponseToken =
        (DoctorResponseToken) request.getAttribute("doctorResponseToken");
    Integer doctorid = doctorResponseToken.getDoctorID();
    // Integer doctorid = 11761;

    List<Group> t = null;
    try {
      t = patientService.selectGroupsByDoctorId(doctorid, 1);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw e;
    }
    if (t.size() == 0) {
      try {
        patientService.addGroupById(doctorid, ConstantClass.DEFAULT_DOCTOR_GROUP, 1);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        throw e;
      }
    }
    try {
      return patientService.selectGroupsByDoctorId(doctorid, 0);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 根据医生ID获得医生的人群分类。如果该医生没有任何人群分类，则新建一条数据。
   * 
   * @return
   */
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
  @RequestMapping(value = "/getGroupListPageInfo", method = RequestMethod.GET)
  public PageInfo<Group> selectGroupsByDoctorIdPageInfo(@RequestParam(value = "pageIndex",
      required = false, defaultValue = "1") Integer pageIndex, @RequestParam(value = "pageSize",
      required = false) Integer pageSize, HttpServletRequest request) throws HttpStatus403Exception {
    DoctorResponseToken doctorResponseToken =
        (DoctorResponseToken) request.getAttribute("doctorResponseToken");
    Integer doctorid = doctorResponseToken.getDoctorID();
    // Integer doctorid = 11761;
    List<Group> t = null;
    try {
      t = patientService.selectGroupsByDoctorId(doctorid, 1);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw e;
    }
    if (t.size() == 0) {
      try {
        patientService.addGroupById(doctorid, ConstantClass.DEFAULT_DOCTOR_GROUP, 1);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        throw e;
      }
    }
    try {
      return new PageInfo<Group>(patientService.selectGroupsByDoctorIdPageInfo(doctorid, 0,
          pageIndex, pageSize));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 新建一个人群分类
   */
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @RequestMapping(value = "/addGroup", method = RequestMethod.GET)
  public int addGroupById(@RequestParam(value = "name", required = true) String name,
      HttpServletRequest request) {
    DoctorResponseToken doctorResponseToken =
        (DoctorResponseToken) request.getAttribute("doctorResponseToken");
    Integer doctorid = doctorResponseToken.getDoctorID();
    // Integer doctorid = 11761;
    try {
      name = URLDecoder.decode(name, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    try {
      return patientService.addGroupById(doctorid, name, 0);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 修改一个人群分类的名称
   */
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @RequestMapping(value = "/updateGroupName", method = RequestMethod.GET)
  public int updateGroupNameById(@RequestParam(value = "name", required = true) String name,
      @RequestParam(value = "id", required = true) int id, HttpServletRequest request) {
    try {
      name = URLDecoder.decode(name, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    try {
      return patientService.updateGroupNameById(id, name);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 删除一个人群分类，并将其中的人群都变为默认分类
   */
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @RequestMapping(value = "/delGroup", method = RequestMethod.GET)
  public int delGroupById(@RequestParam(value = "id", required = true) int id,
      HttpServletRequest request) {
    DoctorResponseToken doctorResponseToken =
        (DoctorResponseToken) request.getAttribute("doctorResponseToken");
    Integer doctorid = doctorResponseToken.getDoctorID();
    // Integer doctorid = 11761;
    try {
      return patientService.delGroupById(id, doctorid);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 根据医生id和病人id批量修改病人的人群分类
   * 
   * @return
   */
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @RequestMapping(value = "/reGroupBatch", method = RequestMethod.GET)
  public int reGroupBatch(@RequestParam(value = "pidStr", required = true) String pidStr,
      @RequestParam(value = "gid", required = true) String gid, HttpServletRequest request) {
    if (pidStr != "") {
      DoctorResponseToken doctorResponseToken =
          (DoctorResponseToken) request.getAttribute("doctorResponseToken");
      Integer doctorid = doctorResponseToken.getDoctorID();
      // Integer doctorid = 11761;
      String[] pids = pidStr.split(",");
      int[] p = new int[pids.length];
      for (int i = 0; i < pids.length; i++) {
        p[i] = Integer.valueOf(pids[i]);
      }
      if (ValidationTools.isInt(gid)) {
        try {
          return patientService.reGroupBatch(p, doctorid, Integer.valueOf(gid));
        } catch (Exception e) {
          log.error(e.getMessage(), e);
          throw e;
        }
      } else {
        return 0;
      }
    } else {
      return 0;
    }
  }

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public void test() {
    System.out.println("i=" + (i++));
  }

  // ///////////////////////////////////app用//////////////////////////////////////////////////////////
  /**
   * 
   * @Title: getPatientById
   * @Description: 根据患者Id获取患者信息
   * @param patientId
   * @return Patient 返回类型
   * @throws
   */
  @RequestMapping(value = "/{patientId}", method = RequestMethod.GET)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public Patient getPatientById(@PathVariable("patientId") int patientId) throws Exception {

    if (patientId <= 0)
      throw new HttpStatus400Exception("患者信息查新", "", "参数错误【patientId】应该大于0", "");

    Patient p = patientService.getPatientById(patientId);
    if (p != null && p.getId() > 0) {
      return p;
    } else {
      throw new HttpStatus404Exception("患者信息查新", "", "没有找到患者信息，请确认后重新查找。", "");
    }
  }

  // ///////////////////////////////////app用//////////////////////////////////////////////////////////
  /**
   * 
   * @Title: getPatientById
   * @Description: 根据患者Id获取患者信息
   * @param patientId
   * @return Patient 返回类型
   * @throws
   */
  @RequestMapping(value = "patientInfoForDoctor/{patientId}", method = RequestMethod.GET)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  public Patient patientInfoForDoctor(@PathVariable("patientId") int patientId) throws Exception {

    if (patientId <= 0)
      throw new HttpStatus400Exception("患者信息查新", "", "参数错误【patientId】应该大于0", "");

    Patient p = patientService.getPatientById(patientId);
    if (p != null && p.getId() > 0) {
      return p;
    } else {
      throw new HttpStatus404Exception("患者信息查新", "", "没有找到患者信息，请确认后重新查找。", "");
    }
  }

  /**
   * 
   * @Title: getPatientByLoginName
   * @Description: 根据患者名称获取患者信息
   * @param @param loginName
   * @param @return
   * @param @throws Exception 设定文件
   * @return Patient 返回类型
   * @throws
   */
  @RequestMapping(value = "/login/{loginName}", method = RequestMethod.GET)
  public Patient getPatientByLoginName(@PathVariable String loginName) throws Exception {
    if (loginName == null || loginName.isEmpty())
      throw new HttpStatus400Exception("患者信息查新", "", "参数错误【loginName】不存在", "");

    Patient p = patientService.getPatientByLoginName(loginName);
    if (p != null && p.getId() > 0) {
      return p;
    } else {
      throw new HttpStatus404Exception("患者信息查新", "", "没有找到患者信息，请确认后重新查找。", "");
    }
  }

  /**
   * 获得病人的基本信息
   * 
   * @param loginName
   * @return
   * @throws Exception
   */
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @RequestMapping(value = "/getPatientInfo/{loginName}", method = RequestMethod.GET)
  public Map<String, Object> getPatientInfo(@PathVariable String loginName,
      HttpServletRequest request) throws Exception {
    if (loginName == null || loginName.isEmpty())
      throw new HttpStatus400Exception("患者信息查询", "", "参数错误【loginName】不存在", "");
    Map<String, Object> pi = patientService.getPatientInfo(loginName);
    if (pi != null) {
      return pi;
    } else {
      throw new HttpStatus404Exception("患者信息查询", "", "没有找到患者信息，请确认后重新查找。", "");
    }
  }

  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @RequestMapping(value = "/getPatientNumsByGroupId/{groupId}", method = RequestMethod.GET)
  public int getPatientNumsByGroupId(@PathVariable Integer groupId) throws Exception {
    if (groupId > 0) {
      return patientService.getPatientNumsByGroupId(groupId);
    } else {
      return 0;
    }
  }

  /**
   * 获得病人的病史信息
   * 
   * @param loginName
   * @return
   * @throws Exception
   */
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @RequestMapping(value = "/getPatientExtInfo/{loginName}", method = RequestMethod.GET)
  public List<Map<String, Object>> getPatientExtInfo(@PathVariable String loginName,
      HttpServletRequest request) throws Exception {
    if (loginName == null || loginName.isEmpty())
      throw new HttpStatus400Exception("患者信息查询", "", "参数错误【loginName】不存在", "");
    List<Map<String, Object>> pi = patientService.getPatientExtInfoByLoginName(loginName);
    if (pi != null) {
      return pi;
    } else {
      throw new HttpStatus404Exception("患者信息查询", "", "没有找到患者信息，请确认后重新查找。", "");
    }
  }

  /**
   * 
   * @Title: uploadHeadImage
   * @Description: 患者头像上传
   * @param 患者ID
   * @param 头像
   * @param
   * @return
   * @throws
   */
  @RequestMapping(value = "/{patientId}/head", method = RequestMethod.POST)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public void uploadHeadImage(@PathVariable("patientId") int patientId,
      @RequestParam("file") MultipartFile file) throws Exception {
    try {

      // 上传图片
      imageService.uploadPatientHeader(patientId, file);
      // 更新url
      patientService.updatePatientHeadPhoto(patientId, patientId + ".jpg");

    } catch (Exception e) {
      throw new HttpStatus400Exception("图片上传", "", "图片上传失败，请稍后重试！", "");
    }
  }

  // //////////////////////////////////////app用//////////////////////////////////////////////////////////
  /**
   * 
   * @Title: addDoctor
   * @Description: 给患者添加医生
   * @param @param patientId
   * @param @param doctorId
   * @param @throws Exception 设定文件
   * @return void 返回类型
   * @throws
   */
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  @RequestMapping(value = "/{patientId}/doctor/{doctorId}", method = RequestMethod.POST)
  public void addDoctor(@PathVariable int patientId, @PathVariable int doctorId) throws Exception {

    if (patientId < 0)
      throw new HttpStatus400Exception("添加医生", "", "参数错误【patientId】应该大于0", "");

    if (doctorId < 0)
      throw new HttpStatus400Exception("添加医生", "", "参数错误【doctorId】应该大于0", "");

    try{
      patientService.addDoctor(patientId, doctorId);
    }catch(HttpStatus400Exception ex){
      throw ex;
    }catch(Exception e){
      throw new HttpStatus400Exception("添加医生", "", "患者添加医生失败，请稍后重试！", "");
    }
  }

  /**
   * 
   * @Title: getPatientDoctor
   * @Description: 获取患者的医生列表
   * @param @param patientId
   * @param @return 设定文件
   * @return List<DoctorPatient> 返回类型
   * @throws
   */
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  @RequestMapping(value = "/{patientId}/doctors", method = RequestMethod.GET)
  public List<DoctorPatient> getPatientDoctor(@PathVariable int patientId) {
    return patientService.getPatientDoctor(patientId);
  }

  // /**
  // *
  // * @Title: updatePatientPhoneIMEIInfo
  // * @Description: 更新患者IMEI
  // * @param @param patientId
  // * @param @param patient
  // * @param @throws Exception 设定文件
  // * @return void 返回类型
  // * @throws
  // */
  // @ApiControl(apiAccessType = APIAccessType.PRIVATE_API,apiType=APIType.PATIENT)
  // @RequestMapping(value="/{patientId}/phoneImei",method = RequestMethod.PATCH)
  // public void updatePatientPhoneIMEIInfo(@PathVariable int patientId,
  // @RequestBody Patient patient) throws Exception{
  //
  // if(patientId < 0)
  // throw new HttpStatus400Exception("更新患者手机串码","","参数错误【patientId】应该大于0","");
  //
  // if(!patientService.updatePatientPhoneIEMI(patientId, patient))
  // throw new HttpStatus400Exception("更新患者手机串码","","更新患者手机串码失败，请稍后重试！","");
  // }

  // ////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * @throws Exception
   * 
   * @Title: syncPatientInfo
   * @Description: 同步患者信息
   * @param 设定文件
   * @return void 返回类型
   * @throws
   */
  @RequestMapping(value = "/sync", method = RequestMethod.POST)
  public void syncPatientInfo(@RequestBody SyncPatient syncPatient) throws Exception {

    // Map<String, Object> cardInfo = new HashMap<String, Object>();
    // String idCard = syncPatient.getIdCard();
    // if(idCard != null && !idCard.isEmpty()){
    // if(idCard.length() == 15){
    // cardInfo = CardUtil.getCarInfo(idCard);
    // }else if(idCard.length() == 15){
    // cardInfo = CardUtil.getCarInfo15W(idCard);
    // }
    // }

    Patient patient =
        Patient.builder().birthday(syncPatient.getBirthday())
            .districtId(syncPatient.getDistrictId()).idCard(syncPatient.getIdCard())
            .loginName(syncPatient.getLoginName()).phoneNumber(syncPatient.getPhoneNum())
            .sex(syncPatient.getSex()).name(syncPatient.getName()).sysId(syncPatient.getSysId())
            .build();

    if (!patientService.syncPatient(patient))
      throw new HttpStatus400Exception("患者信息同步", "", "患者信息同步失败，请稍后重试！", "");
  }


  /**
   * 获取患者病历信息
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @RequestMapping(value = "/getPatientCaseInfo/{patientId}", method = RequestMethod.GET)
  public PatientCaseInfoResp getPatientCaseInfo(@PathVariable int patientId, @RequestParam(
      value = "planId", required = true) Integer planId, HttpServletRequest request)
      throws Exception {

    try {
      DoctorResponseToken token = (DoctorResponseToken) request.getAttribute("doctorResponseToken");
      int doctorId = token.getDoctorID();
      Doctor doctor = doctorService.getDoctor(doctorId);
      int deptId = 0;
      PatientCaseInfo patientCaseInfo = new PatientCaseInfo();
      if (doctor != null) {
        deptId = doctor.getDoctorDepartmentId();
        patientCaseInfo.setDepartmentId(deptId);
      } else {
        patientCaseInfo.setDepartmentId(deptId);
      }
      patientCaseInfo.setPatientid(patientId);
      PatientCaseInfoResp patientCaseInfoResp = new PatientCaseInfoResp();
      List<PatientCaseInfoResp> partients =
          patientCaseInfoService.getPatientCaseInfoMap(patientCaseInfo);


      if (partients.size() <= 0) {
        patientCaseInfo.setDepartmentId(null);
        partients = patientCaseInfoService.getPatientCaseInfoMap(patientCaseInfo);
      }
      for (int i = 0; i < partients.size(); i++) {
        patientCaseInfoResp = partients.get(i);
        patientCaseInfoResp.setDepartmentId(deptId);
        patientCaseInfoResp.setDoctorid(doctorId);
        patientCaseInfoResp.setPlanId(planId);
      }

      List<PatientCaseInfoResult> patientCaseInfoResults =
          patientCaseInfoResultService.getPatientCaseInfoResultByPlanId(planId);
      if (patientCaseInfoResults.size() > 0) {
        PatientCaseInfoResult caseInfoResult = patientCaseInfoResults.get(0);
        patientCaseInfoResp.setOpinionresult(caseInfoResult.getOpinionresult());
        patientCaseInfoResp.setDiagnosis(caseInfoResult.getDiagnosis());
      }
      return patientCaseInfoResp;
    } catch (HttpStatus403Exception e) {
      log.error(e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 更新患者病历信息
   */
  @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @RequestMapping(value = "/updatePatientCaseInfo", method = RequestMethod.PATCH)
  public int getPatientCaseInfo(@RequestBody PatientCaseInfoResp patientCaseInfoResp)
      throws Exception {

    try {
      PatientCaseInfo patientCaseInfo = new PatientCaseInfo();
      PatientCaseInfoResult patientCaseInfoResult = new PatientCaseInfoResult();

      patientCaseInfo.setAuxiliaryexamination(patientCaseInfoResp.getAuxiliaryexamination());
      patientCaseInfo.setComplained(patientCaseInfoResp.getComplained());
      patientCaseInfo.setPasthistory(patientCaseInfoResp.getPasthistory());
      patientCaseInfo.setDoctorid(patientCaseInfoResp.getDoctorid());
      patientCaseInfo.setDepartmentId(patientCaseInfoResp.getDepartmentId());
      patientCaseInfo.setPatientid(patientCaseInfoResp.getPatientid());
      patientCaseInfo.setPhysical(patientCaseInfoResp.getPhysical());
      patientCaseInfo.setPresentillness(patientCaseInfoResp.getPresentillness());

      if (patientCaseInfoResp.getId() == 0) {
        patientCaseInfo.setId(null);
        patientCaseInfoService.insertPatientCaseInfo(patientCaseInfo);
      } else {
        patientCaseInfo.setId(patientCaseInfoResp.getId());
        patientCaseInfoService.updatePatientCaseInfo(patientCaseInfo);
      }

      patientCaseInfoResult.setDiagnosis(patientCaseInfoResp.getDiagnosis());
      patientCaseInfoResult.setDoctorid(patientCaseInfoResp.getDoctorid());
      patientCaseInfoResult.setOpinionresult(patientCaseInfoResp.getOpinionresult());
      patientCaseInfoResult.setPlandetailid(patientCaseInfoResp.getPlanId());

      List<PatientCaseInfoResult> patientCaseInfoResults =
          patientCaseInfoResultService.getPatientCaseInfoResultByPlanId(patientCaseInfoResp
              .getPlanId());

      if (patientCaseInfoResults.size() > 0) {
        PatientCaseInfoResult caseInfoResult = patientCaseInfoResults.get(0);
        patientCaseInfoResult.setId(caseInfoResult.getId());
        patientCaseInfoResultService.updatePatientCaseInfoResult(patientCaseInfoResult);
      } else {
        patientCaseInfoResultService.insertPatientCaseInfoResult(patientCaseInfoResult);
      }

      return 0;
    } catch (HttpStatus403Exception e) {
      log.error(e.getMessage(), e);
      throw e;
    }
  }
  
  /**
   * 
  * @Title: getPatientDetailPageUrl 
  * @Description: 获取患者详情页面链接地址
  * @param @param planDetailId
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  //@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
  @RequestMapping(value = "/detail/url", method = RequestMethod.GET)
  public Map<String,String> getPatientDetailPageUrl(@RequestParam int planDetailId){
    
    String url = "";
    PatientFollowUpPlanDetail planDetail = 
        patientFollowUpPlanDetailService.getPatientFollowUpPlanDetailById(planDetailId);
    PatientFollowUpPlan plan = 
        patientFollowUpPlanService.getPatientFollowUpPlanById(planDetail.getPlanId());
    if(planDetail != null && plan != null){
      url = "/ezhuanbing_web/html/followup_detail_papers/index.html?planId="+plan.getId()
                +"&doctorId="+plan.getDoctorId()
                +"&patientId="+plan.getPatientId()
                +"&isLastRecord="+planDetail.getIsLastRecord()
                +"&planDetailId="+planDetailId
                +"&status="+planDetail.getStatus();
    }
    
    Map<String,String> patientDetailUrl = new HashMap<String,String>();
    patientDetailUrl.put("url", url);
    return patientDetailUrl;
  }
  
  
  /**将wxOpendId与loginName绑定*/
  @RequestMapping(value = "/wxAccountBind", method = RequestMethod.GET)
  public @ResponseBody JSONObject bindWxAccount(@RequestParam(name="wxOpenId",required=true) String wxOpenId,@RequestParam(name="loginName",required=true) String loginName) throws Exception  {
    if(wxOpenId == null || wxOpenId.trim().equals("")) {
      throw new HttpStatus400Exception("参数wxOpenId错误","","","");
    }
    if(loginName == null || loginName.trim().equals("")) {
      throw new HttpStatus400Exception("参数loginName错误","","","");
    }
    
    try {
      return patientService.bindWxAccount(wxOpenId,loginName);
    }catch(Exception e) {
      JSONObject json = new JSONObject();
      json.put("result", "fail");
      return json;
    }
    
  }
  
  /**check openId是否绑定*/
  @RequestMapping(value = "/checkWxBind/{wxOpenId}", method = RequestMethod.GET)
  public @ResponseBody JSONObject checkWxBindUrl(@PathVariable("wxOpenId") String wxOpenId) throws Exception  {
    if(wxOpenId == null || wxOpenId.trim().equals("")) {
      throw new HttpStatus400Exception("参数wxOpenId错误","","","");
    }
      try {
        return  patientService.checkWxBindUrl(wxOpenId);
      }catch(Exception e) {
        JSONObject json = new JSONObject();
        json.put("result", "fail");
        return json;
      }
  }
  
  /**
   * 微信端关注医生
   * @Title: addDoctor
   * @Description: 给患者添加医生
   * @param @param patientId
   * @param @param doctorId
   * @param @throws Exception 设定文件
   * @return void 返回类型
   * @throws
   */
  @RequestMapping(value = "/wx/{loginName}/doctor/{doctorId}", method = RequestMethod.POST)
  public JSONObject addDoctor(@PathVariable String loginName, @PathVariable int doctorId) throws Exception {
    try{
      Patient p = patientService.getPatientByLoginName(loginName);
      if(p == null) {
        JSONObject json = new JSONObject();
        json.put("result", "fail");
        return json;
      }
      JSONObject result = patientService.addDoctorForWeixin(p.getId(), doctorId);
      return result;
    }catch(Exception e){
      JSONObject json = new JSONObject();
      json.put("result", "fail");
      return json;
    }
  }
  
  /**
   * 微信端check患者是否关注医生
   * @Title: addDoctor
   * @Description: 给患者添加医生
   * @param @param patientId
   * @param @param doctorId
   * @param @throws Exception 设定文件
   * @return void 返回类型
   * @throws
   */
  @RequestMapping(value = "/wxcheck/{loginName}/doctor/{doctorId}", method = RequestMethod.POST)
  public JSONObject wxcheck(@PathVariable String loginName, @PathVariable int doctorId) throws Exception {
    try{
      Patient p = patientService.getPatientByLoginName(loginName);
      if(p == null) {
        JSONObject json = new JSONObject();
        json.put("result", "fail");
        return json;
      }
      JSONObject result = patientService.wxcheckAddDoctorForWeixin(p.getId(), doctorId);
      return result;
    }catch(Exception e){
      JSONObject json = new JSONObject();
      json.put("result", "fail");
      return json;
    }
  }


}

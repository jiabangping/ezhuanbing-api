package com.ezhuanbing.api.service;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ezhuanbing.api.conf.ConstantConfig;
import com.ezhuanbing.api.dao.mybatis.mapper.AreaMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.DoctorPatientMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.GroupMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientWeixinMapper;
import com.ezhuanbing.api.dao.mybatis.model.Area;
import com.ezhuanbing.api.dao.mybatis.model.AreaCriteria;
import com.ezhuanbing.api.dao.mybatis.model.DoctorPatient;
import com.ezhuanbing.api.dao.mybatis.model.DoctorPatientCriteria;
import com.ezhuanbing.api.dao.mybatis.model.Group;
import com.ezhuanbing.api.dao.mybatis.model.patient.Patient;
import com.ezhuanbing.api.dao.mybatis.model.patient.PatientExample;
import com.ezhuanbing.api.dao.mybatis.model.patient.PatientExtraInfo;
import com.ezhuanbing.api.dao.mybatis.model.patient.PatientWeixin;
import com.ezhuanbing.api.dao.mybatis.model.patient.PatientWeixinExample;
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.exception.HttpStatus403Exception;
import com.ezhuanbing.api.tools.ConstantClass;
import com.ezhuanbing.api.vo.PatientResp;
import com.github.pagehelper.PageHelper;

@Service
public class PatientService {

  @Autowired
  PatientMapper patientMapper;
  @Autowired
  PatientExtraInfoService patientExtraInfoService;
  @Autowired
  DoctorPatientMapper doctorPatientMapper;
  @Autowired
  GroupMapper groupMapper;
  @Autowired
  PatientPaperGenerateService patientPlanGenerateService;
  @Autowired
  AreaMapper areaMapper;
  
  @Autowired
  PatientWeixinMapper patientWeixinMapper;

  @Value("${ssoUrl}")
  private String ssoUrl;
  public String getSsoUrl() {
    return ssoUrl;
  }
  public void setSsoUrl(String ssoUrl) {
    this.ssoUrl = ssoUrl;
  }

  public List<Patient> getList(Integer pageIndex, Integer pageSize) throws HttpStatus403Exception {
    if (pageSize == null) {
      pageSize = ConstantConfig.PAGE_SIZE;
    }
    PageHelper.startPage(pageIndex, pageSize);
    PatientExample example = new PatientExample();
    return patientMapper.selectByExample(example);
  }

  /**
   * 
   * @Title: getPatientById @Description: 根据患者Id获取患者信息 @param patientId @return Patient 患者信息 @throws
   */
  public Patient getPatientById(int patientId) {
    PatientExample example = new PatientExample();
    example.createCriteria().andIdEqualTo(patientId);
    List<Patient> p = patientMapper.selectByExample(example);
    Patient result = null;

    if (p.size() > 0) {
      result = p.get(0);
      List<PatientExtraInfo> extraInfos = patientExtraInfoService.getPatientExtraInfo(patientId);
      result.setPatientExtraInfo(extraInfos);
    }
    return result;
  }

  /**
   * 
   * @Title: getPatientByLoginName @Description: 根据登陆名获取患者信息 @param @param loginName @param @return
   *         设定文件 @return Patient 返回类型 @throws
   */
  public Patient getPatientByLoginName(String loginName) {
    PatientExample example = new PatientExample();
    example.createCriteria().andLoginNameEqualTo(loginName);
    List<Patient> p = patientMapper.selectByExample(example);
    Patient result = null;

    if (p.size() > 0) {
      result = p.get(0);
      List<PatientExtraInfo> extraInfos =
          patientExtraInfoService.getPatientExtraInfo(result.getId());
      result.setPatientExtraInfo(extraInfos);
    }
    return result;
  }

  /**
   * 
   * @Title: updatePatientHeadPhoto @Description: 更新患者头像url @param patientId @param
   *         url @param @return boolean 返回类型 @throws
   */
  public boolean updatePatientHeadPhoto(int patientId, String url) {
    Patient p = Patient.builder().id(patientId).photo(url).build();
    int result = patientMapper.updateByPrimaryKeySelective(p);
    if(result > 0){
      Patient patient = patientMapper.selectByPrimaryKey(patientId);
      registerPatientImgToIm(patient);
    }
    return result > 0;
  }
  
  @Async
  private void registerPatientImgToIm(Patient p){
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters()
    .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    restTemplate.getForObject(ssoUrl + "/im/netease/user/sync?loginName="
                    +p.getLoginName()+"&userName="+p.getName()+"&userPhotoUrl="+p.getPhotoUrl(), String.class);
  }

  /**
   * 
   * @Title: getPatientDoctor @Description: 获取患者的医生列表 @param @param patientId @param @return
   *         设定文件 @return List<DoctorPatient> 返回类型 @throws
   */
  public List<DoctorPatient> getPatientDoctor(int patientId) {
    return doctorPatientMapper.getPatientDoctor(patientId);
  }

  /**
   * @throws Exception
   * 
   * @Title: addDoctor 
   * @Description: 给患者添加医生 
   * @param @param patientId 
   * @param @param doctorId 
   * @param @return 设定文件 
   * @return boolean 返回类型 
   * @throws
   */
  @Transactional
  public void addDoctor(int patientId, int doctorId) throws Exception {

    DoctorPatientCriteria criteria = new DoctorPatientCriteria();
    criteria.createCriteria().andDoctoridEqualTo(doctorId).andPatientidEqualTo(patientId);
    List<DoctorPatient> reuslt = doctorPatientMapper.selectByExample(criteria);
    if (reuslt.size() > 0)
      throw new HttpStatus400Exception("患者医生关系", "", "该医生已添加过，不能再重复添加！", "");

    // 查询医生是否有默认群组
    int doctorDefaultGroupId = 0;
    List<Group> t = selectGroupsByDoctorId(doctorId, 1);
    if (t.size() == 0) {
      doctorDefaultGroupId = addGroupById(doctorId,ConstantClass.DEFAULT_DOCTOR_GROUP, 1);
    }else{
      doctorDefaultGroupId = t.get(0).getId();
    }
    
    DoctorPatient doctor = DoctorPatient.builder().patientId(patientId).doctorId(doctorId)
        .groupId(doctorDefaultGroupId)
        .joinDate(new Date()).build();
    // 患者医生关系建立
    int result = doctorPatientMapper.insertUseGeneratedKeys(doctor);    
    if (result > 0) {
      if(patientPlanGenerateService.GenerateFirstPaper(patientId, doctorId)){
        // 正常
      }else{
        // 删除医生患者关系
        doctorPatientMapper.deleteByPrimaryKey(doctor.getId());
        throw new HttpStatus400Exception("患者医生关系", "", "创建首诊问卷失败！", "");
      }
    }else{
      throw new HttpStatus400Exception("患者医生关系", "", "医生添加失败，请稍后重试！", "");
    }
  }

  /**
   * 
   * @Title: updatePatientPhoneIEMI @Description: 更新手机串码 @param @param patientId @param @param
   *         patient @param @return 设定文件 @return boolean 返回类型 @throws
   */
  public boolean updatePatientPhoneIEMI(int patientId, Patient patient) {
    Patient p = Patient.builder().id(patientId).phoneType(patient.getPhoneType())
        .phoneIMEI(patient.getPhoneIMEI()).build();
    int result = patientMapper.updateByPrimaryKeySelective(p);
    return result > 0;
  }

  /**
   * 
   * @Title: syncPatient @Description: 同步患者信息 @param @param patient 设定文件 @return boolean
   *         返回类型 @throws
   */
  public boolean syncPatient(Patient patient) {

    AreaCriteria areaCriteria = new AreaCriteria();
    areaCriteria.createCriteria().andIdEqualTo(patient.getDistrictId());
    List<Area> area = areaMapper.selectByExample(areaCriteria);
    if(area.size() > 0){
      patient.setAddress(area.get(0).getProvincename() + "-" + area.get(0).getCityname());
    }
    
    PatientExample example = new PatientExample();
    example.createCriteria().andLoginNameEqualTo(patient.getLoginName());
    List<Patient> p = patientMapper.selectByExample(example);
    int result;
    if (p.size() > 0) {
      // 更新
      patient.setId(p.get(0).getId());
      result = patientMapper.updateByPrimaryKey(patient);
    } else {
      // 新增
      result = patientMapper.insertSelective(patient);
    }

    return result > 0;
  }

  /**
   * 获得患者管理页面的病人列表
   */
  public List<PatientResp> getDoctorPatientList(Integer doctorId, String name, Integer groupId,
      String followupCount, Integer pageIndex, Integer pageSize) throws HttpStatus403Exception {
    if (pageSize == null) {
      pageSize = ConstantConfig.PAGE_SIZE;
    }
    PageHelper.startPage(pageIndex, pageSize);
    Map<String, Object> paramsMap = new HashMap<String, Object>();
    if (null != doctorId) {
      paramsMap.put("doctorId", doctorId);
    }
    if (null != name) {
      paramsMap.put("name", name);
    }
    if (null != groupId) {
      paramsMap.put("groupId", groupId);
    }
    if (null != followupCount) {
      paramsMap.put("followupCount", followupCount);
    }
    return patientMapper.selectByParamsMap(paramsMap);
  }

  /**
   * 获得医生已有的人群分类。如果该医生人群分类为空，则新建一条默认数据。
   * 
   * @param doctorId
   * @return
   */
  public List<Group> selectGroupsByDoctorId(Integer doctorId, Integer isdefault) {
    Map<String, Object> paramsMap = new HashMap<String, Object>();
    paramsMap.put("doctorId", doctorId);
    paramsMap.put("isdefault", isdefault);
    return groupMapper.selectGroupsByDoctorId(paramsMap);
  }

  /**
   * 删除一个人群分类，并将其中的人群都变为默认分类
   * 
   * @param id
   * @param doctorId
   * @return
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
  public int delGroupById(int id, int doctorId) {
    int defaultid = this.selectGroupsByDoctorId(doctorId, 1).get(0).getId();
    Map<String, Object> paramsMap = new HashMap<String, Object>();
    paramsMap.put("id", id);
    paramsMap.put("doctorId", doctorId);
    paramsMap.put("defaultid", defaultid);
    doctorPatientMapper.updateDPGroupId(paramsMap);
    return groupMapper.delGroupById(paramsMap);
  }

  /**
   * 新建一个人群分类
   * 
   * @param doctorId
   * @param name
   * @param isdefault
   * @return
   */
  public int addGroupById(Integer doctorId, String name, Integer isdefault) {
	  Group g = Group.builder().doctorId(doctorId).name(name).isdefault(isdefault).build();
	  groupMapper.insertUseGeneratedKeys(g);
	  return g.getId();
  }

  /**
   * 修改一个人群分类的名称
   * 
   * @param name
   * @param id
   * @return
   */
  public int updateGroupNameById(int id, String name) {
    return groupMapper.updateByPrimaryKeySelective(new Group(id, null, name, null));
  }

  /**
   * 批量修改人群分类
   * 
   * @param p
   * @param doctorid
   * @param gid
   * @return
   */
  public int reGroupBatch(int[] p, Integer doctorid, int gid) {
    Map<String, Object> paramsMap = new HashMap<String, Object>();
    paramsMap.put("p", p);
    paramsMap.put("doctorId", doctorid);
    paramsMap.put("gid", gid);
    return doctorPatientMapper.updateGroupBatch(paramsMap);
  }

  /**
   * 获得病人的病史信息
   * 
   * @param loginName
   * @return
   */
  public List<Map<String, Object>> getPatientExtInfoByLoginName(String loginName) {
    Map<String, Object> paramsMap = new HashMap<String, Object>();
    paramsMap.put("loginName", loginName);
    return patientMapper.getPatientExtInfoByLoginName(paramsMap);
  }

	public Map<String, Object> getPatientInfo(String loginName) {
	    Map<String, Object> paramsMap = new HashMap<String, Object>();
	    paramsMap.put("loginName", loginName);
	    return patientMapper.getPatientInfo(paramsMap);
	}
	
	public List<Group> selectGroupsByDoctorIdPageInfo(Integer doctorId, int isdefault, Integer pageIndex, Integer pageSize) {
	    if (pageSize == null) {
	        pageSize = ConstantConfig.PAGE_SIZE;
	      }
	      PageHelper.startPage(pageIndex, pageSize);
	      
	    Map<String, Object> paramsMap = new HashMap<String, Object>();
	    paramsMap.put("doctorId", doctorId);
	    paramsMap.put("isdefault", isdefault);
	    return groupMapper.selectGroupsByDoctorId(paramsMap);
	}

	public int getPatientNumsByGroupId(Integer groupId) {
	    Map<String, Object> paramsMap = new HashMap<String, Object>();
	    paramsMap.put("id", groupId);
	    return patientMapper.getPatientNumsByGroupId(paramsMap);
	}
	
   public JSONObject checkWxBindUrl(String wxOpenId) {
   
    PatientWeixinExample criteria = new PatientWeixinExample();
    criteria.createCriteria().andWxOpendIdEqualTo(wxOpenId).andSystemIdEqualTo(3);
    List<PatientWeixin> result = patientWeixinMapper.selectByExample(criteria);
    JSONObject json = new JSONObject();
    if(result.size() > 0){
      json.put("result", "alreadyBind");
      json.put("loginName", result.get(0).getLoginName());
    }else {
      json.put("result", "notBind"); 
    }
    return json;
  }
  public JSONObject bindWxAccount(String wxOpenId, String loginName) {
    JSONObject json = new JSONObject();
    PatientWeixin record = PatientWeixin.builder().wxOpendId(wxOpenId).loginName(loginName).systemId(3).build();
    if(patientWeixinMapper.insert(record) > 0 ) {
      json.put("result", "success");
      return json;
    }
    json.put("result", "fail");
    return json;
  }
  
  
  /**
   * @throws Exception
   * 
   * @Title: addDoctorForWeixin 
   * @Description: 给患者添加医生 
   * @param @param patientId 
   * @param @param doctorId 
   * @param @return 设定文件 
   * @return boolean 返回类型 
   * @throws
   */
  @Transactional
  public JSONObject addDoctorForWeixin(int patientId, int doctorId) {
    try {
      DoctorPatientCriteria criteria = new DoctorPatientCriteria();
      criteria.createCriteria().andDoctoridEqualTo(doctorId).andPatientidEqualTo(patientId);
      List<DoctorPatient> reuslt = doctorPatientMapper.selectByExample(criteria);
      if (reuslt.size() > 0) {
        JSONObject result = new JSONObject();
        result.put("result", "alreadyBind");
        return result;
      }
  
      // 查询医生是否有默认群组
      int doctorDefaultGroupId = 0;
      List<Group> t = selectGroupsByDoctorId(doctorId, 1);
      if (t.size() == 0) {
        doctorDefaultGroupId = addGroupById(doctorId,ConstantClass.DEFAULT_DOCTOR_GROUP, 1);
      }else{
        doctorDefaultGroupId = t.get(0).getId();
      }
      
      DoctorPatient doctor = DoctorPatient.builder().patientId(patientId).doctorId(doctorId)
          .groupId(doctorDefaultGroupId)
          .joinDate(new Date()).build();
      // 患者医生关系建立
      int result = doctorPatientMapper.insertUseGeneratedKeys(doctor);    
      if (result > 0) {
        if(patientPlanGenerateService.GenerateFirstPaper(patientId, doctorId)){
          // 正常
        }else{
          // 删除医生患者关系
          doctorPatientMapper.deleteByPrimaryKey(doctor.getId());
        }
      }
      JSONObject json = new JSONObject();
      json.put("result", "success");
      return json;
    }catch(Exception e) {
      JSONObject result = new JSONObject();
      result.put("result", "fail");
      return result;
    }
  }
  
  
  public JSONObject wxcheckAddDoctorForWeixin(int patientId, int doctorId) {
    try {
      DoctorPatientCriteria criteria = new DoctorPatientCriteria();
      criteria.createCriteria().andDoctoridEqualTo(doctorId).andPatientidEqualTo(patientId);
      List<DoctorPatient> reuslt = doctorPatientMapper.selectByExample(criteria);
      if (reuslt.size() > 0) {
        JSONObject result = new JSONObject();
        result.put("result", "alreadyBind");
        return result;
      }else {
        JSONObject result = new JSONObject();
        result.put("result", "notBind");
        return result;
      }
    }catch(Exception e) {
      JSONObject result = new JSONObject();
      result.put("result", "fail");
      return result;
    
    }
  }
  
}

package com.ezhuanbing.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.conf.ConstantConfig;
import com.ezhuanbing.api.dao.mybatis.mapper.DoctorPatientMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.OnlineDoctorMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientAssignMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PatientFollowUpPlanMapper;
import com.ezhuanbing.api.dao.mybatis.model.Doctor;
import com.ezhuanbing.api.dao.mybatis.model.DoctorPatient;
import com.ezhuanbing.api.dao.mybatis.model.Group;
import com.ezhuanbing.api.dao.mybatis.model.OnlineDoctor;
import com.ezhuanbing.api.dao.mybatis.model.OnlineDoctorCriteria;
import com.ezhuanbing.api.dao.mybatis.model.PatientAssign;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlan;
import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlanExample;
import com.ezhuanbing.api.tools.ConstantClass;
import com.github.pagehelper.PageHelper;

@Service
public class DoctorService {
  
  @Autowired
  DoctorPatientMapper doctorPatientMapper;
  @Autowired
  PatientAssignMapper patientAssignMapper;
  @Autowired
  PatientService patientService;
  @Autowired
  OnlineDoctorMapper onlineDoctorMapper;
  @Autowired
  PatientFollowUpPlanMapper patientFollowUpPlanMapper; 
  
  public Doctor getDoctor(int doctorId){
    return doctorPatientMapper.getDoctor(doctorId);
  }

	public List<Doctor> getDoctorList(String doctorName, Integer doctorId, Integer pageIndex, Integer pageSize) {
		if (pageSize == null) {
			pageSize = ConstantConfig.PAGE_SIZE;
		}
		PageHelper.startPage(pageIndex, pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		if(null != doctorName ) { 
			paramsMap.put("doctorName", doctorName);
		}
		if(null != doctorId ) { 
			paramsMap.put("doctorId", doctorId);
		}
		return doctorPatientMapper.getDoctorList(paramsMap);
	}

	/**
	 * 患者重新分配医生。
	 * @param pids 患者id数组
	 * @param pNameArr 患者姓名数组（分配失败时，返回医生姓名）
	 * @param doctorid 旧医生id
	 * @param tid 新医生id
	 * @return
	 */
	public String assignNewDoctor(int[] pids, String[] pNameArr, Integer doctorid, Integer tid) {
		ArrayList<Integer> pids2 = new ArrayList<Integer>();	//存储目标医生和病人没有建立过关系的病人id
		ArrayList<Integer> pids3 = new ArrayList<Integer>();	//存储目标医生和病人已经建立过关系的病人id
		String str = "";
		
		for (int i = 0; i < pids.length; i++) {
			//更新前先判断mb_doctorpatient中是否已有该医生病人的关系，没有的才准备继续更新，有的返回病人姓名，告诉医生分配失败！
			int t2 = doctorPatientMapper.selectCount(DoctorPatient.builder().doctorId(tid).patientId(pids[i]).build());
			if (t2 > 0) {
				pids3.add(pids[i]);
				str += pNameArr[i] + "、";
				continue;
			} else {
				pids2.add(pids[i]);
			}
		}
		
		if (pids2.isEmpty() && !pids3.isEmpty()) {
			str = str.substring(0, str.length()-1);
			str = "患者<span style='color:red'>" + str;
			str += "</span>分配失败，因为患者已经绑定了医生doctorName！";
			return str;
		}
		
		//1. 分配信息数据插入mb_patientassign表
		List<PatientAssign> recordList = new ArrayList<PatientAssign>();
		for (int pid:pids2) {
			recordList.add(new PatientAssign(null, doctorid, tid, pid, null, null));
		}
		patientAssignMapper.insertList(recordList);
		
		//2. 查看目标医生是否有默认分类，如果没有则新建一个默认分类
		List<Group> t = patientService.selectGroupsByDoctorId(tid, 1);
		int gId = 0;
		if (t.size() == 0) {
			gId = patientService.addGroupById(tid,ConstantClass.DEFAULT_DOCTOR_GROUP, 1);
		} else {
			gId = t.get(0).getId();
		}
		
		//3. 将首诊随访计划中的医生id修改为新医生的id
		for (int pid:pids2) {
			PatientFollowUpPlanExample example = new PatientFollowUpPlanExample();
			example.createCriteria().andDoctorIdEqualTo(doctorid).andPatientIdEqualTo(pid).andPlanTypeEqualTo(1);
			patientFollowUpPlanMapper.updateByExampleSelective(PatientFollowUpPlan.builder().doctorId(tid).build(), example);
		}
		
		//4. 更新mb_doctorpatient中对应的医生患者信息
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("gId", gId);
		paramsMap.put("p", pids2);
		paramsMap.put("fid", doctorid);
		paramsMap.put("tid", tid);
		int sNum = patientAssignMapper.assignNewDoctor(paramsMap);
		
		if (sNum == pids.length) {
			return "ok";
		} else if (sNum == pids2.size() && pids2.size() + pids3.size() == pids.length) {
			if (str != null && str.length() > 0) {
				str = str.substring(0, str.length()-1);
			}
			str = "患者<span style='color:red'>" + str;
			str += "</span>分配失败，因为患者已经绑定了医生doctorName。其他患者分配成功！";
			return str;
		} else {
			return "分配患者时发生错误，请稍后再试！";
		}

	}

	public List<Map<String, Object>> getAssignedPatientList(Integer doctorid, Integer pageIndex, Integer pageSize) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("id", doctorid);
		List<Map<String, Object>> t = patientAssignMapper.getAssignedPatientList(paramsMap);
		return t;
	}
	
	/**
	 * 
	* @Title: setDoctorOnlineStatus 
	* @Description: 给医生在线表里添加数据
	* @param @param doctorId
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean setDoctorOnline(int doctorId){
	  OnlineDoctor onlineDoctor = OnlineDoctor.builder().doctorid(doctorId).build();
	  int result = onlineDoctorMapper.insert(onlineDoctor);
	  return result > 0;
	}
	
	   /**
     * 
    * @Title: setDoctorOnlineStatus 
    * @Description: 删除医生在线表里数据
    * @param @param doctorId
    * @param @return    设定文件 
    * @return boolean    返回类型   
    * @throws
     */
	public boolean setDoctorOffline(int doctorId){
	  
	  OnlineDoctorCriteria criteria = new OnlineDoctorCriteria();
	  criteria.createCriteria().andDoctoridEqualTo(doctorId);
	  int result = onlineDoctorMapper.deleteByExample(criteria);
	  return result > 0;
	}
}

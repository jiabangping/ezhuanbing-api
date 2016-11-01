package com.ezhuanbing.api.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.DrugListMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.MedicineMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.PlanDrugMapper;
import com.ezhuanbing.api.dao.mybatis.model.medicine.Dictionary;
import com.ezhuanbing.api.dao.mybatis.model.medicine.Medicine;
import com.ezhuanbing.api.dao.mybatis.model.medicine.PlanDrug;
import com.ezhuanbing.api.vo.DrugListDetail;
import com.ezhuanbing.api.vo.PlanDrugVo;

@Service
public class PlanDrugService {
	@Autowired
	private PlanDrugMapper planDrugMapper;
	
	@Autowired
	private MedicineMapper medicineMapper;
	
	@Autowired 
	private DictionaryService dictionaryService;
	
	@Autowired
	private DrugListMapper drugListMapper;
	
	public PlanDrug add(PlanDrug record){
		int num = planDrugMapper.insert(record);
		if(num>0){
			return record;
		}else{
			return null;
		}
	}
	/**
	 * 根据条件查询
	 */
	public List<PlanDrug> getPlans(PlanDrug plan){
		List<PlanDrug> list = planDrugMapper.select(plan);
		return list;
	}
	
	public PlanDrug getPlanDrugById(Integer id){
		PlanDrug plan = planDrugMapper.selectByPrimaryKey(id);
//		Integer drugId = plan.getDrugId();
//		Medicine medicine = medicineMapper.selectByPrimaryKey(drugId);
		
//		PlanDrugInfo info = new PlanDrugInfo();
//		BeanCopier beanCopier = BeanCopier.create(PlanDrug.class, PlanDrugInfo.class, false);
//		beanCopier.copy(plan, info, null);
//		info.setName(medicine.getName());
//		info.setSpecification(medicine.getSpecifications());
		return plan;
	}
	/**
	 * 修改
	 * @param record
	 * @return
	 */
	public PlanDrug update(PlanDrug record){
		int num = planDrugMapper.updateByPrimaryKeySelective(record);
		if(num>0){
			return record;
		}else{
			return null;
		}
	}
	/**
	 * 根据id在药单中删除药物，如果当前药单中药删完了，此药单也删除
	 * @param id
	 * @return
	 */
	public int delete(Integer id,Integer drugListId){
		int num = planDrugMapper.deleteByPrimaryKey(id);
		int count = count(drugListId);
		if(count==0){//药单中已无药，删除药单
			drugListMapper.deleteByPrimaryKey(drugListId);
		}
		return num;
	}
	/**
	 * 返回当前药单中药品数量
	 * @param drugListId
	 * @return
	 */
	public int count(Integer drugListId){
		PlanDrug record = new PlanDrug();
		record.setDrugListId(drugListId);
		return planDrugMapper.selectCount(record);
	}
	/**
	 * 当前药物是否已经在药单中存在
	 * @param drugId
	 * @return
	 */
	public boolean isDrugInList(Integer planDetailId,Integer drugId){
//		PlanDrug record = new PlanDrug();
//		record.setDrugId(drugId);
		int count = planDrugMapper.getCountByDrugIdAndPlanId(planDetailId,drugId);
		if(count>0){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 根据病人id,计划详情id获取所有用药
	 * @param patientId
	 * @return
	 */
	public List<DrugListDetail> getDrugsByPatientId(Integer patientId,Integer planDetailId){
		List<DrugListDetail> details = new ArrayList<DrugListDetail>();
		List<PlanDrug> list = planDrugMapper.getDrugsByPatientIdAndPlanDetailId(patientId, planDetailId);
		if(CollectionUtils.isNotEmpty(list)){
			for(PlanDrug drug:list){
			  if(drug.getDrugId() > 0){
	            details.add(convert(drug)); 
			  }
			}
		}
		return details;
	}
	public DrugListDetail convert(PlanDrug info){
//		Dictionary dict = new Dictionary();
		DrugListDetail detail = new DrugListDetail();
		detail.setId(info.getId());
		detail.setAmount(info.getAmount()+"");
		Medicine medicine = medicineMapper.selectByPrimaryKey(info.getDrugId());
		detail.setName(medicine.getName());
		detail.setSpecification(medicine.getSpecifications());
//		dict.setTypeName("frequency");
//		dict.setCode(info.getFrequency());
		detail.setFrequency(dictionaryService.getDictCode("frequency",info.getFrequency()));
		detail.setAmount(info.getAmount()+"");
//		detail.setDose(dictionaryService.getDictCode("dose",info.getDose()));
		detail.setDose(info.getDose());
//		dict.setTypeName("unit");
//		dict.setCode(info.getUnit());;
		detail.setUnit(dictionaryService.getDictCode("unit",info.getUnit()));
//		dict.setTypeName("medicationMethods");
//		dict.setCode(info.getMedicationMethods());;
		detail.setMedicationMethods(dictionaryService.getDictCode("medicationMethods",info.getMedicationMethods()));
//		detail.setDoseCode(info.getDose());
		detail.setUnitCode(info.getUnit());
		detail.setMedicationMethodsCode(info.getMedicationMethods());
		detail.setFrequencyCode(info.getFrequency());
		detail.setDoctorAdvice(info.getDoctoradvice());
		detail.setDrugListId(info.getDrugListId());
		detail.setDays(info.getDays());
		detail.setDoseUnit(medicine.getDoseUnit());
		return detail;
	}
	
	public PlanDrug convert(PlanDrugVo plan){
		PlanDrug planDrug = new PlanDrug();
		planDrug.setAmount(plan.getAmount());
		planDrug.setDays(plan.getDays());
		planDrug.setDoctoradvice(plan.getDoctoradvice());
		planDrug.setFrequency(plan.getFrequency());
		planDrug.setPatientId(plan.getPatientId());
		planDrug.setDose(plan.getDose());
		planDrug.setMedicationMethods(dictionaryService.getDictKey("medicationMethods",plan.getMedicationMethods()));
		planDrug.setUnit(dictionaryService.getDictKey("unit",plan.getUnit()));
		return planDrug;
	}
	
}

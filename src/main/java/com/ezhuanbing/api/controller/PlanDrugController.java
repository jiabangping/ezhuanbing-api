package com.ezhuanbing.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
//import com.ezhuanbing.api.dao.mybatis.model.medicine.Dictionary;
import com.ezhuanbing.api.dao.mybatis.model.medicine.DrugList;
//import com.ezhuanbing.api.dao.mybatis.model.medicine.Medicine;
import com.ezhuanbing.api.dao.mybatis.model.medicine.PlanDrug;
//import com.ezhuanbing.api.service.DictionaryService;
import com.ezhuanbing.api.service.DrugListService;
import com.ezhuanbing.api.service.MedicineService;
import com.ezhuanbing.api.service.PlanDrugService;
import com.ezhuanbing.api.tools.DictionaryEnumUtils;
import com.ezhuanbing.api.vo.DrugListDetail;
import com.ezhuanbing.api.vo.PlanDrugVo;

@RestController
@RequestMapping("/planDrug")
public class PlanDrugController {

	@Autowired
	private PlanDrugService planDrugService;
	@Autowired
	private MedicineService medicineService;
	
//	@Autowired
//	private DictionaryService dictionaryService;
	@Autowired
	private DrugListService drugListService;
	
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/add",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String add(@RequestBody PlanDrugVo plan,HttpServletRequest req){
		String name = req.getParameter("name");
		String specification = req.getParameter("spec");
		String planId = req.getParameter("planId");
		Integer drugId = medicineService.getMedicineId(name, specification,plan.getUnit(),plan.getMedicationMethods());
		PlanDrug planDrug = planDrugService.convert(plan);
		planDrug.setDrugId(drugId);
		int planDetailId = Integer.parseInt(planId==null?"0":planId);
		Map<String,Object> map = getMapData(planDetailId);
//		Integer amount = Integer.parseInt(map.get("count")==null?"0":map.get("count").toString());
		if(null == map){//当前计划第一次增加药物
			DrugList record = new DrugList();
			record.setName("药单1");
			record.setPlanDetailId(planDetailId);
			
			int key = drugListService.add(record);
			planDrug.setDrugListId(record.getId());
			planDrug = planDrugService.add(planDrug);
		}else{
			Integer drugListId = Integer.parseInt(map.get("drugListId").toString());
			int count = planDrugService.count(drugListId);//planId plan.getPlanId();
			if(count<5){//药单内数量小于5，可以增加
				boolean b = planDrugService.isDrugInList(planDetailId,drugId);
				if(b){//药品不存在药单中
					planDrug.setDrugListId(drugListId);
					planDrug = planDrugService.add(planDrug);
				}else{
					return JSON.toJSONString(1);
				}
			}else{//当前药单药品数量大于5，重新生成一张药单
				boolean b = planDrugService.isDrugInList(planDetailId,drugId);
				if(b){//药品不存在药单中
					int value = drugListService.count(planDetailId);//药单数量
					
					DrugList record = new DrugList();
					record.setName("药单"+(value+1));
					record.setPlanDetailId(planDetailId);
					int key = drugListService.add(record);
					planDrug.setDrugListId(record.getId());
					planDrug = planDrugService.add(planDrug);
				}else{
					return JSON.toJSONString(1);
				}
				
	//			return JSON.toJSONString(2);
			}
		}
		return JSON.toJSONString(planDrug);
	}
	
	public Map<String,Object> getMapData(Integer planId){
		List<Map<String,Object>> list = medicineService.getDrugListInfo(planId);
		Map<String,Object> map = null;
		if(CollectionUtils.isNotEmpty(list)){
			if(list.size()==1){
				return map=list.get(0);
			}else{
				for(int i=0;i<list.size();i++){
					if(Integer.parseInt(list.get(i).get("count").toString())<5){
						map = list.get(i);
						break;
					}
				}
				if(map==null){
					map = list.get(list.size()-1);
				}
			}
		}
		return map;
	}
	/**
	 * 查询
	 * @param plan
	 * @param req
	 * @return
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getPlans",method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getPlans(@RequestBody PlanDrug plan,HttpServletRequest req){
		String name = req.getParameter("name");
		String specification = req.getParameter("spec");
		Integer drugId = medicineService.getMedicineId(name, specification,DictionaryEnumUtils.getTextUnitEnum(plan.getUnit()),DictionaryEnumUtils.getTextUsageEnum(plan.getMedicationMethods()));
		plan.setDrugId(drugId);
		return JSON.toJSONString(getPlans(planDrugService.getPlans(plan)));//,name,specification
	}
	/**
	 * 把表中的字典code码转为实际名字
	 * @param plans
	 * @return
	 */
	public List<DrugListDetail> getPlans(List<PlanDrug> plans){//,String name,String spec
		List<DrugListDetail> rltList = new ArrayList<DrugListDetail>();
		if(CollectionUtils.isNotEmpty(plans)){
			for(PlanDrug drug:plans){
				rltList.add(planDrugService.convert(drug));
			}
		}
		return rltList;
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="getPlanDetailById/{id}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getPlanDetailById(@PathVariable("id") Integer id) throws Exception{
		PlanDrug info = planDrugService.getPlanDrugById(id);
		DrugListDetail detail = planDrugService.convert(info);
		return JSON.toJSONString(detail);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/update",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String update(@RequestBody PlanDrug plan,HttpServletRequest req){
//		String name = req.getParameter("name");
//		String specification = req.getParameter("spec");
//		Integer drugId = medicineService.getMedicineId(name, specification);
//		plan.setDrugId(drugId);
		plan = planDrugService.update(plan);
		return JSON.toJSONString(plan);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/updateInfo",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String updateInfo(@RequestParam Integer id,@RequestParam Integer amount,@RequestParam Integer days,@RequestParam String dose,
			      @RequestParam String remark,@RequestParam Integer frequency){
		PlanDrug plan = planDrugService.getPlanDrugById(id);
		plan.setAmount(amount);
		plan.setDays(days);
		plan.setDoctoradvice(remark);
		plan.setDose(dose);
		plan.setFrequency(frequency);
		plan = planDrugService.update(plan);
		return JSON.toJSONString(plan);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/delete")
	public String delete(Integer id,Integer drugListId){
		int num = planDrugService.delete(id,drugListId);
		return String.valueOf(num);
	}
	/**
	 * 显示药单里所开的药
	 * @param planId 根据planid找到未满5的药单，如果满5，则显示最后开的那个药单
	 * @return
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getPlanInfoByDrugListId/{planId}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getPlanInfoById(@PathVariable("planId")Integer planId){
		PlanDrug plan = new PlanDrug();
		Map<String,Object> map = this.getMapData(planId);
		if(null == map){
			plan.setDrugListId(-1);
		}else{
			Integer drugListId = Integer.parseInt(map.get("drugListId").toString());
			plan.setDrugListId(new Integer(drugListId.intValue()));
		}
		return JSON.toJSONString(getPlans(planDrugService.getPlans(plan)));
	}
	/**
	 * 根据药单id查看药单有哪些药品
	 * @param drugListId
	 * @return
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getDrugListByListId/{drugListId}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getDrugListByListId(@PathVariable("drugListId") Integer drugListId){
		PlanDrug plan = new PlanDrug();
		plan.setDrugListId(drugListId);
		return JSON.toJSONString(getPlans(planDrugService.getPlans(plan)));
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getDrugList/{planDetailId}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getDrugList(@PathVariable("planDetailId") Integer planDetailId){
		return JSON.toJSONString(drugListService.getDrugList(planDetailId));
	}
	/**
	 * 获得当前药单里面药品数量
	 * @param drugListId
	 * @return
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getDrugListCount/{drugListId}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getDrugListCount(@PathVariable("drugListId") Integer drugListId){
		return JSON.toJSONString(planDrugService.count(drugListId));
	}
	
}

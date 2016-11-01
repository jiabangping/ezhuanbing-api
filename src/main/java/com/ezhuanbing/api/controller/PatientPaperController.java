package com.ezhuanbing.api.controller;

import java.util.List;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PaperImage;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientPaper;
import com.ezhuanbing.api.service.PaperImageService;
import com.ezhuanbing.api.service.PatientPaperService;

@RestController
@RequestMapping("/api/v1/patientPaper")
public class PatientPaperController {

	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PatientPaperService patientPaperService;
	
	@Autowired
	PaperImageService paperImageService;
	/**
	 * 获取Paper>classes>questions   展示总表
	 */
	@RequestMapping(value = "/getPatientPaperSummary/{patientPaperId}", method = RequestMethod.GET)
	public PatientPaper getPatientPaperSummary(
			@PathVariable("patientPaperId") Integer id) throws Exception  {
		
		return patientPaperService.selectPaperWithClassesAndQuestions(id);
	}
	
	
	/**
	 * 根据patientPaperId获取分类菜单Paper>class>class..
	 */
	@RequestMapping(value = "/getPaperClass/{patientPaperId}", method = RequestMethod.GET)
	public PatientPaper getPaperClass(
			@PathVariable("patientPaperId") Integer id) throws Exception  {
		
		return patientPaperService.getPaperClassesTree(id);
	}
	

   @RequestMapping(value = "/getPatientPaperDetail/{patientPaperId}", method = RequestMethod.GET)
   public PatientPaper getPatientPaperDetail(
           @PathVariable("patientPaperId") Integer id) throws Exception  {
       
       return patientPaperService.getPatientPaperSummary(id);
   }
	
	/**
	 * 根据planDetailId获取PatientPapers
	 */
	@RequestMapping(value = "/patientPapers/{planDetailId}", method = RequestMethod.GET)
	public JSONArray getPatientPapersByPlanDetailId(
			@PathVariable("planDetailId") Integer planDetailId) throws Exception  {
		List<PatientPaper> papers = patientPaperService.getPatientPapersByPlanDetailId(planDetailId); 
		return JSONArray.fromObject(papers);
	}
	
	/**
	 * 根据planDetailId获取PatientPaperIds
	 */
	@RequestMapping(value = "/patientPaperIds/{planDetailId}", method = RequestMethod.GET)
	public JSONArray getPatientPaperIdsByPlanDetailId(
			@PathVariable("planDetailId") Integer planDetailId) throws Exception  {
		List<Integer> papers = patientPaperService.getPatientPaperIdsByPlanDetailId(planDetailId); 
		return JSONArray.fromObject(papers);
	}
	

	/**
	 * 
	* @Title: getSimplePatientPapers 
	* @Description: 获取单次随访的所有随访表
	* @param @param planDetailId
	* @param @return
	* @param @throws Exception    设定文件 
	* @return List<PatientPaper>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/simplePatientPapers/{planDetailId}", method = RequestMethod.GET)
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	public List<PatientPaper> getSimplePatientPapers(
			@PathVariable("planDetailId") Integer planDetailId) throws Exception  {
		List<PatientPaper> papers = patientPaperService.getSimplePatientPapers(planDetailId); 
		return papers;
	}
	
	
	//////////////////////////////////////app用/////////////////////////////////////////////////////
	   /**
     * 
    * @Title: getSimplePatientPapers 
    * @Description: 获取单次随访的所有随访表
    * @param @param planDetailId
    * @param @return
    * @param @throws Exception    设定文件 
    * @return List<PatientPaper>    返回类型 
    * @throws
     */
    @RequestMapping(value = "/app/simplePatientPapers/{planDetailId}", method = RequestMethod.GET)
    @ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
    public List<PatientPaper> getAppSimplePatientPapers(
            @PathVariable("planDetailId") Integer planDetailId) throws Exception  {
        List<PatientPaper> papers = patientPaperService.getSimplePatientPapers(planDetailId); 
        return papers;
    }
	/////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 根据planDetailId获取PatientPaperIds
	 */
	@RequestMapping(value = "/PaperImages/{paperId}", method = RequestMethod.GET)
	public List<PaperImage> getPaperImagesByPaperId(
			@PathVariable("paperId") Integer paperId) throws Exception  {
		List<PaperImage> paperImages = paperImageService.getPaperImagesByPaperId(paperId); 
		return paperImages;
	}
	


}

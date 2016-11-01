package com.ezhuanbing.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.model.PapersTemplates;
import com.ezhuanbing.api.model.DoctorResponseToken;
import com.ezhuanbing.api.service.ContentClassTemplatesService;
import com.ezhuanbing.api.service.PapersTemplatesService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/template")
public class PapersTemplatesController {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PapersTemplatesService mbPapersTemplatesService;

	@Autowired
	private ContentClassTemplatesService mbContentClassTemplatesService;

	
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String add(HttpServletRequest request){
		Integer id = Integer.parseInt(request.getParameter("id")==null?"0":request.getParameter("id"));
		PapersTemplates t1 = mbPapersTemplatesService.getTemplateById(id);
//		String name = request.getParameter("name");
//		Integer nameId = Integer.parseInt(request.getParameter("userNameId")==null?"0":request.getParameter("userNameId"));
		DoctorResponseToken token = (DoctorResponseToken)request.getAttribute("doctorResponseToken");
		PapersTemplates t = mbPapersTemplatesService.copy(id, token.getLoginName(), token.getUserId(),t1);
//		if(mbPapersTemplatesService.isExist(token.getUserId(),token.getDepartId())){//,t1.getPaperstatus()
			/*
			PapersTemplates t = new PapersTemplates();
			BeanCopier beanCopier = BeanCopier.create(PapersTemplates.class, PapersTemplates.class, false);
			beanCopier.copy(t1, t, null);
	//		t.setUsername(name);
	//		t.setUsernameid(nameId);
			t.setUsername(token.getLoginName());
			t.setUsernameid(token.getUserId());
			t.setPaperstatus(1);
			t = mbPapersTemplatesService.addTemplate(t);
			if(null != t){
				//复制分类
				List<ContentClassTemplates> mbClasses = mbContentClassTemplatesService.getAllClass(id);
				if(CollectionUtils.isNotEmpty(mbClasses)){
					for(ContentClassTemplates mbc:mbClasses){
						ContentClassTemplates dest = new ContentClassTemplates();
						BeanCopier classBeanCopier = BeanCopier.create(ContentClassTemplates.class, ContentClassTemplates.class, false);
						classBeanCopier.copy(mbc, dest, null);
						dest.setPapersid(t.getId());
						mbContentClassTemplatesService.add(dest);
						//复制题目
	                    List<QuestionsTemplates> qsts = mbQuestionsTemplatesService.getAllQsts(mbc.getId());//根据分类id获得分类的所有题目
	                    if(CollectionUtils.isNotEmpty(qsts)){
	                    	Integer cid = dest.getId();
	                    	for(QuestionsTemplates qst:qsts){
	                    		QuestionsTemplates mbQst = new QuestionsTemplates();
	        					BeanCopier qstBeanCopier = BeanCopier.create(QuestionsTemplates.class, QuestionsTemplates.class, false);
	        					qstBeanCopier.copy(qst, mbQst, null);
	        					mbQst.setContentclassicid(cid);
	        					mbQuestionsTemplatesService.add(mbQst);
	        					//下面复制答案
	        					Integer qid = qst.getId();
	        					List<OptionTemplates> options = optionTemplatesService.getAllOptionClass(qid);
	        					for(OptionTemplates option:options){
	        						OptionTemplates op = new OptionTemplates();
		        					BeanCopier optionBeanCopier = BeanCopier.create(OptionTemplates.class, OptionTemplates.class, false);
		        					optionBeanCopier.copy(option, op, null);
		        					optionTemplatesService.add(op);
	        					}
	                    	}
	                    }
						
					}
				}
			}
			return JSON.toJSONString(t);
//		}else{
//			return JSON.toJSONString(1);
//		}
 * */
		if(null != t){
			return JSON.toJSONString(t);
		}else{
			return JSON.toJSONString(1);
		}

	}
	/**
	 * 模板库列表
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getAll")
	public PageInfo<PapersTemplates> getAll(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
	          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,HttpServletRequest request){//@RequestParam("deptId") Integer deptId
		DoctorResponseToken token = (DoctorResponseToken)request.getAttribute("doctorResponseToken");
		Integer deptId = token.getDepartId();
		List<PapersTemplates> list = mbPapersTemplatesService.getAllTemplate(pageIndex,pageSize,deptId);
		return new PageInfo<PapersTemplates>(list);
	}
	/**
	 * 根据用户id过滤，doctorid，某个医生的模板库
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getTemplateByName")
	public PageInfo<PapersTemplates> getTemplateByName(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
	          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,HttpServletRequest request){//@RequestParam("name") String name  @RequestParam("userNameId") Integer userNameId
		DoctorResponseToken token = (DoctorResponseToken)request.getAttribute("doctorResponseToken");
		Integer userNameId = token.getUserId();
		List<PapersTemplates> list = mbPapersTemplatesService.getTemplateByName(pageIndex,pageSize,userNameId);//Integer userNameId
		return new PageInfo<PapersTemplates>(list);
	}
	
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getTemplateByDoctorIdAndName")
	public PageInfo<PapersTemplates> getTemplateByDoctorIdAndName(
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
			@RequestParam(value = "name", required = false) String name,
			HttpServletRequest request){
		DoctorResponseToken doctorResponseToken = (DoctorResponseToken)request.getAttribute("doctorResponseToken");
		Integer doctorid = doctorResponseToken.getDoctorID();
//		Integer doctorid = 11761;
		try {
			if (name != null) {
				name = URLDecoder.decode(name, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<PapersTemplates> list = null;
        try {
        	list = mbPapersTemplatesService.getTemplateByDoctorIdAndName(doctorid,pageIndex,pageSize,name);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw e;
        }
		return new PageInfo<PapersTemplates>(list);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getTemplateById/{id}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getTemplateById(@PathVariable("id") Integer id){
		PapersTemplates tm = mbPapersTemplatesService.getTemplateById(id);
		return JSON.toJSONString(tm);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/delete/{id}")
	public String delete(@PathVariable("id") Integer id){
		mbContentClassTemplatesService.deleteClassByTid(id);//根据模板id删除模板下所有分类及分类下面的题目
		int num = mbPapersTemplatesService.delete(id);//根据模板id删除模板
		return String.valueOf(num);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/updateTemplateName")
	public String updateTemplateName(HttpServletRequest request){//@RequestParam Integer tId,@RequestParam String paperName
		int tId = Integer.parseInt(request.getParameter("id")==null?"0":request.getParameter("id"));
		String paperName = request.getParameter("name");
//		int isfirstfollowup = Integer.parseInt(request.getParameter("isfirstfollowup"));
		
		int num = mbPapersTemplatesService.updateTemplateName(tId, paperName);//,isfirstfollowup
		return String.valueOf(num);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/update")
	public String updateTemplateName(@RequestParam PapersTemplates template){
		int num = mbPapersTemplatesService.update(template);
		return String.valueOf(num);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getTemplatesMenu/{id}")
    public com.ezhuanbing.api.dao.mybatis.model.templateManager.PapersContentClass getTemplatesMenu(@PathVariable("id") Integer id){         
        return mbPapersTemplatesService.getTemplateMenu(id);
    }
}

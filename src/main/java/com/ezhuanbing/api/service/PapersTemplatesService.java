package com.ezhuanbing.api.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.PapersTemplatesMapper;
import com.ezhuanbing.api.dao.mybatis.model.ContentClassTemplates;
import com.ezhuanbing.api.dao.mybatis.model.OptionTemplates;
import com.ezhuanbing.api.dao.mybatis.model.PapersTemplates;
import com.ezhuanbing.api.dao.mybatis.model.PapersTemplatesCriteria;
import com.ezhuanbing.api.dao.mybatis.model.QuestionsTemplates;
import com.ezhuanbing.api.dao.mybatis.model.templateManager.ContentClassQuestions;
import com.ezhuanbing.api.dao.mybatis.model.templateManager.PapersContentClass;
import com.github.pagehelper.PageHelper;

@Service
public class PapersTemplatesService {
  @Autowired
  private PapersTemplatesMapper mbPapersTemplatesMapper;

  @Autowired
  private ContentClassTemplatesService mbContentClassTemplatesService;
  
  @Autowired
  private QuestionsTemplatesService mbQuestionsTemplatesService;
  
  @Autowired
  private OptionTemplatesService optionTemplatesService;

  public int delete(Integer id) {
    return mbPapersTemplatesMapper.deleteByPrimaryKey(id);
  }

  public PapersTemplates getTemplateById(Integer id) {
    return mbPapersTemplatesMapper.selectByPrimaryKey(id);
  }
  
	public boolean isExist(Integer userId, String title) {// ,Integer
																// paperStatus
		PapersTemplatesCriteria example = new PapersTemplatesCriteria();
		PapersTemplatesCriteria.Criteria c = example.createCriteria();
		c.andUsernameidEqualTo(userId).andPapertileEqualTo(title);//.andPaperdeptidEqualTo(departId).andPaperstatusEqualTo(1);
		List<PapersTemplates> list = mbPapersTemplatesMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list)) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 复制模板
	 * @param paperId
	 * @param name
	 * @param nameId
	 * @param departId
	 */
    public PapersTemplates copy3(Integer paperId,String name,Integer nameId,PapersTemplates t1){
    	if(isExist(nameId, t1.getPapertile())){
//	    	PapersTemplates t1 = this.getTemplateById(paperId);
	    	PapersTemplates t = new PapersTemplates();
			BeanCopier beanCopier = BeanCopier.create(PapersTemplates.class, PapersTemplates.class, false);
			beanCopier.copy(t1, t, null);
			t.setUsername(name);
			t.setUsernameid(nameId);
			t = this.addTemplate(t);
			
			if(null != t){
				//复制分类
				List<ContentClassTemplates> mbClasses = mbContentClassTemplatesService.getAllClass(paperId);
				
				if(CollectionUtils.isNotEmpty(mbClasses)){
					for(ContentClassTemplates mbc:mbClasses){
						ContentClassTemplates dest = new ContentClassTemplates();
						BeanCopier classBeanCopier = BeanCopier.create(ContentClassTemplates.class, ContentClassTemplates.class, false);
						classBeanCopier.copy(mbc, dest, null);
						dest.setPapersid(t.getId()); 
						
						mbContentClassTemplatesService.add(dest);
						if(mbc.getClasspid()!=0){
							dest.setClasspid(dest.getId());
							mbContentClassTemplatesService.update(dest);
						}
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
	        					Integer qid = mbQst.getId();
	        					List<OptionTemplates> options = optionTemplatesService.getAllOptionClass(qst.getId());
	        					if(CollectionUtils.isNotEmpty(options)){
		        					for(OptionTemplates option:options){
		        						OptionTemplates op = new OptionTemplates();
			        					BeanCopier optionBeanCopier = BeanCopier.create(OptionTemplates.class, OptionTemplates.class, false);
			        					optionBeanCopier.copy(option, op, null);
			        					op.setQuestionstemplatesid(qid);
			        					optionTemplatesService.add(op);
		        					}
	        					}
	                    	}
	                    }
					}
				}
				return t;
			}else{
	    		return null;
			}
    	}else{
    		return null;
    	}
    }
    
    public PapersTemplates copy(Integer paperId,String name,Integer nameId,PapersTemplates t1){
    	if(isExist(nameId, t1.getPapertile())){
//	    	PapersTemplates t1 = this.getTemplateById(paperId);
	    	PapersTemplates t = new PapersTemplates();
			BeanCopier beanCopier = BeanCopier.create(PapersTemplates.class, PapersTemplates.class, false);
			beanCopier.copy(t1, t, null);
			t.setUsername(name);
			t.setUsernameid(nameId);
			t.setIsfirstfollowup(0);
			t = this.addTemplate(t);
			
			if(null != t){
				//复制分类
				List<ContentClassTemplates> mbClasses = mbContentClassTemplatesService.getAllClass(paperId);
				if(CollectionUtils.isNotEmpty(mbClasses)){
					for(ContentClassTemplates mbc:mbClasses){
						
						if(mbc.getClasspid()==0){
							ContentClassTemplates dest1 = new ContentClassTemplates();
							BeanCopier classBeanCopier = BeanCopier.create(ContentClassTemplates.class, ContentClassTemplates.class, false);
							classBeanCopier.copy(mbc, dest1, null);
							dest1.setPapersid(t.getId());
							mbContentClassTemplatesService.add(dest1);
							copyQstAndOption(dest1.getId(),mbc.getId());
//							dest.setClasspid(dest.getId());
//							mbContentClassTemplatesService.update(dest);
							List<ContentClassTemplates> list = mbContentClassTemplatesService.getAllClassByPid(mbc.getId(),mbc.getPapersid());
							ContentClassTemplates dest = null;
							for(ContentClassTemplates obj1:list){
								dest = new ContentClassTemplates();
								BeanCopier classBeanCopier1 = BeanCopier.create(ContentClassTemplates.class, ContentClassTemplates.class, false);
								classBeanCopier1.copy(obj1, dest, null);
								dest.setPapersid(t.getId());
								dest.setClasspid(dest1.getId());
								mbContentClassTemplatesService.add(dest);
								copyQstAndOption(dest.getId(),obj1.getId());
						     }
							
						}
						
					}
				}
				return t;
			}else{
	    		return null;
			}
    	}else{
    		return null;
    	}
    }
    
    public void copyQstAndOption(Integer newId,Integer oldId){
    	//复制题目
        List<QuestionsTemplates> qsts = mbQuestionsTemplatesService.getAllQsts(oldId);//原分类id获得分类的所有题目
        if(CollectionUtils.isNotEmpty(qsts)){
        	Integer cid = newId;//dest.getId();//新分类id
        	for(QuestionsTemplates qst:qsts){
        		QuestionsTemplates mbQst = new QuestionsTemplates();
				BeanCopier qstBeanCopier = BeanCopier.create(QuestionsTemplates.class, QuestionsTemplates.class, false);
				qstBeanCopier.copy(qst, mbQst, null);
				mbQst.setContentclassicid(cid);
				mbQuestionsTemplatesService.add(mbQst);
				//下面复制答案
				Integer qid = mbQst.getId();
				List<OptionTemplates> options = optionTemplatesService.getAllOptionClass(qst.getId());
				if(CollectionUtils.isNotEmpty(options)){
					for(OptionTemplates option:options){
						OptionTemplates op = new OptionTemplates();
    					BeanCopier optionBeanCopier = BeanCopier.create(OptionTemplates.class, OptionTemplates.class, false);
    					optionBeanCopier.copy(option, op, null);
    					op.setQuestionstemplatesid(qid);
    					optionTemplatesService.add(op);
					}
				}
        	}
        }
    }
	public List<PapersTemplates> getTemplateByDoctorIdAndName(Integer doctorId, Integer pageIndex, Integer pageSize,
			String name) {
		PageHelper.startPage(pageIndex, pageSize);
		PapersTemplatesCriteria example = new PapersTemplatesCriteria();
		PapersTemplatesCriteria.Criteria c = example.createCriteria();
		c.andUsernameidEqualTo(doctorId);
		if (name == null) {
			name = "";
		}
		c.andPapertileLike("%"+name+"%");
		return mbPapersTemplatesMapper.selectByExample(example);
	}

  public List<PapersTemplates> getAllTemplate(int pageIndex, int pageSize, Integer deptId) {
    PageHelper.startPage(pageIndex, pageSize);
    PapersTemplatesCriteria example = new PapersTemplatesCriteria();
    PapersTemplatesCriteria.Criteria c = example.createCriteria();
    c.andPaperdeptidEqualTo(deptId).andUsernameidEqualTo(0);
    return mbPapersTemplatesMapper.selectByExample(example);
  }

	public List<PapersTemplates> getTemplateByName(int pageIndex, int pageSize, Integer userNameId) {// String name
		PageHelper.startPage(pageIndex, pageSize);
		PapersTemplatesCriteria example = new PapersTemplatesCriteria();
		PapersTemplatesCriteria.Criteria c = example.createCriteria();
		// c.andUsernameEqualTo(name);
		c.andUsernameidEqualTo(userNameId);
		return mbPapersTemplatesMapper.selectByExample(example);
	}

  public PapersTemplates addTemplate(PapersTemplates record) {
    int num = mbPapersTemplatesMapper.insert(record);
    if (num > 0) {
      return record;
    } else {
      return null;
    }
  }

  public int update(PapersTemplates record) {
    return mbPapersTemplatesMapper.updateByPrimaryKeySelective(record);
  }

  /**
   * 根据id修改我的模板名字
   * 
   * @param id
   * @param paperName
   * @return
   */
  public int updateTemplateName(Integer id, String paperName) {//,Integer isfirstfollowup
    PapersTemplates record = mbPapersTemplatesMapper.selectByPrimaryKey(id);
    record.setPapertile(paperName);
//    record.setIsfirstfollowup(isfirstfollowup);
    return mbPapersTemplatesMapper.updateByPrimaryKeySelective(record);
  }
  
  public PapersContentClass getTemplateDetailById(Integer id) {
    PapersContentClass paperCont = new PapersContentClass();
    PapersTemplates paper = getTemplateById(id);
    List<ContentClassQuestions> contQues = mbContentClassTemplatesService.getAllContentQuestion(id);
    paperCont.setId(paper.getId());
    paperCont.setPaperBg(paper.getPaperbg());
    paperCont.setPaperContentClass(contQues);
    paperCont.setPaperDept(paper.getPaperdeptid());
    paperCont.setPaperLevel(paper.getPaperlevel());
    paperCont.setPaperStartDate(paper.getPaperstartdate());
    paperCont.setPaperStatus(paper.getPaperstatus());
    paperCont.setPaperSummary(paper.getPapersummary());
    paperCont.setPaperTile(paper.getPapertile());
    paperCont.setPaperType(paper.getPapertype());
    paperCont.setUserName(paper.getUsername());
    return paperCont;
  }

  public PapersContentClass getTemplateMenu(Integer id) {
    PapersContentClass paperCont = new PapersContentClass();
    PapersTemplates paper = getTemplateById(id);
    List<ContentClassQuestions> contQues = mbContentClassTemplatesService.getAllContentQuestion(id);
    paperCont.setId(paper.getId());
    paperCont.setPaperBg(paper.getPaperbg());
    paperCont.setPaperContentClass(contQues);
    paperCont.setPaperDept(paper.getPaperdeptid());
    paperCont.setPaperLevel(paper.getPaperlevel());
    paperCont.setPaperStartDate(paper.getPaperstartdate());
    paperCont.setPaperStatus(paper.getPaperstatus());
    paperCont.setPaperSummary(paper.getPapersummary());
    paperCont.setPaperTile(paper.getPapertile());
    paperCont.setPaperType(paper.getPapertype());
    paperCont.setUserName(paper.getUsername());
    return paperCont;
  }

  /**
   * 
  * @Title: getDoctorTemplates 
  * @Description: 获取医生模板
  * @param @param doctorId
  * @param @return    设定文件 
  * @return List<PapersTemplates>    返回类型 
  * @throws
   */
  public List<PapersTemplates> getDoctorTemplates(int doctorId){
    PapersTemplatesCriteria papersTemplatesCriteria = new PapersTemplatesCriteria();
    papersTemplatesCriteria.createCriteria().andUsernameidEqualTo(doctorId);
    return mbPapersTemplatesMapper.selectByExample(papersTemplatesCriteria);
  }
}

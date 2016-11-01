package com.ezhuanbing.api.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.ContentClassTemplatesMapper;
import com.ezhuanbing.api.dao.mybatis.model.ContentClassTemplates;
import com.ezhuanbing.api.dao.mybatis.model.ContentClassTemplatesCriteria;
import com.ezhuanbing.api.dao.mybatis.model.templateManager.ContentClassQuestions;
import com.ezhuanbing.api.dao.mybatis.model.templateManager.QuestionsOption;
import com.github.pagehelper.PageHelper;

@Service
public class ContentClassTemplatesService {
	@Autowired
	private ContentClassTemplatesMapper mbContentClassTemplatesMapper;
	
	@Autowired
	private QuestionsTemplatesService mbQuestionsTemplatesService;
	
	public ContentClassTemplates getById(Integer id){
		return mbContentClassTemplatesMapper.selectByPrimaryKey(id);
	}
	
	public ContentClassTemplates add(ContentClassTemplates record){
		int num = mbContentClassTemplatesMapper.insert(record);
		if(num>0){
			return record;
		}else{
			return null;
		}
	}
	/**
	 * 删除模板下面的分类块
	 * @param id
	 * @return
	 */
	public int deleteById(Integer id){
		//先把分类的题给删了
		mbQuestionsTemplatesService.deleteByCid(id);
		return  mbContentClassTemplatesMapper.deleteByPrimaryKey(id);
	}
	
	public int update(ContentClassTemplates record){
		return  mbContentClassTemplatesMapper.updateByPrimaryKeySelective(record);
	}
	/**
	 * 根据模板id删除模板下所有分类及分类下所有题目
	 * @param papersId
	 * @return
	 */
	public int deleteClassByTid(Integer papersId){
		//删除分类下所有题目
		List<Integer> ids = getAllClassIds(papersId);
		for(Integer cid:ids){
			mbQuestionsTemplatesService.deleteByCid(cid);
		}
		ContentClassTemplatesCriteria example = new ContentClassTemplatesCriteria();
		ContentClassTemplatesCriteria.Criteria c = example.createCriteria();
		c.andPapersidEqualTo(papersId);
		return mbContentClassTemplatesMapper.deleteByExample(example);
	}
	/**
	 * 根据模板id获取当前模板的所有分类的id
	 * @param papersId
	 * @return
	 */
	public List<Integer> getAllClassIds(Integer papersId){
		List<ContentClassTemplates> list = getAllClass(papersId);
		List<Integer> ids = new ArrayList<Integer>();
		if(CollectionUtils.isNotEmpty(list)){
			for(ContentClassTemplates mbc:list){
				ids.add(mbc.getId());
			}
		}
		return ids;
	}
	/**
	 * 查询模板的所有分类块，分页展示
	 * @param pageIndex
	 * @param pageSize
	 * @param papersId
	 * @return
	 */
	public List<ContentClassTemplates> getAllClassByTid(int pageIndex,int pageSize,Integer papersId){
		PageHelper.startPage(pageIndex,pageSize);
		ContentClassTemplatesCriteria example = new ContentClassTemplatesCriteria();
		ContentClassTemplatesCriteria.Criteria c = example.createCriteria();
		c.andPapersidEqualTo(papersId);
		return mbContentClassTemplatesMapper.selectByExample(example);
	}
	
	/**
	 * 
	 * @param pid 也就是contentid
	 * @return
	 */
	public List<ContentClassTemplates> getAllClassByPid(Integer pid,Integer paperId){
		ContentClassTemplatesCriteria example = new ContentClassTemplatesCriteria();
		ContentClassTemplatesCriteria.Criteria c = example.createCriteria();
		c.andClasspidEqualTo(pid).andPapersidEqualTo(paperId);
		return mbContentClassTemplatesMapper.selectByExample(example);
	}
	/**
	 * 
	 * @param papersId
	 * @return
	 */
	public List<ContentClassTemplates> getAllClass(Integer papersId){
		ContentClassTemplatesCriteria example = new ContentClassTemplatesCriteria();
		ContentClassTemplatesCriteria.Criteria c = example.createCriteria();
		c.andPapersidEqualTo(papersId);
		return mbContentClassTemplatesMapper.selectByExample(example);
	}
	
	public List<ContentClassQuestions> getAllContentQuestion(Integer papersId){
      List<ContentClassQuestions> contQues = new ArrayList<ContentClassQuestions>();
      List<ContentClassTemplates> conts =getAllClass(papersId);
      for(int i=0;i<conts.size();i++){
        ContentClassQuestions contQue =new ContentClassQuestions();
        ContentClassTemplates cont = conts.get(i);
        List<QuestionsOption> quesOptions = mbQuestionsTemplatesService.getAllQuestOption(cont.getId());
        contQue.setId(cont.getId());
        contQue.setPapersId(cont.getPapersid());
        contQue.setClassCount(cont.getClasscount());
        contQue.setClassName(cont.getClassname());
        contQue.setClassSort(cont.getClasssort());
        contQue.setClassStatus(cont.getClassstatus());
        contQue.setQueOptionTemp(quesOptions);
        contQue.setClassPid(cont.getClasspid());
        contQues.add(i, contQue);
      }
      return contQues;
  }
    public List<ContentClassQuestions> getPaperContentClassMenu(Integer papersId){
      List<ContentClassQuestions> contQues = new ArrayList<ContentClassQuestions>();
      List<ContentClassTemplates> conts =getAllClass(papersId);
      for(int i=0;i<conts.size();i++){
        ContentClassQuestions contQue =new ContentClassQuestions();
        ContentClassTemplates cont = conts.get(i);
        contQue.setId(cont.getId());
        contQue.setPapersId(cont.getPapersid());
        contQue.setClassCount(cont.getClasscount());
        contQue.setClassName(cont.getClassname());
        contQue.setClassSort(cont.getClasssort());
        contQue.setClassStatus(cont.getClassstatus());
        contQue.setClassPid(cont.getClasspid());
        contQues.add(i, contQue);
      }
      return contQues;
  }
    
    public List<ContentClassQuestions> getContentClassTree(Integer id){
      List<ContentClassQuestions> contQues = new ArrayList<ContentClassQuestions>();
      ContentClassTemplates cont =getById(id);
      ContentClassQuestions contQue =new ContentClassQuestions();
      List<QuestionsOption> quesOptions = mbQuestionsTemplatesService.getAllQuestOption(cont.getId());
      contQue.setId(cont.getId());
      contQue.setPapersId(cont.getPapersid());
      contQue.setClassCount(cont.getClasscount());
      contQue.setClassName(cont.getClassname());
      contQue.setClassSort(cont.getClasssort());
      contQue.setClassStatus(cont.getClassstatus());
      contQue.setQueOptionTemp(quesOptions);
      contQue.setClassPid(cont.getClasspid());
      contQues.add(0, contQue);
      return contQues;
  }
	
}

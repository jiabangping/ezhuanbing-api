package com.ezhuanbing.api.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.mapper.QuestionsTemplatesMapper;
import com.ezhuanbing.api.dao.mybatis.model.OptionTemplates;
import com.ezhuanbing.api.dao.mybatis.model.QuestionsTemplates;
import com.ezhuanbing.api.dao.mybatis.model.QuestionsTemplatesCriteria;
import com.ezhuanbing.api.dao.mybatis.model.templateManager.QuestionsOption;
import com.github.pagehelper.PageHelper;

@Service
public class QuestionsTemplatesService {
	@Autowired
	private QuestionsTemplatesMapper mbQuestionsTemplatesMapper;
	
	@Autowired
    private OptionTemplatesService mbOptionTemplates;
    
	
	public QuestionsTemplates add(QuestionsTemplates record){
		int num = mbQuestionsTemplatesMapper.insert(record);
		if(num > 0){
			return record;
		}else{
			return null;
		}
	}
	public List<QuestionsTemplates> getAllQsts(Integer cId){
		QuestionsTemplatesCriteria example = new QuestionsTemplatesCriteria();
		QuestionsTemplatesCriteria.Criteria c = example.createCriteria();
		c.andContentclassicidEqualTo(cId);
		return mbQuestionsTemplatesMapper.selectByExample(example);
	}
	public List<QuestionsTemplates> getAllQstsByPage(int pageIndex,int pageSize,Integer cId){
		PageHelper.startPage(pageIndex,pageSize);
		QuestionsTemplatesCriteria example = new QuestionsTemplatesCriteria();
		QuestionsTemplatesCriteria.Criteria c = example.createCriteria();
		c.andContentclassicidEqualTo(cId);
		return mbQuestionsTemplatesMapper.selectByExample(example);
	}
	/**
	 * 根据分类id删除分类下的所有题目
	 * @param cId
	 * @return
	 */
	public int deleteByCid(Integer cId){
		QuestionsTemplatesCriteria example = new QuestionsTemplatesCriteria();
		QuestionsTemplatesCriteria.Criteria c = example.createCriteria();
		c.andContentclassicidEqualTo(cId);
		List<QuestionsTemplates> list = getAllQsts(cId);//
		if(CollectionUtils.isNotEmpty(list)){
			for(QuestionsTemplates qst:list){
				mbOptionTemplates.deleteByQuesId(qst.getId());//删除所有答案
			}
		}
		return mbQuestionsTemplatesMapper.deleteByExample(example);
	}
	
	/**
	 * 根据题目id删除题目
	 * @param id
	 * @return
	 */
	public int deleteById(Integer id){
		return mbQuestionsTemplatesMapper.deleteByPrimaryKey(id);
	}

    public List<QuestionsOption> getAllQuestOption(Integer cId){
      List<QuestionsOption> quesOptions = new ArrayList<QuestionsOption>();
      List<QuestionsTemplates> ques =getAllQsts(cId);   
      for(int i=0;i<ques.size();i++){
        QuestionsOption quesOption = new QuestionsOption();
        QuestionsTemplates mbQues = ques.get(i);
        List<OptionTemplates> option = mbOptionTemplates.getAllOptionClass(mbQues.getId());
        quesOption.setId(mbQues.getId());
        quesOption.setContentclassicid(mbQues.getContentclassicid());
        quesOption.setQstpicture(mbQues.getQstpicture());
        quesOption.setQstrestultcount(mbQues.getQstrestultcount());
        quesOption.setQsttitle(mbQues.getQsttitle());
        quesOption.setQsttype(mbQues.getQsttype());
        quesOption.setOptionTemp(option);       
        quesOptions.add(i, quesOption);
      }
      return quesOptions;
    }
}

package com.ezhuanbing.api.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.dao.mybatis.model.patientpaper.Answer;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientContentClass;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientQuestionResult;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.PatientQuestions;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.QuestionsAnswer;
import com.ezhuanbing.api.dao.mybatis.model.patientpaper.Result;

import com.ezhuanbing.api.service.PatientContentClassService;
import com.ezhuanbing.api.service.PatientQuestionResultService;
import com.ezhuanbing.api.service.PatientQuestionsService;


@RestController
@RequestMapping("/api/v1/patientContentClass")
public class PatientContenClassController {

	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PatientContentClassService patientContentClassService;
	
	@Autowired
	PatientQuestionResultService patientQuestionResultService;
	
	@Autowired
	PatientQuestionsService patientQuestionsService;
	
	/**
	 * 根据patientPaperId获取概要
	 */
	@RequestMapping(value = "/patientContentClassDetail/{patientPaperId}", method = RequestMethod.GET)
	public PatientContentClass getPatientContentClassDetail(
			@PathVariable("patientPaperId") Integer id) throws Exception  {		
		return patientContentClassService.getPatientContentClassDetail(id);
	}
	
	/**
     * 保存答案
     *//*
    @RequestMapping(value = "/submitQuesResult", method = RequestMethod.PATCH)
    public String submitQuesResult(
        @RequestBody Result results) throws Exception  {
      if(results!=null){
        List<QuestionsAnswer> questionsAnswers = results.getResult();
        
        for(int i=0;i<questionsAnswers.size();i++){
          QuestionsAnswer questionsAnswer = questionsAnswers.get(i);
          List<Answer> answers = questionsAnswer.getAnswers();
          int id = Integer.valueOf(questionsAnswer.getQuestionId());
          for(int j=0;j<answers.size();j++){
            Answer answer = answers.get(j);
            PatientQuestionResult patientQuestionResult = new PatientQuestionResult();
            if("normalScore".equals(answer.getType())){
              patientQuestionResult.setPatientQuestionsId(id);                           
              String value =answer.getValue();
              if(value!=null&&!"".equals(value)){
                patientQuestionResult.setOptionNumber(value);
              }  
            }else{
              patientQuestionResult.setId(Integer.valueOf(answer.getAnswerId()));
              String value =answer.getValue();
              if(value!=null&&!"".equals(value)){
                patientQuestionResult.setOptientResult(value);
              }  
            }
            patientQuestionResult.setOptientStatus(1);         
            patientQuestionResultService.updateByPrimary(patientQuestionResult);
          }
          updatePqtientQuestions(id);
        }
      }
        return "";
    }*/
	

	/**
     * 修改答案
     */
    @RequestMapping(value = "/updateQuesResult", method = RequestMethod.PATCH)
    public void updateQuesResult(
        @RequestBody Result results) throws Exception  {
      if(results!=null){
        List<QuestionsAnswer> questionsAnswers = results.getResult();
        
        for(int i=0;i<questionsAnswers.size();i++){
          QuestionsAnswer questionsAnswer = questionsAnswers.get(i);         
          PatientQuestionResult pqrs = new PatientQuestionResult();
          int id = Integer.valueOf(questionsAnswer.getQuestionId());
          pqrs.setPatientQuestionsId(id); 
          pqrs.setOptientStatus(0); 
          pqrs.setOptientResult("");
          patientQuestionResultService.updateByPrimary(pqrs);
          List<Answer> answers = questionsAnswer.getAnswers();
          for(int j=0;j<answers.size();j++){
            Answer answer = answers.get(j);
            PatientQuestionResult patientQuestionResult = new PatientQuestionResult();
            String answerId =answer.getAnswerId();
            if(answerId!=null && !"".equals(answerId)){
                         
              patientQuestionResult.setPatientQuestionsId(id); 
              patientQuestionResult.setOptientStatus(0); 
              
              if("normalScore".equals(answer.getType())){
                patientQuestionResult.setPatientQuestionsId(id);   
                               
                String value =answer.getValue();
                if(value!=null&&!"".equals(value)){
                  patientQuestionResult.setOptionNumber(value);                
                  patientQuestionResult = patientQuestionResultService.selectQuestionResult(patientQuestionResult);
                  patientQuestionResult.setOptientStatus(1);
                  if(patientQuestionResult.getOptionType()==2){
                    patientQuestionResult.setOptientResult(value);
                  }
                  patientQuestionResultService.updateByPrimary(patientQuestionResult);
                }                              
                  
              }else{
                patientQuestionResult.setId(Integer.valueOf(answerId));
                String value =answer.getValue();
                if(value!=null&&!"".equals(value)){
                  patientQuestionResult.setOptientResult(value);                 
                }  
                patientQuestionResult.setOptientStatus(1);  
                patientQuestionResultService.updateByPrimary(patientQuestionResult);
              }                                                         
            }
          }
          updatePqtientQuestions(id);
        }
      }
      
    }
	
    
	@RequestMapping(value = "/patientContentClassWithQuestions/{classId}", method = RequestMethod.GET)
	public PatientContentClass getPatientContentClassWithQuestions(
			@PathVariable("classId") Integer classId) throws Exception  {
		
		return patientContentClassService.selectPatientContentClassWithQuestions(classId);
	}
    
    
    public void updatePqtientQuestions(int id){
      PatientQuestions patientQuestions = patientQuestionsService.selectByIdPatientContentClass(id);
      PatientQuestionResult patientQuestionResultCondition = new PatientQuestionResult();
      patientQuestionResultCondition.setPatientQuestionsId(id);
      patientQuestionResultCondition.setOptientStatus(1);
      List<PatientQuestionResult> patientQuesResults = patientQuestionResultService.selectPatientQuestionsBy(patientQuestionResultCondition);
      String qstAnswer ="";
      int index = patientQuesResults.size();
      for(int i=0;i<index;i++){
        PatientQuestionResult patientQuestionResult = patientQuesResults.get(i);  
        if(patientQuestionResult.getOptionType()==1){
          qstAnswer += patientQuestionResult.getOptionContent()+":"+patientQuestionResult.getOptientResult()+"><";
        }else{
         
          if(i==(index-1)){            
            if(patientQuestions.getQstType()==0||patientQuestions.getQstType()==1||patientQuestions.getQstType()==4||patientQuestions.getQstType()==5){
              if(patientQuestionResult.getOptionType()==2){
                qstAnswer += patientQuestionResult.getOptientResult();
              }else{
                qstAnswer += patientQuestionResult.getOptionContent();
              }
              
            }else{
              qstAnswer += patientQuestionResult.getOptientResult();
            }
          }else{
            if(patientQuestions.getQstType()==0||patientQuestions.getQstType()==1||patientQuestions.getQstType()==4||patientQuestions.getQstType()==5){
              
              if(patientQuestionResult.getOptionType()==2){
                qstAnswer += patientQuestionResult.getOptientResult()+"><";
              }else{
                qstAnswer += patientQuestionResult.getOptionContent()+"><";
              }
            }else{
              qstAnswer += patientQuestionResult.getOptientResult()+"><";
            }
            
          }
        }
        
      }
      //patientQuestions.setId(id);
      patientQuestions.setQstAnswer(qstAnswer);
      patientQuestions.setQstAnswerDateTime(new Date());
      patientQuestionsService.updateByPrimary(patientQuestions);
    }
}

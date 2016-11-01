package com.ezhuanbing.api.dao.mybatis.model.followplan;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mb_patientfollowupplandetail")
public class PatientFollowUpPlanDetail {
  // 1、在发出后3天提醒患者付费
  // 2、发出后5天如果还未收到反馈，提醒操作
  @Id
  private Integer id;

  private Integer planId;

  private Integer templateId;

  private Integer questionnaireId;

  private String planMark;

  private Integer isActive;

  private String status;// 0:新建,1:已发送给患者,2:患者超时未处理,3:患者已付费,4:患者已反馈,5:随访结束(已归档)

  private Integer planOrder;

  private Integer planResult;
  
  private Date generateTime;
  
  private Date sendTime;
  
  private Date feedbackTime;//反馈时间
  
  private String pushMessage;
  
  private Integer isPay;
  
  private Integer isLastRecord;//1：该条记录是整个plan的最后一条
  
  private Date achiveTime;//归档时间

  private Date nextTime;//下次开始时间
  
  private Integer overTimeStatus;//NULL:default,1:超时,2:已提醒
  
  @Transient
  private String unEqStatu;// 用于状态查询条件

}


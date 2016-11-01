package com.ezhuanbing.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPatientCaseInfoResult {
  
  private int doctorId;
  private int planFollowupDetailId;
  // 诊断
  private String diagnosis;
  // 处理意见
  private String opinionResult;
  // 主诉
  private String complained;
  // 现病史
  private String presentIllness;
  // 既往史
  private String pastHistory;
  // 体格检查
  private String physical;
  // 辅助检查
  private String auxiliaryExamination;
}

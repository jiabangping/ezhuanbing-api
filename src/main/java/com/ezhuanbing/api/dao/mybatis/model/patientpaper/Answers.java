package com.ezhuanbing.api.dao.mybatis.model.patientpaper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answers {          
  private List<Answer> answers;
}

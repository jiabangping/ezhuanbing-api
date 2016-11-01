package com.ezhuanbing.api.scheduledtask;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.ezhuanbing.api.dao.mybatis.model.followplan.PatientFollowUpPlanDetail;
import com.ezhuanbing.api.service.PatientFollowUpPlanDetailService;
import com.ezhuanbing.api.service.PatientPaperGenerateService;
import com.ezhuanbing.api.service.PushMsgService;
import com.ezhuanbing.api.tools.PropertyFileOperation;

@Component
@ComponentScan(basePackages = "com.ezhuanbing.api.service")
public class FollowUpDetailScheduledTask {
	private static final Logger log = LoggerFactory.getLogger(FollowUpDetailScheduledTask.class);
    
	public FollowUpDetailScheduledTask(){
		Timer timer = new Timer();
		long period = Long.valueOf(PropertyFileOperation.getPropertyByName("application", "followup.period"));
		timer.schedule(new MyTimerTask1(), 1000, period);
	};
	
	@Autowired
	PatientFollowUpPlanDetailService patientFollowUpPlanDetailService;
	@Autowired
	PatientPaperGenerateService patientPaperGenerateService;
	@Autowired
	PushMsgService pushMsgService;
	
	@PropertySource("classpath:application.properties")
	class MyTimerTask1 extends TimerTask {
		
		@SuppressWarnings("deprecation")
		public void run() {
			log.debug("定时推送随访计划方法开始执行!");
			Date d = new Date();
			Integer followupStartHour = Integer.valueOf(PropertyFileOperation.getPropertyByName("application", "followup.start.hour"));
			Integer followupEndHour = Integer.valueOf(PropertyFileOperation.getPropertyByName("application", "followup.end.hour"));
			if (d.getHours() < followupStartHour || d.getHours() > followupEndHour) {
				return;
			};
			
			int predays = Integer.valueOf(PropertyFileOperation.getPropertyByName("application", "followup.predays"));
			List<Map<String, Object>> planDetailList = patientFollowUpPlanDetailService.getPlanDetailToPatient(predays);
			if (planDetailList.isEmpty()) {
				return;
			}
			for (Map<String, Object> m : planDetailList) {
				try {
					int planOrder = (int)(long) m.get("planOrder");
					if (planOrder > 1) {
						//当随访计划不是第一条时，需判断前一条是否完结，即status是否等于5，如果是5才推送，否则不推送。
						int planId = (int) m.get("pid");
						Map<String, Object> t = patientFollowUpPlanDetailService.getPlanDetailToPatient2(planId, planOrder-1);
						if (t != null && (Integer.valueOf((String)t.get("status")) != 5)) {
							continue;
						}
					}
					
					int patientId = (int) m.get("patientId");
					int doctorId = (int) m.get("doctorId");
					int planDetailId = (int)(long) m.get("id");
					int templateId = (int)(long) m.get("templateId");
					log.debug("当前推送的planDetailId是：" + planDetailId);
					boolean isOk = patientPaperGenerateService.GeneratePaper(patientId, doctorId, planDetailId, templateId);
					
				    if(!isOk) {
				    	log.error("随访表生成失败，请稍后重试！");
				    } else {
				    	patientFollowUpPlanDetailService.updateBySelective(PatientFollowUpPlanDetail.builder().id(planDetailId).sendTime(new Date()).isActive(1).build());
				    	log.debug("planDetailId是：" + planDetailId + "的随访计划推送成功！");
				    }
				    
				    pushMsgService.sendFellowupFee(patientId, planDetailId);
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
			log.debug("定时推送随访计划方法执行结束!");
		}
	}
}

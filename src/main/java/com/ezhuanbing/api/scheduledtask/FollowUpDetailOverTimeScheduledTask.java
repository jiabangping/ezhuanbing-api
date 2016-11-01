package com.ezhuanbing.api.scheduledtask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.service.PatientFollowUpPlanDetailService;
import com.ezhuanbing.api.tools.PropertyFileOperation;

@PropertySource("classpath:application.properties")
@RestController
@ComponentScan(basePackages = "com.ezhuanbing.api.service")
public class FollowUpDetailOverTimeScheduledTask {
	private static final Logger log = LoggerFactory.getLogger(FollowUpDetailOverTimeScheduledTask.class);
	
	@Autowired
	PatientFollowUpPlanDetailService patientFollowUpPlanDetailService;
	
	@Scheduled(cron="${followup.detail}")
	public void reportCurrentTime() {
		log.debug("扫描超时的planDetail开始!");
		int overtimeDays = Integer.valueOf(PropertyFileOperation.getPropertyByName("application", "followup.overtime.days"));
		try {
			patientFollowUpPlanDetailService.updateDetailOvertimeStatus(overtimeDays);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		}
		log.debug("扫描超时的planDetail结束!");
	}
}

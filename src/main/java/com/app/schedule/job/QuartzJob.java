package com.app.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;


public class QuartzJob implements Job{

	public void work() {
		System.out.println("Quartz的任务调度！！！");
	}

	@Scheduled(cron="0 33/3 * * * ?")
	public void workAnn() {
		System.out.println("Quartz的任务调度！！！");
	}
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("Quartz的任务调度！！！");
	}

}
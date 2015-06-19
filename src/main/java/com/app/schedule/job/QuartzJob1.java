package com.app.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class QuartzJob1 implements Job{

	public void work() {
		System.out.println("Test！！！");
	}
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("Test！！！");
	}

}
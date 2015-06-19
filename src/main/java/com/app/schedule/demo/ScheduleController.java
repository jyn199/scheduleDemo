package com.app.schedule.demo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.schedule.job.QuartzJob;
import com.app.schedule.job.QuartzJob1;

@Controller
@RequestMapping("/")
public class ScheduleController {

	@Resource(name="startQuertz")
	private Scheduler startQuertz;
	
	@RequestMapping(value = "schedule")
	public ModelAndView appIndex(HttpServletRequest request,
			HttpServletResponse response) throws SchedulerException {
		ModelAndView mav = new ModelAndView("/schedule/index");
		springType();
//		quartzType();
		return mav;
	}

	@RequestMapping(value = "")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/index");
		return mav;
	}

	private void springType() throws SchedulerException {
		JobDetail job2 = JobBuilder.newJob(QuartzJob1.class)
				.withIdentity("job2", "group2").build();
		Trigger trigger2 = TriggerBuilder
				.newTrigger()
				.withIdentity(new TriggerKey("trigger2", "group2"))
				.startNow()
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(2).repeatForever())
				.build();
		startQuertz.scheduleJob(job2, trigger2);
	}

	private void quartzType() throws SchedulerException {
		try {
			// Scheduler instances are produced by a SchedulerFactory
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler scheduler = sf.getScheduler();

			// JobDetail Conveys the detail properties of a given Job instance.
			// JobDetails are to be created/defined with JobBuilder.
			// JobBuilder无构造函数，所以只能通过JobBuilder的静态方法newJob(Class<? extends Job>
			// jobClass)生成JobBuilder实例
			// withIdentity(String name,String
			// group)参数用来定义jobKey，如果不设置，也会自动生成一个独一无二的jobKey用来区分不同的job
			// build()方法 Produce the JobDetail instance defined by this
			// JobBuilder.
			JobDetail job1 = JobBuilder.newJob(QuartzJob.class)
					.withIdentity("job1", "group1").build();
			
			JobDetail job2 = JobBuilder.newJob(QuartzJob1.class)
					.withIdentity("job2", "group2").build();

			// use TriggerBuilder to instantiate an actual Trigger
			// withIdentity(String name,String
			// group)参数用来定义TriggerKey，如果不设置，也会自动生成一个独一无二的TriggerKey用来区分不同的trigger
			Trigger trigger1 = TriggerBuilder
					.newTrigger()
					.withIdentity(new TriggerKey("trigger1", "group1"))
					.startNow()
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(2).repeatForever())
					.build();
			
			Trigger trigger2 = TriggerBuilder
					.newTrigger()
					.withIdentity(new TriggerKey("trigger2", "group2"))
					.startNow()
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(2).repeatForever())
					.build();

			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job1, trigger1);

			// Start up the scheduler
			scheduler.start();
			
			// 动态添加
			scheduler.scheduleJob(job2, trigger2);

			// 当前主线程睡眠2秒
			System.out.println(Thread.currentThread().getName());
			Thread.sleep(5 * 1000);

			scheduler.shutdown(true);

		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

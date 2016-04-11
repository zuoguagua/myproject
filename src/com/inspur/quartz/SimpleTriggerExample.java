package com.inspur.quartz;


import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SimpleTriggerExample {
	public static void main(String[] args) throws Exception{
		JobDetail job = JobBuilder.newJob(HelloJob.class)
				.withIdentity("dummyJobName", "group1").build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("dummyTriggerName","group1")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
				.build();
		
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}
}




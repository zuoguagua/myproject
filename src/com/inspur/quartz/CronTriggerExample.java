package com.inspur.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


public class CronTriggerExample {
	public static void main(String[] args) throws Exception{
		
		JobKey jobKeyA = new JobKey("jobA", "group1");
    	JobDetail jobA = JobBuilder.newJob(JobA.class)
		.withIdentity(jobKeyA).build();
    	
    	JobKey jobKeyB = new JobKey("jobB", "group1");
    	JobDetail jobB = JobBuilder.newJob(JobB.class)
		.withIdentity(jobKeyB).build();
    	
    	JobKey jobKeyC = new JobKey("jobC", "group1");
    	JobDetail jobC = JobBuilder.newJob(JobC.class)
		.withIdentity(jobKeyC).build();
		
		
		/**
		JobKey jobKey = new JobKey("dummyJobName", "group1");
		JobDetail job = JobBuilder.newJob(HelloJob.class)
				.withIdentity(jobKey)
				.build();
		*/
    	
    	
    	Trigger trigger1 = TriggerBuilder
    			.newTrigger()
    			.withIdentity("dummyTriggerName1", "group1")
    			.withSchedule(
    				CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
    			.build();
    	
    	Trigger trigger2 = TriggerBuilder
    			.newTrigger()
    			.withIdentity("dummyTriggerName2", "group1")
    			.withSchedule(
    				CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
    			.build();
    	
    	
    	Trigger trigger3 = TriggerBuilder
    			.newTrigger()
    			.withIdentity("dummyTriggerName3", "group1")
    			.withSchedule(
    				CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
    			.build();
    	
    	
    	/**
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("dummyTriggerName","group1")
				.withSchedule(
						CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
				.build();
		*/
    	
    	
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		
		/**
		scheduler.getListenerManager().addJobListener(
	    		new HelloJobListener(), KeyMatcher.keyEquals(jobKey)
	    	);
		*/
		scheduler.start();
		//scheduler.scheduleJob(job, trigger);
		
		scheduler.scheduleJob(jobA, trigger1);
    	scheduler.scheduleJob(jobB, trigger2);
    	scheduler.scheduleJob(jobC, trigger3);
		
	}
}

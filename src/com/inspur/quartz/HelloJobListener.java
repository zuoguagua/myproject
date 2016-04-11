package com.inspur.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class HelloJobListener implements JobListener {
	
	public static final String LISTENER_NAME = "dummyJobListenerName";
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return LISTENER_NAME;
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub
		System.out.println("jobExecutionVetoed");
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		// TODO Auto-generated method stub
		String jobName = context.getJobDetail().getKey().toString();
		System.out.println("jobToBeExecuted.");
		System.out.println("Job : "+jobName+ " is going to start...");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		// TODO Auto-generated method stub
		System.out.println("jobWasExecuted");
		
		String jobName = context.getJobDetail().getKey().toString();
		System.out.println("Job : " + jobName + " is finished...");
 
		if (!jobException.getMessage().equals("")) {
			System.out.println("Exception thrown by: " + jobName
				+ " Exception: " + jobException.getMessage());
		}
 
	}

	

}

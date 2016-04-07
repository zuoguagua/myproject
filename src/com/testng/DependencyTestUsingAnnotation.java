package com.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DependencyTestUsingAnnotation {
	String message = ".com";
	MessageUtil messageUtil = new MessageUtil(message);

	/**
	@Test
	public void testPrintMessage(){
		System.out.println("testPrintMessage()");
		message = ".com";
		Assert.assertEquals(message, messageUtil.printMessage());
	}
	
	@Test(dependsOnMethods = {"initEnvironmentTest"})
	public void testSalutationMessage(){
		System.out.println("testsalutationMessage()");
		message = "tutorialspoint"+".com";
		Assert.assertEquals(message, messageUtil.salutationMessage());
	}
	
	@Test
	public void initEnvironmentTest(){
		System.out.println("initEnvironmentTest");
	}
	
	*/
	
	@Test(groups = {"init"})
	public void testPrintMessage(){
		System.out.println("testPrintMessage()");
		message = ".com";
		Assert.assertEquals(message, messageUtil.printMessage());
	}
	
	@Test(dependsOnGroups = {"init*"})
	public void testSalutationMessage(){
		System.out.println("testsalutationMessage()");
		message = "tutorialspoint"+".com";
		Assert.assertEquals(message, messageUtil.salutationMessage());
	}
	
	@Test(groups = {"init"})
	public void initEnvironmentTest(){
		System.out.println("initEnvironmentTest");
		//int a = 0;
		//int b = 1/a;
	}
	
}

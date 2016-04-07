package com.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IgnoreTest {
	String message = "zuoqiang";
	MessageUtil messageUtil = new MessageUtil(message);
	
	@Test(enabled = false)
	public void testPrintMessage(){
		System.out.println("testPrintMessage()");
		message = "zuoqiang";
		Assert.assertEquals(message, messageUtil.printMessage());
	}
	
	@Test
	public void testSalutationMessage(){
		System.out.println("testSalutationMessage()");
		message = "Hi"+"zuoqiang";
		Assert.assertEquals(message, messageUtil.salutationMessage());
	}
	
}

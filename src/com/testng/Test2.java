package com.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Test2 {
	String message = "zuoqiang";
	MessageUtil messageUtil = new MessageUtil(message);
	
	@Test
	public void testSalutationMessage(){
		System.out.println("Inside testSalutationMessage()");
		message = "Hi" + "zuoqiang";
		Assert.assertEquals(message, messageUtil.salutationMessage());
	}
}

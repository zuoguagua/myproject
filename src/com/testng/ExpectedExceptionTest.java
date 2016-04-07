package com.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ExpectedExceptionTest {
	String message = "zuoqiang";
	MessageUtil messageUtil = new MessageUtil(message);
	
	@Test(expectedExceptions = ArithmeticException.class)
	public void testPrintMessageException(){
		System.out.println("testPrintMessageException()");
		messageUtil.printMessageException();
	}
	
	@Test
	public void testSalutationMessage(){
		System.out.println("testsalutationMessage()");
		message = "tutorialspoint"+message;
		Assert.assertEquals(message, messageUtil.salutationMessage());
	}
}

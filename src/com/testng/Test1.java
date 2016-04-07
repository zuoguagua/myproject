package com.testng;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;


public class Test1 {
	String message = "zuoqiang";
	MessageUtil  messageUtil = new MessageUtil(message);
	@Test
	public void testPrintMessage(){
		System.out.println("Inside testPrintMessage()");
		AssertJUnit.assertEquals(message,messageUtil.printMessage());
	}
}

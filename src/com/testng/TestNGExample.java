package com.testng;

import org.testng.Assert;
import org.testng.annotations.Test;




public class TestNGExample {
	
	
	String message = "Hello World";
	
	MessageUtil messageutil = new MessageUtil(message);
	
	@Test
	public void testPrintMessage(){
		
		Assert.assertEquals(message, messageutil.printMessage());
	}
}

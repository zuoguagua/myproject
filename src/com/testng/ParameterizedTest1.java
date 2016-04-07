package com.testng;

import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

public class ParameterizedTest1 {
	@Test
	@Parameters("myName")
	public void parameterTest(String myName){
		System.out.println("Parameterized value is :"+myName);
	}
}

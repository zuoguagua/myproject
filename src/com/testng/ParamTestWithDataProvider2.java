package com.testng;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

public class ParamTestWithDataProvider2 {
	
	
	@DataProvider(name = "test1")
	public static Object[][] primeNumbers(){
		return new Object[][] {{new Bean("Bean",111)}};
	}
	
	@Test(dataProvider = "test1")
	public void testMethod(Bean myBean){
		System.out.println(myBean.getVal()+" "+myBean.getI());
	}
}

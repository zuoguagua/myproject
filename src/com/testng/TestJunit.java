package com.testng;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestJunit {
	@Test
	public void testAdd(){
		String str = "Junit testing using TestNG.";
		assertEquals("Junit testing using TestNG.",str);
	}
}

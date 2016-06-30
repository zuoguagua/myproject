package com.ant.example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculatorTest {
	@Test
	public void testGetValue1(){
		int x=10,y=20;
		int expected=30;
		int actual = new Calculator().getValue(x,y);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testGetValue2(){
		int x=30,y=20;
		int expected=10;
		int actual = new Calculator().getValue(x,y);
		assertEquals(expected,actual);
	}
}

package com.testng;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * org.testng.internal.junit.ArrayAssert
 * org.testng.AssertJUnit
 * public class AssertJUnit extends ArrayAsserts
 * @author zuoqiang
 *
 */

public class AssertJUnitTest {
	
	/**
	 * testAssertEquals
	 * asserts that two booleans are equal
	 * boolean byte[] byte char int long short 
	 * assertEquals(boolean expected, boolean actual)
	 */
	@Test
	public void testAssertEquals0(){
		AssertJUnit.assertEquals(false, false);
	}
	
	/**
	 * testAssertEquals
	 * Asserts that two doubles are concerning a delta
	 * float double 
	 * assertEquals(double expected, double actual, double delta)
	 */
	@Test
	public void testAssertEquals1(){
		AssertJUnit.assertEquals(0.3, 0.3, 0.0);
	}
	
	
	@Test
	public void testAssertFalse(){
		AssertJUnit.assertFalse(false);
	}
	
	
	@Test
	public void testAssertNotNull(){
		String a = "aaa";
		AssertJUnit.assertNotNull(a);
	}
	
	
	@Test
	public void testAssertNotSame(){
		String a = "aa";
		String b = "bb";
		AssertJUnit.assertNotSame(a, b);
	}
	
	@Test
	public void testAssertTrue(){
		AssertJUnit.assertTrue(true);
	}
	
	@Test
	public void testFail(){
		AssertJUnit.fail();
	}
}

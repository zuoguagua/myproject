package com.testng;

import org.junit.Before;
import org.junit.Test;

public class MessageUtilTest {
	public static MessageUtil messageutil = new MessageUtil("HE");
	@Before
	public void setUp() throws Exception {
	}



	@Test
	public void testPrintMessage() {
		messageutil.printMessage();
	}

}

package com.log4j;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class log4jExample {
	static Logger log = Logger.getLogger(log4jExample.class.getName());
	
	public void log4jTest()throws IOException,SQLException{
		log.debug("debug message!");
		log.info("info message!");
	}
}

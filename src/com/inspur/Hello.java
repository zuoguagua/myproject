package com.inspur;

import java.io.IOException;

import com.inspur.host.hostList;
import com.inspur.util.SslTest;
import com.log4j.log4jExample;



public class Hello {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, Exception {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		

		
		//hostList hostlist = new hostList();
		//hostlist.sendGetRequestHostList();
		/**
		try{
			SslTest st = new SslTest();
			String a = st.getRequest("https://10.166.15.160/api/", 3000);
			System.out.println(a);
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		
		//log4jExample log4je = new log4jExample();
		
		//log4je.log4jTest();
		//currentTimeMillis ct = new currentTimeMillis();
		
		//currentTimeMillis.getStsrt();
		
		/*
		 * WebDriver driver = new FirefoxDriver();
		 * driver.get("http://www.baidu.com");
		
			driver.manage().window().maximize();
		
			WebElement txtbox = driver.findElement(By.name("wd"));
			txtbox.sendKeys("Gelln");
			txtbox.submit();
		
			WebElement btn = driver.findElement(By.id("su"));
			btn.click();
		
			driver.close();
		 */
		
		
		System.out.println("OK");
	}

}

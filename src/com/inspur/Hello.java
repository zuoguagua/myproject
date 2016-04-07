package com.inspur;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.inspur.common.*;



public class Hello {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		
		currentTimeMillis ct = new currentTimeMillis();
		
		ct.getStsrt();
		
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

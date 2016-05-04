package com.testng;

import org.testng.Assert;
import org.testng.annotations.Test;



public class TestEmployeeDetails {
	EmpBusinessLogic empBussinessLogic = new EmpBusinessLogic();
	EmployeeDetails employee = new EmployeeDetails();
	
	@Test
	public void testCalculateAppriasal(){
		employee.setName("zuoqiang");
		employee.setAge(25);
		employee.setMonthlySalary(8000);
		
		double appraisal = empBussinessLogic.calculateAppraisal(employee);
		Assert.assertEquals(500,appraisal,0.0,"500");
	}
	@Test
	public void testCalculateYearlySalay(){
		employee.setName("zuoqiang");
		employee.setAge(25);
		employee.setMonthlySalary(8000);
		double salary = empBussinessLogic.calculateYearlySalary(employee);
		Assert.assertEquals(96000,salary,0.0,"8000");
	}
	
}

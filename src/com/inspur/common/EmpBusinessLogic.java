package com.inspur.common;

public class EmpBusinessLogic {
	
	public double calculateYearlySalary(EmployeeDetails employeedetails){
		double yearlySalay = 0;
		yearlySalay = employeedetails.getMonthlySalary() * 12;
		return yearlySalay;
	}
	
	public double calculateAppraisal(EmployeeDetails emloyeedetails){
		double appraisal = 0;
		if(emloyeedetails.getMonthlySalary() < 10000){
			appraisal = 500;
		}else{
			appraisal = 1000;
		}
		return appraisal;
	}
}

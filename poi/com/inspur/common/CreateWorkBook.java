package com.inspur.common;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateWorkBook {

	/**
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		FileOutputStream out = new FileOutputStream(new File("createworkbook.xlsx"));
		workbook.write(out);
		out.close();
		System.out.println("11");
		
		
	}

	
	
	
}

package com.selenium.core.hybrid.util;

import java.util.Hashtable;

public class Datautil {
	public static Object[][] getdata(Xls_Reader reader,String TestunderExecution){
		
	String sheetName="Data";
	// reads data for only testCaseName
	
	int testStartRowNum=1;
	while(!reader.getCellData(sheetName, 0, testStartRowNum).equals(TestunderExecution)){
		testStartRowNum++;
	}
	System.out.println("Test starts from row - "+ testStartRowNum);
	int colStartRowNum=testStartRowNum+1;
	int dataStartRowNum=testStartRowNum+2;
	
	// calculate rows of data
	int rows=0;
	while(!reader.getCellData(sheetName, 0, dataStartRowNum+rows).equals("")){
		rows++;
	}
	System.out.println("Total rows are  - "+rows );
	
	//calculate total cols
	int cols=0;
	while(!reader.getCellData(sheetName, cols, colStartRowNum).equals("")){
		cols++;
	}
	System.out.println("Total cols are  - "+cols );
	Object[][] data=new Object[rows][1];
	int rowdata=0;
	Hashtable<String,String> table=null;
	//read the data
	for(int rNum=dataStartRowNum;rNum<dataStartRowNum+rows;rNum++){
		
		table=new Hashtable<String,String>();
		
		for(int cNum=0;cNum<cols;cNum++){
			String key=reader.getCellData(sheetName, cNum, colStartRowNum);
			String value=reader.getCellData(sheetName, cNum, rNum);
			table.put(key, value);
		}
		data[rowdata][0]=table;
		rowdata++;
	}
	return data;


 }
	
	public static boolean isSkip(Xls_Reader reader,String TestunderExecution){
		int rows=reader.getRowCount(constants.TESTCASES_SHEET);
		
		for(int rnum=2;rnum<rows;rnum++){
			if(reader.getCellData(constants.TESTCASES_SHEET, constants.TCID_col, rnum).equals(TestunderExecution)){
				if(reader.getCellData(constants.TESTCASES_SHEET, constants.Runmode_col, rnum).equals("Y")){
					return false;
				}else{
					return true;
				}
				
			}
		}
		return true;
		
	}


}

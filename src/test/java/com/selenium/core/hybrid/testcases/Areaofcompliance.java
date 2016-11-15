package com.selenium.core.hybrid.testcases;

import java.awt.AWTException;
import java.text.ParseException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.selenium.core.hybrid.Keywords;
import com.selenium.core.hybrid.util.Datautil;
import com.selenium.core.hybrid.util.ExtentManager;
import com.selenium.core.hybrid.util.Xls_Reader;
import com.selenium.core.hybrid.util.constants;

public class Areaofcompliance {
	ExtentReports rep=ExtentManager.getInstance();
	ExtentTest test;
	Xls_Reader reader=new Xls_Reader(constants.SUITEA_XLS);
	String TestunderExecution="Areaofcompliance";
	Keywords app;
	@Test(dataProvider="getdata")
	public void dologin(Hashtable<String,String> data) throws InterruptedException, AWTException, ParseException {
		
		test=rep.startTest(TestunderExecution);
		test.log(LogStatus.INFO, data.toString());
		
		if(Datautil.isSkip(reader, TestunderExecution) || data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Skipping the test because runmode N");
			throw new SkipException("Skipping the test");
		}
		test.log(LogStatus.INFO, "Starting the gmail test"); 
		
		app=new Keywords(test);
		
		test.log(LogStatus.INFO, "Executing keywords");
		app.execute_keyword(TestunderExecution, reader,data);
		
	//	app.getgenerickeywords().takescreenshot();
		//add screenshot for failure
		//app.getgenerickeywords().reportFail("Fail");	
		
	}
	
	@AfterTest
	public void close(){
		if(rep!=null){
			rep.endTest(test);
			rep.flush();
		}
		app.getgenerickeywords().closebrowser();
	}
	
	@DataProvider
	public Object[][] getdata(){
		return Datautil.getdata(reader, TestunderExecution);
	}
		
		    

}

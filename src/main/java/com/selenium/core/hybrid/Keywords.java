package com.selenium.core.hybrid;

import java.awt.AWTException;
import java.text.ParseException;
import java.util.Hashtable;

import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.selenium.core.hybrid.util.Xls_Reader;
import com.selenium.core.hybrid.util.constants;


public class Keywords {
	ExtentTest test;
	public AppKeywords app;
	public Keywords(ExtentTest test) {
		this.test=test;
	}

	public void execute_keyword(String TestunderExecution,
			Xls_Reader reader,
			Hashtable<String,String> testdata) throws InterruptedException, AWTException, ParseException{
		/*GenericKeyWords app=new GenericKeyWords();
		app.openbroswer("mozilla");
		app.navigate("url");
		app.click("gmail_xpath");
		app.input("Username_id","karthick.inform@gmail.com");
		app.click("Next_button_id");*/
		
		
		 app=new AppKeywords(test);
		//app.reportFail("Failure");
		
		int rowNum=reader.getRowCount(constants.Keywords_sheet);
		//System.out.println("Total Rows ---- "+rowNum);
		for(int rNum=2;rNum<=rowNum;rNum++){
			String TCID=reader.getCellData(constants.Keywords_sheet,constants.TCID_col, rNum);
			if(TestunderExecution.equals(TCID)){
				String Keyword=reader.getCellData(constants.Keywords_sheet, constants.Keyword_col, rNum);
				String Object=reader.getCellData(constants.Keywords_sheet, constants.Object_col, rNum);
				String key=reader.getCellData(constants.Keywords_sheet, constants.Data_col, rNum);
				String Data=testdata.get(key);
				
				test.log(LogStatus.INFO,TCID+"---"+Keyword+"---"+Object+"---"+Data);				
				String result="";
				if(Keyword.equals("openbroswer"))
					result=app.openbroswer(Data);
				if(Keyword.equals("navigate"))
					result=app.navigate(Object);
				if(Keyword.equals("click"))
					result=app.click(Object);
				if(Keyword.equals("input"))
					result=app.input(Object, Data);
				if(Keyword.equals("closebrowser"))
					result=app.closebrowser();
				if(Keyword.equals("verifylogindetails"))
					result=app.verifylogindetails(testdata);
				if(Keyword.equals("verifyElementPresent"))
					result=app.verifyElementPresent(Object);
				if(Keyword.equals("mouseover"))
					result=app.mouseover(Object);
				if(Keyword.equals("wait"))
					result=app.wait(key);
				if(Keyword.equals("clicklinktext"))
					result=app.clicklinktext(Data);
				if(Keyword.equals("clickoninfocenter"))
					result=app.clickoninfocenter(testdata);
				if(Keyword.equals("clickonbutton"))
					result=app.clickonbutton(Object,key);
				if(Keyword.equals("clickoninfoport"))
					result=app.clickoninfoport(testdata);
				if(Keyword.equals("MultiListSelect"))
					result=app.MultiListSelect(Object,Data);
				if(Keyword.equals("enterValueinRTF"))
					result=app.enterValueinRTF(Object,Data);
				if(Keyword.equals("SingleListSelect"))
					result=app.SingleListSelect(Object,Data);
				if(Keyword.equals("SelectValueInList"))
					result=app.SelectValueInList(Object,Data);
				if(Keyword.equals("DateSelection"))
					result=app.DateSelection(Object,Data);
				if(Keyword.equals("clickOnLink"))
					result=app.clickOnLink(key);
				if(Keyword.equals("AddValueInGrid"))
					result=app.AddValueInGrid(Object,key);
				if(Keyword.equals("SelectMultiValueInList"))
					result=app.SelectMultiValueInList(Object,Data);
				
				
				if(!result.equals(constants.PASS)){
					app.reportFail(result);
					Assert.fail(result);
				}
						
			}
		}
		
	}
	
	public AppKeywords getgenerickeywords(){
		return app;
	}

}

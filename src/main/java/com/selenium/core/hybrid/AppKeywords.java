package com.selenium.core.hybrid;

import java.util.Hashtable;
import org.openqa.selenium.By;
import com.relevantcodes.extentreports.ExtentTest;
import com.selenium.core.hybrid.util.constants;

public class AppKeywords extends GenericKeyWords {

	public AppKeywords(ExtentTest test) {
		super(test);

	}
	
	public String verifylogindetails(Hashtable<String, String> testdata) {
		// TODO Auto-generated method stub
		//String expectedname=testdata.get("Name");
		//String expectedID=testdata.get("username");
		return constants.PASS;
		
	}
	
	public String clickoninfocenter(Hashtable<String, String> testdata) throws InterruptedException{
		String expected=testdata.get("Infocenter");
		System.out.println(expected+"----------");
		try{
		driver.findElement(By.id("100016")).click();
		}catch(Exception e){
			return constants.FAIL;
		}
		return constants.PASS;
		
	}
	
	public String clickoninfoport(Hashtable<String, String> testdata) throws InterruptedException {
	
		String expected=testdata.get("Formname");
		//System.out.println(expected);
		try{
		driver.findElement(By.linkText(expected)).click();
		}catch(Exception e){
			return constants.FAIL;
		}
		wait("5000");
		
		return constants.PASS;
	}
	
	
		

	
	

}

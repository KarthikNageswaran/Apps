package com.selenium.core.hybrid;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.selenium.core.hybrid.util.constants;

public class GenericKeyWords {

	WebDriver driver;
	Properties prop;
	ExtentTest test;
	Actions act;
	Robot robot;
	public GenericKeyWords(ExtentTest test){
		
		this.test=test;
		
		prop=new Properties();
		try {
			FileInputStream fs=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\Project.properties");
			prop.load(fs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String openbroswer(String browserType) throws AWTException{
		test.log(LogStatus.INFO, "Open the browser");
		
		if(prop.getProperty("Grid").equals("Y")){
			DesiredCapabilities cap=null;
			if(browserType.equals("mozilla")){
				
				cap=DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setJavascriptEnabled(true);
				//cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				
			}
			else if(browserType.equals("chrome")){
				cap=DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
							
			}
			
				try {
					driver=new RemoteWebDriver(new URL("http://172.27.16.133:4444/wd/hub"), cap);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		}else{
		
				if(browserType.equals("mozilla")){
					driver=new FirefoxDriver();
				}
				else if(browserType.equals("chrome")){
					System.setProperty("webdriver.chrome.driver", "C:\\Users\\knageswaran\\Downloads\\chromedriver_win32\\chromedriver.exe");
					driver=new ChromeDriver();
				
				}
				else if(browserType.equals("ie")){
					System.setProperty("webdriver.ie.driver", "C:\\Users\\knageswaran\\Desktop\\IEDriverServer.exe");
					driver=new InternetExplorerDriver();
				
				}
			}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		act=new Actions(driver);
		robot = new Robot();
		return constants.PASS;
	}
	
	public String navigate(String url){
		test.log(LogStatus.INFO, "URL is "+prop.getProperty(url));
		
		driver.get(prop.getProperty(url));
		return constants.PASS;
	}
	
	public String click(String locator){
		test.log(LogStatus.INFO,"Clicking the "+prop.getProperty(locator) );
		
		WebElement e=getElement(locator);
		e.click();
		return constants.PASS;
	}
	
	public String input(String locator,String data){
		test.log(LogStatus.INFO, "Typing Input --"+prop.getProperty(locator));
		
		WebElement e=getElement(locator);
		e.sendKeys(data);
		
		return constants.PASS;

	}
	
	public String verifyText(String locator,String ExpectedText){
		
		return constants.PASS;
	}
	
	public String closebrowser() {
		test.log(LogStatus.INFO, "Closing the browser");
		driver.quit();
		return constants.PASS;
	}
	
	public String verifyElementPresent(String locator){
		boolean result=isElementPresent(locator);
		if(result)
			return constants.PASS;
		else
			return constants.FAIL+"-- could not find the element"+locator;
	}
	
	public String verifyElementNotPresent(String locator){
		boolean result=isElementPresent(locator);
		if(result)
			return constants.FAIL;
		else
			return constants.PASS+"-- could find the element"+locator;
	}
	public String wait(String data) throws InterruptedException{
		int time=Integer.parseInt(data);
		Thread.sleep(time);
		return data;
		
	}
	public String mouseover(String locator) throws InterruptedException{
		wait("6000");
		act.moveToElement(getElement(locator)).build().perform();
		//act.clickAndHold(getElement(locator)).build().perform();
		
		return constants.PASS; 
	}
	
	
	
	public String clicklinktext(String data) throws InterruptedException{
		wait("6000");
		driver.findElement(By.linkText(data));
		return data;
		
	}
	
	public String clickonbutton(String locator,String data){
		
		String actual=getElement(locator).getText();
		try{
		Assert.assertEquals(actual, data);
		getElement(locator).click();
		}catch(Exception e){
			return constants.FAIL;
		}
		return constants.PASS;
	}
	
    public String MultiListSelect(String locator,String data) throws InterruptedException {
		
    	try{
		getElement(locator).click();
		wait("6000");
		WebElement searchbox=driver.findElement(By.xpath(prop.getProperty("searchbox_xpath")));
		searchbox.sendKeys(data);
		//robot.keyPress(KeyEvent.VK_ENTER);
		searchbox.sendKeys(Keys.ENTER);
		wait("5000");
		driver.findElement(By.xpath("//input[@class='checkall']")).click();
		driver.findElement(By.xpath("//*[@id='done']")).click();
		wait("2000");
    	}catch(Exception e){
    		return constants.FAIL;
    	}
		return constants.PASS;
	}
    
public String SingleListSelect(String locator,String data) throws InterruptedException {
		
    	try{
		getElement(locator).click();
		wait("6000");
		WebElement searchbox=driver.findElement(By.xpath(prop.getProperty("searchbox_xpath")));
		searchbox.sendKeys(data);
		//robot.keyPress(KeyEvent.VK_ENTER);
		searchbox.sendKeys(Keys.ENTER);
		wait("5000");
		driver.findElement(By.xpath(".//*[@id='admin-view']/div[2]/table/tbody/tr/td[1]/input")).click();
		driver.findElement(By.xpath("//*[@id='done']")).click();
		wait("2000");
    	}catch(Exception e){
    		return constants.FAIL;
    	}
		return constants.PASS;
	}
    
	public String SelectValueInList(String locator,String data) throws InterruptedException{
		
		getElement(locator).click();
		wait("1000");
		List<WebElement> values=driver.findElements(By.xpath("//*[@id='select2-drop']/ul/li"));
			
		for(int i=0;i<values.size();i++){
			if(values.get(i).getText().equals(data)){
				values.get(i).click();
				//act.moveToElement(driver.findElement(By.xpath("//*[@id='select2-drop-mask']"))).doubleClick().build().perform();
				wait("5000");
				return constants.PASS;
			}
			
		}	return constants.FAIL;
	}
		
		public String SelectMultiValueInList(String locator,String data) throws InterruptedException{
			
			getElement(locator).click();
			wait("1000");
			List<WebElement> values=driver.findElements(By.xpath("//*[@id='select2-drop']/ul/li"));
				
			for(int i=0;i<values.size();i++){
				if(values.get(i).getText().equals(data)){
					values.get(i).click();
					act.moveToElement(driver.findElement(By.xpath("//*[@id='select2-drop-mask']"))).doubleClick().build().perform();
					wait("5000");
					return constants.PASS;
				}
			}
		
			
		return constants.FAIL;
	}
    
    public String enterValueinRTF(String locator,String data) throws InterruptedException{
    	
    	getElement(locator).click();
    	wait("2000");
    	driver.findElement(By.xpath("//div[@class='modal-body rft-modal']")).sendKeys(data);
    	
    	return constants.PASS;
    }
    
    public String DateSelection(String locator,String data) throws InterruptedException, ParseException{
    	
    	getElement(locator).click();
    	wait("2000");
    	
    	SimpleDateFormat sf=new SimpleDateFormat("dd/MM/yyyy");
    	//Date date1=new Date();
    	Date date2=sf.parse(data);
    	Date currentdate=new Date();
    	String day=new SimpleDateFormat("d").format(date2);
    	String month=new SimpleDateFormat("MMMM").format(date2);
    	String year=new SimpleDateFormat("yyyy").format(date2);
    	System.out.println(day+"--"+month+"--"+year);
    	String monthyeartobeselected=month+" "+year;
    	
    	String monthyeardisplayed=driver.findElement(By.xpath(".//*[@id='fg-sub-GROUPFIELD_8']/div[1]/div[2]/div[1]/table/thead/tr[1]/th[2]")).getText();
    	
    	List<WebElement> date_cells=driver.findElements(By.xpath("//table[@class=' table-condensed']/tbody/tr/td"));
    	System.out.println(monthyeardisplayed+"------");
    	while(true){
    		if(monthyeartobeselected.equals(monthyeardisplayed)){
    			for(int i=0;i<date_cells.size();i++){
    				if(date_cells.get(i).getText().equals(day)){
    					date_cells.get(i).click();
    					wait("5000");
    					return constants.PASS;
    				}
    			}
    		}else{
    			if(currentdate.after(date2)){
    				driver.findElement(By.xpath("//table[@class=' table-condensed']/thead/tr[1]/th[1]")).click();
    				wait("1000");
    			}else{
    				driver.findElement(By.xpath("//table[@class=' table-condensed']/thead/tr[1]/th[3]")).click();
    				wait("1000");
    			}
    		}
    	monthyeardisplayed=driver.findElement(By.xpath(".//*[@id='fg-sub-GROUPFIELD_8']/div[1]/div[2]/div[1]/table/thead/tr[1]/th[2]")).getText();
    	
    	date_cells=driver.findElements(By.xpath("//table[@class=' table-condensed']/tbody/tr/td"));
    	}
    	
    }
    
    public String clickOnLink(String key) throws InterruptedException{
    	driver.findElement(By.partialLinkText(key)).click();
    	wait("6000");
    	return constants.PASS;
    }
    
    public String AddValueInGrid(String Object,String key) throws InterruptedException{
    	driver.findElement(By.xpath("//*[@id='filter-toolbar']/div/div[2]/button")).click();
    	String xpath1="//*[@id='0_";
    	String xpath2="_Name']";
    	
    	//if(Object.equals("Organization")){
    		driver.findElement(By.xpath(xpath1+Object+xpath2)).sendKeys(key);
    	//}
    	driver.findElement(By.xpath("//*[@id='report-filter-sidebar']/div/div[2]/button[2]")).click();
    	wait("6000");
    	driver.findElement(By.xpath("//input[@class='select-all']")).click();
    	driver.findElement(By.xpath("//*[@id='done']")).click();
    	wait("2000");
    	return constants.PASS;
    }
    
    
  
    

	/* **************************** Utility functions **************************** */
	public WebElement getElement(String locator) {
		WebElement e=null;
		try{
			if(locator.endsWith("_id")){
				e=driver.findElement(By.id(prop.getProperty(locator)));
			}else if(locator.endsWith("_name")){
				e=driver.findElement(By.name(prop.getProperty(locator)));
			}else if(locator.endsWith("_xpath")){
				e=driver.findElement(By.xpath(prop.getProperty(locator)));
			}
		}catch(Exception ec){
			test.log(LogStatus.FAIL, "Fail in element extraction -- "+locator);
			Assert.fail("Fail in element extraction -- "+locator);
		}
		
	return e;
	}
	
	public boolean isElementPresent(String locator){
		List<WebElement> e=null;
		if(locator.endsWith("_id")){
			e=driver.findElements(By.id(prop.getProperty(locator)));
		}else if(locator.endsWith("_name")){
			e=driver.findElements(By.name(prop.getProperty(locator)));
		}else if(locator.endsWith("_xpath")){
			e=driver.findElements(By.xpath(prop.getProperty(locator)));
		}
		
		if(e.size()==0)
			return false;
		else
			return true;
	}

	
	
	/* ******************** Reporting Functions ******************************** */
	
	public void reportFail(String message){
		takescreenshot();
		test.log(LogStatus.FAIL, message);
	}
	
	public void takescreenshot(){
		Date d=new Date();
		String screeshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		String path=constants.REPORT_PATH+"screenshots"+screeshotFile;
		
		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try{
			FileUtils.copyFile(srcFile, new File(path));
		}catch(IOException e){
			e.printStackTrace();
		}
		//embed screenshot in report
		test.log(LogStatus.INFO, test.addScreenCapture(path));
		
	}

}

package Utilty;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import ExtentReports.ExtentManager;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GenericKeyword {
	public  static WebDriver driver;
	public Properties prop;
	public Properties envprop;
	public  ExtentReports report;
	public  static ExtentTest test;
	public SoftAssert softAssert;
	public Select select;
	public Alert alert;
	public String date;
	public Actions action;
	public void launchBrowser(String browser) {
		
		log( "Launching Browser  "+ browser);
		if(prop.get("grid_run").equals("Y")) {
			log("Remote WebDriver");
			DesiredCapabilities cap = new DesiredCapabilities();
					if(browser.equals("chrome")) {
						log("Chrome Remote WebDriver");
						cap.setBrowserName("chrome");
						cap.setPlatform(org.openqa.selenium.Platform.WIN10);
					}else if(browser.equals("mozilla")){
						log("Mozilla Remote WebDriver");
						cap.setBrowserName("mozilla");
						cap.setJavascriptEnabled(true);
						cap.setPlatform(org.openqa.selenium.Platform.WIN10);
					}
					try {
						driver = new RemoteWebDriver(new URL("http://localhost:4444"), cap);		
					} catch (Exception e) {
						e.printStackTrace();
					}
		}else if(browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}else if(browser.equals("mozilla")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}else if(browser.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	public void navigate(String url) {
		log("Navigating to the browser");
		driver.get(prop.getProperty(url));
	}
	
	public void click(String Locator) {
		log("Clicking an element");
		getElement(Locator).click();
	}
	
	public void clickEnter(String locator) {
		getElement(locator).sendKeys(Keys.ENTER);
		
	}
	
	public void type(String locator,String data) {
		log("Locator  "+locator+"    "+"Data     "+data);
		getElement(locator).sendKeys(prop.getProperty(data));
		
		
	}
	
	public void select(String locator, String data) {
		
	}
	
	public void getText(String locator) {
		
	}
	
	public boolean visibilityOfElement(String locator) {
		WebDriverWait  wait = new  WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
		
		return true;
	}
	
	public boolean presenceOfElement(String locator) {
		WebDriverWait  wait = new  WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locator)));
		
		return true;
	}
	
	public WebElement getElement(String locator) {
		log("Getting WebElement  "+locator);
		//Check the presence
		if(!presenceOfElement(locator)) {
			log("Element not present  "+locator);
		}
		
		//Check the visibility
		if(!visibilityOfElement(locator)) {
			log("Element not visible  "+locator);
		}
		
		
		WebElement e = driver.findElement(getLocator(locator));
		
		return e;
	}
	 
	public By getLocator(String locator) {
	
		By by = null;
		if(locator.endsWith("_id")) {
			by = By.id(prop.getProperty(locator));
		}else if(locator.endsWith("_name")) {
			by = By.name(prop.getProperty(locator));
		}else if(locator.endsWith("_css")) {
			by = By.cssSelector(prop.getProperty(locator));
		}else if(locator.endsWith("_xpath")) {
			by = By.xpath(prop.getProperty(locator));
		}else 
			by = By.className(prop.getProperty(locator));
		return by;
	}
	
	public void log(String message) {
		test.log(Status.INFO, message);
	}
	
	public void reportFailure( String failMsg, boolean stopOnFailure) {
		test.log(Status.FAIL, failMsg);
		generateScreenshots();
		softAssert.fail(failMsg);
		if(stopOnFailure) {
			Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalfailure", "Y");
			assertAll();
		}	
	}
	
	public void assertAll() {
		Reporter.getCurrentTestResult().getTestContext().setAttribute("critical", "Y");
		softAssert.assertAll();
	}
	
	public  static String  generateScreenshots()  {

		Date d = new Date();
		String filelocation = d.toString().replaceAll(":", "-").replaceAll(" ","_")+".jpg";
		TakesScreenshot takesScreenshot=(TakesScreenshot) driver;
		File src = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String path = ExtentManager.reports+"//"+filelocation;
		File des = new File(path);
		
		try {
			FileUtils.copyFile(src, des);
			test.log(Status.INFO, "Screenshot--> "+test.addScreenCaptureFromPath(path));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	return path;

	}
	
	public void clear(String locator) {
		getElement(locator).clear();
	}
	
	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		int i =0;
		while(i!=10) {
			String ajaxStatus = (String) js.executeScript("return document.readyState;");
			if(ajaxStatus.equals("complete")) {
				break;
			}else {
				wait(2);
			}
			i++;
		}
		i=0;
		while(i!=10) {
			Long jQueryStatus = (Long) js.executeScript("return jQuery.active;");
			if(jQueryStatus.longValue()==0) {
				break;
			}else {
				wait(2);
			}
			i++;
		}
		
	}
	
	public void wait(int time) {
		try {
			Thread.sleep(time*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void selectFromDown(String locator, String option) {
		
		select = new Select(getElement(locator));
		select.selectByVisibleText(prop.getProperty(option));
		
		
	}
	
	public void selectdValueInDropDown(String locator) {
		select = new Select(getElement(locator));
		select.getFirstSelectedOption().getText();
	}
	
	public void acceptAlert() {
		alert = driver.switchTo().alert();
		alert.accept();
	
	}
	
	public void selectDateFromCalendar(String date) {
		Date d = new Date();
		try {
			Date sdf = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			String day = new SimpleDateFormat("dd").format(sdf);
			String month = new SimpleDateFormat("MMMM").format(sdf);
			String year = new SimpleDateFormat("yyyy").format(sdf);
			String monthyear = month+" "+year;
			String displayedMonth = getElement("monthyear_css").getText();
			while(!monthyear.equals(displayedMonth)) {
				click("forward_xpath");
				displayedMonth= getElement("monthyear_css").getText();
			}
			WebElement e =  driver.findElement(By.xpath("//td[text()='"+day+"']"));
			wait(3);
			e.click();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public int selectDataFromTable(String locator, String data) {
		
		WebElement table = driver.findElement(getLocator(locator));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for(int i=0;i<rows.size();i++) {
			System.out.println("Value before loop   "+i);
			WebElement row = rows.get(i);
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for(int col=0; col<cells.size(); col++) {
				WebElement cell= cells.get(col);
				System.out.println(cell.getText());
				if(!cell.getText().trim().equals(" ")) 
					if(data.startsWith(cell.getText())) 
				
					 return (i+1);
					
			}
			
			
		}
		return -1;
		
	}
	
	
	
	
	public  void quit() {
		driver.quit();
	}
}

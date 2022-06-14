package Utilty;

import java.io.FileInputStream;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class ApplicationKeyword extends ValidationKeyword{
	
	
		
	
	public ApplicationKeyword() {
		String path = "C:\\Java WorkSpace\\DataDrivenFrameWork\\env.properties";
		envprop = new Properties();
		prop = new Properties();
		try {
			FileInputStream file = new FileInputStream(path);
			envprop.load(file);
			String environment = envprop.getProperty("env")+".properties";
			path =  "C:\\Java WorkSpace\\DataDrivenFrameWork\\"+environment;
			file = new FileInputStream(path);
			prop.load(file);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		softAssert = new SoftAssert();
	}
	
	
	
	public void setReport(ExtentTest test) {
		this.test = test;
	}
	
	public int findCurentStockQuantity(String company) {
		int row = selectDataFromTable("stock_xpath",company);
		if(row==-1) {
			log("Data Not Found");
			return 0;
		}
			String data = driver.findElement(By.xpath(prop.getProperty("stock_xpath")+"//tr["+row+"]//td[4]")).getText();
			log("Stock Quantity  "+data);
			return Integer.parseInt(data);
	}
	
	public void clickOnCompany(String com) {
		action = new Actions(driver);
		int row = selectDataFromTable("stock_xpath", com);
		System.out.println(row+"  "+"value    ");
		if(row==-1){
			log("Unable to click on the company");
		}
		WebElement click = driver.findElement(By.xpath("//table[@id='stock']//tr["+row+"]//td[1]"));
		action.moveToElement(click).click().perform();
		click.click();
	}
	
	public void buyTheStock(String com) {
		log("Buy the stocks in Reliance Company");
		int row = selectDataFromTable("stock_xpath", com);
		//driver.findElement(By.xpath("//table[@id='stock']//tr["+row+"]//td[1]")).click();
		driver.findElement(By.xpath("//table[@id=\"stock\"]//tr["+row+"]//td//input[@name='Buy / Sell']")).click();
		wait(3);
	}
	
	public void quantitybeforeModification(String com) {
		log("Quantity before Modification");
		int row = selectDataFromTable("stock_xpath", com);
		String quantity = driver.findElement(By.xpath("//table[@id='stock']//tr["+row+"]//td[4]")).getText();
		log("Quantity  after entering into the row  "+quantity);
		//return Integer.parseInt(quantity);
	}
	
	public void sellTheStock(String com) {
		log("Sell the Stocks in Reliance Company");
		int row = selectDataFromTable("stock_xpath", com);
		driver.findElement(By.xpath("//table[@id=\"stock\"]//tr["+row+"]//td//input[@name='Buy / Sell']")).click();
		wait(2);

	}
	
	public void transaction(String company) {
		log("Click on Transaction History");
		action = new Actions(driver);
		int row = selectDataFromTable("stock_xpath", company);
		WebElement ele= driver.findElement(By.xpath("//table[@id=\"stock\"]//tr["+row+"]//td//input[@name='Transaction History']"));
		action.moveToElement(ele).click().perform();
		ele.click();
		wait(2);
	}
	
	public void transactionTable(String locator,String data) {
		int row= selectDataFromTable("trns_xpath", data);
		driver.findElement(By.xpath("//table[@class='dataTable']//tr["+row+"]//td[1]")).click();
		String select = driver.findElement(By.xpath("//table[@class='dataTable']//tr[\"+row+\"]//td[2]")).getText();
		log("Trasaction Date   "+select);
		wait(2);
	}
	
}

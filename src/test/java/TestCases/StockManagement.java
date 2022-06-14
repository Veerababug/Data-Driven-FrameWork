package TestCases;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import TestBase.RediffBase;

public class StockManagement extends RediffBase{

	@Test
	public void addStock(ITestContext context) {
		String company=  "Tata Consultancy";
		String SelectAction = "Sell";
		String date = "22-08-2022";
		String Quantity = "50";
		String Price = "100";
		test.log(Status.INFO, context.getName());
		application.click("stock_name");
		application.type("add_id", "birla");
		application.wait(2);
		application.clickEnter("add_id");
		application.click("opencalendar_xpath");
		application.wait(3);
		application.selectDateFromCalendar(date);
		application.type("quantity_css","quantity");
		application.type("price_xpath", Price);
		application.click("addstock_css");
		application.wait(5);
	}
	
	@Test
	public void goToCompany() {
		//String company =  "Birla Corporation Lt";
		String company=  "Tata Consultancy";

		application.clickOnCompany(company);
		application.log("Successfully clicked on Reliance Industries"); 
	
	}
	@Parameters({"action"})
	@Test
	public void buyTheStock(String action,ITestContext context) {
		JSONObject data = (JSONObject) context.getAttribute("data");

		//String company=  "Birla Corporation Lt";
		//String company=  "Tata Consultancy";
		String SelectAction = "Sell";
		//String date = "22-08-2022";
		String company=  (String) data.get("Company");
		String date = (String) data.get("Date");
		String Quantity = "20";
		String Price = "100";
		application.quantitybeforeModification(company);
		application.buyTheStock(company);
		application.wait(2);
		application.log("Click on Buy Stocks");
		application.click("opencalendar_css");
		application.selectDateFromCalendar(date);
		application.wait(3);
		application.type("quantity_css","quantity");
		application.type("price_css","price" );
		application.click("submit_css");
		application.wait(3);
	}
	
	@Test
	public void quantityAfterModification() {
		String company=  "Tata Consultancy";
		
		application.quantitybeforeModification(company);
		application.log("Quantity AfterModification   ");
	}
	
	@Parameters({"action"})
	@Test
	public void sellTheStock(String action,ITestContext context) {
		JSONObject data = (JSONObject) context.getAttribute("data");
		String company= (String) data.get("Company");
		//String company=  "Tata Consultancy";
		String SelectAction = "Sell";
		//String date = "22-08-2022";
		//String SelectAction = "Sell";
		String date = (String) data.get("Date");
		String Quantity = "50";
		String Price = "100";
		//String company=  "Birla Corporation Lt";
		application.clickOnCompany(company);
		application.wait(2);
		application.sellTheStock(company);
		application.selectFromDown("selectionAction_xpath", "selli");
		application.wait(3);
		application.click("opencalendar_css");
		application.selectDateFromCalendar(date);
		application.wait(5);
		application.type("quantitysell_css","quanitysell");
		application.type("sellprice_css", "selling");
		application.click("submit_css");
		application.wait(3);
				
	}
	
	
	public void transactionHistory() {
		String company=  "Tata Consultancy";
		String com = "	22-AUG-22";
		application.wait(3);
		application.transaction(company);
		application.wait(2);
		application.click("transaction_css");
		application.transactionTable("trns_xpath", com);
		application.click("edit_css");
		application.wait(2);
	}
	
}

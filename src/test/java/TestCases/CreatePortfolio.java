package TestCases;

import org.testng.ITestContext;
import org.testng.annotations.Test;


import TestBase.RediffBase;

public class CreatePortfolio extends RediffBase {

	@Test
	public void createPortfolio(ITestContext context) {
		application.log( context.getName());
		application.click("portfolio_css");
		application.click("create_id");
		application.clear("portfolioname_id");
		application.type("portfolioname_id", "portuser");
		application.click("created_id");
		application.waitForPageLoad();
		
		
	}
	
	@Test
	public void deletePortfolio() {
		application.selectFromDown("deletedropdown_id", "deleteoption");
		application.wait(2);
		application.click("delete_id");
		application.acceptAlert();
		application.waitForPageLoad();
	}
}

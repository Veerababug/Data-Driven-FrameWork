package TestCases;

import org.testng.annotations.Test;

import TestBase.RediffBase;

public class DoLogin extends RediffBase {

	@Test
	public void doLogin() {
		application.launchBrowser("chrome");
		application.navigate("url");
		application.click("sign_xpath");
		application.type("user_id", "username");
		application.type("pass_name", "password");
		//application.reportFailure("My mistake", false);
		application.click("login_id");
		//application.assertAll();
	}
	
	@Test
	public void selectPortfolio() {
		application.selectFromDown("deletedropdown_id", "deleteoption");
		application.wait(5);
	}
	
}

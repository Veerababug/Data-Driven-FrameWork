package TestCases;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import TestBase.RediffBase;

public class Session extends RediffBase {

	@Test
	public void doLogin(ITestContext context) {
		test.log(Status.INFO, context.getName());
		
		application.launchBrowser("edge");
		application.navigate("url");
		application.click("sign_xpath");
		application.type("user_id", "username");
		application.type("pass_name", "password");
		application.click("login_id");
		//application.assertAll();
		
		
	}
	
	@Test
	public void doLogout() {
		test.log(Status.INFO,"Logging out");
	}
}

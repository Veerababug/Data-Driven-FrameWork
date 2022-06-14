package TestBase;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import ExtentReports.ExtentManager;
import Runner.JsonData;
import Utilty.ApplicationKeyword;
import Utilty.GenericKeyword;


public class RediffBase {
	
	public ExtentReports report;
	public ExtentTest test;
	
	
	
	public ApplicationKeyword application; 
	
	@BeforeTest(alwaysRun = true)
	public void beforeTest(ITestContext context ) throws Exception {
		String path = context.getCurrentXmlTest().getParameter("filePath");
		String dataFlag = context.getCurrentXmlTest().getParameter("dataFalg");
		String iteration = context.getCurrentXmlTest().getParameter("Iteration");
		JSONObject content = new JsonData().getTestData(path, dataFlag, Integer.parseInt(iteration));
		String Company = (String)content.get("Company");
		String runMode = (String)content.get("runMode");
		System.out.println(Company +"    "+runMode+"  "+"line no 39");
		System.out.println("Data file location  "+path);
		System.out.println(dataFlag+"  "+"Line no 34");
		context.setAttribute("data", content);
		
		 report = ExtentManager.generateReports();
		 test = report.createTest(context.getCurrentXmlTest().getName());
		 test.log(Status.INFO, "Starting the  "+context.getCurrentXmlTest().getName());
		 test.log(Status.INFO, "Content"+content.toString());
		 	
		 context.setAttribute("rep", report);
		 context.setAttribute("tests", test);
		 if(runMode.equals("N")) {
			 test.log(Status.SKIP , "run mode is no");
			 throw new SkipException("run mode is no");
		 }
		 application = new ApplicationKeyword();
		 application.setReport(test);
		 context.setAttribute("app", application);
		
	}
	
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(ITestContext context) {
		test = (ExtentTest) context.getAttribute("tests");
		
		String critical = (String)context.getAttribute("crtiticalfailure");
		if(critical !=null && critical.equals("Y")) {
			test.log(Status.SKIP, "Skipped");
			throw new SkipException("Skipped Exception");
		
		}
		application = (ApplicationKeyword) context.getAttribute("app");	
		report = (ExtentReports) context.getAttribute("rep");
		

	}
	
	@AfterTest
	public void afterTest(ITestContext context) {
		application = (ApplicationKeyword) context.getAttribute("app");	
		if(application!=null)
		application.quit();
		
		report=(ExtentReports) context.getAttribute("rep");
		if(report!=null)
			report.flush();
	} 
	
	
	@AfterSuite
	public void afterSuite(ITestContext context) {
		
	}
	
}

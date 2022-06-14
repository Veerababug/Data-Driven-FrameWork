package Runner;

import java.util.ArrayList;
import java.util.List;

public class Runner {

	public static void main(String[] args) {
		TestNGRunner runner = new TestNGRunner(1);
		runner.createSuite("Rediff Suite", false);
		//First Test Case
		runner.addListeners("Listener.MyTestResult");
		runner.addTest("Buy the Stocks");
		//runner.addParameters("action", "doLogin");
		 List<String> addMethods = new ArrayList<String>();
		 addMethods.add("doLogin");
		 addMethods.add("selectPortfolio");
		 runner.addClasses("TestCases.DoLogin", addMethods);
		 addMethods = new ArrayList<String>();
		 addMethods.add("goToCompany");
		 addMethods.add("buyTheStock");
		 //Second Test Case
		runner.addTest("Sell the Stocks");
		addMethods = new ArrayList<String>();
		 addMethods.add("doLogin");
		 addMethods.add("selectPortfolio");
		 runner.addClasses("TestCases.DoLogin", addMethods);
		 addMethods.add("goToCompany");
		 addMethods.add("sellTheStock");
		 runner.addClasses("TestCases.StockManagement", addMethods);
		 runner.run();
	}
}

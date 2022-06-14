package Utilty;

import org.testng.ITestResult;

public class ValidationKeyword extends GenericKeyword{
	
	

	public void validateTitle() {
		
	}
	
	public void validateText() {
		
	}
	
	public void validateElementPresent(String locator) {
		boolean result = presenceOfElement(locator);
		reportFailure("Element not found   "+locator, true);
		
		
	}
	
	public void validateLlogin() {
		
	}
}

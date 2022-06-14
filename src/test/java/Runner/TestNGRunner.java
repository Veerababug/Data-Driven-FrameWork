package Runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

public class TestNGRunner {

	public TestNG testng = new TestNG();
	public List<XmlSuite> listOfSuites;
	public XmlSuite singleSuite ;
	public List<XmlTest> listOfTests;
	public XmlTest singleTest =new XmlTest();
	Map<String, String> parameters;
	public List<XmlClass> listOfClasses;
	public XmlClass singleClass;
	
	public TestNGRunner(int threadPoolsize) {
		listOfSuites = new ArrayList<XmlSuite>();
		testng.setSuiteThreadPoolSize(threadPoolsize);
		testng.setXmlSuites(listOfSuites);
	}
	
	public void createSuite(String name, boolean parallel) {
		singleSuite = new XmlSuite();
		singleSuite.setName(name);
		
		if(parallel)
			singleSuite.setParallel(ParallelMode.TESTS);
		
 		listOfSuites.add(singleSuite);
				
	}
	
	public void addTest(String name) {
		listOfTests = new ArrayList<XmlTest>();		//List of Tests
		singleTest = new XmlTest(singleSuite);			//Single Test adding to suite
		singleTest.setName(name);
		parameters = new HashMap<String, String>();				//creating parameters to test
		singleTest.setParameters(parameters);				//set parameters to Test
		listOfClasses = new ArrayList<XmlClass>();		//object for list of classes
		singleTest.setXmlClasses(listOfClasses);				//set all classes to test
		listOfTests.add(singleTest);									//adding all tests to list of tests
		
		
	}
	
	public void addParameters(String key, String value) {
		parameters.put(key, value);
	}
	
	public void addClasses(String classname, List<String> includedMethods) {
		singleClass = new XmlClass();
		singleClass.setName(classname);
		List<XmlInclude> listOfMethods = new ArrayList<XmlInclude>();
		
		int order =1;
		for (String name  : includedMethods) {
			listOfMethods.add(new XmlInclude(name, order));
			order++;
		}
		singleClass.setIncludedMethods(listOfMethods);
		listOfClasses.add(singleClass);
		
	}
	
	public void addListeners(String Listener) {
		singleSuite.addListener(Listener);
	}
	
	public void run() {
		testng.run();
	}
	
}

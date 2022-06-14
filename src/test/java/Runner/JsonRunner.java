package Runner;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonRunner {
		
		
		public static void main(String[] args) throws Exception{
			Map<String, String> loadmethod = new JsonData().loadmethods();
			String path = System.getProperty("user.dir")+"\\src\\test\\resources\\Json\\testconfig.json";
			JSONParser parser = new JSONParser();
			JSONObject json =  (JSONObject) parser.parse(new FileReader(new File(path)));
			String parallelSuites =  (String) json.get("parallelSuites");
			JSONArray testSuites = (JSONArray) json.get("testSuites");
			TestNGRunner runner = new TestNGRunner(Integer.parseInt(parallelSuites));
			for(int ts=0;ts<testSuites.size();ts++)
			{
				JSONObject ats =  (JSONObject) testSuites.get(ts);
				String runMode = (String)ats.get("runMode");
					if(runMode.equals("Y"))
					{
						String Name = (String)ats.get("Name");
						String testDataJsonFile =System.getProperty("user.dir")+"\\src\\test\\resources"+ (String)ats.get("testDataJsonFile");
						String suiteJsonFile = (String)ats.get("suiteJsonFile");
						String parallelTests = (String)ats.get("parallelTests");
						boolean ptest=false;
							if(parallelTests.equals("Y"))
								ptest= true;
								runner.createSuite(Name, ptest);
								runner.addListeners("Listener.MyTestResult");
								String suitePath = System.getProperty("user.dir")+"\\src\\test\\resources\\Json"+suiteJsonFile;
								JSONParser suiteParser = new JSONParser();
								JSONObject suitTest =  (JSONObject) suiteParser.parse(new FileReader(new File(suitePath)));
								JSONArray ta = (JSONArray) suitTest.get("testcases");
									for(int tc=0; tc<ta.size();tc++) 
									{
										JSONObject Test = (JSONObject) ta.get(tc);
												String TestName =(String) Test.get("TestName");
												JSONArray ParameterNames =(JSONArray) Test.get("ParameterNames");
												JSONArray Executions = (JSONArray)Test.get("Executions");
												for(int ex=0;ex<Executions.size();ex++)
												{
													JSONObject testExecutions= (JSONObject) Executions.get(ex);
													String trunMode = (String)testExecutions.get("runMode");
													if(trunMode !=null && trunMode.equals("Y"))
													{
													String ExecutionName = (String) testExecutions.get("ExecutionName");
													String dataFlag = (String) testExecutions.get("dataFlag");
													
													int dataSet = new JsonData().getTestDataIterations(testDataJsonFile,dataFlag);
													for(int iter=0;iter<dataSet;iter++)
													{
													JSONArray ParameterValues = (JSONArray) testExecutions.get("ParameterValues");
														for(int i=0;i<ParameterNames.size();i++)
														{
															runner.addTest(Name+"   "+ExecutionName+" "+"Iteration"+(iter+1));
															runner.addParameters((String)ParameterNames.get(i),(String) ParameterValues.get(i));
														}
															runner.addParameters("filePath", testDataJsonFile);
															runner.addParameters("dataFalg", dataFlag);
															runner.addParameters("Iteration", String.valueOf(iter));
															System.out.println(dataFlag+"and"+"  "+iter);
															JSONArray arrayMethods =  (JSONArray) testExecutions.get("Methods");
															List<String> includeMethods = new ArrayList<String>();
																	for(int mid=0;mid<arrayMethods.size();mid++)
																	{
																		String Methods = (String)arrayMethods.get(mid);
																		String ClassMethods = loadmethod.get(Methods);
																		if(mid==arrayMethods.size()-1 || !((String)loadmethod.get(arrayMethods.get(mid+1))).equals(ClassMethods))
																		{
																			includeMethods.add(Methods);
																			runner.addClasses(ClassMethods, includeMethods);
  																			includeMethods = new ArrayList<String>();
																		}
																		else
																		{
																			includeMethods.add(Methods);
																		}
																	}
														
												}
													}
												}
									}
					}
			}
			runner.run();
		}
}

package Runner;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonData {
	

	public Map<String, String> loadmethods() throws Exception {
		Map<String, String> loadMethods = new HashMap<String, String>();
		String path = System.getProperty("user.dir")+"\\src\\test\\resources\\Json\\Methods.json";
		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(new FileReader(new File(path)));
		JSONArray ja = (JSONArray) json.get("Classes");
		for(int i=0;i<ja.size();i++) {
			JSONObject data =  (JSONObject) ja.get(i);
			String ClassName = (String) data.get("ClassName");
			JSONArray Methods = (JSONArray) data.get("Methods");{
				for(int l=0; l<Methods.size();l++) {
					String method = (String)Methods.get(l);
					loadMethods.put(method,ClassName);
				}
			}
		}
		return loadMethods;
	}
	
	public int getTestDataIterations( String filePath,String dataFlag)throws Exception {
		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(new FileReader(new File(filePath)));
		JSONArray testdata =  (JSONArray) json.get("testdata");
		for(int d=0;d<testdata.size();d++)
		{
			JSONObject dataSets = (JSONObject)  testdata.get(d);
			String flag = (String)dataSets.get("flag");
			if(dataFlag.equals(flag)) {
				JSONArray data = (JSONArray) dataSets.get("data");
				return data.size();
			}
		}
		return -1;
	}
	
	public JSONObject getTestData( String filePath,String dataFlag, int iteration)throws Exception {
		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(new FileReader(new File(filePath)));
		JSONArray testdata =  (JSONArray) json.get("testdata");
		for(int d=0;d<testdata.size();d++)
		{
			JSONObject dataSets = (JSONObject)  testdata.get(d);
			String flag = (String)dataSets.get("flag");
			if(dataFlag.equals(flag)) {
				JSONArray data = (JSONArray) dataSets.get("data");
				JSONObject content = (JSONObject)data.get(iteration);
				return content;
					
				}
			}
		return null;
	}
	
}

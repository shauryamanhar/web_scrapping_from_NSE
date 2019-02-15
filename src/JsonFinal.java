import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonFinal {

	public static void main(String[] args) {
		JSONParser jsonParser = new JSONParser();
		Object object;
		ArrayList<String> list = new ArrayList<String>();
		try {
			object = jsonParser.parse(new FileReader("C:\\Users\\Shaurya Manhar\\json1.json"));//"D:\US.json"
			//"C:\Users\Shaurya Manhar\json1.json"
			JSONArray jsonArray = (JSONArray) object;
			
			Iterator itr = jsonArray.iterator();
			while(itr.hasNext()) {
				Object slide = itr.next();
				JSONObject jsonObject2 = (JSONObject) slide;
				System.out.println(jsonObject2.get("symbol"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}

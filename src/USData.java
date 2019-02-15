import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;
import java.sql.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import sis.com.util.DbUtil;

public class USData {

	public static void main(String[] args) {
		JSONParser jsonParser = new JSONParser();
		Object object;
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> listOfCompanyName = new ArrayList<String>();
		try {
			object = jsonParser.parse(new FileReader("C:\\Users\\Shaurya Manhar\\json1.json"));//"D:\US.json"
			//"C:\Users\Shaurya Manhar\json1.json"
			JSONArray jsonArray = (JSONArray) object;
			
			Iterator itr = jsonArray.iterator();
			while(itr.hasNext()) {
				Object slide = itr.next();
				JSONObject jsonObject2 = (JSONObject) slide;
				list.add((String) jsonObject2.get("symbol"));
				listOfCompanyName.add((String) jsonObject2.get("name"));
				//System.out.println(jsonObject2.get("symbol")+"  "+jsonObject2.get("name"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		Connection con=null;
		ResultSet rs=null;
		try {
			con=DbUtil.getConnection();
			
			for(String symbol:list) {
				/*//String sql="CREATE TABLE `"+symbol+"`(id int,name varchar(45)  )  ";
				String sql="drop table `"+symbol+"`";
				//create table A (id int,name varchar(45));
				Statement stmt = con.createStatement();
				Boolean r=stmt.execute(sql);*/
				System.out.println("table create for "+symbol);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}//main

}

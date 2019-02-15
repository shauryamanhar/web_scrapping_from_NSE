import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import sis.com.util.DbUtil;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
public class USDateScraping {

	public static void main(String[] args) throws IOException, ParseException, SQLException, java.text.ParseException {
		/////
		Connection con = DbUtil.getConnection();
		ResultSet rs= null;
		/////
		
		
		JSONParser jsonParser = new JSONParser();
		Object object;
		Document document=null;
		ArrayList<String> list = new ArrayList<String>();
		try {
			object = jsonParser.parse(new FileReader("C:\\Users\\Shaurya Manhar\\json1.json"));//"D:\US.json"
			//"C:\Users\Shaurya Manhar\json1.json"
			JSONArray jsonArray = (JSONArray) object;
			
			Iterator itr = jsonArray.iterator();
			while(itr.hasNext()) {
				Object slide = itr.next();
				JSONObject jsonObject2 = (JSONObject) slide;
				list.add((String) jsonObject2.get("symbol"));
				//System.out.println(jsonObject2.get("symbol"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		for(String sym:list) {
			//https://api.iextrading.com/1.0/stock/symbol/quote
			//document = Jsoup.connect("https://api.iextrading.com/1.0/stock/"+sym+"/quote").get();
			StringBuilder sb = new StringBuilder();
			URLConnection urlConn = null;
			InputStreamReader in = null;
			try {
				URL url = new URL("https://api.iextrading.com/1.0/stock/"+sym+"/quote");
				urlConn = url.openConnection();
				if (urlConn != null)
					urlConn.setReadTimeout(60*1000*60);
				if (urlConn != null && urlConn.getInputStream() != null) {
					in = new InputStreamReader(urlConn.getInputStream(),Charset.defaultCharset());
					BufferedReader bufferedReader = new BufferedReader(in);
					if (bufferedReader != null) {
						int cp;
						while ((cp = bufferedReader.read()) != -1) {
							sb.append((char) cp);
						}//while
						bufferedReader.close();
					}//if
				}//if
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			String jsonString = sb.toString();
			System.out.println(jsonString);
			
			//JsonParser parser = Json.createParser(new StringReader(jsonString));
			JSONParser parser = new JSONParser();
			JSONObject json= (JSONObject)parser.parse(jsonString);
			try {
				String sql="insert into stocks(uid,symbol,company_name)values(?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, ""+json.get("iexId"));
				ps.setString(2, ""+json.get("symbol"));
				ps.setString(3, ""+json.get("companyName"));
				ps.executeUpdate();
				System.out.println("stocks-> symbol "+(String)json.get("symbol"));
				
				sql="insert into details(uid,high,low,open,close,volume,time)values(?,?,?,?,?,?,?)";
				PreparedStatement ps1 = con.prepareStatement(sql);
				
				String sd=(String)json.get("latestTime");
				SimpleDateFormat sdf = new SimpleDateFormat("MMMMM d, yyyy");
				Calendar c =Calendar.getInstance(); 
				Date de =sdf.parse(sd);
				java.sql.Date sqlDate = new java.sql.Date(de.getTime());
				
				
				//{"symbol":"AFC","companyName":"Allied Capital Corporation 6.875% Notes due April 15 2047","primaryExchange":"New York Stock Exchange","sector":"Financial Services","calculationPrice":"previousclose","open":25.9,"openTime":1513953001004,"close":25.94,"closeTime":1513976522114,"high":25.94,"low":25.826,"latestPrice":25.8938,"latestSource":"Previous close","latestTime":"December 22, 2017","latestUpdate":1513900800000,
				ps1.setString(1,  ""+json.get("iexId"));
				ps1.setString(2,  ""+json.get("high"));
				ps1.setString(3,  ""+json.get("low"));
				ps1.setString(4,  ""+json.get("open"));
				ps1.setString(5,  ""+json.get("close"));
				ps1.setString(6,  ""+json.get("latestVolume"));
				ps1.setString(7,  ""+json.get("latestUpdate"));
				
				ps1.executeUpdate();
				System.out.println("details->  "+(String)json.get("symbol"));
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}//for
		

	}//main
}//class

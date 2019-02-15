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

import pojo.Stocks;
import sis.com.util.DbUtil;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
public class Stock {

	public static void main(String[] args) throws IOException, ParseException, SQLException, java.text.ParseException {
		//https://api.iextrading.com/1.0/ref-data/symbols
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL("https://api.iextrading.com/1.0/ref-data/symbols");
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String jsonString = sb.toString();
		System.out.println(jsonString);
		
		//here start parsing
		JSONParser parser = new JSONParser();
		//JSONObject json= (JSONObject)parser.parse(jsonString);
		JSONArray json= (JSONArray)parser.parse(jsonString);
		Stocks p = null;
		ArrayList<Stocks> a = new ArrayList<Stocks>();
		for(int i=0;i<json.size();i++) {
			JSONObject jO= (JSONObject)json.get(i);
			p =new Stocks();
			p.setIexId(""+jO.get("iexId"));
			p.setName(""+jO.get("name"));
			p.setSymbol(""+jO.get("symbol"));
			p.setDate(""+jO.get("date"));
			
			a.add(p);
		}//for
		Connection con = DbUtil.getConnection();
		for(Stocks e : a) {
			try {
				String sql="insert into stocks(uid,symbol,company_name)values(?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1,e.getIexId());
				ps.setString(2,e.getSymbol());
				ps.setString(3,e.getName());
				System.out.println("inserted "+e.getSymbol());
				ps.executeUpdate();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}//for
		
		
	}//main
}//class

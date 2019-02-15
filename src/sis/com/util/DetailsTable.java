package sis.com.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pojo.Stocks;

public class DetailsTable {

	public static void main(String[] args) {
		Connection con=null;
		ResultSet rs=null;
		Stocks s =null;
		ArrayList<Stocks> stockList = new ArrayList<Stocks>();
		long i=1;
		try{
			String sql="select * from stocks";
			con=DbUtil.getConnection();
			PreparedStatement p = con.prepareStatement(sql);
			rs=p.executeQuery();
			while(rs.next()) {
				s=new Stocks();
				s.setIexId(rs.getString("uid"));
				s.setSymbol(rs.getString("symbol"));
				stockList.add(s);
				System.out.println("Got uid "+rs.getString("uid"));
				i++;
			}//while
			
			System.out.println("Total data found from stocks table "+i);
			long j=1;
			for(Stocks e:stockList) {
				//https://api.iextrading.com/1.0/stock/symbol/quote
				//document = Jsoup.connect("https://api.iextrading.com/1.0/stock/"+sym+"/quote").get();
				StringBuilder sb = new StringBuilder();
				URLConnection urlConn = null;
				InputStreamReader in = null;
				try {
					URL url = new URL("https://api.iextrading.com/1.0/stock/"+e.getSymbol()+"/quote");
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
				}catch(Exception e2) {
					e2.printStackTrace();
				}
				
				String jsonString = sb.toString();
				System.out.println(jsonString);
				
				//JsonParser parser = Json.createParser(new StringReader(jsonString));
				JSONParser parser = new JSONParser();
				JSONObject json= (JSONObject)parser.parse(jsonString);
				try {
					sql="insert into details(uid,high,low,open,close,volume,time)values(?,?,?,?,?,?,?)";
					PreparedStatement ps1 = con.prepareStatement(sql);
					
					String sd=(String)json.get("latestTime");
					SimpleDateFormat sdf = new SimpleDateFormat("MMMMM d, yyyy");
					Calendar c =Calendar.getInstance(); 
					Date de =sdf.parse(sd);
					java.sql.Date sqlDate = new java.sql.Date(de.getTime());
					
					
					//{"symbol":"AFC","companyName":"Allied Capital Corporation 6.875% Notes due April 15 2047","primaryExchange":"New York Stock Exchange","sector":"Financial Services","calculationPrice":"previousclose","open":25.9,"openTime":1513953001004,"close":25.94,"closeTime":1513976522114,"high":25.94,"low":25.826,"latestPrice":25.8938,"latestSource":"Previous close","latestTime":"December 22, 2017","latestUpdate":1513900800000,
					ps1.setString(1,  e.getIexId());
					System.out.println("inserted uid "+e.getIexId()+"  and symbol "+e.getSymbol());
					ps1.setString(2,  ""+json.get("high"));
					ps1.setString(3,  ""+json.get("low"));
					ps1.setString(4,  ""+json.get("open"));
					ps1.setString(5,  ""+json.get("close"));
					ps1.setString(6,  ""+json.get("latestVolume"));
					ps1.setString(7,  ""+json.get("latestUpdate"));
					ps1.executeUpdate();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				j++;
			}//for
			System.out.println("Total Data inserted to Details table "+j);
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}//main

}//class

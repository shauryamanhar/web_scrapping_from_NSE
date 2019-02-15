import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetAll25Name {

	public static void main(String[] args) {
		//"D:\equity\eq1.xlsx"
		String csvFile = "D:\\equity\\eq1.csv";
		String line = "";
        String cvsSplitBy = ",";
        String[] csvData=null;
        Map<String,String> list = new HashMap<String,String>();
        List<String> listOfFieldKey = new ArrayList<String>();
        List<String> listOfFieldValue = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                csvData = line.split(cvsSplitBy);
                //System.out.println("" + csvData[0] + " ||   " + csvData[1] + "]");
                list.put(csvData[0],csvData[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<String> l = list.keySet().iterator();
        Document document=null;
        String responseFromServerJson="";
        JsonParser parser=null;
        int i=1;
        while (l.hasNext() && i!=2) {
        	 System.out.println(">>"+i++);
            String symbolOfCompany = l.next();
            //System.out.println(symbolOfCompany + ": " + list.get(symbolOfCompany));
            try {
				document = Jsoup.connect("https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol="+symbolOfCompany+"").get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Elements row = document.select("#responseDiv");
    		responseFromServerJson =row.select("#responseDiv").text();
    		System.out.println(responseFromServerJson);
    		parser = Json.createParser(new StringReader(responseFromServerJson));
    		String key = null;
    		String value = null;
    		while (parser.hasNext()) {
    			final Event event = parser.next();
    			String _key=null;
    			String _value=null;
    			switch (event) {
    			case KEY_NAME:
    				key = parser.getString();
    				_key=key;
    				listOfFieldKey.add(_key);
    				System.out.println(key);
    				break;
    			case VALUE_STRING:
    				value = parser.getString();
    				_value=value;
    				listOfFieldValue.add(_key);
    				//System.out.println(value);
    				break;
    			}
    			
    		}//while
    		parser.close();

        }//while
        System.out.println(">>end while loop");
        for(String key : listOfFieldKey) {
        	System.out.println(">>for loop");
        	System.out.println(key);
        }
	}//main

}//class

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FinalScraping {

	public static void main(String[] args) throws IOException {
		String csvFile = "C:\\Users\\Shaurya Manhar\\Downloads\\EQUITY_L.csv";
		String line = "";
        String cvsSplitBy = ",";
        Map<String,String> list = new HashMap<String,String>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] csvData = line.split(cvsSplitBy);
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
        while (l.hasNext()) {
            String symbolOfCompany = l.next();
            System.out.println(symbolOfCompany + ": " + list.get(symbolOfCompany));
            document = Jsoup.connect("https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol="+symbolOfCompany+"").get();
            Elements row = document.select("#responseDiv");
    		responseFromServerJson =row.select("#responseDiv").text();
    		//System.out.println(responseFromServerJson);
    		parser = Json.createParser(new StringReader(responseFromServerJson));
    		String key = null;
    		String value = null;
    		while (parser.hasNext()) {
    			final Event event = parser.next();
    			switch (event) {
    			case KEY_NAME:
    				key = parser.getString();
    				System.out.println(key);
    				break;
    			case VALUE_STRING:
    				value = parser.getString();
    				System.out.println(value);
    				break;
    			}
    		}
    		parser.close();

        }

	}//main

}//class

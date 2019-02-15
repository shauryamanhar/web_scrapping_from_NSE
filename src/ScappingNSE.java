import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.simple.parser.JSONParser;
public class ScappingNSE {
	public static void main(String[] args) throws Exception{
		final Document document = Jsoup.connect("https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=BAJFINANCE").get();
		String s="";
		Elements row = document.select("#responseDiv");
		s =row.select("#responseDiv").text();
		System.out.println(s);
		/*JSONParser parser = new JSONParser();
		Object obj = parser.parse(s);
		JSONArray array = (JSONArray)obj;m
		System.out.println(array.get(1));
		*/
		/*JSONParser parser = new JSONParser();
		Object obj  = parser.parse(s);
		JSONArray array = new JSONArray();
		array.add(obj);
		System.out.println(array.get(1));*/

}//main
}//class
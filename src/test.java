import org.json.simple.JSONObject;  
import org.json.simple.JSONValue; 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class test {

	public static void main(String[] args) {
		String j = "{\"futLink\":\"\\/live_market\\/dynaContent\\/live_watch\\/get_quote\\/GetQuoteFO.jsp?underlying=BAJFINANCE&instrument=FUTSTK&expiry=30NOV2017&type=-&strike=-\",\"otherSeries\":[\"EQ\"],\"lastUpdateTime\":\"31-OCT-2017 15:59:59\",\"tradedDate\":\"31OCT2017\",\"data\":[{\"extremeLossMargin\":\"5.00\",\"cm_ffm\":\"42,561.16\",\"bcStartDate\":\"08-JUL-17\",\"change\":\"-4.10\",\"buyQuantity3\":\"-\",\"sellPrice1\":\"-\",\"buyQuantity4\":\"-\",\"sellPrice2\":\"-\",\"priceBand\":\"No Band\",\"buyQuantity1\":\"-\",\"deliveryQuantity\":\"2,51,068\",\"buyQuantity2\":\"-\",\"sellPrice5\":\"-\",\"quantityTraded\":\"7,64,883\",\"buyQuantity5\":\"-\",\"sellPrice3\":\"-\",\"sellPrice4\":\"-\",\"open\":\"1,787.00\",\"low52\":\"760.50\",\"securityVar\":\"7.94\",\"marketType\":\"N\",\"pricebandupper\":\"1,984.70\",\"totalTradedValue\":\"13,769.42\",\"faceValue\":\"2.00\",\"ndStartDate\":\"-\",\"previousClose\":\"1,804.30\",\"symbol\":\"BAJFINANCE\",\"varMargin\":\"7.94\",\"lastPrice\":\"1,800.20\",\"pChange\":\"-0.23\",\"adhocMargin\":\"-\",\"companyName\":\"Bajaj Finance Limited\",\"averagePrice\":\"1,800.20\",\"secDate\":\"31OCT2017\",\"series\":\"EQ\",\"isinCode\":\"INE296A01024\",\"surv_indicator\":\"-\",\"indexVar\":\"-\",\"pricebandlower\":\"1,623.90\",\"totalBuyQuantity\":\"-\",\"high52\":\"1,985.90\",\"purpose\":\"ANNUAL GENERAL MEETING\\/DIVIDEND - RS 3.60\\/- PER SHARE\",\"cm_adj_low_dt\":\"22-DEC-16\",\"closePrice\":\"1,800.60\",\"isExDateFlag\":false,\"recordDate\":\"-\",\"cm_adj_high_dt\":\"07-SEP-17\",\"totalSellQuantity\":\"-\",\"dayHigh\":\"1,824.05\",\"exDate\":\"06-JUL-17\",\"sellQuantity5\":\"-\",\"bcEndDate\":\"19-JUL-17\",\"css_status_desc\":\"Listed\",\"ndEndDate\":\"-\",\"sellQuantity2\":\"-\",\"sellQuantity1\":\"-\",\"buyPrice1\":\"-\",\"sellQuantity4\":\"-\",\"buyPrice2\":\"-\",\"sellQuantity3\":\"-\",\"applicableMargin\":\"12.94\",\"buyPrice4\":\"-\",\"buyPrice3\":\"-\",\"buyPrice5\":\"-\",\"dayLow\":\"1,774.55\",\"deliveryToTradedQuantity\":\"32.82\",\"basePrice\":\"1,804.30\",\"totalTradedVolume\":\"7,64,883\"}],\"optLink\":\"\\/marketinfo\\/sym_map\\/symbolMapping.jsp?symbol=BAJFINANCE&instrument=-&date=-&segmentLink=17&symbolCount=2\"}";
		Object obj=JSONValue.parse(j);  
	    JSONObject jsonObject = (JSONObject) obj;  
	    JSONObject data = (JSONObject) jsonObject.get("data");
	    int open = (int)jsonObject.get("open");
	    System.out.println(open);
	}//main
}//class

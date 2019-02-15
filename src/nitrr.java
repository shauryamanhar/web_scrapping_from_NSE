import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class nitrr {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		final Document document = Jsoup.connect("http://www.nitrr.ac.in").get();
		Elements ee = document.select(".shortcodetextbox");
		Elements e = ee.select(".description");
		System.out.println(e.text());
	}

}

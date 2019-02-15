import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EQutiy64File {

	public static void main(String[] args) {
		//"D:\equity\eq1.xlsx" "D:\equity\aa.csv"
		String csvFile = "C:\\Users\\Shaurya Manhar\\Downloads\\EQUITY_L.csv";//"D:\\equity\\aa.csv";//"C:\Users\Shaurya Manhar\Downloads\EQUITY_L.csv"
		String line = "";
        String cvsSplitBy = ",";
        String[] csvData=null;
        Map<String,String> list = new HashMap<String,String>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                csvData = line.split(cvsSplitBy);
                System.out.println("" + csvData[0] + " ||   " + csvData[1] + "]");
                list.put(csvData[0],csvData[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

	}//main

}//class

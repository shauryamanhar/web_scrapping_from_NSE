import java.io.*;

public class RenameFile {

	public static void main(String[] args) {
		//"D:\equity\eq1.xlsx"
		for(int j=1;j<65;j++) {
			String fileName ="D:\\equity\\eq"+j+".xlsx";
			File f = new File(fileName);
			if(f.exists()) {
				String s="D:\\equity\\eq"+j+".csv";
				File n = new File(s);
				boolean r=f.renameTo(n);
				System.out.println(r+">>"+s);
			}//if
		}//for
	}
}

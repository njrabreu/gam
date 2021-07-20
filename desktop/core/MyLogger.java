package apps.gam.desktop.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLogger {
	
	public static void WriteMessage(String text) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
		System.out.println(formatter.format(date) + " => " + text);
		
	}
	
}

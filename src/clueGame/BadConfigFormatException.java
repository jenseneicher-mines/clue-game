/* Clue Game Part 1
 * Kai Mizuno and Jensen Eicher
 */

package clueGame;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BadConfigFormatException extends Exception {
	
	private static final String ERROR_LOG_FILE = "ErrorLog.txt";
	
	public BadConfigFormatException() {
		this("Unknown Bad Format in Config...");
	}

	public BadConfigFormatException(String errorMessage) {
        super(errorMessage);
        
        //get the date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = new Date();
    	
    	//output the date and error to the error log
    	PrintWriter out;
    	try { 
    		FileWriter fileWriter = new FileWriter(ERROR_LOG_FILE, true);
    		out = new PrintWriter(fileWriter);
    		out.println( dateFormat.format(date) + " --- " + errorMessage );
    		out.close();
    	}
    	catch ( IOException e ) { System.out.println(e.getMessage()); }
    	
    }
}

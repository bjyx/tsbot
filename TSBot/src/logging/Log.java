package logging;

import java.io.FileWriter;
import java.io.IOException;

import game.GameData;
/**
 * Generates a log for a game.
 * @author adalbert
 *
 */
public class Log {
	/**
	 * Writes something to the log. 
	 * @param s is a String, containing the text to be written in the log. 
	 */
	public static void writeToLog(String s) {
		if (GameData.hasGameEnded()) return;
		try {
			FileWriter writer = new FileWriter("log.txt",true);
			writer.write(s + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

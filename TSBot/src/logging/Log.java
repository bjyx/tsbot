package logging;

import java.io.FileWriter;
import java.io.IOException;

import game.GameData;

public class Log {
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

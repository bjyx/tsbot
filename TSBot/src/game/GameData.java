package game;

public class GameData {
	private static boolean started = false;
	private static boolean ended = false;
	
	public static void startGame() {
		started = true;
	}
	
	public static boolean hasGameStarted() {
		return started;
	}
	
	public static void endGame() {
		ended = true;
	}
	
	public static boolean hasGameEnded() {
		return ended;
	}
	
	public static void reset() {
		started = false;
		ended = false;
	}
}

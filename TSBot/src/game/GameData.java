package game;

public class GameData {
	private static boolean started = false;
	private static boolean ended = false;
	
	public void startGame() {
		started = true;
	}
	
	public boolean hasGameStarted() {
		return started;
	}
	
	public void endGame() {
		ended = true;
	}
	
	public boolean hasGameEnded() {
		return ended;
	}
	
	public void reset() {
		started = false;
		ended = false;
	}
}

package game;

import cards.HandManager;
import events.Decision;
import logging.Log;
/**
 * A random number generator between one and six. 
 * @author [REDACTED]
 *
 */
public class Die {
	private static final String[] numbers = {":zero:",":one:",":two:",":three:",":four:",":five:",":six:"};
	
	public int result;
	public Die() {
		result = 0;
	}
	
	/**
	 * Rolls a D6, affects it with PPR if need be, then returns the result.
	 */
	public int roll() {
		result = (int) (Math.random()*6 + 1);
		if (HandManager.effectActive(1350)||HandManager.effectActive(1351)) {
			GameData.dec = new Decision(HandManager.effectActive(1350)?0:1,135);
			GameData.diestore = result;
			synchronized(GameData.sync) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			result = GameData.diestore;
		}
		Log.writeToLog("Roll: " + result);
		return result;
	}
	
	public String toString() {
		return ":game_die:"+numbers[result];
	}
}

package game;

import cards.HandManager;
/**
 * A random number generator between one and six. 
 * @author [REDACTED]
 *
 */
public class Die {
	private static final String[] numbers = {":zero:",":one:",":two:",":three:",":four:",":five:",":six:"};
	
	int result;
	public Die() {
		result = (int)(Math.random()*6 + 1);
	}
	
	/**
	 * Rolls a D6, affects it with PPR if need be, then returns the result.
	 */
	public int roll() {
		result = (int) (Math.random()*6 + 1);
		if (HandManager.effectActive(135)) {
			synchronized(GameData.sync) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public String toString() {
		return ":game_die:"+numbers[result];
	}
}

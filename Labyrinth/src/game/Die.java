package game;

import logging.Log;
/**
 * A random number generator between one and six. 
 * @author adalbert
 *
 */
public class Die {
	private static final String[] numbers = {":zero:",":one:",":two:",":three:",":four:",":five:",":six:"};
	/**
	 * The result of the die roll.
	 */
	public int result;
	/**
	 * Constructor.
	 */
	public Die() {
		result = 0;
	}
	
	/**
	 * Rolls a D6, affects it with PPR if need be, then returns the result.
	 */
	public int roll() {
		result = (int) (Math.random()*6 + 1);
		Log.writeToLog("Roll: " + result);
		return result;
	}
	
	public String toString() {
		return ":game_die:"+numbers[result];
	}
}

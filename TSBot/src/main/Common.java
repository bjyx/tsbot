package main;

import java.awt.Color;

/**
 * A class used to store things that may be used across multiple classes. 
 * @author adalbert
 *
 */
public class Common {
	public static final String[] players = {"USA", "USSR"};
	public static final String[] adject = {"American", "Soviet"};
	
	/**
	 * The opposing power, so I don't have to hit the f*cking % symbol every damn time. 
	 * @param sp is the power in question.
	 * @return Either 0 or 1. 
	 */
	public static int opp(int sp) {
		return (sp+1)%2;
	}
	/**
	 * The color for a given power.
	 * @param sp
	 * @return Red, for the USSR, or blue, for the USA.
	 */
	public static Color spColor(int sp) {
		return (sp==0?Color.blue:Color.red);
	}
}

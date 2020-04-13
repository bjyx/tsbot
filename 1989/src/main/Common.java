package main;

import java.awt.Color;

/**
 * A class used to store things that may be used across multiple classes. 
 * @author adalbert
 *
 */
public class Common {
	public static final String[] players = {"Democrat", "Communist"};
	public static final String[] adject = {"Democratic", "Communist"};
	public static final String[] ideology = {"Democracy", "Communism"};
	public static final String[] icons = {"Worker", "Farmer", "Elite", "Bureaucrat", "Intellectual", "Student", "Church", "Minority"};
	public static final String[] suits = {"Rally", "Strike", "March", "Petition"};
	public static final String[] countries = {"East Germany", "Poland", "Czechoslovakia", "Hungary", "Romania", "Bulgaria"};
	public static final String[] flags = {"<:flag_dd:648119347469877268>",":flag_pl:",":flag_cz:",":flag_hu:",":flag_ro:",":flag_bg:"};
	
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
	 * @return Red, for the communist, or blue, for the democrat.
	 */
	public static Color spColor(int sp) {
		return (sp==0?Color.blue:Color.red);
	}
}

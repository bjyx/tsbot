package main;

import java.awt.Color;

import game.GameData;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;

/**
 * A class used to store things that may be used across multiple classes. 
 * @author adalbert
 *
 */
public class Common {
	/**
	 * Strings for the players. Flavor only.
	 */
	public static final String[] players = {"Democrat", "Communist"};
	/**
	 * Strings for the players. Flavor only.
	 */
	public static final String[] adject = {"Democratic", "Communist"};
	/**
	 * Strings for the players. Flavor only.
	 */
	public static final String[] ideology = {"Democracy", "Communism"};
	/**
	 * Strings for the icons on the board.
	 */
	public static final String[] icons = {"Worker", "Farmer", "Elite", "Bureaucrat", "Intellectual", "Student", "Church", "Minority"};
	/**
	 * Strings for the power struggle suits.
	 */
	public static final String[] suits = {"Rally", "Strike", "March", "Petition"};
	/**
	 * Strings for the countries.
	 */
	public static final String[] countries = {"East Germany", "Poland", "Czechoslovakia", "Hungary", "Romania", "Bulgaria"};
	/**
	 * Strings for each country's flag.
	 */
	public static final String[] flags = {"<:flag_dd:648119347469877268>",":flag_pl:",":flag_cz:",":flag_hu:",":flag_ro:",":flag_bg:"};
	/**
	 * The boundaries of each region, in order of presentation.
	 */
	public static final int[] bracket = {0, 12, 26, 37, 49, 63, 75};
	
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
	
	/**
	 * The text channel for a given power.
	 * 
	 */
	public static MessageChannel spChannel(int sp) {
		return (sp==0?GameData.txtdem:GameData.txtcom);
	}
	
	/**
	 * The role for a given power.
	 */
	public static Role spRole(int sp) {
		return (sp==0?GameData.roledem:GameData.rolecom);
	}
}

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
	 * Strings representing numbers between 0 and 6. 
	 */
	public static final String[] numbers = {"zero","one","two","three","four","five","six","seven","eight","nine"};
	/**
	 * Strings for the players. Flavor only.
	 */
	public static final String[] players = {"USA", "Jihadist"};
	/**
	 * Strings for the players. Flavor only.
	 */
	public static final String[] adject = {"American", "Jihadist"};
	/**
	 * Strings for the players. Flavor only.
	 */
	public static final String[] players_e = {"<:InflAC:648119364238966833>", ":star_and_crescent:", "<:InflNC:648119367728496661>"};
	/**
	 * Strings for country types.
	 */
	public static final String[] religs = {"Sunni", "Shia-Mix", "Non-Muslim", "Special"};
	/**
	 * Strings for Governances.
	 */
	public static final String[] govs = {"Untested","Good", "Fair", "Poor", "Islamist Rule"};
	/**
	 * Strings for Alignments.
	 */
	public static final String[] aligns = {"Untested", "Ally", "Neutral", "Adversary"};
	/**
	 * Strings for Postures.
	 */
	public static final String[] posts = {"Soft", "Untested", "Hard"};
	/**
	 * Emoji for country types.
	 */
	public static final String[] relig_e = {":white_circle:", ":green_circle:", ":blue_circle:", ":red_circle:"};
	/**
	 * Emoji for Governances.
	 */
	public static final String[] gov_e = {":question:",":blue_square:", ":yellow_square:", ":red_square:", ":star_and_crescent:"};
	/**
	 * Strings for Alignments.
	 */
	public static final String[] align_e = {":question:", ":handshake:", ":scales:", ":crossed_swords:"};
	/**
	 * Strings for Postures.
	 */
	public static final String[] post_e = {":sponge:", ":question:", ":bricks:"};
	
	/**
	 * Representations of units.
	 */
	public static final String[] units_e = {":compass:",":brown_square:",":blue_circle:","white_large_square:",":purple_square:",":star_and_crescent:",":flag_iq:"};
	
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
	 * @return Green, for the communist, or blue, for the democrat.
	 */
	public static Color spColor(int sp) {
		return (sp==0?Color.blue:Color.green);
	}
	
	/**
	 * The text channel for a given power.
	 * 
	 */
	public static MessageChannel spChannel(int sp) {
		return (sp==0?GameData.txtusa:GameData.txtjih);
	}
	
	/**
	 * The role for a given power.
	 */
	public static Role spRole(int sp) {
		return (sp==0?GameData.roleusa:GameData.rolejih);
	}
}

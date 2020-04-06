package game;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cards.HandManager;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
/**
 * Handles a list of players who are playing this game. No AI yet. 
 * @author adalbert
 *
 */
public class PlayerList {
	/**
	 * The array.
	 */
	private static User[] players = {null, null};
	/**
	 * Adds a player to the list.
	 * @param u is the player in question.
	 */
	public static void addPlayer(User u) {
		if (players[0]==null) {
			players[0]=u;
			return;
		}
		if (players[1]==null) {
			players[1]=u;
			return;
		}
	}
	/**
	 * Removes a player from the list, if present.
	 * @param u is the player in question.
	 */
	public static void removePlayer(User u) {
		if (players[0]==u) {
			players[0]=null;
			return;
		}
		if (players[1]==u) {
			players[1]=null;
			return;
		}
	}
	/**
	 * Determines who is on which side of the war. 
	 */
	public static void detSides() {
		Random rng = new Random();
		int side = rng.nextInt(1);
		if(side == 0) return;
		User temp = players[0];
		players[0] = players[1];
		players[1] = temp;
	}
	/**
	 * Gets a list of players as an embed.
	 * @param started is whether the game has started or not. 
	 * @return a MessageEmbed containing a list of players. 
	 */
	public static MessageEmbed getPlayers(boolean started) {
		EmbedBuilder builder = new EmbedBuilder().setTitle("Superpowers").setColor(Color.darkGray);
		if (started) {
			builder.setDescription(":flag_us: " + players[0].getAsTag() + "\n" + MapManager.get(85) + " " + players[1].getAsTag());
		}
		else {
			builder.setDescription(players[0] + "\n" + players[1]);
		}
		return builder.build();
	}
	/**
	 * Converts the array containing the players into a list.
	 * @return a List of Users.
	 */
	public static List<User> getArray() {
		return Arrays.asList(players);
	}
	/**
	 * 
	 * @return the first player in the array, corresponding to the Democrat. 
	 */
	public static User getDem() {
		return players[0];
	}
	/**
	 * 
	 * @return the second player in the array, corresponding to the Communist.
	 */
	public static User getCom() {
		return players[1];
	}
	/**
	 * Provides the phasing player for a given action round. Why this is here of all places I have no idea.
	 * @return an integer. 
	 */
	public static int getPhasing() {
		return GameData.getAR()%2; //ar1 is USSR, ar2 is US, and so forth until ar12/14/16
	}
	/**
	 * Provides the opposing player for a given action round. 
	 * @return an integer. 
	 */
	public static int getOpposing() {
		return (getPhasing()+1)%2;
	}
}

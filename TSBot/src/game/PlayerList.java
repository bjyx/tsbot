package game;

import java.util.Random;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

public class PlayerList {
	private static User[] players;
	
	public PlayerList() {
		players = new User[2];
		players[0]=null;
		players[1]=null;
	}
	
	public void addPlayer(User u) {
		if (players[0]==null) {
			players[0]=u;
			return;
		}
		if (players[1]==null) {
			players[1]=u;
			return;
		}
	}
	public void removePlayer(User u) {
		if (players[0]==u) {
			players[0]=null;
			return;
		}
		if (players[1]==u) {
			players[1]=null;
			return;
		}
	}
	public void detSides() {
		Random rng = new Random();
		int side = rng.nextInt(1);
		if(side == 0) return;
		User temp = players[0];
		players[0] = players[1];
		players[1] = temp;
	}
	public MessageEmbed getPlayers(boolean started) {
		EmbedBuilder builder = new EmbedBuilder().setTitle("Superpowers").setColor(8519882);
		if (started) {
			builder.setDescription(":flag_us: " + players[0] + "\n:flag_su: " + players[1]);
		}
		else {
			builder.setDescription(players[0] + "\n" + players[1]);
		}
		return builder.build();
	}
	public User[] getArray() {
		return players;
	}
	public User getUSA() {
		return players[0];
	}
	public User getSSR() {
		return players[1];
	}
	public User getPhasing(int ar) {
		if (ar==0) return null; //(I can't deal with this yet)
		return players[ar%2]; //ar1 is USSR, ar2 is US, and so forth until ar12/14/16
	}
}

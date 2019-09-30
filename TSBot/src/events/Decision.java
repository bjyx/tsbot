package events;

import java.awt.Color;

import cards.CardList;
import game.GameData;
import main.Launcher;
import map.MapManager;

/**
 * A class defining decisions required from a multitude of cards. 
 * @author [REDACTED]
 *
 */
public class Decision {
	/**
	 * The card involved in this decision.
	 */
	public int card;
	/**
	 * The superpower making the decision.
	 */
	public int sp;
	
	/**
	 * Creates a decision
	 * @param s is the superpower making this decision.
	 * @param c is the card the decision is being made on.
	 */
	public Decision(int s, int c) {
		card = c;
		sp = s;
	}
	/**
	 * The result of selecting to discard a card on {@link events.Blockade 010 Blockade}.
	 * @param c is the card to be discarded.
	 */
	public static void BerlinAirLift(int c) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Berlin Airlift")
			.setDescription("West Berlin successfully supplied for over a year; Soviets forced to lift blockade")
			.setFooter("\"We can haul anything.\" \n"
					+ "- Curtis LeMay, 1948",Launcher.url("countries/us.png"))
			.setColor(Color.blue);
		builder.addField("Soviets lift blockade of Berlin.", "Discarded card: "+CardList.getCard(c), false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}
	/**
	 * The result of electing not to discard a card on {@link events.Blockade 010 Blockade}.
	 */
	public static void BerlinConcede() {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Berlin Blockade")
			.setDescription("Concessions made to the Soviet Union to prevent starvation")
			.setFooter("\"What happens to Berlin, happens to Germany; what happens to Germany, happens to Europe.\" \n"
					+ "- Vyacheslav Molotov, 1948",Launcher.url("countries/su.png"))
			.setColor(Color.red);
		builder.changeInfluence(20, 0, -MapManager.get(20).influence[0]);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	/**
	 * The result of electing to boycott an installment of the {@link events.OlympicGames 020 Olympic Games}.
	 */
	public static void OlympicBoycott() {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Olympics Boycotted!")
			.setDescription("")
			.setFooter("\"To act differently would be tantamount to approving of the anti-Olympic actions of the U.S. authorities and organizers of the games.\" \n"
					+ "- Tass, 1984",Launcher.url("countries/su.png"))
			.setColor(Color.DARK_GRAY);
		builder.changeDEFCON(-1);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		GameData.txtchnl.sendMessage("Awaiting " + (GameData.phasing()==0?GameData.roleusa.getAsMention():GameData.rolessr.getAsMention())+" to play 4 Operations points (remember to adjust to account for Red Scare/Purge/Containment/Brezhnev Doctrine).").complete();
	}
	/**
	 * The result of electing to participate in an installment of the {@link events.OlympicGames 020 Olympic Games}.
	 */
	public static void OlympicGames() {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		int[] die = {0,0};
		
		while (die[0] == die[1]) {
			die[0] = (int) (Math.random()*6 + 1);
			die[1] = (int) (Math.random()*6 + 1);
			die[OlympicGames.host] += 2;
			builder.addField(":flag_us::"+CardEmbedBuilder.numbers[die[0]]+"::flag_su::"+CardEmbedBuilder.numbers[die[1]]+":",die[0]==die[1]?"A tie - roll again.":("And "+(die[0]>die[1]?"the Americans":"the Soviets")+ " take home the gold!"),false);
		}
		builder.setTitle("Olympics in " + (OlympicGames.host==0?"Los Angeles": "Moscow"))
			.setDescription("")
			.setFooter("\"You were born to be a player. You were meant to be here. This moment is yours.\"\n"
					+ "- Herb Brooks, 1980",Launcher.url("countries/us.png"))
			.setColor(die[0]>die[1]?Color.BLUE:Color.RED);
		builder.changeVP(die[0]>die[1]?2:-2);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}
}

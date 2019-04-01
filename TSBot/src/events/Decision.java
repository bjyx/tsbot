package events;

import java.awt.Color;

import cards.CardList;
import game.GameData;
import map.MapManager;

public class Decision {
	public int card;
	
	public static void BerlinAirLift(int card) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Berlin Airlift")
			.setDescription("Successfully supplied West Berlin.")
			.setFooter("\"We can haul anything.\" - Curtis LeMay, 1948","images/countries/us.png")
			.setColor(Color.blue);
		builder.addField("Soviets lift blockade of Berlin.", "Discarded card: "+CardList.getCard(card), false);
		GameData.txtchnl.sendMessage(builder.build());
		GameData.txtchnl.sendMessage("`Event Complete`");
	}

	public static void BerlinConcede() {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Berlin Blockade")
			.setDescription("No airlift comes. Concessions have been made to the Soviet Union.")
			.setFooter("\"What happens to Berlin, happens to Germany; what happens to Germany, happens to Europe.\" - Vyacheslav Molotov, 1948","images/countries/su.png")
			.setColor(Color.red);
		builder.changeInfluence(20, 0, -MapManager.map.get(20).influence[0]);
		GameData.txtchnl.sendMessage(builder.build());
		GameData.txtchnl.sendMessage("`Event Complete`");
	}
}

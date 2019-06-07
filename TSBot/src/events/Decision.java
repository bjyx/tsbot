package events;

import java.awt.Color;

import cards.CardList;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class Decision {
	public int card;
	public int sp;
	
	public Decision(int s, int c) {
		card = c;
		sp = s;
	}
	
	public static void BerlinAirLift(int c) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Berlin Airlift")
			.setDescription("West Berlin successfully supplied for over a year; Soviets forced to lift blockade")
			.setFooter("\"We can haul anything.\" \n- Curtis LeMay, 1948",Launcher.url("countries/us.png"))
			.setColor(Color.blue);
		builder.addField("Soviets lift blockade of Berlin.", "Discarded card: "+CardList.getCard(c), false);
		GameData.txtchnl.sendMessage(builder.build());
	}

	public static void BerlinConcede() {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Berlin Blockade")
			.setDescription("No airlift comes; concessions made to the Soviet Union")
			.setFooter("\"What happens to Berlin, happens to Germany; what happens to Germany, happens to Europe.\" \n- Vyacheslav Molotov, 1948",Launcher.url("countries/su.png"))
			.setColor(Color.red);
		builder.changeInfluence(20, 0, -MapManager.get(20).influence[0]);
		GameData.txtchnl.sendMessage(builder.build());
	}
}

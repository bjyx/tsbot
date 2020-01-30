package yiyo;

import java.awt.Color;

import cards.HandManager;
import cards.Operations;
import events.Card;
import events.CardEmbedBuilder;
import events.Decision;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class Finlandization extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Finlandization")
			.setDescription("President Kekkonen folds on USSR-favoring foreign policy")
			.setFooter("\"If Adenauer were here with us in the sauna, we could see for ourselves that Germany is and will remain divided but also that Germany never will rise again.\"\n" + 
					"- Nikita Khrushchev to Urho Kekkonen, 1957", Launcher.url("people/khrushchev.png"))
			.setColor(sp==0?Color.blue:Color.red)
			.addField("Idänkortti", "The USSR may conduct "+MapManager.get(7).influence[1]+" realignments in Europe" + (HandManager.effectActive(21)?" pursuant to the restrictions of NATO.":"."), false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		GameData.dec = new Decision(1,119);
		GameData.ops = new Operations(1, MapManager.get(7).influence[1], false, true, false,false,false);
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "119";
	}

	@Override
	public String getName() {
		return "Finlandization";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 0;
	}

	@Override
	public int getAssociation() {
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "The USSR takes the amount of influence it has in Finland and performs that many realignments in Europe. These realignments are subject to restrictions by NATO.";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: Operations.";
	}

}

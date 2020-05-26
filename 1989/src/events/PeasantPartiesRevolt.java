package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class PeasantPartiesRevolt extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Government Formation Reaches Impasse")
			.setDescription("Proposed communist government rejected by influential democratic parties")
			.setColor(Color.blue);
		HandManager.addEffect(72);
		Log.writeToLog("Peasant Parties Revolt active.");
		builder.addField("Coalition Proposal", "If the Democrat controls a Farmer Space in the next country targeted by a Power Struggle, the Democrat gains one Power Struggle card, and the Communist loses one.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "072";
	}

	@Override
	public String getName() {
		return "Peasant Parties Revolt";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 1;
	}

	@Override
	public int getAssociation() {
		return 0;
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
		return "If the Democrat controls a Farmer space in the next country targeted by a Power Struggle, the Democrat gains one card in that Power Struggle, and the Communist loses one.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

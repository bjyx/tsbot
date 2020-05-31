package events;

import java.awt.Color;

import cards.HandManager;
import events.Card;
import logging.Log;
import main.Launcher;

public class NationalSalvationFront extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("National Salvation Front")
		.setColor(Color.red);
		builder.addField("","When the next Power Struggle in the Balkans happens, the Communist gains two Power Struggle cards, and the Democrat loses two.",false);
		HandManager.addEffect(102);
		Log.writeToLog("National Salvation Front active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "102";
	}

	@Override
	public String getName() {
		return "National Salvation Front";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 2;
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
		return "*When the next Power Struggle in the Balkans happens, the Communist gains two Power Struggle cards, and the Democrat loses two.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

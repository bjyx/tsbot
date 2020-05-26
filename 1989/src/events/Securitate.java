package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import logging.Log;
import main.Launcher;

public class Securitate extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Securitate")
		.setColor(Color.red);
		builder.addField("","The Democrat's hand will be visible to the Communist for all Romanian Power Struggles.",false);
		HandManager.addEffect(70);
		Log.writeToLog("Securitate active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return !HandManager.effectActive(108);
	}

	@Override
	public String getId() {
		return "070";
	}

	@Override
	public String getName() {
		return "Securitate";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 1;
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
		if (HandManager.effectActive(108)) return "The Army backs the Romanian Revolution! Play for Operations only.";
		return "*The Democrat's hand will be visible to the Communist for all Romanian Power Struggles. This event is cancelled/prevented by " + CardList.getCard(108) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

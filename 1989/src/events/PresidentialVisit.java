package events;

import java.awt.Color;

import cards.HandManager;
import logging.Log;
import main.Launcher;

public class PresidentialVisit extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("George Bush Visits Eastern Europe")
		.setColor(Color.blue);
		builder.addField("Prudence (yes, again)","The Communist hand next turn is reduced to 7 cards.",false);
		HandManager.addEffect(65);
		Log.writeToLog("Presidential Visit active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "065";
	}

	@Override
	public String getName() {
		return "Presidential Visit";
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
		return "*The Communist hand is reduced to 7 cards next turn.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

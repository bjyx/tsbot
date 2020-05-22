package events;

import java.awt.Color;

import cards.HandManager;
import logging.Log;
import main.Launcher;

public class SinatraDoctrine extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Sinatra Doctrine")
		.setColor(Color.blue);
		builder.addField("My Way","The Democrat adds 1 Operations point to any card played for operations for the rest of this turn.",false);
		HandManager.addEffect(50);
		Log.writeToLog("Sinatra active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "050";
	}

	@Override
	public String getName() {
		return "Sinatra Doctrine";
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
		return "The Democrat adds 1 Operations Point to any card played for Operations this turn.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

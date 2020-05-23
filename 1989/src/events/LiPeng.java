package events;

import java.awt.Color;

import cards.HandManager;
import logging.Log;
import main.Launcher;

public class LiPeng extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Li Peng Declares Martial Law")
		.setColor(Color.red);
		builder.addField("\"Butcher of Beijing\"","The Communist adds 1 to all future T-Square rolls.",false);
		HandManager.addEffect(53);
		Log.writeToLog("Li Peng active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "053";
	}

	@Override
	public String getName() {
		return "Li Peng";
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
		return "*Add 1 to all Communist T-Square rolls for the rest of the game.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

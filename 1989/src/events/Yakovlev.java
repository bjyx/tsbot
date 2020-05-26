package events;

import java.awt.Color;

import cards.HandManager;
import logging.Log;
import main.Launcher;

public class Yakovlev extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Yakovlev Counsels Gorbachev") //rnged a historical figure
		.setColor(Color.blue);
		builder.addField("Godfather of Glasnost","If the Democrat wins the next Power Struggle, he adds 1 to both rolls. ",false);
		HandManager.addEffect(62);
		Log.writeToLog("Yakovlev active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "062";
	}

	@Override
	public String getName() {
		return "Yakovlev Counsels Gorbachev";
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
		return "*If the next Power Struggle is won by the Democrat, add 1 to both rolls made after the struggle.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

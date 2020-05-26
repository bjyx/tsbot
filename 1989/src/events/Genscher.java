package events;

import java.awt.Color;

import cards.HandManager;
import logging.Log;
import main.Launcher;

public class Genscher extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Genscher Brokers Deal with GDR")
		.setDescription("Prague Embassy Germans allowed to travel to West Germany")
		.setColor(Color.blue)
		.setFooter("\"We have come to you to tell you that today, your departure—\"\n"
				+ "- Hans-Dietrich Genscher, 30 September", Launcher.url("people/genscher.png"));
		builder.addField("Embassy speech","The Democrat ignores Communist control for the purposes of placing Support in East Germany for the rest of the turn.",false);
		HandManager.addEffect(63);
		Log.writeToLog("Genscher active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "063";
	}

	@Override
	public String getName() {
		return "Genscher";
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
		return "The Democrat ignores Communist control for the purposes of placing Support in East Germany for the rest of the turn. ";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

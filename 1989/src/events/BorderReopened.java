package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class BorderReopened extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Hungary Tears Down Border Fence")
			.setColor(Color.blue);
		builder.addField("", "All Democratic Operations exclusively used in East Germany will now have one extra point to use.", false);
		HandManager.addEffect(58);
		Log.writeToLog("Austria-Hungary Border Reopened active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "058";
	}

	@Override
	public String getName() {
		return "Austria—Hungary Border Reopened";
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
		return "*For the rest of this turn, the Democrat gets a +1 bonus to Operations if they are all used in East Germany.*";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Operations for the rest of this turn: if you want to take advantage of this ability on support checks in East Germany, write `habsburg` after your target—this locks your second support check to East Germany.";
	}

}

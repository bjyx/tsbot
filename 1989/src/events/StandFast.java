package events;

import java.awt.Color;

import cards.HandManager;
import logging.Log;
import main.Common;
import main.Launcher;

public class StandFast extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Stand Fast")
		.setColor(Common.spColor(sp));
		builder.addField("","For the rest of the turn, the " + Common.players[Common.opp(sp)] + " gets a -1 malus to Support Check in spaces controlled by the " + Common.players[sp] + ".",false);
		HandManager.addEffect(1000 + sp);
		Log.writeToLog("Stand Fast active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "100";
	}

	@Override
	public String getName() {
		return "Stand Fast";
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
		return 2;
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
		return "*Your opponent gets a -1 malus to all Support Checks in spaces you control for the rest of this turn.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

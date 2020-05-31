package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class Ligachev extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Ligachev")
			.setColor(Color.red);
		builder.addField("","If "+CardList.getCard(14)+" is not played for the event on the Democrat's next action round, the Communist gains 3 VP before any Democrat point award.",false);
		HandManager.addEffect(99);
		Log.writeToLog("Ligachev Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "099";
	}

	@Override
	public String getName() {
		return "Ligachev";
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
		return "If "+CardList.getCard(14)+" is not played for the event on the Democrat's next action round, the Communist gains 3 VP before any Democrat point award.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

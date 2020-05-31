package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class ArmyBacksRevolution extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Army Backs Romanian Revolution")
			.setColor(Color.blue);
		builder.addField("",HandManager.removeEffect(70)?"The effects of " + CardList.getCard(70) + " are cancelled.":"The event for " + CardList.getCard(70) + " can no longer be played.",false);
		Log.writeToLog("Army Backs Revolution Active.");
		HandManager.addEffect(108);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "108";
	}

	@Override
	public String getName() {
		return "Army Backs Revolution";
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
		return "*Cancels/Prevents the effects of " + CardList.getCard(70) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

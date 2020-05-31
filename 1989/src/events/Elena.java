package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import logging.Log;
import main.Launcher;

public class Elena extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Elena")
		.setColor(Color.red);
		builder.changeInfluence(51, 1, 2);
		builder.addField("","For the rest of this turn, the Democrat receives a -1 malus to Support Checks in Romania.",false);
		HandManager.addEffect(101);
		Log.writeToLog("Elena active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return !HandManager.effectActive(97);
	}

	@Override
	public String getId() {
		return "101";
	}

	@Override
	public String getName() {
		return "Elena";
	}

	@Override
	public int getOps() {
		return 1;
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
		if (HandManager.effectActive(97)) return "Ceaușescu has been deposed! Play for Operations only.";
		return "Add 2 Communist Support to Cluj-Napoca. *For the rest of this turn, the Democrat receives a -1 malus to Support Checks in Romania. This event is cancelled/prevented by " + CardList.getCard(97) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

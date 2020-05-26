package events;

import java.awt.Color;

import cards.HandManager;
import logging.Log;
import main.Launcher;

public class FRGEmbassies extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Travel Restrictions Strand East Germans Abroad")
		.setDescription("West German embassies converted into refugee camps")
		.setColor(Color.blue);
		builder.addField("","The Democrat receives a +1 bonus to Support Checks in Eastern Europe for the rest of this turn.",false);
		HandManager.addEffect(74);
		Log.writeToLog("FRG Embassies active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "074";
	}

	@Override
	public String getName() {
		return "FRG Embassies";
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
		return "*For the rest of this turn, the Democrat receives a +1 bonus to Support Checks in Eastern Europe.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

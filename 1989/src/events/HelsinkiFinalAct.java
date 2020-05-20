package events;

import java.awt.Color;

import cards.HandManager;
import logging.Log;
import main.Launcher;

public class HelsinkiFinalAct extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Helsinki Accords signed") //rnged a historical figure
		.setDescription("35 countries agree to respect human rights")
		.setColor(Color.blue);
		builder.addField("Article VII","The Democrat receives 1 VP for every support check conducted by the Communist on a Student or Intellectual space.",false);
		HandManager.addEffect(26);
		Log.writeToLog("Helsinki Final Act active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "026";
	}

	@Override
	public String getName() {
		return "Helsinki Final Act";
	}

	@Override
	public int getOps() {
		return 1;
	}

	@Override
	public int getEra() {
		return 0;
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
		return "*The Democrat will gain 1 VP for every Support Check the Communist conducts on a Student or Intellectual Space.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

package events;

import java.awt.Color;

import cards.HandManager;
import logging.Log;
import main.Launcher;

public class Grenztruppen extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Republikflucht rates drop") 
		.setColor(Color.red);
		builder.addField("Grenztruppen","The Democrat gets a -1 malus to support checks in East Germany for the rest of this turn.",false);
		HandManager.addEffect(59);
		Log.writeToLog("Grenztruppen active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "059";
	}

	@Override
	public String getName() {
		return "GrenzTruppen";
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
		return "*For the rest of this turn, Democratic support checks in East Germany receive a -1 malus.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return null;
	}

}

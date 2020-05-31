package events;

import java.awt.Color;

import cards.HandManager;
import game.Die;
import game.GameData;
import logging.Log;
import main.Common;
import map.MapManager;

public class Adamec extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		Die die = new Die();
		int mod = die.roll();
		Log.writeToLog("Roll: " + mod);
		CardEmbedBuilder builder = new CardEmbedBuilder();
		String adjacents = "";
		for (int i=Common.bracket[2]; i<Common.bracket[3]; i++) {
			if (MapManager.get(i).isControlledBy()==0&&MapManager.get(i).icon==0) {
				mod--;
				adjacents += MapManager.get(i);
				Log.writeToLog(MapManager.get(i).shorthand.toUpperCase() + ": -1");
			}
		}
		builder.addField("Attempted Coalition", die + (mod==die.result?"":(" - " + adjacents)), false);
		if (mod>=3) {
			builder.setTitle("Adamec Forms Coalition Government")
				.setColor(Color.red);
			GameData.dec=new Decision(1, 88);
		}
		else {
			builder.setTitle("Adamec Resigns")
				.setColor(Color.blue);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "088";
	}

	@Override
	public String getName() {
		return "Adamec";
	}

	@Override
	public int getOps() {
		return 2;
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
		return "The Communist rolls a die and subtracts the number of worker spaces controlled by the Democrat in Czechoslovakia. On a roll of 3-6, add 4 Communist Support to Czechoslovakia (no more than 2 per space).";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: Support, if necessary.";
	}

}

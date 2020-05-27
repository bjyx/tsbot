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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return null;
	}

}

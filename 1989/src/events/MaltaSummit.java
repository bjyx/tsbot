package events;

import java.awt.Color;

import game.Die;
import game.GameData;
import logging.Log;
import main.Common;
import map.MapManager;

public class MaltaSummit extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		Die die = new Die();
		int mod = die.roll() + (5 - GameData.getStab());
		Log.writeToLog("Roll: " + mod);
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.addField("Malta Summit", die + (mod==die.result?"":(" - " + (5 - GameData.getStab()))), false);
		if (mod>=4) {
			builder.setTitle("An End to the Cold War")
				.setColor(Color.blue);
			builder.changeVP(3);
			GameData.dec=new Decision(0, 110);
			GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", you may now remove 5 Communist Support from Elite spaces.");
		}
		else {
			builder.setTitle("An End to the Cold War?")
				.setColor(Color.red);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.getStab()>1;
	}

	@Override
	public String getId() {
		return "110";
	}

	@Override
	public String getName() {
		return "Malta Summit";
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
		if (GameData.getStab()==1) return "Gorbachev has been deposed! Play for Operations only.";
		return "The Democrat rolls a die and adds one for each Baltic States event that has happened (Sąjūdis, Baltic Way, Breakaway Baltic States). On a roll of 4-6, the Democrat gains 3 VP and removes 5 Communist support from elite spaces. *May not be played after ";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: Support.";
	}

}

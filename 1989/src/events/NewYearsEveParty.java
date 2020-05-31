package events;

import java.awt.Color;

import cards.HandManager;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import logging.Log;
import main.Launcher;
import powerstruggle.PowerStruggle;

public class NewYearsEveParty extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		if (Integer.parseInt(args[1])==1) {
			builder.setTitle("The New Year Approaches.")
			.setColor(Color.red);
			int x=0;
			for (int i : PowerStruggle.retained) {
				if (i!=-1) x++;
			}
			if (x>=4) builder.changeVP(-3);
			else builder.changeVP(3);
			builder.addField("","The game will end at the end of this turn.",false);
			HandManager.addEffect(104);
			Log.writeToLog("The game will end this turn.");
			
		}
		else {
			builder.setTitle("Take your time. The New Year is still far away.")
				.setColor(Color.yellow);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			Log.writeToLog("Nothing happens.");
		}
		
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "104";
	}

	@Override
	public String getName() {
		return "New Year's Eve Party";
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
		if (args.length<2) return false;
		if (args[1].equals("1") || args[1].equals("0")) return true;
		return false;
	}

	@Override
	public String getDescription() {
		return "The Communist may choose to have the game end at the end of this turn without going to Final Scoring. Selecting this option yields an immediate 3 VP for the Communist if he still holds power in at least 4 countries, and 3 VP for the Democrat if he does not. At the end of this turn, the Democrat may choose to conduct a Power Struggle in a country where the Communist still holds power.";
	}

	@Override
	public String getArguments() {
		return "0 (you do *not* want to end the game this turn) or 1 (you do). **This is not a decision to be taken lightly.**";
	}

}

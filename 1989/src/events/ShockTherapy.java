package events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import game.Die;
import game.GameData;
import logging.Log;
import main.Common;
import map.MapManager;
import powerstruggle.PowerStruggle;

public class ShockTherapy extends Card {

	public static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		Die die = new Die();
		int mod = die.roll();
		Log.writeToLog("Roll: " + mod);
		CardEmbedBuilder builder = new CardEmbedBuilder();
		String adjacents = "";
		for (int i=Common.bracket[target]; i<Common.bracket[target+1]; i++) {
			if (MapManager.get(i).isControlledBy()==1&&(MapManager.get(i).icon==0||MapManager.get(i).icon==1)) {
				mod--;
				adjacents += MapManager.get(i);
				Log.writeToLog(MapManager.get(i).shorthand.toUpperCase() + ": -1");
			}
		}
		builder.addField("Shock Therapy", die + (mod==die.result?"":(" - " + adjacents)), false);
		if (mod>=3) {
			builder.setTitle("Radical Economic Reform Revitalizes " + Common.countries[target])
				.setColor(Color.blue);
			builder.changeVP(3);
			GameData.dec=new Decision(0, 93);
		}
		else {
			builder.setTitle("Recession in " + Common.countries[target])
				.setColor(Color.red);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		for (int i=0; i<5; i++) {
			if (PowerStruggle.retained[i]==-1) return true; //a country exists under the democrat
		}
		return false;
	}

	@Override
	public String getId() {
		return "093";
	}

	@Override
	public String getName() {
		return "Shock Therapy";
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
		ArrayList<Character> a = new ArrayList<Character>();
		a.addAll(Arrays.asList('e','p','c','h','r','b'));
		if (args.length<2) return false;
		char b = args[1].charAt(0);
		if (a.indexOf(b)==-1) return false;
		target = a.indexOf(b);
		return (PowerStruggle.retained[target]==-1);
	}

	@Override
	public String getDescription() {
		return "Select one country where the Democrat holds Power. Roll a die and subtract 1 for each worker or farmer space under Communist control. On a roll of 3 or more, the Democrat gains 3 VP and may place a total of 3 Support in the targeted country.";
	}

	@Override
	public String getArguments() {
		return "Event: The targeted country.\n"
				+ "Decision: If successful, the influence. ";
	}

}

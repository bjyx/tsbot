package events;

import java.awt.Color;

import cards.HandManager;
import game.Die;
import game.GameData;
import logging.Log;
import main.Common;
import main.Launcher;
import map.MapManager;
import powerstruggle.PowerStruggle;

public class WorkersRevolt extends Card {

	private static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		Die die = new Die();
		int mod = die.roll();
		Log.writeToLog("Roll: " + mod);
		CardEmbedBuilder builder = new CardEmbedBuilder();
		String adjacents = "";
		for (int i : MapManager.get(target).adj) {
			if (MapManager.get(i).isControlledBy()==(sp+1)%2) {
				mod--;
				adjacents += MapManager.get(i);
				Log.writeToLog(MapManager.get(i).shorthand.toUpperCase() + ": -1");
			}
		}
		builder.addField("Worker Revolt in " + MapManager.get(target).name, die + (mod==die.result?":":(": - " + adjacents)), false);
		if (mod>=4) {
			builder.setTitle("Austerity Measures Overturned In " + MapManager.get(target).name)
				.setColor(Common.spColor(sp));
			builder.changeInfluence(target, sp, MapManager.get(target).support[(sp+1)%2]);
			builder.changeInfluence(target, (sp+1)%2, -MapManager.get(target).support[(sp+1)%2]);
		}
		else {
			builder.setTitle("Worker Revolt Put Down")
				.setColor(Common.spColor(Common.opp(sp)));
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "078";
	}

	@Override
	public String getName() {
		return "Workers Revolt";
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
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		if (args.length<2) return false;
		target = MapManager.find(args[1]);
		if (target==-1) return false;
		if (MapManager.get(target).icon!=0) return false;
		if (sp==0^PowerStruggle.retained[MapManager.get(target).region]==-1) return false; //power reqt
		return true;
	}

	@Override
	public String getDescription() {
		return "Target a single Worker Space in a country where your opponent holds power. Roll a die and subtract the number of spaces neighboring the target under your opponent's control. On a roll of 4-6, replace all opposing influence in the country with your own.";
	}

	@Override
	public String getArguments() {
		return "The country to target.";
	}

}

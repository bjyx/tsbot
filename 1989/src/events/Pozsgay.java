package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Common;
import main.Launcher;
import map.MapManager;

public class Pozsgay extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> doable;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Pozsgay Defends the Revolution")
			.setDescription("State Minister deems 1956 a \"popular uprising\"")
			.setColor(Color.red);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "All of Hungary has embraced Democratic ideals. How did *that* happen?", false);
		}
		else for (int c : order) {
			builder.changeInfluence(c, 1, 1);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "018";
	}

	@Override
	public String getName() {
		return "Pozsgay Defends the Revolution"; //the actual card spells it Poszgay, which is wrong
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 0;
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
		doable = new ArrayList<Integer>();
		order = new ArrayList<Integer>();
		for (int i=Common.bracket[3]; i<Common.bracket[4]; i++) {
			if (MapManager.get(i).isControlledBy()!=0) {
				doable.add(i);
			}
		}
		if (doable.size()<=4) {
			order = doable;
			return true;
		}
		if (args.length!=5) return false;
		for (int i=1; i<=4; i++) {
			int c = MapManager.find(args[i]);
			if (c==-1) return false;
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
		}
		if (!doable.containsAll(order)) return false; // all need to be able to be played into
		return true;
	}

	@Override
	public String getDescription() {
		return "Place 1 Communist Support in four spaces in Hungary not under Democratic control.";
	}

	@Override
	public String getArguments() {
		return "Four spaces in Hungary. These cannot be controlled by Democrats.";
	}

}

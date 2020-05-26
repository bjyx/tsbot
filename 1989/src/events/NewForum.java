package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Common;
import map.MapManager;

public class NewForum extends Card {
	
	private static ArrayList<Integer> order;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("New Forum")
			.setColor(Color.blue);
		for (int c : order) {
			builder.changeInfluence(c, 0, 1);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "066";
	}

	@Override
	public String getName() {
		return "New Forum";
	}

	@Override
	public int getOps() {
		return 1;
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
		order = new ArrayList<Integer>();
		if (args.length!=4) return false;
		for (int i=1; i<=3; i++) {
			int c = MapManager.find(args[i]);
			if (c==-1) return false;
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
			if (!MapManager.get(c).inRegion(0)) return false;
		}
		return true;
	}

	@Override
	public String getDescription() {
		return "Place 1 Democratic Support in each of 3 spaces in East Germany.";
	}

	@Override
	public String getArguments() {
		return "The three spaces.";
	}

}

package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class Decolonization extends Card {
	
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> doable;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Decolonization")
			.setDescription("")
			.setFooter("", Launcher.url(""))
			.setColor(Color.red);
		for (int c : order) {
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
		return "030";
	}

	@Override
	public String getName() {
		return "Decolonization";
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		doable = new ArrayList<Integer>();
		order = new ArrayList<Integer>();
		for (int i=33; i<64; i++) {
			if (MapManager.get(i).region==5||MapManager.get(i).region==6) {
				doable.add(i);
			}
		}
		if (args.length!=5) return false;
		for (int i=1; i<=4; i++) {
			int c = MapManager.find(args[i]);
			order.add(c);
			if (order.indexOf(c)!=i) return false; // no duplicates plox
		}
		if (!doable.containsAll(order)) return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "Add 1 USSR influence to each of four countries in Africa or Southeast Asia.";
	}

	@Override
	public String getArguments() {
		return "The four countries. All four must be valid aliases of countries present in Africa or Southeast Asia.";
	}

}

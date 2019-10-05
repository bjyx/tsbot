package events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class MuslimRevolution extends Card {
	private static final List<Integer> VALID_TARGETS = Arrays.asList(21, 23, 24, 26, 28, 29, 30, 59);
	private static ArrayList<Integer> targetable = new ArrayList<Integer>();
	private static ArrayList<Integer> order = new ArrayList<Integer>();
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Revolution in " + MapManager.get(order.get(0)).name)
		.setFooter("\"I cannot avoid wondering about the feelings of those "
				+ "who are now the apparent rulers of Iran. "
				+ "They are, despite their mistakes and the crimes which they have instigated, "
				+ "men of faith who claim to be sent by God. "
				+ "I hope they will eventually realize that the revolution which they believe they have brought about "
				+ "is not to the glory of God, but serves the forces of evil.\"\n"
				+ "- Mohammad Reza Pahlavi, 1980", Launcher.url("people/pahlavi.png"))
		.setColor(Color.RED)
		.setDescription("Secular government toppled");
		for (int i : order) {
			builder.changeInfluence(i, 0, -MapManager.get(i).influence[0]);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return !HandManager.effectActive(110);
	}

	@Override
	public String getId() {
		return "056";
	}

	@Override
	public String getName() {
		return "Muslim Revolution";
	}

	@Override
	public int getOps() {
		return 4;
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		targetable = new ArrayList<Integer>();
		order = new ArrayList<Integer>();
		for (Integer i : VALID_TARGETS) {
			if (MapManager.get(i).influence[0]>0) targetable.add(i);
		}
		if (targetable.size()<=2) {
			order = targetable;
			return true;
		}
		if (args.length!=3) return false;
		order.add(MapManager.find(args[1]));
		order.add(MapManager.find(args[2]));
		if (order.get(1)==order.get(0)) return false; //no duplicates plox
		return (targetable.containsAll(order));
	}

	@Override
	public String getDescription() {
		return "Remove all US Influence from two countries among the following: Egypt, Libya, Sudan, ";
	}

	@Override
	public String getArguments() {
		return "Two nations among those listed to remove influence from. If at most two such nations exist, that influence will be removed automatically, and no further input will be required.";
	}

}

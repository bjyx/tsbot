package yiyo;

import java.awt.Color;
import java.util.ArrayList;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import map.MapManager;

public class SEATOMETO extends Card {

	private static final int[] possible = {23, 25, 32, 36, 41, 42}; //ir, il, au, jp, ph, kr;
	private static int target1;
	private static int target2;
	private ArrayList<Integer> doable;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle(MapManager.get(target1).region==3?"CENTO formed in Baghdad":"SEATO formed in Manila")
			.setDescription(MapManager.get(target1).name + " to be among founding members")
			.setColor(Color.BLUE);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "None of the targetable countries are valid targets for US Influence placement.", false);
		}
		else {
			builder.changeInfluence(target1, 0, 2)
			.changeInfluence(target2, 0, 1);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "123";
	}

	@Override
	public String getName() {
		return "SEATO/METO";
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
		return 0;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		doable = new ArrayList<Integer>();
		for (int i : possible) {
			if (MapManager.get(i).isControlledBy()==-1) {
				doable.add(i);
			}
		}
		if (doable.isEmpty()) return true;
		if (args.length<3) return false;
		if (!doable.contains(MapManager.find(args[1]))) return false;
		target1 = MapManager.find(args[1]);
		if (MapManager.get(target1).adj.contains(MapManager.find(args[2]))) {
			target2 = MapManager.find(args[2]);
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "The US may place 2 Influence in one of the following nations, if it is uncontrolled: Israel, Iran, Australia, Japan, South Korea, Philippines. The US may then place 1 Influence in a country adjacent to the first.";
	}

	@Override
	public String getArguments() {
		return "The first country (an uncontrolled country with printed US start influence in the ME or Asia) and the second country adjacent to the first.";
	}

}

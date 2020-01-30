package yiyo;

import java.awt.Color;
import java.util.ArrayList;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class KosyginReform extends Card {

	private static ArrayList<Integer> order;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Soviet Economy Undergoes Reform")
			.setDescription("Kosygin begins decentralization of economic planning")
			.setFooter("\"What is profitable for society should be profitable for every enterprise.\"\n- Yevsei Liberman, 19XX", Launcher.url("yiyo/liberman.png"))
			.setColor(Color.red);
		for (int c : order) {
			builder.changeInfluence(c, 1, 1);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		for (int i=0; i<21; i++) {
			if (MapManager.get(i).isControlledBy()==0 && MapManager.get(i).region!=1) return false;
		}
		return true;
	}

	@Override
	public String getId() {
		return "134";
	}

	@Override
	public String getName() {
		return "Kosygin Reform";
	}

	@Override
	public int getOps() {
		return 3;
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
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		order = new ArrayList<Integer>();
		if (args.length!=5) return false;
		for (int i=1; i<=4; i++) {
			int c = MapManager.find(args[i]);
			if (MapManager.get(c).isBattleground) return false; //cannot be bgs
			if (MapManager.get(c).region>=3) return false; //must be in Europe
			order.add(c);
			if (order.indexOf(c)!=order.lastIndexOf(c)) return false; // no duplicates plox
		}		
		return true;
	}

	@Override
	public String getDescription() {
		return "Add 1 USSR influence into each of four non-battleground European countries. *May only be played if the US does not control a country in Eastern Europe.*";
	}

	@Override
	public String getArguments() {
		return "The four countries. These must be European non-battlegrounds.";
	}

}

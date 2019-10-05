package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class MarshallPlan extends Card {
	
	private static ArrayList<Integer> doable;
	private static ArrayList<Integer> order;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Marshall Plan implemented")
			.setDescription("Assistance to be provided for European recovery by the United States")
			.setFooter("\"Our policy is not directed against any country, but against hunger, poverty, desperation and chaos. Any government that is willing to assist in recovery will find full co-operation on the part of the United States.\"\n"
					+ "- George Marshall, 1947", Launcher.url("people/marshall.png"))
			.setColor(Color.blue);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "All of Western Europe is red for some reason. How did *that* happen?", false);
		}
		else for (int c : order) {
			builder.changeInfluence(c, 0, 1);
		}
		builder.addField("Aid to Western Europe", "**NATO can now be formed.**", false);
		HandManager.Effects.add(23);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "023";
	}

	@Override
	public String getName() {
		return "Marshall Plan";
	}

	@Override
	public int getOps() {
		return 4;
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
		order = new ArrayList<Integer>();
		for (int i=0; i<21; i++) {
			if ((MapManager.get(i).region<=1)&&MapManager.get(i).isControlledBy()!=1) {
				doable.add(i);
			}
		}
		if (doable.size()<=7) {
			order = doable;
			return true;
		}
		if (args.length!=8) return false;
		for (int i=1; i<=7; i++) {
			int c = MapManager.find(args[i]);
			order.add(c);
			if (order.indexOf(c)!=order.lastIndexOf(c)) return false; // no duplicates plox
		}
		if (!doable.containsAll(order)) return false;
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Add 1 US Influence to each of seven non-USSR-controlled Western European countries.\n"
				+ "*This event enables the play of " + CardList.getCard(21) + ".*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "The seven Western European countries to which influence will be added. This is not necessary if, for some reason, there are at most seven countries in Western Europe not under USSR control.";
	}

}

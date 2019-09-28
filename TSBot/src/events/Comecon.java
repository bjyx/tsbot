package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class Comecon extends Card {
	
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> doable;

	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Council for Mutual Economic Assistance founded")
			.setDescription("")
			.setFooter("", Launcher.url(""))
			.setColor(Color.red);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "All of Eastern Europe is US-controlled for some reason. How did *that* happen?", false);
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
		return "014";
	}

	@Override
	public String getName() {
		return "COMECON";
	}

	@Override
	public int getOps() {
		return 3;
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
	public boolean isFormatted(String[] args) {
		doable = new ArrayList<Integer>();
		order = new ArrayList<Integer>();
		for (int i=0; i<21; i++) {
			if ((MapManager.get(i).region==0||MapManager.get(i).region==2)&&MapManager.get(i).isControlledBy()!=0) {
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
			order.add(c);
			if (order.indexOf(c)!=i) return false; // no duplicates plox
		}
		if (!doable.containsAll(order)) return false; // all need to be able to be played into
		
		return true;
	}

	@Override
	public String getDescription() {
		return "Add one USSR Influence to each of four Eastern European countries not controlled by the USA.";
	}

	@Override
	public String getArguments() {
		return "The four countries. If the number of countries able to receive this influence is somehow less than or equal to four, no additional input is required.";
	}

}

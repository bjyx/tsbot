package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class EastEuropeanUnrest extends Card {
	
	private static ArrayList<Integer> order;
	private static ArrayList<Integer> doable;

	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Unrest in Eastern Europe")
			.setDescription("Demonstrations against communist governments intensify")
			.setFooter("\"People who today are falling into depression and defeatism, commenting that there are not enough guarantees, that everything could end badly, that we might again end up in a marasmus of censorship and trials, that this or that could happen, are simply weak people, who can live only in illusions of certainty.\"\n"
					+ "- Milan Kundera, 1968", Launcher.url("people/kundera.png"))
			.setColor(Color.blue);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "No USSR Influence in Eastern Europe. How did *that* happen?", false);
		}
		else for (int c : order) {
			if (GameData.getEra()==2) builder.changeInfluence(c, 1, -2);
			else builder.changeInfluence(c, 1, -1);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "029";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "East European Unrest";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		doable = new ArrayList<Integer>();
		order = new ArrayList<Integer>();
		for (int i=0; i<21; i++) {
			if ((MapManager.get(i).region!=1)&&MapManager.get(i).influence[1]>0) {
				doable.add(i);
			}
		}
		if (doable.size()<=3) {
			order = doable;
			return true;
		}
		if (args.length!=4) return false;
		for (int i=1; i<=3; i++) {
			int c = MapManager.find(args[i]);
			order.add(c);
			if (order.indexOf(c)!=order.lastIndexOf(c)) return false; // no duplicates plox
		}
		if (!doable.containsAll(order)) return false; // all need to be able to be played into
		
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Remove one USSR influence from each of at most three countries in Eastern Europe. If played in the Late War, remove two influence from at most three countries in Eastern Europe instead.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "The three countries. If the number of countries able to receive this influence is somehow less than or equal to four, no additional input is required.";
	}

}

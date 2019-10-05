package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class PuppetGovernments extends Card {

	private static ArrayList<Integer> doable;
	private static ArrayList<Integer> order;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Puppet Governments")
			.setDescription("")
			.setFooter("\"In the South, the United States sought a leader for the new government who was both anti‐French and anti‐Communist. It selected Ngo Dinh Diem, who had an enviable record as a young civil servant.\"\n"
					+ "- *New York Times*", Launcher.url("people/times.png"))
			.setColor(Color.blue);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "Congratulations! You have filled every country on the board with some form of influence.", false);
		}
		else for (int c : order) {
			builder.changeInfluence(c, 0, 1);
		}
		HandManager.Effects.add(23);
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
		return "Puppet Governments";
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
		for (int i=0; i<84; i++) {
			if (MapManager.get(i).influence[0]==0 && MapManager.get(i).influence[1]==0) doable.add(i);
		}
		if (doable.size()<=3) {
			order = doable;
			return true;
		}
		for (int i=1; i<=3; i++) {
			int c = MapManager.find(args[i]);
			order.add(c);
			if (order.indexOf(c)!=order.lastIndexOf(c)) return false; // no duplicates plox
		}
		return doable.containsAll(order);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "The US adds 1 Influence to three countries that do not have influence from either player.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "The three countries to target. These must be devoid of all influence.";
	}

}

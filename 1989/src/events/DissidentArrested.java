package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Common;
import map.MapManager;

public class DissidentArrested extends Card {

	private static ArrayList<Integer> doable;
	
	private static int target;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Dissident Arrested in " + Common.countries[MapManager.get(target).region])
			.setColor(Color.RED);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "It seems the Democratic movement has not sought the support of their country's Intellectuals.", false);
		}
		else {
			builder.changeInfluence(target, 0, -2);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "011";
	}

	@Override
	public String getName() {
		return "Dissident Arrested";
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
		for (int i=0; i<75; i++) {
			if (MapManager.get(i).icon==4 && MapManager.get(i).support[0]>0) {
				doable.add(i);
			}
		}
		if (doable.size()==0) {
			return true;
		}
		if (doable.size()==1) {
			target = doable.get(0);
			return true;
		}
		if (args.length<2) return false;
		if (!doable.contains(MapManager.find(args[1]))) return false;
		target = MapManager.find(args[1]);
		return true;
	}

	@Override
	public String getDescription() {
		return "Remove 2 Democratic SPs from one Intellectual space on the board.";
	}

	@Override
	public String getArguments() {
		return "The space to target. If only one or zero exist with Democratic Support, this is not needed.";
	}

}

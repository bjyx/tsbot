package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class GovernmentResigns extends Card {

	private static ArrayList<Integer> doable;
	
	private static int target = 0; // Good to have a default for flavor purposes

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Government Resigns")
			.setColor(Color.BLUE);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "No uncontrolled countries with USSR influence in Europe.", false);
		}
		else {
			builder.changeInfluence(target, 1, -MapManager.get(target).support[1]);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "103";
	}

	@Override
	public String getName() {
		return "Government Resigns";
	}

	@Override
	public int getOps() {
		return 1;
	}

	@Override
	public int getEra() {
		return 2;
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
		for (int i=0; i<75; i++) {
			if (MapManager.get(i).icon==2 && MapManager.get(i).support[1]>0) {
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
		return "Remove all Communist Support from any Elite Space.";
	}

	@Override
	public String getArguments() {
		return "The space to target.";
	}

}

package events;

import java.awt.Color;

import game.GameData;
import main.Common;
import map.MapManager;

public class Betrayal extends Card {

	public static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Orthodox Church of " + Common.countries[MapManager.get(target).region] + " Cooperates With Government")
		.setColor(Color.red);
		builder.changeInfluence(target, 1, MapManager.get(target).support[0]);
		builder.changeInfluence(target, 0, -MapManager.get(target).support[0]);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "092";
	}

	@Override
	public String getName() {
		return "Betrayal";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 2;
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
		target = MapManager.find(args[1]);
		return MapManager.get(target).icon==6&&MapManager.get(target).inRegion(7); //Church in Eastern Europe but not East Germany
	}

	@Override
	public String getDescription() {
		return "Select any Orthodox Church. All Democratic Support in that space is replaced with Communist Support.";
	}

	@Override
	public String getArguments() {
		return "The space to choose.";
	}

}

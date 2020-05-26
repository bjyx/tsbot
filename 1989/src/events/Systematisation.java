package events;

import java.awt.Color;

import cards.HandManager;
import logging.Log;
import main.Launcher;
import map.MapManager;

public class Systematisation extends Card {

	private static int target;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Ceaușescu Bulldozes " + MapManager.get(target).name)
		.setColor(Color.red);
		builder.addField("Systematization",MapManager.get(target).name + " is out of play.",false);
		Log.writeToLog(MapManager.get(target).shorthand + " is out of play.");
		MapManager.systematize(target);
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "069";
	}

	@Override
	public String getName() {
		return "Systematization";
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
		if (args.length<2) return false;
		target = MapManager.find(args[1]);
		if (target==-1) return false;
		return MapManager.get(target).inRegion(4);
	}

	@Override
	public String getDescription() {
		return "Select one space in Romania. That space is removed from the board; all of its adjacencies are now considered adjacent to one another, any Democratic SPs are removed from the board, and any Communist SPs are relocated to București.";
	}

	@Override
	public String getArguments() {
		return "A valid alias for any space in Romania. ";
	}

}

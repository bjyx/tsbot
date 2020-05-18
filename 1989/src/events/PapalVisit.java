package events;

import java.awt.Color;

import game.GameData;
import main.Common;
import map.MapManager;

public class PapalVisit extends Card {
	
	public static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("John Paul II visits " + Common.countries[MapManager.get(target).region])
		.setDescription("Papal visit sees visitors in the hundred thousands")
		.setColor(Color.blue);
		builder.changeInfluence(target, 0, 3);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "019";
	}

	@Override
	public String getName() {
		return "Papal Visit";
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
		target = MapManager.find(args[1]);
		return MapManager.get(target).icon==6&&MapManager.get(target).inRegion(6)&&!MapManager.get(target).inRegion(0); //Church in Eastern Europe but not East Germany
	}

	@Override
	public String getDescription() {
		return "Place 3 Democratic Support in any Catholic Church space.";
	}

	@Override
	public String getArguments() {
		return "A valid alias for any Catholic Church—these are the ones in Poland, Hungary, and Czechoslovakia.";
	}

}

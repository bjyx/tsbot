package events;

import java.awt.Color;

import game.GameData;
import map.MapManager;

public class Normalization extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder
			.setTitle("Czechoslovak Government Experiences Purge")
			.setDescription("Sympathizers to the Prague Spring dismissed")
			.setColor(Color.red);
		builder.changeInfluence(31, 1, -MapManager.get(44).support[1]);
		builder.changeInfluence(32, 1, -MapManager.get(44).support[1]);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "052";
	}

	@Override
	public String getName() {
		return "Normalization";
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
		return true;
	}

	@Override
	public String getDescription() {
		return "Remove all Democratic support from Praha and Plzen.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

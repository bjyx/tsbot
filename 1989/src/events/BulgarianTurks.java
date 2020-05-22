package events;

import java.awt.Color;

import game.GameData;
import map.MapManager;

public class BulgarianTurks extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder
		.setTitle("Turks Expelled From Bulgaria")
		.setColor(Color.red);
	builder.changeInfluence(66, 0, -MapManager.get(66).support[0]);
	builder.changeVP(-2);
	GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "047";
	}

	@Override
	public String getName() {
		return "Bulgarian Turks Expelled";
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
		return "The Communist gains 2 VP. Remove all Democratic Support from Razgrad.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

package events;

import java.awt.Color;

import game.GameData;
import map.MapManager;

public class KlausAndKomarek extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder
			.setTitle("Economists Criticize Czechoslovak Regime")
			.setColor(Color.blue);
		builder.changeInfluence(32, 1, -2);
		builder.changeInfluence(32, 0, 2);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "068";
	}

	@Override
	public String getName() {
		return "Klaus and Komarek";
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
		return 0;
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
		return "Remove 2 Communist Support from Praha. Add 2 Democratic Support to Praha.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

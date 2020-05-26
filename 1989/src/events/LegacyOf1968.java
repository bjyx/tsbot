package events;

import java.awt.Color;

import game.GameData;
import main.Common;
import main.Launcher;
import map.MapManager;

public class LegacyOf1968 extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Legacy of 1968")
			.setColor(Color.blue);
		for (int i=Common.bracket[2]; i<Common.bracket[3]; i++) {
			if (MapManager.get(i).isControlledBy()!=1) {
				builder.changeInfluence(i, 0, 1);
			}
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "064";
	}

	@Override
	public String getName() {
		return "Legacy of 1968";
	}

	@Override
	public int getOps() {
		return 4;
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
		return "Place 1 Democratic SP in every space in Czechoslovakia not controlled by the Communist.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

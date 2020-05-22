package events;

import java.awt.Color;

import game.GameData;

public class FIDESZ extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Viktor Orban Criticizes Regime")
		.setDescription("Activist FIDESZ rises to prominence")
		.setColor(Color.blue);
		builder.changeInfluence(47, 0, 5);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "034";
	}

	@Override
	public String getName() {
		return "FIDESZ";
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
		return true;
	}

	@Override
	public String getDescription() {
		return "Place 5 Democratic SPs in Eötvös Loránd University (the Hungarian Student space).";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

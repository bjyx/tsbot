package events;

import java.awt.Color;

import game.GameData;

public class DuckAndCover extends Card {

	@Override
	public void onEvent(String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("*Duck and Cover*")
			.setDescription("There was a turtle by the name of Bert...")
			.setColor(Color.blue);
		builder.changeDEFCON(-1);
		builder.changeVP(5-GameData.getDEFCON());
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable() {
		return true;
	}

	@Override
	public String getId() {
		return "004";
	}

	@Override
	public String getName() {
		return "*Duck and Cover*";
	}

	@Override
	public int getOps() {
		return 3;
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
		return false;
	}

	@Override
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "**Degrades DEFCON by 1.** \nThe US then gains (5 - DEFCON) VP.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

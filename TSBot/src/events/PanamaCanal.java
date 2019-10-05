package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;

public class PanamaCanal extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Panama Canal to be Returned to Panama")
		.setDescription("Carter and Torrijos agree to a gradual handover")
		.setColor(Color.blue)
		.setFooter("\"We have never been, are not and will never be an associated state, colony or protectorate, and we do not intend to add a star to the United States flag.\"\n"
				+ "- Omar Torrijos, 1973", Launcher.url("people/torrijos.png"));
		builder.changeInfluence(64, 0, 1); // Costa Rica
		builder.changeInfluence(73, 0, 1); // Panama
		builder.changeInfluence(83, 0, 1); // Venezuela
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
		return "Panama Canal Returned";
	}

	@Override
	public int getOps() {
		return 1;
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
		return "Add 1 US Influence to each of Costa Rica, Panama, and Venezuela.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

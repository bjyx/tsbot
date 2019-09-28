package events;

import java.awt.Color;

import game.GameData;

public class PortEmpire extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Angola and Mozambique Gain Independence")
		.setDescription("New Portuguese government agrees to independence-granting accords")
		.setColor(Color.red)
		.setFooter("", "");
		builder.changeInfluence(47, 1, 2); //Alvor Agreement
		builder.changeInfluence(55, 1, 2); //Lusaka Accord
		GameData.txtchnl.sendMessage(builder.build());
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
		return "Portuguese Empire Collapses";
	}

	@Override
	public int getOps() {
		return 2;
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
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "The USSR gains 2 Influence in each of Angola and SE African States.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

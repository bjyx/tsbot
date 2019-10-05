package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;

public class NixonInChina extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Nixon Visits China")
		.setDescription("Unexpected trip to Beijing improves relations with China")
		.setColor(Color.blue)
		.setFooter("\"This was the week that changed the world, "
				+ "as what we have said in that Communique is not nearly as important "
				+ "as what we will do in the years ahead to build a bridge across 16,000 miles "
				+ "and 22 years of hostilities which have divided us in the past. "
				+ "And what we have said today is that we shall build that bridge.\"\n" + 
				"- Richard M. Nixon, 1972",Launcher.url("people/nixon.png"));
		if (HandManager.China%2==0 || HandManager.China==-1) {
			builder.changeVP(2);
		}
		else {
			builder.addField("Meeting with Mao", "The China Card has been given to the US, face-down.", false);
			HandManager.China = 2;
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "071";
	}

	@Override
	public String getName() {
		return "Nixon Plays the China Card";
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
		return "If the USSR has the China Card, the USA receives the China Card face-down. Otherwise, the USA receives 2 VP.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class YuriAndSamantha extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("America's Youngest Ambassador")
			.setDescription("Samantha Smith visits the Soviet Union")
			.setColor(Color.red)
			.setFooter("\"We want peace — there is something that we are occupied with: growing wheat, building and inventing, writing books and flying into space. We want peace for ourselves and for all peoples of the planet. For our children and for you, Samantha.\"\n"
					+ "- Yuri Andropov, 1983", Launcher.url("people/hoffman.png"));
		builder.addField("Goodwill Ambassador","For the rest of the turn, all US coups give the USSR 1 Victory Point.",false);
		HandManager.addEffect(109);
		Log.writeToLog("Yuri and Samantha Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "109";
	}

	@Override
	public String getName() {
		return "Yuri and Samantha";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 2;
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
		return "For the rest of the turn, the USSR gains 1 VP for every coup conducted by the US.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

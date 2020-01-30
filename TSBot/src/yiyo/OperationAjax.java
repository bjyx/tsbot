package yiyo;

import java.awt.Color;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;

public class OperationAjax extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Mossadegh Ousted")
			.setDescription("CIA forces Iranian PM from office")
			.setColor(Color.blue)
			.setFooter("\"Yes, my sin — my greater sin and even my greatest sin is that I nationalized Iran's oil industry and discarded the system of political and economic exploitation by the world's greatest empire. This at the cost to myself, my family; and at the risk of losing my life, my honor and my property. With God's blessing and the will of the people, I fought this savage and dreadful system of international espionage and colonialism.\"\n"
					+ "- Mohammed Mossadegh, 1953", Launcher.url("people/tokyo.png"));
		builder.changeDEFCON(Math.min(0, 3-GameData.getDEFCON())).changeInfluence(23, 0, 2);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "122";
	}

	@Override
	public String getName() {
		return "Operation Ajax";
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
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "If DEFCON is greater than three, degrade DEFCON to three. Add 2 US Influence to Iran.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class WeWillBuryYou extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("\"We Will Bury You\", Says Khrushchev").setDescription("Polish Embassy Reception Turns Chilly")
			.setFooter("\"About the capitalist states, it doesn't depend on you whether or not we exist.\"\n"
					+ "- Nikita Khrushchev, 1956", Launcher.url("people/khrushchev.png"))
			.setColor(Color.red);
		builder.changeDEFCON(-1);
		builder.addField("Walk-out","If "+CardList.getCard(32)+" is not played for the event on the US's next action round, the USSR gains 3 VP before any US point award.",false);
		HandManager.addEffect(50);
		Log.writeToLog("We Will Bury You Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "050";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "\"We Will Bury You!\"";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "**Degrade DEFCON by 1**. If the US does not play UN Intervention for the event on the next action round, the USSR gains 3 VP before any US VP award.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "None.";
	}

}

package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import logging.Log;
import main.Launcher;

public class CrowdTurnsAgainstCeausescu extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Romania in Revolution")
		.setDescription("Ceaușescu faced with jeering at speech")
		.setFooter("\"Ceaușescu, who are you? A criminal from Scornicești.\"\n"
				+ "- Unknown, December 21", Launcher.url("countries/ro.png"))
		.setColor(Color.blue);
		builder.addField("\"Jos dictatorul!\"",CardList.getCard(97) + " may be played for the event.",false);
		builder.addField("\"Timișoara! Timișoara!\"","Upon the next scoring of Romania, the Democrat draws 15 Power Struggle cards after the initial drawing and conducts Operations in Romania using the number of Rally in the Square cards multiplied by 3.",false);
		HandManager.addEffect(54);
		HandManager.addEffect(540);
		Log.writeToLog("Crowd Turns Against Ceausescu active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "054";
	}

	@Override
	public String getName() {
		return "The Crowd Turns Against Ceaușescu";
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
		return "*Upon the next Power Struggle in Romania, after drawing cards for the next Power Struggle, the Democrat looks at the next 15 Power Struggle cards and notes the number of Rally cards among them; the Democrat then takes one Action Round in Romania using three times that numbers' worth of Operations. Allows play of " + CardList.getCard(97) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

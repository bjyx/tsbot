package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class NorthSeaOil extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Oil and Gas sources discovered in North Sea")
			.setDescription("Ekofisk oil provides viable alternative to OPEC")
			.setColor(Color.blue)
			.setFooter("\"Oil glut! What once seemed as unlikely as $850 gold or an 18 percent Treasury bill is here.\"\n"
					+ "- *New York Times*, 1981", Launcher.url("people/times.png"))
			.addField("Oil glut", CardList.getCard(61) + " may no longer be played for the event.", false)
			.addField("Domestic surplus", "The US may take eight action rounds this turn.", false);
		HandManager.addEffect(86);
		Log.writeToLog("North Sea Oil Active.");
		HandManager.addEffect(860);
		GameData.txtchnl.sendMessage(builder.build()).complete();

	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "086";
	}

	@Override
	public String getName() {
		return "North Sea Oil";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 2;
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
		return "The US may choose to take eight action rounds this turn. *Disables the event for " + CardList.getCard(61) + ".*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

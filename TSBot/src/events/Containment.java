package events;

import java.awt.Color;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class Containment extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("The Sources of Soviet Conduct")
			.setDescription("Mr. X")
			.setColor(Color.blue)
			.setFooter("\"In summary, we have here a political force committed fanatically to the belief that with US there can be no permanent modus vivendi, that it is desirable and necessary that the internal harmony of our society be disrupted, our traditional way of life be destroyed, the international authority of our state be broken, if Soviet power is to be secure.\"\n"
					+ "- George F. Kennan, 1946", Launcher.url("people/kennan.png"));
		builder.addField("Long Telegram","The US adds 1 Operations point to any card played for operations. ",false);
		HandManager.addEffect(25);
		Log.writeToLog("Containment Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "025";
	}

	@Override
	public String getName() {
		return "Containment";
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
		return "The US receives one additional Operations Point on every card played by him/her for every purpose for the remainder of this turn.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

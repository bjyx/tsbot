package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class IranContra extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Iran-Contra Scandal Revealed")
			.setDescription("Funding from Iranian arms shipments found to be diverted towards Nicaraguan guerillas")
			.setFooter("\"A few months ago I told the American people I did not trade arms for hostages. My heart and my best intentions still tell me that's true, but the facts and the evidence tell me it is not.\"\n"
					+ "- Ronald Reagan, 1987", Launcher.url("people/reagan.png"))
			.setColor(Color.red);
		builder.addField("Boland Amendment", "For the rest of the turn, American realignments get a -1 malus.", false);
		HandManager.addEffect(93);
		Log.writeToLog("Iran-Contra Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "093";
	}

	@Override
	public String getName() {
		return "Iran–Contra Affair";
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
		return "*For the rest of the turn, subtract 1 from all US realignment rolls.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

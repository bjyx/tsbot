package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class GeneralStrike extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("General Strike!")
			.setDescription("Communist governments face economic crisis")
			.setColor(Color.blue)
			.setFooter("\"<missing flavortext>\"\n"
					+ "- <missing flavortext>", Launcher.url("people/qmark.png"));
		builder.addField("Standoff","The USSR must discard a card worth at least 2 Ops every action round and roll 1-4 to cancel this effect.",false);
		HandManager.addEffect(5);
		Log.writeToLog("Bear Trap Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "005";
	}

	@Override
	public String getName() {
		return "General Strike";
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
		return "*For every Communist action round until this is cancelled, the Communist must discard a card and roll a die. Cancel if the sum of the card's Operations and the roll is at least 6. Scoring cards may be played for the event while under this event's effect, but cannot be discarded.*";
	}

	@Override
	public String getArguments() {
		return "Event: None. \n"
				+ "Decision: the card to discard. Can be any card, including scoring cards, but scoring cards are played directly for the event.";
	}

}

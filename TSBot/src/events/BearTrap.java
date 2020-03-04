package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class BearTrap extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("USSR Hits Standstill in Afghanistan")
			.setDescription("Red Army fails to quell Islamic insurgency")
			.setColor(Color.blue)
			.setFooter("\"The main scourge of our country is perennial foreign intervention... "
					+ "Only final cessation of foreign aggression will allow us to start solving all other problems of Afghanistan, economic and political.\"\n"
					+ "- Ahmad Shah Massoud", Launcher.url("people/massoud.png"));
		builder.addField("Mujahideen","The USSR must discard a card worth at least 2 Ops every action round and roll 1-4 to cancel this effect.",false);
		HandManager.addEffect(44);
		Log.writeToLog("Bear Trap Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "044";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Bear Trap";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 0;
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
		return "*The USSR must discard a card worth at least two OPs every action round and roll a die in lieu of performing an action. If and only if said roll is between 1 and 4, this effect is cancelled. If no cards are available to discard, the USSR must play a scoring card for the event instead; if the USSR has no scoring cards, they must skip all action rounds for the rest of the turn.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None.\n"
				+ "Decision: the ID of the card to be discarded/played.";
	}

}

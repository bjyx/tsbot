package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;

public class DeutscheMarks extends Card {
	
	public static int maxops = -1;
	
	public static int card = 0;

	@Override
	public void onEvent(int sp, String[] args) {
		for (Integer i : HandManager.DemHand) {
			maxops = Math.max(maxops, CardList.getCard(i).getOps());
		}
		if (maxops<=0) { //no valid cards
			CardEmbedBuilder builder = new CardEmbedBuilder();
			Log.writeToLog("Handed over nothing.");
			builder.setTitle("East German Government Ransoms Dissident")
				.setDescription("West Germany to pay in Deutsche Marks")
				.setColor(Color.blue);
			builder.addField("No card given!", "Guess those debts aren't going away any time soon.", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			return;
		}
		GameData.dec = new Decision(0, 20);
		GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", your opponent has played " + CardList.getCard(20) + ". "
				+ "Select a card with "+CardEmbedBuilder.intToEmoji(maxops)+" Operations Points to give to your opponent.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "020";
	}

	@Override
	public String getName() {
		return "Deutsche Marks";
	}

	@Override
	public int getOps() {
		return 4;
	}

	@Override
	public int getEra() {
		return 0;
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
		return "The Democrat gives the Communist the card in his hand with the highest Operations value (the Democrat breaks any ties). If it is a playable Communist event, the event occurs; otherwise, the Communist may use the Operations Value of the card without triggering the event.";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision 1: The Democrat will select a card.\n"
				+ "Decision 2: The Communist will play that card accordingly. ";
	}

}

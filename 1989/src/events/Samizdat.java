package events;

import java.awt.Color;
import java.util.Random;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;

public class Samizdat extends Card {

	public static int card;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Samizdat")
			.setColor(Color.BLUE);
		if (card == 0) {
			builder.addField("All quiet...", "The Democrat has opted not to take advantage of the event. Interesting.", false);
			Log.writeToLog("Left nothing.");
		}
		else {
			HandManager.addEffect(77);
			HandManager.removeFromHand(0, card);
			Log.writeToLog("Left something.");
			Random random = new Random();
			HandManager.DemHand.add(HandManager.Deck.remove(random.nextInt(HandManager.Deck.size())));
			if(HandManager.Deck.isEmpty()) {
				HandManager.Deck.addAll(HandManager.Discard);
				HandManager.Discard.clear();
			}
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "077";
	}

	@Override
	public String getName() {
		return "Samizdat";
	}

	@Override
	public int getOps() {
		return 1;
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
		try {
			card = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException err) {
			return false;
		}
		if (card==0)return true;
		if (!HandManager.handContains(0, card)) return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "The Democrat may designate a card in his hand. *This card is removed from the Democrat's hand, and will return to the Democrat's hand on the next turn.* The Democrat then draws a replacement card.";
	}

	@Override
	public String getArguments() {
		return "The card to set aside. Alternatively, 0, to do nothing.";
	}

}

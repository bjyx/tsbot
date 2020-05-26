package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;

public class KissOfDeath extends Card {

	public static int card;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Kiss of Death")
			.setColor(Color.blue);
		if (HandManager.ComHand.isEmpty()) {
			builder.addField("The Communist's hand is empty!", "Can't discard a card if there is no card to discard.", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			return;
		}
		card = HandManager.ComHand.get((int) (Math.random()*HandManager.ComHand.size()));
		if (CardList.getCard(card).getAssociation()!=1&&CardList.getCard(card).isPlayable(0)) {
			GameData.dec = new Decision(0, 71);
			GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", play the event you just pulled from the Communist hand.").complete();
		}
		else {
			HandManager.discard(1, card);
		}
		Log.writeToLog("Communist discards " + CardList.getCard(card).getName() + ".");
		builder.addField("Disapproval","The Communists lose " + CardList.getCard(card) + " and their favor with Gorbachev.",false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "071";
	}

	@Override
	public String getName() {
		return "Kiss of Death";
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
		return "The Communist randomly discards a card; if the event on the card is not associated with the Communist, trigger the event.";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: Play the event.";
	}

}

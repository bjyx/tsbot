package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;

/**
 * The Brought In For Questioning card.
 * @author wes4zhang
 *
 */
public class Questioning extends Card {
	public static int card;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Regimes Crack Down On Dissent")
			.setDescription("Political dissidents arrested for questioning")
			.setColor(Color.red);
		if (HandManager.DemHand.isEmpty()) {
			builder.addField("The Democrat's hand is empty!", "Can't discard a card if there is no card to discard.", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			return;
		}
		card = HandManager.DemHand.get((int) (Math.random()*HandManager.DemHand.size()));
		if (CardList.getCard(card).getAssociation()==1&&CardList.getCard(card).isPlayable(1)) {
			GameData.dec = new Decision(1, 6);
			GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", play the event you just pulled from the Soviet hand.").complete();
		}
		else {
			HandManager.discard(1, card);
		}
		Log.writeToLog("Democrat discards " + CardList.getCard(card).getName() + ".");
		builder.addField("Security Services","The Democrats lose " + CardList.getCard(card) + " to communist police.",false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "006";
	}

	@Override
	public String getName() {
		return "Brought In for Questioning";
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
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "The Democrat randomly discards a card; if it is a Communist event, trigger the event.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None.\n"
				+ "Decision: Event.";
	}

}

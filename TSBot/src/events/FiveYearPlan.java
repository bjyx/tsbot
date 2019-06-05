package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;

public class FiveYearPlan extends Card {
	public static int card;

	@Override
	public void onEvent(String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Five Year Plan")
			.setDescription("Russians Hungry, But Not Starving")
			.setColor(Color.BLUE)
			.setFooter("\"In that terrible year [in 1933] nearly half the population of my native village, Privolnoye, starved to death, including two sisters and one brother of my father.\"\n"
					+ "- Mikhail Gorbachev, 1995", null);
		if (HandManager.SUNHand.isEmpty()) {
			builder.addField("The Soviet hand is empty!", "Can't discard a card if there is no card to discard.", false);
			GameData.txtchnl.sendMessage(builder.build());
			return;
		}
		card = HandManager.SUNHand.get((int) (Math.random()*HandManager.SUNHand.size()));
		if (CardList.getCard(card).getAssociation()==0&&CardList.getCard(card).isPlayable()) {
			HandManager.SUNHand.remove(HandManager.SUNHand.indexOf(card));
			GameData.dec = new Decision(0, 5);
		}
		else {
			HandManager.discard(1, card);
			
		}
		builder.addField("Centralization","The Soviets lose **" + CardList.getCard(card) + "** attempting to improve their economy.",false);
		GameData.txtchnl.sendMessage(builder.build());
		if (CardList.getCard(card).getAssociation()==0) {
			GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", play the event you just pulled from the Soviet hand.");
		}
	}

	@Override
	public boolean isPlayable() {
		return true;
	}

	@Override
	public String getId() {
		return "005";
	}

	@Override
	public String getName() {
		return "Five-Year Plan";
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
		return false;
	}

	@Override
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Discard one card from the USSR's hand at random. If the event is US-associated, the event takes place immediately; otherwise, the event is discarded.";
	}

	@Override
	public String getArguments() {
		return "None. May require a decision that takes another card's arguments.";
	}

}

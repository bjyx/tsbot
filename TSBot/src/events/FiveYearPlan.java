package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;

public class FiveYearPlan extends Card {
	public static int card;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Five Year Plan")
			.setDescription("Russians Hungry, But Not Starving")
			.setColor(Color.BLUE)
			.setFooter("\"In that terrible year [in 1933] nearly half the population of my native village, Privolnoye, starved to death, including two sisters and one brother of my father.\"\n"
					+ "- Mikhail Gorbachev, 1995", Launcher.url("people/gorbachev.png"));
		if (HandManager.SUNHand.isEmpty()) {
			builder.addField("The Soviet hand is empty!", "Can't discard a card if there is no card to discard.", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			return;
		}
		card = HandManager.SUNHand.get((int) (Math.random()*HandManager.SUNHand.size()));
		if (CardList.getCard(card).getAssociation()==0&&CardList.getCard(card).isPlayable(0)) {
			GameData.dec = new Decision(0, 5);
		}
		else {
			HandManager.discard(1, card);
		}
		builder.addField("Centralization","The Soviets lose " + CardList.getCard(card) + " attempting to improve their economy.",false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		if (CardList.getCard(card).getAssociation()==0) {
			GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", play the event you just pulled from the Soviet hand.").complete();
		}
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
	public boolean isFormatted(int sp, String[] args) {
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

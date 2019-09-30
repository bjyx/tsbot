package events;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;

public class GrainSales extends Card {
	
	public static int card;
	/**
	 * What happens to {@link #card} after it is drawn. Exactly the same as mode (e, o, l, f, s).
	 * 
	 */
	public static char status; 

	@Override
	public void onEvent(int sp, String[] args) {
		if (HandManager.SUNHand.isEmpty()) {
			GameData.dec = new Decision (0, 671);
			GameData.ops = new Operations (0, CardList.getCard(67).getOpsMod(0), true, true, true, true, false);
			status = 'o';
			GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", the USSR is out of cards. You may conduct operations using Grain Sales to Soviets.").complete();
		}
		else {
			int random = (int) (Math.random()*HandManager.SUNHand.size());
			card = HandManager.SUNHand.get(random);
			HandManager.transfer(1, card);
			GameData.dec = new Decision (0, 67);
			GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you have obtained " + CardList.getCard(card) + " from the Soviet hand. Choose how you will use it.").complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "067";
	}

	@Override
	public String getName() {
		return "Grain Sales to Soviets";
	}

	@Override
	public int getOps() {
		return 2;
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
		return false;
	}

	@Override
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "The US selects a random card from the Soviet hand, "
				+ "and may play it like a normal card or return it. "
				+ "If returned (or if the Soviets do not have a card in hand), the US may conduct operations using this card.";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision 1: Play the card, or write 'r' where the mode would otherwise go. (Use TS.decide in place of the regular command.)\n"
				+ "Decision 2: Handle the effects. (Use TS.decide in place of the regular command.)\n";
	}

}

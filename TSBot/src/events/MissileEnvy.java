package events;

import cards.CardList;
import cards.HandManager;
import game.GameData;
/**
 * The Missile Envy Card.
 * @author adalbert
 *
 */
public class MissileEnvy extends Card {
	
	public static int maxops = -1;
	
	public static int card = 0;

	@Override
	public void onEvent(int sp, String[] args) {
		
		GameData.dec = new Decision((sp+1)%2, 490);
		if (sp==0) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", your opponent has played " + CardList.getCard(49) + ". "
				+ "Select a card with "+CardEmbedBuilder.intToEmoji(maxops)+" Operations Points to give to your opponent.").complete();
		else GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", your opponent has played " + CardList.getCard(49) + ". "
				+ "Select a card with "+CardEmbedBuilder.intToEmoji(maxops)+" Operations Points to give to your opponent.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		for (Integer i : (sp==0?HandManager.SUNHand:HandManager.USAHand)) {
			if (i!=6) maxops = Math.max(maxops, CardList.getCard(i).getOps());
		}
		return maxops!=-1;
	}

	@Override
	public String getId() {
		return "049";
	}

	@Override
	public String getName() {
		return "Missile Envy";
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
		return 2;
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
		return "Exchange for the card with the highest ops value in the opponent's hand (opponent chooses when tied). "
				+ "If that card contains your event or a neutral event, the event occurs immediately. "
				+ "If the card contains your opponent's event, use for Operations without triggering the event. "
				+ "Your opponent must use this card for Operations on their next action round (or, if applicable, use it on Quagmire/Bear Trap).";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision 1: Your opponent must select a card, and use its ID as an argument.\n"
				+ "Decision 2: Perform the event/operations as normal, but using TS.decide.";
	}

}

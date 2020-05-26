package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Common;
import main.Launcher;

public class ReformerRehabilitated extends Card {

	public static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Dubček Rehabilitated")
			.setColor(Common.spColor(sp));
		if (target==-1) {
			builder.addField("Oops!", "The discard pile is empty. You sure picked a good time to play this.", false);
		}
		else {
			builder.addField("", "The " + Common.players[sp] + " retrieves " + CardList.getCard(target) + " to play as an event.", false);
			HandManager.getFromDiscard(sp, target);
			if (CardList.getCard(target).isRemoved()) HandManager.removeFromGame(0, target);
			else HandManager.discard(sp, target);
			GameData.dec = new Decision(CardList.getCard(target).getAssociation()==Common.opp(sp)?Common.opp(sp):sp, 67);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.aheadInSpace()==sp;
	}

	@Override
	public String getId() {
		return "067";
	}

	@Override
	public String getName() {
		return "Reformer Rehabilitated";
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
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		if (HandManager.Discard.isEmpty()) {
			target = -1;
			return true;
		}
		if (args.length<2) return false;
		try {
			target = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return HandManager.Discard.contains(target) && CardList.getCard(target).isPlayable(sp) && CardList.getCard(target).getOps()!=0;
	}

	@Override
	public String getDescription() {
		return "Retrieve any non-scoring card from the discard pile and play for the event. *Must be ahead on the T-Square track.*";
	}

	@Override
	public String getArguments() {
		return "Event: the card.\n"
				+ "Decision: event the card.";
	}

}

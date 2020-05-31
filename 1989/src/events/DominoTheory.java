package events;

import java.awt.Color;
import java.util.Arrays;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import powerstruggle.PowerStruggle;

public class DominoTheory extends Card {

	private static int target;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Domino Theory")
		.setColor(Color.blue);
		builder.addField("Chain Reaction", "The event " + CardList.getCard(target)+ " will now be played.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		CardList.getCard(target).onEvent(0, Arrays.copyOfRange(args, 1, args.length));
	}

	@Override
	public boolean isPlayable(int sp) {
		int x = 0;
		for (int i=0; i<6; i++) {
			if (PowerStruggle.retained[i]==-1)x++;
			if (x==2) return true;
		}
		return false;
	}

	@Override
	public String getId() {
		return "089";
	}

	@Override
	public String getName() {
		return "Domino Theory";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 2;
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
			target = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException err) {
			return false;
		}
		if (target==0) return true;
		if (!HandManager.Discard.contains(target)) return false;
		if (CardList.getCard(target).getOps()==0) return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "The Democrat may retrieve a scoring card from the discard pile and play it. *Requires the Democrat to hold power in at least two countries.*";
	}

	@Override
	public String getArguments() {
		return "The card's ID. Alternatively, 0, to not do anything, in case the Communist stacked the discard pile against you.";
	}

}

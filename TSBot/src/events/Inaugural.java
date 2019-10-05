package events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;

public class Inaugural extends Card {

	private static ArrayList<Integer> order;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Kennedy's Inaugural Address")
			.setDescription("And so, my fellow Americans: ask not what your country can do for you — ask what you can do for your country.")
			.setFooter("\"...we observe today not a victory of party, but a celebration of freedom — symbolizing an end, as well as a beginning — signifying renewal, as well as change.\"\n"
					+ "- John F. Kennedy, 1961", Launcher.url("people/jfk.png"))
			.setColor(Color.BLUE);
		int x = order.size();
		String cards = "";
		for (int i=0; i<x; i++) {
			HandManager.discard(0, order.get(i));
			cards += CardList.getCard(order.get(i)) + "\n";
		}
		builder.addField("Discarded Cards:", cards, false);
		for (int i=0; i<x; i++) {
			Random random = new Random();
			HandManager.USAHand.add(HandManager.Deck.remove(random.nextInt(HandManager.Deck.size())));
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
		// TODO Auto-generated method stub
		return "\"Ask Not What Your Country Can Do For You...\"";
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
		order = new ArrayList<Integer>();
		if (args.length<2) return false;
		if (args[1].equals("0")) return true;
		for (int i=1; i<args.length; i++) {
			int x;
			try {
				x = Integer.parseInt(args[i]);
			}
			catch (NumberFormatException err) {
				return false;
			}
			if (!HandManager.handContains(0, x)) return false;
			order.add(x);
			if (order.indexOf(x)!=order.lastIndexOf(x)) return false;
		}
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "The US may discard as many cards as they so choose from their hand and draw an equal number of replacements.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "At least one ID, representing the cards one wishes to discard, or `0`, to discard no cards.\n"
				+ "*e.g. TS.event 62 56 42*";
	}

}

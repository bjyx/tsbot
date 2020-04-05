package yiyo;

import java.util.ArrayList;

import cards.CardList;
import cards.HandManager;
import events.Card;
import events.Decision;
import game.GameData;
/**
 * The Apollo-Soyuz Card from the Year-In and Year-Out Pack.
 * @author adalbert
 *
 */
public class ApolloSoyuz extends Card {
	
	public static ArrayList<Integer> usa;
	public static ArrayList<Integer> sun;

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.dec = new Decision((sp+1)%2, 131);
		if (sp==0) GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you're collaborating with the Americans on a space project. You may discard up to " + usa.size() + " non-scoring cards.").complete();
		if (sp==1) GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you're collaborating with the Soviets on a space project. You may discard up to " + sun.size() + " non-scoring cards.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "131";
	}

	@Override
	public String getName() {
		return "Apollo-Soyuz";
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
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		if (sp==0) usa = new ArrayList<Integer>();
		else sun = new ArrayList<Integer>();
		if (args.length<2) return false; //at least one
		if (args.length>4) return false; //at most three
		for (int i=1; i<4; i++) {
			if (args.length<i+1) break; //if less than given, end
			int card;
			try {
				card = Integer.parseInt(args[i]);
			}
			catch (NumberFormatException err) {
				return false;
			}
			if (CardList.getCard(card).getOps()==0) return false;
			if (sp==0) {
				if (HandManager.USAHand.contains(card)) usa.add(card);
				else return false;
			}
			else {
				if (HandManager.SUNHand.contains(card)) sun.add(card);
				else return false;
			}
		}
		return true;
	}

	@Override
	public String getDescription() {
		return "Place up to three cards face-down. Then your opponent may do the same up to the number of cards you had discarded. Reveal the cards; whoever has more operations on their discarded cards advances one space on the Space Race (in the event of ties, nothing happens). Discard the played cards and draw replacements.";
	}

	@Override
	public String getArguments() {
		return "Event and Decision: the cards to discard. These must be __non-scoring__.";
	}

}

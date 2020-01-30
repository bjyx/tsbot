package yiyo;

import java.awt.Color;
import java.util.Random;

import cards.CardList;
import cards.HandManager;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;

public class IndoSovietTreaty extends Card {
	
	public static int discard;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("India Signs Treaty with Soviet Union")
			.setDescription("Treaty of \"Peace, Friendship, and Co-Operation\" intended to combat Pakistani connections")
			.setFooter("\"There are moments in history when brooding tragedy and its dark shadows can be lightened by recalling great moments of the past.\"\n"
					+ "- Indira Gandhi, 1971", Launcher.url("people/indira.png"))
			.setColor(Color.RED);
		builder.changeDEFCON(1).changeInfluence(34, 1, 1);
		if (discard!=0) {
			builder.addField("Article II", "The USSR discards " + CardList.getCard(discard) + " to play eight action rounds.", false);
			HandManager.discard(1, discard);
			for (int i=0; i<2; i++) {
				Random random = new Random();
				HandManager.SUNHand.add(HandManager.Deck.remove(random.nextInt(HandManager.Deck.size())));
				if(HandManager.Deck.isEmpty()) {
					HandManager.Deck.addAll(HandManager.Discard);
					HandManager.Discard.clear();
				}
			}
			HandManager.effectActive(129);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();

	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "129";
	}

	@Override
	public String getName() {
		return "Indo-Soviet Treaty";
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
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		if (args.length<2) return false;
		try {
			discard = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException err) {
			return false;
		}
		if (discard==0) return true;
		if (!HandManager.SUNHand.contains(discard)) return false; //must be in USSR's hand
		if (CardList.getCard(discard).getAssociation()!=1) return false; //must be a USSR event
		return true;
	}

	@Override
	public String getDescription() {
		return "Improve DEFCON by 1. The USSR gains 1 influence in India. If the USSR chooses to do so, they may discard a USSR event to draw two replacement cards; they are then obligated to play 8 action rounds this turn if they can.";
	}

	@Override
	public String getArguments() {
		return "A card to discard, or `0` should they choose not to discard one.";
	}

}

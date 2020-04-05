package yiyo;

import java.awt.Color;
import java.util.Random;

import cards.CardList;
import cards.HandManager;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Checkpoint C Card from the Year-In and Year-Out Pack.
 * @author adalbert
 *
 */
public class CheckpointC extends Card {
	
	public static boolean flag = true;
	public static int discard;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Standoff in Berlin")
		.setDescription("American and Soviet tanks face each other at Checkpoint Charlie")
		.setColor(Color.blue)
		.setFooter("\"We have tanks too. We hate the idea of carrying out such actions, and are sure that you will re-examine your course.\"\n"
				+ "- Colonel Solovyov, 1961", Launcher.url("yiyo/solovyov.png"));
		if (flag) {
			builder.addField("No Card to Discard!", "Tensions inflame further.", false);
			builder.changeDEFCON(-1);
		}
		else {
			builder.addField("", "Discarded " + CardList.getCard(discard) + ". A replacement has been drawn.", false);
			HandManager.discard(0, discard);
			Log.writeToLog("Discarded " + CardList.getCard(discard).getName() + ".");
			Random random = new Random();
			HandManager.USAHand.add(HandManager.Deck.remove(random.nextInt(HandManager.Deck.size())));
			if(HandManager.Deck.isEmpty()) {
				HandManager.Deck.addAll(HandManager.Discard);
				HandManager.Discard.clear();
			}
		}
		builder.addField("", "This will occur before every US Action round until one side sets their military operations to zero before their action round. __To do so, write `TS.decide charlie`.__", false);
		Log.writeToLog("Checkpoint C Active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "128";
	}

	@Override
	public String getName() {
		return "Checkpoint C";
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
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		for (int i : HandManager.USAHand) {
			if (CardList.getCard(i).getAssociation()==1) {
				flag = false;
				break;
			}
		}
		if (flag) return true; //no USSR cards in hand; oh no, DEFCON DROP!
		if (args.length<2) return false; //otherwise must say the card 
		try {
			discard = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException err) {
			return false;
		}
		if (!HandManager.USAHand.contains(discard)) return false; //must be in USA hand
		if (CardList.getCard(discard).getAssociation()!=1) return false; //must be a USSR event
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "The US must discard a USSR event and draw a replacement. If unable, **drop DEFCON by 1.** (Note that this occurs during the US's Action Round.)\n"
				+ "*This occurs at the beginning of every action round until either side sets their Military Operations to zero.* __To do so, write `TS.decide charlie`.__";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "The discarded card. If none are available, no argument is needed.";
	}

}

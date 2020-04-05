package yiyo;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import events.Card;
import events.CardEmbedBuilder;
import events.GrainSales;
import events.StarWars;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Antifaschistischer Schutzwall Card from the Year-In and Year-Out Pack.
 * @author adalbert
 *
 */
public class BerlinWall2 extends Card {
	
	private static int discard;
	private static int scored;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Wall Erected Around Berlin")
			.setDescription("\"Anti-Fascist Protective Rampart\" drastically cuts westward emigration")
			.setFooter("\"Freedom has many difficulties and democracy is not perfect, but we have never had to put a wall up to keep our people in, to prevent them from leaving us.\"\n"
					+ "- John F. Kennedy, 1963", Launcher.url("people/jfk.png"))
			.setColor(Color.RED);
		if (HandManager.Discard.contains(103)) {
			HandManager.Discard.remove((Integer) 103);
			HandManager.Removed.add(103);
			builder.addField("Closed Border", CardList.getCard(103) + " has been removed from the game.", false);
			Log.writeToLog("Removed Defectors from game.");
		}
		if (playedByUS()&&discard!=0) {
			HandManager.discard(1, discard);
			builder.addField("Maintenance", "The USSR discards " + CardList.getCard(discard) + " to play " + CardList.getCard(scored), false);
			Log.writeToLog("Discarded " + CardList.getCard(discard).getName() + ".");
			Log.writeToLog(CardList.getCard(scored).getName() + ":");
			CardList.getCard(scored).onEvent(1, new String[] {"dummy"});
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "125";
	}

	@Override
	public String getName() {
		return "Antifaschistischer Schutzwall";
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
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		if (!playedByUS()) return true;
		if (args.length<2) return false;
		try {
			discard = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException err) {
			return false;
		}
		if (discard==0) return true;
		if (args.length<3) return false;
		if (!HandManager.SUNHand.contains(discard)) return false; //must be in USSR's hand
		if (CardList.getCard(discard).getAssociation()!=1) return false; //must be a USSR event
		try {
			scored = Integer.parseInt(args[2]);
		}
		catch (NumberFormatException err) {
			return false;
		}
		if (!HandManager.Discard.contains(scored)) return false; //must be in the discard pile
		if (CardList.getCard(scored).getOps()!=0) return false; //must be scoring card
		return true;
	}

	@Override
	public String getDescription() {
		return "Removes " + CardList.getCard(103) + " from the game if it is in the discard pile. If played by US action, the USSR may discard a USSR-affiliated event to play a scoring card currently in the discard pile.";
	}

	@Override
	public String getArguments() {
		return "Varies: \n- If played by the USSR, no arguments. \n- If played by the US, at least one number: \n\t- In normal cases, two card IDs. The first must be a USSR event in the USSR's hand and the second must be a scoring card in the discard pile. \n\t- Alternatively, one argument: 0, to skip over that section.";
	}
	
	public static boolean playedByUS() {
		if (HandManager.activecard==125&&GameData.phasing()==0) return true; //played directly
		if (HandManager.activecard==67&&GrainSales.card==125) return true; //played by grain sales proxy
		if (HandManager.activecard==85&&StarWars.target==125) return true; //star wars proxy, unlikely as it is
		return false;
	}

}

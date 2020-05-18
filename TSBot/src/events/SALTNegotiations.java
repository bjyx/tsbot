package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The SALT Negotiations Card.
 * @author adalbert
 *
 */
public class SALTNegotiations extends Card {
	
	public static boolean emptyDiscard = false;
	public static int target;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Successful Negotiations in Helsinki")
			.setDescription("Strategic Arms Limitations Talks - the First Step to De-Escalation")
			.setFooter("\"We shall continue, in this era of negotiation, to work for the limitation of nuclear arms, and to reduce the danger of confrontation between the great powers.\"\n"
					+ "- Richard M. Nixon, 1973", Launcher.url("people/nixon.png"))
			.setColor(Color.GRAY);
		builder.changeDEFCON(2);
		builder.addField("Anti-Ballistic Missile Treaty", "For the rest of this turn, coups conducted by either superpower receive a -1 malus.", false);
		HandManager.addEffect(43);
		Log.writeToLog("SALT Active.");
		if (target==-1) {
			builder.addField("Oops!", "The discard pile is empty. You sure picked a good time to play this.", false);
		}
		else if (target==0) {
			builder.addField("No card selected!", "I suppose that pile of cards isn't really good for you right now?", false);
		}
		else {
			builder.addField("", (sp==0?"The USA ":"The USSR ") + "retrieves " + CardList.getCard(Integer.parseInt(args[1])) + " from the discard pile.", false);
			HandManager.getFromDiscard(sp, Integer.parseInt(args[1]));
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "043";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SALT Negotiations";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
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
		catch (NumberFormatException err) {
			return false;
		}
		if (target==0) return true;
		if (CardList.getCard(target).getOps()==0) return false;
		if (HandManager.Discard.contains(target)) return true;
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Increase DEFCON by two levels. \n"
				+ "*For the remainder of this turn, all coups will receive a -1 penalty to the roll.*\n"
				+ "The player who plays this may select a card out of the discard.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "The ID of the card you wish to select out of the discard, or 0 if you do not wish to extract a card.";
	}

}

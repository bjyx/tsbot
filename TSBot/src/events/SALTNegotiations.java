package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;

public class SALTNegotiations extends Card {
	
	public static boolean emptyDiscard = false;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Successful Negotiations in Helsinki")
			.setDescription("Strategic Arms Limitations Talks - the First Step to De-Escalation")
			.setFooter("\"We shall continue, in this era of negotiation, to work for the limitation of nuclear arms, and to reduce the danger of confrontation between the great powers.\"\n"
					+ "- Richard M. Nixon, 1973", Launcher.url("countries/us.png"))
			.setColor(Color.GRAY);
		builder.changeDEFCON(2);
		builder.addField("Anti-Ballistic Missile Treaty", "All coups ", false);
		HandManager.addEffect(43);
		builder.addField("", (sp==0?"The USA ":"The USSR ") + "retrieves " + CardList.getCard(Integer.parseInt(args[1])) + " from the discard pile.", false);
		HandManager.getFromDiscard(sp, Integer.parseInt(args[1]));
		GameData.txtchnl.sendMessage(builder.build());
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
	public boolean isFormatted(String[] args) {
		if (HandManager.Discard.isEmpty()) {
			emptyDiscard = true;
			return true;
		}
		else {
			emptyDiscard = false;
		}
		if (args.length<2) return false;
		int card;
		try {
			card = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException err) {
			return false;
		}
		if (CardList.getCard(card).getOps()==0) return false;
		if (HandManager.Discard.contains(card)) return true;
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
		return "The ID of the card you wish to select out of the discard.";
	}

}

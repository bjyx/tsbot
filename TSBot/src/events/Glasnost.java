package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import logging.Log;
import main.Launcher;

public class Glasnost extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Glasnost Policy")
			.setDescription("Soviet \"openness\" brings government transparency")
			.setColor(Color.red)
			.setFooter("\"Broad-based and frequently turbulent popular movements have given expression, "
					+ "in a multidimensional and contradictory way, to a longing for independence, democracy and social justice. "
					+ "The idea of democratizing the entire world order has become a powerful socio-political force.\"\n"
					+ "- Mikhail Gorbachev, 1988", Launcher.url("people/gorbachev.png"));
		builder.changeDEFCON(1);
		builder.changeVP(-2);
		if (HandManager.effectActive(87)) {
			Log.writeToLog("This card is boosted by The Reformer.");
			builder.addField("The Reformer", "The USSR may now place influence or conduct realignments using this card.", false);
			GameData.dec = new Decision (1, 90);
			GameData.ops = new Operations (1, CardList.getCard(90).getOpsMod(1), true, true, false, false, false);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "090";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Glasnost";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Improve DEFCON by 1. The USSR gains 2 VP. \n"+(HandManager.effectActive(87)?"Gorbachev is in power. The USSR may place influence or conduct realignments using this card.":("If "+CardList.getCard(87)+" is active, the USSR may place influence or conduct realignments using the Operations of this card."));
	}

	@Override
	public String getArguments() {
		return "Event: None."
				+ "Decision: Operations.";
	}

}

package promo;

import java.awt.Color;
import java.util.Random;

import cards.CardList;
import cards.HandManager;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class NonAligned extends Card {
	
	public static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle(MapManager.get(target).name + " joins the Non-Aligned Movement")
			.setDescription("")
			.setColor(Color.gray)
			.setFooter("\"Peace can not be achieved with separation, but with the aspiration towards collective security in global terms and expansion of freedom, as well as terminating the domination of one country over another.\"\n"
					+ "- Declaration of Brijuni, 1956", Launcher.url("promo/nam.png"));
		builder.addField("Mutual Non-Interference", "", false);
		builder.changeInfluence(target, 0, -MapManager.get(target).influence[0]);
		builder.changeInfluence(target, 1, -MapManager.get(target).influence[1]);
		for (int i=0; i<4; i++) {
			int x = HandManager.Deck.remove(new Random().nextInt(HandManager.Deck.size()));
			if (HandManager.Deck.isEmpty()) {
				HandManager.Deck.addAll(HandManager.Discard);
				HandManager.Discard.clear();
			}
			HandManager.Discard.add(x);
			builder.addField(panchsheel(i), "Discarded "+CardList.getCard(x), false);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		for (int i=21; i<84; i++) {
			if (MapManager.get(i).region==3||MapManager.get(i).region==5||MapManager.get(i).region==6||MapManager.get(i).region==8||i==34) {
				if (MapManager.get(i).influence[0]>=2&&MapManager.get(i).influence[1]>=2) return true;
			}
		}
		return false;
	}

	@Override
	public String getId() {
		return "111";
	}

	@Override
	public String getName() {
		return "Non-Aligned Movement";
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		target = MapManager.find(args[1]);
		if (target==-1) return false;
		if (MapManager.get(target).region==3||MapManager.get(target).region==5||MapManager.get(target).region==6||MapManager.get(target).region==8||target==34) {
			if (MapManager.get(target).influence[0]>=2&&MapManager.get(target).influence[1]>=2) return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Select any of the following:\n"
				+ "- India\n"
				+ "- A country in Southeast Asia\n"
				+ "- A country in the Middle East\n"
				+ "- A country in Africa\n"
				+ "- A country in South America\n"
				+ "The country must have at least two influence from each superpower. \n\n"
				+ "Clear that country of influence, then take four cards from the top of the deck and place them in the discard pile (reshuffle as necessary).";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "The country.";
	}
	
	private String panchsheel(int i) {
		if (i==0) return "Mutual Respect for Integrity and Sovereignty";
		if (i==1) return "Mutual Non-Aggression";
		if (i==2) return "Mutual Benefit";
		if (i==3) return "Peaceful Coexistence";
		return "";
	}

}

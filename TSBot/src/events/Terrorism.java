package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import main.Launcher;

public class Terrorism extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Terrorism!")
			.setDescription("")
			.setColor(sp==0?Color.blue:Color.red)
			.setFooter("\"We must try to find ways to starve the terrorist and the hijacker of the oxygen of publicity on which they depend.\"\n"
					+ "- Margaret Thatcher, 1985", Launcher.url("people/thatcher.png"));
		if (sp==0) {
			if (HandManager.SUNHand.isEmpty()) {
				builder.addField("The Soviet hand is empty!", "Can't discard a card if there is no card to discard.", false);
				GameData.txtchnl.sendMessage(builder.build()).complete();
				return;
			}
			int card = HandManager.SUNHand.get((int) (Math.random()*HandManager.SUNHand.size()));
			HandManager.discard(1, card);
			builder.addField("Anti-Communist Terrorism","The Soviets lose " + CardList.getCard(card) + " to a terrorist attack.",false);
		}
		else {
			if (HandManager.USAHand.isEmpty()) {
				builder.addField("The American hand is empty!", "Can't discard a card if there is no card to discard.", false);
				GameData.txtchnl.sendMessage(builder.build()).complete();
				return;
			}
			int card = HandManager.USAHand.get((int) (Math.random()*HandManager.USAHand.size()));
			HandManager.discard(0, card);
			builder.addField("Red Factions","The American lose " + CardList.getCard(card) + " to a terrorist attack.",false);
			if (HandManager.effectActive(82)) {
				card = HandManager.USAHand.get((int) (Math.random()*HandManager.USAHand.size()));
				HandManager.discard(0, card);
				builder.addField("State-sponsored terrorism","The American lose " + CardList.getCard(card) + " to Iranian-sponsored terrorists.",false);
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
		return "092";
	}

	@Override
	public String getName() {
		return "Terrorism";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 2;
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
		return true;
	}

	@Override
	public String getDescription() {
		return "Randomly discards one card from your opponent's hand. If " + CardList.getCard(82) + " is in effect and the card is targeted at the US, the US has to randomly discard two cards instead.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

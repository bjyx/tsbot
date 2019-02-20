package cards;

import java.util.List;
import java.util.Random;

import cards.CardList;
import events.Card;
import game.GameData;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class HandManager {
	public static List<Integer> USAHand;
	public static List<Integer> SUNHand;
	public static List<Integer> Deck;
	public static List<Integer> Discard;
	public static List<Integer> Removed;
	public static List<Integer> Effects;
	public static int China;
	
	public static void addToDeck(int era) {
		for (Card c : CardList.cardList) {
			if (c.getEra()==era&&!c.getId().equals("006")) Deck.add(CardList.cardList.indexOf(c)); //If the card isn't China and is part of the era listed, add that card
		}
	}
	
	public static void deal() {
		Random random = new Random();
		int handsize = (GameData.getEra()==0)?8:9;
		while (handsize > SUNHand.size()||handsize > USAHand.size()) {
			if (handsize > SUNHand.size()&&!Deck.isEmpty()) {
				SUNHand.add(Deck.remove(random.nextInt(Deck.size())));
			}
			if (handsize > USAHand.size()&&!Deck.isEmpty()) {
				USAHand.add(Deck.remove(random.nextInt(Deck.size())));
			}
			if(Deck.isEmpty()) {
				Deck.addAll(Discard);
				Discard.clear();
			}
		}
	}
	
	public static MessageEmbed getUSAHand() {
		EmbedBuilder builder = new EmbedBuilder().setTitle("USA Hand");
		for (int c : USAHand) {
			builder.addField(CardList.getCard(c).toString(), CardList.getCard(c).getDescription(), false);
		}
		if (China==0) {
			builder.addField(CardList.cardList.get(6).toString(), CardList.cardList.get(6).getDescription(), false);
		}
		return builder.build(); 
	}
	
	public static MessageEmbed getSUNHand() {
		EmbedBuilder builder = new EmbedBuilder().setTitle("USA Hand");
		for (int c : SUNHand) {
			builder.addField(CardList.getCard(c).toString(), CardList.getCard(c).getDescription(), false);
		}
		if (China==1) {
			builder.addField(CardList.cardList.get(6).toString(), CardList.cardList.get(6).getDescription(), false);
		}
		return builder.build(); 
	}
	
	public static void play(int sp, int card, String[] args) {
		if (card==6&&China==sp) {
			China += 2;
		}
		if (sp==0&&USAHand.contains(card)) {
			USAHand.remove(card);
		}
		if (sp==1&&SUNHand.contains(card)) {
			SUNHand.remove(card);
		}
		//super complicated area, will nitgrit later
		if (CardList.getCard(card).isRemoved()) Removed.add(card);
		else Discard.add(card);
	}
	public static void discard(int sp, int card) {
		if (sp==0&&USAHand.contains(card)) {
			USAHand.remove(card);
		}
		if (sp==1&&SUNHand.contains(card)) {
			SUNHand.remove(card);
		}
		Discard.add(card);
	}
	public static void getFromDiscard(int sp, int card) {
		if (Discard.contains(card)) {
			Discard.remove(card);
		}
		if (sp==0) {
			USAHand.add(card);
		}
		if (sp==1) {
			SUNHand.add(card);
		}
	}
	public static void transfer(int source, int card) {
		if (source==0&&USAHand.contains(card)) {
			USAHand.remove(card);
			SUNHand.add(card);
		}
		if (source==1&&SUNHand.contains(card)) {
			USAHand.add(card);
			SUNHand.remove(card);
		}
	}
	public static void addEffect(int card) {
		Effects.add(card);
	}
	public static void removeEffect(int card) {
		Effects.remove(Effects.indexOf(card));
	}
}

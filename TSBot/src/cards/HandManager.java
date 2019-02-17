package cards;

import java.util.List;
import java.util.Random;

import cards.CardList;
import events.Card;
import game.GameData;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class HandManager {
	private static List<Integer> USAHand;
	private static List<Integer> SUNHand;
	private static List<Integer> Deck;
	private static List<Integer> Discard;
	private static List<Integer> Removed;
	private static List<Integer> Effects;
	private static boolean China;
	
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
		if (China==false) {
			builder.addField(CardList.cardList.get(6).toString(), CardList.cardList.get(6).getDescription(), false);
		}
		return builder.build(); 
	}
	
	public static MessageEmbed getSUNHand() {
		EmbedBuilder builder = new EmbedBuilder().setTitle("USA Hand");
		for (int c : SUNHand) {
			builder.addField(CardList.getCard(c).toString(), CardList.getCard(c).getDescription(), false);
		}
		if (China==true) {
			builder.addField(CardList.cardList.get(6).toString(), CardList.cardList.get(6).getDescription(), false);
		}
		return builder.build(); 
	}
	
	public static void play(int sp, int card) {
		if (sp==0&&USAHand.contains(card)) {
			
		}
		if (sp==1&&SUNHand.contains(card)) {
			
		}
		if (CardList.getCard(card).isRemoved()) Removed.add(card);
		else Discard.add(card);
	}
	public static void discard(int sp, int card) {
		
	}
}

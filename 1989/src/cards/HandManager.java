package cards;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import cards.CardList;
import commands.InfoCommand;
import commands.TimeCommand;
import events.Card;
import events.CardEmbedBuilder;
import events.Chernobyl;
import game.GameData;
import logging.Log;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import readwrite.ReadWrite;
import turnzero.NationalistChina;
/**
 * Manages everything related to the cards used in the game - which cards are in the Deck, which are Discarded, which are in the players' hands, and which have ongoing effects.
 * @author adalbert
 *
 */
public class HandManager {
	/**
	 * The cards in the hand of the Democrat player.
	 */
	public static ArrayList<Integer> DemHand = new ArrayList<Integer>();
	/**
	 * The cards in the hand of the Communist player.
	 */
	public static ArrayList<Integer> ComHand = new ArrayList<Integer>();
	/**
	 * The cards in the deck.
	 */
	public static ArrayList<Integer> Deck = new ArrayList<Integer>();
	/**
	 * The cards in the discard pile.
	 */
	public static ArrayList<Integer> Discard = new ArrayList<Integer>();
	/**
	 * The cards with starred events that have been triggered.
	 */
	public static ArrayList<Integer> Removed = new ArrayList<Integer>();
	/**
	 * The cards with ongoing effects.
	 * <br>
	 * Valid cards to put here:
	 * <ul>
	 * <li>
	 */
	public static ArrayList<Integer> Effects = new ArrayList<Integer>();
	/**
	 * The active card (i.e. the card in play for this action round). 
	 */
	public static int activecard = 0;
	/**
	 * The way in which the activecard is being played:
	 * <ul>
	 * 	<li>e(vent)</li>
	 * 	<li>o(perations)</li>
	 * 	<li>t(square)</li>
	 * 	<li>(event) f(irst)</li>
	 * 	<li>(event) l(ast)</li>
	 * </ul>
	 */
	public static char playmode = 0;
	/**
	 * Creates a custom array of cards.
	 */
	/*public static void customCards (String s) {
		final ArrayList<Integer> effectsPermanent = new ArrayList<Integer>(Arrays.asList(16,17,21,23,27,55,59,65,68,82,83,86,87,96,97,110,124,137));
		final ArrayList<Integer> effectsTemporary = new ArrayList<Integer>(Arrays.asList(9,25,35,41, 42,43,44,50, 51,60,73,861, 93,0,0,94, 106,109,115,126, 128,129,310,311, 400,401,490,491, 690,691,1001,1002, 1003,1004,1005,1006, 1210,1211,1270,1271, 1350,1351,0,0));
		DemHand = new ArrayList<Integer>();
		ComHand = new ArrayList<Integer>();
		Deck = new ArrayList<Integer>(); 
		Discard = new ArrayList<Integer>();
		Removed = new ArrayList<Integer>();
		Effects = new ArrayList<Integer>();
		for (int i=0; i<=110; i++) {
			if (ReadWrite.undoParser(s.charAt(i))==0) CardList.cardList.set(i, new Placeholder());
			if (ReadWrite.undoParser(s.charAt(i))==1) continue;
			if (ReadWrite.undoParser(s.charAt(i))==2) Deck.add(i);
			if (ReadWrite.undoParser(s.charAt(i))==3) DemHand.add(i);
			if (ReadWrite.undoParser(s.charAt(i))==4) ComHand.add(i);
			if (ReadWrite.undoParser(s.charAt(i))==5) Discard.add(i);
			if (ReadWrite.undoParser(s.charAt(i))==6) {
				Removed.add(i);
				if (effectsPermanent.contains(i)) {
					if (effectActive(55) && i==96) removeEffect(55);
					if (effectActive(59) && i==97) removeEffect(59);
					if (GameData.getSpace(1)>=3 && i==124);
					else addEffect(i);
				}
				if (i==112) MapManager.get(62).stab = 3;
			}
		}
		for (int i=0; i<11; i++) {
			int x = ReadWrite.undoParser(s.charAt(139+i));
			if (x>=8 && effectsTemporary.get(4*i)!=0) {
				x-=8;
				Effects.add(effectsTemporary.get(4*i));
			}
			if (x>=4 && effectsTemporary.get(4*i+1)!=0) {
				x-=4;
				Effects.add(effectsTemporary.get(4*i+1));
			}
			if (x>=2 && effectsTemporary.get(4*i+2)!=0) {
				x-=2;
				Effects.add(effectsTemporary.get(4*i+2));
			}
			if (x>=1 && effectsTemporary.get(4*i+3)!=0) {
				x-=1;
				Effects.add(effectsTemporary.get(4*i+3));
				if (effectsTemporary.get(4*i+3)==94) {
					Chernobyl.regionBan = new boolean[10];
					if (x==0) {
						Chernobyl.regionBan[0]=true;
						Chernobyl.regionBan[1]=true;
						Chernobyl.regionBan[2]=true;
						Chernobyl.reg = 0;
					}
					else if (x==1) {
						Chernobyl.regionBan[3]=true;
						Chernobyl.reg = 3;
					}
					else if (x==2) {
						Chernobyl.regionBan[4]=true;
						Chernobyl.regionBan[5]=true;
						Chernobyl.reg = 4;
					}
					else {
						Chernobyl.regionBan[x+3]=true;
						Chernobyl.reg = x+3;
					}
					
				}
			}
			if (i==10 && x>=2) {
				x -= 2;
				CardList.cardList.set(35, new NationalistChina());
				MapManager.get(43).isBattleground = true;
			}
			if (i==10 && x==1) {
				x -= 1;
				Operations.tsarbomba = true;
			}
		}
	} 
	*/
	/**
	 * Resets everything to its initial state.
	 */
	public static void reset() {
		DemHand = new ArrayList<Integer>();
		ComHand = new ArrayList<Integer>();
		Deck = new ArrayList<Integer>();
		Discard = new ArrayList<Integer>();
		Removed = new ArrayList<Integer>();
		Effects = new ArrayList<Integer>();
		activecard = 0;
		playmode = 0;
	}
	/**
	 * Adds all cards of the specified era to the deck - meant to be done on Turns 4 and 8.
	 * @param era is one of 0, 1, and 2, representing the early, mid, and late wars.
	 */
	public static void addToDeck(int era) {
		for (Card c : CardList.cardList) {
			if (c.getEra()==era&&!c.getId().equals("006")) Deck.add(CardList.cardList.indexOf(c)); //If the card isn't China and is part of the era listed, add that card
		}
	}
	/**
	 * Deals each player a hand of eight or nine cards, depending on the era. If the deck is empty, it reshuffles the discards. (Rules 3.1, 4.1, 4.3, and 4.5B)
	 */
	public static void deal() {
		Random random = new Random();
		while (8 > ComHand.size()||8 > DemHand.size()) {
			if (8 > ComHand.size()&&!Deck.isEmpty()) {
				ComHand.add(Deck.remove(random.nextInt(Deck.size())));
			}
			if(Deck.isEmpty()) { //Rule 4.3
				Deck.addAll(Discard);
				Discard.clear();
			}
			if (8 > DemHand.size()&&!Deck.isEmpty()) {
				DemHand.add(Deck.remove(random.nextInt(Deck.size())));
			}
			if(Deck.isEmpty()) { //Rule 4.3
				Deck.addAll(Discard);
				Discard.clear();
			}
		}
	}
	/**
	 * Creates a MessageEmbed containing the details of the US's cards. To be sent after every turn. 
	 * @return A MessageEmbed, containing the details of the US's hand.
	 */
	public static MessageEmbed getUSAHand() {
		EmbedBuilder builder = new EmbedBuilder().setTitle("USA Hand").setColor(Color.blue);
		for (int c : DemHand) {
			builder.addField(CardList.getCard(c).toString(), CardList.getCard(c).getDescription(), false);
		}
		return builder.build(); 
	}
	/**
	 * Creates a MessageEmbed containing the details of the USSR's cards. To be sent after every turn. 
	 * @return A MessageEmbed, containing the details of the USSR's hand.
	 */
	public static MessageEmbed getSUNHand() {
		EmbedBuilder builder = new EmbedBuilder().setTitle("USSR Hand").setColor(Color.red);
		for (int c : ComHand) {
			builder.addField(CardList.getCard(c).toString(), CardList.getCard(c).getDescription(), false);
		}
		return builder.build(); 
	}
	/**
	 * Plays a card, as in a normal action round, by setting the active card and playmode.
	 * @param sp is the superpower playing the card (0 for the US, 1 for the USSR). 
	 * @param card is the card in question.
	 * @param mode is the mode of playing, as described under playmode.
	 */
	public static void play(int sp, int card, char mode) {
		
		
		//super complicated area, will nitgrit later
		
		GameData.txtchnl.sendMessage(CardList.getCard(card).toEmbed(sp).setAuthor("Turn " + GameData.getTurn() + " " + (GameData.getAR()==0?"Headline":("AR " + ((GameData.getAR() + 1)/2) + (GameData.phasing()==0?" US":" USSR"))), InfoCommand.url()).build()).complete();
		if (mode=='e') {
			removeFromHand(sp,card);
			if (CardList.getCard(card).getAssociation()==(GameData.getAR()+1)%2) {
				Log.writeToLog((sp==0?"US":"SU")+"plays " + CardList.getCard(card).getName() + " for the event first.");
				playmode = 'f';
			}
			else {
				Log.writeToLog((sp==0?"US":"SU")+"plays " + CardList.getCard(card).getName() + " as event.");
				playmode = 'e';
			}
			activecard = card;
			TimeCommand.cardPlayed = true;
			TimeCommand.eventRequired = true;
		}
		if (mode=='o') {
			if (card==6) {
				China = (China+1)%2+2;
				if (GameData.phasing()==0&&removeEffect(35)) {
					Log.writeToLog("Formosan Resolution Cancelled.");
					GameData.txtchnl.sendMessage(new CardEmbedBuilder()
							.setTitle("Defense Treaty Abrogated")
							.setDescription("Taiwan will no longer be a battleground country in any situation.")
							.setColor(Color.DARK_GRAY)
							.setFooter("\"Everyone, listen; just let me say one thing. I opposed China, I was wrong.\"\n"
							+ "- Richard M. Nixon, *Nixon in China*", Launcher.url("countries/us.png"))
							.build()).complete();
				}
				Log.writeToLog((sp==0?"US":"SU")+"plays " + CardList.getCard(card).getName() + " as ops.");
				playmode = 'o'; //ops only — bug fix
			}
			
			else if (CardList.getCard(card).getAssociation()==(GameData.getAR()+1)%2&&CardList.getCard(card).isPlayable(sp)) {
				removeFromHand(sp,card);
				Log.writeToLog((sp==0?"US":"SU")+"plays " + CardList.getCard(card).getName() + " for ops first.");
				playmode = 'l'; //event last
			}
			else {
				discard(sp, card);
				Log.writeToLog((sp==0?"US":"SU")+"plays " + CardList.getCard(card).getName() + " as ops.");
				playmode = 'o'; //ops only
			}
			activecard = card;
			GameData.ops = new Operations(sp, CardList.getCard(card).getOpsMod(sp), true, true, true, false, false);
			TimeCommand.cardPlayed = true;
			TimeCommand.operationsRequired = true;
		}
		if (mode=='s') {
			if (card==6) {
				China = (China+1)%2+2;
			}
			Log.writeToLog((sp==0?"US":"SU")+"plays " + CardList.getCard(card).getName() + " to space.");
			discard(sp, card);
			playmode = 's';
			activecard = card;
			GameData.ops = new Operations(sp, CardList.getCard(card).getOpsMod(sp), false, false, false, true, false);
			TimeCommand.cardPlayed = true;
			TimeCommand.spaceRequired = true;
		}
		TimeCommand.prompt();
	}
	public static boolean handContains(int sp, int card) {
		if (sp==0) return DemHand.contains(card);
		if (sp==1) return ComHand.contains(card);
		return false;
	}
	public static boolean addToHand(int sp, int card) {
		if (sp==0) {
			DemHand.add(card);
		}
		if (sp==1) {
			ComHand.add(card);
		}
		return true;
	}
	public static boolean removeFromHand(int sp, int card) {
		if (sp==0&&DemHand.contains(card)) {
			USAHand.remove((Integer) card);
			return true;
		}
		else if (sp==1&&SUNHand.contains(card)) {
			SUNHand.remove((Integer) card);
			return true;
		}
		else return false;
	}
	/**
	 * Discards the card in question.
	 * @param sp is the integer representing the player in possession of the card.
	 * @param card is the card to be discarded. 
	 * @return true if successful, false otherwise.
	 */
	public static boolean discard(int sp, int card) {
		if (removeFromHand(sp, card)) {
			Discard.add(card);
			return true;
		}
		else return false;
	}
	/**
	 * Removes the card in question from the game.
	 * @param sp is the integer representing the player in possession of the card.
	 * @param card is the card to be discarded. 
	 * @return true if successful, false otherwise.
	 */
	public static boolean removeFromGame(int sp, int card) {
		if (removeFromHand(sp, card)) {
			Removed.add(card);
			return true;
		}
		else return false;
	}
	/**
	 * Obtains the card requested from the discard. 
	 * @param sp is the integer representing the player requesting the card. 
	 * @param card is the card to be reclaimed. 
	 * @return true if successful, false otherwise.
	 */
	public static boolean getFromDiscard(int sp, int card) {
		if (Discard.contains(card)) {
			Discard.remove((Integer) card);
		}
		else return false;
		addToHand(sp, card);
		return true;
	}
	/**
	 * Transfers the card in question to the other player's hand.
	 * @param source is the integer representing the player in possession of the card.
	 * @param card is the card in question. 
	 * @return true if successful, false otherwise.
	 */
	public static boolean transfer(int source, int card) {
		if (removeFromHand(source, card)) {
			addToHand((source+1)%2, card);
			return true;
		}
		return false;
	}
	/**
	 * Puts the card in question into the effects pile.
	 * @param card is the card in question.
	 */
	public static void addEffect(int card) {
		Effects.add(card);
	}
	/**
	 * Removes the card in question from {@link #Effects} if such a card exists in the pile.
	 * @param card is the card in question.
	 * @return true if successful, false otherwise.
	 */
	public static boolean removeEffect(int card) {
		if (!effectActive(card)) return false;
		Effects.remove((Integer) card);
		return true;
	}
	/**
	 * Detects whether a given persistent event is active.
	 * @param card is the event in question.
	 * @return A boolean value.
	 */
	public static boolean effectActive(int card) {
		return Effects.contains(card);
	}
	/**
	 * Checks the hands for scoring cards.
	 * @return 0 if neither hand contains a scoring card;
	 * <br> 1 if the US's hand contains a scoring card;
	 * <br> 2 if the USSR's hand contains a scoring card.
	 * <br> 3 if both hands contain a scoring card.
	 */
	public static int checkScoring() {
		int i=0;
		for (int c : ComHand) {
			if (CardList.getCard(c).getOps()==0) {
				i+= 2;
				break;
			}
		}
		for (int c : USAHand) {
			if (CardList.getCard(c).getOps()==0) {
				i+= 1;
				break;
			}
		}
		return i;
	}
	/**
	 * Counts the scoring cards in a given player's hand. 
	 * @param sp is the superpower in question.
	 * @return An integer.
	 */
	public static int countScoring(int sp) {
		int i=0;
		if (sp==1) for (int c : ComHand) {
			if (CardList.getCard(c).getOps()==0) i++;
		}
		else for (int c : USAHand) {
			if (CardList.getCard(c).getOps()==0) i++;
		}
		return i;
	}
}

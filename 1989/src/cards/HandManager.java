package cards;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import cards.CardList;
import commands.TimeCommand;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import logging.Log;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
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
	 * <li> {@code 002 Solidarity Legalized} - Allows {@code 003 Walesa}. Com can no longer check Gdansk.
	 * <li> {@code 005 General Strike} - Bear Trap.
	 * <li> {@code 009 The Wall} - The next Com support check ignores adjacency for the Democrat.
	 * <li> {@code 013 Stasi} - Fuck Stasi.
	 * <li> {@code 015 Honecker} - Com gets an extra action round (skippable)
	 * <li> {@code 017 Roundtable Talks} - Dem gets two cards from Com in next power struggle.
	 * <li> {@code 024 St. Nicholas} - Allows {@code 061 Monday Demonstrations}.
	 * <li> {@code 025 Perestroika} - Brezhnev.
	 * <li> {@code 026 Helsinki Final Act} - Com support checks in student and intellectual spaces give +1 VP to the Democrat.
	 * <li> {@code 030 Tear Gas} - The next Com support check in a Student Space gets +1.
	 * <li> {@code 039 Ecoglasnost} - Com support checks in Ruse give +1 VP to Dem.
	 * <li> {@code 048 We Are The People} - Com can no longer check Leipzig.
	 * <li> {@code 049 Debt Burden} - Chernobyl, but for support checks.
	 * <li> {@code 050 Sinatra Doctrine} - Containment.
	 * <li> {@code 053 Li Peng} - +1 to Com TSquares.
	 * <li> {@code 054 Crowd Turns Against Ceausescu} - On the next scoring of Romania, sample 15 cards from the PS deck after drawing. Then Dem gets 3x the number of rally cards in that sample as Ops for the turn. (aaaaa) Also allows {@code 097 Tyrant is Gone}. <br>
	 * <li> {@code 058 Austria-Hungary Border} - +1 Ops for Dem if ops used in E. Germany.
	 * <li> {@code 059 Grenztruppen} - -1 to Dem checks in E. Germany for rest of turn.
	 * <li> {@code 062 Yakolev} - Winning the next struggle gives +1 to rolls.
	 * <li> {@code 063 Genscher} - Ignores extra cost in E. Germany.
	 * <li> {@code 065 Presidential Visit} - Com draws 7 cards next turn.
	 * <li> {@code 070 Securitate} - Dem must reveal hand during next power struggle in Romania.
	 * <li> {@code 072 Peasant Revolt} - If Dem holds Farmer Space in next PS, draw one card from communist.
	 * <li> {@code 073 Laszlo Tokes} - Allows {@code Massacre in Timisoara}.
	 * <li> {@code 074 FRG Embassies} - +1 to Dem checks in E. Europe
	 * <li> {@code 077 Samizdat} - Delay a card.
	 * <li> {@code 080 Prudence, Bush 41} - Red Scare.
	 * <li> {@code 081 Prudence, Shev} - Purge.
	 * <li> {@code 083 Modrow} - Prevents {@code 015 Honecker}.
	 * <li> {@code 097 Tyrant Is Gone} - Prevents Ceausescu Events.
	 * <li> {@code 970 Tyrant Is Gone pending} - Plays Tyrant Is Gone as soon as Romania Scoring is resolved.
	 * <li> {@code 099 Ligachev} - WWBY. 
	 * <li> {@code 100 Stand Fast} - -1 to opponent checks in your spaces for rest of turn.
	 * <li> {@code 101 Elena} - -1 to Dem checks in Romania for rest of turn.
	 * <li> {@code 102 National Salvation Front} - Com gets two cards from Dem in next Balkan PS.
	 * <li> {@code 104 New Year's Eve} - AAAAAAAAAAAAAAAAAAA
	 * <li> {@code 108 Army Backs Revolution} - Cancels {@code 070 Securitate}.
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
			if (c.getEra()==era) Deck.add(CardList.cardList.indexOf(c)); //If the card is part of the era listed, add that card
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
	public static void play(int sp, int card, char mode, boolean a) {
		
		
		//super complicated area, will nitgrit later
		
		GameData.txtchnl.sendMessage(CardList.getCard(card).toEmbed(sp).setAuthor("Turn " + GameData.getTurn() + " " + (GameData.getAR()==0?"Headline":("AR " + ((GameData.getAR() + 1)/2) + (GameData.phasing()==0?" US":" USSR")))).build()).complete();
		if (mode=='e') {
			removeFromHand(sp,card);
			if (CardList.getCard(card).getAssociation()==(GameData.getAR()+1)%2) {
				Log.writeToLog((sp==0?"Dem":"Com")+" plays " + CardList.getCard(card).getName() + " for the event first.");
				playmode = 'f';
			}
			else {
				if (a&&Operations.eight==sp) {
					EmbedBuilder builder = new CardEmbedBuilder().setTitle("T-Square Initiative").setDescription(CardList.getCard(card) + " to be used for both Operations and Events.");
					GameData.txtchnl.sendMessage(builder.build()).complete();
					Operations.eight+=2;
					Log.writeToLog((sp==0?"Dem":"Com")+" plays " + CardList.getCard(card).getName() + " for the event first, using ability 8.");
					playmode = 'f';
				}
				else {
					Log.writeToLog((sp==0?"Dem":"Com")+" plays " + CardList.getCard(card).getName() + " as event.");
					playmode = 'e';
				}
			}
			activecard = card;
			TimeCommand.cardPlayed = true;
			TimeCommand.eventRequired = true;
		}
		if (mode=='o') {
			if (CardList.getCard(card).getAssociation()==(GameData.getAR()+1)%2&&CardList.getCard(card).isPlayable(sp)) {
				if (a&&Operations.seven==sp) {
					Operations.seven+=2;
					EmbedBuilder builder = new CardEmbedBuilder().setTitle("T-Square Initiative").setDescription(CardList.getCard(card) + " event cancelled.");
					GameData.txtchnl.sendMessage(builder.build()).complete();
					Log.writeToLog((sp==0?"Dem":"Com")+" plays " + CardList.getCard(card).getName() + " as ops, using ability 7.");
					playmode = 'o';
				}
				else {
					removeFromHand(sp,card);
					Log.writeToLog((sp==0?"Dem":"Com")+" plays " + CardList.getCard(card).getName() + " for ops first.");
					playmode = 'l'; //event last
				}
			}
			else {
				if (a&&Operations.eight==sp) {
					Operations.eight+=2;
					EmbedBuilder builder = new CardEmbedBuilder().setTitle("T-Square Initiative").setDescription(CardList.getCard(card) + " to be used for both Operations and Events.");
					GameData.txtchnl.sendMessage(builder.build()).complete();
					Log.writeToLog((sp==0?"Dem":"Com")+" plays " + CardList.getCard(card).getName() + " for ops first, using ability 8.");
					playmode = 'l';
				}
				else {
					discard(sp, card);
					Log.writeToLog((sp==0?"Dem":"Com")+" plays " + CardList.getCard(card).getName() + " as ops.");
					playmode = 'o'; //ops only
				}
			}
			activecard = card;
			GameData.ops = new Operations(sp, CardList.getCard(card).getOpsMod(sp), true, true, false, 2);
			TimeCommand.cardPlayed = true;
			TimeCommand.operationsRequired = true;
		}
		if (mode=='t') {
			Log.writeToLog((sp==0?"Dem":"Com")+" T-squares " + CardList.getCard(card).getName() + ".");
			discard(sp, card);
			playmode = 't';
			activecard = card;
			GameData.ops = new Operations(sp, CardList.getCard(card).getOpsMod(sp), false, false, true, 0);
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
			DemHand.remove((Integer) card);
			return true;
		}
		else if (sp==1&&ComHand.contains(card)) {
			ComHand.remove((Integer) card);
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
		for (int c : DemHand) {
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
		else for (int c : DemHand) {
			if (CardList.getCard(c).getOps()==0) i++;
		}
		return i;
	}
}

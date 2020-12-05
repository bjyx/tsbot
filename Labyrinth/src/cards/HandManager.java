package cards;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cards.CardList;
//import commands.TimeCommand;
import events.Card;
import events.CardEmbedBuilder;
//import events.Decision;
import game.GameData;
import logging.Log;
import main.Common;
import main.Launcher;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
/**
 * Manages everything related to the cards used in the game - which cards are in the Deck, which are Discarded, which are in the players' hands, and which have ongoing effects.
 * @author adalbert
 *
 */
public class HandManager {
	/**
	 * Whether the decks in the game operate on a reshuffle discard principle. 
	 */
	public static boolean reshuffle = true;
	/**
	 * The cards in the hand of the American player.
	 */
	public static ArrayList<Integer> USAHand = new ArrayList<Integer>();
	/**
	 * The cards in the hand of the Jihadist player.
	 */
	public static ArrayList<Integer> JihHand = new ArrayList<Integer>();
	/**
	 * The cards in the deck currently in use.
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
	 * The cards with ongoing effects, incl. Lingering and Lapsing events. 
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
		USAHand = new ArrayList<Integer>();
		JihHand = new ArrayList<Integer>();
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
	public static void seedDecks(int scenario) {
		List<Integer> x, y;
		switch (scenario) {
		case 0:
			for (int i=1; i<=120; i++) {
				Deck.add(i);
			}
			break;
		case 1:
			for (int i=1; i<=120; i++) {
				if (i!=78) Deck.add(i); //dodge Axis of Evil
				else Removed.add(i);
			}
			break;
		case 2:
			x = Arrays.asList(43, 109);
			for (int i=1; i<=120; i++) {
				if (!x.contains(i)) Deck.add(i);
				else Removed.add(i);
			}
			addEffect(43); //Patriot
			addEffect(80); //FATA
			break;
		case 3:
			x = Arrays.asList(43, 109, 5, 57, 116, 37);
			for (int i=1; i<=120; i++) {
				if (!x.contains(i)) Deck.add(i);
				else Removed.add(i);
			}
			addEffect(43); //Patriot
			addEffect(80); //FATA
			addEffect(57); //Abu Sayyaf
			addEffect(5); //NEST
			addEffect(31); //Wiretap
			addEffect(44); //Renditions
			addEffect(34); //Enhanced
			break;
		case 4:
			for (int i=121; i<=240; i++) {
				Deck.add(i);
			}
			//new normal; no carryover
			break;
		case 5:
			for (int i=121; i<=240; i++) {
				Deck.add(i);
			}
			break;
		case 6: //deck seeding time lol
			Deck = new ArrayList<Integer>(Arrays.asList(124, 131, 132, 133, 134, 148, 153, 154, 164, 175, 191, 203, 206, 211, 212, 213, 220, 223, 227, 228));
			x = Arrays.asList(123, 141, 142, 145, 151, 156, 173, 178, 180, 188, 201, 210, 215, 216, 217, 218, 221, 225, 230, 231);
			y = Arrays.asList();
			for (int i=121; i<=240; i++) {
				if (!x.contains(i)&&!Deck.contains(i)) y.add(i);
			}
			Random random = new Random();
			for (int i=0; i<40; i++) {
				Deck.add(y.remove(random.nextInt(y.size()))); //40 random cards
			}
			reshuffle = false;
			break;
		case 7:
			x = Arrays.asList(133, 185, 237);
			for (int i=121; i<=240; i++) {
				if (!x.contains(i)) Deck.add(i);
				else Removed.add(i);
			}
			break;
		case 8:
			x = Arrays.asList(133, 185, 237, 151, 174, 184, 188, 194, 234);
			for (int i=121; i<=240; i++) {
				if (!x.contains(i)) Deck.add(i);
				else Removed.add(i);
			}
			break;
		case 9:
			for (int i=1; i<=120; i++) {
				Deck.add(i);
			}
			reshuffle = false;
			break;
		case 10:
			for (int i=1; i<=240; i++) { //full deck shuffle
				Deck.add(i);
			}
			break;
		case 11:
			for (int i=241; i<=360; i++) {
				Deck.add(i);
			}
			break;
		case 12:
			x = Arrays.asList(303, 340, 343);
			for (int i=241; i<=360; i++) {
				if (!x.contains(i)) Deck.add(i);
				else Removed.add(i);
			}
			addEffect(251); //Tweets on
			break;
		case 13:
			x = Arrays.asList(303, 340, 343);
			for (int i=241; i<=360; i++) {
				if (!x.contains(i)) Deck.add(i);
				else Removed.add(i);
			}
			addEffect(251); //Tweets on
			break;
		case 14:
			for (int i=1; i<=120; i++) {
				Deck.add(i);
			}
			reshuffle = false;
			break;
		case 15:
			for (int i=1; i<=360; i++) {
				Deck.add(i);
			}
			break;
		default:
			break;
		}
	}
	/**
	 * Deals each player a hand of cards, depending on funding or deployment. If the deck is empty, it creates a new deck based on the scenario.
	 */
	public static void deal() {
		Random random = new Random();
		while ((GameData.getFund()-1)/3+7 > JihHand.size()||Math.min(GameData.trackTroops()/5+7, 9) > USAHand.size()) {
			if ((GameData.getFund()-1)/3+7 > JihHand.size()&&!Deck.isEmpty()) {
				JihHand.add(Deck.remove(random.nextInt(Deck.size())));
			}
			if(Deck.isEmpty()) { //Rule 4.3
				GameData.reshuffle--;
				if (GameData.reshuffle==0) {
					GameData.checkVictory(true); //game ends upon exhausting deck
					return;
				}
				if (reshuffle) { //reuse the discard pile
					Deck.addAll(Discard);
					Discard.clear();
				}
				else if (GameData.scenario==6) { //second half of a seeded deck
					for (int i=121; i<=240; i++) {
						if (!Discard.contains(i)&&!Removed.contains(i)&&!USAHand.contains(i)&&!JihHand.contains(i)) Deck.add(i);
					}
				}
				else if (GameData.scenario==14&&GameData.reshuffle==1) { //Third stage of extended campaign
					for (int i=241; i<=360; i++) {
						Deck.add(i);
					}
				}
				else {
					for (int i=121; i<=240; i++) { //second stage of either campaign
						Deck.add(i);
					}
					GameData.awk = true;
				}
			}
			if (Math.min(GameData.trackTroops()/5+7, 9) > USAHand.size()&&!Deck.isEmpty()) {
				USAHand.add(Deck.remove(random.nextInt(Deck.size())));
			}
			if(Deck.isEmpty()) { //Rule 4.3
				GameData.reshuffle--;
				if (GameData.reshuffle==0) {
					GameData.checkVictory(true);
				}
				if (reshuffle) { //reuse the discard pile
					Deck.addAll(Discard);
					Discard.clear();
				}
				else if (GameData.scenario==6) { //second half of a seeded deck
					for (int i=121; i<=240; i++) {
						if (!Discard.contains(i)&&!Removed.contains(i)&&!USAHand.contains(i)&&!JihHand.contains(i)) Deck.add(i);
					}
				}
				else if (GameData.scenario==14&&GameData.reshuffle==1) { //Third stage of extended campaign
					for (int i=241; i<=360; i++) {
						Deck.add(i);
					}
				}
				else for (int i=121; i<=240; i++) { //second stage of either campaign
					Deck.add(i);
				}
			}
		}
	}
	/**
	 * Creates a MessageEmbed containing the details of the US's cards. To be sent after every turn. 
	 * @return A MessageEmbed, containing the details of the US's hand.
	 */
	public static MessageEmbed getUSAHand() {
		EmbedBuilder builder = new EmbedBuilder().setTitle("USA Hand").setColor(Color.blue);
		for (int c : USAHand) {
			builder.addField(CardList.getCard(c).toString(), CardList.getCard(c).getDescription(), false);
		}
		return builder.build(); 
	}
	/**
	 * Creates a MessageEmbed containing the details of the USSR's cards. To be sent after every turn. 
	 * @return A MessageEmbed, containing the details of the USSR's hand.
	 */
	public static MessageEmbed getSUNHand() {
		EmbedBuilder builder = new EmbedBuilder().setTitle("Jihadist Hand").setColor(Color.green);
		for (int c : JihHand) {
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
		
		/*
		//TODO super complicated area, will nitgrit later
		activecard = card;
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
			TimeCommand.cardPlayed = true;
			TimeCommand.cardPlayedSkippable = true;
			TimeCommand.eventRequired = true;
		}
		if (mode=='o') {
			if (card==21) {
				CardEmbedBuilder builder = new CardEmbedBuilder();
				Log.writeToLog("Common European Home:");
				builder.setTitle("Gorbachev Blasts " + Common.players[sp] + "s")
					.setDescription("Common European Home policy used to justify violence")
					.setColor(Common.spColor(Common.opp(sp)));
				builder.changeVP(1-sp*2);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			if (CardList.getCard(card).getAssociation()==Common.opp(sp)&&CardList.getCard(card).isPlayable(sp)) {
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
				if (a&&Operations.eight==sp&&CardList.getCard(card).getAssociation()!=Common.opp(sp)&&CardList.getCard(card).isPlayable(sp)) {
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
			GameData.ops = new Operations(sp, CardList.getCard(card).getOpsMod(sp), true, true, false, 2);
			TimeCommand.cardPlayed = true;
			TimeCommand.cardPlayedSkippable = true;
			TimeCommand.operationsRequired = true;
		}
		if (mode=='t') {
			Log.writeToLog((sp==0?"Dem":"Com")+" T-squares " + CardList.getCard(card).getName() + ".");
			discard(sp, card);
			playmode = 't';
			GameData.ops = new Operations(sp, CardList.getCard(card).getOpsMod(sp), false, false, true, 0);
			TimeCommand.cardPlayed = true;
			TimeCommand.cardPlayedSkippable = true;
			TimeCommand.spaceRequired = true;
		}
		if (mode=='c') {
			EmbedBuilder un = new CardEmbedBuilder().setTitle("Common European Home")
					.setColor(Common.spColor(sp))
					.setFooter("\"We assign an overriding significance to the European course of our foreign policy.... We are resolutely against the division of the continent into military blocs facing each other, against the accumulation of military arsenals in Europe, against everything that is the source of the threat of war.\"\n"
							+ "- Mikhail Gorbachev, 1987", Launcher.url("people/gorbachev.png"))
					.addField("Common European Home", "The event of "+ CardList.getCard(card)+" will not occur.", false);
			GameData.txtchnl.sendMessage(un.build()).complete();
			HandManager.discard(sp, card);
			HandManager.discard(sp, 21);
			playmode = 'o';
			GameData.ops = new Operations(sp, CardList.getCard(card).getOpsMod(sp), true, true, false);
			Common.spChannel(sp).sendMessage(Common.spRole(sp).getAsMention() + ", you may now perform the operations.");
			TimeCommand.cardPlayed = true;
			TimeCommand.cardPlayedSkippable = true;
			TimeCommand.operationsRequired = true;
			Log.writeToLog("Common Europeah Home played with card for ops.");
		}
		TimeCommand.prompt();*/
	}
	public static boolean handContains(int sp, int card) {
		if (sp==0) return USAHand.contains(card);
		if (sp==1) return JihHand.contains(card);
		return false;
	}
	public static boolean addToHand(int sp, int card) {
		if (sp==0) {
			USAHand.add(card);
		}
		if (sp==1) {
			JihHand.add(card);
		}
		return true;
	}
	public static boolean removeFromHand(int sp, int card) {
		if (sp==0&&USAHand.contains(card)) {
			USAHand.remove((Integer) card);
			return true;
		}
		else if (sp==1&&JihHand.contains(card)) {
			JihHand.remove((Integer) card);
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
		for (int c : JihHand) {
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
		if (sp==1) for (int c : JihHand) {
			if (CardList.getCard(c).getOps()==0) i++;
		}
		else for (int c : USAHand) {
			if (CardList.getCard(c).getOps()==0) i++;
		}
		return i;
	}
}

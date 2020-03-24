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
 * @author [REDACTED]
 *
 */
public class HandManager {
	/**
	 * The cards in the hand of the US player.
	 */
	public static ArrayList<Integer> USAHand = new ArrayList<Integer>();
	/**
	 * The cards in the hand of the USSR player.
	 */
	public static ArrayList<Integer> SUNHand = new ArrayList<Integer>();
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
	 * <li>{@code 009 Vietnam Revolts} - Soviets get +1 Operations point if all are used in SEA. √</li>
	 * <li>{@code 016 Warsaw Pact} - Allows the play of {@code 021 NATO}. √</li>
	 * <li>{@code 017 De Gaulle Leads France} - Disables {@code 021 NATO} for France. √</li>
	 * <li>{@code 021 NATO} - Disallows the USSR from couping, realigning, or using {@code 036 Brush War} on US-controlled countries in Europe. √</li>
	 * <li>{@code 023 Marshall Plan} - Allows the play of {@code 021 NATO}. √</li>
	 * <li>{@code 025 Containment} - The US gets one extra Operations point on every card, to a maximum of four. √</li>
	 * <li>{@code 027 Anpō Treaty} - Disallows the USSR from couping or realigning Japan. √</li>
	 * <li>{@code 035 Formosa Resolution} - Taiwan, if American-controlled, counts as a battleground. Cancel after the US plays the China Card. √</li>
	 * <li>{@code 041 Nuclear Submarines} - The US's coups no longer lower DEFCON. √</li>
	 * <li>{@code 042 Quagmire} - Traps the US: every turn they must discard a card worth 2 Ops or more and roll less than a 5 to attempt to break the trap. If this cannot be done, he must play his scoring cards instead. √</li>
	 * <li>{@code 043 SALT Negotiations} - All coups, by both sides, will have a -1 modifier. √</li>
	 * <li>{@code 044 Bear Trap} - Quagmire for the USSR. √</li>
	 * <li>{@code 050 We Will Bury You} - If active after the US plays his card, add 3 VP to the USSR if the card is not UN Intervention for the event. √</li>
	 * <li>{@code 051 Brezhnev Doctrine} - Containment for the USSR. √</li>
	 * <li>{@code 055 Willy Brandt} - Disables {@code 021 NATO} for West Germany. Cancelled by {@code 096 Tear Down This Wall}. √ </li>
	 * <li>{@code 059 Flower Power} - All war cards played for anything besides the space race (unless {@code 013 Arab-Israeli War} was disabled by {@code 065 Camp David Accords}) will grant 2 VP to the USSR. Cancelled by {@code 097 "An Evil Empire"}. √</li>
	 * <li>{@code 060 U2 Incident} - If UN Intervention is played during this turn, the USSR gains 1 VP. √</li>
	 * <li>{@code 065 Camp David Accords} - Disables {@code 013 Arab Israeli War}. √</li>
	 * <li>{@code 068 John Paul II Elected} - Allows the play of {@code 101 Solidarity}. √</li>
	 * <li>{@code 073 Shuttle Diplomacy} - Upon the next scoring of Asia or the Middle East, remove one battleground from the USSR's country count. √</li>
	 * <li>{@code 082 Iran Hostage Crisis} - Double the effect of {@code 092 Terrorism} on the US. √</li>
	 * <li>{@code 083 The Iron Lady} - Disables {@code 007 Socialist Governments}. √</li>
	 * <li>{@code 086 North Sea Oil} - Disables {@code 061 OPEC}. √</li>
	 * <li>{@code 861 North Sea Oil} - The non-constant effect that allows the eighth action round. √</li>
	 * <li>{@code 087 The Reformer} - Improves the effect of {@code 090 Glasnost}, but disallows the USSR from couping any European countries. √</li>
	 * <li>{@code 093 Iran-Contra Affair} - All US realignment rolls incur a -1 penalty for the rest of the turn. √</li>
	 * <li>{@code 094 Chernobyl} - Disallows the USSR from placing influence in a specific region for the rest of the turn. √</li>
	 * <li>{@code 096 Tear Down This Wall} - Disables {@code 055 Willy Brandt}. √</li>
	 * <li>{@code 097 "An Evil Empire"} - Disables {@code 059 Flower Power}. √</li>
	 * <li>{@code 106 NORAD} - Whenever DEFCON drops to 2 on an Action Round, place one influence in any country with US Influence as long as Canada is controlled. Cancelled by {@code 042 Quagmire}. √</li>
	 * <li>{@code 109 Yuri and Samantha} - All US coup rolls give the USSR 1 VP each for the rest of the turn. √</li>
	 * <li>{@code 110 AWACS Sale to Saudis} - Disables {@code 056 Muslim Revolution}. √</li>
	 * <li>{@code 115 Kremlin Flu} - USSR must play a scoring card or skip its turn. √</li>
	 * <li>{@code 124 Laika} - USSR spacing attempts are easier until "Man in Space". √</li>
	 * <li>{@code 126 Tsar Bomba} - Degrade DEFCON on any round the US coups for the rest of the turn. √</li>
	 * <li>{@code 128 Checkpoint C} - The US player chooses at the start of their action round to discard a USSR event (and draw a replacement); if one doesn't exist, lower DEFCON by 1. Cancelled by setting Military Operations to 0 before the action round.</li>
	 * <li>{@code 129 Indo-Soviet Treaty} - The USSR *must* take eight action rounds this turn. </li>
	 * <li>{@code 137 Red Africa} - The USSR gains 1VP during final scoring for each of the Ivory Coast, West African States, Kenya, Somalia, and Botswana that they control then. </li>
	 * <li>{@code 310 Red Scare} - The US gets -1 Operation point on every card for the rest of the turn to a minimum of 1. √</li>
	 * <li>{@code 311 Purge} - The USSR gets -1 Operation point on every card for the rest of the turn to a minimum of 1. √</li>
	 * <li>{@code 400 Turkish Missile Crisis} - For the rest of the turn, the US cannot coup-doing so loses the game by thermonuclear war. Cancelled by removing two influence from Turkey or West Germany. √</li>
	 * <li>{@code 401 Cuban Missile Crisis} - For the rest of the turn, the USSR cannot coup-doing so loses the game by thermonuclear war. Cancelled by removing two influence from Cuba. √</li>
	 * <li>{@code 490 Missile Envy} - The next US Action Round must use the card Missile Envy on either Operations or Quagmire, if possible. √</li>
	 * <li>{@code 491 Missile Envy} - The next USSR Action Round must use the card Missile Envy on either Operations or Bear Trap, if possible. √</li>
	 * <li>{@code 690 Latin American Death Squads} - All US coups in Central and South America get +1 to the roll; all USSR coups in the same area get -1 to the roll. √</li>
	 * <li>{@code 691 Latin American Death Squads} - All USSR coups in Central and South America get +1 to the roll; all US coups in the same area get -1 to the roll. √</li>
	 * <li>{@code 1001 Yalta 6} - The US goes first in all turns during the early war.
	 * <li>{@code 1002 Allied Berlin} - Disables {@code 010 Blockade}. Europe Scoring is now modified with Allied Berlin (3/6/6 instead of 3/7/V).
	 * <li>{@code 1003 VJ 6} - The US ignores DEFCON restrictions during Turn 1. DEFCON cannot drop below 2. Coups galore!
	 * <li>{@code 1004 Coalition Government} - Socialist Governments has no effect during Turns 1 and 2.
	 * <li>{@code 1005 Tory Victory} - Disables {@code 028 Suez Crisis}. Turns Socialist Governments into a Mid-War Card. 
	 * <li>{@code 1006 VJ 1} - Turns {@code 027 Anpō Treaty} into a Mid-War Card. 
	 * <li>{@code 1210/1211 Nuclear Proliferation} - At the start of the next action round, increase DEFCON by 2; the player has the option of lowering DEFCON by 1 at the end of the action round. </li>
	 * <li>{@code 1270/1271 Vasili Arkhipov} - The player holding this may, at the start of their action round, raise DEFCON by 1. </li>
	 * <li>{@code 1350/1351 People's Power} - The player holding this may, after any die roll, flip the die to its opposite face before resolving its effects. </li>
	 * </ul>
	 * Turn zero statuses:
	 * <ul>
	 * <li>{@code 100109 Vietnam Revolts} - Adds Vietnam Revolts to USSR Hand during dealing.</li>
	 * <li>{@code 100113 Arab-Israeli War} - Adds Arab-Israeli War to USSR Hand during dealing. </li>
	 * <li>{@code 100123 Marshall Plan} - Adds Marshall Plan to US Hand during dealing.</li>
	 * <li>{@code 100101 Yalta 1} - Allows the USSR to add 1 influence to any country in Europe at game start. </li>
	 * <li>{@code 100201 VE 1} - Allows the USSR to add 2 additional influence to Eastern Europe. </li>
	 * <li>{@code 100401 Israel 1} - Allows the USSR to add 2 additional influence to the Middle East during setup (no more than 1 per country).</li>
	 * <li>{@code 100506 Nationalist China} - Converts {@code 035 Formosa Resolution} to {@code 035 Nationalist China}.</li>
	 * <li>{@code 100501 Soviet China} - Removes {@code 076 Ussuri River Skirmish} from the game.</li>
	 * <li>{@code 100606 VJ 6} - Removes {@code 011 Korean War} from the game. </li>
	 */
	public static ArrayList<Integer> Effects = new ArrayList<Integer>();
	/**
	 * The status of the China Card:
	 * <br> -1 at the start of Chinese Civil War optional rule games.
	 * <br>	0 if in US hand face up
	 * <br> 1 if in USSR hand face up
	 * <br> 2 if in US hand face down
	 * <br> 3 if in USSR hand face down
	 */
	public static int China=1;
	/**
	 * The headlines for each player.
	 */
	public static int[] headline = {0,0};
	/**
	 * The player's integer, for which their headline takes precedence over their opponent's.
	 */
	public static int precedence = -1;
	/**
	 * The active card (i.e. the card in play for this action round). 
	 */
	public static int activecard = 0;
	/**
	 * The way in which the activecard is being played:
	 * <ul>
	 * 	<li>h(eadline)</li>
	 * 	<li>e(vent)</li>
	 * 	<li>o(perations)</li>
	 * 	<li>s(pace)</li>
	 * 	<li>(event) f(irst)</li>
	 * 	<li>(event) l(ast)</li>
	 * </ul>
	 */
	public static char playmode = 0;
	/**
	 * Creates a custom array of cards.
	 */
	public static void customCards (String s) {
		final ArrayList<Integer> effectsPermanent = new ArrayList<Integer>(Arrays.asList(16,17,21,23,27,55,59,65,68,82,83,86,87,96,97,110,124,137));
		final ArrayList<Integer> effectsTemporary = new ArrayList<Integer>(Arrays.asList(9,25,35,41, 42,43,44,50, 51,60,73,861, 93,0,0,94, 106,109,115,126, 128,129,310,311, 400,401,490,491, 690,691,1001,1002, 1003,1004,1005,1006, 1210,1211,1270,1271, 1350,1351,0,0));
		USAHand = new ArrayList<Integer>();
		SUNHand = new ArrayList<Integer>();
		Deck = new ArrayList<Integer>(); 
		Discard = new ArrayList<Integer>();
		Removed = new ArrayList<Integer>();
		Effects = new ArrayList<Integer>();
		for (int i=0; i<=138; i++) {
			if (i==6) {
				China = ReadWrite.undoParser(s.charAt(6))-3;
				continue;
			}
			if (ReadWrite.undoParser(s.charAt(i))==0) CardList.cardList.set(i, new Placeholder());
			if (ReadWrite.undoParser(s.charAt(i))==1) continue;
			if (ReadWrite.undoParser(s.charAt(i))==2) Deck.add(i);
			if (ReadWrite.undoParser(s.charAt(i))==3) USAHand.add(i);
			if (ReadWrite.undoParser(s.charAt(i))==4) SUNHand.add(i);
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
	
	/**
	 * Resets everything to its initial state.
	 */
	public static void reset() {
		USAHand = new ArrayList<Integer>();
		SUNHand = new ArrayList<Integer>();
		Deck = new ArrayList<Integer>();
		Discard = new ArrayList<Integer>();
		Removed = new ArrayList<Integer>();
		Effects = new ArrayList<Integer>();
		China = 1;
		headline[1] = 0;
		headline[0] = 0;
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
	 * Sets up a deck for the Late War scenario.
	 */
	public static void lateWarDeck() {
		for (Card c : CardList.cardList) {
			if ((c.getEra()<2&&c.getEra()>=0)&&!c.isRemoved()&&!c.getId().equals("006")) Deck.add(CardList.cardList.indexOf(c));
		}
		addToDeck(2); //late war cards
		Deck.add(44); //bear trap
		Deck.add(65); //camp david
		Deck.add(68); //jp2
		Deck.add(64); //panama canal
		Effects.add(27); //anpo
		Effects.add(23); //mp
		Effects.add(21); // nato
		Effects.add(16); //wp
		Effects.add(17); //degaulle
		Effects.add(59); //fp
	}
	/**
	 * Deals each player a hand of eight or nine cards, depending on the era. If the deck is empty, it reshuffles the discards.
	 */
	public static void deal() {
		Random random = new Random();
		int handsize = (GameData.getEra()<=0&&!GameData.yiyo)?8:9;
		while (handsize > SUNHand.size()||handsize > USAHand.size()) {
			if (handsize > SUNHand.size()&&!Deck.isEmpty()) {
				SUNHand.add(Deck.remove(random.nextInt(Deck.size())));
			}
			if(Deck.isEmpty()) {
				Deck.addAll(Discard);
				Discard.clear();
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
	/**
	 * Creates a MessageEmbed containing the details of the US's cards. To be sent after every turn. 
	 * @return A MessageEmbed, containing the details of the US's hand.
	 */
	public static MessageEmbed getUSAHand() {
		EmbedBuilder builder = new EmbedBuilder().setTitle("USA Hand").setColor(Color.blue);
		for (int c : USAHand) {
			builder.addField(CardList.getCard(c).toString(), CardList.getCard(c).getDescription(), false);
		}
		if (China==0) {
			builder.addField(CardList.getCard(6).toString(), CardList.getCard(6).getDescription(), false);
		}
		return builder.build(); 
	}
	/**
	 * Creates a MessageEmbed containing the details of the USSR's cards. To be sent after every turn. 
	 * @return A MessageEmbed, containing the details of the USSR's hand.
	 */
	public static MessageEmbed getSUNHand() {
		EmbedBuilder builder = new EmbedBuilder().setTitle("USSR Hand").setColor(Color.red);
		for (int c : SUNHand) {
			builder.addField(CardList.getCard(c).toString(), CardList.getCard(c).getDescription(), false);
		}
		if (China==1) {
			builder.addField(CardList.getCard(6).toString(), CardList.getCard(6).getDescription(), false);
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
		
		if (card==116&&mode!='s') {
			Log.writeToLog("First Lightning:");
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("American Nuclear Monopoly Broken!")
				.setDescription("")
				.setColor(Color.red);
			builder.changeDEFCON(-1);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (effectActive(59)&&((card==13&&!effectActive(65))||card==11||card==24||card==36||card==102)&&sp==0&&mode!='s') {
			CardEmbedBuilder builder = new CardEmbedBuilder();
			Log.writeToLog("Flower Power:");
			builder.setTitle("Student Strikes Rock American Universities")
				.setDescription("Protests against the " + CardList.getCard(card).getName() + " intensify after Kent State Massacre")
				.setFooter("\"I think that we're up against the strongest, well-trained, "
						+ "militant, revolutionary group that has ever assembled in America.\" \n"
						+ "- Jim Rhodes, 1970", Launcher.url("people/rhodes.png"))
				.setColor(Color.red);
			builder.changeVP(-2);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (HandManager.effectActive(115)&&sp==1&&!GameData.isHeadlinePhase()) {
			GameData.txtchnl.sendMessage(new CardEmbedBuilder()
					.setTitle("Kremlin Flu")
					.setDescription("The USSR has played " + CardList.getCard(card) + ".")
					.build())
			.complete();
			HandManager.removeEffect(115);
			return;
		}
		//super complicated area, will nitgrit later
		if (mode=='h') {
			Log.writeToLog((sp==0?"US":"SU")+" h");
			removeFromHand(sp, card);
			playmode = 'h';
			headline[sp]=card;
			
			if (headline[(sp+1)%2]!=0) {
				Log.writeToLog("US plays " + CardList.getCard(headline[0]).getName() + " as headline");
				Log.writeToLog("SU plays " + CardList.getCard(headline[1]).getName() + " as headline");
				if (headline[0]==103) {
					precedence = 0;
					activecard = headline[0];
				}
				else if (CardList.getCard(headline[0]).getOps()>=CardList.getCard(headline[1]).getOps()) {
					precedence = 0;
					activecard = headline[0];
				}
				else {
					activecard = headline[1];
					precedence = 1;
				}
				TimeCommand.cardPlayed = true;
				TimeCommand.hl1 = false;
				TimeCommand.hl2 = false;
				GameData.txtchnl.sendMessage(CardList.getCard(card).toEmbed(sp).setAuthor("Turn " + GameData.getTurn() + " " + (GameData.getAR()==0?"Headline":("AR " + ((GameData.getAR() + 1)/2) + (GameData.phasing()==0?" US":" USSR")))).build()).complete();
				if (!(GameData.hasAbility(sp,4))) GameData.txtchnl.sendMessage(CardList.getCard(headline[(sp+1)%2]).toEmbed((sp+1)%2).setAuthor("Turn " + GameData.getTurn() + " " + (GameData.getAR()==0?"Headline":("AR " + ((GameData.getAR() + 1)/2) + (GameData.phasing()==0?" US":" USSR")))).build()).complete();
			}
			else if (GameData.hasAbility((sp+1)%2,4)) {
				GameData.txtchnl.sendMessage(CardList.getCard(card).toEmbed(sp).setAuthor("Turn " + GameData.getTurn() + " " + (GameData.getAR()==0?"Headline":("AR " + ((GameData.getAR() + 1)/2) + (GameData.phasing()==0?" US":" USSR")))).build()).complete();
			}
			else return;
		}
		else {
			GameData.txtchnl.sendMessage(CardList.getCard(card).toEmbed(sp).setAuthor("Turn " + GameData.getTurn() + " " + (GameData.getAR()==0?"Headline":("AR " + ((GameData.getAR() + 1)/2) + (GameData.phasing()==0?" US":" USSR"))), InfoCommand.url()).build()).complete();
		}
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
		if (sp==0) return USAHand.contains(card);
		if (sp==1) return SUNHand.contains(card);
		return false;
	}
	public static boolean addToHand(int sp, int card) {
		if (sp==0) {
			USAHand.add(card);
		}
		if (sp==1) {
			SUNHand.add(card);
		}
		return true;
	}
	public static boolean removeFromHand(int sp, int card) {
		if (sp==0&&USAHand.contains(card)) {
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
	 * 
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
		for (int c : SUNHand) {
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
	
	public static int countScoring(int sp) {
		int i=0;
		if (sp==1) for (int c : SUNHand) {
			if (CardList.getCard(c).getOps()==0) i++;
		}
		else for (int c : USAHand) {
			if (CardList.getCard(c).getOps()==0) i++;
		}
		return i;
	}
}

package cards;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import cards.CardList;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
/**
 * Manages everything related to the cards used in the game - which cards are in the Deck, which are Discarded, which are in the players' hands, and which have ongoing effects.
 * @author [REDACTED]
 *
 */
public class HandManager {
	/**
	 * The cards in the hand of the US player.
	 */
	public static ArrayList<Integer> USAHand;
	/**
	 * The cards in the hand of the USSR player.
	 */
	public static ArrayList<Integer> SUNHand;
	/**
	 * The cards in the deck.
	 */
	public static ArrayList<Integer> Deck;
	/**
	 * The cards in the discard pile.
	 */
	public static ArrayList<Integer> Discard;
	/**
	 * The cards with starred events that have been triggered.
	 */
	public static ArrayList<Integer> Removed;
	/**
	 * The cards with ongoing effects.
	 * <br>
	 * Valid cards to put here:
	 * <ul>
	 * <li>{@code 009 Vietnam Revolts} - Soviets get +1 Operations point if all are used in SEA. √</li>
	 * <li>{@code 016 Warsaw Pact} - Allows the play of {@code 021 NATO}.</li>
	 * <li>{@code 017 De Gaulle Leads France} - Disables {@code 021 NATO} for France.</li>
	 * <li>{@code 021 NATO} - Disallows the USSR from couping, realigning, or using {@code 036 Brush War} on US-controlled countries in Europe.</li>
	 * <li>{@code 023 Marshall Plan} - Allows the play of {@code 021 NATO}.</li>
	 * <li>{@code 025 Containment} - The US gets one extra Operations point on every card, to a maximum of four.</li>
	 * <li>{@code 027 Anpō Treaty} - Disallows the USSR from couping or realigning Japan.</li>
	 * <li>{@code 035 Formosa Resolution} - Taiwan, if American-controlled, counts as a battleground. Cancel after the US plays the China Card.</li>
	 * <li>{@code 041 Nuclear Submarines} - The US's coups no longer lower DEFCON.</li>
	 * <li>{@code 042 Quagmire} - Traps the US: every turn they must discard a card worth 2 Ops or more and roll less than a 5 to attempt to break the trap. If this cannot be done, he must play his scoring cards instead.</li>
	 * <li>{@code 043 SALT Negotiations} - All coups, by both sides, will have a -1 modifier. √</li>
	 * <li>{@code 044 Bear Trap} - Quagmire for the USSR.</li>
	 * <li>{@code 050 We Will Bury You} - If active after the US plays his card, add 3 VP to the USSR if the card is not UN Intervention for the event. √</li>
	 * <li>{@code 051 Brezhnev Doctrine} - Containment for the USSR.</li>
	 * <li>{@code 055 Willy Brandt} - Disables {@code 021 NATO} for West Germany. Cancelled by {@code 096 Tear Down This Wall}. - </li>
	 * <li>{@code 059 Flower Power} - All war cards played for anything besides the space race (unless {@code 013 Arab-Israeli War} was disabled by {@code 065 Camp David Accords} will grant 2 VP to the USSR. Cancelled by {@code 097 "An Evil Empire"}. - </li>
	 * <li>{@code 060 U2 Incident} - If UN Intervention is played during this turn, the USSR gains 1 VP. √</li>
	 * <li>{@code 065 Camp David Accords} - Disables {@code 013 Arab Israeli War}.</li>
	 * <li>{@code 068 John Paul II Elected} - Allows the play of {@code 100 Solidarity}.</li>
	 * <li>{@code 073 Shuttle Diplomacy} - Upon the next scoring of Asia or the Middle East, remove one battleground from the USSR's country count.</li>
	 * <li>{@code 082 Iran Hostage Crisis} - Double the effect of {@code 092 Terrorism} on the US.</li>
	 * <li>{@code 083 The Iron Lady} - Disables {@code 007 Socialist Governments}.</li>
	 * <li>{@code 086 North Sea Oil} - Disables {@code 061 OPEC}.</li>
	 * <li>{@code 087 The Reformer} - Improves the effect of {@code 090 Glasnost}, but disallows the USSR from couping any European countries.</li>
	 * <li>{@code 093 Iran-Contra Affair} - All US realignment rolls incur a -1 penalty for the rest of the turn.</li>
	 * <li>{@code 094 Chernobyl} - Disallows the USSR from placing influence in a specific region for the rest of the turn.</li>
	 * <li>{@code 096 Tear Down This Wall} - Disables {@code 055 Willy Brandt}.</li>
	 * <li>{@code 097 "An Evil Empire"} - Disables {@code 059 Flower Power}.</li>
	 * <li>{@code 106 NORAD} - Whenever DEFCON drops to 2 on an Action Round, place one influence in any country with US Influence as long as Canada is controlled. Cancelled by {@code 042 Quagmire}.</li>
	 * <li>{@code 109 Yuri and Samantha} - All US coup rolls give the USSR 1 VP each for the rest of the turn. </li>
	 * <li>{@code 110 AWACS Sale to Saudis} - Disables {@code 056 Muslim Revolution}.</li>
	 * <li>{@code 310 Red Scare} - The US gets -1 Operation point on every card for the rest of the turn to a minimum of 1.</li>
	 * <li>{@code 311 Purge} - The USSR gets -1 Operation point on every card for the rest of the turn to a minimum of 1.</li>
	 * <li>{@code 400 Turkish Missile Crisis} - For the rest of the turn, the US cannot coup-doing so loses the game by thermonuclear war. Cancelled by removing two influence from Turkey or West Germany.</li>
	 * <li>{@code 401 Cuban Missile Crisis} - For the rest of the turn, the USSR cannot coup-doing so loses the game by thermonuclear war. Cancelled by removing two influence from Cuba.</li>
	 * <li>{@code 690 Latin American Death Squads} - All US coups in Central and South America get +1 to the roll; all USSR coups in the same area get -1 to the roll.</li>
	 * <li>{@code 691 Latin American Death Squads} - All USSR coups in Central and South America get +1 to the roll; all US coups in the same area get -1 to the roll.</li>
	 * </ul>
	 */
	public static ArrayList<Integer> Effects;
	/**
	 * The status of the China Card:
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
	 * Resets everything to its initial state.
	 */
	public static void reset() {
		USAHand.clear();
		SUNHand.clear();
		Deck.clear();
		Discard.clear();
		Removed.clear();
		Effects.clear();
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
	 * Deals each player a hand of eight or nine cards, depending on the era. If the deck is empty, it reshuffles the discards.
	 */
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
	/**
	 * Creates a MessageEmbed containing the details of the US's cards. To be sent after every turn. 
	 * @return A MessageEmbed, containing the details of the US's hand.
	 */
	public static MessageEmbed getUSAHand() {
		EmbedBuilder builder = new EmbedBuilder().setTitle("USA Hand");
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
		EmbedBuilder builder = new EmbedBuilder().setTitle("USSR Hand");
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
		if (Effects.contains(50)&&sp==0&&mode!='h') {
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("We Will Bury You...");
			if(card!=32 || mode != 'e') {
				builder.setDescription("...and the UN sits idle.")
					.setFooter("If you don't like us, don't accept our invitations, and don't invite us to come to see you. Whether you like it or not, history is on our side.\" \n"
							+ "- Nikita Khrushchev, 1956", "images/countries/su")
					.setColor(Color.red);
				builder.changeVP(-3);
			}
			else {
				builder.setDescription("UN INTERVENTION!")
					.setColor(Color.blue)
					.setFooter("\"We are happy with our way of life. We recognize its shortcomings and are always trying to improve it. But if challenged, we shall fight to the death to preserve it.\"\n"
							+ "- Norris Poulson, 1959", "images/countries/us");
			}
			Effects.remove(Effects.indexOf(50));
			GameData.txtchnl.sendMessage(builder.build());
		}
		
		if (Effects.contains(60)&&card==32) {
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("U2 Incident")
				.setDescription("UN INTERVENTION!")
				.setFooter("\"I must tell you a secret. When I made my first report I deliberately did not say that the pilot was alive and well ... and now just look how many silly things the Americans have said.\n" + 
						"\n- Nikita Khrushchev, 1960\"","images/countries/su")
				.setColor(Color.red);
			builder.changeVP(-1);
			Effects.remove(Effects.indexOf(60));
			GameData.txtchnl.sendMessage(builder.build());
		}
		
		if (Effects.contains(59)&&(card==13||card==11||card==24||card==36||card==102)) {
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Flower Power")
				.setDescription("Anti-war protests erupt against the " + CardList.getCard(card).getName() + "!")
				.setFooter("\"I think that we're up against the strongest, well-trained, militant, revolutionary group that has ever assembled in America.\" \n"
						+ "- Jim Rhodes, 1970", "images/countries/us.png")
				.setColor(Color.red);
			builder.changeVP(-1);
			Effects.remove(Effects.indexOf(60));
			GameData.txtchnl.sendMessage(builder.build());
		}
		
		if (card==6&&China==sp) {
			China = (China+1)%2+2;
			activecard=6;
			playmode = 'o';
			return;
		}
		if (sp==0&&USAHand.contains(card)) {
			USAHand.remove(card);
		}
		if (sp==1&&SUNHand.contains(card)) {
			SUNHand.remove(card);
		}
		//super complicated area, will nitgrit later
		if (mode=='h') {
			if (CardList.getCard(card).isRemoved()) {
				Removed.add(card);
			}
			else {
				Discard.add(card);
			}
			playmode = 'h';
			headline[sp]=card;
			
			if (headline[(sp+1)%2]!=0) {
				if (CardList.getCard(headline[0]).getOps()>=CardList.getCard(headline[1]).getOps()) precedence = 0;
				else precedence = 1;
			}
		}
		if (mode=='e') {
			if (CardList.getCard(card).getAssociation()==(GameData.getAR()+1)%2) {
				if (CardList.getCard(card).isRemoved()) {
					Removed.add(card);
				}
				else {
					Discard.add(card);
				}
				playmode = 'f';
			}
			else {
				if (CardList.getCard(card).isRemoved()) {
					Removed.add(card);
				}
				else {
					Discard.add(card);
				}
				playmode = 'e';
			}
			activecard = card;
			
		}
		if (mode=='o') {
			if (CardList.getCard(card).getAssociation()==(GameData.getAR()+1)%2) {
				if (CardList.getCard(card).isRemoved()) {
					Removed.add(card);
				}
				else {
					Discard.add(card);
				}
				playmode = 'l';
			}
			else {
				Discard.add(card);
				playmode = 'o'; //ops only
			}
			activecard = card;
		}
		if (mode=='s') {
			Discard.add(card);
			playmode = 's';
			activecard = card;
		}
		
	}
	/**
	 * Discards the card in question.
	 * @param sp is the integer representing the player in possession of the card.
	 * @param card is the card to be discarded. 
	 */
	public static void discard(int sp, int card) {
		if (sp==0&&USAHand.contains(card)) {
			USAHand.remove(card);
		}
		if (sp==1&&SUNHand.contains(card)) {
			SUNHand.remove(card);
		}
		Discard.add(card);
	}
	/**
	 * Obtains the card requested from the discard. 
	 * @param sp is the integer representing the player requesting the card. 
	 * @param card is the card to be reclaimed. 
	 */
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
	/**
	 * Transfers the card in question to the other player's hand.
	 * @param source is the integer representing the player in possession of the card.
	 * @param card is the card in question. 
	 */
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
	 */
	public static void removeEffect(int card) {
		if (Effects.contains(card)) Effects.remove(Effects.indexOf(card));
	}
	/**
	 * Checks the hands for scoring cards.
	 * @return 0 if neither hand contains a scoring card;
	 * <br> 1 if the US's hand contains a scoring card;
	 * <br> 2 if the USSR's hand contains a scoring card;
	 * <br> 3 if both hands contain a scoring card.
	 */
	public static int checkScoring() {
		int ret=0;
		for (int c : SUNHand) {
			if (CardList.getCard(c).getOps()==0) ret += 2; 
			break;
		}
		for (int c : USAHand) {
			if (CardList.getCard(c).getOps()==0) ret += 1;
			break;
		}
		return ret;
	}
}

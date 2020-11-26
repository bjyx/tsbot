package game;

import java.awt.Color;

import cards.HandManager;
import cards.Operations;
import events.CardEmbedBuilder;
import events.Decision;
import events.Samizdat;
import logging.Log;
import main.Common;
import main.Launcher;
import map.Country;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import powerstruggle.PowerStruggle;
import powerstruggle.Scoring;
/**
 * Deals with the variables in the game, including the score, the space race, and time.
 * @author adalbert
 *
 */
public class GameData {
	/**
	 * The location where the majority of the game will be taking place. All public game updates will be placed here.
	 */
	public static TextChannel txtchnl;
	/**
	 * The location where all information privy to the US will be placed. This includes the following:
	 * <ul>
	 * <li> The US's hand
	 * <li> Any intel given by this one card
	 * </ul>
	 * <br>
	 * It is also recommended that the US write all commands in here to avoid betraying information to the Jihadist.
	 */
	public static TextChannel txtusa = null;
	/**
	 * The location where all information privy to the Jihadist will be placed. This includes the following:
	 * <ul>
	 * <li> The Jihadist's hand
	 * </ul>
	 * <br>
	 * It is also recommended that the Jihadist write all commands in here to avoid betraying information to the US.
	 */
	public static TextChannel txtjih = null;
	/**
	 * The role allowing access to {@link #txtusa}.
	 */
	public static Role roleusa = null;
	/**
	 * The role allowing access to {@link #txtjih}.
	 */
	public static Role rolejih = null;
	
	/**
	 * Whether the game has started.
	 */
	private static boolean started = false;
	/**
	 * Whether the game has ended.
	 */
	private static boolean ended = false;
	/**
	 * Whether the first optional rule, Zakat/Strong Horse, is active.
	 */
	public static boolean zakat = false;
	/**
	 * Whether the second optional rule, Heroic Alerts, is active.
	 */
	public static boolean hero = false;
	/**
	 * Whether the third optional rule, Variable Difficulty, is active.
	 */
	public static boolean vardiff = false;
	/**
	 * The current scenario, which affects the implementation of certain rules.
	 */
	public static int scenario = 0;
	/**
	 * The method of playing the game, which definitely affects implementation. (0: solitaire as US, 1: solitaire as Jihadist, 2: 2-player
	 */
	public static int player = 2;
	/**
	 * A boolean array of difficulty settings, for use with variable difficulty. 
	 */
	public static boolean[] diff = {false, false, false, false, false};
	/**
	 * The reshuffle count, an inbuilt timer.
	 */
	private static int reshuffle = 0;
	/**
	 * The current action round (-> infinity), as a time tracker, esp. for some tracked events.
	 */
	private static int ar = 0;
	/**
	 * The US's prestige.
	 */
	private static int prestige = 8;
	/**
	 * Jihadist funding.
	 */
	private static int fund = 9;
	/**
	 * The reserves from each side - an integer from 0 to 2 inclusive.
	 */
	private static int[] reserves = {0,0};
	/**
	 * An Operations object, denoting any operations that need to be performed as a result of any action. 
	 */
	public static Operations ops = null;
	/**
	 * A Decision object, denoting any decision required to be made.
	 */
	public static Decision dec = null;
	/**
	 * You never know when you'll need a synchronized object.
	 */
	public static Object sync;
	/**
	 * A temporary storage space for the result of a die; blame EDSA.
	 */
	//public static int diestore;
	/**
	 * Sets {@link #started} to true.
	 */
	public static void startGame() {
		started = true;
	}
	
	/**
	 * Creates the conditions dictated by an eight-character string. AKA: no.
	 */
	public static boolean startCustom(int s) {
		switch (s) {
		case 0:
			//TODO ...
		default:
			break;
		}
	}
	/**
	 * @return {@link #started}
	 */
	public static boolean hasGameStarted() {
		return started;
	}
	
	/**
	 * Ends the game, declaring a victor (or lack thereof) and the reason behind their victory.
	 * @param victor is the superpower that won this game - 0 for the US, 1 for the USSR, -1 for a draw.
	 * @param cause is how said superpower won that game
	 */
	public static void endGame(int victor, int cause) {
		ended = true;
		if (victor==-1) {
			txtchnl.sendMessage(new EmbedBuilder()
					.setTitle("GAME OVER - DRAW!")
					.setImage(Launcher.url("victory/draw.png"))
					.setColor(Color.gray)
					.build()).complete();
			Log.writeToLog("Draw Game");
			return;
		}
		
		EmbedBuilder builder = new EmbedBuilder()
				.setTitle("GAME OVER - The winner is " + PlayerList.getArray().get(victor).getName() + "!")
				.setDescription("Cause: " + getCause(cause))
				.setColor(Common.spColor(victor))
				.setImage(Launcher.url("victory/" + (victor==0?"us":"ji") + cause + ".png"));
		txtchnl.sendMessage(builder.build()).complete();
		Log.writeToLog("Game Won By " + (victor==0?"US":"SU"));
	}
	
	/**
	 * An aid function for {@link #endGame(int, int)}.
	 * @param cause is as enumerated in {@link #endGame(int, int)}.
	 * @return a String, detailing the cause of the game's end.
	 */
	private static String getCause(int cause) {
		if (cause==0) return "Resource Control."; //VP == 20 OR VP > the other after turn 10
		if (cause==1) return "Favorable Islamic World.";
		if (cause==2) return "All Jihadists eliminated!"; //yep
		if (cause==3) return "Another dark day."; //WMD
		return null;
	}

	/**
	 * @return {@link #ended}
	 */
	public static boolean hasGameEnded() {
		return ended;
	}
	
	/**
	 * Resets everything in {@link #GameData()} to its initial state.
	 */
	public static void reset() {
		txtchnl.sendMessage(":hourglass: Take us back.").complete();
		started = false;
		ended = false;
		//TODO reset all values
		HandManager.reset();
		txtchnl = null;
		Country.count = 0;
	}
	
	/**
	 * Moves the turn forward, performing all functions necessary at the end of the turn.
	 */
	public static void advanceTurn() {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("End of Turn Summary");
		checkScore(false, false);
		int scoring = HandManager.checkScoring();
		if (scoring!=0) {
			endGame((scoring/2+1)%2, 3); //1 -> 1, 2 -> 0, 3 -> 0
		} //stage 4
		if (HandManager.effectActive(104)) { //stage 5
			dec = new Decision(0,104);
			HandManager.addEffect(1040);
			builder.addField("New Year's Eve Party!", "The Democrat may select a Communist country to hold a Power Struggle in.", false);
			txtchnl.sendMessage(builder.build()).complete();
			return;
			//TODO you're gonna have to code in this later
		}
		if (turn==10) { // stage 7
			for (int i=0; i<6; i++) {
				Scoring.score(i);
				if (PowerStruggle.retained[i]!=-1) builder.changeVP(-4);
			}
			if (HandManager.effectActive(971)) {
				builder.addField("The Ceaușescus escape Romania.", "", false);
				builder.changeVP(-2);
			}
			checkScore(true, false);
			return;
		}
		turn++; // stage 6
		ar = 1; 
		hasT2[0] = false; 
		hasT2[1] = false;
		//TODO effect reminders
		HandManager.removeEffect(15); //Honecker
		if (HandManager.removeEffect(13)) builder.addField("","The Stasi have moved you off the watch-list. The Communist no longer has advance knowledge of the Democrat's plays.",false);	//Stasi
		if (HandManager.removeEffect(50)) builder.addField("", "The Democrat loses +1 Operations bonus.", false);	//Sinatra
		if (HandManager.removeEffect(58)) builder.addField("Hungarian Travel Banned","The Democrat loses bonus to Operations in East Germany.",false);	//A-H Border
		if (HandManager.removeEffect(63)) builder.addField("","The Democrat will now pay normal cost to place influence in Communist East German spaces.",false);	//Genscher
		if (HandManager.removeEffect(25)) builder.addField("","The Communist loses +1 Operations bonus.",false);;	//Perestroika
		if (HandManager.removeEffect(74)) builder.addField("","The Democrat loses bonus to support checks in Eastern Europe.",false);	//FRG
		if (HandManager.removeEffect(59)) builder.addField("","US rolls on support checks in East Germany are no longer penalized.",false);	//Grenz
		if (HandManager.removeEffect(49)) builder.addField("Debt Burden Lifted","USSR support checks are no longer restricted by region.",false);	//Debt Burden
		if (HandManager.removeEffect(77)) {
			builder.addField("","The card set aside by the Democrat has returned to the hand.",false);	//Samizdat
			HandManager.addToHand(0, Samizdat.card);
		}
		if (HandManager.removeEffect(101)) builder.addField("","US rolls on support checks in Romania are no longer penalized.",false);	//Elena
		if (HandManager.removeEffect(80)) builder.addField("","Democrat malus to Operations removed.",false);	//Prudence
		if (HandManager.removeEffect(81)) builder.addField("","Communist malus to Operations removed.",false);
		if (HandManager.removeEffect(1000)) builder.addField("Crowd Mentality","Communist Support Checks in Democratic spaces no longer have a -1 malus.",false);	//Stand Fast
		if (HandManager.removeEffect(1001)) builder.addField("Crowd Mentality","Democratic Support Checks in Communist spaces no longer have a -1 malus.",false);
		if (turn==4) {
			Log.writeToLog("MW Cards Added.");
			HandManager.addToDeck(1); //Rule 4.4
			builder.addField("Mid War","Mid War cards now available for use. You will now get nine cards per turn instead of eight, and there will be eight action rounds per turn.",false);
		}
		if (turn==8) {
			Log.writeToLog("LW Cards Added.");
			HandManager.addToDeck(2); //Rule 4.4
			builder.addField("Late War","Late War cards now available for use.",false);
		}
		if (hasAbility(0, 7)||hasAbility(1,7)) Operations.seven %= 2;
		if (hasAbility(0, 8)||hasAbility(1,8)) Operations.eight %= 2;
		txtchnl.sendMessage(builder.build()).complete();
	}
	
	/**
	 * Performs all functions necessary at the start of the turn.
	 */
	public static void startTurn() {
		txtchnl.sendMessage(new CardEmbedBuilder().setTitle("Start of Turn " + getTurn()).addField("Cards have been dealt.", "", false).build()).complete();
		HandManager.deal(); // stage 1; also reshuffles automatically
		Log.writeToLog("-+-+- Turn "+getTurn()+" -+-+-");
	}
	
	/**
	 * @return {@link #turn}
	 */
	public static int getTurn() {
		return turn;
	}
	/**
	 * The era in which the current turn resides.
	 * @return <ul>
	 * <li> -1 if setting up.
	 * <li> 0 if Early War.
	 * <li> 1 if Mid War.
	 * <li> 2 if Late War.
	 * </ul>
	 */
	public static int getEra() {
		if (turn==0) return -1;
		if (turn<4) return 0;
		if (turn>7) return 2;
		return 1;
	}
	/**
	 * Advances the action round.
	 */
	public static void advanceTime() {
		if (HandManager.Effects.contains(15)&&ar==14) {
			HandManager.removeEffect(15);
			ar = 15; 
			return; //Honecker's effect, end of AR7
		}
		if (ar>=14) { //AR7 or later
			advanceTurn();
			return;
		}
		ar++; //if you don't advance the turn or have anything to do with extra action rounds
		HandManager.activecard = 0;
		Log.writeToLog("--- New AR ---");
	}
	
	public static int arsLeft() {
		if (HandManager.Effects.contains(15)) {
			return 8-(getAR()-1)/2;
		}
		return 7-(getAR()-1)/2;
	}
	
	
	/**
	 * @return {@link #ar}
	 */
	public static int getAR() {
		return ar;
	}
	/**
	 * Determines the superpower currently executing his actions.
	 * @return 0 for the US, 1 for the USSR.
	 */
	public static int phasing() {
		return ar%2;
	}
	/**
	 * Determines the superpower not currently executing his actions.
	 * @return 0 for the US, 1 for the USSR.
	 */
	public static int opponent() {
		return (phasing()+1)%2;
	}
	/**
	 * Decrement {@link #ussr}, and changes the VP accordingly. The Baltic Chain is a linear progression, so this is possible.
	 */
	public static void setStab() {
		ussr--;
		Log.writeToLog("USSR -1 Stability");
		changeScore(ussrVP[ussr]);
	}
	/**
	 * 
	 * @return {@link #ussr}
	 */
	public static int getStab() {
		return ussr;
	}
	/**
	 * Adds 1 to a party's progress on the T2 Track.
	 * @param sp is 0 if US, 1 if USSR.
	 */
	public static void addT2(int sp) {
		tsquare[sp]++;
		Log.writeToLog("T2 " + (sp==0?"D":"C") + " + 1");
	}
	/**
	 * 
	 * @param sp is 0 if US, 1 if USSR.
	 * @return {@link #tsquare}[sp].
	 */
	public static int getT2(int sp) {
		return tsquare[sp];
	}
	/**
	 * Changes {@link #score}.
	 * @param amt is the amount to change {@link #score} by.
	 */
	public static void changeScore(int amt) {
		if (HandManager.effectActive(99)&&GameData.phasing()==0) {
			Log.writeToLog("Ligachev: ");
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Ligachev Vindicated")
					.setColor(Color.red);
			HandManager.removeEffect(99); //this has to come before because otherwise it's an infinite loop
			builder.changeVP(-3);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			GameData.checkScore(false, false);
		}
		score += amt;
		Log.writeToLog("VP" + (amt>0?"+":"") + amt);
	}
	/**
	 * 
	 * @return {@link #score}
	 */
	public static int getScore() {
		return score;
	}
	/**
	 * Checks the score for victory conditions, and ends the game accordingly.
	 * @param f is true if triggered under final scoring or New Years' Eve.
	 * @param w is true only if triggered under New Years' Eve.
	 */
	public static void checkScore(boolean f, boolean w) {
		if (score<=-20) {
			endGame(1, 0);
			return;
		}
		if (score>=20) {
			endGame(0, 0);
			return;
		}
		int x = 0;
		if (!f) return;
		if (w) x=4;
		if (score<0) endGame(1,x);
		else if (score>0) endGame(0,x);
		else endGame(-1,x);
	}
	/**
	 * Marks the superpower as having sent a card to China for that turn.
	 * @param sp is 0 if US, 1 if USSR.
	 */
	public static void toT2(int sp) {
		hasT2[sp] = true;
	}
	/**
	 * Determines the superpower currently ahead on the space race.
	 * @return -1 if tied, 0 if US, 1 if USSR.
	 */
	public static int aheadInSpace() {
		if (tsquare[0]<tsquare[1]) return 1;
		if (tsquare[1]<tsquare[0]) return 0;
		return -1;
	}
	/**
	 * Determines whether a superpower has reached its allotted amount of cards able to be sent on the space race.
	 * @param sp is 0 if US, 1 if USSR.
	 * @return true iff the superpower in question has used all opportunities for spacing. 
	 */
	public static boolean hasT2(int sp) {
		return hasT2[sp];
	}
	/**
	 * Determines whether a superpower has an ability granted to it by the space race.
	 * @param sp is 0 if US, 1 if USSR.
	 * @param rank is the rank in question.
	 * @param alt asks whether the alternate space track is active.
	 * @return true if {@link #space}[sp] >= rank and {@link #space}[(sp+1)%2] < rank.
	 */
	public static boolean hasAbility(int sp, int rank) {
		return (tsquare[sp]>=rank) && (tsquare[(sp+1)%2]<rank);
	}
}

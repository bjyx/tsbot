package game;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import commands.TimeCommand;
import events.CardEmbedBuilder;
import events.Decision;
import logging.Log;
import main.Launcher;
import map.Country;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import powerstruggle.PowerStruggle;
import readwrite.ReadWrite;
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
	 * The location where all information privy to the Democrat will be placed. This includes the following:
	 * <ul>
	 * <li> The Democrat's hand
	 * </ul>
	 * <br>
	 * It is also recommended that the Democrat write all commands in here to avoid betraying information to the Communist.
	 */
	public static TextChannel txtdem = null;
	/**
	 * The location where all information privy to the Communist will be placed. This includes the following:
	 * <ul>
	 * <li> The Communist's hand
	 * </ul>
	 * <br>
	 * It is also recommended that the Communist write all commands in here to avoid betraying information to the Democrat.
	 */
	public static TextChannel txtcom = null;
	/**
	 * The role allowing access to {@link #txtdem}.
	 */
	public static Role roledem = null;
	/**
	 * The role allowing access to {@link #txtcom}.
	 */
	public static Role rolecom = null;
	
	/**
	 * Whether the game has started.
	 */
	private static boolean started = false;
	/**
	 * Whether the game has ended.
	 */
	private static boolean ended = false;
	/**
	 * Whether optional cards are included in this game.
	 */
	//public static boolean optional = true;
	/**
	 * The current turn, from 0 (during the setup phase) to 10. 
	 */
	private static int turn = 0;
	/**
	 * The current action round. Odd for Communists, even for Democrats.
	 */
	private static int ar = 1;
	/**
	 * The USSR's stability. 4 is Sajudis, 3 is Baltic Way, 2 is Independence, 1 is the Coup. Dropping this to 1 is not the end of the world.
	 */
	private static int ussr = 5;
	/**
	 * The VP granted on each decrement of the ussr's stability.
	 */
	private static final int[] ussrVP = {0, -1, 5, 3, 1};
	/**
	 * The military ops attained by each side - an integer from 0 to 5 inclusive.
	 */
	//private static int[] milops = {0,0};
	/**
	 * Each side's progress on the track on a certain square where nothing happened - an integer from 0 to 8 inclusive. This is probably why it will/may be banned in China.
	 */
	private static int[] tsquare = {0,0};
	/**
	 * The current status of the Victory Points marker - a positive number for the US and a negative one for the USSR.
	 */
	private static int score = 0;
	/**
	 * Whetehr a given country has sent a card to China that turn.
	 */
	private static boolean[] hasT2 = {false, false};
	/**
	 * An Operations object, denoting any operations that need to be performed as a result of any action. 
	 */
	public static Operations ops = null;
	/**
	 * A Decision object, denoting any decision required to be made.
	 */
	public static Decision dec = null;
	/**
	 * A PowerStruggle object, denoting the current power struggle occurring.
	 */
	public static PowerStruggle ps = null;
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
	 * Creates the conditions dictated by an eight-character string.
	 */
	/*public static boolean startCustom(String s) {
		for (int i=0; i<8; i++) {
			int x = ReadWrite.undoParser(s.charAt(i));
			if (i==0) {
				if (x<=0||x>=40) return false;
				score = x - 20;
			}
			if (i==1) {
				if (x>5) return false;
				if (x<=1) return false;
				ussr = x;
			}
			if (i==2) {
				if (x>10) return false;
				if (x<1) return false;
				turn = x;
			}
			if (i==3) {
				if (x>16) return false;
				if (x<0) return false;
				ar = x;
			}
			if (i==4) {
				if (x>5) return false;
				if (x<0) return false;
				milops[0] = x;
			}
			if (i==5) {
				if (x>5) return false;
				if (x<0) return false;
				milops[1] = x;
			}
			if (i==6) {
				if (x>8) return false;
				if (x<0) return false;
				space[0] = x;
			}
			if (i==7) {
				if (x>8) return false;
				if (x<0) return false;
				space[1] = x;
			}
		}
		return true;
	}*/
	/**
	 * @return {@link #started}
	 */
	public static boolean hasGameStarted() {
		return started;
	}
	
	/**
	 * Ends the game, declaring a victor (or lack thereof) and the reason behind their victory.
	 * @param victor is the superpower that won this game - 0 for the US, 1 for the USSR, -1 for a draw.
	 * @param cause is how said superpower won that game: <ul>
	 * <li> 0: Win by autovictory/final scoring
	 * <li> 1: Win by the other player holding a scoring card
	 * <li> 2: Win by New Years' Eve Party
	 * </ul>
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
				.setTitle("GAME OVER - The winner is " + MapManager.get(victor==0?84:85) + PlayerList.getArray().get(victor).getName() + "!")
				.setDescription("Cause: " + getCause(cause))
				.setColor(victor==0?Color.blue:Color.red)
				.setImage(Launcher.url("victory/" + (victor==0?"us":"su") + cause + ".png"));
		txtchnl.sendMessage(builder.build()).complete();
		Log.writeToLog("Game Won By " + (victor==0?"US":"SU"));
	}
	
	/**
	 * An aid function for {@link #endGame(int, int)}.
	 * @param cause is as enumerated in {@link #endGame(int, int)}.
	 * @return a String, detailing the cause of the game's end.
	 */
	private static String getCause(int cause) {
		if (cause==0) return "Ideological Domination."; //VP == 20 OR VP > the other after turn 10
		if (cause==1) return "Withheld a scoring card. Oops.";
		if (cause==2) return "Party at the Gate!"; //wargamed
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
		txtchnl.sendMessage(":hourglass: Back to January!").complete();
		started = false;
		ended = false;
		turn = 0;
		ar = 1;
		ussr = 5;
		tsquare[0] = 0;
		tsquare[1] = 0;
		hasT2[0] = false;
		hasT2[1] = false;
		HandManager.reset();
		txtchnl = null;
		Country.count = 0;
	}
	
	/**
	 * Sets {@link #turn} to 1.
	 */
	public static void endSetupPhase() {
		turn = 1;
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
			//TODO later
		}
		if (turn==10) { // stage 7
			//TODO later
		}
		turn++; // stage 6
		ar = 1; 
		hasT2[0] = false; 
		hasT2[1] = false;
		//TODO effect reminders
		/*if (HandManager.removeEffect(9)) builder.addField("Geneva Accords","Vietnam now independent. USSR loses +1 Operations bonus in Southeast Asia.",false);	//Vietnam
		if (HandManager.removeEffect(25)) builder.addField("Ineffective Policy", "New administration calls for rollback. US loses +1 Operations bonus.", false);	//Containment
		if (HandManager.removeEffect(41)) builder.addField("Soviets Develop Nuclear Submarines","US coups in battleground countries will now lower DEFCON.",false);	//Nuclear Subs
		if (HandManager.removeEffect(43)) builder.addField("SALT Treaty Expires","Coup rolls are no longer penalized.",false);	//SALT
		if (HandManager.removeEffect(51)) builder.addField("Polish Crisis","USSR loses +1 Operations bonus.",false);;	//Brezhnev
		if (HandManager.removeEffect(60)) builder.addField("Prisoner Exchange","Powers traded for Abel. UN Intervention will no longer incur a VP penalty.",false);	//U2
		if (HandManager.removeEffect(93)) builder.addField("Mistakes were made","US rolls on realignments are no longer penalized.",false);	//Iran Contra
		if (HandManager.removeEffect(94)) builder.addField("Sarcophagus","USSR influence placements are no longer restricted by region.",false);	//Chernobyl
		if (HandManager.removeEffect(109)) builder.addField("Bar Harbor Airlines Flight 1808","Coups conducted by the US no longer award VP to the USSR",false);	//Samantha Smith
		if (HandManager.removeEffect(126)) builder.addField("Test Ban","Coups conducted on US Action Rounds no longer drop DEFCON.",false);	//Tsar Bomba
		HandManager.removeEffect(129); //Indo-Soviet
		if (HandManager.removeEffect(310)) builder.addField("Army Hearings","US malus to Operations removed.",false);	//Red Scare/Purge
		if (HandManager.removeEffect(311)) builder.addField("Purges End","USSR malus to Operations removed.",false);
		if (HandManager.removeEffect(400)) builder.addField("Missiles removed","Coups now allowed by the US.",false);	//Missile Crisis
		if (HandManager.removeEffect(401)) builder.addField("Missiles removed","Coups now allowed by the USSR.",false);
		if (HandManager.removeEffect(690)) builder.addField("Dictator deposed","Coups in Latin America are no longer tilted towards the US.",false);	//LADS
		if (HandManager.removeEffect(691)) builder.addField("Dictator deposed","Coups in Latin America are no longer tilted towards the USSR.",false);
		if (HandManager.removeEffect(860)); // just stop the eighth action round effect
		if (turn==2) if (HandManager.removeEffect(1003)) builder.addField("First Lightning", "**Thermonuclear war is now very much a possibility.**\nUS coups are now restricted by DEFCON.", false);
		if (turn==3) if (HandManager.removeEffect(1004)) builder.addField("UK Coalition Government Dissolves","Socialist Governments now has an effect.",false);*/
		if (turn==4) {
			Log.writeToLog("MW Cards Added.");
			HandManager.addToDeck(1); //Rule 4.4
			builder.addField("Mid War","Mid War cards now available for use. You will now get nine cards per turn instead of eight, and there will be eight action rounds per turn.",false);
			if (HandManager.removeEffect(1001)) builder.addField("Deténte","USSR will now go first on all subsequent turns.",false);
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
		if (HandManager.Effects.contains(15)&&ar==14) {
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

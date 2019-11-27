package game;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import commands.TimeCommand;
import events.CardEmbedBuilder;
import events.Decision;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;

public class GameData {
	/**
	 * The location where the majority of the game will be taking place. All public game updates will be placed here.
	 */
	public static TextChannel txtchnl;
	/**
	 * The location where all information privy to the USA will be placed. This includes the following:
	 * <ul>
	 * <li> The US's hand
	 * <li> The USSR's hand after a playing of CIA Created
	 * <li> Cards shown to the US by Our Man in Tehran
	 * </ul>
	 * <br>
	 * It is also recommended that the USA write all commands in here to avoid betraying information to the USSR.
	 */
	public static TextChannel txtusa = null;
	/**
	 * The location where all information privy to the USSR will be placed. This includes the following:
	 * <ul>
	 * <li> The USSR's hand
	 * <li> The US's hand after a playing of Lone Gunman or Aldrich Ames
	 * </ul>
	 * <br>
	 * It is also recommended that the USSR write all commands in here to avoid betraying information to the USA.
	 */
	public static TextChannel txtssr = null;
	/**
	 * The role allowing access to {@link #txtusa}.
	 */
	public static Role roleusa = null;
	/**
	 * The role allowing access to {@link #txtussr}.
	 */
	public static Role rolessr = null;
	
	/**
	 * Whether the game has started.
	 */
	private static boolean started = false;
	/**
	 * Whether the game has ended.
	 */
	private static boolean ended = false;
	/**
	 * Whether the Chinese Civil War optional rule is in effect.
	 */
	public static boolean ccw = false;
	/**
	 * Whether the late war scenario is being used.
	 */
	public static boolean latewar = false;
	/**
	 * Whether the promotional cards in pack 1 are present in the deck.
	 */
	public static boolean promo1 = false;
	/**
	 * Whether the promotional cards in pack 2 are present in the deck.
	 */
	public static boolean promo2 = false;
	/**
	 * Whether the Turn Zero optional rule is in effect. Turn Zero modifies the start conditions in various ways depending on the effects of a series of crises at the start.
	 */
	public static boolean turnzero = false;
	/**
	 * Whether the alternate Space Race track is in use.
	 */
	public static boolean altspace = false;
	/**
	 * Whether optional cards are included in this game.
	 */
	public static boolean optional = true;
	/**
	 * The current turn, from 0 (during the setup phase) to 10. 
	 */
	private static int turn = 0;
	/**
	 * The current action round according to the following:
	 * <ul>
	 * <li> 0 stands for the headline phase.
	 * <li> Each action round after that gets its own number, with odd numbers being for the USSR and even numbers being for the USA.
	 */
	private static int ar = 0;
	/**
	 * The current DEFCON. When this drops to 1, the game ends immediately.
	 */
	private static int defcon = 5;
	/**
	 * The military ops attained by each side - an integer from 0 to 5 inclusive.
	 */
	private static int[] milops = {0,0};
	/**
	 * Each side's progress in the space race - an integer from 0 to 8 inclusive.
	 */
	private static int[] space = {0,0};
	/**
	 * The current status of the Victory Points marker - a positive number for the US and a negative one for the USSR.
	 */
	private static int score = 0;
	/**
	 * How many times a given country has sent a card to space that turn.
	 */
	private static int[] hasSpaced = {0,0};
	/**
	 * An Operations object, denoting any operations that need to be performed as a result of any action. 
	 */
	public static Operations ops = null;
	/**
	 * A Decision object, denoting any decision required to be made.
	 */
	public static Decision dec = null;
	
	/**
	 * Sets {@link #started} to true.
	 */
	public static void startGame() {
		started = true;
	}
	
	/**
	 * Creates the conditions dictated in the Late War scenario.
	 */
	public static void startLateWar() {
		turn = 8;
		defcon = 4;
		space[0] = 6;
		space[1] = 4;
		score = -4;
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
	 * @param cause is how said superpower won that game: <ul>
	 * <li> 0: Win by score
	 * <li> 1: Win by DEFCON
	 * <li> 2: Win by controlling Europe
	 * <li> 3: Win by the other player holding a scoring card
	 * <li> 4: Win by WarGames
	 * </ul>
	 */
	public static void endGame(int victor, int cause) {
		ended = true;
		if (victor==-1) {
			txtchnl.sendMessage(new EmbedBuilder()
					.setTitle("GAME OVER - DRAW!")
					.setImage(Launcher.url("draw.png"))
					.setColor(Color.gray)
					.build());
			return;
		}
		EmbedBuilder builder = new EmbedBuilder()
				.setTitle("GAME OVER - The winner is :flag_" + (victor==0?"us":"su") + ": " + PlayerList.getArray().get(victor).getAsMention() + "!")
				.setDescription("Cause: " + getCause(cause))
				.setColor(victor==0?Color.blue:Color.red)
				.setImage(Launcher.url("victory_" + (victor==0?"us":"su") + cause + ".png"));
		txtchnl.sendMessage(builder.build());
	}
	
	/**
	 * An aid function for {@link #endGame(int, int)}.
	 * @param cause is as enumerated in {@link #endGame(int, int)}.
	 * @return a String, detailing the cause of the game's end.
	 */
	private static String getCause(int cause) {
		if (cause==0) return "Ideological Domination."; //VP == 20 OR VP > the other after turn 10
		if (cause==1) return "Cold War got hot."; //DEFCON == 1
		if (cause==2) return "Tore Down That Wall like the Kool-Aid Man; Oh YEAH!"; //winning Europe scoring
		if (cause==3) return "Withheld a scoring card. Oops.";
		if (cause==4) return "Learned futility."; //wargamed
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
		txtchnl.sendMessage(":hourglass: Charting course for 1949.");
		started = false;
		ended = false;
		turn = 0;
		ar = 0;
		defcon = 5;
		milops[0] = 0;
		milops[1] = 0;
		space[0] = 0;
		space[1] = 0;
		hasSpaced[0] = 0;
		hasSpaced[1] = 0;
		HandManager.reset();
		txtchnl = null;
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
		builder.changeVP(OpsToVP()); // stage E
		checkScore(false, false);
		int scoring = HandManager.checkScoring();
		if (scoring!=0) {
			endGame((scoring/2+1)%2, 3); //1 -> 1, 2 -> 0, 3 -> 0
		} // stage F
		if (HandManager.China != -1) HandManager.China %= 2; // stage G
		if (turn==10) {
			HandManager.removeEffect(73); //if still active; ShuttleDip doesn't count for final scoring
			CardList.getCard(2).onEvent(-1, new String[] {}); //Europe Scoring
			if (ended) return; //if someone controls Europe, it's over.
			CardList.getCard(1).onEvent(-1, new String[] {}); //Asia Scoring
			CardList.getCard(3).onEvent(-1, new String[] {}); //Middle East Scoring
			CardList.getCard(37).onEvent(-1, new String[] {}); //Central America Scoring
			CardList.getCard(79).onEvent(-1, new String[] {}); //Africa Scoring
			CardList.getCard(81).onEvent(-1, new String[] {}); //South America Scoring
			if (HandManager.China==0) score++;
			else score--;
			checkScore(true, false);
			return;
		}
		turn++; // stage H
		ar = 0; 
		HandManager.headline[0]=0;
		HandManager.headline[1]=0;
		hasSpaced[0] = 0; 
		hasSpaced[1] = 0;
		if (HandManager.removeEffect(9)) builder.addField("Geneva Accords","Vietnam now independent. USSR loses +1 Operations bonus in Southeast Asia.",false);	//Vietnam
		if (HandManager.removeEffect(25)) builder.addField("Ineffective Policy", "New administration calls for rollback. US loses +1 Operations bonus.", false);	//Containment
		if (HandManager.removeEffect(41)) builder.addField("Soviets Develop Nuclear Submarines","US coups in battleground countries will now lower DEFCON.",false);	//Nuclear Subs
		if (HandManager.removeEffect(43)) builder.addField("SALT Treaty Expires","Coup rolls are no longer penalized.",false);	//SALT
		if (HandManager.removeEffect(51)) builder.addField("Brezhnev Dies","USSR loses +1 Operations bonus.",false);;	//Brezhnev
		if (HandManager.removeEffect(60)) builder.addField("Prisoner Exchange","Powers traded for Abel. UN Intervention will no longer incur a VP penalty.",false);	//U2
		if (HandManager.removeEffect(93)) builder.addField("Mistakes were made","US rolls on realignments are no longer penalized.",false);	//Iran Contra
		if (HandManager.removeEffect(94)) builder.addField("","USSR influence placements are no longer restricted by region.",false);	//Chernobyl
		if (HandManager.removeEffect(109)) builder.addField("Bar Harbor Airlines Flight 1808","Coups conducted by the US no longer award VP to the USSR",false);	//Samantha Smith
		if (HandManager.removeEffect(310)) builder.addField("Army Hearings","US malus to Operations removed.",false);	//Red Scare/Purge
		if (HandManager.removeEffect(311)) builder.addField("Purges End","USSR malus to Operations removed.",false);
		if (HandManager.removeEffect(400)) builder.addField("Missiles removed","Coups now allowed by the US.",false);	//Missile Crisis
		if (HandManager.removeEffect(401)) builder.addField("Missiles removed","Coups now allowed by the USSR.",false);
		if (HandManager.removeEffect(690)) builder.addField("Dictator deposed","Coups in Latin America are no longer tilted towards the US.",false);	//LADS
		if (HandManager.removeEffect(691)) builder.addField("Dictator deposed","Coups in Latin America are no longer tilted towards the USSR.",false);
		if (HandManager.removeEffect(860)); // just stop the eighth action round effect
		if (turn==2) if (HandManager.removeEffect(1003)) builder.addField("First Lightning", "**Thermonuclear war is now very much a possibility.**\nUS battleground coups will now lower DEFCON.", false);
		if (turn==3) if (HandManager.removeEffect(1004)) builder.addField("UK Coalition Government Dissolves","Socialist Governments now has an effect.",false);
		if (turn==4) {
			HandManager.addToDeck(1);
			builder.addField("Mid War","Mid War cards now available for use.",false);
			if (HandManager.removeEffect(1001)) builder.addField("Deténte","USSR will now go first on all subsequent turns.",false);
		}
		if (turn==8) {
			HandManager.addToDeck(2);
			builder.addField("Late War","Late War cards now available for use.",false);
		}
		txtchnl.sendMessage(builder.build()).complete();
	}
	
	/**
	 * Performs all functions necessary at the start of the turn.
	 */
	public static void startTurn() {
		txtchnl.sendMessage(new CardEmbedBuilder().changeDEFCON(defcon!=5?1:0).setTitle("Start of Turn " + getTurn()).addField("Cards have been dealt.", "", false).build()).complete(); //stage A
		HandManager.deal(); // stage B; also reshuffles automatically
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
		if (getEra()==0 && ar==12) { //end of AR6 in early war
			advanceTurn();
			return;
		}
		if (HandManager.Effects.contains(860)&&(ar==14||ar==15)) {
			ar = 16; 
			return; //North Sea Oil's effect, end of AR7
		}
		if (hasAbility(1, 8)&&ar==14) { //Space Station
			ar=15;
			return;
		}
		if (hasAbility(0, 8)&&ar==14) {
			ar=16;
			return;
		}
		if (ar>=14) { //AR7 or later in late war
			advanceTurn();
			return;
		}
		ar++; //if you don't advance the turn or have anything to do with extra action rounds
		HandManager.activecard = 0;
	}
	/**
	 * @return {@link #ar}
	 */
	public static int getAR() {
		return ar;
	}
	/**
	 * Determines whether the current phase is the headline phase.
	 * @return true if {@link #ar}==0.
	 */
	public static boolean isHeadlinePhase() {
		return ar==0;
	}
	/**
	 * Determines the superpower currently executing his actions.
	 * @return 0 for the US, 1 for the USSR.
	 */
	public static int phasing() {
		if (isHeadlinePhase()) return (HandManager.precedence+(TimeCommand.hl1?1:0))%2;
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
	 * Sets {@link #defcon}.
	 * @param defcon2 is the new value of {@link #defcon}
	 */
	public static void setDEFCON(int defcon2) {
		if (defcon==defcon2) return;
		defcon = Math.max(1, Math.min(5, defcon2));
		if (defcon==1) endGame((phasing()+1)%2, 1);
		if (defcon==2&&!isHeadlinePhase()&&MapManager.get(3).isControlledBy()==0&&HandManager.effectActive(106)) {
			TimeCommand.NORAD=false;
			GameData.dec = new Decision(0,106);
		}
		TimeCommand.prompt();
	}
	/**
	 * 
	 * @return {@link #defcon}
	 */
	public static int getDEFCON() {
		return defcon;
	}
	/**
	 * Sets the number of military operations for a given superpower.
	 * @param sp is 0 if US, 1 if USSR.
	 * @param mil is the amount to change by.
	 */
	public static void addMilOps(int sp,int mil) {
		milops[sp] += mil;
		if (milops[sp] > 5) milops[sp]=5;
	}
	/**
	 * 
	 * @param sp is 0 if US, 1 if USSR.
	 * @return {@link #milops}[sp].
	 */
	public static int getMilOps(int sp) {
		return milops[sp];
	}
	/**
	 * Handles Stage E of a given turn, calculating the VP change from military operations while resetting the military operations status. 
	 * @return the amount of VP given as a result of Military Operations.
	 */
	public static int OpsToVP() {
		int vp = Math.min(0, milops[0]-defcon)-Math.min(0, milops[1]-defcon);
		milops[0]=0;
		milops[1]=0;
		return vp;
	}
	/**
	 * Adds 1 to a superpower's space progress.
	 * @param sp is 0 if US, 1 if USSR.
	 */
	public static void addSpace(int sp) {
		space[sp]++;
	}
	/**
	 * 
	 * @param sp is 0 if US, 1 if USSR.
	 * @return {@link #space}[sp].
	 */
	public static int getSpace(int sp) {
		return space[sp];
	}
	/**
	 * Changes {@link #score}.
	 * @param amt is the amount to change {@link #score} by.
	 */
	public static void changeScore(int amt) {
		score += amt;
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
	 * @param f is true if triggered under final scoring or WarGames.
	 * @param w is true only if triggered under WarGames.
	 */
	public static void checkScore(boolean f, boolean w) {
		if (latewar) {
			if (!f) return;
			int x=0;
			if (w) x = 4;
			if (score>=20) {
				endGame(0,x);
				return;
			}
			else {
				endGame(1,x);
			}
		}
		
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
	 * Marks the superpower as having sent a card to space for that turn.
	 * @param sp is 0 if US, 1 if USSR.
	 */
	public static void toSpace(int sp) {
		hasSpaced[sp]++;
	}
	/**
	 * Determines the superpower currently ahead on the space race.
	 * @return -1 if tied, 0 if US, 1 if USSR.
	 */
	public static int aheadInSpace() {
		if (space[0]<space[1]) return 1;
		if (space[1]<space[0]) return 0;
		return -1;
	}
	/**
	 * Determines whether a superpower has reached its allotted amount of cards able to be sent on the space race.
	 * @param sp is 0 if US, 1 if USSR.
	 * @return true iff the superpower in question has used all opportunities for spacing. 
	 */
	public static boolean hasSpace(int sp) {
		return (hasAbility(sp, 2))?hasSpaced[sp]==2:hasSpaced[sp]==1;
	}
	/**
	 * Determines whether a superpower has an ability granted to it by the space race.
	 * @param sp is 0 if US, 1 if USSR.
	 * @param rank is the rank in question.
	 * @return true if {@link #space}[sp] >= rank and {@link #space}[(sp+1)%2] < rank.
	 */
	public static boolean hasAbility(int sp, int rank) {
		return (space[sp]>=rank) && (space[(sp+1)%2]<rank);
	}
}

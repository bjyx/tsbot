package game;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.Decision;
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
	 * <li> The US's hand
	 * <li> The USSR's hand after a playing of Lone Gunman or Aldrich Ames
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
	 * The current turn, from 1 to 10. 
	 */
	private static int turn = 1;
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
	 * A Decision object, denoting any non-operation-related decision required to be made.
	 */
	public static Decision dec = null;
	
	public static void startGame() {
		started = true;
	}
	
	public static boolean hasGameStarted() {
		return started;
	}
	
	public static void endGame(int victor, int cause) {
		ended = true;
		if (victor==-1) {
			txtchnl.sendMessage(new EmbedBuilder()
					.setTitle("GAME OVER - NO CONTEST!")
					.setImage("https://raw.githubusercontent.com/bjyx/tsbot/master/TSBot/images/draw.png")
					.setColor(Color.gray)
					.build());
			return;
		}
		EmbedBuilder builder = new EmbedBuilder()
				.setTitle("GAME OVER - The winner is :flag_" + (victor==0?"us":"su") + ": " + PlayerList.getArray().get(victor).getAsMention() + "!")
				.setDescription("Cause: " + getCause(cause))
				.setColor(victor==0?Color.blue:Color.red)
				.setImage("https://raw.githubusercontent.com/bjyx/tsbot/master/TSBot/images/victory_" + (victor==0?"us":"su") + ".png");
		txtchnl.sendMessage(builder.build());
	}
	
	private static String getCause(int cause) {
		if (cause==0) return "Ideological Domination."; //VP = 20 OR VP > the other after turn 10
		if (cause==1) return "Cold War got hot."; //DEFCON == 1
		if (cause==2) return "Tore Down That Wall like the Kool-Aid Man; Oh YEAH!"; //winning Europe scoring
		if (cause==3) return "Withheld a scoring card. Oops.";
		if (cause==4) return "Only winning move was not to play."; //wargamed
		return null;
	}

	public static boolean hasGameEnded() {
		return ended;
	}
	
	public static void reset() {
		txtchnl.sendMessage(":hourglass: Charting course for 1949.");
		started = false;
		ended = false;
		turn = 0;
		ar = 1;
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
	public static void advanceTurn() {
		
		changeScore(OpsToVP()); // stage E
		int scoring = HandManager.checkScoring();
		if (scoring!=0) {
			if (scoring==3) {
				endGame(-1, 3);
			}
			endGame(scoring%2, 3);
		} // stage F
		HandManager.China %= 2; // stage G
		if (turn==10) {
			CardList.getCard(2).onEvent(new String[] {}); //Europe Scoring
			if (ended) return; //if someone controls Europe, it's over.
			CardList.getCard(1).onEvent(new String[] {}); //Asia Scoring
			CardList.getCard(3).onEvent(new String[] {}); //Middle East Scoring
			CardList.getCard(37).onEvent(new String[] {}); //Central America Scoring
			CardList.getCard(79).onEvent(new String[] {}); //Africa Scoring
			CardList.getCard(81).onEvent(new String[] {}); //South America Scoring
			if (HandManager.China==0) score++;
			else score--;
			checkScore(true, false);
		}
		turn++; // stage H
		ar = 1; 
		hasSpaced[0] = 0; 
		hasSpaced[1] = 0;
		HandManager.removeEffect(9);	//Vietnam
		HandManager.removeEffect(25);	//Containment
		HandManager.removeEffect(41);	//Nuclear Subs
		HandManager.removeEffect(43);	//SALT
		HandManager.removeEffect(51);	//Brezhnev
		HandManager.removeEffect(60);	//U2
		HandManager.removeEffect(93);	//Iran Contra
		HandManager.removeEffect(94);	//Chernobyl
		HandManager.removeEffect(109);	//Samantha Smith
		HandManager.removeEffect(310);	//Red Scare/Purge
		HandManager.removeEffect(311);
		HandManager.removeEffect(400);	//Missile Crisis
		HandManager.removeEffect(401);
		HandManager.removeEffect(690);	//LADS
		HandManager.removeEffect(691);
		if (turn==4) HandManager.addToDeck(1);
		if (turn==7) HandManager.addToDeck(2);
		if (defcon!=5) defcon++; //stage A
		HandManager.deal(); // stage B
	}
	public static int getTurn() {
		return turn;
	}
	public static int getEra() {
		if (turn==0) return -1;
		if (turn<4) return 0;
		if (turn>7) return 2;
		return 1;
	}
	public static void advanceTime() {
		if (getEra()==0 && ar==12) { //end of AR6 in early war
			advanceTurn();
			return;
		}
		if (HandManager.Effects.contains(83)&&(ar==14||ar==15)) {
			ar = 16; 
			return; //North Sea Oil's effect, end of AR7
		}
		if (space[1]==8&&space[0]<8&&ar==14) {
			ar=15;
			return;
		}
		if (space[0]==8&&space[1]<8&&ar==14) {
			ar=16;
			return;
		}
		if (ar>=14) {
			advanceTurn();
			return;
		}
		ar++; //if you don't advance the turn or have anything to do with extra action rounds
	}
	public static int getAR() {
		return ar;
	}
	public static boolean isHeadlinePhase() {
		return ar==0;
	}
	public static int phasing() {
		if (isHeadlinePhase()) return (HandManager.precedence+ar-1)%2;
		return ar%2;
	}
	public static void setDEFCON(int defcon2) {
		defcon = defcon2;
	}
	public static int getDEFCON() {
		return defcon;
	}
	public static void addMilOps(int sp,int mil) {
		milops[sp] += mil;
		if (milops[sp] > 5) milops[sp]=5;
	}
	public static int getMilOps(int sp) {
		return milops[sp];
	}
	public static int OpsToVP() {
		int vp = Math.min(0, milops[0]-defcon)-Math.min(0, milops[1]-defcon);
		milops[0]=0;
		milops[1]=0;
		return vp;
	}
	public static void addSpace(int sp) {
		space[sp]++;
	}
	public static int getSpace(int sp) {
		return space[sp];
	}
	public static void changeScore(int amt) {
		score += amt;
	}
	public static int getScore() {
		return score;
	}
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
		if (score<0) {
			endGame(1,x);
			return;
		}
		if (score>0) {
			endGame(0,x);
			return;
		}
		if (score==0) {
			endGame(-1,x);
		}
	}
	public static void toSpace(int sp) {
		hasSpaced[sp]++;
	}
	public static int aheadInSpace() {
		if (space[0]<space[1]) return 1;
		if (space[1]<space[0]) return 0;
		return -1;
	}
	public static boolean hasSpace(int sp) {
		return (space[sp]>=2&&space[(sp+1)%2]<2)?hasSpaced[sp]==2:hasSpaced[sp]==1;
	}
	public static void setOps(int sp, int o, boolean i, boolean r, boolean c, boolean s, boolean f) {
		ops = new Operations(sp, o, i, r, c, s, f);
	}
}

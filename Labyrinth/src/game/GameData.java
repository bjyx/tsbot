package game;

import java.awt.Color;

import cards.HandManager;
import cards.Operations;
import commands.TimeCommand;
import events.CardEmbedBuilder;
import events.Decision;
import logging.Log;
import main.Common;
import main.Launcher;
import map.Country;
import map.MapManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
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
	 * Whether the rules from Awakening are in effect.
	 */
	public static boolean awk = false;
	/**
	 * Whether the rules from Forever War are in effect.
	 */
	public static boolean fw = false;
	/**
	 * Whether the first optional rule, Zakat/Strong Horse, is active.
	 */
	public static boolean zakat = false;
	/**
	 * Whether the first optional rule, Zakat/Strong Horse, is active.
	 */
	public static boolean stronghorse = false;
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
	public static int reshuffle = 1;
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
	/*public static boolean startCustom(int s) {
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
		Log.writeToLog("Game Won By " + (victor==0?"US":"JI"));
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
	 * Initializes everything (i.e. prestige and funding) in {@link #GameData()} to a scenario.
	 */
	public static void initialize() {
		switch (scenario) {
		case 0:
			prestige = 7;
			fund = 9;
		case 1:
			prestige = 7;
			fund = 9;
		case 2:
			prestige = 8;
			fund = 6;
		case 3:
			prestige = 3;
			fund = 5;
		case 4:
			prestige = 5;
			fund = 5;
		case 5:
			prestige = 7;
			fund = 6;
		case 6:
			prestige = 5;
			fund = 5;
		case 7:
			prestige = 6;
			fund = 6;
		case 8:
			prestige = 5;
			fund = 7;
		case 9:
			prestige = 7;
			fund = 9;
		case 10:
			prestige = 7;
			fund = 9;
		case 11:
			prestige = 5;
			fund = 6;
		case 12:
			prestige = 6;
			fund = 4;
		case 13:
			prestige = 6;
			fund = 4;
		case 14:
			prestige = 7;
			fund = 9;
		case 15:
			prestige = 7;
			fund = 9;
		}
	}
	
	/**
	 * Resets everything in {@link #GameData()} to its initial state.
	 */
	public static void reset() {
		txtchnl.sendMessage(":hourglass: Take us back.").complete();
		started = false;
		ended = false;
		zakat = false;
		stronghorse = false;
		hero = false;
		vardiff = false;
		scenario = 0;
		player = 2;
		diff = new boolean[] {false, false, false, false, false};
		reshuffle = 1;
		ar = 0;
		prestige = 8;
		fund = 9;
		reserves = new int[] {0,0};
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
		checkVictory(false);
		
		builder.changeFund(-1); //funding-- 
		boolean flag = false;
		if (MapManager.GWOT()>=3) builder.changePrestige(1); //prestige++ if gwot marker is 3 in favor
		for (int i=0; i<MapManager.length(); i++) {
			if (MapManager.get(i).rc==1) MapManager.get(i).rc=2; //green -> tan
			if (MapManager.get(i).getGovernance()==4) {
				flag = true;
			}
		}
		if (flag) builder.changePrestige(-1); //islamist rule
		//TODO lapses
		
		
		builder.changeReserves(0, -2).changeReserves(1, -2); //clear reserves
		HandManager.deal(); // stage 1; also reshuffles automatically
		Log.writeToLog("-+-+- New Turn -+-+-");
		builder.addField("Cards have been dealt.", "", false);
		txtchnl.sendMessage(builder.build()).complete();
	}
	/**
	 * Advances the action round.
	 */
	public static void advanceTime() {
		ar++;
		if (HandManager.JihHand.isEmpty() && (HandManager.USAHand.isEmpty()||TimeCommand.usHolds)) ar = (ar/4+1)*4; //advance to next mult of 4
		if (ar%4==0) { //after US action rounds/at end of turn, resolve plots
			for (int i=0; i<MapManager.length(); i++) {
				for (Integer p : MapManager.get(i).plots) {
					Operations.resolvePlot(i, p);
				}
				MapManager.get(i).plots.clear();
			}
		}
		/*advance turn when:
		 * - both sides are out
		 * - the jihadist is out and the US holds/discards
		 */
		if (HandManager.JihHand.isEmpty() && (HandManager.USAHand.isEmpty()||TimeCommand.usHolds)) advanceTurn();
		Log.writeToLog("--- AR "+ar+" ---");
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
	 * Determines the superpower currently executing actions.
	 * @return 0 for the US, 1 for the Jihadist.
	 */
	public static int phasing() {
		return ((ar%4)/2+1)%2;
	}
	/**
	 * Determines the superpower not currently executing his actions.
	 * @return 0 for the US, 1 for the Jihadist.
	 */
	public static int opponent() {
		return (phasing()+1)%2;
	}
	/**
	 * Adds 1 to a party's progress on the T2 Track.
	 * @param sp is 0 if US, 1 if USSR.
	 */
	public static void changeReserves(int sp, int amt) {
		reserves[sp]+=amt;
		reserves[sp] = Math.min(2, Math.max(0, reserves[sp]));
		Log.writeToLog("R" + (sp==0?"U":"J") + (amt>0?"+":"") + amt);
	}
	/**
	 * 
	 * @param sp is 0 if US, 1 if USSR.
	 * @return {@link #tsquare}[sp].
	 */
	public static int getReserves(int sp) {
		return reserves[sp];
	}
	/**
	 * Changes {@link #prestige}.
	 * @param amt is the amount to change {@link #prestige} by.
	 */
	public static void changePrestige(int amt) {
		prestige += amt;
		prestige = Math.min(12, Math.max(1, prestige));
		Log.writeToLog("P" + (amt>0?"+":"") + amt);
	}
	/**
	 * 
	 * @return {@link #prestige}
	 */
	public static int getPrestige() {
		return prestige;
	}
	/**
	 * Changes {@link #fund}.
	 * @param amt is the amount to change {@link #fund} by.
	 */
	public static void changeFund(int amt) {
		fund += amt;
		fund = Math.min(9, Math.max(1, fund));
		Log.writeToLog("$" + (amt>0?"+":"") + amt);
	}
	/**
	 * 
	 * @return {@link #fund}
	 */
	public static int getFund() {
		return fund;
	}
	/**
	 * Checks for victory conditions, and ends the game accordingly.
	 * @param fin is true if triggered under final scoring.
	 */
	public static void checkVictory(boolean fin) {
		if (trackCells()==cellMax()) {
			endGame(0, 2);
			return;
		}
		int g = 0, f = 0, p = 0, r = 0, c = 0;
		boolean i2 = false;
		for (int i=0; i<MapManager.length(); i++) {
			if (MapManager.get(i).religion<=1) {
				if (MapManager.get(i).getGovernance()<=2) {
					f++;
					if (MapManager.get(i).getGovernance()==1) {
						g+=MapManager.get(i).res;
					}
				}
				if (MapManager.get(i).getGovernance()>=3) {
					p++;
					if (MapManager.get(i).getGovernance()==4) {
						r+=MapManager.get(i).res;
						if (!i2) {
							for (Integer j : MapManager.get(i).adj) {
								if (MapManager.get(j).getGovernance()==4) i2=true;
							}
						}
					}
				}
				if (fin && MapManager.get(i).rc==1) c++;
			}
		}
		if (g>=12) {
			endGame(0, 0);
			return;
		}
		if (r>=6&&i2) {
			endGame(1, 0);
			return;
		}
		if (f>=15) {
			endGame(0, 1);
			return;
		}
		if (p>=15&&prestige==1) {
			endGame(1, 1);
			return;
		}
		if (!fin) return;
		if (g>2*(r+c)) {
			endGame(0, 0);
		}
		else endGame(0, 1);
	}
	/**
	 * Counts the troops on the track.
	 * @param sp is 0 if US, 1 if Jihadist.
	 */
	public static int trackTroops() {
		int x=0;
		for (int i=0; i<MapManager.length(); i++) {
			x += MapManager.get(i).countUnits(-2) - MapManager.get(i).countUnits(-3); // do not count NATO or other troops
		}
		return 15-x;
	}
	
	/**
	 * Counts the available cells on the track.
	 * @param sp is 0 if US, 1 if Jihadist.
	 */
	public static int trackCells() {
		int x=0;
		for (int i=0; i<MapManager.length(); i++) {
			x += MapManager.get(i).countUnits(1) - MapManager.get(i).countUnits(3); // do not count Sadr
		}
		return Math.max(cellMax()-x, 0);
	}
	
	/**
	 * Calculates the maximum number of cells that can be used. A contingency for Awakening introducing Training Camps.
	 */
	public static int cellMax() {
		return ((fund-1)/3+1)*5;
	}
}

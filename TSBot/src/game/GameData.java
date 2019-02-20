package game;

import cards.HandManager;

public class GameData {
	private static boolean started = false;
	private static boolean ended = false;
	private static int turn = 0;
	private static int ar = 0; // 0 - headline, (ar+1)/2 - actual action rounds, ar%2 - phasing player
	private static int defcon = 5;
	private static int[] milops = {0,0};
	private static int[] space = {0,0};
	
	public static void startGame() {
		started = true;
	}
	
	public static boolean hasGameStarted() {
		return started;
	}
	
	public static void endGame() {
		ended = true;
	}
	
	public static boolean hasGameEnded() {
		return ended;
	}
	
	public static void reset() {
		started = false;
		ended = false;
		turn = 0;
		ar = 0;
		defcon = 5;
		milops[0] = 0;
		milops[1] = 0;
		space[0] = 0;
		space[1] = 0;
		
	}
	public static void advanceTurn() {
		if (turn==10) return; //should do final scoring...
		turn++;
		ar = 0;
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
		if (getEra()==0 && ar==12) {
			advanceTurn();
			return;
		}
		if (HandManager.Effects.contains(83)&&ar==14) {
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
		ar++;
	}
	public static int getAR() {
		return ar;
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
}

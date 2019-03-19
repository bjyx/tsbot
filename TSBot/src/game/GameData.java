package game;

import cards.HandManager;
import cards.Operations;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

public class GameData {
	public static TextChannel txtchnl;
	
	private static boolean started = false;
	private static boolean ended = false;
	private static int turn = 0;
	private static int ar = 1; // 1,2 - headline, (ar+1)/2 - actual action rounds, ar%2 - phasing player
	private static int defcon = 5;
	private static int[] milops = {0,0};
	private static int[] space = {0,0};
	private static int score = 0;
	private static int[] hasSpaced = {0,0};
	public static Operations ops = null;
	
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
					.build());
			return;
		}
		EmbedBuilder builder = new EmbedBuilder()
				.setTitle("GAME OVER - The winner is :flag_" + (victor==0?"us":"su") + ": " + PlayerList.getArray().get(victor).getAsMention() + "!")
				.setDescription("Cause: " + getCause(cause))
				.setImage("https://raw.githubusercontent.com/bjyx/tsbot/master/TSBot/images/victory_" + (victor==0?"us":"su") + ".png");
		txtchnl.sendMessage(builder.build());
	}
	
	private static String getCause(int cause) {
		if (cause==0) return "Ideological Domination"; //VP = 20 OR VP > the other
		if (cause==1) return "Cold War got hot"; //DEFCON == 1
		if (cause==2) return "Tore Down That Wall like the Kool-Aid Man; Oh YEAH!"; //winning Europe scoring
		if (cause==3) return "Withheld a scoring card";
		if (cause==4) return "Only winning move was not to play"; //wargamed
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
	}
	public static void advanceTurn() {
		
		changeScore(OpsToVP()); // stage E
		int scoring = HandManager.checkScoring();
		if (scoring!=0) {
			
		} // stage F
		HandManager.China %= 2; // stage G
		if (turn==10) return; //stage I
		turn++; // stage H
		ar = 1; 
		hasSpaced[0] = 0; 
		hasSpaced[1] = 0;
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
		if (getEra()==0 && ar==14) { //end of AR6 in early war
			advanceTurn();
			return;
		}
		if (HandManager.Effects.contains(83)&&(ar==16||ar==17)) {
			ar = 18; 
			return; //North Sea Oil's effect, end of AR7
		}
		if (space[1]==8&&space[0]<8&&ar==16) {
			ar=17;
			return;
		}
		if (space[0]==8&&space[1]<8&&ar==16) {
			ar=18;
			return;
		}
		if (ar>=16) {
			advanceTurn();
			return;
		}
		ar++; //if you don't advance the turn or have anything to do with extra action rounds
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
	public static void toSpace(int sp) {
		hasSpaced[sp]++;
	}
	public static boolean hasSpace(int sp) {
		return (space[sp]>=2&&space[(sp+1)%2]<2)?hasSpaced[sp]==2:hasSpaced[sp]==1;
	}
}

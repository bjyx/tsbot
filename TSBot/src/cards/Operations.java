package cards;

import java.util.Random;

import game.GameData;
import map.Country;
import map.MapManager;

public class Operations {
	private boolean realignment = false; //0 = anything else - that uses ops in one fell swoop, 1 = realignment
	private int opnumber = -1;
	private int restrictions = 7;
	public static final int[] spaceOps = {2, 2, 2, 2, 3, 3, 4, 4};
	public static final int[] spaceVP = {2, 0, 2, 0, 3, 0, 4, 2};
	public static final int[] spaceVP2 = {1, 0, 0, 0, 1, 0, 2, 0};
	public static final int[] spaceRoll = {3, 4, 3, 4, 3, 4, 3, 2};
	
	public Operations(int ops) {
		opnumber = ops;
	}
	
	public void influence(int sp, int[] countries, int[] amt) {
		if (HandManager.playmode!='o') {
			GameData.txtchnl.sendMessage(":x: Changed your mind on this card already?");
			return;
		}
		if (realignment) {
			GameData.txtchnl.sendMessage(":x: You're already doing realignments.");
			return;
		}
		for (int i=0; i<countries.length; i++) {
			MapManager.map.get(countries[i]).changeInfluence(sp, amt[i]);
		}
	}
	public void realignment(int sp, int country) {
		if (HandManager.playmode!='o') {
			GameData.txtchnl.sendMessage(":x: Changed your mind on this card already?");
			return;
		}
		realignment = true;
		if (opnumber<1) {
			GameData.txtchnl.sendMessage(":x: You've run out of ops...");
			return;
		}
	}
	
	public void coup(int sp, int country) {
		if (HandManager.playmode!='o') {
			GameData.txtchnl.sendMessage(":x: Changed your mind on this card already?");
			return;
		}
		if (realignment) {
			GameData.txtchnl.sendMessage(":x: You're already doing realignments.");
			return;
		}
		if (restrictions%2==0) return;
		if (!MapManager.map.get(country).checkIsCoupable()) return;
		if (MapManager.map.get(country).isBattleground()) GameData.setDEFCON(GameData.getDEFCON()-1);
		GameData.addMilOps(sp, opnumber);
		int die = (new Random().nextInt(6))+1;
		int amt = opnumber + die - MapManager.map.get(country).getStab()*2;
		if (amt>0) {
			if (amt>MapManager.map.get(country).influence[(sp+1)%2]) {
				MapManager.map.get(country).changeInfluence(sp, amt-MapManager.map.get(country).influence[(sp+1)%2]);
				MapManager.map.get(country).changeInfluence((sp+1)%2, MapManager.map.get(country).influence[(sp+1)%2]);
			}
		}
	}
	public void space(int sp) {
		if (HandManager.playmode!='s') {
			GameData.txtchnl.sendMessage(":x: Changed your mind on this card already?");
			return;
		}
		if (realignment) {
			GameData.txtchnl.sendMessage(":x: You're already doing realignments.");
			return;
		}
	}
	
}

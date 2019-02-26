package cards;

import java.util.Random;

import game.GameData;
import map.Country;

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
	
	public void influence(Country[] countries, int[] amt) {
		if (realignment) {
			GameData.txtchnl.sendMessage(":x: You're already doing realignments.");
			return;
		}
		for (int i=0; i<countries.length; i++) {
			
		}
	}
	public void realignment(Country country) {
		
		realignment = true;
		
	}
	public int getCoupModifiers(Country country, int sp) {
		return (HandManager.Effects.contains(43)?-1:0);
	}
	public void coup(Country country) {
		if (realignment) {
			GameData.txtchnl.sendMessage(":x: You're already doing realignments.");
			return;
		}
		if (restrictions%2==0) return;
		if (!country.checkIsCoupable()) return;
		int die = (new Random().nextInt(6))+1;
		int amt = opnumber + die - country.getStab()*2;
		if (amt>0) {
			
		}
	}
	public void space() {
		if (realignment) {
			GameData.txtchnl.sendMessage(":x: You're already doing realignments.");
			return;
		}
		
	}
	
}

package cards;

import java.util.Random;

import map.Country;

public class Operations {
	private boolean realignment = false; //0 = anything else - that uses ops in one fell swoop, 1 = realignment
	private int opnumber = -1;
	private int restrictions = 7;
	
	public Operations(int ops) {
		ops = opnumber;
	}
	
	public void influence(Country country, int amt) {
		
	}
	public void realignment(Country country) {
		
		realignment = true;
		
	}
	public int getCoupModifiers(Country country) {
		return (HandManager.Effects.contains(43)?-1:0);
	}
	public void coup(Country country) {
		if (realignment) return;
		if (restrictions%2==0) return;
		if (!country.checkIsCoupable()) return;
		int die = (new Random().nextInt(6))+1;
		int amt = opnumber + die - country.getStab()*2;
		if (amt>0) {
			
		}
	}
}

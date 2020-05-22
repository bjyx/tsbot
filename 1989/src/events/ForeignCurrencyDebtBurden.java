package events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Common;
import powerstruggle.PowerStruggle;

public class ForeignCurrencyDebtBurden extends Card {

	public static int target = -1;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Foreign Currency Debt Burden")
			.setColor(Color.blue);
		builder.changeVP(1);
		HandManager.addEffect(49);
		builder.addField("Debt Spiral", "The Communist may not conduct Support Checks in " + Common.countries[target] + " for the rest of this turn.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		Log.writeToLog("FCDB active.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "049";
	}

	@Override
	public String getName() {
		return "Foreign Currency Debt Burden";
	}

	@Override
	public int getOps() {
		return 1;
	}

	@Override
	public int getEra() {
		return 1;
	}

	@Override
	public int getAssociation() {
		return 0;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		ArrayList<Character> a = new ArrayList<Character>();
		a.addAll(Arrays.asList('e','p','c','h','r','b'));
		if (args.length<2) return false;
		char b = args[1].charAt(0);
		if (a.indexOf(b)==-1) return false;
		target = a.indexOf(b);
		if (target==4) return false; //no Romania
		return true;
	}

	@Override
	public String getDescription() {
		return "The Democrat gains 1 VP. *The Democrat chooses any country besides Romania; the Communist may not conduct Support Checks in that country for the rest of this turn.*";
	}

	@Override
	public String getArguments() {
		return "The country to target. Alternatively, the first letter of said country.";
	}

}

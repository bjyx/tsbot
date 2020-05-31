package events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import logging.Log;
import main.Launcher;
import powerstruggle.PowerStruggle;

public class ChineseSolution extends Card {
	
	public static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("The Chinese Solution")
		.setColor(Color.red);
		builder.changeVP(3); //democratic backfire
		GameData.txtchnl.sendMessage(builder.build()).complete();
		GameData.dec = new Decision(1, 1);
		HandManager.addEffect(96); //how +3 is effected
		GameData.ops = new Operations(1, CardList.getCard(96).getOpsMod(1), false, true, false, 5, target);
		GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", you may now conduct your support checks.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.getT2(1)>=7;
	}

	@Override
	public String getId() {
		return "096";
	}

	@Override
	public String getName() {
		return "The Chinese Solution";
	}

	@Override
	public int getOps() {
		return 1;
	}

	@Override
	public int getEra() {
		return 2;
	}

	@Override
	public int getAssociation() {
		return 1;
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
		return (PowerStruggle.retained[target]!=-1);
	}

	@Override
	public String getDescription() {
		return "The Communist selects a country where the Communist holds Power. The Communist conducts **five (5)** support checks within that country with a +3 modifier. However, the Democrat gains 3 VP. *Can only be played if the Communist has reached space 7 on the T-Square track.*";
	}

	@Override
	public String getArguments() {
		return "Event: the country to target.\n"
				+ "Decision: the support checks.";
	}

}

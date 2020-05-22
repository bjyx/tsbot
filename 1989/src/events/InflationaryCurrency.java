package events;

import java.util.ArrayList;
import java.util.Arrays;

import cards.Operations;
import game.GameData;
import main.Common;
import map.MapManager;
import powerstruggle.PowerStruggle;

public class InflationaryCurrency extends Card {

	public static int target;
	
	public static final String[] currencies = {"Ostmark","Złoty","Koruna","Forint","Leu","Lev"};
	
	@Override
	public void onEvent(int sp, String[] args) {
		boolean opponentInfluence=false;
		for (int i=Common.bracket[target]; i<Common.bracket[target+1]; i++) {
			if (MapManager.get(i).support[1]>0) {
				opponentInfluence=true;
				break;
			}
		}
		if(opponentInfluence) {
			GameData.dec = new Decision(sp, 440);
			Common.spChannel(sp).sendMessage(Common.spRole(sp).getAsMention() + ", you have selected " + Common.countries[target] + " as the unfortunate victim of inflation. You may now remove 2 of your opponent's SP from that country.").complete();
		}
		else {
			Common.spChannel(sp).sendMessage("Why the f@%k did you play this for the event if you were just going to select a region you already have locked down!?").complete();
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder
				.setTitle("Skyrocketing Prices in " + Common.countries[target])
				.setDescription("Inflation rocks the " + currencies[target])
				.setColor(Common.spColor(Common.opp(sp)))
				.setFooter("GMTBot is nothing more than a game, and these infoboxes do not represent real life happenings. GMTBot may not be held responsible for any financial irresponsibility caused by the misinterpretation of game events as real life news.", null);
			builder.addField("No spaces to target!", "The people are already openly hostile to this government—what's the point?", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		
	}

	@Override
	public boolean isPlayable(int sp) {
		for (int i=0; i<5; i++) {
			if (sp==0^PowerStruggle.retained[i]==-1) return true; //a country exists of the opposite orientation
		}
		return false;
	}

	@Override
	public String getId() {
		return "044";
	}

	@Override
	public String getName() {
		return "Inflationary Currency";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 1;
	}

	@Override
	public int getAssociation() {
		return 2;
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
		return (sp==0^PowerStruggle.retained[target]==-1);
	}

	@Override
	public String getDescription() {
		return "Target a region where your opponent holds power. Remove 2 of your opponent's Support from that region. Then, unless your opponent discards a card with an Operations value of at least 3, conduct a Support Check in that region.";
	}

	@Override
	public String getArguments() {
		return "Event: The region to target. Alternatively, the first letter suffices.\n"
				+ "Decision 1: Remove the support.\n"
				+ "Decision 2: Your opponent chooses whether to discard a card.\n"
				+ "Decision 3: If not, Support Check.";
	}

}

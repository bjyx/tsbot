package events;

import java.util.ArrayList;
import java.util.Arrays;

import game.GameData;
import main.Common;
import map.MapManager;
import powerstruggle.PowerStruggle;

public class SocialDemocraticPlatform extends Card {
	private static ArrayList<Integer> doable;
	public static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.dec = new Decision(1, 106);
		Common.spChannel(1).sendMessage(Common.spRole(1).getAsMention() + ", you have selected " + Common.countries[target] + ". You may now add 2 Communist Support to that country.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "106";
	}

	@Override
	public String getName() {
		return "Social Democratic Platform Adopted";
	}

	@Override
	public int getOps() {
		return 3;
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
		doable = new ArrayList<Integer>();
		for (int i = 0; i<6; i++) {
			if (PowerStruggle.retained[i]==-1) doable.add(i);
		}
		ArrayList<Character> a = new ArrayList<Character>();
		a.addAll(Arrays.asList('e','p','c','h','r','b'));
		if (args.length<2) return false;
		char b = args[1].charAt(0);
		if (a.indexOf(b)==-1) return false;
		target = a.indexOf(b);
		return doable.contains(target);
	}

	@Override
	public String getDescription() {
		return "The Communist selects a country where the Democrat holds power. Add 2 Communist Support to that country, then conduct a support check within that country.";
	}

	@Override
	public String getArguments() {
		return "Event: The country.\n"
				+ "Decision 1: the influence to add.\n"
				+ "Decision 2: the support check.";
	}

}

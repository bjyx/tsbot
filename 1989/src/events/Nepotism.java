package events;

import game.Die;
import game.GameData;

public class Nepotism extends Card {
	
	public static int roll;
	public static final int[] results = {0, 4, 4, 3, 3, 1, 1};

	@Override
	public void onEvent(int sp, String[] args) {
		roll = new Die().roll();
		GameData.dec = new Decision(1, 80);
		GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", you rolled a " + roll + ". You may place " + results[roll] + " support among worker spaces in the Balkans.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "080";
	}

	@Override
	public String getName() {
		return "Nepotism";
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
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "A die is rolled.\n"
				+ "1-2: Place a total of 4 Communist Support in Worker spaces in the Balkans.\n"
				+ "3-4: Place a total of 3 Communist Support in Worker spaces in the Balkans.\n"
				+ "5-6: Place 1 Communist Support in a Worker space in the Balkans.\n";

	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: Influence.";
	}

}

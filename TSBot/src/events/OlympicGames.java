package events;

import game.GameData;

public class OlympicGames extends Card {
	
	public static int host;

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.dec = new Decision((sp+1)%2, 20);
		host = sp;
		GameData.txtchnl.sendMessage("Pending response.").complete();
		if (GameData.dec.sp==0) {
			GameData.txtusa.sendMessage((GameData.roleusa.getAsMention()) + ", state your decision regarding participation in the Moscow Olympics. (`TS.decide/decision/choose boycott [card]` to boycott (**drops DEFCON and lets opponent conduct 4 Operations**), or `compete` to participate in a die game.)").complete();
		}
		else {
			GameData.txtssr.sendMessage((GameData.rolessr.getAsMention()) + ", state your decision regarding participation in the Los Angeles Olympics. (`TS.decide/decision/choose boycott [card]` to boycott (**drops DEFCON and lets opponent conduct 4 Operations**), or `compete` to participate in a die game.)").complete();
		}
		
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "020";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Olympic Games";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Gives your opponent a choice:\n"
				+ "- Participate in your Olympic Games, wherein both sides roll a die and you add 2 to your die roll, with the higher roll gaining 2 VP (ties are rerolled);\n"
				+ "- Boycott your Olympic Games, which lowers the DEFCON level by one and allows you to conduct Operations with 4 Ops (modified accordingly).";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "No arguments when playing for event.\n"
				+ "Decision required by opponent:\n"
				+ "- `boycott`, which drops DEFCON and allows the phasing player to play 4 Operations points (modified accordingly);"
				+ "- `compete`, which activates the dice game.";
	}

}

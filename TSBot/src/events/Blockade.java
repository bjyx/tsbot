package events;

import game.GameData;

public class Blockade extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.dec = new Decision(0, 10);
		GameData.txtssr.sendMessage("Pending response.").complete();
		GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", the Soviets are blockading Berlin. What will you do? (`TS.decide/decision/choose airlift [card]` to lift the blockade, or `concede` to remove your influence in West Germany.)").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "010";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Blockade";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "The US must decide between discarding a card worth at least 3 Ops, or removing all US Influence in West Germany.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Event: None.\n"
				+ "Decision:\n"
				+ "	- `TS.decide airlift [card]` to defuse the blockade.\n"
				+ " - `TS.decide concede` to remove all influence in West Germany.";
	}

}

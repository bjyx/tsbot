package events;

import cards.HandManager;
import game.GameData;
/**
 * The Blockade Card.
 * @author adalbert
 *
 */
public class Blockade extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.dec = new Decision(0, 10);
		GameData.txtssr.sendMessage("Pending response.").complete();
		GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", the Soviets are blockading Berlin. What will you do? (`TS.decide/decision/choose airlift [card]` to lift the blockade, or `concede` to remove your influence in West Germany.)").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return !HandManager.effectActive(1002);
	}

	@Override
	public String getId() {
		return "010";
	}

	@Override
	public String getName() {
		return "Blockade";
	}

	@Override
	public int getOps() {
		return 1;
	}

	@Override
	public int getEra() {
		return 0;
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
		if (HandManager.effectActive(1002)) return "The Allies control Berlin. This card has no effect.";
		return "The US must decide between discarding a card worth at least 3 Ops, or removing all US Influence in West Germany.";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision:\n"
				+ "	- `TS.decide airlift [card]` to defuse the blockade.\n"
				+ " - `TS.decide concede` to remove all influence in West Germany.";
	}

}

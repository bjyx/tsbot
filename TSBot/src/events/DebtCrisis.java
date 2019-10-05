package events;

import game.GameData;

public class DebtCrisis extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.dec = new Decision(0, 95);
		GameData.txtssr.sendMessage("Pending response.").complete();
		GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", South America is mired in a debt crisis. What will you do? (`TS.decide/decision/choose aid [card]` to end the debt crisis, or `concede` to double Soviet Influence in two South American countries.)").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "095";
	}

	@Override
	public String getName() {
		return "Latin American Debt Crisis";
	}

	@Override
	public int getOps() {
		return 2;
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "The US must decide between discarding a card worth at least 3 Ops, or doubling USSR Influence in two South American countries.";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision 1: \n"
				+ " - `aid <card>`, to discard <card>, which must be worth 3 Ops or more.\n"
				+ " - `concede`, to activate Decision 2."
				+ "Decision 2: Two countries in South America. If at most two countries in South America have USSR Influence, this is not needed.";
	}

}

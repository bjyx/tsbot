package events;

import game.GameData;
import powerstruggle.PowerStruggle;

public class ScoringEastGermany extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.ps = new PowerStruggle(0, sp);
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "042";
	}

	@Override
	public String getName() {
		return "Scoring Card: East Germany";
	}

	@Override
	public int getOps() {
		return 0;
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Initiates a Power Struggle for East Germany. Then, scores East Germany (worth 3).";
	}

	@Override
	public String getArguments() {
		return "None.";
	}
}

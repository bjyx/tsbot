package events;

import game.GameData;
import powerstruggle.PowerStruggle;

public class ScoringRomania extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.ps = new PowerStruggle(4, sp);
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
		return "Scoring Card: Romania";
	}

	@Override
	public int getOps() {
		return 0;
	}

	@Override
	public int getEra() {
		return 2;
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
		return "Initiates a Power Struggle for Romania. Then, scores Romania (worth 2).";
	}

	@Override
	public String getArguments() {
		return "None.";
	}
}

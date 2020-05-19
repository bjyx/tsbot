package events;

import game.GameData;
import powerstruggle.PowerStruggle;

public class ScoringHungary extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.ps = new PowerStruggle(3, sp);
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "023";
	}

	@Override
	public String getName() {
		return "Scoring Card: Hungary";
	}

	@Override
	public int getOps() {
		return 0;
	}

	@Override
	public int getEra() {
		return 0;
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
		return "Initiates a Power Struggle for Poland. Then, scores Hungary (worth 1).";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

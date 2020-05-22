package events;

import game.GameData;
import powerstruggle.PowerStruggle;

public class ScoringBulgaria extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.ps = new PowerStruggle(5, sp);
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "043";
	}

	@Override
	public String getName() {
		return "Scoring Card: Bulgaria";
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
		return "Initiates a Power Struggle for Bulgaria. Then, scores Bulgaria (worth 1).";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

package events;

import game.GameData;
import powerstruggle.PowerStruggle;

public class ScoringPoland extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.ps = new PowerStruggle(1, sp);
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "022";
	}

	@Override
	public String getName() {
		return "Scoring Card: Poland";
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
		return "Initiates a Power Struggle for Poland. Then, scores Poland (worth 3).";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

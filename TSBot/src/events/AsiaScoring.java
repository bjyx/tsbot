package events;

public class AsiaScoring extends Card {
	private static final int presence = 3;
	private static final int domination = 7;
	private static final int control = 9;
	
	@Override
	public int getOps() {
		return 0;
	}
	@Override
	public int getAssociation() {
		return 0;
	}
	@Override
	public void onEvent(String[] args) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getId() {
		return "001";
	}
	@Override
	public String getName() {
		return "Asia Scoring";
	}
	@Override
	public int getEra() {
		return 0;
	}
	@Override
	public boolean isRemoved() {
		return false;
	}
	@Override
	public String getDescription() {
		return "Scores Asia on a scale of 3/7/9. +1 for battlegrounds, +1 for each country you control that borders the other superpower (Afghanistan, North Korea, Japan).";
	}
}

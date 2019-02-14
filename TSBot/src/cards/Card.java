package cards;

public abstract class Card {
	public abstract void onPlay(String[] args); //what happens when played for event?
	public abstract String getId();
	public abstract String getName();
	public abstract int getOps();
	public abstract int getEra(); //0 for early, 1 for mid, 2 for late; +3 for optional
	public abstract int getAssociation(); //0 for US, 1 for USSR, 2 for neutral;
	public abstract String getDescription();
}

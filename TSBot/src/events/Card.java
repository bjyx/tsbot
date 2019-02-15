package events;

public abstract class Card {
	public abstract void onEvent(String[] args); //what happens when played for event?
	public abstract String getId();
	public abstract String getName();
	public abstract int getOps();
	public abstract int getEra(); //0 for early, 1 for mid, 2 for late; +3 for optional
	public abstract int getAssociation(); //0 for US, 1 for USSR, 2 for neutral;
	public abstract boolean isRemoved();
	public abstract String getDescription();
	
	public String getAssociationString() {
		if (getAssociation() == 0) return "A";
		if (getAssociation() == 1) return "R";
		return "N";
	}
	
	public String toString() {
		return "`" + getId() + " " + getName() + (isRemoved()?"*":"") + " (" + (getOps()==0?"S":getOps()) + getAssociationString() + ")`";
	}
}

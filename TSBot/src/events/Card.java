package events;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public abstract class Card {
	public static final String[] numbers = {"zero","one","two","three","four","five","six"};
	public abstract void onEvent(String[] args); //what happens when played for event?
	public abstract boolean isPlayable();
	public abstract String getId();
	public abstract String getName();
	public abstract int getOps();
	public abstract int getEra(); //0 for early, 1 for mid, 2 for late; +3 for optional
	public abstract int getAssociation(); //0 for US, 1 for USSR, 2 for neutral;
	public abstract boolean isRemoved();
	public abstract boolean isFormatted(String[] args);
	public abstract String getDescription();
	
	public String getAssociationString() {
		if (getAssociation() == 0) return "A";
		if (getAssociation() == 1) return "R";
		return "N";
	}
	
	public String toString() {
		return "`" + getId() + " " + getName() + (isRemoved()?"*":"") + " (" + (getOps()==0?"S":getOps()) + getAssociationString() + ")`";
	}
	
	public MessageEmbed toEmbed() {
		EmbedBuilder builder = new EmbedBuilder()
				.setTitle(":" + numbers[getOps()] + "::Influence" + getAssociationString() + ": `" + getId() + " " + getName() + (isRemoved()?"*":"") + "`")
				.setImage("https://raw.githubusercontent.com/bjyx/tsbot/master/TSBot/images/cards/" + getId())
				.setDescription(getDescription());
		return builder.build();
	}
}

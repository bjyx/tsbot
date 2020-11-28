package events;

import java.awt.Color;

import cards.HandManager;
import commands.StartCommand;
import game.GameData;
import main.Launcher;
import net.dv8tion.jda.api.EmbedBuilder;


/**
 * The abstract class defining a card in the game according to Section 2.2. Most other classes in this package are derivatives of this class.
 * @author adalbert
 *
 */
public abstract class Card {
	/**
	 * Strings, representing various numbers betweeon 0 and 6. 
	 */
	public static final String[] numbers = {"zero","one","two","three","four","five","six"};
	/**
	 * The event associated with the card.
	 * @param sp is the superpower *playing* the card (to be distinguished from the phasing player, in the case of self-inflicted Grain Sales playing a neutral card).
	 * @param args includes the parameters for the event.
	 */
	public abstract void onEvent(int sp, String[] args);
	/**
	 * Determines whether the card is playable at the current moment.
	 * @param sp is the superpower playing the card (only relevant for Red Scare under CCW rules).
	 * @return true if the card is playable in the current set of circumstances, false otherwise.
	 */
	public abstract boolean isPlayable(int sp);
	/**
	 * Gives the ID of the card.
	 * @return A string, containing the ID (a three-digit integer with leading zeroes from 1 to the card count).
	 */
	public abstract String getId();
	/**
	 * Gives the name of the card.
	 * @return A string containing the Event Title of this card.
	 */
	public abstract String getName();
	/**
	 * Gives the number of Operation Points a card has.
	 * @return An integer.
	 */
	public abstract int getOps();
	/**
	 * Gives the era of the card.
	 * @return 0 for an early war card, 1 for a mid war card, 2 for a late war card.
	 */
	public abstract int getEra();
	/**
	 * Gives the superpower associated with the card, as detailed in rule 2.2.2.
	 * @return 0 for a US event, 1 for a USSR event, 2 for a neutral event.
	 */
	public abstract int getAssociation();
	/**
	 * Whether the event will be reshuffled into the deck after being played for the event.
	 * @return A boolean value.
	 */
	public abstract boolean isRemoved();
	/**
	 * Whether the arguments in the event tag are suitable for this event.
	 * @param sp is only important for UN Intervention, as it requires knowledge of the hand of the person who played the card. 
	 * @param args are the arguments presented to the event.
	 * @return A boolean value.
	 */
	public abstract boolean isFormatted(int sp, String[] args);
	/**
	 * A description of the card and how it works.
	 * @return A string with the Event Description of this card.
	 */
	public abstract String getDescription();
	/**
	 * A description of the arguments to be used with the event command.
	 * @return A string.
	 */
	public abstract String getArguments();
	
	/**
	 * Used in the shorthand used to describe a card.
	 * @return One of "A", "R", or "N", depending on the association of the given card.
	 */
	public String getAssociationString() {
		if (getAssociation() == 0) return "A";
		if (getAssociation() == 1) return "R";
		return "N";
	}
	
	public String toString() {
		return "`" + getId() + " " + getName() + (isRemoved()?"\\*":"") + " (" + (getOps()==0?"S":getOps()) + getAssociationString() + ")`";
	}
	
	/**
	 * Turns the information of the card into an embed.
	 * @return the embed.
	 */
	public EmbedBuilder toEmbed(int sp) {
		return new EmbedBuilder()
				.setTitle(":" + numbers[getOps()] + ":" + StartCommand.emojiID[9+this.getAssociation()] + "`" + getId() + " " + getName() + (isRemoved()?"*":"") + "`")
				.setImage(Launcher.url("cards/" + getId() + ".png"))
				.setDescription(getDescription())
				.setColor(sp==0?Color.blue:(sp==1?Color.red:Color.gray));
	}
	/**
	 * Applies the effects of Prudence and Perestroika/Sinatra Doctrine to the cards.
	 * @param sp
	 * @return the actual number of Operations able to be used using this card. 
	 */
	public int getOpsMod(int sp) {
		if (getOps()==0) return 0;
		int x = this.getOps();
		if (GameData.hasAbility(sp, 2) && x==1) x = 2; //Rally space
		if (sp==0) {
			return Math.max(1, x + (HandManager.Effects.contains(50)?1:0) - (HandManager.Effects.contains(80)?1:0));
		}
		else return Math.max(1, x + (HandManager.Effects.contains(25)?1:0) - (HandManager.Effects.contains(81)?1:0));
	}
}

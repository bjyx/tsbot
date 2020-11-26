package events;

import java.awt.Color;

import cards.HandManager;
import commands.StartCommand;
import game.GameData;
import main.Common;
import main.Launcher;
import net.dv8tion.jda.core.EmbedBuilder;


/**
 * The abstract class defining a card in the game. Thanks to repeats, I have to edit this class a bit.
 * @author adalbert
 *
 */
public abstract class Card {
	
	
	public static int count = 0;
	
	public int id;
	
	public Card() {
		id = count;
		count++;
	}
	/**
	 * The event associated with the card.
	 * @param sp is the player *playing* the card (to be distinguished from the phasing player, in the case of self-inflicted Grain Sales playing a neutral card, for example).
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
	 * Gives the player associated with the card.
	 * @return 0 for a US event, 1 for a Jihadist event, 2 for a neutral event.
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
	 * There are multiple "Jihadist Personality" cards that affect how certain cards are played.
	 * @return a boolean value.
	 */
	public abstract boolean isJihadistPersonality();
	
	/**
	 * Gives the ID of the card.
	 * @return A string, containing the ID (a three-digit integer with leading zeroes from 1 to the card count).
	 */
	public String getId() {
		return String.format("%03d", id);
	}
	/**
	 * Used in the shorthand used to describe a card.
	 * @return One of "A", "R", or "N", depending on the association of the given card.
	 */
	public String getAssociationString() {
		if (getAssociation() == 0) return "U";
		if (getAssociation() == 1) return "J";
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
				.setTitle(":" + Common.numbers[getOps()] + ":" + Common.players_e[this.getAssociation()] + "`" + getId() + " " + getName() + (isRemoved()?"*":"") + "`")
				.setImage(Launcher.url("cards/" + getId() + ".png"))
				.setDescription(getDescription())
				.setColor(sp==0?Color.blue:(sp==1?Color.red:Color.gray));
	}
	/**
	 * Applies the effects of Prudence and Perestroika/Sinatra Doctrine to the cards.
	 * @param sp
	 * @return the actual number of Operations able to be used using this card. 
	 */
	/*public int getOpsMod(int sp) {
		if (getOps()==0) return 0;
		int x = this.getOps();
		if (GameData.hasAbility(sp, 2) && x==1) x = 2; //Rally space
		if (sp==0) {
			return Math.max(1, x + (HandManager.Effects.contains(50)?1:0) - (HandManager.Effects.contains(80)?1:0));
		}
		else return Math.max(1, x + (HandManager.Effects.contains(25)?1:0) - (HandManager.Effects.contains(81)?1:0));
	}*/
}

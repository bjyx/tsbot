package events;

import java.util.ArrayList;

import cards.HandManager;
import commands.StartCommand;
import game.GameData;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
/**
 * An extension of Discord's EmbedBuilder built for displaying changes to the game.
 * @author adalbert
 *
 */
public class CardEmbedBuilder extends EmbedBuilder {
	/**
	 * Strings that are written-out numbers.
	 */
	public static final String[] numbers = {"zero","one","two","three","four","five","six","seven","eight","nine"};
	/**
	 * Constructor calls super().
	 */
	public CardEmbedBuilder() {
		super();
		this.setTitle("<missing flavortext>").setDescription("<missing flavortext>").setFooter("\"<missing flavortext>\"\n- <missing flavortext>", Launcher.url("people/qmark.png"));
	}
	
	@Override
	public MessageEmbed build() {
		this.setAuthor("Turn " + GameData.getTurn() + " " + ("Action Round " + (GameData.getAR() + 1)/2  + " " + (GameData.phasing()==0?"US":"USSR")), "");
		return super.build();
	}
	
	/**
	 * Converts integers into emoji equivalents.
	 * @param i is the integer in question
	 * @return A string containing Discord emoji syntax.
	 */
	public static String intToEmoji(int i) {
		if (Math.abs(i)>=10) return intToEmoji(i/10) + ":" + numbers[Math.abs(i)%10] + ":";
		String str="";
		if (i<0) str += ":heavy_minus_sign:";
		else str += ":heavy_plus_sign:";
		return str + ":" + numbers[Math.abs(i)] + ":";
		
	}
	/**
	 * Adds a field to denote the change in score. Also changes the score.
	 * @param amt is the amount the score changes by.
	 * @return the builder with the new field, for chaining.
	 */
	public CardEmbedBuilder changeVP(int amt) {
		if (amt==0) return this;
		GameData.changeScore(amt);
		return (CardEmbedBuilder) this.addField(":regional_indicator_v::regional_indicator_p:"+StartCommand.emojiID[amt>0?3:2] + intToEmoji(amt),"Now at " + GameData.getScore(),false);
	}
	/**
	 * Adds a field to denote a change in the influence values of a nation. Also changes said value.
	 * @param country is the country this change affects.
	 * @param sp is the superpower whose influence is affected.
	 * @param amt is the amount said influence changes by.
	 * @return the builder with the new field, for chaining.
	 */
	public CardEmbedBuilder changeInfluence(int country, int sp, int amt) {
		if (amt==0) return this;
		if (MapManager.get(country).icon==8) return this; //failsafe for the fucking systematization
		MapManager.get(country).changeInfluence(sp, amt);
		if (HandManager.effectActive(971)&&MapManager.get(TyrantIsGone.target).isControlledBy()==0) {
			this.addField("The Ceausescus have been captured!", "", false);
			HandManager.removeEffect(971);
			this.changeVP(2);
		}
		return (CardEmbedBuilder) this.addField(MapManager.get(country) + ((sp==0?StartCommand.emojiID[amt>0?3:1]:StartCommand.emojiID[amt>0?2:0]))+intToEmoji(amt),"Now at " + (MapManager.get(country).isControlledBy()==0?"**":"") + MapManager.get(country).support[0] + (MapManager.get(country).isControlledBy()==0?"**":"") + "/" + (MapManager.get(country).isControlledBy()==1?"**":"") + MapManager.get(country).support[1] + (MapManager.get(country).isControlledBy()==1?"**":""),true);
	}
	/**
	 * Adds a field to denote a change in DEFCON. Also changes said DEFCON.
	 * @param amt is the amount DEFCON changes by.
	 * @return the builder with the new field, for chaining.
	 */
	public CardEmbedBuilder changeStab() {
		GameData.setStab();
		return (CardEmbedBuilder) this.addField("<:flag_su:648119356387098644>:scales: - 1","Now at " + GameData.getStab(),false);
	}
	/**
	 * Applies {@link events.changeInfluence} to every country in a list.
	 * @param order is the list.
	 * @param sp is the superpower this applies to.
	 * @param values is a list of amounts to change the influence in those countries by.
	 * @return the builder with the new field, for chaining.
	 */
	public CardEmbedBuilder bulkChangeInfluence(ArrayList<Integer> order, int sp, ArrayList<Integer> values) {
		for (int i=0; i<order.size(); i++) {
			this.changeInfluence(order.get(i),sp,values.get(i));
		}
		return this;
	}
}

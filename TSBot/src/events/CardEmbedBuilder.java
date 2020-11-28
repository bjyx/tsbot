package events;

import java.util.ArrayList;

import commands.InfoCommand;
import commands.StartCommand;
import game.GameData;
import map.MapManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
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
	}
	
	@Override
	public MessageEmbed build() {
		this.setAuthor("Turn " + GameData.getTurn() + " " + (GameData.isHeadlinePhase()?"Headline":("Action Round " + (GameData.getAR() + 1)/2  + " " + (GameData.phasing()==0?"US":"USSR"))), InfoCommand.url());
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
	public CardEmbedBuilder changeVP(int amt, boolean wwby) {
		if (amt==0) return this;
		GameData.changeScore(amt, wwby);
		return (CardEmbedBuilder) this.addField(":regional_indicator_v::regional_indicator_p:"+StartCommand.emojiID[amt>0?9:10] + intToEmoji(amt),"Now at " + GameData.getScore(),false);
	}
	/**
	 * Adds a field to denote the change in score. Also changes the score.
	 * @param amt is the amount the score changes by.
	 * @return the builder with the new field, for chaining.
	 */
	public CardEmbedBuilder changeVP(int amt) {
		if (amt==0) return this;
		GameData.changeScore(amt, true);
		return (CardEmbedBuilder) this.addField(":regional_indicator_v::regional_indicator_p:"+StartCommand.emojiID[amt>0?9:10] + intToEmoji(amt),"Now at " + GameData.getScore(),false);
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
		MapManager.get(country).changeInfluence(sp, amt);
		return (CardEmbedBuilder) this.addField(MapManager.get(country) + ((sp==0?StartCommand.emojiID[amt>0?9:6]:StartCommand.emojiID[amt>0?10:7]))+intToEmoji(amt),"Now at " + (MapManager.get(country).isControlledBy()==0?"**":"") + MapManager.get(country).influence[0] + (MapManager.get(country).isControlledBy()==0?"**":"") + "/" + (MapManager.get(country).isControlledBy()==1?"**":"") + MapManager.get(country).influence[1] + (MapManager.get(country).isControlledBy()==1?"**":""),true);
	}
	/**
	 * Adds a field to denote a change in DEFCON. Also changes said DEFCON.
	 * @param amt is the amount DEFCON changes by.
	 * @return the builder with the new field, for chaining.
	 */
	public CardEmbedBuilder changeDEFCON(int amt) {
		if (amt==0) return this;
		GameData.setDEFCON(GameData.getDEFCON()+amt);
		return (CardEmbedBuilder) this.addField(":radioactive:" + intToEmoji(amt),"Now at " + GameData.getDEFCON(),false);
	}
	/**
	 * Adds a field to denote a change in military operations. Also changes said Military Operations.
	 * @param sp is the superpower this change applies to.
	 * @param mil is the amount to change by.
	 * @return the builder with the new field, for chaining.
	 */
	public CardEmbedBuilder addMilOps(int sp, int mil) {
		if (mil==0) return this;
		GameData.addMilOps(sp, mil);
		return (CardEmbedBuilder) this.addField(StartCommand.emojiID[12]+ (sp==0?StartCommand.emojiID[9]:StartCommand.emojiID[10])+intToEmoji(mil),"Now at " + GameData.getMilOps(sp),false);
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

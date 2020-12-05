package events;

import game.GameData;
import main.Common;
import main.Launcher;
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
	 * Constructor calls super().
	 */
	public CardEmbedBuilder() {
		super();
		this.setTitle("<missing flavortext>").setDescription("<missing flavortext>").setFooter("\"<missing flavortext>\"\n- <missing flavortext>", Launcher.url("people/qmark.png"));
	}
	
	@Override
	public MessageEmbed build() {
		this.setAuthor("Action Round " + GameData.getAR(), null);
		return super.build();
	}
	
	/**
	 * Converts integers into emoji equivalents.
	 * @param i is the integer in question
	 * @return A string containing Discord emoji syntax.
	 */
	public static String intToEmoji(int i) {
		if (Math.abs(i)>=10) return intToEmoji(i/10) + ":" + Common.numbers[Math.abs(i)%10] + ":";
		String str="";
		if (i<0) str += ":heavy_minus_sign:";
		else str += ":heavy_plus_sign:";
		return str + ":" + Common.numbers[Math.abs(i)] + ":";
		
	}
	/**
	 * Adds a field to denote a change in prestige. Also changes it.
	 * @param amt is the amount the score changes by.
	 * @return the builder with the new field, for chaining.
	 */
	public CardEmbedBuilder changePrestige(int amt) {
		if (amt==0) return this;
		GameData.changePrestige(amt);
		return (CardEmbedBuilder) this.addField(":star:" + intToEmoji(amt),"Now at " + GameData.getPrestige(),false);
	}
	/**
	 * Adds a field to denote a change in funding. Also changes it.
	 * @param amt is the amount the score changes by.
	 * @return the builder with the new field, for chaining.
	 */
	public CardEmbedBuilder changeFund(int amt) {
		if (amt==0) return this;
		GameData.changeFund(amt);
		return (CardEmbedBuilder) this.addField(":dollar:" + intToEmoji(amt),"Now at " + GameData.getFund(),false);
	}
	/**
	 * Adds a field to denote a change in the units in a nation. Also changes said value.
	 * @param country is the country this change affects.
	 * @param sp is the superpower whose influence is affected.
	 * @param amt is the amount said influence changes by.
	 * @return the builder with the new field, for chaining.
	 */
	public CardEmbedBuilder editUnits(int country, int type, int amt) {
		if (amt==0) return this;
		MapManager.get(country).editUnits(type, amt);
		return (CardEmbedBuilder) this.addField(MapManager.get(country) + Common.units_e[type+3] + intToEmoji(amt), "Now " + MapManager.get(country).unitString(),true);
	}
	
	public CardEmbedBuilder setGovernance(int country, int lvl) {
		if (MapManager.get(country).getGovernance()==lvl) return this;
		if (MapManager.get(country).religion>1) return this; //non-mutable governances exist
		if (lvl>4) return this;
		if (lvl<0) return this; //TODO can you set something back to untested? 
		MapManager.get(country).setGovernance(lvl);
		return (CardEmbedBuilder) this.addField(MapManager.get(country) + Common.gov_e[lvl], "", false);
		
	}
	public CardEmbedBuilder shiftGovernance(int country, int dir) {
		if (dir==0) return this;
		if (MapManager.get(country).religion>1) return this; //non-mutable governances exist
		if (MapManager.get(country).getGovernance()==1&&dir==-1) return this;
		if (MapManager.get(country).getGovernance()==3&&dir==1) return this; //Poor to Islamist rule isn't happening by event. Use setGovernance for this.
		MapManager.get(country).setGovernance(MapManager.get(country).getGovernance()+dir);
		return (CardEmbedBuilder) this.addField(MapManager.get(country) + Common.gov_e[MapManager.get(country).getGovernance()], "", false);
	}
	
	public CardEmbedBuilder setAlignment(int country, int lvl) {
		if (MapManager.get(country).getPosture()==lvl) return this;
		if (MapManager.get(country).religion>1) return this; //no alignment
		if (lvl>3) return this;
		if (lvl<0) return this;
		MapManager.get(country).setGovernance(lvl);
		return (CardEmbedBuilder) this.addField(MapManager.get(country) + Common.gov_e[lvl], "", false);
	}
	
	public CardEmbedBuilder setPosture(int country, int lvl) {
		if (MapManager.get(country).getPosture()==lvl) return this;
		if (MapManager.get(country).religion!=2) return this; //no posture
		if (lvl>1) return this;
		if (lvl<-1) return this;
		MapManager.get(country).setGovernance(lvl);
		return (CardEmbedBuilder) this.addField(MapManager.get(country) + Common.gov_e[lvl], "", false);
	}
	
	public CardEmbedBuilder shiftAlignment(int country, int dir) {
		if (dir==0) return this;
		if (MapManager.get(country).religion>1) return this; //non-mutable governances exist
		if (MapManager.get(country).getPosture()==1&&dir<0) return this;
		if (MapManager.get(country).getPosture()==3&&dir>0) return this;
		MapManager.get(country).setGovernance(MapManager.get(country).getGovernance()+dir);
		return (CardEmbedBuilder) this.addField(MapManager.get(country) + Common.gov_e[MapManager.get(country).getGovernance()], "", false);
	}
	
	public CardEmbedBuilder changeReserves(int sp, int amt) {
		if (amt==0) return this;
		GameData.changeReserves(sp, amt);
		return (CardEmbedBuilder) this.addField(Common.players_e[sp] +":military_helmet:"+ intToEmoji(amt), "", false);
	}
}

package events;

import game.GameData;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;

public class CardEmbedBuilder extends EmbedBuilder {
	public static final String[] numbers = {"zero","one","two","three","four","five","six","seven","eight","nine"};
	public static String intToEmoji(int i) {
		if (Math.abs(i)>10) return intToEmoji(i/10) + ":" + numbers[Math.abs(i)%10] + ":";
		String str="";
		if (i<0) str += ":heavy_minus:";
		else str += ":heavy_plus:";
		return str + ":" + numbers[Math.abs(i)] + ":";
		
	}
	public CardEmbedBuilder changeVP(int amt) {
		GameData.changeScore(amt);
		return (CardEmbedBuilder) this.addField(":regional_indicator_v::regional_indicator_p:" + intToEmoji(amt),"Now at " + GameData.getScore(),false);
	}
	public CardEmbedBuilder changeInfluence(int country, int sp, int amt) {
		MapManager.map.get(country).changeInfluence(sp, amt);
		return (CardEmbedBuilder) this.addField(":flag_" + MapManager.map.get(country).getISO3166() + "::Influence" + (sp==0?"A":"R") + ":" + intToEmoji(amt),"Now at " + MapManager.map.get(country).influence[0] + "/" + MapManager.map.get(country).influence[1],true);
	}
	public CardEmbedBuilder changeDEFCON(int amt) {
		GameData.setDEFCON(GameData.getDEFCON()+amt);
		return (CardEmbedBuilder) this.addField(":radioactive:" + intToEmoji(amt),"Now at " + GameData.getDEFCON(),false);
		
	}
	public CardEmbedBuilder addMilOps(int sp, int mil) {
		GameData.addMilOps(sp, mil);
		return (CardEmbedBuilder) this.addField(":tank::Influence" + (sp==0?"A:":"R:") + intToEmoji(mil),"Now at " + GameData.getMilOps(sp),false);
	}
	public CardEmbedBuilder bulkChangeInfluence(int[] country, int sp, int[] amt) {
		for (int i=0; i<country.length; i++) {
			this.changeInfluence(country[i],sp,amt[i]);
		}
		return this;
	}
}

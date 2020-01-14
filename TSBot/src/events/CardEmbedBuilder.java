package events;

import java.util.ArrayList;

import commands.InfoCommand;
import commands.StartCommand;
import game.GameData;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class CardEmbedBuilder extends EmbedBuilder {
	public static final String[] numbers = {"zero","one","two","three","four","five","six","seven","eight","nine"};
	
	public CardEmbedBuilder() {
		super();
		this.setAuthor("Turn " + GameData.getTurn() + " " + (GameData.isHeadlinePhase()?"Headline":("Action Round " + (GameData.getAR() + 1)/2  + " " + (GameData.phasing()==0?"US":"USSR"))), InfoCommand.url());
	}
	
	@Override
	public MessageEmbed build() {
		this.setAuthor("Turn " + GameData.getTurn() + " " + (GameData.isHeadlinePhase()?"Headline":("Action Round " + (GameData.getAR() + 1)/2  + " " + (GameData.phasing()==0?"US":"USSR"))), InfoCommand.url());
		return super.build();
	}
	
	public static String intToEmoji(int i) {
		if (Math.abs(i)>=10) return intToEmoji(i/10) + ":" + numbers[Math.abs(i)%10] + ":";
		String str="";
		if (i<0) str += ":heavy_minus_sign:";
		else str += ":heavy_plus_sign:";
		return str + ":" + numbers[Math.abs(i)] + ":";
		
	}
	public CardEmbedBuilder changeVP(int amt) {
		if (amt==0) return this;
		GameData.changeScore(amt);
		return (CardEmbedBuilder) this.addField(":regional_indicator_v::regional_indicator_p:"+StartCommand.emojiID[amt>0?9:10] + intToEmoji(amt),"Now at " + GameData.getScore(),false);
	}
	public CardEmbedBuilder changeInfluence(int country, int sp, int amt) {
		if (amt==0) return this;
		MapManager.get(country).changeInfluence(sp, amt);
		return (CardEmbedBuilder) this.addField(MapManager.get(country) + ((sp==0?StartCommand.emojiID[amt>0?9:6]:StartCommand.emojiID[amt>0?10:7]))+intToEmoji(amt),"Now at " + (MapManager.get(country).isControlledBy()==0?"**":"") + MapManager.get(country).influence[0] + (MapManager.get(country).isControlledBy()==0?"**":"") + "/" + (MapManager.get(country).isControlledBy()==1?"**":"") + MapManager.get(country).influence[1] + (MapManager.get(country).isControlledBy()==1?"**":""),true);
	}
	public CardEmbedBuilder changeDEFCON(int amt) {
		if (amt==0) return this;
		GameData.setDEFCON(GameData.getDEFCON()+amt);
		return (CardEmbedBuilder) this.addField(":radioactive:" + intToEmoji(amt),"Now at " + GameData.getDEFCON(),false);
		
	}
	public CardEmbedBuilder addMilOps(int sp, int mil) {
		if (mil==0) return this;
		GameData.addMilOps(sp, mil);
		return (CardEmbedBuilder) this.addField(StartCommand.emojiID[12]+ (sp==0?StartCommand.emojiID[9]:StartCommand.emojiID[10])+intToEmoji(mil),"Now at " + GameData.getMilOps(sp),false);
	}
	public CardEmbedBuilder bulkChangeInfluence(ArrayList<Integer> order, int sp, ArrayList<Integer> values) {
		for (int i=0; i<order.size(); i++) {
			this.changeInfluence(order.get(i),sp,values.get(i));
		}
		return this;
	}
}

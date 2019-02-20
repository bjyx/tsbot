package map;

import java.util.List;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public abstract class Country {
	//practical
	public static final String[] regions = {"Officially Neutral Europe","Western Europe","Eastern Europe","Middle East","Asia","Southeast Asia","Africa","Central America","South America","Superpower"};
	public int[] influence = {0,0};
	public abstract int getID(); //yes, countries have a unique identifier so it can work with twistrug; problem?
	public abstract String getName();
	public abstract int getRegion();
	public abstract String getISO3166();
	public abstract List<String> getAliases();
	public abstract int getStab();
	public abstract List<Integer> getAdj();
	public abstract boolean isBattleground();
	
	//flavor
	public abstract String getDesc();
	public abstract String getLeader(); //Will be determined based on turns and who controls more:
	/*
	 * Early: 1948-60 (Truman and Eisenhower)
	 * Mid: 1960-1976 (Down to Ford)
	 * Late: 1976-1988 (Carter, Reagan, Bush)
	 * */
	
	public void changeInfluence(int sp, int amt) {
		influence[sp] += amt;
	}
	public int isControlledBy() {
		if (influence[0]>=influence[1]+getStab()) return 0;
		if (influence[1]>=influence[0]+getStab()) return 1;
		return -1;
	}
	
	//flavor again
	public MessageEmbed getInfo() {
		EmbedBuilder builder = new EmbedBuilder()
				.setTitle(getName()+" ("+getISO3166()+")")
				.setDescription(getStab() + " stability" + (isBattleground()?" battleground ":" ")+ "country in " + regions[getRegion()])
				.setThumbnail("https://raw.githubusercontent.com/bjyx/tsbot/master/TSBot/images/countries/"+getISO3166())
				.addField("Current Leader", getLeader(),false)
				.addField("Influence", ":InflUS:"+influence[0]+":InflSU"+influence[1], false)
				.addField("", getDesc(), false);
		String str = "";
		for (int i : getAdj()) {
			str += MapManager.map.get(i).getName() + "\n";
		}
		builder.addField("Adjacencies", str, false);
		return builder.build();
	}
}

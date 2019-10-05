package map;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Country {
	//practical
	public static final String[] regions = {"Europe","Europe (Western)","Europe (Eastern)","Middle East","Asia","Southeast Asia","Africa","Central America","South America","Superpower"};
	public static int count=0;
	public int[] influence = {0,0};
	public int id; //yes, countries have a unique identifier so it can work with twistrug; problem?
	public String name;
	public int region;
	public String iso;
	public List<String> aliases;
	public int stab;
	public List<Integer> adj;
	public boolean isBattleground;
	
	//flavor
	public String desc;
	
	/**
	 * Sets up a given country. 
	 * @param n is the name of the country.
	 * @param reg is the region that country belongs to.
	 * @param i is the ISO 3166-1 alpha-2 code, used for the generation of flags.
	 * @param st is the stability of that country.
	 * @param bg is true if the country is a battleground, and will drop DEFCON if targeted with a coup.
	 * @param a provides a list of aliases for the country that can stand in for the actual name.
	 * @param j provides a list of integers, representing countries adjacent 
	 * @param desc2
	 * @param inf
	 */
	
	public Country(String n, int reg, String i, int st, boolean bg, String[] a, Integer[] j, String desc2, int[] inf) {
		name = n;
		region = reg;
		iso = i;
		stab = st;
		isBattleground = bg;
		aliases = Arrays.asList(a);
		adj = Arrays.asList(j);
		desc = desc2;
		influence[0] = inf[0];
		influence[1] = inf[1];
		id = count; 
		count++;
	}
	
	public void changeInfluence(int sp, int amt) {
		influence[sp] += amt;
		if (influence[sp]<0) influence[sp]=0;
	}
	public int isControlledBy() {
		if (influence[0]>=influence[1]+stab) return 0;
		if (influence[1]>=influence[0]+stab) return 1;
		return -1;
	}
	public boolean checkIsCoupable() {
		if ((region<=2&&GameData.getDEFCON()<5) //Europe
				||(region<=5&&region>=4&&GameData.getDEFCON()<4) //Asia
				||(region==3&&GameData.getDEFCON()<3)) //Middle East
			return false;
		return true;
	}
	
	public String toString() {
		return ":flag_" + iso + ":"; //flag emoji
	}
	public Color getColor() {
		if (region<=2) return Color.blue;
		if (region==3) return Color.cyan;
		if (region==4||region==5) return Color.orange;
		if (region==6) return Color.yellow;
		if (region==7) return Color.green;
		if (region==8) return Color.green;
		return Color.darkGray;
	}
	
	//flavor again
	public MessageEmbed getInfo() {
		EmbedBuilder builder = new EmbedBuilder()
				.setTitle(name+" ("+iso+")")
				.setDescription(stab + " stability" + (isBattleground?" battleground ":" ")+ "country in " + regions[region])
				.setThumbnail(Launcher.url("countries/"+iso+".png"))
				.setColor(getColor())
				.addField("Influence", ":InfluenceA:"+CardEmbedBuilder.intToEmoji(influence[0])+":InfluenceR:"+CardEmbedBuilder.intToEmoji(influence[1]), false)
				.addField("", desc, false);
		String str = "";
		for (int i : adj) {
			str += MapManager.get(i);
		}
		builder.addField("Adjacencies", str, false);
		return builder.build();
	}
}

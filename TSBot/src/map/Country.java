package map;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import commands.StartCommand;
import events.CardEmbedBuilder;
import game.GameData;
import logging.Log;
import main.Launcher;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Country {
	/**
	 * An array of strings, used to mark down the Region of this country.
	 */
	public static final String[] regions = {"Europe","Europe (Western)","Europe (Eastern)","Middle East","Asia","Southeast Asia","Africa","Central America","South America","Superpower"}; //2.1.1
	/**
	 * A counter for the number of countries, used to create an ID.
	 */
	public static int count=0;
	/**
	 * The amount of influence the superpowers have in this country.
	 */
	public int[] influence = {0,0};
	/**
	 * The country's ID, used in the find() function. Countries are presented in a specific order for compatibility with TwiStrug. Likely redundant.
	 */
	public int id;
	/**
	 * The country's name. 
	 */
	public String name;
	/**
	 * The country's region, as outlined in rule 2.1.1.
	 */
	public int region;
	/**
	 * A code for the country that will allow a flag to be displayed for said country on Discord.
	 */
	public String iso;
	/**
	 * A list of strings that can be used to denote the country in commands.
	 */
	public List<String> aliases; 
	/**
	 * The stability value of the country (Rule 2.1.3). Mutable only in the case of Zaire.
	 */
	public int stab;
	/**
	 * The IDs of the countries to which this country is connected by a line on the map (Rule 2.1.6).
	 */
	public List<Integer> adj;
	/**
	 * Whether this country is considered a Battleground, and therefore have special rules applied to them (Rule 2.1.4). Mutable only in the case of Taiwan.
	 */
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
	/**
	 * An implementation of Rule 2.1.2 and the Austria/Finland clause of Rule 2.1.1, so I don't need to deal with parentheses and logic gates every time I talk about Europe or Asia.
	 * @param r is the region being tested for
	 * @return whether this country is in r, as described in 2.1.2 (Europe and Asia include their own sub-regions) and the Austria/Finland clause of 2.1.1 (which are in both Eastern AND Western Europe).
	 */
	public boolean inRegion(int r) {
		if ((r==0)&&this.region<=2) return true; //Europe includes 0, 1, 2
		if (r<=2 && this.region==0) return true; //Western/Eastern Europe includes their own and 0
		if (r==4&&this.region==5) return true; //Asia includes SE Asia
		return r==this.region; //Every region includes itself
	}
	/**
	 * Change the amount of influence in a country.
	 * @param sp is the superpower whose influence is affected.
	 * @param amt is by how much.
	 */
	public void changeInfluence(int sp, int amt) {
		influence[sp] += amt;
		if (influence[sp]<0) influence[sp]=0;
		Log.writeToLog(iso.toUpperCase() + (amt>=0?" +":" ") + amt + (sp==0?"US":"SU") + ", now " + influence[0] + "/" + influence[1]);
	}
	/**
	 * Defines "control", as outlined in Rule 2.1.7.
	 * @return whether a superpower's influence exceeds the opponent's influence by the stability number.
	 */
	public int isControlledBy() {
		if (influence[0]>=influence[1]+stab) return 0;
		if (influence[1]>=influence[0]+stab) return 1;
		return -1;
	}
	/**
	 * Outlines whether a country can be couped or realigned, as outlined in Rule 8.1.5.
	 * @return whether the country's region is accessible to coups under the current DEFCON.
	 */
	public boolean checkIsCoupable() {
		if ((this.inRegion(0)&&GameData.getDEFCON()<5) //Europe
				||(this.inRegion(4)&&GameData.getDEFCON()<4) //Asia
				||(this.inRegion(3)&&GameData.getDEFCON()<3)) //Middle East
			return false;
		return true;
	}
	
	public String toString() {
		if (this.id==6) return StartCommand.emojiID[1];
		if (this.id==20) return StartCommand.emojiID[2];
		if (this.id==62) return StartCommand.emojiID[3];
		if (this.id==33) return StartCommand.emojiID[5];
		if (this.id==85) return StartCommand.emojiID[4];
		return ":flag_" + iso + ":"; //flag emoji
	}
	/**
	 * Assigns a color to each region. 
	 * @return a Color.
	 */
	public Color getColor() {
		if (region<=2) return new Color(153,110,255);
		if (region==3) return Color.cyan;
		if (region==4||region==5) return Color.orange;
		if (region==6) return Color.yellow;
		if (region==7) return new Color(214, 255, 110);
		if (region==8) return Color.green;
		return Color.darkGray;
	}
	
	/**
	 * Creates a MessageEmbed for this particular country based on all of its information. 
	 * @return flavor.
	 */
	public MessageEmbed getInfo() {
		EmbedBuilder builder = new EmbedBuilder()
				.setAuthor("Information", null, Launcher.url("emoji/InflNC.png"))
				.setTitle(name+" ("+iso+")")
				.setDescription(stab + " stability" + (isBattleground?" battleground ":" ")+ "country in " + regions[region])
				.setColor(getColor())
				.addField("Influence", StartCommand.emojiID[(this.isControlledBy()==0?9:6)]+CardEmbedBuilder.intToEmoji(influence[0])+StartCommand.emojiID[(this.isControlledBy()==1?10:7)]+CardEmbedBuilder.intToEmoji(influence[1]), false)
				.setFooter(desc, Launcher.url("emoji/InflNC.png"));
		if (Launcher.f) builder.setImage(Launcher.url("countries/"+iso+".png"));
		else builder.setThumbnail(Launcher.url("countries/"+iso+".png"));
		String str = "";
		for (int i : adj) {
			str += MapManager.get(i);
		}
		builder.addField("Adjacencies", str, false);
		return builder.build();
	}
}

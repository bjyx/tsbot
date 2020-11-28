package map;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import commands.StartCommand;
import events.CardEmbedBuilder;
import game.GameData;
import logging.Log;
import main.Common;
import main.Launcher;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
/**
 * These are called countries as a legacy of Twilight Struggle. 
 * @author adalbert
 *
 */
public class Country {
	/**
	 * An array of strings, used to mark down an emoji for the icon of this space.
	 */
	public static final String[] emoji = {":hammer:",":axe:",":oncoming_automobile:",":star:",":memo:",":v:",":church:",":crescent_moon:",":tractor:"};
	/**
	 * A counter for the number of spaces, used to create an ID.
	 */
	public static int count=0;
	/**
	 * The amount of influence the superpowers have in this country.
	 */
	public int[] support = {0,0};
	/**
	 * The space's ID, used in the find() function. Likely redundant.
	 */
	public int id;
	/**
	 * The space's name. 
	 */
	public String name;
	/**
	 * The country, as outlined in rule 2.1.1.
	 */
	public int region;
	/**
	 * A list of strings that can be used to denote the country in commands.
	 */
	public List<String> aliases; 
	/**
	 * The stability value of the country, determined by its socioeconomic class.
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
	/**
	 * The socio-economic icon for this space.
	 */
	public int icon;
	/**
	 * The form used in the shorthand of the log, which is also a valid alias.
	 */
	public String shorthand;
	/**
	 * Flavor.
	 */
	public String desc;
	
	/**
	 * Sets up a given space. 
	 * @param n is the name of the space.
	 * @param reg is the country that space belongs to.
	 * @param i indicates the socioeconomic stratum for that space.
	 * @param bg is true if the country is a battleground, and will drop DEFCON if targeted with a coup.
	 * @param a provides a list of aliases for the country that can stand in for the actual name.
	 * @param j provides a list of integers, representing countries adjacent 
	 * @param desc2 is a description of the history of that space with regards to 1989.
	 * @param inf is the starting support in that space.
	 * @param sh is the shorthand for the space.
	 */
	
	public Country(String n, int reg, int i, boolean bg, String[] a, Integer[] j, String desc2, int[] inf, String sh) {
		name = n;
		region = reg;
		shorthand = sh;
		isBattleground = bg;
		aliases = Arrays.asList(a);
		adj = Arrays.asList(j);
		desc = desc2;
		support[0] = inf[0];
		support[1] = inf[1];
		id = count; 
		icon = i;
		count++;
		switch (icon) {
			case 0:
				stab = 3;
				break;
			case 1:
				stab = 4;
				break;
			case 2:
				stab = 4;
				break;
			case 3:
				stab = 3;
				break;
			case 4:
				stab = 2;
				break;
			case 5:
				stab = 1;
				break;
			case 6:
				if (this.inRegion(7)) stab = 3;
				else stab = 5;
				break;
			case 7: 
				stab = 4;
				break;
			default:
				break;
		}
			
	}
	/**
	 * An implementation of Rule 2.1.2, so I don't need to deal with parentheses and logic gates every time I talk about a superregion.
	 * @param r is the region being tested for
	 * @return whether this country is in r, as described in 2.1.2.
	 */
	public boolean inRegion(int r) {
		if (r==6&&this.region<=3) return true; //Eastern Europe
		if (r==7&&this.region>=4) return true; //Balkans
		return r==this.region; //Every region includes itself
	}
	/**
	 * Change the amount of influence in a country.
	 * @param sp is the superpower whose influence is affected.
	 * @param amt is by how much.
	 */
	public void changeInfluence(int sp, int amt) {
		support[sp] += amt;
		if (support[sp]<0) support[sp]=0;
		Log.writeToLog(shorthand.toUpperCase() + (amt>=0?" +":" ") + amt + (sp==0?"US":"SU") + ", now " + support[0] + "/" + support[1]);
	}
	/**
	 * Defines "control", as outlined in Rule 2.1.7.
	 * @return whether a superpower's influence exceeds the opponent's influence by the stability number.
	 */
	public int isControlledBy() {
		if (support[0]>=support[1]+stab) return 0;
		if (support[1]>=support[0]+stab) return 1;
		return -1;
	}
	/**
	 * Outlines whether a country can be couped or realigned, as outlined in Rule 8.1.5.
	 * @return whether the country's region is accessible to coups under the current DEFCON.
	 */
	public boolean checkIsCoupable() {
		if ((this.inRegion(0)&&GameData.getStab()<5) //Europe
				||(this.inRegion(4)&&GameData.getStab()<4) //Asia
				||(this.inRegion(3)&&GameData.getStab()<3)) //Middle East
			return false;
		return true;
	}
	
	public String toString() {
		return Common.flags[this.region] + Country.emoji[this.icon] + " " + this.shorthand + (this.isBattleground?"\\*":" ");
	}
	/**
	 * Assigns a color to each region. 
	 * @return a Color.
	 */
	public Color getColor() {
		if (region==0) return Color.gray;
		if (region==1) return Color.yellow;
		if (region==2) return new Color(35, 106, 14);
		if (region==3) return Color.orange;
		if (region==4) return Color.lightGray;
		if (region==5) return new Color(113, 196, 88);
		return Color.darkGray;
	}
	
	/**
	 * Creates a MessageEmbed for this particular country based on all of its information. 
	 * @return flavor.
	 */
	public MessageEmbed getInfo() {
		EmbedBuilder builder = new EmbedBuilder()
				.setAuthor("Information", null, Launcher.url("emoji/InflNC.png"))
				.setTitle(name+" "+emoji[icon] + " (" + shorthand + ")")
				.setDescription(stab + " stability" + (isBattleground?" battleground ":" ")+ "space in " + Common.countries[region])
				.setColor(getColor())
				.addField("Influence", StartCommand.emojiID[(this.isControlledBy()==0?3:1)]+CardEmbedBuilder.intToEmoji(support[0])+StartCommand.emojiID[(this.isControlledBy()==1?2:0)]+CardEmbedBuilder.intToEmoji(support[1]), false)
				.setFooter(desc, Launcher.url("emoji/InflNC.png"));
		if (Launcher.f) builder.setImage(Launcher.url("countries/"+shorthand+".png"));
		else builder.setThumbnail(Launcher.url("countries/"+shorthand+".png"));
		String str = "";
		for (int i : adj) {
			str += MapManager.get(i);
		}
		builder.addField("Adjacencies", str, false);
		return builder.build();
	}
}

package map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.Die;
//import game.GameData;
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
	 * A counter for the number of spaces, used to create an ID.
	 */
	public static int count=0;
	/**
	 * The units in this country:
	 * <br>
	 * - Positive numbers are Jihadist cells. Sleepers are 1, actives 2. Sadr gets 3. Cadres are 0. 
	 * <br>
	 * - Negative numbers are US units, including troops (-2) and militia (-1). NATO units get -3. 
	 * <br>
	 * #AsLongAsItWorks
	 */
	public ArrayList<Integer> units;
	/**
	 * The space's ID, used in the find() function. Likely redundant.
	 */
	public int id;
	/**
	 * The space's name. 
	 */
	public String name;
	/**
	 * A list of strings that can be used to denote the country in commands.
	 */
	public List<String> aliases; 
	/**
	 * The religion of this space—0 for Sunni, 1 for Shia-Mix, 2 for non-Muslim. Iran gets 3.
	 */
	public int religion;
	/**
	 * The governance of the country, on a scale of 1-Good to 4-Islamic rule. Untested is 0.
	 */
	private int gov;
	/**
	 * The alignment/posture of the country, on a scale of 1-Ally to 3-Adversary or -1-Soft to 1-Hard. Yes, two different scales. No, I do not care. 0 is untested, as always.
	 */
	private int post;
	/**
	 * The IDs of the countries to which this country is connected by a line on the map. This also includes all the Schengen countries.
	 */
	public List<Integer> adj;
	/**
	 * The presence of a rec number means recruitment gets a higher chance of success here. 
	 */
	public int rec;
	/**
	 * The resource number in a Muslim country. 
	 */
	public int res;
	/**
	 * The presence of oil increases the resource number of a Muslim country. 
	 */
	public boolean oil;
	/**
	 * Civil War. 
	 */
	public boolean cw = false;
	/**
	 * Regime Change—0 for not, 1 for green, 2 for tan. Because that's important.
	 */
	public int rc = 0;
	/**
	 * Aid.
	 */
	public int aid = 0;
	/**
	 * Awakening and Reaction. Use only on Muslim. 
	 */
	public int[] spring = {0,0};
	/**
	 * Last disruption, for some cards.
	 */
	public int disrupt = -2147483648; //"never"
	/**
	 * Last governance improvement, for some cards.
	 */
	public int govplus = -2147483648; //"never"
	/**
	 * Besieged Regime marker.
	 */
	public boolean br = false;
	/**
	 * Advisors - +1 militia at turn start.
	 */
	public boolean advisors = false;
	/**
	 * The form used in the shorthand of the log, which is also a valid alias. Also used to pull out a flag. 
	 */
	public String shorthand;
	/**
	 * Flavor.
	 */
	public String desc;
	
	/**
	 * Creates a given space with all initial constraints. 
	 * @param n is the name of the space.
	 * @param r is the country that space belongs to.
	 * @param i indicates the socioeconomic stratum for that space.
	 * @param bg is true if the country is a battleground, and will drop DEFCON if targeted with a coup.
	 * @param a provides a list of aliases for the country that can stand in for the actual name.
	 * @param j provides a list of integers, representing countries adjacent 
	 * @param desc2 is a description of the history of that space with regards to 1989.
	 * @param inf is the starting support in that space.
	 * @param sh is the shorthand for the space.
	 */
	
	public Country(String n, int r, int g, int re, boolean o, String[] a, Integer[] j, String desc2, String sh) {
		id = count; 
		count++;
		name = n;
		shorthand = sh;
		religion = r;
		gov = g;
		post = 0;
		if (religion==2 && id != 39) { //non muslim, but also non nigeria
			res=0;
			rec=re;
		}
		else {
			res=re;
			rec=0;
		}
		oil=o; //outside because Nigeria is a thing (TM)
		aliases = Arrays.asList(a);
		adj = Arrays.asList(j); //adj list
		desc = desc2; //description
		units = new ArrayList<Integer>();
		
	}
	/**
	 * Tests the country. Unlike rolling the country, can only be done once.
	 */
	public void testCountry() {
		if (post!=0&&gov!=0) return;
		rollCountry();
	}
	/**
	 * Rolls a new governance/posture for the country. 
	 */
	public void rollCountry() {
		int r = new Die().roll();
		if (religion<=1) gov = r<=4?3:2;
		if (religion==2) {
			if (id==0) r+=1; //this is only for the US
			post = r<=4?-1:1;
		}
	}
	/**
	 * ...
	 * @param test dictates the necessity of a test for this situation. For example, if you're simply checking for the existence of a Fair Muslim country, you need to skip over an untested country. If you're checking this specific country you'll need to test it.
	 * @return the governance of the country.
	 */
	public int getGovernance(boolean test) {
		if (gov==0&&test) testCountry();
		return gov;
	}
	
	/**
	 * ...
	 */
	public void setGovernance(int i) {
		gov=i;
	}
	/**
	 * ...
	 * @param test dictates the necessity of a test for this situation. For example, if you're simply checking for the existence of a Fair Muslim country, you need to skip over an untested country. If you're checking this specific country you'll need to test it.
	 * @return the posture of the country.
	 */
	public int getPosture(boolean test) {
		if (post==0&&test) testCountry();
		return post;
	}
	
	/**
	 * ...
	 */
	public void setPosture(int i) {
		post=i;
	}
	/**
	 * Change the troops/cells in a country.
	 * @param type is the type of unit affected.
	 * @param amt is by how much.
	 */
	public void editUnits(Integer type, int amt) {
		for (int i=0; i<Math.abs(amt); i++) {
			if (amt>0) units.add(type);
			else units.remove(type);
		}
		Log.writeToLog(shorthand.toUpperCase() + " + " + amt + "*" + type +", now " + units);
	}
	/**
	 * Determine the number of troops/cells in a country.
	 * @param cond defines what to count. If 1, counts all cells; if 2, counts only actives + always-active Sadr. If -1, counts all US units inc militia; if -2, counts only troops.
	 * @return a number.
	 */
	public int countUnits(int cond) {
		int x = 0;
		for (Integer i : units) {
			if (cond > 0) {
				if (i >= cond) x++;
			}
			else {
				if (i<=cond) x++;
			}
		}
		return x;
	}
	/**
	 * Determines the possibility of a War of Ideas or a Major Jihad. 
	 * @return whether one side's units exceeds the other's by 5.
	 */
	public int isControlledBy() {
		if (countUnits(-1)>=countUnits(1)+5) return 0; //can WoI a RC country
		if (countUnits(1)>=countUnits(-1)+5) return 1; //can Major Jihad
		return -1;
	}
	
	public String unitString() {
		String str = "";
		for (int i : units) {
			str += Common.units_e[i+3];
		}
		return str;
	}
	
	public String toString() {
		return ":flag_" + this.shorthand + ":";
	}
	/**
	 * Assigns a color that changes based on the country's situation. 
	 * @return a Color.
	 */
	public Color getColor() {
		//color non-muslim countries based on posture
		if (religion==2 && post==1) return Color.pink;
		if (religion==2 && post==2) return new Color(159, 94, 168);
		//color muslim countries based on governance
		if (religion<=1&&gov==1) return Color.blue;
		if (religion<=1&&gov==2) return Color.yellow;
		if (religion<=1&&gov==3) return Color.red;
		if (religion<=1&&gov==4) return Color.green;
		return Color.darkGray;
	}
	
	/**
	 * Creates a MessageEmbed for this particular country based on all of its information. 
	 * @return flavor.
	 */
	public MessageEmbed getInfo() {
		EmbedBuilder builder = new EmbedBuilder()
				.setAuthor("Information", null, Launcher.url("emoji/InflNC.png"))
				.setTitle(":flag_"+shorthand+": "+name+" (" + shorthand + ")")
				.setDescription(Common.govs[gov] + " " + (religion<=1?(oil?"Oil-producing ":"" + Common.religs[religion] + " " + Common.aligns[post] + " (" + res + " resources)"):(Common.posts[post+1]+"-Postured" + "Non-Muslim Country")) + Common.religs[religion] + " Country") //e.g. Poor oil-producing Shia-Mix Adversary or Good Hard-Postured Non-Muslim Country
				.setColor(getColor())
				.setFooter(desc, Launcher.url("emoji/InflNC.png"));
		if (Launcher.f) builder.setImage(Launcher.url("countries/"+shorthand+".png"));
		else builder.setThumbnail(Launcher.url("countries/"+shorthand+".png"));
		String str = "";
		for (int i : adj) {
			str += MapManager.get(i);
		}
		builder.addField("Adjacencies", str, false);
		builder.addField("Units", unitString(), false);
		return builder.build();
	}
}

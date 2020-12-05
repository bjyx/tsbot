package cards;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import commands.TimeCommand;
import events.CardEmbedBuilder;
import events.Decision;
import game.Die;
import game.GameData;
import logging.Log;
import main.Common;
import map.MapManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
/**
 * Class for handling all related to operations.
 * @author adalbert
 *
 */
public class Operations {
	/**
	 * The superpower in charge of these operations.
	 */
	private int sp = -1;
	/**
	 * If the current set of ops can be used for option 1 (M; Deploy/Travel).
	 */
	private boolean movement = true;
	/**
	 * If the current set of ops can be used for option 2 (G; WoI/Jihad).
	 */
	private boolean governance = true;
	/**
	 * If the current set of ops can be used for option 3 (T; Alert/Plot).
	 */
	private boolean terror = false;
	/**
	 * If the current set of ops can be used for option 4 (C; Disrupt/Recruit).
	 */
	private boolean cells = false;
	/**
	 * The number of ops available to use.
	 */
	public int opnumber = -1;
	/**
	 * The plots currently available to the jihadist. 
	 */
	public ArrayList<Integer> plots = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 2, 2, 3));
	/**
	 * Whether the Jihadist has already used the first plot (which disables the corresponding event).
	 */
	public static boolean firstplot = false;
	/**
	 * A set of restrictions under which this instance of ops operates: <br>
	 * TODO?
	 */
	private int restrictions = 0;
	
	private TextChannel txtsp = (sp==0)?GameData.txtusa:GameData.txtjih;
	/**
	 * Constructor, specifying the number of ops on the card used and the conditions under which they can be used.
	 * @param party is the superpower in charge of these Operations points.
	 * @param ops is the number of Operation Points usable in this instance of Operations.
	 * @param infl indicates whether one can place Influence using this instance of Operations.
	 * @param sc indicates whether one can Realigh using this instance of Operations.
	 * @param t2 indicates whether one can send this instance of Operations to China.
	 * @param restr creates a set of spatial restrictions under which these Operations must operate. Only relevant for Junta and Tear Down This Wall.
	 */
	public Operations(int p, int ops, boolean m, boolean g, boolean t, boolean c, int restr) {
		sp = p;
		opnumber = ops;
		movement = m;
		governance = g;
		terror = t;
		cells = c;
		restrictions = restr;
		txtsp = (sp==0)?GameData.txtusa:GameData.txtjih;
	}
	
	/**
	 * Handles the Operations Command; moved here in order to also handle any events brought about by the decision command.
	 * @param args are the arguments entered into the Operations or Decision command that contain the argument(s) required for an Operation to take place.
	 * @return true if the operation succeeds, false otherwise.
	 */
	public boolean ops(String[] args) {
		String usage = args[1];
		if (sp==0) {
			if (usage.equalsIgnoreCase("m")||usage.equalsIgnoreCase("movement")||usage.equalsIgnoreCase("deploy")) {
				//Deploy takes one origin and one destination, along with the number of troops.
				//checks length
				if (args.length<5) {
					txtsp.sendMessage(":x: From where, to where, and how much?").complete();
					return false;
				}
				//pulls arguments
				int from = MapManager.find(args[2]);
				if (from==-1) {
					txtsp.sendMessage(":x: "+args[2]+" isn't a country or alias of one.").complete();
					return false;
				}
				int to = MapManager.find(args[3]);
				if (to==-1) {
					txtsp.sendMessage(":x: "+args[3]+" isn't a country or alias of one.").complete();
					return false;
				}
				int amt;
				try {
					amt = Integer.parseInt(args[4]);
				}
				catch (Exception e) {
					txtsp.sendMessage(":x: I'd rather not have to deal with fractional troops.").complete();
					return false;
				}
				//checks legality:
				//from must have the specified no. of troops
				int total = (from==0?GameData.trackTroops():MapManager.get(from).countUnits(-2)); //incs NATO and others
				if (total<amt) {
					txtsp.sendMessage(":x: You can't draw troops out of thin air.").complete();
					return false;
				}
				//from cannot disable a WoI in an RC country
				if (MapManager.get(from).rc!=0&&MapManager.get(from).countUnits(-2)-amt<MapManager.get(from).countUnits(1)+5) {
					txtsp.sendMessage(":x: We still have a war to pursue in " + MapManager.get(from).name + ".").complete();
					return false;
				}
				//to must be Muslim (or "the US")
				if (to!=0) {
					if (MapManager.get(to).religion==2) {
						txtsp.sendMessage(":x: " + MapManager.get(to).name + " isn't so much an ally as a diplomatic partner.").complete();
						return false;
					}
					if (MapManager.get(to).religion==3) {
						txtsp.sendMessage(":x: You're going to get a very chilly reception to that.").complete();
						return false;
					}
				//to must be allied or RCable
					if (MapManager.get(to).getPosture()!=1 //not allied
						&&!((MapManager.get(to).getGovernance()==4 //not islamist rule...
						||(to==18&&HandManager.effectActive(37)) //nor iraqi wmd...
						||(to==35&&HandManager.effectActive(39)))&&amt>=6) //nor libyan wmd, and 6 troops or more for a rc
						) { //make casus belli compatible
						txtsp.sendMessage(":x: " + MapManager.get(to).name + " is not going to humor your request to station troops in their land.").complete();
						return false;
					}
				}
				//sufficient ops required
				if (MapManager.get(to).getGovernance()>this.opnumber) {
					if (MapManager.get(to).getGovernance()<=this.opnumber+GameData.getReserves(0)) GameData.changeReserves(0, -2);
					else {
						txtsp.sendMessage(":x: Needs more investment.").complete();
						return false;
					}
				}
				return this.deploy(from, to, amt);
			}
			if (usage.equalsIgnoreCase("g")||usage.equalsIgnoreCase("governance")||usage.equalsIgnoreCase("woi")) {
				
			}
			if (usage.equalsIgnoreCase("t")||usage.equalsIgnoreCase("terror")||usage.equalsIgnoreCase("alert")) {
				
			}
			if (usage.equalsIgnoreCase("c")||usage.equalsIgnoreCase("cells")||usage.equalsIgnoreCase("disrupt")) {
				
			}
			if (usage.equalsIgnoreCase("r")||usage.equalsIgnoreCase("reserves")) {
				
			}
		}
		else { // sp == 1
			if (usage.equalsIgnoreCase("m")||usage.equalsIgnoreCase("movement")||usage.equalsIgnoreCase("travel")) {
				
			}
			if (usage.equalsIgnoreCase("g")||usage.equalsIgnoreCase("governance")||usage.equalsIgnoreCase("jihad")) {
				
			}
			if (usage.equalsIgnoreCase("t")||usage.equalsIgnoreCase("terror")||usage.equalsIgnoreCase("plot")) {
				
			}
			if (usage.equalsIgnoreCase("c")||usage.equalsIgnoreCase("cells")||usage.equalsIgnoreCase("recruit")) {
				
			}
			if (usage.equalsIgnoreCase("r")||usage.equalsIgnoreCase("reserves")) {
				
			}
		}
		txtsp.sendMessage(":x: That's not an action you can take. M, G, T, C, R. Four genres, four operations per side.").complete();
		return false;
	}
	//TODO operations
	/**
	 * Conducts a single deploy operation. 
	 * @param from is the origin.
	 * @param to is the destination.
	 * @param num is the number of troops.
	 * @return the success of the operation.
	 */
	public boolean deploy(int from, int to, int num) {
		return false;
	}
	/**
	 * Conducts a travel operation worth one cell.
	 * @param from
	 * @param to
	 * @return the success of the operation.
	 */
	public boolean travel(int from, int to) {
		return false;
	}
	/**
	 * Conducts a whole travel operation.
	 * @param from is the entire set of origin countries.
	 * @param to is the entire set of destination countries.
	 * @return the success of the operation.
	 */
	public boolean bulkTravel(int[] from, int[] to) {
		return false;
	}
	/**
	 * Conducts a single War of Ideas.
	 * @param target is the target of the WoI.
	 * @return the success of the operation.
	 */
	public boolean woi(int target) {
		return false;
	}
	/**
	 * Conducts a jihad operation. 
	 * @param target is the set of countries targeted.
	 * @param amts is the number of dice used on each given country in order. 
	 * @param major is the determinant of whether each country is being targeted by Major Jihad and is thus eligible for Islamism. 
	 * @return the success of the operation.
	 */
	public boolean jihad(int[] target, int[] amts, boolean[] major) {
		return false;
	}
	/**
	 * Conducts a single alert.
	 * @param target
	 * @return
	 */
	public boolean alert(int target) {
		return false;
	}
	/**
	 * Conducts a single plot. Since juking is possible, placement of plots is in a separate command. 
	 * @param target is the target country.
	 * @return
	 */
	public boolean plot(int target) {
		return false;
	}
	
	/**
	 * Places one successful plot. 
	 * @param target is the target country.
	 * @return
	 */
	public boolean placePlot(int target, int level) {
		return false;
	}
	/**
	 * Resolves one unblocked plot.
	 */
	public static boolean resolvePlot(int target, int level) {
		return false;
	}
	/**
	 * Conducts one disrupt operation. Preferentially targets active cells for optimality.
	 * @param target is the target country.
	 * @return
	 */
	public boolean disrupt(int target) {
		return false;
	}
	/**
	 * Conducts one recruit operation. Serial.
	 * @param target
	 * @return
	 */
	public boolean recruit(int target) {
		return false;
	}
	/**
	 * Conducts a whole recruit operation. Serial.
	 * @param target
	 * @return
	 */
	public boolean bulkRecruit(int[] target, int[] amts) {
		return false;
	}
}

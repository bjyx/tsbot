package cards;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import commands.TimeCommand;
import events.CardEmbedBuilder;
import events.Decision;
import events.ForeignCurrencyDebtBurden;
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
		if (usage.equals("influence")||usage.equals("i")) {
			if (args.length<3) {
				txtsp.sendMessage(":x: Usage: [origin][destination][amount].").complete();
				return false;
			}
			if (args.length%2!=0) {
				txtsp.sendMessage(":x: An influence value must be associated with every listed country.").complete();
				return false;
			}
			int[] countries = new int[(args.length-2)/2];
			int[] amt = new int[(args.length-2)/2];
			for (int i=2; i<args.length; i+=2) {
				countries[(i-2)/2] = MapManager.find(args[i]);
				if (countries[(i-2)/2]==-1) {
					txtsp.sendMessage(":x: "+args[i]+" isn't a country or alias of one.").complete();
					return false;
				}
				try {
					amt[(i-2)/2] = Integer.parseInt(args[i+1]);
				}
				catch (NumberFormatException err) {
					txtsp.sendMessage(":x: NaN").complete();
					return false;
				}
				if (amt[(i-2)/2]<=0) {
					txtsp.sendMessage(":x: Positive integers only, please - this is not De-Stalinization.").complete();
					return false;
				}
			}
			return this.influence(countries, amt);
		}
		if (usage.equals("check")||usage.equals("c")) {
			if (args.length<3) {
				txtsp.sendMessage(":x: Where?").complete();
				return false;
			}
			int country = MapManager.find(args[2]);
			if (country==-1) {
				txtsp.sendMessage(":x: "+args[2]+" isn't a country or alias of one.").complete();
				return false;
			}
			if (HandManager.effectActive(58)&&!ahbr) {
				ahbr=true;
				if (args.length>3) { 
					if (MapManager.get(country).inRegion(0)&&args[3].equalsIgnoreCase("habsburg")) {
						opnumber++;
						restrictions = 0;
					}
				}
			}
			
			return this.realignment(country);
		}
		//legacy
		if (usage.equals("t")||usage.equals("tsquare")||usage.equalsIgnoreCase("t2")) {
			if (GameData.getT2(sp)==8) {
				txtsp.sendMessage(":x: This is as far as you go on the square. How did you get here, anyways?").complete();
				return false;
			}
			return this.space();
		}
		txtsp.sendMessage(":x: You clearly aren't spacing the card. Why not use it for operations?").complete();
		return false;
	}
	//TODO operations
	/**
	 * Conducts a deploy operation. 
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
	
	public boolean woi(int target) {
		return false;
	}
	
	public boolean jihad(int target) {
		return false;
	}
	
	public boolean alert(int target) {
		return false;
	}
	
	public boolean plot(int target) {
		return false;
	}
	
	public boolean disrupt(int target) {
		return false;
	}
	
	public boolean recruit(int target) {
		return false;
	}
}

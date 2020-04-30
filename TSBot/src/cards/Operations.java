package cards;

import java.awt.Color;
import java.util.ArrayList;

import events.CardEmbedBuilder;
import events.Chernobyl;
import events.Decision;
import game.Die;
import game.GameData;
import logging.Log;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.core.entities.TextChannel;
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
	 * If the current set of ops can be used for influence placement.
	 */
	private boolean influence = true;
	/**
	 * If the current set of ops can be used for realignments.
	 */
	private boolean realignment = true;
	/**
	 * If the current set of ops can be used for a coup.
	 */
	private boolean coupdetat = true;
	/**
	 * If the current set of ops can be sent to space.
	 */
	private boolean spaceable = false;
	/**
	 * If used on a coup, whether this set of ops contributes to Military Operations.
	 */
	private boolean free = false;
	/**
	 * The number of ops available to use.
	 */
	public int opnumber = -1;
	/**
	 * The number of ops required to send things into space. This is the same for both tracks.
	 */
	public static final int[] spaceOps = {2, 2, 2, 2, 3, 3, 3, 4};
	/**
	 * The number of VP obtained by being the <b> first </b> to advance to that stage.
	 */
	public static final int[] spaceVP = {2, 0, 2, 0, 3, 0, 4, 2};
	/**
	 * The number of VP obtained by being the <b> second </b> to advance to that stage.
	 */
	public static final int[] spaceVP2 = {1, 0, 0, 0, 1, 0, 2, 0};
	/**
	 * The number of VP obtained by being the <b> first </b> to advance to that stage. This is used under the alternate track.
	 */
	public static final int[] altspaceVP = {2, 0, 2, 0, 3, 0, 0, 4};
	/**
	 * The number of VP obtained by being the <b> second </b> to advance to that stage. This is used under the alternate track.
	 */
	public static final int[] altspaceVP2 = {1, 0, 0, 0, 1, 0, 0, 2};
	/**
	 * The number you must roll under or at to advance to the next stage of the space race. This is the same for both tracks.
	 */
	public static final int[] spaceRoll = {3, 4, 3, 4, 3, 4, 3, 2};
	/**
	 * Whether all ops are being used in Asia--only relevant for the China Card.
	 */
	private boolean allasia = true;
	/**
	 * Whether all ops are being used in Southeast Asia--only relevant for the op boost from the Vietnam Revolts.
	 */
	private boolean allsea = true;
	/**
	 * A set of restrictions under which this instance of ops operates: <br>
	 * 0 - no restrictions <br>
	 * 47 - Junta - restrictions to Latin America <br>
	 * 96 - Tear Down This Wall - restrictions to Europe
	 * 136 - America's Backyard - restrictions to Central America
	 */
	private int restrictions = 0;
	/**
	 * The set of countries in which the USA is allowed to place influence.
	 */
	public static ArrayList<Integer> allowedUSA;
	/**
	 * The set of countries in which the USSR is allowed to place influence.
	 */
	public static ArrayList<Integer> allowedSUN;
	/**
	 * The superpower that has the alternate space ability 4, and whether he has used it or not. <br>
	 * -1 - no one has access <br>
	 * 0 - US has access <br>
	 * 1 - USSR has access <br>
	 * 2 - US has access, spent <br>
	 * 3 - USSR has access, spent <br>
	 */
	public static int discount = -1;
	/**
	 * The superpower that has the alternate space ability 6, and whether he has used it or not. <br>
	 * -1 - no one has access <br>
	 * 0 - US has access <br>
	 * 1 - USSR has access <br>
	 * 2 - US has access, spent <br>
	 * 3 - USSR has access, spent <br>
	 */
	public static int coupReroll = -1;
	/**
	 * Data from the coup roll, saved in case of altspace six.
	 */
	public static int die6 = 0;
	/**
	 * Data from the coup roll, saved in case of altspace six.
	 */
	public static int amt6 = 0;
	/**
	 * Data from the coup roll, saved in case of altspace six.
	 */
	public static int target6 = -1;
	/**
	 * Whether a coup has occurred this action round.
	 */
	public static boolean tsarbomba = false;
	private TextChannel txtsp = (sp==0)?GameData.txtusa:GameData.txtssr;
	/**
	 * Constructor, specifying the number of ops on the card used and the conditions under which they can be used.
	 * @param superpower is the superpower in charge of these Operations points.
	 * @param ops is the number of Operation Points usable in this instance of Operations.
	 * @param infl indicates whether one can place Influence using this instance of Operations.
	 * @param realign indicates whether one can Realign using this instance of Operations.
	 * @param coup indicates whether one can conduct a Coup using this instance of Operations.
	 * @param space indicates whether one can send this instance of Operations to space.
	 */
	public Operations(int superpower, int ops, boolean infl, boolean realign, boolean coup, boolean space, boolean f) {
		sp = superpower;
		opnumber = ops;
		influence = infl;
		realignment = realign;
		coupdetat = coup;
		spaceable = space;
		free = f;
		restrictions = 0;
		txtsp = (sp==0)?GameData.txtusa:GameData.txtssr;
	}
	/**
	 * Constructor, specifying the number of ops on the card used and the conditions under which they can be used.
	 * @param superpower is the superpower in charge of these Operations points.
	 * @param ops is the number of Operation Points usable in this instance of Operations.
	 * @param infl indicates whether one can place Influence using this instance of Operations.
	 * @param realign indicates whether one can Realigh using this instance of Operations.
	 * @param coup indicates whether one can conduct a Coup using this instance of Operations.
	 * @param space indicates whether one can send this instance of Operations to space.
	 * @param restr creates a set of spatial restrictions under which these Operations must operate. Only relevant for Junta and Tear Down This Wall.
	 */
	public Operations(int superpower, int ops, boolean infl, boolean realign, boolean coup, boolean space, boolean f, int restr) {
		sp = superpower;
		opnumber = ops;
		influence = infl;
		realignment = realign;
		coupdetat = coup;
		spaceable = space;
		free = f;
		restrictions = restr;
		txtsp = (sp==0)?GameData.txtusa:GameData.txtssr;
	}
	/**
	 * Handles the Operations Command; moved here in order to also handle any events brought about by the decision command.
	 * @param args are the arguments entered into the Operations or Decision command that contain the argument(s) required for an Operation to take place.
	 * @return true if the operation succeeds, false otherwise.
	 */
	public boolean ops(String[] args) {
		allasia = true;
		allsea = true;
		String usage = args[1];
		if (usage.equals("influence")||usage.equals("i")) {
			if (args.length<3) {
				txtsp.sendMessage(":x: To where?").complete();
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
				if (MapManager.get(countries[(i-2)/2]).region==9&&countries[(i-2)/2]!=86) {
					txtsp.sendMessage(":x: You and your rival are out of play for influence markers.").complete();
					return false;
				}
				if (countries[(i-2)/2]==86 && sp==0) {
					txtsp.sendMessage(":x: One does not simply support the Nationalists after Turn Zero.").complete();
					return false;
				}
				if (countries[(i-2)/2]==86 && amt[(i-2)/2]>3-MapManager.get(86).influence[1]) {
					txtsp.sendMessage(":x: The Chinese Civil War only needs three influence markers to win.").complete();
					return false;
				}
				if (HandManager.effectActive(94) && Chernobyl.regionBan[MapManager.get(countries[(i-2)/2]).region]) {
					txtsp.sendMessage(":x: Maybe you should deal with that nuclear reactor first.").complete();
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
		if (usage.equals("realignment")||usage.equals("r")) {
			int country = MapManager.find(args[2]);
			if (country==-1) {
				txtsp.sendMessage(":x: "+args[2]+" isn't a country or alias of one.").complete();
				return false;
			}
			if (MapManager.get(country).region==9) {
				txtsp.sendMessage(":x: You cannot realign this country.").complete();
				return false;
			}
			if (MapManager.get(country).region!=7&&MapManager.get(country).region!=8&&restrictions==47) {
				txtsp.sendMessage(":x: Juntas are only a thing in Latin America.").complete();
				return false;
			}
			if (MapManager.get(country).region>=3&&restrictions==96) {
				txtsp.sendMessage(":x: Aren't you trying to tear down the Iron Curtain?").complete();
				return false;
			}
			if (MapManager.get(country).region!=7&&restrictions==136) {
				txtsp.sendMessage(":x: That's a bit far to be part of your backyard.").complete();
				return false;
			}
			return this.realignment(country);
		}
		if (usage.equals("coup")||usage.equals("c")) {
			int country = MapManager.find(args[2]);
			if (country==-1) {
				txtsp.sendMessage(":x: "+args[2]+" isn't a country or alias of one.").complete();
				return false;
			}
			if (MapManager.get(country).region==9) {
				txtsp.sendMessage(":x: You cannot try to coup this country.").complete();
				return false;
			}
			if (MapManager.get(country).region!=7&&MapManager.get(country).region!=8&&restrictions==47) {
				txtsp.sendMessage(":x: Juntas are only a thing in Latin America.").complete();
				return false;
			}
			if (MapManager.get(country).region>=3&&restrictions==96) {
				txtsp.sendMessage(":x: Aren't you trying to tear down the Iron Curtain?").complete();
				return false;
			}
			return this.coup(country);
		}
		//no longer legacy, but still cannot be accessed via the OpsCommand
		if (usage.equals("s")||usage.equals("space")) {
			if (GameData.getSpace(sp)==8) {
				txtsp.sendMessage(":x: This is as far as you go towards the Final Frontier. How did you get here, anyways?").complete();
				return false;
			}
			return this.space();
		}
		txtsp.sendMessage(":x: You clearly aren't spacing the card. Why not use it for operations?").complete();
		return false;
	}
	
	/**
	 * Places the given amounts of influence in their respective countries.
	 * @param countries is an array containing the IDs of the countries targeted.
	 * @param amt is an array containing the amount of influence used on each country.
	 */
	public boolean influence(int[] countries, int[] amt) {
		if (!influence) {
			txtsp.sendMessage(":x: You must do something else with these ops.").complete();
			return false;
		}
		int ops = 0;
		
		for (int i=0; i<countries.length; i++) {
			ops += (Math.min(amt[i], 
					Math.max(0,MapManager.get(countries[i]).influence[(sp+1)%2]
							- MapManager.get(countries[i]).influence[sp] //get difference between you and opponent op values
							- MapManager.get(countries[i]).stab+1))) //subtract stab, add 1; this represents the amount of influence needed to break control of a country.
							+ amt[i]; //add the amount to add to the country
			/*
			 * Say that you're the USSR playing into Thailand 3/0 with the China Card under Vietnam Revolts. (control break and additional).
			 * That's six ops total for four influence:
			 * ops = Math.min(4, Math.max(0, 3-0-2+1=2)) + 4
			 * 	   = 2 + 4 = 6.
			 * 
			 * Say that you're the USA playing into a 1/5 Angola with Nuclear Test Ban (no reduced cost).
			 * That's four ops for two influence:
			 * ops = Math.min(2, Math.max(0, (5-1-1+1=4))) + 2
			 *     = 2 + 2 = 4.
			 *     
			 * Say that you're the USSR playing into an empty Pakistan with COMECON (reduced cost only).
			 * That's three ops for three influence:
			 * ops = Math.min(3, Math.max(0, (0-0-2+1))) + 3
			 *     = 0 + 3 = 3.
			 */
			if (MapManager.get(countries[i]).region!=5) {
				allsea=false;
				if(MapManager.get(countries[i]).region!=4) {
					allasia=false;
				}
			}
			if (MapManager.get(countries[i]).influence[sp]!=0);
			else {
				boolean flag = false;
				for (int c : MapManager.get(countries[i]).adj) {
					if (MapManager.get(c).influence[sp]!=0) flag = true;
				}
				if (!flag) {
					txtsp.sendMessage(":x: Can you even reach that country with your influence?...").complete(); 
					return false;
				}
			}
			if ((sp==0&&(!allowedUSA.contains(countries[i])))||(sp==1&&(!allowedSUN.contains(countries[i])))) {
				txtsp.sendMessage(":x: You'll have to stop right there—the influence must have been there from the start. Whatever influence you got from the recent ouster of Galtieri does not count.").complete(); //Rule 6.1.1 - the influence marker has to be there at the start of the turn
				return false;
			}
		}
		if (allasia && HandManager.activecard==6) ops--; //handles China Card  case
		if (allsea && HandManager.Effects.contains(9)&&sp==1) ops--;
		if (ops<opnumber) {
			txtsp.sendMessage(":x: Endeavor to use all available ops.").complete();
			return false;
		}
		CardEmbedBuilder builder = new CardEmbedBuilder();
		if (ops>opnumber) {
			if (ops==opnumber+1 && GameData.hasAbility(sp, 4, true)&&discount==sp) {
				for (int i=0; i<countries.length; i++) {
					if (MapManager.get(countries[i]).isControlledBy()==(sp+1)%2) { //applies without loss of generality
						discount += 2;
						Log.writeToLog("Space Race Ability 4 used.");
						builder.addField("Space Race Bonus", "You had an easier time infiltrating the government of "+MapManager.get(countries[i]).name+".", false);
						break;
					}
				}
				if (discount < 2) {
					txtsp.sendMessage(":x: Overstreching yourself is never good.").complete();
					return false;
				}
			}
			else {
				txtsp.sendMessage(":x: Overstreching yourself is never good.").complete();
				return false;
			}
		}
		realignment = false;
		coupdetat = false;
		spaceable = false;
		Log.writeToLog("Operations: Influence");
		builder.setTitle("Influence Placement").setColor(sp==0?Color.blue:Color.red);
		for (int i=0; i<countries.length; i++) {
			builder.changeInfluence(countries[i], sp, amt[i]);
			if (GameData.ccw && MapManager.get(86).isControlledBy()==1 && HandManager.China==-1) {
				HandManager.China=1; //China activating condition
				Log.writeToLog("China Card active.");
				builder.addField("Huainan Campaign", "The communists have won the Chinese Civil War. " + CardList.getCard(31) + ", " + CardList.getCard(58) + ", and " + CardList.getCard(35) + " are now playable for the event. The China Card is now playable by the Soviets.", false);
			}
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
		return true;
	}
	/**
	 * Realigns the given country in an attempt to remove influence.
	 * @param country is the country targeted by the realignment.
	 */
	public boolean realignment(int country) {
		if (!realignment) {
			txtsp.sendMessage(":x: You must do something else with these ops.").complete();
			return false;
		}
		if (!coupdetat&&!influence&&!spaceable) { //this should only occur if you are locked into realigning
			if (restrictions==47) {
				boolean flag = true;
				for (int i=64; i<84; i++) {
					if (MapManager.get(i).influence[(sp+1)%2]>0) {
						flag = false;
						break;
					}
				}
				if (flag) {
					txtsp.sendMessage("Oh, it seems you can no longer realign in this region.").complete();
					return true;
				}
			}
			else if (restrictions==96) {
				boolean flag = true;
				for (int i=0; i<21; i++) {
					if (MapManager.get(i).influence[1]>0) {
						flag = false;
						break;
					}
				}
				if (flag) {
					txtsp.sendMessage("Oh, it seems you can no longer realign in this region.").complete();
					return true;
				}
			}
			else if (restrictions==136) {
				boolean flag = true;
				for (int i=64; i<74; i++) {
					if (MapManager.get(i).influence[1]>0) {
						flag = false;
						break;
					}
				}
				if (flag) {
					txtsp.sendMessage("Oh, it seems you can no longer realign in this region.").complete();
					return true;
				}
			}
			else {
				boolean flag = true;
				for (int i=0; i<84; i++) {
					if (!MapManager.get(i).checkIsCoupable()&&!HandManager.effectActive(1003)) continue;
					if (MapManager.get(i).inRegion(0)&&HandManager.effectActive(21)&&MapManager.get(i).isControlledBy()==0&&!(i==8&&HandManager.Effects.contains(17)) && !(i==19&&HandManager.Effects.contains(55)) && sp == 1) continue;
					if (i==36&&sp==1&&HandManager.effectActive(27)) continue;
					if (MapManager.get(i).influence[(sp+1)%2]>0) {
						flag = false;
						break;
					}
				}
				if (flag) {
					txtsp.sendMessage("Dear god, how did this happen!? Your enemy doesn't have influence you can realign on the board.").complete();
					return true;
				}
			}
		}
		if (MapManager.get(country).inRegion(0) && HandManager.effectActive(21) && MapManager.get(country).isControlledBy()==0 && (country!=8 || !HandManager.Effects.contains(17)) && (country != 19 || !HandManager.Effects.contains(55)) && sp == 1) {
			txtsp.sendMessage(":x: This country is under the protection of NATO.").complete();
			return false;
		}
		if (country==36 && sp==1 && HandManager.effectActive(27)) {
			txtsp.sendMessage(":x: Japan's under one defense pact you don't want to violate...").complete();
			return false;
		}
		if (!HandManager.effectActive(1003)&&(!MapManager.get(country).checkIsCoupable() && !(MapManager.get(country).region<=2 && restrictions==96))) {
			txtsp.sendMessage(":x: DEFCON restricts you from realigning this country.").complete();
			return false;
		}
		if (MapManager.get(country).influence[(sp+1)%2]==0) {
			txtsp.sendMessage(":x: This country is fresh out of foreign influence, I can tell you that.").complete();
			return false;
		}
		if (!(MapManager.get(country).region==5)) {
			allsea=false;
			if(!(MapManager.get(country).region==4)) {
				allasia=false;
			}
		}
		if (opnumber<1) {
			if (allsea&&MapManager.get(country).region==5&&HandManager.Effects.contains(9)&&sp==1) {
				opnumber++;
				allsea=false;
			}
			else if (allasia&&(MapManager.get(country).region==4||allasia&&MapManager.get(country).region==5)&&HandManager.activecard==6) {
				opnumber++;
				allasia=false;
			}
			else {
				txtsp.sendMessage("You've run out of ops...").complete();
				opnumber--;
				return true;
			}
		}
		influence = false;
		coupdetat = false;
		spaceable = false;
		Log.writeToLog("Realignment in " + MapManager.get(country).iso.toUpperCase());
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Realignment")
			.setDescription("Target: "+ MapManager.get(country))
			.setColor(sp==0?Color.blue:Color.red);
		int[] rolls = new int[] {new Die().roll(), new Die().roll()};
		String[] modifiers = {"",""};
		if (MapManager.get(country).influence[0]>MapManager.get(country).influence[1]) {
			rolls[0]++;
			modifiers[0] += MapManager.get(country);
			Log.writeToLog("+1 to US (more influence in target).");
		}
		if (MapManager.get(country).influence[0]<MapManager.get(country).influence[1]) {
			rolls[1]++;
			modifiers[1] += MapManager.get(country);
			Log.writeToLog("+1 to SU (more influence in target).");
		}
		if (HandManager.effectActive(93)) {
			rolls[0]--;
			builder.addField("Iran-Contra Affair!", "-1", false);
			Log.writeToLog("-1 to US (Iran-Contra).");
		}
		for (int adj : MapManager.get(country).adj) {
			if (adj==84) {
				rolls[0]++;
				modifiers[0] += MapManager.get(adj);
				Log.writeToLog("+1 to US (control of US).");
			}
			else if (adj==85) {
				rolls[1]++;
				modifiers[1] += MapManager.get(adj);
				Log.writeToLog("+1 to SU (control of SU).");
			}
			else {
				if (MapManager.get(adj).isControlledBy()!=-1) {
					rolls[MapManager.get(adj).isControlledBy()]++;
					modifiers[MapManager.get(adj).isControlledBy()] += MapManager.get(adj);
					Log.writeToLog("+1 to "+(MapManager.get(adj).isControlledBy()==0?"US":"SU")+" (control of "+MapManager.get(adj).iso.toUpperCase()+").");
				}
			}
		}
		Log.writeToLog("Final Rolls: "+rolls[0]+"/"+rolls[1]);
		builder.addField("Rolls", ":flag_us::" + CardEmbedBuilder.numbers[rolls[0]] + "::heavy_plus_sign:" + modifiers[0] + "\n"+MapManager.get(85)+":" + CardEmbedBuilder.numbers[rolls[1]] + "::heavy_plus:" + modifiers[1], false);
		if (rolls[1]>rolls[0]) builder.changeInfluence(country, 0, -(rolls[1]-rolls[0]));
		if (rolls[0]>rolls[1]) builder.changeInfluence(country, 1, -(rolls[0]-rolls[1]));
		GameData.txtchnl.sendMessage(builder.build()).complete();
		opnumber--;
		return false;
	}
	/**
	 * Coups the given country, to attempt to sway that country to the side of sp.
	 * @param country is the country targeted by the coup.
	 */
	public boolean coup(int country) {
		if (!coupdetat) {
			txtsp.sendMessage(":x: You must do something else with these ops.").complete();
			return false;
		}
		if (!HandManager.effectActive(1003)&&(!MapManager.get(country).checkIsCoupable()&&!(MapManager.get(country).region<=2 && restrictions==96))) {
			txtsp.sendMessage(":x: DEFCON restrictions disallow you from couping this nation.").complete();
			return false;
		}
		if (MapManager.get(country).region<=2 && HandManager.effectActive(21) && MapManager.get(country).isControlledBy()==0 && (country!=8 || !HandManager.Effects.contains(17)) && (country != 19 || !HandManager.Effects.contains(55)) && sp == 1) {
			txtsp.sendMessage(":x: This country is under the protection of NATO.").complete();
			return false;
		}
		if (country==36 && sp==1 && HandManager.effectActive(27)) {
			txtsp.sendMessage(":x: Japan's under one defense pact you don't want to violate...").complete();
			return false;
		}
		if (MapManager.get(country).region<=2  && HandManager.effectActive(87) && sp == 1) {
			txtsp.sendMessage(":x: Gorbachev is in power, and he isn't repeating the mistakes of 1956 and 1968.").complete();
			return false;
		}
		if (MapManager.get(country).influence[(sp+1)%2]==0) {
			txtsp.sendMessage(":x: This country is fresh out of foreign influence, I can tell you that.").complete();
			return false;
		}
		if (HandManager.Effects.contains(400+sp)) {
			GameData.txtchnl.sendMessage(new CardEmbedBuilder().setTitle((sp==0?"American ":"Soviet ") + "Missiles Launched Against " + MapManager.get(country).name)
					.setDescription((sp==0?"Soviet":"American")+" retaliation imminent")
					.setColor(Color.BLACK)
					.setFooter("\"You have ignited a nuclear war. And no, there is no animated display "
							+ "or a mushroom cloud with parts of bodies flying through the air. "
							+ "We do not reward failure.\"\n"
							+ "- *Balance of Power* (video game)", Launcher.url("people/victory_us1.png"))
					.build()).complete();
			GameData.endGame(sp==0?1:0, 1); //NUCLEAR WAR
			return false;
		}
		if (!(MapManager.get(country).region==5)) {
			allsea=false;
			if(!(MapManager.get(country).region==4)) {
				allasia=false;
			}
		}
		if (allsea&&HandManager.Effects.contains(9)&&sp==1) opnumber++;
		if (allasia&&HandManager.activecard==6) opnumber++;
		realignment = false;
		influence = false;
		spaceable = false;
		if (coupReroll==sp) {
			die6 = new Die().roll();
			amt6 = opnumber + die6 - MapManager.get(country).stab*2 - (HandManager.effectActive(43)?1:0);
			if (HandManager.effectActive(690+sp) && MapManager.get(country).region>6) {
				amt6++;
			}
			if (HandManager.effectActive(690+((sp+1)%2)) && MapManager.get(country).region>6) {
				amt6--;
			}
			if (GameData.hasAbility((sp+1)%2, 7, true)) {
				amt6--;
			}
			target6=country;
			if (sp==0) GameData.txtusa.sendMessage("Your coup roll in "+MapManager.get(target6).name+" is :"+CardEmbedBuilder.numbers[die6]+":, resulting in "+(amt6>0?amt6+" Influence Points of damage.":"no change to the regime.") + " Write `TS.decide reroll` to request a reroll with your ABMs, or `TS.decide accept` to leave it as is.").complete();
			GameData.dec = new Decision(sp, 4176);
			try {
				synchronized(GameData.sync) {
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}
		Die die = new Die();
		return this.coupPreDet(country, die.roll());
	}
	
	public boolean coupPreDet(int country, int die) {
		if (GameData.phasing()==0) tsarbomba = true;
		Log.writeToLog("Operations: Coup in " + MapManager.get(country).iso.toUpperCase());
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Coup d'État!")
			.setDescription("Target: "+ MapManager.get(country))
			.setColor(sp==0?Color.blue:Color.red);
		if (MapManager.get(country).isBattleground) {
			if (HandManager.effectActive(41) && sp==0) builder.addField("Nuclear Submarines!", "This coup does not affect DEFCON.", false);
			else builder.changeDEFCON(-1);
		}
		if (!free) builder.addMilOps(sp, opnumber);
		int amt = opnumber + die - MapManager.get(country).stab*2;
		if (HandManager.effectActive(43)) {
			builder.addField("SALT Negotiations","-1",false);
			amt--;
			Log.writeToLog("SALT: -1");
		}
		if (HandManager.effectActive(690+sp) && MapManager.get(country).region>6) {
			builder.addField("Latin American Death Squads", "+1", false);
			amt++;
			Log.writeToLog("LADS: +1");
		}
		if (HandManager.effectActive(690+((sp+1)%2)) && MapManager.get(country).region>6) {
			builder.addField("Latin American Death Squads", "-1", false);
			amt--;
			Log.writeToLog("LADS: -1");
		}
		if (HandManager.effectActive(109)) {
			Log.writeToLog("Yuri and Samantha:");
			builder.addField("Yuri and Samantha", "", false);
			builder.changeVP(-1);
		}
		if (GameData.hasAbility((sp+1)%2, 7, true)) {
			Log.writeToLog("Neutron Bombs: -1");
			builder.addField("Enemy Neutron Bombs", "-1", false);
			amt--;
		}
		builder.addField("Roll: :" + CardEmbedBuilder.numbers[die] + ":", CardEmbedBuilder.intToEmoji(amt), false);
		Log.writeToLog("Roll: "+die);
		Log.writeToLog("Final Total: " + amt);
		if (amt>0) {
			if (amt>MapManager.get(country).influence[(sp+1)%2]) {
				int x = MapManager.get(country).influence[(sp+1)%2];
				builder.changeInfluence(country, (sp+1)%2, -x);
				builder.changeInfluence(country, sp, amt-x);
			}
			else {
				builder.changeInfluence(country, (sp+1)%2, -amt);
			}
		}
		else { //oof
			builder.addField(":( Failure!", "", false);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
		return true;
	}
	/**
	 * Sends the card to space.
	 * @return the success of the operation.
	 */
	public boolean space() {
		if (!spaceable) {
			txtsp.sendMessage(":x: You must do something else with these ops.").complete();
			return false;
		}
		realignment = false;
		coupdetat = false;
		influence = false;
		Log.writeToLog("Space Race: ");
		GameData.toSpace(sp);
		int spaceLevel = GameData.getSpace(sp);
		int die = new Die().roll();
		CardEmbedBuilder builder = new CardEmbedBuilder();
		Log.writeToLog("Roll: " + die);
		builder.setTitle("Space Race")
		.setDescription("Card used: " + CardList.getCard(HandManager.activecard));
		if (HandManager.effectActive(124) && sp==1) {
			builder.addField("Laika","-1",false);
			die--;
			Log.writeToLog("Laika: -1");
		}
		if (die <= spaceRoll[spaceLevel]) {
			builder.setColor(sp==0?Color.blue:Color.red)
				.addField("Roll: :"+CardEmbedBuilder.numbers[die]+":",getSpaceNames(spaceLevel, sp),false);
			if (GameData.aheadInSpace()==(sp+1)%2) {
				builder.changeVP(-(sp*2-1)*spaceVP2[spaceLevel]);
			}
			else builder.changeVP(-(sp*2-1)*spaceVP[spaceLevel]);
			GameData.addSpace(sp);
		}
		else {
			builder.setColor(Color.ORANGE);
			builder.addField("Roll: :"+CardEmbedBuilder.numbers[die]+":","Failure.",false);
		}
		if (spaceLevel<3&&GameData.getSpace(sp)>=3&&sp==1&&HandManager.removeEffect(124)) {
			builder.addField("Vostok 1", "The Soviets have acquired the capability to send a man into space. They will no longer receive a -1 bonus to their space race die rolls.", false);
			Log.writeToLog("Laika no longer active.");
		}
		if (spaceLevel<4&&GameData.getSpace(sp)>=4) {
			if (GameData.getSpace((sp+1)%2)<4) {
				discount = sp;
			}
			else {
				discount = -1;
			}
		}
		if (spaceLevel<6&&GameData.getSpace(sp)>=6) {
			if (GameData.getSpace((sp+1)%2)<6) {
				coupReroll = sp;
			}
			else {
				coupReroll = -1;
			}
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
		return true;
	}
	/**
	 * Flavor text regarding the space race goes here.
	 * @param spaceLevel is {@code s}'s advancement on the race.
	 * @return a string, with relevant flavor text.
	 */
	public static String getSpaceNames(int spaceLevel, int s) {
		if (GameData.altspace) {
			if (s==0) {
				if (spaceLevel==0) return "Mk-21 tested.";
				if (spaceLevel==1) return "Explorer 1 launched.";
				if (spaceLevel==2) return "Atlas developed.";
				if (spaceLevel==3) return "\"Houston, Tranquility Base here. The Eagle has landed.\" \n- Neil Armstrong, 1969";
				if (spaceLevel==4) return "Minuteman III developed.";
				if (spaceLevel==5) return "Nike X active.";
				if (spaceLevel==6) return "W66 developed.";
				if (spaceLevel==7) return "SDI initiated.";
			}
			if (s==1) {
				if (spaceLevel==0) return "RDS-37 tested.";
				if (spaceLevel==1) return "Sputnik launched.";
				if (spaceLevel==2) return "R-7 developed.";
				if (spaceLevel==3) return "N1 launched.";
				if (spaceLevel==4) return "R-36 developed.";
				if (spaceLevel==5) return "A35 active.";
				if (spaceLevel==6) return "53T6 developed.";
				if (spaceLevel==7) return "SDI initiated.";
			}
		}
		if (s==0) {
			if (spaceLevel==0) return "Explorer 1 launched.";
			if (spaceLevel==1) return "AM-18 launched.";
			if (spaceLevel==2) return "\"I just wanted to be the first one to fly for America, not because I'd end up in the pages of history books.\" \n- Alan Shepard, 1991";
			if (spaceLevel==3) return "\"Godspeed, John Glenn.\"\n- Scott Carpenter, 1962";
			if (spaceLevel==4) return "Lunar Orbiter 1 launched.";
			if (spaceLevel==5) return "\"Houston, Tranquility Base here. The Eagle has landed.\" \n- Neil Armstrong, 1969";
			if (spaceLevel==6) return "Space Shuttle launched.";
			if (spaceLevel==7) return "Skylab launched.";
		}
		if (s==1) {
			if (spaceLevel==0) return "Sputnik launched.";
			if (spaceLevel==1) return "Sputnik 2 launched.";
			if (spaceLevel==2) return "\"Poyekhali [Let's go]!\" \n- Yuri Gagarin, 1961";
			if (spaceLevel==3) return "\"I am a friend, comrades, a friend!\" \n-Yuri Gagarin, 1961";
			if (spaceLevel==4) return "Luna 10 launched.";
			if (spaceLevel==5) return "N1 launched.";
			if (spaceLevel==6) return "Success.";
			if (spaceLevel==7) return "Salyut 6 launched.";
		}
		return null;
	}
	
	public static ArrayList<Integer> influencePossible (int sp) {
		ArrayList<Integer> x = new ArrayList<Integer>();
		for (int i=0; i<84; i++) {
			if (MapManager.get(i).influence[sp]>0) x.add(i);
			else for (Integer c : MapManager.get(i).adj) {
				if (MapManager.get(c).influence[sp]>0) {
					x.add(i);
					break;
				}
			}
		}
		if (GameData.ccw && MapManager.get(86).influence[sp]<3&&sp==1) x.add(86);
		return x;
	}
}

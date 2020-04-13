package cards;

import java.awt.Color;
import java.util.ArrayList;

import events.CardEmbedBuilder;
import game.Die;
import game.GameData;
import logging.Log;
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
	 * If the current set of ops can be used for support checks.
	 */
	private boolean supportcheck = true;
	/**
	 * If the current set of ops can be T^2ed.
	 */
	private boolean tsquare = false;
	/**
	 * The number of ops available to use.
	 */
	public int opnumber = -1;
	/**
	 * The number of support checks left on the card. 
	 */
	private int remainingsc = 2;
	/**
	 * The threshold required to T2 for the Democrat.
	 */
	public static final int[] spaceOpsD = {5, 5, 6, 6, 7, 8, 9, 10};
	/**
	 * The threshold required to T2 for the Communist.
	 */
	public static final int[] spaceOpsC = {6, 6, 7, 7, 8, 7, 6, 5};
	/**
	 * Whether the superpower has already tried to T-square for this particular square.
	 */
	public static boolean[] tried = {false, false};
	/**
	 * A set of restrictions under which this instance of ops operates: <br>
	 * TODO
	 */
	private int restrictions = 0;
	/**
	 * The set of countries in which the USA is allowed to place influence.
	 */
	//public static ArrayList<Integer> allowedUSA;
	/**
	 * The set of countries in which the USSR is allowed to place influence.
	 */
	//public static ArrayList<Integer> allowedSUN;
	/**
	 * The superpower that has T2 ability 7, and whether he has used it or not. <br>
	 * -1 - no one has access <br>
	 * 0 - Dem has access <br>
	 * 1 - Com has access <br>
	 * 2 - Dem has access, spent <br>
	 * 3 - Com has access, spent <br>
	 */
	public static int seven = -1;
	/**
	 * The superpower that has T2 ability 8, and whether he has used it or not. <br>
	 * -1 - no one has access <br>
	 * 0 - Dem has access <br>
	 * 1 - Com has access <br>
	 * 2 - Dem has access, spent <br>
	 * 3 - Com has access, spent <br>
	 */
	public static int eight = -1;
	
	private TextChannel txtsp = (sp==0)?GameData.txtdem:GameData.txtcom;
	/**
	 * 
	 */
	public Operations(int party, int ops, boolean infl, boolean sc, boolean t2) {
		this(party, ops, infl, sc, t2, 2, 0);
	}
	/**
	 * Constructor, specifying the number of ops on the card used and the conditions under which they can be used.
	 * @param party is the superpower in charge of these Operations points.
	 * @param ops is the number of Operation Points usable in this instance of Operations.
	 * @param infl indicates whether one can place Influence using this instance of Operations.
	 * @param sc indicates whether one can conduct a Support Check using this instance of Operations.
	 * @param t2 indicates whether one can send this instance of Operations to China.
	 */
	public Operations(int party, int ops, boolean infl, boolean sc, boolean t2, int nosc) {
		this(party, ops, infl, sc, t2, nosc, 0);
	}
	/**
	 * Constructor, specifying the number of ops on the card used and the conditions under which they can be used.
	 * @param party is the superpower in charge of these Operations points.
	 * @param ops is the number of Operation Points usable in this instance of Operations.
	 * @param infl indicates whether one can place Influence using this instance of Operations.
	 * @param sc indicates whether one can Realigh using this instance of Operations.
	 * @param t2 indicates whether one can send this instance of Operations to China.
	 * @param restr creates a set of spatial restrictions under which these Operations must operate. Only relevant for Junta and Tear Down This Wall.
	 */
	public Operations(int party, int ops, boolean infl, boolean sc, boolean t2, int nosc, int restr) {
		sp = party;
		opnumber = ops;
		influence = infl;
		supportcheck = sc;
		tsquare = t2;
		remainingsc = nosc;
		restrictions = restr;
		txtsp = (sp==0)?GameData.txtdem:GameData.txtcom;
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
		//legacy
		if (usage.equals("t")||usage.equals("tsquare")||usage.equalsIgnoreCase("t2")) {
			if (GameData.getT2(sp)==8) {
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
					Math.max(0,MapManager.get(countries[i]).support[(sp+1)%2]
							- MapManager.get(countries[i]).support[sp] //get difference between you and opponent op values
							- MapManager.get(countries[i]).stab+1))) //subtract stab, add 1; this represents the amount of influence needed to break control of a country.
							+ amt[i]; //add the amount to add to the country
			//This is the same as Twilight Struggle Math.
			if (MapManager.get(countries[i]).support[sp]!=0);
			else {
				boolean flag = false;
				for (int c : MapManager.get(countries[i]).adj) {
					if (MapManager.get(c).support[sp]!=0) flag = true;
				}
				if (!flag) {
					txtsp.sendMessage(":x: Can you even reach that country with your influence?...").complete(); 
					return false;
				}
			}
		}
		if (ops<opnumber) {
			txtsp.sendMessage(":x: Endeavor to use all available ops.").complete();
			return false;
		}
		CardEmbedBuilder builder = new CardEmbedBuilder();
		if (ops>opnumber) {
			txtsp.sendMessage(":x: Overstreching yourself is never good.").complete();
			return false;
		}
		supportcheck = false;
		tsquare = false;
		Log.writeToLog("Operations: Influence");
		builder.setTitle("Influence Placement").setColor(sp==0?Color.blue:Color.red);
		for (int i=0; i<countries.length; i++) {
			builder.changeInfluence(countries[i], sp, amt[i]);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
		return true;
	}
	/**
	 * Realigns the given country in an attempt to remove influence.
	 * @param country is the country targeted by the realignment.
	 */
	public boolean realignment(int country) {
		if (!supportcheck) {
			txtsp.sendMessage(":x: You must do something else with these ops.").complete();
			return false;
		}
			boolean flag = true;
			for (int i=0; i<75; i++) {
				if (MapManager.get(i).support[(sp+1)%2]>0) {
					flag = false;
					break;
				}
			}
			if (flag) {
				txtsp.sendMessage("Dear god, how did this happen!? Your enemy doesn't have influence on the board.").complete();
				return true;
			}
		
		if (MapManager.get(country).support[(sp+1)%2]==0) {
			txtsp.sendMessage(":x: This country is fresh out of foreign influence, I can tell you that.").complete();
			return false;
		}
		influence = false;
		tsquare = false;
		Log.writeToLog("Check in " + MapManager.get(country).shorthand.toUpperCase());
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Support Check")
			.setDescription("Target: "+ MapManager.get(country))
			.setColor(sp==0?Color.blue:Color.red);
		int raw = new Die().roll() + opnumber;
		int roll = raw;
		Log.writeToLog("Roll: "+roll);
		String[] modifiers = {"",""};
		for (int adj : MapManager.get(country).adj) {
			if (MapManager.get(adj).isControlledBy()==sp) {
				roll++;
				modifiers[MapManager.get(adj).isControlledBy()] += MapManager.get(adj);
				Log.writeToLog("+1 to roll (control of "+MapManager.get(adj).shorthand.toUpperCase()+").");
			}
			else if (MapManager.get(adj).isControlledBy()==(sp+1)%2) {
				roll--;
				modifiers[MapManager.get(adj).isControlledBy()] += MapManager.get(adj);
				Log.writeToLog("-1 to roll (enemy control of "+MapManager.get(adj).shorthand.toUpperCase()+").");
			}
		}
		int amt = opnumber + roll - MapManager.get(country).stab*2;
		builder.addField("Roll: :game_die:"+ CardEmbedBuilder.numbers[raw] + "::heavy_plus_sign::" + CardEmbedBuilder.numbers[opnumber] + "::heavy_plus_sign:" + modifiers[0] + ":heavy_minus_sign:" + modifiers[1] + ":heavy_minus_sign:" + MapManager.get(country).stab + ":heavy_multiplication_x: 2", CardEmbedBuilder.intToEmoji(amt), false);
		if (amt>0) {
			if (amt>MapManager.get(country).support[(sp+1)%2]) {
				int x = MapManager.get(country).support[(sp+1)%2];
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
		remainingsc--;
		return remainingsc==0;
	}
	/**
	 * Sends the card to China.
	 * @return the success of the operation.
	 */
	public boolean space() {
		if (!tsquare) {
			txtsp.sendMessage(":x: You must do something else with these ops.").complete();
			return false;
		}
		supportcheck = false;
		influence = false;
		Log.writeToLog("Space Race: ");
		GameData.toT2(sp);
		int spaceLevel = GameData.getT2(sp);
		int die = new Die().roll();
		CardEmbedBuilder builder = new CardEmbedBuilder();
		Log.writeToLog("Roll: " + die);
		builder.setTitle("Space Race")
		.setDescription("Card used: " + CardList.getCard(HandManager.activecard));
		if (die+opnumber >= (sp==0?spaceOpsD[spaceLevel]:spaceOpsC[spaceLevel])) {
			builder.setColor(sp==0?Color.blue:Color.red)
				.addField("Roll: :"+CardEmbedBuilder.numbers[die]+"::heavy_plus_sign:"+opnumber,getSpaceNames(spaceLevel, sp),false);
			GameData.addT2(sp);
		}
		else {
			builder.setColor(Color.ORANGE);
			builder.addField("Roll: :"+CardEmbedBuilder.numbers[die]+"::heavy_plus_sign:"+opnumber,"Failure.",false);
		}
		if (spaceLevel<3&&GameData.getT2(sp)>=3&&sp==1&&HandManager.removeEffect(124)) {
			builder.addField("Vostok 1", "The Soviets have acquired the capability to send a man into space. They will no longer receive a -1 bonus to their space race die rolls.", false);
			Log.writeToLog("Laika no longer active.");
		}
		if (spaceLevel<7&&GameData.getT2(sp)>=7) {
			if (GameData.getT2((sp+1)%2)<7) {
				seven = sp;
			}
			else {
				seven = -1;
			}
		}
		if (spaceLevel<8&&GameData.getT2(sp)>=8) {
			if (GameData.getT2((sp+1)%2)<8) {
				eight = sp;
			}
			else {
				eight = -1;
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
		if (s==0) {
			if (spaceLevel==0) return "\"It's all because of you people.\"\n- Li Zhao, at the funeral of Hu Yaobang";
			if (spaceLevel==1) return "Rally in the Square!";
			if (spaceLevel==2) return "Foreign News Covers Incident.";
			if (spaceLevel==3) return "Hunger Strikes begin.";
			if (spaceLevel==4) return "\"At this grim moment, what we need most is to remain calm and united in a single purpose. We need a powerful cementing force to strengthen our resolve: That is the Goddess of Democracy.\"\n- Unknown";
			if (spaceLevel==5) return "\"The PLA came on orders. We support you. There is no disorder in Beijing. You guys go on home.\"\n- Unknown";
			if (spaceLevel==6) return "Barricades erected.";
			if (spaceLevel==7) return "Pluralist China?";
		}
		if (s==1) {
			if (spaceLevel==0) return "Reformer Discredited.";
			if (spaceLevel==1) return "Students Dispersed.";
			if (spaceLevel==2) return "\"It is necessary to take a clear-cut stand against disturbances.\"\n- *People's Daily*, 26 April";
			if (spaceLevel==3) return "\"You are still young, there are still many days yet to come, you must live healthy, and see the day when China accomplishes the Four Modernizations.\" \n-Zhao Ziyang, 19 May";
			if (spaceLevel==4) return "||[REDACTED]||.";
			if (spaceLevel==5) return "\"There is no way to back down now without the situation spiraling out of control.\"\n- Deng Xiaoping, 4 May";
			if (spaceLevel==6) return "||[REDACTED]||.";
			if (spaceLevel==7) return "Most Favored Nation Status?";
		}
		return null;
	}
	
	public static ArrayList<Integer> influencePossible (int sp) {
		ArrayList<Integer> x = new ArrayList<Integer>();
		for (int i=0; i<75; i++) {
			if (MapManager.get(i).support[sp]>0) x.add(i);
			else for (Integer c : MapManager.get(i).adj) {
				if (MapManager.get(c).support[sp]>0) {
					x.add(i);
					break;
				}
			}
		}
		return x;
	}
}

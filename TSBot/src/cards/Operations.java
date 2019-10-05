package cards;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import events.CardEmbedBuilder;
import events.Chernobyl;
import game.GameData;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.core.entities.TextChannel;
/**
 * Class for handling all related to operations.
 * @author [REDACTED]
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
	 * The number of ops required to send things into space.
	 */
	public static final int[] spaceOps = {2, 2, 2, 2, 3, 3, 4, 4};
	/**
	 * The number of VP obtained by being the <b> first </b> to advance to that stage.
	 */
	public static final int[] spaceVP = {2, 0, 2, 0, 3, 0, 4, 2};
	/**
	 * The number of VP obtained by being the <b> second </b> to advance to that stage.
	 */
	public static final int[] spaceVP2 = {1, 0, 0, 0, 1, 0, 2, 0};
	/**
	 * The number you must roll under or at to advance to the next stage of the space race.
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
	
	private TextChannel txtsp = (sp==0)?GameData.txtusa:GameData.txtssr;
	/**
	 * Constructor, specifying the number of ops on the card used and the conditions under which they can be used.
	 * @param superpower is the superpower in charge of these Operations points.
	 * @param ops is the number of Operation Points usable in this instance of Operations.
	 * @param infl indicates whether one can place Influence using this instance of Operations.
	 * @param realign indicates whether one can Realigh using this instance of Operations.
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
		//legacy
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
							- MapManager.get(countries[i]).stab+1)))*2 //subtract stab, add 1; this represents the amount of influence needed to break control of a country.
							+ amt[i] //add the amount to add to the country
							- Math.min(amt[i], 
									Math.max(0,MapManager.get(countries[i]).influence[(sp+1)%2]
											- MapManager.get(countries[i]).influence[sp] //get difference between you and opponent op values
											- MapManager.get(countries[i]).stab+1)); //subtract earlier difference
			/*
			 * Say that you're the USSR playing into Thailand 3/0 with the China Card under Vietnam Revolts. (control break and additional).
			 * That's six ops total for four influence:
			 * ops = Math.min(4, Math.max(0, 3-0-2+1=2))*2 + 4 - Math.min(4, Math.max(0, 3-0-2+1=2))
			 * 	   = 2*2 + 4 - 2 = 6.
			 * 
			 * Say that you're the USA playing into a 1/5 Angola with Nuclear Test Ban (no reduced cost).
			 * That's four ops for two influence:
			 * ops = Math.min(2, Math.max(0, (5-1-1+1=4)))*2 + 2 - Math.min(2, Math.max(0, 5-1-1+1=4))
			 *     = 2*2 + 2 - 2 = 4.
			 *     
			 * Say that you're the USSR playing into an empty Pakistan with COMECON (reduced cost only).
			 * That's three ops for three influence:
			 * ops = Math.min(3, Math.max(0, (0-0-2+1)))*2 + 3 - Math.min(3, Math.max(0, (0-0-2+1))
			 *     = 0 + 3 - 0 = 3.
			 */
			if (MapManager.get(countries[i]).region!=5) {
				allsea=false;
				if(MapManager.get(countries[i]).region!=4) {
					allasia=false;
				}
			}
			if ((sp==0&&!allowedUSA.contains(countries[i]))||(sp==1&&!allowedSUN.contains(countries[i]))) {
				txtsp.sendMessage(":x: Can you even reach that country with your influence?...").complete();
				return false;
			}
		}
		if (allasia && HandManager.activecard==6) ops--; //handles China Card  case
		if (allsea && HandManager.Effects.contains(9)&&sp==1) ops--;
		if (ops<opnumber) {
			txtsp.sendMessage(":x: Endeavor to use all available ops.").complete();
			return false;
		}
		if (ops>opnumber) {
			txtsp.sendMessage(":x: Overstreching yourself is never good.").complete();
			return false;
		}
		realignment = false;
		coupdetat = false;
		spaceable = false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Influence Placement").setColor(sp==0?Color.blue:Color.red);
		for (int i=0; i<countries.length; i++) {
			builder.changeInfluence(countries[i], sp, amt[i]);
			if (GameData.ccw && MapManager.get(86).isControlledBy()==1 && HandManager.China==-1) {
				HandManager.China=1; //China activating condition
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
		else {
			boolean flag = true;
			for (int i=0; i<84; i++) {
				if (MapManager.get(i).influence[(sp+1)%2]>0) {
					flag = false;
					break;
				}
			}
			if (flag) {
				txtsp.sendMessage("Dear god, how did this happen!? Your enemy doesn't have influence on the board.").complete();
				return true;
			}
		}
		if (!MapManager.get(country).checkIsCoupable() && !(MapManager.get(country).region<=2 && restrictions==96)) {
			txtsp.sendMessage(":x: DEFCON restricts you from realigning this country.").complete();
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
		Random rand = new Random();
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Realignment")
			.setDescription("Target: "+ MapManager.get(country))
			.setColor(sp==0?Color.blue:Color.red);
		int[] rolls = new int[] {rand.nextInt(6)+1, rand.nextInt(6)+1};
		String[] modifiers = {"",""};
		if (MapManager.get(country).influence[0]>MapManager.get(country).influence[1]) {
			rolls[0]++;
			modifiers[0] += MapManager.get(country);
		}
		if (MapManager.get(country).influence[0]<MapManager.get(country).influence[1]) {
			rolls[1]++;
			modifiers[1] += MapManager.get(country);
		}
		if (HandManager.effectActive(93)) {
			rolls[0]--;
			builder.addField("Iran-Contra Affair!", "-1", false);
		}
		for (int adj : MapManager.get(country).adj) {
			if (adj==84) {
				rolls[0]++;
				modifiers[0] += MapManager.get(adj);
			}
			else if (adj==85) {
				rolls[1]++;
				modifiers[1] += MapManager.get(adj);
			}
			else {
				if (MapManager.get(adj).isControlledBy()!=-1) {
					rolls[MapManager.get(adj).isControlledBy()]++;
					modifiers[MapManager.get(adj).isControlledBy()] += MapManager.get(adj);
				}
			}
		}
		builder.addField("Rolls", ":flag_us::" + CardEmbedBuilder.numbers[rolls[0]] + "::heavy_plus:" + modifiers[0] + "\n:flag_su::" + CardEmbedBuilder.numbers[rolls[1]] + "::heavy_plus:" + modifiers[1], false);
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
		if (!MapManager.get(country).checkIsCoupable()&&!(MapManager.get(country).region<=2 && restrictions==96)) {
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
					.setDescription((sp==0?"Soviet":"American")+" reprisal imminent")
					.setColor(Color.BLACK)
					.setFooter("\"You have ignited a nuclear war. And no, there is no animated display "
							+ "or a mushroom cloud with parts of bodies flying through the air. "
							+ "We do not reward failure.\"\n"
							+ "- *Balance of Power* (video game)", Launcher.url("victory_us1.png"))
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
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Coup d'État!")
			.setDescription("Target: "+ MapManager.get(country))
			.setColor(sp==0?Color.blue:Color.red);
		if (HandManager.effectActive(41) && sp==0) builder.addField("Nuclear Submarines!", "This coup does not affect DEFCON.", false);
		else if (MapManager.get(country).isBattleground) builder.changeDEFCON(-1);
		if (!free) builder.addMilOps(sp, opnumber);
		int die = (new Random().nextInt(6))+1;
		int amt = opnumber + die - MapManager.get(country).stab*2 - (HandManager.effectActive(43)?1:0);
		if (HandManager.effectActive(43)) builder.addField("SALT Negotiations","-1",false);
		if (HandManager.effectActive(690+sp) && MapManager.get(country).region>6) {
			builder.addField("Latin American Death Squads", "+1", false);
			amt++;
		}
		if (HandManager.effectActive(690+((sp+1)%2)) && MapManager.get(country).region>6) {
			builder.addField("Latin American Death Squads", "-1", false);
			amt--;
		}
		if (HandManager.effectActive(109)) {
			builder.addField("Yuri and Samantha", "", false);
			builder.changeVP(-1);
		}
		builder.addField("Roll: :" + CardEmbedBuilder.numbers[die] + ":", CardEmbedBuilder.intToEmoji(amt), false);
		
		if (amt>0) {
			if (amt>MapManager.get(country).influence[(sp+1)%2]) {
				builder.changeInfluence(country, sp, amt-MapManager.get(country).influence[(sp+1)%2]);
				builder.changeInfluence(country, (sp+1)%2, -MapManager.get(country).influence[(sp+1)%2]);
			}
			else {
				builder.changeInfluence(country, (sp+1)%2, -amt);
			}
		}
		else {
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
		GameData.toSpace(sp);
		int spaceLevel = GameData.getSpace(sp);
		int die = (new Random().nextInt(6))+1;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Space Race")
		.setDescription("Card used: " + CardList.getCard(HandManager.activecard));
		if (die <= spaceRoll[spaceLevel]) {
			builder.setColor(sp==0?Color.blue:Color.red)
				.addField("Roll: "+CardEmbedBuilder.numbers[die],getSpaceNames(spaceLevel, sp),false);
			if (GameData.aheadInSpace()==(sp+1)%2) {
				builder.changeVP(-(sp*2-1)*spaceVP2[spaceLevel]);
			}
			else builder.changeVP(-(sp*2-1)*spaceVP[spaceLevel]);
			GameData.addSpace(sp);
		}
		else {
			builder.setColor(Color.ORANGE);
			builder.addField("Roll: "+CardEmbedBuilder.numbers[die],"Failure.",false);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
		return true;
	}
	/**
	 * Flavor text regarding the space race goes here.
	 * @param spaceLevel is the superpower's advancement on the race.
	 * @return a string, with relevant flavor text.
	 */
	public static String getSpaceNames(int spaceLevel, int s) {
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

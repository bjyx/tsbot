package commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import game.PlayerList;
import logging.Log;
import map.MapManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * The command that starts the game.
 * @author adalbert
 *
 */
public class StartCommand extends Command {
	
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {

		if (GameData.hasGameEnded()) {
			sendMessage(e, ":x: Have you tried turning it off and on again?");
			return;
		}
		if (GameData.hasGameStarted()) {
			sendMessage(e, ":hourglass: There's a time and place for everything, but not now.");
			return;
		}
		if (!PlayerList.getArray().contains(e.getAuthor())) {
			sendMessage(e, ":x: Excuse me, but who are *you* playing as?");
			return;
		}
		//determine number of players
		int players = 2;
		if (PlayerList.getArray().contains(null)) { //there is exactly one person here
			try {
				if (args.length>=3 && Integer.parseInt(args[2])>=0 && Integer.parseInt(args[2])<2) players=Integer.parseInt(args[2]);
				else {
					sendMessage(e, ":x: You are lacking in players. Pick one of the US (0) or the Jihadist (1) to play as.");
					return;
				}
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: Pick one of the US (0) or the Jihadist (1) to play as.");
				return;
			}
			
		}
		//otherwise, ignore this slot
		
		//now parse a scenario
		int scenario = 0;
		try {
			if (args.length>=2 && Integer.parseInt(args[1])>=0 && Integer.parseInt(args[1])<16) scenario=Integer.parseInt(args[1]);
			else if (args.length>=2){
				sendMessage(e, ":x: Leave this field blank if you want to. The scenario must be written as a non-negative integer less than 16.");
				return;
			}
		}
		catch (NumberFormatException err) {
			sendMessage(e, ":x: Leave this field blank if you want to. The scenario must be written as a non-negative integer less than 16.");
			return;
		}
		//now determine the rules. 
		int rules = 0;
		try {
			if (args.length>=4 && Integer.parseInt(args[3])>=0 && Integer.parseInt(args[3])<8) rules=Integer.parseInt(args[3]);
			else if (args.length>=4){
				sendMessage(e, ":x: Leave this field blank if you want to. Settings must be written as non-negative integers less than 8.");
				return;
			}
		}
		catch (NumberFormatException err) {
			sendMessage(e, ":x: Leave this field blank if you want to. Settings must be written as non-negative integers less than 8.");
			return;
		}
		/* If a handicap exists for Labyrinth...
		try {
			if (args.length>=3 && Math.abs(Integer.parseInt(args[2]))<=10) SetupCommand.handicap=Integer.parseInt(args[2]);
			else if (args.length>=3) {
				sendMessage(e, ":x: Leave this field blank if you want to. A handicap is the amount of influence one player gets in addition to starting; its absolute value must be at most 10.");
				return;
			}
		}
		catch (NumberFormatException err) {
			sendMessage(e, ":x: Leave this field blank if you want to. A handicap is the amount of influence one player gets in addition to starting; its absolute value must be at most 10.");
			return;
		}*/
		GameData.txtchnl = e.getTextChannel();
		// create two channels, one for the US and one for the USSR, accessible only by the respective role
		
		if (e.getGuild().getTextChannelsByName("lb-usa", true).isEmpty()) {
			GameData.txtusa = e.getGuild().createTextChannel("lb-usa")
					.setTopic("[REDACTED]")
					.addPermissionOverride(GameData.roleusa, 68672, 0)
					.addPermissionOverride(e.getGuild().getPublicRole(), 0, 2146958847)
					.complete();
		}
		else {
			GameData.txtusa = e.getGuild().getTextChannelsByName("lb-usa", true).get(0);
			for (PermissionOverride po : GameData.txtusa.getPermissionOverrides()) {
				po.delete().complete();
			}
			GameData.txtusa.putPermissionOverride(e.getGuild().getPublicRole()).setPermissions(0, 2146958847).queue();
			GameData.txtusa.putPermissionOverride(GameData.roleusa).setPermissions(68672, 0).queue();
		}
		if (e.getGuild().getTextChannelsByName("lb-jih", true).isEmpty()) {
			GameData.txtjih = e.getGuild().createTextChannel("lb-jih")
					.setTopic("[REDACTED]")
					.addPermissionOverride(GameData.rolejih, 68672, 0)
					.addPermissionOverride(e.getGuild().getPublicRole(), 0, 2146958847).complete();
		}
		else {
			GameData.txtjih = e.getGuild().getTextChannelsByName("lb-jih", true).get(0);
			for (PermissionOverride po : GameData.txtjih.getPermissionOverrides()) {
				po.delete().complete();
			}
			GameData.txtjih.putPermissionOverride(e.getGuild().getPublicRole()).setPermissions(0, 2146958847).queue();
			GameData.txtjih.putPermissionOverride(GameData.rolejih).setPermissions(68672, 0).queue();
		}
		GameData.startGame();
		PlayerList.detSides();
		// give the roles to the respective countries
		e.getGuild().addRoleToMember(e.getGuild().getMember(PlayerList.getUSA()), GameData.roleusa).complete();
		e.getGuild().addRoleToMember(e.getGuild().getMember(PlayerList.getJih()), GameData.rolejih).complete();
		Log.writeToLog("New Game: Labyrinth");
		EmbedBuilder builder = new EmbedBuilder().setTitle("A New Game of Labyrinth Has Started.").setDescription(":hourglass:" + getDescription(GameData.scenario) + " Good luck.").setColor(Color.WHITE);
		sendMessage(e, new MessageBuilder(builder.build()).build());
		
		//start parsing the commands
		
		GameData.scenario = scenario;
		GameData.player = players;
		if (rules>=4) {
			rules-=4;
			GameData.zakat = true;
			GameData.stronghorse = true;
			builder.addField("Zakat/Strong Horse", "Gives each player a second wind.", false);
		}
		else {
			GameData.zakat = false;
			GameData.stronghorse = false;
		}
		if (rules>=2) {
			rules-=2;
			GameData.hero = true;
			builder.addField("Heroic Alerts", "Allows the US to make riskier Alerts for less Operations.", false);
		}
		else {
			GameData.hero = false;
		}
		if (players < 2) { //ignore if two-player
			if (rules>=1) {
				rules-=1;
				GameData.vardiff = true;
				builder.addField("Variable Difficulty", "Play with a variable singleplayer difficulty setting.", false);
			}
			else {
				GameData.vardiff = false;
				//create a static difficulty.
				GameData.diff = new boolean[] {false, false, false, false, false};
				try {
					if (args.length>=5 && Integer.parseInt(args[4])>=0 && Integer.parseInt(args[4])<=5) {
						for (int i=0; i<Integer.parseInt(args[4]); i++) {
							GameData.diff[i] = true;
						}
					}
					else if (args.length>=5){
						sendMessage(e, ":x: Leave this field blank if you want to. The difficulty must be written as a non-negative integer of at most 5.");
						return;
					}
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: Leave this field blank if you want to. The difficulty must be written as a non-negative integer of at most 5.");
					return;
				}
			}
		}
		if (scenario>3 && scenario !=9 && scenario != 14) GameData.awk = true;
		if (scenario>10) GameData.fw = true; //amends awakening rules for campaign scenario, but doesn't add any rules by themselves
		
		//and now for the board state
		GameData.initialize(); //game variables
		MapManager.initialize(scenario); //map
		
		Log.writeToLog("-+-+- Setup -+-+-");
		CardList.initialize();
		HandManager.seedDecks(scenario); //decks
		HandManager.deal();
		if (scenario==2) {
			SetupCommand.setup = false;
			GameData.txtjih.sendMessage(PlayerList.getJih().getAsMention() + ", please place one cell in each of three non-US countries. (Use LB.setup)").complete();
		}
		else {
			TimeCommand.cardPlayed = false;
			TimeCommand.prompt();
		}
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("LB.start");
	}

	@Override
	public String getDescription() {
		return "Start 1989.";
	}

	@Override
	public String getName() {
		return "Start (start)";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("LB.start <scenario> [player] [rules] [diff] - How it all begins.\n"
				+ "The scenario will be a number between 0 and 16 inclusive:\n"
				+ "0 - Let's Roll! (Base)\n"
				+ "1 - You Can Call Me Al (Base)\n"
				+ "2 - Anaconda (Base)\n"
				+ "3 - Mission Accomplished? (Base)\n"
				
				+ "4 - Awakening (AWK)\n"
				+ "5 - Mitt's Turn (AWK)\n"
				+ "6 - Arab Spring (AWK)\n"
				+ "7 - Status of Forces (AWK)\n"
				+ "8 - ISIL (AWK)\n"
				+ "9 - Campaign (AWK)\n"
				+ "10 - Campaign (AWK, shuffled)\n"
				
				+ "11 - Fall of ISIL (FW)\n"
				+ "12 - Trump Takes Command (FW)\n"
				+ "13 - Hillary Takes Command (FW)\n"
				+ "14 - Extended Campaign (FW)\n"
				+ "15 - Extended Campaign (FW, shuffled)\n"
				+ "If no number is given, it will be by default 0 (Let's Roll!).\n\n"
				+ "The second integer indicates the side to play for a solitaire game (0 US 1 Jihad), or 2-player mode. Note that 1 is not available in the base game.\n"
				+ "If no number is given, it will be by default 2 (two-player).\n\n"
				+ "The third integer is an optional rules setting.\n"
				+ "If the number in binary has a digit in the following position:\n"
				+ "`100` Zakat/Strong Horse\n"
				+ "`010` Heroic Alerts\n"
				+ "`001` Variable Bot Difficulty\n"
				+ "If no number is given, it will be by default 0 (no additional rules).\n\n"
				+ "Provided that a solitaire game is being started and Variable Bot Difficulty is not on, the fourth integer describes the difficulty, from 0-5.\n"
				+ "If no number is given, it will be by default 0 (Muddled/Off Guard)."
				+ "The last integer indicates the number of reshuffles to play, to a maximum of three. This option may not be available for some scenarios.\n"
				+ "If no number is given, it will be set to a default for each scenario—often, this is 1.\n\n");
	}

	private String getDescription(int scenario) {
		switch (scenario) {
		case 0: 
			return "It is September 12, and the towers have fallen.";
		case 1: 
			return "[Note: Alternative History.] It is September 12, and the towers have fallen.";
		case 2: 
			return "It is 2002. The Taliban has been overthrown, but the fight isn't over yet.";
		case 3: 
			return "It is 2003, and the US has just launched an attack on Iraq.";
		case 4: 
			return "It is 2010, the start of an Arab Spring.";
		case 5: 
			return "[Note: Alternative History.] It is 2010, the start of an Arab Spring.";
		case 6: 
			return "[Note: this game will have a partially set deck.] It is 2010, the start of an Arab Spring.";
		case 7: 
			return "It is 2011, and the US has just pulled out of Iraq.";
		case 8: 
			return "It is 2014, and ISIL has just proclaimed its caliphate.";
		case 9: 
			return "It is September 12, and the towers have fallen. This game will continue into 2015.";
		case 10: 
			return "[Note: Alternative History.] It is September 12, and the towers have fallen. This game will continue into 2015.";
		case 11: 
			return "It is 2016. ISIL's mark on the Middle East is starting to fade.";
		case 12: 
			return "It is 2017, and Donald Trump has just taken power.";
		case 13: 
			return "[Note: Alternative History.] It is 2017, and Hillary Clinton has just taken power.";
		case 14: 
			return "It is September 12, and the towers have fallen. This game will continue into 2020.";
		case 15: 
			return "[Note: Alternative History.] It is September 12, and the towers have fallen. This game will continue into 2020.";
		
		default:
			return "";
		}
	}
	
}

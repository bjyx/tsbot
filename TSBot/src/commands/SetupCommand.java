package commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import events.CardEmbedBuilder;
import game.GameData;
import game.PlayerList;
import logging.Log;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * The command handling setting up the initial board state. 
 * @author adalbert
 *
 */
public class SetupCommand extends Command {

	public static boolean USSR = false;
	public static boolean Yalta = false;
	public static boolean VE = false;
	public static boolean Israel = false;
	
	public static boolean USA = false;
	
	public static boolean hreq = false;
	public static int handicap = 2;
	
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (GameData.hasGameEnded()) {
			sendMessage(e, ":x: Have you tried turning it off and on again?");
			return;
		}
		if (!GameData.hasGameStarted()) {
			sendMessage(e, ":hourglass: There's a time and place for everything, but not now.");
			return;
		}
		if (e.getChannel().equals(GameData.txtchnl)) {
			sendMessage(e, ":x: Don't. You're compromising your play.");
			return;
		}
		if (!(USSR||USA)) {
			sendMessage(e, "That's not the part of the game you're looking for.");
			return;
		}
		if (!((USSR&&e.getAuthor().equals(PlayerList.getSSR()))||(USA&&e.getAuthor().equals(PlayerList.getUSA())))) {
			sendMessage(e, ":x: Excuse me, but who are *you* playing as? China's abstracted as a card and the rest of the world has a board space each.");
			return;
		}
		if (args.length%2!=1) {
			sendMessage(e, ":x: An influence value must be associated with every listed country.");
			return;
		}
		int[] countries = new int[(args.length-1)/2];
		int[] amt = new int[(args.length-1)/2];
		int sum = 0;
		for (int i=1; i<args.length; i+=2) {
			countries[(i-1)/2] = MapManager.find(args[i]);
			if (countries[(i-1)/2]==-1) {
				sendMessage(e, ":x: "+args[i]+" isn't a country or alias of one.");
				return;
			}
			if (!hreq) {
				if (Yalta) {
					if (countries[(i-1)/2] > 20) {
						sendMessage(e, ":x: You sure you aren't going to reinforce your positions in Europe?");
						return;
					}
				}
				else if (VE) {
					if (countries[(i-1)/2] > 20) {
						sendMessage(e, ":x: You sure you aren't going to reinforce your positions in Europe?");
						return;
					}
					if (MapManager.get(countries[(i-1)/2]).region==1) {
						sendMessage(e, ":x: Stay on your side of the curtain.");
						return;
					}
				}
				else if (Israel) {
					if (MapManager.get(countries[(i-1)/2]).region!=3) {
						sendMessage(e, ":x: Those are for the Arabs.");
						return;
					}
				}
				else {
					if (countries[(i-1)/2] > 20) {
						sendMessage(e, ":x: You sure you aren't going to reinforce your positions in Europe?");
						return;
					}
					if ((USSR && MapManager.get(countries[(i-1)/2]).region==1)||(USA && MapManager.get(countries[(i-1)/2]).region==2)) {
						sendMessage(e, ":x: Stay on your side of the curtain.");
						return;
					}
				}
			}
			else {
				if ((USSR&&MapManager.get(countries[(i-1)/2]).influence[1]==0)||(USA&&MapManager.get(countries[(i-1)/2]).influence[0]==0)) {
					sendMessage(e, ":x: Handicap influence can only be placed within countries that already hold your influence.");
					return;
				}
			}
			try {
				amt[(i-1)/2] = Integer.parseInt(args[i+1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: Those aren't numbers.");
				return;
			}
			sum += amt[(i-1)/2];
			if (amt[(i-1)/2] <=0) {
				sendMessage(e, ":x: Positive numbers only.");
				return;
			}
		}
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setAuthor("Setup Phase")
		.setTitle(hreq?("Handicap for " + (handicap>0?"USA":"USSR")):(USSR?"Eastern Bloc":("Western Bloc")))
		.setColor(USSR?Color.red:(USA?Color.blue:(handicap>0?Color.blue:Color.red)));
		if (USSR&&!hreq) {
			if (Yalta) {
				if (sum!=1) {
					sendMessage(e, ":x: You get one influence here from Yalta. You'll get more later, don't worry.");
					return;
				}
			}
			else if (VE) {
				if (sum!=2) {
					sendMessage(e, ":x: You get two influence here from sweeping Eastern Europe before Christmas. You'll get more later, don't worry.");
					return;
				}
			}
			else if (Israel) {
				if (sum!=2) {
					sendMessage(e, ":x: You get two influence here from anti-Israel militia. You'll get more later, don't worry.");
					return;
				}
			}
			else {
				if (sum!=6) {
					sendMessage(e, ":x: You get six influence in the Eastern Bloc. You'll get more later, don't worry.");
					return;
				}
			}
			Yalta = false;
			VE = false;
			Israel = false;
			builder.setFooter("\"This war is not as in the past; whoever occupies a territory also imposes on it his own social system. Everyone imposes his own system as far as his army can reach.\"\n"
					+ "- Joseph Stalin, 1945", Launcher.url("people/stalin.png"));
			Log.writeToLog("USSR Setup: ");
			for (int i=0; i<countries.length; i++) {
				builder.changeInfluence(countries[i], 1, amt[i]);
			}
			
			if (HandManager.removeEffect(100101)) {
				Yalta = true;
				GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", please place one influence marker anywhere in Europe. (Use TS.setup)").complete();
			}
			else if (HandManager.removeEffect(100201)) {
				VE = true;
				GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", please place two more influence markers in Eastern Europe. (Use TS.setup)").complete();
			}
			else if (HandManager.removeEffect(100401)) {
				Israel = true;
				GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", please place two influence markers in the Middle East. (Use TS.setup)").complete();
			}
			else {
				USSR = false;
				USA = true;
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", please place seven influence markers in Western Europe. (Use TS.setup)").complete();
			}
		}
		else if (USA&&!hreq) {
			if (sum!=7) {
				sendMessage(e, ":x: You get seven influence in the Western Bloc. You'll get more later, don't worry.");
				return;
			}
			builder.setFooter("\"A shadow has fallen upon the scenes so lately lighted by the Allied victory…. From Stettin in the Baltic to Trieste in the Adriatic an iron curtain has descended across the Continent.\"\n"
					+ "- Winston Churchill, 1946",Launcher.url("people/churchill.png"));
			Log.writeToLog("USA Setup: ");
			for (int i=0; i<countries.length; i++) {
				builder.changeInfluence(countries[i], 0, amt[i]);
			}
			USA = false;
			if (handicap==0) {
				GameData.endSetupPhase();
				TimeCommand.cardPlayed = false;
				TimeCommand.prompt();
			}
			else {
				hreq = true;
				if (handicap>0) {
					USA = true;
					GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", please place your handicap influence. (Use TS.setup)").complete();
				}
				else {
					USSR = true;
					GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", please place your handicap influence. (Use TS.setup)").complete();
				}
			}
		}
		else if (hreq) {
			if (sum!=Math.abs(handicap)) {
				sendMessage(e, ":x: You only have " + Math.abs(handicap) + " influence to use here.");
				return;
			}
			Log.writeToLog("Handicap: ");
			for (int i=0; i<countries.length; i++) {
				builder.changeInfluence(countries[i], USSR?1:0, amt[i]);
			}
			handicap = 0;
			hreq = false;
			USSR = false;
			USA = false;
			GameData.endSetupPhase();
			TimeCommand.cardPlayed = false;
			TimeCommand.prompt();
			Log.writeToLog("-+-+- Turn 1 -+-+-");
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("TS.setup");
	}

	@Override
	public String getDescription() {
		return "Allows the player to place their starting influence.";
	}

	@Override
	public String getName() {
		return "Setup influence";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("TS.setup <country> <influence> ad infinitum. You will be prompted to use this command when needed.\n"
				+ "- Example: TS.setup ddr 1 polska 4 bg 1");
	}

}

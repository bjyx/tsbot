package commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import events.CardEmbedBuilder;
import game.GameData;
import game.PlayerList;
import logging.Log;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * The command handling setting up the initial board state. (Rule 3.4)
 * @author adalbert
 *
 */
public class SetupCommand extends Command {

	public static int setup = -1;
	public static final int infl[] = {2, 3, 3, 4, 2};
	//public static boolean hreq = false;
	//public static int handicap = 2;
	
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
		if (setup==-1) {
			sendMessage(e, "That's not the part of the game you're looking for.");
			return;
		}
		if (!((setup%2==0&&e.getAuthor().equals(PlayerList.getCom()))||(setup%2==1&&e.getAuthor().equals(PlayerList.getDem())))) {
			sendMessage(e, ":x: Play the game correctly.");
			return;
		}
		if (args.length%2!=1) {
			sendMessage(e, ":x: An influence value must be associated with every listed space.");
			return;
		}
		int sp = (setup+1)%2;
		int[] countries = new int[(args.length-1)/2];
		int[] amt = new int[(args.length-1)/2];
		int sum = 0;
		for (int i=1; i<args.length; i+=2) {
			countries[(i-1)/2] = MapManager.find(args[i]);
			if (countries[(i-1)/2]==-1) {
				sendMessage(e, ":x: "+args[i]+" isn't a country or alias of one.");
				return;
			}
			if (MapManager.get(countries[(i-1)/2]).support[(sp+1)%2]>0) {
				sendMessage(e, ":x: It is inadvisable to have your support in a space with even some form of opposition right now.");
				return;
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
		.setTitle(sp==1?"Communist Support":"Democratic Support")
		.setColor(sp==1?Color.red:Color.blue);
		if (sum!=infl[setup]) {
			sendMessage(e, ":x: You get " + infl[setup] + " support points. You'll get more later this phase, don't worry.");
			return;
		}
		if (sp==1) {
			builder.setFooter("Know good quotes to fill these spots? Let me know!\n"
					+ "- X X, XXXX",Launcher.url("people/null.png"));
			Log.writeToLog("Com Setup: ");
			for (int i=0; i<countries.length; i++) {
				builder.changeInfluence(countries[i], 1, amt[i]);
			}
			if (setup==4) {
				GameData.endSetupPhase();
				TimeCommand.cardPlayed = false;
				TimeCommand.prompt();
			}
			else {
				setup++;
				GameData.txtdem.sendMessage(GameData.roledem.getAsMention() + ", please place " + infl[setup] + " support points. (Use DF.setup)").complete();
			}
		}
		else {
			builder.setFooter("Know good quotes to fill these spots? Let me know!\n"
					+ "- X X, XXXX",Launcher.url("people/null.png"));
			Log.writeToLog("Dem Setup: ");
			for (int i=0; i<countries.length; i++) {
				builder.changeInfluence(countries[i], 0, amt[i]);
			}
			setup++;
			GameData.txtcom.sendMessage(GameData.rolecom.getAsMention() + ", please place " + infl[setup] + " support points. (Use DF.setup)").complete();
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("DF.setup");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Allows the player to place their starting support points.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Setup starting position";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("DF.setup <space> <support> ad infinitum. You will be prompted to use this command when needed.\n"
				+ "- Example: DF.setup ttb 1 msk 1");
	}

}

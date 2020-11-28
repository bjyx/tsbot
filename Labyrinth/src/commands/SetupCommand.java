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
		if (!e.getAuthor().equals(PlayerList.getJih())&&GameData.scenario!=2) { //restrict setup to the cells placed by the Jihadist
			sendMessage(e, ":x: Now's not the time to use this.");
			return;
		}
		if (args.length<4) {
			sendMessage(e, ":x: Three cells in three different countries.");
			return;
		}
		int sp = 1;
		int[] countries = new int[(args.length-1)];
		for (int i=1; i<args.length; i++) {
			countries[i-1] = MapManager.find(args[i]);
			if (countries[i-1]==-1) {
				sendMessage(e, ":x: "+args[i]+" isn't a country or alias of one.");
				return;
			}
			if (countries[i-1]==0) { //cannot be placed in the US
				sendMessage(e, ":x: That's the PATRIOT Act for you.");
				return;
			}
		}
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setAuthor("Setup Phase")
		.setTitle("Anaconda Setup")
		.setColor(Color.green);
		builder.setFooter("\"Leave us alone, or else expect us in New York and Washington.\"\n"
				+ "- Osama bin Laden, 2002",Launcher.url("people/binladen.png"));
		Log.writeToLog("Setup: ");
		for (int i=0; i<countries.length; i++) {
			MapManager.get(countries[i]).testCountry();
			builder.editUnits(countries[i], 1, 1);
		}
		TimeCommand.cardPlayed = false;
		TimeCommand.prompt();
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
		return Arrays.asList("LB.setup <space> <space> <space>. Only used to set up scenario 2, and only by the Jihadist.\n"
				+ "- Example: LB.setup ca uk ph");
	}

}

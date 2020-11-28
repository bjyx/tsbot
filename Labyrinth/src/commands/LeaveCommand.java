package commands;

import java.util.Arrays;
import java.util.List;

import game.GameData;
import game.PlayerList;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * The command allowing people to leave the game (so long as it hasn't started. 
 * @author adalbert
 *
 */
public class LeaveCommand extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (GameData.hasGameEnded()) {
			sendMessage(e, ":x: Have you tried turning it off and on again?");
			return;
		}
		if (GameData.hasGameStarted()) {
			sendMessage(e, ":x: This isn't a game of chicken. Once you've dived into the Labyrinth that is the Middle East you see it through to the end.");
			return;
		}
		if (!(PlayerList.getArray().get(0).equals(e.getAuthor())||PlayerList.getArray().get(1).equals(e.getAuthor()))) {
			sendMessage(e, ":x: You're not on the list.");
			return;
		}
		sendMessage(e, ":o: Done.");
		PlayerList.removePlayer(e.getAuthor());
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("LB.leave");
	}

	@Override
	public String getDescription() {
		return "Leave a game that hasn't started. ";
	}

	@Override
	public String getName() {
		return "Leave (leave)";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("LB.leave - Leave a game.");
	}

}

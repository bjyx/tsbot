package commands;

import java.util.Arrays;
import java.util.List;

import game.GameData;
import game.PlayerList;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class LeaveCommand extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (GameData.hasGameEnded()) {
			sendMessage(e, ":x: Have you tried turning it off and on again?");
			return;
		}
		if (GameData.hasGameStarted()) {
			sendMessage(e, ":x: This isn't a game of chicken. Once you're in the Cold War you see it through to the end.");
		}
		if (!Arrays.asList(PlayerList.getArray()).contains(e.getAuthor())) {
			sendMessage(e, ":x: You're not on the list.");
			return;
		}
		PlayerList.removePlayer(e.getAuthor());
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("TS.leave");
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
		return Arrays.asList("TS.leave - Leave a game.");
	}

}

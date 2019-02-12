package commands;

import java.util.Arrays;
import java.util.List;
import game.PlayerList;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class JoinCommand extends Command {
	
	private PlayerList plist = new PlayerList();
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		//TODO: if game started return;
		if (Arrays.asList(plist.getArray()).contains(e.getAuthor())) {
			sendMessage(e, ":x: You're already on the list.");
			return;
		}
		if (!Arrays.asList(plist.getArray()).contains(null)) {
			plist.addPlayer(e.getAuthor());
			sendMessage(e, ":o: Done");
			return;
		}
		sendMessage(e, ":x: There is only enough room for two superpowers on this world stage.");
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("TS.join");
	}

	@Override
	public String getDescription() {
		return "Join a game that hasn't started. ";
	}

	@Override
	public String getName() {
		return "Join";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("TS.join - Join a game.");
	}

}

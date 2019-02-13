package commands;

import java.util.List;

import game.GameData;
import game.PlayerList;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class StartCommand extends Command {
	private GameData gamedata = new GameData();
	private PlayerList plist = new PlayerList();
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (gamedata.hasGameEnded()) {
			sendMessage(e, ":x: Have you tried turning it off and on again?");
			return;
		}
		if (gamedata.hasGameStarted()) {
			sendMessage(e, ":hourglass: There's a time and place for everything, but not now.");
			return;
		}
		if (!plist.getArray().contains(e.getAuthor())) {
			sendMessage(e, ":x: Excuse me, but who are you playing as? China's abstracted as a card and the rest of the world is a board space.");
			return;
		}
		if (plist.getArray().contains(null)) {
			sendMessage(e, ":x: Can't have a Cold War if one of the superpowers doesn't exist.");
			return;
		}
		sendMessage(e, ":hourglass: Seven minutes to midnight... and counting.");
		gamedata.startGame();
		plist.detSides();
		
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return null;
	}

}

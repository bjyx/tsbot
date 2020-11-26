package commands;

import java.util.Arrays;
import java.util.List;

import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.MessageBuilder;
/**
 * The command that provides a list of the people who are playing this. 
 * @author adalbert
 *
 */
public class ListCommand extends Command {
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		sendMessage(e, new MessageBuilder(PlayerList.getPlayers(GameData.hasGameStarted())).build());
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("LB.list", "LB.players");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Lists any players in the current game. If the game has started, the side they play will also be listed.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Player List";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("LB.list - Lists any players in the current game.");
	}

}

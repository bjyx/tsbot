package commands;

import java.util.Arrays;
import java.util.List;

import cards.CardList;
import map.MapManager;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class InfoCommand extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub
		if (args.length<3) {
			sendMessage(e, ":x: Not enough arguments.");
			return;
		}
		if (args[1].equals("card")) {
			int id = Integer.parseInt(args[2]);
			if (id <= 0 || id > CardList.numberOfCards()) {
				sendMessage(e, ":x: Cards are indexed from 1 to " + CardList.numberOfCards() + ".");
				return;
			}
			sendMessage(e, new MessageBuilder().setEmbed(CardList.getCard(id).toEmbed(CardList.getCard(id).getAssociation())).build());
		}
		else if (args[1].equals("country")) {
			int id = MapManager.find(args[2]);
			if (id==-1) {
				sendMessage(e, ":x: That's not a country...");
				return;
			}
			sendMessage(e, new MessageBuilder().setEmbed(MapManager.get(id).getInfo()).build());
		}
		else {
			sendMessage(e, ":x: Write your arguments correctly. ˙>̇");
			return;
		}
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("TS.info", "TS.i");
	}

	@Override
	public String getDescription() {
		return "Obtain information about a card or country; this includes their current status in the game, as well as context.";
	}

	@Override
	public String getName() {
		return "Information (info, i)";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("TS.info card **[card id]** or TS.info country **[name]**");
	}

}

package commands;

import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PlayCommand extends Command {
	
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		int card = Integer.parseInt(args[0]);
		char mode = args[1].charAt(0);
		if (GameData.hasGameEnded()) {
			sendMessage(e, ":x: Have you tried turning it off and on again?");
			return;
		}
		if (!GameData.hasGameStarted()) {
			sendMessage(e, ":hourglass: There's a time and place for everything, but not now.");
			return;
		}
		if (!PlayerList.getArray().contains(e.getAuthor())) {
			sendMessage(e, ":x: Excuse me, but who are *you* playing as? China's abstracted as a card and the rest of the world has a board space each.");
			return;
		}
		if (card<=0 || card > 110) {
			sendMessage(e, ":x: Cards are indexed from 1 to 110.");
			return;
		}
		if (GameData.getAR()%2!=PlayerList.getArray().indexOf(e.getAuthor())&&GameData.getAR()>2) {
			sendMessage(e, ":x: Please wait for your Action Round to come.");
			return;
		}
		if (HandManager.getActive()!=0) {
			sendMessage(e, "There is already a card played.");
			return;
		}
		
		if (e.getAuthor().equals(PlayerList.getSSR())&&HandManager.SUNHand.contains(card)) {
			HandManager.play(1, card, mode, args);
		}
		else if (e.getAuthor().equals(PlayerList.getUSA())&&HandManager.USAHand.contains(card)) {
			HandManager.play(0, card, mode, args);
		}
		else {
			sendMessage(e, ":x: Do you even own that card?");
			return;
		}
	}
	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.play","TS.use");
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Play a card (play, use)";
	}
	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.play **[card ID]** - The user (who should be phasing player) plays the card with the corresponding ID.\n Is also used during the headline phase; the message must be immediately deleted for headline privacy.");
	}
	
	
}

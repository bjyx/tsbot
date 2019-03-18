package commands;

import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
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
		e.getMessage().delete().complete();
		if (GameData.getAR()%2!=PlayerList.getArray().indexOf(e.getAuthor())&&GameData.getAR()>2) {
			sendMessage(e, ":x: Please wait for your Action Round to come.");
			return;
		}
		if (HandManager.getActive()!=0) {
			sendMessage(e, ":x: There is already a card being played.");
			return;
		}
		if (mode == 'h'&&GameData.getAR()>2) {
			sendMessage(e, ":x: Can't play headlines in non-headline phases.");
			return;
		}
		if (mode != 'h'&&GameData.getAR()<=2) {
			sendMessage(e, ":x: Must play headlines in headline phases.");
			return;
		}
		if (mode == 'h'&&(card == 32 || card == 6)) {
			sendMessage(e, ":x: "+CardList.getCard(card)+" is not a card you can play in the headline.");
			return;
		}
		if (mode == 'h'&&HandManager.headline[PlayerList.getArray().indexOf(e.getAuthor())]!=0) {
			sendMessage(e, ":x: You've already set a headline.");
			return;
		}
		if (mode == 'o'&&CardList.getCard(card).getOps()==0) {
			sendMessage(e, ":x: Scoring cards must be played for the event.");
			return;
		}
		if (mode == 's' && (GameData.getSpace(PlayerList.getArray().indexOf(e.getAuthor()))==8||CardList.getCard(card).getOps()<Operations.spaceOps[GameData.getSpace(PlayerList.getArray().indexOf(e.getAuthor()))])) {
			sendMessage(e, ":x: You cannot play that card on the space race.");
			return;
		}
		if (mode=='s'&&GameData.hasSpace(PlayerList.getArray().indexOf(e.getAuthor()))) {
			sendMessage(e, ":x: Wait until next turn to space this card.");
			return;
		}
		if (mode=='e'&&!CardList.getCard(card).isPlayable()) {
			sendMessage(e, "This card's event is currently disabled. (Perhaps read the description again? Or, if you intended to use it for ops, try using 'o' there instead of 'e'.");
			return;
		}
		if (e.getAuthor().equals(PlayerList.getSSR())&&HandManager.SUNHand.contains(card)) {
			HandManager.play(1, card, mode);
		}
		else if (e.getAuthor().equals(PlayerList.getUSA())&&HandManager.USAHand.contains(card)) {
			HandManager.play(0, card, mode);
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
		return Arrays.asList("TS.play **[ID]** **[usage method]** \n- The user (who should be phasing player) plays the card with ID **ID** in one of the following manners:\n"
				+ "- `h`eadline\n"
				+ "- `e`vent (first)\n"
				+ "- `o`perations (first)\n"
				+ "- `s`pace");
	}
	
	
}

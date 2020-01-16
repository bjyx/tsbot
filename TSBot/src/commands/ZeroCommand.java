package commands;

import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import game.PlayerList;
import turnzero.TurnZero;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ZeroCommand extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub
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
		if (e.getChannel().equals(GameData.txtchnl)) {
			sendMessage(e, ":x: Don't. You're compromising your play.");
			return;
		}
		if (GameData.getTurn()!=0) {
			sendMessage(e, ":x: Is the setup phase not over by now?");
			return;
		}
		if (args.length<2) {
			sendMessage(e, ":x: Specify a statecraft card. To pass on doing so, play the dummy card.");
			return;
		}
		boolean result=false;
		if (e.getAuthor().equals(PlayerList.getUSA())) {
			result = TurnZero.playLeaderCard(0, args[1]);
			
		}
		if (e.getAuthor().equals(PlayerList.getSSR())) {
			result = TurnZero.playLeaderCard(1, args[1]);
			
		}
		if (result) {
			sendMessage(e, ":o: Done");
		}
		else {
			sendMessage(e, ":x: That's not a statecraft card.");
			return;
		}
		if (TurnZero.played[0]==null||TurnZero.played[1]==null) {
			return;
		}
		TurnZero.endCrisis();
		if (GameData.dec!=null) return;
		if (TurnZero.startCrisis()) {
			return;
		}
		CardList.initialize();
		HandManager.addToDeck(0);
		if (HandManager.removeEffect(100109)) {
			HandManager.Deck.remove((Integer)9);
			HandManager.SUNHand.add(9);
		}
		if (HandManager.removeEffect(100113)) {
			HandManager.Deck.remove((Integer)13);
			HandManager.SUNHand.add(13);
		}
		if (HandManager.removeEffect(100123)) {
			HandManager.Deck.remove((Integer)23);
			HandManager.USAHand.add(23);
		}
		HandManager.deal();
		SetupCommand.USSR = true;
		GameData.txtssr.sendMessage(PlayerList.getSSR().getAsMention() + ", please place six influence markers in Eastern Europe. (Use TS.setup)").complete();
		
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.zero","TS.0");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Play Leader Cards for Turn Zero.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Turn Zero";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.zero [cardName]\n"
				+ "*Private command*");
	}

}

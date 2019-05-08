package commands;

import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import events.Decision;
import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
/**
 * The command that handles any decisions to be made with regards to any card that cannot be handled by the event command list. The following cards have such an effect:
 * {@code 010 Blockade} - airlift or concede (USSR Event, USA decision)
 * {@code 020 Olympic Games} - compete or boycott (opponent chooses)
 * {@code 040 Missile Crisis} - resolve [COUNTRY] (country must be one of Cuba, West Germany, or Turkey)
 * {@code 042 Quagmire} - fuck quagmire
 * {@code 044 Bear Trap} - fuck bear trap
 * {@code 045 Summit} - decrease or increase (or not at all)
 * {@code 067 Grain Sales to Soviets} - return, event, operations, space
 * {@code 095 Latin American Debt Crisis} - discard or concede
 * {@code 098 Aldrich Ames} - The USSR gets to pick a specific card from the US's hand
 * {@code 104 Cambridge Five} - country for influence placement
 * {@code 106 NORAD} - country for influence placement
 * {@code 108 Our Man in Tehran} - any of the five cards seen
 * 
 * The command will also handle operations that come about as a result of events:
 * {@code 010 Blockade} - airlift or concede (USSR Event, USA decision)
 * 
 * @author [REDACTED]
 *
 */
public class DecisionCommand extends Command {

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
		if (!PlayerList.getArray().contains(e.getAuthor())) {
			sendMessage(e, ":x: Excuse me, but who are *you* playing as? China's abstracted as a card and the rest of the world has a board space each.");
			return;
		}
		if (GameData.dec == null) {
			sendMessage(e, ":x: No pending decisions. Are you sure you've satisfied the conditions for that?");
			return;
		}
		if (args.length==0) {
			sendMessage(e, ":x: Indecision is not an option.");
			return;
		}
		if (GameData.dec.card==10) {
			if (args[0].equals("airlift")) {
				if (args.length < 2) {
					sendMessage(e, ":x: Allocate some resources to your airlift.");
					return;
				}
				int card = Integer.parseInt(args[1]);
				if (!HandManager.USAHand.contains(card)) {
					sendMessage(e, ":x: We don't have that. At least not at our disposal.");
					return;
				}
				if (CardList.getCard(card).getOps()<3) {
					sendMessage(e, ":x: This won't be enough.");
					return;
				}
				Decision.BerlinAirLift(card);
				return;
			}
			if (args[0].equals("concede")) {
				Decision.BerlinConcede();
			}
			sendMessage(e, ":x: That's not an option.");
			return;
		}
		// TODO more events as enumerated above as they come
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.decide","TS.decision","TS.choose");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "When prompted, choose between one of two options.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Decision making";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("Usage information will be given by prompt.");
	}

}

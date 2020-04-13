package commands;

import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
/**
 * The command that handles any decisions to be made with regards to any card that cannot be handled by the event command list. The following cards have such an effect:
 * <ul>
 * <li> {@code 010 Blockade} - airlift or concede (USSR Event, USA decision)
 * <li> {@code 020 Olympic Games} - compete or boycott (opponent chooses)
 * <li> {@code 040 Missile Crisis} - resolve [COUNTRY] (country must be one of Cuba, West Germany, or Turkey)
 * <li> {@code 042 Quagmire} - fuck quagmire
 * <li> {@code 044 Bear Trap} - fuck bear trap
 * <li> {@code 045 Summit} - decrease or increase (or not at all; person conducting decision contingent on die rolls)
 * <li> {@code 067 Grain Sales to Soviets} - return, event, operations, space (contingent on card received)
 * <li> {@code 095 Latin American Debt Crisis} - discard or concede (USSR event, USA decision)
 * <li> {@code 098 Aldrich Ames} - The USSR gets to pick a specific card from the US's hand (contingent on seeing the US's hand)
 * <li> {@code 104 Cambridge Five} - country for influence placement (contingent on the US having scoring cards)
 * <li> {@code 106 NORAD} - country for influence placement (literal disconnect)
 * <li> {@code 108 Our Man in Tehran} - any of the five cards seen (contingent on seeing cards)
 * </ul>
 * The command will also handle all "operations" that come about as a result of events, as these may be contingent on seeing cards:
 * <ul>
 * <li> {@code 020 Olympic Games} - 4 Ops, any action (after a boycott)
 * <li> {@code 026 CIA Created} - 1 Op, any action
 * <li> {@code 047 Junta} - 2 Ops, realignments/coup in Latin America
 * <li> {@code 049 Missile Envy} - variable, any action
 * <li> {@code 057 ABM Treaty} - 4 Ops, any action
 * <li> {@code 062 Lone Gunman} - 1 Op, any action
 * <li> {@code 067 Grain Sales to Soviets} - variable, quite literally *any* action
 * <li> {@code 089 Soviets Shoot Down KAL-007} - 4 Ops, influence/realignments
 * <li> {@code 090 Glasnost} - 4 Ops, influence/realignments
 * <li> {@code 096 Tear Down This Wall} - 3 Ops, realignments/coup in Europe
 * </ul>
 * The command will also handle all events that come about as a result of another event:
 * <ul>
 * <li> {@code 005 Five-Year Plan} - US events
 * <li> {@code 047 Missile Envy} - Phasing player's event OR neutral event
 * <li> {@code 067 Grain Sales to Soviets} - Any event
 * <li> {@code 085 Star Wars} - Non-scoring cards
 * 
 * The command will also handle discarding of the card at the end of the turn. 
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
		if (args.length==1) {
			sendMessage(e, ":x: Indecision is not an option.");
			return;
		}
		if (GameData.ps!=null) {
			sendMessage(e, ":x: No.");
			return;
		}
		if (GameData.dec == null) {
			sendMessage(e, ":x: No pending decisions. Are you sure you've satisfied the conditions for that?");
			return;
		}
		if (e.getAuthor().equals(PlayerList.getArray().get((GameData.dec.sp+1)%2))) {
			sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
			return;
		}
		
		// TODO more events as enumerated above as they come
		GameData.checkScore(false, false);

		GameData.dec=null;
		TimeCommand.eventDone = true;
		if (HandManager.playmode == 'f') {
			TimeCommand.operationsRequired = true;
			GameData.ops = new Operations(GameData.phasing(), CardList.getCard(HandManager.activecard).getOpsMod(GameData.phasing()), true, true, false);
		}
		TimeCommand.prompt();
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("TS.decide","TS.decision","TS.choose");
	}

	@Override
	public String getDescription() {
		return "When prompted, supply input requested by an event.";
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

package commands;

import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import events.CardEmbedBuilder;
import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
/**
 * The command allowing players to play cards.
 * @author adalbert
 *
 */
public class PlayCommand extends Command {
	private static final List<Character> modes = Arrays.asList('h','e','o','s');
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
			sendMessage(e, ":x: Excuse me, but who are *you* playing as? There's no third way here.");
			return;
		}
		if (GameData.getTurn()==0) {
			sendMessage(e, ":x: You have some setting up to do, do you not?");
			return;
		}
		if (e.getChannel().equals(GameData.txtchnl)) {
			sendMessage(e, ":x: Don't. You're compromising your play.");
			return;
		}
		if (args.length==2) {
			try {
				if (Integer.parseInt(args[1])!=0) {
					sendMessage(e, ":x: How would you like to play this card?");
					return;
				}
				if (!TimeCommand.cardPlayedSkippable) {
					sendMessage(e, "Skip intent noted.");
					TimeCommand.cardPlayedSkippable = true;
					GameData.txtchnl.sendMessage(new CardEmbedBuilder()
							.setTitle("Inactivity")
							.setDescription(e.getAuthor().equals(PlayerList.getDem())?"The Democrats ":"The Communists " + "elect not to play a card.")
							.build())
					.complete();
					return;
				}
				else {
					sendMessage(e, ":x: Are you stuck in zugzwang? Too bad, you have to play a card.");
					return;
				}
			} catch (NumberFormatException e2) {
				sendMessage(e, ":x: That's no card. Cards are denoted by their ID, which is an integer.");
				return;
			}
		}
		if (args.length<3) {
			sendMessage(e, ":x: You're playing... what, exactly? And how?");
			return;
		}
		int card;
		try {
			card = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException err) {
			sendMessage(e, ":x: That's no card. Cards are denoted by integers.");
			return;
		}
		char mode = args[2].charAt(0);
		if (card<=0 || card > CardList.numberOfCards()) {
			sendMessage(e, ":x: Cards are indexed from 1 to 110.");
			return;
		}
		if (!modes.contains(mode)) {
			sendMessage(e, ":x: Modes can be any of e, o, or t. Not the one you chose, though.");
			return;
		}
		if (GameData.getAR()%2!=PlayerList.getArray().indexOf(e.getAuthor())) {
			sendMessage(e, ":x: Please wait for your Action Round to come.");
			return;
		}
		if (HandManager.activecard!=0) {
			sendMessage(e, ":x: There is already a card being played.");
			return;
		}
		if (TimeCommand.cardPlayed) {
			sendMessage(e, ":x: This is hardly your priority right now.");
			return;
		}
		if (mode == 'o'&&CardList.getCard(card).getOps()==0) { //All cards have either an op value or is a scoring card that is obligatorily played for the event
			sendMessage(e, ":x: A scoring card? For ops!? Preposterous!");
			return;
		}
		if (mode == 't' && (GameData.getT2(PlayerList.getArray().indexOf(e.getAuthor()))==8)) {
			sendMessage(e, ":x: You cannot play this card on the T-Square track.");
			return;
		}
		if (mode=='t'&&GameData.hasT2(PlayerList.getArray().indexOf(e.getAuthor()))) {
			sendMessage(e, ":x: Wait until next turn to T-Square this card.");
			return;
		}
		if ((mode=='e')&&!CardList.getCard(card).isPlayable(PlayerList.getArray().indexOf(e.getAuthor()))) {
			sendMessage(e, "This card's event is currently disabled. (Perhaps read the description again? Or, if you intended to use it for ops, try using 'o' there instead of 'e'.");
			return;
		}
		/*if (mode=='u'&&HandManager.handContains(GameData.phasing(), 32)) {
			sendMessage(e, "Actually have the 'UN Intervention' Card in your hand first.");
			return;
		}
		if (mode=='u'&&CardList.getCard(card).getAssociation()!=(GameData.phasing()+1)%2) {
			sendMessage(e, "This is not a card you can match with UN Intervention - just play it for Ops directly.");
			return;
		}*/
		if (e.getAuthor().equals(PlayerList.getCom())&&(HandManager.ComHand.contains(card))) {
			HandManager.play(1, card, mode);
		}
		else if (e.getAuthor().equals(PlayerList.getDem())&&(HandManager.DemHand.contains(card))) {
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
		return Arrays.asList("DF.play","DF.use","DF.p");
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Play a card from your hand.";
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Play a card (play, use, p)";
	}
	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("DF.play **[ID]** **[usage method]** \n"
				+ "- The user (who should be phasing player) plays the card with ID **ID** in one of the following manners:\n"
				+ "- `e`vent (first)\n"
				+ "- `o`perations (first)\n"
				+ "- `t`square\n");
	}
	
	
}

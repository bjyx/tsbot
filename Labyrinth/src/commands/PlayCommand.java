package commands;

import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import events.CardEmbedBuilder;
import events.Stasi;
import game.GameData;
import game.PlayerList;
import main.Common;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * The command allowing players to play cards.
 * @author adalbert
 *
 */
public class PlayCommand extends Command {
	private static final List<Character> modes = Arrays.asList('e','o','p');
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
			sendMessage(e, ":x: Excuse me, but who are *you* playing as?");
			return;
		}
		if (!SetupCommand.setup) {
			sendMessage(e, ":x: You have some setting up to do, do you not?");
			return;
		}
		if (e.getChannel().equals(GameData.txtchnl)) {
			sendMessage(e, ":x: Don't. You're compromising your play.");
			return;
		}
		if (args.length<2) {
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
		//start parsing order
		if (args.length==2) {
			if (card>0) { //any actual card
				sendMessage(e, ":x: How would you like to play this card?");
				return;
			}
			if (!TimeCommand.cardPlayedSkippable) {
				if (card==0) { //hold
					sendMessage(e, "Holding the card.");
					TimeCommand.usHolds = true;
					TimeCommand.cardPlayedSkippable = true;
					GameData.txtchnl.sendMessage(new CardEmbedBuilder()
						.setTitle("Suspicious Lack of Activity")
						.setDescription(e.getAuthor().equals(PlayerList.getUSA())?"The USA":"The Jihadist" + " elects not to play a card.")
						.build())
					.complete();
					return;
				}
				if (card<0) { //discard
					sendMessage(e, "Discarding the card.");
					HandManager.USAHand.clear(); //deletes the USA Hand, which should only be one card if this is being allowed
					TimeCommand.cardPlayedSkippable = true;
					GameData.txtchnl.sendMessage(new CardEmbedBuilder()
						.setTitle("Counterterrorism")
						.setDescription("The USA elects to discard .")
						.build())
					.complete();
					return;
				}
			}
			else {
				sendMessage(e, ":x: Are you stuck in zugzwang? Too bad, you have to play a card.");
				return;
			}
		}
		char mode = args[2].charAt(0);
		if (card<=0 || card > CardList.numberOfCards()) {
			sendMessage(e, ":x: Cards are indexed from 1 to 360 (and counting!).");
			return;
		}
		if (!modes.contains(mode)) {
			sendMessage(e, ":x: Modes can be any of e, o, v, p (as the jihadist), or r (as the US). Not the one you chose, though.");
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
		//TODO mode-based technicalities
		if ((mode=='e')&&!CardList.getCard(card).isPlayable(PlayerList.getArray().indexOf(e.getAuthor()))) {
			sendMessage(e, "This card's event is currently disabled. (Perhaps read the description again? Or, if you intended to use it for ops, try using 'o' there instead of 'e'.");
			return;
		}
		if (e.getAuthor().equals(PlayerList.getJih())&&(HandManager.JihHand.contains(card))) {
			HandManager.play(1, card, mode);
		}
		else if (e.getAuthor().equals(PlayerList.getUSA())&&(HandManager.USAHand.contains(card))) {
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
		return Arrays.asList("DF.play **<ID>** **<usage method>** **[ability]**\n"
				+ "- The user (who should be phasing player) plays the card with ID **ID** in one of the following manners:\n"
				+ "- `e`vent (first)\n"
				+ "- `o`perations (first)\n"
				+ "- `t`square\n"
				+ "- `c`ommon (this will cancel the event and allow you to conduct Operations)\n"
				+ "If anything is entered in **ability**, a T-Square ability is activated, if applicable. If this is an enemy's card, the card may be played for operations only (as if Common European Home was played); this requires ability 7, and **usage method** must be `o`. Otherwise, this card may be played for both Operations and the Event, in either order; this requires ability 8.");
	}
	
	
}

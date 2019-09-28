package commands;

import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.CardEmbedBuilder;
import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PlayCommand extends Command {
	private static final List<Character> modes = Arrays.asList('h','e','o','s','u');
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
				if (e.getAuthor().equals(PlayerList.getUSA())?(HandManager.USAHand.isEmpty()):(HandManager.SUNHand.isEmpty())) {
					sendMessage(e, "I see. You have no cards (and choose not to use the China Card)...");
					TimeCommand.cardPlayed = true;
					GameData.txtchnl.sendMessage(new CardEmbedBuilder()
							.setTitle("No cards to play!")
							.setDescription(e.getAuthor().equals(PlayerList.getUSA())?"The USA ":"The USSR " + "has no cards in their hand.")
							.build())
					.complete();
					return;
				}
				else {
					sendMessage(e, ":x: Are you stuck in zugzwang? Too bad, you have to play a card.");
					return;
				}
			} catch (NumberFormatException e2) {
				sendMessage(e, ":x: That's no card. Cards are denoted by integers.");
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
		if (card<=0 || card > 110) {
			sendMessage(e, ":x: Cards are indexed from 1 to 110.");
			return;
		}
		e.getMessage().delete().complete();
		if (!modes.contains(mode)) {
			sendMessage(e, ":x: Modes can be any of h, e, o, s, or u. Not the one you chose, though.");
			return;
		}
		if (GameData.getAR()%2!=PlayerList.getArray().indexOf(e.getAuthor())&&!GameData.isHeadlinePhase()) {
			sendMessage(e, ":x: Please wait for your Action Round to come.");
			return;
		}
		if (HandManager.activecard!=0) {
			sendMessage(e, ":x: There is already a card being played.");
			return;
		}
		if (((mode != 'o' && mode != 's')|| card != 49) && !GameData.isHeadlinePhase() && HandManager.effectActive(490+GameData.phasing())) {
			sendMessage(e, ":x: You have Missile Envy.");
			return;
		}
		if (mode == 'h'&&!GameData.isHeadlinePhase()) {
			sendMessage(e, ":x: Can't play headlines in non-headline phases.");
			return;
		}
		if (mode != 'h'&&GameData.isHeadlinePhase()) {
			sendMessage(e, ":x: Must play headlines in headline phases.");
			return;
		}
		if (mode == 'h'&&(card == 32 || card == 6)) { //UN Intervention and the China Card cannot be played in the headline
			sendMessage(e, ":x: That is not a card you can play in the headline.");
			return;
		}
		if (mode == 'e' && card == 32) {
			sendMessage(e, ":x: UN Intervention is a separate function. Use `TS.play [card here] u` instead of whatever you managed to type.");
			return;
		}
		if (mode == 'h'&&HandManager.headline[PlayerList.getArray().indexOf(e.getAuthor())]!=0) {
			sendMessage(e, ":x: You've already set a headline.");
			return;
		}
		if (mode == 'o'&&CardList.getCard(card).getOps()==0) { //All cards have either an op value or is a scoring card that is obligatorily played for the event
			sendMessage(e, ":x: This card must be played for the event only.");
			return;
		}
		if (mode == 's' && (GameData.getSpace(PlayerList.getArray().indexOf(e.getAuthor()))==8||CardList.getCard(card).getOpsMod(GameData.phasing())<Operations.spaceOps[GameData.getSpace(GameData.phasing())])) {
			sendMessage(e, ":x: You cannot play this card on the space race.");
			return;
		}
		if (mode=='s'&&GameData.hasSpace(PlayerList.getArray().indexOf(e.getAuthor()))) {
			sendMessage(e, ":x: Wait until next turn to space this card.");
			return;
		}
		if ((mode=='e'||mode=='u')&&!CardList.getCard(card).isPlayable(PlayerList.getArray().indexOf(e.getAuthor()))) {
			sendMessage(e, "This card's event is currently disabled. (Perhaps read the description again? Or, if you intended to use it for ops, try using 'o' there instead of 'e'.");
			return;
		}
		if (mode=='e'&&card==83&&GameData.phasing()==1) {
			sendMessage(e, "Oh no, I see what you're about to do and I state you have no reason to do so. "
					+ "`083 The Iron Lady` provides 1 USSR Influence in Argentina that can then be exploited by knowledge of the lackluster main code to spread directly into Chile and Uruguay. "
					+ "You'll have to do the operations first. There's absolutely no reason for you to use the event first - no sane USSR player would ever contest the UK, "
					+ "and you can only place influence using Operations if the country you place them in is adjacent to (or already is) a country containing your influence **at the start of your turn**. "
					+ "And no, I am not rewriting my code for one exception.");
			return;
		}
		if (mode=='u'&&HandManager.handContains(GameData.phasing(), 32)) {
			sendMessage(e, "Actually have the 'UN Intervention' Card in your hand first.");
			return;
		}
		if (mode=='u'&&CardList.getCard(card).getAssociation()!=(GameData.phasing()+1)%2) {
			sendMessage(e, "This is not a card you can match with UN Intervention - just play it for Ops directly.");
			return;
		}
		if (e.getAuthor().equals(PlayerList.getSSR())&&(HandManager.SUNHand.contains(card)||(card == 6 && HandManager.China==1))) {
			HandManager.play(1, card, mode);
		}
		else if (e.getAuthor().equals(PlayerList.getUSA())&&(HandManager.USAHand.contains(card)||(card == 6 && HandManager.China==0))) {
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
		return "Play a card from your hand.";
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Play a card (play, use)";
	}
	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.play **[ID]** **[usage method]** \n"
				+ "- The user (who should be phasing player) plays the card with ID **ID** in one of the following manners:\n"
				+ "- `h`eadline\n"
				+ "- `e`vent (first)\n"
				+ "- `o`perations (first)\n"
				+ "- `s`pace\n"
				+ "- `u`N Intervention for ops");
	}
	
	
}

package commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.CambridgeFive;
import events.CardEmbedBuilder;
import events.Decision;
import events.FiveYearPlan;
import events.GrainSales;
import events.MissileEnvy;
import events.OlympicGames;
import events.OurManInTehran;
import events.StarWars;
import game.GameData;
import game.PlayerList;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
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
		if ((HandManager.Effects.contains(400)||HandManager.Effects.contains(401))&&args[1].equals("resolve")) {
			if (args.length==2) {
				sendMessage(e, ":x: Write a country. Yes, you still write in the country even if you only have one option.");
				return;
			}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			
			int i = MapManager.find(args[2]);
			if (i==-1) {
				sendMessage(e, ":x: That's not a country.");
				return;
			}
			builder.setTitle("Crisis Resolved")
			.setDescription("Missiles pulled out of " + MapManager.get(i).name)
			.setFooter("","")
			.setColor(Color.green);
			if (HandManager.Effects.contains(400)) {
				if (e.getAuthor().equals(PlayerList.getSSR())) {
					sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
					return;
				}
				if (i!=19&&i!=17) {
					sendMessage(e, ":x: Those are not where your missiles are...");
					return;
				}
				if (MapManager.get(i).influence[0]<2) {
					sendMessage(e, ":x: So apparently you can't pull your missiles out of a country you installed them in. So much for peace.");
					return;
				}
				builder.changeInfluence(i, 0, -2);
			}
			else {
				if (e.getAuthor().equals(PlayerList.getUSA())) {
					sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
					return;
				}
				if (i!=65) {
					sendMessage(e, ":x: Those are not where your missiles are...");
					return;
				}
				if (MapManager.get(i).influence[1]<2) {
					sendMessage(e, ":x: So apparently you can't pull your missiles out of a country you installed them in. So much for peace.");
					return;
				}
				builder.changeInfluence(i, 1, -2);
			}
			GameData.txtchnl.sendMessage(builder.build()).complete();
			return;
		}
		if (!TimeCommand.trapDone && !GameData.isHeadlinePhase()) {
			int x;
			if (HandManager.Effects.contains(42) && (GameData.phasing()==0)) {
				if (e.getAuthor().equals(PlayerList.getSSR())) {
					sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
					return;
				}
				try {
					x = Integer.parseInt(args[1]);
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: That's not a number.");
					return;
				}
				if (!HandManager.USAHand.contains(x)) {
					sendMessage(e, ":x: Don't generate cards out of thin air.");
					return;
				}
				boolean canDiscard = false;
				for (Integer c : HandManager.USAHand) {
					if (CardList.getCard(c).getOpsMod(0)>=2) {
						canDiscard = true;
					}
				}
				if (canDiscard) {
					if (CardList.getCard(x).getOpsMod(0)<2) {
						sendMessage(e, ":x: This is not going to help here.");
						return;
					}
					if (x!=49 && CardList.getCard(49).getOpsMod(0)>=2 && HandManager.effectActive(490)) {
						sendMessage(e, ":x: https://www.gmtgames.com/nnts/FAQv5.pdf, ruling on Missile Envy use under Quagmire.");
						return;
					}
					HandManager.discard(0, x);
					EmbedBuilder builder = new CardEmbedBuilder().setTitle("Quagmire!").setDescription("Discarded " + CardList.getCard(x));
					int die = (int)(6*Math.random()+1);
					builder.addField("Roll: " + CardEmbedBuilder.intToEmoji(die), die<=4?"Success - Quagmire cancelled!":"Failure", false).setColor(die<=4?Color.blue:Color.red);
					if (die<=4) HandManager.removeEffect(42);
					GameData.txtchnl.sendMessage(builder.build()).complete();
					TimeCommand.trapDone=true;
				}
				else {
					if (CardList.getCard(x).getOpsMod(0)!=0) {
						sendMessage(e, ":x: You must play your scoring cards.");
						return;
					}
					CardList.getCard(x).onEvent(0, new String[] {});
					HandManager.discard(0, x);
					TimeCommand.trapDone=true;
				}
				
			}
			else if (HandManager.Effects.contains(44) && (GameData.phasing()==1)) {
				if (e.getAuthor().equals(PlayerList.getUSA())) {
					sendMessage(e, ":x: You aren't a puppeteer. Especially not for your opponent.");
					return;
				}
				try {
					x = Integer.parseInt(args[1]);
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: That's not a number.");
					return;
				}
				if (!HandManager.SUNHand.contains(x)) {
					sendMessage(e, ":x: Don't generate cards out of thin air.");
					return;
				}
				boolean canDiscard = false;
				for (Integer c : HandManager.SUNHand) {
					if (CardList.getCard(c).getOpsMod(1)>=2) {
						canDiscard = true;
					}
				}
				if (canDiscard) {
					if (CardList.getCard(x).getOpsMod(1)<2) {
						sendMessage(e, ":x: This is not going to help here.");
						return;
					}
					if (x!=49 && CardList.getCard(49).getOpsMod(1)>=2 && HandManager.effectActive(491)) {
						sendMessage(e, ":x: https://www.gmtgames.com/nnts/FAQv5.pdf, ruling on Missile Envy use under Bear Trap.");
						return;
					}
					HandManager.discard(1, x);
					EmbedBuilder builder = new CardEmbedBuilder().setTitle("Bear Trap!").setDescription("Discarded " + CardList.getCard(x));
					int die = (int)(6*Math.random()+1);
					builder.addField("Roll: " + CardEmbedBuilder.intToEmoji(die), die<=4?"Success - Bear Trap cancelled!":"Failure", false).setColor(die<=4?Color.red:Color.blue);
					if (die<=4) HandManager.removeEffect(44);
					GameData.txtchnl.sendMessage(builder.build()).complete();
					TimeCommand.trapDone=true;
				}
				else {
					if (CardList.getCard(x).getOpsMod(1)!=0) {
						sendMessage(e, ":x: You must play your scoring cards.");
						return;
					}
					CardList.getCard(x).onEvent(1, new String[] {});
					HandManager.discard(1, x);
					TimeCommand.trapDone=true;
				}
			}
			else {
				sendMessage(e, "You shouldn't be seeing this, but you are not a puppeteer.");
			}
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
		if (GameData.dec.card==0) {
			int card;
			try {
				card = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: Give the number of the card you wish to discard, or 0 if you do not wish to do so.");
				return;
			}
			if (card==0) {
				sendMessage(e, "Roger.");
				EmbedBuilder builder = new CardEmbedBuilder().setTitle("Space Race Advantage").setDescription("Discarded nothing.");
				GameData.txtchnl.sendMessage(builder.build()).complete();
				TimeCommand.isCardDiscarded=true;
			}
			else if (card<=CardList.numberOfCards()) {
				if (!HandManager.discard(GameData.dec.sp, card)) {
					sendMessage(e, ":x: You don't have this card.");
					return;
				}
				EmbedBuilder builder = new CardEmbedBuilder().setTitle("Space Race Advantage").setDescription("Discarded " + CardList.getCard(card));
				GameData.txtchnl.sendMessage(builder.build()).complete();
				TimeCommand.isCardDiscarded=true;
			}
			else {
				sendMessage(e, ":x: Give the number of the card you wish to discard.");
				return;
			}
			GameData.startTurn();
			TimeCommand.cardPlayed = false;
			if (!(GameData.getSpace(0)>=4&&GameData.getSpace(1)<4)) {
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", play a headline card.").complete();
			}
			if (!(GameData.getSpace(1)>=4&&GameData.getSpace(0)<4)) {
				GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", play a headline card.").complete();
			}
			TimeCommand.eventDone = false;
			TimeCommand.operationsDone = false;
			TimeCommand.eventRequired = false;
			TimeCommand.operationsRequired = false;
			TimeCommand.spaceRequired = false;
			TimeCommand.spaceDone = false;
			GameData.dec=null;
			return;
		}
		if (GameData.dec.card==5) {
			if (!CardList.getCard(FiveYearPlan.card).isFormatted(0, args)) {
				sendMessage(e, ":x: Format your arguments correctly.");
				return;
			}
			if (!CardList.getCard(FiveYearPlan.card).isPlayable(0)) {
				sendMessage(e, "Oops. Forgot you can't play that card for an event. Let's rectify that.");
				HandManager.discard(1, FiveYearPlan.card);
			}
			else {
				if (CardList.getCard(FiveYearPlan.card).isRemoved()) {
					HandManager.removeFromGame(1, FiveYearPlan.card);
				}
				else {
					HandManager.discard(1, FiveYearPlan.card);
				}
				CardList.getCard(FiveYearPlan.card).onEvent(0, args);
			}
		}
		if (GameData.dec.card==10) {
			if (args[1].equals("airlift")) {
				if (args.length < 3) {
					sendMessage(e, ":x: Allocate some resources to your airlift.");
					return;
				}
				int card;
				try {
					card = Integer.parseInt(args[2]);
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: Now how is that a card!?");
					return;
				}
				if (CardList.getCard(card).getOpsMod(0)<3) {
					sendMessage(e, ":x: This won't be enough.");
					return;
				}
				if (!HandManager.discard(0, card)) {
					sendMessage(e, ":x: We don't have that. At least not at our disposal.");
					return;
				}
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Berlin Airlift")
					.setDescription("West Berlin successfully supplied for over a year; Soviets forced to lift blockade")
					.setFooter("\"We can haul anything.\" \n"
							+ "- Curtis LeMay, 1948",Launcher.url("people/lemay.png"))
					.setColor(Color.blue);
				builder.addField("Soviets lift blockade of Berlin.", "Discarded card: "+CardList.getCard(card), false);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else if (args[1].equals("concede")) {
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Berlin Blockade")
					.setDescription("Concessions made to the Soviet Union to prevent starvation")
					.setFooter("\"What happens to Berlin, happens to Germany; what happens to Germany, happens to Europe.\" \n"
							+ "- Vyacheslav Molotov, 1948",Launcher.url("people/molotov.png"))
					.setColor(Color.red);
				builder.changeInfluence(20, 0, -MapManager.get(20).influence[0]);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else {
				sendMessage(e, ":x: That's not an option.");
				return;
			}
		}
		if (GameData.dec.card==20) {
			if (args[1].equals("boycott")) {
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Olympics Boycotted!")
					.setDescription("")
					.setFooter("\"To act differently would be tantamount to approving of the anti-Olympic actions of the U.S. authorities and organizers of the games.\" \n"
							+ "- Tass, 1984",Launcher.url("people/tass.png"))
					.setColor(Color.DARK_GRAY);
				builder.changeDEFCON(-1);
				GameData.txtchnl.sendMessage(builder.build()).complete();
				GameData.txtchnl.sendMessage("Awaiting " + (GameData.phasing()==0?GameData.roleusa.getAsMention():GameData.rolessr.getAsMention())+" to play 4 Operations points (remember to adjust to account for Red Scare/Purge/Containment/Brezhnev Doctrine).").complete();
				GameData.dec = new Decision(OlympicGames.host, 201);
				GameData.ops = new Operations(GameData.dec.sp, CardList.getCard(34).getOpsMod(GameData.dec.sp), true, true, true, true, false);
				return;
			}
			else if (args[1].equals("compete")) {
				CardEmbedBuilder builder = new CardEmbedBuilder();
				int[] die = {0,0};
				
				while (die[0] == die[1]) {
					die[0] = (int) (Math.random()*6 + 1);
					die[1] = (int) (Math.random()*6 + 1);
					die[OlympicGames.host] += 2;
					builder.addField(":flag_us::"+CardEmbedBuilder.numbers[die[0]]+":-:" + CardEmbedBuilder.numbers[die[1]]+":" + MapManager.get(85),die[0]==die[1]?"A tie - roll again.":("And "+(die[0]>die[1]?"the Americans":"the Soviets")+ " take home the gold!"),false);
				}
				builder.setTitle("Olympics in " + (OlympicGames.host==0?"Los Angeles": "Moscow"))
					.setDescription("")
					.setFooter("\"You were born to be a player. You were meant to be here. This moment is yours.\"\n"
							+ "- Herb Brooks, 1980",Launcher.url("people/brooks.png"))
					.setColor(die[0]>die[1]?Color.BLUE:Color.RED);
				builder.changeVP(die[0]>die[1]?2:-2);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else {
				sendMessage(e, ":x: That's not an option.");
				return;
			}
		}
		if (GameData.dec.card==201) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (GameData.dec.card==26) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (GameData.dec.card==32) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (GameData.dec.card==45) {
			int i;
			try {
				i = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: That's not an option. That's not even a number!");
				return;
			}
			if (Math.abs(i)>1) {
				sendMessage(e, ":x: Ah, nope. The number must be -1, 0, or 1.");
				return;
			}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			if (i==-1) {
				builder.setTitle("Escalation")
				.setDescription("Espionage incident threatens planned summit")
				.setColor(Color.GRAY);
			}
			else if (i==1) {
				builder.setTitle("Deténte")
				.setDescription("Soviet-American Summit leads to a decrease in tensions")
				.setColor(Color.GRAY);
			}
			else {
				builder.setTitle("Summit Inconclusive")
				.setDescription("Meeting ends without reaching an agreement on arms control")
				.setColor(Color.GRAY);
			}
			builder.changeDEFCON(i);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (GameData.dec.card==47) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (GameData.dec.card==49) {
			int i;
			try {
				i = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: Card IDs are integers. I suppose you forgot that.");
				return;
			}
			if (!(GameData.dec.sp==0?HandManager.USAHand.contains(i):HandManager.SUNHand.contains(i))) {
				sendMessage(e, ":x: Don't conjure cards out of thin air...");
				return;
			}
			if (CardList.getCard(i).getOps()<MissileEnvy.maxops) {
				sendMessage(e, ":x: Don't be a trickster. There are cards with a higher OP value.");
				return;
			}
			if (i==6) {
				sendMessage(e, ":x: 别摸我.");
				return;
			}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("The Arms Race and Nuclear War")
				.setDescription("Helen Caldicott")
				.setColor(GameData.dec.sp==0?Color.red:Color.blue)
				.setFooter("The superpowers often behave like two heavily armed blind men "
						+ "feeling their way around a room, each believing himself in mortal peril from the other, "
						+ "whom he assumes to have perfect vision... Of course, over time, "
						+ "even two armed blind men can do enormous damage to each other, "
						+ "not to speak of the room.\n" + 
						"- Henry Kissinger, 1979", Launcher.url("people/kissinger.png"));
			builder.addField("Missile Envy", GameData.dec.sp==0?"The USA":"The USSR" + " has given " + CardList.getCard(i) + " in exchange for " + CardList.getCard(49) + ".", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			MissileEnvy.card = i;
			HandManager.getFromDiscard(GameData.dec.sp, 49);
			if (CardList.getCard(i).getAssociation()==GameData.dec.sp) {
				HandManager.discard(GameData.dec.sp, i);
				GameData.ops = new Operations((GameData.dec.sp+1)%2, CardList.getCard(i).getOpsMod((GameData.dec.sp+1)%2), true, true, true, false, false);
			}
			else {
				if (CardList.getCard(i).isRemoved()) {
					HandManager.removeFromGame(GameData.dec.sp, i);
				}
				else if (i!=73) {
					HandManager.discard(GameData.dec.sp, i);
				}
				else {
					HandManager.removeFromHand(GameData.dec.sp, i);
				}
			}
			if (HandManager.effectActive(59)&&((i==13&&!HandManager.effectActive(65))||i==11||i==24||i==36||i==102)&&GameData.dec.sp==0) {
				CardEmbedBuilder fp = new CardEmbedBuilder();
				fp.setTitle("Flower Power")
					.setDescription("Anti-war protests erupt against the " + CardList.getCard(i).getName() + "!")
					.setFooter("\"I think that we're up against the strongest, well-trained, "
							+ "militant, revolutionary group that has ever assembled in America.\" \n"
							+ "- Jim Rhodes, 1970", Launcher.url("people/rhodes.png"))
					.setColor(Color.red);
				fp.changeVP(-2);
				GameData.txtchnl.sendMessage(fp.build()).complete();
			} //flowerpower still activates here
			GameData.dec = new Decision((GameData.dec.sp+1)%2, 491);
			return;
		}
		if (GameData.dec.card==491) {
			HandManager.addEffect(490 + (GameData.dec.sp+1)%2); //handles part 2
			if (CardList.getCard(MissileEnvy.card).getAssociation()==(GameData.dec.sp+1)%2) {
				boolean result = GameData.ops.ops(args);
				if (!result) return;
			}
			else if (CardList.getCard(MissileEnvy.card).isPlayable(GameData.dec.sp)) {
				if (!CardList.getCard(MissileEnvy.card).isFormatted(GameData.dec.sp, args))
				CardList.getCard(MissileEnvy.card).onEvent(GameData.dec.sp, args);
			}
		}
		if (GameData.dec.card==57) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (GameData.dec.card==62) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (GameData.dec.card==67) {
			List<Character> modes = Arrays.asList('r','e','o','s','u');

			if (args.length<2) {
				sendMessage(e, ":x: You're playing... what, exactly? And how?");
				return;
			}
			int card = GrainSales.card;
			char mode = args[1].charAt(0);
			e.getMessage().delete().complete();
			if (!modes.contains(mode)) {
				sendMessage(e, ":x: Modes can be any of r, e, o, s, or u. Not the one you chose, though.");
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
			if ((mode=='e'||mode=='u')&&!CardList.getCard(card).isPlayable(0)) {
				sendMessage(e, "This card's event is currently disabled. (Perhaps read the description again? Or, if you intended to use it for ops, try using 'o' there instead of 'e'.");
				return;
			}
			if (mode=='u'&&!HandManager.handContains(0, 32)) {
				sendMessage(e, "Actually have the 'UN Intervention' Card in your hand first.");
				return;
			}
			if (mode=='u'&&CardList.getCard(card).getAssociation()!=(GameData.phasing()+1)%2) {
				sendMessage(e, "This is not a card you can match with UN Intervention - just play it for Ops directly.");
				return;
			}
			if (HandManager.effectActive(59)&&((card==13&&!HandManager.effectActive(65))||card==11||card==24||card==36||card==102)&&mode!='s'&&mode!='r') {
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Flower Power")
					.setDescription("Anti-war protests erupt against the " + CardList.getCard(card).getName() + "!")
					.setFooter("\"I think that we're up against the strongest, well-trained, "
							+ "militant, revolutionary group that has ever assembled in America.\" \n"
							+ "- Jim Rhodes, 1970", Launcher.url("people/rhodes.png"))
					.setColor(Color.red);
				builder.changeVP(-2);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Soviet Union Accepts Grain Deal")
			.setDescription("9 million tons of grain to be bought per year")
			.setColor(Color.blue);
			if (mode=='r') {
				HandManager.transfer(0, card);
				GameData.ops = new Operations (0, CardList.getCard(67).getOpsMod(0), true, true, true, true, false);
				GrainSales.status = 'o';
				builder.addField("Obtained card: " + CardList.getCard(card), "Card to be returned to the USSR.", false);
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you have returned the card. You may conduct operations using Grain Sales to Soviets.").complete();
				GameData.dec = new Decision(0, 671);
			}
			if (mode=='e') {
				if (CardList.getCard(card).getAssociation()==1) {
					if (CardList.getCard(card).isRemoved()) {
						HandManager.removeFromGame(0, card);
					}
					else if (card!=73) {
						HandManager.discard(0, card);
					}
					else HandManager.removeFromHand(0, card);
					builder.addField("Obtained card: " + CardList.getCard(card), "Card to be used for the event before operations.", false);
					GrainSales.status = 'f';
					GameData.dec = new Decision(1, 671);
				}
				else {
					if (CardList.getCard(card).isRemoved()) {
						HandManager.removeFromGame(0, card);
					}
					else if (card!=73) {
						HandManager.discard(0, card);
					}
					else HandManager.removeFromHand(0, card);
					builder.addField("Obtained card: " + CardList.getCard(card), "Card to be used as event.", false);
					GrainSales.status = 'e';
					GameData.dec = new Decision(0, 671);
				}
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may implement this event.").complete();
			}
			if (mode=='o') {
				if (CardList.getCard(card).getAssociation()==1&&CardList.getCard(card).isPlayable(0)) {
					if (CardList.getCard(card).isRemoved()) {
						HandManager.removeFromGame(0, card);
					}
					else if (card!=73) {
						HandManager.discard(0, card);
					}
					else HandManager.removeFromHand(0, card);
					builder.addField("Obtained card: " + CardList.getCard(card), "Card to be used for operations before the event.", false);
					GrainSales.status = 'l'; //event last
					GameData.dec = new Decision(0, 671);
				}
				else {
					HandManager.discard(0, card);
					builder.addField("Obtained card: " + CardList.getCard(card), "Card to be used for operations.", false);
					GrainSales.status = 'o'; //ops only
					GameData.dec = new Decision(0, 671);
				}
				GameData.ops = new Operations(0, CardList.getCard(card).getOpsMod(0), true, true, true, false, false);
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may now perform the operations.").complete();

			}
			if (mode=='s') {
				HandManager.discard(0, card);
				GrainSales.status = 's';
				builder.addField("Obtained card: " + CardList.getCard(card), "Card to be used on the space race.", false);
				GameData.ops = new Operations(0, CardList.getCard(card).getOpsMod(0), false, false, false, true, false);
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may now send this card to space.").complete();
				GameData.dec = new Decision(0, 671);
			}
			if (mode=='u') {
				EmbedBuilder un = new CardEmbedBuilder().setTitle("UN INTERVENTION!")
						.setDescription("The UN collectively agrees on something for once")
						.setFooter("\"It is not the Soviet Union or indeed any other big Powers who need the United Nations for their protection. "
								+ "It is all the others. In this sense, the Organization is first of all their Organization "
								+ "and I deeply believe in the wisdom with which they will be able to use it and guide it. "
								+ "I shall remain in my post during the term of my office as a servant of the Organization in the interests of all those other nations, as long as they wish me to do so.\"\n"
								+ "- Dag Hammarskjöld, 1960", Launcher.url("people/hammarskjold.png"))
						.setColor(Color.blue)
						.addField("UN Security Council Resolution", "The event of "+ CardList.getCard(GrainSales.card)+" has been condemned by the UN, and will not occur.", false);
				GameData.txtchnl.sendMessage(un.build()).complete();
				HandManager.discard(0, card);
				HandManager.discard(0, 32);
				GrainSales.status = 'o';
				builder.addField("Obtained card: " + CardList.getCard(GrainSales.card), "Card matched with UN Intervention for operations.", false);
				GameData.ops = new Operations(0, CardList.getCard(card).getOpsMod(0), true, true, true, false, false);
				GameData.txtusa.sendMessage(GameData.roleusa.getAsMention() + ", you may now perform the operations.");
				GameData.dec = new Decision(0, 671);
			}
			GameData.txtchnl.sendMessage(builder.build()).complete();
			return;
		}
		if (GameData.dec.card==671) {
			if (GrainSales.status=='e'||GrainSales.status=='f') {
				if (!CardList.getCard(GrainSales.card).isFormatted(0, args)) {
					sendMessage(e, ":x: Format your arguments correctly.");
					return;
				}
				CardList.getCard(GrainSales.card).onEvent(0, args);
			}
			if (GrainSales.status=='o'||GrainSales.status=='l') {
				boolean result = GameData.ops.ops(args);
				
				if (!result) {
					return;
				}
			}
			if (GrainSales.status=='s') {
				GameData.ops.space();
			}
			if (GrainSales.status=='l') {
				GrainSales.status='e';
				GameData.dec = new Decision(1, 671);
				return;
			}
			if (GrainSales.status=='f') {
				GameData.dec = new Decision(0, 671);
				GrainSales.status='o';
				return;
			}
		}
		if (GameData.dec.card==85) {
			if (!CardList.getCard(StarWars.target).isFormatted(0, args)) {
				sendMessage(e, ":x: Format your arguments correctly.");
				return;
			}
			if (HandManager.effectActive(59)&&((StarWars.target==13&&!HandManager.effectActive(65))||StarWars.target==11||StarWars.target==24||StarWars.target==36||StarWars.target==102)) {
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Flower Power")
					.setDescription("Anti-war protests erupt against the " + CardList.getCard(StarWars.target).getName() + "!")
					.setFooter("\"I think that we're up against the strongest, well-trained, "
							+ "militant, revolutionary group that has ever assembled in America.\" \n"
							+ "- Jim Rhodes, 1970", Launcher.url("people/rhodes.png"))
					.setColor(Color.red);
				builder.changeVP(-2);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else {
				CardList.getCard(StarWars.target).onEvent(0, args);
			}
		}
		if (GameData.dec.card==89) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (GameData.dec.card==90) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (GameData.dec.card==95) {
			if (args[1].equals("aid")) {
				if (args.length < 3) {
					sendMessage(e, ":x: Allocate some resources to that relief package.");
					return;
				}
				int card;
				try {
					card = Integer.parseInt(args[2]);
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: Now how is that a card!?");
					return;
				}
				if (CardList.getCard(card).getOpsMod(0)<3) {
					sendMessage(e, ":x: This won't be enough.");
					return;
				}
				if (!HandManager.discard(0, card)) {
					sendMessage(e, ":x: We don't have that. At least not at our disposal.");
					return;
				}
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Latin American Debt Crisis")
					.setDescription("IMF Intervention turns Latin America to free-market capitalism")
					.setColor(Color.blue);
				builder.addField("Debt crisis relieved.", "Discarded card: "+CardList.getCard(card), false);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else if (args[1].equals("concede")) {
				GameData.dec = new Decision(1, 951);
				GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", the US has failed to address the debt crisis. Select two countries in South America to double your influence in.").complete();
			}
			else {
				sendMessage(e, ":x: That's not an option.");
				return;
			}
		}
		if (GameData.dec.card==951) {
			ArrayList<Integer> doable = new ArrayList<Integer>();
			ArrayList<Integer> order = new ArrayList<Integer>();
			for (int i=74; i<84; i++) {
				if (MapManager.get(i).influence[1]>0) doable.add(i);
			}
			if (doable.size()<=2) order = doable;
			else {
				if (args.length < 3) {
					sendMessage(e, ":x: Two countries—seize that opportunity.");
					return;
				}
				int k = MapManager.find(args[1]);
				int l = MapManager.find(args[2]);
				if (k==l) {
					sendMessage(e, ":x: Duplicate.");
					return;
				}
				if (k==-1 || l==-1) {
					sendMessage(e, ":x: Non-existent country.");
					return;
				}
				if (doable.contains(k)&&doable.contains(l)) {
					order.add(k);
					order.add(l);
				}
				else {
					sendMessage(e, ":x: Invalid country.");
					return;
				}
			}
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Latin American Debt Crisis")
				.setDescription("Debt forces economic restructuring in Latin American Countries")
				.setColor(Color.red);
			for (Integer i : order) {
				builder.changeInfluence(i, 1, MapManager.get(i).influence[1]);
			}
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		if (GameData.dec.card==96) {
			boolean result = GameData.ops.ops(args);
			if (!result) {
				return;
			}
		}
		if (GameData.dec.card==98) {
			int card;
			try {
				card = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException err) {
				sendMessage(e, ":x: Give the number of the card you wish to discard.");
				return;
			}
			if (0<card && card<=CardList.numberOfCards()) {
				if (!HandManager.discard(0, card)) {
					sendMessage(e, ":x: Ames hasn't told you of this card. Better to make use of what intelligence you have.");
					return;
				}
				EmbedBuilder builder = new CardEmbedBuilder().setTitle("Compromised Operations").setDescription("Discarded " + CardList.getCard(card) + " to Aldrich Ames.");
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else {
				sendMessage(e, ":x: Give the number of the card you wish to discard.");
				return;
			}
		}
		if (GameData.dec.card==104) {
			int i = MapManager.find(args[1]);
			if (i!=-1 && CambridgeFive.regions[MapManager.get(i).region]) {
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Intelligence Secrets Leaked")
				.setDescription("Attempted operations in " + MapManager.get(i).name + " fail")
				.setFooter("\"The agents we sent into Albania were armed men intent on murder, sabotage and assassination ..."
						+ "They knew the risks they were running. I was serving the interests of the Soviet Union "
						+ "and those interests required that these men were defeated. "
						+ "To the extent that I helped defeat them, even if it caused their deaths, I have no regrets.\"\n"
						+ "- Kim Philby, on Operation Valuable", Launcher.url("people/philby.png"))
				.setColor(Color.RED);
				builder.changeInfluence(i, 1, 1);
				GameData.txtchnl.sendMessage(builder.build()).complete();
			}
			else {
				sendMessage(e, ":x: Philby would like to inform you that you aren't reacting to anything related to this intelligence.");
				return;
			}
		}
		if (GameData.dec.card==106) {
			int i = MapManager.find(args[1]);
			if (i!=1 && MapManager.get(i).influence[0]>0) {
				CardEmbedBuilder builder = new CardEmbedBuilder();
				builder.setTitle("Two Minutes to Midnight")
				.setDescription("Allied forces in " + MapManager.get(i).name + " put on high alert")
				.setColor(Color.BLUE);
				builder.changeInfluence(i, 0, 1);
				GameData.txtchnl.sendMessage(builder.build()).complete();
				TimeCommand.NORAD = true;
			}
			else {
				sendMessage(e, ":x: You must drop this influence in a country with US influence.");
				return;
			}
		}
		if (GameData.dec.card==108) {
			ArrayList<Integer> order = new ArrayList<Integer>();
			CardEmbedBuilder builder = new CardEmbedBuilder();
			builder.setTitle("Our Man in " + OurManInTehran.capital[OurManInTehran.country-21])
			.setDescription("The President and his ally in the Middle East")
			.setFooter("\"We have no other nation on earth... closer to us in planning our mutual military security. "
					+ "We have no other nation with whom we have closer consultation on regional problems that concern us both. "
					+ "And there is no leader with whom I have a deeper sense of personal gratitude and personal friendship.\"\n"
					+ "- James E. Carter, 1978", Launcher.url("people/carter.png"))
			.setColor(Color.BLUE);
			for (int i = 1; i<args.length; i++) {
				try {
					int x = Integer.parseInt(args[i]);
					order.add(x);
					if (order.indexOf(x)!=order.lastIndexOf(x)) {
						sendMessage(e, ":x: No duplicates.");
						return;
					}
				}
				catch (NumberFormatException err) {
					sendMessage(e, ":x: This is not a card. This isn't even a number!");
					return;
				}
			}
			String str = "";
			if (order.size()==1 && order.get(0)==0) {
				sendMessage(e, "Roger.");
				builder.addField("", "Discarded nothing.", false);
			}
			else {
				if (OurManInTehran.cards.containsAll(order)) {
					for (Integer i : order) {
						HandManager.Discard.add(i);
						OurManInTehran.cards.remove(i);
						str += CardList.getCard(i) + "\n";
					}
				}
				else {
					sendMessage(e, ":x: This definitely didn't turn up in your sneak preview.");
					return;
				}
				builder.addField("Discarded the following:", str, false);
			}
			HandManager.Deck.addAll(OurManInTehran.cards);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
		
		// TODO more events as enumerated above as they come
		GameData.checkScore(false, false);

		GameData.dec=null;
		if (GameData.isHeadlinePhase()) {
			if (TimeCommand.hl1) TimeCommand.hl2 = true;
			else TimeCommand.hl1 = true;
			if (HandManager.precedence==0&&TimeCommand.hl2==false) {
				HandManager.activecard=HandManager.headline[1];
			}
			else if (HandManager.precedence==1) {
				HandManager.activecard=HandManager.headline[0];
			}	
			TimeCommand.prompt();
			return;
		}
		TimeCommand.eventDone = true;
		if (HandManager.playmode == 'f') {
			TimeCommand.operationsRequired = true;
			GameData.ops = new Operations(GameData.phasing(), CardList.getCard(HandManager.activecard).getOpsMod(GameData.phasing()), true, true, true, false, false);
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

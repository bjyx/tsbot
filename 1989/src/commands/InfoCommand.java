package commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.CardEmbedBuilder;
import game.GameData;
import main.Common;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
/**
 * The command to get information about a card. 
 * @author adalbert
 *
 */
public class InfoCommand extends Command {
	
	private static final String[] ussrStab = {"","<:tank:648119369041182741> Kremlin Coup!",":flag_ee: Breakaway",":flag_lv: Baltic Way",":flag_lt: Sąjūdis","<:flag_su:648119356387098644> Stable"};
	
	//If a URL ever comes up for a 1989 game state generator, lmk.

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub
		if (!GameData.hasGameStarted()) {
			sendMessage(e, ":x: What game?");
			return;
		}
		if (args.length<2) {
			sendMessage(e, ":x: Not enough arguments.");
			return;
		}
		//game data
		else if (args[1].equals("game")) {
			EmbedBuilder builder = new CardEmbedBuilder()
					.setTitle("Game State")
					.setColor(Color.WHITE)
					.addField("Score: " + (GameData.getScore()<0?StartCommand.emojiID[10]:(GameData.getScore()>0?StartCommand.emojiID[9]:"")) + CardEmbedBuilder.intToEmoji(Math.abs(GameData.getScore())), "", false)
					.addField("USSR Stability: " + InfoCommand.ussrStab[GameData.getStab()], "", false)
					.addField("T-Square: " + (GameData.hasT2(0)?StartCommand.emojiID[3]:StartCommand.emojiID[1]) + CardEmbedBuilder.intToEmoji(GameData.getT2(0))
						+(GameData.hasAbility(0, 1)?":frame_photo:":"")
						+(GameData.hasAbility(0, 2)?":man_student:":"")
						+(GameData.hasAbility(0, 5)?":statue_of_liberty:":"")
						+(GameData.hasAbility(0, 6)?"<:pla:696879280935927898>":"")
						+(GameData.hasAbility(0, 7)?(Operations.seven>2?":construction:":":japanese_castle:"):"")
						+(GameData.hasAbility(0, 8)?(Operations.eight>2?"<:InflDC:696610045730881588>":"<:InflD:696610045663772723>"):"")+"\n"
						+(GameData.hasT2(1)?StartCommand.emojiID[2]:StartCommand.emojiID[0]) + CardEmbedBuilder.intToEmoji(GameData.getT2(1))
						+(GameData.hasAbility(1, 1)?":frame_photo:":"")
						+(GameData.hasAbility(1, 2)?":man_student:":"")
						+(GameData.hasAbility(1, 5)?":statue_of_liberty:":"")
						+(GameData.hasAbility(1, 6)?":<:pla:696879280935927898>:":"")
						+(GameData.hasAbility(1, 7)?(Operations.seven>2?":black_square:":":japanese_castle:"):"")
						+(GameData.hasAbility(1, 8)?(Operations.eight>2?"<:InflCC:696610045575692308>":"<:InflC:696610045621829713>"):"")+"\n","",false);
			for (int i=0; i<6; i++) {
				String map = "";
				for (int j=Common.bracket[i]; j<Common.bracket[i+1]; j++) {
					map += MapManager.get(j) + (MapManager.get(j).isControlledBy()==0?StartCommand.emojiID[3]:(MapManager.get(j).isControlledBy()==1?StartCommand.emojiID[2]:"<:InflN:648119362619834373>"))+ (MapManager.get(j).isControlledBy()==0?"**":"") + MapManager.get(j).support[0] + (MapManager.get(j).isControlledBy()==0?"**":"") + "/" + (MapManager.get(j).isControlledBy()==1?"**":"") + MapManager.get(j).support[1] + (MapManager.get(j).isControlledBy()==1?"**\n":"\n");
				}
				builder.addField(Common.flags[i] + " " + Common.countries[i], map, false);
			}
		}
		else if (args[1].equals("cards")) {
			EmbedBuilder builder = new CardEmbedBuilder()
					.setTitle("Cards Played")
					.setColor(Color.WHITE);
			String cards = "";
			for (int i : HandManager.Discard) {
				cards += CardList.getCard(i) + "\n";
			}
			builder.addField("Discard Pile", cards, false);
			cards = "";
			for (int i : HandManager.Removed) {
				cards += CardList.getCard(i) + "\n";
			}
			builder.addField("Removed From Game", cards, false);
			builder.addField("Deck", "Size: " + HandManager.Deck.size(), false);
			builder.addField("Democrat Hand", "Size: " + HandManager.DemHand.size(), false);
			builder.addField("Communist Hand", "Size: " + HandManager.ComHand.size(), false);
			sendMessage(e, new MessageBuilder().setEmbed(builder.build()).build());
		}
		else if (args.length<3) {
			sendMessage(e, ":x: Not enough arguments.");
			return;
		}
		else if (args[1].equals("card")) {
			int id = Integer.parseInt(args[2]);
			if (id <= 0 || id > CardList.numberOfCards()) {
				sendMessage(e, ":x: Cards are indexed from 1 to " + CardList.numberOfCards() + ".");
				return;
			}
			sendMessage(e, new MessageBuilder().setEmbed(CardList.getCard(id).toEmbed(CardList.getCard(id).getAssociation()).setAuthor("Information", null, Launcher.url("people/qmark.png"))
					.addField("Arguments:",CardList.getCard(id).getArguments(),false).build()).build());
		}
		else if (args[1].equals("space")) {
			int id = MapManager.find(args[2]);
			if (id==-1) {
				sendMessage(e, ":x: That's not a space...");
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
		return Arrays.asList("DF.info", "DF.i");
	}

	@Override
	public String getDescription() {
		return "Obtain information.";
	}

	@Override
	public String getName() {
		return "Information (info, i)";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("DF.info card **[card id]** or DF.info country **[name]** or DF.info game");
	}

}

package commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class InfoCommand extends Command {
	
	private char urlParser(int i) {
		if (i<10) return (char) ('0'+i);
		else if (i<36) return (char) ('a'+i-10);
		else return (char) ('A'+i-36);
	}

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		// TODO Auto-generated method stub
		
		if (args.length<2) {
			sendMessage(e, ":x: Not enough arguments.");
			return;
		}
		//aldrich ames
		if (args[1].equals("ames") && HandManager.effectActive(98) && e.getChannel().equals(GameData.txtssr)) {
			GameData.txtssr.sendMessage(HandManager.getUSAHand()).complete();
			return;
		}
		//game data
		if (args[1].equals("game")) {
			EmbedBuilder builder = new CardEmbedBuilder()
					.setTitle("Game State")
					.setColor(Color.WHITE)
					.addField("Score: " + (GameData.getScore()<0?"<:InfluenceRC:"+StartCommand.emojiID[10]+">":(GameData.getScore()>0?"<:InfluenceAC:" + StartCommand.emojiID[9]+">":"")) + CardEmbedBuilder.intToEmoji(Math.abs(GameData.getScore())), "", false)
					.addField("DEFCON " + CardEmbedBuilder.intToEmoji(GameData.getDEFCON()), "", false)
					.addField("Military Operations: ", "<:InfluenceA"+(GameData.getMilOps(0)>GameData.getDEFCON()?"C:" + StartCommand.emojiID[9]:":" + StartCommand.emojiID[6]) + ">" + CardEmbedBuilder.intToEmoji(GameData.getMilOps(0)) + "/" + CardEmbedBuilder.intToEmoji(GameData.getDEFCON()) + "\n"
							+ "<:InfluenceR"+(GameData.getMilOps(1)>GameData.getDEFCON()?"C:" + StartCommand.emojiID[10]:":" + StartCommand.emojiID[7]) + ">" + CardEmbedBuilder.intToEmoji(GameData.getMilOps(1)) + "/" + CardEmbedBuilder.intToEmoji(GameData.getDEFCON()), false)
					.addField("Space Race: " + "<:InfluenceA"+(GameData.getSpace(0)>GameData.getSpace(1)?"C:" + StartCommand.emojiID[9]:":" + StartCommand.emojiID[6]) + ">" + CardEmbedBuilder.intToEmoji(GameData.getSpace(0))+"\n"
							+ "<:InfluenceR"+(GameData.getSpace(1)>GameData.getSpace(0)?"C:" + StartCommand.emojiID[10]:":" + StartCommand.emojiID[7]) + ">" + CardEmbedBuilder.intToEmoji(GameData.getSpace(1)), "", false);
			String map = "";
			for (int i=0; i<84; i++) {
				map += MapManager.get(i) + (MapManager.get(i).isControlledBy()==0?"<:InfluenceAC:" + StartCommand.emojiID[9]:(MapManager.get(i).isControlledBy()==1?"<:InfluenceRC:" + StartCommand.emojiID[10]:"<:InfluenceN:" + StartCommand.emojiID[8])) +"> " + (MapManager.get(i).isControlledBy()==0?"**":"") + MapManager.get(i).influence[0] + (MapManager.get(i).isControlledBy()==0?"**":"") + "/" + (MapManager.get(i).isControlledBy()==1?"**":"") + MapManager.get(i).influence[1] + (MapManager.get(i).isControlledBy()==1?"**\n":"\n");
			}
			if (GameData.ccw) map += ":flag_cn:" + (MapManager.get(86).isControlledBy()==1?"**":"") + MapManager.get(86).influence[1] + (MapManager.get(86).isControlledBy()==1?"**\n":"\n");;
			builder.addField("Map:", map, false);
			
			//for (Integer i : HandManager.Effects) {
				//TODO things
			//}
			sendMessage(e, new MessageBuilder().setEmbed(builder.build()).build());
		}
		if (args[1].equals("cards")) {
			//TODO things
		}
		if (args[1].equals("map")) {
			String url = "http://twistrug.jjt.io/#/board/00000000/" 
					+ urlParser(GameData.getScore()+20)
					+ urlParser(GameData.getDEFCON())
					+ urlParser(GameData.getTurn())
					+ urlParser(GameData.getAR())
					+ urlParser(GameData.getMilOps(0))
					+ urlParser(GameData.getMilOps(1))
					+ urlParser(GameData.getSpace(0))
					+ urlParser(GameData.getSpace(1));
			for (int i=0; i<84; i++) {
				url += urlParser(MapManager.get(i).influence[0]);
				url += urlParser(MapManager.get(i).influence[1]);
			}
			sendMessage(e, url);
		}
		if (args.length<3) {
			sendMessage(e, ":x: Not enough arguments.");
			return;
		}
		if (args[1].equals("card")) {
			int id = Integer.parseInt(args[2]);
			if (id <= 0 || id > CardList.numberOfCards()) {
				sendMessage(e, ":x: Cards are indexed from 1 to " + CardList.numberOfCards() + ".");
				return;
			}
			sendMessage(e, new MessageBuilder().setEmbed(CardList.getCard(id).toEmbed(CardList.getCard(id).getAssociation()).setAuthor("Information", null, Launcher.url("emoji/InfluenceNC.png"))
					.addField("Arguments:",CardList.getCard(id).getArguments(),false).build()).build());
		}
		else if (args[1].equals("country")) {
			int id = MapManager.find(args[2]);
			if (id==-1) {
				sendMessage(e, ":x: That's not a country...");
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
		return Arrays.asList("TS.info", "TS.i");
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
		return Arrays.asList("TS.info card **[card id]** or TS.info country **[name]** or TS.info game");
	}

}

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
					.addField("Score: " + (GameData.getScore()<0?StartCommand.emojiID[10]:(GameData.getScore()>0?StartCommand.emojiID[9]:"")) + CardEmbedBuilder.intToEmoji(Math.abs(GameData.getScore())), "", false)
					.addField("DEFCON " + CardEmbedBuilder.intToEmoji(GameData.getDEFCON()), "", false)
					.addField("Military Operations: ", (GameData.getMilOps(0)>GameData.getDEFCON()?StartCommand.emojiID[9]:StartCommand.emojiID[6]) + CardEmbedBuilder.intToEmoji(GameData.getMilOps(0)) + "/" + CardEmbedBuilder.intToEmoji(GameData.getDEFCON()) + "\n"
							+ (GameData.getMilOps(1)>GameData.getDEFCON()?StartCommand.emojiID[10]:StartCommand.emojiID[7])+CardEmbedBuilder.intToEmoji(GameData.getMilOps(1)) + "/" + CardEmbedBuilder.intToEmoji(GameData.getDEFCON()), false)
					.addField("Space Race: " + (GameData.hasSpace(0)?StartCommand.emojiID[9]:StartCommand.emojiID[6]) + CardEmbedBuilder.intToEmoji(GameData.getSpace(0))+(GameData.hasAbility(0, 2)?":dog2:":"")+(GameData.hasAbility(0, 4)?":rocket:":"")+(GameData.hasAbility(0, 6)?":full_moon:":"")+(GameData.hasAbility(0, 8)?":satellite_orbital:":"")+"\n"
							+ (GameData.hasSpace(0)?StartCommand.emojiID[10]:StartCommand.emojiID[7]) + CardEmbedBuilder.intToEmoji(GameData.getSpace(1))+(GameData.hasAbility(0, 2)?":dog2:":"")+(GameData.hasAbility(0, 4)?":rocket:":"")+(GameData.hasAbility(0, 6)?":full_moon:":"")+(GameData.hasAbility(0, 8)?":satellite_orbital:":""), "", false);
			String map = "";
			for (int i=0; i<21; i++) {
				map += MapManager.get(i) + (MapManager.get(i).isControlledBy()==0?StartCommand.emojiID[9]:(MapManager.get(i).isControlledBy()==1?StartCommand.emojiID[10]:StartCommand.emojiID[8]))+ (MapManager.get(i).isControlledBy()==0?"**":"") + MapManager.get(i).influence[0] + (MapManager.get(i).isControlledBy()==0?"**":"") + "/" + (MapManager.get(i).isControlledBy()==1?"**":"") + MapManager.get(i).influence[1] + (MapManager.get(i).isControlledBy()==1?"**\n":"\n");
			}
			builder.addField("Europe: ", map, false);
			map = "";
			for (int i=21; i<31; i++) {
				map += MapManager.get(i) + (MapManager.get(i).isControlledBy()==0?StartCommand.emojiID[9]:(MapManager.get(i).isControlledBy()==1?StartCommand.emojiID[10]:StartCommand.emojiID[8]))+ (MapManager.get(i).isControlledBy()==0?"**":"") + MapManager.get(i).influence[0] + (MapManager.get(i).isControlledBy()==0?"**":"") + "/" + (MapManager.get(i).isControlledBy()==1?"**":"") + MapManager.get(i).influence[1] + (MapManager.get(i).isControlledBy()==1?"**\n":"\n");
			}
			builder.addField("Middle East: ", map, false);
			map = "";
			for (int i=31; i<46; i++) {
				map += MapManager.get(i) + (MapManager.get(i).isControlledBy()==0?StartCommand.emojiID[9]:(MapManager.get(i).isControlledBy()==1?StartCommand.emojiID[10]:StartCommand.emojiID[8]))+ (MapManager.get(i).isControlledBy()==0?"**":"") + MapManager.get(i).influence[0] + (MapManager.get(i).isControlledBy()==0?"**":"") + "/" + (MapManager.get(i).isControlledBy()==1?"**":"") + MapManager.get(i).influence[1] + (MapManager.get(i).isControlledBy()==1?"**\n":"\n");
			}
			builder.addField("Asia: ", map, false);
			map = "";
			for (int i=46; i<64; i++) {
				map += MapManager.get(i) + (MapManager.get(i).isControlledBy()==0?StartCommand.emojiID[9]:(MapManager.get(i).isControlledBy()==1?StartCommand.emojiID[10]:StartCommand.emojiID[8]))+ (MapManager.get(i).isControlledBy()==0?"**":"") + MapManager.get(i).influence[0] + (MapManager.get(i).isControlledBy()==0?"**":"") + "/" + (MapManager.get(i).isControlledBy()==1?"**":"") + MapManager.get(i).influence[1] + (MapManager.get(i).isControlledBy()==1?"**\n":"\n");
			}
			builder.addField("Africa: ", map, false);
			map = "";
			for (int i=64; i<74; i++) {
				map += MapManager.get(i) + (MapManager.get(i).isControlledBy()==0?StartCommand.emojiID[9]:(MapManager.get(i).isControlledBy()==1?StartCommand.emojiID[10]:StartCommand.emojiID[8]))+ (MapManager.get(i).isControlledBy()==0?"**":"") + MapManager.get(i).influence[0] + (MapManager.get(i).isControlledBy()==0?"**":"") + "/" + (MapManager.get(i).isControlledBy()==1?"**":"") + MapManager.get(i).influence[1] + (MapManager.get(i).isControlledBy()==1?"**\n":"\n");
			}
			builder.addField("Central America: ", map, false);
			map = "";
			for (int i=74; i<84; i++) {
				map += MapManager.get(i) + (MapManager.get(i).isControlledBy()==0?StartCommand.emojiID[9]:(MapManager.get(i).isControlledBy()==1?StartCommand.emojiID[10]:StartCommand.emojiID[8]))+ (MapManager.get(i).isControlledBy()==0?"**":"") + MapManager.get(i).influence[0] + (MapManager.get(i).isControlledBy()==0?"**":"") + "/" + (MapManager.get(i).isControlledBy()==1?"**":"") + MapManager.get(i).influence[1] + (MapManager.get(i).isControlledBy()==1?"**\n":"\n");
			}
			builder.addField("South America: ", map, false);
			if (GameData.ccw) builder.addField("China:",":flag_cn:" + (MapManager.get(86).isControlledBy()==1?"**":"") + MapManager.get(86).influence[1] + (MapManager.get(86).isControlledBy()==1?"**\n":"\n"),false);
			String effects = "";
			for (Integer i : HandManager.Effects) {
				if (i==400||i==401) effects+=":rocket: Missile Crisis";
				if (i==41) effects+=":anchor: Nuclear Submarines";
				if (i==43) effects+=":atom: Salt Negotiations";
				if (i==690||i==691) effects += ":earth_americas: Latin American Death Squads";
				if (i==93) effects+=":flag_ni: Iran-Contra Affair";
				if (i==94) effects+=":radioactive: Chernobyl";
				if (i==50) effects+=":coffin: \"We Will Bury You\"";
				if (i==60) effects+=":airplane: U-2 Incident";
				if (i==82) effects+=":flag_ir: Iran Hostage Crisis";
				if (i==87) effects+=StartCommand.emojiID[4] + "The Reformer";
				if (i==59) effects+=":blossom: Flower Power";
				if (i==109) effects+=":dove: Yuri and Samantha";
				if (i==27) effects+=":flag_jp: Anpo";
				if (i==21) effects+=":earth_africa: NATO\n";
				if (i==17) effects+=":flag_fr: Charles de Gaulle\n";
				if (i==55) effects+=":flag_de: Willy Brandt\n";
				if (i==16&&!HandManager.effectActive(21)) effects+=":flag_pl: NATO formable\n";
				if (i==23&&!HandManager.effectActive(21)) effects+=":money_with_wings: NATO formable\n";
				if (i==68) effects+=":flag_va: John Paul II\n";
				if (i==65) effects+=":flag_il: Camp David Accords\n";
				if (i==83) effects+=":flag_gb: Iron Lady\n";
				if (i==96) effects+=StartCommand.emojiID[1] + " \"Tear Down This Wall\"\n";
				if (i==97) effects+=":flag_us: \"An Evil Empire\"\n";
				if (i==110) effects+=":flag_sa: AWACS Sold\n";
				if (i==42) effects+=":helicopter: Quagmire\n";
				if (i==44) effects+=":flag_af: Bear Trap\n";
				if (i==25) effects+=StartCommand.emojiID[9] + " Containment";
				if (i==51) effects+=StartCommand.emojiID[10] + " Brezhnev Doctrine";
				if (i==310) effects+=StartCommand.emojiID[6] + " Red Scare";
				if (i==311) effects+=StartCommand.emojiID[7] + " Purge";
				if (i==9) effects+=":flag_vn: Vietnam Revolts\n";
				if (i==35) effects+=":flag_tw: Formosan Resolution";
				if (i==73) effects+=":earth_asia: Shuttle Diplomacy";
				if (i==106) effects+=":flag_ca: NORAD";

			}
			builder.addField("Effects:", effects, false);
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

package commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import events.CardEmbedBuilder;
import game.GameData;
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
	
	private static char urlParser(int i) {
		if (i<10) return (char) ('0'+i);
		else if (i<36) return (char) ('a'+i-10);
		else return (char) ('A'+i-36);
	}
	/**
	 * Generates a URL for the game-state to display on TwiStrug. I don't know image-editing techniques on Java yet :|
	 * @return the url.
	 */
	public static String url() {
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
		return url;
	}

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
		//aldrich ames
		if (args[1].equals("ames") && HandManager.effectActive(98) && e.getChannel().equals(GameData.txtssr)) {
			GameData.txtssr.sendMessage(HandManager.getUSAHand()).complete();
			return;
		}
		//game data
		else if (args[1].equals("game")) {
			EmbedBuilder builder = new CardEmbedBuilder()
					.setTitle("Game State", url())
					.setColor(Color.WHITE)
					.addField("Score: " + (GameData.getScore()<0?StartCommand.emojiID[10]:(GameData.getScore()>0?StartCommand.emojiID[9]:"")) + CardEmbedBuilder.intToEmoji(Math.abs(GameData.getScore())), "", false)
					.addField("DEFCON " + CardEmbedBuilder.intToEmoji(GameData.getDEFCON()), "", false)
					.addField("Military Operations: ", (GameData.getMilOps(0)>=GameData.getDEFCON()?StartCommand.emojiID[9]:StartCommand.emojiID[6]) + CardEmbedBuilder.intToEmoji(GameData.getMilOps(0)) + "/" + CardEmbedBuilder.intToEmoji(GameData.getDEFCON()) + "\n"
							+ (GameData.getMilOps(1)>=GameData.getDEFCON()?StartCommand.emojiID[10]:StartCommand.emojiID[7])+CardEmbedBuilder.intToEmoji(GameData.getMilOps(1)) + "/" + CardEmbedBuilder.intToEmoji(GameData.getDEFCON()), false)
					.addField("Space Race: " + (GameData.hasSpace(0)?StartCommand.emojiID[9]:StartCommand.emojiID[6]) + CardEmbedBuilder.intToEmoji(GameData.getSpace(0))
						+(GameData.hasAbility(0, 2)?":dog2:":"")
						+(GameData.hasAbility(0, 4)?":rocket:":"")
						+(GameData.hasAbility(0, 6)?":full_moon:":"")
						+(GameData.hasAbility(0, 8)?":satellite_orbital:":"")
						+(GameData.hasAbility(0, 2, true)?":alembic:":"")
						+(GameData.hasAbility(0, 4, true)?(Operations.discount>2?":new_moon:":":full_moon:"):"")
						+(GameData.hasAbility(0, 6, true)?(Operations.coupReroll>2?":boom:":":rocket:"):"")
						+(GameData.hasAbility(0, 7, true)?":satellite_orbital:":"")+"\n"
						+(GameData.hasSpace(1)?StartCommand.emojiID[10]:StartCommand.emojiID[7]) + CardEmbedBuilder.intToEmoji(GameData.getSpace(1))+
							(GameData.hasAbility(1, 2)?":dog2:":"")+
							(GameData.hasAbility(1, 4)?":rocket:":"")+
							(GameData.hasAbility(1, 6)?":full_moon:":"")+
							(GameData.hasAbility(1, 8)?":satellite_orbital:":"")+
							(GameData.hasAbility(1, 2, true)?":alembic:":"")+
							(GameData.hasAbility(1, 4, true)?(Operations.discount>2?":new_moon:":":full_moon:"):"")+
							(GameData.hasAbility(1, 6, true)?(Operations.coupReroll>2?":boom:":":rocket:"):"")+
							(GameData.hasAbility(1, 7, true)?":satellite_orbital:":""), "", false);
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
				//top priority!
				//DEFCON affectors
				if (HandManager.effectActive(126)) effects+=":: **Tsar Bomba**";
				if (HandManager.effectActive(1270)||HandManager.effectActive(1271)) effects+=":: Vasili Arkhipov";
				if (HandManager.effectActive(1210)||HandManager.effectActive(1211)) effects+=":: **Nuclear Weapons Proliferation**";
				if (HandManager.effectActive(128)) effects+=StartCommand.emojiID[12]+" **Checkpoint C**";
				//coup modifiers
				if (HandManager.effectActive(400)||HandManager.effectActive(401)) effects+=":rocket: **Missile Crisis**\n"; //loses the game if coups happen
				if (HandManager.effectActive(41)) effects+=":anchor: Nuclear Submarines\n";
				if (HandManager.effectActive(43)) effects+=":atom: Salt Negotiations\n";
				if (HandManager.effectActive(690)||HandManager.effectActive(691)) effects += ":earth_americas: Latin American Death Squads\n";
				//other ops modifiers
				if (HandManager.effectActive(93)) effects+=":flag_ni: Iran-Contra Affair\n";
				if (HandManager.effectActive(94)) effects+=":radioactive: Chernobyl\n";
				if (HandManager.effectActive(124)) effects+=":dog2: Laika";
				//UN
				if (HandManager.effectActive(50)) effects+=":coffin: \"We Will Bury You\"\n";
				if (HandManager.effectActive(60)) effects+=":airplane: U-2 Incident\n";
				//Effect improvement
				if (HandManager.effectActive(82)) effects+=":flag_ir: Iran Hostage Crisis\n";
				if (HandManager.effectActive(87)) effects+=StartCommand.emojiID[4] + "The Reformer\n";
				//VP for US actions
				if (HandManager.effectActive(59)) effects+=":blossom: Flower Power\n";
				if (HandManager.effectActive(109)) effects+=":dove: Yuri and Samantha\n";
				//Coup protection
				if (HandManager.effectActive(27)) effects+=":flag_jp: Anpo\n";
				//	Interacts with NATO
				if (HandManager.effectActive(21)) effects+=":compass: NATO\n"; //also coup protection
				if (HandManager.effectActive(17)) effects+=":flag_fr: Charles de Gaulle\n";
				if (HandManager.effectActive(55)) effects+=":flag_de: Willy Brandt\n";
				//Activators
				if (HandManager.effectActive(16)&&!HandManager.effectActive(21)) effects+=":flag_pl: NATO formable\n";
				if (HandManager.effectActive(23)&&!HandManager.effectActive(21)) effects+=":tractor: NATO formable\n";
				if (HandManager.effectActive(68)) effects+=":flag_va: John Paul II\n";
				//provides an action round
				if (HandManager.effectActive(129)) effects+=":flag_in: Indo-Soviet Treaty";
				if (HandManager.effectActive(861)) effects+=":eight: North Sea Oil's eighth action round";
				//Deactivators
				if (HandManager.effectActive(86)) effects+=":flag_no: North Sea Oil\n";
				if (HandManager.effectActive(65)) effects+=":flag_il: Camp David Accords\n";
				if (HandManager.effectActive(83)) effects+=":flag_gb: Iron Lady\n";
				if (HandManager.effectActive(96)) effects+=StartCommand.emojiID[1] + " \"Tear Down This Wall\"\n";
				if (HandManager.effectActive(97)) effects+=":flag_us: \"An Evil Empire\"\n";
				if (HandManager.effectActive(110)) effects+=":flag_sa: AWACS Sold\n";
				//fluxing quagmire
				if (HandManager.effectActive(42)) effects+=":helicopter: Quagmire\n"; //also deactivator
				if (HandManager.effectActive(44)) effects+=":flag_af: Bear Trap\n";
				//Op Modifiers
				if (HandManager.effectActive(25)) effects+=StartCommand.emojiID[9] + " Containment\n";
				if (HandManager.effectActive(51)) effects+=StartCommand.emojiID[10] + " Brezhnev Doctrine\n";
				if (HandManager.effectActive(310)) effects+=StartCommand.emojiID[6] + " Red Scare\n";
				if (HandManager.effectActive(311)) effects+=StartCommand.emojiID[7] + " Purge\n";
				if (HandManager.effectActive(9)) effects+=":flag_vn: Vietnam Revolts\n";
				//score affecting
				if (HandManager.effectActive(35)) effects+=":flag_tw: Formosan Resolution\n";
				if (HandManager.effectActive(73)) effects+=":earth_asia: Shuttle Diplomacy\n";
				if (HandManager.effectActive(137)) effects+=":earth_africa: Red Africa\n";
				//luck changing
				if (HandManager.effectActive(1350)||HandManager.effectActive(1351)) effects+=":game_die: People's Power Revolution\n";
				//NORAD
				if (HandManager.effectActive(106)) effects+=":flag_ca: NORAD\n";
			builder.addField("Effects:", effects, false);
			sendMessage(e, new MessageBuilder().setEmbed(builder.build()).build());
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
			builder.addField("US Hand", "Size: " + HandManager.USAHand.size() + (HandManager.China==0?" + :flag_cn:":(HandManager.China==2?StartCommand.emojiID[0]:"")), false);
			builder.addField("USSR Hand", "Size: " + HandManager.SUNHand.size()+(HandManager.China==1?" + :flag_cn:":(HandManager.China==3?StartCommand.emojiID[0]:"")), false);
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
			sendMessage(e, new MessageBuilder().setEmbed(CardList.getCard(id).toEmbed(CardList.getCard(id).getAssociation()).setAuthor("Information", null, Launcher.url("emoji/InflNC.png"))
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

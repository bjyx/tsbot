package commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import game.PlayerList;
import logging.Log;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.PermissionOverride;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.Route;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;
import turnzero.TurnZero;
/**
 * The command that starts the game.
 * @author adalbert
 *
 */
public class StartCommand extends Command {
	/**
	 * A list of emoji needed for the proper function of the game.
	 *
	 */
	public static final String[] emojiID = new String[] {
			"<:TScardback:648119343019982859>",
			"<:flag_dd:648119347469877268>",
			"<:flag_yu:648119349659566101>",
			"<:flag_zr:648119354508050460>",
			"<:flag_su:648119356387098644>",
			"<:flag_bu:648119357746053150>",
			"<:InflA:648119359038029828>",
			"<:InflR:648119360430407691>",
			"<:InflN:648119362619834373>",
			"<:InflAC:648119364238966833>",
			"<:InflRC:648119366377930752>",
			"<:InflNC:648119367728496661>",
			"<:tank:648119369041182741>"};
	public static int ruleset;
	
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {

		if (GameData.hasGameEnded()) {
			sendMessage(e, ":x: Have you tried turning it off and on again?");
			return;
		}
		if (GameData.hasGameStarted()) {
			sendMessage(e, ":hourglass: There's a time and place for everything, but not now.");
			return;
		}
		if (!PlayerList.getArray().contains(e.getAuthor())) {
			sendMessage(e, ":x: Excuse me, but who are *you* playing as? China's abstracted as a card and the rest of the world has a board space each.");
			return;
		}
		if (PlayerList.getArray().contains(null)) {
			sendMessage(e, ":x: Can't have a Cold War if one of the superpowers doesn't exist.");
			return;
		}
		int settings = 1;
		try {
			if (args.length>=2 && Integer.parseInt(args[1])%256<144 && Integer.parseInt(args[1])<512) settings=Integer.parseInt(args[1]);
			else if (args.length>=2){
				sendMessage(e, ":x: Leave this field blank if you want to. Settings must be written as non-negative integers less than 144.");
				return;
			}
		}
		catch (NumberFormatException err) {
			sendMessage(e, ":x: Leave this field blank if you want to. Settings must be written as non-negative integers less than 144.");
			return;
		}
		try {
			if (args.length>=3 && Math.abs(Integer.parseInt(args[2]))<=10) SetupCommand.handicap=Integer.parseInt(args[2]);
			else if (args.length>=3) {
				sendMessage(e, ":x: Leave this field blank if you want to. A handicap is the amount of influence one player gets in addition to starting; its absolute value must be at most 10.");
				return;
			}
		}
		catch (NumberFormatException err) {
			sendMessage(e, ":x: Leave this field blank if you want to. A handicap is the amount of influence one player gets in addition to starting; its absolute value must be at most 10.");
			return;
		}
		GameData.txtchnl = e.getTextChannel();
		// create two channels, one for the US and one for the USSR, accessible only by the respective role
		
		if (e.getGuild().getTextChannelsByName("ts-usa", true).isEmpty()) {
			GameData.txtusa = (TextChannel) new ChannelAction(Route.Guilds.CREATE_CHANNEL.compile(e.getGuild().getId()),"ts-usa", e.getGuild(), ChannelType.TEXT)
					.setTopic("O thus be it ever, when freemen shall stand\n" + 
							"Between their loved homes and the war's desolation.\n" + 
							"Blest with vict'ry and peace, may the Heav'n rescued land\n" + 
							"Praise the Power that hath made and preserved us a nation!\n") //final stanza of *-spangled banner
					.addPermissionOverride(GameData.roleusa, 68672, 0)
					.addPermissionOverride(e.getGuild().getPublicRole(), 0, 2146958847)
					.complete();
		}
		else {
			GameData.txtusa = e.getGuild().getTextChannelsByName("ts-usa", true).get(0);
			for (PermissionOverride po : GameData.txtusa.getPermissionOverrides()) {
				po.delete().complete();
			}
			GameData.txtusa.putPermissionOverride(e.getGuild().getPublicRole()).setPermissions(0, 2146958847).queue();
			GameData.txtusa.putPermissionOverride(GameData.roleusa).setPermissions(68672, 0).queue();
		}
		if (e.getGuild().getTextChannelsByName("ts-ussr", true).isEmpty()) {
			GameData.txtssr = (TextChannel) new ChannelAction(Route.Guilds.CREATE_CHANNEL.compile(e.getGuild().getId()),"ts-ussr", e.getGuild(), ChannelType.TEXT)
					.setTopic("В победе бессмертных идей коммунизма\n" + 
							"Мы видим грядущее нашей страны,\n" + 
							"И Красному знамени славной Отчизны\n" + 
							"Мы будем всегда беззаветно верны!") //final stanza of soviet anthem, pre-chorus
					.addPermissionOverride(GameData.rolessr, 68672, 0)
					.addPermissionOverride(e.getGuild().getPublicRole(), 0, 2146958847).complete();
		}
		else {
			GameData.txtssr = e.getGuild().getTextChannelsByName("ts-ussr", true).get(0);
			for (PermissionOverride po : GameData.txtssr.getPermissionOverrides()) {
				po.delete().complete();
			}
			GameData.txtssr.putPermissionOverride(e.getGuild().getPublicRole()).setPermissions(0, 2146958847).queue();
			GameData.txtssr.putPermissionOverride(GameData.rolessr).setPermissions(68672, 0).queue();
		}
		GameData.startGame();
		PlayerList.detSides();
		// give the roles to the respective countries
		new GuildController(e.getGuild()).addRolesToMember(e.getGuild().getMember(PlayerList.getUSA()), GameData.roleusa).complete();
		new GuildController(e.getGuild()).addRolesToMember(e.getGuild().getMember(PlayerList.getSSR()), GameData.rolessr).complete();
		Log.writeToLog("New Game: "+settings+" "+SetupCommand.handicap);
		ruleset = settings;
		EmbedBuilder builder = new EmbedBuilder().setTitle("A New Twilight Struggle Game Has Started.").setDescription(":hourglass: It is now seven minutes to midnight and counting. Good luck.").setColor(Color.WHITE);
		if (settings>=256) {
			Launcher.change();
			settings-=256;
			builder.addField("Secret mode activated", "Well, you got a bit too inquisitive. This world behaves a bit... differently. Nahh, it's just an aesthetic change, don't mind.", false);
		}
		if (settings>=128) {
			GameData.latewar = true;
			settings-=128;
			builder.addField("Late War Scenario", "Scratch that, let's take you to 1975! What awaits you in the post-Vietnam era?", false);
		}
		else {
			GameData.latewar = false;
		}
		if (settings>=64) {
			GameData.yiyo = true;
			settings-=64;
			builder.addField("Year-In and Year-Out", "Fan-made expansion pack by u/Aogu, adding 20 cards and an extra action round to the Early War. To be implemented soonish.", false);
		}
		else {
			GameData.yiyo = false;
		}
		if (settings>=32) {
			settings-=32;
			GameData.ccw = true;
			HandManager.China=-1;
			builder.addField("Chinese Civil War Variant", "In vanilla, China has already fallen to communism. Now the Soviets have to put something in to throw the dragon's weight around.", false);
		}
		else {
			GameData.ccw = false;
			HandManager.China=1;
		}
		if (settings>=16) {
			settings-=16;
			GameData.turnzero = true;
			builder.addField("Turn Zero Variant", "What if? What if the Allies had reached Berlin first? What if the Middle Eastern conflict had been handled more diplomatically? And what if the Soviets had swept through Korea before the bombs dropped? \nWarning: game may be heavily tilted towards US.", false);
		}
		else {
			GameData.turnzero = false;
		}
		if (settings>=8) {
			settings-=8;
			GameData.altspace = true;
			builder.addField("Alternate Space Race", "Play with a more aggressive space race track, where you're racing to develop space-based weapons rather than simply trying to get an edge in the race.", false);
		}
		else {
			GameData.altspace = false;
		}
		if (settings>=4) {
			settings-=4;
			GameData.promo1 = true;
			builder.addField("Promotional Cards 1", "Play with the first set of promotional cards, including the Non-Aligned Movement, Mobutu Sese Seko, Stanislav Petrov, and the Berlin Wall.", false);
		}
		else {
			GameData.promo1 = false;
		}
		if (settings>=2) {
			settings-=2;
			GameData.promo2 = true;
			builder.addField("Promotional Cards 2", "Play with the second set of promotional cards, including First Lightning, Who Lost China, Don't Wait for the Translation, and the notorious Kremlin Flu.\nWarning: game may be heavily tilted towards US.", false);
		}
		else {
			GameData.promo2 = false;
		}
		if (settings>=1) {
			settings-=1;
			GameData.optional = true;
			builder.addField("Optional Cards Enabled", "Play with the seven optional cards that come with the Deluxe Edition, including the Cambridge Five, Special Relationship, NORAD, Che, Our Man in Tehran, Yuri and Samantha, and AWACS.", false);
		}
		else {
			GameData.optional = false;
		}
		sendMessage(e, new MessageBuilder(builder.build()).build());
		MapManager.initialize();
		if (GameData.latewar) {
			GameData.startLateWar();
			CardList.initialize();
			HandManager.lateWarDeck();
			MapManager.lateWarSetup();
			HandManager.deal();
			TimeCommand.cardPlayed = false;
			Operations.allowedUSA = Operations.influencePossible(0);
			Operations.allowedSUN = Operations.influencePossible(1);
			TimeCommand.prompt();
			if (GameData.altspace) Operations.coupReroll = 0;//US starts at space 6
			Log.writeToLog("-+-+- Turn 8 -+-+-");
			return;
		}
		if (!GameData.turnzero && !GameData.latewar) {
			Log.writeToLog("-+-+- Setup -+-+-");
			CardList.initialize();
			HandManager.addToDeck(0);
			HandManager.deal();
			SetupCommand.USSR = true;
			GameData.txtssr.sendMessage(PlayerList.getSSR().getAsMention() + ", please place six influence markers in Eastern Europe. (Use TS.setup)").complete();
		}
		if (GameData.turnzero) {
			Log.writeToLog("-+-+- Turn 0 -+-+-");
			TurnZero.startCrisis();
		}
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.start");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Commence the Cold War.";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Start (start)";
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return Arrays.asList("TS.start **[settings]** **[handicap]** - How it all begins.\n"
				+ "Settings will be a number between 0 and 143 inclusive.\n"
				+ "If the number in binary has a digit in the following position:\n"
				+ "`10000000` Late War Scenario\n"
				+ "`01000000` Year-In Year-Out Expansion and recommended rules enabled (because hey, I liked that expansion).\n"
				+ "`00100000` Chinese Civil War enabled\n"
				+ "`00010000` Turn Zero enabled\n"
				+ "`00001000` Optional Space Race enabled\n"
				+ "`00000100` Promo card pack 1 enabled\n"
				+ "`00000010` Promo card pack 2 enabled\n"
				+ "`00000001` Optional cards enabled\n"
				+ "If no number is given, it will be by default 1 (+optional cards).\n"
				+ "Be aware that some rules are incompatible with each other—in particular, the Late War cannot be used with Turn Zero, Chinese Civil War, or the YIYO pack.\n\n"
				+ "Handicap can be any integer (don't make it too big), and indicates the side (negative for USSR, positive for USA) to which a handicap of `|handicap|` influence will be given. This handicap may be placed anywhere where the player already has influence.\n"
				+ "If no number is given, it will be by default 2 (US+2).");
	}

}

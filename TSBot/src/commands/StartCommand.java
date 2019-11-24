package commands;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import game.PlayerList;
import main.Launcher;
import map.MapManager;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.entities.PermissionOverride;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.Route;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;
import net.dv8tion.jda.core.requests.restaction.RoleAction;
import turnzero.TurnZero;

public class StartCommand extends Command {
	
	public static String[] emojiID = new String[13];
	
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
			if (args.length>=2 && Integer.parseInt(args[1])%128<72 && Integer.parseInt(args[1])<256) settings=Integer.parseInt(args[1]);
			else {
				sendMessage(e, ":x: Leave this field blank if you want to. Settings must be written as non-negative integers less than 72.");
				return;
			}

		}
		catch (NumberFormatException err) {
			sendMessage(e, ":x: Leave this field blank if you want to. Settings must be written as non-negative integers less than 72.");
			return;
		}
		try {
			if (args.length>=3 && Math.abs(Integer.parseInt(args[2]))<=10) SetupCommand.handicap=Integer.parseInt(args[2]);
			else {
				sendMessage(e, ":x: Leave this field blank if you want to. A handicap is the amount of influence one player gets in addition to starting; its absolute value must be at most 10.");
				return;
			}
		}
		catch (NumberFormatException err) {
			sendMessage(e, ":x: Leave this field blank if you want to. A handicap is the amount of influence one player gets in addition to starting; its absolute value must be at most 10.");
			return;
		}
		sendMessage(e, ":hourglass: Seven minutes to midnight... and counting.");
		
		GameData.txtchnl = e.getTextChannel();
		// if the roles TSUSA and TSSSR don't exist, create them.
		if (e.getGuild().getRolesByName("TSUSA", true).isEmpty()) {
			GameData.roleusa = new RoleAction(Route.Roles.CREATE_ROLE.compile(e.getGuild().getId()), e.getGuild())
					.setName("TSUSA").setColor(Color.BLUE).setMentionable(true).complete();
		}
		else {
			GameData.roleusa = e.getGuild().getRolesByName("TSUSA", true).get(0);
		}
		if (e.getGuild().getRolesByName("TSSSR", true).isEmpty()) {
			GameData.rolessr = new RoleAction(Route.Roles.CREATE_ROLE.compile(e.getGuild().getId()), e.getGuild())
					.setName("TSSSR").setColor(Color.RED).setMentionable(true).complete();
		}
		else {
			GameData.rolessr = e.getGuild().getRolesByName("TSSSR", true).get(0);
		}
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
		// add the emoji needed for proper function.
		try {
			if (e.getGuild().getEmotesByName("TScardback", false).isEmpty()) emojiID[0] = new GuildController(e.getGuild()).createEmote("TScardback", Icon.from(new File("./src/images/emoji/TSCardback.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[0] = e.getGuild().getEmotesByName("TScardback", false).get(0).getAsMention();
			if (e.getGuild().getEmotesByName("flag_dd", false).isEmpty()) emojiID[1] = new GuildController(e.getGuild()).createEmote("flag_dd", Icon.from(new File("./src/images/emoji/flag_dd.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[1] = e.getGuild().getEmotesByName("flag_dd", false).get(0).getAsMention();
			if (e.getGuild().getEmotesByName("flag_yu", false).isEmpty()) emojiID[2] =  new GuildController(e.getGuild()).createEmote("flag_yu", Icon.from(new File("./src/images/emoji/flag_yu.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[2] = e.getGuild().getEmotesByName("flag_yu", false).get(0).getAsMention();
			if (e.getGuild().getEmotesByName("flag_zr", false).isEmpty()) emojiID[3] = new GuildController(e.getGuild()).createEmote("flag_zr", Icon.from(new File("./src/images/emoji/flag_zr.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[3] = e.getGuild().getEmotesByName("flag_zr", false).get(0).getAsMention();
			if (e.getGuild().getEmotesByName("flag_su", false).isEmpty()) emojiID[4] = new GuildController(e.getGuild()).createEmote("flag_su", Icon.from(new File("./src/images/emoji/flag_su.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[4] = e.getGuild().getEmotesByName("flag_su", false).get(0).getAsMention();
			if (e.getGuild().getEmotesByName("flag_bu", false).isEmpty()) emojiID[5] = new GuildController(e.getGuild()).createEmote("flag_bu", Icon.from(new File("./src/images/emoji/flag_bu.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[5] = e.getGuild().getEmotesByName("flag_bu", false).get(0).getAsMention();
			if (e.getGuild().getEmotesByName("InflA", false).isEmpty()) emojiID[6] = new GuildController(e.getGuild()).createEmote("InflA", Icon.from(new File("./src/images/emoji/InflA.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[6] = e.getGuild().getEmotesByName("InflA", false).get(0).getAsMention();
			if (e.getGuild().getEmotesByName("InflR", false).isEmpty()) emojiID[7] = new GuildController(e.getGuild()).createEmote("InflR", Icon.from(new File("./src/images/emoji/InflR.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[7] = e.getGuild().getEmotesByName("InflR", false).get(0).getAsMention();
			if (e.getGuild().getEmotesByName("InflN", false).isEmpty()) emojiID[8] = new GuildController(e.getGuild()).createEmote("InflN", Icon.from(new File("./src/images/emoji/InflN.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[8] = e.getGuild().getEmotesByName("InflN", false).get(0).getAsMention();
			if (e.getGuild().getEmotesByName("InflAC", false).isEmpty()) emojiID[9] = new GuildController(e.getGuild()).createEmote("InflAC", Icon.from(new File("./src/images/emoji/InflAC.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[9] = e.getGuild().getEmotesByName("InflAC", false).get(0).getAsMention();
			if (e.getGuild().getEmotesByName("InflRC", false).isEmpty()) emojiID[10] = new GuildController(e.getGuild()).createEmote("InflRC", Icon.from(new File("./src/images/emoji/InflRC.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[10] = e.getGuild().getEmotesByName("InflRC", false).get(0).getAsMention();
			if (e.getGuild().getEmotesByName("InflNC", false).isEmpty()) emojiID[11] = new GuildController(e.getGuild()).createEmote("InflNC", Icon.from(new File("./src/images/emoji/InflNC.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[11] = e.getGuild().getEmotesByName("InflNC", false).get(0).getAsMention();
			if (e.getGuild().getEmotesByName("tank", true).isEmpty()) emojiID[12] = new GuildController(e.getGuild()).createEmote("tank", Icon.from(new File("./src/images/emoji/tank.png")), e.getGuild().getPublicRole()).complete().getAsMention();
			else emojiID[12] = e.getGuild().getEmotesByName("tank", false).get(0).getAsMention();
		} catch (IOException e1) {
			System.out.print("Error creating emoji.");
		}
		if (settings>=128) {
			Launcher.change();
			settings-=128;
		}
		if (settings>=64) {
			GameData.latewar = true;
			settings-=64;
		}
		else {
			GameData.latewar = false;
		}
		if (settings>=32) {
			settings-=32;
			GameData.ccw = true;
			HandManager.China=-1;
		}
		else {
			GameData.ccw = false;
			HandManager.China=1;
		}
		if (settings>=16) {
			settings-=16;
			GameData.turnzero = true;
		}
		else {
			GameData.turnzero = false;
		}
		if (settings>=8) {
			settings-=8;
			GameData.altspace = true;
		}
		else {
			GameData.altspace = false;
		}
		if (settings>=4) {
			settings-=4;
			GameData.promo1 = true;
		}
		else {
			GameData.promo1 = false;
		}
		if (settings>=4) {
			settings-=4;
			GameData.promo2 = true;
		}
		else {
			GameData.promo2 = false;
		}
		if (settings>=1) {
			settings-=1;
			GameData.optional = true;
		}
		else {
			GameData.optional = false;
		}
		if (GameData.latewar) {
			GameData.startLateWar();
			CardList.initialize();
			HandManager.lateWarDeck();
			MapManager.lateWarMap();
			HandManager.deal();
			GameData.txtssr.sendMessage(PlayerList.getSSR().getAsMention() + ", please select your headline.").complete();
			GameData.txtusa.sendMessage(PlayerList.getUSA().getAsMention() + ", please select your headline.").complete();
			return;
		}
		else {
			MapManager.initialize();
		}
		if (!GameData.turnzero && !GameData.latewar) {
			CardList.initialize();
			HandManager.addToDeck(0);
			HandManager.deal();
			SetupCommand.USSR = true;
			GameData.txtssr.sendMessage(PlayerList.getSSR().getAsMention() + ", please place six influence markers in Eastern Europe. (Use TS.setup)").complete();
		}
		if (GameData.turnzero) {
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
				+ "Settings will be a number between 0 and 71 inclusive.\n"
				+ "If the number in binary has a digit in the following position:\n"
				+ "`1000000` Late War Scenario\n"
				+ "`0100000` Chinese Civil War enabled\n"
				+ "`0010000` Turn Zero enabled\n"
				+ "`0001000` Optional Space Race enabled\n"
				+ "`0000100` Promo card pack 1 enabled\n"
				+ "`0000010` Promo card pack 2 enabled\n"
				+ "`0000001` Optional cards enabled\n"
				+ "If no number is given, it will be by default 1.\n\n"
				+ "Handicap can be any integer (don't make it too big), and indicates the side (negative for USSR, positive for USA) to which a handicap of `|handicap|` influence will be given. This handicap may be placed anywhere where the player already has influence.\n"
				+ "If no number is given, it will be by default 2.");
	}

}

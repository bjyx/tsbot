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
import net.dv8tion.jda.core.requests.restaction.RoleAction;
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
			"<:InflC:696610045621829713>",
			"<:InflD:696610045663772723>",
			"<:InflCC:696610045575692308>",
			"<:InflDC:696610045730881588>"
	};
	
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
		}/*
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
		}*/
		GameData.txtchnl = e.getTextChannel();
		// if the roles TSUSA and TSSSR don't exist, create them.
		if (e.getGuild().getRolesByName("TSUSA", true).isEmpty()) {
			GameData.roledem = new RoleAction(Route.Roles.CREATE_ROLE.compile(e.getGuild().getId()), e.getGuild())
					.setName("TSUSA").setColor(Color.BLUE).setMentionable(true).complete();
		}
		else {
			GameData.roledem = e.getGuild().getRolesByName("TSUSA", true).get(0);
		}
		if (e.getGuild().getRolesByName("TSSSR", true).isEmpty()) {
			GameData.rolecom = new RoleAction(Route.Roles.CREATE_ROLE.compile(e.getGuild().getId()), e.getGuild())
					.setName("TSSSR").setColor(Color.RED).setMentionable(true).complete();
		}
		else {
			GameData.rolecom = e.getGuild().getRolesByName("TSSSR", true).get(0);
		}
		// create two channels, one for the US and one for the USSR, accessible only by the respective role
		
		if (e.getGuild().getTextChannelsByName("89-dem", true).isEmpty()) {
			GameData.txtdem = (TextChannel) new ChannelAction(Route.Guilds.CREATE_CHANNEL.compile(e.getGuild().getId()),"ts-usa", e.getGuild(), ChannelType.TEXT)
					.setTopic("O thus be it ever, when freemen shall stand\n" + 
							"Between their loved homes and the war's desolation.\n" + 
							"Blest with vict'ry and peace, may the Heav'n rescued land\n" + 
							"Praise the Power that hath made and preserved us a nation!\n") //final stanza of *-spangled banner
					.addPermissionOverride(GameData.roledem, 68672, 0)
					.addPermissionOverride(e.getGuild().getPublicRole(), 0, 2146958847)
					.complete();
		}
		else {
			GameData.txtdem = e.getGuild().getTextChannelsByName("ts-usa", true).get(0);
			for (PermissionOverride po : GameData.txtdem.getPermissionOverrides()) {
				po.delete().complete();
			}
			GameData.txtdem.putPermissionOverride(e.getGuild().getPublicRole()).setPermissions(0, 2146958847).queue();
			GameData.txtdem.putPermissionOverride(GameData.roledem).setPermissions(68672, 0).queue();
		}
		if (e.getGuild().getTextChannelsByName("89-com", true).isEmpty()) {
			GameData.txtcom = (TextChannel) new ChannelAction(Route.Guilds.CREATE_CHANNEL.compile(e.getGuild().getId()),"ts-ussr", e.getGuild(), ChannelType.TEXT)
					.setTopic("В победе бессмертных идей коммунизма\n" + 
							"Мы видим грядущее нашей страны,\n" + 
							"И Красному знамени славной Отчизны\n" + 
							"Мы будем всегда беззаветно верны!") //final stanza of soviet anthem, pre-chorus
					.addPermissionOverride(GameData.rolecom, 68672, 0)
					.addPermissionOverride(e.getGuild().getPublicRole(), 0, 2146958847).complete();
		}
		else {
			GameData.txtcom = e.getGuild().getTextChannelsByName("ts-ussr", true).get(0);
			for (PermissionOverride po : GameData.txtcom.getPermissionOverrides()) {
				po.delete().complete();
			}
			GameData.txtcom.putPermissionOverride(e.getGuild().getPublicRole()).setPermissions(0, 2146958847).queue();
			GameData.txtcom.putPermissionOverride(GameData.rolecom).setPermissions(68672, 0).queue();
		}
		GameData.startGame();
		PlayerList.detSides();
		// give the roles to the respective countries
		new GuildController(e.getGuild()).addRolesToMember(e.getGuild().getMember(PlayerList.getDem()), GameData.roledem).complete();
		new GuildController(e.getGuild()).addRolesToMember(e.getGuild().getMember(PlayerList.getCom()), GameData.rolecom).complete();
		Log.writeToLog("New Game: 1989");
		EmbedBuilder builder = new EmbedBuilder().setTitle("A New Game of 1989 Has Started.").setDescription(":hourglass: January 1989—the start of a pivotal year for Eastern Europe. Good luck.").setColor(Color.WHITE);
		sendMessage(e, new MessageBuilder(builder.build()).build());
		MapManager.initialize();
		
		Log.writeToLog("-+-+- Setup -+-+-");
		CardList.initialize();
		HandManager.addToDeck(0);
		HandManager.deal();
		SetupCommand.setup = 0;
		GameData.txtcom.sendMessage(PlayerList.getCom().getAsMention() + ", please place six influence markers in Eastern Europe. (Use TS.setup)").complete();
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
				+ "`01000000` Year-In Year-Out Expansion and recommended rules enabled\n"
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

package commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import game.PlayerList;
import logging.Log;
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
		// create two channels, one for the US and one for the USSR, accessible only by the respective role
		
		if (e.getGuild().getTextChannelsByName("df-dem", true).isEmpty()) {
			GameData.txtdem = (TextChannel) new ChannelAction(Route.Guilds.CREATE_CHANNEL.compile(e.getGuild().getId()),"df-dem", e.getGuild(), ChannelType.TEXT)
					.setTopic("O thus be it ever, when freemen shall stand\n" + 
							"Between their loved homes and the war's desolation.\n" + 
							"Blest with vict'ry and peace, may the Heav'n rescued land\n" + 
							"Praise the Power that hath made and preserved us a nation!\n") //final stanza of *-spangled banner
					.addPermissionOverride(GameData.roledem, 68672, 0)
					.addPermissionOverride(e.getGuild().getPublicRole(), 0, 2146958847)
					.complete();
		}
		else {
			GameData.txtdem = e.getGuild().getTextChannelsByName("df-dem", true).get(0);
			for (PermissionOverride po : GameData.txtdem.getPermissionOverrides()) {
				po.delete().complete();
			}
			GameData.txtdem.putPermissionOverride(e.getGuild().getPublicRole()).setPermissions(0, 2146958847).queue();
			GameData.txtdem.putPermissionOverride(GameData.roledem).setPermissions(68672, 0).queue();
		}
		if (e.getGuild().getTextChannelsByName("df-com", true).isEmpty()) {
			GameData.txtcom = (TextChannel) new ChannelAction(Route.Guilds.CREATE_CHANNEL.compile(e.getGuild().getId()),"df-com", e.getGuild(), ChannelType.TEXT)
					.setTopic("В победе бессмертных идей коммунизма\n" + 
							"Мы видим грядущее нашей страны,\n" + 
							"И Красному знамени славной Отчизны\n" + 
							"Мы будем всегда беззаветно верны!") //final stanza of soviet anthem, pre-chorus
					.addPermissionOverride(GameData.rolecom, 68672, 0)
					.addPermissionOverride(e.getGuild().getPublicRole(), 0, 2146958847).complete();
		}
		else {
			GameData.txtcom = e.getGuild().getTextChannelsByName("df-com", true).get(0);
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
		GameData.txtcom.sendMessage(PlayerList.getCom().getAsMention() + ", please place your first two Support Points. (Use TS.setup)").complete();
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("DF.start");
	}

	@Override
	public String getDescription() {
		return "Start 1989.";
	}

	@Override
	public String getName() {
		return "Start (start)";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("DF.start - How it all begins.\n");
	}

}

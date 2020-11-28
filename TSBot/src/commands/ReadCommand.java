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
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import readwrite.ReadWrite;
/**
 * The command that loads a board state into the bot. 
 * @author adalbert
 *
 */
public class ReadCommand extends Command {

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
		if (args.length<2) {
			sendMessage(e, ":x: You can't expect me to create a game out of zero bits.");
			return;
		}
		if (args[1].length()!=329) {
			sendMessage(e, ":x: Are you sure you have a valid game?");
			return;
		}
		int settings = 16*ReadWrite.undoParser(args[1].charAt(0))+ReadWrite.undoParser(args[1].charAt(1));
		
		GameData.txtchnl = e.getTextChannel();
		// if the roles TSUSA and TSSSR don't exist, create them.
		if (GameData.roleusa==null) {
			if (e.getGuild().getRolesByName("TSUSA", true).isEmpty()) {
				GameData.roleusa = e.getGuild().createRole()
						.setName("TSUSA").setColor(Color.BLUE).setMentionable(true).complete();
			}
			else {
				GameData.roleusa = e.getGuild().getRolesByName("TSUSA", true).get(0);
			}
			if (e.getGuild().getRolesByName("TSSSR", true).isEmpty()) {
				GameData.rolessr = e.getGuild().createRole()
						.setName("TSSSR").setColor(Color.RED).setMentionable(true).complete();
			}
			else {
				GameData.rolessr = e.getGuild().getRolesByName("TSSSR", true).get(0);
			}
		}
		// create two channels, one for the US and one for the USSR, accessible only by the respective role
		
		if (e.getGuild().getTextChannelsByName("ts-usa", true).isEmpty()) {
			GameData.txtusa = (TextChannel) e.getGuild().createTextChannel("ts-usa")
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
			GameData.txtssr = e.getGuild().createTextChannel("ts-ussr")
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
		// give the roles to the respective countries
		e.getGuild().addRoleToMember(e.getGuild().getMember(PlayerList.getUSA()), GameData.roleusa).complete();
		e.getGuild().addRoleToMember(e.getGuild().getMember(PlayerList.getSSR()), GameData.rolessr).complete();
		
		Log.writeToLog("Load: "+settings+" "+SetupCommand.handicap);
		StartCommand.ruleset = settings;
		EmbedBuilder builder = new EmbedBuilder().setTitle("Loading Twilight Struggle Game.").setDescription(":hourglass: It is now seven minutes to midnight and counting. Good luck.").setColor(Color.WHITE);
		if (settings>=256) {
			Launcher.change();
			settings-=256;
			builder.addField("Secret mode activated", "Oh no, you found the secret mode! \'˚>̊̇\nHope you like furry art. It's safe for work, I promise.", false);
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
			builder.addField("Year-In and Year-Out", "Fan-made expansion pack, adding 20 cards and an extra action round to the Early War.", false);
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
		GameData.startCustom(args[1].substring(2, 10));
		CardList.initialize();
		MapManager.customSetup(args[1].substring(10, 179));
		HandManager.customCards(args[1].substring(179));
		Operations.allowedUSA = Operations.influencePossible(0);
		Operations.allowedSUN = Operations.influencePossible(1);
		TimeCommand.prompt();
		Log.writeToLog("-+-+- LOAD -+-+-");
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("TS.read","TS.load");
	}

	@Override
	public String getDescription() {
		return "A replacement of the start command that loads an already existing game.";
	}

	@Override
	public String getName() {
		return "Load Game";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("TS.read/load **string** - Load a game in progress.\n"
				+ "The string for a game in progress can be obtained by TS.save. **The game must not have started, and there must be two players ready to play.**\n"
				+ "After loading, make sure to use TS.+ to advance the state of the game, as all save states take you to the end of the turn in question. \n"
				+ "The first player to join will take the role of the US, and the second the USSR; make sure you join the game in that order.");
	}

}

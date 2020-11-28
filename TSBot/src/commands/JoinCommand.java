package commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import game.GameData;
import game.PlayerList;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/**
 * The command allowing people to join the game.
 * @author adalbert
 *
 */
public class JoinCommand extends Command {
	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
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
		for (Member m : e.getGuild().getMembers()) {
			if (m.getRoles().contains(GameData.roleusa)) e.getGuild().removeRoleFromMember(m, GameData.roleusa).complete();
			if (m.getRoles().contains(GameData.rolessr)) e.getGuild().removeRoleFromMember(m, GameData.rolessr).complete();
		}
		if (GameData.hasGameEnded()) {
			sendMessage(e, ":x: Have you tried turning it off and on again?");
			return;
		}
		if (GameData.hasGameStarted()) {
			sendMessage(e, ":x: Cannot join a game that has already started.");
			return;
		}
		if (PlayerList.getArray().contains(e.getAuthor())) {
			sendMessage(e, ":x: You're already on the list.");
			return;
		}
		if (PlayerList.getArray().contains(null)) {
			PlayerList.addPlayer(e.getAuthor());
			sendMessage(e, ":o: Done. Make sure that at least one of the players owns a copy of *Twilight Struggle* before starting, per the licensing agreement.");
			return;
		}
		sendMessage(e, ":x: There is only enough room for two superpowers on this world stage.");
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("TS.join");
	}

	@Override
	public String getDescription() {
		return "Join a game that hasn't started. ";
	}

	@Override
	public String getName() {
		return "Join (join)";
	}

	@Override
	public List<String> getUsageInstructions() {
		return Arrays.asList("TS.join - Join a game.");
	}

}

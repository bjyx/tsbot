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
		if (GameData.roledem==null) {
			if (e.getGuild().getRolesByName("DFDEM", true).isEmpty()) {
				GameData.roledem = e.getGuild().createRole()
						.setName("DFDEM").setColor(Color.BLUE).setMentionable(true).complete();
			}
			else {
				GameData.roledem = e.getGuild().getRolesByName("DFDEM", true).get(0);
			}
			if (e.getGuild().getRolesByName("DFCOM", true).isEmpty()) {
				GameData.rolecom = e.getGuild().createRole()
						.setName("DFCOM").setColor(Color.RED).setMentionable(true).complete();
			}
			else {
				GameData.rolecom = e.getGuild().getRolesByName("DFCOM", true).get(0);
			}
			for (Member m : e.getGuild().getMembers()) {
				if (m.getRoles().contains(GameData.roledem)) e.getGuild().removeRoleFromMember(m, GameData.roledem).complete();
				if (m.getRoles().contains(GameData.rolecom)) e.getGuild().removeRoleFromMember(m, GameData.rolecom).complete();
			}
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
			sendMessage(e, ":o: Done. Make sure that at least one of the players owns a copy of *1989: Dawn of Freedom* before starting, per the licensing agreement.");
			return;
		}
		sendMessage(e, ":x: There is only enough room for two superpowers on this world stage.");
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("DF.join");
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
		return Arrays.asList("DF.join - Join a game.");
	}

}

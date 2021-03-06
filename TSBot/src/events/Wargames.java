package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
/**
 * The Wargames Card.
 * @author adalbert
 *
 */
public class Wargames extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Brinkmanship")
			.setDescription("");
		if (Integer.parseInt(args[1])==1) {
			builder.setFooter("`A STRANGE GAME. THE ONLY WINNING MOVE IS NOT TO PLAY. HOW ABOUT A NICE GAME OF CHESS?`\n"
					+ "- Joshua, *WarGames*", Launcher.url("people/wargames2"))
			.setColor(Color.black);
			builder.changeVP((sp*2-1)*6);
			builder.addField("Futility", "The game has ended.", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			GameData.checkScore(true, true);
		}
		else {
			builder.setFooter("\"This is a policy adapted from a sport which, I am told, is practiced by some youthful degenerates. This sport is called 'Chicken!'.\"\n"
					+ "- Bertrand Russell", Launcher.url("people/russell.png"))
			.setColor(Color.yellow);
			builder.addField("What is the primary goal?", "`YOU SHOULD KNOW THAT, PROFESSOR. YOU PROGRAMMED ME.`", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		if (GameData.latewar&&sp==1) return false; //cannot be played by soviets during late war scenario
		return GameData.getDEFCON()==2;
	}

	@Override
	public String getId() {
		return "100";
	}

	@Override
	public String getName() {
		return "WarGames";
	}

	@Override
	public int getOps() {
		return 4;
	}

	@Override
	public int getEra() {
		return 2;
	}

	@Override
	public int getAssociation() {
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		if (args.length<2) return false;
		if (args[1].equals("1") || args[1].equals("0")) return true;
		return false;
	}

	@Override
	public String getDescription() {
		return "The player may choose to end the game after giving their opponent 6 VPs. *Only playable at DEFCON 2.*\n";
	}

	@Override
	public String getArguments() {
		return "1, signifying that you want to end the game, or 0, signifying that you do not. *While it is an option in physical copies, giving away 6 VPs without ending the game is strictly suboptimal on the phasing player's part, and is thus not implemented here.*";
	}

}

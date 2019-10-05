package events;

import java.awt.Color;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import game.GameData;
import main.Launcher;

public class LoneGunman extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("John F. Kennedy Assassinated!")
			.setDescription("")
			.setFooter("\"The public must be satisfied that Oswald was the assassin; that he did not have confederates who are still at large.\" \n"
					+ "- Nicholas Katzenbach, 1963",Launcher.url("people/katzenbach.png"))
			.setColor(Color.red);
		GameData.txtssr.sendMessage(HandManager.getUSAHand()).complete();
		GameData.dec = new Decision(1, 62);
		GameData.ops = new Operations(1, CardList.getCard(62).getOpsMod(1), true, true, true, false, false);
		builder.addField("Lee Harvey Oswald", "The USSR may now look at the USA's hand.", false);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		GameData.txtssr.sendMessage(GameData.rolessr.getAsMention() + ", you may now conduct operations.").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "062";
	}

	@Override
	public String getName() {
		return "Lone Gunman";
	}

	@Override
	public int getOps() {
		return 1;
	}

	@Override
	public int getEra() {
		return 1;
	}

	@Override
	public int getAssociation() {
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Allows the USSR to see the American Hand. The USSR may then **conduct Operations using the value of this card.**";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: Operations.";
	}

}

package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import logging.Log;
import main.Launcher;
/**
 * The Vietnam Revolts Card.
 * @author adalbert
 *
 */
public class VietnamRevolts extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Dien Bien Phu falls")
			.setDescription("Negotiations now underway in Geneva to decide fate of French Indochina")
			.setFooter("\"You can kill ten of my men for every one I kill of yours. But even at those odds, you will lose and I will win.\"\n"
					+ "- Hô Chi Minh, 195X", Launcher.url("people/hochiminh.png"))
			.setColor(Color.red);
		builder.changeInfluence(45, 1, 2); //Vietnam 0/+2
		builder.addField("Viet Minh", "All Soviet Operations exclusively used in Southeast Asia will now have one extra point to use.", false);
		HandManager.addEffect(9);
		Log.writeToLog("Vietnam Revolts active.");
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "009";
	}

	@Override
	public String getName() {
		return "Vietnam Revolts";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 0;
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
		return "Adds 2 USSR Influence to Vietnam. *For the rest of the turn, the USSR gets +1 Operations Point to any card that uses all of its operations in Southeast Asia.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

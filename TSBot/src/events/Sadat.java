package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Sadat Expels Soviets Card.
 * @author adalbert
 *
 */
public class Sadat extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Sadat Expels Soviet Army Advisors")
			.setDescription("Egyptian president frustrated with lack of Soviet aid in war against Israel")
			.setColor(Color.blue)
			.setFooter("\"Russians can give you arms, but only the United States can give you a solution.\"\n"
					+ "- *Learning from Sadat: The Dividends of American Resolve*", Launcher.url("people/sadat.png"));
		builder.changeInfluence(21, 1, -MapManager.get(65).influence[1]); //remove all Soviet influence in Egypt
		builder.changeInfluence(21, 0, 1); //add 1 US influence to Egypt
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "072";
	}

	@Override
	public String getName() {
		return "Sadat Expels Soviets";
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
		return "Remove all USSR Influence in Egypt. Add 1 US Influence to Egypt.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

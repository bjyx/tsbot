package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
/**
 * The Allende Card.
 * @author adalbert
 *
 */
public class Allende extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Allende Takes Power in Chile")
		.setDescription("Landmark president lays out the Chilean path to socialism")
		.setColor(Color.red)
		.setFooter("\"Keep in mind that, much sooner than later, "
				+ "the great avenues will again be opened through which will pass "
				+ "free men to construct a better society. Long live Chile! "
				+ "Long live the people! Long live the workers!\"\n"
				+ "-Salvador Allende, 1973", Launcher.url("people/allende.png"));
		builder.changeInfluence(77, 1, 2);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "054";
	}

	@Override
	public String getName() {
		return "Allende";
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
		return "Add 2 USSR Influence to Chile.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

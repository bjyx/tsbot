package events;

import java.awt.Color;

import game.GameData;
import main.Launcher;
/**
 * The Portuguese Empire Crumbles Card.
 * @author adalbert
 *
 */
public class PortEmpire extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Angola and Mozambique Gain Independence")
		.setDescription("New Portuguese government hands power to communist guerillas")
		.setColor(Color.red)
		.setFooter("\"To Angola, quickly and with strength!\""
				+ "\n- António de Oliveira Salazar (from the old right-wing government), 1961", Launcher.url("people/salazar.png"));
		builder.changeInfluence(47, 1, 2); //Alvor Agreement
		builder.changeInfluence(55, 1, 2); //Lusaka Accord
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "052";
	}

	@Override
	public String getName() {
		return "Portuguese Empire Collapses";
	}

	@Override
	public int getOps() {
		return 2;
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
		return "The USSR gains 2 Influence in each of Angola and SE African States.";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

package events;

import java.awt.Color;

import cards.HandManager;
import game.GameData;
import main.Launcher;

public class ShuttleDiplomacy extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Shuttle Diplomacy")
			.setDescription("Kissinger's peace-promoting methods")
			.setColor(Color.blue)
			.setFooter("\"Empires have no interest in operating within an international system; "
					+ "they aspire to be the international system... "
					+ "That is how the United States has conducted its foreign policy in the Americas, "
					+ "and China through most of its history in Asia.\"\n"
					+ "- Henry Kissinger, 1994", Launcher.url("countries/us.png"));
		builder.addField("Intermediary","Upon the next scoring of Asia or the Middle East, subtract one from the USSR battleground count.",false);
		HandManager.addEffect(73);
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "073";
	}

	@Override
	public String getName() {
		return "Shuttle Diplomacy";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 1;
	}

	@Override
	public int getAssociation() {
		return 0;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Upon the next scoring of Asia or the Middle East, subtract one from the Soviet battleground count.";
	}

	@Override
	public String getArguments() {
		return "None. If Soviets control Japan, 1. shame on you, 2. the battleground not counted will be Japan.";
	}

}

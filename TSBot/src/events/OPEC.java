package events;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cards.HandManager;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class OPEC extends Card {
	
	private static final List<Integer> targets = Arrays.asList(21, 22, 23, 24, 28, 29, 83);

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("OPEC Commences Oil Embargo")
		.setDescription("Oil prices rise in wake of Western support for Israel")
		.setFooter("\"The OPEC action is really the first illustration "
				+ "and at the same time the most concrete and most spectacular illustration "
				+ "of the importance of raw material prices for our countries, "
				+ "the vital need for the producing countries to operate the levers of price control, "
				+ "and lastly, the great possibilities of a union of raw material producing countries. "
				+ "This action should be viewed by the developing countries as an example and a spurce of hope.\"\n"
				+ "- Houari Boumediene, 1974", Launcher.url("people/boumediene.png"))
		.setColor(Color.red);
		int x = 0;
		String str = "";
		for (Integer i : targets) {
			if (MapManager.get(i).isControlledBy()==1) {
				x++;
				str += MapManager.get(i);
			}
		}
		builder.addField("Cooperating countries: ", str, false);
		builder.changeVP(-x);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return !HandManager.effectActive(86);
	}

	@Override
	public String getId() {
		return "061";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "OPEC";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "The USSR gains 1 VP for controlling each of the following countries; ";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

package events;

import java.util.List;
import java.awt.Color;
import java.util.Arrays;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import map.MapManager;

public class SouthAfricanUnrest extends Card {
	
	public static int option;
	private static final List<Integer> VALID_OPTIONS = Arrays.asList(47, 48, 58); //ao, bw, za

	@Override
	public void onEvent(int sp, String[] args) {
		// TODO Auto-generated method stub
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Anti-Apartheid Demonstrations Violently Quelled")
			.setDescription("Hundreds dead after police shoot into crowd in Sharpeville")
			.setColor(Color.red)
			.setFooter("", "");
		builder.changeInfluence(option, 1, 2);
		if (option!=58) {
			builder.changeInfluence(58, 1, 1);
		}
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable(int sp) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "053";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "South African Unrest";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 2;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFormatted(String[] args) {
		if (args.length<2) return false;
		option = MapManager.find(args[1]);
		if (VALID_OPTIONS.contains(option)) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Either place 2 USSR influence in South Africa or 1 USSR Influence in South Africa and 2 in one of its adjacencies.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "A valid alias for one of three countries: South Africa, Angola, or Botswana, representing the one to put two influence into.";
	}

}

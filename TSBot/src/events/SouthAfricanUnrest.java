package events;

import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The South African Unrest Card.
 * @author adalbert
 *
 */
public class SouthAfricanUnrest extends Card {
	
	public static int option;
	private static final List<Integer> VALID_OPTIONS = Arrays.asList(47, 48, 58); //ao, bw, za

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Anti-Apartheid Demonstrations Flare Up")
			.setDescription("Black majority campaigns against South African racist policy")
			.setColor(Color.red)
			.setFooter("\"Mandela has overstepped the mark. He has broken the law. The judiciary of this country has put him where he belongs according to the rules of democracy.\"\n"
					+ "- P. W. Botha, 1980", Launcher.url("people/botha.png"));
		if (option==-1) {
			builder.bulkChangeInfluence(new ArrayList<Integer>(VALID_OPTIONS), 1, new ArrayList<Integer>(Arrays.asList(1,1,1)));
		}
		else {
			builder.changeInfluence(option, 1, 2);
			if (option!=58) {
				builder.changeInfluence(58, 1, 1);
			}
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "053";
	}

	@Override
	public String getName() {
		return "South African Unrest";
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		if (args.length<2) return false;
		option = MapManager.find(args[1]);
		if (VALID_OPTIONS.contains(option)) {
			return true;
		}
		if (args[1].equalsIgnoreCase("split")) return true;
		return false;
	}

	@Override
	public String getDescription() {
		return "Either place 2 USSR influence in South Africa or 1 USSR Influence in South Africa and 2 total in the countries adjacent to South Africa.";
	}

	@Override
	public String getArguments() {
		return "A valid alias for one of three countries: South Africa, Angola, or Botswana, representing the one to put two influence into. Alternatively, the word \"split\" (exact spelling), for one in each of the three.";
	}

}

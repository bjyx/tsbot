package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.CardList;
import cards.Operations;
import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Ortega Elected in Nicaragua Card.
 * @author adalbert
 *
 */
public class Ortega extends Card {
	
	public static ArrayList<Integer> doable;
	public static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Sandinistas Overthrow Nicaraguan Dictator")
			.setDescription("Daniel Ortega elected President")
			.setFooter("\"The Nicaraguan people have won, with their blood, the right to be here today, in this way breaking with a historic past of servility towards imperialist policy. For the first time in their entire history the Nicaraguan people can officially express their sovereign will, joining this movement of the nonaligned barely forty-one days after their triumph.\"\n"
					+ "- Daniel Ortega, 1979", Launcher.url("people/ortega.png"))
			.setColor(Color.red);
		builder.changeInfluence(72, 0, -MapManager.get(72).influence[0]);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!","No leftist guerrillas to supply here.",false);
		}
		else {
			builder.addField("Arms shipments", "A coup will now be conducted in " + MapManager.get(target).name, false);
		}
		GameData.ops = new Operations(1, CardList.getCard(91).getOpsMod(1), false, false, true, false, true);
		GameData.ops.coup(target);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "091";
	}

	@Override
	public String getName() {
		return "Ortega Elected in Nicaragua";
	}

	@Override
	public int getOps() {
		return 2;
	}

	@Override
	public int getEra() {
		return 2;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		doable = new ArrayList<Integer>();
		for (Integer i : MapManager.get(72).adj) {
			if (MapManager.get(i).influence[0]>0) doable.add(i);
		}
		if (doable.isEmpty()) return true;
		if (args.length<2) {
			doable = new ArrayList<Integer>();
			return true;
		}
		try {
			target = Integer.parseInt(args[1]);
			if (doable.contains(target)) return true;
		} catch (NumberFormatException err) {
			return false;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "Remove all US Influence from Nicaragua. **The USSR may conduct a free coup using this card in a country adjacent to Nicaragua.**";
	}

	@Override
	public String getArguments() {
		return "One of Honduras, Costa Rica, or Cuba, indicating the country to coup—if none of those countries have any US Influence, or if the USSR does not wish to coup a country, this can be ignored.";
	}

}

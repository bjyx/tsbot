package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Truman Doctrine Card.
 * @author adalbert
 *
 */
public class TrumanDoctrine extends Card {
	
	private static ArrayList<Integer> doable;
	
	private static int target = 17; //Turkey is default for flavor purposes

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Truman addresses Congress")
			.setDescription("Speech urges swift aid to " + MapManager.get(target).name)
			.setFooter("\"I believe that it must be the policy of the United States to support free peoples who are resisting attempted subjugation by armed minorities or by outside pressures.\"\n"
					+ "- Harry S. Truman, 1947",Launcher.url("people/truman.png"))
			.setColor(Color.BLUE);
		if (doable.isEmpty()) {
			builder.addField("No countries to target!", "No uncontrolled countries with USSR influence in Europe.", false);
		}
		else {
			builder.changeInfluence(target, 1, -MapManager.get(target).influence[1]);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "019";
	}

	@Override
	public String getName() {
		return "Truman Doctrine";
	}

	@Override
	public int getOps() {
		return 1;
	}

	@Override
	public int getEra() {
		return 0;
	}

	@Override
	public int getAssociation() {
		return 0;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		doable = new ArrayList<Integer>();
		for (int i=0; i<21; i++) {
			if (MapManager.get(i).isControlledBy()==-1 && MapManager.get(i).influence[1]>0) {
				doable.add(i);
			}
		}
		if (doable.size()==0) {
			return true;
		}
		if (doable.size()==1) {
			target = doable.get(0);
			return true;
		}
		if (args.length<2) return false;
		if (!doable.contains(MapManager.find(args[1]))) return false;
		target = MapManager.find(args[1]);
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Remove all USSR influence from one uncontrolled country in Europe.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "The one country to remove influence from. If at most one option exists for this influence removal, no argument is required; it will be done immediately.";
	}

}

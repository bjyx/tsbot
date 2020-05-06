package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Pershing II Deployed Card.
 * @author adalbert
 *
 */
public class PershingII extends Card {

	public static ArrayList<Integer> doable = new ArrayList<Integer>();
	public static ArrayList<Integer> order = new ArrayList<Integer>();
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Pershing II Deployment faces backlash")
			.setDescription("")
			.setFooter("\"It is sobering thought that these missiles were actually deployed in continental Europe in those days and that on at least one occasion, namely the 1973 Arab-Israel[i] war, there was an alert serious enough to leave the commanding officers trembling.\"\n"
					+ "- Robert B. Laughlin", Launcher.url("people/laughlin.png"))
			.setColor(Color.red);
		for (int c : order) {
			builder.changeInfluence(c, 0, -1);
		}
		builder.changeVP(-1);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "099";
	}

	@Override
	public String getName() {
		return "Pershing II Deployed";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
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
		order = new ArrayList<Integer>();
		for (int i=0; i<21; i++) {
			if ((MapManager.get(i).region==0||MapManager.get(i).region==1)&&MapManager.get(i).influence[0]>0) {
				doable.add(i);
			}
		}
		if (doable.size()<=3) {
			order = doable;
			return true;
		}
		if (args.length!=4) return false;
		for (int i=1; i<=3; i++) {
			int c = MapManager.find(args[i]);
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
		}
		if (!doable.containsAll(order)) return false;
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "The USSR gains 1 VP. The US loses 1 Influence point in each of three Western European countries.";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Three countries, all Western European, in which the US loses influence. If (for some reason) at most three countries have US Influence in them, this is not needed.";
	}

}

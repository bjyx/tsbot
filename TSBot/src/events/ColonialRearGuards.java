package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class ColonialRearGuards extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> doable;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Colonial Rear Guards")
			.setDescription("")
			.setFooter("\"From now on, Frenchman [sic] must know that they don't have the right "
					+ "to condemn in the same terms as ten years ago the destruction of Oradour and the torture by the Gestapo.\"\n"
					+ "- Hubert Beuve-Mery, 1957", Launcher.url("countries/dz.png"))
			.setColor(Color.blue);
		for (int c : order) {
			builder.changeInfluence(c, 0, 1);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "063";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Colonial Rear Guards";
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
		return 0;
	}

	@Override
	public boolean isRemoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFormatted(String[] args) {
		doable = new ArrayList<Integer>();
		order = new ArrayList<Integer>();
		for (int i=33; i<64; i++) {
			if (MapManager.get(i).region==5||MapManager.get(i).region==6) {
				doable.add(i);
			}
		}
		if (args.length!=5) return false;
		for (int i=1; i<=4; i++) {
			int c = MapManager.find(args[i]);
			order.add(c);
			if (order.indexOf(c)!=i) return false; // no duplicates plox
		}
		if (!doable.containsAll(order)) return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "Add 1 US influence to each of four countries in Africa or Southeast Asia.";
	}

	@Override
	public String getArguments() {
		return "The four countries. All four must be valid aliases of countries present in Africa or Southeast Asia.";
	}

}

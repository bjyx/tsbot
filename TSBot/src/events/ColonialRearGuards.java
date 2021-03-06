package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Colonial Rear Guards Card.
 * @author adalbert
 *
 */
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
					+ "- Hubert Beuve-Mery, 1957", Launcher.url("people/lemonde.png"))
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
		return "Colonial Rear Guards";
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
		return 0;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
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
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
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

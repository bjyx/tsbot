package turnzero;

import java.awt.Color;
import java.util.ArrayList;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Nationalist China Card from Turn Zero.
 * @author adalbert
 *
 */
public class NationalistChina extends Card {

	private static ArrayList<Integer> order;
	
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Chiang's Allies")
			.setDescription("Nationalist government funding anti-communist movements in " + MapManager.get(order.get(0)).name)
			.setFooter("\"[O]ne can imagine Chiang Kai-shek's ghost wandering round China today nodding in approval, while Mao's ghost follows behind him, moaning at the destruction of his vision.\"\n"
					+ "- Shantashil Rajyeswar Mitter", Launcher.url("people/macmillan.png"))
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
		return "035";
	}

	@Override
	public String getName() {
		return "Nationalist China";
	}

	@Override
	public int getOps() {
		return 2;
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		order = new ArrayList<Integer>();
		if (args.length!=4) return false; //exactly three countries
		for (int i=1; i<=3; i++) {
			int c = MapManager.find(args[i]);
			if (MapManager.get(c).region==4||MapManager.get(c).region==5) return false; //must be asia
			order.add(c);
			if (order.indexOf(c)!=order.lastIndexOf(c)) return false; // no duplicates plox
		}
		return true;
	}

	@Override
	public String getDescription() {
		return "Add 1 US Influence to each of three countries in Asia.";
	}

	@Override
	public String getArguments() {
		return "Three countries in Asia. They may not be the same.";
	}

}

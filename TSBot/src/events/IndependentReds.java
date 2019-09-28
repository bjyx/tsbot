package events;

import java.awt.Color;
import java.util.ArrayList;

import game.GameData;
import main.Launcher;
import map.MapManager;

public class IndependentReds extends Card {
	
	private static final int[] possible = {2, 4, 10, 14, 20}; //bg, cs, hu, ro, yu;
	
	private boolean lessThanReq;
	private ArrayList<Integer> doable;
	
	private static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("")
			.setDescription("")
			.setFooter("\"\"\n"
					+ "- XXXXX, 19XX",Launcher.url("countries/XX.png"))
			.setColor(Color.BLUE);
		if (lessThanReq) {
			if (doable.isEmpty()) {
				builder.addField("No countries to target!", "None of the targetable countries are valid targets for US Influence placement.", false);
			}
			else for (int i : doable) {
				builder.changeInfluence(i, 0, MapManager.get(i).influence[1]-MapManager.get(i).influence[0]);
			}
		}
		else {
			builder.changeInfluence(target, 0, MapManager.get(target).influence[1]-MapManager.get(target).influence[0]);
		}
		GameData.txtchnl.sendMessage(builder.build());
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "022";
	}

	@Override
	public String getName() {
		return "Independent Reds";
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(String[] args) {
		doable = new ArrayList<Integer>();
		for (int i : possible) {
			if (MapManager.get(i).influence[1]-MapManager.get(i).influence[0]>0) {
				doable.add(i);
			}
		}
		if (doable.size()<=1) {
			lessThanReq = true;
			return true;
		}
		lessThanReq = false;
		if (args.length<2) return false;
		if (doable.contains(MapManager.find(args[1]))) {
			target = MapManager.find(args[1]);
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "Add sufficient US Influence to *one* of the following countries such that it equals the amount USSR Influence in that country:\n"
				+ "- Bulgaria\n"
				+ "- Czechoslovakia\n"
				+ "- Hungary\n"
				+ "- Romania\n"
				+ "- Yugoslavia";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "A valid alias for one of Bulgaria, Czechoslovakia, Hungary, Romania, or Yugoslavia. If at most one of these countries is available for placing, the bot will handle it automatically; in this case no argument is required.";
	}

}

package events;

import java.awt.Color;
import java.util.ArrayList;

import cards.Operations;
import game.GameData;
import main.Common;
import map.MapManager;

public class MagyarDemokrataForum extends Card {

	private static ArrayList<Integer> order;
	private static ArrayList<Integer> values;
	
	@Override
	public void onEvent(int sp, String[] args) {
		boolean opponentInfluence=false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder
			.setTitle("Hungarian Democratic Forum")
			.setColor(Color.blue);
		builder.bulkChangeInfluence(order, 0, values);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		
		for (int i=Common.bracket[3]; i<Common.bracket[4]; i++) {
			if (MapManager.get(i).support[1]>0) {
				opponentInfluence=true;
				break;
			}
		}
		if(opponentInfluence) {
			GameData.ops=new Operations(sp, getOpsMod(sp), false, true, false, 1, 3); //one check in hungary
			GameData.dec=new Decision(sp, 1); //uses a general channel for ops
			Common.spChannel(0).sendMessage(Common.spRole(0).getAsMention()+", you may now conduct your support check in Hungary.").complete();
		}
		else {
			Common.spChannel(0).sendMessage("For the oddest reason, you cannot support check Hungary.").complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "040";
	}

	@Override
	public String getName() {
		return "Hungarian Democratic Forum";
	}

	@Override
	public int getOps() {
		return 3;
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
		order = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		if (args.length%2!=1) return false;
		for (int i=1; i<args.length; i+=2) {
			int c = MapManager.find(args[i]);
			if (c==-1) return false;
			if (order.indexOf(c)!=-1) return false; // no duplicates plox
			order.add(c);
			if (!MapManager.get(c).inRegion(3)) return false; // must be Hungary
			try{
				values.add(Integer.parseInt(args[i+1]));
			}
			catch (NumberFormatException e){
				return false; //this isn't an integer. xP
			}
		}
		int sum = 0;
		for (int i=0; i<order.size(); i++) {
			if (values.get(i)<=0) return false; //no non-positive numbers please
			sum += values.get(i);
		}
		if (sum!=3) return false;
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Place 3 Democratic Support in Hungary. Then, the Democrat makes 1 Support Check in Hungary using the Operations of this card.";
	}

	@Override
	public String getArguments() {
		return "Event: Support placement.\n"
				+ "Decision: The Support Check.";
	}

}

package events;

import java.awt.Color;

import cards.Operations;
import game.GameData;
import main.Common;
import main.Launcher;
import map.MapManager;

/**
 * The Legacy of Martial Law Card.
 * @author adalbert
 *
 */
public class LegacyOfMartialLaw extends Card {
	private static int inflTarget;

	@Override
	public void onEvent(int sp, String[] args) {
		boolean opponentInfluence=false;
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("<missing flavortext>")
			.setDescription("Polish leadership evaluates 1981 decision to impose martial law")
			.setFooter("\"It is my duty to take this responsibility - concerning the future of Poland, that my generation fought for on all the fronts of the war and for which they sacrificed the best years of their life.\"\n" + 
					"- Wojciech Jaruzelski, 1981", Launcher.url("people/jaruzelski.png"))
			.setColor(Color.red);
		if (inflTarget==-1) {
			builder.addField("No enemy Support!", "Guess that Martial Law worked out very well. Good job, Jaruzelski.", false);
			GameData.txtchnl.sendMessage(builder.build()).complete();
			return;
		}
		builder.changeInfluence(inflTarget, sp, 1);
		builder.changeInfluence(inflTarget, Common.opp(sp), -1);
		GameData.txtchnl.sendMessage(builder.build()).complete();
		
		for (int i=Common.bracket[1]; i<Common.bracket[2]; i++) {
			if (MapManager.get(i).support[Common.opp(sp)]>0) {
				opponentInfluence=true;
				break;
			}
		}
		if(opponentInfluence) {
			GameData.ops=new Operations(sp, getOpsMod(sp), false, true, false, 1, 1); //one check in poland
			GameData.dec=new Decision(sp, 1);
			Common.spChannel(1).sendMessage(Common.spRole(1).getAsMention()+", you may now conduct your support check in Poland.").complete();
		}
		else {
			Common.spChannel(1).sendMessage("For the oddest reason, you cannot support check Poland.").complete();
		}
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "001";
	}

	@Override
	public String getName() {
		return "Legacy of Martial Law";
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
		return 1;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		int x = 0;
		for (int i=Common.bracket[1]; i<Common.bracket[2]; i++) {
			if (MapManager.get(i).support[0]>0) {
				x+=MapManager.get(i).support[0];
			}
		}
		if (x<=0) {
			inflTarget = -1;
			return true;
		}
		if (args.length<2) return false;
		inflTarget = MapManager.find(args[1]);
		if (!MapManager.get(inflTarget).inRegion(1)) return false; //influence target must be in Poland
		if (MapManager.get(inflTarget).support[0]<=0) return false; //influence target must have Dem SP to target
		return true;
	}

	@Override
	public String getDescription() {
		return "Replace 1 Democratic SP with a Communist SP in any space in Poland. Then, make one Support Check in Poland using this card's value.";
	}

	@Override
	public String getArguments() {
		return "Event: The location of the influence to be removed. This must be in Poland.\n"
				+ "Decision: The space to conduct the support check. This must also be in Poland.";
		
	}

}

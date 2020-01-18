package events;

import java.awt.Color;

import game.Die;
import game.GameData;
import main.Launcher;
import map.MapManager;

public class IranIraqWar extends Card {

	private static int target;

	@Override
	public void onEvent(int sp, String[] args) {
		Die die = new Die();
		int mod = die.roll();
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.addMilOps(sp,  2);
		String adjacents = "";
		for (int i : MapManager.get(target).adj) {
			if (MapManager.get(i).isControlledBy()==(sp+1)%2) {
				mod--;
				adjacents += MapManager.get(i);
			}
		}
		builder.addField(target==23?"Iraq Invades Iran":"Iran Invades Iraq", die + (mod==die.result?":":(": - " + adjacents)), false);
		if (mod>=4) {
			builder.setTitle(target==23?"Iraq demonstrates superiority":"Hussein Overthrown")
				.setDescription(target==23?"Khuzestan annexed to Iraq":"Shi'ite rebellion causes fall of Ba'ath Party")
				.setFooter("\"Since 1986, you have not stopped proclaiming victory, and now you are calling upon population to resist until victory. Is that not an admission of failure on your part?\"\n"
						+ "- Saddam Hussein, 1988",Launcher.url("people/hussein.png"))
				.setColor(sp==0?Color.blue:Color.red);
			builder.changeInfluence(target, sp, MapManager.get(target).influence[(sp+1)%2]);
			builder.changeInfluence(target, (sp+1)%2, -MapManager.get(target).influence[(sp+1)%2]);
			builder.changeVP(sp==0?2:-2);
		}
		else {
			builder.setTitle("Ceasefire brokered between Iran and Iraq")
				.setDescription("War ends in stalemate")
				.setFooter("\"Happy are those who have departed through martyrdom. Unhappy am I that I still survive.… Taking this decision is more deadly than drinking from a poisoned chalice. I submitted myself to Allah's will and took this drink for His satisfaction.\"\n"
						+ "- Ruhollah Khomeini, 1988",Launcher.url("people/khomeini.png"))
				.setColor(sp==0?Color.red:Color.blue);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "102";
	}

	@Override
	public String getName() {
		return "Iran–Iraq War";
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
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		if (args.length<2) return false;
		target = MapManager.find(args[1]);
		if (target!=23 && target != 24) return false;
		return true;
	}

	@Override
	public String getDescription() {
		return "The player of this card gains 2 Military Operations and selects one of Iran or Iraq to invade. Roll a die and subtract 1 from its value for every country bordering the target controlled by your opponent. Upon a modified roll of 4 or greater, gain 2 VP and replace your opponent's influence in the target country with your own.";
	}

	@Override
	public String getArguments() {
		return "A valid alias for one of Iran or Iraq, the country to target.";
	}

}

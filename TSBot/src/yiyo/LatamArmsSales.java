package yiyo;

import java.awt.Color;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import map.MapManager;
/**
 * The Lat-Am Arms Sales Card from the Year-In and Year-Out Pack.
 * @author adalbert
 *
 */
public class LatamArmsSales extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		int usa=0,ssr=0;
		for (int i=64; i<84; i++) {
			if (!MapManager.get(i).isBattleground) {
				if (MapManager.get(i).isControlledBy()==0) usa++;
				else if (MapManager.get(i).isControlledBy()==1) ssr++;
			}
		}
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Soviets Exploit Arms Vacuum")
			.setDescription("Presidential Directive 13 forces Latin American countries to turn towards Soviet weapons")
			.setColor(Color.red);
		builder.changeVP(Math.max(-5, Math.min(0, usa-ssr)));
		GameData.checkScore(false, false);
		if (GameData.getScore()>0) {
			builder.changeDEFCON(-1);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();

	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "138";
	}

	@Override
	public String getName() {
		return "Latin American Arms Sales";
	}

	@Override
	public int getOps() {
		return 3;
	}

	@Override
	public int getEra() {
		return 2;
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
		return true;
	}

	@Override
	public String getDescription() {
		return "The USSR gains VP equal to the disparity between the number of non-battlegrounds in Central and South America controlled by the USSR and the number controlled by the US (to a maximum of 5 VP). Then, if the US is ahead of the USSR in VPs, **degrade DEFCON by 1.**";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

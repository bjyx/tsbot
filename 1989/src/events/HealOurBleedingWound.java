package events;

import java.awt.Color;

import game.GameData;

public class HealOurBleedingWound extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Soviet Troops Pull Out of Afghanistan")
		.setColor(GameData.getEra()==2?Color.blue:Color.red);
		builder.changeVP(GameData.getEra()==0?-3:(GameData.getEra()==1?-1:3));
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
		return "Heal Our Bleeding Would";
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
		return GameData.getEra()==2?"The Democrat gains 3 VPs.":("The Communist gains " + (GameData.getEra()==0?3:1) + " VPs. *This will be " +(GameData.getEra()==0?"1 VP":"3 VPs in the Democrat's favor")+ " in the next part of the year.*");
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

package yiyo;

import java.awt.Color;

import cards.HandManager;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;

public class Proliferation extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle((sp==0?"France":"China")+" successfully tests the atomic bomb").setDescription("")
			.setFooter("\"The idea that every nation ought to have an atomic bomb, like every woman of fashion ought to have a mink coat, is deplorable.\"\n"
					+ "- Clement Attlee, cited 1965", Launcher.url("tz/attlee.png"))
			.setColor(sp==0?Color.blue:Color.red);
		builder.changeDEFCON(-1);
		builder.addField("Nuclear Proliferation","On the next " + (sp==0?"US":"USSR") + " Action Round, DEFCON will increase by 2. The "+(sp==0?"US":"USSR") + " will, after that action round, be given the option to lower DEFCON by another level.",false);
		HandManager.addEffect(1210+sp);
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "121";
	}

	@Override
	public String getName() {
		return "Nuclear Weapons Proliferation";
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
		return 2;
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
		return "**Degrade DEFCON by 1**. At the start of your next action round, DEFCON increases by 2; at the end of that action round, you may choose to **degrade DEFCON by 1** again.";
	}

	@Override
	public String getArguments() {
		return "Event: None.\n"
				+ "Decision: `y(es)` or `n(o)`.";
	}

}

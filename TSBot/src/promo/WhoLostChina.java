package promo;

import java.awt.Color;

import cards.HandManager;
import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;
/**
 * The Who Lost China Card from the Promo Pack.
 * @author adalbert
 *
 */
public class WhoLostChina extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Who Lost China?")
			.setDescription("*White Paper* fails to defend Truman administration from onslaught")
			.setColor(Color.red)
			.setFooter("\"An inexplicable change took place in the relative moral strength of the two contending parties, the Kuomintang and the Communist Party; the morale of one party dropped sharply to below zero, while that of the other rose sharply to white heat. What was the reason? Nobody knows. Such is the logic inherent in the 'high order of culture' of the United States as represented by Dean Acheson.\"\n"
					+ "- Mao Zedong, 1949", Launcher.url("people/mao.png"));
		builder.addMilOps(0, -GameData.getMilOps(0));
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return !(GameData.ccw&&(HandManager.China!=1||HandManager.China!=3));
	}

	@Override
	public String getId() {
		return "117";
	}

	@Override
	public String getName() {
		return "Who Lost China";
	}

	@Override
	public int getOps() {
		return 1;
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Sets US Military Operations to zero. *Can only be played if the China Card is in the possession of the USSR. Cannot be played while the Chinese Civil War is ongoing.*";
	}

	@Override
	public String getArguments() {
		return "None.";
	}

}

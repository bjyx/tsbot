package events;

import java.awt.Color;

import cards.CardList;
import cards.Operations;
import game.GameData;
import main.Launcher;
import map.MapManager;
/**
 * The Soviets Shoot Down KAL-007 Card.
 * @author adalbert
 *
 */
public class KAL007 extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("KAL-007 Shot Down")
			.setDescription("Soviets mistake civilian airliner for spy plane")
			.setColor(Color.blue)
			.setFooter("\"Korean Air zero zero seven Korean Air zero zero seven this is Tokyo Radio if you read me re..."
					+ "request radio check Tokyo requests radio check one two seven decimal six one two seven decimal six\"\n"
					+ "- Last attempt to contact KAL-007", Launcher.url("people/tokyo.png"));
		builder.changeDEFCON(-1);
		builder.changeVP(2);
		if (MapManager.get(42).isControlledBy()==0) {
			builder.addField("\"Korean Airline Massacre\"", "The US may now place influence or conduct realignments using this card.", false);
			GameData.dec = new Decision (0, 89);
			GameData.ops = new Operations (0, CardList.getCard(89).getOpsMod(0), true, true, false, false, false);
		}
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return true;
	}

	@Override
	public String getId() {
		return "089";
	}

	@Override
	public String getName() {
		return "Soviets Shoot Down KAL-007";
	}

	@Override
	public int getOps() {
		return 4;
	}

	@Override
	public int getEra() {
		return 2;
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
		return true;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "**Degrade DEFCON by 1.** The US gains 2 VP.\n" + (MapManager.get(42).isControlledBy()==0?"The US controls South Korea. The US may place influence or conduct realignments with the Operations of this card.":"If the US controls South Korea, the US may place influence or conduct realignments using this card's Operations.");
	}

	@Override
	public String getArguments() {
		return "Event: None."
				+ "Decision: Operations. Must be realignments or influence.";
	}

}

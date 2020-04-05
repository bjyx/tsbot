package promo;

import java.awt.Color;

import events.Card;
import events.CardEmbedBuilder;
import game.GameData;
import main.Launcher;
/**
 * The Stanislav Petrov Card from the Promo Pack.
 * @author adalbert
 *
 */
public class StanislavPetrov extends Card {

	public static int x;
	@Override
	public void onEvent(int sp, String[] args) {
		CardEmbedBuilder builder = new CardEmbedBuilder();
		builder.setTitle("Soviet Officer Prevents Nuclear War")
			.setDescription("Stanislav Petrov ignores false alarm of inbound American missiles")
			.setColor(Color.gray)
			.setFooter("\"It is nice of them to consider me a hero. I don’t know that I am. Since I am the only one in this country who has found himself in this situation, it is difficult to know if others would have acted differently.\"\n"
					+ "- Stanislav Petrov, 2004", Launcher.url("promo/petrov.png"));
		builder.changeDEFCON(x-GameData.getDEFCON());
		GameData.txtchnl.sendMessage(builder.build()).complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		return GameData.getDEFCON()==2;
	}

	@Override
	public String getId() {
		return "114";
	}

	@Override
	public String getName() {
		return "Stanislav Petrov";
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
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return true;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		try {
			x = Integer.parseInt(args[1]);
			return x==4||x==5;
		} catch(NumberFormatException err) {
			return false;
		}
	}

	@Override
	public String getDescription() {
		return "Raise DEFCON to 4 or 5. *Can only be played when DEFCON is 2.*";
	}

	@Override
	public String getArguments() {
		return "Either of the numbers 4 or 5.";
	}

}

package events;

import game.GameData;
/**
 * The China Card. This is only here for completeness's sake.
 * @author adalbert
 *
 */
public class ChinaCard extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		// this should never trigger but is nonetheless required
		GameData.txtchnl.sendMessage(":warning: You have encountered an error—the China card should not be played for the event. Please leave a comment on Github and I will rectify this. This is fine. :fire: :fire: :fire:").complete();
	}

	@Override
	public boolean isPlayable(int sp) {
		// this should be used so you can't play it for the event. China is not headlineable either.
		return false;
	}

	@Override
	public String getId() {
		return "006";
	}

	@Override
	public String getName() {
		return "The China Card";
	}

	@Override
	public int getOps() {
		return 4;
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
		// this should also not be used but eh it's in the formatting
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		// this should also not be used...
		return false;
	}

	@Override
	public String getDescription() {
		return "Starts the game with the USSR. This card provides an extra operations point if all operations are used in Asia. Passes to other player upon use. Worth 1 VP at the end of Turn 10. *Cancels, but does not prevent, Formosan Resolution if played by the US.*";
	}

	@Override
	public String getArguments() {
		return "Again, you are not using this for an event. Any attempt to do so will be invalidated.";
	}

}

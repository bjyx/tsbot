package events;

import game.GameData;

public class UNIntervention extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.txtchnl.sendMessage(":warning: If you are seeing this message, then you have discovered a bug that allows `032 UN Intervention` to be played \"for the event\". To rectify this error, you can drop a comment on GitHub detailing the situation. This is fine. :fire: :fire: :fire:");
	}

	@Override
	public boolean isPlayable(int sp) {
		// no thanks
		return false;
	}

	@Override
	public String getId() {
		return "032";
	}

	@Override
	public String getName() {
		return "UN Intervention";
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
		return 2;
	}

	@Override
	public boolean isRemoved() {
		return false;
	}

	@Override
	public boolean isFormatted(String[] args) {
		// no thanks
		return false;
	}

	@Override
	public String getDescription() {
		return "Play with an event associated with your opponent; use the operations of your opponent's card without triggering the event. *May not be played as a headline*.";
	}

	@Override
	public String getArguments() {
		return "You aren't going to write TS.play 32 e. You write TS.play [other card here] u, which takes you straight to the operations.";
	}

}

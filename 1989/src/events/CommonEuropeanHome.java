package events;

import game.GameData;

public class CommonEuropeanHome extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		GameData.txtchnl.sendMessage("Data indicates that you have managed to play Common European Home for the event. This isn't supposed to happen. Mark it as a bug on my GitHub with appropriate context and I'll be sure to fix it.");
	}

	@Override
	public boolean isPlayable(int sp) {
		return false;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "021";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Common European Home";
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
		return false;
	}

	@Override
	public boolean isFormatted(int sp, String[] args) {
		return false;
	}

	@Override
	public String getDescription() {
		return "Play with a card associated with your Opponent to cancel that event and play that card for its Operations. Your Opponent gains 1 VP if this card is played for Operations.";
	}

	@Override
	public String getArguments() {
		return "TS.play **<id>** c, where **id** is the card to cancel the event of. Attempting to play this card directly for the event will fail.";
	}

}

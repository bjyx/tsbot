package events;

import game.GameData;

public class ChinaCard extends Card {

	@Override
	public void onEvent(String[] args) {
		// this should never trigger but is nonetheless required
		GameData.txtchnl.sendMessage(":warning: Aaaaaaaaaagh how did you even do that is this game bugged");
	}

	@Override
	public boolean isPlayable() {
		// this should also not be used
		return false;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "006";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "The China Card";
	}

	@Override
	public int getOps() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public int getEra() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAssociation() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public boolean isRemoved() {
		// this should also not be used but eh it's in the formatting
		return false;
	}

	@Override
	public boolean isFormatted(String[] args) {
		// this should also not be used...
		return false;
	}

	@Override
	public String getDescription() {
		return "Starts the game with the USSR. This card provides an extra operations point if all operations are used in Asia. Passes to other player upon use. Worth 1 VP at the end of Turn 10. *Cancels, but does not prevent, Formosan Resolution if played by the US.*";
	}

	@Override
	public String getArguments() {
		// TODO Auto-generated method stub
		return "Again, you are not using this for an event. Any attempt to do so will result in this card being played for operations.";
	}

}

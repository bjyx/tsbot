package cards;

import events.Card;
/**
 * The Placeholder Card.
 * @author adalbert
 *
 */
public class Placeholder extends Card {

	@Override
	public void onEvent(int sp, String[] args) {
		//nothing
	}

	@Override
	public boolean isPlayable(int sp) {
		return false;
	}

	@Override
	public String getId() {
		return "000";
	}

	@Override
	public String getName() {
		return "Placeholder";
	}

	@Override
	public int getOps() {
		return -1;
	}

	@Override
	public int getEra() {
		return -1;
	}

	@Override
	public int getAssociation() {
		return -1;
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
		return "If you're seeing this, this is not supposed to happen.";
	}

	@Override
	public String getArguments() {
		return "Aaaaaaaaaa.";
	}

}

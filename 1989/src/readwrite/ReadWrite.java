package readwrite;

import cards.CardList;
import cards.HandManager;
import game.GameData;
import map.MapManager;
/**
 * A class that handles the save states of the game.
 * @author adalbert
 *
 */
public class ReadWrite {
	/*
	 * There is something that needs to be said about the structure of the string used here.
	 * 
	 * First is two characters in base 16 that describe the ruleset being used.
	 * 
	 * Characters 2-177 are exactly like TwiStrug:
	 * SDTRMOPAATBEBGCACSDKDDFIFRGRHUITNOPLROESSETRGBDEYUEGAEIRIQILJOLBLYSASYAFAUBUINIDJPLAMYKPPKPHKRTWTHVNDZAOBWCMETCIKEMANGMZTDSOZASDTNMLZRZWCRCUDOSVGTHTHNMXNIPAARBOBRCLCOECPYPEUYVE
	 *         1         2         3         4         5         6         7         8         9        10        11        12        13        14        15        16        17       
	 * These exactly describe the state of the board. 
	 * 
	 * Character 178 is the amount of influence in China.
	 * 
	 * Characters from 179-318 describe the state of each card: (including the placeholder)
	 * - 0: does not exist in this game (i.e. replaced by placeholder)
	 * - 1: exists. Not in play (e.g. Mid War cards before turn 4, ShuttleDip while active).
	 * - 2: exists. In deck.
	 * - 3: exists. In US Hand.
	 * - 4: exists. In USSR Hand.
	 * - 5: exists, discarded.
	 * - 6: exists, removed (e.g. * cards, Defectors if it gets hit by Antifascist)
	 * 
	 * For card 6 (China), it's HandManager.China + 1:
	 * - 0: pre-CCW.
	 * - 1: Face-up, US.
	 * - 2: Face-up, USSR.
	 * - 3: Face-down, US.
	 * - 4: Face-down, USSR.
	 * 
	 * The next 11 describe special effects that are active in hex, in this order:
	 * - Vietnam Revolts 8
	 * - Containment 4
	 * - Formosa 2
	 * - Nuclear Subs 1
	 * 
	 * - Quagmire 8
	 * - SALT 4
	 * - Bear Trap 2
	 * - WWBY 1
	 * 
	 * - Brezhnev 8
	 * - U2 4
	 * - Shuttle 2
	 * - North Sea 1
	 * 
	 * - Iran-Contra 8
	 * - Chernobyl (<7)
	 * 
	 * - NORAD 8
	 * - Yuri and Samatha 4
	 * - Kremlin Flu 2
	 * - Tsar Bomba 1
	 * 
	 * - Checkpoint C 8
	 * - Indo-Soviet 4
	 * - Red Scare 2
	 * - Purge 1
	 * 
	 * - Missile Crisis Turkey 8
	 * - Missile Crisis Cuba 4
	 * - Missile Envy US 2
	 * - Missile Envy USSR 1
	 * 
	 * - LADS 8
	 * - LADS 4
	 * - Yalta 2
	 * - Allied Berlin 1
	 * 
	 * - VJ6 8
	 * - Coalition 4
	 * - Tory Victory 2
	 * - VJ1 1
	 * 
	 * - Nuclear Proliferation US 8
	 * - Nuclear Proliferation USSR 4
	 * - Arkhipov US 2
	 * - Arkhipov USSR 1
	 * 
	 * - EDSA US 8
	 * - EDSA USSR 4
	 * - Natlist China 2
	 * - Nuclear Proliferation end decision 1
	 */
	/**
	 * Events that have a temporary effect. 
	 */
	//private static final int[][] a = {{9,25,35,41},{42,43,44,50},{51,60,73,86},{93,0,0,94},{106,109,115,126},{128,129,310,311},{400,401,490,491},{690,691,1001,1002},{1003,1004,1005,1006},{1210,1211,1270,1271},{1350,1351,0,0}};
	//TODO redo the above
	/**
	 * A way to convert any number into a character for the board state notation in TwiStrug.
	 * @param i is the number in question
	 * @return a character.
	 */
	private static char urlParser(int i) {
		if (i<10) return (char) ('0'+i);
		else if (i<36) return (char) ('a'+i-10);
		else return (char) ('A'+i-36);
	}
	/**
	 * Undoes the operation performed by {@link readwrite.ReadWrite.urlParser}.
	 * @param i is the number in question
	 * @return a character.
	 */
	public static int undoParser(char c) {
		if (c>='0'&&c<='9') return (c-'0');
		else if (c>='a'&&c<='z') return (c-'a'+10);
		else return (c-'A'+36);
	}
	/**
	 * Creates a string for the exact save state of the game. 
	 * @return
	 */
	public static String write() {
		String url = "" 
				+ urlParser(GameData.getScore()+20)			//0
				+ urlParser(GameData.getStab())
				+ urlParser(GameData.getTurn())
				+ urlParser(GameData.getAR())					
				+ urlParser(GameData.getT2(0))
				+ urlParser(GameData.getT2(1));				//5
		for (int i=0; i<75; i++) {
			url += urlParser(MapManager.get(i).support[0]);	 
			url += urlParser(MapManager.get(i).support[1]); 
		}													//80
		for (int i=0; i<=110; i++) {
			if (CardList.getCard(i).getId().equals("000")) url += "0"; //placeholder
			else {
				if (HandManager.Deck.contains(i)) url += "2";
				else if (HandManager.DemHand.contains(i)) url += "3";
				else if (HandManager.ComHand.contains(i)) url += "4";
				else if (HandManager.Discard.contains(i)) url += "5";
				else if (HandManager.Removed.contains(i)) url += "6";
				else url += "1";
			}
		}													//190
		/*for (int i=0; i<11; i++) { //TODO
			int x=0;
			for (int j=0; j<4; j++) {
				if (HandManager.effectActive(a[i][j])) x += (int) Math.pow(2, 3-j);
				if (a[i][j]==94) {
					if (Chernobyl.reg==0);
					else if (Chernobyl.reg<5) x += Chernobyl.reg - 2;
					else x += Chernobyl.reg - 3;
				}
				if (i==10 && j == 2) {
					if (!CardList.getCard(35).isRemoved()) x+=2; //natlst china is not removed from the decc but formosa is, making this a good distinguisher
				}
				if (i==10 && j == 3) {
					if (Operations.tsarbomba) x+=1;
				}
			}
			url += urlParser(x);
		}*/
		return url;
	}
}

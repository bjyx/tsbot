package readwrite;

import cards.CardList;
import cards.HandManager;
import cards.Operations;
import commands.StartCommand;
import events.Chernobyl;
import game.GameData;
import map.MapManager;

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
	 * - 0: does not exist in this game
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
	private static final int[][] a = {{9,25,35,41},{42,43,44,50},{51,60,73,86},{93,0,0,94},{106,109,115,126},{128,129,310,311},{400,401,490,491},{690,691,1001,1002},{1003,1004,1005,1006},{1210,1211,1270,1271},{1350,1351,0,0}};
	
	private static char urlParser(int i) {
		if (i<10) return (char) ('0'+i);
		else if (i<36) return (char) ('a'+i-10);
		else return (char) ('A'+i-36);
	}
	public static int undoParser(char c) {
		if (c>='0'&&c<='9') return (c-'0');
		else if (c>='a'&&c<='z') return (c-'a'+10);
		else return (c-'A'+36);
	}
	public static String write() {
		String url = "" + urlParser(StartCommand.ruleset/16)  	//0
				+ urlParser(StartCommand.ruleset%16) 
				+ urlParser(GameData.getScore()+20)
				+ urlParser(GameData.getDEFCON())
				+ urlParser(GameData.getTurn())
				+ urlParser(GameData.getAR())					//5
				+ urlParser(GameData.getMilOps(0))
				+ urlParser(GameData.getMilOps(1))
				+ urlParser(GameData.getSpace(0))
				+ urlParser(GameData.getSpace(1));
		for (int i=0; i<84; i++) {
			url += urlParser(MapManager.get(i).influence[0]);	//10, ... 
			url += urlParser(MapManager.get(i).influence[1]);
		}
		url += urlParser(MapManager.get(86).influence[1]);
		for (int i=0; i<=138; i++) {							//179 is 0
			if (CardList.getCard(i).getId().equals("000")) url += "0"; //placeholder
			else {
				if (i==6) url += (HandManager.China + 3);
				else if (HandManager.Deck.contains(i)) url += "2";
				else if (HandManager.USAHand.contains(i)) url += "3";
				else if (HandManager.SUNHand.contains(i)) url += "4";
				else if (HandManager.Discard.contains(i)) url += "5";
				else if (HandManager.Removed.contains(i)) url += "6";
				else url += "1";
			}
		}
		for (int i=0; i<11; i++) {
			int x=0;
			for (int j=0; j<4; j++) {
				if (HandManager.effectActive(a[i][j])) x += (int) Math.pow(2, 3-j);
				if (a[i][j]==94) {
					if (Chernobyl.reg==0);
					else if (Chernobyl.reg<5) x += Chernobyl.reg - 2;
					else x += Chernobyl.reg - 3;
				}
				if (i==10 && j == 2) {
					if (!CardList.getCard(35).isRemoved()) x+=2; //natlst china is not removed from the decc but formosa is
				}
				if (i==10 && j == 3) {
					if (Operations.tsarbomba) x+=1;
				}
			}
			url += urlParser(x);
		}
		return url;
	}
}

package cards;

import java.util.ArrayList;

import events.*;
/**
 * Implements a list of the cards used in the game.
 * @author [REDACTED]
 *
 */
public class CardList {
	/**
	 * Accesses the list itself.
	 */
	public static ArrayList<Card> cardList;
	/**
	 * The number of cards in the cardlist. There should be 111 after initiation.
	 */
	private static int counter=0;
	/**
	 * Initializes the list with all 110 Cards and a null card.
	 */
	public static void initialize() {
		CardList.addCard(null); //just to occupy the 0 slot so I don't get peeved enough to use a map
		
		CardList.addCard(new AsiaScoring());
		CardList.addCard(new EuropeScoring());
		CardList.addCard(new MiddleEastScoring());
		CardList.addCard(new DuckAndCover());
		CardList.addCard(new FiveYearPlan());
		CardList.addCard(new ChinaCard());
		CardList.addCard(new SocialistGovernments());
		CardList.addCard(new Fidel());
		CardList.addCard(new VietnamRevolts());
		CardList.addCard(new Blockade());
		
		//CardList.addCard(new KoreanWar());
		//CardList.addCard(new RomanianAbdication());
		//CardList.addCard(new ArabIsraeliWar());
		//CardList.addCard(new Comecon());
		//CardList.addCard(new Nasser());
		//CardList.addCard(new WarsawPact());
		//CardList.addCard(new DeGaulle());
		//CardList.addCard(new Paperclip());
		//CardList.addCard(new TrumanDoctrine());
		//CardList.addCard(new OlympicGames());
		
		/*CardList.addCard(new NATO());
		CardList.addCard(new IndependentReds());
		CardList.addCard(new MarshallPlan());
		CardList.addCard(new IndoPakiWar());
		CardList.addCard(new Containment());
		CardList.addCard(new CIACreated());
		CardList.addCard(new AnpoTreaty());
		CardList.addCard(new SuezCrisis());
		CardList.addCard(new EastEuropeanUnrest());
		CardList.addCard(new Decolonization());*/
		
		/*CardList.addCard(new RedScare());
		CardList.addCard(new UNIntervention());
		CardList.addCard(new DeStalinization());
		CardList.addCard(new NuclearTestBan());
		CardList.addCard(new FormosanResolution());
		CardList.addCard(new BrushWar());
		CardList.addCard(new CentrAmScoring());
		CardList.addCard(new SEAsiaScoring());
		CardList.addCard(new ArmsRace());
		CardList.addCard(new MissileCrisis());*/
		
		/*CardList.addCard(new NuclearSubs());
		CardList.addCard(new Quagmire());
		CardList.addCard(new SALTNegotiations());
		CardList.addCard(new BearTrap());
		CardList.addCard(new Summit());
		CardList.addCard(new DrStrangelove());
		CardList.addCard(new Junta());
		CardList.addCard(new KitchenDebates());
		CardList.addCard(new MissileEnvy());
		CardList.addCard(new WeWillBuryYou());*/
		
		/*CardList.addCard(new BrezhnevDoctrine());
		CardList.addCard(new PortEmpire());
		CardList.addCard(new SouthAfricanUnrest());
		CardList.addCard(new Allende());
		CardList.addCard(new WillyBrandt());
		CardList.addCard(new MuslimRevolution());
		CardList.addCard(new ABMTreaty());
		CardList.addCard(new CulturalRevolution());
		CardList.addCard(new FlowerPower());
		CardList.addCard(new U2Incident());*/
		
		/*CardList.addCard(new OPEC());
		CardList.addCard(new LoneGunman());
		CardList.addCard(new ColonialRearGuards());
		CardList.addCard(new PanamaCanal());
		CardList.addCard(new CampDavidAccords());
		CardList.addCard(new PuppetGovernments());
		CardList.addCard(new GrainSales());
		CardList.addCard(new JohnPaulII());
		CardList.addCard(new LADS());
		CardList.addCard(new OASFounded());*/
		
		/*CardList.addCard(new NixonInChina());
		CardList.addCard(new Sadat());
		CardList.addCard(new ShuttleDiplomacy());
		CardList.addCard(new VoiceOfAmerica());
		CardList.addCard(new LiberationTheology());
		CardList.addCard(new UssuriRiverSkirmish());
		CardList.addCard(new Inaugural());
		CardList.addCard(new AllianceForProgress());
		CardList.addCard(new AfricaScoring());
		CardList.addCard(new OneSmallStep());*/
		
		/*CardList.addCard(new SouthAmScoring());
		CardList.addCard(new IranHostage());
		CardList.addCard(new IronLady());
		CardList.addCard(new ReaganBombsLibya());
		CardList.addCard(new StarWars());
		CardList.addCard(new NorthSeaOil());
		CardList.addCard(new TheReformer());
		CardList.addCard(new MarineBarracks());
		CardList.addCard(new KAL007());
		CardList.addCard(new Glasnost());*/
		
		/*CardList.addCard(new Ortega());
		CardList.addCard(new Terrorism());
		CardList.addCard(new IranContra());
		CardList.addCard(new Chernobyl());
		CardList.addCard(new DebtCrisis());
		CardList.addCard(new TearDownThisWall());
		CardList.addCard(new EvilEmpire());
		CardList.addCard(new AldrichAmes());
		CardList.addCard(new PershingII());
		CardList.addCard(new Wargames());*/
		
		/*CardList.addCard(new Solidarity());
		CardList.addCard(new IranIraqWar());
		CardList.addCard(new Defectors());
		CardList.addCard(new CambridgeFive());
		CardList.addCard(new SpecialRelationship());
		CardList.addCard(new NORAD());
		CardList.addCard(new Che());
		CardList.addCard(new OurManInTehran());
		CardList.addCard(new YuriAndSamantha());
		CardList.addCard(new AWACSSale());*/
	}
	/**
	 * Adds the Card to the list.
	 * @param card is the card to add - these must extend the abstract 'card' class.
	 */
	public static void addCard(Card card) {
		cardList.add(card);
		counter++;
	}
	/**
	 * Obtains the card at a position in the list.
	 * @param i is the card requested.
	 * @return the Card at index i in the cardList.
	 */
	public static Card getCard(int i) {
		return cardList.get(i);
	}
	/**
	 * A check to see if the number of cards is sufficient to start a game with.
	 * @return counter-1, representing all the cards in the list sans the null card.
	 */
	public static int numberOfCards() {
		return counter-1; // counter should be @ 111
	}
}

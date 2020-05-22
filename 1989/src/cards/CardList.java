package cards;

import java.util.ArrayList;

import events.*;
//import game.GameData;
/**
 * Implements a list of the cards used in the game.
 * @author adalbert
 *
 */
public class CardList {
	/**
	 * Accesses the list itself.
	 */
	public static ArrayList<Card> cardList = new ArrayList<Card>();
	/**
	 * The number of cards in the cardlist. There should be 111 after initiation.
	 */
	private static int counter=0;
	/**
	 * Initializes the list with all cards and a null card.
	 */
	public static void initialize() {
		cardList = new ArrayList<Card>();
		CardList.addCard(new Placeholder()); //just to occupy the 0 slot so I don't get peeved enough to use a map

		CardList.addCard(new LegacyOfMartialLaw());
		CardList.addCard(new Solidarity()); //red
		CardList.addCard(new Walesa());
		CardList.addCard(new Michnik());
		CardList.addCard(new GeneralStrike());
		CardList.addCard(new Questioning());
		CardList.addCard(new StateRunMedia());
		CardList.addCard(new Prudence());
		CardList.addCard(new TheWall());
		CardList.addCard(new CultOfPersonality());
		
		CardList.addCard(new DissidentArrested());
		CardList.addCard(new Apparatchiks());
		CardList.addCard(new Stasi());
		CardList.addCard(new Gorby());
		CardList.addCard(new Honecker());
		CardList.addCard(new Nomenklatura());
		CardList.addCard(new RoundTableTalks());
		CardList.addCard(new Pozsgay());
		CardList.addCard(new PapalVisit());
		CardList.addCard(new DeutscheMarks());
		
		CardList.addCard(new CommonEuropeanHome());
		CardList.addCard(new ScoringPoland());
		CardList.addCard(new ScoringHungary());
		CardList.addCard(new StNicholasChurch()); //red
		CardList.addCard(new Perestroika());
		CardList.addCard(new HelsinkiFinalAct()); //red
		CardList.addCard(new Consumerism());
		CardList.addCard(new FactoryPartyCells());
		CardList.addCard(new JanPalachWeek());
		CardList.addCard(new TearGas());
		
		CardList.addCard(new Intelligentsia());
		CardList.addCard(new PeasantParties());
		CardList.addCard(new Sajudis());		//red
		CardList.addCard(new FIDESZ());
		CardList.addCard(new HealOurBleedingWound());
		CardList.addCard(new DashForTheWest());
		CardList.addCard(new NagyReburied());
		CardList.addCard(new JulyConcept());
		CardList.addCard(new EcoGlasnost());
		CardList.addCard(new MagyarDemokrataForum());
		//MidWar
		CardList.addCard(new Ceausescu());
		CardList.addCard(new ScoringEastGermany());
		CardList.addCard(new ScoringBulgaria());
		CardList.addCard(new InflationaryCurrency());
		CardList.addCard(new SovietTroopWithdrawals());
		CardList.addCard(new GoodbyeLenin());
		CardList.addCard(new BulgarianTurks());
		CardList.addCard(new WeAreThePeople());
		CardList.addCard(new ForeignCurrencyDebtBurden());
		CardList.addCard(new SinatraDoctrine());
		/*
		CardList.addCard(new AnniversaryCelebration());
		CardList.addCard(new Normalisation());
		CardList.addCard(new LiPeng());
		CardList.addCard(new CrowdTurnsAgainstCeausescu()); //red; see 7.6
		CardList.addCard(new ScoringCzechoslovakia());
		CardList.addCard(new ForeignTelevision());
		CardList.addCard(new CentralCommitteeReshuffle());
		CardList.addCard(new BorderReopened());
		CardList.addCard(new GrenzTruppen());
		CardList.addCard(new ToxicWaste());
		/*
		CardList.addCard(new MondayDemonstrations());
		CardList.addCard(new Yakolev());
		CardList.addCard(new Genscher());
		CardList.addCard(new LegacyOf1968());
		CardList.addCard(new PresidentialVisit());
		CardList.addCard(new NewForum());
		CardList.addCard(new ReformerRehabilitated());
		CardList.addCard(new KlausAndKomarek());
		CardList.addCard(new Systematisation());
		CardList.addCard(new Securitate());
		/*
		CardList.addCard(new KissOfDeath());
		CardList.addCard(new PeasantPartiesRevolt());
		CardList.addCard(new LaszloTokes());		//red
		CardList.addCard(new FRGEmbassies());
		CardList.addCard(new ExitVisas());
		CardList.addCard(new WarsawPactSummit());
		CardList.addCard(new Samizdat());
		CardList.addCard(new WorkersRevolt());
		CardList.addCard(new TheThirdWay());
		CardList.addCard(new Nepotism());
		/*
		CardList.addCard(new TheBalticWay());		//red
		//LateWar
		CardList.addCard(new Spitzel());
		CardList.addCard(new Modrow());
		CardList.addCard(new BreakawayBaltics());	//red
		CardList.addCard(new RubberDucks()); ///s
		CardList.addCard(new TheWallMustGo());
		CardList.addCard(new KohlReunification());
		CardList.addCard(new Adamec());
		CardList.addCard(new DominoTheory());
		CardList.addCard(new CivicForum());
		/*
		CardList.addCard(new MyFirstBanana());
		CardList.addCard(new Betrayal());
		CardList.addCard(new ShockTherapy());
		CardList.addCard(new UnionOfDemocraticForces());
		CardList.addCard(new ScoringRomania());
		CardList.addCard(new ChineseSolution());
		CardList.addCard(new TyrantIsGone()); //see 7.6
		CardList.addCard(new PolitburoIntrigue());
		CardList.addCard(new Ligachev());
		CardList.addCard(new StandFast());
		/*
		CardList.addCard(new Elena());
		CardList.addCard(new NationalSalvationFront());
		CardList.addCard(new GovernmentResigns());
		CardList.addCard(new NewYearsEveParty());
		CardList.addCard(new PublicAgainstViolence());
		CardList.addCard(new SocialDemocraticPlatform());
		CardList.addCard(new Timisoara());
		CardList.addCard(new ArmyBacksRevolution());
		CardList.addCard(new KremlinCoup());
		CardList.addCard(new MaltaSummit());*/
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
		if (i<=numberOfCards()) return cardList.get(i);
		return null;
	}
	/**
	 * A check to see if the number of cards is sufficient to start a game with.
	 * @return counter-1, representing all the cards in the list sans the first null card.
	 */
	public static int numberOfCards() {
		return counter-1;
	}
}

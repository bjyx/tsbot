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
	 * The number of cards in the cardlist. There should be 361 after initiation. Holy schist that's a lot of cards.
	 */
	private static int counter=0;
	/**
	 * Initializes the list with all cards and a null card.
	 */
	public static void initialize() {
		cardList = new ArrayList<Card>();
		CardList.addCard(new Placeholder()); //just to occupy the 0 slot so I don't get peeved enough to use a map
//US cards start here
		CardList.addCard(new Backlash());
		CardList.addCard(new Biometrics()); //red
		CardList.addCard(new CTR());
		CardList.addCard(new MoroTalks());
		CardList.addCard(new NEST());
		CardList.addCard(new Sanctions());
		CardList.addCard(new Sanctions());
		CardList.addCard(new SpecialForces());
		CardList.addCard(new SpecialForces());
		CardList.addCard(new SpecialForces());
		
		CardList.addCard(new Abbas());
		CardList.addCard(new AlAzhar());
		CardList.addCard(new AnbarAwakening());
		CardList.addCard(new CovertAction());
		CardList.addCard(new Ethiopia());
		CardList.addCard(new EuroIslam());
		CardList.addCard(new FSB());
		CardList.addCard(new IntelCommunity());
		CardList.addCard(new KemalistRepublic());
		CardList.addCard(new KingAbdullah());
		
		CardList.addCard(new LetsRoll());
		CardList.addCard(new MossadShinBet());
		CardList.addCard(new Predator());
		CardList.addCard(new Predator());
		CardList.addCard(new Predator());
		CardList.addCard(new Quartet());
		CardList.addCard(new SaddamCaptured());
		CardList.addCard(new Sharia());
		CardList.addCard(new TonyBlair());
		CardList.addCard(new UNNationBuilding());
		
		CardList.addCard(new Wiretapping());
		CardList.addCard(new BackChannel());
		CardList.addCard(new BenazirBhutto());
		CardList.addCard(new EnhancedMeasures());
		CardList.addCard(new Hijab());
		CardList.addCard(new IndoPakiTalks());
		CardList.addCard(new IraqiWMD());
		CardList.addCard(new LibyanDeal());
		CardList.addCard(new LibyanWMD());
		CardList.addCard(new MassTurnout());

		CardList.addCard(new NATO());
		CardList.addCard(new PakiOffensive());
		CardList.addCard(new PatriotAct());
		CardList.addCard(new Renditions());
		CardList.addCard(new SaferNow());
		CardList.addCard(new Sistani());
		CardList.addCard(new IjtihadA());
		//Jihadist cards stop here
		CardList.addCard(new AdamGadahn());
		CardList.addCard(new IttihadIslami());
		CardList.addCard(new AnsarAlIslam());
		
		CardList.addCard(new FREs());
		CardList.addCard(new IEDs());
		CardList.addCard(new Madrassas());
		CardList.addCard(new MoqtadaAlSadr()); //Sadr
		CardList.addCard(new UyghurJihad());
		CardList.addCard(new VieiraDeMelloSlain());
		CardList.addCard(new AbuSayyaf());
		CardList.addCard(new AlAnbar());
		CardList.addCard(new Amerithrax());
		CardList.addCard(new BhuttoShot());
		
		CardList.addCard(new DetaineeRelease());
		CardList.addCard(new ExKGB());
		CardList.addCard(new GazaWar());
		CardList.addCard(new HaririKilled());
		CardList.addCard(new HEU());
		CardList.addCard(new Homegrown());
		CardList.addCard(new IslamicJihadUnion());
		CardList.addCard(new JemaahIslamiya());
		CardList.addCard(new KazakhStrain());
		CardList.addCard(new LashkarETayyiba());
		
		CardList.addCard(new LooseNuke());
		CardList.addCard(new Opium());
		CardList.addCard(new Pirates());		//red
		CardList.addCard(new SchengenVisas());
		CardList.addCard(new SchroederChirac());
		CardList.addCard(new AbuGhurayb());
		CardList.addCard(new AlJazeera());
		CardList.addCard(new AxisOfEvil());
		CardList.addCard(new CleanOperatives());
		CardList.addCard(new FATA());
		
		CardList.addCard(new ForeignFighters());		//red
		CardList.addCard(new JihadistVideos());
		CardList.addCard(new Kashmir());
		CardList.addCard(new Leak());	//red
		CardList.addCard(new Leak()); ///s
		CardList.addCard(new LebanonWar());
		CardList.addCard(new MartyrdomOperation());
		CardList.addCard(new MartyrdomOperation());
		CardList.addCard(new MartyrdomOperation());
		CardList.addCard(new Quagmire());
		
		CardList.addCard(new RegionalAlQaeda());
		CardList.addCard(new Saddam());
		CardList.addCard(new Taliban());
		CardList.addCard(new IjtihadJ());
		CardList.addCard(new Wahhabism());
		//Neutrals start here
		CardList.addCard(new DanishCartoons());
		CardList.addCard(new Fatwa()); //see 7.6
		CardList.addCard(new GazaWithdrawal());
		CardList.addCard(new Hamas());
		CardList.addCard(new HizbUtTahrir());
		
		CardList.addCard(new Kosovo());
		CardList.addCard(new FormerSovietUnion());
		CardList.addCard(new Hizballah());
		CardList.addCard(new Iran());
		CardList.addCard(new Iran());
		CardList.addCard(new JayshAlMahdi());
		CardList.addCard(new Kurdistan());
		CardList.addCard(new Musharraf());
		CardList.addCard(new ToraBora());
		CardList.addCard(new Zarqawi());
		
		CardList.addCard(new Zawahiri());
		CardList.addCard(new BinLadin());
		CardList.addCard(new Darfur());
		CardList.addCard(new GTMO());
		CardList.addCard(new Hambali());
		CardList.addCard(new KSM());
		CardList.addCard(new OilPriceSpike());
		CardList.addCard(new OilPriceSpike());
		CardList.addCard(new Saleh());
		CardList.addCard(new USElection());
		
		//Awakening cards
		
		CardList.addCard(new Advisors());
		CardList.addCard(new Backlash()); //red
		CardList.addCard(new HumanitarianAid());
		CardList.addCard(new PearlRoundabout());
		CardList.addCard(new Peshmerga());
		CardList.addCard(new Reaper());
		CardList.addCard(new Reaper());
		CardList.addCard(new Reaper());
		CardList.addCard(new SpecialForces());	//repeat?
		CardList.addCard(new SpecialForces());
		
		CardList.addCard(new ArabSpringFallout());
		CardList.addCard(new BattleSirte());
		CardList.addCard(new BenghaziFalls());
		CardList.addCard(new CivilResistance());
		CardList.addCard(new DeltaSeals());
		CardList.addCard(new FactionalInfighting());
		CardList.addCard(new FMS());
		CardList.addCard(new IntelCommunity()); //repeat?
		CardList.addCard(new InternationalBanking());
		CardList.addCard(new MaerskAlabama());
		
		CardList.addCard(new MalalaYousafzai());
		CardList.addCard(new Militia());
		CardList.addCard(new ObamaDoctrine());
		CardList.addCard(new OperationNewDawn());
		CardList.addCard(new RussianAid());
		CardList.addCard(new Sharia());
		CardList.addCard(new StrikeEagle());
		CardList.addCard(new TahrirSquare());
		CardList.addCard(new UNNationBuilding()); //repeat?
		CardList.addCard(new UNSCR1973());
		
		CardList.addCard(new UNSCR2118());
		CardList.addCard(new CongressActs());
		CardList.addCard(new Facebook());
		CardList.addCard(new Facebook());
		CardList.addCard(new Fracking());
		CardList.addCard(new GulfUnion());
		CardList.addCard(new LimitedDeployment());
		CardList.addCard(new MassTurnout()); //repeat?
		CardList.addCard(new NATO());		//repeat?
		CardList.addCard(new OperationNeptuneSpear());

		CardList.addCard(new PRISM());
		CardList.addCard(new SCAF());
		CardList.addCard(new StatusQuo());
		//Jihadist
		CardList.addCard(new BloodyThursday());
		CardList.addCard(new Coup());
		CardList.addCard(new Ferguson());
		CardList.addCard(new HouthiRebels());
		CardList.addCard(new IEDs());		//repeat?
		CardList.addCard(new IslamicMaghreb());
		CardList.addCard(new TheftOfState());
		
		CardList.addCard(new AbuGhraibJailBreak());
		CardList.addCard(new AlShabaab());
		CardList.addCard(new ArabWinter());
		CardList.addCard(new BostonMarathon()); //Sadr
		CardList.addCard(new Censorship());
		CardList.addCard(new ChangeOfState());
		CardList.addCard(new GazaRockets());
		CardList.addCard(new GhostSoldiers());
		CardList.addCard(new KoreanCrises());
		CardList.addCard(new MosulCentral());
		
		CardList.addCard(new NPTSafeguardsIgnored());
		CardList.addCard(new ParisAttacks());
		CardList.addCard(new Pirates()); //repeat?
		CardList.addCard(new Sequestration());
		CardList.addCard(new AlMaliki());
		CardList.addCard(new BokoHaram());
		CardList.addCard(new ForeignFighters()); //repeat?
		CardList.addCard(new ISIL());
		CardList.addCard(new JihadistVideos()); //repeat?
		CardList.addCard(new MartyrdomOperations()); //repeat?
		
		CardList.addCard(new MuslimBrotherhood());
		CardList.addCard(new Quagmire()); //repeat?
		CardList.addCard(new RegionalAlQaeda());		//repeat?
		CardList.addCard(new Snowden());
		CardList.addCard(new TalibanResurgent());
		CardList.addCard(new TrainingCamps());
		CardList.addCard(new Unconfirmed());
		CardList.addCard(new USAtrocities());
		CardList.addCard(new USConsulateAttacked());
		//Neutral
		CardList.addCard(new CriticalMiddle());
		
		CardList.addCard(new CrossBorderSupport());		//red
		CardList.addCard(new CyberWarfare());
		CardList.addCard(new DayOfRage());
		CardList.addCard(new EbolaScare());	//red
		CardList.addCard(new ErdoganEffect()); ///s
		CardList.addCard(new FridayOfAnger());
		CardList.addCard(new JVCopycat());
		CardList.addCard(new KinderGentler());
		CardList.addCard(new QudsForce());
		CardList.addCard(new SectarianViolence());
		
		CardList.addCard(new Smartphones());
		CardList.addCard(new Smartphones());
		CardList.addCard(new Smartphones());
		CardList.addCard(new ThreeCups());
		CardList.addCard(new AbuBakrAlBaghdadi());
		CardList.addCard(new AbuSayyafISIL()); //repeat?
		CardList.addCard(new Agitators()); //see 7.6
		CardList.addCard(new AlNusra());
		CardList.addCard(new AymanAlZawahiri()); //repeat from Zawahiri?
		CardList.addCard(new Daraa());
		
		CardList.addCard(new FlyPaper());
		CardList.addCard(new Hagel());
		CardList.addCard(new IranianElections());
		CardList.addCard(new JeSuisCharlie());
		CardList.addCard(new JihadiJohn());
		CardList.addCard(new OperationServal());
		CardList.addCard(new PopularSupport());
		CardList.addCard(new PopularSupport());
		CardList.addCard(new PrisonerExchange());
		CardList.addCard(new Sellout());
		
		CardList.addCard(new SiegeOfKobanigrad());
		CardList.addCard(new TradeEmbargo());
		CardList.addCard(new UNCeasefire());
		CardList.addCard(new FreeSyrianArmy());
		CardList.addCard(new Qadhafi());
		CardList.addCard(new OilPriceSpike());
		CardList.addCard(new OsamaBinLadin()); //repeat?
		CardList.addCard(new Revolution());
		CardList.addCard(new Truce());
		CardList.addCard(new USElection()); //repeat?
		
		//Forever War starts here 
		
		CardList.addCard(new AbdelFattahElSisi());
		CardList.addCard(new Avenger());
		CardList.addCard(new Backlash()); //repeat?
		CardList.addCard(new ForeignInternalDefense());
		CardList.addCard(new GreenMovement2());
		CardList.addCard(new HolidaySurprise());
		CardList.addCard(new NadiaMurad());
		CardList.addCard(new PatrioticArabDemocracies());
		CardList.addCard(new SaudiAirStrikes());
		CardList.addCard(new SpecialForces()); //repeat?
		
		CardList.addCard(new TrumpTweets());
		CardList.addCard(new TrumpTweets());
		CardList.addCard(new TrumpTweets());
		CardList.addCard(new USEmbassyJerusalem());
		CardList.addCard(new WesternArmsSales());
		CardList.addCard(new WhiteHelmets());
		CardList.addCard(new WomensRightsActivism());
		CardList.addCard(new SeventyFifthRanger());
		CardList.addCard(new ArabNATO());
		CardList.addCard(new ImranKhan());
		
		CardList.addCard(new IntelCommunity()); //repeat?
		CardList.addCard(new MOAB());
		CardList.addCard(new OperationTidalWaveII());
		CardList.addCard(new PersonalSecurityContractors());
		CardList.addCard(new PopularMobilizationForces());
		CardList.addCard(new PresidentialRealityShow());
		CardList.addCard(new ThirdOffset());
		CardList.addCard(new TrumpTrip());
		CardList.addCard(new AirAmerica());
		CardList.addCard(new DeepState());
		
		CardList.addCard(new ExpandedROE());
		CardList.addCard(new FireAndFury());
		CardList.addCard(new FullyResourcedCOIN());
		CardList.addCard(new GovtOfNationalAccord());
		CardList.addCard(new OperationInherentResolve());
		CardList.addCard(new PopulismEuroscepticism());
		CardList.addCard(new RegimeChangePolicy());
		CardList.addCard(new SiegeOfMosul());
		CardList.addCard(new SFABs());
		CardList.addCard(new SunniShiaRift());
		//Jihadist
		CardList.addCard(new DroneSwarms());
		CardList.addCard(new Executive13492());
		CardList.addCard(new LoneWolf());
		CardList.addCard(new ManchesterBombing());
		CardList.addCard(new MohamedMorsiSupporters());
		CardList.addCard(new PalestinianPeace());
		CardList.addCard(new SayyedHassanNasrallah());
		CardList.addCard(new SoldiersOfTheCaliphate());	
		CardList.addCard(new StraitOfHormuz());
		CardList.addCard(new UyghurNationalism());
		
		CardList.addCard(new VehicleRammingAttacks());
		CardList.addCard(new AmaqNewsAgency());
		CardList.addCard(new AttemptedCoup());
		CardList.addCard(new BarcelonaBombs()); //Sadr
		CardList.addCard(new BlackGold());
		CardList.addCard(new BotchedYemeniRaid());
		CardList.addCard(new EarlyExit());
		CardList.addCard(new FalseFlagAttacks());
		CardList.addCard(new ForeignFightersReturn()); //repeat??
		CardList.addCard(new GoingUnderground());
		
		CardList.addCard(new GreenOnBlue());
		CardList.addCard(new ImperialOverstretch());
		CardList.addCard(new IranianWithdrawal());
		CardList.addCard(new LooseChemicals());
		CardList.addCard(new PresidentialWhistleblower());
		CardList.addCard(new PublicDebate());
		CardList.addCard(new RussianSubterfuge());
		CardList.addCard(new BattleOfMarawi());
		CardList.addCard(new EasterBombings());
		CardList.addCard(new ForeverWar());
		
		CardList.addCard(new GazaBorderProtests());
		CardList.addCard(new HamaOffensive()); 
		CardList.addCard(new HayatTahirAlSham());		
		CardList.addCard(new JihadistAfricanSafari());
		CardList.addCard(new KhashoggiCrisis());
		CardList.addCard(new MartyrdomOperation()); //repeat????
		CardList.addCard(new QatariCrisis());
		CardList.addCard(new SouthChinaSeaCrisis());
		CardList.addCard(new TehranBeirutLandCorridor());
		CardList.addCard(new TribalLeadersWithdrawSupport());
		
		CardList.addCard(new UngovernedSpaces());
		//Neutrals
		CardList.addCard(new AmnestyInternational());
		CardList.addCard(new Blasphemy());
		CardList.addCard(new Brexit());	//red
		CardList.addCard(new DissentChannel()); ///s
		CardList.addCard(new FilibusterNuclear());
		CardList.addCard(new GazaAid());
		CardList.addCard(new HafizSaeedKhan());
		CardList.addCard(new HamzaBinLaden());
		CardList.addCard(new IRGC());
		
		CardList.addCard(new JASTA());
		CardList.addCard(new KhanShaykhun());
		CardList.addCard(new MbS());
		CardList.addCard(new Novichok());
		CardList.addCard(new RohingyaGenocide());
		CardList.addCard(new USNKSummit()); //repeat?
		CardList.addCard(new USBorderCrisis()); //see 7.6
		CardList.addCard(new AbuMuhammadAlShimali());
		CardList.addCard(new ErdoganDance()); //repeat from Zawahiri?
		CardList.addCard(new EUBolstersIranDeal());
		
		CardList.addCard(new GulenMovement());
		CardList.addCard(new GulmurodKhalimov());
		CardList.addCard(new JCPOA());
		CardList.addCard(new MediaManipulation());
		CardList.addCard(new OperationEuphratesShield());
		CardList.addCard(new PakistaniIntelligence());
		CardList.addCard(new SwitchingJerseys());
		CardList.addCard(new TravelBan());
		CardList.addCard(new TurkishCoup());
		CardList.addCard(new UNPeaceEnvoy());
		
		CardList.addCard(new APT());
		CardList.addCard(new AlBaghdadi()); //repeat?
		CardList.addCard(new BowlingGreen());
		CardList.addCard(new ElectionMeddling());
		CardList.addCard(new FakeNews());
		CardList.addCard(new OPECProductionCut());
		CardList.addCard(new PeaceDividend());
		CardList.addCard(new PoliticalIslamism());
		CardList.addCard(new QuickWinBadIntel());
		CardList.addCard(new USChinaTradeWar());
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

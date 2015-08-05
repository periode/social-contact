package polsys;

import java.util.ArrayList;

//email libraries
import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;
import java.io.*;
import java.nio.channels.SeekableByteChannel;
import java.util.*;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import megamu.mesh.Hull;
import megamu.mesh.MPolygon;
import megamu.mesh.Voronoi;
import beads.AudioContext;
import beads.Buffer;
import beads.Gain;
import beads.KillTrigger;
import beads.Noise;
import beads.Panner;
import beads.WavePlayer;
import beads.Envelope;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.MouseEvent;
import damkjer.ocd.Camera;

public class PolSys extends PApplet {

	public Cluster finalCluster;

	public static Agent[] agents;
	public static Agent[] agents_pre;
	public static Agent selectedAgent;
	public static Agent selectedAgent2;
	public static ArrayList<Agent> checkedAgents;
	public static ArrayList<Predator> predators;
	public static ArrayList<Connection> connections;
	public static ArrayList<Cluster> totalClusters;
	public static ArrayList<Resource> resources;
	public static ArrayList <Resource> resources2;
	
	Connection hoveredConnection;
	boolean canRemoveConnec;
	
	public static ArrayList<Agent> community;
	public static ArrayList<ConnectionLove> connectionsLove;
	public static ArrayList<ConnectionPower> connectionsPower;
	public static ArrayList<ConnectionFriendship> connectionsFriendship;
	
	public static ArrayList<Alliance> alliances;
	public static ArrayList<Trade> trades;
	public static ArrayList<War> wars;

	int numberOfPredators;
	int numberOfResources;
	
	public static float rectBorderX;
	public static float rectBorderY;
	public static float rectBorderW;
	public static float rectBorderH;

	public static Seasons seasons;
	public static int bgColBox;
	public static int newBgColBox;
	float bgColBoxLerpSpeed;
	float bgColBoxLerpInc;
	
	Camera cam;
	
	public static PFont mainFont;
	public static PFont subtitleFont;
	public static PFont headerFont;
	public static PFont textFont;
	public static PFont thoughtFont;
	public static PFont seasonsFont;
	
	public static int mainFontSize;
	public static int subtitleFontSize;
	public static int headerFontSize;
	public static int textFontSize;
	public static int thoughtFontSize;
	public static int seasonsFontSize;
	
	public static PFont ashburyHead;
	public static PFont callunaText;
	
	//-----------------------------------------------------------------AUDIO
	static AudioContext ac;
	static Gain masterGain;
	static Gain gMasterRhythm;
	static Gain gMasterSeasons;
	
	static Envelope eMasterRhythm;
	static Envelope eMasterSeasons;
	
	float rhythmTimer;
	float rhythmStartTimer;
	
	//------ Seasons

	float x;
	float y;
	float rad;
	static int connec;
	int minConnec;
	int maxConnec;
	int allowMovement;
	String allowMovementText;
	
	float xCam;
	float yCam;
	float zCam;
	float camSpeed;
	int xCamLeftBorder;
	int xCamRightBorder;
	int yCamTopBorder;
	int yCamBottomBorder;
	int zCamFurthest;
	int zCamClosest;

	static boolean canFormStableRelationships;
	static boolean canDisturb;
	static boolean canProtect;
	static boolean canRememberFallen;

	float violence;
	String violenceText;
	
	float resourceSeek;
	String resourceText;

	boolean startDeathSentence;

	float rectProceedAlpha;
	
	int goalInt;
	String goalText;
	
	boolean canShowConnections;
	boolean canShowResources;
	boolean canShowPredators;
	boolean drawFishConnec;
	boolean drawFishResources;
	boolean drawFishPredators;

	int totalConnections;
	int totalDeaths;
	
	boolean isHovering;
	
	Agent hoverAgent;
	float hoverTextAlpha;
	float hoverTextAlphaRate;
	boolean hovering;
	float hoverRand;
	boolean hoverCanGenerateRand;

	Agent draggedAgent;
	float dragForce;
	float dragCoeff;
	boolean canDragAgent;
	float draggedX;
	float draggedY;
	boolean isReleased;
	float dragLerpSpeed;
	
	float timer;
	float startTime;
	float startPhaseTimer;
	float agentsArriveTimer;
	float agentsArriveStartTimer;
	int arriveIndex;
	
	//VORONOI
	Voronoi voronoi;

	// booleans for start screen and game screen and end screen
	boolean intro;
	public static boolean startGame1;
	public static boolean startGame2;
	public static boolean startGame3;
	static boolean inGame1;
	static boolean inGame2;
	static boolean inGame3;
	boolean canEndGame1;
	boolean canEndGame2;
	boolean canEndGame3;
	boolean endGame1;
	boolean endGame2;
	boolean endGame3;
	boolean canDrawEndScreen;

	boolean selGenPred;
	boolean selStopAgent;
	boolean selRemoveConnec;
	boolean selFinalCluster;
	boolean showGenPredInfo;
	boolean showStopAgentInfo;
	boolean showRemoveConnectionInfo;
	boolean showSelFinalClusterInfo;
	
	float leftColX;
	float rightColX;
	float buttonBufferY;
	float bufferXstart1;
	float headerHeight;
	float goalButtonHeight;
	
	float firstRowY;
	float secondRowY;
	float thirdRowY;
	float fourthRowY;
	
	// ------------------------------------------------------------------------------------ INTRO
	float textIntroAlpha;
	int introStage;
	boolean readyToStart;
	String textIntro;
	
	PVector introPosA;
	PVector introPosB;
	PVector introPosC;
	float circleIntroAlpha;
	float lineIntroAlpha;
	float linesIntroAlpha;
	
	float othersIntroAlpha;
	PVector[] introPosO;
	int othersIntro;
	float lerpIntro;
	
	float rectIntroAlpha;
	float titleIntroAlpha;
	
	
	// ------------------------------------------------------------------------------------ FISH TANK
	public static ArrayList<Fish> fishes;
	float fishTankSize;
	static int fishNumber;
	PVector fallenPos;
	int colorPower;
	static int colorFriendship;
	int colorLove;
	boolean canShowPower;
	boolean canShowFriendship;
	boolean canShowLove;
	
	Fish fishPred;
	
	PVector fishResourcePos;
	int fishResourceSides;
	float[] fishResourceSidesVar;
	float fishResourceRad;
	
	
	boolean drawFishPower;
	boolean drawFishFriend;
	boolean drawFishLove;
	boolean drawChildren;
	boolean drawCulture;
	
	int citizenFishNum;
	PVector[] citizenFishPos;
	float[] citizenFishRad;
	int[] citizenFishWeight;
	
	int citizenFishNum2;
	PVector[] citizenFishPos2;
	float[] citizenFishRad2;
	int[] citizenFishWeight2;
	
	int fishHullCol1;
	int fishHullCol2;
	
	boolean canShowTrade;
	boolean canShowWar;
	boolean canShowAlliances;
	boolean canShowRuin;
	boolean canShowCulture;
	
	int fishVesselsNum;
	PVector[] fishVesselsPos;
	float fishVesselsSize;
	float[] fishVesselsLerp;
	float fishLerpAmount;

	// Buttons
	
	// ---------------------------------- 1
	float buttonGoalRight;
	float buttonGoalLeft;
	float buttonGoalHeight;
	
	Button duty_up;
	Button duty_down;

	Button violence_up;
	Button violence_down;
	
	Button resource_up;
	Button resource_down;

	Button right1;
	Button right2;
	Button right3;
	Button right4;

	Button pursuitLeft;
	Button pursuitRight;

	Button startButton;
	
	float proceedX;
	float proceedY;
	float proceedW;
	float proceedH;

	PFont junctionFont;

	int endAlpha;

	int genPred;
	boolean genPred1;
	boolean genPred2;
	boolean genPred3;
	Predator pred1;
	Predator pred2;
	Predator pred3;
	int stopAgent;
	int removeConnec;
	
	float showAlpha;
	
	// ----------------------------------------------- POST GAME
	PVector portraitPos;
	float portraitRad;
	float portraitPulse;
	float portraitPulseInc;
	int portraitCol;
	
	boolean postGame;
	
	String storyTitle1;
	String storyTitle2;
	String storyTitle3;
	
	int textAlpha;
	
	public static Agent[] finalAgents;
	
	String[] finalIndividualStories;
	String endGame1_text;
	
	String[] history;
	
	String[] goal0;
	String[] goal1;
	String[] goal2;
	String[] goal3;
	
	String[] formStabRel;
	String[] canPro;
	String[] canDist;
	String[] canFight;
	String[] canRememberDead;
	String[] canConnec;
	
	String[] totCo;
	String[] connecMax;
	String[] coToDead;
	String[] isKill;
	String[] coToKill;
	String[] isHun;
	String[] timeAlone;
	String[] hasBeenRej;
	String[] hasRej;
	String[] hasProt;
	String[] isCoToHun;
	String[] hasDist;
	String[] isTamer;
	
	// -- opposites
	String[] formStabRelX;
	String[] canProX;
	String[] canDistX;
	String[] canFightX;
	String[] canRememberDeadX;
	String[] canConnecX;
	
	String[] totCoX;
	String[] connecMaxX;
	String[] coToDeadX;
	String[] isKillX;
	String[] coToKillX;
	String[] isHunX;
	String[] timeAloneX;
	String[] hasBeenRejX;
	String[] hasRejX;
	String[] hasProtX;
	String[] isCoToHunX;
	String[] hasDistX;
	String[] isTamerX;

	int fsrRND;
	int cDRND;
	int cPRND;
	int totCoRND;
	int coMaxRND;
	int isTamRND;
	int coTDRND;
	int isKRND;
	int cTKRND;
	int isHRND;
	int taRND;
	int hasBRRND;
	int hasRRND;
	int hasPrRND;
	int isCTHRND;
	int hasDisRND;
	
	int fsrRNDX;
	int cDRNDX;
	int cPRNDX;
	int totCoRNDX;
	int coMaxRNDX;
	int isTamRNDX;
	int coTDRNDX;
	int isKRNDX;
	int cTKRNDX;
	int isHRNDX;
	int taRNDX;
	int hasBRRNDX;
	int hasRRNDX;
	int hasPrRNDX;
	int isCTHRNDX;
	int hasDisRNDX;
	
	int cfRND;
	int cfRNDX;
	int crdRND;
	int crdRNDX;
	int ccRND;
	int ccRNDX;
	
	int goal0RND;
	int goal1RND;
	int goal2RND;
	int goal3RND;
	
	static boolean randGen3;
	
	// ----------------------------------------- COMMUNAL HISTORY
	public boolean communalHistory;
	
	static boolean communalHistoryRndGenerator;
	
	float t;
	
	// ---------------------------------------------------------------------------------------------------- 2
	Button startButton2;
	
	Button powerForceUpButton;
	Button powerForceDownButton;
	Button friendshipForceUpButton;
	Button friendshipForceDownButton;
	Button loveForceUpButton;
	Button loveForceDownButton;
	
	Button ageMajorityUpButton;
	Button ageMajorityDownButton;
	Button numberOfChildrenUpButton;
	Button numberOfChildrenDownButton;
	Button independenceOfChildrenUpButton;
	Button independenceOfChildrenDownButton;
	
	Button cultureButtonRight;
	Button cultureButtonLeft;
	
	float mapMin;
	float mapMax;
	
	float powerForce;
	float friendshipForce;
	float loveForce;
	
	float ageModifier;
	int numberOfChildren;
	float independenceOfChildren;
	
	PVector strata1Anchor1;
	PVector strata1Control1;
	PVector strata1Anchor2;
	PVector strata1Control2;
	
	PVector strata2Anchor1;
	PVector strata2Control1;
	PVector strata2Anchor2;
	PVector strata2Control2;
	
	
	int strataCol;
	int strataAlpha;
	
	float spatialCultureIncrement;
	
	boolean selRevolt;
	boolean selOppression;
	boolean forceCulturalSim;
	boolean forceCulturalDiff;
	boolean finalStructure;
	boolean showInduceRevoltInfo;
	boolean showInduceOppressionInfo;
	boolean showForceCultDiffInfo;
	boolean showForceCultSimInfo;
	boolean showFinalStructureInfo;
	
	int induceRevolt;
	int induceOppression;
	int induceCulturalSim;
	int induceCulturalDiff;
	
	float revoltMultiplier;
	float oppressionMultiplier;
	
	int cultureOrigin;
	
	PVector[] finalStructurePos;
	
	int bezierNum;
	PVector[] startPointBezierFish;
	PVector[] anchorPointABezierFish;
	PVector[] anchorPointBBezierFish;
	PVector[] endPointBezierFish;
	
	// -------------------- 2 END
	String endGame2_text;
	
	String[] spaceCultureText;
	String[] socialCultureText;
	String[] inLoveText;
	String[] friendships;
	String[] hasForcedSimText;
	String[] hasForcedDiffText;
	String[] hasRevoltedText;
	String[] hasOppressedText;
	String[] numberOfChildrenText;
	String[] oppressionInducedText;
	String[] oppressionReceivedText;
	String[] generationText;
	String[] culturalHomogeneityText;
	
	boolean randomGen2;
	
	
	// ---------------------------------------------------------------------------------------------------- 3
	Nation nation;
	
	int numberOfOthers;
	public static ArrayList<Nation> others;
	ArrayList<PVector> othersPositions;
	
	int randomStatement;
	
	float allianceModifier;
	float tradeModifier;
	float warModifier;
	int nationGoal;
	float victoryBehaviour;
	float tradeLimit;
	float allianceTrust;
	float cultureExternal;
	
	Button allianceModifierUp;
	Button allianceModifierDown;
	Button tradeModifierUp;
	Button tradeModifierDown;
	Button warModifierUp;
	Button warModifierDown;
	Button victoryBehaviourUp;
	Button victoryBehaviourDown;
	Button tradeLimitUp;
	Button tradeLimitDown;
	Button allianceTrustUp;
	Button allianceTrustDown;
	Button cultureExternalUp;
	Button cultureExternalDown;
	
	Button nationGoalButtonRight;
	Button nationGoalButtonLeft;
	
	
	// ---- THOUGHT STRINGS;
	
	static String[] tsOld;
	static String[] tsKiller;
	static String[] tsTamer;
	static String[] tsCoToKill;
	static String[] tsCoToDead;
	static String[] tsAlone;
	static String[] tsNotAlone;
	static String[] tsHasProtected;
	static String[] tsHasRejected;
	static String[] tsHasBeenRejected;
	static String[] tsWealthy;
	static String[] tsLoneKiller;
	static String[] tsBeast;
	
	static String[] tsExertPower;
	static String[] tsFewFriends;
	static String[] tsFullFriends;
	static String[] tsHasChildren;
	static String[] tsInLove;
	static String[] tsIsOfAge;
	static String[] tsIsSettled;
	static String[] tsIsSterile;
	static String[] tsNoFriends;
	static String[] tsNotSettled;
	static String[] tsOppressed;
	static String[] tsSpaceCulture;
	
	
	// ---------------------------------------------------------------------------------------------------- 3 - INTERFACE
	Button startButton3;
	PVector mouse;
	
	boolean selWealthInc;
	boolean selWealthDec;
	boolean selWarInduce;
	boolean selAllianceBreak;
	boolean selFinalProceed;
	boolean showWealthIncInfo;
	boolean showWealthDecInfo;
	boolean showWarInduceInfo;
	boolean showAllianceBreakInfo;
	boolean showFinalProceedInfo;
	
	float wealthIncrease;
	float wealthDecrease;

	// ---------------------------------------------------------------------------------------------------- 3 - END
	boolean randomGen3;
	String endGame3_text;
	
	String[] st_nationGoalHonor;
	String[] st_nationGoalSurvival;
	String[] st_nationGoalWealth;
	String[] st_wealth;
	String[] st_war;
	String[] st_tradePartners;
	String[] st_promiscuity;
	String[] st_alliances;
	String[] st_similarCultureAlone;
	String[] st_similarCultureAllies;
	String[] st_movement;
	
	static String[] tsAtPeace;
	static String[] tsAtPeaceAgain;
	static String[] tsAtWar;
	static String[] tsAverageCulture;
	static String[] tsNotAverageCulture;
	static String[] tsHasAllies;
	static String[] tsHasNeighbors;
	static String[] tsHasTradePartners;
	static String[] tsNoTradePartners;
	static String[] tsNationDisappeared;
	static String[] tsNoAllies;
	static String[] tsNoNeighbors;
	static String[] tsPoor;
	static String[] tsWealthyNation;
	static String[] tsWasAtWar;
	

	// ---------------------------------------------------------------------------------------------------- EMAIL SCREEN
	boolean emailScreen;
	boolean finalScreen;
	Button sendButton;
	String typedText;
	boolean typedEmail;
	int blinkingType;
	
	String emailAddress;
	String emailStory;
	
	// --------------------------------------------------------------------------------------------------- THANK YOU NOTE
	float finalAlphaSmall;
	float finalAlphaFull;
	float finalTimerSmall;
	float finalTimerFull;
	
	float rectCol;
	
	float alphaFade;
	float fadeRate;
	boolean fading;
	
	public void setup() {
		//randomSeed(5);// FOR DEBUG
		size(1600, 900);
		System.setProperty("java.net.preferIPv4Stack" , "true");
		smooth();
		cursor(CROSS);

		frameRate(30);
		
		mouse = new PVector();

		bgColBox = color(255);
		newBgColBox = color(255);
		bgColBoxLerpSpeed = 0.0f;
		bgColBoxLerpInc = 0.025f;
		
		rectBorderX = width*0.00375f;
		rectBorderY = height*0.00333f;
		rectBorderW = width*0.9925f;
		rectBorderH = height*0.95f;
		
		intro = false;
		startGame1 = true;
		startGame2 = false;
		startGame3 = false;
		inGame1 = false;
		inGame2 = false;
		inGame3 = false;
		endGame1 = false;
		endGame2 = false;
		endGame3 = false;
		canEndGame1 = false;
		canEndGame2 = false;
		canEndGame3 = false;	
		emailScreen = false;
		finalScreen = false;
		
		mainFont = loadFont("data/fonts/Ashbury-Medium-60.vlw");
		subtitleFont = loadFont("data/fonts/Ashbury-Light-40.vlw");
		headerFont = loadFont("data/fonts/EdwardPro-Normal-36.vlw");
		textFont = loadFont("data/fonts/EdwardPro-ExtraLight-24.vlw");
		thoughtFont = loadFont("data/fonts/EdwardPro-ExtraLight-16.vlw");
		seasonsFont = loadFont("data/fonts/Ashbury-Light-100.vlw");
		
		mainFontSize = 60;
		subtitleFontSize = 36;
		headerFontSize = 36;
		textFontSize = 24;
		thoughtFontSize = 16;
		seasonsFontSize = 100;
		
		
		leftColX = width*0.125f;
		rightColX = width*0.875f;
		buttonBufferY = height*0.044f;
		bufferXstart1 = width*0.0425f;
		headerHeight = height*0.175f;
		
		firstRowY= height*0.3f;
		secondRowY = height*0.5f;
		thirdRowY = height*0.7f;
		fourthRowY = height*0.75f;
		goalButtonHeight = 0.8f;

		// -----------------------------------------------------------------------------------------------------------------------------------AUDIO
		
		ac = new AudioContext();
		masterGain = new Gain(ac, 2, 0.1f);
		eMasterRhythm = new Envelope(ac, 1.0f);
		gMasterRhythm = new Gain(ac, 1, eMasterRhythm);
		eMasterSeasons = new Envelope(ac, 0.1f);
		gMasterSeasons = new Gain(ac, 1, eMasterSeasons);
		
		ac.out.addInput(gMasterRhythm);
		ac.out.addInput(gMasterSeasons);
		
		ac.out.addInput(masterGain);
		
		rhythmTimer = 1250.0f; // 90bpm
		rhythmStartTimer = millis();
		
		
		// ---------Seasons
		seasons = null;

		// ------------ camera
		xCam = 0;
		yCam = 0;
		zCam = 0;
		camSpeed = 5.0f;
		
		xCamLeftBorder = -450;
		xCamRightBorder = 450;
		yCamTopBorder = -250;
		yCamBottomBorder = 250;;
		zCamFurthest = 400;
		zCamClosest = -200;

		connec = 3;
		minConnec = 3;
		maxConnec = 7;
		violence = 0.5f;
		violenceText = "";
		
		resourceSeek = 1.0f;
		resourceText = "";

		endAlpha = 1;

		startTime = millis();
		timer = 500;
		startPhaseTimer = 20000; // start phase is 5 seconds before checking if
									// we can end the simulation
		
		agentsArriveTimer = 1000.0f;
		agentsArriveStartTimer = 0.0f;
		arriveIndex = 0;
		totalDeaths = 0;
		
		isHovering = false;
		
		hoverAgent = null;
		hoverTextAlpha = 0;
		hoverTextAlphaRate = 3.0f;
		hovering = false;
		hoverRand = random(1);
		hoverCanGenerateRand = true;
		
		draggedAgent = null;
		dragForce = 0.5f;
		dragCoeff = 2.0f;
		canDragAgent = true;
		isReleased = false;
		dragLerpSpeed = 0.0f;
		
		startDeathSentence = false;

		allowMovement = 0;
		allowMovementText = "";

		canFormStableRelationships = false;
		canDisturb = false;
		canProtect = false;
		canRememberFallen = false;

		goalInt = 0;
		goalText = "";
		
		rectProceedAlpha = 0.0f;
		
		canShowConnections = false;
		canShowResources = false;
		canShowPredators = false;
		drawFishConnec = false;
		drawFishResources = false;
		drawFishPredators = false;

		genPred = 4;
		genPred1 = false;
		stopAgent = 4;
		removeConnec = 9;

		selGenPred = false;
		selRemoveConnec = false;
		selStopAgent = false;
		selFinalCluster = false;
		showGenPredInfo = false;
		showStopAgentInfo = false;
		showRemoveConnectionInfo = false;
		showSelFinalClusterInfo = false;

		agents = new Agent[100];
		for(int i = 0; i < agents.length; i++){
			agents[i] = new Agent();
		}
		
		agents_pre = new Agent[40];
		selectedAgent = null;
		selectedAgent2 = null;
		checkedAgents = new ArrayList<Agent>();
		connections = new ArrayList<Connection>();
		numberOfPredators = 5;
		predators = new ArrayList<Predator>();
		resources = new ArrayList<Resource>();
		numberOfResources = 15;
		
		for(int i = 0; i < numberOfResources; i++){
			resources.add(new Resource(random(width*0.1f, width*0.9f), random(height*0.1f, height*0.8f), (int)random(40, 100), this));
		}
		
		resources2 = new ArrayList<Resource>();
		
		hoveredConnection = null;
		canRemoveConnec = false;
		
		community = new ArrayList<Agent>();
		connectionsLove = new ArrayList<ConnectionLove>();
		connectionsPower = new ArrayList<ConnectionPower>();
		connectionsFriendship = new ArrayList<ConnectionFriendship>();

		totalClusters = new ArrayList<Cluster>();
		
		// ------------------------------------------------------------------------------------ INTRO
		textIntroAlpha = 0;
		introStage = 0;
		readyToStart = false;
		textIntro = "";
		introPosA = new PVector(width*0.2f, height/2);
		introPosB = new PVector(width*0.8f, height/2);
		introPosC = new PVector(width*0.5f, height*0.9f);
		
		circleIntroAlpha = 0;
		lineIntroAlpha = 0;
		linesIntroAlpha = 0;
		
		othersIntro = 120;
		othersIntroAlpha = 0;
		introPosO = new PVector[othersIntro];
		lerpIntro = 0;
		
		rectIntroAlpha = 0;
		titleIntroAlpha = 0;
		
		for(int i = 0; i < introPosO.length; i++){
			if(i < 30){ //west
				introPosO[i] = new PVector(random(-300, 0), random(0, height));
			}else if(i < 60){ //north
				introPosO[i] = new PVector(random(30, width-30), random(-500, 0));
			}else if(i < 90){ //east
				introPosO[i] = new PVector(random(width+0, width+300), random(0, height));
			}else{ //south
				introPosO[i] = new PVector(random(0, width), random(height+0, height+300));
			}
		}
		
		tsOld = loadStrings("data/text/thought/inGame1/old.txt");
		tsKiller = loadStrings("data/text/thought/inGame1/isKiller.txt");
		tsTamer = loadStrings("data/text/thought/inGame1/isTamer.txt");
		tsCoToKill = loadStrings("data/text/thought/inGame1/old.txt");
		tsCoToDead = loadStrings("data/text/thought/inGame1/old.txt");
		tsAlone = loadStrings("data/text/thought/inGame1/alone.txt");
		tsNotAlone = loadStrings("data/text/thought/inGame1/notAlone.txt");
		tsHasProtected = loadStrings("data/text/thought/inGame1/hasProtected.txt");
		tsHasRejected = loadStrings("data/text/thought/inGame1/hasRejected.txt");
		tsHasBeenRejected = loadStrings("data/text/thought/inGame1/hasBeenRejected.txt");
		tsWealthy = loadStrings("data/text/thought/inGame1/wealthy.txt");
		tsLoneKiller = loadStrings("data/text/thought/inGame1/loneKiller.txt");
		tsBeast = loadStrings("data/text/thought/inGame1/beast.txt");
		
		tsExertPower = loadStrings("data/text/thought/inGame2/exertPower.txt");
		tsFewFriends = loadStrings("data/text/thought/inGame2/fewFriends.txt");
		tsFullFriends = loadStrings("data/text/thought/inGame2/fullFriends.txt");
		tsHasChildren = loadStrings("data/text/thought/inGame2/hasChildren.txt");
		tsInLove = loadStrings("data/text/thought/inGame2/inLove.txt");
		tsIsOfAge = loadStrings("data/text/thought/inGame2/isOfAge.txt");
		tsIsSettled = loadStrings("data/text/thought/inGame2/isSettled.txt");
		tsIsSterile = loadStrings("data/text/thought/inGame2/isSterile.txt");
		tsNoFriends = loadStrings("data/text/thought/inGame2/noFriends.txt");
		tsNotSettled = loadStrings("data/text/thought/inGame2/notSettled.txt");
		tsOppressed = loadStrings("data/text/thought/inGame2/oppressed.txt");
		tsSpaceCulture = loadStrings("data/text/thought/inGame2/spaceCulture.txt");
		
		
		// ------------------------------------------------------------------------------------ FISHTANK
		buttonGoalLeft = width*0.38f;
		buttonGoalRight = width*0.62f;
		buttonGoalHeight = height*0.7725f;
		
		fishResourceSides = (int)random(5, 10);
		fishResourceRad = 10;
		fishResourcePos = new PVector(random(width*0.45f, width*0.55f), random(height*0.45f, height*0.55f));
		fishResourceSidesVar = new float[fishResourceSides];
		for(int i = 0; i < fishResourceSides; i++){
			fishResourceSidesVar[i] = random(10, 50);
		}
		
		fishes = new ArrayList<Fish>();
		fishTankSize = height/3;
		fishNumber = 5;
		for(int i = 0; i < fishNumber; i++){
			fishes.add(new Fish(random(width/2 - fishTankSize/2 + rad*1.5f, width/2 + fishTankSize/2 - rad*1.5f), random(height/2 - fishTankSize/2 + rad*1.5f, height/2 + fishTankSize/2 - rad*1.5f), connec, 25, 250, this));
		}
		fallenPos = new PVector(width/2 - height/12, height/2 + height/12);
		
		fishPred = new Fish(width*0.5f, height*0.5f, this);
		
		colorPower = color(10);
		colorFriendship = color(0, 100, 0);
		colorLove = color(200, 50, 50);
		canShowPower = false;
		canShowFriendship = false;
		canShowLove = false;
		
		// fish - 2
		drawFishPower = false;
		drawFishFriend = false;
		drawFishLove = false;
		drawChildren = false;
		drawCulture = false;
		
		
		// fish - 3
		citizenFishNum = (int)random(15, 30);
		citizenFishPos = new PVector[citizenFishNum];
		citizenFishRad = new float[citizenFishNum];
		citizenFishWeight = new int[citizenFishNum];
		for(int i = 0; i < citizenFishNum; i++) {
			citizenFishPos[i] = new PVector(random(width*0.52f, width*0.6f), random(height*0.52f, height*0.6f));
			citizenFishPos[i].x = constrain(citizenFishPos[i].x, width*0.5f - height/6,  width*0.5f + height/6);
			citizenFishPos[i].y = constrain(citizenFishPos[i].y, height*0.5f - height/6,  height*0.5f + height/6);
			citizenFishWeight[i] = (int)random(1, 4);
			citizenFishRad[i] = random(5, 10);
		}
		
		citizenFishNum2 = (int)random(15, 30);
		citizenFishPos2 = new PVector[citizenFishNum2];
		citizenFishRad2 = new float[citizenFishNum2];
		citizenFishWeight2 = new int[citizenFishNum2];
		for(int i = 0; i < citizenFishNum2; i++) {
			citizenFishPos2[i] = new PVector(random(width*0.4f, width*0.48f), random(height*0.4f, height*0.48f));
			citizenFishPos2[i].x = constrain(citizenFishPos2[i].x, width*0.5f - height/6,  width*0.5f + height/6);
			citizenFishPos2[i].y = constrain(citizenFishPos2[i].y, height*0.5f - height/6,  height*0.5f + height/6);
			citizenFishWeight2[i] = (int)random(1, 4);
			citizenFishRad2[i] = random(5, 10);
		}
		
		fishHullCol1 = 0;
		fishHullCol2 = 0;
		
		canShowTrade = false;
		canShowWar = false;
		canShowAlliances = false;
		canShowRuin = false;
		canShowCulture = false;
		
		fishVesselsNum = 5;
		fishVesselsPos = new PVector[fishVesselsNum];
		fishVesselsLerp = new float[fishVesselsNum];
		fishVesselsSize = 10;
		fishLerpAmount = 0.005f;
		
		for(int i = 0; i < fishVesselsNum; i++){
			fishVesselsLerp[i] = i*0.2f;
			fishVesselsPos[i] = new PVector(width*0.5f, height*0.5f);
		}
		
		storyTitle1 = "my life";
		storyTitle2 = "our lives";
		storyTitle3 = "their lives";
		
		//  BUTTONS
		
		// ------------------------------------------------------------ 1

		duty_up = new Button(rightColX, firstRowY-buttonBufferY, 20, 1, this);
		duty_down = new Button(rightColX, firstRowY+buttonBufferY, 20, 0, this);

		violence_up = new Button(rightColX, secondRowY-buttonBufferY, 20, 1, this);
		violence_down = new Button(rightColX, secondRowY+buttonBufferY, 20, 0, this);
		
		resource_up = new Button(rightColX, thirdRowY-buttonBufferY, 20, 1, this);
		resource_down = new Button(rightColX, thirdRowY+buttonBufferY, 20, 0, this);

		right1 = new Button(leftColX*0.93f-bufferXstart1, height*0.297f, 20, 5, this);
		right2 = new Button(leftColX*0.93f-bufferXstart1, height*0.427f, 20, 5, this);
		right3 = new Button(leftColX*0.93f-bufferXstart1, height*0.557f, 20, 5, this);
		right4 = new Button(leftColX*0.93f-bufferXstart1, height*0.687f, 20, 5, this);

		pursuitLeft = new Button(buttonGoalLeft, buttonGoalHeight, 20, 3, this);
		pursuitRight = new Button(buttonGoalRight, buttonGoalHeight, 20, 4, this);

		startButton = new Button(width*0.5f, height*0.9f, 60, 2, this);
		
		proceedX = width*0.85f;
		proceedY = height*0.91f;
		proceedW = width*0.1f;
		proceedH = height*0.06f;
		
		
		// ------------------------------------------------------------ HISTORY
		portraitPos = new PVector(width*0.5f, height*0.75f);
		portraitRad = width*0.1f;
		portraitPulse = random(10);
		portraitPulseInc = 0.035f;
		portraitCol = color(20);
		
		endGame1_text = "";
		
		textAlpha = 0;
		history = loadStrings("data/text/endGame1/history.txt");
		
		goal0 = loadStrings("data/text/endGame1/goal0.txt");
		goal1 = loadStrings("data/text/endGame1/goal1.txt");
		goal2 = loadStrings("data/text/endGame1/goal2.txt");
		goal3 = loadStrings("data/text/endGame1/goal3.txt");
		
		formStabRel = loadStrings("data/text/endGame1/formStabRel.txt");
		canPro = loadStrings("data/text/endGame1/canPro.txt");
		canDist = loadStrings("data/text/endGame1/canDist.txt");
		canRememberDead = loadStrings("data/text/endGame1/canRememberDead.txt");
		
		canFight = loadStrings("data/text/endGame1/canFight.txt");
		canConnec = loadStrings("data/text/endGame1/canConnec.txt");
		
		totCo =  loadStrings("data/text/endGame1/totCo.txt");
		connecMax  = loadStrings("data/text/endGame1/connecMax.txt");
		isTamer  = loadStrings("data/text/endGame1/isTamer.txt");
		coToDead = loadStrings("data/text/endGame1/coToDead.txt");
		isKill = loadStrings("data/text/endGame1/isKill.txt");
		coToKill = loadStrings("data/text/endGame1/coToKill.txt");
		isHun = loadStrings("data/text/endGame1/isHun.txt");
		timeAlone = loadStrings("data/text/endGame1/timeAlone.txt");
		hasBeenRej = loadStrings("data/text/endGame1/hasBeenRej.txt");
		hasRej = loadStrings("data/text/endGame1/hasRej.txt");
		hasProt = loadStrings("data/text/endGame1/hasProt.txt");
		isCoToHun = loadStrings("data/text/endGame1/isCoToHun.txt");
		hasDist = loadStrings("data/text/endGame1/hasDist.txt");
		
		//opposites
		formStabRelX = loadStrings("data/text/endGame1/formStabRelX.txt");
		
		canProX = loadStrings("data/text/endGame1/canProX.txt");
		canDistX = loadStrings("data/text/endGame1/canDistX.txt");
		canRememberDeadX = loadStrings("data/text/endGame1/canRememberDeadX.txt");
		
		canFightX = loadStrings("data/text/endGame1/canFightX.txt");
		canConnecX = loadStrings("data/text/endGame1/canConnecX.txt");
		
		totCoX =  loadStrings("data/text/endGame1/totCoX.txt");
		connecMaxX  = loadStrings("data/text/endGame1/connecMaxX.txt");
		isTamerX  = loadStrings("data/text/endGame1/isTamerX.txt");
		coToDeadX = loadStrings("data/text/endGame1/coToDeadX.txt");
		isKillX = loadStrings("data/text/endGame1/isKillX.txt");
		coToKillX = loadStrings("data/text/endGame1/coToKillX.txt");
		isHunX = loadStrings("data/text/endGame1/isHunX.txt");
		timeAloneX = loadStrings("data/text/endGame1/timeAloneX.txt");
		hasBeenRejX = loadStrings("data/text/endGame1/hasBeenRejX.txt");
		hasRejX = loadStrings("data/text/endGame1/hasRejX.txt");
		hasProtX = loadStrings("data/text/endGame1/hasProtX.txt");
		isCoToHunX = loadStrings("data/text/endGame1/isCoToHunX.txt");
		hasDistX = loadStrings("data/text/endGame1/hasDistX.txt");	
		
		randGen3 = true;
		
		//----------------- COMMUNAL HISTORY
		communalHistory = false;
		
		communalHistoryRndGenerator = true;
		
		
		t = 0;
		
		// --------------------------------------------------------------------------------------------------- 2
		powerForce = 5.0f;
		friendshipForce = 5.0f;
		loveForce = 50.0f;
		ageModifier = 0.25f;
		numberOfChildren = 3;
		independenceOfChildren = 1.0f;
		
		startButton2 = new Button(width*0.5f, height*0.9f, 60, 2, this);
		
		powerForceUpButton = new Button(leftColX, firstRowY-buttonBufferY, 20, 1, this);
		powerForceDownButton = new Button(leftColX, firstRowY+buttonBufferY, 20, 0, this);
		
		friendshipForceUpButton = new Button(leftColX, secondRowY-buttonBufferY, 20, 1, this);
		friendshipForceDownButton = new Button(leftColX, secondRowY+buttonBufferY, 20, 0, this);
		
		loveForceUpButton = new Button(leftColX, thirdRowY-buttonBufferY, 20, 1, this);
		loveForceDownButton = new Button(leftColX, thirdRowY+buttonBufferY, 20, 0, this);
		
		ageMajorityUpButton = new Button(rightColX, firstRowY-buttonBufferY, 20, 1, this);
		ageMajorityDownButton = new Button(rightColX, firstRowY+buttonBufferY, 20, 0, this);
		
		numberOfChildrenUpButton = new Button(rightColX, secondRowY-buttonBufferY, 20, 1, this);
		numberOfChildrenDownButton = new Button(rightColX, secondRowY+buttonBufferY, 20, 0, this);
		
		independenceOfChildrenUpButton = new Button(rightColX, thirdRowY-buttonBufferY, 20, 1, this);
		independenceOfChildrenDownButton = new Button(rightColX, thirdRowY+buttonBufferY, 20, 0, this);
		
		cultureButtonRight = new Button(buttonGoalLeft, buttonGoalHeight, 20, 3, this);
		cultureButtonLeft = new Button(buttonGoalRight, buttonGoalHeight, 20, 4, this);
		
		strata1Anchor1 = new PVector(3, height/3);
		strata1Control1 = new PVector(width*0.25f, height/3);
		strata1Anchor2 = new PVector(width*0.75f, height/3);
		strata1Control2 = new PVector(width-3, height/3);
		
		strata2Anchor1 = new PVector(3, (height/3)*2);
		strata2Control1 = new PVector(width*0.25f, (height/3)*2);
		strata2Anchor2 = new PVector(width*0.75f, (height/3)*2);
		strata2Control2 = new PVector(width-3, (height/3)*2);
		
		strataCol = color(0);
		strataAlpha = 50;
		
		spatialCultureIncrement = 0.01f;
		
		selRevolt = false;
		selOppression = false;
		forceCulturalSim = false;
		forceCulturalDiff = false;
		finalStructure = false;
		showInduceRevoltInfo = false;
		showInduceOppressionInfo = false;
		showForceCultDiffInfo = false;
		showForceCultSimInfo = false;
		showFinalStructureInfo = false;
		
		induceRevolt = 6;
		induceOppression = 6;
		induceCulturalSim = 6;
		induceCulturalDiff = 6;
		
		revoltMultiplier = 20.0f;
		oppressionMultiplier = 20.0f;
		
		cultureOrigin = 0;
		
		bezierNum = 15;
		startPointBezierFish = new PVector[bezierNum];
		anchorPointABezierFish = new PVector[bezierNum];
		anchorPointBBezierFish = new PVector[bezierNum];
		endPointBezierFish = new PVector[bezierNum];
		
		for(int i = 0; i < bezierNum; i++){
			//line(, (width/2+fishTankSize/2), (height/2-fishTankSize/2)+(i*20));
			startPointBezierFish[i] = new PVector((width*0.5f-fishTankSize*0.5f), (height*0.5f-fishTankSize*0.5f)+(i*20));
			anchorPointABezierFish[i] = new PVector((width*0.5f-fishTankSize*0.25f), (height*0.5f-fishTankSize*0.5f)+(i*20));
			anchorPointBBezierFish[i] = new PVector((width*0.5f+fishTankSize*0.25f), (height*0.5f-fishTankSize*0.5f)+(i*20));
			endPointBezierFish[i] = new PVector((width*0.5f+fishTankSize*0.5f), (height*0.5f-fishTankSize*0.5f)+(i*20));
		}
		
		
		 // --------------- END 2 TEXT
		endGame2_text = "";
		
		spaceCultureText = loadStrings("data/text/endGame2/definedBySpace.txt");
		socialCultureText = loadStrings("data/text/endGame2/definedByOthers.txt");
		inLoveText = loadStrings("data/text/endGame2/timeInLove.txt");
		friendships = loadStrings("data/text/endGame2/friends.txt");
		hasForcedSimText = loadStrings("data/text/endGame2/hasForcedSim.txt");
		hasForcedDiffText = loadStrings("data/text/endGame2/hasForcedDiff.txt");
		hasRevoltedText = loadStrings("data/text/endGame2/hasRevolted.txt");
		hasOppressedText = loadStrings("data/text/endGame2/hasOppressed.txt");
		numberOfChildrenText = loadStrings("data/text/endGame2/numberOfChildren.txt");
		oppressionInducedText = loadStrings("data/text/endGame2/oppressionInduced.txt");
		oppressionReceivedText = loadStrings("data/text/endGame2/oppressionReceived.txt");
		generationText = loadStrings("data/text/endGame2/generation.txt");
		culturalHomogeneityText = loadStrings("data/text/endGame2/culturalHomogeneity.txt");
		
		randomGen2 = true;
		
		// ------------------------------------------------------------------------------- 3
		nation = null;
		others = new ArrayList<Nation>();
		numberOfOthers = 5;
		othersPositions = new ArrayList<PVector>();
		othersPositions.add(new PVector(width*0.1f, height*0.15f+random(-height*0.05f, height*0.05f)));
		othersPositions.add(new PVector(width*0.35f, height*0.15f+random(-height*0.05f, height*0.05f)));
		othersPositions.add(new PVector(width*0.7f, height*0.15f+random(-height*0.05f, height*0.05f)));
		othersPositions.add(new PVector(width*0.9f, height*0.15f+random(-height*0.05f, height*0.05f)));

		othersPositions.add(new PVector(width*0.1f, height*0.75f+random(-height*0.05f, height*0.05f)));
		othersPositions.add(new PVector(width*0.4f, height*0.75f+random(-height*0.05f, height*0.05f)));
		othersPositions.add(new PVector(width*0.7f, height*0.75f+random(-height*0.05f, height*0.05f)));
		othersPositions.add(new PVector(width*0.9f, height*0.75f+random(-height*0.05f, height*0.05f)));
		
		othersPositions.add(new PVector(width*0.15f+random(-width*0.05f, width*0.05f), height*0.3f));
		othersPositions.add(new PVector(width*0.15f, height*0.5f));
		
		othersPositions.add(new PVector(width*0.85f+random(-width*0.05f, width*0.05f), height*0.3f));
		othersPositions.add(new PVector(width*0.85f+random(-width*0.05f, width*0.05f), height*0.5f));
		
		randomStatement = 0;
		
		alliances = new ArrayList<Alliance>();
		trades = new ArrayList<Trade>();
		wars = new ArrayList<War>();
		
		startButton3 = new Button(width*0.5f, height*0.9f, 60, 2, this);
		
		allianceModifier = 1.0f;
		tradeModifier = 1.0f;
		warModifier = 1.0f;
		nationGoal = 0;
		victoryBehaviour = 1.0f;
		tradeLimit = 10; //(use as constrain() per frame?)
		allianceTrust = 1.0f;
		cultureExternal = 1.0f;
		
		allianceModifierUp = new Button(leftColX, firstRowY-buttonBufferY, 20, 1, this);
		allianceModifierDown = new Button(leftColX, firstRowY+buttonBufferY, 20, 0, this);
		
		tradeModifierUp = new Button(leftColX, secondRowY-buttonBufferY, 20, 1, this);
		tradeModifierDown = new Button(leftColX, secondRowY+buttonBufferY, 20, 0, this);
		
		tradeLimitUp = new Button(leftColX, thirdRowY-buttonBufferY, 20, 1, this);
		tradeLimitDown = new Button(leftColX, thirdRowY+buttonBufferY, 20, 0, this);
		
		victoryBehaviourUp = new Button(rightColX, firstRowY-buttonBufferY, 20, 1, this);
		victoryBehaviourDown = new Button(rightColX, firstRowY+buttonBufferY, 20, 0, this);
		
		warModifierUp = new Button(rightColX, secondRowY-buttonBufferY, 20, 1, this);
		warModifierDown = new Button(rightColX, secondRowY+buttonBufferY, 20, 0, this);
		
		allianceTrustUp = new Button(rightColX, thirdRowY-buttonBufferY, 20, 1, this);
		allianceTrustDown = new Button(rightColX, thirdRowY+buttonBufferY, 20, 0, this);
		
		//cultureExternalUp = new Button(rightColX, (height / 5) * 4.0f, 20, 1, this);
		//cultureExternalDown = new Button(rightColX, (height / 5) * 4.5f, 20, 0, this);
		
		nationGoalButtonRight = new Button(buttonGoalLeft, buttonGoalHeight, 20, 3, this);
		nationGoalButtonLeft = new Button(buttonGoalRight, buttonGoalHeight, 20, 4, this);
		
		//interactions
		selWealthInc = false;
		selWealthDec = false;
		selWarInduce = false;
		selAllianceBreak = false;
		selFinalProceed = false;
		showWealthIncInfo = false;
		showWealthDecInfo = false;
		showWarInduceInfo = false;
		showAllianceBreakInfo = false;
		showFinalProceedInfo = false;
		
		wealthIncrease = 1.3f;
		wealthDecrease = 0.7f;
		
		// -------------------------------------------------------------------------------------------------------- ENDGAME 3
		randomGen3 = true;
		endGame3_text = "";
		
		st_nationGoalHonor = loadStrings("data/text/endGame3/nationGoalHonor.txt");
		st_nationGoalSurvival = loadStrings("data/text/endGame3/nationGoalSurvival.txt");
		st_nationGoalWealth = loadStrings("data/text/endGame3/nationGoalWealth.txt");
		st_wealth = loadStrings("data/text/endGame3/wealth.txt");
		st_war = loadStrings("data/text/endGame3/war.txt");
		st_tradePartners = loadStrings("data/text/endGame3/tradePartners.txt");
		st_promiscuity = loadStrings("data/text/endGame3/promiscuity.txt");
		st_alliances = loadStrings("data/text/endGame3/alliances.txt");
		st_similarCultureAlone = loadStrings("data/text/endGame3/similarCultureAlone.txt");
		st_similarCultureAllies = loadStrings("data/text/endGame3/similarCultureAllies.txt");
		st_movement = loadStrings("data/text/endGame3/movement.txt");
		
		tsAtPeace = loadStrings("data/text/thought/inGame3/atPeace.txt");
		tsAtPeaceAgain = loadStrings("data/text/thought/inGame3/atPeaceAgain.txt");
		tsAtWar = loadStrings("data/text/thought/inGame3/atWar.txt");
		tsAverageCulture = loadStrings("data/text/thought/inGame3/averageCulture.txt");
		tsNotAverageCulture = loadStrings("data/text/thought/inGame3/notAverageCulture.txt");
		tsHasAllies = loadStrings("data/text/thought/inGame3/hasAllies.txt");
		tsHasNeighbors = loadStrings("data/text/thought/inGame3/hasNeighbors.txt");
		tsHasTradePartners = loadStrings("data/text/thought/inGame3/hasTrade.txt");
		tsNoTradePartners = loadStrings("data/text/thought/inGame3/noTrade.txt");
		tsNationDisappeared = loadStrings("data/text/thought/inGame3/nationDisappeared.txt");
		tsNoAllies = loadStrings("data/text/thought/inGame3/noAllies.txt");
		tsNoNeighbors = loadStrings("data/text/thought/inGame3/noNeighbors.txt");
		tsPoor = loadStrings("data/text/thought/inGame3/poor.txt");
		tsWealthyNation = loadStrings("data/text/thought/inGame3/wealthy.txt");
		tsWasAtWar = loadStrings("data/text/thought/inGame3/wasAtWar.txt");
		
		// -------------------------------------------------------------------------------------------------------- EMAIL
		emailScreen = false;
		sendButton = new Button(width*0.5f, height*0.3f, 40, 2, this);
		typedText = "";
		typedEmail = false;
		blinkingType = 0;
		// -------------------------------------------------------------------------------------------------------- FINAL
		finalAlphaFull = 0;
		finalAlphaSmall = 0;
		finalTimerFull = 0;
		finalTimerSmall = 0;
		
		ac.start();
		
		  
		  alphaFade = 0;
		  fadeRate = 5.0f;
		  fading = false;
	}
	
	void makeSameCluster(Agent a){ 
		
		if(a.cluster == null){ //if it doesn't have a cluster, give him a new one
			a.cluster = new Cluster(a);
		}
		
		for(int i = 0; i < a.currentConnections.size(); i++){
			Connection c = a.currentConnections.get(i);
			
			if(c.a2 == a){
				if(c.a1.cluster == null){
					a.cluster.addAgent(c.a1);
					makeSameCluster(c.a1);
				}
			}else if(c.a1 == a){
				if(c.a2.cluster == null){
					a.cluster.addAgent(c.a2);
					makeSameCluster(c.a2);
				}
			}
		}
	}
	
	public boolean sketchFullScreen(){
		return true;
	}

	void update() {
			checkedAgents.clear();
			totalClusters.clear();
			
			for (int i = 0; i < agents.length; i++) { //clearing out the clusters
				agents[i].cluster = null;
			}
			
			for (int i = 0; i < agents.length; i++) { //clearing out the clusters
				makeSameCluster(agents[i]);
			}
			
			for(int i = 0; i < agents.length; i++){ // this should never happen
				if(agents[i].cluster == null) println("holy shit");
			}
			
			for (int i = 0; i < agents.length; i++) { // we assign a new cluster to each agent
				if (agents[i].isAlive && !checkedAgents.contains(agents[i]) && agents[i].arrived){
					agents[i].update();
					agents[i].connect();
					agents[i].collide();
				}
			}
			
			// ------------------------------------- MOVE PREDATORS ----------------------------------
			for (int i = 0; i < predators.size(); i++) {
				Predator p = predators.get(i);
				if (p.isAlive){
					p.update();
					p.wander();
					p.collide();
				}
			}

			// ------------------------------------- REMOVE EMPTY CLUSTERS ----------------------------------
			for (int i = 0; i < totalClusters.size(); i++) {
				Cluster c = totalClusters.get(i);
				if (c.agentsInside.size() == 0) totalClusters.remove(c);
			}

			
			// ------------------------------------- REMOVE DEAD AGENTS FROM CLUSTERS ----------------------------------
			for (int i = 0; i < totalClusters.size(); i++) {
				Cluster c = totalClusters.get(i);
				for(int j = 0; j < c.agentsInside.size(); i++){
					Agent a = c.agentsInside.get(j);
					if (!a.isAlive) c.agentsInside.remove(a);
					if (a.isKilled) c.agentsInside.remove(a);
				}
			}

			
			// ------------------------------------- REMOVE AGENTS FROM CLUSTERS THAT THEY DON'T REFERENCE ----------------------------------
			for (int i = 0; i < agents.length; i++) {
				for (int j = 0; j < totalClusters.size(); j++) {
					Cluster c = totalClusters.get(j);

					if (c != agents[i].cluster) {
						c.agentsInside.remove(agents[i]);
					}
				}
			}
			
			// ------------------------------------- COUNT CLUSTERS ----------------------------------
			totalClusters.clear(); // clear the list of all the clusters present on screen


			for (int i = 0; i < agents.length; i++) {
				if (agents[i].isAlive) {
					Cluster cTested = agents[i].cluster; 

					boolean isInside = false;
					for (int j = 0; j < totalClusters.size(); j++) {

						Cluster cInside = totalClusters.get(j); 

						if (cTested == cInside) {
							isInside = true; 
							break; // and quit the function
						}
					}

					if (!isInside) {
						totalClusters.add(cTested);
					}
				}
			}
			
			if (millis() - startTime >= timer) { //counting connections every given amount of time
				totalConnections = countConnections();
				startTime = millis();
			}
			isHovering = false;
			for(int i = 0; i < agents.length; i++){
				if(PApplet.dist(mouseX, mouseY, agents[i].pos.x, agents[i].pos.y) < agents[i].rad){
					isHovering = true;
					break;
				}
			}
			
			//------------------------------------- MOUSE OVER CONNECTION TO REMOVE

			for(int k = 0; k < connections.size(); k++){
				Connection c = connections.get(k);
				Agent start;
				Agent end;
				if(c.a1.pos.x > c.a2.pos.x){
					start = c.a1;
					end = c.a2;
				}else{
					start = c.a2;
					end = c.a1;
				}
				
				PVector a = PVector.sub(mouse, start.pos);
				PVector b = PVector.sub(end.pos, start.pos);
				
				b.normalize();
				b.mult(a.dot(b));
				PVector normalPoint = PVector.add(start.pos, b);
				

				float distance = PVector.dist(mouse, normalPoint);
				
				if(distance < 20 && dist(mouseX, mouseY, c.a1.pos.x, c.a1.pos.y) > c.a1.rad*1.1f && dist(mouseX, mouseY, c.a2.pos.x, c.a2.pos.y) > c.a2.rad*1.1f){
					fill(0, 75);
					c.canRemove = true;
					if(selRemoveConnec && removeConnec > 0) c.w = 4;
				}else{
					c.canRemove = false;
					c.w = 1;
				}
			}
	}
	
	void update2(){
		for(int i = 0; i < community.size(); i++){
			//this is the update loop for the agents
			Agent a = community.get(i);
			if(a.isAlive){
				a.update();
				a.connectLove();
				a.connectPower();
				a.connectFriendship();
				a.exertPower();
				a.exertFriendship();
				a.exertLove();
				a.reproduce();
			}
		}
		
		for(int i = 0; i < connectionsFriendship.size(); i++){
			ConnectionFriendship cF = connectionsFriendship.get(i);
			if(!cF.a1.isAlive || !cF.a2.isAlive){
				connectionsFriendship.remove(cF);
			}
		}
		
		for(int i = 0; i < connectionsLove.size(); i++){
			ConnectionLove cL = connectionsLove.get(i);
			if(!cL.a1.isAlive || !cL.a2.isAlive){
				connectionsLove.remove(cL);
			}
		}
		
		for(int i = 0; i < connectionsPower.size(); i++){
			ConnectionPower cP = connectionsPower.get(i);
			if(!cP.a1.isAlive || !cP.a2.isAlive){
				connectionsPower.remove(cP);
			}
		}
		
		for(int i = 0; i < connectionsPower.size(); i++){
			ConnectionPower cP = connectionsPower.get(i);
			cP.update();
		}
		
		for(int i = 0; i < community.size(); i++){
			Agent a = community.get(i);
			float inc = 0.04f;
			if(a.pos.y < strata1Anchor1.y){ //top
				if(a.pos.x < strata1Control1.x){ //left
					strata1Control1.y += inc;
				}else if(a.pos.x < strata1Control2.x){ //middle
					strata1Control1.y += inc/2;
					strata1Control2.y += inc/2;
				}else{ //right
					strata1Control2.y += inc;
				}
				
				//culture modification
				for(int j = 0; j < a.culture.length; j++){
					if(a.culture[j] < a.maxCulture) a.culture[j] += spatialCultureIncrement; //TODO it increases every slot here, is that what i want?
					
				}
				
				
			}else if(a.pos.y < strata2Anchor1.y){//middle
				if(a.pos.x < strata1Control1.x){ //left
					strata1Control1.y -= inc;
					strata2Control1.y += inc;
				}else if(a.pos.x < strata1Control2.x){ //middle
					strata1Control1.y -= inc/2;
					strata1Control2.y -= inc/2;
					
					strata2Control1.y += inc/2;
					strata2Control2.y += inc/2;
				}else{ //right
					strata1Control2.y -= inc;
					strata2Control2.y += inc;
				}
				
				//culture modification
				for(int j = 0; j < a.culture.length; j++){
					if(a.culture[j] > 5) a.culture[j] -= spatialCultureIncrement;
					if(a.culture[j] < 5) a.culture[j] += spatialCultureIncrement;
				}
				
			}else{ //bottom
				if(a.pos.x < strata1Control1.x){ //left
					strata2Control1.y -= inc;
				}else if(a.pos.x < strata1Control2.x){ //middle
					strata2Control1.y -= inc;
					strata2Control2.y -= inc;
				}else{ //right
					strata2Control2.y -= inc;
				}
				
				//culture modification
				for(int j = 0; j < a.culture.length; j++){
					if(a.culture[j] > 0) a.culture[j] -= spatialCultureIncrement;
				}
			}
			
			strata1Control1.y = constrain(strata1Control1.y, 3, height - height/20);
			strata1Control2.y = constrain(strata1Control2.y, 3, height - height/20);
			
			strata2Control2.y = constrain(strata2Control1.y, 3, height - height/20);
			strata2Control2.y = constrain(strata2Control2.y, 3, height - height/20);
		}
	}

	public void draw() {
		mouse = new PVector(mouseX, mouseY);
		background(250);
		rhythm();
		
		textFont(mainFont);
		
		if(fading){
			alphaFade += fadeRate;
			if(alphaFade > 255){
				if(inGame1){
					inGame1 = false;
					endGame1 = true;
					fading = false;
					if(seasons != null) eMasterSeasons.addSegment(0.00f, 2000.0f); //lower the sound of seasons then kill it (we recreate it at each stage)
					eMasterRhythm.addSegment(1.0f, 2000.0f);
				}else if(inGame2){
					inGame2 = false;
					endGame2 = true;
					fading = false;
					if(seasons != null) eMasterSeasons.addSegment(0.00f, 2000.0f); //lower the sound of seasons then kill it (we recreate it at each stage)
					eMasterRhythm.addSegment(1.0f, 2000.0f);
				}else if(inGame3){
					inGame3 = false;
					endGame3 = true;
					fading = false;
					if(seasons != null) eMasterSeasons.addSegment(0.00f, 2000.0f); //lower the sound of seasons then kill it (we recreate it at each stage)
					eMasterRhythm.addSegment(1.0f, 2000.0f);
				}else if(startGame1){
					generateAgents(agents.length);
					generatePredators();
					startPhaseTimer = startPhaseTimer + millis();
					agentsArriveStartTimer = millis();
					startGame1 = false;
					inGame1 = true;
					fading = false;
					if(seasons != null) eMasterSeasons.addSegment(0.1f, 4000.0f);
					eMasterRhythm.addSegment(0.5f, 2000.0f);
				}else if(startGame2){
					canDragAgent = true;
					selFinalCluster = false;
					generateAgents(20); //the int argument isn't used if finalCluster != null;
					inGame2 = true;
					startGame2 = false;
					fading = false;
					if(seasons != null) eMasterSeasons.addSegment(0.1f, 4000.0f);
					eMasterRhythm.addSegment(0.5f, 2000.0f);
				}else if(startGame3){
					generateNation();
					generateOtherNations();
					startGame3 = false;
					inGame3 = true;
					fading = false;
					if(seasons != null) eMasterSeasons.addSegment(0.1f, 4000.0f);
					eMasterRhythm.addSegment(0.5f, 2000.0f);
				}else if(endGame1){
					endGame1 = false;
					startGame2 = true;
					fading = false;
				}else if(endGame2){
					endGame2 = false;
					startGame3 = true;
					fading = false;
				}else if(endGame3){
					endGame3 = false;
					emailScreen = true;
					fading = false;
				}
			}
		}else{
			alphaFade = 0;
		}
		
		// ----------------------------------------------- END GAME 1
		if (endGame1) {
			endGame();
		}
		
		if(endGame2){
			endGame2();
		}
		
		if(endGame3){
			endGame3();
		}
		
		if(finalScreen){
			finalScreen();
		}


		// ------------------------------------------ START GAME MODE 1
		if(intro){
			noCursor();
			intro();
		}
		
		if (startGame1) { // startScreen
			cursor(CROSS);
			startGame();
		}
		
		if(startGame2){
			startGame2();
		}
		
		if(startGame3){
			startGame3();
		}
		
		if (inGame1) {
			inGame();
		}
		
		if(inGame2){
			inGame2();
		}
		
		if(inGame3){
			inGame3();
		}
		
		if(emailScreen){
			emailScreen();
		}
		
		//---------------------------------------------------------------- POST GAME
		if(postGame){ 				
			endGame();
		}
		
		if(communalHistory){
				generateCommunalHistory();
		}
		
		rectMode(CORNER);
		stroke(255, alphaFade);
		fill(255, alphaFade);
		rect(rectBorderX, rectBorderY, rectBorderW, height*0.99334f);
		stroke(10);
		
	}
	
	public void emailScreen(){
		fill(0);
		textAlign(CENTER);
		textSize(mainFontSize);
		textFont(mainFont);
		text("SOCIAL CONTACT", width*0.5f, height*0.1f);
		
		textSize(headerFontSize);
		textFont(headerFont);
		text("personal archival", width/2, height/7);

		text("enter your email address to archive the story of your society.", width*0.5f, height*0.3f);
		
		rectMode(CENTER);
		textAlign(CENTER);
		stroke(0);
		strokeWeight(1);
		noFill();
		rect(width*0.5f, height*0.5f, width*0.35f, height*0.05f);
		if(typedText == ""){
			if(frameCount % 15 == 0){
				if(blinkingType == 255){
					blinkingType = 0;
				}else{
					blinkingType = 255;
				}
			}
			stroke(0, blinkingType);
			strokeWeight(2);
			line(width*0.5f, height*0.49f, width*0.5f, height*0.51f);
		}
		text(typedText, width*0.5f, height*0.51f);
		
		
		rectMode(CORNER);
		textAlign(LEFT);
		strokeWeight(2);
		stroke(10, 210);
		noFill();
		rect(proceedX, proceedY, proceedW, proceedH);
		textFont(headerFont);
		textSize(headerFontSize);
		text("Proceed", proceedX*1.01f, proceedY*1.0475f);
	}
	
	public void finalScreen(){
		background(255);
		stroke(0);
		strokeWeight(1);
		rect(3, 3, width-6, height-6);
		rect(6, 6, width-12, height-12);
		
		fill(0);
		textAlign(CENTER);
		textFont(headerFont);
		textSize(headerFontSize);
		text("thank you", width*0.5f, height*0.4f);
		
		if(millis() > finalTimerSmall){
			finalAlphaSmall += 10.0f;
		}
		
		if(millis() > finalTimerFull){
			finalAlphaFull += 10.0f;
		}
		
		fill(255, finalAlphaSmall);
		noStroke();
		rect(width*0.455f, height*0.35f, width*0.052f, width*0.05f);
		
		
		fill(255, finalAlphaFull);
		noStroke();
		rect(3, 3, width - 6, height - 6);
		
		if(finalAlphaFull > 1000){
			emailScreen = false;
			startGame1 = true;
		}
		
	}
	
	
	public void sendEmail(){
		try {

		    Properties props = new Properties();
		    
		    props.put("mail.smtp.host", "smtp.gmail.com");
		    props.put("mail.smtp.socketFactory.port", "465");
		    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.port", "465");
		    
		    // Create authentication object
		    Auth auth = new Auth();

		    Session session = Session.getDefaultInstance(props, auth);

		    try {
		   
		      Message message = new MimeMessage(session);
		      message.setFrom(new InternetAddress("socialcontact.representative@gmail.com", "social contact"));
		      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
		      message.setSubject("on "+storyTitle1+", "+storyTitle2+" and "+storyTitle3);
		      message.setText(emailStory);
		      Transport.send(message);

		      println("email sent");
		    } 

		    finally 
		    {
		      //session.close();
		    }
		  }
		  catch (Exception e) 
		  {
			  e.printStackTrace();
		    //throw new RuntimeException(e);
		  }
	}
	
	void intro(){
		fill(50, textIntroAlpha);
		textAlign(CENTER);
		textSize(headerFontSize);
		textFont(headerFont);
		text(textIntro, width/2, height/3);
		if(textIntroAlpha < 200){
			textIntroAlpha += 0.5f;
		}else{
			introStage++;
			textIntroAlpha = 0;
		}
		

		
		if(introStage == 0){
			textIntro = "politics is the act of coming together.";
			if(introPosA.x < width*0.45f && introPosB.x > width*0.55f){
				introPosA.x += 1.4f;
				introPosB.x -= 1.4f;
				introPosC.y -= 0.7f;
			}else{
				strokeCap(ROUND);
				stroke(50, lineIntroAlpha);
				line(introPosA.x+rad/2, introPosA.y, introPosB.x-rad/2, introPosB.y);
				line(introPosA.x, introPosA.y+rad/2, introPosC.x, introPosC.y-rad/2);
				line(introPosB.x, introPosB.y+rad/2, introPosC.x, introPosC.y-rad/2);
				if(lineIntroAlpha < 200) lineIntroAlpha += 0.85f;
			}
			
			if(dist(mouse.x, mouse.y, introPosA.x, introPosA.y) < rad){
			text("hello.", introPosA.x, introPosA.y);	
			}
			
			if(dist(mouse.x, mouse.y, introPosB.x, introPosB.y) < rad){
				text("hi.", introPosB.x, introPosB.y);	
			}
			
			if(dist(mouse.x, mouse.y, introPosC.x, introPosC.y) < rad){
				text("hey.", introPosC.x, introPosC.y);	
			}

		}
		
		if(introStage == 1){
			textIntro = "politics is the act of living with one another.";
			
			if(introPosA.x < width*0.47f) introPosA.x += 0.3f;
			if(introPosA.y < height*0.53f) introPosA.y += 0.3f;
			
			if(introPosC.x > width*0.48f) introPosC.x -= 0.3f;
			if(introPosC.y > height*0.58f) introPosC.y -= 0.65f;
			

			if(introPosB.x < width*0.57f) introPosB.x += 0.3f;
			if(introPosB.y > height*0.43f) introPosB.y -= 0.6f;
			
			if(linesIntroAlpha < 100) linesIntroAlpha += 0.65f;
			
			if(dist(mouse.x, mouse.y, introPosA.x, introPosA.y) < rad){
				text("people can fall in love.", introPosA.x, introPosA.y);	
			}
			
			if(dist(mouse.x, mouse.y, introPosB.x, introPosB.y) < rad){
				text("people can make friends.", introPosB.x, introPosB.y);	
			}
			
			if(dist(mouse.x, mouse.y, introPosC.x, introPosC.y) < rad){
				text("people can subjugate.", introPosC.x, introPosC.y);	
			}
			
			
		}
		if(introStage == 2){
			textIntro = "politics is the act of acknowledging the presence of others.";
			strokeWeight(2);
			stroke(0, othersIntroAlpha);
			noFill();
			rad = 20;
			for(int i = 0; i < introPosO.length; i++){
				ellipse(introPosO[i].x, introPosO[i].y, rad, rad);
				ellipse(introPosO[i].x, introPosO[i].y, rad*0.66f, rad*0.66f);
				ellipse(introPosO[i].x, introPosO[i].y, rad*0.33f, rad*0.33f);
				ellipse(introPosO[i].x, introPosO[i].y, 1, 1);
				/*
				introPosO[i].x = lerp(introPosO[i].x, width/2, lerpIntro);
				introPosO[i].y = lerp(introPosO[i].y, height/2, lerpIntro);
				*/
				//make them lerp towards the middle of the triangle? who really cares at that point?
				introPosO[i].x = lerp(introPosO[i].x, (introPosA.x+introPosB.x+introPosC.x)/3, lerpIntro);
				introPosO[i].y = lerp(introPosO[i].y, (introPosA.y+introPosB.y+introPosC.y)/3, lerpIntro);
				lerpIntro += 0.0000001f;
			}
			
			if(othersIntroAlpha < 40 && rectIntroAlpha < 20) othersIntroAlpha += 0.2f;
			
			if(dist(mouse.x, mouse.y, introPosA.x, introPosA.y) < rad){
			text("others can be enticing.", introPosA.x, introPosA.y);	
			}
			
			if(dist(mouse.x, mouse.y, introPosB.x, introPosB.y) < rad){
				text("others can be frightening.", introPosB.x, introPosB.y);	
			}
			
			if(dist(mouse.x, mouse.y, introPosC.x, introPosC.y) < rad){
				text("others can be dangerous.", introPosC.x, introPosC.y);	
			}
		}
		
		if(introStage < 3){
			
			stroke(50, circleIntroAlpha);
			fill(252, 255);
			strokeWeight(3);
			//noFill(); 
			ellipse(introPosA.x, introPosA.y, rad, rad);
			ellipse(introPosB.x, introPosB.y, rad, rad);
			ellipse(introPosC.x, introPosC.y, rad, rad);
			
			float rad = 30;

			if(circleIntroAlpha < 220) circleIntroAlpha += 0.7f;
			
			rad = 30;
			strokeCap(PApplet.ROUND);
			
			//love line
			strokeWeight(30);
			stroke(255, 0, 0, linesIntroAlpha);
			fill(255, 0, 0, linesIntroAlpha);
			ellipse((introPosA.x+introPosC.x)*0.5f, (introPosA.y + introPosC.y)*0.5f, dist(introPosA.x, introPosA.y, introPosC.x, introPosC.y), dist(introPosA.x, introPosA.y, introPosC.x, introPosC.y));
			noFill();
			//friendship line
			strokeWeight(15);
			strokeCap(PApplet.ROUND);
			stroke(0, 0, 255, linesIntroAlpha);
			line(introPosB.x, introPosB.y, introPosC.x, introPosC.y);
			
			//power line
			strokeWeight(3);
			strokeCap(PApplet.ROUND);
			stroke(0, 150, 0, linesIntroAlpha);
			line(introPosB.x, introPosB.y, introPosA.x, introPosA.y);
			
			
			
			stroke(50, circleIntroAlpha);
			//fill(252, 255);
			strokeWeight(2);
			noFill(); 
			
			ellipse(introPosA.x, introPosA.y, rad, rad);
			ellipse(introPosA.x, introPosA.y, rad*0.66f, rad*0.66f);
			ellipse(introPosA.x, introPosA.y, rad*0.33f, rad*0.33f);
			ellipse(introPosA.x, introPosA.y, 1, 1);
			ellipse(introPosB.x, introPosB.y, rad, rad);
			ellipse(introPosB.x, introPosB.y, rad*0.66f, rad*0.66f);
			ellipse(introPosB.x, introPosB.y, rad*0.33f, rad*0.33f);
			ellipse(introPosB.x, introPosB.y, 1, 1);
			ellipse(introPosC.x, introPosC.y, rad, rad);
			ellipse(introPosC.x, introPosC.y, rad*0.66f, rad*0.66f);
			ellipse(introPosC.x, introPosC.y, rad*0.33f, rad*0.33f);
			ellipse(introPosC.x, introPosC.y, 1, 1);
		}

		
		if(othersIntroAlpha > 40 || rectIntroAlpha > 3.0f){
			textIntroAlpha -= 4.0f;
			circleIntroAlpha -= 4.0f;
			linesIntroAlpha -= 4.0f;
			fill(255, rectIntroAlpha);
			rect(3, 3, width-3, height-3);
			rectIntroAlpha += 0.5f;
			if(othersIntroAlpha > 0) othersIntroAlpha -= 0.5f;
			//lerpIntro = lerpIntro *0.9f;
		}
		
		if(rectIntroAlpha > 105){
			//introStage++;
			textSize(mainFontSize);
			textFont(mainFont);
			fill(60, titleIntroAlpha);
			text("SOCIAL CONTACT", width/2, height*0.1f);
			textSize(headerFontSize);
			textFont(headerFont);
			text("a simulation of politics", width/2, height*0.25f);
			textSize(textFontSize);
			textFont(textFont);
			text("developed by Pierre Depaz", width/2, height*0.95f);
			titleIntroAlpha += 2.0f;
		}
		
		if(titleIntroAlpha > 400){
			intro = false;
			startGame1 = true;
		}
	}
	
	void fishTank(){
		stroke(50);
		strokeWeight(2);
		fill(255, 150);
		rect(width/2, height/2, fishTankSize, fishTankSize);
		if(startGame1){
			for(int i = 0; i < fishes.size(); i++){
				Fish f = fishes.get(i);
				f.update();
				f.display();
				f.boundaries();
				f.collide();
				if(drawFishConnec) f.drawConnections();
			}
			
			if(drawFishPredators){
				fishPred.predatorDisplay();
				fishPred.update();
			}
			
			
			if(drawFishResources){
				if(resourceSeek == 0.5f){
					stroke(0, 100, 0, 50);
					fill(0, 100, 0, 20);
				}else if(resourceSeek == 1.0f){
					stroke(0, 100, 0, 100);
					fill(0, 100, 0, 50);
				}else{
					stroke(0, 100, 0, 200);
					fill(0, 100, 0, 150);
				}
				
				int angleIncrement = (int)(360/fishResourceSides);
				strokeWeight(2);

				beginShape();
				int index = 0;
				for(float angle = 0; angle < 360; angle += angleIncrement){
					float x = fishResourcePos.x + PApplet.sin(PApplet.radians(angle))*(fishResourceRad+fishResourceSidesVar[index]);
					float y = fishResourcePos.y + PApplet.cos(PApplet.radians(angle))*(fishResourceRad+fishResourceSidesVar[index]);
					vertex(x, y);
					if(index < fishResourceSidesVar.length-1) index++;
				}
				endShape(PApplet.CLOSE);
			}
			
			
			if(canRememberFallen){
				stroke(0, 50);
				ellipse(fallenPos.x, fallenPos.y, 15, 15);
				line(fallenPos.x-3, fallenPos.y-3, fallenPos.x+3, fallenPos.y+3);
				line(fallenPos.x-3, fallenPos.y+3, fallenPos.x+3, fallenPos.y-3);
				line(fallenPos.x, fallenPos.y, fishes.get(0).pos.x, fishes.get(0).pos.y);
			}
			
			if(canProtect){
				fishes.get(0).color = color(200, 200, 0);
			}else{
				fishes.get(0).color = color(50);
			}
			
			if(canDisturb){
				fishes.get(1).color = color(100, 0, 0);
			}else{
				fishes.get(1).color = color(50);	
			}
			
			if(canFormStableRelationships){
				for(int i = 0; i < fishes.size(); i++){
					Fish f = fishes.get(i);
					f.maxSpeed = 0.5f;
				}
			}else{
				for(int i = 0; i < fishes.size(); i++){
					Fish f = fishes.get(i);
					f.maxSpeed = 1.0f;
				}
			}
			
			if(violence == 0.5f){
				fishes.get(2).color = color(200, 0, 0);
				fishes.get(3).color = color(200, 0, 0);
				fishes.get(4).color = color(50);
			}else if(violence == 1.0f){
				fishes.get(1).color = color(50);
				fishes.get(2).color = color(200, 0, 0);
				fishes.get(3).color = color(200, 0, 0);
				fishes.get(4).color = color(200, 0, 0);
			}else{
				fishes.get(1).color = color(50);
				fishes.get(2).color = color(50);
				fishes.get(3).color = color(50);
				fishes.get(4).color = color(50);
			}			
		}
		
		if(startGame2){
			for(int i = 0; i < fishes.size(); i++){
				Fish f = fishes.get(i);
				f.update();
				f.display();
				f.boundaries();
				f.collide();
			}
			
			if(powerForceUpButton.onClick() || powerForceDownButton.onClick()){
				drawFishPower = true;
				drawFishFriend = false;
				drawFishLove = false;
				drawCulture = false;
				drawChildren = false;
			}
			
			if(friendshipForceUpButton.onClick() || friendshipForceDownButton.onClick()){
				drawFishPower = false;
				drawFishFriend = true;
				drawFishLove = false;
				drawCulture = false;
				drawChildren = false;
			}
			
			if(loveForceUpButton.onClick() || loveForceDownButton.onClick()){
				drawFishPower = false;
				drawFishFriend = false;
				drawFishLove = true;
				drawCulture = false;
				drawChildren = false;
			}
			
			if(cultureButtonRight.onClick() || cultureButtonLeft.onClick()){
				drawFishPower = false;
				drawFishFriend = false;
				drawFishLove = false;
				drawCulture = true;
				drawChildren = false;
			}
			
			if(numberOfChildrenUpButton.onClick() || numberOfChildrenUpButton.onClick() || independenceOfChildrenDownButton.onClick() || independenceOfChildrenUpButton.onClick() || ageMajorityDownButton.onClick() || ageMajorityUpButton.onClick()){
				drawFishPower = false;
				drawFishFriend = false;
				drawFishLove = false;
				drawCulture = false;
				drawChildren = true;
			}
			
			if(drawFishPower){
				int fp = (int)PApplet.map(powerForce, 1.0f, 7.0f, 1.0f, 5.0f);
				for(int i = 0; i < fishes.size(); i++){
					Fish f = fishes.get(i);
					f.drawPower(fp);
				}
			}
			
			if(drawFishFriend){
				int ff = (int)PApplet.map(friendshipForce, 1.0f, 7.0f, 1.0f, 5.0f);
				for(int i = 0; i < fishes.size(); i++){
					Fish f = fishes.get(i);
					f.drawFriendship(ff);
				}
			}
			
			if(drawFishLove){
				int fl = (int)PApplet.map(loveForce, 40.0f, 60.0f, 10.0f, 100.0f);
					fishes.get(0).drawLove(fl, fishes.get(1), fishes.get(2));
			}
			
			if(drawCulture){
				for(int i = 0; i < 15; i++){
					stroke(0, 20);
					strokeWeight(1);
					if(cultureOrigin == 1){
						strokeWeight(2);
						stroke(0, 100);
					}
					if(cultureOrigin == 2){
						strokeWeight(1);
						stroke(0, 20);
					}
					fill(0, 20);
					bezier(startPointBezierFish[i].x, startPointBezierFish[i].y, anchorPointABezierFish[i].x, anchorPointABezierFish[i].y, anchorPointBBezierFish[i].x, anchorPointBBezierFish[i].y, endPointBezierFish[i].x, endPointBezierFish[i].y);
					for(int k = 0; k < fishes.size(); k++){
						Fish f = fishes.get(k);
					
					if(f.pos.y > (height*0.5f)){
						anchorPointABezierFish[i].y += 0.04f;
						anchorPointBBezierFish[i].y += 0.06f;
					}else{
						anchorPointABezierFish[i].y -= 0.06f;
						anchorPointBBezierFish[i].y -= 0.04f;
					}
					
					anchorPointABezierFish[i].y = constrain(anchorPointABezierFish[i].y, height*0.5f-fishTankSize*0.5f, height*0.5f+fishTankSize*0.5f);
					anchorPointBBezierFish[i].y = constrain(anchorPointABezierFish[i].y, height*0.5f-fishTankSize*0.5f, height*0.5f+fishTankSize*0.5f);
					}
				}
				float[][] points = new float[fishes.size()][2];
				for(int i = 0; i < fishes.size(); i++){
					points[i][0] = fishes.get(i).pos.x;
					points[i][1] = fishes.get(i).pos.y;
				}
				Hull cultureEnvelopeHull = new Hull(points);
				
				MPolygon fishesRegion = cultureEnvelopeHull.getRegion();
				if(cultureOrigin == 2){
					fill(0, 50);
				}else{
					fill(0, 10);
				}
				noStroke();
				fishesRegion.draw(this);
				noFill();
				
			}
			
			if(drawChildren){
				if(fishes.size() < fishNumber + numberOfChildren){
					fishes.add(new Fish(random(width*0.45f, width*0.55f), random(height*0.45f, height*0.55f), 0, 10, 150, this));
				}
			}
		}
		
		if(startGame3){
			stroke(0, 50);
			line(width*0.5f-height/6, height*0.5f+height/6, width*0.5f+height/6, height*0.5f-height/6);
			for(int i = 0; i < citizenFishNum; i++){
				stroke(0, fishHullCol1*0.5f, fishHullCol1, 200);
				noFill();
				strokeWeight(citizenFishWeight[i]);
				ellipse(citizenFishPos[i].x, citizenFishPos[i].y, citizenFishRad[i], citizenFishRad[i]);
				if(citizenFishPos[i].x > width*0.5f) citizenFishPos[i].x += (cos(millis()*0.00001f))*0.01f;
				if(citizenFishPos[i].x < width*0.5f) citizenFishPos[i].x -= (cos(millis()*0.00001f))*0.01f;
				if(citizenFishPos[i].y < height*0.5f) citizenFishPos[i].y += (cos(millis()*0.00001f))*0.01f;
				if(citizenFishPos[i].y > height*0.5f) citizenFishPos[i].y -= (cos(millis()*0.00001f))*0.01f;
				citizenFishPos[i].x = constrain(citizenFishPos[i].x, width*0.5f - height/6,  width*0.5f + height/6);
				citizenFishPos[i].y = constrain(citizenFishPos[i].y, height*0.5f - height/6,  height*0.5f + height/6);
			}
			
			float[][] points = new float[(int)citizenFishNum][2];
			for(int i = 0; i < citizenFishNum; i++){
				points[i][0] = citizenFishPos[i].x;
				points[i][1] = citizenFishPos[i].y;
			}
			Hull cultureEnvelopeHull = new Hull(points);
			
			MPolygon nationBorders = cultureEnvelopeHull.getRegion();
			
			fill(0, fishHullCol1*0.5f, fishHullCol1, 50);
			stroke(0, fishHullCol1*0.5f, fishHullCol1, 100);
			strokeWeight(1);
			nationBorders.draw(this);
			
			
			for(int i = 0; i < citizenFishNum2; i++){
				stroke(fishHullCol2, fishHullCol2*0.5f, 0, 200);
				noFill();
				strokeWeight(citizenFishWeight2[i]);
				ellipse(citizenFishPos2[i].x, citizenFishPos2[i].y, citizenFishRad2[i], citizenFishRad2[i]);
				if(citizenFishPos2[i].x > width*0.5f) citizenFishPos2[i].x += (cos(millis()*0.00001f))*0.01f;
				if(citizenFishPos2[i].x < width*0.5f) citizenFishPos2[i].x -= (cos(millis()*0.00001f))*0.01f;
				if(citizenFishPos2[i].y < height*0.5f) citizenFishPos2[i].y += (cos(millis()*0.00001f))*0.01f;
				if(citizenFishPos2[i].y > height*0.5f) citizenFishPos2[i].y -= (cos(millis()*0.00001f))*0.01f;
				citizenFishPos2[i].x = constrain(citizenFishPos2[i].x, width*0.5f - height/6,  width*0.5f + height/6);
				citizenFishPos2[i].y = constrain(citizenFishPos2[i].y, height*0.5f - height/6,  height*0.5f + height/6);
			}
			
			float[][] points2 = new float[(int)citizenFishNum2][2];
			for(int i = 0; i < citizenFishNum2; i++){
				points2[i][0] = citizenFishPos2[i].x;
				points2[i][1] = citizenFishPos2[i].y;
			}
			Hull cultureEnvelopeHull2 = new Hull(points2);
			
			MPolygon nationBorders2 = cultureEnvelopeHull2.getRegion();
			
			fill(fishHullCol2, fishHullCol2*0.5f, 0, 20);
			stroke(fishHullCol2, fishHullCol2*0.5f, 0, 100);
			strokeWeight(1);
			nationBorders2.draw(this);
			
			if(tradeLimitUp.onClick() || tradeLimitUp.onClick() || tradeModifierUp.onClick() || tradeModifierDown.onClick()){
				canShowWar = false;
				canShowAlliances = false;
				canShowTrade = true;
				canShowRuin = false;
				canShowCulture = false;
			}
			
			if(allianceModifierDown.onClick() || allianceModifierUp.onClick() || allianceTrustDown.onClick() || allianceTrustUp.onClick()){
				canShowWar = false;
				canShowAlliances = true;
				canShowTrade = false;
				canShowRuin = false;
				canShowCulture = false;
			}
			
			if(warModifierDown.onClick() || warModifierUp.onClick()){
				canShowWar = true;
				canShowAlliances = false;
				canShowTrade = false;
				canShowRuin = false;
				canShowCulture = false;
			}
			
			if(victoryBehaviourUp.onClick() || victoryBehaviourDown.onClick()){
				canShowWar = false;
				canShowAlliances = false;
				canShowTrade = false;
				canShowRuin = true;
				canShowCulture = false;
			}
			
			if(nationGoalButtonRight.onClick() || nationGoalButtonLeft.onClick()){
				canShowWar = false;
				canShowAlliances = false;
				canShowTrade = false;
				canShowRuin = false;
				canShowCulture = true;
			}
			
			if(canShowTrade){
				strokeWeight(2);
				rectMode(PApplet.CENTER);
				for(int i = 0; i < fishVesselsNum; i++){
					fishVesselsPos[i] = PVector.lerp(new PVector(width*0.45f, height*0.45f), new PVector(width*0.55f, height*0.55f), fishVesselsLerp[i]);
					PVector dir = PVector.sub(new PVector(width*0.45f, height*0.45f), new PVector(width*0.55f, height*0.55f));
					float theta = dir.heading2D();
					pushMatrix();
					translate(fishVesselsPos[i].x, fishVesselsPos[i].y);
					rotate(theta);
					stroke(0, 50, 200);
					fill(0, 20, 150, 150);
					rect(0, 0, fishVesselsSize*tradeModifier, 5*tradeLimit);
					popMatrix();
					fishVesselsLerp[i] += fishLerpAmount;
					if(fishVesselsLerp[i] > 1) fishVesselsLerp[i] = 0;
				}
			}
			
			if(canShowWar){
				for(int i = 0; i < 10; i++){
					strokeWeight(2);
					stroke(255-random(100), 0, 0, 150*warModifier);
					line(width*0.45f, height*0.45f, width*0.5f+random(-1, 1), height*0.5f+random(-35, 35));
					line(width*0.55f, height*0.55f, width*0.5f+random(-1, 1), height*0.5f+random(-35, 35));
				}
			}
			
			if(canShowAlliances){
				int numAlliances = min(citizenFishNum, citizenFishNum2);
				strokeWeight(1+1*allianceModifier);
				stroke(0, 150, 0, 20+80*allianceTrust);
				for(int i = 0; i < numAlliances; i++){
					line(citizenFishPos[i].x, citizenFishPos[i].y, citizenFishPos2[i].x, citizenFishPos2[i].y);
				}
			}
			
			if(canShowRuin){
				for(int i = 0; i < citizenFishNum2; i++){
					if(citizenFishPos2[i].x > width*0.45f && citizenFishPos2[i].x < width*0.5f){
						citizenFishPos2[i].x += 0.05f;
					}
					if(citizenFishPos2[i].y > height*0.45f && citizenFishPos2[i].y < height*0.5f){
						citizenFishPos2[i].y += 0.05f;
					}
				}
				
				for(int i = 0; i < citizenFishNum; i++){
					if(citizenFishPos[i].x > width*0.55f && citizenFishPos[i].x < width*0.6f){
						citizenFishPos[i].x -= 0.05f;
					}
					if(citizenFishPos[i].y > height*0.55f && citizenFishPos[i].y < height*0.6f){
						citizenFishPos[i].y -= 0.05f;
					}
				}
			}
			
			if(canShowCulture){
				fishHullCol1 = 50 + 50*nationGoal;
				fishHullCol2 = 50 + 50*nationGoal;
			}else{
				fishHullCol1 = 0;
				fishHullCol2 = 0;
			}
		}
	}
	
	void startGame(){
		rectMode(CORNER);
		rectCol = 245+(noise(t))*10; 
		fill(rectCol);
		t+= 0.4f;
		strokeWeight(2);
		rect(width*0.00375f, height*0.00333f, width*0.9925f, height*0.99334f);
		//rect(width*0.00750f, height*0.00666f, width*0.99250f, height*0.98666f);
		stroke(0);
		
		fill(0);
		textAlign(CENTER);
		textSize(mainFontSize);
		textFont(mainFont);
		text("SOCIAL CONTACT", width*0.5f, height*0.1f);
		
		textSize(subtitleFontSize);
		textFont(subtitleFont);
		text("I - coming together", width/2, height/7);

		textSize(headerFontSize);
		textFont(headerFont);
		text("Inside", leftColX, headerHeight);
		
		textSize(textFontSize);
		textFont(textFont);
		right1.display();
		right2.display();
		right3.display();
		right4.display();
		textAlign(LEFT);
		fill(0);
		text("stay close to those they encounter", leftColX-bufferXstart1, height*0.305f);
		text("disturb communities they come upon", leftColX-bufferXstart1,  height*0.435f);
		text("protect their own against any threat", leftColX-bufferXstart1,  height*0.565f);
		text("remember those who are dead", leftColX-bufferXstart1,  height*0.695f);

		textSize(headerFontSize);
		textFont(headerFont);
		textAlign(CENTER);
		text("Outside", rightColX, headerHeight);
		
		
		textSize(textFontSize);
		textFont(textFont);
		duty_up.display();
		text("connect to " + connec + " other(s).", rightColX, firstRowY);
		duty_down.display();
		
		fishTank();

		if (violence == 0.5) {
			violenceText = "fight as they please";
		} else if (violence == 1) {
			violenceText = "fight anyone they encounter";
		} else if (violence == 0) {
			violenceText = "never fight anyone";
		}
		fill(0);
		violence_up.display();
		text("" + violenceText, rightColX, secondRowY);
		violence_down.display();		
		
		fill(0);
		resource_up.display();
		text(resourceText, rightColX,thirdRowY);
		resource_down.display();
		
		if(resourceSeek == 0.5f){
			resourceText = "they do not need to accumulate resources at all";
		}else if(resourceSeek == 1.0f){
			resourceText = "they should look for resources";
		}else{
			resourceText = "they should compete for resources";
		}
		
		

		if (goalInt == 0) {
			goalText = "a blissful existence\n(no one dies)";
			storyTitle1 = "bliss";
		} else if (goalInt == 1) {
			goalText = "a life of realities\n(a couple will die)";
			storyTitle1 = "reality";
		} else if (goalInt == 2) {
			goalText = "a life of common struggle\n(some will die)";
			storyTitle1 = "struggle";
		} else if (goalInt == 3) {
			goalText = "a life of hardships\n(most will die)";
			storyTitle1 = "hardship";
		} else if (goalInt > 3) {
			goalInt = 0;
		}else if (goalInt < 0){
			goalInt = 3;
		}
		
		showAlpha = abs(cos(millis()*0.001f)*150)+100;
		
		textAlign(LEFT);
		if(canShowConnections){
			fill(0, showAlpha);
			drawFishConnec = true;
		}else{
			fill(100, showAlpha+100);
			drawFishConnec = false;
		}
		text("ties", width*0.405f, height*0.329f);
		if(canShowResources){
			fill(0, 100, 0, showAlpha+100);
			drawFishResources = true;
		}else{
			fill(100, showAlpha);
			drawFishResources = false;
		}
		text("resource", width*0.46f, height*0.329f);
		if(canShowPredators){
			fill(50, 50, 0, showAlpha+100);
			drawFishPredators = true;
		}else{
			fill(100, showAlpha);
			drawFishPredators = false;
		}
		text("predator", width*0.542f, height*0.329f);

		
		pursuitLeft.display();
		pursuitRight.display();
		
		textSize(textFontSize);
		textFont(textFont);
		text(goalText, width*0.5f, buttonGoalHeight);

		startButton.display();
		startButton.onClick();

		right1.onClick();
		right2.onClick();
		right3.onClick();
		right4.onClick();
		duty_up.onClick();
		duty_down.onClick();

		violence_up.onClick();
		violence_down.onClick();
		
		resource_up.onClick();
		resource_down.onClick();

		pursuitLeft.onClick();
		pursuitRight.onClick();
	}
	
	void inGame(){
		update();
		textSize(textFontSize);
		textFont(textFont);
		textAlign(LEFT);
		fill(bgColBox, 50);
		bgColBox = lerpColor(bgColBox, newBgColBox, bgColBoxLerpSpeed);
		bgColBoxLerpSpeed = map((millis()-Seasons.startTime), 0, 6000.0f, 0, 1);
		//noFill();
		stroke(0);
		strokeWeight(1);
		rect(rectBorderX, rectBorderY, rectBorderW, rectBorderH);
		strokeWeight(2);
		rect(width*0.00375f, height*0.00333f, width*0.9925f, height*0.99334f);
		
		seasons.cycle();
		seasons.populate(Seasons.numberOfSeasons);
		drawVoronoi(1);
		
		if(arriveIndex < agents.length){
			if(millis() - agentsArriveStartTimer > agentsArriveTimer){
				agents[arriveIndex].arrived = true;
				arriveIndex++;
				agentsArriveStartTimer = millis();
			}
		}
		
		for(int i = 0; i < resources.size(); i++){
			Resource r = resources.get(i);
			r.display();
			r.deplete();
		}
		
		
		float choiceY = height - height*0.02f;
		textSize(textFontSize);
		textFont(textFont);
		textAlign(CENTER);
		
		stroke(0);
		
		fill(150);
		if (selGenPred) fill(0);
		
		text("create " + genPred + " predator(s).", width/12 + 0, choiceY);
		fill(150);
		
		if (selRemoveConnec) fill(0);
		
		text("remove " + removeConnec + " connection(s).", width/12 + width/4, choiceY);
		fill(150);
		
		if (selStopAgent) fill(0);
		
		text("immobilize " + stopAgent + " agent(s).", width/12 + width/2, choiceY);
		fill(150);
		
		if(selFinalCluster)	fill(0);

		if(finalCluster == null){
			text("no community looked at", width/12 + (width/4)*3, choiceY);
			if(rectProceedAlpha > 0) rectProceedAlpha -= 10.0f;
		}else if(finalCluster != null){
			text("community selected", width/12 + (width/4)*3, choiceY);
			if(rectProceedAlpha < 200) rectProceedAlpha += 10.0f;
		}
		fill(0);

		

		for (int i = 0; i < predators.size(); i++) {
			Predator p = predators.get(i);
			if (p.isAlive){
				p.display();
			}
			if (!p.isAlive) {
				fill(10);
				line(p.pos.x-2, p.pos.y-2, p.pos.x+2, p.pos.y+2);
				line(p.pos.x-2, p.pos.y+2, p.pos.x+2, p.pos.y-2);
			}
		}

		for (int i = 0; i < connections.size(); i++) {
			Connection c = connections.get(i);
			c.display();
			c.destroyConnection();
		}
		

		
		onHover(1);
		dragAgent();

		// ------------------------------------------ END CONDITIONS
		if (millis() > startPhaseTimer) {
			// if there are not enough agents left to create a
			int maxConnec = 0;
			int agentsAlive = 0;

			boolean agentsWithMaxConnecLeft = false;

			for (int i = 0; i < agents.length; i++) {
				maxConnec = max(maxConnec, agents[i].connecMax);
				if (agents[i].isAlive && agents[i].connec == agents[i].connecMax) agentsAlive++;

				if (agents[i].connec == agents[i].connecMax) agentsWithMaxConnecLeft = true;
			}

			if (agentsAlive <= maxConnec && !startDeathSentence) { 
				// println("not enough agents to form a new cluster");
				// deathSentence();
				// endGame = true;
				// inGame = false;
			}

			if (!agentsWithMaxConnecLeft) {
				// println("no agents with max connec left");
				canEndGame1 = true;

			}

			if (totalConnections <= 2) {
				// println("total conections left less than 2");
				canEndGame1 = true;
			}
		}
		
		for (int i = 0; i < agents.length; i++) {
			if (agents[i].isAlive && agents[i].arrived){
				agents[i].display();
				agents[i].debug();
			}else if(!agents[i].isAlive && agents[i].arrived){
				strokeWeight(1);
				noFill();
				ellipse(agents[i].pos.x, agents[i].pos.y, agents[i].rad*0.75f, agents[i].rad*0.75f);
				strokeWeight(2);
				line(agents[i].pos.x-4, agents[i].pos.y-4, agents[i].pos.x+4, agents[i].pos.y+4);
				line(agents[i].pos.x-4, agents[i].pos.y+4, agents[i].pos.x+4, agents[i].pos.y-4);
			}
		}
		
		rectMode(CORNER);
		textFont(thoughtFont);
		textSize(thoughtFontSize);
		if(showGenPredInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("click anywhere on the screen to make a predator appear.", rectBorderX*2.1f, rectBorderY+(rectBorderH*0.99f));
		}
		
		if(showStopAgentInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("click on a connection to sever the bond between to individuals.", rectBorderX*122.0f, rectBorderY+(rectBorderH*0.99f));
		}
		
		if(showRemoveConnectionInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("click on an individuals to prevent him from moving, forever.", rectBorderX*242.0f, rectBorderY+(rectBorderH*0.99f));
		}
		
		if(showSelFinalClusterInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("click on a group of individuals whose future you'd like to witness.", rectBorderX*352.0f, rectBorderY+(rectBorderH*0.99f));
		}
		
		fill(255, rectProceedAlpha);
		noStroke();
		rectMode(CENTER);
		textAlign(CENTER);
		rect(width*0.5f, height*0.5f, width*0.992f, height*0.1f);
		fill(0, rectProceedAlpha);
		textFont(headerFont);
		textSize(headerFontSize);
		text("press space to proceed", width*0.5f, height*0.5f);
	}
	
	void drawVoronoi(int stage){
		if(stage == 1){
			float[][] voronoiPoints;
			ArrayList<Cluster> clustersAppeared;
			clustersAppeared = new ArrayList<Cluster>();
			
			
			for(int i = 0; i < totalClusters.size(); i++){
				Cluster c = totalClusters.get(i);
				boolean clusterHasAppeared = true;
				for(int j = 0; j < c.agentsInside.size(); j++){
					Agent a = c.agentsInside.get(j);
					
					if(!a.arrived){
						clusterHasAppeared = false;
					}
				}
				if(clusterHasAppeared && !clustersAppeared.contains(c)){
					clustersAppeared.add(c);
				}
			}
			
			voronoiPoints = new float [clustersAppeared.size()][2];
			
			for(int i = 0; i < clustersAppeared.size(); i++){
				Cluster c = clustersAppeared.get(i);
					voronoiPoints[i][0] = c.getAveragePos().x;
					voronoiPoints[i][1] = c.getAveragePos().y;
			}
			
			voronoi = new Voronoi(voronoiPoints);
			
			float[][] voronoiEdges = voronoi.getEdges();
			
			for(int i = 0; i < voronoiEdges.length; i++){
				float x1 = voronoiEdges[i][0];
				x1 = PApplet.constrain(x1, rectBorderX, width-rectBorderX);
				float y1 = voronoiEdges[i][1];
				y1 = PApplet.constrain(y1, rectBorderY, rectBorderH);
				
				float x2 = voronoiEdges[i][2];
				x2 = PApplet.constrain(x2, rectBorderX, width-rectBorderX);
				float y2 = voronoiEdges[i][3];
				y2 = PApplet.constrain(y2, rectBorderY, rectBorderH);
				
				stroke(0, 15);
				line(x1, y1, x2, y2);
			}
		}else if(stage == 3){
			float[][] voronoiPoints;

			voronoiPoints = new float [others.size()][2];
			
			for(int i = 0; i < others.size(); i++){
				Nation n = others.get(i);
					voronoiPoints[i][0] = n.pos.x;
					voronoiPoints[i][1] = n.pos.y;
			}
			
			voronoi = new Voronoi(voronoiPoints);
			
			float[][] voronoiEdges = voronoi.getEdges();
			
			for(int i = 0; i < voronoiEdges.length; i++){
				float x1 = voronoiEdges[i][0];
				x1 = PApplet.constrain(x1, rectBorderX, width-rectBorderX);
				float y1 = voronoiEdges[i][1];
				y1 = PApplet.constrain(y1, rectBorderY, rectBorderH);
				
				float x2 = voronoiEdges[i][2];
				x2 = PApplet.constrain(x2, rectBorderX, width-rectBorderX);
				float y2 = voronoiEdges[i][3];
				y2 = PApplet.constrain(y2, rectBorderY, rectBorderH);
				
				stroke(0, 15);
				line(x1, y1, x2, y2);
			}
		}
		
	}
	
	void endGame_temp(){
		for (int i = 0; i < agents.length; i++) {
			if (agents[i].isAlive)
				agents[i].display();
		}

		for (int i = 0; i < connections.size(); i++) {
			Connection c = connections.get(i);
			c.display();
			c.destroyConnection();
		}

		if (canDrawEndScreen) {
			fill(255, endAlpha);
			noStroke();
			//rect(3, 3, width - 6, height - 6);
		}

		if (endAlpha < 100) endAlpha++;

		fill(0);
		textSize(textFontSize);
		text("they have chosen to gather in "+ totalClusters.size()+ "communities.\n(this function is broken, please disregard thanks lol)", width / 2, height / 3.2f);

		ArrayList<Agent> deadAgents = new ArrayList<Agent>();
		ArrayList<Agent> killedAgents = new ArrayList<Agent>();
		ArrayList<Agent> killerAgents = new ArrayList<Agent>();

		for (int i = 0; i < agents.length; i++) {

			agents[i].movement.x = 0;
			agents[i].movement.y = 0;

			if (!agents[i].isAlive && !agents[i].isKilled) {
				deadAgents.add(agents[i]);
			}

			if (!agents[i].isAlive && agents[i].isKilled) {
				killedAgents.add(agents[i]);
			}

			if (agents[i].isAlive && agents[i].isKiller) {
				killerAgents.add(agents[i]);
			}
		}

		text("it happened over the course of " + seasons.numberOfCycles + " seasons.", width / 2, height / 2.2f);

		if (killedAgents.size() > 1) {
			text(killedAgents.size() + " of them were killed.", width / 2, height / 2.6f);
		} else if (killedAgents.size() == 1) {
			text(killedAgents.size() + " of them was killed.", width / 2, height / 2.6f);
		} else {
			text("none of them were killed.", width / 2, height / 2.6f);
		}

		if (killerAgents.size() > 1) {
			text(killerAgents.size() + " killers are alive.", width / 2, height / 2.4f);
		} else if (killerAgents.size() == 1) {
			text("one killer is alive.", width / 2, height / 2.4f);
		}

		if (deadAgents.size() > 0) {
			text("there has been " + deadAgents.size() + " death(s).", width / 2, height / 2); // give the number of deaths
			if (goalInt == 0) {
				text("you thought no one would die", width / 2, (height / 3) * 2); // FAIL for no deaths
			} else if (goalInt == 1 && deadAgents.size() < 4){
				text("a necessary sacrifice", width / 2, (height / 3) * 2); // SUCCESS
			} else if (goalInt == 2 && deadAgents.size() > 4 && deadAgents.size() <= 8) {
				text("a hefty toll to give meaning to their co-existence", width / 2, (height / 3) * 2); // SUCCESS for a lot										
			} else if (goalInt == 3 && deadAgents.size() > 8) {
				text("there might be enough left to celebrate", width / 2, (height / 3) * 2); // SUCCESS for a SHITLOAD of
			} else {
				text("too many deaths.", width / 2, (height / 3) * 2);
			}
		} else {
			text("no one died.", width / 2, height / 2);
			if (goalInt == 0) {
				text("they achieved your goal", width / 2, (height / 3) * 2);
			} else if (goalInt > 0){
				text("they achieved their goal", width / 2, (height / 3) * 2); // fail for goal 1 & 2 & 3
		}
}
	}
	
	void endGame(){
		background(255);
		stroke(0);
		strokeWeight(1);
		rect(width*0.00375f, height*0.00333f, width*0.99625f, height*0.99666f);
		rect(width*0.00750f, height*0.00666f, width*0.99250f, height*0.98666f);
		
		fill(0);
		textSize(mainFontSize);
		textFont(mainFont);
		textAlign(CENTER);
		text("On "+storyTitle1, width/2, height/10);
		
		textSize(textFontSize);
		textFont(textFont);
		textAlign(LEFT);
		
		if(randGen3){
			totCoRND = (int) random(totCo.length);
			coMaxRND = (int) random(connecMax.length);
			isTamRND = (int) random(isTamer.length);
			coTDRND = (int) random(coToDead.length);
			isKRND = (int) random(isKill.length);
			cTKRND = (int) random(coToKill.length);
			isHRND = (int) random(isHun.length);
			taRND = (int) random(timeAlone.length);
			hasBRRND = (int) random(hasBeenRej.length);
			hasRRND = (int) random(hasRej.length);
			hasPrRND = (int) random(hasProt.length);
			isCTHRND = (int) random(isCoToHun.length);
			hasDisRND = (int) random(hasDist.length);
			
			totCoRNDX = (int) random(totCoX.length);
			coMaxRNDX = (int) random(connecMaxX.length);
			isTamRNDX = (int) random(isTamerX.length);
			coTDRNDX = (int) random(coToDeadX.length);
			isKRNDX = (int) random(isKillX.length);
			cTKRNDX = (int) random(coToKillX.length);
			isHRNDX = (int) random(isHunX.length);
			taRNDX = (int) random(timeAloneX.length);
			hasBRRNDX = (int) random(hasBeenRejX.length);
			hasRRNDX = (int) random(hasRejX.length);
			hasPrRNDX = (int) random(hasProtX.length);
			isCTHRNDX = (int) random(isCoToHunX.length);
			hasDisRNDX = (int) random(hasDistX.length);
			
			randGen3 = false;
			if(selectedAgent != null) portraitRad = map(selectedAgent.rad, 20, 30, width*0.1f, width*0.15f);
		}
		
		if(finalCluster != null){
				if(selectedAgent != null && (!selectedAgent.isAlive || selectedAgent.isKiller)){
					fill(255, 0, 0, textAlpha);
				}else{
					fill(0, textAlpha);
				}
					//getting all the parts into one big string
					endGame1_text = "";
					
					if(selectedAgent.totalConnectionsMade > 5){
						endGame1_text = endGame1_text+totCo[totCoRND]+" ";
					}else{
						endGame1_text = endGame1_text+totCoX[totCoRNDX]+" ";
					}
					
					if(selectedAgent.connecMax > 3){
						endGame1_text = endGame1_text+connecMax[coMaxRND]+" ";
					}else{
						endGame1_text = endGame1_text+connecMaxX[coMaxRNDX]+" ";
					}
					
					if(selectedAgent.isTamer){
						endGame1_text = endGame1_text+isTamer[isTamRND]+" ";
					}else{
						endGame1_text = endGame1_text+isTamerX[isTamRNDX]+" ";
					}
					
					
					if(selectedAgent.isConnectedToDead){
						endGame1_text = endGame1_text+coToDead[coTDRND]+" ";
					}else{
						endGame1_text = endGame1_text+coToDeadX[coTDRNDX]+" ";
					}
					
					if(selectedAgent.isKiller){
						endGame1_text = endGame1_text+isKill[isKRND]+" ";
					}else{
						endGame1_text = endGame1_text+isKillX[isKRNDX]+" ";
					}
					
					if(selectedAgent.isConnectedToKiller){
						endGame1_text = endGame1_text+coToKill[cTKRND]+" ";
					}else{
						endGame1_text = endGame1_text+coToKillX[cTKRNDX]+" ";
					}
					
					if(selectedAgent.isHunter){
						endGame1_text = endGame1_text+isHun[isHRND]+"\n\n";
					}else{
						endGame1_text = endGame1_text+isHunX[isHRNDX]+"\n\n";
					}
					
					if(selectedAgent.maxTimeWithoutRelationship > 5000.0f){
						endGame1_text = endGame1_text+timeAlone[taRND]+" ";
					}else{
						endGame1_text = endGame1_text+timeAloneX[taRNDX]+" ";
					}
										
					if(selectedAgent.hasBeenRejected > 0){
						endGame1_text = endGame1_text+hasBeenRej[hasBRRND]+" ";
					}else{
						endGame1_text = endGame1_text+hasBeenRejX[hasBRRNDX]+" ";
					}
					
					if(selectedAgent.hasRejected > 0){
						endGame1_text = endGame1_text+hasRej[hasRRND]+" ";
					}else{
						endGame1_text = endGame1_text+hasRejX[hasRRNDX]+" ";
					}

					if(selectedAgent.hasProtected > 0){
						endGame1_text = endGame1_text+hasProt[hasPrRND]+" ";
					}else{
						endGame1_text = endGame1_text+hasProtX[hasPrRNDX]+" ";
					}
					
					if(selectedAgent.isConnectedToHunter){
						endGame1_text = endGame1_text+isCoToHun[isCTHRND]+" ";
					}else{
						endGame1_text = endGame1_text+isCoToHunX[isCTHRNDX]+" ";
					}
					
					if(selectedAgent.hasDisturbed > 0){
						endGame1_text = endGame1_text+hasDist[hasDisRND]+" ";
					}else{
						endGame1_text = endGame1_text+hasDistX[hasDisRNDX]+" ";
					}
					
					text(endGame1_text, width/5, height/5, (width/5)*3, 19*(height/20));
					
					//drawing part
					if(selectedAgent.isKiller){
						stroke(200, 50, 50);
					}else if(selectedAgent.isHunter){
						stroke(150, 0, 0);
					}else if(selectedAgent.hasProtected > 0){
						stroke(100, 100, 0);
					}else{
						stroke(portraitCol);
					}
					
					noFill();
					pushMatrix();
					translate(portraitPos.x, portraitPos.y);
					for(int i = 0; i < selectedAgent.connecMax; i++){
						ellipse(0, 0, (i+1)*15 + cos(portraitPulse)*0.2f, (i+1)*15 + cos(portraitPulse)*0.2f);
					}
					strokeWeight(8);
					ellipse(0, 0, portraitRad, portraitRad);
					popMatrix();
					
					portraitRad += cos(portraitPulse)*0.5f;
					portraitPulse += portraitPulseInc*1.0f;
			}else{
				text("no cluster chosen", width/2, height/2);
				
				stroke(portraitCol);
				strokeWeight(3);
				noFill();
				pushMatrix();
				translate(portraitPos.x, portraitPos.y);
				for(int i = 0; i < 5; i++){
					ellipse(0, 0, (i+1)*15 + cos(portraitPulse)*0.2f, (i+1)*15 + cos(portraitPulse)*0.2f);
				}
				strokeWeight(8);
				ellipse(0, 0, portraitRad, portraitRad);
				popMatrix();
				portraitRad += cos(portraitPulse)*0.5f;
				portraitPulse += portraitPulseInc;
			}
		
		if(textAlpha < 254) textAlpha += 1.5f;
		
		strokeWeight(2);
		stroke(10, 210);
		noFill();
		rect(proceedX, proceedY, proceedW, proceedH);
		textFont(headerFont);
		textSize(headerFontSize);
		fill(0);
		text("Proceed", proceedX*1.01f, proceedY*1.0475f);	
	}
	
	void generateCommunalHistory(){
		//here we generate the common history
		background(250);
		stroke(0);
		strokeWeight(1);
		rect(3, 3, width-6, height-6);
		rect(6, 6, width-12, height-12);
		
		textFont(mainFont);
		textSize(mainFontSize);
		textAlign(CENTER);
		fill(0);
		text("COMMUNAL HISTORY", width/2, height/8);
		
		String finalCommunityHistory = "";
		
		//first part is generate the strings related to rules and set-ups.
		if(communalHistoryRndGenerator){
			//numbers for the general rules
			
			goal0RND = (int) random(goal0.length);
			goal1RND = (int) random(goal1.length);
			goal2RND = (int) random(goal2.length);
			goal3RND = (int) random(goal3.length);
			
			fsrRND = (int) random(formStabRel.length);
			cDRND = (int) random(canDist.length);
			cPRND = (int) random(canPro.length);
			cfRND = (int) random(canFight.length);
			crdRND = (int) random(canRememberDead.length);
			ccRND = (int) random(canConnec.length);
			
			fsrRNDX = (int) random(formStabRelX.length);
			cDRNDX = (int) random(canDistX.length);
			cPRNDX = (int) random(canProX.length);
			cfRNDX = (int) random(canFightX.length);
			crdRND = (int) random(canRememberDeadX.length);
			ccRNDX = (int) random(canConnecX.length);
			
			communalHistoryRndGenerator = false;
		}
			
			if(goalInt == 0){
				finalCommunityHistory = finalCommunityHistory+goal0[goal0RND]+"\n\n";
			}else if(goalInt == 1){
				finalCommunityHistory = finalCommunityHistory+goal1[goal1RND]+"\n\n";
			}else if(goalInt == 2){
				finalCommunityHistory = finalCommunityHistory+goal2[goal2RND]+"\n\n";
			}else if(goalInt == 3){
				finalCommunityHistory = finalCommunityHistory+goal3[goal3RND]+"\n\n";
			}
			 
			
			if(canFormStableRelationships){
				finalCommunityHistory = finalCommunityHistory+formStabRel[fsrRND]+" ";
			}else{
				finalCommunityHistory = finalCommunityHistory+formStabRelX[fsrRNDX]+" ";
			}
			if(canDisturb){
				finalCommunityHistory = finalCommunityHistory+canDist[cDRND]+" ";
			}else{
				finalCommunityHistory = finalCommunityHistory+canDistX[cDRNDX]+" ";
			}
			if(canProtect){
				finalCommunityHistory = finalCommunityHistory+canPro[cPRND]+" ";
			}else{
				finalCommunityHistory = finalCommunityHistory+canProX[cPRNDX]+" ";
			}
			if(canRememberFallen){
				finalCommunityHistory = finalCommunityHistory+canRememberDead[crdRND]+"\n\n";
			}else{
				finalCommunityHistory = finalCommunityHistory+canRememberDeadX[crdRNDX]+"\n\n";
			}
			
			
			if(violence > 0.6f){
				finalCommunityHistory = finalCommunityHistory+canFight[cfRND]+" ";
			}else if(violence == 0.5f){
				finalCommunityHistory = finalCommunityHistory+canFightX[cfRNDX]+" ";
			}else{
				//TODO place holder, add another text file
				finalCommunityHistory = finalCommunityHistory+"It seemed everyone decided not to fight. We decided to focus our energy on connecting with others."+" ";
			}
			
			//TODO Add one more text file?
			if(connec > 4){
				finalCommunityHistory = finalCommunityHistory+canConnec[ccRND]+" ";	
			}else if(connec <= 4){
				finalCommunityHistory = finalCommunityHistory+canConnecX[ccRNDX]+" ";
			}
			
			fill(0, textAlpha);
			textFont(headerFont);
			textSize(headerFontSize);
			textAlign(LEFT);
			text(finalCommunityHistory, width/6, height/5, 4*(width/6), 4*(height/5));
			
			if(textAlpha < 254) textAlpha += 2f;
			
			strokeWeight(2);
			stroke(10, 210);
			noFill();
			rect(proceedX, proceedY, proceedW, proceedH);
			textFont(subtitleFont);
			textSize(subtitleFontSize);
			fill(0);
			text("Proceed", proceedX*1.01f, proceedY*1.0475f);
		}
		

	void removeSuperfluousAgents(Cluster cl){ //this function compares every agent in the cluster with each other and removes any duplicates DEPRECATED
		for(int i = 0; i < cl.agentsInside.size(); i++){
			Agent a1 = cl.agentsInside.get(i);
			
			for(int j = 1; j < cl.agentsInside.size(); j++){
				Agent a2 = cl.agentsInside.get(j);
				
				if(a2 == a1){
					cl.agentsInside.remove(a2);
					j--;
				}
			}
		}
	}

	void deathSentence() { // once there are not enough agents left, give them a death sentence
							
		for (int i = 0; i < agents.length; i++) {
			if (agents[i].isAlive && agents[i].connec == agents[i].connecMax) {
				agents[i].timerDeath = random(5, 10) * 1000;
			}
		}
		startDeathSentence = true;
	}
	
	void onHover(int stage){//takes an argument to make sure at what stage it is to display relevant info and i don't have to write the same for/if statement over and over and over and over again
		
		if(stage == 1){
			hovering = false;
			
			for(int i = 0; i < agents.length; i++){
				if(dist(mouseX, mouseY, agents[i].pos.x, agents[i].pos.y) < agents[i].rad && agents[i].isAlive && agents[i].arrived){
					hovering = true;
					hoverAgent = agents[i];
					pushMatrix();
					translate(hoverAgent.pos.x, hoverAgent.pos.y);
					rotate(PI/4);
					rectMode(CENTER);
					stroke(100, 100);
					strokeWeight(5);
					noFill();
					rect(0, 0, hoverAgent.rad, hoverAgent.rad);
					strokeWeight(2);
					popMatrix();
					if(hoverTextAlpha < 150) hoverTextAlpha += hoverTextAlphaRate;
				}
			 }
			
			for(int i = 0; i < predators.size(); i++){
				Predator p = predators.get(i);
				textFont(thoughtFont);
				textSize(thoughtFontSize);
				if(dist(mouseX, mouseY, p.pos.x, p.pos.y) < p.size && p.isAlive){
					p.sw = 2;
					if(p.isDomesticated){
						text("the beast has been tamed", p.pos.x*1.01f, p.pos.y*0.99f);
					}else if(p.hungerDuration > 0){ //has eaten
						text("the beast is no longer hungry", p.pos.x*1.01f, p.pos.y*0.99f);
					}else{
						text("the beast is looking for preys", p.pos.x*1.01f, p.pos.y*0.99f);
					}
				}else{
					p.sw = 1;
				}
			}
			
			if(!hovering){
				if(hoverTextAlpha > 0) hoverTextAlpha -= hoverTextAlphaRate;
				if(hoverTextAlpha <= 0) hoverAgent = null;
			}
			
			String thought;
			
			if(hoverCanGenerateRand) hoverRand = random(1);
			
			if(hoverAgent != null){
				hoverCanGenerateRand = false;
				textAlign(LEFT);
				textFont(thoughtFont);
				textSize(thoughtFontSize);
				fill(0, hoverTextAlpha);
				text(hoverAgent.computeThought(1, hoverRand), hoverAgent.pos.x + hoverAgent.rad, hoverAgent.pos.y);
			}else{
				hoverTextAlpha = 0;
				hoverCanGenerateRand = true;
			}
		}
			
			if(stage == 2){
				
				hovering = false;
				
				for(int i = 0; i < community.size(); i++){
					Agent a = community.get(i);
					if(dist(mouseX, mouseY, a.pos.x, a.pos.y) < a.rad && a.isAlive){
						a.speak();
						if(hoverTextAlpha < 150) hoverTextAlpha += hoverTextAlphaRate;
						hovering = true;
						hoverAgent = a;
						for(int j = 0; j < community.size(); j++){
							Agent a2 = community.get(j);
							if(a2 != a) a2.canTalk = true;
						}
					}
				}
				
				if(!hovering){
					if(hoverTextAlpha > 0) hoverTextAlpha -= hoverTextAlphaRate;
					if(hoverTextAlpha <= 0) hoverAgent = null;
				}
				
				if(hoverCanGenerateRand) hoverRand = random(1);

				if(hoverAgent != null){
					hoverCanGenerateRand = false;
					textAlign(LEFT);
					textFont(thoughtFont);
					textSize(thoughtFontSize);
					fill(0, hoverTextAlpha);
					text(hoverAgent.computeThought(stage, hoverRand), hoverAgent.pos.x + hoverAgent.rad, hoverAgent.pos.y);
				}else{
					hoverCanGenerateRand = true;
				}
				
				//this is the hovering for connections
				for(int i = 0; i < connectionsPower.size(); i++){
					ConnectionPower cP = connectionsPower.get(i);
					Agent start;
					Agent end;
					if(cP.a1.pos.x > cP.a2.pos.x){
						start = cP.a1;
						end = cP.a2;
					}else{
						start = cP.a2;
						end = cP.a1;
					}
					
					PVector a = PVector.sub(mouse, start.pos);
					PVector b = PVector.sub(end.pos, start.pos);
					
					b.normalize();
					b.mult(a.dot(b));
					PVector normalPoint = PVector.add(start.pos, b);
					

					float distance = PVector.dist(mouse, normalPoint);
					
					if(distance < 10 && dist(mouseX, mouseY, cP.a1.pos.x, cP.a1.pos.y) > cP.a1.rad*1.1f && dist(mouseX, mouseY, cP.a2.pos.x, cP.a2.pos.y) > cP.a2.rad*1.1f){
						fill(0, 75);
						String cPText = "";
						textFont(thoughtFont);
						textSize(thoughtFontSize);
						if(cP.powerDifference < 1){
							cPText = "the slightest power struggle";
						}else if(cP.powerDifference < 3){
							cPText = "one is stronger than the other";
						}else{
							cPText = "one is being savagely oppressed";
						}
						text(cPText, cP.averagePos.x, cP.averagePos.y);
					}
				}
			 }
			
			if(stage == 3){
				for(int i = 0; i < others.size(); i++){
					Nation n = others.get(i);
					
					if(dist(mouseX, mouseY, n.pos.x, n.pos.y) < n.rad){
						n.hullWeight = 4;
						textFont(thoughtFont);
						textSize(thoughtFontSize);
						if(!n.hovered) randomStatement  = (int)random(n.possibleStatements.size());
						fill(0);
						n.hovered = true;
						text(n.statement(randomStatement), n.pos.x, n.pos.y);
					}else{
						n.hovered = false;
						n.hullWeight = 2;
					}
				}
			}
			noFill();
		}

	void generateAgents(int numberOfAgents) { // this function is called once when at the launch of the startScreen (either 1 or 2);
		rad = 20 + (int) (random(0, 5));
		if(seasons == null) seasons = new Seasons(this);
		if(startGame2 && finalCluster != null){// this is the normal situation
			float minX = 2000;
			float maxX = -100;
			float minY = 2000;
			float maxY = -100;
			Agent wealthiestAgent = null;
			float maxWealth = 0;
			
			mapMin = map(finalCluster.agentsInside.size(), 2, 15, 0.4f, 0.2f);
			mapMax = map(finalCluster.agentsInside.size(), 2, 15, 0.6f, 0.8f);
			
			for(int i = 0; i < finalCluster.agentsInside.size(); i++){//getting the positions to map
				Agent a = finalCluster.agentsInside.get(i);
				if(a.pos.x < minX) minX = a.pos.x;
				if(a.pos.x > maxX) maxX = a.pos.x;
				if(a.pos.y < minY) minY = a.pos.y;
				if(a.pos.y > maxY) maxY = a.pos.y;
			}

			
			for(int i = 0; i < finalCluster.agentsInside.size(); i++){
				Agent a = finalCluster.agentsInside.get(i);
				a.pos.x = map(a.pos.x, minX, maxX, width*mapMin, width*mapMax);
				a.pos.y = map(a.pos.y, minY, maxY, height*mapMin, height*mapMax);
				a.isAlive = true;
				a.startTimeDeath2 = millis();
				a.moveCoeff = 1;
				a.velocity = new PVector(random(-1.5f, 1.5f), random(-1.5f, 1.5f));
				community.add(a);
			}

			for(int i = 0; i < resources.size(); i++){
				Resource r = resources.get(i);
				for(int j = 0; j < community.size(); j++){
					Agent a = community.get(j);
					if(PVector.dist(r.pos, a.pos) < r.rad*1.5f){
						if(!resources2.contains(r)) resources2.add(r);
					}
				}
			}
			
			println(resources2.size());
			
			float minRX = width;
			float maxRX = 0;
			float minRY = height;
			float maxRY = 0;
			
			for(int i = 0 ; i < resources2.size(); i++){
				Resource r = resources2.get(i);
				if(r.pos.x < minRX) minRX = r.pos.x;
				if(r.pos.x > maxRX) maxRX = r.pos.x;
				if(r.pos.y < minRY) minRY = r.pos.y;
				if(r.pos.y > maxRY) maxRY = r.pos.y;
			}
			
			for(int i = 0; i < resources2.size(); i++){
				Resource r = resources2.get(i);
				r.rad *= 1.5f;
				if(resources2.size() > 1){
					r.pos.x = map(r.pos.x, minRX, maxRX, width*0.3f, width*0.7f);
					r.pos.y = map(r.pos.y, minRY, maxRY, height*0.3f, height*0.7f);
				}
				println(r.pos);
			}
			
			for(int i = 0; i < community.size(); i++){ //this is where i modify the variables of the agents based on start2 decisions
				Agent a = community.get(i);

				a.col = color(50);
				a.alpha = 255;
				a.powerModifier = powerForce;
				a.friendshipModifier = friendshipForce;
				a.loveModifier = loveForce;
				a.ageMajority = millis() + agents[i].lifeSpan*ageModifier;
				a.numberOfChildren = numberOfChildren;
				a.independenceOfChildren = independenceOfChildren;
				a.moveCoeff = 1.0f;
				a.startTimeDeath2 = millis();
				if(cultureOrigin == 1){
					Agent.cultSimRequirement = 7; //it is 5 by default, so that means that people will have more friends
					a.cultureVariationFriendship = 0.01f; //the default value is 0.005f
				}
				if(cultureOrigin == 2){
					a.socialEnvironmentVariation = 0.01f; //the default value is 0.005f
				}
			}

		}else if(startGame2 && finalCluster == null){ //debug mode to startgame2
			for (int i = 0; i < numberOfAgents; i++) {
				agents[i] = new Agent(random(rad, width - rad), random(rad, height - rad - height/20), rad, 5, -2, 2, 100, canFormStableRelationships, canDisturb, canProtect, violence, 1.0f, this);
				
				//this is where i modify the variables of the agents based on start2 decisions
				agents[i].powerModifier = powerForce;
				agents[i].friendshipModifier = friendshipForce;
				agents[i].loveModifier = loveForce;
				agents[i].ageMajority = millis() + agents[i].lifeSpan*ageModifier;
				agents[i].numberOfChildren = numberOfChildren;
				agents[i].independenceOfChildren = independenceOfChildren;
				
				agents[i].startTimeDeath2 = millis();
				
				community.add(agents[i]);
			}
		}else{ //start game = 1 
			for (int i = 0; i < numberOfAgents; i++) {
				agents[i] = new Agent(random(rad, width - rad), random(rad, height - rad - height/20), rad, connec, -2, 2, 100, canFormStableRelationships, canDisturb, canProtect, violence, resourceSeek, this);
			}
		}
	}
	
	float estimateWealth(ArrayList<Agent> al){
		float accumulatedWealth = 0;
		
		for(int i = 0; i < al.size(); i++){
			Agent a = al.get(i);
			accumulatedWealth += a.wealthAccumulation;
		}
		
		return accumulatedWealth;
	}
	
	float estimateCulture(ArrayList<Agent> al){
		float culture = 0;
		float cultureAverage = 0;
		for(int i = 0; i < al.size(); i++){
			Agent a = al.get(i);
			for(int j = 0; j < a.culture.length; j++){
				cultureAverage += a.culture[j];
			}
			cultureAverage = cultureAverage / a.culture.length;
			culture += cultureAverage;
		}
		
		culture = culture/al.size();
		
		return culture;
	}
	
	void generateNation(){
		if(seasons == null) seasons = new Seasons(this);
		if(community.size() == 0){
			println("generating out of nowhere");
			// pos / friendship / power / love / population / culturalHomogeneity / canFight / canProtect / connections / craveForWealth / wealth / isNation / papplet
			nation = new Nation(new PVector(width*0.5f, height*0.5f), 0.5f, 0.5f, 0.5f, 20.0f, 0.5f, 1.0f, true, 50, 0.5f, 100, true, this);
			nation.culturalRequirementAlliance *= allianceModifier;
			nation.culturalRequirementTrade *= tradeModifier;
			nation.culturalRequirementWar *= warModifier;
			nation.goal = nationGoal;
			nation.victoryBehaviour = victoryBehaviour;
			nation.tradeLimit = tradeLimit;
			nation.allianceTrust = allianceTrust;
			/*
			nation.cultureModifierAlliance *= cultureExternal;
			nation.cultureModifierWar *= cultureExternal;
			nation.cultureModifierTrade *= cultureExternal;
			*/
		}else{
			float tempWealth = random(50, 200);
			if(estimateWealth(community) != 0) tempWealth = estimateWealth(community);
			
			nation = new Nation(new PVector(width*0.5f, height*0.5f), friendshipForce*0.5f, powerForce*0.5f, map(loveForce, 40, 60, 0.1f, 0.8f), community.size(), estimateCulture(community), violence, canProtect, connec*10, resourceSeek, tempWealth, true, this);
			println("NATION STATUS");
			println("---");
			println("friendship: "+friendshipForce*0.5f);
			println("power: "+powerForce*0.5f);
			println("love: "+map(loveForce, 40, 60, 0.1f, 0.8f));
			println("population: "+community.size());
			println("culture: "+estimateCulture(community));
			println("connections: "+connec*10);
			println("wealth: "+estimateWealth(community));			
		}
	}
	
	void generateOtherNations(){		
		numberOfOthers = (int) (int)random(4, 12);
		
		
		for(int i = 0; i < numberOfOthers; i++){
			int pop = (int)random(10, 50);
			
			float p = random(othersPositions.size());
			PVector pos = othersPositions.get((int) p);
			othersPositions.remove((int) p);
			

			float fight = random(1);
			
			float q = random(1);
			boolean protect;
			if(q > 0.5f){
				protect = true;
			}else{
				protect = false;	
			}
			
			float cultureN = nation.culturalHomogeneity += random(-1.75f, 1.75f);
			
			// pos / friendship / power / love / population / culturalHomogeneity / canFight / canProtect / connections / craveForWealth / wealth / isNation / papplet
			Nation n = new Nation(pos, random(1), random(1), random(0.2f, 0.8f), pop, random(1), fight, protect, pop*((int)random(3, 6)), random(1), random(50, 200), false, this);
			//the whole point might be to randomiwe these?
			n.culturalRequirementAlliance *= allianceModifier;
			n.culturalRequirementTrade *= tradeModifier;
			n.culturalRequirementWar *= warModifier;
			n.goal = nationGoal;
			n.victoryBehaviour = victoryBehaviour;
			n.tradeLimit = tradeLimit;
			n.allianceTrust = allianceTrust;
			/*
			n.cultureModifierAlliance *= cultureExternal;
			n.cultureModifierWar *= cultureExternal;
			n.cultureModifierTrade *= cultureExternal;
			*/
			others.add(n);
			if(!others.contains(nation)) others.add(nation);
		}
	}

	void generatePredators() {
		for (int i = 0; i < numberOfPredators; i++) {
			predators.add(new Predator(random(width), random(height-height/20), 10, -2, 2, random(80, 105) * 1000, random(4, 6) * 10, this));
		}
	}

	int countConnections() { // counts the total connections left of rogue agents
		int t = 0; // start at 0;
		for (int i = 0; i < agents.length; i++) {
			// go through all the agents
			if (agents[i].isAlive && agents[i].connec == agents[i].connecMax)
				t += agents[i].connec; // add up all the connections left
		}
		return t;
	}
	
	float culturalHomogeneity(){
		float c = 0;
		float culturalHomogeneityThreshold = 1;
		
		for(int i = 0; i < community.size(); i++){ //take one agent
			Agent a1 = community.get(i);
			
			for(int j = 0; j < community.size(); j++){ //compare it to another one
				Agent a2 = community.get(j);
				
				for(int k = 0; k < a1.culture.length; k++){ //compare each slot in culture
					if(a1 != a2){
						if(abs(a1.culture[k] - a2.culture [k]) > culturalHomogeneityThreshold){
							c++;
						}
					}
				}
			}
		}
		return c;
	}

	void startGame2() {
		rectProceedAlpha = 0.0f;
		rectMode(CORNER);
		rectCol = 245+(noise(t))*10; 
		fill(rectCol);
		t+= 0.4f;
		strokeWeight(2);
		rect(width*0.00375f, height*0.00333f, width*0.9925f, height*0.99334f);
		stroke(0);
		
		fill(15);
		textSize(mainFontSize);
		textFont(mainFont);
		textAlign(CENTER);
		text("SOCIAL CONTACT", width/2, height/10);
		
		textSize(subtitleFontSize);
		textAlign(CENTER);
		textFont(subtitleFont);
		text("II - growing together", width/2, height/7);
		
		rectMode(CENTER);
		fishTank();
		
		textFont(textFont);
		textSize(textFontSize);
		startButton2.display();
		startButton2.onClick();
		
		//left column
		textSize(headerFontSize);
		textFont(headerFont);
		text("Relations", leftColX, headerHeight);
		
		
		textFont(textFont);
		textSize(textFontSize);
		powerForceDownButton.display();
		powerForceDownButton.onClick();
		
		String powerForceText = "";
		if(powerForce == 1.0f){
			powerForceText = "barely noticeable";
		}else if(powerForce == 3.0f){
			powerForceText = "somewhat important";
		}else if(powerForce == 5.0f){
			powerForceText = "a normal part of their interaction";
		}else if(powerForce == 7.0f){
			powerForceText = "a major component of their lives";
		}else{
			powerForceText = "the only real relationship one can sustain";
		}
		
		text("power struggles between beings are\n"+powerForceText, leftColX, firstRowY);
		powerForceUpButton.display();
		powerForceUpButton.onClick();
		
		friendshipForceDownButton.display();
		friendshipForceDownButton.onClick();
		
		String friendshipForceText = "";
		if(friendshipForce == 1.0f){
			friendshipForceText = "hardly notice each other";
		}else if(friendshipForce == 3.0f){
			friendshipForceText = "pay little attention to one another";
		}else if(friendshipForce == 5.0f){
			friendshipForceText = "gravitate towards each other";
		}else if(friendshipForce == 7.0f){
			friendshipForceText = "gravitate towards each other";
		}else{
			friendshipForceText = "be as close as possible";
		}
		
		text("two culturally similar beings tend to\n"+friendshipForceText, leftColX, secondRowY);
		friendshipForceUpButton.display();
		friendshipForceUpButton.onClick();
		
		loveForceDownButton.display();
		loveForceDownButton.onClick();
		
		String loveForceText = "";
		if(loveForce == 40.0f){
			loveForceText = "almost non-existant";
		}else if(loveForce == 45.0f){
			loveForceText = "superficial";
		}else if(loveForce == 50.0f){
			loveForceText = "an essential component of life";
		}else if(loveForce == 55.0f){
			loveForceText = "something that can't be ignored";
		}else{
			loveForceText = "the strongest bond";
		}
		
		text("love is\n"+loveForceText, leftColX, thirdRowY);
		loveForceUpButton.display();
		loveForceUpButton.onClick();
		
		//right column
		textSize(headerFontSize);
		textFont(headerFont);
		text("Children", rightColX, headerHeight);
		
		
		textFont(textFont);
		textSize(textFontSize);
		
		ageMajorityDownButton.display();
		ageMajorityDownButton.onClick();
		
		String ageModifierText = "";
		if(ageModifier == 0.20f){
			ageModifierText = "they can reproduce as soon as they can";
		}else if(ageModifier == 0.25f){
			ageModifierText = "they shall reproduce in a timely manner";
		}else{
			ageModifierText = "reproduction comes last";
		}
		
		text(ageModifierText, rightColX, firstRowY);
		ageMajorityUpButton.display();
		ageMajorityUpButton.onClick();
		
		numberOfChildrenDownButton.display();
		numberOfChildrenDownButton.onClick();
		
		String numberOfChildrenText = "";
		if(numberOfChildren == 0.0f){
			numberOfChildrenText = "they will not bear any child";
		}else if(numberOfChildren == 1.0f){
			numberOfChildrenText = "they can only bear a single child";
		}else if(numberOfChildren == 2.0f){
			numberOfChildrenText = "they can bear a couple of children";
		}else if(numberOfChildren == 3.0f){
			numberOfChildrenText = "they can bear up to three children";
		}else if(numberOfChildren == 4.0f){
			numberOfChildrenText = "they can bear up to four children";
		}else{
			numberOfChildrenText = "they can bear as many as they can";
		}
		
		text(numberOfChildrenText, rightColX, secondRowY);
		numberOfChildrenUpButton.display();
		numberOfChildrenUpButton.onClick();
		
		independenceOfChildrenDownButton.display();
		independenceOfChildrenDownButton.onClick();
		
		String independenceOfChildrenText = "";
		if(independenceOfChildren == 1.0f){
			independenceOfChildrenText = "close to their birth parents";
		}else if(independenceOfChildren == 2.0f){
			independenceOfChildrenText = "within the group they were born in";
		}else{
			independenceOfChildrenText = "wherever they desire";
		}
		
		text("children will settle\n"+independenceOfChildrenText, rightColX, thirdRowY);
		independenceOfChildrenUpButton.display();
		independenceOfChildrenUpButton.onClick();
		
		//bottom
		textFont(textFont);
		textSize(textFontSize);
		String cultureText = "";
		
		//these are the two big assumptions - they don't really do anything but they allow people to think about it.
		if(cultureOrigin == 1){
			cultureText = "culture is the result of where someone lives";
			storyTitle2 = "territory";
		}else if(cultureOrigin == 2){
			cultureText = "culture is the result of with whom someone lives";
			storyTitle2 = "others";
		}else{
			cultureText = "culture is ever elusive";
			storyTitle2 = "uncertainty";
		}
		text(cultureText, width*0.5f, buttonGoalHeight);
		
		cultureButtonRight.display();
		cultureButtonRight.onClick();
		cultureButtonLeft.display();
		cultureButtonLeft.onClick();
		
		showAlpha = abs(cos(millis()*0.001f)*150)+100;
		
		textAlign(LEFT);
		if(canShowPower){
			fill(colorPower, showAlpha+100);
			drawFishPower = true;
		}else{
			fill(0, showAlpha);
			drawFishPower = false;
		}
		text("power", width*0.405f, height*0.329f);
		if(canShowFriendship){
			fill(colorFriendship, showAlpha+100);
			drawFishFriend = true;
		}else{
			fill(0, showAlpha);
			drawFishFriend = false;
		}
		text("friendships", width*0.475f, height*0.329f);
		if(canShowLove){
			fill(colorLove, showAlpha+100);
			drawFishLove = true;
		}else{
			fill(0, showAlpha);
			drawFishLove = false;
		}
		text("love", width*0.572f, height*0.329f);
		
	}
	
	void inGame2() {
		background(255);
		update2();
		fill(bgColBox, 50);
		bgColBox = lerpColor(bgColBox, newBgColBox, bgColBoxLerpSpeed);
		bgColBoxLerpSpeed = map((millis()-Seasons.startTime), 0, 6000.0f, 0, 1);
		stroke(0);
		strokeWeight(1);
		rect(rectBorderX, rectBorderY, rectBorderW, rectBorderH);
		strokeWeight(1);
		rect(width*0.00375f, height*0.00333f, width*0.9925f, height*0.99334f);
		
		//drawing the stratas of society eventually just made by the movement of agents
		strokeWeight(1);
		stroke(strataCol, strataAlpha*0.25f);
		
		//bezier curves top
		for(int i = 0; i < 20; i++){
			fill(255, 40-i);
			bezier(strata1Anchor1.x, strata1Anchor1.y*i*0.05f, strata1Control1.x, strata1Control1.y*i*0.05f, strata1Anchor2.x, strata1Anchor2.y*i*0.05f, strata1Control2.x, strata1Control2.y*i*0.05f);
		}
		strokeWeight(2);
		noFill();
		stroke(strataCol, strataAlpha);
		bezier(strata1Anchor1.x, strata1Anchor1.y, strata1Control1.x, strata1Control1.y, strata1Anchor2.x, strata1Anchor2.y, strata1Control2.x, strata1Control2.y);
		strokeWeight(1);
		
		//bezier middle-top
		stroke(strataCol, strataAlpha*0.25f);
		for(int i = 0; i < 20; i++){
			fill(255, 40-i);
			bezier(strata1Anchor1.x, strata1Anchor1.y*(1+(i*0.05f)), strata1Control1.x, strata1Control1.y*(1+(i*0.05f)), strata1Anchor2.x, strata1Anchor2.y*(1+(i*0.05f)), strata1Control2.x, strata1Control2.y*(1+(i*0.05f)));
		}
		
		
		//bezier bottom curves
		stroke(strataCol, strataAlpha*0.25f);
		for(int i = 0; i < 20; i++){
			fill(255, 40-i);
			bezier(strata2Anchor1.x, strata2Anchor1.y*(1-(i*0.025f)), strata2Control1.x, strata2Control1.y*(1-(i*0.025f)), strata2Anchor2.x, strata2Anchor2.y*(1-(i*0.025f)), strata2Control2.x, strata2Control2.y*(1-(i*0.025f)));
		}
		
		//beizer middle-bottom
		stroke(strataCol, strataAlpha*0.25f);
		for(int i = 0; i < 20; i++){
			fill(255, 40-i);
			bezier(strata2Anchor1.x, strata2Anchor1.y*(1+(i*0.023f)), strata2Control1.x, strata2Control1.y*(1+(i*0.023f)), strata2Anchor2.x, strata2Anchor2.y*(1+(i*0.023f)), strata2Control2.x, strata2Control2.y*(1+(i*0.023f)));
		}
		
		stroke(strataCol, strataAlpha);
		strokeWeight(2);
		bezier(strata2Anchor1.x, strata2Anchor1.y, strata2Control1.x, strata2Control1.y, strata2Anchor2.x, strata2Anchor2.y, strata2Control2.x, strata2Control2.y);

		seasons.cycle();
		seasons.populate(Seasons.numberOfSeasons);
		for(int i = 0; i < resources2.size(); i++){
			Resource r = resources2.get(i);
			r.display();
			r.deplete();
		}
		
		for(int i = 0; i < community.size(); i++){
			Agent a = community.get(i);
			if(a.isAlive){
				a.display();
			}else{
				strokeWeight(1);
				noFill();
				ellipse(a.pos.x, a.pos.y, a.rad*0.75f, a.rad*0.75f);
				strokeWeight(2);
				line(a.pos.x-4, a.pos.y-4, a.pos.x+4, a.pos.y+4);
				line(a.pos.x-4, a.pos.y+4, a.pos.x+4, a.pos.y-4);
			}
				a.debug();
		}
		
		onHover(2);
		
		for(int i = 0; i < connectionsPower.size(); i++){
			ConnectionPower cP = connectionsPower.get(i);
			cP.display();
		}
		
		for(int i = 0; i < connectionsLove.size(); i++){
			ConnectionLove cL = connectionsLove.get(i);
			cL.display();
		}
		
		for(int i = 0; i < connectionsFriendship.size(); i++){
			ConnectionFriendship cF = connectionsFriendship.get(i);
			cF.display();
		}		
		
		//interactions
		float choiceY = height - height*0.02f;
		textSize(textFontSize);
		textFont(textFont);
		textAlign(CENTER);
		fill(0);
		stroke(0);
		strokeWeight(1);
		fill(150);
		if (selRevolt) fill(0);
		text("induce " + induceRevolt + " revolt(s)", width*0.1f, choiceY);
		fill(150);
		
		if (selOppression) fill(0);
		
		text("induce " + induceOppression + " oppression(s)", width*0.25f, choiceY);
		fill(150);
		
		if (forceCulturalSim) fill(0);
		
		text("force " + induceCulturalSim + " cultural similaritie(s)", width*0.45f, choiceY);
		fill(150);
		
		if (forceCulturalDiff) fill(0);
		text("force " + induceCulturalDiff + " cultural difference(s)", width*0.65f, choiceY);
		fill(150);
		
		if(finalStructure){
			strokeWeight(2);
			stroke(0, 150);
			fill(255, 150);
			rectMode(CORNER);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textFont(thoughtFont);
			textSize(thoughtFontSize);
			text("select the one you want to hear from", width*0.85f, rectBorderY+(rectBorderH*0.99f));
			if(selectedAgent2 == null){
				fill(0);
				textFont(textFont);
				textSize(textFontSize);
				text("no agent selected", width*0.85f, choiceY);
				if(rectProceedAlpha > 0) rectProceedAlpha -= 10.0f;
			}else if(selectedAgent2 != null){
				fill(0);
				text("agent selected", width*0.85f, choiceY);
				if(rectProceedAlpha < 200) rectProceedAlpha += 10.0f;
			}
		}else{
			fill(150);
			textFont(textFont);
			textSize(textFontSize);
			text("no agent selected", width*0.85f, choiceY);
		}
		
		fill(0);
				
		dragAgent();
		rectMode(CORNER);
		textFont(thoughtFont);
		textSize(thoughtFontSize);
		if(showInduceOppressionInfo){
			strokeWeight(2);
			stroke(0, 150);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("click on an individual to subject his surroundings to just amounts of power.", rectBorderX*42.0f, rectBorderY+(rectBorderH*0.99f));
		}
		
		if(showInduceRevoltInfo){
			strokeWeight(2);
			stroke(0, 150);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("click on an indivdual subjected to unjust amounts of power.", rectBorderX*2.0f, rectBorderY+(rectBorderH*0.99f));
		}
		
		if(showForceCultDiffInfo){
			strokeWeight(2);
			stroke(0, 150);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("click on an individual to make him more like the others.", rectBorderX*172.0f, rectBorderY+(rectBorderH*0.99f));
		}
		
		if(showForceCultSimInfo){
			strokeWeight(2);
			stroke(0, 150);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("click on an individual to make him different from others.", rectBorderX*267.0f, rectBorderY+(rectBorderH*0.99f));
		}
		
		fill(255, rectProceedAlpha);
		noStroke();
		rectMode(CENTER);
		rect(width*0.5f, height*0.5f, width*0.992f, height*0.1f);
		fill(0, rectProceedAlpha);
		textFont(headerFont);
		textSize(headerFontSize);
		text("press space to proceed", width*0.5f, height*0.5f);
	}
	
	void endGame2() {
		background(255);
		stroke(0);
		strokeWeight(1);
		rect(3, 3, width-6, height-6);
		rect(6, 6, width-12, height-12);
		
		fill(0);
		textSize(mainFontSize);
		textFont(mainFont);
		textAlign(CENTER);
		text("On "+storyTitle2, width/2, height/10);
		
		textSize(textFontSize);
		textFont(textFont);
		textAlign(LEFT);

		if(randomGen2 == true){
			randomGen2 = false;
		//generate random indexes
			int indexCO1 = (int)(random(spaceCultureText.length*0.5f));
			int indexCO2 = (int)(random(spaceCultureText.length*0.5f, spaceCultureText.length));
			int indexLove1 = (int)(random(inLoveText.length*0.5f, inLoveText.length));
			int indexLove2 = (int)(random(inLoveText.length*0.5f));
			int indexChildren1 = (int)(random(numberOfChildrenText.length*0.5f, numberOfChildrenText.length));
			int indexChildren2 = (int)(random(numberOfChildrenText.length*0.5f));
			int friends1 = (int)(random(friendships.length*0.5f, friendships.length));
			int friends2 = (int)(random(friendships.length*0.5f));
			int pExert1 = (int)(random(oppressionInducedText.length*0.5f, oppressionInducedText.length));
			int pExert2 = (int)(random(oppressionInducedText.length*0.5f));
			int pReceived1 = (int)(random(oppressionReceivedText.length*0.5f, oppressionReceivedText.length));
			int pReceived2 = (int)(random(oppressionReceivedText.length*0.5f));
			int indexGen1 = (int)(random(generationText.length*0.5f, generationText.length));
			int indexGen2 = (int)(random(generationText.length*0.5f));
			int indexRevolt1 = (int)(random(hasRevoltedText.length*0.5f));
			int indexRevolt2 = (int)(random(hasRevoltedText.length*0.5f, hasRevoltedText.length));
			int indexOppressed1 = (int)(random(hasOppressedText.length*0.5f, hasOppressedText.length));
			int indexOppressed2 = (int)(random(hasOppressedText.length*0.5f));
			int indexSim1 = (int)(random(hasForcedSimText.length*0.5f, hasForcedSimText.length));
			int indexSim2 = (int)(random(hasForcedSimText.length*0.5f));
			int indexDiff1 = (int)(random(hasForcedDiffText.length*0.5f, hasForcedDiffText.length));
			int indexDiff2 = (int)(random(hasForcedDiffText.length*0.5f));
			int indexCult1 = (int)(random(culturalHomogeneityText.length*0.5f, culturalHomogeneityText.length));
			int indexCult2 = (int)(random(culturalHomogeneityText.length*0.5f));


		
		//load the story
		
		

		if(selectedAgent2 != null){
			if(cultureOrigin == 1){//where someone lives
				endGame2_text = endGame2_text + spaceCultureText[indexCO1]; //
				endGame2_text = endGame2_text + socialCultureText[indexCO1];
			}else if(cultureOrigin == 2){ //with whom
				endGame2_text = endGame2_text + spaceCultureText[indexCO2];
				endGame2_text = endGame2_text + socialCultureText[indexCO2];
			}
			
			if(selectedAgent2.timeInLove > 10.0f){
				endGame2_text = endGame2_text + inLoveText[indexLove1];
			}else{
				endGame2_text = endGame2_text + inLoveText[indexLove2];
			}
			
			if(numberOfChildren > 0){
				endGame2_text = endGame2_text + numberOfChildrenText[indexChildren1];
			}else{
				endGame2_text = endGame2_text + numberOfChildrenText[indexChildren2];
			}
			
			if(selectedAgent2.friendsHad.size() > 5){
				endGame2_text = endGame2_text + friendships[friends1];
			}else{
				endGame2_text = endGame2_text + friendships[friends2];
			}
			
			if(selectedAgent2.powerExertedTotal > 50){
				endGame2_text = endGame2_text + oppressionInducedText[pExert1];
			}else{
				endGame2_text = endGame2_text + oppressionInducedText[pExert2];
			}
			
			if(selectedAgent2.powerReceivedTotal > 50){
				endGame2_text = endGame2_text + oppressionReceivedText[pReceived1];
			}else{
				endGame2_text = endGame2_text + oppressionReceivedText[pReceived2];
			}
			
			if(selectedAgent2.generation < 2){
				endGame2_text = endGame2_text + generationText[indexGen1];
			}else{
				endGame2_text = endGame2_text + generationText[indexGen2];
			}

			if(selectedAgent2.hasRevolted){
				endGame2_text = endGame2_text + hasRevoltedText[indexRevolt1];
			}else{
				endGame2_text = endGame2_text + hasRevoltedText[indexRevolt2];
			}
			
			if(selectedAgent2.hasOppressed){
				endGame2_text = endGame2_text + hasOppressedText[indexOppressed1];
			}else{
				endGame2_text = endGame2_text + hasOppressedText[indexOppressed2];
			}
			
			if(selectedAgent2.hasForcedSim){
				endGame2_text = endGame2_text + hasForcedSimText[indexSim1];
			}else{
				endGame2_text = endGame2_text + hasForcedSimText[indexSim2];
			}
			
			if(selectedAgent2.hasForcedDiff){
				endGame2_text = endGame2_text + hasForcedDiffText[indexDiff1];
			}else{
				endGame2_text = endGame2_text + hasForcedDiffText[indexDiff2];
			}
			
			if(culturalHomogeneity() < 50){ //TODO check value
				endGame2_text = endGame2_text + culturalHomogeneityText[indexCult1];
			}else{
				endGame2_text = endGame2_text + culturalHomogeneityText[indexCult2];
			}
		}
		
	}

		text(endGame2_text, width*0.2f, height*0.2f, (width/5)*3, 19*(height/20));
		
		strokeWeight(2);
		stroke(10, 210);
		noFill();
		rect(proceedX, proceedY, proceedW, proceedH);
		textFont(headerFont);
		textSize(headerFontSize);
		text("Proceed", proceedX*1.01f, proceedY*1.0475f);
		
		stroke(0);
		 pushMatrix();
		 translate(portraitPos.x, portraitPos.y);
		  float angleInc = 360/(selectedAgent2.sides+5);
		  
		  //base shape
		  beginShape();
		  for(int i = 0; i < 360; i += angleInc){
			  float x = PApplet.cos(PApplet.radians(i))*(portraitRad*0.5f);
			  float y = PApplet.sin(PApplet.radians(i))*(portraitRad*0.5f);
			  vertex(x, y);
		  }
		  endShape(PApplet.CLOSE);
		  portraitRad += (PApplet.cos(selectedAgent2.xPulse))*0.15f;
		  selectedAgent2.xPulse += selectedAgent2.pulseRatio;
		  
		  stroke(selectedAgent2.col, selectedAgent2.alpha2*0.4f);
		  if(selectedAgent2.culture[1] >= 5){
			  beginShape();
			  for(int i = 0; i < 360; i += angleInc){
				  float x = PApplet.cos(PApplet.radians(i))*(portraitRad*0.4f);
				  float y = PApplet.sin(PApplet.radians(i))*(portraitRad*0.4f);
				  vertex(x, y);
			  }
			  endShape(PApplet.CLOSE);
		  }
		  
		  stroke(selectedAgent2.col, selectedAgent2.alpha2*0.3f);
		  if(selectedAgent2.culture[1] >= 8){
			  beginShape();
			  for(int i = 0; i < 360; i += angleInc){
				  float x = PApplet.cos(PApplet.radians(i))*(portraitRad*0.3f);
				  float y = PApplet.sin(PApplet.radians(i))*(portraitRad*0.3f);
				  vertex(x, y);
			  }
			  endShape(PApplet.CLOSE);
		  }
		  
		  stroke(selectedAgent2.col, selectedAgent2.alpha2*0.1f);
		  if(selectedAgent2.culture[1] == 10){
			  beginShape();
			  for(int i = 0; i < 360; i += angleInc){
				  float x = PApplet.cos(PApplet.radians(i))*(portraitRad*0.2f);
				  float y = PApplet.sin(PApplet.radians(i))*(portraitRad*0.2f);
				  vertex(x, y);
			  }
			  endShape(PApplet.CLOSE);
		  }

		  popMatrix();
		
	}
	
	void startGame3() {
		rectProceedAlpha = 0.0f;
		rectMode(CORNER);
		rectCol = 245+(noise(t))*10; 
		fill(rectCol);
		t+= 0.4f;
		strokeWeight(2);
		rect(width*0.00375f, height*0.00333f, width*0.9925f, height*0.99334f);
		stroke(0);
		

		fill(15);
		textSize(headerFontSize);
		textAlign(CENTER);
		textFont(mainFont);
		text("SOCIAL CONTACT", width/2, height/10);
		
		textSize(subtitleFontSize);
		textFont(subtitleFont);
		text("III - confronting others", width/2, height/7);
		
		rectMode(CENTER);
		fishTank();
		
		fill(0);
		textSize(headerFontSize);
		textFont(headerFont);
		text("Commerce", leftColX, headerHeight);		
		text("War", rightColX, headerHeight);
		
		textFont(textFont);
		textSize(textFontSize);
		textAlign(LEFT);
		
		showAlpha = abs(cos(millis()*0.001f+random(0.05f, 0.08f))*150)+100;
		
		if(canShowAlliances){
			fill(0, 150, 0, showAlpha+100);
		}else{
			fill(0, showAlpha);
		}
		text("alliances", width*0.405f, height*0.33f);
		
		if(canShowWar){
			fill(200, 0, 0, showAlpha+100);
		}else{
			fill(0, showAlpha);
		}
		text("war", width*0.495f, height*0.33f);
		
		if(canShowTrade){
			fill(0, 0, 150, showAlpha+100);
		}else{
			fill(0, showAlpha);
		}
		text("trade", width*0.5625f, height*0.33f);

		
		textAlign(CENTER);
		fill(0);
		if(allianceModifier == 0.5f){
			text("alliances are easy to make", leftColX, firstRowY);
		}else if(allianceModifier == 1.0f){
			text("alliances are carefully thought out", leftColX, firstRowY);
		}else{
			text("alliances are long and arduous processes", leftColX, firstRowY);
		}
		
		if(tradeModifier == 0.5f){
			text("trade is natural", leftColX, secondRowY);
		}else if(tradeModifier == 1.0f){
			text("trade requires trust", leftColX, secondRowY);
		}else{
			text("trade comes last", leftColX, secondRowY);
		}
		
		if(warModifier == 0.5f){
			text("war is always an option", rightColX, secondRowY);//you need to have a certain amount of resources to fight and win
		}else if(warModifier == 1.0f){
			text("war should be considered", rightColX, secondRowY);
		}else{
			text("war should never happen", rightColX, secondRowY);
		}
		
		if(nationGoal == 0.0f){
			text("nations strive to self-preserve", width*0.5f, buttonGoalHeight);
			storyTitle3 = "survival";
		}else if(nationGoal == 1.0f){
			text("nations strive to maintain their honor", width*0.5f, buttonGoalHeight);
			storyTitle3 = "honor";
		}else{
			text("nations strive to accumulate wealth", width*0.5f, buttonGoalHeight);
			storyTitle3 = "wealth";
		}
		
		if(victoryBehaviour == 1.5f){
			text("victors are merciful", rightColX, firstRowY);
		}else if(victoryBehaviour == 1.0f){
			text("victors are respectful", rightColX, firstRowY);
		}else{
			text("victors are the repositaries of violence", rightColX, firstRowY);
		}
		
		
		if(tradeLimit == 1.0f){
			text("trade is limited to the minimum", leftColX, thirdRowY);
		}else if(tradeLimit == 2.0f){
			text("trade can grow profit", leftColX, thirdRowY);
		}else{
			text("trade is not bound", leftColX, thirdRowY);
		}
		
		
		if(allianceTrust == 0.5f){
			text("alliances are but fleeting agreements", rightColX, thirdRowY);
		}else if(allianceTrust == 1.0f){
			text("alliances are to be respected", rightColX, thirdRowY);
		}else{
			text("alliances are promises", rightColX, thirdRowY);
		}
		
		//text("how much their connections affects their culture ", rightColX, fourthRowY);
		
		allianceModifierUp.display();
		allianceModifierDown.display();
		tradeModifierUp.display();
		tradeModifierDown.display();
		warModifierUp.display();
		warModifierDown.display();
		nationGoalButtonRight.display();
		nationGoalButtonLeft.display();
		victoryBehaviourUp.display();
		victoryBehaviourDown.display();
		tradeLimitUp.display();
		tradeLimitDown.display();
		allianceTrustUp.display();
		allianceTrustDown.display();
		//cultureExternalUp.display();
		//cultureExternalDown.display();
		
		allianceModifierUp.onClick();
		allianceModifierDown.onClick();
		tradeModifierUp.onClick();
		tradeModifierDown.onClick();
		warModifierUp.onClick();
		warModifierDown.onClick();
		nationGoalButtonRight.onClick();
		nationGoalButtonLeft.onClick();
		victoryBehaviourUp.onClick();
		victoryBehaviourDown.onClick();
		tradeLimitUp.onClick();
		tradeLimitDown.onClick();
		allianceTrustUp.onClick();
		allianceTrustDown.onClick();
		//cultureExternalUp.onClick();
		//cultureExternalDown.onClick();
		
		startButton3.display();
		startButton3.onClick();
	}
	
	void inGame3() {
		// TODO Auto-generated method stub
		background(255);
		update3();
		fill(bgColBox, 50);
		bgColBox = lerpColor(bgColBox, newBgColBox, bgColBoxLerpSpeed);
		bgColBoxLerpSpeed += bgColBoxLerpInc;
		bgColBoxLerpSpeed = map((millis()-Seasons.startTime), 0, 6000.0f, 0, 1);
		stroke(0);
		strokeWeight(1);
		rect(rectBorderX, rectBorderY, rectBorderW, rectBorderH);
		strokeWeight(2);
		rect(width*0.00375f, height*0.00333f, width*0.9925f, height*0.99334f);
		drawVoronoi(3);
		seasons.cycle();
		seasons.populate(Seasons.numberOfSeasons);
		nation.display();
		nation.update();
		
		for(int i = 0; i < others.size(); i++){
			Nation o = others.get(i);
			o.display();
			o.update();
		}
		
		for(int i = 0; i < trades.size(); i++){
			Trade t = trades.get(i);
			t.display();
		}
		
		for(int i = 0; i < alliances.size(); i++){
			Alliance a = alliances.get(i);
			a.display();
		}
		
		for(int i = 0; i < wars.size(); i++){
			War w = wars.get(i);
			w.display();
		}
		
		onHover(3);
		
		float choiceY = height - height*0.02f;
		textSize(textFontSize);
		textFont(textFont);
		textAlign(CENTER);
		fill(150);
		stroke(0);
		strokeWeight(2);
		
		if (selWealthInc) fill(0);
		text("can increase wealth", width*0.1f, choiceY);
		fill(150);
		
		if (selWealthDec) fill(0);
		
		text("can decrease wealth", width*0.25f, choiceY);
		fill(150);
		
		if (selWarInduce) fill(0);
		text("induce aggressivity", width*0.45f, choiceY);
		fill(150);
		
		if (selAllianceBreak) fill(0);
		text("break alliances", width*0.65f, choiceY);
		fill(150);
		
		if (selFinalProceed){
			if(rectProceedAlpha < 200) rectProceedAlpha += 10.0f;
			fill(0);
		}else{
			if(rectProceedAlpha > 0) rectProceedAlpha -= 10.0f;
			fill(150);
		}
		
		text("carry on", width*0.85f, choiceY);
		fill(150);
		
		rectMode(CORNER);
		textFont(thoughtFont);
		textSize(thoughtFontSize);
		if(showWealthDecInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("click on a nation to decrease their wealth.", rectBorderX*62.0f, rectBorderY+(rectBorderH*0.99f));
		}
		
		if(showWealthIncInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("click on a nation to increase their wealth.", rectBorderX*2.0f, rectBorderY+(rectBorderH*0.99f));
		}
		
		if(showWarInduceInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("click on a nation to make it more prone to enter war.", rectBorderX*172.0f, rectBorderY+(rectBorderH*0.99f));
		}
		
		if(showAllianceBreakInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("click on a nation to break its alliances.", rectBorderX*267.0f, rectBorderY+(rectBorderH*0.99f));
		}
		
		if(showFinalProceedInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			text("become a repository for their history", rectBorderX*357.0f, rectBorderY+(rectBorderH*0.99f));
		}
		
		fill(255, rectProceedAlpha);
		noStroke();
		rectMode(CENTER);
		rect(width*0.5f, height*0.5f, width*0.992f, height*0.1f);
		fill(0, rectProceedAlpha);
		textFont(headerFont);
		textSize(headerFontSize);
		text("press space to proceed", width*0.5f, height*0.5f);
		textFont(textFont);
		textSize(textFontSize);
		fill(0);
	}
	
	void update3() {
		nation.update();
		
		for(int i = 0; i < others.size(); i++){
			Nation o = others.get(i);
			o.update();
		}
		
		
		for(int i = 0; i < trades.size(); i++){
			Trade t = trades.get(i);
			t.update();
		}
		
		for(int i = 0; i < alliances.size(); i++){
			Alliance a = alliances.get(i);
			a.update();
		}
		

	}

	void endGame3() {
		background(255);
		stroke(0);
		strokeWeight(1);
		rect(3, 3, width-6, height-6);
		rect(6, 6, width-12, height-12);
		
		fill(0);
		textSize(mainFontSize);
		textFont(mainFont);
		textAlign(CENTER);
		text("On "+storyTitle3, width/2, height/10);
		
		textSize(textFontSize);
		textFont(textFont);
		textAlign(LEFT);
		
		if(randomGen3 == true && nation != null){
			randomGen3 = false;
			//first part of text file is achieved
			int indexGoalHonor = (int) random(st_nationGoalHonor.length*0.5f);
			int indexGoalSurvival = (int) random(st_nationGoalSurvival.length*0.5f);
			int indexGoalWealth = (int) random(st_nationGoalWealth.length*0.5f);
			int indexWealth = (int) random(st_wealth.length*0.5f);
			int indexWar = (int) random(st_war.length*0.5f);
			int indexPartners = (int) random(st_tradePartners.length*0.5f);
			int indexPromiscuity = (int) random(st_promiscuity.length*0.5f);
			int indexAlliances = (int) random(st_alliances.length*0.5f);
			int indexCultureAlone = (int) random(st_similarCultureAlone.length*0.5f);
			int indexCultureAllies = (int) random(st_similarCultureAllies.length*0.5f);
			
			//second part of text file is missed
			int indexGoalHonor0 = (int) random(st_nationGoalHonor.length*0.5f, st_nationGoalHonor.length);
			int indexGoalSurvival0 = (int) random(st_nationGoalSurvival.length*0.5f);
			int indexGoalWealth0 = (int) random(st_nationGoalWealth.length*0.5f, st_nationGoalSurvival.length);
			int indexWealth0 = (int) random(st_wealth.length*0.5f, st_wealth.length);
			int indexWar0 = (int) random(st_war.length*0.5f, st_war.length);
			int indexPartners0 = (int) random(st_tradePartners.length*0.5f, st_tradePartners.length);
			int indexPromiscuity0 = (int) random(st_promiscuity.length*0.5f, st_promiscuity.length);
			int indexAlliances0 = (int) random(st_alliances.length*0.5f, st_alliances.length);
			int indexCultureAlone0 = (int) random(st_similarCultureAlone.length*0.5f, st_similarCultureAlone.length);
			int indexCultureAllies0 = (int) random(st_similarCultureAllies.length*0.5f, st_similarCultureAllies.length);
		
		
			if(nationGoal == 1 && nation.wasAtWar){
				endGame3_text = endGame3_text+" "+st_nationGoalHonor[indexGoalHonor];
			}else{
				endGame3_text = endGame3_text+" "+st_nationGoalHonor[indexGoalHonor0];
			}
			
			if(nationGoal == 0 && !nation.isRuined){
				endGame3_text = endGame3_text+" "+st_nationGoalSurvival[indexGoalSurvival];
			}else{
				endGame3_text = endGame3_text+" "+st_nationGoalSurvival[indexGoalSurvival0];
			}
			
			if(nationGoal == 2 && nation.totalWealth > 10000){ //TODO put this in variable
				endGame3_text = endGame3_text+" "+st_nationGoalWealth[indexGoalWealth];
			}else{
				endGame3_text = endGame3_text+" "+st_nationGoalWealth[indexGoalWealth0];
			}
			
			if(nation.totalWealth > 10000){ //TODO same as above
				endGame3_text = endGame3_text+" "+st_wealth[indexWealth];
			}else{
				endGame3_text = endGame3_text+" "+st_wealth[indexWealth0];
			}
			
			if(nation.wasAtWar){
				endGame3_text = endGame3_text+" "+st_war[indexWar];
			}else{
				endGame3_text = endGame3_text+" "+st_war[indexWar0];
			}
			
			if(nation.totalTradePartners > 4){
				endGame3_text = endGame3_text+" "+st_tradePartners[indexPartners];
			}else{
				endGame3_text = endGame3_text+" "+st_tradePartners[indexPartners0];
			}
			
			endGame3_text = endGame3_text + "\n"+ "\n";
			
			if(nation.promiscuity){
				endGame3_text = endGame3_text+" "+st_promiscuity[indexPromiscuity];
			}else{
				endGame3_text = endGame3_text+" "+st_promiscuity[indexPromiscuity0];
			}
			
			if(nation.totalAllies > 4){
				endGame3_text = endGame3_text+st_alliances[indexAlliances];
				if(nation.similarCulture()){
					endGame3_text = endGame3_text+" "+st_similarCultureAllies[indexCultureAllies];
				}else{
					endGame3_text = endGame3_text+" "+st_similarCultureAllies[indexCultureAllies0];
				}
			}else{
				endGame3_text = endGame3_text+st_alliances[indexAlliances0];
					if(nation.similarCulture()){
						endGame3_text = endGame3_text+" "+st_similarCultureAlone[indexCultureAlone];
					}else{
						endGame3_text = endGame3_text+" "+st_similarCultureAlone[indexCultureAlone0];
					}
				}
		}
		
		fill(0);
		text(endGame3_text, width*0.2f, height*0.2f, (width/5)*3, 19*(height/20));
		
		strokeWeight(2);
		stroke(10, 210);
		noFill();
		rect(proceedX, proceedY, proceedW, proceedH);
		textFont(headerFont);
		textSize(headerFontSize);
		text("Proceed", proceedX*1.01f, proceedY*1.0475f);
		
	}
	
	public void rhythm(){
		if(millis() - rhythmStartTimer > rhythmTimer){
			Noise n = new Noise(ac);
			Envelope eNoise = new Envelope(ac, 0.0f);
			Gain gNoise = new Gain(ac, 1, eNoise);
			gNoise.addInput(n);
			Panner pNoise = new Panner(ac, random(0.5f, 1.0f));
			pNoise.addInput(gNoise);
			gMasterRhythm.addInput(pNoise);
			
			if(random(1) > 0.6f){
				eNoise.addSegment(random(0.01f, 0.03f), random(0, 20));
				eNoise.addSegment(0.0f, random(100, 500), new KillTrigger(pNoise));
			}else{
				eNoise.addSegment(random(0.01f, 0.03f), random(100, 300));
				eNoise.addSegment(0.0f, random(100, 3000), new KillTrigger(pNoise));
			}
			rhythmStartTimer = millis();
		}
	}
	
	public void dragAgent(){
		
		if(draggedAgent != null){ //if you're dragging an agent (meaning you're either dragging it or it's getting back to its original pos)
			float dist = min(dist(draggedAgent.pos.x, draggedAgent.pos.y, mouseX, mouseY), 100);
			pushMatrix();
			translate(draggedAgent.pos.x, draggedAgent.pos.y);
			noFill();
			if(!isReleased){ //if you're currently holding it, two possibilities
					draggedAgent.alpha = 50;
					PVector p = PVector.sub(new PVector(mouseX, mouseY), draggedAgent.pos);
					float theta = degrees(p.heading());
						
					draggedX = cos(radians(theta))*dist;
					draggedY = sin(radians(theta))*dist;

					noFill();
					stroke(0, 150);
					strokeWeight(2);
					ellipse(draggedX, draggedY, draggedAgent.rad, draggedAgent.rad);
					stroke(0, 50);
					strokeWeight(1);
					for(int i = 0; i < draggedAgent.currentConnections.size(); i++){
						Connection c = draggedAgent.currentConnections.get(i);
						if(c.a1 != draggedAgent) line(draggedX - draggedAgent.rad/2, draggedY, c.a1.pos.x - draggedAgent.pos.x - draggedAgent.rad/2, c.a1.pos.y - draggedAgent.pos.y);
						if(c.a2 != draggedAgent) line(draggedX - draggedAgent.rad/2, draggedY, c.a2.pos.x - draggedAgent.pos.x - draggedAgent.rad/2, c.a2.pos.y- draggedAgent.pos.y);
					}
					
					for(int i = 0; i < draggedAgent.currentConnectionsFriendship.size(); i++){
						ConnectionFriendship cF = draggedAgent.currentConnectionsFriendship.get(i);
						stroke(cF.c);
						if(cF.a1 != draggedAgent) line(draggedX, draggedY, cF.a1.pos.x - draggedAgent.pos.x, cF.a1.pos.y - draggedAgent.pos.y);
						if(cF.a2 != draggedAgent) line(draggedX, draggedY, cF.a2.pos.x - draggedAgent.pos.x, cF.a2.pos.y- draggedAgent.pos.y);
					}
					
					for(int i = 0; i < draggedAgent.currentConnectionsPower.size(); i++){
						ConnectionPower cP = draggedAgent.currentConnectionsPower.get(i);
						stroke(cP.c);
						if(cP.a1 != draggedAgent) line(draggedX, draggedY, cP.a1.pos.x - draggedAgent.pos.x, cP.a1.pos.y - draggedAgent.pos.y);
						if(cP.a2 != draggedAgent) line(draggedX, draggedY, cP.a2.pos.x - draggedAgent.pos.x, cP.a2.pos.y- draggedAgent.pos.y);
					}
				}
				
			if(isReleased){ //if you're not currently holding it, then its getting back to its original position (draggedAgent.pos)
				draggedX = lerp(draggedX, 0, dragLerpSpeed);
				draggedY = lerp(draggedY, 0, dragLerpSpeed);
				dragLerpSpeed += 0.03f;
				noFill();
				stroke(0, 150);
				strokeWeight(2);
				ellipse(draggedX, draggedY, draggedAgent.rad, draggedAgent.rad);
					
				if(dragLerpSpeed > 0.6f){
					draggedAgent.alpha = 255;
					dist = 0;
					draggedAgent = null;
					isReleased = false;
					canDragAgent = true;
					dragLerpSpeed = 0.0f;
				}

			}
			popMatrix();
		}
	}
	
	public void mouseDragged(){
		if(inGame1){
			for(int i = 0; i < agents.length; i++){
				if(dist(mouseX, mouseY, agents[i].pos.x, agents[i].pos.y) < agents[i].rad*0.9f && canDragAgent && agents[i].isAlive && agents[i].arrived){
					canDragAgent = false;
					draggedAgent = agents[i];
					agents[i].isDragged = true;
				}
			}
		}
		
		if(inGame2){
			for(int i = 0; i < community.size(); i++){
				Agent a = community.get(i);
				if(dist(mouse.x, mouse.y, a.pos.x, a.pos.y) < a.rad*0.9f && canDragAgent && a.isAlive){
					canDragAgent = false;
					draggedAgent = a;
					a.isDragged = true;
				}
			}
		}
	}
	
	public void mouseReleased(){
		if(draggedAgent != null){
			isReleased = true;
		}		
	}
		
	public void mousePressed() {
		//click sound
		WavePlayer wp2 = new WavePlayer(ac, 220, Buffer.SINE);
		Envelope e = new Envelope(ac, 0.0f);
		Gain g = new Gain(ac, 1, e);
		g.addInput(wp2);
		masterGain.addInput(g);
		e.addSegment(1.0f, 20.0f);//a
		e.addSegment(0.1f, 40.0f);//s
		e.addSegment(0.0f, 700.0f, new KillTrigger(g));
		
		
		if(endGame1){
			if(mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY + proceedH){
				fading = true;
			}
		}
		
		if(endGame2){
			if(mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY + proceedH){
				fading = true;
			}
		}
		
		if(endGame3){
			if(mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY + proceedH){
				endGame3 = false;
				emailScreen = true;
			}
		}
		
		if(emailScreen){
			if(mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY*1.02f + proceedH && typedEmail){
				emailAddress = typedText;
			    emailAddress.replaceAll("\\p{Z}", "");
			    emailAddress.replaceAll("\uFFFD", "");
			    emailAddress.replaceAll("\uFFFF", "");
			    int arobase = emailAddress.indexOf('@');
			    String local = emailAddress.substring(0, arobase);
			    println("local: "+local);
			    String host = emailAddress.substring(arobase+1, emailAddress.length());
			    println("host: "+host);
			    emailAddress = local + "@" + host;
			    emailStory = "i have been through a lot."+"\n\n\n\n"+"\n\n"+storyTitle1+"\n\n"+endGame1_text+"\n\n"+storyTitle2+"\n\n"+endGame2_text+"\n\n"+storyTitle3+"\n\n"+endGame3_text + "\n\n\n\n thank you.";
			    thread("sendEmail");
				emailScreen = false;
				finalScreen = true;
				finalTimerSmall = millis() + 2000.0f;
				finalTimerFull = millis() + 8000.0f;
			}
		}
		if (startGame1) {
			
			if(mouseY > height*0.32f && mouseY < height*0.34f){
				if(mouseX > width*0.405f && mouseX < width*0.44f){
					canShowConnections = !canShowConnections;
				}
				
				if(mouseX > width*0.46f && mouseX < width*0.52f){
					canShowResources = !canShowResources;
				}
				
				if(mouseX > width*0.53f && mouseX < width*0.6f){
					canShowPredators = !canShowPredators;
				}
			}
			
			if (right1.onClick()) {
				canFormStableRelationships = !canFormStableRelationships;
				if (canFormStableRelationships) {
					right1.c = right1.c2;
				} else {
					right1.c = right1.c1;
				}
			}
			if (right2.onClick()) {
				canDisturb = !canDisturb;
				if (canDisturb) {
					right2.c = right2.c2;
				} else {
					right2.c = right2.c1;
				}
			}
			if (right3.onClick()) {
				canProtect = !canProtect;
				if (canProtect) {
					right3.c = right3.c2;
				} else {
					right3.c = right3.c1;
				}
			}
			
			if (right4.onClick()) {
				canRememberFallen = !canRememberFallen;
				if (canRememberFallen) {
					right4.c = right4.c2;
				} else {
					right4.c = right4.c1;
				}
			}

			if (duty_up.onClick()){
				if(connec < maxConnec){
					connec++;
				}else{
					connec = minConnec;
				}
				for(int i = 0; i < fishes.size(); i++){
					Fish f = fishes.get(i);
					f.connec++;
				}
			}
			if (duty_down.onClick()){
				if(connec > minConnec){
					connec--;
				}else{
					connec = maxConnec;
				}
				for(int i = 0; i < fishes.size(); i++){
					Fish f = fishes.get(i);
					f.connec--;
				}
			}

			if (violence_up.onClick()) {
				if(violence < 1.0f){
					violence += 0.5f;
				}else{
					violence = 0.0f;
				}
			}
			if (violence_down.onClick()) {
				if(violence > 0.0f){
					violence -= 0.5f;
				}else{
					violence = 1.0f;
				}
			}
			
			if(resource_up.onClick()){
				if(resourceSeek < 1.5f){
					resourceSeek += 0.5f;
				}else{
					resourceSeek = 0.5f;
				}
			}
			
			if(resource_down.onClick()){
				if(resourceSeek > 0.5f){
					resourceSeek -= 0.5f;
				}else{
					resourceSeek = 1.5f;
				}
			}

			if (startButton.onClick()) { // when you click start, a bunch of things happen
				fading = true;
			}

			if (pursuitRight.onClick())
				goalInt++;
			
			if(pursuitLeft.onClick())
				goalInt--;
		}
		
		if(startGame2){
			
			if(mouseY > height*0.32f && mouseY < height*0.34f){
				if(mouseX > width*0.405f && mouseX < width*0.45f){
					canShowPower = !canShowPower;
				}
				
				if(mouseX > width*0.472f && mouseX < width*0.555f){
					canShowFriendship = !canShowFriendship;
				}
				
				if(mouseX > width*0.575f && mouseX < width*0.6f){
					canShowLove = !canShowLove;
				}
			}
			
			if(startButton2.onClick()){
				fading = true;
			}
			
			if(powerForceUpButton.onClick()){
				if(powerForce < 7.0f){
					powerForce += 2.0f;
				}else if(powerForce == 7.0f){
					powerForce = 1.0f;
				}
			}
			if(powerForceDownButton.onClick()){
				if(powerForce > 1.0f){
					powerForce -= 2.0f;
				}else if(powerForce == 1.0f){
					powerForce = 7.0f;
				}
			}
			
			if(friendshipForceUpButton.onClick()){
				if(friendshipForce < 7.0f){
					friendshipForce += 2.0f;
				}else if(friendshipForce == 7.0f){
					friendshipForce = 1.0f;
				}
			}
			if(friendshipForceDownButton.onClick()){
				if(friendshipForce > 1.0f){
					friendshipForce -= 2.0f;
				}else if(friendshipForce == 1.0f){
					friendshipForce = 7.0f;
				}
			}
			
			if(loveForceUpButton.onClick()){
				if(loveForce < 60.0f){
					loveForce += 5.0f;
				}else{
					loveForce = 40.0f;
				}
			}
			if(loveForceDownButton.onClick()){
				if(loveForce > 40.0f){
					loveForce -= 5.0f;
				}else{
					loveForce = 60.0f;
				}
			}
			
			if(ageMajorityUpButton.onClick()){
				if(ageModifier < 0.30f){
					ageModifier += 0.05f;
				}else{
					ageModifier = 0.20f;
				}
			}
			if(ageMajorityDownButton.onClick()){
				if(ageModifier > 0.20f){
					ageModifier -= 0.05f;
				}else{
					ageModifier = 0.30f;
				}
			}
			
			if(numberOfChildrenUpButton.onClick()){
				if(numberOfChildren < 5.0f){
					numberOfChildren += 1.0f;
				}else{
					numberOfChildren = (int) 0.0f;
				}
			}
			if(numberOfChildrenDownButton.onClick()){
				if(fishes.size() > numberOfChildren && fishes.size() > 8){
					fishes.remove(fishes.get(fishes.size()-1));
				}
				
				if(numberOfChildren > 0.0f){
					numberOfChildren -= 1.0f;
				}else{
					numberOfChildren = (int) 5.0f;
				}
			}
			
			if(independenceOfChildrenUpButton.onClick()){
				
				if(independenceOfChildren < 2.0f){
					independenceOfChildren += 1.0f;
				}else{
					independenceOfChildren = 0.0f;
				}
			}
			if(independenceOfChildrenDownButton.onClick()){
				if(independenceOfChildren > 0.0f){
					independenceOfChildren -= 1.0f;
				}else{
					independenceOfChildren = 3.0f;
				}
			}
			
			if(cultureButtonRight.onClick()){
				if(cultureOrigin == 1){
					cultureOrigin = 2;
				}else if(cultureOrigin == 2){
					cultureOrigin = 0;
				}else{
					cultureOrigin = 1;
				}
			}
			
			if(cultureButtonLeft.onClick()){
				if(cultureOrigin == 1){
					cultureOrigin = 0;
				}else if(cultureOrigin == 2){
					cultureOrigin = 1;
				}else{
					cultureOrigin = 2;
				}
			}
		}
		
		if(startGame3){
			if(mouseY > height*0.32f && mouseY < height*0.34f){
				if(mouseX > width*0.405f && mouseX < width*0.45f){
					canShowAlliances = !canShowAlliances;
				}
				
				if(mouseX > width*0.48f && mouseX < width*0.52f){
					canShowWar = !canShowWar;
				}
				
				if(mouseX > width*0.575f && mouseX < width*0.595f){
					canShowTrade = !canShowTrade;
				}
			}
			
			if(allianceModifierUp.onClick()){
				if(allianceModifier < 1.5f){
					allianceModifier += 0.5;
				}else{
					allianceModifier = 0.5f;
				}
			}
			
			if(allianceModifierDown.onClick()){
				if(allianceModifier > 0.5f){
					allianceModifier -= 0.5f;
				}else{
					allianceModifier = 1.5f;
				}
			}
			
			if(tradeModifierUp.onClick()){
				if(tradeModifier < 1.5f){
					tradeModifier += 0.5;
				}else{
					tradeModifier = 0.5f;
				}
			}
			
			if(tradeModifierDown.onClick()){
				if(tradeModifier > 0.5f){
					tradeModifier -= 0.5f;
				}else{
					tradeModifier = 1.5f;
				}
			}
			
			if(warModifierUp.onClick()){
				if(warModifier < 1.5f){
					warModifier += 0.5;
				}else{
					warModifier = 0.5f;
				}
			}
			
			if(warModifierDown.onClick()){
				if(warModifier > 0.5f){
					warModifier -= 0.5f;
				}else{
					warModifier = 1.5f;
				}
			}
			
			
			if(victoryBehaviourUp.onClick()){
				println(victoryBehaviour);
				if(victoryBehaviour < 1.5f){
					victoryBehaviour += 0.5f;
				}else{
					victoryBehaviour = 0.5f;
				}
			}
			
			if(victoryBehaviourDown.onClick()){
				if(victoryBehaviour > 0.5f){
					victoryBehaviour -= 0.5f;
				}else{
					victoryBehaviour = 1.5f;
				}
			}
			
			if(tradeLimitUp.onClick()){
				if(tradeLimit < 1.5f){
					tradeLimit += 0.5f;
				}else{
					tradeLimit = 0.5f;
				}
			}
			
			if(tradeLimitDown.onClick()){
				if(tradeLimit > 0.5f){
					tradeLimit -= 0.5f;
				}else{
					tradeLimit = 1.5f;
				}
			}
			
			if(allianceTrustUp.onClick()){
				if(allianceTrust < 1.5f){
					allianceTrust += 0.5f;
				}else{
					allianceTrust = 0.5f;
				}
			}
			
			if(allianceTrustDown.onClick()){
				if(allianceTrust > 0.5f){
					allianceTrust -= 0.5f;
				}else{
					allianceTrust = 1.5f;
				}
			}
			
			if(nationGoalButtonRight.onClick()){
				if(nationGoal < 2){
					nationGoal++;
				}else{
					nationGoal = 0;
				}
			}
			
			if(nationGoalButtonLeft.onClick()){
				if(nationGoal > 0){
					nationGoal--;
				}else{
					nationGoal = 2;
				}
			}

			if(startButton3.onClick()){
				fading = true;
			}
		}

		if (inGame1) {
			float choiceY = height - height*0.02f;
			float bufferX = 70;
			float bufferY = 20;
			if(mouseY < height - height/40 + bufferY && mouseY > choiceY - bufferY){ //if you're at the bottom of the screen
				if(mouseX > width/12 - bufferX  && mouseX < width/12 + bufferX){ //first interaction generate predators
					selGenPred = true;
					selStopAgent = false;
					selRemoveConnec = false;
					selFinalCluster = false;
					showGenPredInfo = true;
					showStopAgentInfo = false;
					showRemoveConnectionInfo = false;
					showSelFinalClusterInfo = false;
				}
				
				if(mouseX > width/12 + width/4 - bufferX  && mouseX < width/12 + width/4 + bufferX){ //second interaction stop agent
					selGenPred = false;
					selStopAgent = false;
					selRemoveConnec = true;
					selFinalCluster = false;
					showGenPredInfo = false;
					showStopAgentInfo = true;
					showRemoveConnectionInfo = false;
					showSelFinalClusterInfo = false;
				}
				
				if(mouseX > width/12 + width/2 - bufferX  && mouseX < width/12 + width/2 + bufferX){ //third interaction remove connec
					selGenPred = false;
					selStopAgent = true;
					selRemoveConnec = false;
					selFinalCluster = false;
					showGenPredInfo = false;
					showStopAgentInfo = false;
					showRemoveConnectionInfo = true;
					showSelFinalClusterInfo = false;
				}
				
				if(mouseX > width/12 + (width/4)*3 - bufferX  && mouseX < width/12 + (width/4)*3 + bufferX){ //select cluster
					selGenPred = false;
					selStopAgent = false;
					selRemoveConnec = false;
					selFinalCluster = true;
					showGenPredInfo = false;
					showStopAgentInfo = false;
					showRemoveConnectionInfo = false;
					showSelFinalClusterInfo = true;
				}
			}

			if (genPred > 0 && selGenPred && mouseY < height - height/40 - bufferY) {
				predators.add(new Predator(mouseX, mouseY, 10, -2, 2, random(30, 35) * 1000, random(2, 3) * 10, this));
				genPred--;
			}

			for (int i = 0; i < agents.length; i++) { //this is the loop where anything involving clicking on an agent happens
				
				if (mouseX < agents[i].pos.x + rad && mouseX > agents[i].pos.x - rad && mouseY < agents[i].pos.y + rad && mouseY > agents[i].pos.y - rad) {
					
					if (stopAgent > 0 && selStopAgent) { //stopping agent
						agents[i].isStopped = true;
						stopAgent--;
					}

					if (removeConnec > 0 && selRemoveConnec) { //removing connections

						for(int k = 0; k < connections.size(); k++){
							Connection c = connections.get(k);
							
							if(c.canRemove){
								println("removing connection");
								connections.remove(c);
								c.a1.currentConnections.remove(c);
								c.a2.currentConnections.remove(c);
								removeConnec--;
							}
						}
					}

					if (selFinalCluster && agents[i].cluster.agentsInside.size() > 1) { //selecting the final cluster
						for(int j = 0; j < agents.length; j++){//unselect all the agents
							agents[j].isSelected = false;
							agents[j].col = color(50);
							selectedAgent = agents[j];
						}

						agents[i].isSelected = true;
						finalCluster = agents[i].cluster;				
					}
				}else if(!isHovering && finalCluster != null && PApplet.dist(mouseX, mouseY, agents[i].pos.x, agents[i].pos.y) > agents[i].rad*1.1f){
					for(int j = 0; j < agents.length; j++){//unselect all the agents
						agents[j].isSelected = false;
						agents[j].col = color(50);
						selectedAgent = null;
						finalCluster = null;
					}
				}
			}
		}
		
		if(postGame && !randGen3){
			if (mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY + proceedH) {
				postGame = false; //i changed this from endgame1
				communalHistory = true;
			}
	}
		
		if(communalHistory && !communalHistoryRndGenerator){
			if (mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY + proceedH) {
				communalHistory = false;
				startGame2 = true;
			}
	}
		
		if (inGame2) {
			float choiceY = height - height*0.02f;
			float bufferX = 70;
			float bufferY = 20;
			if(mouseY < height - height/40 + bufferY && mouseY > choiceY - bufferY){ //if you're at the bottom of the screen
				if(mouseX > width*0.1f - bufferX  && mouseX < width*0.1f + bufferX){ //first interaction generate predators
					selRevolt = true;
					selOppression = false;
					forceCulturalDiff = false;
					forceCulturalSim = false;
					finalStructure = false;
					showInduceRevoltInfo = true;
					showInduceOppressionInfo = false;
					showForceCultDiffInfo = false;
					showForceCultSimInfo = false;
					showFinalStructureInfo = false;
				}
				
				if(mouseX > width*0.25f - bufferX  && mouseX < width*0.25f + bufferX){ //second interaction stop agent
					selRevolt = false;
					selOppression = true;
					forceCulturalDiff = false;
					forceCulturalSim = false;
					finalStructure = false;
					showInduceRevoltInfo = false;
					showInduceOppressionInfo = true;
					showForceCultDiffInfo = false;
					showForceCultSimInfo = false;
					showFinalStructureInfo = false;
				}
				
				if(mouseX > width*0.45f - bufferX  && mouseX < width*0.45f + bufferX){ //third interaction remove connec
					selRevolt = false;
					selOppression = false;
					forceCulturalDiff = false;
					forceCulturalSim = true;
					finalStructure = false;
					showInduceRevoltInfo = false;
					showInduceOppressionInfo = false;
					showForceCultDiffInfo = true;
					showForceCultSimInfo = false;
					showFinalStructureInfo = false;
				}
				
				if(mouseX > width*0.65f - bufferX  && mouseX < width*0.65f + bufferX){ //select cluster
					selRevolt = false;
					selOppression = false;
					forceCulturalDiff = true;
					forceCulturalSim = false;
					finalStructure = false;
					showInduceRevoltInfo = false;
					showInduceOppressionInfo = false;
					showForceCultDiffInfo = false;
					showForceCultSimInfo = true;
					showFinalStructureInfo = false;
				}
				
				if(mouseX > width*0.85f - bufferX  && mouseX < width*0.85f + bufferX){ //select cluster
					selRevolt = false;
					selOppression = false;
					forceCulturalDiff = false;
					forceCulturalSim = false;
					finalStructure = true;
					showInduceRevoltInfo = false;
					showInduceOppressionInfo = false;
					showForceCultDiffInfo = false;
					showForceCultSimInfo = false;
					showFinalStructureInfo = true;
				}
			}else{
				for(int i = 0; i < community.size(); i++){
					Agent a = community.get(i);
					a.isSelected2 = false;
					if(dist(mouseX, mouseY, a.pos.x, a.pos.y) < a.rad && a.isAlive){
						if(selRevolt && induceRevolt > 0){ // --------------------------------------select revolt!
							//go through all the power relationships
							for(int j = 0; j < a.agentsPower.size(); j++){
								//for anyone more powerful (applying force on the agent)
								Agent other = a.agentsPower.get(j);
								if(a.culture[1] < other.culture[1]){
									PVector f = PVector.sub(other.pos, a.pos);
									f.normalize();
									
									f.mult(revoltMultiplier);
									//push them away
									other.applyForce(f);
									//remove their culture //TODO should it be that radical?
									other.culture[1] = 0.0f;
								}
								
								a.hasRevolted = true;
								
							}
							induceRevolt--;
						}
						
						if(selOppression && induceOppression > 0){ //--------------------------------------select oppression
							//go through all the power relationships
							for(int j = 0; j < a.agentsPower.size(); j++){
								
								Agent other = a.agentsPower.get(j);
								//for anyone less powerful, apply force on the agent
								if(a.culture[1] > other.culture[1]){
									PVector f = PVector.sub(other.pos, a.pos);
									f.normalize();
									
									f.mult(revoltMultiplier);
									//push them away
									other.applySpecialForce(f);
									//remove their culture
									other.culture[1] = 0.0f;
								}
								a.hasOppressed = true;
							}
							induceOppression--;
						}
						
						if(forceCulturalDiff && induceCulturalDiff > 0){ //--------------------------------------induce cultural diff
							//go through all the friendship agents
							float[] sumcult = new float[4];
							float[] averageCulture = new float[4];
							for(int j = 0; j < a.agentsFriendship.size(); j++){
								Agent other = a.agentsFriendship.get(j);
								for(int k = 0; k < a.culture.length; k++){
									//sumcult is the addition of all cultures
									sumcult[k] += other.culture[k];
								}
							}
							
							for(int k = 0; k < a.culture.length; k++){ //then we make the average
								averageCulture[k] = sumcult[k]/a.agentsFriendship.size();
								
								if(averageCulture[k] <= a.culture[k]){ //if the average culture is below this agent's culture, then make this agent's culture = 10
									a.culture[k] = 9.5f;
								}
								
								if(averageCulture[k] >= a.culture[k]){ //if the average culture is above this agent's culture, then make this agent's culture = 0
									a.culture[k] = 0.5f;
								}
								
								a.hasForcedDiff = true;
							}
							induceCulturalDiff--;
						}
						
						if(forceCulturalSim && induceCulturalSim > 0){ //--------------------------------------induce cultural sim
							//go through all the friendship agents
							//make an average of all their culture
							//their culture is now your culture
							
							float[] sumcult = new float[4];
							float[] averageCulture = new float[4];
							for(int j = 0; j < a.agentsFriendship.size(); j++){
								Agent other = a.agentsFriendship.get(j);
								for(int k = 0; k < a.culture.length; k++){
									//sumcult is the addition of all cultures
									sumcult[k] += other.culture[k];
								}
							}
							
							for(int k = 0; k < a.culture.length; k++){ //then we make the average
								averageCulture[k] = sumcult[k]/a.agentsFriendship.size();
								
								a.culture[k] = averageCulture[k];
							}
							
							a.hasForcedSim = true;
							induceCulturalSim--;
						}
						
						if(finalStructure){						
							if(PVector.dist(mouse, a.pos) < a.rad){
								for(int k = 0; k < community.size(); k++){
									Agent ag = community.get(k);
									ag.isSelected2 = false;
								}
								a.isSelected2 = !a.isSelected2;
								selectedAgent2 = a;
							}
							
							canEndGame2 = true;
						}
					}else if(selectedAgent2 != null && dist(mouseX, mouseY, selectedAgent2.pos.x, selectedAgent2.pos.y) > selectedAgent2.rad*1.1f && finalStructure){
						selectedAgent2 = null;
					}
				}
			}
		}
		
		if(inGame3){
			float choiceY = height - height*0.02f;
			float bufferX = 70;
			float bufferY = 20;
			if(mouseY < height - height/40 + bufferY && mouseY > choiceY - bufferY){ //if you're at the bottom of the screen
				if(mouseX > width*0.1f - bufferX  && mouseX < width*0.1f + bufferX){ //first interaction 
					selWealthInc = true;
					selWealthDec = false;
					selWarInduce = false;
					selAllianceBreak = false;
					selFinalProceed = false;
					showWealthIncInfo = true;
					showWealthDecInfo = false;
					showWarInduceInfo = false;
					showAllianceBreakInfo = false;
					showFinalProceedInfo = false;
				}
				
				if(mouseX > width*0.25f - bufferX  && mouseX < width*0.25f + bufferX){ //second interaction 
					selWealthInc = false;
					selWealthDec = true;
					selWarInduce = false;
					selAllianceBreak = false;
					selFinalProceed = false;
					showWealthIncInfo = false;
					showWealthDecInfo = true;
					showWarInduceInfo = false;
					showAllianceBreakInfo = false;
					showFinalProceedInfo = false;
				}
				
				if(mouseX > width*0.45f - bufferX  && mouseX < width*0.45f + bufferX){ //third interaction
					selWealthInc = false;
					selWealthDec = false;
					selWarInduce = true;
					selAllianceBreak = false;
					selFinalProceed = false;
					showWealthIncInfo = false;
					showWealthDecInfo = false;
					showWarInduceInfo = true;
					showAllianceBreakInfo = false;
					showFinalProceedInfo = false;
				}
				
				if(mouseX > width*0.65f - bufferX  && mouseX < width*0.65f + bufferX){ //4
					selWealthInc = false;
					selWealthDec = false;
					selWarInduce = false;
					selAllianceBreak = true;
					selFinalProceed = false;
					showWealthIncInfo = false;
					showWealthDecInfo = false;
					showWarInduceInfo = false;
					showAllianceBreakInfo = true;
					showFinalProceedInfo = false;
				}
				
				if(mouseX > width*0.85f - bufferX  && mouseX < width*0.85f + bufferX){ //final
					selWealthInc = false;
					selWealthDec = false;
					selWarInduce = false;
					selAllianceBreak = false;
					selFinalProceed = true;
					showWealthIncInfo = false;
					showWealthDecInfo = false;
					showWarInduceInfo = false;
					showAllianceBreakInfo = false;
					showFinalProceedInfo = true;
				}
			}else{
				if(selFinalProceed) selFinalProceed = false;
			}
			//actual interactions
			
			
			
			for(int i = 0; i < others.size(); i++){
				Nation n = others.get(i);
				if(dist(mouseX, mouseY, n.pos.x, n.pos.y) < n.rad){
					if(selWealthInc){
						n.totalWealth *= wealthIncrease;
					}
					
					if(selWealthDec){
						n.totalWealth *= wealthDecrease;
					}
					
					if(selWarInduce){
						n.culturalRequirementWar *= 0.5f;
					}
					
					if(selAllianceBreak){
						for(int j = 0; j < n.currentAlliances.size(); j++){
							Alliance a = n.currentAlliances.get(j);
							
							//remove from both parties
							a.n2.currentAlliances.remove(a);
							a.n1.currentAlliances.remove(a);
							
							//remove from general AL
							alliances.remove(a);
							
							//have them blacklist each other
							if(!a.n1.rogueStates.contains(a.n2)) a.n1.rogueStates.add(a.n2);
							if(!a.n2.rogueStates.contains(a.n1)) a.n2.rogueStates.add(a.n1);
							}
						}
					}
				}
			}
		}
	
	public void mouseWheel(MouseEvent e){
		if(inGame1){
			
			float f = e.getCount();
			
			if(zCam > zCamClosest && zCam < zCamFurthest){
				zCam -= camSpeed * f;
			}
			if(zCam <= zCamClosest){
				zCam = zCamClosest+1;
			}
			if(zCam >= zCamFurthest){
				zCam = zCamFurthest-1;
			}
		}
	}

	public void keyPressed() {
		
		if(key == 'p'){
			saveFrame();
		}
		
		if(key == '1'){
			intro = false;
			startGame1 = true;
			startGame2 = false;
			startGame3 = false;
			inGame1 = false;
			inGame2 = false;
			inGame3 = false;
			endGame1 = false;
			endGame2 = false;
			endGame3 = false;
			emailScreen = false;
		}
		
		if(key == '2'){
			intro = false;
			startGame1 = false;
			startGame2 = true;
			startGame3 = false;
			inGame1 = false;
			inGame2 = false;
			inGame3 = false;
			endGame1 = false;
			endGame2 = false;
			endGame3 = false;
			emailScreen = false;
		}
		
		if(key == '3'){
			intro = false;
			startGame1 = false;
			startGame2 = false;
			startGame3 = true;
			inGame1 = false;
			inGame2 = false;
			inGame3 = false;
			endGame1 = false;
			endGame2 = false;
			endGame3 = false;
			emailScreen = false;
		}
		
		if(key == '4'){
			intro = false;
			startGame1 = false;
			startGame2 = false;
			startGame3 = false;
			inGame1 = true;
			inGame2 = false;
			inGame3 = false;
			endGame1 = false;
			endGame2 = false;
			endGame3 = false;
			emailScreen = false;
		}
		
		if(key == '5'){
			intro = false;
			startGame1 = false;
			startGame2 = false;
			startGame3 = false;
			inGame1 = false;
			inGame2 = true;
			inGame3 = false;
			endGame1 = false;
			endGame2 = false;
			endGame3 = false;
			emailScreen = false;
		}
		
		if(key == '6'){
			intro = false;
			startGame1 = false;
			startGame2 = false;
			startGame3 = false;
			inGame1 = false;
			inGame2 = false;
			inGame3 = true;
			endGame1 = false;
			endGame2 = false;
			endGame3 = false;
			emailScreen = false;
		}
		
		if(key == '7'){
			intro = false;
			startGame1 = false;
			startGame2 = false;
			startGame3 = false;
			inGame1 = false;
			inGame2 = false;
			inGame3 = false;
			endGame1 = true;
			endGame2 = false;
			endGame3 = false;
			emailScreen = false;
		}
		
		if(key == '8'){
			intro = false;
			startGame1 = false;
			startGame2 = false;
			startGame3 = false;
			inGame1 = false;
			inGame2 = false;
			inGame3 = false;
			endGame1 = false;
			endGame2 = true;
			endGame3 = false;
			emailScreen = false;
		}
		
		if(key == '9'){
			intro = false;
			startGame1 = false;
			startGame2 = false;
			startGame3 = false;
			inGame1 = false;
			inGame2 = false;
			inGame3 = false;
			endGame1 = false;
			endGame2 = false;
			endGame3 = true;
			emailScreen = false;
		}
		
		if(inGame1){	
			if(selectedAgent != null && key == ' '){
				fading = true;
			}
		}
		
			if(inGame2){
				if(finalStructure && selectedAgent2 != null && key == ' '){
					finalStructurePos = new PVector[community.size()];
					for(int i = 0; i < finalStructurePos.length; i++){
						finalStructurePos[i] = new PVector(community.get(i).pos.x, community.get(i).pos.y);
					}
					fading = true;
				}
			}
			
			if(inGame3){
				if(key == ' '){
					fading = true;
				}
			}
			
			if(endGame2){
				if(key == ' '){//these are disabled for now for the sake of just playtesting
					//endGame2 = false;
					//startGame3 = true;
				}
			}
			
			if(emailScreen){
				if ((int)key == 8) {
					typedText = typedText.substring(0, max(0, (typedText.length())-1));
				}else if(key != CODED) {
					typedText = typedText + key;
					typedEmail = true;
				}
			}
		}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { polsys.PolSys.class.getName() });
	}
	}



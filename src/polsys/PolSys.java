package polsys;

import java.util.ArrayList;

//email libraries
import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;
import java.awt.Dimension;
import java.awt.Toolkit;
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

import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.SecretKeyResolver;

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
import processing.core.PVector;
import sun.security.tools.keytool.Resources_pt_BR;

public class PolSys extends PApplet {

	/*--------------------------------*/
	// This is the source code for Social Contact, MFA Thesis by Pierre Depaz, dated 2015.05.20
	// 
	// The program is written in Processing, using the Eclipse IDE and the Proclipsing plug-in.
	// As object-oriented programming, this document is separated in classes, each classes being
	// centered around one particular concept. Since the simulation is a tryptich, I have tried to
	// keep variables and functions grouped under these parts: 1, 2 and 3
	//
	// I'd be happy to answer any questions, or send any updated version of the code: pierre.depaz@gmail.com
	//
	/*--------------------------------*/

	
	/* ---------------- Declaring the existence of any agents in the game ---------------- */
	public static String language;
	Button englishButton;
	Button frenchButton;
	
	public static Seasons seasons;
	
	public static Agent[] agents;;
	public static ArrayList<Agent> checkedAgents;
	public static ArrayList<Predator> predators;
	public static ArrayList<Connection> connections;
	public static ArrayList<Cluster> totalClusters;
	public static ArrayList<Resource> resources;
	public static Agent selectedAgent;
	public static Cluster finalCluster;
	public static Agent[] finalAgents;
	Connection hoveredConnection;
	
	public static ArrayList<Agent> community;
	public static ArrayList<ConnectionLove> connectionsLove;
	public static ArrayList<ConnectionPower> connectionsPower;
	public static ArrayList<ConnectionFriendship> connectionsFriendship;
	public static ArrayList<Resource> resources2;
	public static Agent selectedAgent2;
	
	public static ArrayList<Alliance> alliances;
	public static ArrayList<Trade> trades;
	public static ArrayList<War> wars;
	public static ArrayList<Resource> resources3;
	public static Nation selectedNation;
	public Nation nation;
	public static ArrayList<Nation> others;
	
	static float timerWar;
	static float startTimeWar;
	
	boolean canRemoveConnec;
	boolean initiate;
	static float beginAlpha;

	int numberOfAgents;
	int numberOfPredators;
	int numberOfResources;
	
	boolean allAgentsDead;
	float rectDeadAlpha;
	
	/* ---------------- User Interface variables ---------------- */
	public static float optimalWidth;
	public static float optimalHeight;
	
	static float translateBufferX;
	static float translateBufferY;
	static float scaleW;
	static float scaleH;
	
	public static float rectBorderX;
	public static float rectBorderY;
	public static float rectBorderW;
	public static float rectBorderH;
	public static float rectBorderHMenu;
	public static float rectBorderHTemp;

	public static int bgColBox;
	public static int newBgColBox;
	float bgColBoxLerpSpeed;
	float bgColBoxLerpInc;
	
	public static PFont mainFont;
	public static PFont subtitleFont;
	public static PFont loadingFont;
	public static PFont headerFont;
	public static PFont textFont;
	public static PFont thoughtFont;
	public static PFont seasonsFont;
	
	public static int mainFontSize;
	public static int subtitleFontSize;
	public static int loadingFontSize;
	public static int headerFontSize;
	public static int textFontSize;
	public static int thoughtFontSize;
	public static int seasonsFontSize;
	
	float rectProceedAlpha;
	
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
	
	float selPulse;
	
	float proceedX;
	float proceedY;
	float proceedW;
	float proceedH;
	float proceedSW;
	
	float noiseVal;
	
	float loadingPos;
	float loadingLerpVal;
	float loadingLerpInc;
	
	float alphaFade;
	float loadingTextAlpha;
	float loadingTextVal;
	String loadingText;
	float fadeRate;
	static boolean fading;
	
	PVector rectFadeTopPos;
	PVector rectFadeRightPos;
	PVector rectFadeLeftPos;
	PVector rectFadeBottomPos;
	
	float rectFadeTopW;
	float rectFadeTopH;
	float rectFadeRightW;
	float rectFadeRightH;
	float rectFadeLeftW;
	float rectFadeLeftH;
	float rectFadeBottomW;
	float rectFadeBottomH;
	float rectFadeAlpha;
	float rectFadeAlphaInc;
	float rectFadeBufferMax;
	float rectFadeBuffer;
	
	static boolean canStart;
	static int interactions;
	int interactionsThreshold;
	
	/* ---------------- Legend/help variables ---------------- */
	boolean canShowLegend;
	
	PVector showLegendPos;
	float legendW;
	float legendH;
	float legendLerpVal;
	float legendLerpInc;
	
	PVector legendBackgroundPos;
	float legendBackgroundAlpha;
	float legendBackgroundAlphaInc;
	float legendTextAlpha;
	float legendTextAlphaInc;
	PVector legendTextPos;
	
	String legendTextContent;
	String legendTextContent1;
	String legendTextContent2;
	String legendTextContent3;
	
	String legendTextContent1FR;
	String legendTextContent2FR;
	String legendTextContent3FR;
	
	String legendButtonText;
	
	float showLegendAlpha;
	int showLegendSW;
	
	Button showLegendButton;
	Button resetButton;
	PVector resetButtonPos;
	float resetButtonSize;
	int resetStage;
	boolean canReset;
	
	boolean canShowAssumptions;
	Button showAssumptions;
	float showAssumptionsButtonW;
	PVector showAssumptionsButtonPos;
	
	String[] assumptions;
	PVector assumptionsTextPos;
	float assumptionsTextW;
	float assumptionsTextH;
	
	PVector assumptionsRectPos;
	float assumptionsRectW;
	float assumptionsRectH;
	static float assumptionsRectAlpha;
	float assumptionsRectAlphaInc;
	float assumptionsTextAlpha;
	float assumptionsTextAlphaInc;
	
	/* ---------------- Sound variables ---------------- */
	static AudioContext ac;
	static Gain masterGain;
	static Gain gMasterRhythm;
	static Gain gMasterSeasons;
	
	static Envelope eMasterRhythm;
	static Envelope eMasterSeasons;
	
	float rhythmTimer;
	float rhythmStartTimer;
	

	/* ---------------- StartGame1 variables ---------------- */
	float rad;
	static int connec;
	int minConnec;
	int maxConnec;
	int allowMovement;
	String allowMovementText;

	static float formingStableRelationships;
	static float meetingOthers;
	static float rememberDead;

	float violence;
	String violenceText;
	
	float resourceSeek;
	String resourceText;
	static int colorResources;

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
	
	
	/* ---------------- Hovering variables ---------------- */
	boolean isHovering;
	
	Agent hoveredAgent;
	float hoverTextAlpha;
	float hoverTextAlphaRate;
	boolean hovering;
	static int colorPredator;
	Predator hoveredPredator;
	float hoverTextAlphaPredator;
	float hoverRandPredator;
	float hoverTextAlphaPredatorRate;
	boolean hoveringPredator;
	String predatorThought;
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
	
	/* ---------------- Timer variables ---------------- */
	float timer;
	float startTime;
	float startPhaseTimer;
	float agentsArriveTimer;
	float agentsArriveStartTimer;
	int arriveIndex;
	
	/* ---------------- Voronoi diagram for InGame3 ---------------- */
	Voronoi voronoi;

	/* ---------------- Boolean (true/false) variables for which screen to display ---------------- */
	static boolean splash;
	static boolean statement;
	static boolean intro;
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
	
	/* ---------------- Variables for the splash screen ---------------- */
	static Fish[] splashFish;
	PVector[] splashPos;
	float[] splashLerpVal;
	float [] splashLerpInc;
	PVector[] splashStartPos;
	PVector[] splashTempPos;
	PVector[] splashEndPos;
	
	/* ---------------- Variables for the artist statement ---------------- */
	int statementPage;
	String[] statementText;
	String[] statementTextFR;
	String[] currentStatement;
	String[] currentStatementFR;
	String[] statementText0;
	String[] statementText1;
	String[] statementText2;
	String[] statementText0FR;
	String[] statementText1FR;
	String[] statementText2FR;
	float statementAlpha;
	boolean switchPages;
	
	float statementRectSize;
	Button statementPage0;
	Button statementPage1;
	Button statementPage2;
	
	PVector statementRectPos;
	PVector statementTargetPos;
	float statementLerpVal;
	float statementLerpInc;

	
	/* ---------------- Variables for InGame1 ---------------- */
	boolean selGenPred;
	boolean selStopAgent;
	boolean selRemoveConnec;
	boolean selFinalCluster;
	boolean showGenPredInfo;
	boolean showStopAgentInfo;
	boolean showRemoveConnectionInfo;
	boolean showSelFinalClusterInfo;
	
	float interactionAlpha;
	float interactionAlphaInc;
	

	/* ---------------- Introduction variables ---------------- */
	float textIntroAlpha;
	int introStage;
	boolean readyToStart;
	String textIntro;
	
	float rIntro;
	
	float noiseArtVal;
	float noiseArtStep;
	float noiseArtThreshold;
	
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
	float politicsAlpha;
	float titleIntroAlpha;
	
	/* ---------------- Fish tank variables ---------------- */
	float rotateTimer;
	float rotateStartTime;
	int rotatePos;
	
	boolean hasClicked;
	
	public static ArrayList<Fish> fishes;
	static PVector fishTankPos;
	float fishTankSize;
	float fishTankAlpha;
	float fishTankExpand;
	static int fishNumber;
	PVector deadFishPos;
	PVector deadFishPos2;
	static int colorPower;
	static int colorFriendship;
	static int colorLove;
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
	
	float showAlpha;
	
	Button predatorButton;
	Button resourceButton;
	Button connectionsButton;
	
	Button friendshipButton;
	Button loveButton;
	Button powerButton;
	
	Button warButton;
	Button tradeButton;
	Button alliancesButton;
	
	/* ---------------- Button variables for StartGame1 ---------------- */
	float buttonGoalRight;
	float buttonGoalLeft;
	float buttonGoalHeight;
	float bufferGoalHeight;
	float subtitleHeight;
	float subtitle2Height;
	float subtitle3Height;
	
	Button connectionsUpButton;
	Button connectionsDownButton;

	Button violenceUpButton;
	Button violenceDownButton;
	
	Button resourceUpButton;
	Button resourceDownButton;
	
	Button stayCloseUpButton;
	Button stayCloseDownButton;
	Button meetingOthersUpButton; //meeting others is mostly going to be what happens when a single agent bounces on another community (combine rejection+kill)
	Button meetingOthersDownButton;
	Button rememberDeadUpButton;
	Button rememberDeadDownButton;

	Button pursuitLeftButton;
	Button pursuitRightButton;

	Button startButton;
	
	String formingStableRelationshipsString;
	String meetingOthersString;
	String rememberDeadString;
	String violenceString;
	String resourceString;

	int genPred;
	int stopAgent;
	int removeConnec;
	
	/* ---------------- Variables for EndGame1 ---------------- */
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
	
	String[] finalIndividualStories;
	String endGame1_text;
	
	
	//loading the different sentences to be assembled at the end of each chapter
	String[] goal0;
	String[] goal1;
	String[] goal2;
	String[] goal3;
	
	String[] meetingOthersEndString;
	String[] resourcesString;
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
	String[] hasFoughtOthers;
	String[] isTamer;
	
	String[] meetingOthersEndStringX;
	String[] resourcesStringX;
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
	String[] hasFoughtOthersX;
	String[] isTamerX;
	
	int meetingOthersRND;
	int resourcesRND;

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
	int hasFoughtRND;
	
	int meetingOthersRNDX;
	int resourcesRNDX;
	
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
	int hasFoughtRNDX;
	
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
	
	/* ---------------- Variables for StartGame2 ---------------- */
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
	
	int bezierNum;
	PVector[] startPointBezierFish;
	PVector[] anchorPointABezierFish;
	PVector[] anchorPointBBezierFish;
	PVector[] endPointBezierFish;
	
	float mapMin;
	float mapMax;
	
	float powerForce;
	float friendshipForce;
	float loveForce;
	
	float ageModifier;
	int numberOfChildren;
	float independenceOfChildren;
	float spatialCultureIncrement;
	
	/* ---------------- Variables for InGame2 ---------------- */
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
	
	/* ---------------- Variables for EndGame2 ---------------- */
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
	
	
	/* ---------------- Variables for StartGame3 ---------------- */
	int numberOfOthers;

	ArrayList<PVector> othersPositions;
	ArrayList<PVector> OTHERS_POSITIONS;
	
	int randomStatement;
	String statementStr;
	String statementHeader;
	float statementH;
	float statementHMax;
	float statementHInc;
	float statementRectAlpha;
	float statementTextAlpha;
	float statementAlphaInc;
	Nation hoveredNation;
	
	float allianceModifier;
	float tradeModifier;
	float warModifier;
	int nationGoal;
	float victoryBehaviour;
	float tradeLimit;
	float allianceTrust;
	float cultureExternal;
	
	static int colorAlliances;
	static int colorTrade;
	static int colorWar;
	
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

	/* ---------------- Variables for EndGame3 ---------------- */
	boolean randomGen3;
	String endGame3_text;
	
	float endWealthThreshold;
	
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
	
	/* ---------------- Variables for individual thoughts ---------------- */
	/* ---------------- Thoughts - InGame1 ---------------- */
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
	
	/* ---------------- Thoughts - InGame2 ---------------- */
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
	
	/* ---------------- Thoughts - InGame3 ---------------- */
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

	/* ---------------- Variables for email screen ---------------- */
	boolean emailScreen;
	boolean finalScreen;
	Button sendButton;
	String typedText;
	String errorMessageMail;
	boolean typedEmail;
	int blinkingType;
	
	String emailAddress;
	String emailStory;
	
	/* ---------------- thank you note ---------------- */
	float finalAlphaSmall;
	float finalAlphaFull;
	float finalTimerSmall;
	float finalTimerFull;
	
	float rectCol;
	
	static boolean isPaused;
	
	public void setup() {
		//randomSeed(5);// FOR DEBUG
		//size(1600, 900);
		//size(displayWidth, displayHeight);
		
		optimalWidth = 1600;
		optimalHeight = 900;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		float SCREEN_WIDTH = (float)screenSize.getWidth();
		float SCREEN_HEIGHT = (float)screenSize.getHeight();
		
		if(SCREEN_WIDTH < optimalWidth){
			translateBufferX = (optimalWidth - SCREEN_WIDTH)*0.5f;
			translateBufferY = (optimalHeight - SCREEN_HEIGHT)*0.5f;
			scaleW = SCREEN_WIDTH/optimalWidth;
			scaleH = SCREEN_HEIGHT/optimalHeight;
			println("scaleW", scaleW);
			println("scaleH", scaleH);
		}else{
//			scaleW = 1;
//			scaleH = 1;
			translateBufferX = (SCREEN_WIDTH - optimalWidth)*0.5f;
			translateBufferY = (SCREEN_HEIGHT - optimalHeight)*0.5f;
			scaleW = SCREEN_WIDTH/optimalWidth;
			scaleH = SCREEN_HEIGHT/optimalHeight;
		}

		size(1600, 900);
		frame.setResizable(true);
		
		System.setProperty("java.net.preferIPv4Stack" , "true");
		smooth();
		cursor(CROSS);
		println("current display - width: " + displayWidth);
		println("current display - height: " + displayHeight);

		frameRate(30);
		
		mouse = new PVector();
		
		language = "english";
		
		englishButton = new Button(width*0.95f, height*0.95f, width*0.05f, 7, this);
		frenchButton = new Button(width*0.05f, height*0.95f, width*0.05f, 8, this);
		
		initiate = false;
		beginAlpha = 200;

		bgColBox = color(255);
		newBgColBox = color(255);
		bgColBoxLerpSpeed = 0.0f;
		bgColBoxLerpInc = 0.025f;
		
		rectBorderX = width*0.00375f;
		rectBorderY = rectBorderX;
		rectBorderW = width*0.9925f;
		rectBorderH = height*0.95f;
		rectBorderHMenu = height-(rectBorderY*2.0f);
		rectBorderHTemp = rectBorderHMenu;
		
		splash = true;
		statement = false;
		intro = false;
		startGame1 = false;
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
		subtitleFont = loadFont("data/fonts/Ashbury-Light-24.vlw");
		loadingFont = loadFont("data/fonts/Ashbury-Light-34.vlw");
		headerFont = loadFont("data/fonts/EdwardPro-Normal-36.vlw");
		if(width > 1500){
			textFont = loadFont("data/fonts/EdwardPro-ExtraLight-24.vlw");
		}else{
			textFont = loadFont("data/fonts/EdwardPro-ExtraLight-16.vlw");
		}
		thoughtFont = loadFont("data/fonts/EdwardPro-ExtraLight-16.vlw");
		seasonsFont = loadFont("data/fonts/Ashbury-Light-100.vlw");
		
		mainFontSize = 60;
		subtitleFontSize = 24;
		loadingFontSize = 34;
		headerFontSize = 36;
		if(width > 1500){
			textFontSize = 24;
		}else{
			textFontSize = 16;
		}
		thoughtFontSize = 16;
		seasonsFontSize = 100;
		
		/* ---------------- Variables for splash screen ---------------- */
		int splashNum = 20;
		splashFish = new Fish[splashNum];
		splashLerpVal = new float[4];
		splashLerpInc = new float[4];
		splashStartPos = new PVector[4];
		splashTempPos = new PVector[4];
		splashEndPos = new PVector[4];
		
		for(int i = 0; i < splashNum; i++){
			splashFish[i] = new Fish(random(width*0.01f, width*0.9f), random(height*0.3f, height*0.9f), 5, (int)random(20, 40), 100+(int)random(55), this);
		}
		
		for(int i = 0; i < splashLerpVal.length; i++){
			splashLerpVal[i] = 0;
			splashLerpInc[i] = random(0.01f, 0.05f);
			splashStartPos[i] = splashFish[(int)random(splashFish.length)].pos;
			splashTempPos[i] = splashStartPos[i];
			splashEndPos[i] = splashFish[(int)random(splashFish.length)].pos;
		}
		
		/* ---------------- Variables for artist statement ---------------- */
		statementPage = 0;
		statementText = loadStrings("data/text/en/statement.txt");
		statementText0 = split(statementText[0], "$");
		statementText1 = split(statementText[1], "$");
		statementText2 = split(statementText[2], "$");
		
		statementTextFR = loadStrings("data/text/fr/statement.txt");
		statementText0FR = split(statementTextFR[0], "$");
		statementText1FR = split(statementTextFR[1], "$");
		statementText2FR = split(statementTextFR[2], "$");
		statementAlpha = 200;
		statementLerpVal = 1;
		statementLerpInc = 0.05f;
		currentStatement = statementText0;
		currentStatementFR = statementText0FR;
		
		switchPages = false;
		statementRectSize = width*0.015f;
		statementPage0 = new Button(width*0.425f, height*0.85f, statementRectSize, 5, this);
		statementPage1 = new Button(width*0.5f, height*0.85f, statementRectSize, 9, this);
		statementPage2 = new Button(width*0.575f, height*0.85f, statementRectSize, 10, this);
		
		statementRectPos = new PVector(statementPage0.pos.x, statementPage0.pos.y);
		statementTargetPos = statementRectPos;
		
		leftColX = width*0.15f;
		rightColX = width*0.5f;
		buttonBufferY = height*0.044f;
		bufferXstart1 = width*0.075f;
		headerHeight = height*0.2f;
		
		firstRowY= height*0.3f;
		secondRowY = height*0.5f;
		thirdRowY = height*0.7f;
		fourthRowY = height*0.75f;
		goalButtonHeight = 0.8f;
		
		canShowLegend = false;
	
		showLegendPos = new PVector(width*0.05f, height*0.9f);
		legendW = width*0.125f;
		legendH = height*0.0375f;
		legendLerpVal = 0;
		legendLerpInc = 0.001f;
	
		legendBackgroundPos = new PVector(width*0.5f, height*2);
		legendBackgroundAlpha = 0;
		legendBackgroundAlphaInc = 7.5f;
		legendTextAlpha = 0;
		legendTextAlphaInc = 10f;
		legendTextPos = new PVector(width*0.5f, height*0.1f);
		
		legendTextContent = "";
		legendTextContent1 = "Social Contact is an interactive triptych of political philosophy\n"
				+ "\n"
				+ "-\n"
				+ "\n"
				+ "our understanding of how we live together is conditioned by what we assume about people.\n"
				+ "\n"
				+ "Social Contact allows you to simulate the consequences of these assumptions."
				+ "\n"
				+ "choose those you agree with the most."
				+ "\n"
				+ "they will dictate the behaviors of abstracted agents, represented as circles.\n"
				+ "\n"
				+ "--\n"
				+ "\n"
				+ "this first part of the tryptich is dedicated to how agents first form bonds, or connections.\n"
				+ "\n"
				+ "these connections are represented by solid lines, between two different agents.\n"
				+ "\n"
				+ "the world contains both resources and predators.\n"
				+ "\n"
				+ "---\n"
				+ "\n"
				+ "to proceed to the next part of the triptych, select an agent to hear the story of how it lived in the world you constructed.";
		
		legendTextContent2 = "this second part of the triptych represent the evolution of a closed community.\n"
				+ "\n"
				+ "-\n"
				+ "\n"
				+ "love is represented by red lines.\n"
				+ "\n"
				+ "friendship is represented by green lines\n"
				+ "\n"
				+ "power is represented by black lines\n"
				+ "power is a one-way relation from an oppressor to an oppressee.\n"
				+ "\n"
				+ "--\n"
				+ "\n"
				+ "the culture of individuals is represented by\n"
				+ "the number of sides a circle has\n"
				+ "its radius,\n"
				+ "the size of the stroke,\n"
				+ "the sound it emits.";
		
		legendTextContent3 = "this last part of the triptych simulates the interactions of different, self-contained communities.\n"
				+ "\n"
				+ "-\n"
				+ "\n"
				+ "they can form alliances, go to war and trade."
				+ "\n"
				+ "--\n"
				+ "\n"
				+ "the color of a nation-state represents its culture.\n"
				+ "\n"
				+ "the size of a nation state represents its population.\n"
				+ "\n"
				+ "the stroke of a nation-state represents its wealth.\n";
		
		legendTextContent1FR = "";
		legendTextContent2FR = "";
		legendTextContent3FR = "";
		
		legendButtonText = "";
		
		showLegendAlpha = 100;
		showLegendSW = 1;
		
		showLegendButton = new Button(showLegendPos.x, showLegendPos.y, legendW, 2, this);
		
		canShowAssumptions = false;
		
		showAssumptionsButtonW = width*0.05f;
		showAssumptionsButtonPos = new PVector(rectBorderX+showAssumptionsButtonW*2.0f, rectBorderY+showAssumptionsButtonW*0.25f+1);
		showAssumptions = new Button(showAssumptionsButtonPos.x, showAssumptionsButtonPos.y, showAssumptionsButtonW, 2, this);
		
		assumptions = new String[7];
		assumptionsTextPos = new PVector(rectBorderX+showAssumptionsButtonW*2.0f, height*0.9f); //the Y component doesnt actually matter
		assumptionsTextW = width*0.175f;
		assumptionsTextH = height*0.1f;
		
		assumptionsRectPos = new PVector(rectBorderX+showAssumptionsButtonW*2.0f, height*0.5f-(rectBorderY*2.75f));
		assumptionsRectW = width*0.2f-2;
		assumptionsRectH = rectBorderH;
		assumptionsRectAlpha = 0;
		assumptionsRectAlphaInc = 12.0f;
		assumptionsTextAlpha = 0;
		assumptionsTextAlphaInc = 10.0f;
		
		resetButtonPos = new PVector(assumptionsTextPos.x, height*0.9f);
		resetButtonSize = showAssumptionsButtonW;
		resetButton = new Button(resetButtonPos.x, resetButtonPos.y, resetButtonSize, 6, this);	
		resetStage = 1;
		canReset = false;

		// -----------------------------------------------------------------------------------------------------------------------------------AUDIO
		
		ac = new AudioContext();
		masterGain = new Gain(ac, 3, 0.2f);
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

		connec = 3;
		minConnec = 3;
		maxConnec = 5;
		violence = 0.5f;
		violenceText = "";
		
		resourceSeek = 1.0f;
		resourceText = "";
		colorResources = color(0, 100, 0);

		startTime = millis();
		timer = 500;
		startPhaseTimer = 20000; // start phase is 5 seconds before checking if
									// we can end the simulation
		
		agentsArriveTimer = 7500.0f;
		agentsArriveStartTimer = 0.0f;
		arriveIndex = 0;
		totalDeaths = 0;
		
		isHovering = false;
		
		hoveredAgent = null;
		hoverTextAlpha = 0;
		hoverTextAlphaRate = 3.0f;
		hovering = false;
		hoverTextAlphaPredator = 0;
		hoverTextAlphaPredatorRate = 4.0f;
		hoveringPredator = false;
		hoveredPredator = null;
		predatorThought = "";
		colorPredator = color(210, 180, 140);
		hoverRand = random(1);
		hoverCanGenerateRand = true;
		
		draggedAgent = null;
		dragForce = 0.5f;
		dragCoeff = 2.0f;
		canDragAgent = true;
		isReleased = false;
		dragLerpSpeed = 0.0f;

		allowMovement = 0;
		allowMovementText = "";

		formingStableRelationships = 1.0f;
		meetingOthers = 1.0f;
		rememberDead = 1.0f;

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
		
		interactionAlpha = 0;
		interactionAlphaInc = 0.75f;

		numberOfAgents = 100;
		agents = new Agent[numberOfAgents];
		for(int i = 0; i < agents.length; i++){
			agents[i] = new Agent();
		}
		
		allAgentsDead = false;
		
		selectedAgent = null;
		selectedAgent2 = null;
		checkedAgents = new ArrayList<Agent>();
		connections = new ArrayList<Connection>();
		numberOfPredators = 10;
		predators = new ArrayList<Predator>();
		resources = new ArrayList<Resource>();
		numberOfResources = 15;
		
		for(int i = 0; i < numberOfResources; i++){
			resources.add(new Resource(random(width*0.1f, width*0.9f), random(height*0.1f, height*0.8f), (int)random(40, 100), this));
		}
		
		resources2 = new ArrayList<Resource>();
		resources3 = new ArrayList<Resource>();
		
		selectedNation = null;
		
		hoveredConnection = null;
		canRemoveConnec = false;
		
		community = new ArrayList<Agent>();
		connectionsLove = new ArrayList<ConnectionLove>();
		connectionsPower = new ArrayList<ConnectionPower>();
		connectionsFriendship = new ArrayList<ConnectionFriendship>();

		totalClusters = new ArrayList<Cluster>();
		
		selPulse = 0;
		
		// ------------------------------------------------------------------------------------ INTRO
		textIntroAlpha = -20;
		introStage = 0;
		readyToStart = false;
		textIntro = "";
		rIntro = 0;
		introPosA = new PVector(width*0.2f, height/2);
		introPosB = new PVector(width*0.8f, height/2);
		introPosC = new PVector(width*0.5f, height*0.9f);
		
		noiseArtVal = random(10);
		noiseArtStep = 0.1f;
		noiseArtThreshold = 0.65f;
		
		circleIntroAlpha = 0;
		lineIntroAlpha = 0;
		linesIntroAlpha = 0;
		
		othersIntro = 120;
		othersIntroAlpha = 0;
		politicsAlpha = 0;
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
		
		
		// ------------------------------------------------------------------------------------ FISHTANK
		fishTankPos = new PVector(width*0.85f, height*0.5f);
		buttonGoalLeft = fishTankPos.x*0.9f;
		buttonGoalRight = fishTankPos.x*1.1f;
		buttonGoalHeight = height*0.7725f;
		bufferGoalHeight = height*0.01f;
		subtitleHeight = height*0.13f;
		subtitle2Height = height*0.16f;
		subtitle3Height = height*0.19f;

		rotateTimer = 2000;
		rotateStartTime = millis();
		rotatePos = 0;
		hasClicked = true;
		
		fishes = new ArrayList<Fish>();
		fishTankSize = height*0.3334f;
		fishTankAlpha = 0;
		fishTankExpand = 0;
		
		fishResourceSides = (int)random(5, 10);
		fishResourceRad = 10;
		fishResourcePos = new PVector(random(fishTankPos.x*0.95f, fishTankPos.x*1.05f), random(fishTankPos.y*0.95f, fishTankPos.y*1.05f));
		fishResourceSidesVar = new float[fishResourceSides];
		for(int i = 0; i < fishResourceSides; i++){
			fishResourceSidesVar[i] = random(10, 50);
		}
		
		fishNumber = 5;
		for(int i = 0; i < fishNumber; i++){
			fishes.add(new Fish(random(fishTankPos.x - fishTankSize/2 + rad*1.5f, fishTankPos.x + fishTankSize/2 - rad*1.5f), random(fishTankPos.y - fishTankSize/2 + rad*1.5f, fishTankPos.y + fishTankSize/2 - rad*1.5f), connec, 25, 250, this));
		}
		deadFishPos = new PVector(fishTankPos.x*0.95f, fishTankPos.y*1.05f);
		deadFishPos2 = new PVector(fishTankPos.x*0.9f, fishTankPos.y*0.95f);
		
		fishPred = new Fish(fishTankPos.x, fishTankPos.y, this);
		
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
			citizenFishPos[i] = new PVector(random(fishTankPos.x*1.05f, fishTankPos.x*1.1f), random(fishTankPos.y*1.1f, fishTankPos.y*1.3f));
			citizenFishPos[i].x = constrain(citizenFishPos[i].x, fishTankPos.x - fishTankSize*0.5f,  fishTankPos.x + fishTankSize*0.5f);
			citizenFishPos[i].y = constrain(citizenFishPos[i].y, fishTankPos.y - fishTankSize*0.5f,  fishTankPos.y + fishTankSize*0.5f);
			citizenFishWeight[i] = (int)random(1, 4);
			citizenFishRad[i] = random(5, 10);
		}
		
		citizenFishNum2 = (int)random(15, 30);
		citizenFishPos2 = new PVector[citizenFishNum2];
		citizenFishRad2 = new float[citizenFishNum2];
		citizenFishWeight2 = new int[citizenFishNum2];
		for(int i = 0; i < citizenFishNum2; i++) {
			citizenFishPos2[i] = new PVector(random(fishTankPos.x*0.9f, fishTankPos.x*0.95f), random(fishTankPos.y*0.7f, fishTankPos.y*0.9f));
			citizenFishPos2[i].x = constrain(citizenFishPos2[i].x, fishTankPos.x - fishTankSize*0.5f,  fishTankPos.x + fishTankSize*0.5f);
			citizenFishPos2[i].y = constrain(citizenFishPos2[i].y, fishTankPos.y - fishTankSize*0.5f,  fishTankPos.y + fishTankSize*0.5f);
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
		
		if(language == "english"){
			storyTitle1 = "my life";
			storyTitle2 = "our lives";
			storyTitle3 = "their lives";
		}else{
			storyTitle1 = "ma vie";
			storyTitle2 = "nos vies";
			storyTitle3 = "leurs vies";
		}
		
		//  BUTTONS
		
		// ------------------------------------------------------------ 1
		float fishButtonSize = 30;
		connectionsButton = new Button(fishTankPos.x*0.9f, firstRowY, fishButtonSize, 13, this);
		resourceButton = new Button(fishTankPos.x*1.0f, firstRowY, fishButtonSize, 12, this);
		predatorButton = new Button(fishTankPos.x*1.1f, firstRowY, fishButtonSize, 11, this);
		
		friendshipButton = new Button(fishTankPos.x*0.9f, firstRowY, fishButtonSize, 11, this);
		loveButton = new Button(fishTankPos.x*1.0f, firstRowY, fishButtonSize, 12, this);
		powerButton = new Button(fishTankPos.x*1.1f, firstRowY, fishButtonSize, 13, this);
		
		alliancesButton = new Button(fishTankPos.x*0.9f, firstRowY, fishButtonSize, 11, this);
		warButton = new Button(fishTankPos.x*1.0f, firstRowY, fishButtonSize, 12, this);
		tradeButton = new Button(fishTankPos.x*1.1f, firstRowY, fishButtonSize, 13, this);
		

		connectionsUpButton = new Button(rightColX, firstRowY-buttonBufferY, 20, 1, this);
		connectionsDownButton = new Button(rightColX, firstRowY+buttonBufferY, 20, 0, this);

		violenceUpButton = new Button(rightColX, secondRowY-buttonBufferY, 20, 1, this);
		violenceDownButton = new Button(rightColX, secondRowY+buttonBufferY, 20, 0, this);
		
		resourceUpButton = new Button(rightColX, thirdRowY-buttonBufferY, 20, 1, this);
		resourceDownButton = new Button(rightColX, thirdRowY+buttonBufferY, 20, 0, this);
		
		stayCloseUpButton = new Button(leftColX, firstRowY-buttonBufferY, 20, 1, this);
		stayCloseDownButton = new Button(leftColX, firstRowY+buttonBufferY, 20, 0, this);
				
		meetingOthersUpButton = new Button(leftColX, secondRowY-buttonBufferY, 20, 1, this);
		meetingOthersDownButton = new Button(leftColX, secondRowY+buttonBufferY, 20, 0, this);
		
		rememberDeadUpButton = new Button(leftColX, thirdRowY-buttonBufferY, 20, 1, this);
		rememberDeadDownButton = new Button(leftColX, thirdRowY+buttonBufferY, 20, 0, this);

		pursuitLeftButton = new Button(buttonGoalLeft, buttonGoalHeight, 20, 3, this);
		pursuitRightButton = new Button(buttonGoalRight, buttonGoalHeight, 20, 4, this);

		startButton = new Button(fishTankPos.x, height*0.9f, 60, 2, this);
		
		proceedX = width*0.9f;
		proceedY = height*0.93f;
		proceedW = width*0.0725f;
		proceedH = height*0.04f;
		proceedSW = 1;
		
		// ------------------------------------------------------------ HISTORY
		portraitPos = new PVector(width*0.5f, height*0.85f);
		portraitRad = width*0.1f;
		portraitPulse = random(10);
		portraitPulseInc = 0.035f;
		portraitCol = color(20);
		
		endGame1_text = "";
		
		textAlpha = 0;
		
		
		
		randGen3 = true;		
		
		noiseVal = 0;
		
		loadingPos = 0;
		loadingLerpVal = 0.0f;
		loadingLerpInc = 0.0f;
		// --------------------------------------------------------------------------------------------------- 2
		powerForce = 5.0f;
		friendshipForce = 5.0f;
		loveForce = 50.0f;
		ageModifier = 0.25f;
		numberOfChildren = 3;
		independenceOfChildren = 1.0f;
		
		startButton2 = new Button(fishTankPos.x, height*0.9f, 60, 2, this);
		
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
		
		spatialCultureIncrement = 0.02f;
		
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
		
		bezierNum = (int)(fishTankSize/20);;
		startPointBezierFish = new PVector[bezierNum];
		anchorPointABezierFish = new PVector[bezierNum];
		anchorPointBBezierFish = new PVector[bezierNum];
		endPointBezierFish = new PVector[bezierNum];
		
		for(int i = 0; i < bezierNum; i++){
			//line(, (width/2+fishTankSize/2), (height/2-fishTankSize/2)+(i*20));
			startPointBezierFish[i] = new PVector((fishTankPos.x-fishTankSize*0.5f), (fishTankPos.y-fishTankSize*0.5f)+(i*20));
			anchorPointABezierFish[i] = new PVector((fishTankPos.x-fishTankSize*0.25f), (fishTankPos.y-fishTankSize*0.5f)+(i*20));
			anchorPointBBezierFish[i] = new PVector((fishTankPos.x+fishTankSize*0.25f), (fishTankPos.y-fishTankSize*0.5f)+(i*20));
			endPointBezierFish[i] = new PVector((fishTankPos.x+fishTankSize*0.5f), (fishTankPos.y-fishTankSize*0.5f)+(i*20));
		}
		
		
		 // --------------- END 2 TEXT
		endGame2_text = "";
		
		randomGen2 = true;
		
		// ------------------------------------------------------------------------------- 3
		nation = null;
		others = new ArrayList<Nation>();
		numberOfOthers = 5;
		
		timerWar = 20000.0f;
		startTimeWar = 0;
		
		OTHERS_POSITIONS = new ArrayList<PVector>();
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
		
		for(int i = 0; i < othersPositions.size(); i++){
			PVector pos = othersPositions.get(i);
			OTHERS_POSITIONS.add(pos);
		}
		
		randomStatement = 0;
		statementStr = "";
		statementHeader = "";
		statementH = 0;
		statementHMax = 100;
		statementHInc = 6.0f;
		statementRectAlpha = 0;
		statementTextAlpha = 0;
		statementAlphaInc = 7.0f;
		hoveredNation = null;
		
		alliances = new ArrayList<Alliance>();
		trades = new ArrayList<Trade>();
		wars = new ArrayList<War>();
		
		startButton3 = new Button(fishTankPos.x, height*0.9f, 60, 2, this);
		
		allianceModifier = 1.0f;
		tradeModifier = 1.0f;
		warModifier = 1.0f;
		nationGoal = 0;
		victoryBehaviour = 1.0f;
		tradeLimit = 1; //(use as constrain() per frame?)
		allianceTrust = 1.0f;
		cultureExternal = 1.0f;
		
		colorAlliances = color(80, 150, 80);
		colorWar = color(100, 0, 0);
		colorTrade = color(0, 100, 200);
		
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
		
		endWealthThreshold = 10000.0f;
		
		// -------------------------------------------------------------------------------------------------------- EMAIL
		emailScreen = false;
		sendButton = new Button(width*0.5f, height*0.3f, 40, 2, this);
		typedText = "";
		errorMessageMail = "";
		typedEmail = false;
		blinkingType = 0;
		// -------------------------------------------------------------------------------------------------------- FINAL
		finalAlphaFull = 0;
		finalAlphaSmall = 0;
		finalTimerFull = 0;
		finalTimerSmall = 0;
		
		//ac.start();
		
		  
	  alphaFade = 0;
	  loadingTextAlpha = 0;
	  loadingTextVal = -PI*0.5f;
	  loadingText = "";
	  fadeRate = 5.0f;
	  fading = false;
	  
	  rectFadeTopPos = new PVector(rectBorderX, rectBorderY);
	  rectFadeRightPos = new PVector(width-rectBorderX, rectBorderY);
	  rectFadeLeftPos = new PVector(rectBorderX, rectBorderY);
	  rectFadeBottomPos = new PVector(rectBorderX, rectBorderH+5);
	  
	  rectFadeAlpha = 255;
	  rectFadeAlphaInc = 0.45f;
	  rectFadeBufferMax = 20 * 4.0f; //hard coded because it's 20 in generateAgents()
	  rectFadeBuffer = 0; 
	  
	  canStart = false;
	  interactions = 0;
	  interactionsThreshold = 10;
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
		if(rectBorderHTemp > rectBorderH && !canReset) rectBorderHTemp -= 1.0f;
		if(interactionAlpha < 150 && !canReset) interactionAlpha += interactionAlphaInc;
		
		hasClicked = false;
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
			
			if(distance < 20 && dist(mouseX, mouseY, c.a1.pos.x, c.a1.pos.y) > c.a1.rad*1.1f && dist(mouseX, mouseY, c.a2.pos.x, c.a2.pos.y) > c.a2.rad*1.1f && mouseX < start.pos.x && mouseX > end.pos.x){
				fill(0, 75);
				c.canRemove = true;
			}else{
				c.canRemove = false;
			}
		}
		
		removeConnec = max(removeConnec, 0);
	}
	
	void update2(){
		if(rectBorderHTemp > rectBorderH && !canReset) rectBorderHTemp -= 1.0f;
		if(interactionAlpha < 150 && !canReset) interactionAlpha += interactionAlphaInc;
		hasClicked = false;
		for(int i = 0; i < community.size(); i++){
			//this is the update loop for the agents
			Agent a = community.get(i);
			if(a.isAlive){
				a.update();
				
				a.connectLove();
				a.exertLove();
				
				a.connectFriendship();
				a.exertFriendship();
				
				if(a.isSettled){
					a.connectPower();
					a.exertPower();
					//a.reproduce(); --this happens now on ConnectionLove
				}
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
			
			if(cL.a1.canReproduce){
				//println(cL.a1+" can reproduce "+cL.a1.canReproduce+" @"+frameCount);
				cL.a1.reproduce(cL.a2, cL);
			}
			
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
		
		//changing the position of the lines to reflect the spatial repartition of agents
		if(frameCount % 50 == 0) strataModulation();
	}

	public void draw() {
		mouse = new PVector(mouseX, mouseY);
		//scale(scaleW, scaleH);
		//translate(translateBufferX, translateBufferY);
		//frame.setSize(1440, 900);
		background(250);
		rhythm();
		selPulse = (cos(millis()*0.005f)+1)*50;
		textFont(mainFont);
		
		if(fading){
			
			if(rectBorderHTemp < rectBorderHMenu && alphaFade > 200) rectBorderHTemp += 0.75f;
			if(interactionAlpha > 0) interactionAlpha -= interactionAlphaInc*1.5f;
			
			if(!inGame1){
				alphaFade += fadeRate;
			}else{
				alphaFade += fadeRate*0.25f; //slower fade for the zooming in part
			}
			
			if(alphaFade > 255 && alphaFade < 1000){
					loadingTextVal = map(alphaFade, 255, 1000, -PI/2, 3*(PI/2));
					loadingLerpVal = map(alphaFade, 255, 1000, 0, 1);
					loadingPos = lerp(0, width, loadingLerpVal);
			}

			loadingTextAlpha = (sin(loadingTextVal)+1)*200.0f;
			
			if(alphaFade > 1000){
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
					if(seasons != null) eMasterSeasons.addSegment(0.00f, 2000.0f, new KillTrigger(gMasterSeasons)); //lower the sound of seasons then kill it (we recreate it at each stage)
					eMasterRhythm.addSegment(0.75f, 2000.0f);
				}else if(startGame1){
					generateAgents(numberOfAgents);
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
				}else if(emailScreen){
					fading = false;
					emailScreen = false;
					finalScreen = true;
					finalTimerSmall = millis() + 2000.0f;
					finalTimerFull = millis() + 5000.0f;
				}
			}
		}else{
			if(alphaFade > 0) alphaFade -= 30;
		}

		// ------------------------------------------
		if(splash){
			splash();
		}
		
		if(statement){
			artistStatement();
		}
		
		if(intro){
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
		
		if (endGame1) {
			endGame();
		}
		
		if(endGame2){
			endGame2();
		}
		
		if(endGame3){
			endGame3();
		}
		
		if(emailScreen){
			emailScreen();
		}
		
		if(finalScreen){
			finalScreen();
		}
		
		if(canReset){
			reset(resetStage);
		}
		
		rectMode(CORNER);

		/* ----- fade effect ----- */
		stroke(0);
		strokeWeight(1);
		fill(255, alphaFade);
		rect(rectBorderX+1, rectBorderY+1, rectBorderW-2, rectBorderHTemp-2);
		
		fill(0, loadingTextAlpha);
		textFont(loadingFont);
		textSize(loadingFontSize);
		textAlign(CENTER);
		text(loadingText, width*0.5f, height*0.4f);
		
		stroke(0, loadingTextAlpha*1.75f);
		line(rectBorderX, height*0.75f, loadingPos, height*0.75f);
		
		//mask
		noStroke();
		fill(255);
		rect(0, 0, width, rectBorderY);
		rect(0, 0, rectBorderX, height);
		rect(width, 0, -rectBorderX, height);
		
		debug();
	}
	
	public void emailScreen(){
		if(language == "english"){
			loadingText = "archiving";
		}else{
			loadingText = "archivage";
		}
		
		fill(0);
		textAlign(CENTER);
		textSize(mainFontSize);
		textFont(mainFont);
		text("Social Contact", width*0.5f, height*0.1f);
		
		textSize(headerFontSize);
		textFont(headerFont);
		if(language == "english"){
			text("personal archival", width*0.5f, height*0.15f);
		}else{
			text("archive personelle", width*0.5f, height*0.15f);
		}
		
		if(language == "english"){
			text("enter your email address to archive the story of your society.", width*0.5f, height*0.3f);
		}else{
			text("entrez votre addresse email pour archiver l'histoire de votre soci\u00E9t\u00E9.", width*0.5f, height*0.3f);
		}
		
		
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
		
		text(errorMessageMail, width*0.5f, height*0.6f);
		
		rectMode(CORNER);
		textAlign(LEFT);
		strokeWeight(1);
		stroke(10, 210);
		noFill();
		rect(proceedX, proceedY, proceedW, proceedH);
		textFont(textFont);
		textSize(textFontSize);
		fill(0);
		if(language == "english"){
			text("conclude", proceedX*1.015f, proceedY*1.03f);
		}else{
			text("conclure", proceedX*1.015f, proceedY*1.03f);
		}
		
		if(mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY + proceedH){
			proceedSW = 2;
		}else{
			proceedSW = 1;
		}
	}
	
	public void showAssumptions(){
		rectMode(CENTER);
		if(canShowAssumptions && !canReset){
			if(assumptionsRectAlpha < 200) assumptionsRectAlpha += assumptionsRectAlphaInc;
			if(assumptionsTextAlpha < 240 && assumptionsRectAlpha > 150) assumptionsTextAlpha += assumptionsTextAlphaInc;
		}else{
			if(assumptionsRectAlpha > 0 && assumptionsTextAlpha < 10) assumptionsRectAlpha -= assumptionsRectAlphaInc;
			if(assumptionsTextAlpha > 0) assumptionsTextAlpha -= assumptionsTextAlphaInc;
		}
		
		strokeWeight(1);
		fill(255, assumptionsRectAlpha);
		stroke(0, assumptionsRectAlpha);
		rect(assumptionsRectPos.x, assumptionsRectPos.y, assumptionsRectW, assumptionsRectH-2);
		
		textFont(thoughtFont);
		textSize(thoughtFontSize);
		textAlign(CENTER);
		fill(0, assumptionsTextAlpha);
		for(int i = 0; i < assumptions.length; i++){
			text(assumptions[i], assumptionsTextPos.x, height*(i+2f)*0.1f, assumptionsTextW, assumptionsTextH);
		}
	}
	
	public void strataModulation(){
		float inc = 0.12f;
		for(int i = 0; i < community.size(); i++){
			Agent a = community.get(i);
			if(a.pos.y < strata1Anchor1.y){ //----------------top
				if(a.pos.x < strata1Control1.x){ //left
					strata1Control1.y += inc;
				}else if(a.pos.x < strata1Control2.x){ //middle
					strata1Control1.y += inc/2;
					strata1Control2.y += inc/2;
				}else{ //right
					strata1Control2.y += inc;
				}
				
				//culture modification - all slots in culture are affected
				for(int j = 0; j < a.culture.length; j++){
					if(a.culture[j] < a.maxCulture) a.culture[j] += spatialCultureIncrement;	
				}
			}else if(a.pos.y < strata2Anchor1.y){//----------------middle
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
				
				for(int j = 0; j < a.culture.length; j++){
					if(a.culture[j] > 5) a.culture[j] -= spatialCultureIncrement;
					if(a.culture[j] < 5) a.culture[j] += spatialCultureIncrement;
				}	
			}else{//------------------------------------------------bottom
				if(a.pos.x < strata1Control1.x){ //left
					strata2Control1.y -= inc;
				}else if(a.pos.x < strata1Control2.x){ //middle
					strata2Control1.y -= inc;
					strata2Control2.y -= inc;
				}else{ //right
					strata2Control2.y -= inc;
				}
				
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
	
	public void reset(int stage){
		//fade white
		if(assumptionsRectAlpha > 0) assumptionsRectAlpha -= 2.0f;
		if(rectDeadAlpha <= 250) rectDeadAlpha += 2.0f;
		if(rectBorderHTemp < rectBorderHMenu && rectDeadAlpha > 100) rectBorderHTemp += 1.0f;
		if(interactionAlpha > 0) interactionAlpha -= 7.0f;
		if(rectDeadAlpha >= 250){
			if(stage == 1){
				inGame1 = false;
				startGame1 = true;
			}else if(stage == 2){
				inGame2 = false;
				community.clear();
				connectionsFriendship.clear();
				connectionsLove.clear();
				connectionsPower.clear();
				startGame2 = true;
			}else{
				inGame2 = false;
				others.clear();
				nation = null;
				startGame2 = true;
			}
			canReset = false;
		}
		fill(255, rectDeadAlpha);
		noStroke();
		rectMode(CORNER);
		rect(rectBorderX, rectBorderY, rectBorderW, rectBorderH);
	}
	
	public void showLegend(){
//		rectMode(CORNER);
//		noStroke();
//		
//		if(canShowLegend){
//			legendButtonText = "dismiss";
//
//			if(legendBackgroundAlpha < 255) legendBackgroundAlpha += legendBackgroundAlphaInc;
//			if(legendTextAlpha < 255 && legendBackgroundAlpha > 250) legendTextAlpha += legendTextAlphaInc;
//		}else{
//			legendButtonText = "about";
//			if(legendBackgroundAlpha > 0 && legendTextAlpha < 10) legendBackgroundAlpha -= legendBackgroundAlphaInc;
//			if(legendTextAlpha > 0) legendTextAlpha -= legendTextAlphaInc*2;
//		}
//		
//		strokeWeight(1);
//		fill(255, legendBackgroundAlpha);
//		rect(rectBorderX, rectBorderY, rectBorderW, rectBorderHMenu);
//	
//		fill(0, legendTextAlpha);
//		textAlign(CENTER);
//		text(legendTextContent, legendTextPos.x, legendTextPos.y);
//		legendW = textWidth(legendButtonText)*1.25f;
//		rectMode(CENTER);
//		stroke(0, showLegendAlpha+150);
//		strokeWeight(1);
//		fill(255, 200);
//		rect(showLegendPos.x, showLegendPos.y*0.995f, legendW, legendH);
//		fill(0, showLegendAlpha);
//		textFont(textFont);
//		textSize(textFontSize);
//		text(legendButtonText, showLegendPos.x, showLegendPos.y+5);
//		
//		if(mouseX > showLegendPos.x - legendW*0.5f && mouseX < showLegendPos.x + legendW*0.5f && mouseY > showLegendPos.y - legendH*0.5f && mouseY < showLegendPos.y + legendH*0.5f){
//			showLegendAlpha = 150;
//			showLegendSW = 2;
//		}else{
//			showLegendAlpha = 100;
//			showLegendSW = 1;
//		}
	}
	
	public void finalScreen(){
		background(255);
		
		fill(0);
		textAlign(CENTER);
		textFont(headerFont);
		textSize(headerFontSize);
		text("thank you", width*0.5f, height*0.5f);
		
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
		
		println(finalAlphaFull);
		
		if(finalAlphaFull > 1000){
			
			emailScreen = false;
			finalScreen = false;
			statement = true;
		}
	}
	
	public void loadText(String language){
		println("loading Strings with language: "+language);
		String lg = "en";
		if(language == "english"){
			lg = "en";
		}else{
			lg = "fr";
		}
		
		tsOld = loadStrings("data/text/"+lg+"/thought/inGame1/old.txt");
		tsKiller = loadStrings("data/text/"+lg+"/thought/inGame1/isKiller.txt");
		tsTamer = loadStrings("data/text/"+lg+"/thought/inGame1/isTamer.txt");
		tsCoToKill = loadStrings("data/text/"+lg+"/thought/inGame1/old.txt");
		tsCoToDead = loadStrings("data/text/"+lg+"/thought/inGame1/old.txt");
		tsAlone = loadStrings("data/text/"+lg+"/thought/inGame1/isAlone.txt");
		println(tsAlone);
		tsNotAlone = loadStrings("data/text/"+lg+"/thought/inGame1/notAlone.txt");
		tsHasProtected = loadStrings("data/text/"+lg+"/thought/inGame1/hasProtected.txt");
		tsHasRejected = loadStrings("data/text/"+lg+"/thought/inGame1/hasRejected.txt");
		tsHasBeenRejected = loadStrings("data/text/"+lg+"/thought/inGame1/hasBeenRejected.txt");
		tsWealthy = loadStrings("data/text/"+lg+"/thought/inGame1/wealthy.txt");
		tsLoneKiller = loadStrings("data/text/"+lg+"/thought/inGame1/loneKiller.txt");
		tsBeast = loadStrings("data/text/"+lg+"/thought/inGame1/beast.txt");
		
		goal0 = loadStrings("data/text/"+lg+"/endGame1/goal0.txt");
		goal1 = loadStrings("data/text/"+lg+"/endGame1/goal1.txt");
		goal2 = loadStrings("data/text/"+lg+"/endGame1/goal2.txt");
		goal3 = loadStrings("data/text/"+lg+"/endGame1/goal3.txt");
		
		meetingOthersEndString = loadStrings("data/text/"+lg+"/endGame1/meetingOthers.txt");
		resourcesString = loadStrings("data/text/"+lg+"/endGame1/resources.txt");
		formStabRel = loadStrings("data/text/"+lg+"/endGame1/formStabRel.txt");
		canPro = loadStrings("data/text/"+lg+"/endGame1/canPro.txt");
		canDist = loadStrings("data/text/"+lg+"/endGame1/canDist.txt");
		canRememberDead = loadStrings("data/text/"+lg+"/endGame1/canRememberDead.txt");
		
		canFight = loadStrings("data/text/"+lg+"/endGame1/canFight.txt");
		canConnec = loadStrings("data/text/"+lg+"/endGame1/canConnec.txt");
		
		totCo =  loadStrings("data/text/"+lg+"/endGame1/totCo.txt");
		connecMax  = loadStrings("data/text/"+lg+"/endGame1/connecMax.txt");
		isTamer  = loadStrings("data/text/"+lg+"/endGame1/isTamer.txt");
		coToDead = loadStrings("data/text/"+lg+"/endGame1/coToDead.txt");
		isKill = loadStrings("data/text/"+lg+"/endGame1/isKill.txt");
		coToKill = loadStrings("data/text/"+lg+"/endGame1/coToKill.txt");
		isHun = loadStrings("data/text/"+lg+"/endGame1/isHun.txt");
		timeAlone = loadStrings("data/text/"+lg+"/endGame1/timeAlone.txt");
		hasBeenRej = loadStrings("data/text/"+lg+"/endGame1/hasBeenRej.txt");
		hasRej = loadStrings("data/text/"+lg+"/endGame1/hasRej.txt");
		hasProt = loadStrings("data/text/"+lg+"/endGame1/hasProt.txt");
		isCoToHun = loadStrings("data/text/"+lg+"/endGame1/isCoToHun.txt");
		hasDist = loadStrings("data/text/"+lg+"/endGame1/hasDist.txt");
		hasFoughtOthers = loadStrings("data/text/"+lg+"/endGame1/hasFoughtOthers.txt");
		
		//opposites
		meetingOthersEndStringX = loadStrings("data/text/"+lg+"/endGame1/meetingOthers.txt");
		resourcesStringX = loadStrings("data/text/"+lg+"/endGame1/resources.txt");
		formStabRelX = loadStrings("data/text/"+lg+"/endGame1/formStabRelX.txt");
		
		canProX = loadStrings("data/text/"+lg+"/endGame1/canProX.txt");
		canDistX = loadStrings("data/text/"+lg+"/endGame1/canDistX.txt");
		canRememberDeadX = loadStrings("data/text/"+lg+"/endGame1/canRememberDeadX.txt");
		
		canFightX = loadStrings("data/text/"+lg+"/endGame1/canFightX.txt");
		canConnecX = loadStrings("data/text/"+lg+"/endGame1/canConnecX.txt");
		
		totCoX =  loadStrings("data/text/"+lg+"/endGame1/totCoX.txt");
		connecMaxX  = loadStrings("data/text/"+lg+"/endGame1/connecMaxX.txt");
		isTamerX  = loadStrings("data/text/"+lg+"/endGame1/isTamerX.txt");
		coToDeadX = loadStrings("data/text/"+lg+"/endGame1/coToDeadX.txt");
		isKillX = loadStrings("data/text/"+lg+"/endGame1/isKillX.txt");
		coToKillX = loadStrings("data/text/"+lg+"/endGame1/coToKillX.txt");
		isHunX = loadStrings("data/text/"+lg+"/endGame1/isHunX.txt");
		timeAloneX = loadStrings("data/text/"+lg+"/endGame1/timeAloneX.txt");
		hasBeenRejX = loadStrings("data/text/"+lg+"/endGame1/hasBeenRejX.txt");
		hasRejX = loadStrings("data/text/"+lg+"/endGame1/hasRejX.txt");
		hasProtX = loadStrings("data/text/"+lg+"/endGame1/hasProtX.txt");
		isCoToHunX = loadStrings("data/text/"+lg+"/endGame1/isCoToHunX.txt");
		hasDistX = loadStrings("data/text/"+lg+"/endGame1/hasDistX.txt");
		hasFoughtOthersX = loadStrings("data/text/"+lg+"/endGame1/hasFoughtOthersX.txt");
		
		//part 2
		tsExertPower = loadStrings("data/text/"+lg+"/thought/inGame2/exertPower.txt");
		tsFewFriends = loadStrings("data/text/"+lg+"/thought/inGame2/fewFriends.txt");
		tsFullFriends = loadStrings("data/text/"+lg+"/thought/inGame2/fullFriends.txt");
		tsHasChildren = loadStrings("data/text/"+lg+"/thought/inGame2/hasChildren.txt");
		tsInLove = loadStrings("data/text/"+lg+"/thought/inGame2/inLove.txt");
		tsIsOfAge = loadStrings("data/text/"+lg+"/thought/inGame2/isOfAge.txt");
		tsIsSettled = loadStrings("data/text/"+lg+"/thought/inGame2/isSettled.txt");
		tsIsSterile = loadStrings("data/text/"+lg+"/thought/inGame2/isSterile.txt");
		tsNoFriends = loadStrings("data/text/"+lg+"/thought/inGame2/noFriends.txt");
		tsNotSettled = loadStrings("data/text/"+lg+"/thought/inGame2/notSettled.txt");
		tsOppressed = loadStrings("data/text/"+lg+"/thought/inGame2/oppressed.txt");
		tsSpaceCulture = loadStrings("data/text/"+lg+"/thought/inGame2/spaceCulture.txt");
		
		spaceCultureText = loadStrings("data/text/"+lg+"/endGame2/definedBySpace.txt");
		socialCultureText = loadStrings("data/text/"+lg+"/endGame2/definedByOthers.txt");
		inLoveText = loadStrings("data/text/"+lg+"/endGame2/timeInLove.txt");
		friendships = loadStrings("data/text/"+lg+"/endGame2/friends.txt");
		hasForcedSimText = loadStrings("data/text/"+lg+"/endGame2/hasForcedSim.txt");
		hasForcedDiffText = loadStrings("data/text/"+lg+"/endGame2/hasForcedDiff.txt");
		hasRevoltedText = loadStrings("data/text/"+lg+"/endGame2/hasRevolted.txt");
		hasOppressedText = loadStrings("data/text/"+lg+"/endGame2/hasOppressed.txt");
		numberOfChildrenText = loadStrings("data/text/"+lg+"/endGame2/numberOfChildren.txt");
		oppressionInducedText = loadStrings("data/text/"+lg+"/endGame2/oppressionInduced.txt");
		oppressionReceivedText = loadStrings("data/text/"+lg+"/endGame2/oppressionReceived.txt");
		generationText = loadStrings("data/text/"+lg+"/endGame2/generation.txt");
		culturalHomogeneityText = loadStrings("data/text/"+lg+"/endGame2/culturalHomogeneity.txt");
		
		
		//part 3
		st_nationGoalHonor = loadStrings("data/text/"+lg+"/endGame3/nationGoalHonor.txt");
		st_nationGoalSurvival = loadStrings("data/text/"+lg+"/endGame3/nationGoalSurvival.txt");
		st_nationGoalWealth = loadStrings("data/text/"+lg+"/endGame3/nationGoalWealth.txt");
		st_wealth = loadStrings("data/text/"+lg+"/endGame3/wealth.txt");
		st_war = loadStrings("data/text/"+lg+"/endGame3/war.txt");
		st_tradePartners = loadStrings("data/text/"+lg+"/endGame3/tradePartners.txt");
		st_promiscuity = loadStrings("data/text/"+lg+"/endGame3/promiscuity.txt");
		st_alliances = loadStrings("data/text/"+lg+"/endGame3/alliances.txt");
		st_similarCultureAlone = loadStrings("data/text/"+lg+"/endGame3/similarCultureAlone.txt");
		st_similarCultureAllies = loadStrings("data/text/"+lg+"/endGame3/similarCultureAllies.txt");
		st_movement = loadStrings("data/text/"+lg+"/endGame3/movement.txt");
		
		tsAtPeace = loadStrings("data/text/"+lg+"/thought/inGame3/atPeace.txt");
		tsAtPeaceAgain = loadStrings("data/text/"+lg+"/thought/inGame3/atPeaceAgain.txt");
		tsAtWar = loadStrings("data/text/"+lg+"/thought/inGame3/atWar.txt");
		tsAverageCulture = loadStrings("data/text/"+lg+"/thought/inGame3/averageCulture.txt");
		tsNotAverageCulture = loadStrings("data/text/"+lg+"/thought/inGame3/notAverageCulture.txt");
		tsHasAllies = loadStrings("data/text/"+lg+"/thought/inGame3/hasAllies.txt");
		tsHasNeighbors = loadStrings("data/text/"+lg+"/thought/inGame3/hasNeighbors.txt");
		tsHasTradePartners = loadStrings("data/text/"+lg+"/thought/inGame3/hasTrade.txt");
		tsNoTradePartners = loadStrings("data/text/"+lg+"/thought/inGame3/noTrade.txt");
		tsNationDisappeared = loadStrings("data/text/"+lg+"/thought/inGame3/nationDisappeared.txt");
		tsNoAllies = loadStrings("data/text/"+lg+"/thought/inGame3/noAllies.txt");
		tsNoNeighbors = loadStrings("data/text/"+lg+"/thought/inGame3/noNeighbors.txt");
		tsPoor = loadStrings("data/text/"+lg+"/thought/inGame3/poor.txt");
		tsWealthyNation = loadStrings("data/text/"+lg+"/thought/inGame3/wealthy.txt");
		tsWasAtWar = loadStrings("data/text/"+lg+"/thought/inGame3/wasAtWar.txt");
		
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
		      message.setFrom(new InternetAddress("representative@socialcontact.cc", "social contact"));
		      message.setHeader("Content-Type", "text/plain; charset=UTF-8");
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
		if(initiate){
			if(beginAlpha > 0) beginAlpha -= 10.0f;
			noCursor();
			
			textAlign(CENTER);
			textSize(headerFontSize);
			textFont(headerFont);
			
			fill(50, textIntroAlpha);

			text(textIntro, width*0.5f, height*0.334f);
			fill(50, politicsAlpha);
			text("politics is", width*0.5f, height*0.25f);
			
			if(textIntroAlpha < 220){
				textIntroAlpha += 0.65f;
				if(politicsAlpha < 150) politicsAlpha += 1.35f;
			}else{
				introStage++;
				textIntroAlpha = 0;
			}
			
			strokeWeight(1);
			if(introStage == 0){
				if(noise(noiseArtVal) < noiseArtThreshold){
					textIntro = "the act of coming together.";
				}else{
					textIntro = "the art of coming together.";
				}
				
				if(introPosA.x < width*0.45f && introPosB.x > width*0.55f){
					introPosA.x += 1.5f;
					introPosB.x -= 1.5f;
					introPosC.y -= 0.75f;
				}else{
					strokeCap(ROUND);
					stroke(50, lineIntroAlpha);
					line(introPosA.x+rad/2, introPosA.y, introPosB.x-rad/2, introPosB.y);
					line(introPosA.x, introPosA.y+rad/2, introPosC.x, introPosC.y-rad/2);
					line(introPosB.x, introPosB.y+rad/2, introPosC.x, introPosC.y-rad/2);
					if(lineIntroAlpha < 200) lineIntroAlpha += 0.85f;
				}
			}
			
			noiseArtVal += noiseArtStep;
			
			if(introStage == 1){
				if(noise(noiseArtVal) < noiseArtThreshold){
					textIntro = "the act of living with one another.";
				}else{
					textIntro = "the art of living with one another.";
				}
				
				if(introPosA.x < width*0.47f) introPosA.x += 0.3f;
				if(introPosA.y < height*0.53f) introPosA.y += 0.3f;
				
				if(introPosC.x > width*0.48f) introPosC.x -= 0.3f;
				if(introPosC.y > height*0.58f) introPosC.y -= 0.65f;
				

				if(introPosB.x < width*0.57f) introPosB.x += 0.3f;
				if(introPosB.y > height*0.43f) introPosB.y -= 0.6f;
				
				if(linesIntroAlpha < 100) linesIntroAlpha += 0.65f;
			}
			
			if(introStage == 2){
				if(noise(noiseArtVal) < noiseArtThreshold){
					textIntro = "the act of acknowledging the presence of others.";
				}else{
					textIntro = "the art of acknowledging the presence of others.";
				}
				
				stroke(0, othersIntroAlpha);
				strokeWeight(1);
				noFill();
				rad = 20;
				for(int i = 0; i < introPosO.length; i++){
					ellipse(introPosO[i].x, introPosO[i].y, rad, rad);
					ellipse(introPosO[i].x, introPosO[i].y, rad*0.66f, rad*0.66f);
					ellipse(introPosO[i].x, introPosO[i].y, rad*0.33f, rad*0.33f);
					ellipse(introPosO[i].x, introPosO[i].y, 1, 1);
					//make them lerp towards the middle of the triangle? who really cares at that point?
					introPosO[i].x = lerp(introPosO[i].x, (introPosA.x+introPosB.x+introPosC.x)/3, lerpIntro);
					introPosO[i].y = lerp(introPosO[i].y, (introPosA.y+introPosB.y+introPosC.y)/3, lerpIntro);
					lerpIntro += 0.0000001f;
				}
				
				if(othersIntroAlpha < 40 && rectIntroAlpha < 20) othersIntroAlpha += 0.2f;
			}
			
			if(introStage < 3){
				
				stroke(50, circleIntroAlpha);
				fill(252, 255);
				
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
				strokeWeight(1);
				strokeCap(PApplet.ROUND);
				stroke(0, 150, 0, linesIntroAlpha);
				for(int i = -3; i < 6; i++){
					line(introPosB.x-i*2, introPosB.y, introPosC.x+i*2, introPosC.y);
				}
				
				
				//power line
				strokeWeight(2);
				strokeCap(PApplet.ROUND);
				stroke(0, linesIntroAlpha);
				line(introPosB.x, introPosB.y, introPosA.x, introPosA.y);
				
				
				
				stroke(50, circleIntroAlpha);
				//fill(252, 255);
				strokeWeight(1);
				noFill(); 
				
				ellipse(introPosA.x, introPosA.y, rad, rad);
				ellipse(introPosA.x, introPosA.y, rad*0.5f, rad*0.5f);
				ellipse(introPosA.x, introPosA.y, rad*0.3f, rad*0.3f);
				ellipse(introPosA.x, introPosA.y, rad*0.1f, rad*0.1f);
				ellipse(introPosB.x, introPosB.y, rad, rad);
				ellipse(introPosB.x, introPosB.y, rad*0.7f, rad*0.7f);
				ellipse(introPosB.x, introPosB.y, rad*0.5f, rad*0.5f);
				ellipse(introPosB.x, introPosB.y, rad*0.3f, rad*0.3f);
				ellipse(introPosB.x, introPosB.y, rad*0.1f, rad*0.1f);
				ellipse(introPosC.x, introPosC.y, rad, rad);
				ellipse(introPosC.x, introPosC.y, rad*0.3f, rad*0.3f);
				ellipse(introPosC.x, introPosC.y, rad*0.1f, rad*0.1f);
			}

			
			if(othersIntroAlpha > 40 || rectIntroAlpha > 3.0f){
				textIntroAlpha -= 4.0f;
				politicsAlpha -= 4.0f;
				circleIntroAlpha -= 4.0f;
				linesIntroAlpha -= 4.0f;
				fill(255, rectIntroAlpha);
				rect(3, 3, width-3, height-3);
				rectIntroAlpha += 0.5f;
				if(othersIntroAlpha > 0) othersIntroAlpha -= 0.5f;
				//lerpIntro = lerpIntro *0.9f;
			}
			
			if(rectIntroAlpha > 65){
				//introStage++;
				textSize(mainFontSize);
				textFont(mainFont);
				fill(60, titleIntroAlpha);
				text("Social Contact", width*0.5f, height*0.1f);
				textSize(headerFontSize);
				textFont(headerFont);
				if(titleIntroAlpha < 290) text("an interactive work of political philosophy", width/2, height*0.25f);
				textSize(textFontSize);
				textFont(textFont);
				if(titleIntroAlpha < 250) text("developed by Pierre Depaz", width/2, height*0.95f);
				titleIntroAlpha += 2.0f;
			}
			
			if(titleIntroAlpha > 350){
				intro = false;
				startGame1 = true;
			}
		}
	}
	
	void splash(){
		textAlign(CENTER);
		rectMode(CENTER);
		textSize(mainFontSize);
		textFont(mainFont);
		fill(100, beginAlpha);
		text("Social Contact", width*0.5f, height*0.1f);
		textSize(textFontSize);
		textFont(textFont);
		
		for(Fish f : splashFish){
			f.update();
			f.collide();
			f.boundaries();
			f.drawConnections();
			f.display();
		}
		
		for(int i = 0; i < splashLerpVal.length; i++){
				if(i % 2 == 0){
					stroke(colorFriendship, 50);
				}else{
					stroke(colorLove, 50);
				}
				
				if(splashLerpVal[i] < 1.0f){
					splashTempPos[i] = PVector.lerp(splashStartPos[i], splashEndPos[i], splashLerpVal[i]);
				}else if(splashLerpVal[i] < 1.95f){
					splashStartPos[i] = PVector.lerp(splashStartPos[i], splashTempPos[i], splashLerpVal[i]-1);
				}else{
					splashStartPos[i] = splashEndPos[i];
					splashEndPos[i] = splashFish[(int)random(splashFish.length)].pos;
					splashLerpVal[i] = 0;
				}
				splashLerpVal[i] += splashLerpInc[i];
				
				//line(splashStartPos[i].x, splashStartPos[i].y, splashTempPos[i].x, splashTempPos[i].y);
		}

	}
	
	void artistStatement(){
		textAlign(CENTER);
		rectMode(CENTER);
		textSize(mainFontSize);
		textFont(mainFont);
		fill(100, beginAlpha);
		text("Social Contact", width*0.5f, height*0.1f);
		textSize(textFontSize);
		textFont(textFont);

		if(language == "english"){
			fill(100, statementAlpha);
			for(int i = 0; i < currentStatement.length; i++){
				text(currentStatement[i], width*0.5f, height*0.4f+(i*height*0.05f), width*0.45f, height*0.3f);
			}
			fill(100, beginAlpha);
			text("press space to begin", width*0.5f, height*0.95f);
		}else{
			fill(100, statementAlpha);
			for(int i = 0; i < currentStatementFR.length; i++){
				text(currentStatementFR[i], width*0.5f, height*0.4f+(i*height*0.05f), width*0.45f, height*0.3f);
			}
			fill(100, beginAlpha);
			text("espace pour commencer", width*0.5f, height*0.95f);
		}
		
		if(!initiate){
			statementAlpha = (sin(map(statementLerpVal, 0, 1, PI, TWO_PI))*255)+255;
		}else{
			statementAlpha -= 4.0f;
			beginAlpha -= 4.0f;
			if(statementAlpha < -5){
				statement = false;
				intro = true;
			}
		}
		statementRectPos = PVector.lerp(statementRectPos, statementTargetPos, statementLerpVal);
		if(statementLerpVal < 1) statementLerpVal += statementLerpInc;
		
		if(statementAlpha < 10){
			if(statementPage == 0){
				currentStatement = statementText0;
				currentStatementFR = statementText0FR;
			}else if(statementPage == 1){
				currentStatement = statementText1;
				currentStatementFR = statementText1FR;
			}else{
				currentStatement = statementText2;
				currentStatementFR = statementText2FR;
			}
		}
		
		statementPage0.display();
		statementPage1.display();
		statementPage2.display();
		
		statementPage0.alpha = beginAlpha;
		statementPage1.alpha = beginAlpha;
		statementPage2.alpha = beginAlpha;
		
		statementPage0.onClick();
		statementPage1.onClick();
		statementPage2.onClick();
		
		englishButton.display();
		frenchButton.display();
		
		englishButton.onClick();
		frenchButton.onClick();
		
		stroke(70, beginAlpha);
		rect(statementRectPos.x, statementRectPos.y, statementRectSize*1.25f, statementRectSize*1.25f);
	}
	
	void fishTank(){
		noFill();
		strokeWeight(1);
		stroke(0, fishTankAlpha);
		rect(fishTankPos.x, fishTankPos.y, fishTankSize+fishTankExpand, fishTankSize+fishTankExpand);
		stroke(0);
		strokeWeight(1);
		fill(255, 150);
		rect(fishTankPos.x, fishTankPos.y, fishTankSize, fishTankSize);
		
		fishTankAlpha -= 25.0f;
		fishTankExpand += 2.0f;
		
		if(startGame1){
			predatorButton.display();
			resourceButton.display();
			connectionsButton.display();
			for(int i = 0; i < fishes.size(); i++){
				Fish f = fishes.get(i);
				f.update();
				f.display();
				f.boundaries();
				f.collide();
				f.drawConnections();
				if(drawFishConnec){
					f.alpha = 200;
				}else{
					f.alpha = 10;
				}
			}
			
			fishPred.predatorDisplay();
			fishPred.update();
			
			if(drawFishPredators){
				fishPred.alphaPred = 250;
			}else{
				fishPred.alphaPred = 30;
			}
			
			if(drawFishResources){
				if(resourceSeek == 0.5f){
					stroke(0, 100, 0, 50);
					fill(colorResources, 20);
				}else if(resourceSeek == 1.0f){
					stroke(0, 100, 0, 100);
					fill(colorResources, 50);
				}else{
					stroke(0, 100, 0, 200);
					fill(colorResources, 150);
				}
			}
			
			int angleIncrement = (int)(360/fishResourceSides);
			strokeWeight(1);

			beginShape();
			int index = 0;
			for(float angle = 0; angle < 360; angle += angleIncrement){
				float x = fishResourcePos.x + PApplet.sin(PApplet.radians(angle))*(fishResourceRad+fishResourceSidesVar[index]);
				float y = fishResourcePos.y + PApplet.cos(PApplet.radians(angle))*(fishResourceRad+fishResourceSidesVar[index]);
				vertex(x, y);
				if(index < fishResourceSidesVar.length-1) index++;
			}
			endShape(PApplet.CLOSE);
			
			if(dist(mouseX, mouseY, fishResourcePos.x, fishResourcePos.y) < fishResourceRad*4.0f){
				textFont(PolSys.thoughtFont);
				textSize(PolSys.thoughtFontSize);
				fill(0, 200);
				if(language == "english"){
					text("a resource", fishResourcePos.x + rad*0.75f, fishResourcePos.y - rad*0.75f);
				}else{
					text("une ressource", fishResourcePos.x + rad*0.75f, fishResourcePos.y - rad*0.75f);
				}
			}
			
			if(dist(mouseX, mouseY, deadFishPos.x, deadFishPos.y) < 10){
				textFont(PolSys.thoughtFont);
				textSize(PolSys.thoughtFontSize);
				fill(0, 200);
				if(language == "english"){
					text("a grave", deadFishPos.x + 7.5f, deadFishPos.y - 7.5f);
				}else{
					text("une tombe", deadFishPos.x + 7.5f, deadFishPos.y - 7.5f);
				}
			}
			
			textFont(textFont);
			textSize(textFontSize);
			
			
			if(rememberDead == 1.0f){ //connected to one dead
				stroke(0, 200);
				noFill();
				line(deadFishPos.x-3, deadFishPos.y, deadFishPos.x+3, deadFishPos.y);
				line(deadFishPos.x, deadFishPos.y-4, deadFishPos.x, deadFishPos.y+8);
				stroke(0, 50);
				line(deadFishPos.x, deadFishPos.y, fishes.get(0).pos.x, fishes.get(0).pos.y);
			}else if(rememberDead == 1.5f){ //connected to both dead
				stroke(0, 150);
				noFill();
				line(deadFishPos.x-4, deadFishPos.y, deadFishPos.x+4, deadFishPos.y);
				line(deadFishPos.x, deadFishPos.y-4, deadFishPos.x, deadFishPos.y+8);
				line(deadFishPos2.x-4, deadFishPos2.y, deadFishPos2.x+4, deadFishPos2.y);
				line(deadFishPos2.x, deadFishPos2.y-4, deadFishPos2.x, deadFishPos2.y+8);
				stroke(0, 50);
				line(deadFishPos2.x, deadFishPos2.y, fishes.get(0).pos.x, fishes.get(0).pos.y);
				line(deadFishPos.x, deadFishPos.y, fishes.get(0).pos.x, fishes.get(0).pos.y);
			}
			
			if(meetingOthers == 1.0f){
				//TODO how to represent meeting others? just a hull
			}
			
			if(formingStableRelationships == 0.5f){
				for(int i = 0; i < fishes.size(); i++){
					Fish f = fishes.get(i);
					f.maxSpeed = 0.5f;
					f.mov = new PVector(random(-1f, 1f), random(-1f, 1f));
				}
			}else if(formingStableRelationships == 1.0f){
				for(int i = 0; i < fishes.size(); i++){
					Fish f = fishes.get(i);
					f.maxSpeed = 1.0f;
					f.mov = new PVector(random(-2f, 2f), random(-2f, 2f));
				}
			}else{
				for(int i = 0; i < fishes.size(); i++){
					Fish f = fishes.get(i);
					f.maxSpeed = 3.0f;
					f.mov = new PVector(random(-4f, 4f), random(-4f, 4f));
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
			friendshipButton.display();
			loveButton.display();
			powerButton.display();
			for(int i = 0; i < fishes.size(); i++){
				Fish f = fishes.get(i);
				f.maxSpeed = 1.0f;
				f.update();
				f.display();
				f.boundaries();
				f.collide();
			}
			
			if(powerForceUpButton.onClick() || powerForceDownButton.onClick()){
				drawFishPower = true;
			}
			
			if(friendshipForceUpButton.onClick() || friendshipForceDownButton.onClick()){
				drawFishFriend = true;
			}
			
			if(loveForceUpButton.onClick() || loveForceDownButton.onClick()){
				drawFishLove = true;
			}
			
			if(cultureButtonRight.onClick() || cultureButtonLeft.onClick()){
				drawCulture = true;
			}
			
			if(numberOfChildrenUpButton.onClick() || numberOfChildrenUpButton.onClick() || independenceOfChildrenDownButton.onClick() || independenceOfChildrenUpButton.onClick() || ageMajorityDownButton.onClick() || ageMajorityUpButton.onClick()){
				drawChildren = true;
			}
			
			if(drawFishPower){
				int fp = (int)PApplet.map(powerForce, 1.0f, 7.0f, 1.0f, 5.0f);
				for(int i = 0; i < fishes.size(); i++){
					Fish f = fishes.get(i);
					f.powerSW = (int) map(powerForce, 1.0f, 7.0f, 2.0f, 4.0f);
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
				for(int i = 0; i < startPointBezierFish.length; i++){
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
					fill(0, 10);
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
					
					anchorPointABezierFish[i].y = constrain(anchorPointABezierFish[i].y, fishTankPos.y-fishTankSize*0.5f, fishTankPos.y+fishTankSize*0.5f);
					anchorPointBBezierFish[i].y = constrain(anchorPointABezierFish[i].y, fishTankPos.y-fishTankSize*0.5f, fishTankPos.y+fishTankSize*0.5f);
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
					fishes.add(new Fish(random(fishTankPos.x*0.9f, fishTankPos.x*1.1f), random(fishTankPos.y*0.9f, fishTankPos.y*1.1f), 0, 10, 150, this));
				}
			}
		}
		
		if(startGame3){
			alliancesButton.display();
			warButton.display();
			tradeButton.display();
			stroke(0, 50);
			line(fishTankPos.x-fishTankSize*0.5f, fishTankPos.y+fishTankSize*0.5f, fishTankPos.x+fishTankSize*0.5f, fishTankPos.y-fishTankSize*0.5f);
			for(int i = 0; i < citizenFishNum; i++){
				stroke(0, fishHullCol1*0.5f, fishHullCol1, 200);
				noFill();
				strokeWeight(citizenFishWeight[i]);
				ellipse(citizenFishPos[i].x, citizenFishPos[i].y, citizenFishRad[i], citizenFishRad[i]);
				if(citizenFishPos[i].x > fishTankPos.x) citizenFishPos[i].x += (cos(millis()*0.00001f))*0.01f;
				if(citizenFishPos[i].x < fishTankPos.x) citizenFishPos[i].x -= (cos(millis()*0.00001f))*0.01f;
				if(citizenFishPos[i].y < fishTankPos.y) citizenFishPos[i].y += (cos(millis()*0.00001f))*0.01f;
				if(citizenFishPos[i].y > fishTankPos.y) citizenFishPos[i].y -= (cos(millis()*0.00001f))*0.01f;
				citizenFishPos[i].x = constrain(citizenFishPos[i].x, fishTankPos.x - fishTankSize*0.5f,  fishTankPos.x + fishTankSize*0.5f);
				citizenFishPos[i].y = constrain(citizenFishPos[i].y, fishTankPos.y - fishTankSize*0.5f,  fishTankPos.y + fishTankSize*0.5f);
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
				if(citizenFishPos2[i].x > fishTankPos.x) citizenFishPos2[i].x += (cos(millis()*0.00001f))*0.01f;
				if(citizenFishPos2[i].x < fishTankPos.x) citizenFishPos2[i].x -= (cos(millis()*0.00001f))*0.01f;
				if(citizenFishPos2[i].y < fishTankPos.y) citizenFishPos2[i].y += (cos(millis()*0.00001f))*0.01f;
				if(citizenFishPos2[i].y > fishTankPos.y) citizenFishPos2[i].y -= (cos(millis()*0.00001f))*0.01f;
				citizenFishPos2[i].x = constrain(citizenFishPos2[i].x, fishTankPos.x - fishTankSize*0.5f,  fishTankPos.x + fishTankSize*0.5f);
				citizenFishPos2[i].y = constrain(citizenFishPos2[i].y, fishTankPos.y - fishTankSize*0.5f,  fishTankPos.y + fishTankSize*0.5f);
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
				strokeWeight(1);
				rectMode(PApplet.CENTER);
				for(int i = 0; i < fishVesselsNum; i++){
					fishVesselsPos[i] = PVector.lerp(new PVector(fishTankPos.x*0.95f, fishTankPos.y*0.85f), new PVector(fishTankPos.x*1.05f, fishTankPos.y*1.15f), fishVesselsLerp[i]);
					PVector dir = PVector.sub(new PVector(fishTankPos.x*0.95f, fishTankPos.y*0.85f), new PVector(fishTankPos.x*1.05f, fishTankPos.y*1.15f));
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
				for(int i = 0; i < 8*warModifier; i++){
					strokeWeight(2);
					stroke(255-random(100), 0, 0, 150*warModifier);
					line(fishTankPos.x*0.95f, fishTankPos.y*0.85f, fishTankPos.x+random(-1, 1), fishTankPos.y+random(-35, 35));
					line(fishTankPos.x*1.05f, fishTankPos.y*1.15f, fishTankPos.x+random(-1, 1), fishTankPos.y+random(-35, 35));
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
					if(citizenFishPos2[i].x > fishTankPos.x*0.9f && citizenFishPos2[i].x < fishTankPos.x){
						citizenFishPos2[i].x += 0.05f;
					}
					if(citizenFishPos2[i].y > fishTankPos.y*0.9f && citizenFishPos2[i].y < fishTankPos.y){
						citizenFishPos2[i].y += 0.05f;
					}
				}
				
				for(int i = 0; i < citizenFishNum; i++){
					if(citizenFishPos[i].x > fishTankPos.x*1.05f && citizenFishPos[i].x < fishTankPos.x*1.1f){
						citizenFishPos[i].x -= 0.05f;
					}
					if(citizenFishPos[i].y > fishTankPos.y*1.05f && citizenFishPos[i].y < fishTankPos.y*1.1f){
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
		intro = false;
		seasons = null;
		if(!fading) cursor(CROSS);
		rectMode(CORNER);
		rectCol = 245+(noise(noiseVal))*10; 
		fill(rectCol);
		noiseVal+= 0.4f;
		strokeWeight(1);
		stroke(0);
		
		if(interactions > interactionsThreshold) canStart = true;
		
		rectBorderHTemp = rectBorderHMenu;
		
		if(language == "english"){
			loadingText = "computing your world";
			legendTextContent = legendTextContent1;
		}else{
			loadingText = "calcul de votre monde";
			legendTextContent = legendTextContent1FR;
		}
		
		
		fill(0);
		textAlign(CENTER);
		textSize(mainFontSize);
		textFont(mainFont);
		text("Social Contact", width*0.5f, height*0.1f);

		textSize(subtitleFontSize);
		textFont(subtitleFont);
		
		if(language == "english"){
			fill(10);
			text("I - coming together", width/2, subtitleHeight);
			fill(200);
			text("II - growing together", width/2, subtitle2Height);
			text("III - confronting others", width/2, subtitle3Height);
			fill(0);
		}else{
			fill(10);
			text("I - r\u00E9union", width/2, subtitleHeight);
			fill(200);
			text("II - communion", width/2, subtitle2Height);
			text("III - confrontation", width/2, subtitle3Height);
			fill(0);
		}
		
		textSize(textFontSize);
		textFont(textFont);
		stayCloseUpButton.display();
		stayCloseDownButton.display();
		meetingOthersUpButton.display();
		meetingOthersDownButton.display();
		rememberDeadUpButton.display();
		rememberDeadDownButton.display();
		
		//textAlign(LEFT);
		fill(0);
		
		if(language == "english"){
			if(formingStableRelationships == 0.5f){
				formingStableRelationshipsString ="move freely";
			}else if(formingStableRelationships == 1.0f){
				formingStableRelationshipsString = "try to gather";
			}else{
				formingStableRelationshipsString = "stick with others";
			}
			text("they should " + formingStableRelationshipsString, leftColX, firstRowY);
			assumptions[0] = "they should " + formingStableRelationshipsString;
		}else{
			if(formingStableRelationships == 0.5f){
				formingStableRelationshipsString ="se d\u00E9placer librement";
			}else if(formingStableRelationships == 1.0f){
				formingStableRelationshipsString = "tenter de se retrouver";
			}else{
				formingStableRelationshipsString = "rester group\u00E9s";
			}
			text("ils doivent " + formingStableRelationshipsString, leftColX, firstRowY);
			assumptions[0] = "ils doivent " + formingStableRelationshipsString;
		}
		
		if(language == "english"){
			if(meetingOthers == 0.5f){
				meetingOthersString = "always be welcomed";
			}else if(meetingOthers == 1.0f){
				meetingOthersString = "be given the benefit of the doubt";
			}else{
				meetingOthersString = "never be trusted";
			}
			text("newcomers should\n" + meetingOthersString, leftColX, secondRowY);
			assumptions[1] = "newcomers should " + meetingOthersString;
		}else{
			if(meetingOthers == 0.5f){
				meetingOthersString = "sont toujours accueillis";
			}else if(meetingOthers == 1.0f){
				meetingOthersString = "sont pr\u00E9sum\u00E9s innocents";
			}else{
				meetingOthersString = "ne m\u00E9ritent pas notre confiance";
			}
			text("les nouveaux-venus\n" + meetingOthersString, leftColX, secondRowY);
			assumptions[1] = "les nouveaux-venus " + meetingOthersString;
		}
		
		if(language == "english"){
			if(rememberDead == 0.5f){
				rememberDeadString = "forgotten";
			}else if(rememberDead == 1.0f){
				rememberDeadString = "remembered";
			}else{
				rememberDeadString = "cherished";
			}
			text("the dead should be\n" + rememberDeadString, leftColX, thirdRowY);
			assumptions[2] = "the dead should be " + rememberDeadString;
		}else{
			if(rememberDead == 0.5f){
				rememberDeadString = "etre oubli\u00E9s";
			}else if(rememberDead == 1.0f){
				rememberDeadString = "etre honor\u00E9s";
			}else{
				rememberDeadString = "etre ch\u00E9ris";
			}
			text("les morts doivent\n" + rememberDeadString, leftColX, thirdRowY);
			assumptions[2] = "les morts doivent " + rememberDeadString;
		}

		
		textSize(textFontSize);
		textFont(textFont);
		//textAlign(RIGHT);
		connectionsUpButton.display();
		String connecString = "";
		
		if(language == "english"){
			if(connec == 2){
				connecString = "two";
			}else if(connec == 3){
				connecString = "three";
			}else if(connec == 4){
				connecString = "four";
			}else if(connec == 5){
				connecString = "five";
			}else if(connec == 6){
				connecString = "six";
			}else{
				connecString = "seven";
			}
			text("they can connect to " + connecString + " others", rightColX, firstRowY);
			assumptions[3] = "they can connect to " + connecString + " others";
		}else{
			if(connec == 2){
				connecString = "deux";
			}else if(connec == 3){
				connecString = "trois";
			}else if(connec == 4){
				connecString = "quatre";
			}else if(connec == 5){
				connecString = "cinq";
			}else if(connec == 6){
				connecString = "six";
			}else{
				connecString = "sept";
			}
			text("ils peuvent former " + connecString + " connexions", rightColX, firstRowY);
			assumptions[3] = "ils peuvent former " + connecString + " connexions";
		}

		connectionsDownButton.display();

		if(language == "english"){
			if (violence == 0.5f) {
				violenceText = "a constant possibility";
			} else if (violence == 1.0f) {
				violenceText = "inevitable";
			} else if (violence == 0.0f) {
				violenceText = "never a solution";
			}
			text("violence is " + violenceText, rightColX, secondRowY);
			assumptions[4] = "violence is " + violenceText;
		}else{
			if (violence == 0.5f) {
				violenceText = "une constante possibilit\u00E9";
			} else if (violence == 1.0f) {
				violenceText = "in\u00E9vitable";
			} else if (violence == 0.0f) {
				violenceText = "une fausse solution";
			}
			text("le recours \u00E0 la violence est\n" + violenceText, rightColX, secondRowY);
			assumptions[4] = "le recours \u00E0 la violence est " + violenceText;
		}

		violenceUpButton.display();
		violenceDownButton.display();		
		
		if(language == "english"){
			if(resourceSeek == 0.5f){
				resourceText = "there is no need for accumulation";
			}else if(resourceSeek == 1.0f){
				resourceText = "they should look for resources";
			}else{
				resourceText = "they should compete for resources";
			}
		}else{
			if(resourceSeek == 0.5f){
				resourceText = "il y a nul besoin d'accumuler des ressources";
			}else if(resourceSeek == 1.0f){
				resourceText = "ils doivent consommer leurs ressources";
			}else{
				resourceText = "ils doivent conserver leurs ressources";
			}
		}


		resourceUpButton.display();
		text(resourceText, rightColX,thirdRowY);
		assumptions[5] = resourceText;
		resourceDownButton.display();

		
		if(language == "english"){
			if (goalInt == 0) {
				goalText = "life is\nfree of death";
				storyTitle1 = "bliss";
			} else if (goalInt == 1) {
				goalText = "life is\n both pain and bliss";
				storyTitle1 = "reality";
			} else if (goalInt == 2) {
				goalText = "life is\na voluntary struggle";
				storyTitle1 = "struggle";
			} else if (goalInt == 3) {
				goalText = "life is\nriddled with death";
				storyTitle1 = "hardship";
			} else if (goalInt > 3) {
				goalInt = 0;
			}else if (goalInt < 0){
				goalInt = 3;
			}
		}else{
			if (goalInt == 0) {
				goalText = "la vie\nn'est pas la mort";
				storyTitle1 = "le bonheurs";
			} else if (goalInt == 1) {
				goalText = "la vie\nest \u00E9gal plaisir et douleur";
				storyTitle1 = "la r\u00E9alit\u00E9";
			} else if (goalInt == 2) {
				goalText = "la vie\nest une souffrance volontaire";
				storyTitle1 = "les difficult\u00E9s";
			} else if (goalInt == 3) {
				goalText = "la vie\nd\u00E9pend de la mort";
				storyTitle1 = "la perte";
			} else if (goalInt > 3) {
				goalInt = 0;
			}else if (goalInt < 0){
				goalInt = 3;
			}
		}

		
		pursuitLeftButton.display();
		pursuitRightButton.display();
		
		textSize(textFontSize);
		textFont(textFont);
		textAlign(CENTER);
		text(goalText, fishTankPos.x, buttonGoalHeight-bufferGoalHeight);
		assumptions[6] = goalText;
		
		fishTank();
		
		showAlpha = abs(cos(millis()*0.002f)*150)+100;

		startButton.display();
		startButton.onClick();

		stayCloseUpButton.onClick();
		stayCloseDownButton.onClick();
		meetingOthersUpButton.onClick();
		meetingOthersDownButton.onClick();
		rememberDeadUpButton.onClick();
		rememberDeadDownButton.onClick();
		connectionsUpButton.onClick();
		connectionsDownButton.onClick();

		violenceUpButton.onClick();
		violenceDownButton.onClick();
		
		resourceUpButton.onClick();
		resourceDownButton.onClick();

		pursuitLeftButton.onClick();
		pursuitRightButton.onClick();
		
		if(millis()-rotateStartTime > rotateTimer){
			if(rotatePos < 2){
				rotatePos++;
			}else{
				rotatePos = 0;
			}
			rotateStartTime = millis();
			
			if(!hasClicked){
				if(rotatePos == 0){
					canShowConnections = true;
					canShowPredators = false;
					canShowResources = false;
				}else if(rotatePos == 1){
					canShowConnections = false;
					canShowPredators = false;
					canShowResources = true;
				}else{
					canShowConnections = false;
					canShowPredators = true;
					canShowResources = false;
				}
			}
		}
		
		showLegend();
	}
	
	void inGame(){
		if(!fading && !allAgentsDead && !isPaused) update();
		textSize(textFontSize);
		textFont(textFont);
		textAlign(LEFT);
		noStroke();
		fill(bgColBox, 50);
		bgColBox = lerpColor(bgColBox, newBgColBox, bgColBoxLerpSpeed);
		bgColBoxLerpSpeed = map((millis()-Seasons.startTime), 0, Seasons.timer, 0, 1);
		
		seasons.cycle();
		//seasons.populate(Seasons.numberOfSeasons);
		drawVoronoi(1);
		if(language == "english"){
			loadingText = "fetching the story of their lives";
		}else{
			loadingText = "r\u00E9daction de sa vie";
		}
		
		
		if(arriveIndex < agents.length){
			if(millis() - agentsArriveStartTimer > agentsArriveTimer){
				agents[arriveIndex].arrived = true;
				arriveIndex++;
				agentsArriveStartTimer = millis();
				if(agentsArriveTimer > 1000) agentsArriveTimer -= 500;
			}
		}
		
		for(int i = 0; i < resources.size(); i++){
			Resource r = resources.get(i);
			r.display();
			r.deplete();
		}
		
		for (int i = 0; i < predators.size(); i++) {
			Predator p = predators.get(i);
			if (p.isAlive){
				p.display();
			}
			if (!p.isAlive) {
				stroke(10);
				line(p.pos.x-random(1, 3), p.pos.y-random(1, 3), p.pos.x+random(1, 3), p.pos.y+random(1, 3));
				line(p.pos.x-random(1, 3), p.pos.y+random(1, 3), p.pos.x+random(1, 3), p.pos.y-random(1, 3));
			}
		}

		for (int i = 0; i < connections.size(); i++) {
			Connection c = connections.get(i);
			c.display();
			c.destroyConnection();
		}
		
		float choiceY = height - height*0.02f;
		textSize(textFontSize);
		textFont(textFont);
		textAlign(CENTER);
		
		fill(255);
		noStroke();
		rectMode(CORNER);
		rect(0, rectBorderH, width, 300);
		
		stroke(0);
		
		fill(0, interactionAlpha);
		if (selGenPred)	fill(0, 150+selPulse);
		
		textAlign(LEFT);
		if(language == "english"){
			text("introduce predators", rectBorderX, choiceY);
		}else{
			text("introduire des pr\u00E9dateurs", rectBorderX, choiceY);	
		}
		fill(0, interactionAlpha);
		
		textAlign(CENTER);
		
		if (selRemoveConnec) fill(0, 150+selPulse);
		
		if(language == "english"){
			text("remove connections", width*0.3334f, choiceY);
		}else{
			text("supprimer des connexions", width*0.3334f, choiceY);	
		}
		fill(0, interactionAlpha);
		
		if (selStopAgent) fill(0, 150+selPulse);
		
		if(language == "english"){
			text("immobilize an agent", width*0.6667f, choiceY);
		}else{
			text("immobiliser un individu", width*0.6667f, choiceY);	
		}
		fill(0, interactionAlpha);
		
		textAlign(RIGHT);
		
		if(selFinalCluster)	fill(0, interactionAlpha+selPulse);

		if(finalCluster == null){
			if(language == "english") text("advance", rectBorderX+rectBorderW, choiceY);
			if(language == "francais") text("continuer", rectBorderX+rectBorderW, choiceY);
			if(rectProceedAlpha > 0) rectProceedAlpha -= 10.0f;
		}else if(finalCluster != null){
			if(language == "english") text("press space", rectBorderX+rectBorderW, choiceY);
			if(language == "francais") text("appuyer sur espace", rectBorderX+rectBorderW, choiceY);
			if(rectProceedAlpha < 200) rectProceedAlpha += 10.0f;
		}
		fill(0);

		onHover(1);
		
		for (int i = 0; i < agents.length; i++) {
			if (agents[i].isAlive && agents[i].arrived){
				agents[i].display();
				agents[i].debug();
			}else if(!agents[i].isAlive && agents[i].arrived){
				strokeWeight(1);
				noFill();
				agents[i].deathVisuals();
				strokeWeight(1);
				stroke(0, agents[i].graveAlpha);
				agents[i].graveAlpha -= agents[i].graveAlphaInc*(1.5f-rememberDead);
				float inc = agents[i].rad*0.2f;
				line(agents[i].pos.x-inc, agents[i].pos.y, agents[i].pos.x+inc, agents[i].pos.y);
				line(agents[i].pos.x, agents[i].pos.y-inc, agents[i].pos.x, agents[i].pos.y+inc*2.0f);
			}
		}
		
		textAlign(CENTER);
		rectMode(CORNER);
		textFont(thoughtFont);
		textSize(thoughtFontSize);
		if(showGenPredInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW-2, rectBorderH*0.03f-1);
			fill(0, 200);
			textAlign(LEFT);
			if(language == "english"){
				text("click anywhere on the screen to make a predator appear.", rectBorderX*2.0f, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("cliquez pour faire appara\u00EEtre un pr\u00E9dateur.", rectBorderX*2.0f, rectBorderY+(rectBorderH*0.99f));
			}
		}
		
		textAlign(CENTER);
		if(showStopAgentInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW-2, rectBorderH*0.03f-1);
			fill(0, 200);
			if(language == "english"){
				text("click on a connection to sever the bond between to individuals.", width*0.3334f, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("cliquez sur une connexion entre individus pour la faire dispara\u00EEtre.", width*0.3334f, rectBorderY+(rectBorderH*0.99f));
				
			}
		}
		
		if(showRemoveConnectionInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW-2, rectBorderH*0.03f-1);
			fill(0, 200);
			if(language == "english"){
				text("click on an individuals to prevent him from moving, forever.", width*0.6667f, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("cliquez sur un individu pour l'immobiliser \u00E0 jamais.", width*0.6667f, rectBorderY+(rectBorderH*0.99f));
			}
		}
		
		if(showSelFinalClusterInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW-2, rectBorderH*0.03f-1);
			fill(0, 200);
			textAlign(RIGHT);
			if(language == "english"){
				text("click on a group of individuals whose future you'd like to witness.", rectBorderW, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("cliquez sur un individus dont vous souhaitez entendre le r\u00E9cit.", rectBorderW, rectBorderY+(rectBorderH*0.99f));
			}
		}

		showAssumptions();
		textFont(textFont);
		textSize(textFontSize);
		resetButton.display();
		resetButton.onClick();
		showAssumptions.display();
		showAssumptions.onClick();
		
		death(1);
		
		noStroke();
		fill(255, rectFadeAlpha);
		
		if(rectFadeAlpha > 0 && selectedAgent == null){
			rectMode(CORNER);
			//removing all the "-2px" and "+1px" to account for the stokeWeight
			rectFadeTopH = (agents[0].pos.y + rectBorderY) - rectFadeBuffer;
			rectFadeTopW = rectBorderW-2;
				
			rectFadeRightW = -(rectBorderW - agents[0].pos.x) + rectFadeBuffer;

			rectFadeRightH = rectBorderH-2;
			
			rectFadeLeftW = (agents[0].pos.x + rectBorderX) - rectFadeBuffer;
			rectFadeLeftH = rectBorderH-2;

			rectFadeBottomH = -(rectBorderH - agents[0].pos.y) + rectFadeBuffer;
			rectFadeBottomW = rectBorderW-2;
			
			rect(rectFadeTopPos.x+1, rectFadeTopPos.y, rectFadeTopW, rectFadeTopH);
			rect(rectFadeRightPos.x, rectFadeRightPos.y, rectFadeRightW, rectFadeRightH);
			rect(rectFadeLeftPos.x+1, rectFadeLeftPos.y, rectFadeLeftW, rectFadeLeftH);
			rect(rectFadeBottomPos.x+1, rectFadeBottomPos.y, rectFadeBottomW, rectFadeBottomH);
			
			rectFadeAlpha -= rectFadeAlphaInc;
			if(rectFadeBuffer < rectFadeBufferMax) rectFadeBuffer += 0.25f;
			
			//conditions to quicken the reveal of the world
			if(!agents[0].isAlive || agents[0].connec != agents[0].connecMax) rectFadeAlphaInc += 0.5f;
		}else if(selectedAgent != null){
			rectMode(CORNER);
			rectFadeTopH = (selectedAgent.pos.y + rectBorderY) - rectFadeBuffer;
			rectFadeTopW = rectBorderW-2;
			rectFadeRightW = -(rectBorderW - selectedAgent.pos.x) + rectFadeBuffer;
			rectFadeRightH = rectBorderH-2;
			rectFadeLeftW = (selectedAgent.pos.x + rectBorderX) - rectFadeBuffer;
			rectFadeLeftH = rectBorderH-2;
			rectFadeBottomH = -(rectBorderH - selectedAgent.pos.y) + rectFadeBuffer;
			rectFadeBottomW = rectBorderW-2;
			
			rect(rectFadeTopPos.x+1, rectFadeTopPos.y, rectFadeTopW, rectFadeTopH);
			rect(rectFadeRightPos.x, rectFadeRightPos.y, rectFadeRightW, rectFadeRightH);
			rect(rectFadeLeftPos.x+1, rectFadeLeftPos.y, rectFadeLeftW, rectFadeLeftH);
			rect(rectFadeBottomPos.x+1, rectFadeBottomPos.y, rectFadeBottomW, rectFadeBottomH);
			
			if(fading) rectFadeAlpha += 10.0f;
		}
		
		fill(255, rectProceedAlpha);
		stroke(0, rectProceedAlpha);
		rectMode(CENTER);
		textAlign(CENTER);
		rect(width*0.5f, height*0.5f, rectBorderW-2, height*0.1f);
		fill(0, rectProceedAlpha);
		textFont(headerFont);
		textSize(headerFontSize);
		if(language == "english"){
			text("press space to proceed", width*0.5f, height*0.5f);
		}else{
			text("appuyer sur espace pour continuer", width*0.5f, height*0.5f);
		}
	}
	
	void debug(){
		textAlign(LEFT);
		textFont(textFont);
		textSize(textFontSize);
		fill(0, 200);
		if(inGame1){
			String agents_debug = "agents: "+agents.length;
		}else{
			String agents_debug = "not the correct phase";
		}
		String nations_debug = "nations: " + others.size();
		String allDead_debug = "all agents dead: "+allAgentsDead;
		String resources_debug = "resources 3: "+resources3.size();
		String stayClose_debug = "stayClose: "+formingStableRelationships;
		String meetingOthers_debug = "meetingOthers: "+meetingOthers;
		String rememberDead_debug = "rememberDead: "+rememberDead;
		String power_debug = "power: "+powerForce;
		String friendship_debug = "friendship: "+friendshipForce;
		String love_debug = "love: "+loveForce;
		//text("language: "+language, width*0.025f, height*0.025f);
		//text(friendship_debug, width*0.025f, height*0.045f);
		//text(power_debug, width*0.025f, height*0.065f);
		//text("love: "+connectionsLove.size(), width*0.025f, height*0.085f);
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
	
	void endGame(){
		background(255);
		stroke(100);
		strokeWeight(1);
		noFill();
		for(int i = 1; i < 3; i++){
			rect(rectBorderX+2*i, rectBorderY+2*i, rectBorderW-4*i, rectBorderHTemp-4*i);
		}
		
		canStart = false;
		interactions = 0;
		
		if(language == "english"){
			loadingText = "moving on to the second stage of their history";
		}else{
			loadingText = "avanc\u00E9e vers le second temps de leur histoire";
		}
		
		fill(0);
		textSize(mainFontSize);
		textFont(mainFont);
		textAlign(CENTER);
		
		if(language == "english"){
			text("On "+storyTitle1, width/2, height/10);
		}else{
			text("Sur "+storyTitle1, width/2, height/10);
		}
		
		
		textSize(textFontSize);
		textFont(textFont);
		textAlign(LEFT);
		
		if(randGen3){
			if(meetingOthers == 1.0f) meetingOthersRND = (int) random(meetingOthersEndString.length*0.5f);
			if(meetingOthers == 1.5f) meetingOthersRND = (int) random(meetingOthersEndString.length*0.5f, meetingOthersEndString.length);
			if(resourceSeek == 1.0f) meetingOthersRND = (int) random(resourcesString.length*0.5f);
			if(resourceSeek == 1.5f) meetingOthersRND = (int) random(resourcesString.length*0.5f, resourcesString.length);
			
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
			hasFoughtRND = (int) random(hasFoughtOthers.length);
			
			meetingOthersRNDX = (int) random(meetingOthersEndStringX.length);
			resourcesRNDX = (int) random(resourcesStringX.length);
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
			hasFoughtRNDX = (int) random(hasFoughtOthersX.length);
			
			randGen3 = false;
			if(selectedAgent != null) portraitRad = map(selectedAgent.rad, 25, 30, width*0.1f, width*0.15f);
		}
		
		if(finalCluster != null){
				fill(0, textAlpha);
					//getting all the parts into one big string
					endGame1_text = "";
					
					if(meetingOthers == 0.5f){
						endGame1_text = endGame1_text + meetingOthersEndStringX[meetingOthersRNDX] + " ";
					}else{ // 0.5 and 1.0 are both combined here, the difference happens when picking the integer
						endGame1_text = endGame1_text + meetingOthersEndString[meetingOthersRND] + " ";
					}
					
					if(resourceSeek == 0.5f){
						endGame1_text = endGame1_text + resourcesStringX[resourcesRNDX] + " ";
					}else{ // 0.5 and 1.0 are both combined here, the difference happens when picking the integer
						endGame1_text = endGame1_text + resourcesString[resourcesRND] + " ";
					}
					
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
					
					if(selectedAgent.hasFoughtOthers){
						endGame1_text = endGame1_text+hasFoughtOthers[hasFoughtRND]+" ";
					}else{
						endGame1_text = endGame1_text+hasFoughtOthersX[hasFoughtRNDX]+" ";
					}
					
					text(endGame1_text, width/5, height/5, (width/5)*3, 19*(height/20));
					
					//drawing part
					if(selectedAgent != null && (!selectedAgent.isAlive || selectedAgent.isKiller)){
						stroke(255, 0, 0, textAlpha);
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
				if(language == "english"){
					text("no community chosen", width*0.5f, height*0.5f);
				}else{
					text("aucune communaute choisie", width*0.5f, height*0.5f);
				}
				
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
		
		strokeWeight(proceedSW);
		stroke(10, 210);
		noFill();
		rect(proceedX, proceedY, proceedW, proceedH);
		textFont(textFont);
		textSize(textFontSize);
		fill(0);
		if(language == "english"){
			text("proceed", proceedX*1.015f, proceedY*1.03f);
		}else{
			text("continuer", proceedX*1.015f, proceedY*1.03f);
		}
		
		if(mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY + proceedH){
			proceedSW = 2;
		}else{
			proceedSW = 1;
		}
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
	
	void death(int stage){
		if(stage == 1){
			boolean allDead = true;
			for(int i = 0; i < agents.length; i++){
				if(agents[i].isAlive) allDead = false;
			}
			
			if(allDead){
				if(seasons != null) eMasterSeasons.addSegment(0.00f, 2000.0f);
				allAgentsDead = true;
				for(int i = 0; i < resources.size(); i++){
					Resource r = resources.get(i);
					r.canGrow = true;
				}
				
				//display
				fill(100, 0, 0, rectDeadAlpha);
				noStroke();
				rectMode(CENTER);
				textAlign(CENTER);
				rect(width*0.5f, height*0.5f, width*0.992f, height*0.1f);
				fill(255, rectDeadAlpha);
				textFont(headerFont);
				textSize(headerFontSize);
				if(language == "english"){
					text("their story ended", width*0.5f, height*0.5f);
				}else{
					text("leur histoire a pris fin", width*0.5f, height*0.5f);
				}
				
				if(rectDeadAlpha <= 200) rectDeadAlpha += 0.5f;
				if(rectBorderHTemp < rectBorderHMenu) rectBorderHTemp += 1.0f;
				if(interactionAlpha > 0) interactionAlpha -= 1.0f;
				if(rectDeadAlpha >= 200){
					for(int i = 0; i < resources.size(); i++){
						Resource r = resources.get(i);
						r.canGrow = false;
					}
					inGame1 = false;
					startGame1 = true;
				}
			}
			
		}else if(stage == 2){
			boolean allDead = false;
			if(community.size() == 0) allDead = true;
			
			if(allDead){
				allAgentsDead = true;
				resources.clear();
				
				//display
				fill(100, 0, 0, rectDeadAlpha);
				noStroke();
				rectMode(CENTER);
				textAlign(CENTER);
				rect(width*0.5f, height*0.5f, width*0.992f, height*0.1f);
				fill(255, rectDeadAlpha);
				textFont(headerFont);
				textSize(headerFontSize);
				if(language == "english"){
					text("their story ended", width*0.5f, height*0.5f);
				}else{
					text("leur histoire a pris fin", width*0.5f, height*0.5f);
				}
				
				if(rectDeadAlpha < 200) rectDeadAlpha += 0.5f;
				if(rectBorderHTemp < rectBorderHMenu) rectBorderHTemp += 1.0f;
				if(interactionAlpha > 0) interactionAlpha -= 1.0f;
				if(rectDeadAlpha > 300){
					for(int i = 0; i < resources.size(); i++){
						Resource r = resources.get(i);
						r.canGrow = false;
					}
					inGame2 = false;
					startGame2 = true;
				}
			}
		}else{ // --- stage 3
			boolean allDead = false;
			if(others.size() == 0) allDead = true;
			
			if(allDead){
				if(seasons != null) eMasterSeasons.addSegment(0.00f, 2000.0f);
				allAgentsDead = true;
				for(int i = 0; i < resources3.size(); i++){
					Resource r = resources3.get(i);
					r.canGrow = true;
				}
				
				//display
				fill(100, 0, 0, rectDeadAlpha);
				noStroke();
				rectMode(CENTER);
				textAlign(CENTER);
				rect(width*0.5f, height*0.5f, width*0.992f, height*0.1f);
				fill(255, rectDeadAlpha);
				textFont(headerFont);
				textSize(headerFontSize);
				if(language == "english"){
					text("history ended", width*0.5f, height*0.5f);
				}else{
					text("l'histoire a pris fin", width*0.5f, height*0.5f);
				}
				
				if(rectDeadAlpha < 200) rectDeadAlpha += 0.5f;
				if(rectBorderHTemp < rectBorderHMenu) rectBorderHTemp += 1.0f;
				if(interactionAlpha > 0) interactionAlpha -= 1.0f;
				if(rectDeadAlpha >= 200){
					for(int i = 0; i < resources3.size(); i++){
						Resource r = resources3.get(i);
						r.canGrow = false;
					}
					othersPositions = new ArrayList<PVector>();
					for(int i = 0; i < OTHERS_POSITIONS.size(); i++){
						PVector pos = OTHERS_POSITIONS.get(i);
						othersPositions.add(pos);
					}
					inGame3 = false;
					startGame3 = true;
				}
			}
		}
	}
	
	void onHover(int stage){//takes an argument to make sure at what stage it is to display relevant info and i don't have to write the same for/if statement over and over and over and over again
		/*------------ stage 1 ---------*/	
		if(stage == 1){
			hovering = false;
			
			for(int i = 0; i < agents.length; i++){
				if(dist(mouseX, mouseY, agents[i].pos.x, agents[i].pos.y) < agents[i].rad && agents[i].isAlive && agents[i].arrived && !allAgentsDead){
					hovering = true;
					hoveredAgent = agents[i];
					pushMatrix();
					translate(hoveredAgent.pos.x, hoveredAgent.pos.y);
					rotate(PI/4);
					rectMode(CENTER);
					stroke(100, 100);
					strokeWeight(5);
					noFill();
					rect(0, 0, hoveredAgent.rad, hoveredAgent.rad);
					strokeWeight(2);
					popMatrix();
					if(hoverTextAlpha < 150) hoverTextAlpha += hoverTextAlphaRate;
				}
			 }
			
			textFont(thoughtFont);
			textSize(thoughtFontSize);
			
			hoveringPredator = false;
			
			fill(0, hoverTextAlphaPredator);
			for(int i = 0; i < predators.size(); i++){
				Predator p = predators.get(i);
				if(dist(mouseX, mouseY, p.pos.x, p.pos.y) < p.size && p.isAlive){
					p.sw = 2;
					hoveredPredator = p;
					hoveringPredator = true;
				}else{
					p.sw = 1;
				}
			}
			
			if(hoveringPredator){
				if(hoverTextAlphaPredator < 150) hoverTextAlphaPredator += hoverTextAlphaPredatorRate;
				
				if(hoverTextAlphaPredator < 1) hoverRandPredator = random(1);
				
				if(language == "english"){
					if(hoveredPredator.isDomesticated ){
						if(hoverRandPredator < 0.25f){
							predatorThought = "the beast has been tamed";
						}else if(hoverRandPredator < 0.5f){
							predatorThought = "the beast is no longer a danger";
						}else if(hoverRandPredator < 0.75f){
							predatorThought = "the beast has found a master";
						}else{
							predatorThought = "...";
						}
					}else if(hoveredPredator.hungerDuration > 0){ //has eaten
						if(hoverRandPredator < 0.25f){
							predatorThought = "the beast is no longer hungry";
						}else if(hoverRandPredator < 0.5f){
							predatorThought = "the beast has killed";
						}else if(hoverRandPredator < 0.75f){
							predatorThought = "the beast has fed upon others";
						}else{
							predatorThought = "...";
						}
					}else{
						if(hoverRandPredator < 0.25f){
							predatorThought = "the beast is looking for preys";
						}else if(hoverRandPredator < 0.5f){
							predatorThought = "the beast is hungry";
						}else if(hoverRandPredator < 0.75f){
							predatorThought = "the beast starves";
						}else{
							predatorThought = "...";
						}
					}
				}else{
					if(hoveredPredator.isDomesticated){
						if(hoverRandPredator < 0.25f){
							predatorThought = "la b\u00EAte est apprivois\u00E9e";
						}else if(hoverRandPredator < 0.5f){
							predatorThought = "la b\u00EAte n'est plus un danger";
						}else if(hoverRandPredator < 0.75f){
							predatorThought = "la b\u00EAte a trouv\u00E9 son ma\u00EEtre";
						}else{
							predatorThought = "...";
						}
					}else if(hoveredPredator.hungerDuration > 0){ //has eaten
						if(hoverRandPredator < 0.25f){
							predatorThought = "la b\u00EAte n'est plus affam\u00E9e";
						}else if(hoverRandPredator < 0.5f){
							predatorThought = "la b\u00EAte a tu\u00E9";
						}else if(hoverRandPredator < 0.75f){
							predatorThought = "la b\u00EAte s'est nourrie d'un autre";
						}else{
							predatorThought = "...";
						}
					}else{
						if(hoverRandPredator < 0.25f){
							predatorThought = "la b\u00EAte cherche des proies";
						}else if(hoverRandPredator < 0.5f){
							predatorThought = "la b\u00EAte a faim";
						}else if(hoverRandPredator < 0.75f){
							predatorThought = "la b\u00EAte est affamm\u00E9e";
						}else{
							predatorThought = "...";
						}
					}	
				}
				
			}else{
				if(hoverTextAlphaPredator > 0) hoverTextAlphaPredator -= hoverTextAlphaPredatorRate;
			}
			
			if(hoveredPredator != null) text(predatorThought, hoveredPredator.pos.x*1.01f, hoveredPredator.pos.y*0.98f);
			
			if(hoverTextAlphaPredator < 5) hoveredPredator = null;
			
			if(!hovering){
				if(hoverTextAlpha > 0) hoverTextAlpha -= hoverTextAlphaRate;
				if(hoverTextAlpha <= 0) hoveredAgent = null;
			}
			
			if(hoverCanGenerateRand) hoverRand = random(1);
			
			if(hoveredAgent != null){
				hoverCanGenerateRand = false;
				textAlign(LEFT);
				textFont(thoughtFont);
				textSize(thoughtFontSize);
				fill(0, hoverTextAlpha);
				text(hoveredAgent.computeThought(1, hoverRand), hoveredAgent.pos.x + hoveredAgent.rad, hoveredAgent.pos.y);
			}else if(hoveringPredator){
				hoverCanGenerateRand = true;
			}else{
				hoverTextAlpha = 0;
				hoverCanGenerateRand = true;
			}
		}
		
		/*------------ stage 2 ---------*/	
		if(stage == 2){
			
			hovering = false;
			
			for(int i = 0; i < community.size(); i++){
				Agent a = community.get(i);
				if(dist(mouseX, mouseY, a.pos.x, a.pos.y) < a.rad && a.isAlive){
					a.speak();
					if(hoverTextAlpha < 150) hoverTextAlpha += hoverTextAlphaRate;
					hovering = true;
					hoveredAgent = a;
					for(int j = 0; j < community.size(); j++){
						Agent a2 = community.get(j);
						if(a2 != a) a2.canTalk = true;
					}
				}
			}
			
			if(!hovering){
				if(hoverTextAlpha > 0) hoverTextAlpha -= hoverTextAlphaRate;
				if(hoverTextAlpha <= 0) hoveredAgent = null;
			}
			
			if(hoverCanGenerateRand) hoverRand = random(1);

			if(hoveredAgent != null){
				hoverCanGenerateRand = false;
				textAlign(LEFT);
				textFont(thoughtFont);
				textSize(thoughtFontSize);
				fill(0, hoverTextAlpha);
				text(hoveredAgent.computeThought(stage, hoverRand), hoveredAgent.pos.x + hoveredAgent.rad, hoveredAgent.pos.y);
			}else{
				hoverCanGenerateRand = true;
			}
		 }
		
		/*------------ stage 3 ---------*/	
		if(stage == 3){
			boolean nationHovering = false;
			for(int i = 0; i < others.size(); i++){
				Nation n = others.get(i);
				
				if(dist(mouseX, mouseY, n.pos.x, n.pos.y) < n.rad){
					n.hullWeight = 4;
					textFont(thoughtFont);
					textSize(thoughtFontSize);
					if(!n.hovered) randomStatement  = (int)random(n.possibleStatements.size()*0.5f, n.possibleStatements.size());
					fill(0);
					n.hovered = true;
					statementStr = n.statement(randomStatement);
					statementHMax = textWidth(statementStr)*0.5f;
					hoveredNation = n;
					nationHovering = true;
				}else{
					n.hovered = false;
					n.hullWeight = 2;
				}
			}
			
			if(hoveredNation != null && nationHovering){
				if(statementH < statementHMax) statementH += statementHInc;
				if(statementH > statementHMax*0.75f && statementRectAlpha < 220) statementRectAlpha += statementAlphaInc;
				if(statementRectAlpha > 50 && statementRectAlpha < 220) statementTextAlpha += statementAlphaInc;
			}
			if(!nationHovering){
				if(statementH > 0) statementH -= statementHInc;
				if(statementRectAlpha > 0) statementRectAlpha -= statementAlphaInc*1.5f;
				if(statementTextAlpha > 0) statementTextAlpha -= statementAlphaInc*1.5f;
			}
			
			if(!nationHovering && statementRectAlpha < 10) hoveredNation = null;
			if(hoveredNation == null){
				statementH = 0;
				statementRectAlpha = 0;
				statementTextAlpha = 0;
			}
			
			fill(255, statementRectAlpha);
			strokeWeight(2);
			stroke(100, statementRectAlpha);
			rectMode(CENTER);
			if(hoveredNation != null) rect(hoveredNation.pos.x, hoveredNation.pos.y, textWidth(statementStr)*1.1f, statementH);
			fill(0, statementTextAlpha);
			textAlign(LEFT);
			if(hoveredNation != null) text(statementHeader+statementStr, hoveredNation.pos.x-hoveredNation.rad*0.0f, hoveredNation.pos.y+hoveredNation.rad*0.05f, textWidth(statementStr)*0.85f, statementH);
		}
		noFill();
	}

	void generateAgents(int numberOfAgents) {
		allAgentsDead = false;
		arriveIndex = 0;
		rad = 25 + (int) (random(0, 5));
		
		if(seasons == null) seasons = new Seasons(this);
		
		if(startGame2 && finalCluster != null){// this is the normal situation
			community.clear(); //we start by emptying any previous community if they died
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
				a.connec = 5;
				a.velocity = new PVector(random(-1.5f, 1.5f), random(-1.5f, 1.5f));
				community.add(a);
			}

			for(int i = 0; i < resources.size(); i++){
				Resource r = resources.get(i);
				for(int j = 0; j < community.size(); j++){
					Agent a = community.get(j);
					if(PVector.dist(r.pos, a.pos) < r.rad*10.0f){
						if(!resources2.contains(r)) resources2.add(r);
					}
				}
			}
			
			resources2.clear();
			
			
			for(int i = 0; i < community.size(); i++){ //this is where i modify the variables of the agents based on start2 decisions
				Agent a = community.get(i);

				a.col = color(50);
				a.alpha = 255;
				a.powerModifier = powerForce;
				a.friendshipModifier = friendshipForce;
				a.loveModifier = loveForce;
				if(agents[i] != null){
					a.ageMajority = millis() + agents[i].lifeSpan*ageModifier;
				}else{
					a.ageMajority = random(108, 155)*1000.0f*ageModifier;
				}
				a.numberOfChildren = numberOfChildren;
				a.independenceOfChildren = independenceOfChildren;
				a.moveCoeff = 1.0f;
				a.startTimeDeath2 = millis();
				if(cultureOrigin == 1){
					Agent.cultSimRequirementFriendship = 6.5f + (a.friendshipModifier*0.1f); //it is 5 by default, so that means that people will have more friends
					a.cultureVariationFriendship = 0.01f; //the default value is 0.005f
				}
				if(cultureOrigin == 2){
					a.socialEnvironmentVariation = 0.01f; //the default value is 0.005f
				}
			}
			
			agents = null;
			totalClusters.clear();

		}else if(startGame2 && finalCluster == null){ //debug mode to startgame2
			println("generating community from scratch");
			for (int i = 0; i < numberOfAgents; i++) {
				agents[i] = new Agent(random(rad, width - rad), random(rad, height - rad - height/20), rad, 5, -2, 2, 100, formingStableRelationships, meetingOthers, violence, 1.0f, this);
				
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
			
			for(int i = 0; i < numberOfResources; i++){
				resources2.add(new Resource(random(width*0.1f, width*0.9f), random(height*0.1f, height*0.8f), (int)random(40, 100), this));
			}
		}else{ //start game = 1
			println("generating agents from scratch");
			agents = new Agent[numberOfAgents];
			for (int i = 0; i < numberOfAgents; i++) {
				agents[i] = new Agent(random(rad, width - rad), random(rad, height - rad - height/20), rad, connec, -2, 2, 100, formingStableRelationships, meetingOthers, violence, resourceSeek, this);
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
		allAgentsDead = false;
		startTimeWar = millis();
		if(seasons == null) seasons = new Seasons(this);
		
		if(language == "english"){
			if(nationGoal == 0){//preserve
				statementHeader = "we state:\n\n";
			}else if(nationGoal == 1){//honor
				statementHeader = "we declare:\n\n";
			}else{//wealth
				statementHeader = "we announce:\n\n";
			}
		}else{
			if(nationGoal == 0){//preserve
				statementHeader = "nous affirmons:\n\n";
			}else if(nationGoal == 1){//honor
				statementHeader = "nous declarons:\n\n";
			}else{//wealth
				statementHeader = "nous annon\u00E7ons:\n\n";
			}
		}

		
		if(community.size() == 0){
			println("generating out of nowhere");
			// pos / friendship / power / love / population / culturalHomogeneity / canFight / meetingOthers / connections / craveForWealth / wealth / isNation / papplet
			nation = new Nation(new PVector(width*0.5f, height*0.5f), 0.5f, 0.5f, 0.5f, 20.0f, 0.5f, 1.0f, 1.0f, 50, 0.5f, 100, true, this);
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
			
			nation = new Nation(new PVector(width*0.5f, height*0.5f), friendshipForce*0.5f, powerForce*0.5f, map(loveForce, 40, 60, 0.1f, 0.8f), community.size(), estimateCulture(community), violence, meetingOthers, connec*10, resourceSeek, tempWealth, true, this);
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
		
		//getting rid of the past
		community.clear();
		connections.clear();
		connectionsFriendship.clear();
		connectionsPower.clear();
		connectionsLove.clear();
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
			float protect;
			if(q < 0.3f){
				protect = 0.5f;
			}else if(q < 0.6f){
				protect = 1.0f;	
			}else{
				protect = 1.5f;
			}
			
			float cultureN = nation.culturalHomogeneity += random(-1.5f, 1.5f);
			
			// pos / friendship / power / love / population / culturalHomogeneity / canFight / meetingOthers / connections / craveForWealth / wealth / isNation / papplet
			Nation n = new Nation(pos, random(1), random(1), random(0.2f, 0.8f), pop,cultureN, fight, protect, pop*((int)random(3, 6)), random(1), random(50, 200), false, this);
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
		
		for(int i = 0; i < 20; i++){
			resources3.add(new Resource(random(width*0.005f, width*0.995f), random(height*0.005f, height*0.9f), (int)random(5, 15), this));
		}
	}

	void generatePredators() {
		if(predators.size() == 0){
			for (int i = 0; i < numberOfPredators; i++) {
				predators.add(new Predator(random(width), random(height-height/20), 10, -2, 2, random(80, 105) * 1000, random(4, 6) * 10, this));
			}
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
				if(a1 != a2){
					for(int k = 0; k < a1.culture.length; k++){ //compare each slot in culture
						if(abs(a1.culture[k] - a2.culture [k]) < culturalHomogeneityThreshold){
							println("cultural homogeneity: agents are similar");
							c++;
						}else{
							println("cultural homogeneity: agents are not similar");
						}
					}
				}
			}
		}
		c /= community.size(); //the higher the number, the tighter the community bonds are
		
		println("---");
		println("final cultural homogeneity:",c);
		return c;
	}

	void startGame2() {
		seasons = null;
		if(!fading) cursor(CROSS);
		rectProceedAlpha = 0.0f;
		rectMode(CORNER);
		rectCol = 245+(noise(noiseVal))*10; 
		fill(rectCol);
		noiseVal+= 0.4f;
		strokeWeight(1);
		stroke(0);
		
		rectBorderHTemp = rectBorderHMenu;
		
		if(interactions > interactionsThreshold) canStart = true;
		
		if(language == "english"){
			loadingText = "computing your world";
			legendTextContent = legendTextContent2;
		}else{
			loadingText = "calcul de votre monde";
			legendTextContent = legendTextContent2FR;
		}
		
		
		fill(15);
		textSize(mainFontSize);
		textFont(mainFont);
		textAlign(CENTER);
		text("Social Contact", width/2, height/10);
		
		textSize(subtitleFontSize);
		textAlign(CENTER);
		textFont(subtitleFont);
		if(language == "english"){
			fill(200);
			text("I - coming together", width/2, subtitleHeight);
			fill(10);
			text("II - growing together", width/2, subtitle2Height);
			fill(200);
			text("III - confronting others", width/2, subtitle3Height);
			fill(0);
		}else{
			fill(200);
			text("I - r\u00E9union", width/2, subtitleHeight);
			fill(10);
			text("II - communion", width/2, subtitle2Height);
			fill(200);
			text("III - confrontation", width/2, subtitle3Height);
			fill(0);
		}

		
		rectMode(CENTER);
		fishTank();
		
		//left column
		//textSize(headerFontSize);
		//textFont(headerFont);
		//text("Relations", leftColX, headerHeight);
		
		fill(0);
		textFont(textFont);
		textSize(textFontSize);
		//textAlign(LEFT);
		powerForceDownButton.display();
		powerForceDownButton.onClick();
		
		String powerForceText = "";
		
		if(language == "english"){
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
			assumptions[0] = "power struggles between beings are\n"+powerForceText;
		}else{
			if(powerForce == 1.0f){
				powerForceText = "indiscernables";
			}else if(powerForce == 3.0f){
				powerForceText = "pr\u00E9sentes";
			}else if(powerForce == 5.0f){
				powerForceText = "une des bases de toute interaction";
			}else if(powerForce == 7.0f){
				powerForceText = "une composante principale de leurs vies";
			}else{
				powerForceText = "la seule vraie relation sociale";
			}
			
			text("les relations de pouvoir entre chacun sont\n"+powerForceText, leftColX, firstRowY);
			assumptions[0] = "les relations de pouvoir entre chacun sont\n"+powerForceText;
		}

		powerForceUpButton.display();
		powerForceUpButton.onClick();
		
		friendshipForceDownButton.display();
		friendshipForceDownButton.onClick();
		
		String friendshipForceText = "";
		if(language == "english"){
			if(friendshipForce == 1.0f){
				friendshipForceText = "hardly notice each other";
			}else if(friendshipForce == 3.0f){
				friendshipForceText = "pay little attention to one another";
			}else if(friendshipForce == 5.0f){
				friendshipForceText = "stay within reach of each other";
			}else if(friendshipForce == 7.0f){
				friendshipForceText = "remain as close as possible";
			}
			
			text("two culturally similar beings tend to\n"+friendshipForceText, leftColX, secondRowY);
			assumptions[1] = "two culturally similar beings tend to\n"+friendshipForceText;
		}else{
			if(friendshipForce == 1.0f){
				friendshipForceText = "\u00E0 peine se remarquer";
			}else if(friendshipForce == 3.0f){
				friendshipForceText = "r\u00E9aliser leur existence r\u00E9ciproque";
			}else if(friendshipForce == 5.0f){
				friendshipForceText = "demeurer proches l'un de l'autre";
			}else if(friendshipForce == 7.0f){
				friendshipForceText = "ne plus vouloir se quitter";
			}
			
			text("deux \u00EAtres similaires vont\n"+friendshipForceText, leftColX, secondRowY);
			assumptions[1] = "deux \u00EAtres similaires vont\n"+friendshipForceText;
		}

		friendshipForceUpButton.display();
		friendshipForceUpButton.onClick();
		
		loveForceDownButton.display();
		loveForceDownButton.onClick();
		
		String loveForceText = "";
		
		if(language == "english"){
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
			assumptions[2] = "love is\n"+loveForceText;
		}else{
			if(loveForce == 40.0f){
				loveForceText = "presque non-existant";
			}else if(loveForce == 45.0f){
				loveForceText = "superficiel";
			}else if(loveForce == 50.0f){
				loveForceText = "une composante essentielle de la vie";
			}else if(loveForce == 55.0f){
				loveForceText = "difficile \u00E0 ignorer";
			}else{
				loveForceText = "le plus fort des liens";
			}
			
			text("l'amour est\n"+loveForceText, leftColX, thirdRowY);
			assumptions[2] = "l'amour est\n"+loveForceText;
		}

		loveForceUpButton.display();
		loveForceUpButton.onClick();		
		
		textFont(textFont);
		textSize(textFontSize);
		//textAlign(RIGHT);
		
		ageMajorityDownButton.display();
		ageMajorityDownButton.onClick();
		
		String ageModifierText = "";
		if(language == "english"){
			if(ageModifier == 0.20f){
				ageModifierText = "they should reproduce as soon as they can";
			}else if(ageModifier == 0.25f){
				ageModifierText = "they should reproduce when they see fit";
			}else{
				ageModifierText = "reproduction comes last";
			}
			
			text(ageModifierText, rightColX, firstRowY);
			assumptions[3] = ageModifierText;
		}else{
			if(ageModifier == 0.20f){
				ageModifierText = "ils doivent se reproduire d\u00E8s que possible";
			}else if(ageModifier == 0.25f){
				ageModifierText = "ils doivent se reproduire quand ils le d\u00E9sirent";
			}else{
				ageModifierText = "la reproduction ne doit pas \u00EAtre leur priorit\u00E9";
			}
			
			text(ageModifierText, rightColX, firstRowY);
			assumptions[3] = ageModifierText;
		}

		ageMajorityUpButton.display();
		ageMajorityUpButton.onClick();
		
		numberOfChildrenDownButton.display();
		numberOfChildrenDownButton.onClick();
		
		String numberOfChildrenText = "";
		
		if(language == "english"){
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
			assumptions[4] = numberOfChildrenText;
		}else{
			if(numberOfChildren == 0.0f){
				numberOfChildrenText = "ils n'auront pas d'enfants";
			}else if(numberOfChildren == 1.0f){
				numberOfChildrenText = "ils n'auront qu'un enfant";
			}else if(numberOfChildren == 2.0f){
				numberOfChildrenText = "ils n'auront pas plus de deux enfants";
			}else if(numberOfChildren == 3.0f){
				numberOfChildrenText = "ils n'auront pas plus de trois enfants";
			}else if(numberOfChildren == 4.0f){
				numberOfChildrenText = "ils n'auront pas plus de quatre enfants";
			}else{
				numberOfChildrenText = "ils auront autant d'enfants qu'ils le d\u00E9sirent";
			}
			
			text(numberOfChildrenText, rightColX, secondRowY);
			assumptions[4] = numberOfChildrenText;
		}

		numberOfChildrenUpButton.display();
		numberOfChildrenUpButton.onClick();
		
		independenceOfChildrenDownButton.display();
		independenceOfChildrenDownButton.onClick();
		
		String independenceOfChildrenText = "";
		
		if(language == "english"){
			if(independenceOfChildren == 1.0f){
				independenceOfChildrenText = "close to their birth parents";
			}else if(independenceOfChildren == 2.0f){
				independenceOfChildrenText = "within the group they were born in";
			}else{
				independenceOfChildrenText = "wherever they desire";
			}
			
			text("children will settle\n"+independenceOfChildrenText, rightColX, thirdRowY);
			assumptions[5] = "children will settle\n"+independenceOfChildrenText;
		}else{
			if(independenceOfChildren == 1.0f){
				independenceOfChildrenText = "proches de leurs parents";
			}else if(independenceOfChildren == 2.0f){
				independenceOfChildrenText = "au sein de leur groupe de naissance";
			}else{
				independenceOfChildrenText = "o\u00F9 ils le souhaitent";
			}
			
			text("les enfants demeureront\n"+independenceOfChildrenText, rightColX, thirdRowY);
			assumptions[5] = "les enfants demeureront\n"+independenceOfChildrenText;
		}

		independenceOfChildrenUpButton.display();
		independenceOfChildrenUpButton.onClick();
		
		//bottom
		textFont(textFont);
		textSize(textFontSize);
		String cultureText = "";
		
		//these are the two big assumptions - they don't really do anything but they allow people to think about it.
		if(language == "english"){
			if(cultureOrigin == 1){
				cultureText = "culture is\nthe result of where someone lives";
				storyTitle2 = "territory";
			}else if(cultureOrigin == 2){
				cultureText = "culture is\nthe result of with whom someone lives";
				storyTitle2 = "others";
			}else{
				cultureText = "culture is\never elusive";
				storyTitle2 = "uncertainty";
			}
		}else{
			if(cultureOrigin == 1){
				cultureText = "la culture\nest d\u00E9pendante du sol";
				storyTitle2 = "le territoire";
			}else if(cultureOrigin == 2){
				cultureText = "la culture\nest d\u00E9pendante des autres";
				storyTitle2 = "les autres";
			}else{
				cultureText = "la culture\ndemeure un myst\u00E8re";
				storyTitle2 = "l'incertitude";
			}
		}
		text(cultureText, fishTankPos.x, buttonGoalHeight-bufferGoalHeight);
		assumptions[6] = cultureText;

		cultureButtonRight.display();
		cultureButtonRight.onClick();
		cultureButtonLeft.display();
		cultureButtonLeft.onClick();
		
		showAlpha = abs(cos(millis()*0.001f)*150)+100;
		
		if(millis()-rotateStartTime > rotateTimer){
			if(rotatePos < 2){
				rotatePos++;
			}else{
				rotatePos = 0;
			}
			rotateStartTime = millis();
			
			if(!hasClicked){
				if(rotatePos == 0){
					canShowPower = true;
					canShowFriendship = false;
					canShowLove = false;
				}else if(rotatePos == 1){
					canShowPower = false;
					canShowFriendship = true;
					canShowLove = false;
				}else{
					canShowPower = false;
					canShowFriendship = false;
					canShowLove = true;
				}
			}
		}
		
		textFont(textFont);
		textSize(textFontSize);
		startButton2.display();
		startButton2.onClick();
		
		showLegend();
	}
	
	void inGame2() {
		background(255);
		if(!fading && !isPaused) update2();
		fill(bgColBox, 50);
		bgColBox = lerpColor(bgColBox, newBgColBox, bgColBoxLerpSpeed);
		bgColBoxLerpSpeed = map((millis()-Seasons.startTime), 0, 6000.0f, 0, 1);
		stroke(0);
		strokeWeight(1);
		
		//drawing the stratas of society eventually just made by the movement of agents
		strokeWeight(1);
		stroke(strataCol, strataAlpha*0.25f);
		
		if(language == "english"){
			loadingText = "fetching the story of their lives";
		}else{
			loadingText = "r\u00E9daction de sa vie";
		}
		
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
		//seasons.populate(Seasons.numberOfSeasons);
		for(int i = 0; i < resources2.size(); i++){
			println("displaying resource"+i);
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
				a.deathVisuals();
				strokeWeight(1);
				stroke(0, a.graveAlpha);
				a.graveAlpha -= a.graveAlphaInc*(1.5f-rememberDead);
				float inc = agents[i].rad*0.2f;
				line(a.pos.x-inc, a.pos.y, a.pos.x+inc, a.pos.y);
				line(a.pos.x, a.pos.y-inc, a.pos.x, a.pos.y+inc*2.0f);
			}
				a.debug();
		}
		
		onHover(2);
		
		for(int i = 0; i < connectionsPower.size(); i++){
			ConnectionPower cP = connectionsPower.get(i);
			if(cP.a1.isSettled && cP.a2.isSettled) cP.display();
		}
		
		for(int i = 0; i < connectionsLove.size(); i++){
			ConnectionLove cL = connectionsLove.get(i);
			cL.display();
		}
		
		for(int i = 0; i < connectionsFriendship.size(); i++){
			ConnectionFriendship cF = connectionsFriendship.get(i);
			cF.display();
		}	
		
		fill(255);
		rectMode(CORNER);
		noStroke();
		rect(0, rectBorderH, width, 300);
		
		//interactions
		float choiceY = height - height*0.02f;
		textSize(textFontSize);
		textFont(textFont);
		textAlign(LEFT);
		fill(0, interactionAlpha);
		stroke(0);
		strokeWeight(1);
		fill(150);
		if (selRevolt) fill(0, 150+selPulse);
		if(language == "english"){
			text("induce revolts", rectBorderX, choiceY);
		}else{
			text("inciter \u00E0 la r\u00E9volte", rectBorderX, choiceY);
		}
		fill(150);
		
		
		textAlign(CENTER);
		if (selOppression) fill(0, 150+selPulse);
		
		if(language == "english"){
			text("induce oppressions", width*0.25f, choiceY);
		}else{
			text("inciter \u00E0 l'oppression", width*0.25f, choiceY);
		}
		fill(150);
		
		if (forceCulturalSim) fill(0, 150+selPulse);
		
		if(language == "english"){
			text("force cultural similarities", width*0.5f, choiceY);
		}else{
			text("forcer une similarit\u00E9 culturelle", width*0.5f, choiceY);
		}
		fill(150);
		
		if (forceCulturalDiff) fill(0, 150+selPulse);
		if(language == "english"){
			text("force cultural differences", width*0.75f, choiceY);
		}else{
			text("forcer une diff\u00E9rence culturelle", width*0.75f, choiceY);
		}
		fill(150);
		
		textAlign(RIGHT);
		
		if(finalStructure){
			strokeWeight(1);
			stroke(0, 150);
			fill(255, 150);
			rectMode(CORNER);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW-2, rectBorderH*0.03f-1);
			fill(0, 200);
			textFont(thoughtFont);
			textSize(thoughtFontSize);
			if(language == "english"){
				text("select the one you want to hear from", rectBorderW, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("s\u00E9lectionnez celui dont vous voulez entendre le r\u00E9cit", rectBorderW, rectBorderY+(rectBorderH*0.99f));
			}
			if(selectedAgent2 == null){
				fill(0, 150+selPulse);
				textFont(textFont);
				textSize(textFontSize);
				if(language == "english"){
					text("advance further", rectBorderX+rectBorderW, choiceY);
				}else{
					text("avancer", rectBorderX+rectBorderW, choiceY);
				}
				if(rectProceedAlpha > 0) rectProceedAlpha -= 10.0f;
			}else if(selectedAgent2 != null){
				fill(0, 150+selPulse);
				textFont(textFont);
				textSize(textFontSize);
				if(language == "english"){
					text("press space", rectBorderX+rectBorderW, choiceY);
				}else{
					text("appuyez sur espace", rectBorderX+rectBorderW, choiceY);
				}
				if(rectProceedAlpha < 200) rectProceedAlpha += 10.0f;
			}
		}else{
			fill(150);
			textFont(textFont);
			textSize(textFontSize);
			if(language == "english"){
				text("advance further", rectBorderX+rectBorderW, choiceY);
			}else{
				text("avancer", rectBorderX+rectBorderW, choiceY);
			}
		}
		
		fill(0);
				
		//dragAgent();
		rectMode(CORNER);
		textFont(thoughtFont);
		textSize(thoughtFontSize);
		
		if(showInduceRevoltInfo){
			strokeWeight(1);
			stroke(0, 150);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW-2, rectBorderH*0.03f-1);
			fill(0, 200);
			textAlign(LEFT);
			if(language == "english"){
				text("click on an individual subjected to unjust amounts of power.", rectBorderX*2.0f, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("cliquez sur un individu soumis injustement.", rectBorderX*2.0f, rectBorderY+(rectBorderH*0.99f));
			}
		}
		textAlign(CENTER);
		if(showInduceOppressionInfo){
			strokeWeight(1);
			stroke(0, 150);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW-2, rectBorderH*0.03f-1);
			fill(0, 200);
			if(language == "english"){
				text("click on an individual to subject his surroundings to just amounts of power.", width*0.25f, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("cliquez sur un individu pour justement soumettre son entourage.", width*0.25f, rectBorderY+(rectBorderH*0.99f));
			}
		}

		if(showForceCultDiffInfo){
			strokeWeight(1);
			stroke(0, 150);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW-2, rectBorderH*0.03f-1);
			fill(0, 200);
			if(language == "english"){
				text("click on an individual to make him more like the others.", width*0.5f, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("cliquez sur un individu pour le rendre similaire \u00E0 son entourage.", width*0.5f, rectBorderY+(rectBorderH*0.99f));
			}
		}
		
		if(showForceCultSimInfo){
			strokeWeight(1);
			stroke(0, 150);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW-2, rectBorderH*0.03f-1);
			fill(0, 200);
			if(language == "english"){
				text("click on an individual to make him different from others.", width*0.75f, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("cliquez sur un individu pour le diff\u00E9rencier de son entourage.", width*0.75f, rectBorderY+(rectBorderH*0.99f));
			}
		}
		
		death(2);
		
		showAssumptions();
		textFont(textFont);
		textSize(textFontSize);
		resetButton.display();
		resetButton.onClick();
		showAssumptions.display();
		showAssumptions.onClick();
		
		noStroke();
		fill(255, rectFadeAlpha);
		
		if(rectFadeAlpha > 0 && selectedAgent2 == null){
			Agent a = community.get(0);
			rectMode(CORNER);
			//removing all the "-2px" and "+1px" to account for the stokeWeight
			rectFadeTopH = (a.pos.y + rectBorderY) - rectFadeBuffer*1.5f;
			rectFadeTopW = rectBorderW-2;
				
			rectFadeRightW = -(rectBorderW - a.pos.x) + rectFadeBuffer*1.5f;

			rectFadeRightH = rectBorderH-2;
			
			rectFadeLeftW = (a.pos.x + rectBorderX) - rectFadeBuffer*1.5f;
			rectFadeLeftH = rectBorderH-2;

			rectFadeBottomH = -(rectBorderH - a.pos.y) + rectFadeBuffer*1.5f;
			rectFadeBottomW = rectBorderW-2;
			
			rect(rectFadeTopPos.x+1, rectFadeTopPos.y, rectFadeTopW, rectFadeTopH);
			rect(rectFadeRightPos.x, rectFadeRightPos.y, rectFadeRightW, rectFadeRightH);
			rect(rectFadeLeftPos.x+1, rectFadeLeftPos.y, rectFadeLeftW, rectFadeLeftH);
			rect(rectFadeBottomPos.x+1, rectFadeBottomPos.y, rectFadeBottomW, rectFadeBottomH);
			
			rectFadeAlpha -= rectFadeAlphaInc*2.0f;
			if(rectFadeBuffer < rectFadeBufferMax) rectFadeBuffer += 0.25f;
			
			//conditions to quicken the reveal of the world
			if(!a.isAlive || a.connec != a.connecMax) rectFadeAlphaInc += 0.5f;
		}else if(selectedAgent2 != null){
			rectMode(CORNER);
			rectFadeTopH = (selectedAgent2.pos.y + rectBorderY) - rectFadeBuffer*1.25f;
			rectFadeTopW = rectBorderW-2;
			rectFadeRightW = -(rectBorderW - selectedAgent2.pos.x) + rectFadeBuffer*1.25f;
			rectFadeRightH = rectBorderH-2;
			rectFadeLeftW = (selectedAgent2.pos.x + rectBorderX) - rectFadeBuffer*1.25f;
			rectFadeLeftH = rectBorderH-2;
			rectFadeBottomH = -(rectBorderH - selectedAgent2.pos.y) + rectFadeBuffer*1.25f;
			rectFadeBottomW = rectBorderW-2;
			
			rect(rectFadeTopPos.x+1, rectFadeTopPos.y, rectFadeTopW, rectFadeTopH);
			rect(rectFadeRightPos.x, rectFadeRightPos.y, rectFadeRightW, rectFadeRightH);
			rect(rectFadeLeftPos.x+1, rectFadeLeftPos.y, rectFadeLeftW, rectFadeLeftH);
			rect(rectFadeBottomPos.x+1, rectFadeBottomPos.y, rectFadeBottomW, rectFadeBottomH);
			
			if(fading) rectFadeAlpha += 10.0f;
		}
		
		if(rectProceedAlpha > 2){
			fill(255, rectProceedAlpha);
			stroke(0, rectProceedAlpha);
			rectMode(CENTER);
			rect(width*0.5f, height*0.5f, rectBorderW-2, height*0.1f);
			fill(0, rectProceedAlpha);
			textFont(headerFont);
			textSize(headerFontSize);
			if(language == "english"){
				text("press space to proceed", width*0.5f, height*0.5f);
			}else{
				text("appuyez sur espace pour continuer", width*0.5f, height*0.5f);
			}
			textFont(textFont);
			textSize(textFontSize);
		}
	}
	
	void endGame2() {
		background(255);
		stroke(0);
		strokeWeight(1);
		noFill();
		for(int i = 1; i < 4; i++){
			rect(rectBorderX+2*i, rectBorderY+2*i, rectBorderW-4*i, rectBorderHTemp-4*i);
		}
		
		canStart = false;
		interactions = 0;
		
		if(language == "english"){
			loadingText = "moving on to the last stage of their history";
		}else{
			loadingText = "avanc\u00E9e vers le dernier temps de leur histoire";
		}
		
		
		fill(0);
		textSize(mainFontSize);
		textFont(mainFont);
		textAlign(CENTER);
		
		if(language == "english"){
			text("On "+storyTitle2, width/2, height/10);
		}else{
			text("Sur "+storyTitle2, width/2, height/10);
		}
		
		
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
				endGame2_text = endGame2_text + " " + spaceCultureText[indexCO1]; //
				endGame2_text = endGame2_text + " " + socialCultureText[indexCO1];
			}else if(cultureOrigin == 2){ //with whom
				endGame2_text = endGame2_text + " " + spaceCultureText[indexCO2];
				endGame2_text = endGame2_text + " " + socialCultureText[indexCO2];
			}
			
			
			float timeInLoveThreshold = 0;
			for(int i = 0; i < community.size(); i++){
				Agent a = community.get(i);
				timeInLoveThreshold += a.timeInLove;
			}
			timeInLoveThreshold /= community.size();
			
			if(selectedAgent2.timeInLove > timeInLoveThreshold){
				endGame2_text = endGame2_text + " " + inLoveText[indexLove1];
			}else{
				endGame2_text = endGame2_text + " " + inLoveText[indexLove2];
			}
			
			if(selectedAgent2.childrenHad > 0){
				endGame2_text = endGame2_text + " " + numberOfChildrenText[indexChildren1];
			}else{
				endGame2_text = endGame2_text + " " + numberOfChildrenText[indexChildren2];
			}
			
			if(selectedAgent2.generation < 2){
				endGame2_text = endGame2_text + " " + generationText[indexGen1];
			}else{
				endGame2_text = endGame2_text + " " + generationText[indexGen2];
			}			
			
			float friendsThreshold = 0;
			for(int i = 0; i < community.size(); i++){
				Agent a = community.get(i);
				friendsThreshold += a.friendsHad.size();
			}
			friendsThreshold /= community.size();
			
			if(selectedAgent2.friendsHad.size() > friendsThreshold){
				endGame2_text = endGame2_text + " " + friendships[friends1];
			}else{
				endGame2_text = endGame2_text + " " + friendships[friends2];
			}
			
			float powerExertedThreshold = 0;
			for(int i = 0; i < community.size(); i++){
				Agent a = community.get(i);
				powerExertedThreshold += a.powerExerted;
			}
			powerExertedThreshold /= community.size();
			
			if(selectedAgent2.powerExertedTotal > powerExertedThreshold){
				endGame2_text = endGame2_text + " " + oppressionInducedText[pExert1];
			}else{
				endGame2_text = endGame2_text + " " + oppressionInducedText[pExert2];
			}
			
			float powerFacedThreshold = 0;
			for(int i = 0; i < community.size(); i++){
				Agent a = community.get(i);
				powerFacedThreshold += a.powerFaced;
			}
			powerFacedThreshold /= community.size();
			
			if(selectedAgent2.powerReceivedTotal > powerFacedThreshold){
				endGame2_text = endGame2_text + " " + oppressionReceivedText[pReceived1] + "\n";
			}else{
				endGame2_text = endGame2_text + " " + oppressionReceivedText[pReceived2] + "\n";
			}

			//----- this relies on player input
			if(selectedAgent2.hasRevolted){
				endGame2_text = endGame2_text + " " + hasRevoltedText[indexRevolt1];
			}else{
				endGame2_text = endGame2_text + " " + hasRevoltedText[indexRevolt2];
			}
			
			if(selectedAgent2.hasOppressed){
				endGame2_text = endGame2_text + " " + hasOppressedText[indexOppressed1];
			}else{
				endGame2_text = endGame2_text + " " + hasOppressedText[indexOppressed2];
			}
			
			if(selectedAgent2.hasForcedSim){
				endGame2_text = endGame2_text + " " + hasForcedSimText[indexSim1];
			}else{
				endGame2_text = endGame2_text + " " + hasForcedSimText[indexSim2];
			}
			
			if(selectedAgent2.hasForcedDiff){
				endGame2_text = endGame2_text + " " + hasForcedDiffText[indexDiff1];
			}else{
				endGame2_text = endGame2_text + " " + hasForcedDiffText[indexDiff2];
			}
			
			if(culturalHomogeneity() < 50){
				endGame2_text = endGame2_text + " " + culturalHomogeneityText[indexCult1];
			}else{
				endGame2_text = endGame2_text + " " + culturalHomogeneityText[indexCult2];
			}
		}else{
			endGame2_text = "no agent selected";
		}
		
	}

		text(endGame2_text, width*0.2f, height*0.2f, (width/5)*3, 19*(height/20));	
		
		stroke(0);
		noFill();
		pushMatrix();
		translate(portraitPos.x, portraitPos.y);
		float angleInc;
		if(selectedAgent2 != null){
			angleInc = 360/(selectedAgent2.sides+5);
			
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
		}
		
		popMatrix();
		  
		strokeWeight(1);
		stroke(10, 210);
		noFill();
		rect(proceedX, proceedY, proceedW, proceedH);
		textFont(textFont);
		textSize(textFontSize);
		fill(0);
		if(language == "english"){
			text("proceed", proceedX*1.015f, proceedY*1.03f);
		}else{
			text("continuer", proceedX*1.015f, proceedY*1.03f);
		}
		
		if(mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY + proceedH){
			proceedSW = 2;
		}else{
			proceedSW = 1;
		}
	}
	
	void startGame3() {
		cursor(CROSS);
		seasons = null;
		rectProceedAlpha = 0.0f;
		rectMode(CORNER);
		rectCol = 245+(noise(noiseVal))*10; 
		fill(rectCol);
		noiseVal+= 0.4f;
		strokeWeight(1);
		stroke(0);
		
		rectBorderHTemp = rectBorderHMenu;
		
		if(interactions > interactionsThreshold) canStart = true;
		
		if(language == "english"){
			loadingText = "computing your world";
			legendTextContent = legendTextContent3;
		}else{
			loadingText = "calcul de votre monde";
			legendTextContent = legendTextContent3FR;
		}
		

		fill(15);
		textSize(headerFontSize);
		textAlign(CENTER);
		textFont(mainFont);
		text("Social Contact", width/2, height/10);
		
		textSize(subtitleFontSize);
		textFont(subtitleFont);
		if(language == "english"){
			fill(200);
			text("I - coming together", width/2, subtitleHeight);
			text("II - growing together", width/2, subtitle2Height);
			fill(10);
			text("III - confronting others", width/2, subtitle3Height);
			fill(0);
		}else{
			fill(200);
			text("I - r\u00E9union", width/2, subtitleHeight);
			text("II - communion", width/2, subtitle2Height);
			fill(10);
			text("III - confrontation", width/2, subtitle3Height);
			fill(0);
		}
		
		rectMode(CENTER);
		fishTank();
		
		fill(0);
		textSize(headerFontSize);
		textFont(headerFont);

		textFont(textFont);
		textSize(textFontSize);
		textAlign(CENTER);
		
		showAlpha = abs(cos(millis()*0.001f+random(0.05f, 0.08f))*150)+100;
		textAlign(CENTER);
		fill(0);
		String allianceModString = "";
		
		if(language == "english"){
			if(allianceModifier == 0.5f){
				allianceModString = "alliances are easy to make";
			}else if(allianceModifier == 1.0f){
				allianceModString = "alliances are carefully thought out";
			}else{
				allianceModString = "alliances are long and arduous processes";
			}
			
			text(allianceModString, leftColX, firstRowY);
			assumptions[0] = allianceModString;
			
			String tradeModString = "";
			if(tradeModifier == 0.5f){
				tradeModString = "trade is a natural tendency";
			}else if(tradeModifier == 1.0f){
				tradeModString = "trade requires trust and time";
			}else{
				tradeModString = "trade isn't a given of human interactions";
			}
			text(tradeModString, leftColX, secondRowY);
			assumptions[1] = tradeModString;
			
			String warModString = "";
			if(warModifier == 0.5f){
				warModString = "war should never happen";//you need to have a certain amount of resources to fight and win
			}else if(warModifier == 1.0f){
				warModString = "war has to be considered";
			}else{
				warModString = "war is always an option";
			}
			text(warModString, rightColX, secondRowY);
			assumptions[2] = warModString;
			
			String nationGoalString = "";
			if(nationGoal == 0.0f){
				nationGoalString = "nations strive to\nself-preserve";
				storyTitle3 = "survival";
			}else if(nationGoal == 1.0f){
				nationGoalString = "nations strive to\nmaintain their honor";
				storyTitle3 = "honor";
			}else{
				nationGoalString = "nations strive to\naccumulate wealth";
				storyTitle3 = "wealth";
			}
			text(nationGoalString, fishTankPos.x, buttonGoalHeight-bufferGoalHeight);
			assumptions[6] = nationGoalString;
			
			String victoryBehaviourString = "";
			if(victoryBehaviour == 1.5f){
				victoryBehaviourString = "victory leads to peace";
			}else if(victoryBehaviour == 1.0f){
				victoryBehaviourString = "victory leads to compromises";
			}else{
				victoryBehaviourString = "victory leads to violence";
			}
			text("victory leads to violence", rightColX, firstRowY);
			assumptions[3] = victoryBehaviourString;
			
			String tradeLimitString= "";
			if(tradeLimit == 0.5f){
				tradeLimitString = "trade is limited to the minimum";
			}else if(tradeLimit == 1.0f){
				tradeLimitString = "trade can grow profit";
			}else{
				tradeLimitString = "trade is not bound";
			}
			text(tradeLimitString, leftColX, thirdRowY);
			assumptions[4] = tradeLimitString;
			
			String allianceTrustString = "";
			if(allianceTrust == 0.5f){
				allianceTrustString = "alliances are fleeting agreements";
			}else if(allianceTrust == 1.0f){
				allianceTrustString = "alliances are to be respected";
			}else{
				allianceTrustString = "alliances are promises";
			}
			text(allianceTrustString, rightColX, thirdRowY);
			assumptions[5] = allianceTrustString;
		}else{
			if(allianceModifier == 0.5f){
				allianceModString = "les alliances sont faciles";
			}else if(allianceModifier == 1.0f){
				allianceModString = "les alliances sont d\u00E9lib\u00E9r\u00E9es";
			}else{
				allianceModString = "les alliances sont le fruit de longues tractations";
			}
			
			text(allianceModString, leftColX, firstRowY);
			assumptions[0] = allianceModString;
			
			String tradeModString = "";
			if(tradeModifier == 0.5f){
				tradeModString = "le commerce est naturel";
			}else if(tradeModifier == 1.0f){
				tradeModString = "le commerce demande de la confiance";
			}else{
				tradeModString = "le commerce se tient \u00E0 l'oppos\u00E9 du naturel";
			}
			text(tradeModString, leftColX, secondRowY);
			assumptions[1] = tradeModString;
			
			String warModString = "";
			if(warModifier == 0.5f){
				warModString = "la guerre ne devrait pas \u00EAtre";//you need to have a certain amount of resources to fight and win
			}else if(warModifier == 1.0f){
				warModString = "la guerre est une possibilit\u00E9";
			}else{
				warModString = "la guerre est toujours une option";
			}
			text(warModString, rightColX, secondRowY);
			assumptions[2] = warModString;
			
			String nationGoalString = "";
			if(nationGoal == 0.0f){
				nationGoalString = "les nations d\u00E9sirent\nvivre et survivre";
				storyTitle3 = "la survie";
			}else if(nationGoal == 1.0f){
				nationGoalString = "les nations d\u00E9sirent\nvivre et honorer";
				storyTitle3 = "l'honneur";
			}else{
				nationGoalString = "les nations d\u00E9sirent\nvivre et prosp\u00E9rer";
				storyTitle3 = "la richesse";
			}
			text(nationGoalString, width*0.5f, buttonGoalHeight-bufferGoalHeight);
			assumptions[6] = nationGoalString;
			
			String victoryBehaviourString = "";
			if(victoryBehaviour == 1.5f){
				victoryBehaviourString = "toute victoire m\u00E8ne \u00E0 la paix";
			}else if(victoryBehaviour == 1.0f){
				victoryBehaviourString = "toute victoire m\u00E8ne \u00E0 des compromis";
			}else{
				victoryBehaviourString = "toute victoire am\u00E8ne une vengeance";
			}
			text(victoryBehaviourString, rightColX, firstRowY);
			assumptions[3] = victoryBehaviourString;
			
			String tradeLimitString= "";
			if(tradeLimit == 0.5f){
				tradeLimitString = "tout commerce sera limit\u00E9";
			}else if(tradeLimit == 1.0f){
				tradeLimitString = "tout commerce sera durable";
			}else{
				tradeLimitString = "tout commerce sera profitable";
			}
			text(tradeLimitString, leftColX, thirdRowY);
			assumptions[4] = tradeLimitString;
			
			String allianceTrustString = "";
			if(allianceTrust == 0.5f){
				allianceTrustString = "toute alliance sera \u00E9ph\u00E9m\u00E8re";
			}else if(allianceTrust == 1.0f){
				allianceTrustString = "toute alliance sera consid\u00E9r\u00E9e";
			}else{
				allianceTrustString = "toute alliance sera respect\u00E9e";
			}
			text(allianceTrustString, rightColX, thirdRowY);
			assumptions[5] = allianceTrustString;
		}
		
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
		
		if(millis()-rotateStartTime > rotateTimer){
			if(rotatePos < 2){
				rotatePos++;
			}else{
				rotatePos = 0;
			}
			rotateStartTime = millis();
			
			if(!hasClicked){
				if(rotatePos == 0){
					canShowAlliances = true;
					canShowWar = false;
					canShowTrade = false;
				}else if(rotatePos == 1){
					canShowAlliances = false;
					canShowWar = true;
					canShowTrade = false;
				}else{
					canShowAlliances = false;
					canShowWar = false;
					canShowTrade = true;
				}
			}
		}
		
		showLegend();
	}
	
	void inGame3() {
		background(255);
		if(!fading && !isPaused) update3();
		fill(bgColBox, 50);
		bgColBox = lerpColor(bgColBox, newBgColBox, bgColBoxLerpSpeed);
		bgColBoxLerpSpeed += bgColBoxLerpInc;
		bgColBoxLerpSpeed = map((millis()-Seasons.startTime), 0, 6000.0f, 0, 1);
		stroke(0);
		strokeWeight(1);
		drawVoronoi(3);
		seasons.cycle();
		//seasons.populate(Seasons.numberOfSeasons);

		if(language == "english"){
			loadingText = "fetching the story of their lives";	
		}else{
			loadingText = "r\u00E9daction de leurs vies";
		}
		
		for(int i = 0; i < resources3.size(); i++){
			Resource r = resources3.get(i);
			r.display();
			r.deplete();
		}
		
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
		if(millis() - startTimeWar > timerWar){
			for(int i = 0; i < wars.size(); i++){
				War w = wars.get(i);
				w.display();
			}	
		}
		
		onHover(3);
		
		fill(255);
		rectMode(CORNER);
		noStroke();
		rect(0, rectBorderH, width, 300);
		
		float choiceY = height - height*0.02f;
		textSize(textFontSize);
		textFont(textFont);
		textAlign(LEFT);
		fill(0, interactionAlpha);
		stroke(0);
		strokeWeight(2);
		
		if (selWealthInc) fill(0, 150+selPulse);
		if(language == "english"){
			text("increase wealth", rectBorderX, choiceY);
		}else{
			text("augmenter la richesse", rectBorderX, choiceY);
		}
		fill(0, interactionAlpha);
		
		textAlign(CENTER);
		
		if (selWealthDec) fill(0, 150+selPulse);
		
		if(language == "english"){
			text("decrease wealth", width*0.25f, choiceY);
		}else{
			text("diminuer la richesse", width*0.25f, choiceY);
		}
		fill(0, interactionAlpha);
		
		if (selWarInduce) fill(0, 150+selPulse);
		if(language == "english"){
			text("induce aggressivity", width*0.5f, choiceY);
		}else{
			text("inciter \u00E0 la violence", width*0.5f, choiceY);
		}
		fill(0, interactionAlpha);
		
		if (selAllianceBreak) fill(0, 150+selPulse);
		if(language == "english"){
			text("break alliances", width*0.75f, choiceY);
		}else{
			text("rompre une alliance", width*0.75f, choiceY);
		}
		fill(0, interactionAlpha);
		
		textAlign(RIGHT);
		
		if (selFinalProceed && selectedNation != null){
			if(rectProceedAlpha < 200) rectProceedAlpha += 10.0f;
			fill(0, 150+selPulse);
			if(language == "english"){
				text("press space", rectBorderW+rectBorderX, choiceY);
			}else{
				text("appuyew sur espace", rectBorderW+rectBorderX, choiceY);
			}
		}else{
			if(rectProceedAlpha > 0) rectProceedAlpha -= 10.0f;
			fill(0, interactionAlpha);
			if(language == "english"){
				text("advance further", rectBorderW+rectBorderX, choiceY);
			}else{
				text("avancer", rectBorderW+rectBorderX, choiceY);
			}
		}
		
		rectMode(CORNER);
		textFont(thoughtFont);
		textSize(thoughtFontSize);
		
		if(showWealthIncInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(LEFT);
			if(language == "english"){
				text("click on a nation to increase their wealth.", rectBorderX*2.0f, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("cliquez sur une nation pour l'enrichir.", rectBorderX*2.0f, rectBorderY+(rectBorderH*0.99f));
			}
		}
		
		
		if(showWealthDecInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(CENTER);
			if(language == "english"){
				text("click on a nation to decrease their wealth.", width*0.25f, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("cliquez sur une nation pour l'appauvrir.", width*0.25f, rectBorderY+(rectBorderH*0.99f));
			}
		}
		
		if(showWarInduceInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(CENTER);
			if(language == "english"){
				text("click on a nation to make it more prone to enter war.", width*0.5f, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("cliquez sur une nation pour l'inciter \u00E0 la haine.", width*0.5f, rectBorderY+(rectBorderH*0.99f));
			}
		}
		
		if(showAllianceBreakInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(CENTER);
			if(language == "english"){
				text("click on a nation to break its alliances.", width*0.75f, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("cliquez sur une nation pour rompre ses alliances.", width*0.75f, rectBorderY+(rectBorderH*0.99f));
			}
		}
		
		if(showFinalProceedInfo){
			strokeWeight(1);
			stroke(0);
			fill(255, 150);
			rect(rectBorderX+1, rectBorderY+(rectBorderH*0.97f), rectBorderW, rectBorderH*0.03f);
			fill(0, 200);
			textAlign(RIGHT);
			if(language == "english"){
				text("choose a nation for their history", rectBorderW, rectBorderY+(rectBorderH*0.99f));
			}else{
				text("choisissez une nation pour son histoire", rectBorderW, rectBorderY+(rectBorderH*0.99f));
			}
		}
		
		textFont(textFont);
		textSize(textFontSize);
		fill(0);
		
		death(3);
		
		showAssumptions();
		textFont(textFont);
		textSize(textFontSize);
		resetButton.display();
		resetButton.onClick();
		showAssumptions.display();
		showAssumptions.onClick();
		
		noStroke();
		fill(255, rectFadeAlpha);
		
		if(rectFadeAlpha > 0 && selectedNation == null){
			rectMode(CORNER);
			//removing all the "-2px" and "+1px" to account for the stokeWeight
			rectFadeTopH = (nation.pos.y + rectBorderY) - rectFadeBuffer*2.5f;
			rectFadeTopW = rectBorderW-2;
				
			rectFadeRightW = -(rectBorderW - nation.pos.x) + rectFadeBuffer*2.5f;

			rectFadeRightH = rectBorderH-2;
			
			rectFadeLeftW = (nation.pos.x + rectBorderX) - rectFadeBuffer*2.5f;
			rectFadeLeftH = rectBorderH-2;

			rectFadeBottomH = -(rectBorderH - nation.pos.y) + rectFadeBuffer*2.5f;
			rectFadeBottomW = rectBorderW-2;
			
			rect(rectFadeTopPos.x+1, rectFadeTopPos.y, rectFadeTopW, rectFadeTopH);
			rect(rectFadeRightPos.x, rectFadeRightPos.y, rectFadeRightW, rectFadeRightH);
			rect(rectFadeLeftPos.x+1, rectFadeLeftPos.y, rectFadeLeftW, rectFadeLeftH);
			rect(rectFadeBottomPos.x+1, rectFadeBottomPos.y, rectFadeBottomW, rectFadeBottomH);
			
			rectFadeAlpha -= rectFadeAlphaInc*2.0f;
			if(rectFadeBuffer < rectFadeBufferMax) rectFadeBuffer += 0.25f;
			
		}else if(selectedNation != null){
			rectMode(CORNER);
			rectFadeTopH = (selectedNation.pos.y + rectBorderY) - rectFadeBuffer*2.0f;
			rectFadeTopW = rectBorderW-2;
			rectFadeRightW = -(rectBorderW - selectedNation.pos.x) + rectFadeBuffer*2.0f;
			rectFadeRightH = rectBorderH-2;
			rectFadeLeftW = (selectedNation.pos.x + rectBorderX) - rectFadeBuffer*2.0f;
			rectFadeLeftH = rectBorderH-2;
			rectFadeBottomH = -(rectBorderH - selectedNation.pos.y) + rectFadeBuffer*2.0f;
			rectFadeBottomW = rectBorderW-2;
			
			rect(rectFadeTopPos.x+1, rectFadeTopPos.y, rectFadeTopW, rectFadeTopH);
			rect(rectFadeRightPos.x, rectFadeRightPos.y, rectFadeRightW, rectFadeRightH);
			rect(rectFadeLeftPos.x+1, rectFadeLeftPos.y, rectFadeLeftW, rectFadeLeftH);
			rect(rectFadeBottomPos.x+1, rectFadeBottomPos.y, rectFadeBottomW, rectFadeBottomH);
			
			if(fading) rectFadeAlpha += 10.0f;
		}
		
		fill(255, rectProceedAlpha);
		stroke(0, rectProceedAlpha);
		rectMode(CENTER);
		rect(width*0.5f, height*0.5f, rectBorderW-2, height*0.1f);
		fill(0, rectProceedAlpha);
		textFont(headerFont);
		textSize(headerFontSize);
		textAlign(CENTER);
		if(language == "english"){
			text("press space to proceed", width*0.5f, height*0.5f);
		}else{
			text("appuyez sur espace pour continuer", width*0.5f, height*0.5f);
		}
	}
	
	void update3() {
		if(rectBorderHTemp > rectBorderH && !canReset) rectBorderHTemp -= 1.0f;
		if(interactionAlpha < 150 && !canReset) interactionAlpha += interactionAlphaInc;
		hasClicked = false;
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
		
		if(millis() - startTimeWar > timerWar){
			for(int i = 0; i < wars.size(); i++){
				War w = wars.get(i);
				w.update();
				
				if(w.n1.isRuined || w.n2.isRuined){
					w.n1.currentWars.remove(w);
					w.n2.currentWars.remove(w);
					wars.remove(w);
				}
			}
		}
	}

	void endGame3() {
		background(255);
		stroke(0);
		strokeWeight(1);
		noFill();
		for(int i = 1; i < 5; i++){
			rect(rectBorderX+2*i, rectBorderY+2*i, rectBorderW-4*i, rectBorderHTemp-4*i);
		}
		
		canStart = false;
		interactions = 0;
		
		if(language == "english"){
			loadingText = "compiling their past";
		}else{
			loadingText = "compilation de leur pass\u00E9";
		}
		
		fill(0);
		textSize(mainFontSize);
		textFont(mainFont);
		textAlign(CENTER);
		if(language == "english"){
			text("On "+storyTitle3, width*0.5f, height*0.1f);
		}else{
			text("Sur "+storyTitle3, width*0.5f, height*0.1f);
		}
		
		textSize(textFontSize);
		textFont(textFont);
		textAlign(LEFT);
		
		if(randomGen3 == true && selectedNation != null){
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
		
			
			if(nationGoal == 1 && selectedNation.wasAtWar){
				endGame3_text = endGame3_text+" "+st_nationGoalHonor[indexGoalHonor];
			}else if(nationGoal == 1 && !selectedNation.wasAtWar){//0 is the second half of the text file
				endGame3_text = endGame3_text+" "+st_nationGoalHonor[indexGoalHonor0];
			}
			
			if(nationGoal == 0 && selectedNation.isRuined){
				endGame3_text = endGame3_text+" "+st_nationGoalSurvival[indexGoalSurvival];
			}else if(nationGoal == 0 && !selectedNation.isRuined){
				endGame3_text = endGame3_text+" "+st_nationGoalSurvival[indexGoalSurvival0];
			}
			
			if(nationGoal == 2 && selectedNation.totalWealth > endWealthThreshold){
				endGame3_text = endGame3_text+" "+st_nationGoalWealth[indexGoalWealth];
			}else if(nationGoal == 2 && selectedNation.totalWealth < endWealthThreshold){
				endGame3_text = endGame3_text+" "+st_nationGoalWealth[indexGoalWealth0];
			}
			
			if(selectedNation.totalWealth > endWealthThreshold){
				endGame3_text = endGame3_text+" "+st_wealth[indexWealth];
			}else{
				endGame3_text = endGame3_text+" "+st_wealth[indexWealth0];
			}
			
			if(selectedNation.wasAtWar){
				endGame3_text = endGame3_text+" "+st_war[indexWar];
			}else{
				endGame3_text = endGame3_text+" "+st_war[indexWar0];
			}
			
			if(selectedNation.totalTradePartners > 3){
				endGame3_text = endGame3_text+" "+st_tradePartners[indexPartners];
			}else{
				endGame3_text = endGame3_text+" "+st_tradePartners[indexPartners0];
			}
			
			endGame3_text = endGame3_text + "\n"+ "\n";
			
			if(selectedNation.promiscuity){
				endGame3_text = endGame3_text+" "+st_promiscuity[indexPromiscuity];
			}else{
				endGame3_text = endGame3_text+" "+st_promiscuity[indexPromiscuity0];
			}
			
			if(selectedNation.totalAllies > 3){
				endGame3_text = endGame3_text+" "+st_alliances[indexAlliances];
				if(selectedNation.similarCulture()){
					endGame3_text = endGame3_text+" "+st_similarCultureAllies[indexCultureAllies];
				}else{
					endGame3_text = endGame3_text+" "+st_similarCultureAllies[indexCultureAllies0];
				}
				
				endGame3_text = endGame3_text+"\n"+"\n"+"One cannot survive without friends.";
			}else{
				endGame3_text = endGame3_text+" "+st_alliances[indexAlliances0];
					if(selectedNation.similarCulture()){
						endGame3_text = endGame3_text+" "+st_similarCultureAlone[indexCultureAlone];
					}else{
						endGame3_text = endGame3_text+" "+st_similarCultureAlone[indexCultureAlone0];
					}
				}
			
				endGame3_text = endGame3_text+"\n"+"\n"+"Yes, one is better on one's own.";
		}
		
		fill(0);
		text(endGame3_text, width*0.2f, height*0.2f, (width/5)*3, 19*(height/20));
		
		strokeWeight(1);
		stroke(10, 210);
		noFill();
		rect(proceedX, proceedY, proceedW, proceedH);
		textFont(textFont);
		textSize(textFontSize);
		fill(0);
		if(language == "english"){
			text("archive", proceedX*1.015f, proceedY*1.03f);
		}else{
			text("archiver", proceedX*1.015f, proceedY*1.03f);
		}
		
		if(selectedNation != null){
			selectedNation.pos.y = height*0.75f;
			selectedNation.display();
		}
		
		if(mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY + proceedH){
			proceedSW = 2;
		}else{
			proceedSW = 1;
		}
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
	
	public void mouseReleased(){
		if(draggedAgent != null){
			isReleased = true;
		}
	}
	
	public void clickSound(int type){
		if(type == 1){ //UI basic click
			WavePlayer wp2 = new WavePlayer(ac, 261.626f, Buffer.SINE);
			Envelope e = new Envelope(ac, 0.0f);
			Gain g = new Gain(ac, 1, e);
			g.addInput(wp2);
			masterGain.addInput(g);
			e.addSegment(1.0f, 20.0f);//a
			e.addSegment(0.1f, 40.0f);//r
			e.addSegment(0.0f, 700.0f, new KillTrigger(g));
			
			if(startGame1 || startGame2 || startGame3){
				fishTankAlpha = 250;
				fishTankExpand = 0;
			}
		}else if(type == 2){ //UI important click
			
			WavePlayer wp1 = new WavePlayer(ac, 523.251f, Buffer.SINE);
			Envelope e1 = new Envelope(ac, 0.0f);
			Gain g1 = new Gain(ac, 1, e1);
			//g1.addInput(wp1);
			masterGain.addInput(g1);
			
			e1.addSegment(0.2f, 10.0f);//a
			e1.addSegment(0.1f, 50.0f);//d
			e1.addSegment(0.05f, 100.0f);//s
			e1.addSegment(0.0f, 700.0f, new KillTrigger(g1));//r
			
			WavePlayer wp2 = new WavePlayer(ac, 659.255f, Buffer.SINE);
			Envelope e2 = new Envelope(ac, 0.0f);
			Gain g2 = new Gain(ac, 1, e2);
			g2.addInput(wp2);
			masterGain.addInput(g2);
			
			e2.addSegment(0.4f, 10.0f);//a
			e2.addSegment(0.3f, 50.0f);//d
			e2.addSegment(0.15f, 70.0f);//s
			e2.addSegment(0.0f, 1000.0f, new KillTrigger(g2));//r
		}else if(type == 3){ //inGame interaction click
			WavePlayer wp2 = new WavePlayer(ac, 261.626f, Buffer.TRIANGLE);
			Envelope e = new Envelope(ac, 0.0f);
			Gain g = new Gain(ac, 1, e);
			g.addInput(wp2);
			masterGain.addInput(g);
			e.addSegment(1.0f, 20.0f);//a
			e.addSegment(0.1f, 40.0f);//s
			e.addSegment(0.0f, 700.0f, new KillTrigger(g));
		}else if(type == 4){ //inGame select final click
			int numHarmonics = 8;
		    WavePlayer[]  wp = new WavePlayer[numHarmonics];
		    Envelope[] e = new Envelope[numHarmonics];
		    Gain []g = new Gain[numHarmonics];
		    Gain gSelect = new Gain(PolSys.ac, numHarmonics, 0.15f);
		    
		    for(int i = 0; i < numHarmonics; i++){
		    	if(i % 2 == 0){
		    		wp[i] = new WavePlayer(PolSys.ac,  523.251f, Buffer.SINE);
		    	}else{
		    		wp[i] = new WavePlayer(PolSys.ac, 659.255f, Buffer.SINE);
		    	}
		    	e[i] = new Envelope(PolSys.ac, 0.0f);
		    	g[i] = new Gain(PolSys.ac, 1, e[i]);
		    	g[i].addInput(wp[i]);
		    	gSelect.addInput(g[i]);
		    	e[i].addSegment(random(0.2f, 0.3f), 5.0f);
		    	e[i].addSegment(0.0f, random(1500.0f, 2000.0f), new KillTrigger(g[i]));
		    }
		    
		    masterGain.addInput(gSelect);
		}
	}
		
	public void mousePressed() {
		//scale(scaleW, scaleH);
		translate(translateBufferX, translateBufferY);
		
		if(splash){
			splash = false;
			statement = true;
		}
		
		if(statement){
			if(statementPage0.onClick()){
				clickSound(1);
				statementPage = 0;
				statementLerpVal = 0;
				statementTargetPos = statementPage0.pos;
			}else if(statementPage1.onClick()){
				clickSound(1);
				statementPage = 1;
				statementLerpVal = 0;
				statementTargetPos = statementPage1.pos;
			}else if(statementPage2.onClick()){
				clickSound(1);
				statementPage = 2;
				statementLerpVal = 0;
				statementTargetPos = statementPage2.pos;
			}
			
			if(englishButton.onClick()){
				clickSound(1);
				language = "english";
			}
			if(frenchButton.onClick()){
				clickSound(1);
				language = "francais";
			}
		}
		
		if(resetButton.onClick() && canShowAssumptions){
			canReset = true;
			
			//fade sound
			if(seasons != null) eMasterSeasons.addSegment(0.00f, 2000.0f);
			
			if(inGame1){
				resetStage = 1;
			}else if(inGame2){
				resetStage = 2;
			}else{
				resetStage = 3;
			}
		}
		
		if(endGame1){
			if(mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY + proceedH){
				fading = true;
				clickSound(2);
			}
		}
		
		if(endGame2){
			if(mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY + proceedH){
				fading = true;
				clickSound(2);
			}
		}
		
		if(endGame3){
			if(mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY + proceedH){
				fading = true;
				clickSound(2);
			}
		}
		
		if(emailScreen){
			if(mouseX > proceedX && mouseX < proceedX + proceedW && mouseY > proceedY && mouseY < proceedY*1.02f + proceedH && typedEmail){
				clickSound(2);
				emailAddress = typedText;
			    emailAddress.replaceAll("\\p{Z}", "");
			    emailAddress.replaceAll("\uFFFD", "");
			    emailAddress.replaceAll("\uFFFF", "");
			    int arobase = emailAddress.indexOf('@');
			    if(arobase > 0 && arobase < emailAddress.length()){
			    	errorMessageMail = "";
				    String local = emailAddress.substring(0, arobase);
				    println("local: "+local);
				    String host = emailAddress.substring(arobase+1, emailAddress.length());
				    println("host: "+host);
				    emailAddress = local + "@" + host;
				    emailStory = "i have been through a lot."+"\n\n\n\n"+"\n\n"+storyTitle1+"\n\n"+endGame1_text+"\n\n"+storyTitle2+"\n\n"+endGame2_text+"\n\n"+storyTitle3+"\n\n"+endGame3_text + "\n\n\n\nthank you.\n\n\n\nlearn more at www.socialcontact.cc";
				    thread("sendEmail");
				    fading = true;
			    }else{
			    	errorMessageMail = "please enter a valid email address";
			    }
			}
		}
		if (startGame1) {
		
			if(mouseX > showLegendPos.x - legendW && mouseX < showLegendPos.x + legendW && mouseY > showLegendPos.y -legendH && mouseY < showLegendPos.y + legendH){
				canShowLegend = !canShowLegend;
				clickSound(1);
			}
			

			if(connectionsButton.onClick()){
				clickSound(1);
				drawFishConnec = !drawFishConnec;
			}
			
			if(resourceButton.onClick()){
				clickSound(1);
				drawFishResources = !drawFishResources;
			}
			
			if(predatorButton.onClick()){
				clickSound(1);
				drawFishPredators = !drawFishPredators;
			}
			
			if (stayCloseUpButton.onClick()) {
				clickSound(1);
				if(formingStableRelationships < 1.5f){
					formingStableRelationships += 0.5f;
					for(int i = 0; i < fishes.size(); i++){
						Fish f = fishes.get(i);
						f.acceleration = new PVector(random(-4f, 4f), random(-4f, 4f));
					}
				}else{
					formingStableRelationships = 0.5f;
					
				}
			}
			
			if (stayCloseDownButton.onClick()) {
				clickSound(1);
				if(formingStableRelationships > 0.5f){
					formingStableRelationships -= 0.5f;
					for(int i = 0; i < fishes.size(); i++){
						Fish f = fishes.get(i);
						f.acceleration = new PVector(random(-0.5f, 0.5f), random(-0.5f, 0.5f));
					}
				}else{
					formingStableRelationships = 1.5f;
				}
			}
			
			if (meetingOthersUpButton.onClick()) {
				clickSound(1);
				if(meetingOthers < 1.5f){
					meetingOthers += 0.5f;
				}else{
					meetingOthers = 0.5f;
				}
			}
			
			if (meetingOthersDownButton.onClick()) {
				clickSound(1);
				if(meetingOthers > 0.5f){
					meetingOthers -= 0.5f;
				}else{
					meetingOthers = 1.5f;
				}
			}
			
			if (rememberDeadUpButton.onClick()) {
				clickSound(1);
				if(rememberDead < 1.5f){
					rememberDead += 0.5f;
				}else{
					rememberDead = 0.5f;
				}
			}
			
			if (rememberDeadDownButton.onClick()) {
				clickSound(1);
				if(rememberDead > 0.5f){
					rememberDead -= 0.5f;
				}else{
					rememberDead = 1.5f;
				}
			}

			if (connectionsUpButton.onClick()){
				clickSound(1);
				hasClicked = true;
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
			if (connectionsDownButton.onClick()){
				clickSound(1);
				hasClicked = true;
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

			if (violenceUpButton.onClick()) {
				clickSound(1);
				hasClicked = true;
				if(violence < 1.0f){
					violence += 0.5f;
				}else{
					violence = 0.0f;
				}
			}
			if (violenceDownButton.onClick()) {
				clickSound(1);
				hasClicked = true;
				if(violence > 0.0f){
					violence -= 0.5f;
				}else{
					violence = 1.0f;
				}
			}
			
			if(resourceUpButton.onClick()){
				clickSound(1);
				hasClicked = true;
				canShowResources = true;
				if(resourceSeek < 1.5f){
					resourceSeek += 0.5f;
				}else{
					resourceSeek = 0.5f;
				}
			}
			
			if(resourceDownButton.onClick()){
				clickSound(1);
				hasClicked = true;
				canShowResources = true;
				if(resourceSeek > 0.5f){
					resourceSeek -= 0.5f;
				}else{
					resourceSeek = 1.5f;
				}
			}

			if (canStart && startButton.onClick()) { // when you click start, a bunch of things happen
				fading = true;
				clickSound(2);
			}

			if (pursuitRightButton.onClick()){
				clickSound(1);
				goalInt++;
			}
			
			if(pursuitLeftButton.onClick()){
				clickSound(1);
				goalInt--;
			}
				
		}
		
		if(startGame2){
			if(mouseX > showLegendPos.x  - legendW && mouseX < showLegendPos.x + legendW && mouseY > showLegendPos.y - legendH && mouseY < showLegendPos.y + legendH){
				canShowLegend = !canShowLegend;
				clickSound(1);
			}
			
			if(powerButton.onClick()){
				drawFishPower = !drawFishPower;
				clickSound(1);
			}
			
			if(friendshipButton.onClick()){
				drawFishFriend = !drawFishFriend;
				clickSound(1);
			}
			
			if(loveButton.onClick()){
				drawFishLove = !drawFishLove;
				clickSound(1);
			}
			
			if(canStart && startButton2.onClick()){
				fading = true;
				clickSound(2);
			}
			
			if(powerForceUpButton.onClick()){
				hasClicked = true;
				clickSound(1);
				if(powerForce < 7.0f){
					powerForce += 2.0f;
				}else if(powerForce == 7.0f){
					powerForce = 1.0f;
				}
			}
			if(powerForceDownButton.onClick()){
				hasClicked = true;
				clickSound(1);
				if(powerForce > 1.0f){
					powerForce -= 2.0f;
				}else if(powerForce == 1.0f){
					powerForce = 7.0f;
				}
			}
			
			if(friendshipForceUpButton.onClick()){
				hasClicked = true;
				clickSound(1);
				if(friendshipForce < 7.0f){
					friendshipForce += 2.0f;
				}else if(friendshipForce == 7.0f){
					friendshipForce = 1.0f;
				}
			}
			if(friendshipForceDownButton.onClick()){
				hasClicked = true;
				clickSound(1);
				if(friendshipForce > 1.0f){
					friendshipForce -= 2.0f;
				}else if(friendshipForce == 1.0f){
					friendshipForce = 7.0f;
				}
			}
			
			if(loveForceUpButton.onClick()){
				hasClicked = true;
				clickSound(1);
				if(loveForce < 60.0f){
					loveForce += 5.0f;
				}else{
					loveForce = 40.0f;
				}
			}
			if(loveForceDownButton.onClick()){
				hasClicked = true;
				clickSound(1);
				if(loveForce > 40.0f){
					loveForce -= 5.0f;
				}else{
					loveForce = 60.0f;
				}
			}
			
			if(ageMajorityUpButton.onClick()){
				clickSound(1);
				if(ageModifier < 0.30f){
					ageModifier += 0.05f;
				}else{
					ageModifier = 0.20f;
				}
			}
			if(ageMajorityDownButton.onClick()){
				clickSound(1);
				if(ageModifier > 0.20f){
					ageModifier -= 0.05f;
				}else{
					ageModifier = 0.30f;
				}
			}
			
			if(numberOfChildrenUpButton.onClick()){
				clickSound(1);
				if(numberOfChildren < 5.0f){
					numberOfChildren += 1.0f;
				}else{
					numberOfChildren = (int) 0.0f;
				}
			}
			if(numberOfChildrenDownButton.onClick()){
				clickSound(1);
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
				clickSound(1);
				if(independenceOfChildren < 2.0f){
					independenceOfChildren += 1.0f;
				}else{
					independenceOfChildren = 0.0f;
				}
			}
			if(independenceOfChildrenDownButton.onClick()){
				clickSound(1);
				if(independenceOfChildren > 0.0f){
					independenceOfChildren -= 1.0f;
				}else{
					independenceOfChildren = 3.0f;
				}
			}
			
			if(cultureButtonRight.onClick()){
				clickSound(1);
				if(cultureOrigin == 1){
					cultureOrigin = 2;
					spatialCultureIncrement = 0.1f;
				}else if(cultureOrigin == 2){
					cultureOrigin = 0;
					spatialCultureIncrement = 0.2f;
				}else{
					cultureOrigin = 1;
					spatialCultureIncrement = 0.3f;
					
				}
			}
			
			if(cultureButtonLeft.onClick()){
				clickSound(1);
				if(cultureOrigin == 1){
					cultureOrigin = 0;
					spatialCultureIncrement = 0.2f;
				}else if(cultureOrigin == 2){
					cultureOrigin = 1;
					spatialCultureIncrement = 0.3f;
				}else{
					cultureOrigin = 2;
					spatialCultureIncrement = 0.1f;
				}
			}
		}
		
		if(startGame3){
			hasClicked = true;
		
			if(mouseX > showLegendPos.x - legendW && mouseX < showLegendPos.x + legendW && mouseY > showLegendPos.y - legendH && mouseY < showLegendPos.y + legendH){
				canShowLegend = !canShowLegend;
				clickSound(1);
			}
			
			if(alliancesButton.onClick()){
				canShowAlliances = !canShowAlliances;
				clickSound(1);
			}
			
			if(warButton.onClick()){
				canShowWar = !canShowWar;
				clickSound(1);
			}
			
			if(tradeButton.onClick()){
				canShowTrade = !canShowTrade;
				clickSound(1);
			}
			
			if(allianceModifierUp.onClick()){
				clickSound(1);
				if(allianceModifier < 1.5f){
					allianceModifier += 0.5;
				}else{
					allianceModifier = 0.5f;
				}
			}
			
			if(allianceModifierDown.onClick()){
				clickSound(1);
				if(allianceModifier > 0.5f){
					allianceModifier -= 0.5f;
				}else{
					allianceModifier = 1.5f;
				}
			}
			
			if(tradeModifierUp.onClick()){
				clickSound(1);
				if(tradeModifier < 1.5f){
					tradeModifier += 0.5;
				}else{
					tradeModifier = 0.5f;
				}
			}
			
			if(tradeModifierDown.onClick()){
				clickSound(1);
				if(tradeModifier > 0.5f){
					tradeModifier -= 0.5f;
				}else{
					tradeModifier = 1.5f;
				}
			}
			
			if(warModifierUp.onClick()){
				clickSound(1);
				if(warModifier < 1.5f){
					warModifier += 0.5;
				}else{
					warModifier = 0.5f;
				}
			}
			
			if(warModifierDown.onClick()){
				clickSound(1);
				if(warModifier > 0.5f){
					warModifier -= 0.5f;
				}else{
					warModifier = 1.5f;
				}
			}
			
			
			if(victoryBehaviourUp.onClick()){
				clickSound(1);
				if(victoryBehaviour < 1.5f){
					victoryBehaviour += 0.5f;
				}else{
					victoryBehaviour = 0.5f;
				}
			}
			
			if(victoryBehaviourDown.onClick()){
				clickSound(1);
				if(victoryBehaviour > 0.5f){
					victoryBehaviour -= 0.5f;
				}else{
					victoryBehaviour = 1.5f;
				}
			}
			
			if(tradeLimitUp.onClick()){
				clickSound(1);
				if(tradeLimit < 1.5f){
					tradeLimit += 0.5f;
				}else{
					tradeLimit = 0.5f;
				}
			}
			
			if(tradeLimitDown.onClick()){
				clickSound(1);
				if(tradeLimit > 0.5f){
					tradeLimit -= 0.5f;
				}else{
					tradeLimit = 1.5f;
				}
			}
			
			if(allianceTrustUp.onClick()){
				clickSound(1);
				if(allianceTrust < 1.5f){
					allianceTrust += 0.5f;
				}else{
					allianceTrust = 0.5f;
				}
			}
			
			if(allianceTrustDown.onClick()){
				clickSound(1);
				if(allianceTrust > 0.5f){
					allianceTrust -= 0.5f;
				}else{
					allianceTrust = 1.5f;
				}
			}
			
			if(nationGoalButtonRight.onClick()){
				clickSound(1);
				if(nationGoal < 2){
					nationGoal++;
				}else{
					nationGoal = 0;
				}
			}
			
			if(nationGoalButtonLeft.onClick()){
				clickSound(1);
				if(nationGoal > 0){
					nationGoal--;
				}else{
					nationGoal = 2;
				}
			}

			if(canStart && startButton3.onClick()){
				clickSound(2);
				fading = true;
			}
		}

		if (inGame1 && !fading) {
			float choiceY = height - height*0.02f;
			float bufferX = 70;
			float bufferY = 40;
			if(mouseY < height - height/40 + bufferY && mouseY > choiceY - bufferY){ //if you're at the bottom of the screen
				if(mouseX > rectBorderX  && mouseX < width*0.17f){ //first interaction generate predators
					selGenPred = true;
					selStopAgent = false;
					selRemoveConnec = false;
					selFinalCluster = false;
					showGenPredInfo = true;
					showStopAgentInfo = false;
					showRemoveConnectionInfo = false;
					showSelFinalClusterInfo = false;
					clickSound(1);
				}
				
				if(mouseX > width*0.17f - bufferX  && mouseX < width*0.5f){ //second interaction stop agent
					selGenPred = false;
					selStopAgent = false;
					selRemoveConnec = true;
					selFinalCluster = false;
					showGenPredInfo = false;
					showStopAgentInfo = true;
					showRemoveConnectionInfo = false;
					showSelFinalClusterInfo = false;
					clickSound(1);
				}
				
				if(mouseX > width*0.5f  && mouseX < width*0.82f){ //third interaction remove connec
					selGenPred = false;
					selStopAgent = true;
					selRemoveConnec = false;
					selFinalCluster = false;
					showGenPredInfo = false;
					showStopAgentInfo = false;
					showRemoveConnectionInfo = true;
					showSelFinalClusterInfo = false;
					clickSound(1);
				}
				
				if(mouseX > width*0.82f  && mouseX < width-rectBorderX){ //select cluster
					selGenPred = false;
					selStopAgent = false;
					selRemoveConnec = false;
					selFinalCluster = true;
					showGenPredInfo = false;
					showStopAgentInfo = false;
					showRemoveConnectionInfo = false;
					showSelFinalClusterInfo = true;
					clickSound(1);
				}
			}

			if (selGenPred && mouseY < height - height/40 - bufferY) {
				predators.add(new Predator(mouseX, mouseY, 10, -2, 2, random(30, 35) * 1000, random(2, 3) * 10, this));
				genPred--;
				clickSound(3);
			}

			for (int i = 0; i < agents.length; i++) {
				
				if (mouseX < agents[i].pos.x + rad && mouseX > agents[i].pos.x - rad && mouseY < agents[i].pos.y + rad && mouseY > agents[i].pos.y - rad) {
					
					if (selStopAgent) { //stopping agent
						agents[i].isStopped = true;
						stopAgent--;
						clickSound(3);
					}

					if (selRemoveConnec) { //removing connections
						clickSound(3);
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
						clickSound(4);
						selectedAgent = agents[i];
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
			
			if(showAssumptions.onClick()){
				clickSound(1);
				canShowAssumptions = !canShowAssumptions;
			}
		}
		
		if (inGame2 && !fading) {
			float choiceY = height - height*0.02f;
			float bufferX = 70;
			float bufferY = 20;
			if(mouseY < height - height/40 + bufferY && mouseY > choiceY - bufferY){ //if you're at the bottom of the screen
				if(mouseX > rectBorderX  && mouseX < width*0.12f){ //first interaction generate predators
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
					clickSound(1);
				}
				
				if(mouseX > width*0.12f  && mouseX < width*0.375){ //second interaction stop agent
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
					clickSound(1);
				}
				
				if(mouseX > width*0.375f  && mouseX < width*0.675f){ //third interaction remove connec
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
					clickSound(1);
				}
				
				if(mouseX > width*0.675f  && mouseX < width*0.85f){ //select cluster
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
					clickSound(1);
				}
				
				if(mouseX > width*0.85f  && mouseX < width-rectBorderX){ //select cluster
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
					clickSound(1);
				}
			}else{
				for(int i = 0; i < community.size(); i++){
					Agent a = community.get(i);
					a.isSelected2 = false;
					if(dist(mouseX, mouseY, a.pos.x, a.pos.y) < a.rad*0.9f && a.isAlive){
						if(!finalStructure) clickSound(3);
						if(selRevolt){ // --------------------------------------select revolt!
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
						
						if(selOppression){ //--------------------------------------select oppression
							//go through all the power relationships
							for(int j = 0; j < a.agentsPower.size(); j++){
								
								Agent other = a.agentsPower.get(j);
								//for anyone less powerful, apply force on the agent
								if(a.culture[1] > other.culture[1]){
									PVector f = PVector.sub(other.pos, a.pos);
									f.normalize();
									
									f.mult(revoltMultiplier*2.0f);
									//push them away
									other.applySpecialForce(f);
									//remove their culture
									other.culture[1] = 0.0f;
								}
								a.hasOppressed = true;
							}
							induceOppression--;
						}
						
						if(forceCulturalDiff){ //--------------------------------------induce cultural diff
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
						
						if(forceCulturalSim){ //--------------------------------------induce cultural sim
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
								averageCulture[k] = sumcult[k]/(a.agentsFriendship.size()+1);
								
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
								clickSound(4);
							}
							
							canEndGame2 = true;
						}
					}else if(selectedAgent2 != null && dist(mouseX, mouseY, selectedAgent2.pos.x, selectedAgent2.pos.y) > selectedAgent2.rad*1.1f && finalStructure){
						selectedAgent2 = null;
					}
				}
			}
			
			if(showAssumptions.onClick()){
				clickSound(1);
				canShowAssumptions = !canShowAssumptions;
			}
		}
		
		if(inGame3 && !fading){
			float choiceY = height - height*0.02f;
			float bufferX = 70;
			float bufferY = 20;
			if(mouseY < height - height/40 + bufferY && mouseY > choiceY - bufferY){ //if you're at the bottom of the screen
				if(mouseX > rectBorderX  && mouseX < width*0.12f){ //first interaction 
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
					clickSound(1);
				}
				
				if(mouseX > width*0.12f  && mouseX < width*0.36f){ //second interaction 
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
					clickSound(1);
				}
				
				if(mouseX > width*0.36f && mouseX < width*0.63f){ //third interaction
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
					clickSound(1);
				}
				
				if(mouseX > width*0.63f && mouseX < width*0.87f){ //4
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
					clickSound(1);
				}
				
				if(mouseX > width*0.87f && mouseX < width-rectBorderX){ //final
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
					clickSound(1);
				}
			}

			for(int i = 0; i < others.size(); i++){
				Nation n = others.get(i);
				if(dist(mouseX, mouseY, n.pos.x, n.pos.y) < n.rad){
					if(!selFinalProceed) clickSound(3);
					if(selWealthInc){
						n.totalWealth *= wealthIncrease;
						n.culturalHomogeneity += 0.5f;
					}
					
					if(selWealthDec && n.totalWealth > n.povertyThreshold){
						n.totalWealth *= wealthDecrease;
						n.culturalHomogeneity -= 0.5f;
					}
					
					if(selWarInduce){
						n.culturalRequirementWar *= 0.5f;
						n.culturalHomogeneity += random(-0.25f, 0.25f);
					}
					
					if(selAllianceBreak){
						for(int j = 0; j < n.currentAlliances.size(); j++){
							Alliance a = n.currentAlliances.get(j);
							
							a.n2.currentAlliances.remove(a);
							a.n1.currentAlliances.remove(a);
							
							alliances.remove(a);

							if(!a.n1.rogueStates.contains(a.n2)) a.n1.rogueStates.add(a.n2);
							if(!a.n2.rogueStates.contains(a.n1)) a.n2.rogueStates.add(a.n1);
							}
						}
					
					if(selFinalProceed){
						n.isSelected = true;
						selectedNation = n;
						clickSound(4);
					}
				}else if(selectedNation != null && dist(mouseX, mouseY, selectedNation.pos.x, selectedNation.pos.y) > selectedNation.rad){
					selectedNation = null;
					for(int j = 0; j < others.size(); j++){
						Nation nat = others.get(j);
						nat.isSelected = false;
					}
				}
			}
			
			if(showAssumptions.onClick()){
				clickSound(1);
				canShowAssumptions = !canShowAssumptions;
			}
		}
	}

	public void keyPressed() {
		
		if(key == 'p'){
			isPaused = !isPaused;
		}
		
		if(statement){
			if(key == ' '){
				initiate = true;
				loadText(language);
				clickSound(2);
			}
			
			if(key == 'l'){
				loadText(language);
			}
		}
		
		if(!emailScreen){
			if(key == 'f'){
				saveFrame();
			}else if(key =='e'){
				language = "english";
			}else if(key =='f'){
				language = "francais";
			}
			
			if(key == '0'){
				splash = true;
				statement = false;
				intro = false;
				startGame1 = false;
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
			
			if(key == '1'){
				splash = false;
				statement = false;
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
				splash = false;
				statement = false;
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
				splash = false;
				statement = false;
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



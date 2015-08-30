package polsys;

import java.util.ArrayList;

import javax.print.attribute.standard.PrinterLocation;

import beads.Buffer;
import beads.Envelope;
import beads.Gain;
import beads.KillTrigger;
import beads.WavePlayer;

import com.sun.corba.se.spi.ior.MakeImmutable;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import sun.security.util.Resources;

public class Agent extends ProcessingObject {
	
	PApplet p;
	
	ArrayList<Connection> currentConnections;

		  PVector pos; 
		  PVector acceleration;
		  PVector velocity;
		  PVector baseVelocity;
		  float maxSpeed;
		  float maxForce;
		  float convergeCoeff;
		  float convergeForce;
		  float boundariesForce;
		  float resourceForce;
		  float slowness1;
		  float slowness2;
		  float slowness3;
		  
		  float deathAlpha;
		  float deathX;
		  
		  float graveAlpha;
		  float graveAlphaInc;
		  
		  boolean arrived;
		  boolean hasArrived;
		  
		  public float rad;
		  float maxRad;
		  float minRad;
		  float sw;
		  int connec;
		  int originalNumberOfConnections;
		  int connecMax;
		  PVector movement;
		  float distance;
		  int allowMovement;
		  
		  float violence;
		  float resourceSeek;

		  float moveCoeff;
		  float c = 0;
		  float wandertheta;

		  float wealthAccumulation;
		  float wealthAccumulationRate;
		  
		  float xPulse;
		  float yPulse;
		  float pulseRatio;
		  
		  float timerMovement;
		  float startTimeMovement;

		  float timerDeathMax;
		  float timerDeath;
		  float startTimeDeath;
		  float startTimeDeath2;
		  
		  float lifeSpan;
		  float birth;
		  float agingRate;
		  float ageMajorityModifier;
		  float ageMajority;
		  float ageSettle;
		  boolean isOfAge;
		  boolean isSettled;

		  boolean isAlive;
		  boolean isAlone;
		  boolean isKilled;
		  boolean isKiller;
		  boolean isHunter;
		  boolean isTamer;
		  boolean isHunted;
		  boolean isStopped;
		  boolean hasFoughtOthers;
		  
		  boolean tOld;
		  boolean tAlone;
		  boolean tKiller;
		  boolean tLoneKiller;
		  boolean tTamer;
		  boolean tCoToKill;
		  boolean tCoToDead;
		  boolean tWealthy;
		  boolean tNotAlone;
		  boolean tBeast;
		  boolean tHasRejected;
		  boolean tHasBeenRejected;
		  boolean tHasProtected;
		  
		  boolean tExertPower;
		  boolean tFewFriends;
		  boolean tFullFriends;
		  boolean tHasChildren;
		  boolean tNoFriends;
		  boolean tInLove;
		  boolean tIsOfAge;
		  boolean tIsSettled;
		  boolean tNotSettled;
		  boolean tOppressed;
		  boolean tSpaceCulture;
		  boolean tIsSterile;

		  float formingStableRelationships;
		  float meetingOthers;

		  int colDeath;
		  int col;
		  int originalCol;
		  int selectedCol;
		  float arriveAlphaRate;
		  float alpha;
		  float alpha2;
		  float rotateSpeed;
		  float rotateValue;
		  
		  int totalConnectionsMade;
		  boolean isConnectedToDead;
		  boolean isConnectedToKiller;
		  boolean isConnectedToHunter;
		  
		  int hasProtected;
		  int hasDisturbed;
		  
		  int hasBeenRejected;
		  int hasRejected;

		  
		  float maxTimeWithoutRelationship;
		  float lastTimeWithoutRelationship;
		  float startTimeWithoutRelationship;

		  Agent a;

		  Cluster cluster; //all agents have one cluster
		  
		  boolean canTalk;

		  // ------------------------------------------------------------------------------------- INTERACTION
		  
		  boolean isSelected;
		  boolean isDragged;
		  
		  ArrayList<String> thoughts;
		  
		  // ---------------------------------------------------------------------------------------- 2
		  ArrayList<Agent> siblings;
		  
		  float connecLove;
		  float distanceLove;
		  float distanceLoveSeparate;
		  float loveThreshold;
		  float loveIntensity;
		  float reproduceSpanLove;
		  float loveModifier;
		  float loveSeparationThreshold;
		  Agent mate;
		  float timerLove;
		  float startTimeLove;
		  
		  float connecPower;
		  float distancePower;
		  float distancePowerSeparate;
		  float powerModifier;
		  float powerRatio;
		  float powerExerted;
		  float powerFaced;
		  float timerPower;
		  float startTimePower;
		  
		  float connecFriendship;
		  float distanceFriendship;
		  float distanceFriendshipSeparate;
		  float personalSpaceFriendship;
		  float maxFriendshipForce;
		  float reproduceSpanFriendship;
		  float friendshipModifier;
		  static float cultSimRequirementFriendship;
		  float timerFriendship;
		  float startTimeFriendship;
		  
		  ArrayList<Agent> children;
		  boolean canReproduce;
		  float reproductionTimer;
		  Agent parent1;
		  Agent parent2;
		  int numberOfChildren;
		  float independenceOfChildrenModifier;
		  float independenceOfChildren;
		  boolean isSterile;
		  
		  boolean isCloseToBeast;
		  
		  float power;
		  float maxPowerForce;
		  
		  float cultureLength;
		  float[] culture;
		  float maxCulture;
		  float averageCulture;
		  float cultureMaximum;
		  float cultureMinimum;
		  
		  int sides;
		  
		  float cultureVariationPower;
		  float cultureVariationFriendship;
		  float cultureVariationLove;
		  float socialEnvironmentVariation;
		  
		//-------------------- Timer Variables
		  
		  ArrayList<ConnectionLove> currentConnectionsLove;
		  ArrayList<Agent> agentsLove;
		  ArrayList<ConnectionPower> currentConnectionsPower;
		  ArrayList<Agent> agentsPower;
		  ArrayList<ConnectionFriendship> currentConnectionsFriendship;
		  ArrayList<Agent> agentsFriendship;

		  float distanceResource;
		  
		  //birth visuals
		  float r1;
		  float r2;
		  float birthAlpha;
		  
		  //-------------------- END GAME 2 VARIABLES
		  float timeInLove;
		  float childrenHad;
		  ArrayList<Agent> friendsHad;
		  int powerExertedTotal;
		  int powerReceivedTotal;
		  int generation;
		  int spaceCulture;
		  int socialCulture;
		  boolean hasRevolted;
		  boolean hasOppressed;
		  boolean hasForcedSim;
		  boolean hasForcedDiff;
		  
		  boolean isSelected2;		  
		  
		  Agent() {}
		  
		  Agent(float x, float y, float rad, int connections, float speed1, float speed2, float distance, float formingStableRelationships, float meetingOthers, float violence, float resourceSeek, PApplet p) {
		    this.p = p;
		    
		    currentConnections = new ArrayList<Connection>();
		    
		    arrived = false;
		    hasArrived = false;
		    
		    this.pos = new PVector(x, y);
		    velocity = new PVector(p.random(speed1, speed2), p.random(speed1, speed2));
		    baseVelocity = velocity;
		    acceleration = new PVector(0, 0);
		    maxSpeed = p.random(speed2*0.5f, speed2);
		    maxForce = 0.75f;
		    convergeCoeff = 2.0f; 
		    this.resourceSeek = resourceSeek;
		    resourceForce = 0.15f * resourceSeek;
		    convergeForce = 0.6f;
		    distanceResource = 100;
		    moveCoeff = 1;
		    boundariesForce = 0.4f;
		    slowness1 = 0.8f;
		    slowness2 = 1.1f;
		    slowness3 = 0.8f;
		    
		    deathAlpha = 100.0f;
		    deathX = 0f;
		    
		    graveAlpha = 200;
		    graveAlphaInc = 0.25f;
		    
		    this.distance = distance;
		    this.rad = rad;
		    maxRad = 30;
		    minRad = 20;
		    sw = 1;
		    float r = p.random(1);
		    
		    if (r > 0.7f) {
		      this.connec = connections + (int)(p.random(1, 3));
		    } else if(r > 0.85f){
		      this.connec = connections - (int)(p.random(1, 2));
		    }else{
		    	this.connec = connections;
		    }
		    originalNumberOfConnections = connections;
		    this.connecMax = this.connec;
		    float wealthMod = PApplet.map(wealthAccumulation, 0, 500, 0, 1);
		    power = connecMax*wealthMod;
		    connecPower = connec;
		    connecLove = 1;
		    connecFriendship = connec + (int)p.random(1, 6);
		    
		    pulseRatio = 0.1f;
		    yPulse = p.random(10);
		    xPulse = p.random(10);
		    
		    startTimeMovement = p.millis();
		    timerMovement = p.random(8, 14)*1000;
		    
		    startTimeDeath = p.millis();
		    startTimeDeath2 = p.millis();
		    timerDeathMax = p.random(120, 245)*1000;
		    timerDeath = timerDeathMax;
		    lifeSpan = p.random(108, 155)*1000;
		    birth = p.millis();
		    agingRate = 0.1f;
		    ageMajorityModifier = 0.175f;
		    ageMajority = p.millis() + lifeSpan*ageMajorityModifier;
		    ageSettle = ageMajority*1.5f; //being settled happens after you've reached majority.
		    isOfAge = false;
		    
		    isAlive =  true;
		    isAlone = true;
		    
		    wealthAccumulation = 0.0f;
		    wealthAccumulationRate = 0.001f;
		    
		    cluster = new Cluster(this); //when we create the agent, we create a cluster that contains this agent
		    
		    col = p.color(50);
		    originalCol = col;
		    selectedCol = p.color(10, 50, 5);
		    alpha = 0;
		    alpha2 = 255;
		    arriveAlphaRate = 5.0f;
		    colDeath = p.color(190, 190, 190);
		    rotateSpeed = 0.01f;
		    rotateValue = 0.0f;
		    
		    isKilled = false;
		    isKiller = false;
		    isHunter = false;
		    isTamer = false;
		    isHunted = false;
		    isStopped = false;
		    hasFoughtOthers = false;
		    isCloseToBeast = false;
		    
			tOld  = false;
			tAlone = false;
			tKiller = false;
			tLoneKiller = false;
			tTamer = false;
			tCoToKill = false;
			tCoToDead = false;
			tWealthy = false;
			tNotAlone = false;
			tBeast = false;
			tHasRejected = false;
			tHasBeenRejected = false;
			tHasProtected = false;

		    this.formingStableRelationships = formingStableRelationships;
		    this.meetingOthers = meetingOthers;
		    
		    this.violence = PApplet.map(violence, 0.0f, 1.0f, 0.0f, 0.5f) + p.random(0.005f);
		    
		    totalConnectionsMade = 0;
		    isConnectedToDead = false;
		    isConnectedToKiller = false;
		    isConnectedToHunter = false;
		    maxTimeWithoutRelationship = 0;
		    startTimeWithoutRelationship = 0;
		    lastTimeWithoutRelationship = p.millis();
		    
		    hasProtected = 0;
			hasDisturbed = 0;
			  
			hasBeenRejected = 0;
			hasRejected = 0;
			
			isSelected = false;
			isDragged = false;
			thoughts = new ArrayList<String>();
			
			// ---------------------------------------------------------------------------------------- 2
			distanceLove = 100;
			distanceLoveSeparate = distanceLove * 4.0f;
			loveThreshold = 0.15f;
			loveIntensity = 2.0f;
			loveModifier = 50.0f;
			reproduceSpanLove = 8 * 1000.0f; //in seconds
			loveSeparationThreshold = 7.5f;
			mate = null;
			siblings = new ArrayList<Agent>();
			timerLove = p.random(2000, 3000);
			startTimeLove = p.millis();
			
			distancePower = 200;
			distancePowerSeparate = distancePower * 1.2f;
			power = connecPower + p.random(1);
			powerModifier = 5.0f;
			maxPowerForce = 0.025f * powerModifier;
			powerRatio = 0.1f;
			powerExerted = 0.0f;
			powerFaced = 0.0f;
			timerPower = p.random(2000, 3000);
			startTimePower = p.millis();			
			
			distanceFriendship = 300;
			distanceFriendshipSeparate = distanceFriendship * 1.2f;
			friendshipModifier = 5.0f;
			maxFriendshipForce = 0.2f * friendshipModifier;
			personalSpaceFriendship = 100;
			reproduceSpanFriendship = 20 * 1000.0f; //in seconds
			cultSimRequirementFriendship = 4.5f - (friendshipModifier*0.5f);
			timerFriendship = p.random(2000, 3000);
			startTimeFriendship = p.millis();
			
			independenceOfChildrenModifier = 1.0f;
			independenceOfChildren = 1.0f * independenceOfChildrenModifier;
			
			cultureLength = 4;
			culture = new float[(int) cultureLength];
			maxCulture = 10;
			averageCulture = 5;
			cultureMaximum = 5;
			cultureMinimum = 5;
			generation = 0;
			//culture 0 = spatial
			//culture 1 = power
			//culture 2 = social environment
			//culture 3 = variation
			
			for(int i = 0; i < culture.length; i++){
				culture[i] = p.random(maxCulture);
			}
			
			culture[1] = PApplet.map(power, 3, 7, 0, 10);
			
			cultureVariationPower = 0.01f;
			cultureVariationFriendship = 0.005f;
			cultureVariationLove = 0.02f;
			socialEnvironmentVariation = 0.005f;
			
			sides = (int)culture[0];
			
			children = new ArrayList<Agent>();
			canReproduce = false;
			reproductionTimer = p.millis() + p.random(7500, 14000);
			numberOfChildren = 3;
			parent1 = new Agent();
			parent2 = new Agent();
			
			canTalk = true;
			
			float rSterile = p.random(1);
			if(rSterile > 0.95f){
				isSterile = true;
			}else{
				isSterile = false;
			}
			
			currentConnectionsLove = new ArrayList<ConnectionLove>();
			agentsLove = new ArrayList<Agent>();
			currentConnectionsPower = new ArrayList<ConnectionPower>();
			agentsPower = new ArrayList<Agent>();
			currentConnectionsFriendship = new ArrayList<ConnectionFriendship>();
			agentsFriendship = new ArrayList<Agent>();
			
			r1 = p.random(4);
			r2 = p.random(3);
			birthAlpha = 200;
			
			//---------------------------- END GAME 2
			  timeInLove = 0;
			  childrenHad = 0;
			  friendsHad = new ArrayList<Agent>();
			  //power exerted
			  //power faced
			  
			  spaceCulture = 0;
			  socialCulture = 0;
			  hasRevolted = false;
			  hasOppressed = false;
			  hasForcedSim = false;
			  hasForcedDiff = false;
			  
			  isSelected2 = false;
		  }
		  
		  void update(){
			  if(PolSys.inGame1){
				  if(!hasArrived) startTimeDeath = p.millis();
				  if(this.wealthAccumulation > 0) this.wealthAccumulation -= 0.0001f;
				  //fade in arrival
				  if(this.arrived && this.alpha < 250 && this.isAlive && !this.hasArrived){
					  alpha += arriveAlphaRate;
					  if(alpha >= 250){
						  this.hasArrived = true;
					  }
				  }
				  
				  this.isCloseToBeast = false;
				  for(int i = 0; i < PolSys.predators.size(); i++){
					  Predator pred = PolSys.predators.get(i);
					  if(PVector.dist(this.pos, pred.pos) < p.width*0.075f){
						  this.isCloseToBeast = true;
					  }
				  }
				  
				  //seek resources
				  for(int i = 0; i < PolSys.resources.size(); i++){
					  Resource r = PolSys.resources.get(i);
					  if(PApplet.dist(pos.x, pos.y, r.pos.x, r.pos.y) < distanceResource*resourceSeek + r.rad && r.rad > r.depletionThreshold){
						  seek(r.pos, resourceForce, 0.5f);
					  }
				  }
				  
				  //connected to dead
				  for(int i = 0; i < this.currentConnections.size(); i++){
					 Connection c = this.currentConnections.get(i);
					 this.isConnectedToDead = false;
					 if(!c.a1.isAlive || !c.a2.isAlive){
						  this.isConnectedToDead = true;
						  break;
					  }
				  }
				  
				  //connected to hunter
				  for(int i = 0; i < this.currentConnections.size(); i++){
					 Connection c = this.currentConnections.get(i);
					 this.isConnectedToHunter = false;
					 if(c.a1.isHunter || c.a2.isHunter){
						  this.isConnectedToHunter = true;
						  break;
					  }
				  }
			  }
			  
			  if(PolSys.inGame2){
				  wander();
				  
				  //adding any agent to siblings arraylist
				  for(int i = 0; i < PolSys.community.size(); i++){
					  Agent a = PolSys.community.get(i);
					  if(a.parent1 == this.parent1 || a.parent2 == this.parent2 || this.parent1 == a.parent2 || this.parent2 == a.parent2) this.siblings.add(a);
				  }
				  
				  maxRad =  minRad + 1.0f*culture[1]; //the maximum radius is proportional
				  sw = 1 + (culture[1] / 5.0f);

				  if(this.velocity.x < 0.01f && this.velocity.x > -0.01f && this.velocity.y > -0.01f && this.velocity.y < 0.1f){
					  PVector f = new PVector(p.random(-1.5f, 1.5f), p.random(-1.5f, 1.5f));
					  applyForce(f);
				  }
				  
				  //timer for the reproduction of agents
				  if(p.millis() > reproductionTimer && !canReproduce && isOfAge){
					  canReproduce = true;
				  }
				  
				  for(int i = 0; i < culture.length; i++){
					  culture[i] = PApplet.constrain(culture[i], 0, maxCulture);
				  }
				  
				  this.sides = (int)culture[0];
				  if((int)((culture[1]+1)*0.75f)-this.agentsPower.size() > 0) this.connecPower = (int)((culture[1]+1)*0.75f)-this.agentsPower.size();
				  
				  if(currentConnectionsLove.size() > 0){
					timeInLove = p.millis()-currentConnectionsLove.get(0).creationTime; //this implies that the first time you fall in love, it's for ever
				  }
				  
				  //--------------------------social culture = the more friends you have the more culture[2] you have
				  this.culture[2] = socialEnvironmentVariation * agentsFriendship.size();
				  
				  //--------------------------variation culture = always compare for variation
				  averageCulture = (culture[0] + culture[1] + culture[2] + culture[3])*0.25f;
				  if(averageCulture > cultureMaximum) cultureMaximum = averageCulture;
				  if(averageCulture < cultureMinimum) cultureMinimum = averageCulture;
				  float culturalVariation = cultureMaximum - cultureMinimum;
				  culture[3] = culturalVariation;
				  
				  alpha2 = PApplet.map((p.millis()-startTimeDeath2), 0, lifeSpan, 255, 50);
			  }
			  
			  if(isStopped){			    
			    	velocity.mult(0);
			  }

			  boundaries();
			    
			  if (formingStableRelationships == 1.5f) {
				  if (connec == (int)(connecMax*0.0f)){
					  moveCoeff = 0.0f;
				  }else if (connec <= (int)(connecMax*0.25f)){
					  moveCoeff = 0.0f;
				  }else if (connec <= (int)(connecMax*0.5f)){
					  moveCoeff = 0.4f;
				  }else if (connec <= (int)(connecMax*0.75f)){
					  moveCoeff = 0.8f;
				  }else{
					  moveCoeff = 0.8f;
				  }
			  } else if (formingStableRelationships == 1.0f){
				  if (connec == (int)(connecMax*0.0f)){
					  moveCoeff = 0.0f;
				  }else if (connec <= (int)(connecMax*0.25f)){
					  moveCoeff = 0.2f;
				  }else if (connec <= (int)(connecMax*0.5f)){
					  moveCoeff = 0.7f;
				  }else if (connec <= (int)(connecMax*0.75f)){
					  moveCoeff = 1.0f;
				  }else{
					  moveCoeff = 1.0f;
				  }
			  } else if(formingStableRelationships == 0.5f){
				  if (connec == (int)(connecMax*0.0f)){
					  moveCoeff = 0.3f;
				  }else if (connec <= (int)(connecMax*0.25f)){
					  moveCoeff = 0.5f;
				  }else if (connec <= (int)(connecMax*0.5f)){
					  moveCoeff = 0.8f;
				  }else if (connec <= (int)(connecMax*0.75f)){
					  moveCoeff = 1.2f;
				  }else{
					  moveCoeff = 1.2f;
				  }
			  }
			  
			  velocity.add(acceleration);
			  if(PolSys.inGame1) velocity.limit(moveCoeff*slowness1);
			  if(PolSys.inGame2) velocity.limit(moveCoeff*slowness2);
			  if(PolSys.inGame3) velocity.limit(moveCoeff*slowness3);
			  pos.add(velocity);
			  acceleration.mult(0);
		  }
		  
		  void applyForce(PVector f){
			  acceleration.add(f);
		  }
		  
		  void applySpecialForce(PVector f){
			  velocity.add(f);
			  pos.add(velocity);
		  }
		  
		  void seek(PVector target, float limitForce, float coeff){
			  PVector desired = PVector.sub(target, this.pos);
			  
			  desired.normalize();
			  if(PolSys.inGame2){
				  if(isSettled) desired.mult(moveCoeff*0.75f);
			  }else{
				  desired.mult(moveCoeff);
			  }

			  PVector steer = PVector.sub(desired, velocity);
			  steer.limit(limitForce*coeff);
			  applyForce(steer);
		  }
		  
		  void wander() {
			    float wanderR = 50;         // Radius for our "wander circle"
			    float wanderD = 200;         // Distance for our "wander circle"
			    float change = 0.01f;
			    wandertheta += p.random(-change,change);     // Randomly change wander theta

			    // Now we have to calculate the new location to steer towards on the wander circle
			    PVector circleloc = velocity.get();    // Start with velocity
			    circleloc.normalize();            // Normalize to get heading
			    circleloc.mult(wanderD);          // Multiply by distance
			    circleloc.add(pos);               // Make it relative to location
			    
			    float h = velocity.heading2D();

			    PVector circleOffSet = new PVector(wanderR*PApplet.cos(wandertheta+h),wanderR*PApplet.sin(wandertheta+h));
			    PVector target = PVector.add(circleloc,circleOffSet);
			    seek(target, 1.5f, 4.0f);
			  }
		  
		  void debug(){
			  // ----------------------------------------------------------- DEBUG
			  p.textSize(PolSys.thoughtFontSize);
			  p.textFont(PolSys.thoughtFont);
			  p.fill(0);
			  p.textAlign(PApplet.LEFT);
			  //p.text("cluster: "+this.cluster.hashCode() + " + " + this.cluster.agentsInside.size(), x+rad, y-rad);
			  //p.text("inside: "+this.cluster.agentsInside.size(), pos.x, pos.y+rad);
			  //p.text("connec: "+connec, pos.x-rad, pos.y+rad);
			  //p.text("c Max: "+connecMax, x+rad, y-rad);
			  //p.text("maxTime "+maxTimeWithoutRelationship, x-rad, y-rad);
			  //p.text("lastTime "+lastTimeWithoutRelationship, x-rad, y+rad);
			  //p.text("ctd "+isConnectedToDead, pos.x-rad, pos.y+rad);
			  //printAgentsInside(this.cluster);
			  //p.text("is he alone? "+isAlone, x-rad, y+rad);
			  //p.text("velocity "+velocity, pos.x-rad, pos.y+rad);
			  //p.text("power: "+power, pos.x-rad, pos.y+rad);
			  //p.text("agents power: "+agentsPower.size(), pos.x+rad, pos.y-rad);
			  //if(agentsLove.size() > 0) p.text("in love", pos.x+rad, pos.y+(rad*2));
			  if(canReproduce) p.text(""+canReproduce, pos.x+rad, pos.y+rad);
			  //if(isOfAge) p.text("is of age", pos.x+rad, pos.y-rad);
			  //p.text("culture: "+(int)culture[0]+" / "+(int)culture[1]+" / "+(int)culture[2]+" / "+(int)culture[3], pos.x, pos.y+rad);
			  //p.text("friends: "+agentsFriendship.size(), pos.x, pos.y+rad*1.5f);
			  //p.text("wealth: "+wealthAccumulation, pos.x, pos.y +rad);
			  //p.text("maxRad: "+maxRad, pos.x, pos.y+rad+rad);
			  //p.text("social environment: "+culture[2], pos.x, pos.y+rad);
			  //p.text("connecPower: "+connecPower, pos.x, pos.y+rad);
			  //p.text("settled: "+isSettled, pos.x, pos.y+rad);
			  //p.text("thoughts size: "+thoughts.size(), pos.x, pos.y+rad);
			  //p.text("powerFaced: "+powerFaced, pos.x, pos.y+rad);
			  //p.text("powerExerted: "+powerExerted, pos.x, pos.y+rad*2);
			  //p.text(""+isSelected2, pos.x, pos.y+rad);
			  //p.text(alpha, pos.x, pos.y+20);
			  //p.text("release:"+(75.0f+culture[0]*250.0f), pos.x, pos.y+20);
			  //p.text("beast close? "+isCloseToBeast, pos.x, pos.y);
			  //p.text(""+isKiller, pos.x, pos.y);
			  //p.text("death:"+(timerDeath - (p.millis() - startTimeDeath)), pos.x, pos.y);
			  //p.text("disturbed: "+hasDisturbed, pos.x+rad, pos.y);
			  //p.text("protected: "+hasProtected, pos.x+rad, pos.y+rad);
			  //p.text("time in Love: "+this.timeInLove, pos.x+rad, pos.y+rad);
			  //p.text("children"+childrenHad, pos.x, pos.y+rad);
			  //p.text("generation "+generation, pos.x, pos.y+rad);
		  }

		  void display() {
			  
			  //if(birthAlpha > 0) makeBirthVisuals(this, p.random(4), p.random(3));
			  
			  if(isSelected && PolSys.inGame1){
				  p.pushMatrix();
				  p.translate(this.pos.x, this.pos.y);
				  p.rotate(PApplet.PI/4+rotateValue);
				  rotateValue += rotateSpeed;
				  p.noFill();
				  p.strokeWeight(1);
				  p.stroke(selectedCol, alpha);
				  p.rectMode(PApplet.CENTER);
				  p.rect(0, 0, rad*1.2f, rad*1.2f);
				  p.rect(0, 0, rad*1.5f, rad*1.5f);
				  p.popMatrix();
				  for(int i = 0; i < this.cluster.agentsInside.size(); i++){
					  Agent a = this.cluster.agentsInside.get(i);
					  a.col = selectedCol;
				  }
			  }else{
				  boolean isInSelectedCluster = false;
				  if(this.cluster != null || this.cluster.agentsInside != null){
					  for(int i = 0; i < this.cluster.agentsInside.size(); i++){
						  Agent a = this.cluster.agentsInside.get(i);
						  if(a.isSelected){
							  isInSelectedCluster = true;
							  break;
						  }
					  }
				  }

				  if(!isInSelectedCluster){
					  for(int i = 0; i < this.cluster.agentsInside.size(); i++){
						  Agent a = this.cluster.agentsInside.get(i);
						  a.col = originalCol;
					  } 
				  }

			  }
			  
			  if(isSelected2 && PolSys.inGame2){
				  p.rectMode(PApplet.CENTER);
				  p.pushMatrix();
				  p.translate(this.pos.x, this.pos.y);
				  p.rotate(PApplet.PI/4+rotateValue);
				  rotateValue += rotateSpeed;
				  p.noFill();
				  p.stroke(selectedCol);
				  p.strokeWeight(1);
				  p.rect(0, 0, rad*1.2f, rad*1.2f);
				  p.rect(0, 0, rad*1.6f, rad*1.6f);
				  p.popMatrix();
			  }
			  
			  if(isHunted) col = p.color(0, 0, 150);
			  if(isKiller) col = p.color(90, 0, 0);
			  
			  if(PolSys.inGame1){
				  p.stroke(col, alpha);
				  p.noFill();
				  p.strokeWeight(1);
				    for (int i = 0; i < connec; i++) {
				    	p.ellipse(pos.x, pos.y, (i+1)*5, (i+1)*5);
				    }
				  p.strokeWeight(2); 
				  p.ellipse(pos.x, pos.y, rad, rad);
			      rad += (PApplet.cos(xPulse))*0.15f;
			      xPulse += pulseRatio;
			  }

			  if(PolSys.inGame2){
				  p.strokeWeight(2);
				  if(!isOfAge){
					  p.stroke(originalCol, alpha2*0.4f);
				  }else{
					  p.stroke(originalCol, alpha2);
				  }
				  p.noFill();
				  float angleHeading = this.velocity.heading2D() + PApplet.PI/2;
				  p.pushMatrix();
				  p.translate(pos.x, pos.y);
				  p.rotate(angleHeading);
				  
				  float angleInc = 360/(sides+5);
				  
				  //base shape
				  p.beginShape();
				  for(int i = 0; i < 360; i += angleInc){
					  float x = PApplet.cos(PApplet.radians(i))*(rad*0.5f);
					  float y = PApplet.sin(PApplet.radians(i))*(rad*0.5f);
					  p.vertex(x, y);
				  }
				  p.endShape(PApplet.CLOSE);
				  rad += (PApplet.cos(xPulse))*0.15f;
				  xPulse += pulseRatio;
				  
				  p.stroke(col, alpha2*0.4f);
				  p.strokeWeight(1);
				  if(culture[1] >= 5){
					  p.beginShape();
					  for(int i = 0; i < 360; i += angleInc){
						  float x = PApplet.cos(PApplet.radians(i))*(rad*0.4f);
						  float y = PApplet.sin(PApplet.radians(i))*(rad*0.4f);
						  p.vertex(x, y);
					  }
					  p.endShape(PApplet.CLOSE);
				  }
				  
				  p.stroke(col, alpha2*0.3f);
				  if(culture[1] >= 8){
					  p.beginShape();
					  for(int i = 0; i < 360; i += angleInc){
						  float x = PApplet.cos(PApplet.radians(i))*(rad*0.3f);
						  float y = PApplet.sin(PApplet.radians(i))*(rad*0.3f);
						  p.vertex(x, y);
					  }
					  p.endShape(PApplet.CLOSE);
				  }
				  
				  p.stroke(col, alpha2*0.1f);
				  if(culture[1] == 10){
					  p.beginShape();
					  for(int i = 0; i < 360; i += angleInc){
						  float x = PApplet.cos(PApplet.radians(i))*(rad*0.2f);
						  float y = PApplet.sin(PApplet.radians(i))*(rad*0.2f);
						  p.vertex(x, y);
					  }
					  p.endShape(PApplet.CLOSE);
				  }

				  p.popMatrix();
			  }
		    
			  

		    if ((p.millis() - startTimeMovement >= timerMovement) && connec >= connecMax-1) { //only change speed and type when you are alone
		    	movement = new PVector(p.random(-1.5f, 1.5f), p.random(-1.5f, 1.5f));
		    	PVector steer = PVector.sub(movement, velocity);
				if(PolSys.inGame1) steer.limit(moveCoeff);
				if(PolSys.inGame2) steer.limit(moveCoeff*2.0f);
			    applyForce(steer);
			    startTimeMovement = p.millis();
		    }
		    
		    
		    if(PolSys.inGame1){ //this is the dying/aging condition for the first part
		    	
		    	//check if all the agents in the cluster are dead
		    	boolean allDeceased = true;
		    	for(int i = 0; i < this.cluster.agentsInside.size(); i++){
		    		Agent a = this.cluster.agentsInside.get(i);
		    		if(a != this && a.isAlive) allDeceased = false;
		    		
		    	}
			    if(connec == connecMax || allDeceased){
			    	isAlone = true;
			    }else{
			    	isAlone = false;
			    }
			    
			    isAlone = true;
			    
			    if (isAlone && PolSys.inGame1 && hasArrived) { // you're alone and you die of a natural death and you've arrived (i.e. you're not in conflict with the fade in)
			    	this.alpha = PApplet.map((p.millis()-startTimeDeath), 0, timerDeath, 255, 50); 
			        if (p.millis() - startTimeDeath >= timerDeath) {  //when the agent dies
			          this.cluster.buryAgent(this); //remove the agent from the cluster it was in and assign it a null cluster
			          this.makeSound(2);
			          isAlive = false;
			        }
			    }else if(!isAlone) {
			    	this.col = p.color(50);
			    }
		    }
		    
		    if(PolSys.inGame2){
		    	if(p.millis() - startTimeDeath2 > this.lifeSpan){ //dying
		    		this.isAlive = false;
		    	}
		    	
		    	if(this.rad <= this.maxRad) rad += agingRate; //growing
		    	
		    	//if(this.generation == 0) isOfAge = true;
		    	
		    	if(p.millis() > ageMajority && !isOfAge){
		    		//here we can add more connections when they come of age
		    		if(connecMax < 3){
		    			connecMax += 2;
		    			connec += 2;
		    			connecPower += 2;
		    			connecFriendship += 2;
		    		}
		    		isOfAge = true;
		    	}
		    	
		    	if(p.millis() > ageSettle && !isSettled){
		    		isSettled = true;
		    	}
		    }

		  }
		  
		  public String computeThought(int stage, float r){
			  
			  int index;
			  
			  if(stage == 1){
				  	  if(alpha < 50 && !tOld){
						  String thought = PolSys.tsOld[(int)p.random(PolSys.tsOld.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tOld = true;
				  	  }
					  if(isKiller && !tKiller){
						  String thought = PolSys.tsKiller[(int)p.random(PolSys.tsKiller.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tKiller = true;
					  }
					  if(isTamer && !tTamer){
						  String thought = PolSys.tsTamer[(int)p.random(PolSys.tsTamer.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tTamer = true;
					  }
					  if(isConnectedToKiller && !tCoToKill){
						  String thought = PolSys.tsCoToKill[(int)p.random(PolSys.tsCoToKill.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tCoToKill = true;
					  }
					  if(isConnectedToDead && !tCoToDead){
						  String thought = PolSys.tsCoToDead[(int)p.random(PolSys.tsCoToDead.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tCoToDead = true;
					  }
					  if(isAlone && !tAlone){
						  String thought = PolSys.tsAlone[(int)p.random(PolSys.tsAlone.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tAlone = true;
					  }
					  if(hasProtected > 0 && !tHasProtected){
						  String thought = PolSys.tsHasProtected[(int)p.random(PolSys.tsHasProtected.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tHasProtected = true;
					  }
					  if(hasRejected > 0 && !tHasRejected){
						  String thought = PolSys.tsHasRejected[(int)p.random(PolSys.tsHasRejected.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tHasRejected = true;
					  }
					  if(hasBeenRejected > 0 && !tHasBeenRejected){
						  String thought = PolSys.tsHasBeenRejected[(int)p.random(PolSys.tsHasBeenRejected.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tHasBeenRejected = true;
					  }
					  if(wealthAccumulation > 100 && !tWealthy){
						  String thought = PolSys.tsWealthy[(int)p.random(PolSys.tsWealthy.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tWealthy = true;
					  }
					  if(!isAlone && !tNotAlone){
						  String thought = PolSys.tsNotAlone[(int)p.random(PolSys.tsNotAlone.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tNotAlone = true;
					  }
					  if(isKiller && connec == connecMax-1 && !tLoneKiller){
						  String thought = PolSys.tsLoneKiller[(int)p.random(PolSys.tsLoneKiller.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tLoneKiller = true;
					  }
					  if(isCloseToBeast && !tBeast){
						  String thought = PolSys.tsBeast[(int)p.random(PolSys.tsBeast.length)];
						  if(!thoughts.contains(thought)) thoughts.add(thought);
						  tBeast = true;
					  }
			  }

			  if(stage == 2){
				  if(currentConnectionsLove.size() != 0 && !tInLove){
					  String thought = PolSys.tsInLove[(int)p.random(PolSys.tsInLove.length)];
					  if(!thoughts.contains(thought)) thoughts.add(thought);
					  tInLove = true;
				  }
				  
				  if(isSterile && !tIsSterile){
					  String thought = PolSys.tsIsSterile[(int)p.random(PolSys.tsIsSterile.length)];
					  if(!thoughts.contains(thought)) thoughts.add(thought);
					  tIsSterile = true;
				  }
				  
				  if(currentConnectionsFriendship.size() == 0 && !tNoFriends){ //combo
					  String thought = PolSys.tsNoFriends[(int)p.random(PolSys.tsNoFriends.length)];
					  if(!thoughts.contains(thought)) thoughts.add(thought);
					  tNoFriends = true;
				  }else if(currentConnectionsFriendship.size() < 3 && !tFewFriends){
					  String thought = PolSys.tsFewFriends[(int)p.random(PolSys.tsFewFriends.length)];
					  if(!thoughts.contains(thought)) thoughts.add(thought);
					  tFewFriends = true;
				  }else if(currentConnectionsFriendship.size() >= 3 && !tFullFriends){
					  String thought = PolSys.tsFullFriends[(int)p.random(PolSys.tsFullFriends.length)];
					  if(!thoughts.contains(thought)) thoughts.add(thought);
					  tFullFriends = true;
				  }
				  
				  if(culture[0] == 0 || culture[0] == maxCulture && !tSpaceCulture){
					  String thought = PolSys.tsSpaceCulture[(int)p.random(PolSys.tsSpaceCulture.length)];
					  if(!thoughts.contains(thought)) thoughts.add(thought);
					  tSpaceCulture = true;
				  }
				  
				  if(powerExerted > 20 && powerExerted > powerFaced * 2.0f && !tExertPower){ //exerting a lot of power IN THIS CASE NO NEED FOR THEM TO BE MUTUALLY EXCLUSIVE
					  String thought = PolSys.tsExertPower[(int)p.random(PolSys.tsExertPower.length)];
					  if(!thoughts.contains(thought)) thoughts.add(thought);
					  tExertPower = true;
				  }
				  
				  if(powerFaced > 20 && powerFaced > powerExerted * 2.0f && !tOppressed){
					  String thought = PolSys.tsOppressed[(int)p.random(PolSys.tsOppressed.length)];
					  if(!thoughts.contains(thought)) thoughts.add(thought);
					  tOppressed = true;
				  }
				  
				  if(isSettled && !tIsSettled){
					  String thought = PolSys.tsIsSettled[(int)p.random(PolSys.tsIsSettled.length)];
					  if(!thoughts.contains(thought)) thoughts.add(thought);
					  tIsSettled = true;
				  }
				  
				  if(!isSettled && !tNotSettled){
					  String thought = PolSys.tsNotSettled[(int)p.random(PolSys.tsNotSettled.length)];
					  if(!thoughts.contains(thought)) thoughts.add(thought);
					  tNotSettled = true;
					  
				  }
				  
				  if(isOfAge && !tIsOfAge){
					  String thought = PolSys.tsIsOfAge[(int)p.random(PolSys.tsIsOfAge.length)];
					  if(!thoughts.contains(thought)) thoughts.add(thought);
					  tIsOfAge = true;
				  }
				  
				  if(children.size() > 0 && !tHasChildren){
					  String thought = PolSys.tsHasChildren[(int)p.random(PolSys.tsHasChildren.length)];
					  if(!thoughts.contains(thought)) thoughts.add(thought);
					  tHasChildren = true;
				  }
			  }			  
			  
			  index = (int) PApplet.map(r, 0, 1, thoughts.size()*0.5f, thoughts.size());
			  
			  String thoughtDisplayed = null;
			  if(thoughts.size() != 0){
				  thoughtDisplayed = thoughts.get(index);
			  }else{
				  thoughtDisplayed = "";
			  }
			  
			  
			return thoughtDisplayed;
			  
		  }
		  
		  void boundaries() {
			  

			    PVector desired = null;

			    if (pos.x < rad) {
			      desired = new PVector(moveCoeff*4, velocity.y);
			    } 
			    else if (pos.x > p.width - rad) {
			      desired = new PVector(-moveCoeff*4, velocity.y);
			    } 

			    if (pos.y < rad) {
			      desired = new PVector(velocity.x, moveCoeff);
			    } 
			    else if (pos.y > p.height-rad-(p.height/19)) {
			      desired = new PVector(velocity.x, -moveCoeff);
			    } 

			    if (desired != null) {
			      desired.normalize();
			      desired.mult(moveCoeff);
			      PVector steer = PVector.sub(desired, this.velocity);
			      steer.limit(boundariesForce);
			      applyForce(steer);
			    }
			  }
		  
		  void exertPower(){
			  for(int i = 0; i < agentsPower.size(); i++){
				  Agent a = agentsPower.get(i);
				  
				  if(a.isAlive && a.isSettled){
					  float diff = this.culture[1] - a.culture[1];
					  float d = PApplet.abs(PApplet.dist(this.pos.x, this.pos.y, a.pos.x, a.pos.y));
					  
					  if(diff > 0){//if this one is the most powerful, exert power
						  PVector force = new PVector(p.random(-1.0f, 1.0f), 1); //create a new force pointing down;
						  float dist = PApplet.map(d, 0, p.height, 0, 1);
						  this.powerExerted += powerRatio;
						  a.powerFaced += powerRatio;
						  force.mult(dist); //the further they are, the higher the force
						  force.limit(maxPowerForce);
						  PVector inverse = force.get();
						  inverse.mult(-1);
						  
						  a.applyForce(force);
						  this.applyForce(inverse);
					  }else{
						  PVector force = new PVector(p.random(-1.0f, 1.0f), 1); //create a new force pointing down;
						  float dist = PApplet.map(d, 0, p.height, 0, 1);
						  a.powerExerted += powerRatio;
						  this.powerFaced += powerRatio;
						  force.mult(dist); //the further they are, the higher the force
						  force.limit(maxPowerForce);
						  PVector inverse = force.get();
						  inverse.mult(-1);
						  
						  this.applyForce(force);
						  a.applyForce(inverse);
					  }
					  
					  //pushing the culture 1 slot in the different directions --> the more powerful you are, the more powerful you become and vice versa
						if(this.culture[1] < a.culture[1]) {
							if(this.culture[1] < maxCulture) this.culture[1] -= cultureVariationPower*1.2f;
						}else{
							if(this.culture[1] < maxCulture) this.culture[1] += cultureVariationPower;
						}
					  
				  }
			  }
		  }
		  
		  void exertFriendship(){
			  for(int i = 0; i < agentsFriendship.size(); i++){
				  Agent a = agentsFriendship.get(i);

				  if(a.isAlive && !a.isSettled){
					  if(PApplet.dist(this.pos.x, this.pos.y, a.pos.x, a.pos.y) > personalSpaceFriendship){ //this is for calculating vectors
						  
						  float culturalSimilarity = 0;
						  for(int j = 0; j < culture.length; j++){
							  culturalSimilarity += PApplet.abs(this.culture[j] - a.culture[j]); //the smaller the number, the more similar
						  }
						  
						  float culturalSimilarityForce = 1;
						  if(culturalSimilarity < 1){
							  culturalSimilarityForce  = maxFriendshipForce;
						  }else{
							  culturalSimilarityForce = maxFriendshipForce/culturalSimilarity;
						  }
						  
						  PVector friendship = PVector.sub(a.pos, this.pos);
						  friendship.normalize(); //now we have a force pointing at the other friend;
						  friendship.mult(culturalSimilarityForce*0.03f);
						  friendship.limit(maxFriendshipForce);
						  
						  this.applyForce(friendship);
					  }
					  
					  for(int j = 0; j < culture.length; j++){
						if(this.culture[2] < a.culture[2]) {
							if(this.culture[2] < agentsFriendship.size()) this.culture[2] += cultureVariationFriendship;
						}else{
							if(this.culture[2] > agentsFriendship.size()) this.culture[2] -= cultureVariationFriendship;
						}
					  }
				  }
			  }
		  }
		  
		  void exertLove(){
			  for(int k = 0; k < currentConnectionsLove.size(); k++){
				  ConnectionLove cl = currentConnectionsLove.get(k);
				  
				  Agent ag;
				  if(cl.a1 == this){
					  ag = cl.a2;
				  }else{
					  ag = cl.a1;
				  }
				  
				  float culturalSimilarity = 0;
				  for(int c = 0; c < culture.length; c++){
					  culturalSimilarity += PApplet.abs(this.culture[c] - ag.culture[c]);
				  }
				  p.fill(0);
				  
				  if(culturalSimilarity > loveSeparationThreshold){
					  this.currentConnectionsLove.remove(cl);
					  ag.currentConnectionsLove.remove(cl);
					  PolSys.connectionsLove.remove(cl);
					  mate = null;
				  }
			  }

			  //what happens in love? loves keeps you close to each other
			  for(int i = 0; i < agentsLove.size(); i++){
				  Agent a = agentsLove.get(i);
				  if(a.isAlive){
					  if(PApplet.dist(this.pos.x, this.pos.y, a.pos.x, a.pos.y) > distanceLove){
						  PVector love = PVector.sub(a.pos, this.pos);
						  love.normalize();
						  love.mult(loveIntensity*loveModifier);
						  
						  applyForce(love);
					  }
					  
					  for(int j = 0; j < culture.length; j++){ //pushing the culture in the different directions
						if(this.culture[j] < a.culture[j]) {
							if(this.culture[j] < maxCulture) this.culture[j] += cultureVariationLove;
						}else{
							if(this.culture[j] < maxCulture) this.culture[j] += cultureVariationLove;
						}
					  }
				  }

			  }
		  }
		  
		  void reproduce(Agent mate, ConnectionLove bond){
			  if(canReproduce && !isSterile){	
				  	if(mate.canReproduce && !mate.isSterile && p.millis() - bond.creationTime > this.reproduceSpanLove && p.millis() - bond.creationTime > mate.reproduceSpanLove && mate != parent1 && mate != parent2 && this.numberOfChildren > 0 && mate.numberOfChildren > 0){
				  		 float x = (this.pos.x+rad);
						  float y = (this.pos.y+rad);
						  float r = this.rad*0.5f;
						  //int c = (int)((this.connecMax + mate.connec)/2);
						  int c = 2; //this way they can only connect to their parents
						  float d = this.distance;
						  float r1 = formingStableRelationships;
						  float r2 = meetingOthers;
						  float v = (this.violence+mate.violence)/2;
						  float reS = this.resourceSeek;
						  
//						  //this is the result
//						  PApplet.println("someone is born @ "+p.frameCount);
//						  PApplet.println("parent 1: "+this);
//						  PApplet.println("parent 2: "+mate);
						  Agent child = new Agent(x, y, r, c, -2, 2, d, r1, r2, v, reS, this.p);
						  child.parent1 = this;
						  child.parent2 = mate;
						  child.generation = parent1.generation + 1;
						  child.canReproduce = false;
						  if(!this.children.contains(child)) this.children.add(child);
						  if(!mate.children.contains(child)) mate.children.add(child);
						  if(!PolSys.community.contains(child)) PolSys.community.add(child);
						  
						  
						  this.numberOfChildren--;
						  this.childrenHad++;
						  this.canReproduce = false;
						  this.reproductionTimer = p.millis() + reproduceSpanLove;
						  
						  mate.numberOfChildren--;
						  mate.childrenHad++;
						  mate.canReproduce = false;
						  mate.reproductionTimer = p.millis() + reproduceSpanLove;
				  	}
			  }	 
		  }
		  
		  
		  void makeBirthVisuals(Agent child, float r1, float r2){
			  PVector pos1;
			  PVector pos2;
			  
			  if(r1 < 1){
				  pos1 = new PVector(PolSys.rectBorderX, PolSys.rectBorderY);
				  
				  if(r2 < 1){
					  pos2 = new PVector(PolSys.rectBorderX, PolSys.rectBorderY+PolSys.rectBorderH);
				  }else if(r2 < 2){
					  pos2 = new PVector(PolSys.rectBorderX+PolSys.rectBorderW, PolSys.rectBorderY);
				  }else{
					  pos2 = new PVector(PolSys.rectBorderX+PolSys.rectBorderW, PolSys.rectBorderY+PolSys.rectBorderH);
				  }
			  }else if(r1 < 2){
				  pos1 = new PVector(PolSys.rectBorderX, PolSys.rectBorderY+PolSys.rectBorderH);
				  
				  if(r2 < 1){
					  pos2 = new PVector(PolSys.rectBorderX, PolSys.rectBorderY);
				  }else if(r2 < 2){
					  pos2 = new PVector(PolSys.rectBorderX+PolSys.rectBorderW, PolSys.rectBorderY);
				  }else{
					  pos2 = new PVector(PolSys.rectBorderX+PolSys.rectBorderW, PolSys.rectBorderY+PolSys.rectBorderH);
				  }
			  }else if(r1 < 3){
				  pos1 = new PVector(PolSys.rectBorderX+PolSys.rectBorderW, PolSys.rectBorderY);
				  
				  if(r2 < 1){
					  pos2 = new PVector(PolSys.rectBorderX, PolSys.rectBorderY+PolSys.rectBorderH);
				  }else if(r2 < 2){
					  pos2 = new PVector(PolSys.rectBorderX, PolSys.rectBorderY);
				  }else{
					  pos2 = new PVector(PolSys.rectBorderX+PolSys.rectBorderW, PolSys.rectBorderY+PolSys.rectBorderH);
				  }
			  }else{
				  pos1 = new PVector(PolSys.rectBorderX+PolSys.rectBorderW, PolSys.rectBorderY+PolSys.rectBorderH);
				  
				  if(r2 < 1){
					  pos2 = new PVector(PolSys.rectBorderX, PolSys.rectBorderY+PolSys.rectBorderH);
				  }else if(r2 < 2){
					  pos2 = new PVector(PolSys.rectBorderX+PolSys.rectBorderW, PolSys.rectBorderY);
				  }else{
					  pos2 = new PVector(PolSys.rectBorderX, PolSys.rectBorderY);
				  }
			  }
			  
			  p.stroke(255, birthAlpha);
			  p.strokeWeight(child.rad);
			  p.line(pos1.x, pos1.y, child.pos.x, child.pos.y);
			  p.line(pos2.x, pos2.y, child.pos.x, child.pos.y);
			  birthAlpha -= 8.0f;
		  }
		  
		  void deathVisuals(){
			  if(deathX > 0){
				  p.stroke(100, 0, 0, deathAlpha);
				  p.line(pos.x, pos.y-deathX, pos.x, pos.y+(deathX*2.0f));
				  p.line(pos.x-deathX, pos.y, pos.x+deathX, pos.y);
			  }
			  deathAlpha -= 4.0f;
			  deathX += 10.0f;
			  
		  }
		  
		  void printAgentsInside(Cluster c){ //this prints out the name of the agent as well as all the agents contained in this agent's cluster
			  //p.text("this: "+this.hashCode(), this.pos.x, this.pos.y + this.rad*2);
			  for(int i = 0; i < c.agentsInside.size(); i++){
				  Agent a = c.agentsInside.get(i);
				  //p.text("in: "+a.hashCode(), this.pos.x, this.pos.y + this.rad*2 + i*10 + 15);
			  }
		  }
		  
		  void checkConnections(ArrayList<Agent> ala, Agent a){ //the connections are going to make sure that they all have the same cluster
			  
			  if(!ala.contains(a)){
				  ala.add(a);
				  
					  for(int i = 0; i < a.currentConnections.size(); i++){
						  
						  Connection c = a.currentConnections.get(i);
						  
						  
						  if(a == c.a1){ //check for the agent on the other end, in this case agent 
							  if(c.a2.cluster != a.cluster){ //if the two clusters differ

								  c.a1.cluster.addAgent(c.a2);
								  
								  a.col = p.color(250, 250, 0); //visualize the agent trying to make this happen
								  
							  }else{
								  a.col = p.color(0);
							  }

							  //a similar thing is handled in destroyConnections() in Conection
							  if(c.a2.isKiller){
								  a.isConnectedToKiller = true;
							  }else{
								  a.isConnectedToKiller = false;
							  }
							  
							  if(!ala.contains(c.a2)){
								  checkConnections(ala, c.a2);
							  }
							 							  
						  }else if(a == c.a2){
							  		//this is never called but...
							  if(c.a1.isKiller){
								  a.isConnectedToKiller = true;
							  }else{
								  a.isConnectedToKiller = false;
							  }
						  }
					  }
				  }
			  }
		  
		  void selectedColor(Agent a, int cVal){
			  a.col = selectedCol;

			  for(int i = 0; i < a.currentConnections.size(); i ++){
				  Connection c = a.currentConnections.get(i);
				  
				  if(p.green(c.c) != cVal) c.c = p.color(0, cVal, 0); //this creates a bug
				  
				  int a2col = c.a2.col;
				  int a1col = c.a1.col;
				  
				  //these two lines cause a stack overflow
				  if(c.a1 == this && p.green(a2col) != cVal) selectedColor(c.a2, cVal);
				  
				  if(c.a2 == this && p.green(a1col) != cVal) selectedColor(c.a1, cVal);
			  }
		  }

		  void connect() {
			if(connec == connecMax) lastTimeWithoutRelationship = (p.millis() - startTimeWithoutRelationship)/1000.0f;
			
			
		    //failsafe is connections get out of hand
		    if (connec > connecMax) connec = connecMax;

		    for (int i = 0; i < PolSys.agents.length; i++) {
		      if (PApplet.dist(this.pos.x, this.pos.y, PolSys.agents[i].pos.x, PolSys.agents[i].pos.y) < this.distance && PolSys.agents[i] != this && connec > 0 && PolSys.agents[i].connec > 0 && this.arrived && PolSys.agents[i].arrived) { //you are within the distance, and still have open connections left

		        boolean foundConnection = false;

		        for (int j = 0; j < PolSys.connections.size (); j++) { //this is where it gets murky. check for every connection, and if it's not already existing, add one.
		          Connection c = PolSys.connections.get(j);
		          if ((c.a2 == PolSys.agents[i] && c.a1 == this) || (c.a1 == PolSys.agents[i] && c.a2 == this)) { //if there isn't already a link between these two, add a link
		            foundConnection = true;
		            break;
		          }
		        }

		        if (!foundConnection) { //if you haven't found a connection between the two agents, ...
		          if (this.isAlive && (PolSys.agents[i].isAlive || PolSys.rememberDead > 1.0f)) { // ...and if the two agents still have connections left

		            // ------------------------------------------- Create a new connection
		        	Connection c = new Connection(this, PolSys.agents[i], distance, p);
		        	PolSys.connections.add(c); //start by adding a connection
		        	
					this.makeSound(0);
		        	
		        	fadingCircle(125, this.rad);
		        	  
		        	this.currentConnections.add(c); //also add an equivalent connection to the arraylist of all connections of this agent
		        	PolSys.agents[i].currentConnections.add(c);
		        	
		        	
		        	// ------------------------------------------ Keeps track of connections variables
		        	this.totalConnectionsMade += 1;
		        	PolSys.agents[i].totalConnectionsMade += 1;
		        	
		        	if(connec == connecMax){ //the time that has elapsed since the last time it broke apart
		        		lastTimeWithoutRelationship = (p.millis() - startTimeWithoutRelationship)/1000.0f;
		        	}
		        	
		        	if(lastTimeWithoutRelationship >= maxTimeWithoutRelationship){ //if that time is bigger than the max, then let that time be recorded
		        		maxTimeWithoutRelationship = lastTimeWithoutRelationship;
		        	}
		        	
		        	//same deal for the other agent
		        	if(PolSys.agents[i].connec == PolSys.agents[i].connecMax){ //the time that has elapsed since the last time it broke apart
		        		PolSys.agents[i].lastTimeWithoutRelationship = (p.millis() - PolSys.agents[i].startTimeWithoutRelationship)/1000.0f;
		        	}
		        	if(PolSys.agents[i].lastTimeWithoutRelationship >= PolSys.agents[i].maxTimeWithoutRelationship){ //if that time is bigger than the max, then let that time be recorded
		        		PolSys.agents[i].maxTimeWithoutRelationship = PolSys.agents[i].lastTimeWithoutRelationship;
		        	}
				        	

		            //---- stuff that makes them go towards each other
		            PVector converge = PVector.sub(PolSys.agents[i].pos, this.pos);
		            converge.normalize();
		            converge.mult(this.convergeCoeff);
		            PVector steer = PVector.sub(converge, this.velocity);
		            steer.limit(this.convergeForce);
		            this.applyForce(steer);
		            
		            converge = PVector.sub(this.pos, PolSys.agents[i].pos);
		            converge.normalize();
		            converge.mult(PolSys.agents[i].convergeCoeff);
		            steer = PVector.sub(converge, PolSys.agents[i].velocity);
		            steer.limit(PolSys.agents[i].convergeForce);
		            PolSys.agents[i].applyForce(steer);
		            
		            // ------------------------------------------ then remove one connection possibility from each counter
		            this.connec--;
		            PolSys.agents[i].connec--;

		            //this means that the more connections you make the faster you'll die.
		            this.timerDeath = this.timerDeathMax*0.9f;
		            PolSys.agents[i].timerDeath = PolSys.agents[i].timerDeathMax*0.9f;
		          }
		        }
		      } else if (PApplet.dist(this.pos.x, this.pos.y, PolSys.agents[i].pos.x, PolSys.agents[i].pos.y) > this.distance && this.isAlive && PolSys.agents[i].isAlive) { //if they are too far apart
		        for (int j = 0; j < PolSys.connections.size (); j++) {

		          Connection c = PolSys.connections.get(j);

		          if (c.a2 == PolSys.agents[i] && c.a1 == this || c.a1 == PolSys.agents[i] && c.a2 == this) { //if this is the connection between the two agents
		        	  
			           // ------------------------------------------- Remove existing connection
			        PolSys.connections.remove(c);

			        if(this.cluster != null) this.cluster.removeAgent(PolSys.agents[i]);
			        	
			        //remove the connection from this agent's references
			        this.currentConnections.remove(c);
			        	
			        //remove the connection from the other agent's references
			        PolSys.agents[i].currentConnections.remove(c);
			        	

		        	  
		        	if(this.connec == this.connecMax-1){//if this is your last connection, set the start time for a new record (hopefully not)
		        		  this.startTimeWithoutRelationship = p.millis();
		        	}
		        	  
		        	if(PolSys.agents[i].connec == PolSys.agents[i].connecMax-1){ //same deal if it's the case for the other agent
		        		PolSys.agents[i].startTimeWithoutRelationship = p.millis();
		        	}

		            // --- Remove the leaving agent from this agent's cluster, and create a new cluster for the leaving agent 
		        	floodFill();
		            if (!PolSys.agents[i]._dfsMarked) {
		              Cluster cl = new Cluster(PolSys.agents[i]);

		              this.cluster.agentsInside.removeAll(cl.agentsInside);
		            }
		            
		            // --- Increase connection counter
		            PolSys.agents[i].connec++;
		            this.connec++;
		          }
		        }
		      //this part is where i remember the dead, so its dependent on PolSys.rememberDead, but it might create weird stuff regarding the dea's cluster
		      }else if(PApplet.dist(this.pos.x, this.pos.y, PolSys.agents[i].pos.x, PolSys.agents[i].pos.y) > this.distance*PolSys.rememberDead && (!this.isAlive || PolSys.agents[i].isAlive)){
			        for (int j = 0; j < PolSys.connections.size (); j++) {

				          Connection c = PolSys.connections.get(j);

				          if (c.a2 == PolSys.agents[i] && c.a1 == this || c.a1 == PolSys.agents[i] && c.a2 == this) { //if this is the connection between the two agents
				        	  
					           // ------------------------------------------- Remove existing connection
					        PolSys.connections.remove(c);

					        if(this.cluster != null) this.cluster.removeAgent(PolSys.agents[i]);
					        	
					        //remove the connection from this agent's references
					        this.currentConnections.remove(c);
					        	
					        //remove the connection from the other agent's references
					        PolSys.agents[i].currentConnections.remove(c);
				        	  
				        	if(this.isAlive && this.connec == this.connecMax-1){//if this is your last connection, set the start time for a new record (hopefully not)
				        		  this.startTimeWithoutRelationship = p.millis();
				        	}
				        	  
				        	if(PolSys.agents[i].connec == PolSys.agents[i].connecMax-1){ //same deal if it's the case for the other agent
				        		PolSys.agents[i].startTimeWithoutRelationship = p.millis();
				        	}

				            // --- Remove the leaving agent from this agent's cluster, and create a new cluster for the leaving agent 
				        	floodFill();
				            if (!PolSys.agents[i]._dfsMarked) {
				              Cluster cl = new Cluster(PolSys.agents[i]);

				              this.cluster.agentsInside.removeAll(cl.agentsInside);
				            }
				            
				            // --- Increase connection counter
				            PolSys.agents[i].connec++;
				            this.connec++;
				          }
				        }
		      }
		    }
		  }
		  
		  void connectLove(){
			  if(mate != null){
				  float culturalSimilarity = 0;
				  for(int i = 0; i < this.culture.length; i++){
					  culturalSimilarity += PApplet.abs(this.culture[i] - mate.culture[i]);
				  }
				  p.textFont(PolSys.thoughtFont);
				  p.textSize(PolSys.textFontSize);
				  //p.text(culturalSimilarity, pos.x, pos.y);
			  }
			  
			  for(int i = 0; i < PolSys.community.size(); i++){
				  Agent a = PolSys.community.get(i);
				  
				  float culturalSimilarity = 0;
				  for(int c = 0; c < culture.length; c++){
					  culturalSimilarity += PApplet.abs(this.culture[c] - a.culture[c]);
				  }
				  
				  if(culturalSimilarity < loveSeparationThreshold && a.isOfAge){
					  mate = a; //if you are closest in cultural similarity, you become the mate
				  }
				  
				  
				  if(PApplet.dist(this.pos.x, this.pos.y, a.pos.x, a.pos.y) < this.distanceLove && this != a && this.connecLove > 0 && a.connecLove > 0 && culturalSimilarity >= loveThreshold && this.isOfAge && a.isOfAge){
					  boolean foundConnection = false;
					  for(int j = 0; j < PolSys.connectionsLove.size(); j++){
						  ConnectionLove cL = PolSys.connectionsLove.get(j);
						  if((cL.a1 == this && cL.a2 == a) || (cL.a1 == a && cL.a2 == this)){
							  foundConnection = true;
							  break;
						  }
					  }
					  
					  if(!foundConnection && this.isAlive && a.isAlive){
						  ConnectionLove cL = new ConnectionLove(this, a, distanceLove, p);
						  PolSys.connectionsLove.add(cL);
						  
						  this.currentConnectionsLove.add(cL);
						  this.agentsLove.add(a);
						  a.currentConnectionsLove.add(cL);
						  a.agentsLove.add(this);
						  
						  this.connecLove--;
						  a.connecLove--;
					  }
				  }else if(culturalSimilarity > this.distanceLoveSeparate){
					  for(int j = 0; j < PolSys.connectionsLove.size(); j++){
						  ConnectionLove cL = PolSys.connectionsLove.get(j);
						  
						  if((cL.a1 == this && cL.a2 == a) || (cL.a1 == a) && (cL.a2 == this)){
							  
							  PolSys.connectionsLove.remove(cL);
							  
							  this.currentConnectionsLove.remove(cL);
							  this.agentsLove.remove(a);
							  a.currentConnectionsLove.remove(cL);
							  a.agentsLove.remove(this);
							  
							  this.connecLove++;
							  a.connecLove++;
						  }
					  }
				  }
			  }
		  }
		  
		  void connectPower(){
			  for(int i = 0; i < PolSys.community.size(); i++){
				  Agent a = PolSys.community.get(i);
				  if(PApplet.dist(this.pos.x, this.pos.y, a.pos.x, a.pos.y) < this.distancePower && this != a && this.connecPower > 0 && a.connecPower > 0 && this.isOfAge){
					  boolean foundConnection = false;
					  for(int j = 0; j < PolSys.connectionsPower.size(); j++){
						  ConnectionPower cP = PolSys.connectionsPower.get(j);
						  if((cP.a1 == this && cP.a2 == a) || (cP.a1 == a && cP.a2 == this)){
							  foundConnection = true;
							  break;
						  }
					  }
					  
					  if(!foundConnection && this.isAlive && a.isAlive){
						  ConnectionPower cP = new ConnectionPower(this, a, distancePower, p);
						  PolSys.connectionsPower.add(cP);
						  
						  this.currentConnectionsPower.add(cP);
						  this.agentsPower.add(a);
						  a.currentConnectionsPower.add(cP);
						  a.agentsPower.add(this);
						  
						  this.connecPower--;
						  a.connecPower--;
					  }
				  }else if(PApplet.dist(this.pos.x, this.pos.y, a.pos.x, a.pos.y) > this.distancePowerSeparate){
					  for(int j = 0; j < PolSys.connectionsPower.size(); j++){
						  ConnectionPower cP = PolSys.connectionsPower.get(j);
						  
						  if((cP.a1 == this && cP.a2 == a) || (cP.a1 == a) && (cP.a2 == this)){
							  
							  PolSys.connectionsPower.remove(cP);
							  
							  this.currentConnectionsPower.remove(cP);
							  this.agentsPower.remove(a);
							  a.currentConnectionsPower.remove(cP);
							  a.agentsPower.remove(this);
							  
							  this.connecPower++;
							  a.connecPower++;
						  }
					  }
				  }
			  }
		  }

		  void connectFriendship(){
			  for(int i = 0; i < PolSys.community.size(); i++){
				  Agent a = PolSys.community.get(i);
				  
				  //this checks if there is enough cultural similarity between two people to form a friendship
				  float cultSim = 0;
				  for(int j = 0; j < culture.length; j++){
					  //the cultural similarities is the sum of all the differences of all the cultures: therefore the smaller the number, the more culturally similar
					 cultSim += PApplet.abs(this.culture[j] - a.culture[j]);
				  }
				 
				  if(PApplet.dist(this.pos.x, this.pos.y, a.pos.x, a.pos.y) < this.distanceFriendship && this != a && this.connecFriendship > 0 && a.connecFriendship > 0 && cultSim < cultSimRequirementFriendship){
					  boolean foundConnection = false;
					  for(int j = 0; j < PolSys.connectionsFriendship.size(); j++){
						  ConnectionFriendship cF = PolSys.connectionsFriendship.get(j);
						  if((cF.a1 == this && cF.a2 == a) || (cF.a1 == a && cF.a2 == this)){
							  foundConnection = true;
							  break;
						  }
					  }
					  
					  if(!foundConnection && this.isAlive && a.isAlive){
						  ConnectionFriendship cF = new ConnectionFriendship(this, a, distanceFriendship, p);
						  PolSys.connectionsFriendship.add(cF);
						  
						  this.currentConnectionsFriendship.add(cF);
						  this.agentsFriendship.add(a);
						  if(!this.friendsHad.contains(a)) this.friendsHad.add(a);
						  a.currentConnectionsFriendship.add(cF);
						  a.agentsFriendship.add(this);
						  if(!a.friendsHad.contains(this)) a.friendsHad.add(this);
						  
						  this.connecFriendship--;
						  a.connecFriendship--;
					  }
				  }else if(PApplet.dist(this.pos.x, this.pos.y, a.pos.x, a.pos.y) > this.distanceFriendshipSeparate){
					  for(int j = 0; j < PolSys.connectionsFriendship.size(); j++){
						  ConnectionFriendship cF = PolSys.connectionsFriendship.get(j);
						  
						  if((cF.a1 == this && cF.a2 == a) || (cF.a1 == a) && (cF.a2 == this)){
							  
							  PolSys.connectionsFriendship.remove(cF);
							  
							  this.currentConnectionsFriendship.remove(cF);
							  this.agentsFriendship.remove(a);
							  a.currentConnectionsFriendship.remove(cF);
							  a.agentsFriendship.remove(this);
							  
							  this.connecFriendship++;
							  a.connecFriendship++;
						  }
					  }
				  }
				  
				  //checking for better friends
				  float currentCultSim = 0;
				  float otherCultSim = 0;
				  for(int j = 0; j < agentsFriendship.size(); j++){ //compare current friends to other options
					  Agent friend = agentsFriendship.get(j);
					  if(!agentsFriendship.contains(friend)){
						  for(int k = 0; k < culture.length; k++){
							  currentCultSim += PApplet.abs(this.culture[k] - friend.culture[k]);
							  otherCultSim += PApplet.abs(this.culture[k] - a.culture[j]);
						  }
					  }
					  
					  if(otherCultSim < currentCultSim){ //if there is another option
						  for(int k = 0; k < PolSys.connectionsFriendship.size(); k++){
							  ConnectionFriendship cF = PolSys.connectionsFriendship.get(k);
							  
							  if((cF.a1 == this && cF.a2 == friend) || (cF.a1 == friend) && (cF.a2 == this)){
								  
								  PolSys.connectionsFriendship.remove(cF);
								  
								  this.currentConnectionsFriendship.remove(cF);
								  this.agentsFriendship.remove(friend);
								  friend.currentConnectionsFriendship.remove(cF);
								  friend.agentsFriendship.remove(this);
								  
								  ConnectionFriendship newCo = new ConnectionFriendship(this, friend, distanceFriendship, p);
								  this.currentConnectionsFriendship.add(newCo);
								  this.agentsFriendship.add(a);
								  if(!a.currentConnectionsFriendship.contains(newCo)) a.currentConnectionsFriendship.add(newCo);
								  if(!a.agentsFriendship.contains(this)) a.agentsFriendship.add(this);
								  //this.connecFriendship++; --------- do not increment the counter cause we're directly connecting to the new friend
								  friend.connecFriendship++;
								  a.connecFriendship--; //only decrease this one
							  }
						  }
					  }

				  }
			  }
		  }

		  void collide() {
			  
			  if(PolSys.inGame1){
				  for (int i = 0; i < PolSys.agents.length; i++) {
				      if (PApplet.dist(this.pos.x, this.pos.y, PolSys.agents[i].pos.x, PolSys.agents[i].pos.y) < rad && PolSys.agents[i] != this && PolSys.agents[i].isAlive && this.arrived && PolSys.agents[i].arrived) {

				        //can disturb
				        if (meetingOthers == 1.0f && this.connec == connecMax && PolSys.agents[i].connec <= 1) { //the current agent is the outsider and they could be trusted
				        	float r = p.random(1);
				        	if(r < 0.4f){
				        		this.hasDisturbed += 1;
				        		this.isKiller = true;
				        		PolSys.agents[i].cluster.buryAgent(PolSys.agents[i]);
				        		PolSys.agents[i].colDeath = p.color(0, 255, 0);
				        		PolSys.agents[i].isKilled = true;
				        		PolSys.agents[i].isAlive = false;
				        	}else if(r < 0.8f){
				        		PolSys.agents[i].hasProtected += 1;
				        		PolSys.agents[i].isKiller = true;
				        		this.cluster.buryAgent(this);
				        		this.colDeath = p.color(0, 255, 0);
				        		this.isKilled = true;
				        		this.isAlive = false;
				        	}else{
				        		this.hasBeenRejected++;
				        		PolSys.agents[i].hasRejected++;
				        	}
				        }
				        
				        if (meetingOthers == 1.5f && this.connec >= connecMax-1 && PolSys.agents[i].connec <= 1) { //both die
				        		PApplet.println("fought others");
				        		for(int j = 0; j < PolSys.agents[i].cluster.agentsInside.size(); j++){
				        			Agent a  = PolSys.agents[i].cluster.agentsInside.get(j);
				        			if(a != PolSys.agents[i]) a.hasFoughtOthers = true;
				        		}
				        		
				        		this.cluster.buryAgent(PolSys.agents[i]);
				        		this.colDeath = p.color(0, 255, 0);
				        		this.isKilled = true;
				        		this.isAlive = false;
					            
					            PolSys.agents[i].cluster.buryAgent(PolSys.agents[i]);
					            PolSys.agents[i].colDeath = p.color(0, 255, 0);
					            PolSys.agents[i].isKilled = true;
					            PolSys.agents[i].isAlive = false;
					    }

				        // combat stuff
				        if (this.connec == connecMax-1 && PolSys.agents[i].connec == PolSys.agents[i].connecMax-1) { //if they are both alone (they only have one connection)
				          float r = p.random(0, 1);
				          if (r < this.violence) { //they have a x percent chance of fighting
				            if (this.connecMax >= PolSys.agents[i].connecMax) { //if agent has more max connections (is bigger), then he kills 
				            	PolSys.agents[i].colDeath = p.color(255, 0, 0);
				            	PolSys.agents[i].makeSound(1);
				            	PolSys.agents[i].isKilled = true;
				            	this.cluster.buryAgent(PolSys.agents[i]); //kill the other agent
				            	
				            	this.isKiller = true;

				              for (int j = 0; j < PolSys.connections.size (); j++) {
				                Connection c = PolSys.connections.get(j);
				                if (c.a1 == PolSys.agents[i] || c.a2 == PolSys.agents[i]){
				                	PolSys.connections.remove(c);
				                }
				              }
				              
				              PolSys.agents[i].isAlive = false;
				              
				            } else if (this.connecMax < PolSys.agents[i].connecMax) {
				              this.colDeath = p.color(255, 0, 0);
				              this.isKilled = true;
				              this.makeSound(1);
				              this.cluster.buryAgent(this);
				              
				              PolSys.agents[i].isKiller = true;

				              for (int j = 0; j < PolSys.connections.size (); j++) {
				                Connection c = PolSys.connections.get(j);
				                if (c.a1 == this || c.a2 == this) PolSys.connections.remove(c); //remove any connections with the guy that's dead
				              }

				              this.isAlive = false;
				            }
				          }
				        }
				        
				        if(this.connec == 0){
				        	this.hasRejected += 1;
				        	PolSys.agents[i].hasBeenRejected += 1;
				        }else if(PolSys.agents[i].connec == 0){
				        	PolSys.agents[i].hasRejected += 1;
				        	this.hasBeenRejected += 1;
				        }
				        
				        PVector diff = PVector.sub(this.pos, PolSys.agents[i].pos);
				        diff.normalize();
				        diff.mult(maxSpeed);
				        
				        PVector steer = PVector.sub(diff, velocity);
				        steer.limit(maxForce);
				        applyForce(steer);
				      }
				    }
			  }
		  }
		  
		  void fadingCircle(int alphaStart, float size){
			  if(this.isAlive && alphaStart > 0){
				  p.fill(c, alphaStart);
				  p.ellipse(this.pos.x, this.pos.y, size, size);
				  size++;
				  alphaStart--;  
			  }
		  }
			//culture 0 = spatial
			//culture 1 = power
			//culture 2 = social environment
			//culture 3 = variation
		  
		  //spatial is fairly binary (up middle down) or i could turn the space into quadrants (3*3) > so it could be notes or some sort of effect/envelope (reverb +/- decay)?
		  //power should be loudness or, if it's not obvious enough, some sort of distortion
		  //social environment... maybe that should be notes? or a particular frequency range
		  //variation...  not sure how much this variable is useful maybe switch it with love?
		  void speak(){
			  if(canTalk){
				  int numHarmonics = 3;
				  float gainLanguageValue = 0.05f;
				  float baseFreq = 220.0f;
				  //have an array for different notes (consonant vs. dissonant?)
				  
				  WavePlayer[] wp;
				  Gain[] g;
				  Gain gainLanguage;
				  Envelope[] e;
				  
				  wp = new WavePlayer[numHarmonics];
				  g = new Gain[numHarmonics];
				  gainLanguage = new Gain(PolSys.ac, numHarmonics, gainLanguageValue);
				  e = new Envelope[numHarmonics];
				  for(int i = 0; i < numHarmonics; i++){
					  wp[i] = new WavePlayer(PolSys.ac, baseFreq+(baseFreq*PApplet.map(this.culture[2], 0.0f, 1.0f, 0.0f, 50.0f)), Buffer.SINE); //freq depends on social environment
					  e[i] = new Envelope(PolSys.ac, 0.0f);
					  g[i] = new Gain(PolSys.ac, 1, e[i]);
					  g[i].addInput(wp[i]);
					  float riseValueMax = 0.03f+0.05f*(this.culture[1]*0.75f)+p.random(0.01f); //loudness depends on power
					  e[i].addSegment(riseValueMax*0.25f, 25.0f+culture[0]*25.0f);//attack depends on the space
					  e[i].addSegment(0.0f, 75.0f+culture[0]*250.0f, new KillTrigger(g[i])); //release depends on the space
					  gainLanguage.addInput(g[i]);
					  PolSys.ac.out.addInput(gainLanguage);
				  }
				  
				  //TODO if they are in love, then it's only consonant, if not, it's dissonant
				  canTalk = false;
			  }
		  }
		  
		  void makeSound(int type){
			  if(type == 0){//connection
				  //int numberOfHarmonics = (int)PApplet.map(connec, 0, connecMax, 1, 10); //this should be mapped to total connections made already
				  int numberOfHarmonics = 3;
				  float gainAddition = PApplet.map(connecMax, originalNumberOfConnections-2, originalNumberOfConnections+3, 0.75f, 1.0f); //map how loud the their max potential
				  float freq = 220.0f;
				  
				  int r = (int) p.random(0, 3);
				  if(r == 0) freq = 220.0f; //440.0f;
				  if(r == 1) freq = 261.626f; //415.35f;
				  if(r == 2) freq = 195.998f; //466.164f;
				  WavePlayer[] wp;
				  Gain[] g;
				  Gain gainConnection;
				  Envelope[] e;
				  
				  wp = new WavePlayer[numberOfHarmonics];
				  g = new Gain[numberOfHarmonics];
				  gainConnection = new Gain(PolSys.ac, numberOfHarmonics, 0.1f);
				  e = new Envelope[numberOfHarmonics];
				  for(int i = 0; i < numberOfHarmonics; i++){
					  wp[i] = new WavePlayer(PolSys.ac, freq, Buffer.SINE);
					  e[i] = new Envelope(PolSys.ac, 0.0f);
					  g[i] = new Gain(PolSys.ac, 1, e[i]);
					  g[i].addInput(wp[i]);
					  gainConnection.addInput(g[i]);
					  PolSys.ac.out.addInput(gainConnection);
					  //TODO normalize

					  float riseValueMax = 0.05f*gainAddition;
					  e[i].addSegment(riseValueMax, 25.0f);//attack
					  e[i].addSegment(riseValueMax, 100.0f);//sustain
					  e[i].addSegment(0.0f, 1000.0f, new KillTrigger(g[i]));
				  }
			  }else if(type == 1){ //death violent
				  int numberOfHarmonics = 5;
				  float gainAddition = PApplet.map(connecMax, originalNumberOfConnections-2, originalNumberOfConnections+3, 0.75f, 1.0f); //map how loud the their max potential
				  float freq = 110.0f;
				  
				  int r = (int) p.random(0, 3);
				  if(r == 0) freq = 110.0f; //440.0f;
				  if(r == 1) freq = 87.307f; //415.35f;
				  if(r == 2) freq = 97.999f; //466.164f;
				  WavePlayer[] wp;
				  Gain[] g;
				  Envelope[] e;
				  
				  wp = new WavePlayer[numberOfHarmonics];
				  g = new Gain[numberOfHarmonics];
				  e = new Envelope[numberOfHarmonics];
				  for(int i = 0; i < numberOfHarmonics; i++){
					  if(i < numberOfHarmonics*0.5f){
						  wp[i] = new WavePlayer(PolSys.ac, freq, Buffer.SAW);
					  }else{
						  wp[i] = new WavePlayer(PolSys.ac, freq, Buffer.SINE);
					  }
					 
					  e[i] = new Envelope(PolSys.ac, 0.0f);
					  g[i] = new Gain(PolSys.ac, 1, e[i]);
					  g[i].addInput(wp[i]);
					  PolSys.ac.out.addInput(g[i]);
					  //TODO normalize
					  float riseValueMax = 0.005f*gainAddition;
					  e[i].addSegment(riseValueMax, 5.0f);//attack
					  e[i].addSegment(riseValueMax, 50.0f);//sustain
					  e[i].addSegment(0.0f, 1000.0f, new KillTrigger(g[i])); //TODO maybe because I don't kill the envelope, it might fuck up and over time?
				  }
			  }else if(type == 2){ //death natural
				  int numberOfHarmonics = 5;
				  float gainAddition = PApplet.map(connecMax, originalNumberOfConnections-2, originalNumberOfConnections+3, 0.75f, 1.0f); //map how loud the their max potential
				  float freq = 329.628f; //E
				  
				  int r = (int) p.random(0, 3);
				  if(r == 0) freq = 110.0f; //A3;
				  if(r == 1) freq = 261.626f; //C4;
				  if(r == 2) freq = 220f; //466.164f;
				  WavePlayer[] wp;
				  Gain[] g;
				  Envelope[] e;
				  
				  wp = new WavePlayer[numberOfHarmonics];
				  g = new Gain[numberOfHarmonics];
				  e = new Envelope[numberOfHarmonics];
				  for(int i = 0; i < numberOfHarmonics; i++){
					  if(i < numberOfHarmonics*0.25f){
						  wp[i] = new WavePlayer(PolSys.ac, freq, Buffer.TRIANGLE);
					  }else{
						  wp[i] = new WavePlayer(PolSys.ac, freq, Buffer.NOISE);
					  }
					 
					  e[i] = new Envelope(PolSys.ac, 0.0f);
					  g[i] = new Gain(PolSys.ac, 1, e[i]);
					  g[i].addInput(wp[i]);
					  PolSys.ac.out.addInput(g[i]);
					  //TODO normalize
					  float riseValueMax = 0.0025f*gainAddition;
					  e[i].addSegment(riseValueMax, 5.0f);//attack
					  e[i].addSegment(riseValueMax, 50.0f);//sustain
					  e[i].addSegment(0.0f, 1000.0f, new KillTrigger(g[i])); //TODO maybe because I don't kill the envelope, it might fuck up and over time?
				  }
			  }else if(type == 3){ //death hunted
				  int numberOfHarmonics = 5;
				  float gainAddition = 1.0f; //map how loud the their max potential
				  float freq = 329.628f; //E
				  
				  int r = (int) p.random(0, 3);
				  if(r == 0) freq = 110.0f; //A3;
				  if(r == 1) freq = 261.626f; //C4;
				  if(r == 2) freq = 220f; //466.164f;
				  WavePlayer[] wp;
				  Gain[] g;
				  Envelope[] e;
				  
				  wp = new WavePlayer[numberOfHarmonics];
				  g = new Gain[numberOfHarmonics];
				  e = new Envelope[numberOfHarmonics];
				  for(int i = 0; i < numberOfHarmonics; i++){
					  if(i < numberOfHarmonics*0.25f){
						  wp[i] = new WavePlayer(PolSys.ac, freq, Buffer.TRIANGLE);
					  }else{
						  wp[i] = new WavePlayer(PolSys.ac, freq, Buffer.SINE);
					  }
					 
					  e[i] = new Envelope(PolSys.ac, 0.0f);
					  g[i] = new Gain(PolSys.ac, 1, e[i]);
					  g[i].addInput(wp[i]);
					  PolSys.ac.out.addInput(g[i]);
					  //TODO normalize
					  float riseValueMax = 0.0025f*gainAddition;
					  e[i].addSegment(riseValueMax, 5.0f);//attack
					  e[i].addSegment(riseValueMax, 50.0f);//sustain
					  e[i].addSegment(0.0f, 1000.0f, new KillTrigger(g[i])); //TODO maybe because I don't kill the envelope, it might fuck up and over time?
				  }
			  }else if(type == 4){
				  
			  }
		  }

		  void floodFill() {

		    for (int i = 0; i < PolSys.agents.length; i++) {
		    	PolSys.agents[i]._dfsMarked = false;
		    }
		    _floodFill(this);
		  }


		  void _floodFill(Agent a) {

		    if (a._dfsMarked) return;
		    a._dfsMarked = true;
		    for (int i = 0; i < PolSys.connections.size (); i++) {
		      Connection c = PolSys.connections.get(i);
		      Agent b;
		      if (c.a1 == this)
		        b = c.a2;
		      else if (c.a2 == this)
		        b = c.a1;
		      else continue;
		      _floodFill(b);
		    }
		  }
		  boolean _dfsMarked = false;
		}

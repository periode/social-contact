package polsys;

import java.util.ArrayList;

import beads.KillTrigger;
import megamu.mesh.Hull;
import megamu.mesh.MPolygon;
import processing.core.PApplet;
import processing.core.PVector;

public class Nation {

	PApplet p;

	PVector pos;
	PVector origPos;
	PVector acceleration;
	PVector speed;
	int col;
	boolean hovered;

	float rateOfFriendship;
	float rateOfPower;
	float rateOfLove;
	float population;
	float culturalHomogeneity;
	boolean isNation;

	//---- inherited from 1
	float canFight;
	float meetingOthers;
	int connections;
	float craveForWealth;

	//----- inherited from 2
	float totalWealth;
	float povertyThreshold;
	float contraction;

	//---- unique to 3
	PVector[] citizenPos;
	PVector[] origCitizenPos;
	int[] citizenCol;
	float[] citizenRad;
	int[] citizenSW;

	float randomPosX;
	float randomPosY;
	float rad;

	int hullWeight;

	float alphaNation;

	//-----noise wander
	float[] citizenNoiseX;
	float[] citizenNoiseY;
	float noiseZ;
	float xincrement;
	float yincrement;
	float zincrement;

	//-----in use during 3
	float endemicCultureModifier;
	float endemicCultureNoise;
	float endemicCultureNoiseInc;

	float culturalProximity;
	float culturalModifierProximity;
	float similarCultureThreshold;

	float culturalRequirementTrade;
	float distanceTrade;
	float tradePartnerships;
	float cultureModifierTrade;
	float nationalSpending;
	float poorRevenueMultiplier;
	float wealthyRevenueMultiplier;
	boolean isRuined;
	boolean isPoor;
	float wealthDifferenceThreshold;

	float culturalRequirementAlliance;
	float culturalRequirementAllianceTrust;
	float culturalRequirementAllianceMistrust;
	float distanceAlliance;
	float cultureModifierAlliance;
	float allianceLerpVal;
	float allianceLerpInc;
	float allianceLerpMax;
	boolean canLerpAlliance;

	float culturalRequirementWar;
	float distanceWar;
	float cultureModifierWar;
	float loserEconomicDepletion;
	float winnerEconomicDepletion;
	boolean atWar;
	float wealthStopWar;
	boolean isAnnexed;
	PVector targetPos;
	float moveLerpVal;
	float moveLerpMax;
	boolean canLerpWar;

	float timerVanquished;
	float startTimeVanquished;

	ArrayList<Trade> currentTrades;
	ArrayList<Nation> tradePartners;
	ArrayList<Alliance> currentAlliances;
	ArrayList<War> currentWars;
	ArrayList<Nation> allies;
	ArrayList<Nation> rogueStates;
	ArrayList<Nation> enemies;
	ArrayList<Nation> vanquished;
	ArrayList<Nation> annexed;

	int goal;
	float victoryBehaviour;
	float tradeLimit;
	float allianceTrust;

	//bool thought
	boolean tAtPeace;
	boolean tAtPeaceAgain;
	boolean tWealthy;
	boolean tPoor;
	boolean tAverageCulture;
	boolean tNotAverageCulture;
	boolean tAtWar;
	boolean tHasAllies;
	boolean tNoAllies;
	boolean tHasTradePartners;
	boolean tHasNoTradePartners;
	boolean tNationDisappeared;
	boolean tHasNeighbors;
	boolean tNoNeighbors;
	boolean tWasAtWar;

	//----end game var
	boolean wasAtWar;
	int totalTradePartners;
	boolean promiscuity;
	float promiscuityDistance;
	int totalAllies;

	float neighborDistance;

	boolean isSelected;

	ArrayList<String> possibleStatements;
	int r;

	Nation(){}

	Nation(PVector pos, float rateOfFriendship, float rateOfPower, float rateOfLove, float population, float culturalHomogeneity, float canFight, float meetingOthers, int connections, float craveForWealth, float wealth, boolean isNation, PApplet p){
		this.p = p;
		this.pos = pos;
		this.origPos = pos;
		this.acceleration = new PVector();
		this.speed = new PVector();

		this.hovered = false;

		goal = 0; //0 = honor - 1 = wealth - 2 = self-preservation

		this.rateOfFriendship = rateOfFriendship;
		this.rateOfPower = rateOfPower;
		this.rateOfLove = rateOfLove;
		this.population = population;
		this.culturalHomogeneity = culturalHomogeneity;
		this.col = (int)PApplet.map(culturalHomogeneity, 0.0f, 50.0f, 20.0f, 360.0f);
		this.isNation = isNation;

		this.totalWealth = wealth;
		this.contraction = PApplet.map(totalWealth, 0, 100000, 0.0f, 0.9f);

		this.isSelected = false;

		this.canFight = canFight;

		this.meetingOthers = meetingOthers;
		this.connections = connections;
		this.craveForWealth = craveForWealth;

		citizenPos = new PVector[(int) population];
		origCitizenPos = new PVector[(int) population];
		citizenCol = new int[(int) population];
		citizenRad = new float[(int)population];
		citizenSW = new int[(int)population];
		citizenNoiseX = new float[(int) population];
		citizenNoiseY = new float[(int)population];

		randomPosX = (rateOfFriendship/rateOfLove)*30+500; // wide society if super friendly ----------------------------- and love makes everyone closer!
		randomPosX = PApplet.min(randomPosX, 80);
		randomPosY = (rateOfPower/rateOfLove)*30+70; // tall society if super powerful
		randomPosY = PApplet.min(randomPosY, 80);
		rad = p.width*0.1f; //make it as big the position?

		for(int i = 0; i < (int) population; i++){
			citizenPos[i] = new PVector(p.random(-randomPosX, randomPosX), p.random(-randomPosY, randomPosY));
			citizenCol[i] = p.color(0, 150);
			citizenRad[i] = PApplet.map(rateOfPower, 0, 100, 5, 20) * (1+p.random(1));	
			citizenSW[i] = (int) p.random(1, 4);
			citizenNoiseX[i] = p.random(10);
			citizenNoiseY[i] = p.random(10);
		}

		origCitizenPos = citizenPos;

		alphaNation = 100.0f;

		hullWeight = 2;

		xincrement = 0.0000001f;

		yincrement = 0.0000001f;

		noiseZ = p.random(10);
		zincrement = 0.0000001f;

		culturalRequirementTrade = 1.0f;
		distanceTrade = p.width*0.2f;
		tradePartnerships = 3;
		currentTrades = new ArrayList<Trade>();
		tradePartners = new ArrayList<Nation>();
		nationalSpending = (totalWealth*0.00001f)*population; //TODO algorithm too rough?
		povertyThreshold = 10.0f;
		poorRevenueMultiplier = p.random(0.00002f, 0.00008f)*population;
		wealthyRevenueMultiplier = p.random(0.000002f, 0.000008f)*population;
		isRuined = false;
		isPoor = false;
		wealthDifferenceThreshold = 750;

		culturalRequirementAlliance = 0.2f;
		culturalRequirementAllianceTrust = 0.3f;
		culturalRequirementAllianceMistrust = 0.1f;
		distanceAlliance = p.width*0.1f;
		currentAlliances = new ArrayList<Alliance>();
		allies = new ArrayList<Nation>();
		rogueStates = new ArrayList<Nation>();
		allianceLerpVal = 0f;
		allianceLerpInc = 0.001f;
		allianceLerpMax = 0.1f;
		canLerpAlliance = false;

		culturalRequirementWar = 2.0f;
		distanceWar = p.width*0.5f;
		currentWars = new ArrayList<War>();
		enemies = new ArrayList<Nation>();
		loserEconomicDepletion = 0.05f;
		winnerEconomicDepletion = 0.025f;
		atWar = false;
		this.victoryBehaviour = 0;
		wealthStopWar = 25*victoryBehaviour;
		isAnnexed = false;
		targetPos = this.pos;
		moveLerpMax = 0.0f;
		vanquished = new ArrayList<Nation>();
		annexed = new ArrayList<Nation>();
		canLerpWar = false;

		timerVanquished = 20000.0f;
		startTimeVanquished = p.millis();

		neighborDistance = p.width*0.2f;

		endemicCultureModifier = 0.0025f;
		endemicCultureNoise = p.random(100);
		endemicCultureNoiseInc = 0.01f;

		culturalProximity = p.width*0.2f;
		culturalModifierProximity = 0.001f;

		similarCultureThreshold = 0.1f;

		//----end game var
		wasAtWar = false;
		totalTradePartners = 0;
		promiscuity = false;
		promiscuityDistance = p.width*0.2f;
		totalAllies = 0;

		r = 0;

		possibleStatements = new ArrayList<String>();
	}

	void display(){
		p.pushMatrix();
		p.translate(pos.x, pos.y);
		for(int i = 0; i < citizenPos.length; i++){
			citizenPos[i] = PVector.lerp(origCitizenPos[i], new PVector(0.0f, 0.0f), contraction);
			p.stroke(citizenCol[i], alphaNation*2.0f);
			p.noFill();
			p.strokeWeight(citizenSW[i]);
			p.ellipse(citizenPos[i].x, citizenPos[i].y, citizenRad[i], citizenRad[i]);
		}

		float[][] points = new float[(int)population][2];
		for(int i = 0; i < citizenPos.length; i++){
			points[i][0] = citizenPos[i].x;
			points[i][1] = citizenPos[i].y;
		}

		Hull cultureEnvelopeHull = new Hull(points);

		MPolygon nationBorders = cultureEnvelopeHull.getRegion();

		p.colorMode(PApplet.HSB);
		p.fill(col, 200, 200, alphaNation*0.5f);
		p.stroke(col, 200, 80, alphaNation);
		p.strokeWeight(hullWeight);
		float hullSW;
		hullSW = (int) PApplet.map(totalWealth, 50, 200, 1, 5);
		hullSW = (int) PApplet.constrain(hullSW, 1, 5);
		nationBorders.draw(p);
		p.noFill();
		p.colorMode(PApplet.RGB);
		if(isSelected){
			p.rectMode(PApplet.CENTER);
			p.strokeWeight(1);
			p.rect(0, 0, this.rad*1.1f, this.rad*1.1f);
			p.rect(0, 0, this.rad, this.rad);
		}
		p.popMatrix();
	}

	void debug(){
		p.textFont(PolSys.textFont);
		p.textSize(16);
		p.textAlign(PApplet.CENTER);
		p.fill(0);
		//p.text("culture: "+culturalHomogeneity, pos.x, pos.y-10);
		//p.text("wealth: "+totalWealth, pos.x, pos.y+10);
		//p.text("pos: "+citizenPos[0], pos.x, pos.y+10);
		//p.text("nationalSpending: "+nationalSpending, pos.x, pos.y+20);
		//p.text("war: "+atWar, pos.x, pos.y + 20);
		//p.text("statem: "+possibleStatements.size(), pos.x, pos.y - 20);
		//p.text("enemies: "+enemies.size(), pos.x+20, pos.y+30);
		//p.text("lerpVal: "+moveLerpVal, pos.x+20, pos.y+50);
	}

	void wander(){
		for(int i = 0; i < citizenPos.length; i++){
			if(citizenPos[i].x < pos.x+(p.width*0.1f) && citizenPos[i].x > pos.x-(p.width*0.1f)) citizenPos[i].x  += (p.noise(citizenNoiseX[i], noiseZ)-0.5f)*0.3f;
			if(citizenPos[i].y < pos.y+(p.height*0.1f) && citizenPos[i].y > pos.y-(p.height*0.1f)) citizenPos[i].y += (p.noise(citizenNoiseY[i], noiseZ)-0.5f)*0.3f;

			citizenNoiseX[i] += xincrement;
			citizenNoiseY[i] += yincrement;
		}
		noiseZ += zincrement;
	}

	void victoryBehaviour(Nation v, Nation e){

		if(victoryBehaviour == 1.5f){
			//nothing happens, and the loser survives and is listed as a rogue state
			for(int j = 0; j < currentWars.size(); j++){
				War w = currentWars.get(j);

				if((w.n1 == v && w.n2 == e) || (w.n2 == v && w.n1 == e)){
					v.enemies.remove(e);
					v.currentWars.remove(w);
					e.enemies.remove(v);
					e.currentWars.remove(w);
					PolSys.wars.remove(w);
					
					if(!canLerpWar){
						v.targetPos = new PVector((e.pos.x+v.pos.x)*0.5f, (e.pos.y+v.pos.y)*0.5f);
						moveLerpVal = 0;
						moveLerpMax = 0.1f; //moves the nation half-way
						v.canLerpWar = true;
					}

					if(!v.vanquished.contains(e)) v.vanquished.add(e);
					if(!e.vanquished.contains(v)) e.vanquished.add(v);
				}
			}
		}else if(victoryBehaviour == 1.0f){
			//victor survives but the nation moves a little bit and is being sort of annexed
			for(int j = 0; j < currentWars.size(); j++){
				War w = currentWars.get(j);
				if((w.n1 == v && w.n2 == e) || (w.n2 == v && w.n1 == e)){
					v.enemies.remove(e);
					v.currentWars.remove(w);
					e.enemies.remove(this);
					e.currentWars.remove(w);
					PolSys.wars.remove(w);
					e.isAnnexed = true; //this makes the country limited in resources
					if(!v.annexed.contains(e)) v.annexed.add(e);
					if(!e.annexed.contains(v)) e.annexed.add(v);
					
					if(!canLerpWar){
						v.targetPos = new PVector((e.pos.x+v.pos.x)*0.5f, (e.pos.y+v.pos.y)*0.5f);
						moveLerpVal = 0;
						moveLerpMax = 0.3f; //moves the nation half-way
						v.canLerpWar = true;
					}
				}
			}
		}else{
			//			//the nation moves all the way to where the loser once was
			//			for(int j = 0; j < currentWars.size(); j++){
			//				War w = currentWars.get(j);
			//				if((w.n1 == this && w.n2 == e) || (w.n2 == this && w.n1 == e)){
			//					this.enemies.remove(e);
			//					this.currentWars.remove(w);
			//					e.enemies.remove(this);
			//					e.currentWars.remove(w);
			//					PolSys.wars.remove(w);
			//					e.totalWealth = 0; //this makes the country disappear
			//					targetPos = new PVector((e.pos.x+this.pos.x)*0.5f, (e.pos.y+this.pos.y)*0.5f);
			//				}
			//			}

			//nothing happens, they are already dead
		}
	}

	String statement(int r){
		String s;
		s = "";

		float averageWealth = 0;
		float averageCulture = 0;
		for(int i = 0; i < PolSys.others.size(); i++){
			Nation n = PolSys.others.get(i);
			averageWealth += n.totalWealth;
			averageCulture += n.culturalHomogeneity;
		}

		averageWealth = averageWealth/PolSys.others.size();
		averageCulture = averageCulture/PolSys.others.size();

		if(enemies.size() == 0 && !tAtPeace){
			String thought = PolSys.tsAtPeace[(int)p.random(PolSys.tsAtPeace.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tAtPeace = true;
		}
		if(enemies.size() == 0 && this.wasAtWar && !tAtPeaceAgain){
			String thought = PolSys.tsAtPeaceAgain[(int)p.random(PolSys.tsAtPeaceAgain.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tAtPeaceAgain = true;
		}

		if(this.totalWealth > averageWealth && !tWealthy){
			String thought = PolSys.tsWealthyNation[(int)p.random(PolSys.tsWealthyNation.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tWealthy = true;
		}

		if(this.totalWealth < averageWealth && !tPoor){
			String thought = PolSys.tsPoor[(int)p.random(PolSys.tsPoor.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tPoor = true;
		}

		if(this.allies.size() > 0 && !tHasAllies){
			String thought = PolSys.tsHasAllies[(int)p.random(PolSys.tsHasAllies.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tHasAllies = true;
		}

		if(this.allies.size() == 0 && !tNoAllies){
			String thought = PolSys.tsNoAllies[(int)p.random(PolSys.tsNoAllies.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tNoAllies = true;
		}

		if(this.atWar && !tAtWar){
			String thought = PolSys.tsAtWar[(int)p.random(PolSys.tsAtWar.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tAtWar = true;
		}

		if(this.wasAtWar && !this.atWar && !tWasAtWar){
			String thought = PolSys.tsWasAtWar[(int)p.random(PolSys.tsWasAtWar.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tWasAtWar = true;
		}

		if(this.culturalHomogeneity > averageCulture*1.25f || this.culturalHomogeneity < averageCulture*0.75f && !tNotAverageCulture){
			String thought = PolSys.tsNotAverageCulture[(int)p.random(PolSys.tsNotAverageCulture.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tNotAverageCulture = true;
		}

		if(this.culturalHomogeneity < averageCulture*1.25f && this.culturalHomogeneity > averageCulture*0.75f && !tAverageCulture){
			String thought = PolSys.tsAverageCulture[(int)p.random(PolSys.tsAverageCulture.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tAverageCulture = true;
		}


		if(this.tradePartners.size() > 0 && !tHasTradePartners){
			String thought = PolSys.tsHasTradePartners[(int)p.random(PolSys.tsHasTradePartners.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tHasTradePartners = true;
		}

		if(this.tradePartners.size() == 0 && !tHasNoTradePartners){
			String thought = PolSys.tsNoTradePartners[(int)p.random(PolSys.tsNoTradePartners.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tHasNoTradePartners = true;
		}

		boolean hasNeighbors = true;

		for(int i = 0; i < PolSys.others.size(); i++){
			Nation n = PolSys.others.get(i);
			if(n.isRuined && !tNationDisappeared){
				String thought = PolSys.tsNationDisappeared[(int)p.random(PolSys.tsNationDisappeared.length)];
				if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
				tNationDisappeared = true;
			}

			if(PVector.dist(n.pos, this.pos) > neighborDistance){
				hasNeighbors = false;
			}
		}


		if(hasNeighbors && !tNoNeighbors){
			String thought = PolSys.tsNoNeighbors[(int)p.random(PolSys.tsNoNeighbors.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tNoNeighbors = true;
		}

		if(!hasNeighbors && !tHasNeighbors){
			String thought = PolSys.tsHasNeighbors[(int)p.random(PolSys.tsHasNeighbors.length)];
			if(!possibleStatements.contains(thought)) possibleStatements.add(thought);
			tHasNeighbors = true;
		}

		s = possibleStatements.get(PApplet.constrain(r, 0, possibleStatements.size()-1));

		return s;
	}

	boolean similarCulture(){
		boolean isSimilar = false;
		for(int i = 0; i < PolSys.others.size(); i++){
			Nation o = PolSys.others.get(i);
			if(PApplet.abs(this.culturalHomogeneity - o.culturalHomogeneity) < similarCultureThreshold){
				isSimilar = true;
			}
		}
		return isSimilar;
	}

	void update(){
		//wander();
		connectTrade();
		trade();
		if(p.millis() - PolSys.startTimeWar > PolSys.timerWar) war();
		alliances();
		evolveCulture();
		this.contraction = PApplet.map(this.totalWealth, 0.0f, 10000.0f, 0.00005f, 0.0f);
		this.contraction = PApplet.constrain(contraction, 0.0f, 0.00005f);
		this.culturalHomogeneity = PApplet.max(culturalHomogeneity, 0.0f); // never go below 0;
		this.col = (int)PApplet.map(culturalHomogeneity, 0.0f, 40.0f, 0.0f, 720.0f);
		for(int i = 0; i < allies.size(); i++){
			Nation a = allies.get(i);
			if(canLerpAlliance){
				this.pos = PVector.lerp(this.origPos, a.pos, allianceLerpVal);
				if(allianceLerpVal < allianceLerpMax){
					allianceLerpVal += allianceLerpInc;
				}else{
					this.origPos = this.pos;
					canLerpAlliance = false;
				}	
			}

		}

		if(canLerpWar){
			this.pos = PVector.lerp(this.origPos, targetPos, moveLerpVal);
			if(moveLerpVal < moveLerpMax){
				moveLerpVal += 0.001f;
			}else{
				canLerpWar = false;
				this.origPos = this.pos;
			}
		}


		if(this.enemies.size() > 0){
			this.atWar = true;
		}else{
			this.atWar = false;
		}

		if(!isRuined && totalWealth < 1000.0f){
			alphaNation = PApplet.map(totalWealth, 50, 1000.0f, 100, 230);
			hullWeight = (int) PApplet.map(totalWealth, 50, 1000.0f, 1, 10);
		}else if(totalWealth > 1000.0f){
			alphaNation = 230.0f;
			hullWeight = 10;
		}

		if(p.millis() - startTimeVanquished > timerVanquished){
			if(vanquished.size() > 0) vanquished.remove(0); //nations forget who they already fought
		}

		if(isAnnexed) totalWealth = PApplet.min(50.0f, totalWealth);

		if(distanceAlliance < p.width) distanceAlliance += 0.1f;
		if(distanceTrade < p.width) distanceTrade += 0.15f; 

		if(!promiscuity){
			for(int i = 0; i < PolSys.others.size(); i++){
				Nation n = PolSys.others.get(i);
				if(PVector.dist(this.pos, n.pos) < promiscuityDistance){
					promiscuity = true;
				}
			}
		}


		for(int i = 0; i < this.allies.size(); i++){
			Nation a = allies.get(i);
			for(int j = 0; j < a.enemies.size(); j++){
				Nation e = a.enemies.get(j);

				if(allianceTrust == 0.5f){
					culturalRequirementAlliance = culturalRequirementAllianceTrust;
					if(!this.enemies.contains(e) && !e.enemies.contains(this)){
						War w = new War(this, e, p);
						PolSys.wars.add(w);
						this.enemies.add(e);
						this.currentWars.add(w);
						e.currentWars.add(w);
						e.enemies.add(this);
					}
				}else if(allianceTrust == 1.5f){
					culturalRequirementAlliance = culturalRequirementAllianceMistrust;
					if(e.culturalHomogeneity < this.culturalHomogeneity){
						this.culturalHomogeneity += cultureModifierWar;
					}else{
						this.culturalHomogeneity -= cultureModifierWar;
					}
				}

			}
		}


		if(totalWealth < povertyThreshold && !isPoor){ //when you're poor, it's harder to make money, harder to make friends, and easier to go to war
			poorRevenueMultiplier *= 0.5f;
			culturalRequirementAlliance *= 5.0f;
			culturalRequirementWar *= 0.5f;
			isPoor = true;
		}

		if(totalWealth > povertyThreshold && isPoor){
			poorRevenueMultiplier *= 2.0;
			culturalRequirementAlliance *= 0.2f;
			culturalRequirementWar *= 2.0f;
			isPoor = false;
		}

		if(totalWealth <= 1){
			this.isRuined = true;
		}

		if(isRuined){
			for(int i = 0; i < this.currentWars.size(); i++){
				War w = currentWars.get(i);

				w.eWar.addSegment(0.0f, 100.0f, new KillTrigger(w.gWar));
			}
			contraction += 0.0001f;
			alphaNation -= 2.0f;
		}

		if(alphaNation < 10){
			collapse();
		}

		for(int i = 0; i < enemies.size(); i++){
			Nation e = enemies.get(i);
			if(e.totalWealth < wealthStopWar){
				PApplet.println("victory behaviour engaged!");
				victoryBehaviour(this, e);
			}
		}
	}

	void collapse(){
		for(int i = 0; i < this.currentAlliances.size(); i++){
			Alliance a = currentAlliances.get(i);
			if(a.n1 == this){
				a.n2.allies.remove(this);
			}else{
				a.n2.allies.remove(this);
			}
			PolSys.alliances.remove(a);
		}

		for(int i = 0; i < this.currentTrades.size(); i++){
			Trade t = currentTrades.get(i);
			if(t.n1 == this){
				t.n2.tradePartners.remove(this);
			}else{
				t.n2.tradePartners.remove(this);
			}
			PolSys.trades.remove(t);
		}

		for(int i = 0; i < this.currentWars.size(); i++){
			War w = currentWars.get(i);
			if(w.n1 == this){
				w.n2.enemies.remove(this);
			}else{
				w.n2.enemies.remove(this);
			}
			PolSys.wars.remove(w);
		}

		PolSys.others.remove(this);
	}

	void connectTrade(){
		for(int i = 0; i < PolSys.others.size(); i++){
			Nation n = PolSys.others.get(i);

			if(PApplet.dist(this.pos.x, this.pos.y, n.pos.x, n.pos.y) < this.distanceTrade && this != n && this.tradePartnerships > 0 && n.tradePartnerships > 0 && PApplet.abs(this.culturalHomogeneity-n.culturalHomogeneity)  < culturalRequirementTrade && !this.enemies.contains(n)){
				boolean foundTrade = false;
				for(int j = 0; j < PolSys.trades.size(); j++){
					Trade t = PolSys.trades.get(j);
					if((t.n1 == this && t.n2 == n) || (t.n1 == n && t.n2 == this)){
						foundTrade = true;
						break;
					}
				}

				if(!foundTrade){
					Trade t = new Trade(this, n, p);
					PolSys.trades.add(t);

					this.currentTrades.add(t);
					this.tradePartners.add(n);
					if(!this.tradePartners.contains(n)) this.tradePartners.add(n);
					n.currentTrades.add(t);
					n.tradePartners.add(this);
					if(!n.tradePartners.contains(this)) n.tradePartners.add(this);

					this.tradePartnerships--;
					n.tradePartnerships--;

					this.totalTradePartners++;
				}
			}else if(PApplet.abs(this.culturalHomogeneity - n.culturalHomogeneity) > culturalRequirementTrade){ //remove part
				for(int j = 0; j < PolSys.trades.size(); j++){
					Trade t = PolSys.trades.get(j);

					if((t.n1 == this && t.n2 == n) || (t.n1 == n) && (t.n2 == this)){

						PolSys.trades.remove(t);

						this.currentTrades.remove(t);
						this.tradePartners.remove(n);
						n.currentTrades.remove(t);
						n.tradePartners.remove(this);

						this.tradePartnerships++;
						n.tradePartnerships++;
					}
				}
			}
		}
	}

	void alliances(){
		for(int i = 0; i < PolSys.others.size(); i++){
			Nation n = PolSys.others.get(i);
			if(!enemies.contains(i)){
				if(PApplet.dist(this.pos.x, this.pos.y, n.pos.x, n.pos.y) < this.distanceAlliance && this != n && PApplet.abs(this.culturalHomogeneity-n.culturalHomogeneity)  < culturalRequirementAlliance && !this.rogueStates.contains(n)){
					boolean found = false;
					for(int j = 0; j < PolSys.alliances.size(); j++){
						Alliance a = PolSys.alliances.get(j);
						if((a.n1 == this && a.n2 == n) || (a.n1 == n && a.n2 == this)){
							found = true;
							break;
						}
					}

					if(!found){
						Alliance a = new Alliance(this, n, p);
						PolSys.alliances.add(a);

						this.currentAlliances.add(a);
						if(!this.allies.contains(n)) this.allies.add(n);
						n.currentAlliances.add(a);
						n.allies.add(this);

						if(!canLerpAlliance){
							this.allianceLerpVal = 0;
							n.allianceLerpVal = 0;
							canLerpAlliance = true;	
						}
					}
				}else if(PApplet.abs(this.culturalHomogeneity - n.culturalHomogeneity) > culturalRequirementAlliance){ //remove part
					for(int j = 0; j < PolSys.alliances.size(); j++){
						Alliance a = PolSys.alliances.get(j);

						if((a.n1 == this && a.n2 == n) || (a.n1 == n) && (a.n2 == this)){

							PolSys.alliances.remove(a);

							this.currentAlliances.remove(a);
							this.allies.remove(n);
							n.currentAlliances.remove(a);
							n.allies.remove(this);
						}
					}
				}
			}
		}
	}

	void evolveCulture(){
		//culture evolves because of internal intricacies
		float cultureVariance = (p.noise(endemicCultureNoise)-0.5f)*endemicCultureModifier;
		culturalHomogeneity += cultureVariance;

		for(int i = 0; i < PolSys.others.size(); i++){
			Nation o = PolSys.others.get(i);
			if(PApplet.dist(this.pos.x, this.pos.y, o.pos.x, o.pos.y) < culturalProximity){
				if(o.culturalHomogeneity < this.culturalHomogeneity){
					this.culturalHomogeneity -= culturalModifierProximity*(PApplet.map(PApplet.dist(this.pos.x, this.pos.y, o.pos.x, o.pos.y), 0.0f, culturalProximity, 2.0f, 0.5f));
				}else{
					this.culturalHomogeneity += culturalModifierProximity*(PApplet.map(PApplet.dist(this.pos.x, this.pos.y, o.pos.x, o.pos.y), 0.0f, culturalProximity, 2.0f, 0.5f));
				}
			}

			if(this.tradePartners.contains(o) && !this.allies.contains(o)){ //if you trade but not allied, culture goes opposite
				if(this.culturalHomogeneity > o.culturalHomogeneity){
					this.culturalHomogeneity += cultureModifierTrade;
				}else{
					this.culturalHomogeneity -= cultureModifierTrade;
				}
			}
		}

		//culture evolves through trade
		for(int i = 0; i < tradePartners.size(); i++){
			Nation tp = tradePartners.get(i);

			if(PApplet.abs(tp.totalWealth - this.totalWealth) < wealthDifferenceThreshold){ //trade is more or less equal
				if(this.culturalHomogeneity > tp.culturalHomogeneity){
					this.culturalHomogeneity += cultureModifierTrade;
				}else{
					this.culturalHomogeneity -= cultureModifierTrade;
				}
			}else{ //trade is unequal
				if(this.culturalHomogeneity > tp.culturalHomogeneity){
					this.culturalHomogeneity -= cultureModifierTrade*2.0f; //additional multiplier to make the switch more radical
				}else{
					this.culturalHomogeneity += cultureModifierTrade*2.0f;
				}
			}
		}

		//culture evolves because of alliances (pro-allies / who is the head of allies?)
		for(int i = 0; i < allies.size(); i++){
			Nation a = allies.get(i);
			if(a.culturalHomogeneity > this.culturalHomogeneity){
				this.culturalHomogeneity += cultureModifierAlliance;
			}else{
				this.culturalHomogeneity -= cultureModifierAlliance;
			}
		}

		//and culture evolves because of war (anti average of enemies)
		for(int i = 0; i < enemies.size(); i++){
			Nation e = enemies.get(i);
			if(e.culturalHomogeneity > this.culturalHomogeneity){
				this.culturalHomogeneity -= cultureModifierWar;
			}else{
				this.culturalHomogeneity += cultureModifierWar;
			}
		}
	}

	void trade(){		
		//first money fluctuates endemically
		float nationalWealthEvolution = 1.0f;
		if(p.noise(p.millis()*0.001f)-0.5f < 0.0f){
			//nationalWealthEvolution = 10.0f; - debug for quick death
			nationalWealthEvolution = -1.0f;
		}
		totalWealth -= nationalSpending*nationalWealthEvolution;

		//second trade makes you earn more money
		for(int i = 0; i < tradePartners.size(); i++){
			Nation tp = tradePartners.get(i);
			float tradeRevenue; //trade revenue is a fixed amount (percent of totalwealth) modulated by whoever is on the losing or winning side
			if(tp.totalWealth > this.totalWealth){
				tradeRevenue = totalWealth*poorRevenueMultiplier*(int)p.random(-1, 0.75f);
			}else{
				tradeRevenue = totalWealth*wealthyRevenueMultiplier*(int)p.random(-0.75f, 1);
			}

			totalWealth += tradeRevenue;
		}

		//third, if you're at war, you lose money (fast if you're on the losing side / slow if you're on the winning side)
		if(this.atWar){
			for(int i = 0; i < enemies.size(); i++){
				Nation e = enemies.get(i);
				if(e.allies.size() > this.allies.size()){
					this.totalWealth -= (loserEconomicDepletion)*this.enemies.size();
				}else{
					this.totalWealth -= (winnerEconomicDepletion)*this.enemies.size();
				}
			}
		}

		totalWealth = PApplet.constrain(totalWealth, 0, 10000);
	}

	void war(){
		//if cultural difference is too bad, then war
		for(int i = 0; i < PolSys.others.size(); i++){
			Nation n = PolSys.others.get(i);

			if(PApplet.dist(this.pos.x, this.pos.y, n.pos.x, n.pos.y) < this.distanceWar && this != n && PApplet.abs(this.culturalHomogeneity-n.culturalHomogeneity)  > culturalRequirementWar && n.totalWealth > wealthStopWar*1.1f && !this.isRuined && !n.isRuined && !this.vanquished.contains(n)){
				boolean found = false;
				for(int j = 0; j < PolSys.wars.size(); j++){
					War w = PolSys.wars.get(j);
					if((w.n1 == this && w.n2 == n) || (w.n1 == n && w.n2 == this)){
						found = true;
						break;
					}
				}

				if(!found){
					War w = new War(this, n, p);
					PolSys.wars.add(w);
					wasAtWar = true;
					this.currentWars.add(w);
					if(!this.enemies.contains(n)) this.enemies.add(n);
					n.currentWars.add(w);
					if(!n.enemies.contains(this)) n.enemies.add(this);
				}
			}else if(PApplet.abs(this.culturalHomogeneity - n.culturalHomogeneity) > culturalRequirementWar || this.isRuined || n.isRuined){
				for(int j = 0; j < PolSys.wars.size(); j++){
					War w = PolSys.wars.get(j);

					if((w.n1 == this && w.n2 == n) || (w.n1 == n) && (w.n2 == this)){
						w.eWar.addSegment(0.0f, 500.0f, new KillTrigger(w.gWar));
						PolSys.wars.remove(w);

						this.currentWars.remove(w);
						this.enemies.remove(n);
						n.currentWars.remove(w);
						n.enemies.remove(this);
					}
				}
			}
		}
	}
}

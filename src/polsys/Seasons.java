package polsys;

import beads.Envelope;
import beads.WavePlayer;
import beads.Gain;
import beads.Buffer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Seasons extends ProcessingObject {
	
	PApplet p;
	
	//-------------- Sound
	int numberOfWaves;
	
	WavePlayer[] wpA4;
	WavePlayer[] wpA5;
	WavePlayer[] wpB4;
	WavePlayer[] wpC4;
	WavePlayer[] wpC5;
	WavePlayer[] wpD4;
	WavePlayer[] wpE;
	WavePlayer[] wpF;
	WavePlayer[] wpG;
	
	Gain[] gA4;
	Gain[] gA5;
	Gain[] gB4;
	Gain[] gC4;
	Gain[] gC5;
	Gain[] gD4;
	Gain[] gE;
	Gain[] gF;
	Gain[] gG;
	
	Gain gCMaj7;
	Gain gFMaj7;
	Gain gAm;
	Gain gDm7;
	
	Gain gSeason;
	
	Envelope eCMaj7;
	Envelope eFMaj7;
	Envelope eAm;
	Envelope eDm7;
	
	Envelope eSeason;
	
	String[] fall = {
		      "leaves are falling", "trees turn gold", "rain covers the land", "fall is here"
		    };
	String[] winter = {
	      "crystals are pouring from the sky", "everything turned white", "snow crackles under their feet", "the death of winter"
		};
	String[] spring = {
	      "blooms appear", "leaves cover branches", "flowers return", "spring has come"
	    };
	String[] summer = {
	      "the earth is burning", "a symphony of crickets", "the sun heats all", "summer will not last forever"
		};
	    
	int fallCol;
	int winterCol;
	int springCol;
	int summerCol;
	  
	int currentColor;

	String currentSeason;

	float x;
	float y;
	float speed;
	float alpha;
	  
	int targetColor;
	int currentFillColor;
	float lerpCol;
	  
	float seasonsAlpha;

	public static int numberOfSeasons;
	int numberOfCycles;

	int r;
	  
	static float timer;
	static float startTime;
	  
	PVector[] fallingPos;
	float[] fallingSpeed;
	float[] oscillator;
	float[] oscillationSpeed;
	float[] oscillationBreadth;

	Seasons(PApplet p) {
		this.p = p;
		x = p.width*0.5f;
		y = p.height*0.15f;
		speed = 2.0f;
		currentSeason = "";
		numberOfSeasons = 0;
		numberOfCycles = 0;
		timer = 6000.0f; //how long seasons last
		startTime = p.millis();
		r = (int)(p.random(fall.length));
		fallCol = p.color(242, 138, 0);
		winterCol = p.color(175);
		springCol = p.color(124, 67, 80);
		summerCol = p.color(198, 96, 92);
	
		seasonsAlpha = 0;
	
		currentFillColor = p.color(202, 108, 0, 50);
	
		fallingPos = new PVector[60];
		fallingSpeed = new float[fallingPos.length];
		oscillator = new float[fallingPos.length];
		oscillationSpeed = new float[fallingPos.length];
		oscillationBreadth = new float[fallingPos.length];
	
		for(int i = 0; i < fallingPos.length; i++){
			fallingPos[i] = new PVector(p.random(-p.width*0.5f, 0), p.random(0, p.height));
			fallingSpeed[i] = p.random(1.0f, 4.0f);
			oscillator[i] = p.random(1);
			oscillationSpeed[i] = p.random(0.005f, 0.05f);
			oscillationBreadth[i] = p.random(3.0f, 5.0f);
		}
	
		numberOfWaves = 5;
	
		//cmaj7 = spring
		//fmaj7 = summer
		//am = fall
		//dm7 = winter
		
		eCMaj7 = new Envelope(PolSys.ac, 0.0f);
		eFMaj7 = new Envelope(PolSys.ac, 1.0f);
		eAm = new Envelope(PolSys.ac, 0.0f);
		eDm7 = new Envelope(PolSys.ac, 0.0f);
		
		eSeason = new Envelope(PolSys.ac, 0.0f);
		
		gSeason = new Gain(PolSys.ac, 4, eSeason);
		
		gCMaj7 = new Gain(PolSys.ac, 4*numberOfWaves, eCMaj7);
		gFMaj7 = new Gain(PolSys.ac, 4*numberOfWaves, eFMaj7);
		gAm = new Gain(PolSys.ac, 4*numberOfWaves, eAm);
		gDm7 = new Gain(PolSys.ac, 4*numberOfWaves, eDm7);
		
		wpA4 = new WavePlayer[numberOfWaves];
		wpA5 = new WavePlayer[numberOfWaves];
		wpB4 = new WavePlayer[numberOfWaves];
		wpC4 = new WavePlayer[numberOfWaves];
		wpC5 = new WavePlayer[numberOfWaves];
		wpD4 = new WavePlayer[numberOfWaves];	
		wpE = new WavePlayer[numberOfWaves];	
		wpF = new WavePlayer[numberOfWaves];	
		wpG = new WavePlayer[numberOfWaves];
		
		gA4 = new Gain[numberOfWaves];
		gA5 = new Gain[numberOfWaves];
		gB4 = new Gain[numberOfWaves];
		gC4 = new Gain[numberOfWaves];
		gC5 = new Gain[numberOfWaves];
		gD4 = new Gain[numberOfWaves];	
		gE = new Gain[numberOfWaves];	
		gF = new Gain[numberOfWaves];	
		gG = new Gain[numberOfWaves];
		
		for(int i = 0; i < numberOfWaves; i++){
			//generating all the waveforms for each note
			wpA4[i] = new WavePlayer(PolSys.ac, 440.0f, Buffer.SINE);
			wpA5[i] = new WavePlayer(PolSys.ac, 880.0f, Buffer.SINE);
			wpB4[i] = new WavePlayer(PolSys.ac, 493.883f, Buffer.SINE);
			wpC4[i] = new WavePlayer(PolSys.ac, 261.626f, Buffer.SINE);
			wpC5[i] = new WavePlayer(PolSys.ac, 523.251f, Buffer.SINE);
			wpD4[i] = new WavePlayer(PolSys.ac, 293.665f, Buffer.SINE);
			wpE[i] = new WavePlayer(PolSys.ac, 329.628f, Buffer.SINE);
			wpF[i] = new WavePlayer(PolSys.ac, 349.228f, Buffer.SINE);
			wpG[i] = new WavePlayer(PolSys.ac, 391.995f, Buffer.SINE);
			
			//giving them their own individual gain, with some randomness
			gA4[i] = new Gain(PolSys.ac, 1, p.random(0.1f, 0.2f));
			gA5[i] = new Gain(PolSys.ac, 1, p.random(0.1f, 0.2f));
			gB4[i] = new Gain(PolSys.ac, 1, p.random(0.1f, 0.2f));
			gC4[i] = new Gain(PolSys.ac, 1, p.random(0.1f, 0.2f));
			gC5[i] = new Gain(PolSys.ac, 1, p.random(0.1f, 0.2f));
			gD4[i] = new Gain(PolSys.ac, 1, p.random(0.1f, 0.2f));
			gE[i] = new Gain(PolSys.ac, 1, p.random(0.1f, 0.2f));
			gF[i] = new Gain(PolSys.ac, 1, p.random(0.1f, 0.2f));
			gG[i] = new Gain(PolSys.ac, 1, p.random(0.1f, 0.2f));
			
			//plugging each waveform into each gain
			gA4[i].addInput(wpA4[i]);
			gA5[i].addInput(wpA5[i]);
			gB4[i].addInput(wpB4[i]);
			gC4[i].addInput(wpC4[i]);
			gC5[i].addInput(wpC5[i]);
			gD4[i].addInput(wpD4[i]);
			gE[i].addInput(wpE[i]);
			gF[i].addInput(wpF[i]);
			gG[i].addInput(wpG[i]);
		}
		
		for(int i = 0; i < gA4.length; i++){ //plugging each note in their respective chords gain
			gCMaj7.addInput(gC4[i]);
			gCMaj7.addInput(gE[i]);
			gCMaj7.addInput(gG[i]);
			gCMaj7.addInput(gB4[i]);
			
			gFMaj7.addInput(gF[i]);
			gFMaj7.addInput(gA5[i]);
			gFMaj7.addInput(gC5[i]);
			gFMaj7.addInput(gE[i]);
			
			gAm.addInput(gA4[i]);
			gAm.addInput(gC4[i]);
			gAm.addInput(gE[i]);
			gAm.addInput(gA5[i]);
			
			gDm7.addInput(gD4[i]);
			gDm7.addInput(gA4[i]);
			gDm7.addInput(gF[i]);
			gDm7.addInput(gC5[i]);
		}
		
		gSeason.addInput(gCMaj7);
		gSeason.addInput(gFMaj7);
		gSeason.addInput(gAm);
		gSeason.addInput(gDm7);
		
		PolSys.gMasterSeasons.addInput(gSeason);
		
  }
  
  void cycle(){
	  eSeason.addSegment(0.05f, 750.0f);
    if(numberOfSeasons == 0){
      currentSeason = fall[r];
      currentColor = fallCol;
      PolSys.newBgColBox = p.color(214, 182, 27);
    }else if(numberOfSeasons == 1){
      currentSeason = winter[r];
      currentColor = winterCol;
      PolSys.newBgColBox = p.color(215, 240, 237);

    } if(numberOfSeasons == 2){
      currentSeason = spring[r];
      currentColor = springCol;
      PolSys.newBgColBox = p.color(255, 229, 244);
    }else if(numberOfSeasons == 3){
      currentSeason = summer[r];
      currentColor = summerCol;
      PolSys.newBgColBox = p.color(192, 237, 134);
    }
    
    if(p.millis() - timer > startTime && numberOfSeasons < 3){
    	 if(numberOfSeasons == 0){ //fall
		    	for(int i = 0; i < numberOfWaves; i++){
		    		gA4[i].setValue(p.random(0.2f, 0.8f));
		    	}
		      eAm.addSegment(1.0f, 2000.0f); //fall rising
		      eFMaj7.addSegment(0.0f, 2000.0f); //summer declining
		    }else if(numberOfSeasons == 1){
		    	for(int i = 0; i < numberOfWaves; i++){
		    		gD4[i].setValue(p.random(0.2f, 0.8f));
		    	}
		      eDm7.addSegment(1.0f, 2000.0f); //winter rising
		      eAm.addSegment(0.0f, 2000.0f); //fall declining
		    }else if(numberOfSeasons == 2){
		    	eCMaj7.addSegment(1.0f, 2000.0f);  //spring rising
		    	for(int i = 0; i < numberOfWaves; i++){
		    		gC4[i].setValue(p.random(0.2f, 0.8f));
		    	}
		    	eDm7.addSegment(0.0f, 2000.0f); //winter declining
		    }
      numberOfSeasons++;
      numberOfCycles++;
      r = (int)(p.random(fall.length));
	   
      startTime = p.millis();
      lerpCol = 0.0f;
    }
    
    if(p.millis() - timer > startTime && numberOfSeasons == 3){
      numberOfCycles++;
      numberOfSeasons = 0;
      r = (int)(p.random(fall.length));
    	for(int i = 0; i < numberOfWaves; i++){
    		gF[i].setValue(p.random(0.2f, 0.8f));
    	}
      eFMaj7.addSegment(1.0f, 2000.0f); //summer rising
      eCMaj7.addSegment(0.0f, 2000.0f); //spring declining
      startTime = p.millis();
      lerpCol = 0.0f;
    }
    
    alpha = PApplet.map(p.millis()-startTime, 0.0f, 6000.0f, -(PApplet.PI/2), (PApplet.PI/2)*3.0f);
    seasonsAlpha = (PApplet.sin(alpha)+1)*15;
    currentColor = p.color(p.red(currentColor)-20, p.green(currentColor)-20, p.blue(currentColor)-20);
    
    
    // ------ display stuff
	    p.textAlign(PApplet.CENTER);
	    p.textSize(PolSys.seasonsFontSize);
	    p.textFont(PolSys.seasonsFont);
	    p.fill(currentColor, seasonsAlpha);
	    p.text(currentSeason, x, y);		    
	  }
	  
	  void populate(int s){
		  if(s == 0){
			  targetColor = p.color(202, 108, 30, 30);
		  }else if(s == 1){
			  targetColor = p.color(255, 200);
		  }else if(s == 2){
			  targetColor = p.color(245, 209, 209, 70);
		  }else{
			  targetColor = p.color(182, 237, 114, 70);
		  }
		  p.noStroke();
		  
		  currentFillColor = p.lerpColor(currentFillColor, targetColor, lerpCol);
		  if(lerpCol < 1.0f) lerpCol += 0.005f;
		  p.fill(currentFillColor);
		  
		  for(int i = 0; i < fallingPos.length; i++){
			  p.pushMatrix();
			  p.rect(fallingPos[i].x, fallingPos[i].y, 20, 2);
			  p.popMatrix();
			  
			  fallingPos[i].x += fallingSpeed[i];
			  fallingPos[i].y += PApplet.cos(oscillator[i])*oscillationBreadth[i];
			  oscillator[i] += oscillationSpeed[i];
			  
			  if(fallingPos[i].x > PolSys.rectBorderW+PolSys.rectBorderX){
				  fallingPos[i].x = p.random(-20, 0);
			  }
		  }
	  }
}

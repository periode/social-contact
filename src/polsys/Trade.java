package polsys;

import beads.Buffer;
import beads.Envelope;
import beads.Gain;
import beads.KillTrigger;
import beads.WavePlayer;
import processing.core.PApplet;
import processing.core.PVector;

public class Trade {
	
	PApplet p;
	PVector pos;
	Nation n1;
	Nation n2;
	Nation seller;
	Nation buyer;
	float volume;
	
	int vesselsNum;
	PVector[] vesselsPos;
	float[] vesselsLerp;
	float lerpAmount;
	float vesselsSize;
	
	float theta;
	
	int col;
	int sw;
	float alpha;
	float alphaMax;
	float alphaInc;
	
	  int numHarmonics;
	  WavePlayer[] wp;
	  Gain[] g;
	  Envelope[] e;
	  
	  Gain gTrade;
	
	Trade(){}
	
	Trade(Nation n1, Nation n2, PApplet p){
		this.p = p;
		this.n1 = n1;
		this.n2 = n2;
		pos = new PVector((n1.pos.x+n2.pos.x)*0.5f, (n1.pos.y+n2.pos.y)*0.5f);
		this.volume = PApplet.abs(n1.totalWealth - n2.totalWealth);
		this.vesselsSize = (int) PApplet.map(volume, 0, 100, 1, 20);
		vesselsNum = (int)(p.random(2, 6));
		vesselsPos = new PVector[vesselsNum];
		vesselsLerp = new float[vesselsNum];
		if(n1.totalWealth > n2.totalWealth){
			seller = n1;
			buyer = n2;
		}else{
			seller = n2;
			buyer = n1;
		}
		for(int i = 0; i < vesselsNum; i++){
			vesselsLerp[i] = i*0.2f;
			vesselsPos[i] = new PVector(seller.pos.x, seller.pos.y);
		}
		
		lerpAmount = 0.01f+p.random(0.005f);
		
		theta = 0;
		
		alpha = -50;
		alphaMax = 100;
		alphaInc = 1.0f;
		col = p.color(0, 100, 200); 
		sw = 10;
		
		numHarmonics = 1;
	    wp = new WavePlayer[numHarmonics];
	    e = new Envelope[numHarmonics];
	    g = new Gain[numHarmonics];
	    gTrade = new Gain(PolSys.ac, numHarmonics, 0.1f);
	    
	    for(int i = 0; i < numHarmonics; i++){
	    	if(p.random(1) < 0.5f){
	    		wp[i] = new WavePlayer(PolSys.ac,  783.991f, Buffer.SINE);
	    	}else{
	    		wp[i] = new WavePlayer(PolSys.ac, 659.255f, Buffer.SINE);
	    	}
	    	e[i] = new Envelope(PolSys.ac, 0.0f);
	    	g[i] = new Gain(PolSys.ac, 1, e[i]);
	    	g[i].addInput(wp[i]);
	    	gTrade.addInput(g[i]);
	    }
	    
	    PolSys.ac.out.addInput(gTrade);
		
	}
	
	void display(){
		update();
		p.stroke(col, alpha);
		p.strokeWeight(2);
		p.rectMode(PApplet.CENTER);
		for(int i = 0; i < vesselsNum; i++){
			vesselsPos[i] = PVector.lerp(seller.pos, buyer.pos, vesselsLerp[i]);
			p.pushMatrix();
			p.translate(vesselsPos[i].x, vesselsPos[i].y);
			p.rotate(theta);
			p.rect(0, 0, 20, vesselsSize);
			p.popMatrix();
			vesselsLerp[i] += lerpAmount;
			if(vesselsLerp[i] > 1 && !PolSys.fading){
				vesselsLerp[i] = 0;
				for(int j = 0; j < numHarmonics; j++){
					float volumeMax = p.random(0.55f, 0.6f)/PolSys.trades.size();
					e[j].addSegment(volumeMax, 1.0f);
					e[j].addSegment(volumeMax*0.5f, 40.0f);
					e[j].addSegment(0.0f, 400.0f);
				}
			}
		}
		

		p.fill(0);
		p.noFill();
	}
	
	void update(){
		if(alpha < alphaMax) alpha += alphaInc;
		this.volume = PApplet.abs(n1.totalWealth - n2.totalWealth);
		this.volume = PApplet.constrain(volume, 0.0f, 10000.0f);
		this.vesselsSize = (int) PApplet.map(volume, 0, 100, 2, 10);
		this.vesselsSize = PApplet.constrain(vesselsSize, 2, 10);
		
		PVector dir = PVector.sub(buyer.pos, seller.pos);
		theta = dir.heading2D(); //+ PApplet.PI*0.5f;
	}

}

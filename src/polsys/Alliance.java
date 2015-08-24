package polsys;

import beads.Buffer;
import beads.Envelope;
import beads.Gain;
import beads.KillTrigger;
import beads.WavePlayer;
import processing.core.PApplet;
import processing.core.PVector;

public class Alliance {

	PApplet p;
	PVector pos;
	Nation n1;
	Nation n2;
	float strength;
	
	int col;
	int sw;
	int swMin;
	float alpha;
	float alphaMax;
	float alphaInc;
	
	int numConnecs;
	
	int numHarmonics;
	WavePlayer[] wp;
	Gain[] g;
	Envelope[] e;
	  
	Gain gAlliance;
	
	Alliance(){}
	
	Alliance(Nation n1, Nation n2, PApplet p){
		this.p = p;
		this.n1 = n1;
		this.n2 = n2;
		pos = new PVector((n1.pos.x+n2.pos.x)*0.5f, (n1.pos.y+n2.pos.y)*0.5f);
		this.strength = PApplet.abs(n1.culturalHomogeneity-n2.culturalHomogeneity);
		
		alpha = 0;
		alphaMax = 150;
		alphaInc = 0.5f;
		col = p.color(80, 150, 80);
		sw = 100;
		swMin = 2;
		
		 numHarmonics = 2;
		    wp = new WavePlayer[numHarmonics];
		    e = new Envelope[numHarmonics];
		    g = new Gain[numHarmonics];
		    gAlliance = new Gain(PolSys.ac, numHarmonics, 0.1f);
		    
		    for(int i = 0; i < numHarmonics; i++){
		    	if(i % 2 == 0){
		    		wp[i] = new WavePlayer(PolSys.ac,  523.251f, Buffer.SINE);
		    	}else{
		    		wp[i] = new WavePlayer(PolSys.ac, 329.628f, Buffer.SINE);
		    	}
		    	e[i] = new Envelope(PolSys.ac, 0.0f);
		    	g[i] = new Gain(PolSys.ac, 1, e[i]);
		    	g[i].addInput(wp[i]);
		    	gAlliance.addInput(g[i]);
		    	e[i].addSegment(p.random(0.05f, 0.1f), 200.0f);
		    	e[i].addSegment(0.0f, p.random(500.0f, 1000.0f), new KillTrigger(g[i]));
		    }
		    
		    PolSys.ac.out.addInput(gAlliance);
	}
	
	void display(){
		update();
		p.strokeCap(PApplet.PROJECT);
		p.stroke(col, alpha);
		p.strokeWeight(sw);
		for(int i = 0; i < numConnecs; i++){
			p.line(n1.citizenPos[i].x+n1.pos.x, n1.citizenPos[i].y+n1.pos.y, n2.citizenPos[i].x+n2.pos.x, n2.citizenPos[i].y+n2.pos.y);
		}
		p.stroke(PApplet.ROUND);
	}
	
	void update(){
		numConnecs = (int)(p.min(n1.citizenPos.length, n2.citizenPos.length)*0.25f);
		if(alpha < alphaMax) alpha += alphaInc;
		if(sw > swMin) sw -= 1.0f;
	}
	
}

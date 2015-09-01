package polsys;

import beads.Buffer;
import beads.Envelope;
import beads.Gain;
import beads.KillTrigger;
import beads.WavePlayer;
import processing.core.PApplet;
import processing.core.PVector;

public class War {

	PApplet p;
	PVector pos;
	Nation n1;
	Nation n2;
	float intensity;
	
	int col;
	int sw;
	float alpha;
	float alphaMax;
	float alphaInc;
	
	Nation larger;
	Nation smaller;
	
	int lineNum;
	PVector halfway;
	float theta;
	
	float lerp;
	
	  int numHarmonics;
	  Envelope eWar;
	  Gain gWar;
	  WavePlayer[] wp;
	  Gain[] g;
	  Envelope[] e;
	
	War(){}
	
	War(Nation n1, Nation n2, PApplet p){
		this.p = p;
		this.n1 = n1;
		this.n2 = n2;
		
		intensity = PApplet.abs(n1.culturalHomogeneity - n2.culturalHomogeneity);
		alpha = 0;
		alphaMax = 150;
		alphaInc = 1.0f;
		col = PolSys.colorWar;
		lineNum = 7; //TODO make this proportional to the wealth of belligerants
		halfway = new PVector((n1.pos.x+n2.pos.x)*0.5f, (n1.pos.y+n2.pos.y)*0.5f);
		
	    numHarmonics = PApplet.max(1, 5/(PolSys.wars.size()+1));
	    wp = new WavePlayer[numHarmonics];
	    e = new Envelope[numHarmonics];
	    g = new Gain[numHarmonics];
	    eWar = new Envelope(PolSys.ac, 0.075f/(PolSys.wars.size()+1)); //this is where I normalize it when I create it
	    gWar = new Gain(PolSys.ac, numHarmonics, eWar);
	    //eWar.addSegment(0.0f, 1500.0f, new KillTrigger(gWar));
	    
	    for(int i = 0; i < numHarmonics; i++){
	    	if(i % 2 == 0){
	    		wp[i] = new WavePlayer(PolSys.ac, 110.0f, Buffer.SQUARE);
	    	}else{
	    		wp[i] = new WavePlayer(PolSys.ac, 110.0f + p.random(10), Buffer.SAW);
	    	}
	    	e[i] = new Envelope(PolSys.ac, 0.0f);
	    	g[i] = new Gain(PolSys.ac, 1, e[i]);
	    	g[i].addInput(wp[i]);
	    	gWar.addInput(g[i]);
	    	e[i].addSegment(p.random(0.05f, 0.1f), 500.0f);
	    	//e[i].addSegment(0.0f, 1000.0f, new KillTrigger(g[i]));
	    }
	    
	    PolSys.ac.out.addInput(gWar);
	}
	
	void display(){
		update();
		p.stroke(255-p.random(50), 0, 0, alpha);
		p.strokeWeight(2);
		for(int i = 0; i < lineNum; i++){
			float r1 = p.random(-20, 19);
			float r2 = p.random(-19, 20);
			p.line(n1.pos.x, n1.pos.y, halfway.x+r2, halfway.y+r2);
			p.line(halfway.x+r1, halfway.y+r1, n2.pos.x, n2.pos.y);
		}

	}
	
	void update(){
		if(alpha < alphaMax) alpha += alphaInc;
		halfway = new PVector((n1.pos.x+n2.pos.x)*0.5f, (n1.pos.y+n2.pos.y)*0.5f);
		PVector dir = PVector.sub(n1.pos, n2.pos);
		theta = dir.heading2D();
		
		if(n1.totalWealth > n2.totalWealth){
			larger = n1;
			smaller = n2;
		}else{
			larger = n2;
			smaller = n1;
		}
		
		lerp = PApplet.map(PApplet.abs(larger.totalWealth - smaller.totalWealth), 0.0f, larger.totalWealth, 0.5f, 0.9f);
		
		halfway = PVector.lerp(larger.pos, smaller.pos, lerp);
		
		gWar.setValue(0.5f/PolSys.wars.size());
	}

}

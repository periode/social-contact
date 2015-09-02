package polsys;

import beads.Buffer;
import beads.Envelope;
import beads.Gain;
import beads.KillTrigger;
import beads.WavePlayer;
import processing.core.PApplet;
import processing.core.PVector;

public class ConnectionLove extends ProcessingObject {
	
	  PApplet p;
	
	  Agent a1;
	  Agent a2;

	  float rad;
	  PVector pos;
	  
	  PVector pos1;
	  PVector pos2;
	  int i;
	  float size;
	  public int c;
	  float w;
	  
	  float creationTime;
	  
	  float alpha;
	  float alphaMax;
	  float alphaInc;
	  
	  int numHarmonics;
	  WavePlayer[] wp;
	  Gain[] g;
	  Envelope[] e;
	  
	  Gain gConnecLove;
	  
	  ConnectionLove() {
	  }

	  ConnectionLove(Agent a1, Agent a2, float size, PApplet p) {
		 this.p = p;
	    this.a1 = a1;
	    this.a2 = a2;
	    pos1 = new PVector(a1.pos.x, a1.pos.y);
	    pos2 = new PVector(a2.pos.x, a2.pos.y);
	    pos = new PVector((a1.pos.x+a2.pos.x)/2.0f, (a1.pos.y+a2.pos.y)/2);
	    rad = PApplet.dist(a1.pos.x, a1.pos.y, a2.pos.x, a2.pos.y)+((a1.rad+a2.rad)/2);
	    this.size = size;
	    c = p.color(255, 0, 0);
	    
	    alpha = 0;
	    alphaMax = 20;
	    alphaInc = 1.0f;
	    
	    w = PApplet.max(a1.rad, a2.rad);
	    creationTime = p.millis();
	    
	    numHarmonics = 2;
	    wp = new WavePlayer[numHarmonics];
	    e = new Envelope[numHarmonics];
	    g = new Gain[numHarmonics];
	    gConnecLove = new Gain(PolSys.ac, numHarmonics, 0.1f);
	    
	    for(int i = 0; i < numHarmonics; i++){
	    	if(i % 2 == 0){
	    		wp[i] = new WavePlayer(PolSys.ac,  523.251f, Buffer.SINE);
	    	}else{
	    		wp[i] = new WavePlayer(PolSys.ac, 329.628f, Buffer.SINE);
	    	}
	    	e[i] = new Envelope(PolSys.ac, 0.0f);
	    	g[i] = new Gain(PolSys.ac, 1, e[i]);
	    	g[i].addInput(wp[i]);
	    	gConnecLove.addInput(g[i]);
	    	e[i].addSegment(p.random(0.1f, 0.2f), 200.0f);
	    	e[i].addSegment(0.0f, 2000.0f, new KillTrigger(g[i]));
	    }
	    
	    PolSys.ac.out.addInput(gConnecLove);
	  }

	  void display() {
		update();
		p.noStroke();
		
	    pos = new PVector((a1.pos.x+a2.pos.x)/2.0f, (a1.pos.y+a2.pos.y)/2);
	    rad = PApplet.dist(a1.pos.x, a1.pos.y, a2.pos.x, a2.pos.y)+((a1.rad+a2.rad)*0.75f);
	    
	    p.fill(c, alpha);
	    p.ellipse(pos.x, pos.y, rad, rad);
	    p.fill(c, alpha);
	    p.ellipse(pos.x, pos.y, rad*0.5f, rad*0.5f);
	    p.stroke(c, alpha);
	    for(int i = -(int)(3*0.5f); i < 3; i++){
			  p.line(a1.pos.x-i*7.5f, a1.pos.y, a2.pos.x+i*7.5f, a2.pos.y);
		  }
	    p.stroke(0);
	  }
	  
	  void update(){
		  if(alpha < alphaMax) alpha += alphaInc;
	  }
}

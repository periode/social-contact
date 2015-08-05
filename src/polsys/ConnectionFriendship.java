package polsys;

import beads.Buffer;
import beads.Envelope;
import beads.Gain;
import beads.KillTrigger;
import beads.WavePlayer;
import processing.core.PApplet;
import processing.core.PVector;

public class ConnectionFriendship extends ProcessingObject {
	
	PApplet p;
	
	  Agent a1;
	  Agent a2;

	  PVector pos1;
	  PVector pos2;
	  int i;
	  float size;
	  public int c;
	  int maxWeight; 
	  
	  float creationTime;
	  
	  float friendshipDifference;
	  PVector averagePos;
	  
	  float alpha;
	  float alphaMax;
	  float alphaInc;
	  
	  int numHarmonics;
	  WavePlayer[] wp;
	  Gain[] g;
	  Envelope[] e;
	  
	  Gain gConnecFriendship;
	  
	  ConnectionFriendship() {}

	  ConnectionFriendship(Agent a1, Agent a2, float size, PApplet p) {
		 this.p = p;
	    this.a1 = a1;
	    this.a2 = a2;
	    pos1 = new PVector(a1.pos.x, a1.pos.y);
	    pos2 = new PVector(a2.pos.x, a2.pos.y);
	    this.size = size;
	    c = p.color(10, 100, 10);
	    
	    alpha = 0;
	    alphaMax = 50;
	    alphaInc = 1.0f;
	    
	    maxWeight = 3;
	    creationTime = p.millis();
	    
	    numHarmonics = 2;
	    wp = new WavePlayer[numHarmonics];
	    e = new Envelope[numHarmonics];
	    g = new Gain[numHarmonics];
	    gConnecFriendship = new Gain(PolSys.ac, numHarmonics, 0.1f);
	    
	    for(int i = 0; i < numHarmonics; i++){
	    	if(i % 2 == 0){
	    		wp[i] = new WavePlayer(PolSys.ac,  523.251f, Buffer.SINE);
	    	}else{
	    		wp[i] = new WavePlayer(PolSys.ac, 329.628f, Buffer.SINE);
	    	}
	    	e[i] = new Envelope(PolSys.ac, 0.0f);
	    	g[i] = new Gain(PolSys.ac, 1, e[i]);
	    	g[i].addInput(wp[i]);
	    	gConnecFriendship.addInput(g[i]);
	    	e[i].addSegment(p.random(0.1f, 0.2f), 10.0f);
	    	e[i].addSegment(0.0f, 1000.0f, new KillTrigger(g[i]));
	    }
	    
	    PolSys.ac.out.addInput(gConnecFriendship);
	  }

	  void display() {
		  update();
		  averagePos = new PVector((a1.pos.x+a2.pos.x)*0.5f, (a1.pos.y+a2.pos.y)*0.5f);
		  float culturalSim = 0;
		  for(int i = 0; i < a1.culture.length; i++){
			  culturalSim += PApplet.abs(a1.culture[i] - a2.culture[i]);
		  }
		
		  p.noFill();

		  int w = (int) PApplet.map(culturalSim, 0.0f, 5.0f, maxWeight, 10.0f);
		  w = (int) PApplet.constrain(w,  1.0f, maxWeight);
		  p.strokeWeight(1);
		  p.stroke(c, alpha);
		  
		  for(int i = -(int)(maxWeight*0.5f); i < maxWeight; i++){
			  p.line(a1.pos.x-i*7.5f, a1.pos.y, a2.pos.x+i*7.5f, a2.pos.y);
		  }
		  
	    }

	  void update(){
		  if(alpha < alphaMax) alpha += alphaInc;
	  }
}

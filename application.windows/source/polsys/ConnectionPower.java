	package polsys;

import beads.Buffer;
import beads.Envelope;
import beads.Gain;
import beads.KillTrigger;
import beads.WavePlayer;
import processing.core.PApplet;
import processing.core.PVector;

public class ConnectionPower extends ProcessingObject {
	
	PApplet p;
	
	  Agent a1;
	  Agent a2;
	  
	  Agent aSuperior;
	  Agent aInferior;

	  PVector pos1;
	  PVector pos2;
	  PVector posViz;
	  PVector averagePos;
	  int i;
	  float size;
	  public int c;
	  float w;
	  float circSize;
	  
	  float lerpAmt;
	  float lerpSpeed;
	  
	  float powerDifference;
	  
	  float alpha;
	  float alphaMax;
	  float alphaInc;
	  
	  int numHarmonics;
	  WavePlayer[] wp;
	  Gain[] g;
	  Envelope[] e;
	  
	  Gain gConnecPower;
	  
	  PVector rectPos;
	  float rectSizeX;
	  float rectSizeY;
	  float rectAlpha;
	  
	  float theta;
	  
	  float lerpVal;
	  float lerpInc;
	  
	  String revealFunction;
	  float textAlpha;
	  float textAlphaInc;
	  
	  ConnectionPower() {
	  }

	  ConnectionPower(Agent a1, Agent a2, float size, PApplet p) {
		 this.p = p;
	    this.a1 = a1;
	    this.a2 = a2;
	    pos1 = new PVector(a1.pos.x, a1.pos.y);
	    pos2 = new PVector(a2.pos.x, a2.pos.y);
	    posViz = new PVector();
	    this.size = size;
	    c = p.color(0, 50, 0);
	    
	    alpha = 0;
	    alphaMax = 150;
	    alphaInc = 1.0f;
	    
	    w = 5;
	    lerpAmt = 0.0f;
	    lerpSpeed = 0.01f;
	    circSize = w*1.2f;
	    averagePos = new PVector((pos1.x+pos2.x)*0.5f, (pos1.y+pos2.y)*0.5f);
	    
	    rectPos = averagePos;
	    rectSizeX = PApplet.abs(pos1.x-pos2.x);
	    rectSizeY = PApplet.abs(pos1.y-pos2.y);
	    rectAlpha = 100;
	    
	    numHarmonics = 2;
	    wp = new WavePlayer[numHarmonics];
	    e = new Envelope[numHarmonics];
	    g = new Gain[numHarmonics];
	    gConnecPower = new Gain(PolSys.ac, numHarmonics, 0.1f);
	    
	    for(int i = 0; i < numHarmonics; i++){
	    	if(i % 2 == 0){
	    		wp[i] = new WavePlayer(PolSys.ac, 110.0f, Buffer.SQUARE);
	    	}else{
	    		wp[i] = new WavePlayer(PolSys.ac, 110.0f + p.random(10), Buffer.SAW);
	    	}
	    	e[i] = new Envelope(PolSys.ac, 0.0f);
	    	g[i] = new Gain(PolSys.ac, 1, e[i]);
	    	g[i].addInput(wp[i]);
	    	gConnecPower.addInput(g[i]);
	    	e[i].addSegment(p.random(0.025f, 0.075f), 100.0f);
	    	e[i].addSegment(0.0f, 1000.0f, new KillTrigger(g[i]));
	    }
	    
	    PolSys.ac.out.addInput(gConnecPower);
	    
	    lerpVal = 0;
	    lerpInc = 0.1f;
	    
	    revealFunction = "oppress(other);";
	    textAlpha = 150.0f;
	    textAlphaInc = 3.0f;
	  }
	  
	  void update(){
		  if(a1.culture[1] > a2.culture[1]){
			  aSuperior = a1;
			  aInferior = a2;
		  }else{
			  aSuperior = a2;
			  aInferior = a1;
		  }
		  
		  powerDifference = PApplet.abs(a1.culture[1] - a2.culture[1]);
		  
		  if(alpha < alphaMax) alpha += alphaInc;
		  
		  size = PVector.dist(a1.pos, a2.pos);
		  
		  if(lerpVal < 1) lerpVal += lerpInc;
	  }

	  void display() {
		p.strokeWeight(4);
		p.stroke(c, alpha*0.5f);

		averagePos = new PVector((a1.pos.x+a2.pos.x)*0.5f, (a1.pos.y+a2.pos.y)*0.5f);
		
	    //p.line(a1.pos.x, a1.pos.y, a2.pos.x, a2.pos.y);
	    
	    p.stroke(c, alpha*2.0f);
	    p.strokeWeight(2);
	    p.noFill();
	    p.pushMatrix();
	    p.translate(averagePos.x, averagePos.y);
	    p.rotate(theta);
	    
	    p.noStroke();
	    p.fill(0, 20);
	    
	    if(lerpVal < 1){
	    	 p.beginShape();

	 	    float radIncS = 0.3f;
	 	    float radIncI = 0.15f;
	 	    p.vertex(size*0.25f, aSuperior.rad*radIncS);
	 	    p.vertex(size*0.2f, 0);
	 	    p.vertex(size*0.25f, -aSuperior.rad*radIncS);
	 	    
	 	    p.vertex(PApplet.lerp(size*0.25f, -size*0.4f, lerpVal), -aInferior.rad*radIncI);
	 	    p.vertex(PApplet.lerp(size*0.2f, -size*0.45f, lerpVal), p.random(-aInferior.rad*radIncI*0.5f, aInferior.rad*radIncI*0.5f));
	 	    p.vertex(PApplet.lerp(size*0.25f, -size*0.4f, lerpVal), aInferior.rad*radIncI);
	 	    
	 	    //p.vertex(size*0.5f, aSuperior.rad*radIncS);
	 	    
	 	    p.endShape(PApplet.CLOSE);
	    }else{
	    	 p.beginShape();

		 	    float radIncS = 0.3f;
		 	    float radIncI = 0.15f;
		 	    p.vertex(size*0.25f, aSuperior.rad*radIncS);
		 	    p.vertex(size*0.2f, 0);
		 	    p.vertex(size*0.25f, -aSuperior.rad*radIncS);
		 	    
		 	    p.vertex(-size*0.4f, -aInferior.rad*radIncI);
		 	    p.vertex(-size*0.45f, p.random(-aInferior.rad*radIncI*0.5f, aInferior.rad*radIncI*0.5f));
		 	    p.vertex(-size*0.4f, aInferior.rad*radIncI);
		 	    
		 	    //p.vertex(size*0.5f, aSuperior.rad*radIncS);
		 	    
		 	    p.endShape(PApplet.CLOSE);
	    }
	    
	    p.popMatrix();
	    
	    posViz = PVector.lerp(aSuperior.pos, aInferior.pos, lerpAmt);
	    
		PVector dir = PVector.sub(aInferior.pos, aSuperior.pos);
		theta = dir.heading2D()+ PApplet.PI*1.0f;
	    
	    lerpAmt += lerpSpeed;
	    if(lerpAmt > 1) lerpAmt = 0;
	    
	    p.rectMode(PApplet.CENTER);
	    p.noFill();
	    p.strokeWeight(1);
	    p.stroke(c, rectAlpha);
	    if(rectAlpha > 0){
//	    	p.rect(rectPos.x+p.random(2), rectPos.y, rectSizeX, rectSizeY);
//	    	p.rect(rectPos.x, rectPos.y+p.random(2), rectSizeX, rectSizeY);
	    	p.line(a1.pos.x, a1.pos.y, a2.pos.x, a2.pos.y);
	    }
	    rectAlpha -= 1.0f;
	    
		p.fill(0, textAlpha*1.5f);
		p.textFont(PolSys.thoughtFont);
		p.textSize(PolSys.thoughtFontSize);
		if(textAlpha > 0){
			p.text(revealFunction, averagePos.x+10, averagePos.y);
			textAlpha -= textAlphaInc;
		}
	    
	  }

}

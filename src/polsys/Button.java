package polsys;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Button extends ProcessingObject {
	
	PApplet p;
	
	PVector pos;

	float size;
	float type;
	int c;
	int c1;
	int c2;
	int sw;
	  
	float sinVal;
	float sinInc;
	
	float alpha;
	
	float startRectAlpha;
	float startRectSize;
	
	float startRectAlphaInc;
	float startRectSizeInc;

	Button() {
	}

	Button(float x, float y, float size, int direction, PApplet p) {
		this.p = p;
		this.pos = new PVector(x, y);
	    this.size = size;
	    this.type = direction; //increase (1) or decrease (0) or start (2)
	    c1 = p.color(255);
	    c2 = p.color(100);
	    c = c1;
	    sw = 1;
	    
	    sinVal = 1;
	    sinInc = 0.1f;
	    
	    alpha = 150;
	    
	    startRectAlpha = 225f;
	    startRectSize = size;
	    startRectAlphaInc = 12.5f;
	    startRectSizeInc = 7.0f;
	}

	void display() {
		p.rectMode(PConstants.CENTER);
		p.stroke(70, alpha);
		p.pushMatrix();
		p.translate(pos.x, pos.y);
		sinVal += sinInc;
		float inc = (PApplet.sin(sinVal)+1)*3.0f;
		
	    if (type == 1) {//increase
	    	p.strokeWeight(sw);
	    	p.line(0-(size*0.35f), 0+(size*0.2f)+inc, 0, 0-(size*0.3f)+inc);
	    	p.line(0, 0-(size*0.3f)+inc, 0+(size*0.35f), 0+(size*0.2f)+inc);
	    	p.stroke(110, alpha);
	    	p.line(1-(size*0.2f), 0+(size*0.15f)+inc, -1+(size*0.2f), 0+(size*0.15f)+inc);
	    	p.line(0, 0-(size*0f)+inc, 0, 0+(size*0.3f)+inc);
	    } else if (type == 0) {//decrease
	    	p.strokeWeight(sw);
	    	p.line(0-(size*0.35f), 0-(size*0.2f)-inc, 0, 0+(size*0.3f)-inc);
		    p.line(0, 0+(size*0.3f)-inc, 0+(size*0.35f), 0-(size*0.2f)-inc);
		    p.stroke(110, alpha);
		    p.line(1-(size*0.2f), 0-(size*0.15f)-inc, -1+(size*0.2f), 0-(size*0.15f)-inc);
	    } else if (type == 2) {//start
			p.textAlign(PConstants.CENTER);
			if(PolSys.startGame1 || PolSys.startGame2 || PolSys.startGame3){
		    	if(PolSys.canStart){
					p.stroke(70, startRectAlpha);
					p.noFill();
					p.rect(0, 0, startRectSize*3f, startRectSize);
			    	p.strokeWeight(sw);
			    	p.stroke(70, alpha);
		    		p.rect(0, 0, size*3f, size*1f);
		    		p.fill(0);
			    	p.text("begin\nsocial contact", 0, -5);
			    	startRectAlpha -= startRectAlphaInc;
			    	startRectSize += startRectSizeInc;
		    	}else{
		    		p.noFill();
			    	p.strokeWeight(1);
			    	p.stroke(200, alpha);
		    		p.rect(0, 0, size*3f, size*1f);
		    		p.fill(200);
			    	p.text("choose\nyour assumptions", 0, -5);
		    	}
			}else{
	    		p.noFill();
		    	p.strokeWeight(sw);
	    		p.rect(0, 0, size*4.0f-2, size*1f);
	    		p.fill(0);
		    	p.text("your assumptions", 0, size*0.15f);
			}
	    	
	    } else if (type == 3) {//change pursuit right
	    	p.fill(c);
	    	p.strokeWeight(sw);
		    p.line(0+inc, 0+(size*0.25f), 0-(size*0.25f)+inc, 0);
		    p.line(0+inc, 0-(size*0.3f), 0-(size*0.25f)+inc, 0);
	    }else if(type == 4){ //change pursuit left
	    	p.fill(c);
	    	p.strokeWeight(sw);
		    p.line(0-inc, 0+(size*0.25f), 0+(size*0.25f)-inc, 0);
		    p.line(0-inc, 0-(size*0.3f), 0+(size*0.25f)-inc, 0);
	    	p.fill(0);
	    }else if(type == 5){//TODO apparently this category is never used so it can be the page switching system
	    	p.fill(c, 50);
	    	p.strokeWeight(1);
	    	p.rect(0, 0, size, size);
	    }else if(type == 6){
	    	p.stroke(70, PolSys.assumptionsRectAlpha);
			p.noFill();
			p.strokeWeight(1);
			p.rect(0, 0, size*1.0f, size*0.5f);
    		p.fill(0, PolSys.assumptionsRectAlpha);
	    	p.text("reset", 0, PolSys.textFontSize*0.25f);
	    }
	    
	    p.popMatrix();
	    
	    
	    if(PApplet.dist(p.mouseX, p.mouseY, pos.x, pos.y) < size){
	    	alpha = 200;
	    }else{
	    	alpha = 100;
	    }
	}

	boolean onClick() {
	    if (p.mousePressed && p.mouseX > this.pos.x-size*1.5f && p.mouseX < this.pos.x+size*1.5f && p.mouseY > this.pos.y-size*1.5f && p.mouseY < this.pos.y +size*1.5f) {
	    	if(p.mousePressed && !PolSys.statement && !PolSys.intro) PolSys.interactions++;
	    	return true;
	    } else {
	    	return false;
	    }
	}
}

package polsys;

import processing.core.PApplet;
import processing.core.PConstants;

public class Button extends ProcessingObject {
	
	PApplet p;

	  float x;
	  float y;
	  float size;
	  float direction;
	  int c;
	  int c1;
	  int c2;
	  int sw;

	  Button() {
	  }

	  Button(float x, float y, float size, int direction, PApplet p) {
		this.p = p;
	    this.x = x;
	    this.y = y;
	    this.size = size;
	    this.direction = direction; //increase (1) or decrease (0) or start (2)
	    c1 = p.color(255);
	    c2 = p.color(100);
	    c = c1;
	    sw = 1;
	  }

	  void display() {
		  p.rectMode(PConstants.CENTER);
		  p.textAlign(PConstants.CENTER);
		  p.stroke(70);
		  p.pushMatrix();
		  p.translate(x, y);
		  
	    if (direction == 1) {//increase
	      p.strokeWeight(sw);
	      p.line(0-(size*0.35f), 0+(size*0.2f), 0, 0-(size*0.3f));
	      p.line(0, 0-(size*0.3f), 0+(size*0.35f), 0+(size*0.2f));
	    } else if (direction == 0) {//decrease
	    	p.strokeWeight(sw);
	    	p.line(0-(size*0.35f), 0-(size*0.2f), 0, 0+(size*0.3f));
		    p.line(0, 0+(size*0.3f), 0+(size*0.35f), 0-(size*0.2f));
	    } else if (direction == 2) {//start
	    	p.fill(c);
	    	p.strokeWeight(sw);
	    	p.rect(0, 0, size*1.5f, size*0.75f);
	    	p.fill(0);
	    	p.text("begin", 0, 0+4);
	    } else if (direction == 3) {//change pursuit right
	    	p.fill(c);
	    	p.strokeWeight(sw);
		    p.line(0, 0+(size*0.25f), 0-(size*0.25f), 0);
		    p.line(0, 0-(size*0.3f), 0-(size*0.25f), 0);
	    }else if(direction == 4){ //change pursuit left
	    	p.fill(c);
	    	p.strokeWeight(sw);
		    p.line(0, 0+(size*0.25f), 0+(size*0.25f), 0);
		    p.line(0, 0-(size*0.3f), 0+(size*0.25f), 0);
	    	p.fill(0);
	    }else if(direction == 5){
	    	p.fill(c, 150);
	    	p.strokeWeight(sw);
	    	p.rect(0, 0, size*0.75f, size*0.75f);
	    	p.fill(0);
	    }
	    
	    p.popMatrix();
	    
	    
	    if(p.dist(p.mouseX, p.mouseY, x, y) < size){
	    	sw = 2;
	    }else{
	    	sw = 1;
	    }
	  }

	  boolean onClick() {
	    if (p.mouseX > this.x-(size/2) && p.mouseX < this.x+(size/2) && p.mouseY > this.y-(size/2) && p.mouseY < this.y +(size/2)) {
	    	p.strokeWeight(2);
	      return true;
	    } else {
	      return false;
	    }
	  }
}

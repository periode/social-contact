package polsys;

import processing.core.PApplet;
import processing.core.PVector;

public class Connection extends ProcessingObject {
	PApplet p;
	
	  Agent a1;
	  Agent a2;

	  PVector pos1;
	  PVector pos2;
	  int i;
	  public float size;
	  public int c;
	  float w; 
	  
	  float circleAlpha;
	  float circleAlphaRate;
	  float circleRad1;
	  float circleRad2;
	  float circleRadRate;
	  
	  PVector averagePos;
	  
	  boolean canRemove;
	  
	  Connection() {
	  }

	  Connection(Agent a1, Agent a2, float size, PApplet p) {
		 this.p = p;
	    this.a1 = a1;
	    this.a2 = a2;
	    pos1 = new PVector(a1.pos.x, a1.pos.y);
	    pos2 = new PVector(a2.pos.x, a2.pos.y);
	    this.size = 20;
	    c = 0;
	    w = 2;
	    
	    circleAlpha = 150;
	    circleAlphaRate = 8.0f;
	    circleRad1 = a1.rad;
	    circleRad2 = a2.rad;
	    circleRadRate = 5.0f;
		averagePos = new PVector((a1.pos.x+a2.pos.x)*0.5f, (a1.pos.y+a2.pos.y)*0.5f);
		canRemove = false;
	  }

	  void display() {
		  
		averagePos = new PVector((a1.pos.x+a2.pos.x)*0.5f, (a1.pos.y+a2.pos.y)*0.5f);
		 
		  if(circleAlpha > 0){
				p.stroke(0, circleAlpha);
				p.noFill();
				p.ellipse(a1.pos.x, a1.pos.y, circleRad1, circleRad1);
				p.ellipse(a2.pos.x, a2.pos.y, circleRad2, circleRad2);
				
				circleRad1 += circleRadRate;
				circleRad2 += circleRadRate;
				circleAlpha -= circleAlphaRate;
		  }

		
		  
		p.strokeWeight(w);
		
		p.stroke(c, 150);
		if(canRemove){
			w = 4;
		}else{
			w = 1;
		}
	    
	    pos1 = new PVector(a1.pos.x, a1.pos.y);
	    pos2 = new PVector(a2.pos.x, a2.pos.y);

	    p.line(a1.pos.x, a1.pos.y, a2.pos.x, a2.pos.y);
	    

	  }

	  void destroyConnection() {
	        
	        if(PApplet.dist(a1.pos.x, a1.pos.y, a2.pos.x, a2.pos.y) > a1.distance + 10){ //the +10 is here so that whenever agents are just separating, it doesn't ping
	        	
	        	this.a1.currentConnections.remove(this);
	        	this.a1.cluster.agentsInside.remove(a2);
	        	this.a1.connec++;
	        	this.a2.currentConnections.remove(this);
	        	this.a2.cluster.agentsInside.remove(a1);
	        	this.a2.connec++;
	        	PolSys.connections.remove(this);
	        	
	        }
	        
		  
	      for (int j = 0; j < PolSys.connections.size (); j++) {
	        Connection c = PolSys.connections.get(j);
	        
	        if(c.a1.isKiller) c.a2.isConnectedToKiller = true;
	        if(c.a2.isKiller) c.a1.isConnectedToKiller = true;
	        
	        if(c.a1.isHunter) c.a2.isConnectedToHunter = true;
	        if(c.a2.isHunter) c.a1.isConnectedToHunter = true;
	        
	        if(!c.a1.isAlive && !c.a2.isAlive){
	        	PolSys.connections.remove(c);
	        	  
	        	this.a1.currentConnections.remove(c);
	        	this.a2.currentConnections.remove(c);
	        }
	        
	        if (c != this && this.a1 == c.a1 && this.a2 == c.a2) { //if it's twice the same connection
	        	
	        	PolSys.connections.remove(c);
	        	this.a1.connec++;
	          
	        }else if (c != this && this.a2 == c.a1 && this.a1 == c.a2) {
	        	
	        	PolSys.connections.remove(c);
	        	this.a1.connec++;
	          
	        }
	      }
	    }
}

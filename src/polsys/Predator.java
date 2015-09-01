package polsys;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class Predator extends ProcessingObject {
	  PApplet p;
	  
	  PVector pos;
	  PVector velocity;
	  PVector acceleration;
	  float moveCoeff;
	  float moveForce;
	  float maxForce;
	  float slowness;
	  
	  float size;
	  float lifeSpan;
	  float hungerDuration;
	  float hungerDurationMax;
	  boolean isAlive;
	  boolean isDomesticated;
	  
	  int sw;
	  int colPred;

	  float startTimeMovement;
	  float timerMovement;
	  
	  PImage img_pred;
	  PImage img_dead;
	  PImage img_full;
	  
	  float theta;
	  float wandertheta;
	  
	  float huntingDistance;
	  
	  float preyTriangleAlpha;
	  float preyTriangleAlphaRate;
	  float preyTriangleDist;
	  float preyTriangleDistRate;

	  Agent tamer;

	  Predator(float x, float y, float size, float speedMin, float speedMax, float lifeSpan, float hungerDuration, PApplet p) {
		this.p = p;
	    this.pos = new PVector(x, y);
	    
	    this.velocity = new PVector(p.random(speedMin, speedMax), p.random(speedMin, speedMax));
	    this.acceleration = new PVector(0, 0);
	    this.moveCoeff = 1.5f;
	    this.maxForce = 0.15f;
	    slowness = 0.3f;
	    
	    this.size = size;
	    this.lifeSpan = lifeSpan + p.millis();
	    this.isAlive = true;
	    this.isDomesticated = false;
	    this.hungerDuration = 0;
	    this.hungerDurationMax = hungerDuration;
	    this.startTimeMovement = p.millis();
	    //this.timerMovement = p.random(8, 14)*1000;
	    this.timerMovement = 1000;
	    this.colPred = PolSys.colorPredator;
	    
	    theta = 0;
	    wandertheta = 0;
	    
	    sw = 1;
	    
	    huntingDistance = 50;
	    
	    preyTriangleAlpha = 0;
	    preyTriangleAlphaRate = 8.0f;
	    preyTriangleDist = 0.0f;
	    preyTriangleDistRate = 4.0f;
	  }
	  
	  public Predator() {}

	void update(){
		    if (!isDomesticated) {
		    	for(int i = 0; i < PolSys.agents.length; i++){
		    		if(PApplet.dist(this.pos.x, this.pos.y, PolSys.agents[i].pos.x, PolSys.agents[i].pos.y) < huntingDistance && PolSys.agents[i].isAlive && hungerDuration <= 0 && PolSys.agents[i].arrived){
		    			seek(PolSys.agents[i].pos, 0.7f);
		    		}
		    	}

			}

			if (isDomesticated && tamer != null) {
			    	seek(tamer.pos, 0.7f*tamer.moveCoeff);
			    	theta = tamer.velocity.heading2D() + PApplet.PI/2;
			}

		  
		  velocity.add(acceleration);
		  velocity.limit(moveCoeff*slowness);
		  pos.add(velocity);
		  
		  acceleration.mult(0);
	  }
	  
	  void applyForce(PVector f){
		  PVector force = f.get();
		  acceleration.add(force);
	  }
	  
	  void wander() {
		    float wanderR = 50;         // Radius for our "wander circle"
		    float wanderD = 250;         // Distance for our "wander circle"
		    float change = 0.01f;
		    wandertheta += p.random(-change,change);     // Randomly change wander theta

		    // Now we have to calculate the new location to steer towards on the wander circle
		    PVector circleloc = velocity.get();    // Start with velocity
		    circleloc.normalize();            // Normalize to get heading
		    circleloc.mult(wanderD);          // Multiply by distance
		    circleloc.add(pos);               // Make it relative to boid's location
		    
		    float h = velocity.heading2D();        // We need to know the heading to offset wandertheta

		    PVector circleOffSet = new PVector(wanderR*PApplet.cos(wandertheta+h),wanderR*PApplet.sin(wandertheta+h));
		    PVector target = PVector.add(circleloc,circleOffSet);
		    seek(target, 0.5f);
		  }
	  
	  void seek(PVector target, float limitCoeff) {
		    PVector desired = PVector.sub(target,pos);  // A vector pointing from the location to the target

		    // Normalize desired and scale to maximum speed
		    desired.normalize();
		    desired.mult(moveCoeff);
		    // Steering = Desired minus Velocity
		    PVector steer = PVector.sub(desired,velocity);
		    steer.limit(maxForce*limitCoeff);  // Limit to maximum steering force

		    applyForce(steer);
		  }

	  void display() {
	    p.rectMode(PConstants.CENTER);
	    p.strokeWeight(sw);
	    p.stroke(colPred);
	    p.noFill();
		theta = this.velocity.heading2D() + PApplet.PI/2;
    	p.pushMatrix();
    	p.translate(pos.x, pos.y);
    	p.rotate(theta);
    	p.rect(0, 0, size, size*2.0f);
    	p.stroke(colPred, preyTriangleAlpha);
    	p.rect(0, 0, size+preyTriangleDist, (size+preyTriangleDist)*2);
    	p.popMatrix();
	    if (hungerDuration >= 0) { // if the beast has eaten recently, it can be domesticated, and will have a rect in its belly;
	    	p.pushMatrix();
	    	p.translate(pos.x, pos.y);
	    	p.rotate(theta);
	    	p.stroke(colPred);
	    	p.fill(colPred, 150);
	    	p.rect(0, 0, size*0.5f, size*1.5f);
	    	p.popMatrix();
	    }

	    boundaries();

	    hungerDuration -= 0.1f;
	    
	    if(preyTriangleAlpha > 0){
	    	preyTriangleAlpha -= preyTriangleAlphaRate;
	    	preyTriangleDist += preyTriangleDistRate;
	    }
	    
	    if(p.millis() > lifeSpan){
	     isAlive = false;
	    }
	  }
	  
	  void boundaries() {

		    PVector desired = null;

		    if (pos.x < size*2) {
		      desired = new PVector(moveCoeff, velocity.y);
		    } 
		    else if (pos.x > p.width - size*2) {
		      desired = new PVector(-moveCoeff, velocity.y);
		    } 

		    if (pos.y < size*2) {
		      desired = new PVector(velocity.x, moveCoeff);
		    } 
		    else if (pos.y > p.height-size*2-(p.height/19)) {
		      desired = new PVector(velocity.x, -moveCoeff);
		    } 

		    if (desired != null) {
		      desired.normalize();
		      desired.mult(moveCoeff);
		      PVector steer = PVector.sub(desired, velocity);
		      steer.limit(maxForce);
		      applyForce(steer);
		    }
		  }  

	  void collide() {
		  for (int i  = 0; i < PolSys.agents.length; i++) {
			  if (this.pos.x+size/2 > PolSys.agents[i].pos.x - PolSys.agents[i].rad/2 && this.pos.x-size/2 < PolSys.agents[i].pos.x + PolSys.agents[i].rad/2 && this.pos.y+size/2 > PolSys.agents[i].pos.y - PolSys.agents[i].rad/2 && this.pos.y-size/2 < PolSys.agents[i].pos.y - PolSys.agents[i].rad/2) { //if they coll
				  if (PolSys.agents[i].isAlive  && PolSys.agents[i].arrived) {
					  if (hungerDuration <= 0 && !isDomesticated) { //if the beast is hungry and untamed

						  float r = p.random(1);

						  if (r > 0.45f) { //55% chance the agent is dead
							  PolSys.agents[i].makeSound(3);
							  preyTriangleDist = 0;
							  preyTriangleAlpha = 255;

							  for (int j = 0; j < PolSys.connections.size (); j++) {
	             		
								  Connection c = PolSys.connections.get(j);
	             			
								  if (c.a1 == PolSys.agents[i]){
									  c.a2.connec++; //because one agent is dead, the other gets his connection back
		             			
									  c.a2.isConnectedToDead = true; //it has also been scarred for life
		             			
									  c.a2.cluster.buryAgent(PolSys.agents[i]); //remove any reference to it, and set its cluster to null
		             			
									  c.a2.currentConnections.remove(c);
									  PolSys.connections.remove(c);
								  }else{		             			
									  c.a1.connec++;
									  c.a1.isConnectedToDead = true;
									  c.a1.cluster.buryAgent(PolSys.agents[i]);

									  /*
									  if(c.a1.cluster.agentsInside.contains(PolSys.agents[i])){
										  PApplet.println("this should not happen v2.0"); //this happens because they remember the dead?
										  c.a1.cluster.agentsInside.remove(PolSys.agents[i]);
									  }
									  */
		             			
									  c.a1.currentConnections.remove(c);
									  PolSys.connections.remove(c);
								  }
							  }
	             	
							  PolSys.agents[i].isAlive = false; //other is dead
							  PolSys.agents[i].isHunted = true;
							  PolSys.agents[i].colDeath = p.color(0, 0, 255);

							  hungerDuration = hungerDurationMax;
	              
						  }else if(r < 0.2f) {
							  this.isAlive = false;
							  PolSys.agents[i].isHunter = true;
							  PolSys.agents[i].col = p.color(0, 0, 150);
							  for (int j = 0; j < PolSys.connections.size (); j++) {
								  Connection c = PolSys.connections.get(j);
	            	  
								  if(PolSys.agents[i] == c.a2){
									  c.a1.isConnectedToHunter = true;
								  }else if(PolSys.agents[i] == c.a1){
									  c.a2.isConnectedToHunter = true;
								  }
							  }
						  }
					  }else if(hungerDuration >= 0) {
						  float r2 =  p.random(1);

						  if (r2 > 0.5f) {
							  tamer = PolSys.agents[i];
							  PolSys.agents[i].isTamer = true;
	              
							  isDomesticated = true;
							  colPred = p.color(150, 150, 0);
	              
						  }else{
							  if(PolSys.agents[i].connec == PolSys.agents[i].connecMax) {
								  PolSys.agents[i].velocity.mult(-1);
								  this.velocity.mult(-1);
							  } else {
								  this.velocity.mult(-1);
							  }
						  }
					  }
				  }
			  }
		  }
	  }
}

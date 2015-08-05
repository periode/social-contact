package polsys;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Fish {
	
	PVector pos;
	PVector velocity;
	PVector acceleration;
	PVector posViz;
	PApplet p;
	int connec;
	int minConnec;
	int maxConnec;
	float rad;
	float startTimeMovement;
	float timerMovement;
	PVector mov;
	float maxSpeed;
	float maxForce;
	float dist;
	
	int color;
	int strokeAlpha;
	float slow;
	
	float theta;
	float lerpAmt;
	
	// 2
	int sides;
	float pulseRatio;
	float xPulse;
	
	//this constructor simulates a predator
	Fish(float x, float y, PApplet p){
		this.pos = new PVector(x, y);
		this.velocity = new PVector(p.random(-1.5f, 1.5f), p.random(-1.5f, 1.5f));
		this.acceleration = new PVector(0, 0);
		this.p = p;
		theta = 0;
		rad = 10;
		startTimeMovement = p.millis();
		timerMovement = p.millis() + p.random(1, 2)*1000.0f;
		
		mov = new PVector();
		maxSpeed = 1.0f;
		maxForce = 0.15f;
	}
	
	
	Fish(float x, float y, int connec, int rad, int strokeAlpha, PApplet p){
		this.p = p;
		this.pos = new PVector(x, y);
		velocity = new PVector(p.random(-1.5f, 1.5f), p.random(-1.5f, 1.5f));
	    acceleration = new PVector(0, 0);
	    posViz = new PVector();
		
		this.connec = connec;
		minConnec = 3;
		maxConnec = 7;
		this.rad = rad;
		
		startTimeMovement = p.millis();
		timerMovement = p.millis() + p.random(2, 4)*1000.0f;
		mov = new PVector();
		maxSpeed = 1.0f;
		maxForce = 0.15f;
		dist = 100;
		color = p.color(50);
		
		this.strokeAlpha = strokeAlpha;
		
		sides = (int)p.random(5, 8);
	    pulseRatio = 0.1f;
	    xPulse = p.random(10);
	    
	    slow = 0.5f;
	    
	    lerpAmt = 0.0f;
	    theta = 0.0f;
	}
	
	void update(){
	    if ((p.millis() - startTimeMovement >= timerMovement)) { //only change speed and direction when you are alone
			  mov = new PVector(p.random(-1f, 1f), p.random(-1f, 1f));
			  mov.normalize();
			  mov.mult(maxSpeed);
			  PVector steer = PVector.sub(mov, velocity);
			  steer.limit(maxForce);
		      applyForce(steer);
		      startTimeMovement = p.millis();
	    }
	    
		velocity.add(acceleration);
		if(PolSys.startGame1) velocity.limit(maxSpeed);
		if(PolSys.startGame2) velocity.limit(maxSpeed*slow);
		pos.add(velocity);
		  
		acceleration.mult(0);
	}
	
	  void applyForce(PVector f){
		  acceleration.add(f);
	  }
	
	  void boundaries() {

		    PVector desired = null;

		    if (pos.x < (p.width/2 - p.height/6) + rad) {
		      desired = new PVector(maxSpeed, velocity.y);
		    } 
		    else if (pos.x > (p.width/2 + p.height/6) - rad) {
		      desired = new PVector(-maxSpeed, velocity.y);
		    } 

		    if (pos.y < (p.height/2 - p.height/6) + rad) {
		      desired = new PVector(velocity.x, maxSpeed);
		    } 
		    else if (pos.y > (p.height/2 + p.height/6) - rad) {
		      desired = new PVector(velocity.x, -maxSpeed);
		    } 

		    if (desired != null) {
		      desired.normalize();
		      desired.mult(maxSpeed);
		      PVector steer = PVector.sub(desired, this.velocity);
		      steer.limit(maxForce);
		      applyForce(steer);
		    }
		  }
	  
	  void collide(){
		  for(int i = 0; i < PolSys.fishes.size(); i++){
			  Fish f = PolSys.fishes.get(i);
			  if (PApplet.dist(this.pos.x, this.pos.y, f.pos.x, f.pos.y) < rad && this != f){
				  
				  PVector bounce = this.velocity.get();
			        bounce.mult(-1.0f);
			        applyForce(bounce);
			        
			        PVector bounce2 = f.velocity.get();
			        bounce2.mult(-1.0f);
			        applyForce(bounce2);
			  }
		  }
	  }
	  
	void display(){

		this.connec = PolSys.connec;
		
		if(PolSys.startGame1){
			connec = p.constrain(connec, minConnec, maxConnec);
			for(int i = 0; i < connec; i++){
				p.stroke(0, 100);
				p.strokeWeight(2);
				p.noFill();
				p.ellipse(pos.x, pos.y, (i+2)*2, (i+2)*2);
			}
			
			p.stroke(color);
			p.strokeWeight(2);
			p.ellipse(pos.x, pos.y, rad, rad);
		}
		
		if(PolSys.startGame2){
			p.noFill();
			p.strokeWeight(2);
			p.stroke(10, strokeAlpha);
			  float angleHeading = this.velocity.heading2D() + PApplet.PI/2;
			  p.pushMatrix();
			  p.translate(pos.x, pos.y);
			  p.rotate(angleHeading);
			  
			  float angleInc = 360/(sides);
			  
			  //base shape
			  p.beginShape();
			  for(int i = 0; i < 360; i += angleInc){
				  float x = PApplet.cos(PApplet.radians(i))*(rad*0.5f);
				  float y = PApplet.sin(PApplet.radians(i))*(rad*0.5f);
				  p.vertex(x, y);
			  }
			  p.endShape(PApplet.CLOSE);
			  rad += (PApplet.cos(xPulse))*0.15f;
			  xPulse += pulseRatio;
			  p.popMatrix();
			  
				for(int i = 0; i < PolSys.fishNumber; i++){
					Fish f = PolSys.fishes.get(i);
					if(PApplet.dist(this.pos.x, this.pos.y, f.pos.x, f.pos.y) < dist){
						p.stroke(0, 50);
						p.strokeWeight(1);
						p.line(this.pos.x, this.pos.y, f.pos.x, f.pos.y);
					}

				}
		}

		if(PolSys.startGame3){
			
		}
		
	}
	
	void predatorDisplay(){
		p.rectMode(PConstants.CENTER);
	    p.strokeWeight(2);
	    p.stroke(20, 20, 0);
	    p.noFill();
		theta = this.velocity.heading2D() + PApplet.PI/2;
    	p.pushMatrix();
    	p.translate(pos.x, pos.y);
    	p.rotate(theta);
    	p.rect(0, 0, rad, rad*2.0f);
    	p.stroke(20, 20, 0, 200);
    	p.rect(0, 0, rad, rad*2);
    	p.popMatrix();

	    boundaries();
	}
	
	void drawConnections(){		
		for(int i = 0; i < PolSys.fishes.size(); i++){
			Fish f = PolSys.fishes.get(i);
			if(PApplet.dist(this.pos.x, this.pos.y, f.pos.x, f.pos.y) < dist){
				p.stroke(0, 50);
				for(int j = 0; j < connec; j++){
					p.line(this.pos.x, this.pos.y, f.pos.x, f.pos.y);
				}
			}

		}
	}
	
	void drawPower(int fp){
		p.stroke(10, 100);
		p.strokeWeight(1);	
		
		for(int i = 0; i < fp; i++){
			Fish fish = PolSys.fishes.get(i);
			
			if(this != fish && PApplet.dist(this.pos.x, this.pos.y, fish.pos.x, fish.pos.y) < dist){
				posViz = PVector.lerp(this.pos, fish.pos, lerpAmt);
				p.pushMatrix();
			    p.translate(posViz.x, posViz.y);
			    p.rotate(theta);
			    p.line(-5.0f, 0.0f, 0.0f, -7.0f);
			    p.line(5.0f, 0.0f, 0.0f, -7.0f);
			    p.popMatrix();
			    
				PVector dir = PVector.sub(fish.pos, this.pos);
				theta = dir.heading2D()+PApplet.PI*0.5f;
				
				if(lerpAmt < 1){
					lerpAmt += 0.005f;
				}else{
					lerpAmt = 0;
				}
			}

		}
	}
	
	void drawFriendship(int ff){
		p.stroke(PolSys.colorFriendship, 100);
		p.strokeWeight(1);
		for(int i = 0; i < ff; i++){
			Fish fish = PolSys.fishes.get(i);
			if(PApplet.dist(this.pos.x, this.pos.y, fish.pos.x, fish.pos.y) < dist){
				for(int j = -2; j < 2; j++){
					p.line(this.pos.x+j*3, this.pos.y, fish.pos.x+j*3, fish.pos.y);
				}
				
			}

		}
	}
	
	void drawLove(int fl, Fish f1, Fish f2){
		p.stroke(200, 50, 50, fl);
		p.fill(200, 50, 50, 20+fl);
		p.strokeWeight(30);
		
		PVector v = PVector.sub(f1.pos, f2.pos);
		float rad = v.mag();
		if(rad > 60) f2.applyForce(v);
		
		PVector v2 = PVector.sub(f2.pos, f1.pos);
		float rad2 = v.mag();
		if(rad2 > 60) f1.applyForce(v2);
		
		PVector pos = new PVector((f1.pos.x+f2.pos.x)*0.5f, (f1.pos.y+f2.pos.y)*0.5f);
		
		p.ellipse(pos.x, pos.y, rad, rad);

	}
	
	void drawChildren(){
		
	}

}

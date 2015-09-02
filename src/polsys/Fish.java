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
	float splashMaxSpeed;
	float maxForce;
	float dist;
	
	int alpha;
	int alphaPred;
	
	int color;
	int strokeAlpha;
	float slow;
	
	float theta;
	float lerpAmt;
	
	// 2
	int sides;
	float pulseRatio;
	float xPulse;
	
	int powerSW;
	
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
		alphaPred = 30;
		mov = new PVector();
		maxSpeed = 1.0f;

		maxForce = 0.75f;
		powerSW = 2;
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
		splashMaxSpeed = 0.75f;
		maxForce = 0.15f;
		dist = 100;
		color = p.color(50);
		
		alpha = 50;
		
		this.strokeAlpha = strokeAlpha;
		
		sides = (int)p.random(5, 8);
	    pulseRatio = 0.05f;
	    xPulse = p.random(10);
	    
	    slow = 0.5f;
	    
	    lerpAmt = 0.0f;
	    theta = 0.0f;
	}
	
	void update(){
	    if ((p.millis() - startTimeMovement >= timerMovement)) { //only change speed and type when you are alone
			  mov = new PVector(p.random(-1f, 1f), p.random(-1f, 1f));
			  mov.normalize();
			  mov.mult(maxSpeed);
			  PVector steer = PVector.sub(mov, velocity);
			  steer.limit(maxForce);
		      applyForce(steer);
		      startTimeMovement = p.millis();
	    }
	    
		velocity.add(acceleration);
		if(PolSys.splash) velocity.limit(splashMaxSpeed);
		if(PolSys.startGame1) velocity.limit(maxSpeed);
		if(PolSys.startGame2) velocity.limit(maxSpeed*slow);
		pos.add(velocity);
		  
		acceleration.mult(0);
	}
	
	  void applyForce(PVector f){
		  acceleration.add(f);
	  }
	
	  void boundaries() {
		  
		  if(PolSys.splash){
			  PVector desired = null;

			    if (pos.x < 0 + rad*2.0f) {
			      desired = new PVector(maxSpeed, velocity.y);
			    } 
			    else if (pos.x > p.width - rad*2.0f) {
			      desired = new PVector(-maxSpeed, velocity.y);
			    } 

			    if (pos.y < p.height*0.2f + rad*2.0f) {
			      desired = new PVector(velocity.x, maxSpeed);
			    } 
			    else if (pos.y > p.height - rad*2.0f) {
			      desired = new PVector(velocity.x, -maxSpeed);
			    } 

			    if (desired != null) {
			      desired.normalize();
			      desired.mult(maxSpeed);
			      PVector steer = PVector.sub(desired, this.velocity);
			      steer.limit(maxForce);
			      applyForce(steer);
			    }
		  }else{
			  PVector desired = null;

			    if (pos.x < (PolSys.fishTankPos.x - p.height/6) + rad*2.0f) {
			      desired = new PVector(maxSpeed, velocity.y);
			    } 
			    else if (pos.x > (PolSys.fishTankPos.x + p.height/6) - rad*2.0f) {
			      desired = new PVector(-maxSpeed, velocity.y);
			    } 

			    if (pos.y < (PolSys.fishTankPos.y - p.height/6) + rad*2.0f) {
			      desired = new PVector(velocity.x, maxSpeed);
			    } 
			    else if (pos.y > (PolSys.fishTankPos.y + p.height/6) - rad*2.0f) {
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
	 }
	  
	  void collide(){
		  if(PolSys.splash){
			  for(int i = 0; i < PolSys.splashFish.length; i++){
				  Fish f = PolSys.splashFish[i];
				  if (PApplet.dist(this.pos.x, this.pos.y, f.pos.x, f.pos.y) < rad*1.25f && this != f){
				        PVector diff = PVector.sub(this.pos, f.pos);
				        diff.normalize();
				        diff.mult(maxSpeed);
				        
				        PVector steer = PVector.sub(diff, velocity);
				        steer.limit(maxForce);
				        applyForce(steer);
				  }
			  }
		  }else{
			  for(int i = 0; i < PolSys.fishes.size(); i++){
				  Fish f = PolSys.fishes.get(i);
				  if (PApplet.dist(this.pos.x, this.pos.y, f.pos.x, f.pos.y) < rad*1.25f && this != f){
				        PVector diff = PVector.sub(this.pos, f.pos);
				        diff.normalize();
				        diff.mult(maxSpeed);
				        
				        PVector steer = PVector.sub(diff, velocity);
				        steer.limit(maxForce);
				        applyForce(steer);
				  }
			  }
		  }
	  }
	  
	void display(){

		this.connec = PolSys.connec;
		
		if(PApplet.dist(p.mouseX, p.mouseY, this.pos.x, this.pos.y) < this.rad){
			p.textFont(PolSys.thoughtFont);
			p.textSize(PolSys.thoughtFontSize);
			p.fill(0);
			p.text("hello", pos.x + rad*0.75f, pos.y - rad*0.75f);
		}
		
		if(PolSys.startGame1){
			connec = PApplet.constrain(connec, minConnec, maxConnec);
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
		
		if(PolSys.startGame2 || PolSys.splash){
			p.noFill();
			if(PolSys.splash) p.fill(250);
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
			  rad += (PApplet.cos(xPulse))*0.25f;
			  xPulse += pulseRatio;
			  p.popMatrix();
		}
	}
	
	void predatorDisplay(){
		if(PApplet.dist(p.mouseX, p.mouseY, this.pos.x, this.pos.y) < this.rad){
			p.textFont(PolSys.thoughtFont);
			p.textSize(PolSys.thoughtFontSize);
			p.fill(0, 200);
			p.text("a predator", pos.x + rad*0.75f, pos.y - rad*0.75f);
		}
		
		p.rectMode(PConstants.CENTER);
	    p.strokeWeight(2);
	    p.stroke(PolSys.colorPredator, alphaPred);
	    p.noFill();
		theta = this.velocity.heading2D() + PApplet.PI/2;
    	p.pushMatrix();
    	p.translate(pos.x, pos.y);
    	p.rotate(theta);
    	p.rect(0, 0, rad, rad*2.0f);
    	p.stroke(PolSys.colorPredator, alphaPred);
    	p.rect(0, 0, rad, rad*2);
    	p.popMatrix();

	    boundaries();
	    p.noStroke();
	}
	
	void drawConnections(){
		if(PolSys.splash){
			for(int i = 0; i < PolSys.splashFish.length; i++){
				Fish f = PolSys.splashFish[i];
				if(PApplet.dist(this.pos.x, this.pos.y, f.pos.x, f.pos.y) < dist){
					p.stroke(0, 5);
					p.strokeWeight(2);
					for(int j = 0; j < connec; j++){
						p.line(this.pos.x, this.pos.y, f.pos.x, f.pos.y);
					}
				}
	
			}
		}else{
			for(int i = 0; i < PolSys.fishes.size(); i++){
				Fish f = PolSys.fishes.get(i);
				if(PApplet.dist(this.pos.x, this.pos.y, f.pos.x, f.pos.y) < dist){
					p.stroke(0, alpha);
					p.strokeWeight(powerSW);
					for(int j = 0; j < connec; j++){
						p.line(this.pos.x, this.pos.y, f.pos.x, f.pos.y);
					}
				}
	
			}
		}
	}
	
	void drawPower(int fp){
		p.stroke(10, 100);
		p.strokeWeight(powerSW);
		p.strokeCap(PApplet.SQUARE);
		
		for(int i = 0; i < fp; i++){
			Fish fish = PolSys.fishes.get(i);
			
			if(this != fish && PApplet.dist(this.pos.x, this.pos.y, fish.pos.x, fish.pos.y) < dist){
				posViz = PVector.lerp(this.pos, fish.pos, lerpAmt);
				p.pushMatrix();
				PVector dir = PVector.sub(fish.pos, this.pos);
				theta = dir.heading2D()+PApplet.PI*0.5f;
			    p.translate(posViz.x, posViz.y);
			    p.rotate(theta);
			    p.line(-5.0f, 0.0f, 0.0f, -7.0f);
			    p.line(5.0f, 0.0f, 0.0f, -7.0f);
			    p.popMatrix();
			    
				
				
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
					p.line(this.pos.x-j*3, this.pos.y, fish.pos.x+j*3, fish.pos.y);
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

}

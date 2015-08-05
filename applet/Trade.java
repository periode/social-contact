package polsys;

import processing.core.PApplet;
import processing.core.PVector;

public class Trade {
	
	PApplet p;
	PVector pos;
	Nation n1;
	Nation n2;
	Nation seller;
	Nation buyer;
	float volume;
	
	int vesselsNum;
	PVector[] vesselsPos;
	float[] vesselsLerp;
	float lerpAmount;
	float vesselsSize;
	
	float theta;
	
	int col;
	int sw;
	float alpha;
	float alphaMax;
	float alphaInc;
	
	Trade(){}
	
	Trade(Nation n1, Nation n2, PApplet p){
		this.p = p;
		this.n1 = n1;
		this.n2 = n2;
		pos = new PVector((n1.pos.x+n2.pos.x)*0.5f, (n1.pos.y+n2.pos.y)*0.5f);
		this.volume = PApplet.abs(n1.totalWealth - n2.totalWealth);
		this.vesselsSize = (int) PApplet.map(volume, 0, 100, 1, 20);
		vesselsNum = 5;
		vesselsPos = new PVector[vesselsNum];
		vesselsLerp = new float[vesselsNum];
		if(n1.totalWealth > n2.totalWealth){
			seller = n1;
			buyer = n2;
		}else{
			seller = n2;
			buyer = n1;
		}
		for(int i = 0; i < vesselsNum; i++){
			vesselsLerp[i] = i*0.2f;
			vesselsPos[i] = new PVector(seller.pos.x, seller.pos.y);
		}
		
		lerpAmount = 0.01f;
		
		theta = 0;
		
		alpha = -50;
		alphaMax = 100;
		alphaInc = 1.0f;
		col = p.color(0, 100, 200); 
		sw = 10;
		
	}
	
	void display(){
		update();
		p.stroke(col, alpha);
		p.strokeWeight(2);
		p.rectMode(PApplet.CENTER);
		for(int i = 0; i < vesselsNum; i++){
			vesselsPos[i] = PVector.lerp(seller.pos, buyer.pos, vesselsLerp[i]);
			p.pushMatrix();
			p.translate(vesselsPos[i].x, vesselsPos[i].y);
			p.rotate(theta);
			p.rect(0, 0, 20, vesselsSize);
			p.popMatrix();
			vesselsLerp[i] += lerpAmount;
			if(vesselsLerp[i] > 1) vesselsLerp[i] = 0;
		}
		

		p.fill(0);
		//p.text("vol: "+volume, pos.x,pos.y);
		p.noFill();
	}
	
	void update(){
		if(alpha < alphaMax) alpha += alphaInc;
		this.volume = PApplet.abs(n1.totalWealth - n2.totalWealth);
		this.volume = PApplet.constrain(volume, 0.0f, 10000.0f);
		this.vesselsSize = (int) PApplet.map(volume, 0, 100, 2, 10);
		this.vesselsSize = PApplet.constrain(vesselsSize, 2, 10);
		
		PVector dir = PVector.sub(buyer.pos, seller.pos);
		theta = dir.heading2D(); //+ PApplet.PI*0.5f;
	}

}

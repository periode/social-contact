package polsys;

import processing.core.PApplet;
import processing.core.PVector;

public class War {

	PApplet p;
	PVector pos;
	Nation n1;
	Nation n2;
	float intensity;
	
	int col;
	int sw;
	float alpha;
	float alphaMax;
	float alphaInc;
	
	Nation larger;
	Nation smaller;
	
	int lineNum;
	PVector halfway;
	float theta;
	
	float lerp;
	
	War(){}
	
	War(Nation n1, Nation n2, PApplet p){
		this.p = p;
		this.n1 = n1;
		this.n2 = n2;
		
		intensity = PApplet.abs(n1.culturalHomogeneity - n2.culturalHomogeneity);
		alpha = 0;
		alphaMax = 150;
		alphaInc = 2.0f;
		col = p.color(255, 0, 0);
		lineNum = 7;
		halfway = new PVector((n1.pos.x+n2.pos.x)*0.5f, (n1.pos.y+n2.pos.y)*0.5f);
	}
	
	void display(){
		update();
		p.stroke(255-p.random(50), 0, 0, alpha);
		p.strokeWeight(2);
		for(int i = 0; i < lineNum; i++){
			float r1 = p.random(-20, 19);
			float r2 = p.random(-19, 20);
			p.line(n1.pos.x, n1.pos.y, halfway.x+r2, halfway.y+r2);
			p.line(halfway.x+r1, halfway.y+r1, n2.pos.x, n2.pos.y);
		}

	}
	
	void update(){
		if(alpha < alphaMax) alpha += alphaInc;
		halfway = new PVector((n1.pos.x+n2.pos.x)*0.5f, (n1.pos.y+n2.pos.y)*0.5f);
		PVector dir = PVector.sub(n1.pos, n2.pos);
		theta = dir.heading2D();
		
		if(n1.totalWealth > n2.totalWealth){
			larger = n1;
			smaller = n2;
		}else{
			larger = n2;
			smaller = n1;
		}
		
		lerp = PApplet.map(PApplet.abs(larger.totalWealth - smaller.totalWealth), 0.0f, 10000.0f, 0.5f, 0.9f);
		
		halfway = PVector.lerp(larger.pos, smaller.pos, lerp);
	}

}

package polsys;

import processing.core.PApplet;
import processing.core.PVector;

public class Alliance {

	PApplet p;
	PVector pos;
	Nation n1;
	Nation n2;
	float strength;
	
	int col;
	int sw;
	float alpha;
	float alphaMax;
	float alphaInc;
	
	int numConnecs;
	
	Alliance(){}
	
	Alliance(Nation n1, Nation n2, PApplet p){
		this.p = p;
		this.n1 = n1;
		this.n2 = n2;
		pos = new PVector((n1.pos.x+n2.pos.x)*0.5f, (n1.pos.y+n2.pos.y)*0.5f);
		this.strength = PApplet.abs(n1.culturalHomogeneity-n2.culturalHomogeneity);
		
		alpha = 0;
		alphaMax = 150;
		alphaInc = 1.0f;
		col = p.color(80, 150, 80);
		sw = 3;
	}
	
	void display(){
		update();
		p.stroke(col, alpha);
		p.strokeWeight(sw);
		for(int i = 0; i < numConnecs; i++){
			p.line(n1.citizenPos[i].x+n1.pos.x, n1.citizenPos[i].y+n1.pos.y, n2.citizenPos[i].x+n2.pos.x, n2.citizenPos[i].y+n2.pos.y);
		}
		
	}
	
	void update(){
		numConnecs = (int)(p.min(n1.citizenPos.length, n2.citizenPos.length)*0.25f);
		if(alpha < alphaMax) alpha += alphaInc;
	}
	
}

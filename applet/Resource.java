package polsys;

import processing.core.PApplet;
import processing.core.PVector;

public class Resource {

	PApplet p;
	
	PVector pos;
	float rad;
	float maxRad;
	int sides;
	int[] sidesVar;
	
	int colorFill;
	int colorStroke;
	int newColorFill;
	int newColorStroke;
	float colorLerpSpeed;
	float alpha;
	
	float depleteRate;
	
	int i;
	
	Resource(){}
	
	Resource(float x, float y, float rad, PApplet p){
		this.p = p;
		pos = new PVector(x, y);
		this.rad = rad;
		maxRad = rad;
		sides = (int)(p.random(5, 1));
		
		colorStroke = p.color(20, 135, 20);
		colorFill = p.color(50, 155, 50);
		newColorStroke = colorStroke;
		newColorFill = colorFill;
		colorLerpSpeed = 0;
		
		depleteRate = p.random(1, 5)*0.03f;
		sidesVar = new int[sides];
		for(int i = 0; i < sidesVar.length; i++){
			sidesVar[i] = (int)p.random(10, 50);
		}
		i = 0;
		alpha = 70;
	}
	
	void display(){
		
		if(PolSys.seasons.numberOfSeasons == 1){
			newColorFill = p.color(120, 120, 150, alpha); //winter
			newColorStroke = p.color(90, 90, 120, alpha);
		}
		if(PolSys.seasons.numberOfSeasons == 2){
			newColorFill = p.color(150, 120, 150, alpha); //spring
			newColorStroke = p.color(120, 90, 120, alpha);
		}
		if(PolSys.seasons.numberOfSeasons == 3){
			newColorFill = p.color(50, 155, 50, alpha); //summer
			newColorStroke = p.color(20, 125, 120, alpha);
		}
		if(PolSys.seasons.numberOfSeasons == 0){
			newColorFill = p.color(150, 120, 150, alpha); //fall
			newColorStroke = p.color(120, 90, 120, alpha);
		}
		
		colorStroke = p.lerpColor(colorStroke, newColorStroke, colorLerpSpeed);
		colorFill = p.lerpColor(colorFill, newColorFill, colorLerpSpeed);
		
		if(colorLerpSpeed > 1){
			colorStroke = newColorStroke;
			colorFill = newColorFill;
			colorLerpSpeed = 0.0f;
		}
		
		p.stroke(colorStroke, alpha);
		p.fill(colorFill, alpha);
		
		
		int angleIncrement = (int)(360/sides);
		p.beginShape();
		i = 0;
		for(float angle = 0; angle < 360; angle += angleIncrement){
			float x = pos.x + PApplet.sin(PApplet.radians(angle))*(rad+sidesVar[i]);
			float y = pos.y + PApplet.cos(PApplet.radians(angle))*(rad+sidesVar[i]);
			p.vertex(x, y);
			if(i < sidesVar.length-1) i++;
		}
		p.endShape(PApplet.CLOSE);
	}
	
	void deplete(){
		if(PolSys.inGame1){
			int n = 0; //number of agents on the resource
			for(int i = 0; i < PolSys.agents.length; i++){
				if(PApplet.dist(pos.x, pos.y, PolSys.agents[i].pos.x, PolSys.agents[i].pos.y) < rad && PolSys.agents[i].isAlive && PolSys.agents[i].arrived){
					n += 1;
					if(PolSys.agents[i].wealthAccumulation < 500) PolSys.agents[i].wealthAccumulation += PolSys.agents[i].wealthAccumulationRate * rad;
				}
			}
			rad -= depleteRate*n;
			
			if(n == 0){
				if(rad < maxRad) rad += depleteRate*0.5f;
			}
		}else if(PolSys.inGame2){
			int n = 0; //number of agents on the resource
			for(int i = 0; i < PolSys.community.size(); i++){
				Agent a = PolSys.community.get(i);
				if(PApplet.dist(pos.x, pos.y, a.pos.x, a.pos.y) < rad && a.isAlive){
					n += 1;
					if(a.wealthAccumulation < 500) a.wealthAccumulation += a.wealthAccumulationRate * rad;
				}
			}
			rad -= depleteRate*n;
			
			if(n == 0){
				if(rad < maxRad) rad += depleteRate*0.5f;
			}
		}

	}
	
}

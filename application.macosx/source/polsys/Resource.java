package polsys;

import processing.core.PApplet;
import processing.core.PVector;

public class Resource {

	PApplet p;
	
	PVector pos;
	float rad;
	float maxRad;
	float maxRadInit;
	int sides;
	int[] sidesVar;
	
	int colorFill;
	int colorStroke;
	int newColorFill;
	int newColorStroke;
	float colorLerpVal;
	float colorLerpInc;
	float alpha;
	
	float depleteRate;
	float depleteRateNormal;
	float depleteRateGrow;
	boolean canGrow;
	
	float depletionThreshold;
	int layers;
	int i;
	
	Resource(){}
	
	Resource(float x, float y, float rad, PApplet p){
		this.p = p;
		pos = new PVector(x, y);
		this.rad = 0;
		maxRadInit = rad;
		maxRad = rad;
		sides = (int)(p.random(5, 1));
		
		colorStroke = p.color(20, 135, 20);
		colorFill = p.color(50, 155, 50);
		newColorStroke = colorStroke;
		newColorFill = colorFill;
		colorLerpVal = 0;
		colorLerpInc = 0.01f;
		
		depleteRateNormal = p.random(1, 5)*0.03f;
		depleteRateGrow = p.random(2, 4)*0.06f;
		depleteRate = depleteRateNormal;
		sidesVar = new int[sides];
		for(int i = 0; i < sidesVar.length; i++){
			sidesVar[i] = (int)p.random(10, 50);
		}
		i = 0;
		alpha = 70;
		canGrow = false;
		
		depletionThreshold = maxRad*0.2f;
		
		layers = 2;
	}
	
	void display(){
		if(Seasons.numberOfSeasons == 1){
			newColorFill = p.color(120, 120, 150, alpha); //winter
			newColorStroke = p.color(90, 90, 120, alpha);
		}
		if(Seasons.numberOfSeasons == 2){
			newColorFill = p.color(150, 120, 150, alpha); //spring
			newColorStroke = p.color(120, 90, 120, alpha);
		}
		if(Seasons.numberOfSeasons == 3){
			newColorFill = p.color(50, 155, 50, alpha); //summer
			newColorStroke = p.color(20, 125, 120, alpha);
		}
		if(Seasons.numberOfSeasons == 0){
			newColorFill = p.color(150, 120, 150, alpha); //fall
			newColorStroke = p.color(120, 90, 120, alpha);
		}
		
		colorStroke = p.lerpColor(colorStroke, newColorStroke, colorLerpVal);
		colorFill = p.lerpColor(colorFill, newColorFill, colorLerpVal);
		
		if(colorLerpVal > 1){
			colorStroke = newColorStroke;
			colorFill = newColorFill;
			colorLerpVal = 0.0f;
		}else{
			colorLerpVal += colorLerpInc;
		}
		
		p.fill(colorFill, alpha);
		p.stroke(colorStroke, alpha);
		
		int angleIncrement = (int)(360/sides);
		for(int j = 0; j < layers; j++){
			p.beginShape();
			i = 0;
			for(float angle = 0; angle < 360; angle += angleIncrement){
				float x = pos.x + PApplet.sin(PApplet.radians(angle))*(rad+(j*7.0f)+sidesVar[i]);
				float y = pos.y + PApplet.cos(PApplet.radians(angle))*(rad+(j*7.0f)+sidesVar[i]);
				p.vertex(x, y);
				if(i < sidesVar.length-1) i++;
			}
			p.endShape(PApplet.CLOSE);
		}

		
		if(canGrow){
			maxRad = p.width;
			depleteRate = depleteRateGrow;
		}else{
			maxRad = maxRadInit;
			depleteRate = depleteRateNormal;
		}
	}
	
	void debug(){
		p.fill(0);
		p.textFont(PolSys.thoughtFont);
		p.textSize(PolSys.thoughtFontSize);
		p.text("rate: "+depleteRate, pos.x, pos.y);
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
				if(rad < maxRad) rad += depleteRate*0.75f;
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
		}else{
			boolean isClose = false;
			for(int i = 0; i < PolSys.others.size(); i++){
				Nation n = PolSys.others.get(i);
				if(PApplet.dist(this.pos.x, this.pos.y, n.pos.x, n.pos.y) < n.rad + this.rad){
					isClose = true;
				}
			}
			
			if(isClose && rad > maxRad*0.1f){
				rad -= depleteRate;
			}else{
				if(rad < maxRad) rad += depleteRate;
			}
		}
	}
	
}

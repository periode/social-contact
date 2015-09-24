package polsys;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Button extends ProcessingObject {
	
	PApplet p;
	
	PVector pos;

	float size;
	float type;
	int c;
	int c1;
	int c2;
	int colorButtonFish;
	int sw;
	int swa;
	  
	float sinVal;
	float sinInc;
	
	float alpha;
	
	float startRectAlpha;
	float startRectSize;
	
	float startRectAlphaInc;
	float startRectSizeInc;
	
	String language;
	
	float w;
	float h;

	Button() {
	}

	Button(float x, float y, float size, int direction, PApplet p) {
		this.p = p;
		this.pos = new PVector(x, y);
	    this.size = size;
	    this.type = direction; //increase (1) or decrease (0) or start (2)
	    c1 = p.color(255);
	    c2 = p.color(100);
	    c = c1;
	    colorButtonFish = p.color(250);
	    sw = 1;
	    swa = 1;
	    
	    sinVal = 1;
	    sinInc = 0.1f;
	    
	    alpha = 150;
	    
	    startRectAlpha = 225f;
	    startRectSize = size;
	    startRectAlphaInc = 12.5f;
	    startRectSizeInc = 7.0f;
	    
	    w = size;
	    h = size;
	}

	void display() {
		this.language = PolSys.language;
		p.rectMode(PConstants.CENTER);
		p.stroke(50, alpha);
		p.pushMatrix();
		p.translate(pos.x, pos.y);
		sinVal += sinInc;
		float inc = (PApplet.sin(sinVal)+1)*2f;
		
	    if (type == 1) {//increase
	    	p.pushMatrix();
	    	p.scale(1.5f);
	    	p.strokeWeight(swa);
	    	p.stroke(50, alpha);
	    	
	    	p.line(0-(size*0.35f), 0+(size*0.2f)+inc, 0, 0-(size*0.3f)+inc);
	    	p.line(0, 0-(size*0.3f)+inc, 0+(size*0.35f), 0+(size*0.2f)+inc);
	    	
	    	p.strokeWeight(sw);
	    	p.line(1-(size*0.2f), 0+(size*0.15f)+inc, -1+(size*0.2f), 0+(size*0.15f)+inc);
	    	p.line(0, 0-(size*0f)+inc, 0, 0+(size*0.3f)+inc);
	    	p.popMatrix();
	    } else if (type == 0) {//decrease
	    	p.pushMatrix();
	    	p.scale(1.5f);
	    	p.strokeWeight(swa);
	    	p.stroke(50, alpha);
	    	
	    	p.line(0-(size*0.35f), 0-(size*0.2f)-inc, 0, 0+(size*0.3f)-inc);
		    p.line(0, 0+(size*0.3f)-inc, 0+(size*0.35f), 0-(size*0.2f)-inc);
		    
		    p.strokeWeight(sw);
		    p.line(1-(size*0.2f), 0-(size*0.15f)-inc, -1+(size*0.2f), 0-(size*0.15f)-inc);
		    p.popMatrix();
	    } else if (type == 2) {//start
			p.textAlign(PConstants.CENTER);
			if(PolSys.startGame1 || PolSys.startGame2 || PolSys.startGame3){
				
				w = size*3.0f;
				h = size*1.0f;
				
		    	if(PolSys.canShowSettings[7]){
					p.stroke(70, startRectAlpha);
					p.noFill();
					p.rect(0, 0, startRectSize*3f, startRectSize);
			    	p.strokeWeight(sw);
			    	p.stroke(70, alpha);
			    	p.fill(30);
		    		p.rect(0, 0, w, h);
		    		p.fill(255);
			    	if(language == "english"){
			    		p.text("social\ncontact", 0, -5);
			    	}else{
			    		p.text("contact\nsocial", 0, -5);
			    	}
			    	startRectAlpha -= startRectAlphaInc;
			    	startRectSize += startRectSizeInc;
		    	}else{
					w = size*3.0f;
					h = size*1.0f;
					
		    		p.fill(150);
			    	p.strokeWeight(1);
			    	p.stroke(200, alpha);
		    		p.rect(0, 0, w, h);
		    		p.fill(255);
			    	if(language == "english"){
			    		p.text("choose\nyour assumptions", 0, -5);
			    	}else{
			    		p.text("choisissez\nvos pr\u00E9jug\u00E9s", 0, -5);
			    	}
		    	}
			}else{
				w = size*4.0f-2;
				h = size*0.5f;
				
	    		p.fill(255, 100);
		    	p.strokeWeight(sw);
	    		p.rect(0, 0, w, h);
	    		p.fill(0);
		    	if(language == "english"){
		    		p.text("your assumptions", 0, size*0.1f);
		    	}else{
		    		p.text("vos pr\u00E9jug\u00E9s", 0, size*0.1f);
		    	}
			}
	    	
	    } else if (type == 3) {//change pursuit right
	    	p.fill(c);
	    	p.strokeWeight(sw);
		    p.line(0+inc, 0+(size*0.25f), 0-(size*0.25f)+inc, 0);
		    p.line(0+inc, 0-(size*0.3f), 0-(size*0.25f)+inc, 0);
	    }else if(type == 4){ //change pursuit left
	    	p.fill(c);
	    	p.strokeWeight(sw);
		    p.line(0-inc, 0+(size*0.25f), 0+(size*0.25f)-inc, 0);
		    p.line(0-inc, 0-(size*0.3f), 0+(size*0.25f)-inc, 0);
	    	p.fill(0);
	    }else if(type == 5){
	    	p.textFont(PolSys.thoughtFont);
	    	p.textSize(PolSys.thoughtFontSize);
	    	p.fill(c, alpha);
	    	p.strokeWeight(1);
	    	p.rect(0, 0, size, size);
	    	p.fill(0, alpha);
	    	p.text("I", 0, size*0.25f);
	    	p.noFill();
	    }else if(type == 6){
			w = size*2.0f;
			h = size*0.5f;
			
	    	p.stroke(70, PolSys.assumptionsRectAlpha);
			p.noFill();
			p.strokeWeight(1);
			p.rect(0, 0, w, h);
    		p.fill(0, PolSys.assumptionsRectAlpha);
	    	if(language == "english"){
	    		p.text("reset", 0, PolSys.textFontSize*0.25f);
	    	}else{
	    		p.text("recommencer", 0, PolSys.textFontSize*0.25f);
	    	}
	    }else if(type == 7){
			w = size*1.5f;
			h = size*0.5f;
			
	    	p.stroke(0, PolSys.beginAlpha);
	    	p.textFont(PolSys.textFont);
	    	p.textSize(PolSys.textFontSize);
	    	p.noFill();
	    	p.rect(0, 0, w, h);
	    	p.fill(100, PolSys.beginAlpha);
	    	p.text("english", 0, PolSys.textFontSize*0.25f);
	    	p.noFill();
	    }else if(type == 8){
	    	w = size*1.5f;
			h = size*0.5f;
			
	    	p.stroke(0, PolSys.beginAlpha);
	    	p.textFont(PolSys.textFont);
	    	p.textSize(PolSys.textFontSize);
	    	p.noFill();
	    	p.rect(0, 0, w, h);
	    	p.fill(100, PolSys.beginAlpha);
	    	p.text("fran\u00E7ais", 0, PolSys.textFontSize*0.25f);
	    	p.noFill();
	    }else if(type == 9){
	    	p.textFont(PolSys.thoughtFont);
	    	p.textSize(PolSys.thoughtFontSize);
	    	p.fill(c, alpha);
	    	p.strokeWeight(1);
	    	p.rect(0, 0, size, size);
	    	p.fill(0, alpha);
	    	p.text("II", 0, size*0.25f);
	    	p.noFill();
	    }else if(type == 10){
	    	p.textFont(PolSys.thoughtFont);
	    	p.textSize(PolSys.thoughtFontSize);
	    	p.fill(c, 50);
	    	p.strokeWeight(1);
	    	p.rect(0, 0, size, size);
	    	p.fill(0, alpha);
	    	p.text("III", 0, size*0.25f);
	    	p.noFill();
	    }else if(type == 11){//fish tank buttons
	    	p.textFont(PolSys.textFont);
	    	p.textSize(PolSys.textFontSize);
	    	p.noStroke();
	    	if(PolSys.startGame1){
	    		w = size*4.0f;
	    		h = size;
	    		
	    		p.fill(PolSys.colorPredator, alpha+80);
		    	p.rect(0, 0-size*0.25f, w, h);
		    	p.fill(colorButtonFish);
	    		if(PolSys.language == "english"){
	    			p.text("predators", 0, 0);
	    		}else{
	    			p.text("pr\u00E9dateurs", 0, 0);
	    		}
	    	}
	    	if(PolSys.startGame2){
	    		w = size*4.0f;
	    		h = size;
	    		
	    		p.fill(PolSys.colorFriendship, alpha);
		    	p.rect(0, 0-size*0.25f, w, h);
		    	p.fill(colorButtonFish);
	    		if(language == "english"){
	    			p.text("friendship", 0, 0);
	    		}else{
	    			p.text("amiti\u00E9", 0, 0);
	    		}
	    	}
	    	if(PolSys.startGame3){
	    		w = size*4.0f;
	    		h = size;
	    		
	    		p.fill(PolSys.colorAlliances, alpha);
		    	p.rect(0, 0-size*0.25f, w, h);
		    	p.fill(colorButtonFish);
	    		p.text("alliances", 0, 0);
	    	}
	    	p.noFill();
	    }else if(type == 12){
	    	p.textFont(PolSys.textFont);
	    	p.textSize(PolSys.textFontSize);
	    	p.noStroke();
	    	
    		w = size*4.0f;
    		h = size;
    		
	    	if(PolSys.startGame1){
	    		p.fill(PolSys.colorResources, alpha);
		    	p.rect(0, 0-size*0.25f, w, h);
		    	p.fill(colorButtonFish);
	    		if(language == "english"){
	    			p.text("resources", 0, 0);
	    		}else{
	    			p.text("ressources", 0, 0);
	    		}
	    	}
	    	if(PolSys.startGame2){
	    		p.fill(PolSys.colorLove, alpha);
		    	p.rect(0, 0-size*0.25f, w, h);
		    	p.fill(colorButtonFish);
	    		if(language == "english"){
	    			p.text("love", 0, 0);
	    		}else{
	    			p.text("amour", 0, 0);
	    		}
	    	}
	    	if(PolSys.startGame3){
	    		p.fill(PolSys.colorWar, alpha);
		    	p.rect(0, 0-size*0.25f, w, h);
		    	p.fill(colorButtonFish);
	    		if(language == "english"){
	    			p.text("war", 0, 0);
	    		}else{
	    			p.text("guerre", 0, 0);
	    		}
	    	}
	    	p.noFill();
	    }else if(type == 13){
	    	p.textFont(PolSys.textFont);
	    	p.textSize(PolSys.textFontSize);
	    	p.noStroke();
	    	
    		w = size*4.0f;
    		h = size;
    		
	    	if(PolSys.startGame1){
	    		p.fill(50, alpha);
		    	p.rect(0, 0-size*0.25f, w, h);
		    	p.fill(255);
	    		if(language == "english"){
	    			p.text("connections", 0, 0);
	    		}else{
	    			p.text("connexions", 0, 0);
	    		}
	    	}
	    	if(PolSys.startGame2){
	    		p.fill(PolSys.colorPower, alpha);
		    	p.rect(0, 0-size*0.25f, w, h);
		    	p.fill(colorButtonFish);
	    		p.text("oppression", 0, 0);
	    	}
	    	if(PolSys.startGame3){
	    		p.fill(PolSys.colorTrade, alpha);
		    	p.rect(0, 0-size*0.25f, w, h);
		    	p.fill(colorButtonFish);
	    		p.text("commerce", 0, 0);
	    	}
	    	p.noFill();
	    }else if(type == 14){
			w = size*4.0f-2;
			h = size*0.5f;
			
    		p.fill(255, 100);
	    	p.strokeWeight(sw);
    		p.rect(0, 0, w, h);
    		p.fill(0);
	    	if(language == "english"){
	    		p.text("legend", 0, size*0.1f);
	    	}else{
	    		p.text("l\u00E9gende", 0, size*0.1f);
	    	}
	    }
	    
	    p.popMatrix();
	    
	    
	    if(PApplet.dist(p.mouseX, p.mouseY, pos.x, pos.y) < size*1.5f){
	    	alpha = 200;
	    	swa = 2;
	    }else{
	    	alpha = 100;
	    	swa = 1;
	    }
	}

	boolean onClick() {
	    if (p.mouseX > this.pos.x-w*0.5f && p.mouseX < this.pos.x+w*0.5f && p.mouseY > this.pos.y-h*0.5f && p.mouseY < this.pos.y +h*0.5f) {
	    	//if(p.mousePressed && !PolSys.statement && !PolSys.intro) PolSys.interactions++;
	    	PolSys.mouseHovering = true;
	    	return true;
	    } else {
	    	return false;
	    }
	}
}

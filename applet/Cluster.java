package polsys;

import java.util.ArrayList;

import processing.core.PVector;

public class Cluster extends ProcessingObject {
	
	 ArrayList<Agent> agentsInside;
	 float size = 20;
	  
	  public Cluster(Agent a){
	    agentsInside = new ArrayList<Agent>();
	    agentsInside.add(a);
	  }
	  
	  public PVector getAveragePos(){
		  
		  PVector averagePos;
		  
		  averagePos = new PVector();
		  
		  for(int i = 0; i < agentsInside.size(); i++){
			  Agent a = agentsInside.get(i);
				  averagePos.x += a.pos.x;
				  averagePos.y += a.pos.y;
		  }
		  
		  averagePos.x = averagePos.x/agentsInside.size();
		  averagePos.y = averagePos.y/agentsInside.size();
		  
		  return averagePos;
	  }
	  
	  public void addAgent(Agent a){
		  if(!agentsInside.contains(a)) agentsInside.add(a);
		  a.cluster = this;
	  }
	  
	  public void removeAgent(Agent a){
		 agentsInside.remove(a);
		 a.cluster = new Cluster(a);
	  }
	  
	  public void buryAgent(Agent a){
		  agentsInside.remove(a);
		  a.cluster = null; /////////
	  }
}

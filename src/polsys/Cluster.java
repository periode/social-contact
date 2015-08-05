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
		  if(!agentsInside.contains(a) || !a.isAlive) agentsInside.add(a);
		  a.cluster = this;
	  }
	  
	  public void removeAgent(Agent a){
		 agentsInside.remove(a);
		 a.cluster = new Cluster(a);
		 
		 for(int i = 0; i < a.currentConnections.size(); i++){
			 Connection c = a.currentConnections.get(i);
			 if(c.a1 == a){
				 c.a2.connec++;
			 }else{
				 c.a1.connec++;
			 }
		  }
	  }
	  
	  public void buryAgent(Agent a){
		  if(PolSys.rememberDead == 0.5f){ //if rememberDead == 1.0f, then you stay with them and if 1.5f then you consider them like living beings
			  agentsInside.remove(a);
			  a.cluster = null;
		  }
		  
		  for(int i = 0; i < a.currentConnections.size(); i++){
			  Connection c = a.currentConnections.get(i);
			  if(c.a1 == a){
				  c.a2.connec++;
			  }else{
				  c.a1.connec++;
			  }
		  }
	  }
}

package agent.sma.core;

import java.util.List;

import agent.sma.parameter.Parameter;

public class Environment {
	
	private int width;
	private int height;
	private SMA sma;
	private List<Agent> agentlist;
	public Agent[][] agentsPosition;
	private Parameter parameters;
	
	public Environment(SMA sma, Parameter parameters){
		this.parameters = parameters;
		this.width = parameters.getGridSizeX()*parameters.getBoxSize();
		this.height = parameters.getGridSizeY()*parameters.getBoxSize();
		this.sma = sma;
		
		agentsPosition = new Agent[parameters.getGridSizeX()][parameters.getGridSizeY()];
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public List<Agent> getAgents() {
		return agentlist;
	}
	
	public void setAgentlist(List<Agent> agentlist){
		this.agentlist = agentlist;
	}
	
	public void addAgent(Agent agent){
		sma.addAgent(agent);
	}
	
	public void removeAgent(Agent agent){
		sma.removeAgent(agent);
	}
}

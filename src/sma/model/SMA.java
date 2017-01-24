package sma.model;

import java.util.List;
import java.util.Observable;

import sma.core.Agent;
import sma.parameter.Parameter;

public abstract class SMA extends Observable {
	
	public static int tick;
	protected List<Agent> agentlist;
	protected Environment environment;
	protected Parameter parameters;
	
	public SMA(Parameter parameters){
		this.parameters = parameters;
		this.environment = new Environment(this, parameters);
		
		initAgent(parameters);
		environment.setAgentlist(agentlist);
	}
	
	public List<Agent> getAgentlist(){
		return agentlist;
	}
	
	public Environment getEnvironment(){
		return environment;
	}
	
	public void run(){
		tick = 1;
		long startTimeTotal = System.currentTimeMillis();
		while(tick < parameters.getNbTicks()){
			long startTime = System.currentTimeMillis();

			for (int i = 0; i < agentlist.size(); i++) {
				Agent agent = agentlist.get(i);
				agent.decide();
				agent.update();
			}

			
			tick++;
			
			if(parameters.getRefresh()!= 0 && (tick == 0 || tick % parameters.getRefresh() == 0)){
				setChanged();
	            notifyObservers();
			}
			
			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime);
			if(parameters.needTrace()){
				System.out.println("End of tick "+ tick+"  time : " + duration+"ms");
			}
			
            try {
				Thread.sleep(parameters.getDelay());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long endTimeTotal = System.currentTimeMillis();
		long durationTotal = (endTimeTotal - startTimeTotal);
		System.out.println("Total time : " + durationTotal +" ms");
	}
	
	public void addAgent(Agent agent){
		agentlist.add(agent);
	}
	
	public void removeAgent(Agent agent){
		agentlist.remove(agent);
	}
	
	protected abstract void initAgent(Parameter parameters);
}

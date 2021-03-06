package agent.sma.core;

import java.util.List;
import java.util.Observable;

import agent.sma.parameter.Parameter;

public abstract class SMA extends Observable {

	public static int tick;
	protected List<Agent> agentlist;
	protected Environment environment;
	protected Parameter parameters;
	protected String fileName;

	public SMA(Parameter parameters, String fileName) {
		this.parameters = parameters;
		this.environment = new Environment(this, parameters);

		this.fileName = fileName;
		initAgent(parameters);
		environment.setAgentlist(agentlist);
	}

	public List<Agent> getAgentlist() {
		return agentlist;
	}

	public void run() {
		tick = 1;
		long startTimeTotal = System.currentTimeMillis();
		while (tick < parameters.getNbTicks()) {

			for (int i = 0; i < agentlist.size(); i++) {
				Agent agent = agentlist.get(i);
				agent.decide();
				agent.update();
			}

			tick++;

			if (parameters.getRefresh() != 0 && (tick == 0 || tick % parameters.getRefresh() == 0)) {
				setChanged();
				notifyObservers();
			}

			try {
				Thread.sleep(parameters.getDelay());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long endTimeTotal = System.currentTimeMillis();
		long durationTotal = (endTimeTotal - startTimeTotal);
		System.out.println("Total time : " + durationTotal + " ms for the file " + this.fileName);
	}

	public void addAgent(Agent agent) {
		agentlist.add(agent);
	}

	public void removeAgent(Agent agent) {
		agentlist.remove(agent);
	}

	protected abstract void initAgent(Parameter parameters);
}

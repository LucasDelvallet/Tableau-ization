package agent.sma.model;

import java.awt.Color;

import agent.sma.core.Agent;
import agent.sma.parameter.Parameter;

public class Background extends Agent {

	public Background(Environment environment, Parameter parameters, Position xy) {
		super(environment, parameters, xy);

		color = Color.BLACK;
	}

	@Override
	public void decide() {
	}

	@Override
	public void update() {
	}

	public boolean needToFreeze() {
		return needToFreeze;
	}

	@Override
	public void agentCollisionReaction(Agent collided) {
	}
	
	@Override
	public String trace(){
		return "";
	}

}

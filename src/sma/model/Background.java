package sma.model;

import java.awt.Color;

import sma.core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public class Background extends Agent {

	public Background(Environment environment, Parameter parameters, Position xy) {
		super(environment, parameters, xy);

		color = Color.WHITE;
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

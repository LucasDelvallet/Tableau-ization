package agent.sma.model;

import java.awt.Color;

import agent.sma.core.Agent;
import agent.sma.core.Environment;
import agent.sma.parameter.Parameter;

public class Particule extends Agent {

	private int lifetime;
	public Particule(Environment environment, Parameter parameters, Position xy, Color color, int hashCode, int lifeTime) {
		super(environment, parameters, xy);
		this.lifetime = lifeTime * 50;
		this.color = color;

		nextMove.setX(parameters.getBoxSize());
		nextMove.setY(parameters.getBoxSize());
	}

	@Override
	public void decide() {
		processAgentCollision();
	}

	@Override
	public void update() {
		if (!needToFreeze) {
			currentPosition = this.getNextPosition();
		} else {
			needToFreeze = false;
		}
		lifetime--;
		if(lifetime == 0){
			this.environment.removeAgent(this);
		}
	}


	@Override
	public void agentCollisionReaction(Agent collided) {
		if(collided.getClass().getSimpleName().equals("Background")){
			collided.setColor(blend(this.color, collided.getColor()));
		}
	}
	
	public static Color blend(Color c0, Color c1) {
		if(c1.equals(Color.BLACK)){
			return c0;
		}
	    double totalAlpha = c0.getAlpha() + c1.getAlpha();
	    double weight0 = c0.getAlpha() / totalAlpha;
	    double weight1 = c1.getAlpha() / totalAlpha;

	    double r = weight0 * c0.getRed() + weight1 * c1.getRed();
	    double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
	    double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
	    double a = Math.max(c0.getAlpha(), c1.getAlpha());

	    return new Color((int) r, (int) g, (int) b, (int)a);
	  }
}

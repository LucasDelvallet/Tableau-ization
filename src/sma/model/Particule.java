package sma.model;

import java.awt.Color;

import sma.core.Agent;
import sma.model.Environment;
import sma.model.Position;
import sma.parameter.Parameter;

public class Particule extends Agent {

	public Particule(Environment environment, Parameter parameters, Position xy, Color color) {
		super(environment, parameters, xy);

		//int r = rand.nextInt(200);
		//int g = rand.nextInt(200);
		//int b = rand.nextInt(200);
		this.color = color;

		setRandomDirection();
	}

	@Override
	public void decide() {
		processAgentCollision();
	}

	@Override
	public void update() {

		if (!needToFreeze) {
			environment.agentsPosition[currentPosition.getX() / parameters.getBoxSize()][currentPosition.getY()
					/ parameters.getBoxSize()] = null;
			currentPosition = this.getNextPosition();
			environment.agentsPosition[currentPosition.getX() / parameters.getBoxSize()][currentPosition.getY()
					/ parameters.getBoxSize()] = this;
		} else {
			needToFreeze = false;
		}

		if (parameters.needTrace() && this.needToFreeze) {
			System.out.println(trace());
		}
	}
	
	public boolean needToFreeze() {
		return needToFreeze;
	}

	@Override
	public void agentCollisionReaction(Agent collided) {
		if(collided.getClass().getSimpleName().equals("Background")){
			collided.setColor(blend(this.color, collided.getColor()));
		}else{
			Position tmp = collided.getNextMove();
			collided.setNextMove(getNextMove());
			setNextMove(tmp);
		}
	}
	
	public static Color blend(Color c0, Color c1) {
	    double totalAlpha = c0.getAlpha() + c1.getAlpha();
	    double weight0 = c0.getAlpha() / totalAlpha;
	    double weight1 = c1.getAlpha() / totalAlpha;

	    double r = weight0 * c0.getRed() + weight1 * c1.getRed();
	    double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
	    double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
	    double a = Math.max(c0.getAlpha(), c1.getAlpha());

	    return new Color((int) r, (int) g, (int) b, (int) a);
	  }
	
	@Override
	public String trace(){
		return "Particle;" + color + ";x=" + nextMove.getX() / parameters.getBoxSize() + ";y="
				+ nextMove.getY() / parameters.getBoxSize();
	}

}

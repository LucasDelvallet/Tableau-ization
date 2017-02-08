package agent.sma.core;

import java.awt.Color;

import agent.sma.model.Position;
import agent.sma.parameter.Parameter;

public abstract class Agent {
	protected Position currentPosition, nextMove;
	protected boolean needToFreeze;
	protected Environment environment;
	protected Parameter parameters;
	protected Color color;

	public Agent(Environment environment, Parameter parameters, Position xy) {
		this.environment = environment;
		this.parameters = parameters;

		currentPosition = xy;
		nextMove = new Position();
		needToFreeze = false;
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}
	
	public Position getCurrentIndex(){
		return new Position(currentPosition.getX()/parameters.getBoxSize(), currentPosition.getY()/parameters.getBoxSize());
	}


	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public Position getNextPosition() {
		Position nextPosition = new Position((currentPosition.getX() + nextMove.getX()),
				currentPosition.getY() + nextMove.getY());

		return nextPosition;
	}

	protected boolean processAgentCollision() {
		if (!processWallCollision()) {
			Position next = getNextPosition();
			Agent agent = null;

			if ((agent = environment.agentsPosition[next.getX() / parameters.getBoxSize()][next.getY()
					/ parameters.getBoxSize()]) != null) {
				if (!agent.equals(this) && agent.getCurrentPosition().equals(next)) {
					agentCollisionReaction(agent);
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * @return true if there is a collision
	 */
	protected boolean processWallCollision() {
		// Check wall colision

		int border_x = environment.getWidth() - parameters.getBoxSize();
		int border_y = environment.getHeight() - parameters.getBoxSize();
		Position nextPosition = getNextPosition();
		boolean res = false;

		if (nextPosition.getX() < 0) {
			nextMove.setX(-nextMove.getX());
			res = true;
		} else if (nextPosition.getX() > border_x) {
			nextMove.setX(-nextMove.getX());
			res = true;
		}

		if (nextPosition.getY() < 0) {
			nextMove.setY(-nextMove.getY());
			res = true;
		} else if (nextPosition.getY() > border_y) {
			nextMove.setY(-nextMove.getY());
			res = true;
		}
		needToFreeze = res;

		return res;
	}

	public abstract void decide();

	public abstract void update();

	public abstract void agentCollisionReaction(Agent collided);	
}

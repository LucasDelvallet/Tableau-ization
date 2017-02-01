package sma.model;

import java.awt.Color;
import java.util.Random;

import sma.parameter.Parameter;

public class Agent {

	private static Random rand;
	private Color color;
	private Position currentPosition, nextMove;
	private boolean haveDecided, needToFreeze;
	protected Environment environment;
	private Parameter parameters;

	public Agent(Environment environment, Parameter parameters, Position xy) {
		this.environment = environment;
		this.parameters = parameters;

		if (rand == null) {
			if (parameters.getSeed() == 0) {
				rand = new Random();
			} else {
				rand = new Random(parameters.getSeed());
			}
		}

		int r = rand.nextInt(200);
		int g = rand.nextInt(200);
		int b = rand.nextInt(200);
		this.color = new Color(r, g, b);

		currentPosition = xy;
		nextMove = new Position();
		haveDecided = false;
		needToFreeze = false;

		setDirection();
	}

	public void decide() {
		if (!haveDecided) {
			if (parameters.isToric()) {
				checkAgentCollision();
			} else {
				if (!checkWallCollision()) {
					checkAgentCollision();
				}
			}

			haveDecided = true;
		}
	}

	public void update() {
		haveDecided = false;

		if (!needToFreeze) {
			environment.agentsPosition[currentPosition.getX() / parameters.getBoxSize()][currentPosition.getY()
					/ parameters.getBoxSize()] = null;
			currentPosition = this.getNextPosition();
			// currentPosition.setX(currentPosition.getX() + nextMove.getX());
			// currentPosition.setY(currentPosition.getY() + nextMove.getY());
			environment.agentsPosition[currentPosition.getX() / parameters.getBoxSize()][currentPosition.getY()
					/ parameters.getBoxSize()] = this;
		} else {
			needToFreeze = false;
		}

		if (parameters.needTrace() && this.needToFreeze) {
			System.out.println("Agent " + color + "  direction x=" + nextMove.getX() / parameters.getBoxSize() + "  y="
					+ nextMove.getY() / parameters.getBoxSize());
		}

	}

	public Position getNextMove() {
		return nextMove;
	}

	public void setNextMove(Position nextMove) {
		needToFreeze = true;
		haveDecided = true;
		this.nextMove = nextMove;
	}

	public Position getNextPosition() {
		Position nextPosition = new Position((currentPosition.getX() + nextMove.getX()),
				currentPosition.getY() + nextMove.getY());
		if (parameters.isToric()) {
			if (nextPosition.getX() < 0) {
				nextPosition.setX((parameters.getGridSizeX() - 1) * parameters.getBoxSize());
			}
			if (nextPosition.getX() >= parameters.getGridSizeX() * parameters.getBoxSize()) {
				nextPosition.setX(0);
			}

			if (nextPosition.getY() < 0) {
				nextPosition.setY((parameters.getGridSizeY() - 1) * parameters.getBoxSize());
			}
			if (nextPosition.getY() >= parameters.getGridSizeY() * parameters.getBoxSize()) {
				nextPosition.setY(0);
			}
		}

		return nextPosition;
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public Color getColor() {
		return color;
	}

	/**
	 * @return true if there is a collision
	 */
	private boolean checkWallCollision() {
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
		nextPosition = getNextPosition();

		return res;
	}

	private void checkAgentCollision() {
		checkCrossAgentCollision();
	}

	/**
	 * @return true if there is a collision
	 */
	private boolean checkCrossAgentCollision() {
		Position next = getNextPosition();
		Agent agent = null;

		if ((agent = environment.agentsPosition[next.getX() / parameters.getBoxSize()][next.getY()
				/ parameters.getBoxSize()]) != null) {
			if (!agent.equals(this) && agent.getCurrentPosition().equals(next)) {
				Position tmp = agent.getNextMove();
				agent.setNextMove(getNextMove());
				setNextMove(tmp);
				return true;
			}
		}

		return false;
	}

	// /**
	// * @return true if there is a collision
	// */
	// private boolean checkOverlapAgentCollision() {
	// Position nextPlannedPosition = getNextPosition();
	//
	// for (Agent agent : environment.getAgents()) {
	// // Si ce n'est pas lui même
	// if (!agent.equals(this) && !agent.haveDecided &&
	// agent.getNextPosition().equals(nextPlannedPosition)) {
	// Position tmp = agent.getNextMove();
	// agent.setNextMove(getNextMove());
	// setNextMove(tmp);
	// return true;
	// }
	// }
	//
	// return false;
	// }

	public boolean needToFreeze() {
		return needToFreeze;
	}

	public boolean haveDecided() {
		return haveDecided;
	}

	private void setDirection() {
		switch (rand.nextInt(8)) {
		case 0:
			nextMove.setX(parameters.getBoxSize());
			nextMove.setY(0);
			break;
		case 1:
			nextMove.setX(parameters.getBoxSize());
			nextMove.setY(parameters.getBoxSize());
			break;
		case 2:
			nextMove.setX(0);
			nextMove.setY(parameters.getBoxSize());
			break;
		case 3:
			nextMove.setX(-parameters.getBoxSize());
			nextMove.setY(parameters.getBoxSize());
			break;
		case 4:
			nextMove.setX(-parameters.getBoxSize());
			nextMove.setY(0);
			break;
		case 5:
			nextMove.setX(-parameters.getBoxSize());
			nextMove.setY(-parameters.getBoxSize());
			break;
		case 6:
			nextMove.setX(0);
			nextMove.setY(-parameters.getBoxSize());
			break;
		case 7:
			nextMove.setX(parameters.getBoxSize());
			nextMove.setY(-parameters.getBoxSize());
			break;
		}
	}
}

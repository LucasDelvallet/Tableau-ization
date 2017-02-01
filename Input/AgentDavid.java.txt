package core;

import utils.CSVManager;

import java.util.Random;

public abstract class Agent {
	
	private int posX,posY;
	private int posXTmp, posYTmp;
	private MyColor color;
	private String direction;
	private int cptCheckAround;

	public Agent(){

	}
	
	public Agent(int posX,int posY, MyColor color, String direction) {
		this.color = color;
		this.setPosX(posX);
		this.setPosY(posY);
		this.direction = initializeDirection();
		this.cptCheckAround =0;
	}
		
	public String initializeDirection(){
		Random r = new Random();
		int nbDir = r.nextInt(Direction.dir.length);
		return Direction.dir[nbDir];
	}

	public abstract void decide();
	
	public MyColor getColor() {
		return color;
	}

	public void setColor(MyColor color) {
		this.color = color;
	}

	public void findNewPosition(){
		if(PropertiesReader.getInstance().getProperties("trace").equals("true"))System.out.println("["+this.getPosX() + ", " + this.getPosY()+"] "+"want to go " + this.direction);

		switch(this.direction){
		case "N":
			posXTmp = posX-1;
			posYTmp = posY;
			break;
		case "S":
			posXTmp = posX+1;
			posYTmp = posY;
			break;
		case "W":
			posXTmp = posX;
			posYTmp = posY-1;
			break;
		case "E":
			posXTmp = posX;
			posYTmp = posY+1;
			break;
		case "NW":
			posXTmp = posX - 1;
			posYTmp = posY - 1;
			break;
		case "NE":
			posXTmp = posX - 1;
			posYTmp = posY + 1;
			break;
		case "SW":
			posXTmp = posX+1;
			posYTmp = posY - 1;
			break;
		case "SE":
			posXTmp = posX+1;
			posYTmp = posY + 1;
			break;
		}
	}

	/**
	 * check if the agent made a move or not, during the click
	 * @return
	 */
	public Boolean madeAMove(int oldX, int oldY){
		if(posX != oldX || posY != oldY)
			return true;
		return false;
	}
	
	public void makeAction(){
		checkBounds();
		// On an existent core.Agent
		if(Environment.getTab()[posXTmp][posYTmp] != null){
			Agent swapAgent = Environment.getTab()[posXTmp][posYTmp];
			if(PropertiesReader.getInstance().getProperties("trace").equals("true"))System.out.println("He collide with " + "["+swapAgent.getPosX() + ", " + swapAgent.getPosY()+"] ");
			swapAgent(swapAgent);
			findNewPosition();
			checkBounds();
			if(Environment.getTab()[posXTmp][posYTmp] != null)

				if(cptCheckAround <Direction.dir.length) {
					cptCheckAround++;
					this.makeAction();
				}else {	
					takeNextFreeSpace();
				}
		}
		this.cptCheckAround = 0;
		Environment.getTab()[posX][posY] = null;

		posX = posXTmp;
		posY = posYTmp;

		Environment.getTab()[posX][posY] = this;

		if(PropertiesReader.getInstance().getProperties("trace").equals("true"))System.out.println("He go to ["+posX+", "+posY+"]");
	}
	public void takeNextFreeSpace() {
		for(int i=0;i<Direction.dir.length; i++) {
			switch(Direction.dir[i]){
			case "N":
				posXTmp = posX-1;
				posYTmp = posY;
				break;
			case "S":
				posXTmp = posX+1;
				posYTmp = posY;
				break;
			case "W":
				posXTmp = posX;
				posYTmp = posY-1;
				break;
			case "E":
				posXTmp = posX;
				posYTmp = posY+1;
				break;
			case "NW":
				posXTmp = posX - 1;
				posYTmp = posY - 1;
				break;
			case "NE":
				posXTmp = posX - 1;
				posYTmp = posY + 1;
				break;
			case "SW":
				posXTmp = posX+1;
				posYTmp = posY - 1;
				break;
			case "SE":
				posXTmp = posX+1;
				posYTmp = posY + 1;
				break;
			}
			checkBounds();
			if(Environment.getTab()[posXTmp][posYTmp] == null){
				i = Direction.dir.length;
			}
			if(i==Direction.dir.length-1) {
				posXTmp = posX;
				posYTmp = posY;
			}
			
		}
	}
	public void checkBounds() {
		boolean changed = false;
		// Outside the map
		if(posXTmp >= Environment.getTailleX()){
			String wall =  Environment.findWall(posXTmp,posYTmp);
			posXTmp = 0;
			if((PropertiesReader.getInstance().getProperties("torique").equals("false"))) {
				posXTmp = Environment.getTailleX() - 2;
				this.direction = Direction.getAntiDir(this.direction, wall);
				changed = true;
			}
		}
		if(posXTmp == -1){
			String wall =  Environment.findWall(posXTmp,posYTmp);
			posXTmp = Environment.getTailleX() - 1;
			if((PropertiesReader.getInstance().getProperties("torique").equals("false"))) {
				posXTmp = 1;
				this.direction = Direction.getAntiDir(this.direction, wall);
				changed = true;
			}
		}
		if(posYTmp >= Environment.getTailleY()){
			String wall =  Environment.findWall(posXTmp,posYTmp);
			posYTmp = 0;
			if((PropertiesReader.getInstance().getProperties("torique").equals("false"))) {
				posYTmp = Environment.getTailleY() - 2;
				if(!changed)this.direction = Direction.getAntiDir(this.direction,wall);
			}
		}
		if(posYTmp == -1){
			String wall =  Environment.findWall(posXTmp,posYTmp);
			posYTmp = Environment.getTailleY() - 1;
			if((PropertiesReader.getInstance().getProperties("torique").equals("false"))) {
				posYTmp = 1;
				if(!changed)this.direction = Direction.getAntiDir(this.direction,wall);
			}
		}
	}

	public int getPosXTmp() {
		return posXTmp;
	}

	public void setPosXTmp(int posXTmp) {
		this.posXTmp = posXTmp;
	}

	public int getPosYTmp() {
		return posYTmp;
	}

	public void setPosYTmp(int posYTmp) {
		this.posYTmp = posYTmp;
	}

	/**
	 * use when two agents will collide
	 * @param swapAgent
	 */
	public void swapAgent(Agent swapAgent){
		String myDirection = this.direction;
		String hisDirection = swapAgent.direction;
		
		swapAgent.direction = myDirection;
		this.direction = hisDirection;
	}

	public int getPosX() {
		return posX;
	}
	
	public void setPosX(int posX) {
		this.posX = posX;
	}


	public int getPosY() {
		return posY;
	}


	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setDirection(String d){ this.direction = d;}

	public void printCSV(String agent, String state){
		if(Boolean.parseBoolean(PropertiesReader.getInstance().getProperties("csv")))
			CSVManager.getInstance().writeCSV("AGENT;"+agent+";"+state+"\n");
	}
}

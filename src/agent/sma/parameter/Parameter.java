package agent.sma.parameter;

public class Parameter {
	private int gridSizeX;
	private int gridSizeY;
	private int boxSize;
	private int delay;
	private int nbTicks; 
	private int refresh;

	
	private int wallsPercentage;
	
	public Parameter(int gridSizeX, int gridSizeY, int boxSize, int delay, int nbTicks, int refresh) {
		this.gridSizeX = gridSizeX;
		this.gridSizeY = gridSizeY;
		this.boxSize = boxSize;
		this.delay = delay;
		this.nbTicks = nbTicks;
		this.refresh = refresh;
	}

	public int getGridSizeX() {
		return gridSizeX;
	}

	public int getGridSizeY() {
		return gridSizeY;
	}

	public int getBoxSize() {
		return boxSize;
	}

	public int getDelay() {
		return delay;
	}

	public int getNbTicks() {
		return nbTicks;
	}

	public int getRefresh() {
		return refresh;
	}
	
	public int getWallsPercent() {
		return wallsPercentage;
	}
	
	public void setEndOfGame(){
		this.nbTicks = 0;
	}
}

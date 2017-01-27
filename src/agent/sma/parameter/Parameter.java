package agent.sma.parameter;

public class Parameter {
	private int gridSizeX;
	private int gridSizeY;
	private int boxSize;
	private int delay;
	private int scheduling; //TODO 0:equitable 1:sequentiel, 2:aléatoire 
	private int nbTicks; 
	private boolean grid;
	private boolean trace; //TODO
	private int seed;
	private int refresh;
	private int nbParticles;
	private boolean toric;
	private int nbFishs;
	private int fishBreedTime;
	private int nbSharks;
	private int sharkBreedTime;
	private int sharkStarveTime;
	private int nbHunters;
	private int speedHunter;
	private int speedAvatar;
	private int defenderLife;
	
	private int wallsPercentage;
	
	public Parameter(int gridSizeX, int gridSizeY, int boxSize, int delay, int scheduling, int nbTicks, boolean grid, boolean trace, int seed, int refresh, int nbParticles, boolean toric, int nbFishs, int fishBreedTime, int nbSharks, int sharkBreedTime, int sharkStarveTime, int wallsPercentage, int nbHunters, int speedHunter, int speedAvatar, int defenderLife) {
		this.gridSizeX = gridSizeX;
		this.gridSizeY = gridSizeY;
		this.boxSize = boxSize;
		this.delay = delay;
		this.scheduling = scheduling;
		this.nbTicks = nbTicks;
		this.grid = grid;
		this.trace = trace;
		this.seed = seed;
		this.refresh = refresh;
		this.nbParticles = nbParticles;
		this.toric = toric;
		this.nbFishs = nbFishs;
		this.fishBreedTime = fishBreedTime;
		this.nbSharks = nbSharks;
		this.sharkBreedTime = sharkBreedTime;
		this.sharkStarveTime = sharkStarveTime;
		this.wallsPercentage = wallsPercentage;
		this.nbHunters = nbHunters;
		this.speedHunter = speedHunter;
		this.speedAvatar = speedAvatar;
		this.defenderLife = defenderLife;
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

	public int getScheduling() {
		return scheduling;
	}

	public int getNbTicks() {
		return nbTicks;
	}

	public boolean needGrid() {
		return grid;
	}

	public boolean needTrace() {
		return trace;
	}

	public int getSeed() {
		return seed;
	}

	public int getRefresh() {
		return refresh;
	}

	public int getNbParticles() {
		return nbParticles;
	}

	public boolean isToric() {
		return toric;
	}
	
	public int getNbFishs() {
		return nbFishs;
	}
	
	public int getFishBreedTime() {
		return fishBreedTime;
	}
	
	public int getNbSharks() {
		return nbSharks;
	}
	
	public int getSharkBreedTime() {
		return sharkBreedTime;
	}
	
	public int getSharkStarveTime() {
		return sharkStarveTime;
	}
	
	public int getWallsPercent() {
		return wallsPercentage;
	}
	
	public int getNbHunters() {
		return nbHunters;
	}
	
	public int getSpeedHunter() {
		return speedHunter;
	}
	
	public int getSpeedAvatar() {
		return speedAvatar;
	}
	
	public int getDefenderLife() {
		return defenderLife;
	}
	
	public void setEndOfGame(){
		this.nbTicks = 0;
	}
}

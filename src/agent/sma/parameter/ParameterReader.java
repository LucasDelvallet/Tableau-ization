package agent.sma.parameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ParameterReader {
	private static final String GRIDSIZE_X = "gridSizeX";
	private static final String GRIDSIZE_Y = "gridSizeY";
	private static final String BOXSIZE = "boxSize";
	private static final String DELAY = "delay";
	private static final String SCHEDHLING = "scheduling";
	private static final String NBTICKS = "nbTicks";
	private static final String GRID = "grid";
	private static final String TRACE = "trace";
	private static final String SEED = "seed";
	private static final String REFRESH = "refresh";
	private static final String NBPARTICLES= "nbParticles";
	private static final String TORIC = "toric";
	private static final String NBFISHS = "nbFishs";
	private static final String FISHBREEDTIME= "fishBreedTime";
	private static final String NBSHARKS = "nbSharks";
	private static final String SHARKBREEDTIME = "sharkBreedTime";
	private static final String SHARKSTARVETIME = "sharkStarveTime";
	private static final String WALLSPERCENTAGE = "wallsPercentage";
	private static final String NBHUNTERS = "nbHunters";
	private static final String SPEEDHUNTER = "speedHunter";
	private static final String SPEEDAVATAR = "speedAvatar";
	private static final String DEFENDERLIFE = "defenderLife";
	// Default values
	private int gridSizeX = 200;
	private int gridSizeY = 200;
	private int boxSize = 7;
	private int delay = 130;
	private int scheduling = 0; //TODO 0:equitable 1:sequentiel, 2:aléatoire 
	private int nbTicks = 3000000; 
	private boolean grid = true;
	private boolean trace = false; //TODO
	private int seed = 1;
	private int refresh = 1;
	private int nbParticles = 50;
	private boolean toric = false;	
	private int nbFishs = 30;
	private int fishBreedTime = 3;
	private int nbSharks = 10;
	private int sharkBreedTime = 5;
	private int sharkStarveTime = 4;
	private int wallsPercentage = 30;
	private int nbHunters = 1;
	private int speedHunter = 1;
	private int speedAvatar = 1;
	private int defenderLife = 10;
	
	public Parameter getParameters(File paramFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(paramFile));
		try {
		    String line = br.readLine();
		    while (line != null) {
		        setVariable(line);
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}
		
		return new Parameter(gridSizeX, gridSizeY, boxSize, delay, scheduling, nbTicks, grid, trace, seed, refresh, nbParticles, toric, nbFishs, fishBreedTime, nbSharks, sharkBreedTime, sharkStarveTime, wallsPercentage, nbHunters, speedHunter, speedAvatar, defenderLife);
	}
	
	private void setVariable(String line) {
		String[] param = line.split(" ");
		
		switch (param[0]) {
		case GRIDSIZE_X:
			this.gridSizeX = Integer.parseInt(param[1]);
			break;
		case GRIDSIZE_Y:
			this.gridSizeY = Integer.parseInt(param[1]);
			break;
		case BOXSIZE:
			this.boxSize = Integer.parseInt(param[1]);
			break;
		case DELAY:
			this.delay = Integer.parseInt(param[1]);
			break;
		case SCHEDHLING:
			this.scheduling = Integer.parseInt(param[1]);
			break;
		case NBTICKS:
			this.nbTicks = Integer.parseInt(param[1]);
			break;
		case GRID:
			this.grid = Boolean.parseBoolean(param[1]);
			break;
		case TRACE:
			this.trace = Boolean.parseBoolean(param[1]);
			break;
		case SEED:
			this.seed = Integer.parseInt(param[1]);
			break;
		case REFRESH:
			this.refresh = Integer.parseInt(param[1]);
			break;
		case NBPARTICLES:
			this.nbParticles = Integer.parseInt(param[1]);
			break;
		case TORIC:
			this.toric = Boolean.parseBoolean(param[1]);
			break;
		case NBFISHS:
			this.nbFishs = Integer.parseInt(param[1]);
			break;
		case FISHBREEDTIME:
			this.fishBreedTime = Integer.parseInt(param[1]);
			break;
		case NBSHARKS:
			this.nbSharks = Integer.parseInt(param[1]);
			break;
		case SHARKBREEDTIME:
			this.sharkBreedTime = Integer.parseInt(param[1]);
			break;
		case SHARKSTARVETIME:
			this.sharkStarveTime = Integer.parseInt(param[1]);
			break;
		case WALLSPERCENTAGE:
			this.wallsPercentage = Integer.parseInt(param[1]);
			break;
		case NBHUNTERS:
			this.nbHunters = Integer.parseInt(param[1]);
			break;
		case SPEEDHUNTER:
			this.speedHunter = Integer.parseInt(param[1]);
			break;
		case SPEEDAVATAR:
			this.speedAvatar = Integer.parseInt(param[1]);
			break;
		case DEFENDERLIFE:
			this.defenderLife = Integer.parseInt(param[1]);
			break;
		default:
			break;
		}
	}
	
}

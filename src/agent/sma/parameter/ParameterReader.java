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
	private static final String NBTICKS = "nbTicks";
	private static final String REFRESH = "refresh";
	// Default values
	private int gridSizeX = 500;
	private int gridSizeY = 500;
	private int boxSize = 1;
	private int delay = 0;
	private int nbTicks = 1000;
	private int refresh = 1;

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

		return new Parameter(gridSizeX, gridSizeY, boxSize, delay, nbTicks, refresh);
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
		case NBTICKS:
			this.nbTicks = Integer.parseInt(param[1]);
			break;
		case REFRESH:
			this.refresh = Integer.parseInt(param[1]);
			break;
		default:
			break;
		}
	}

}

package agent.main;

import java.io.File;
import java.io.IOException;

import agent.generator.ImageGenerator;
import agent.processing.CodeParser;
import agent.sma.model.SMA;
import agent.sma.model.SMAParticule;
import agent.sma.parameter.Parameter;
import agent.sma.parameter.ParameterReader;

public class Main {

	public static final String FILENAME = "Example2.java";
	
	public static void main(String[] args) {
		try {
			Parameter param = new ParameterReader().getParameters(new File("res/param.txt"));
		
			SMA sma = new SMAParticule(param);
			sma.run();
			
			ImageGenerator imgGenerator = new ImageGenerator();
			imgGenerator.writeToFile(sma.getAgentlist(), param.getGridSizeX(), param.getGridSizeY());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

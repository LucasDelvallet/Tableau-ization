package main;

import java.io.File;
import java.io.IOException;

import generator.ImageGenerator;
import processing.CodeParser;
import sma.model.SMA;
import sma.model.SMAParticule;
import sma.parameter.Parameter;
import sma.parameter.ParameterReader;

public class Main {

	public static final String FILENAME = "Example.java";
	
	public static void main(String[] args) {
		try {
			Parameter param = new ParameterReader().getParameters(new File("res/param.txt"));
		
			SMA sma = new SMAParticule(param);
			sma.run();
			
			ImageGenerator imgGenerator = new ImageGenerator();
			imgGenerator.writeToFile(sma.getAgentlist(), param.getGridSizeX(), param.getGridSizeY());
			
			CodeParser parser = new CodeParser();
			parser.parseFile(FILENAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

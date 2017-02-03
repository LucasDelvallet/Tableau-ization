package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import agent.sma.core.SMA;
import agent.sma.model.SMAParticule;
import agent.sma.parameter.Parameter;
import agent.sma.parameter.ParameterReader;
import generator.ImageGenerator;

public class Main {

	public static final String FOLDERNAME = "Input";
	public static String FILENAME = "M3DS (1).txt";
	public static void main(String[] args) {
		try {
			
			List<String> fileNames = getAllFilesNameFromRessourceDirectory();

			for (String fileName : fileNames) {
				FILENAME = fileName;
				
				Parameter param = new ParameterReader().getParameters(new File("res/param.txt"));

				SMA sma = new SMAParticule(param);
				sma.run();

				ImageGenerator imgGenerator = new ImageGenerator();
				imgGenerator.writeToFile(sma.getAgentlist(), param.getGridSizeX(), param.getGridSizeY());
			}
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public static List<String> getAllFilesNameFromRessourceDirectory(){
        List<String> results = new ArrayList<String>();
        File[] files = new File(FOLDERNAME).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName().substring(0, file.getName().length()));
            }
        }
        return results;
    }


}
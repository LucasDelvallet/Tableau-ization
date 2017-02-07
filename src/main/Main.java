package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import agent.sma.core.SMA;
import agent.sma.model.SMAParticule;
import agent.sma.parameter.Parameter;
import agent.sma.parameter.ParameterReader;
import agent.sma.view.GUIHelper;
import agent.sma.view.View;
import generator.ImageGenerator;

import javax.swing.*;

public class Main {

	public static final String FOLDERNAME = "Input";
	public static final boolean activateView = true;

	public static void main(String[] args) {
		try {
			
			List<File> files = getAllFilesFromRessourceDirectory(FOLDERNAME, new ArrayList<File>());

			for (File file : files) {
				Parameter param = new ParameterReader().getParameters(new File("res/param.txt"));

				SMA sma = new SMAParticule(param, file.getCanonicalPath());

				GUIHelper guiHelper = null;
				View view = null;
				if(activateView){
					view = new View(param);
					sma.addObserver(view);
					JScrollPane scrollPane = new JScrollPane(view);
					guiHelper = new GUIHelper(scrollPane,file.getName());
				}

				sma.run();

				ImageGenerator imgGenerator = new ImageGenerator(file.getCanonicalPath());
				imgGenerator.writeToFile(sma.getAgentlist(), param.getGridSizeX(), param.getGridSizeY());

				if(activateView){
					sma.deleteObserver(view);
					guiHelper.closeFrame();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public static List<File> getAllFilesFromRessourceDirectory(String folderPath, List<File> results){

        File[] files = new File(folderPath).listFiles();

        for (File file : files) {
            if (file.isFile()) {
				results.add(file);
            } else if (file.isDirectory()){
				getAllFilesFromRessourceDirectory(file.getPath(), results);
			}
        }
        return results;
    }


}
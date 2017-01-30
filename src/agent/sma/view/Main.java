package agent.sma.view;

import java.io.File;
import java.io.IOException;

import javax.swing.JScrollPane;

import agent.sma.core.SMA;
import agent.sma.model.SMAParticule;
import agent.sma.parameter.Parameter;
import agent.sma.parameter.ParameterReader;

public class Main {
	
	public static void main(String[] args) {
		try {
			Parameter param = new ParameterReader().getParameters(new File("res/param.txt"));
			SMA sma = new SMAParticule(param);

			View view = new View(param);
			sma.addObserver(view);
			
			JScrollPane scrollPane = new JScrollPane(view);
			GUIHelper.showOnFrame(scrollPane,"Tableau-ization");
			
			sma.run();
		} catch (IOException e) {
			System.out.println("Parameters file error");
			e.printStackTrace();
		}
		
	}

}

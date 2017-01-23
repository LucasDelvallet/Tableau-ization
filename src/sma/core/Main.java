package sma.core;

import java.io.File;
import java.io.IOException;

import javax.swing.JScrollPane;

import sma.model.SMA;
import sma.model.SMAParticule;
import sma.parameter.Parameter;
import sma.parameter.ParameterReader;
import sma.view.GUIHelper;
import sma.view.View;

public class Main {
	
	public static void main(String[] args) {
		try {
			Parameter param = new ParameterReader().getParameters(new File("res/param.txt"));
			SMA sma = new SMAParticule(param);

			View view = new View(param, false);
			sma.addObserver(view);
			
			JScrollPane scrollPane = new JScrollPane(view);
			GUIHelper.showOnFrame(scrollPane,"S.M.A");
			
			sma.run();
		} catch (IOException e) {
			System.out.println("Parameters file error");
			e.printStackTrace();
		}
		
	}

}

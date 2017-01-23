package main;

import generator.ImageGenerator;
import processing.CodeParser;

public class Main {

	public static void main(String[] args) {
		ImageGenerator imgGenerator = new ImageGenerator();
		imgGenerator.writeToFile();
		
		CodeParser parser = new CodeParser();
		parser.parseFile();
	}

}

package generator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;

import agent.sma.core.Agent;
import agent.sma.model.Position;
import main.Main;

public class ImageGenerator {

	private String fileName;
	public ImageGenerator(String fileName){
		this.fileName = fileName;
	}
	
	
	public void writeToFile(List<Agent> agents, int width, int height){
	     BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

	     File f = null;
	     
	     for(Agent agent : agents){
	    	 Color c = agent.getColor();
	    	 int p = (0<<24) | (c.getRed()<<16) | (c.getGreen()<<8) | c.getBlue();
	    	 
	         p = (255<<24) | (c.getRed()<<16) | (c.getGreen()<<8) | c.getBlue();
	    	 Position position = agent.getCurrentIndex();
	    	 img.setRGB(position.getX(), position.getY(), p);
	     }
	     
	     try{
	     	String filePathOutput = fileName+".png";
			 filePathOutput = filePathOutput.replace(Main.FOLDERNAME, "Output");
	       f = new File(filePathOutput);
	       f.getParentFile().mkdirs();
	       ImageIO.write(img, "png", f);
	     }catch(IOException e){
	       System.out.println("Error: " + e);
	     }
	}
}

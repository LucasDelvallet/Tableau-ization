package agent.sma.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import agent.sma.core.Agent;
import agent.sma.core.SMA;
import agent.sma.parameter.Parameter;
import main.Main;
import processing.CodeParser;

public class SMAParticule extends SMA {

	public SMAParticule(Parameter parameters) {
		super(parameters);
	}

	@Override
	protected void initAgent(Parameter parameters){
		agentlist = new ArrayList<Agent>();
		
		List<Position> possiblePositions = new ArrayList<Position>();
		for(int i = 0; i < environment.getWidth(); i+=parameters.getBoxSize()){
			for(int j = 0; j < environment.getHeight(); j+=parameters.getBoxSize()){
				possiblePositions.add(new Position(i,j));
				Agent a = new Background(environment, parameters, new Position(i,j));
				agentlist.add(a);
				environment.agentsPosition[i/parameters.getBoxSize()][j/parameters.getBoxSize()] = a;
			}
		}
		
		CodeParser codeParser = new CodeParser();
		LinkedHashMap<String, Integer> wordsByCount = codeParser.parseFile(Main.FILENAME);
		int x = 0;
		int y = 0;
		boolean l = true;
		boolean d = false;
		boolean r = false;
		boolean u = false;
		int turn = 0;
		
		int total = 0;
		for (Entry<String, Integer> entry : wordsByCount.entrySet())
		{
			total += entry.getValue();
		}
		
		float ratioUpscale = 1;
		if(total < environment.getHeight()/2){
			ratioUpscale = (float)environment.getHeight()/2 / (float)total ;
		}
		
		for (Entry<String, Integer> entry : wordsByCount.entrySet())
		{
			int hashCode = entry.getKey().hashCode();
			String sColor = "#"+ intToARGB(hashCode);
			
		    Color color = Color.decode(sColor);
		    
		    for(int i = 0; i < (entry.getValue() * ratioUpscale); i++){
		    	agentlist.add(new Particule(environment, parameters, new Position(x, y), color, hashCode, entry.getValue() + turn));
		    	
		    	if(l){
		    		y++;
			    	if(y >= environment.getHeight() - turn){
			    		l = false;
			    		d = true;
			    		y--;
			    	}
		    	}
		    	if(d){
		    		x++;
			    	if(x >= environment.getWidth() - turn){
			    		d = false;
			    		r = true;
			    		x--;
			    	}
		    	}
		    	if(r){
		    		y--;
			    	if(y <= turn){
			    		r = false;
			    		u = true;
			    	}
		    	}
		    	if(u){
		    		x--;
			    	if(x <= turn + 1 ){
			    		u = false;
			    		l = true;
			    		turn ++;
			    	}
		    	}
		    }
		}		
	}
	
	public static String intToARGB(int i){
	    return 
	        Integer.toHexString(((i>>16)&0xFF))+
	        Integer.toHexString(((i>>8)&0xFF))+
	        Integer.toHexString((i&0xFF));
	}
}

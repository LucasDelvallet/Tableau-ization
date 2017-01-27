package agent.sma.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import agent.main.Main;
import agent.processing.CodeParser;
import agent.sma.core.Agent;
import agent.sma.parameter.Parameter;

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
		Map<String, Integer> wordsByCount = codeParser.parseFile(Main.FILENAME);
		for (Entry<String, Integer> entry : wordsByCount.entrySet())
		{
			int hashCode = entry.getKey().hashCode();
			String sColor = intToARGB(hashCode);
			
		    Color color = Color.decode("#"+sColor);
		    
		    for(int i = 0; i < entry.getValue(); i++){
				//int index = rand.nextInt(possiblePositions.size());
				agentlist.add(new Particule(environment, parameters, possiblePositions.get(i), color, hashCode, entry.getValue()));
				possiblePositions.remove(0);
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

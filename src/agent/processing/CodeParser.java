package agent.processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CodeParser {
	public CodeParser(){
		
	}
	
	public Map<String, Integer> parseFile(String fileName){
	    Map<String, Integer> countByWords = new HashMap<String, Integer>();
	    Scanner s;
		try {
			File f = new File("");
			String n = f.getAbsolutePath() + "\\Input\\"+fileName;
			
			String content = new Scanner(new File(n)).useDelimiter("\\Z").next();

		    for (String next : content.split("[ \t\\x0B\f\r]+")) {
		        Integer count = countByWords.get(next);
		        if (count != null) {
		            countByWords.put(next, count + 1);
		        } else {
		            countByWords.put(next, 1);
		        }
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return countByWords;
	}
}

package agent.processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CodeParser {

	public Boolean commentaryDetected = false;
	public CodeParser(){
		
	}
	
	public Map<String, Integer> parseFile(String fileName){
	    Map<String, Integer> countByWords = new HashMap<String, Integer>();
	    Scanner s;
		try {
			File f = new File("");
			String n = f.getAbsolutePath() + "\\Input\\"+fileName;
			
			Scanner sc = new Scanner(new File(n));
			while(sc.hasNext()){
				String content = removeCommentary(sc.next());
				if (content == null) continue;
				System.out.println(content);

				for (String next : content.split("[ \t\\x0B\f\r]+")) {
					Integer count = countByWords.get(next);
					if (count != null) {
						countByWords.put(next, count + 1);
					} else {
						countByWords.put(next, 1);
					}
				}
			}
			sc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return countByWords;
	}

	public String removeCommentary(String str){
		int  commentaryBegin = str.indexOf("/**");
		int commentaryEnd = str.indexOf("*/");
		int singleCommentary = str.indexOf("//");

		if (commentaryDetected){
			if (commentaryEnd != -1){
				commentaryDetected = false;
				str = str.substring(commentaryEnd+1, str.length()-1);
			} else {
				return null;
			}
		} else if (commentaryBegin != -1 || singleCommentary != -1) {
			str = str.substring(0, commentaryBegin);
			if (commentaryBegin != 1) commentaryDetected = true;
		}
		return str;
	}
}

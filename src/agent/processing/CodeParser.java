package agent.processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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
			while(sc.hasNextLine()){
				String content = removeCommentary(sc.nextLine());
				if (content == null) continue;
				content = content.replaceAll("[^A-Za-z0-9]", " ");
				//System.out.println(content);

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
		
		
		
		return sortByValue(countByWords);
	}
	
	public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
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

			if (commentaryBegin != -1){
				str = str.substring(0, commentaryBegin);
				commentaryDetected = true;
			} else {
				str = str.substring(0, singleCommentary);
			}
		}
		return str;
	}
}

package processing;

import java.util.HashMap;


public class Commentary {

    protected String startLong, endLong, simple;

    public Commentary(String startLong, String endLong, String simple){
        this.startLong = startLong;
        this.endLong = endLong;
        this.simple = simple;
    }

    public static HashMap<String, Commentary> hashCommentaries;
    static
    {
        hashCommentaries = new HashMap<String, Commentary>();
        hashCommentaries.put(".java", new Commentary("/**", "*/", "//"));
        hashCommentaries.put(".scala", new Commentary("/**", "*/", "//"));
        hashCommentaries.put(".cpp", new Commentary("/*", "*/", "//"));
        hashCommentaries.put(".c", new Commentary("/*", "*/", "//"));
        hashCommentaries.put(".py", new Commentary("\"\"\"", "\"\"\"", "#"));
        hashCommentaries.put(".rb", new Commentary("=begin", "=end", "#"));
    }
}

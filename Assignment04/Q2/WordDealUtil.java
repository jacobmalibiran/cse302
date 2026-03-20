package Q2;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordDealUtil {
	public  String wordFormat4DB(String name){
		Pattern p = Pattern.compile("[A-Z]");
		Matcher m = p.matcher(name);
		StringBuffer sb = new StringBuffer();
		while(m.find()){
			m.appendReplacement(sb, "_"+m.group());
		}
		return m.appendTail(sb).toString().toLowerCase();
	}
}

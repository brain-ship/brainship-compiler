package org.shield.compiler.util;

public class PatternRecog
{
	
	private static boolean checkStar(String s, String pattern) {
		if(pattern.length() != s.length() && !(pattern.contains("#") || "#".equals(pattern)) ) return false;
		boolean flag = true;
		for(int i =0; i < s.length(); i++) {
			if(pattern.charAt(i) == '*') continue;
			if(pattern.charAt(i) != s.charAt(i)) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	private static String makec(String s, String pattern) {
		int fo = pattern.indexOf("#");
		int b = s.indexOf(pattern.charAt(fo+1));
		String repl = ss(s ,fo ,b);
		String st  = "";
		for(int i = 0; i < repl.length(); i++) st+="*";
		String pp = replaceOnce(pattern, '#', st);
		String rp=pp;
		while(rp.contains("#")) rp=makec(s, pp);
		return rp;
	}
	
	public static boolean check(String st, String pattern) {
		if(!pattern.contains("#")) return checkStar(st, pattern);
		String s = makec(st, pattern);
		return checkStar(st, s);
	}
	
	private static String ss(String s, int a, int b){
		String st = "";
		for(int i = a; i < b; i++) st+=s.charAt(i);
		return st;
	}
	
	private static String replaceOnce(String st, char ch, String nch) {
		boolean flag = false;
		String repl = "";
		for(int i = 0; i < st.length(); i++)
			if(st.charAt(i) == ch && !flag){
				repl+=nch;
				flag = true;
			} else {
				repl+=st.charAt(i);
			}
		return repl;
	}
}

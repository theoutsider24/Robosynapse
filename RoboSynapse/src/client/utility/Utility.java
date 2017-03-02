package client.utility;

public class Utility {
	public static boolean isAlpha(char ch) {
		boolean found = false;
		for (char c = ' '; c <= 'z'; c++) {
			if (c == ch) {
				found = true;
			}
		}
		return found;
	}
	
	public static boolean isInCharRange(char toCompare, char low,char high) {
		boolean found = false;
		for (char c = low; c <= high; c++) {
			if (c == toCompare) {
				found = true;
			}
		}
		return found;
	}

	public static String deleteLastChar(String string) {
		if (string.length() == 0) {
			return string;
		} else {
			return string.substring(0, string.length() - 1);
		}
	}
	
	public static String toPassString(String string) {
		String retString = "";
		for(int i=0; i<string.length(); i++) {
			retString+="*";
		}
		return retString;
	}
}

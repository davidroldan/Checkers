package damas.util;

public class NumToLetter {
	
	public static final int NUM_LETTERS = 26; 
	public static String getLetter(int num){
		String a = "";
		while (num > 0){
			int aux = num % NUM_LETTERS;
			a = getLetterSingleDigit(aux) + a;
			num /= NUM_LETTERS;
		}
		return a;
	}
	
	public static String getLetterSingleDigit(int num){
		return Character.toString((char) (num + 96));
	}
}

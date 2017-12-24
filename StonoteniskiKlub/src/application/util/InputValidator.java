package application.util;

public class InputValidator {
	
	public static boolean allEntered(Object... values){
		for(int i = 0 ; i<values.length ; i++){
			if(values[i]==null || "".equals(values[i].toString())){
				return false;
			}
		}
		return true;
	}
}

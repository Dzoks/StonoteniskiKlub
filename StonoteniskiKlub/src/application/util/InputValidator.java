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
	public static boolean validateJMB(String jmb){
		if(jmb.length() == 13 && jmb.matches("\\d+")){
			return true;
		}
		return false;
	}

	public static boolean validateDouble(String input){
		if(input.matches("[+-]?\\d+(?:\\.\\d+)?")){
			return true;
		}
		return false;
	}
}

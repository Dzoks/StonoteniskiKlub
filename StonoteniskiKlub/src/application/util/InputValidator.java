package application.util;

import java.time.LocalDate;

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
	public static boolean validateTelefon(String input){
		if(input.matches("[0-9][0-9][0-9]/[0-9][0-9][0-9]-[0-9][0-9][0-9]")){
			return true;
		}
		return false;
	}
	public static boolean validateDate(LocalDate date, Integer span){
		boolean result = false;
		Integer year = LocalDate.now().getYear() - date.getYear();
		Integer days = LocalDate.now().getDayOfYear() - date.getDayOfYear();
		if(year>span || (year==span && days>=0)){
			result = true;
		}
		return result;
	}
}

package application.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Finder {
	public static <T> List<T> findAll(Collection<T> collecttion, Checker<T> checker){
		List<T> result = new ArrayList<T>();
		for(T obj : collecttion){
			if(checker.check(obj)){
				result.add(obj);
			}
		}
		return result;
	}
}

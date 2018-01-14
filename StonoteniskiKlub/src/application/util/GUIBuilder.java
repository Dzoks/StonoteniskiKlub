package application.util;

import java.util.Iterator;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class GUIBuilder{
	public static <T1, T2> void initializeTableColumns(Map<String, TableColumn<T1,T2>> map){
		Iterator<String> it = map.keySet().iterator();
		for(;it.hasNext();){
			String key = it.next();
			TableColumn<T1, T2> value = map.get(key);
			value.setCellValueFactory(new PropertyValueFactory<T1, T2>(key));
		}
	}
}

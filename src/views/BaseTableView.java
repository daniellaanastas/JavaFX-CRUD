package views;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class BaseTableView extends TableView<ObservableList<String>>{

	public BaseTableView() {
		super();
		
	}

	public BaseTableView(ObservableList<ObservableList<String>> list) {
		super(list);
		
	}
	
	

}

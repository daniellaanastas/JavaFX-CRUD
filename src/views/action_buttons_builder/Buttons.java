package views.action_buttons_builder;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Buttons {

	public final static String ADD_BUTTON = "add_button";
	public final static String SEARCH_BUTTON = "search_button";
	public final static String CLEAR_BUTTON = "clear_button";
	public final static String DELETE_BUTTON = "delete_button";
	public final static String UPDATE_BUTTON = "update_button";


	public VBox buildButtons() {
	
        Color borderColor = Color.web("#d9b24a");

		  Border border = new Border(new javafx.scene.layout.BorderStroke(
				  borderColor, 
	                BorderStrokeStyle.SOLID, 
	                CornerRadii.EMPTY, 
	                new BorderWidths(2)));
		  
		VBox buttonsVBox = new VBox();
		buttonsVBox.setSpacing(2);

		HBox searchClear = new HBox();
		searchClear.setSpacing(2);
		Button searchButton = new Button("SEARCH");
		searchButton.setBorder(border);
		searchButton.setId(Buttons.SEARCH_BUTTON);
		HBox.setHgrow(searchButton, Priority.ALWAYS);
		searchButton.setMaxWidth(Double.MAX_VALUE);
		searchButton.setStyle("-fx-background-color: #fcf7e8; ");
				

		
		Button clearButton = new Button("CLEAR");
		clearButton.setId(Buttons.CLEAR_BUTTON);
		HBox.setHgrow(clearButton, Priority.ALWAYS);
		clearButton.setMaxWidth(Double.MAX_VALUE);
		clearButton.maxWidthProperty().bind(searchButton.widthProperty());
		clearButton.setStyle("-fx-background-color: #fcf7e8; ");
		clearButton.setBorder(border);

		searchClear.getChildren().addAll(searchButton, clearButton);
		
		Button updateButton = new Button("UPDATE");
        updateButton.setId(Buttons.UPDATE_BUTTON);
		updateButton.setStyle("-fx-background-color: #fcf7e8; ");
		updateButton.setBorder(border);

        HBox.setHgrow(updateButton, Priority.ALWAYS);
        updateButton.setMaxWidth(Double.MAX_VALUE);
        
		HBox addDelete = new HBox();
		addDelete.setSpacing(2);
		
		Button addButton = new Button("INSERT");
		addButton.setId(Buttons.ADD_BUTTON);
		HBox.setHgrow(addButton, Priority.ALWAYS);
		addButton.setMaxWidth(Double.MAX_VALUE);
		addButton.setStyle("-fx-background-color: #fcf7e8; ");
		addButton.setBorder(border);

				
		Button deleteButton = new Button("DELETE");
		deleteButton.setId(Buttons.DELETE_BUTTON);
		HBox.setHgrow(deleteButton, Priority.ALWAYS);
		deleteButton.setMaxWidth(Double.MAX_VALUE);
		addButton.maxWidthProperty().bind(deleteButton.widthProperty()); 
		addDelete.getChildren().addAll(addButton, deleteButton);
		deleteButton.setStyle("-fx-background-color: #fcf7e8;  ");
		deleteButton.setBorder(border);

		
		buttonsVBox.getChildren().addAll(searchClear, addDelete, updateButton);

		return buttonsVBox;
	}
}

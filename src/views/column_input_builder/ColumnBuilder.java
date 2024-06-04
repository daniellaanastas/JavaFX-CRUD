package views.column_input_builder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.collections.transformation.FilteredList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ColumnBuilder {

    private HashMap<String, ComboBox<String>> comboBoxes;

    public ColumnBuilder() {
        this.comboBoxes = new HashMap<>();
        
    }
    
   

    public VBox buildTextField(HashMap<String, InputType> text) {
        return this.buildTextField(text, null);
    }

    public VBox buildTextField(HashMap<String, InputType> text, HashMap<String, String[]> combo) {
        VBox list = new VBox();
        list.setSpacing(10);
        list.setStyle("-fx-padding: 50px;");
        for (String name : text.keySet()) {

            Label label = new Label(name);
            Node input = null;
            System.out.println("Column: " + name);
            switch (text.get(name)) {
                case TEXT: {
                    TextField textfield = new TextField();
                    textfield.setId(name);
                    input = textfield;
                    break;
                }
                case NUMBER: {
                    TextField textfieldNumeric = new TextField();
                    textfieldNumeric.setId(name);
                    textfieldNumeric.setPromptText("Numeric Only");
                    textfieldNumeric.setTextFormatter(new TextFormatter<>(change -> {
                        if (change.isContentChange()) {
                            if (!change.getControlNewText().matches("\\d*")
                                    && !change.getControlNewText().matches("\\d*\\.\\d*")) {
                                return null; // don't change if it's not a number
                            }
                        }
                        return change;
                    }));
                    input = textfieldNumeric;
                    break;
                }
                case COMBO: {
                    ComboBox<String> comboBox = new ComboBox<>();
                    comboBox.setId(name);
                    comboBox.setPromptText(String.format("Select a %s", name));
                    comboBox.setStyle("-fx-background-color: #fcf7e8;");
                    comboBox.setPromptText(String.format("Select a %s", name));

                    String[] options = combo.get(name);
                    for (String option : options) {
                    	comboBox.getItems().add(option);
                    }
                    input = comboBox;
                    break;
                }
            }
            list.getChildren().addAll(label, input);
        }

        return list;
    }

   

}

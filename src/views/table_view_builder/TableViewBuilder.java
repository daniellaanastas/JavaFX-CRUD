package views.table_view_builder;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import models.BaseTable;

import java.util.HashMap;

public class TableViewBuilder {
    public final static String TABLE_VIEW_ID_SUFFIX = "data_table_view";

    public TableView<ObservableList<String>> buildTableView(BaseTable[] tables, BaseTable table) {

        TableView<ObservableList<String>> tableview = new TableView<>();
        tableview.setId(table.getTableName() + "_" + TABLE_VIEW_ID_SUFFIX);
        tableview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        HashMap<String, String> colMap = table.getTable();
        int index = 0;

        for (String colName : colMap.keySet()) {
            final int valueIndex = index;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(valueIndex)));
            tableview.getColumns().add(col);
            index++;
        }

        updateData(tableview, tables);

        // Color the header row
        tableview.setRowFactory(tv -> new TableRow<ObservableList<String>>() {
            @Override
            protected void updateItem(ObservableList<String> item, boolean empty) {
                super.updateItem(item, empty);
             if (getIndex() % 2 == 0) {
                    // Even row
                    setStyle("-fx-background-color: #f7e8ba;");
                } else {
                    // Odd row
                    setStyle("-fx-background-color: #fcf7e8;");
                }
            }
        });


        tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tableview;
    }

    public static void updateData(TableView<ObservableList<String>> tableview, BaseTable[] tables) {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        for (BaseTable model : tables) {
            ObservableList<String> row = FXCollections.observableArrayList();

            HashMap<String, String> rowMap = model.getTable();
            for (String columnName : tableview.getColumns().stream().map(TableColumn::getText).toArray(String[]::new)) {
                row.add(rowMap.get(columnName));
            }

            data.add(row);
        }

        tableview.getItems().setAll(data);
    }
}

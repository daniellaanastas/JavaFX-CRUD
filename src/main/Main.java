package main;

import java.sql.Connection;




import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Address;
import models.BaseTable;
import models.Car;
import models.CarPart;
import models.Customer;
import models.Device;
import models.Manufacture;
import models.Orders;
import views.action_buttons_builder.Buttons;
import views.column_input_builder.TableTab;
import views.table_view_builder.TableViewBuilder;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Connection con = SharedData.getInstance().getConnection();
		if (con == null) {
			System.out.println("Connection problem terminating");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Connection");
			alert.setHeaderText(null);
			alert.setContentText("Connection error terminating application!");

			alert.showAndWait();
			return;
		}

// 		Prepare tables
		Repository dataRepo = SharedData.getInstance().getRepo();
		Address[] addressModels = dataRepo.getAddresses(new Address());
		Car[] carModels = dataRepo.getCars(new Car());
		CarPart[] carPartModels = dataRepo.getCarParts(new CarPart());
		Customer[] customerModels = dataRepo.getCustomers(new Customer());
		Device[] deviceModels = dataRepo.getDevices(new Device());
		Manufacture[] manufactureModels = dataRepo.getManufactures(new Manufacture());
		Orders[] ordersModels = dataRepo.getOrders(new Orders());

		System.out.println(Arrays.toString(addressModels));

//		Build UI
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

		String[] tabs = { 
				Address.TABLE_NAME, 
				Car.TABLE_NAME, 
				CarPart.TABLE_NAME, 
				Customer.TABLE_NAME,
				Device.TABLE_NAME, 
				Manufacture.TABLE_NAME,
				Orders.TABLE_NAME };

		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
			if (newTab != null) {
				System.out.println("Selected Tab: " + newTab.getText());
			}
		});

// 		Build Tabs 
		for (String tabName : tabs) {
			Tab tab = new Tab(tabName);
			tabPane.getTabs().add(tab);
			tab.setStyle("-fx-max-width: 95px; -fx-min-width: 95px; -fx-pref-width: 95px; -fx-background-color: #f7e8ba;");

		}

//		Build Tab Contents
		TableTab tableTabBuilder = new TableTab();
		
		Tab addressTab = tabPane.getTabs().get(0);
		VBox addressTabContent = (VBox) tableTabBuilder.buildAddressTab();
		addressTab.setContent(addressTabContent);

		Tab carTab = tabPane.getTabs().get(1);
		VBox carTabContent = (VBox) tableTabBuilder.buildCarTab(manufactureModels);
		carTab.setContent(carTabContent);

		Tab carPartTab = tabPane.getTabs().get(2);
		VBox carPartTabContent = (VBox) tableTabBuilder.buildCarPartTab(carModels, deviceModels);
		carPartTab.setContent(carPartTabContent);

		Tab customerTab = tabPane.getTabs().get(3);
		VBox customerTabContent = (VBox) tableTabBuilder.buildCustomerTab(addressModels);
		customerTab.setContent(customerTabContent);

		Tab deviceTab = tabPane.getTabs().get(4);
		VBox deviceTabContent = (VBox) tableTabBuilder.buildDeviceTab(manufactureModels);
		deviceTab.setContent(deviceTabContent);

		Tab manufactureTab = tabPane.getTabs().get(5);
		VBox manufactureTabContent = (VBox) tableTabBuilder.buildManufactureTab();
		manufactureTab.setContent(manufactureTabContent);

		Tab ordersTab = tabPane.getTabs().get(6);
		VBox ordersTabContent = (VBox) tableTabBuilder.buildOrdersTab(carModels, customerModels);
		ordersTab.setContent(ordersTabContent);

//		Define tables keys
		String[] addressKeys = { "id", "buidling", "street", "city", "country" };
		String[] carKeys = { "model", "name", "made", "year" };
		String[] carPartKeys = { "part", "car" };
		String[] customerKeys = { "job", "id", "f_name", "l_name", "address", };
		String[] deviceKeys = { "weight", "name", "made", "price", "no" };
		String[] manufactureKeys = { "type", "name", "city", "country" };
		String[] ordersKeys = { "customer", "id", "car", "date" };

//		Build Table
		TableViewBuilder tableViewBuilder = new TableViewBuilder();
		addressTabContent.getChildren().add(tableViewBuilder.buildTableView(addressModels, new Address()));
		carTabContent.getChildren().add(tableViewBuilder.buildTableView(carModels, new Car()));
		carPartTabContent.getChildren().add(tableViewBuilder.buildTableView(carPartModels, new CarPart()));
		customerTabContent.getChildren().add(tableViewBuilder.buildTableView(customerModels, new Customer()));
		deviceTabContent.getChildren().add(tableViewBuilder.buildTableView(deviceModels, new Device()));
		manufactureTabContent.getChildren().add(tableViewBuilder.buildTableView(manufactureModels, new Manufacture()));
		ordersTabContent.getChildren().add(tableViewBuilder.buildTableView(ordersModels, new Orders()));

		this.setTableChangeListener(addressTabContent, Address.TABLE_NAME, addressKeys);
		this.setTableChangeListener(carTabContent, Car.TABLE_NAME, carKeys);
		this.setTableChangeListener(carPartTabContent, CarPart.TABLE_NAME, carPartKeys);
		this.setTableChangeListener(customerTabContent, Customer.TABLE_NAME, customerKeys);
		this.setTableChangeListener(deviceTabContent, Device.TABLE_NAME, deviceKeys);
		this.setTableChangeListener(manufactureTabContent, Manufacture.TABLE_NAME, manufactureKeys);
		this.setTableChangeListener(ordersTabContent, Orders.TABLE_NAME, ordersKeys);

//		Actions Buttons functionality setup
		this.setSearchActionHandler(addressTabContent, new Address(), addressKeys);
		this.setSearchActionHandler(carTabContent, new Car(), carKeys);
		this.setSearchActionHandler(carPartTabContent, new CarPart(), carPartKeys);
		this.setSearchActionHandler(customerTabContent, new Customer(), customerKeys);
		this.setSearchActionHandler(deviceTabContent, new Device(), deviceKeys);
		this.setSearchActionHandler(manufactureTabContent, new Manufacture(), manufactureKeys);
		this.setSearchActionHandler(ordersTabContent, new Orders(), ordersKeys);

		this.setClearActionHandler(addressTabContent, new Address(), addressKeys);                                
		this.setClearActionHandler(carTabContent, new Car(), carKeys);                                            
		this.setClearActionHandler(carPartTabContent, new CarPart(), carPartKeys);                                
		this.setClearActionHandler(customerTabContent, new Customer(), customerKeys);                             
		this.setClearActionHandler(deviceTabContent, new Device(), deviceKeys);                                   
		this.setClearActionHandler(manufactureTabContent, new Manufacture(), manufactureKeys);                    
		this.setClearActionHandler(ordersTabContent, new Orders(), ordersKeys);                                   

		this.setAddActionHandler(addressTabContent, new Address(), addressKeys);                                
		this.setAddActionHandler(carTabContent, new Car(), carKeys);                                            
		this.setAddActionHandler(carPartTabContent, new CarPart(), carPartKeys);                                
		this.setAddActionHandler(customerTabContent, new Customer(), customerKeys);                             
		this.setAddActionHandler(deviceTabContent, new Device(), deviceKeys);                                   
		this.setAddActionHandler(manufactureTabContent, new Manufacture(), manufactureKeys);                    
		this.setAddActionHandler(ordersTabContent, new Orders(), ordersKeys);                                   

		this.setDeleteActionHandler(addressTabContent, new Address(), addressKeys);                                
		this.setDeleteActionHandler(carTabContent, new Car(), carKeys);                                            
		this.setDeleteActionHandler(carPartTabContent, new CarPart(), carPartKeys);                                
		this.setDeleteActionHandler(customerTabContent, new Customer(), customerKeys);                             
		this.setDeleteActionHandler(deviceTabContent, new Device(), deviceKeys);                                   
		this.setDeleteActionHandler(manufactureTabContent, new Manufacture(), manufactureKeys);                    
		this.setDeleteActionHandler(ordersTabContent, new Orders(), ordersKeys);                                   

		this.setUpdateActionHandler(addressTabContent, new Address(), addressKeys);                                
		this.setUpdateActionHandler(carTabContent, new Car(), carKeys);                                            
		this.setUpdateActionHandler(carPartTabContent, new CarPart(), carPartKeys);                                
		this.setUpdateActionHandler(customerTabContent, new Customer(), customerKeys);                             
		this.setUpdateActionHandler(deviceTabContent, new Device(), deviceKeys);                                   
		this.setUpdateActionHandler(manufactureTabContent, new Manufacture(), manufactureKeys);                    
		this.setUpdateActionHandler(ordersTabContent, new Orders(), ordersKeys);                                   

		// Setup scene
		ScrollPane scrollPane = new ScrollPane(tabPane);
		scrollPane.setFitToWidth(true);
		Scene scene = new Scene(scrollPane, 800, 600);
		tabPane.setStyle("-fx-background-color: #f7e8ba;"); 

		primaryStage.setTitle("Cars Database");

		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> {
			Platform.exit();
			System.exit(0);
		});
		primaryStage.show();

	}

	@Override
	public void stop() {
		SharedData.getInstance().getRepo().closeConnection();
		Platform.exit();
	}

	private EventHandler<ActionEvent> handleSearchAction(Node contentNode, BaseTable table, String... keys) {
	    EventHandler<ActionEvent> eventHandler = event -> {
	        try {
	            HashMap<String, String> tableMap = new HashMap<>();
	            
	            for (String key : keys) {
	                String value = null;
	                Node input = contentNode.lookup("#" + key);

	                if (this.hasSuperChildNode(input, ComboBox.class)) {
	                    ComboBox<String> comboBox = (ComboBox<String>) input;
	                    value = comboBox.getValue();
	                } else if (this.hasSuperChildNode(input, TextField.class)) {
	                    TextField textField = (TextField) input;
	                    value = textField.getText();
	                } else {
	                    System.err.println("unknown input");
	                }

	                if (value != null && value.length() == 0) {
	                    value = null;
	                }

	                tableMap.put(key, value);
	            }

	            table.loadTable(tableMap);

	            String tableLookup = String.format("#%S_%S", table.getTableName(), TableViewBuilder.TABLE_VIEW_ID_SUFFIX)
	                    .toLowerCase();
	            TableView<ObservableList<String>> tableView = (TableView<ObservableList<String>>) contentNode.lookup(tableLookup);

	            if (tableView == null) {
	                System.err.println("TableView is not found");
	                return;
	            }

	            BaseTable[] tables = table.loadFromRepo();

	            if (tables.length == 0) {
	                Alert notFoundAlert = new Alert(Alert.AlertType.INFORMATION);
	                notFoundAlert.setTitle("Row Not Found");
	                notFoundAlert.setHeaderText(null);
	                notFoundAlert.setContentText("The row isn't found!");
	                notFoundAlert.showAndWait();
	            } else {
	            	 TableViewBuilder.updateData(tableView, tables);
	                System.out.println(table);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();

	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Search Fail");
	            alert.setHeaderText(null);
	            alert.setContentText("Error searching for row: " + e.getMessage());
	            alert.showAndWait();
	        }
	    };

	    return eventHandler;
	}


	private EventHandler<ActionEvent> handleClearAction(Node contentNode, BaseTable table, String... keys) {
	    EventHandler<ActionEvent> eventHandler = event -> {
	        HashMap<String, String> tableMap = new HashMap<>();
	        int index = 0;
	        for (String key : keys) {
	            Node input = contentNode.lookup("#" + key);
	            if (this.hasSuperChildNode(input, ComboBox.class)) {
	                ComboBox<String> comboBox = (ComboBox<String>) input;
	                comboBox.setValue(null);
	            } else if (this.hasSuperChildNode(input, TextField.class)) {
	                TextField textfield = (TextField) input;
	                textfield.setText("");
	                tableMap.put(key, "");
	            } else {
	                System.err.println("unknown input");
	            }

	            index++;
	        }

	        String tableLookup = String.format("#%S_%S", table.getTableName(), TableViewBuilder.TABLE_VIEW_ID_SUFFIX).toLowerCase();
	        TableView<ObservableList<String>> tableview = (TableView<ObservableList<String>>) contentNode.lookup(tableLookup);

	        if (tableview == null) {
	            System.err.println("TableView is not found");
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setHeaderText(null);
	            alert.setContentText("Table View is not found");

	            alert.showAndWait();
	            return;
	        }

	        tableview.getItems().clear();

	        BaseTable[] models = table.loadFromRepo();
	        TableViewBuilder.updateData(tableview, models);
	    };
	    return eventHandler;
	}


	private EventHandler<ActionEvent> handleAddAction(Node contentNode, BaseTable table, String... keys) {
	    EventHandler<ActionEvent> eventHandler = event -> {
	        try {
	        	

	            HashMap<String, String> tableMap = new HashMap<>();
	            boolean allFilled = true;

	            for (String key : keys) {
	                String value = null;
	                Node input = contentNode.lookup("#" + key);
	                if (hasSuperChildNode(input, ComboBox.class)) {
	                    ComboBox<String> comboBox = (ComboBox<String>) input; 
	                    value = comboBox.getValue();
	                } else if (hasSuperChildNode(input, TextField.class)) {
	                    TextField textfield = (TextField) input;
	                    value = textfield.getText();
	                } else {
	                    System.err.println("unknown input");
	                }

	                if ((value != null && value.length() == 0) || value == null) {
	                    System.out.println("Please fill all fields");
	                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                    alert.setTitle("Add");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Please fill all fields!");

	                    alert.showAndWait();
	                    return;
	                }

	                tableMap.put(key, value);
	            }

	            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
	            confirmationAlert.setTitle("Confirm Add");
	            confirmationAlert.setHeaderText(null);
	            confirmationAlert.setContentText("Are you sure you want to add this row?");

	            Optional<ButtonType> result = confirmationAlert.showAndWait();

	            if (result.isPresent() && result.get() == ButtonType.OK) {
	                table.loadTable(tableMap);

	                try {
	                    SharedData.getInstance().getRepo().insertInto(table.getTableName(), table);

	                    String tableLookup = String.format("#%S_%S", table.getTableName(), TableViewBuilder.TABLE_VIEW_ID_SUFFIX)
	                            .toLowerCase();
	                    TableView<ObservableList<String>> tableview = (TableView<ObservableList<String>>) contentNode
	                            .lookup(tableLookup);

	                    if (tableview == null) {
	                        System.err.println("TableView is not found");
	                        return;
	                    }

	                    BaseTable[] models = { table };
	                    TableViewBuilder.updateData(tableview, models);
	                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
	                    successAlert.setTitle("Add Successful");
	                    successAlert.setHeaderText(null);
	                    successAlert.setContentText("Row added successfully!");
	                    successAlert.showAndWait();

	                    System.out.println(table);

	                } catch (PrimaryKeyViolationException e) {
	                    // Handle primary key violation exception
	                    Alert alert = new Alert(Alert.AlertType.ERROR);
	                    alert.setTitle("Add Fail");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Primary key violation: " + e.getMessage());
	                    alert.showAndWait();
	                } catch (Exception e) {
	                    // Handle other exceptions
	                    e.printStackTrace();
	                    Alert alert = new Alert(Alert.AlertType.ERROR);
	                    alert.setTitle("Add Fail");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Error adding row: " + e.getMessage());
	                    alert.showAndWait();
	                }
	            } else {
	                System.out.println("Add canceled");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setHeaderText(null);
	            alert.setContentText("Error adding row: " + e.getMessage());
	            alert.showAndWait();
	        }
	    };

	    return eventHandler;
	}


	private EventHandler<ActionEvent> handleDeleteAction(Node contentNode, BaseTable table, String... keys) {
	    EventHandler<ActionEvent> eventHandler = event -> {
	        try {
	            HashMap<String, String> tableMap = new HashMap<>();
	            boolean anyField = false;

	            for (String key : keys) {
	                String value = null;
	                Node input = contentNode.lookup("#" + key);
	                if (hasSuperChildNode(input, ComboBox.class)) {
	                    ComboBox<String> comboBox = (ComboBox<String>) input;
	                    value = comboBox.getValue();
	                } else if (hasSuperChildNode(input, TextField.class)) {
	                    TextField textfield = (TextField) input;
	                    value = textfield.getText();
	                } else {
	                    System.err.println("unknown input");
	                }

	                if (value != null && value.length() == 0) {
	                    value = null;
	                } else {
	                    anyField = true;
	                }

	                tableMap.put(key, value);
	            }

	            if (!anyField) {
	                System.out.println("Please fill any field");
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Delete");
	                alert.setHeaderText(null);
	                alert.setContentText("Please fill any field!");
	                alert.showAndWait();
	                return;
	            }

	            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
	            confirmationAlert.setTitle("Confirm Delete");
	            confirmationAlert.setHeaderText(null);
	            confirmationAlert.setContentText("Are you sure you want to delete this row?");

	            Optional<ButtonType> result = confirmationAlert.showAndWait();

	            if (result.isPresent() && result.get() == ButtonType.OK) {

	                table.loadTable(tableMap);
	                
	                String[] primaryKey = table.getPrimaryKey();
	                for (String key : primaryKey) {
	                    String value = table.getTable().get(key);
	                    if (value == null) {
	                        Alert alert = new Alert(Alert.AlertType.ERROR);
	                        alert.setTitle("Update Fail");
	                        alert.setHeaderText(null);
	                        alert.setContentText("Please fill primary key fields");
	                        alert.showAndWait();
	                        return;
	                    }
	                }

	                // Delete the row from the data repository
	                SharedData.getInstance().getRepo().deleteFrom(table.getTableName(), table);

	                tableMap.clear();
	                for (String key : keys) {
	                    tableMap.put(key, null);
	                }

	                table.loadTable(tableMap);

	                String tableLookup = String.format("#%S_%S", table.getTableName(), TableViewBuilder.TABLE_VIEW_ID_SUFFIX)
	                        .toLowerCase();
	                TableView<ObservableList<String>> tableview = (TableView<ObservableList<String>>) contentNode
	                        .lookup(tableLookup);

	                BaseTable[] models = table.loadFromRepo();

	                TableViewBuilder.updateData(tableview, models);

	                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
	                successAlert.setTitle("Delete Successful");
	                successAlert.setHeaderText(null);
	                successAlert.setContentText("Row deletion is successful!");
	                successAlert.showAndWait();
	            } else {
	                System.out.println("Delete canceled");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Delete Fail");
	            alert.setHeaderText(null);
	            alert.setContentText("Error deleting row: " + e.getMessage());
	            alert.showAndWait();
	        }
	    };

	    return eventHandler;
	}


	
	private EventHandler<ActionEvent> handleUpdateAction(Node contentNode, BaseTable table, String... keys) {
	    EventHandler<ActionEvent> eventHandler = event -> {
	        try {
	            String tableName = table.getTableName();
	            HashMap<String, String> tableMap = new HashMap<>();

	            for (String key : keys) {
	                String value = null;
	                Node input = contentNode.lookup("#" + key);
	                if (hasSuperChildNode(input, ComboBox.class)) {
	                    ComboBox<String> comboBox = (ComboBox<String>) input;
	                    value = comboBox.getValue();
	                } else if (hasSuperChildNode(input, TextField.class)) {
	                    TextField textfield = (TextField) input;
	                    value = textfield.getText();
	                } else {
	                    System.err.println("unknown input");
	                }
	                if (value != null && value.length() == 0) {
	                    value = null;
	                }
	                tableMap.put(key, value);
	            }

	            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
	            confirmationAlert.setTitle("Confirm Update");
	            confirmationAlert.setHeaderText(null);
	            confirmationAlert.setContentText("Are you sure you want to update this row?");

	            Optional<ButtonType> result = confirmationAlert.showAndWait();

	            if (result.isPresent() && result.get() == ButtonType.OK) {
	                table.loadTable(tableMap);

	                String[] primaryKey = table.getPrimaryKey();
	                for (String key : primaryKey) {
	                    String value = table.getTable().get(key);
	                    if (value == null) {
	                        Alert alert = new Alert(Alert.AlertType.ERROR);
	                        alert.setTitle("Update Fail");
	                        alert.setHeaderText(null);
	                        alert.setContentText("Please fill primary key fields");
	                        alert.showAndWait();
	                        return;
	                    }
	                }

	                try {
	                    SharedData.getInstance().getRepo().update(tableName, table);

	                    // Display a success message
	                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
	                    successAlert.setTitle("Update Successful");
	                    successAlert.setHeaderText(null);
	                    successAlert.setContentText("Update is successful!");
	                    successAlert.showAndWait();

	                    // Clear input values
	                    for (String key : keys) {
	                        tableMap.put(key, null);
	                    }

	                    // Reload the model from the data repository
	                    String tableLookup = String.format("#%S_%S", table.getTableName(), TableViewBuilder.TABLE_VIEW_ID_SUFFIX).toLowerCase();
	                    TableView<ObservableList<String>> tableview = (TableView<ObservableList<String>>) contentNode.lookup(tableLookup);
	                    BaseTable[] models = table.loadFromRepo();

	                    TableViewBuilder.updateData(tableview, models);
	                } catch (PrimaryKeyViolationException e) {
	                    // Handle primary key violation exception
	                    Alert alert = new Alert(Alert.AlertType.ERROR);
	                    alert.setTitle("Update Fail");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Primary key violation: " + e.getMessage());
	                    alert.showAndWait();
	                } catch (Exception e) {
	                    // Handle other exceptions
	                    e.printStackTrace();
	                    Alert alert = new Alert(Alert.AlertType.ERROR);
	                    alert.setTitle("Update Fail");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Error updating row: " + e.getMessage());
	                    alert.showAndWait();
	                }
	            } else {
	                System.out.println("Update canceled");
	            }
	           } catch (Exception e) {
	            // Handle other exceptions
	            e.printStackTrace();
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Update Fail");
	            alert.setHeaderText(null);
	            alert.setContentText("Error updating row: " + e.getMessage());
	            alert.showAndWait();
	        }
	    };

	    return eventHandler;
	}





	private ChangeListener<ObservableList<String>> handleRowSelected(Node contentNode, String... keys) {
		ChangeListener<ObservableList<String>> changeListener = (obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				int index = 0;
				for (String key : keys) {

					Node inputNode = contentNode.lookup("#" + key);
					if (this.hasSuperChildNode(inputNode, ComboBox.class)) {
						ComboBox<String> comboBox = (ComboBox<String>) inputNode;
						comboBox.setValue(newSelection.get(index));
					} else if (this.hasSuperChildNode(inputNode, TextField.class)) {
						TextField textfield = (TextField) inputNode;
						textfield.setText(newSelection.get(index));
					} else {
						System.err.println("unknown input");
					}

					index++;
				}
			}
		};
		return changeListener;
	}

	private boolean hasSuperChildNode(Node node, Class<?> cls) {
		Class<?> currentClass = node.getClass();

		while (currentClass != null) {
			if (cls.isAssignableFrom(currentClass)) {
				return true;
			}
			currentClass = currentClass.getSuperclass();
		}

		return false;
	}

	private void setSearchActionHandler(Node contentNode, BaseTable table, String... keys) {
		VBox actionButtonsVBox = (VBox) contentNode.lookup("#" + TableTab.BUTTONS);
		Button searchButton = (Button) actionButtonsVBox.lookup("#" + Buttons.SEARCH_BUTTON);
		searchButton.setOnAction(this.handleSearchAction(contentNode, table, keys));
	}

	private void setClearActionHandler(Node contentNode, BaseTable table, String... keys) {
		VBox actionButtonsVBox = (VBox) contentNode.lookup("#" + TableTab.BUTTONS);
		Button clearButton = (Button) actionButtonsVBox.lookup("#" + Buttons.CLEAR_BUTTON);
		clearButton.setOnAction(this.handleClearAction(contentNode, table, keys));
	}

	private void setAddActionHandler(Node contentNode, BaseTable table, String... keys) {
		VBox actionButtonsVBox = (VBox) contentNode.lookup("#" + TableTab.BUTTONS);
		Button addButton = (Button) actionButtonsVBox.lookup("#" + Buttons.ADD_BUTTON);
		addButton.setOnAction(this.handleAddAction(contentNode, table, keys));
	}

	private void setDeleteActionHandler(Node contentNode, BaseTable table, String... keys){
		VBox actionButtonsVBox = (VBox) contentNode.lookup("#" + TableTab.BUTTONS);
		Button deleteButton = (Button) actionButtonsVBox.lookup("#" + Buttons.DELETE_BUTTON);
		deleteButton.setOnAction(this.handleDeleteAction(contentNode, table, keys));
	}

	private void setUpdateActionHandler(Node contentNode, BaseTable table, String... keys){
		VBox actionButtonsVBox = (VBox) contentNode.lookup("#" + TableTab.BUTTONS);
		Button updateButton = (Button) actionButtonsVBox.lookup("#" + Buttons.UPDATE_BUTTON);
		updateButton.setOnAction(this.handleUpdateAction(contentNode, table, keys));
	}

	private void setTableChangeListener(Node contentNode, String tableName, String... keys) {

		TableView<ObservableList<String>> tableView = (TableView<ObservableList<String>>) contentNode
				.lookup("#" + tableName + "_" + TableViewBuilder.TABLE_VIEW_ID_SUFFIX);
		tableView.getSelectionModel().selectedItemProperty()
				.addListener(this.handleRowSelected(contentNode, reverseArray(keys)));
	}

	public static String[] reverseArray(String[] array) {
		String[] newArray = array.clone();

		int start = 0;
		int end = newArray.length - 1;

		while (start < end) {

			String temp = newArray[start];
			newArray[start] = newArray[end];
			newArray[end] = temp;

			start++;
			end--;
		}
		return newArray;
	}

	public static void main(String[] args) {

		Repository repo = new Repository();
		repo.openConnection();
		SharedData.getInstance().setRepo(repo);

		launch(args);
	}

}
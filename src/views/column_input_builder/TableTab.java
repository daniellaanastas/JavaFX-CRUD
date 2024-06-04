package views.column_input_builder;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.SharedData;
import models.Address;
import models.Car;
import models.Customer;
import models.Device;
import models.Manufacture;
import views.action_buttons_builder.Buttons;

public class TableTab {
	public final static String BUTTONS = "buttons_VBox";
	public final static double MAX_CONTENT_WIDTH = 300.0;
	
	private ColumnBuilder column;

    private ObservableList<Integer> addressOptionsList; // Add this variable

	public TableTab() {
		column = new ColumnBuilder();
        addressOptionsList = FXCollections.observableArrayList();

	}
	
	public Node buildAddressTab() {
		HashMap<String, InputType> input = new HashMap<>();
		input.put("id", InputType.NUMBER);
		input.put("buidling", InputType.NUMBER);
		input.put("street", InputType.TEXT);
		input.put("city", InputType.TEXT);
		input.put("country", InputType.TEXT);

		VBox inputText = column.buildTextField(input);
		inputText.setMaxWidth(MAX_CONTENT_WIDTH);

		VBox contentVBox = new VBox();

		VBox buttonsVBox = new Buttons().buildButtons();
		buttonsVBox.setId(TableTab.BUTTONS);
		StackPane stackPane = new StackPane(inputText);
		stackPane.setAlignment(Pos.CENTER);
		inputText.getChildren().add(buttonsVBox);
		contentVBox.getChildren().add(stackPane);

		return contentVBox;
	}

	public Node buildCarTab(Manufacture[] manufactures) {
		HashMap<String, String[]> manuOptions = new HashMap<>();

		ArrayList<String> manuOptionsArray = new ArrayList<>();
		for (Manufacture manufacture : manufactures) {
			manuOptionsArray.add(manufacture.getName());
		}

		String[] options = new String[manuOptionsArray.size()];
		options = manuOptionsArray.toArray(options);

		manuOptions.put("made", options);

		HashMap<String, InputType> input = new HashMap<>();
		input.put("name", InputType.TEXT);
		input.put("model", InputType.TEXT);
		input.put("year", InputType.NUMBER);
		input.put("made", InputType.COMBO);

		VBox inputText = column.buildTextField(input, manuOptions);
		inputText.setMaxWidth(MAX_CONTENT_WIDTH);

		VBox contentVBox = new VBox();

		VBox buttonsVBox = new Buttons().buildButtons();
		buttonsVBox.setId(TableTab.BUTTONS);
		StackPane stackPane = new StackPane(inputText);
		stackPane.setAlignment(Pos.CENTER);
		inputText.getChildren().add(buttonsVBox);
		contentVBox.getChildren().add(stackPane);

		return contentVBox;
	}

	
	public Node buildCarPartTab(Car[] cars, Device[] devices) {
		HashMap<String, String[]> cpOptions = new HashMap<>();

		ArrayList<String> carOptionsArray = new ArrayList<>();
		for (Car car : cars) {
			carOptionsArray.add(car.getName());
		}

		String[] carOptions = new String[carOptionsArray.size()];
		carOptions = carOptionsArray.toArray(carOptions);

		ArrayList<String> deviceOptionsArray = new ArrayList<>();
		for (Device device : devices) {
			deviceOptionsArray.add(String.valueOf(device.getNo()));
		}

		String[] deviceOptions = new String[deviceOptionsArray.size()];
		deviceOptions = deviceOptionsArray.toArray(deviceOptions);

		cpOptions.put("part", deviceOptions);
		cpOptions.put("car", carOptions);

		HashMap<String, InputType> input = new HashMap<>();
		input.put("car", InputType.COMBO);
		input.put("part", InputType.COMBO);

		VBox inputTextVBox = column.buildTextField(input, cpOptions);
		inputTextVBox.setMaxWidth(MAX_CONTENT_WIDTH);

		VBox contentVBox = new VBox();

		VBox buttonsVBox = new Buttons().buildButtons();
		buttonsVBox.setId(TableTab.BUTTONS);
		StackPane stackPane = new StackPane(inputTextVBox);
		stackPane.setAlignment(Pos.CENTER);
		inputTextVBox.getChildren().add(buttonsVBox);
		contentVBox.getChildren().add(stackPane);

		return contentVBox;
	}

	
	public Node buildCustomerTab(Address[] addresses) {
		HashMap<String, String[]> addressOptions = new HashMap<>();

		ArrayList<String> addOptionsArray = new ArrayList<>();
		for (Address address : addresses) {
			addOptionsArray.add(String.valueOf(address.getId()));
	     }

		String[] addressArray = new String[addressOptions.size()];
		addressArray = addOptionsArray.toArray(addressArray);

		addressOptions.put("address", addressArray);

		HashMap<String, InputType> input = new HashMap<>();
		input.put("id", InputType.NUMBER);
		input.put("f_name", InputType.TEXT);
		input.put("l_name", InputType.TEXT);
		input.put("address", InputType.COMBO);
		input.put("job", InputType.TEXT);

		VBox inputTextVBox = column.buildTextField(input, addressOptions);
		inputTextVBox.setMaxWidth(MAX_CONTENT_WIDTH);

		VBox contentVBox = new VBox();

		VBox actionButtonsVBox = new Buttons().buildButtons();
		actionButtonsVBox.setId(TableTab.BUTTONS);
		StackPane stackPane = new StackPane(inputTextVBox);
		stackPane.setAlignment(Pos.CENTER);
		inputTextVBox.getChildren().add(actionButtonsVBox);
		contentVBox.getChildren().add(stackPane);

		return contentVBox;
	}
	
		  
	public Node buildDeviceTab(Manufacture[] manufactures) {
		HashMap<String, String[]> manuOptions = new HashMap<>();

		ArrayList<String> manufactureOptionsArray = new ArrayList<>();
		for (Manufacture manufacture : manufactures) {
			manufactureOptionsArray.add(manufacture.getName());
		}

		String[] manuArray = new String[manufactureOptionsArray.size()];
		manuArray = manufactureOptionsArray.toArray(manuArray);

		manuOptions.put("made", manuArray);

		HashMap<String, InputType> input = new HashMap<>();
		input.put("no", InputType.NUMBER);
		input.put("name", InputType.TEXT);
		input.put("price", InputType.NUMBER);
		input.put("weight", InputType.NUMBER);
		input.put("made", InputType.COMBO);

		VBox inputTextVBox = column.buildTextField(input, manuOptions);
		inputTextVBox.setMaxWidth(MAX_CONTENT_WIDTH);

		VBox contentVBox = new VBox();

		VBox actionButtonsVBox = new Buttons().buildButtons();
		actionButtonsVBox.setId(TableTab.BUTTONS);
		StackPane stackPane = new StackPane(inputTextVBox);
		stackPane.setAlignment(Pos.CENTER);
		inputTextVBox.getChildren().add(actionButtonsVBox);
		contentVBox.getChildren().add(stackPane);

		return contentVBox;
	}

	public Node buildManufactureTab() {
		HashMap<String, InputType> input = new HashMap<>();
		input.put("name", InputType.TEXT);
		input.put("type", InputType.TEXT);
		input.put("city", InputType.TEXT);
		input.put("country", InputType.TEXT);

		VBox inputTextVBox = column.buildTextField(input);
		inputTextVBox.setMaxWidth(MAX_CONTENT_WIDTH);

		VBox contentVBox = new VBox();

		VBox actionButtonsVBox = new Buttons().buildButtons();
		actionButtonsVBox.setId(TableTab.BUTTONS);
		StackPane stackPane = new StackPane(inputTextVBox);
		stackPane.setAlignment(Pos.CENTER);
		inputTextVBox.getChildren().add(actionButtonsVBox);
		contentVBox.getChildren().add(stackPane);

		return contentVBox;
	}

	public Node buildOrdersTab(Car[] cars, Customer[] customers) {
		HashMap<String, String[]> ordersOptions = new HashMap<>();

		ArrayList<String> carOptionsArray = new ArrayList<>();
		for (Car car : cars) {
			carOptionsArray.add(car.getName());
		}

		String[] carsArray = new String[carOptionsArray.size()];
		carsArray = carOptionsArray.toArray(carsArray);

		ArrayList<String> customerOptionsArray = new ArrayList<>();
		for (Customer customer : customers) {
			customerOptionsArray.add(String.valueOf(customer.getId()));
		}

		String[] customerArray = new String[customerOptionsArray.size()];
		customerArray = customerOptionsArray.toArray(customerArray);

		ordersOptions.put("customer", customerArray);
		ordersOptions.put("car", carsArray);

		HashMap<String, InputType> input = new HashMap<>();
		input.put("id", InputType.NUMBER);
		input.put("date", InputType.NUMBER);
		input.put("customer", InputType.COMBO);
		input.put("car", InputType.COMBO);

		VBox inputTextVBox = column.buildTextField(input, ordersOptions);
		inputTextVBox.setMaxWidth(MAX_CONTENT_WIDTH);

		VBox contentVBox = new VBox();

		VBox actionButtonsVBox = new Buttons().buildButtons();
		actionButtonsVBox.setId(TableTab.BUTTONS);
		StackPane stackPane = new StackPane(inputTextVBox);
		stackPane.setAlignment(Pos.CENTER);
		inputTextVBox.getChildren().add(actionButtonsVBox);
		contentVBox.getChildren().add(stackPane);

		return contentVBox;
	}

}

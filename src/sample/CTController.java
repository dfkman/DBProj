package sample;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData; 
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.layout.GridPane;
import javafx.geometry.*;
import javafx.application.*;
import table.Customer;
import table.SQLMiddleMan;

/**
 * Controler for customer
 */
public class CTController {

	@FXML 
	private Button ba;
	
	@FXML
	private Button add;

	@FXML
	private Button Edit;

	@FXML
	private Button Del;

	@FXML
	private TableView TabView;

	@FXML
	private TableColumn idCol;

	@FXML
	private TableColumn nameCol;

	@FXML
	private TableColumn phoneCol;

	private Main model;

	private SQLMiddleMan mm;

	private final Alert idExistsAlert = new Alert(Alert.AlertType.ERROR,
			"A customer with this ID already exists", ButtonType.OK);

	private final Alert nameTooLongAlert = new Alert(Alert.AlertType.ERROR,
			"You put in too long of a name", ButtonType.OK);

	private final Alert phoneTooLongAlert = new Alert(Alert.AlertType.ERROR,
			"You entered to long of a phone number. 10 max.", ButtonType.OK);

	private final Alert breakAlert = new Alert(Alert.AlertType.ERROR,
			"You found an unconsidered error. Congratulations",
			ButtonType.OK);

	public CTController(Main mod, SQLMiddleMan mm) {
		model = mod;
		this.mm = mm;
	}
	
	@FXML
	private void initialize() throws IOException {
		idCol.setCellValueFactory(new PropertyValueFactory<Customer, String>
				("ID"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>
				("name"));
		phoneCol.setCellValueFactory(new PropertyValueFactory<Customer, String>
				("pnumber"));
		ObservableList<Customer> data = FXCollections.observableArrayList();
		mm.loadCustData(data);
		TabView.setItems(data);

		ba.setOnAction(event -> {
			try {
				model.swapScene('m');
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		add.setOnAction(event -> {
			Dialog<ArrayList<String>> adddiag = new Dialog<>();
			adddiag.setTitle("Add/Edit an Customer...");	
			adddiag.setHeaderText("Add or Edit a Customer's data");
			ButtonType savButtonType = new ButtonType("Save", ButtonData
					.OK_DONE);
			adddiag.getDialogPane().getButtonTypes().addAll(savButtonType,
					ButtonType.CANCEL);
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			TextField name = new TextField();
			name.setPromptText("name");
			TextField phone = new TextField();
			phone.setPromptText("phone");
			grid.add(new Label("Name:"), 0, 1);
			grid.add(name, 1, 1);
			grid.add(new Label("Phone:"), 0, 2);
			grid.add(phone, 1, 2);
			adddiag.getDialogPane().setContent(grid);
			Platform.runLater(() -> name.requestFocus());
			adddiag.setResultConverter(dialogButton -> {
				if (dialogButton == savButtonType){
					ArrayList<String> Result = new ArrayList<>();
					Result.add(name.getText());
					Result.add(phone.getText());
					String id = mm.addCustomer(name.getText(), phone.getText());
					if (id == null) {
						idExistsAlert.showAndWait();
						return null;
					}
					Result.add(id);
					return Result;
				}
				return null;
			});
			Optional<ArrayList<String>> newEntry = adddiag.showAndWait();
			if (newEntry != null) {
				data.add(new Customer(newEntry.get().get(2), newEntry.get()
						.get(0), newEntry.get().get(1)));
			}
		});

		Edit.setOnAction(event -> {
			Customer cust = (Customer) TabView.getSelectionModel().getSelectedItem();
			if (cust != null) {
				Dialog<ArrayList<String>> adddiag = new Dialog<>();
				adddiag.setTitle("Add/Edit an Customer...");
				adddiag.setHeaderText("Add or Edit a Customer's data");
				ButtonType savButtonType = new ButtonType("Save", ButtonData
						.OK_DONE);
				adddiag.getDialogPane().getButtonTypes().addAll(savButtonType,
						ButtonType.CANCEL);
				GridPane grid = new GridPane();
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 150, 10, 10));
				TextField id = new TextField();
				id.setText(cust.getID());
				TextField name = new TextField();
				name.setText(cust.getName());
				TextField phone = new TextField();
				phone.setText(cust.getPnumber());
				grid.add(new Label("ID:"), 0, 0);
				grid.add(id, 1, 0);
				grid.add(new Label("Name:"), 0, 1);
				grid.add(name, 1, 1);
				grid.add(new Label("Phone:"), 0, 2);
				grid.add(phone, 1, 2);
				adddiag.getDialogPane().setContent(grid);
				Platform.runLater(() -> name.requestFocus());
				adddiag.setResultConverter(dialogButton -> {
					if (dialogButton == savButtonType) {
						Customer oldCust = new Customer(cust.getID(), cust.getName(),
								cust.getPnumber());
						ArrayList<String> Result = new ArrayList<>();
						Result.add(id.getText());
						Result.add(name.getText());
						Result.add(phone.getText());
						cust.setID(id.getText());
						cust.setName(name.getText());
						cust.setPnumber(phone.getText());
						int ret = mm.updateCustomer(cust, oldCust);
						if (ret != 0) {
							switch (ret) {
								case 1:
									phoneTooLongAlert.showAndWait();
									break;
								case 2:
									nameTooLongAlert.showAndWait();
									break;
								case 3:
									idExistsAlert.showAndWait();
									break;
								default:
									breakAlert.showAndWait();
									break;
							}
							cust.setID(oldCust.getID());
							cust.setName(oldCust.getName());
							cust.setPnumber(oldCust.getPnumber());
							return null;
						}
						TabView.refresh();
						return Result;
					}
					return null;
				});
				Optional<ArrayList<String>> newEntry = adddiag.showAndWait();
			}
		});
		Del.setOnAction(event -> {
			Customer cust = (Customer)TabView.getSelectionModel()
					.getSelectedItem();
			if (cust != null) {
				mm.deleteCustomer(cust);
				data.remove(cust);
			}
		});
	}
}

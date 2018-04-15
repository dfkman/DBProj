package sample;

import table.Employee;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.StringConverter;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.control.cell.*;
import javafx.scene.control.ButtonBar.ButtonData; 
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.layout.GridPane;
import javafx.geometry.*;
import javafx.application.*;
import table.SQLMiddleMan;

/**
 * Controler for the Employee table
 */
public class TEController {
	@FXML 
	private Button Ba;
	
	@FXML
	private Button Add;
	
	@FXML
	private Button Edit;
	
	@FXML
	private Button Del;
	
	@FXML
	private TableView TabView;
	
	@FXML
	private TableColumn namCol;
	
	@FXML
	private TableColumn ponCol;

	@FXML
	private TableColumn ssnCol;

	private Main model;

	private SQLMiddleMan mm;

	private final Alert ssnExistsAlert = new Alert(Alert.AlertType.ERROR,
			"A customer with this SSN already exists", ButtonType.OK);

	private final Alert nameTooLongAlert = new Alert(Alert.AlertType.ERROR,
			"You put in too long of a name", ButtonType.OK);

	private final Alert phoneTooLongAlert = new Alert(Alert.AlertType.ERROR,
			"You entered to long of a phone number. 10 max.", ButtonType.OK);

	private final Alert breakAlert = new Alert(Alert.AlertType.ERROR,
			"You found an unconsidered error. Congratulations",
			ButtonType.OK);
	
	
	public TEController(Main mod, SQLMiddleMan mm) {
		model = mod;
		this.mm = mm;
	}
	
	@FXML
	private void initialize() throws IOException {
		ssnCol.setCellValueFactory(new PropertyValueFactory<Employee,String>
				("SSN"));
		namCol.setCellValueFactory(new PropertyValueFactory<Employee,String>
				("name"));
		ponCol.setCellValueFactory(new PropertyValueFactory<Employee,String>
				("pnumber"));
		ObservableList<Employee> data = FXCollections
				.observableArrayList();
		mm.loadEmpData(data);
		TabView.setItems(data);
		Ba.setOnAction(event -> {
			try {
				model.swapScene('m');
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Add.setOnAction(event -> {
			Dialog<ArrayList<String>> adddiag = new Dialog<>();
			adddiag.setTitle("Add/Edit an Employee...");	
			adddiag.setHeaderText("Add or Edit an Employee's data");
			ButtonType savButtonType = new ButtonType("Save", ButtonData
					.OK_DONE);
			adddiag.getDialogPane().getButtonTypes().addAll(savButtonType,
					ButtonType.CANCEL);
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			TextField SSN = new TextField();
			SSN.setPromptText("SSN");
			TextField name = new TextField();
			name.setPromptText("name");
			TextField phone = new TextField();
			phone.setPromptText("phone");
			grid.add(new Label("SSN:"), 0, 0);
			grid.add(SSN, 1, 0);
			grid.add(new Label("Name:"), 0, 1);
			grid.add(name, 1, 1);
			grid.add(new Label("Phone:"), 0, 2);
			grid.add(phone, 1, 2);
			adddiag.getDialogPane().setContent(grid);
			Platform.runLater(() -> name.requestFocus());
			adddiag.setResultConverter(dialogButton -> {
				if (dialogButton == savButtonType) {
					if (name.getText().trim().isEmpty() || phone.getText()
							.trim().isEmpty() || SSN.getText().trim().isEmpty()) {
						CTController.emptyInputAlert.showAndWait();
						return null;
					}
					ArrayList<String> Result = new ArrayList<>();
					Result.add(SSN.getText());
					Result.add(name.getText());
					Result.add(phone.getText());
					int ret = mm.addEmployee(new Employee(SSN.getText(), name
							.getText(), phone.getText()));
					switch (ret) {
						case 0:
							return Result;
						case 1:
							phoneTooLongAlert.showAndWait();
							break;
						case 2:
							nameTooLongAlert.showAndWait();
							break;
						case 3:
							ssnExistsAlert.showAndWait();
							break;
						default:
							breakAlert.showAndWait();
							break;
					}
				}
				return null;
			});
			Optional<ArrayList<String>> newEntry = adddiag.showAndWait();
			if (newEntry.isPresent()) {
				data.add(new Employee(newEntry.get().get(0), newEntry.get().get
						(1),newEntry.get().get(2)));
			}
		});
		Edit.setOnAction(event -> {
			Employee emp = (Employee) TabView.getSelectionModel().getSelectedItem();
			if (emp != null){
				Dialog<ArrayList<String>> adddiag = new Dialog<>();
				adddiag.setTitle("Add/Edit an Employee...");	
				adddiag.setHeaderText("Add or Edit an Employee's data");
				ButtonType savButtonType = new ButtonType("Save", ButtonData
						.OK_DONE);
				adddiag.getDialogPane().getButtonTypes().addAll(savButtonType,
						ButtonType.CANCEL);
				GridPane grid = new GridPane();
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 150, 10, 10));
				TextField SSN = new TextField();
				SSN.setText(emp.getSSN());
				TextField name = new TextField();
				name.setText(emp.getName());
				TextField phone = new TextField();
				phone.setText(emp.getPnumber());
				grid.add(new Label("SSN:"), 0, 0);
				grid.add(SSN, 1, 0);
				grid.add(new Label("Name:"), 0, 1);
				grid.add(name, 1, 1);
				grid.add(new Label("Phone:"), 0, 2);
				grid.add(phone, 1, 2);
				adddiag.getDialogPane().setContent(grid);
				Platform.runLater(() -> name.requestFocus());
				adddiag.setResultConverter(dialogButton -> {
					if (dialogButton == savButtonType) {
						if (name.getText().trim().isEmpty() || phone.getText()
								.trim().isEmpty() || SSN.getText().trim().isEmpty()) {
							CTController.emptyInputAlert.showAndWait();
							return null;
						}
						Employee oldEmp = new Employee(emp.getSSN(), emp
								.getName(), emp.getPnumber());
						ArrayList<String> Result = new ArrayList<>();
						Result.add(SSN.getText());
						Result.add(name.getText());
						Result.add(phone.getText());
						emp.setSSN(SSN.getText());
						emp.setName(name.getText());
						emp.setPhone(phone.getText());
						int ret = mm.updateEmployee(emp, oldEmp);
						if (ret != 0) {
							switch (ret) {
								case 1:
									phoneTooLongAlert.showAndWait();
									break;
								case 2:
									nameTooLongAlert.showAndWait();
									break;
								case 3:
									ssnExistsAlert.showAndWait();
									break;
								default:
									breakAlert.showAndWait();
									break;
							}
							emp.setSSN(oldEmp.getSSN());
							emp.setName(oldEmp.getName());
							emp.setPhone(oldEmp.getPnumber());
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
		Del.setOnAction(event ->{
			Employee emp = (Employee) TabView.getSelectionModel().getSelectedItem();
			if (emp != null){
				mm.deleteEmployee(emp);
				data.remove(emp);
			}
		});
	}
}

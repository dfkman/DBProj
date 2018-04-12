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
				if (dialogButton == savButtonType){
					ArrayList<String> Result = new ArrayList<>();
					Result.add(SSN.getText());
					Result.add(name.getText());
					Result.add(phone.getText());
					if (!mm.addEmployee(new Employee(SSN.getText(), name
							.getText(), phone.getText()))) {
						Alert alert = new Alert(Alert.AlertType.ERROR, "An " +
								"employee with this SSN already exists", ButtonType.OK);
						alert.showAndWait();
						return null;
					}
					return Result;
				}
				return null;
			});
			Optional<ArrayList<String>> newEntry = adddiag.showAndWait();
			if (newEntry != null) {
				data.add(new Employee(newEntry.get().get(0),newEntry.get().get
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
						Employee oldEmp = new Employee(emp.getSSN(), emp
								.getName(), emp.getPnumber());
						ArrayList<String> Result = new ArrayList<>();
						Result.add(SSN.getText());
						Result.add(name.getText());
						Result.add(phone.getText());
						emp.setSSN(SSN.getText());
						emp.setName(name.getText());
						emp.setPhone(phone.getText());
						TabView.refresh();
						mm.updateEmployee(emp, oldEmp); // not implemented yet
						//SQL goes here (UPDATE EMP VALUES WHERE...)
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

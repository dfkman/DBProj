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
	
	
	public TEController(Main mod, SQLMiddleMan mm) {
		model = mod;
		this.mm = mm;
	}
	
	@FXML
	private void initialize() throws IOException {
		namCol.setCellValueFactory(new PropertyValueFactory<Employee,String>
				("name"));
		ponCol.setCellValueFactory(new PropertyValueFactory<Employee,String>
				("pnumber"));
		ssnCol.setCellValueFactory(new PropertyValueFactory<Employee,String>
				("SSN"));
		final ObservableList<Employee> data = FXCollections
				.observableArrayList();
		//LOAD SQL HERE: REPLACE ABOVE
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
			TextField name = new TextField();
			name.setPromptText("name");
			TextField phone = new TextField();
			phone.setPromptText("phone");
			TextField SSN = new TextField();
			SSN.setPromptText("SSN");
			grid.add(new Label("Name:"), 0, 0);
			grid.add(name, 1, 0);
			grid.add(new Label("Phone:"), 0, 1);
			grid.add(phone, 1, 1);
			grid.add(new Label("SSN:"), 0, 2);
			grid.add(SSN, 1, 2);
			adddiag.getDialogPane().setContent(grid);
			Platform.runLater(() -> name.requestFocus());
			adddiag.setResultConverter(dialogButton -> {
				if (dialogButton == savButtonType){
					if(!name.getText().equalsIgnoreCase("Hello")){
						ArrayList<String> Result = new ArrayList<>();
						Result.add(name.getText());
						Result.add(phone.getText());
						Result.add(SSN.getText());
						//SQL GOES HERE (Insert into values)
						return Result;
					}
					else{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText("Look, an Error Dialog");
						alert.setContentText("Ooops, there was an error!");
						alert.showAndWait();
						adddiag.showAndWait();
					}
				}
				return null;
			});
			Optional<ArrayList<String>> newEntry = adddiag.showAndWait();
			data.add(new Employee(newEntry.get().get(0),newEntry.get().get
					(1),newEntry.get().get(2)));
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
				TextField name = new TextField();
				name.setText(emp.getName());
				TextField phone = new TextField();
				phone.setText(emp.getPnumber());
				TextField SSN = new TextField();
				SSN.setText(emp.getSSN());
				grid.add(new Label("Name:"), 0, 0);
				grid.add(name, 1, 0);
				grid.add(new Label("Phone:"), 0, 1);
				grid.add(phone, 1, 1);
				grid.add(new Label("SSN:"), 0, 2);
				grid.add(SSN, 1, 2);
				adddiag.getDialogPane().setContent(grid);
				Platform.runLater(() -> name.requestFocus());
				adddiag.setResultConverter(dialogButton -> {
					if (dialogButton == savButtonType){
						ArrayList<String> Result = new ArrayList<>();
						Result.add(name.getText());
						Result.add(phone.getText());
						Result.add(SSN.getText());
						emp.setName(name.getText());
						emp.setPhone(phone.getText());
						emp.setSSN(SSN.getText());
						TabView.refresh();
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
				//DELETE SQL HERE
				data.remove(emp);
			}
		});
	}
}

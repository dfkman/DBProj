package sample;

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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.layout.GridPane;
import javafx.geometry.*;
import javafx.application.*;
import table.Property;
import table.Appointment;
import table.Customer;
import table.SQLMiddleMan;

/**
 * Controller for the property table
 *
 * HAS ERRORS WHEN BUTTON CLICKED ON
 */
public class TAController {
	@FXML 
	private Button ba;
	
	@FXML
	private Button add;
	
	@FXML
	private Button edit;
	
	@FXML
	private Button del;
	
	@FXML
	private TableView TabView;
	
	@FXML
	private TableColumn Cust;
	@FXML
	private TableColumn Emp;
	@FXML
	private TableColumn Addr;
	@FXML
	private TableColumn Date;
	@FXML
	private TableColumn End;
	@FXML
	private TableColumn Ref;
	
	private Main model;
	private SQLMiddleMan mm;
	
	public TAController(Main mod, SQLMiddleMan mm) {
		model = mod;
		this.mm = mm;
	}
	
	@FXML
	private void initialize() throws IOException {
		Cust.setCellValueFactory(new PropertyValueFactory<Appointment,
                String>("buyer"));
		Addr.setCellValueFactory(new PropertyValueFactory<Appointment,
                String>("property"));
		Emp.setCellValueFactory(new PropertyValueFactory<Appointment,
                String>("employee"));
		Date.setCellValueFactory(new PropertyValueFactory<Appointment,
                String>("start"));
		End.setCellValueFactory(new PropertyValueFactory<Appointment,
                String>("end"));
		Ref.setCellValueFactory(new PropertyValueFactory<Appointment,
                String>("refnum"));
		ObservableList<Appointment> data = FXCollections.observableArrayList();
		mm.loadAptData(data);
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
			adddiag.setTitle("Add/Edit an Appointment...");	
			adddiag.setHeaderText("Add or Edit an Appointment's data");
			ButtonType savButtonType = new ButtonType("Save", ButtonData.OK_DONE);
			adddiag.getDialogPane().getButtonTypes().addAll(savButtonType, ButtonType.CANCEL);
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			TextField start = new TextField();
			start.setPromptText("start time");
			TextField end = new TextField();
			end.setPromptText("end time");
			TextField ref = new TextField();
			ref.setPromptText("Referral Number");
			ObservableList<String> options = mm.loadCust();
			final ComboBox cust = new ComboBox(options);
			cust.setValue(options.get(0));
			ObservableList<String> prop = mm.loadProperty();
			final ComboBox proprt = new ComboBox(prop);
			proprt.setValue(prop.get(0));
			ObservableList<String> empv = mm.loadEmployee();
			final ComboBox emp = new ComboBox(empv);
			emp.setValue(empv.get(0));
			grid.add(new Label("Property:"), 0, 0);
			grid.add(proprt, 1, 0);
			grid.add(new Label("Employee:"), 0, 1);
			grid.add(emp, 1, 1);
			grid.add(new Label("Viewer:"), 0, 2);
			grid.add(cust, 1, 2);
			grid.add(new Label("Start Time:"), 0, 3);
			grid.add(start, 1, 3);
			grid.add(new Label("End Time:"), 0, 4);
			grid.add(end, 1, 4);
			grid.add(new Label("Referral Number"), 0, 5);
			grid.add(ref, 1, 5);
			adddiag.getDialogPane().setContent(grid);
			Platform.runLater(() -> start.requestFocus());
			adddiag.setResultConverter(dialogButton -> {
				if (dialogButton == savButtonType){
					ArrayList<String> Result = new ArrayList<>();
					int slash = cust.getValue().toString().indexOf("/");
					if(slash < 0)
						slash = cust.getValue().toString().length();
					int slashe = emp.getValue().toString().indexOf("/");
					if(slashe < 0)
						slashe = emp.getValue().toString().length();
					Appointment added = new Appointment(emp.getValue().toString().substring(0, slashe),
							cust.getValue().toString().substring(0, slash),proprt.getValue().toString(),ref.getText(),
							start.getText(),end.getText());
					mm.addApt(added);
					data.add(added);
					Result.add((String) cust.getValue());
					return Result;
				}
				return null;
			});
			Optional<ArrayList<String>> newEntry = adddiag.showAndWait();

		});
		edit.setOnAction(event -> {
			Appointment oldapt = (Appointment)TabView.getSelectionModel().getSelectedItem();
			if (oldapt != null){
			Dialog<ArrayList<String>> adddiag = new Dialog<>();
			adddiag.setTitle("Add/Edit an Appointment...");	
			adddiag.setHeaderText("Add or Edit an Appointment's data");
			ButtonType savButtonType = new ButtonType("Save", ButtonData.OK_DONE);
			adddiag.getDialogPane().getButtonTypes().addAll(savButtonType, ButtonType.CANCEL);
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			TextField start = new TextField();
			start.setText(oldapt.getStart());
			TextField end = new TextField();
			end.setText(oldapt.getEnd());
			TextField ref = new TextField();
			ref.setText(oldapt.getRefnum());
			ObservableList<String> options = mm.loadCust();
			final ComboBox cust = new ComboBox(options);
			cust.setValue(oldapt.getBuyer());
			ObservableList<String> prop = mm.loadProperty();
			final ComboBox proprt = new ComboBox(prop);
			proprt.setValue(oldapt.getProperty());
			ObservableList<String> empv = mm.loadEmployee();
			final ComboBox emp = new ComboBox(empv);
			emp.setValue(oldapt.getEmployee());
			grid.add(new Label("Property:"), 0, 0);
			grid.add(proprt, 1, 0);
			grid.add(new Label("Employee:"), 0, 1);
			grid.add(emp, 1, 1);
			grid.add(new Label("Viewer:"), 0, 2);
			grid.add(cust, 1, 2);
			grid.add(new Label("Start Time:"), 0, 3);
			grid.add(start, 1, 3);
			grid.add(new Label("End Time:"), 0, 4);
			grid.add(end, 1, 4);
			grid.add(new Label("Referral Number"), 0, 5);
			grid.add(ref, 1, 5);
			adddiag.getDialogPane().setContent(grid);
			Platform.runLater(() -> start.requestFocus());
			adddiag.setResultConverter(dialogButton -> {
				if (dialogButton == savButtonType){
					ArrayList<String> Result = new ArrayList<>();
					int slash = cust.getValue().toString().indexOf("/");
					if(slash < 0)
						slash = cust.getValue().toString().length();
					int slashe = emp.getValue().toString().indexOf("/");
					if(slashe < 0)
						slashe = emp.getValue().toString().length();
					Appointment added = new Appointment(emp.getValue().toString().substring(0, slashe),
							cust.getValue().toString().substring(0, slash),proprt.getValue().toString(),ref.getText(),
							start.getText(),end.getText());
					mm.updateApt(oldapt, added);
					data.add(added);
					data.remove(oldapt);
					Result.add((String) cust.getValue());
					return Result;
				}
				return null;
			});
			Optional<ArrayList<String>> newEntry = adddiag.showAndWait();
			}
		});
		del.setOnAction(event -> {
			Appointment apt = (Appointment)TabView.getSelectionModel().getSelectedItem();
			if (apt != null){
				mm.deleteAppointment(apt);
				data.remove(apt);
			}
		});
	}
}

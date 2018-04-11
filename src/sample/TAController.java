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
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.layout.GridPane;
import javafx.geometry.*;
import javafx.application.*;
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
	private Main model;
	private SQLMiddleMan mm;
	
	public TAController(Main mod, SQLMiddleMan mm) {
		model = mod;
		this.mm = mm;
	}
	
	@FXML
	private void initialize() throws IOException {
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
			ObservableList<String> options = 
				    FXCollections.observableArrayList(
				        "Option 1",
				        "Option 2",
				        "Option 3"
				    );
			final ComboBox cust = new ComboBox(options);
			cust.setValue(options.get(0));
			ObservableList<String> prop = 
				    FXCollections.observableArrayList(
				        "Option 1",
				        "Option 2",
				        "Option 3"
				    );
			final ComboBox proprt = new ComboBox(prop);
			proprt.setValue(prop.get(0));
			ObservableList<String> empv = 
				    FXCollections.observableArrayList(
				        "Option 1",
				        "Option 2",
				        "Option 3"
				    );
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
					Result.add((String) cust.getValue());
					System.out.println(Result.get(2));
					return Result;
				}
				return null;
			});
			Optional<ArrayList<String>> newEntry = adddiag.showAndWait();

		});
	}
}

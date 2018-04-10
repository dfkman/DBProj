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

/**
 *
 */
public class TPController {
	@FXML 
	private Button Ba;
	
	@FXML
	private Button Add;
	private Main model;
	
	
	
	public TPController(Main mod) {
		model = mod;
	}
	
	@FXML
	private void initialize() throws IOException{
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
			adddiag.setTitle("Add/Edit Property...");	
			adddiag.setHeaderText("Add or Edit a Property's data");
			ButtonType savButtonType = new ButtonType("Save", ButtonData
					.OK_DONE);
			adddiag.getDialogPane().getButtonTypes().addAll(savButtonType,
					ButtonType.CANCEL);
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			TextField addr = new TextField();
			addr.setPromptText("address");
			TextField sqft = new TextField();
			sqft.setPromptText("sq ft");
			TextField listp = new TextField();
			listp.setPromptText("list price");
			TextField nbed = new TextField();
			nbed.setPromptText("Beds");
			TextField nbath = new TextField();
			nbath.setPromptText("Baths");
			ObservableList<String> options = 
				    FXCollections.observableArrayList(
				        "Option 1",
				        "Option 2",
				        "Option 3"
				    );
			final ComboBox cust = new ComboBox(options);
			cust.setValue(options.get(0));
			grid.add(new Label("Address:"), 0, 0);
			grid.add(addr, 1, 0);
			grid.add(new Label("Sq. Footage:"), 0, 1);
			grid.add(sqft, 1, 1);
			grid.add(new Label("List Price"), 0, 2);
			grid.add(listp, 1, 2);
			grid.add(new Label("Seller:"), 0, 3);
			grid.add(cust, 1, 3);
			adddiag.getDialogPane().setContent(grid);
			final ToggleGroup group = new ToggleGroup();
			RadioButton rb1 = new RadioButton("House");
			rb1.setToggleGroup(group);
			rb1.setSelected(true);
			RadioButton rb2 = new RadioButton("Land");
			rb2.setToggleGroup(group);
			grid.add(rb1, 1, 4);
			grid.add(rb2, 1, 5);
			grid.add(nbed, 1, 6);
			grid.add(new Label("Num of Beds"), 0, 6);
			grid.add(nbath, 1, 7);
			grid.add(new Label("Num of Baths"), 0, 7);
			Platform.runLater(() -> addr.requestFocus());
			adddiag.setResultConverter(dialogButton -> {
				if (dialogButton == savButtonType){
					ArrayList<String> Result = new ArrayList<>();
					Result.add(addr.getText());
					Result.add(sqft.getText());
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

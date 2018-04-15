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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.layout.GridPane;
import javafx.geometry.*;
import javafx.application.*;
import table.Property;
import table.SQLMiddleMan;

/**
 *
 */
public class TPController {
	@FXML 
	private Button Ba;

	@FXML
    private TableColumn TypeCol;

    @FXML
    private TableColumn AddrCol;

    @FXML
    private TableColumn PriceCol;

    @FXML
    private TableColumn SqFtCol;

    @FXML
    private TableColumn BedCol;

    @FXML
    private TableColumn BathCol;
	
	@FXML
	private Button Add;

	private Main model;

	private SQLMiddleMan mm;
	
	
	public TPController(Main mod, SQLMiddleMan mm) {
		model = mod;
		this.mm = mm;
	}
	
	@FXML
	private void initialize() throws IOException {
        TypeCol.setCellValueFactory(new PropertyValueFactory<Property,
                String>("Type"));
        AddrCol.setCellValueFactory(new PropertyValueFactory<Property,
                String>("Address"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<Property,
                String>("Price"));
        SqFtCol.setCellValueFactory(new PropertyValueFactory<Property,
                String>("Square Feet"));
        BedCol.setCellValueFactory(new PropertyValueFactory<Property,
                String>("Beds"));
        BathCol.setCellValueFactory(new PropertyValueFactory<Property,
                String>("Baths"));
        ObservableList<Property> data = FXCollections.observableArrayList();
        mm.loadPropertyData();

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
			ObservableList<String> options = mm.loadCust();
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

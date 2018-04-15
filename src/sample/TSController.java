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
import java.util.concurrent.Delayed;

import javafx.scene.layout.GridPane;
import javafx.geometry.*;
import javafx.application.*;
import table.Employee;
import table.Customer;
import table.Sale;
import table.SQLMiddleMan;

public class TSController {
	@FXML 
	private Button Ba;
	
	@FXML
	private Button Del;
	
	@FXML
	private Button Edit;

	@FXML
	private TableView TabView;
	
	@FXML
	private TableColumn Emp;
	@FXML
	private TableColumn Addr;
	@FXML
	private TableColumn Buy;
	@FXML
	private TableColumn Seller;
	@FXML
	private TableColumn Date;
	@FXML
	private TableColumn Ref;
	
	@FXML
	private TableColumn Price;
	
	@FXML
	private Button Add;
	private Main model;
    private SQLMiddleMan mm;
	

	public TSController(Main mod, SQLMiddleMan mm) {
		model = mod;
		this.mm = mm;
	}
	
	@FXML
	private void initialize() throws IOException{
		Addr.setCellValueFactory(new PropertyValueFactory<Sale,String>
			("Prop"));
		Buy.setCellValueFactory(new PropertyValueFactory<Sale,String>
			("buyer"));
		Seller.setCellValueFactory(new PropertyValueFactory<Sale,String>
			("seller"));
		Emp.setCellValueFactory(new PropertyValueFactory<Sale,String>
			("emp"));
		Price.setCellValueFactory(new PropertyValueFactory<Sale,String>
			("price"));
		Date.setCellValueFactory(new PropertyValueFactory<Sale,String>
			("date"));
		Ref.setCellValueFactory(new PropertyValueFactory<Sale,String>
			("refNum"));
		ObservableList<Sale> data = FXCollections.observableArrayList();
		mm.loadSaleData(data);
		TabView.setItems(data);
		Ba.setOnAction(event ->{
			try
			{
				model.swapScene('m');
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Add.setOnAction(event ->{
			Dialog<ArrayList<String>> adddiag = new Dialog<>();
			adddiag.setTitle("Add/Edit a Sale...");	
			adddiag.setHeaderText("Add or Edit a Sale's data");
			ButtonType savButtonType = new ButtonType("Save", ButtonData.OK_DONE);
			adddiag.getDialogPane().getButtonTypes().addAll(savButtonType, ButtonType.CANCEL);
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			TextField date = new TextField();
			date.setPromptText("time of sale");
			TextField salep = new TextField();
			salep.setPromptText("sale price");
			TextField ref = new TextField();
			ref.setPromptText("Referral Number");
			ObservableList<String> options = mm.loadCust();
			ComboBox cust = new ComboBox(options);
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
			grid.add(new Label("Buyer:"), 0, 2);
			grid.add(cust, 1, 2);
			grid.add(new Label("Date:"), 0, 3);
			grid.add(date, 1, 3);
			grid.add(new Label("Sell Price:"), 0, 4);
			grid.add(salep, 1, 4);
			grid.add(new Label("Referral Number"), 0, 5);
			grid.add(ref, 1, 5);
			adddiag.getDialogPane().setContent(grid);
			Platform.runLater(() -> date.requestFocus());
			adddiag.setResultConverter(dialogButton -> {
				if (dialogButton == savButtonType) {
					if (ref.getText().trim().isEmpty() || date.getText().trim
							().isEmpty() || salep.getText().trim().isEmpty()) {
						CTController.emptyInputAlert.showAndWait();
						return null;
					}
					ArrayList<String> Result = new ArrayList<>();
					Result.add((String) cust.getValue());
					String sellerID = mm.getSellerID(proprt.getValue().toString());
					Sale added = new Sale(emp.getValue().toString().substring(0, emp 
							.getValue().toString().indexOf("/")), cust.getValue().toString().substring(0, cust.getValue().toString().indexOf("/")),
							sellerID,proprt.getValue().toString(),ref.getText(),date.getText(),
							salep.getText());
					mm.addSale(added);
					data.add(added);
					return Result;
				}
				return null;
			});
			Optional<ArrayList<String>> newEntry = adddiag.showAndWait();
		});
		
		Edit.setOnAction(event ->{
			Sale oldsale = (Sale) TabView.getSelectionModel().getSelectedItem();
			if (oldsale != null){
			Dialog<ArrayList<String>> adddiag = new Dialog<>();
			adddiag.setTitle("Add/Edit a Sale...");	
			adddiag.setHeaderText("Add or Edit a Sale's data");
			ButtonType savButtonType = new ButtonType("Save", ButtonData.OK_DONE);
			adddiag.getDialogPane().getButtonTypes().addAll(savButtonType, ButtonType.CANCEL);
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			TextField date = new TextField();
			date.setText(oldsale.getDate());
			TextField salep = new TextField();
			salep.setText(oldsale.getPrice());
			TextField ref = new TextField();
			ref.setText(oldsale.getRefNum());
			ObservableList<String> options = mm.loadCust();
			ComboBox cust = new ComboBox(options);
			cust.setValue(oldsale.getBuyer());
			ObservableList<String> prop = mm.loadProperty();
			final ComboBox proprt = new ComboBox(prop);
			proprt.setValue(oldsale.getProp());
			ObservableList<String> empv = mm.loadEmployee();
			final ComboBox emp = new ComboBox(empv);
			emp.setValue(oldsale.getEmp());
			grid.add(new Label("Property:"), 0, 0);
			grid.add(proprt, 1, 0);
			grid.add(new Label("Employee:"), 0, 1);
			grid.add(emp, 1, 1);
			grid.add(new Label("Buyer:"), 0, 2);
			grid.add(cust, 1, 2);
			grid.add(new Label("Date:"), 0, 3);
			grid.add(date, 1, 3);
			grid.add(new Label("Sell Price:"), 0, 4);
			grid.add(salep, 1, 4);
			grid.add(new Label("Referral Number"), 0, 5);
			grid.add(ref, 1, 5);
			adddiag.getDialogPane().setContent(grid);
			Platform.runLater(() -> date.requestFocus());
			adddiag.setResultConverter(dialogButton -> {
				if (dialogButton == savButtonType){
					if (ref.getText().trim().isEmpty() || date.getText().trim
							().isEmpty() || salep.getText().trim().isEmpty()) {
						CTController.emptyInputAlert.showAndWait();
						return null;
					}
					ArrayList<String> Result = new ArrayList<>();
					Result.add((String) cust.getValue());
					int slash = cust.getValue().toString().indexOf("/");
					if(slash < 0)
						slash = cust.getValue().toString().length();
					int slashe = emp.getValue().toString().indexOf("/");
					if(slashe < 0)
						slashe = emp.getValue().toString().length();
					String sellerID = mm.getSellerID(proprt.getValue().toString());
					Sale added = new Sale(emp.getValue().toString().substring(0, slashe),
							cust.getValue().toString().substring(0, slash),
							sellerID,proprt.getValue().toString(),ref.getText(),date.getText(),
							salep.getText());
					mm.updateSale(oldsale, added);
					data.add(added);
					data.remove(oldsale);
					return Result;
				}
				return null;
			});
			Optional<ArrayList<String>> newEntry = adddiag.showAndWait();
			}
		});
		
		Del.setOnAction(event -> {
			Sale sale = (Sale)TabView.getSelectionModel()
					.getSelectedItem();
			if (sale != null) {
				mm.deleteSale(sale);
				data.remove(sale);
			}
		});
	}
}

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
public class MMController
{
	@FXML 
	private Button Emp;
	@FXML
	private Button Cust;
	@FXML
	private Button Prop;
	@FXML
	private Button Sale;
	@FXML
	private Button Apt;
	@FXML
	private Button Exit;
	private Main model;
	

	
	public MMController(Main mod){
		model = mod;
	}
	
	@FXML
	private void initialize(){	
		Emp.setOnAction((event) ->{
			try
			{
				model.swapScene('e');
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		});
		Cust.setOnAction((event) ->{
			try
			{
				model.swapScene('c');
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		});
		Prop.setOnAction((event) ->{
			try
			{
				model.swapScene('p');
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		});
		Sale.setOnAction((event) ->{
			try
			{
				model.swapScene('s');
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		});
		Apt.setOnAction((event) ->{
			try
			{
				model.swapScene('a');
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		});
		Exit.setOnAction((event) ->{
			try
			{
				model.swapScene('q');
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		});
	}
}

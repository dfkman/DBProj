package table;
import javafx.beans.property.SimpleStringProperty;


public class Customer {
	private SimpleStringProperty name;
	private SimpleStringProperty pnumber;
	private SimpleStringProperty ID;
	private boolean isEdited;
	
	public Customer(String id, String name, String phone) {
		this.ID = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.pnumber = new SimpleStringProperty(phone);
		isEdited = false;
	}
	
	public void saveToTable() {
		//save function for going back to sql
		isEdited = false;
	}
	
	public String getName(){
		return name.get();
	}
	public String getPnumber(){
		return pnumber.get();
	}
	public String getID(){
		return ID.get();
	}
	public void setName(String s){
		name.set(s);
	}
	public void setPnumber(String s){
		pnumber.set(s);
	}
	public void setID(String s){
		ID.set(s);
	}

	
}

package table;

import javafx.beans.property.SimpleStringProperty;

public class Employee {
	private final SimpleStringProperty SSN;
	private SimpleStringProperty pnumber;
	private SimpleStringProperty name;
	private boolean isEdited;
	
	public Employee(String Name, String SSN, String phone) {
		this.name = new SimpleStringProperty(Name);
		this.SSN = new SimpleStringProperty(SSN);
		this.pnumber = new SimpleStringProperty(phone);
		isEdited = false;
	}
	
	//Setters and getters
	
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
	public String getSSN(){
		return SSN.get();
	}

}

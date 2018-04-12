package table;

import javafx.beans.property.SimpleStringProperty;

public class Employee {
	private final SimpleStringProperty SSN;
	private SimpleStringProperty pnumber;
	private SimpleStringProperty name;
	private boolean isEdited;
	
	public Employee(String SSN, String name , String phone) {
		this.name = new SimpleStringProperty(name);
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
	public void setName(String nam){
		this.name.set(nam);
	}
	public void setSSN(String ssn){
		this.SSN.set(ssn);
	}
	public void setPhone(String pn){
		this.pnumber.set(pn);
	}

}

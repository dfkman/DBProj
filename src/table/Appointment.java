package table;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {

	private SimpleStringProperty employee;
	private SimpleStringProperty buyer;
	private SimpleStringProperty prop;
	private SimpleStringProperty refnum;
	private SimpleStringProperty sdate;
	private SimpleStringProperty edate;
	boolean isEdited;
	
	public Appointment(String emp, String buy, String prop, String
			ref, String sdate, String edate) {
		this.employee = new SimpleStringProperty(emp);
		this.buyer = new SimpleStringProperty(buy);
		this.prop = new SimpleStringProperty(prop);
		this.refnum = new SimpleStringProperty(ref);
		this.sdate = new SimpleStringProperty(sdate);
		this.edate = new SimpleStringProperty(edate);
		this.isEdited = false;
	}
	public void saveToTable() {
		//save function for going back to sql
		isEdited = false;
	}
	public void setEmployee(String newValue){
		employee.set(newValue);
	}
	public void setBuyer(String newValue){
		buyer.set(newValue);
	}
	public void setProperty(String newValue){
		prop.set(newValue);
	}
	public void setRefnum(String newValue){
		refnum.set(newValue);
	}
	public void setStart(String newValue){
		sdate.set(newValue);
	}
	public void setEnd(String newValue){
		edate.set(newValue);
	}
	public String getEmployee(){
		return employee.get();
	}
	public String getBuyer(){
		return buyer.get();
	}
	public String getRefnum(){
		return refnum.get();
	}
	public String getStart(){
		return sdate.get();
	}
	public String getEnd(){
		return edate.get();
	}
	public String getProperty(){
		return prop.get();
	}
	
}

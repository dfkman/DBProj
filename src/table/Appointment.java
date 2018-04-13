package table;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {

	private SimpleStringProperty seller;
	private SimpleStringProperty buyer;
	private SimpleStringProperty prop;
	private SimpleStringProperty refnum;
	private SimpleStringProperty sdate;
	private SimpleStringProperty edate;
	boolean isEdited;
	
	public Appointment(String sell, String buy, String prop, String
			ref, String sdate, String edate) {
		this.seller.set(sell);
		this.buyer.set(buy);
		this.prop.set(prop);
		this.refnum.set(ref);
		this.sdate.set(sdate);
		this.edate.set(edate);
		this.isEdited = false;
	}
	public void saveToTable() {
		//save function for going back to sql
		isEdited = false;
	}
	public void setSeller(String newValue){
		seller.set(newValue);
	}
	public void setBuyer(String newValue){
		buyer.set(newValue);
	}
}

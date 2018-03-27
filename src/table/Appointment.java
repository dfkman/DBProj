package table;

public class Appointment
{

	Employee seller;
	Customer buyer;
	Property prop;
	String refnum;
	String sdate;
	String edate;
	boolean isEdited;
	
	public Appointment(Employee sell, Customer buy, Property prop, String ref, String sdate, String edate){
		this.seller = sell;
		this.buyer = buy;
		this.prop = prop;
		this.refnum = ref;
		this.sdate = sdate;
		this.edate = edate;
		this.isEdited = false;
	}
	
	public void saveToTable(){
		//save function for going back to sql
		isEdited = false;
	}
}

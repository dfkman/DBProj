package table;

public class Sale
{
	Employee seller;
	Customer buyer;
	Property prop;
	String refnum;
	String date;
	float price;
	boolean isEdited;
	
	public Sale(Employee sell, Customer buy, Property prop, String ref, String date, float price){
		this.seller = sell;
		this.buyer = buy;
		this.prop = prop;
		this.refnum = ref;
		this.date = date;
		this.price = price;
		this.isEdited = false;
	}
	
	public void saveToTable(){
		//save function for going back to sql
		isEdited = false;
	}

}

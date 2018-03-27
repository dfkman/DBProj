package table;

public class Customer
{
	private String name;
	private String pnumber;
	private int ID;
	private boolean isEdited;
	
	public Customer(String Name, String phone, int id){
		this.name = Name;
		this.pnumber = phone;
		this.ID = id;
		isEdited = false;
	}
	
	public void saveToTable(){
		//save function for going back to sql
		isEdited = false;
	}

	
}

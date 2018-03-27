package table;

public class Employee
{
	private String SSN;
	private String pnumber;
	private String name;
	private boolean isEdited;
	
	public Employee(String Name, String SSN, String phone){
		this.name = Name;
		this.SSN = SSN;
		this.pnumber = phone;
		isEdited = false;
	}
	
	//Setters and getters
	
	public void saveToTable(){
		//save function for going back to sql
		isEdited = false;
	}

}

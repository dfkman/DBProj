package table;

public class Property
{
	private String addr;
	private int sqft;
	private float listP;
	private boolean isEdited; 

	public Property(String addr, int sqft, float listing){
		this.addr = addr;
		this.sqft = sqft;
		this.listP = listing;
		isEdited = false;
	}
	
	public void saveToTable(){
		//save function for going back to sql
		isEdited = false;
	}
}
